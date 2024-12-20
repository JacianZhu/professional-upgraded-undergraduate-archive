package com.stu.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.stu.system.domain.*;
import com.stu.system.service.ISysGraduateSchoolService;
import com.stu.system.service.ISysHeadTeacherService;
import com.stu.system.service.ISysSpecialtyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stu.common.annotation.Log;
import com.stu.common.core.controller.BaseController;
import com.stu.common.core.domain.AjaxResult;
import com.stu.common.enums.BusinessType;
import com.stu.system.service.ISysClassService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 班级管理Controller
 *
 * @author stu
 * @date 2024-10-23
 */
@RestController
@RequestMapping("/system/class")
public class SysClassController extends BaseController {
    @Autowired
    private ISysClassService sysClassService;

    @Autowired
    private ISysSpecialtyService sysSpecialtyService;

    @Autowired
    private ISysHeadTeacherService sysHeadTeacherService;

    @Autowired
    private ISysGraduateSchoolService sysGraduateSchoolService;

    /**
     * 查询班级管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:class:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysClass sysClass) {
        startPage();
        List<SysClass> list = sysClassService.selectSysClassList(sysClass);
        return getDataTable(list);
    }

    /**
     * 导出班级管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:class:export')")
    @Log(title = "班级管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysClass sysClass) {
        List<SysClass> list = sysClassService.selectSysClassList(sysClass);
        ExcelUtil<SysClass> util = new ExcelUtil<SysClass>(SysClass.class);
        util.exportExcel(response, list, "班级管理数据");
    }

    @Log(title = "录取信息导入", businessType = BusinessType.IMPORT)
    // @PreAuthorize("@ss.hasPermi('system:admissionInfo:import')")
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysClass> util = new ExcelUtil<>(SysClass.class);
        List<SysClass> sysAdmissionInfoList = util.importExcel(file.getInputStream());
        String operatorName = getUsername();
        String message = sysClassService.importSysClassInfo(sysAdmissionInfoList, operatorName);
        return success(message);
    }


    /**
     * 获取班级管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:class:query')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") Long classId) {
        return success(sysClassService.selectSysClassByClassId(classId));
    }

    /**
     * 新增班级管理
     */
    @PreAuthorize("@ss.hasPermi('system:class:add')")
    @Log(title = "班级管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysClass sysClass) {
        return toAjax(sysClassService.insertSysClass(sysClass));
    }

    /**
     * 修改班级管理
     */
    @PreAuthorize("@ss.hasPermi('system:class:edit')")
    @Log(title = "班级管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysClass sysClass) {
        return toAjax(sysClassService.updateSysClass(sysClass));
    }

    /**
     * 删除班级管理
     */
    @PreAuthorize("@ss.hasPermi('system:class:remove')")
    @Log(title = "班级管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable Long[] classIds) {
        return toAjax(sysClassService.deleteSysClassByClassIds(classIds));
    }


    @GetMapping("/getSpecialtyList")
    public AjaxResult getSpecialtyList() {
        return success(sysSpecialtyService.selectSysSpecialtyList(new SysSpecialty()));
    }

    @GetMapping("/getHeadTeacherList")
    public AjaxResult getHeadTeacherList() {
        return success(sysHeadTeacherService.selectSysHeadTeacherList(new SysHeadTeacher()));
    }

    @GetMapping("/getSchoolList")
    public AjaxResult getSchoolList() {
        return success(sysGraduateSchoolService.selectSysGraduateSchoolList(new SysGraduateSchool()));
    }
}
