package com.stu.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.stu.system.domain.SysGraduateSchool;
import com.stu.system.service.ISysGraduateSchoolService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;

/**
 * 毕业院校管理Controller
 * 
 * @author stu
 * @date 2024-10-23
 */
@RestController
@RequestMapping("/system/school")
public class SysGraduateSchoolController extends BaseController
{
    @Autowired
    private ISysGraduateSchoolService sysGraduateSchoolService;

    /**
     * 查询毕业院校管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:school:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysGraduateSchool sysGraduateSchool)
    {
        startPage();
        List<SysGraduateSchool> list = sysGraduateSchoolService.selectSysGraduateSchoolList(sysGraduateSchool);
        return getDataTable(list);
    }

    /**
     * 导出毕业院校管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:school:export')")
    @Log(title = "毕业院校管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGraduateSchool sysGraduateSchool)
    {
        List<SysGraduateSchool> list = sysGraduateSchoolService.selectSysGraduateSchoolList(sysGraduateSchool);
        ExcelUtil<SysGraduateSchool> util = new ExcelUtil<SysGraduateSchool>(SysGraduateSchool.class);
        util.exportExcel(response, list, "毕业院校管理数据");
    }

    /**
     * 获取毕业院校管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:school:query')")
    @GetMapping(value = "/{graduateSchoolId}")
    public AjaxResult getInfo(@PathVariable("graduateSchoolId") Long graduateSchoolId)
    {
        return success(sysGraduateSchoolService.selectSysGraduateSchoolByGraduateSchoolId(graduateSchoolId));
    }

    /**
     * 新增毕业院校管理
     */
    @PreAuthorize("@ss.hasPermi('system:school:add')")
    @Log(title = "毕业院校管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGraduateSchool sysGraduateSchool)
    {
        return toAjax(sysGraduateSchoolService.insertSysGraduateSchool(sysGraduateSchool));
    }

    /**
     * 修改毕业院校管理
     */
    @PreAuthorize("@ss.hasPermi('system:school:edit')")
    @Log(title = "毕业院校管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGraduateSchool sysGraduateSchool)
    {
        return toAjax(sysGraduateSchoolService.updateSysGraduateSchool(sysGraduateSchool));
    }

    /**
     * 删除毕业院校管理
     */
    @PreAuthorize("@ss.hasPermi('system:school:remove')")
    @Log(title = "毕业院校管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{graduateSchoolIds}")
    public AjaxResult remove(@PathVariable Long[] graduateSchoolIds)
    {
        return toAjax(sysGraduateSchoolService.deleteSysGraduateSchoolByGraduateSchoolIds(graduateSchoolIds));
    }
}
