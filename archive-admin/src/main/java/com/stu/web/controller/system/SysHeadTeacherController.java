package com.stu.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.stu.system.domain.SysAdmissionInfo;
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
import com.stu.system.domain.SysHeadTeacher;
import com.stu.system.service.ISysHeadTeacherService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 班主任管理Controller
 * 
 * @author stu
 * @date 2024-10-23
 */
@RestController
@RequestMapping("/system/teacher")
public class SysHeadTeacherController extends BaseController
{
    @Autowired
    private ISysHeadTeacherService sysHeadTeacherService;

    /**
     * 查询班主任管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysHeadTeacher sysHeadTeacher)
    {
        startPage();
        List<SysHeadTeacher> list = sysHeadTeacherService.selectSysHeadTeacherList(sysHeadTeacher);
        return getDataTable(list);
    }

    /**
     * 导出班主任管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:export')")
    @Log(title = "班主任管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysHeadTeacher sysHeadTeacher)
    {
        List<SysHeadTeacher> list = sysHeadTeacherService.selectSysHeadTeacherList(sysHeadTeacher);
        ExcelUtil<SysHeadTeacher> util = new ExcelUtil<SysHeadTeacher>(SysHeadTeacher.class);
        util.exportExcel(response, list, "班主任管理数据");
    }


    @Log(title = "班主任导入", businessType = BusinessType.IMPORT)
    // @PreAuthorize("@ss.hasPermi('system:admissionInfo:import')")
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysHeadTeacher> util = new ExcelUtil<>(SysHeadTeacher.class);
        List<SysHeadTeacher> sysAdmissionInfoList = util.importExcel(file.getInputStream());
        String operatorName = getUsername();
        String message = sysHeadTeacherService.importSysHeadTeacherInfo(sysAdmissionInfoList, operatorName);
        return success(message);
    }

    /**
     * 获取班主任管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:query')")
    @GetMapping(value = "/{headTeacherId}")
    public AjaxResult getInfo(@PathVariable("headTeacherId") Long headTeacherId)
    {
        return success(sysHeadTeacherService.selectSysHeadTeacherByHeadTeacherId(headTeacherId));
    }

    /**
     * 新增班主任管理
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:add')")
    @Log(title = "班主任管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysHeadTeacher sysHeadTeacher)
    {
        return toAjax(sysHeadTeacherService.insertSysHeadTeacher(sysHeadTeacher));
    }

    /**
     * 修改班主任管理
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:edit')")
    @Log(title = "班主任管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysHeadTeacher sysHeadTeacher)
    {
        return toAjax(sysHeadTeacherService.updateSysHeadTeacher(sysHeadTeacher));
    }

    /**
     * 删除班主任管理
     */
    @PreAuthorize("@ss.hasPermi('system:teacher:remove')")
    @Log(title = "班主任管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{headTeacherIds}")
    public AjaxResult remove(@PathVariable Long[] headTeacherIds)
    {
        return toAjax(sysHeadTeacherService.deleteSysHeadTeacherByHeadTeacherIds(headTeacherIds));
    }
}
