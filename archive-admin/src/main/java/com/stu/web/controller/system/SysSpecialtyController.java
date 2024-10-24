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
import com.stu.system.domain.SysSpecialty;
import com.stu.system.service.ISysSpecialtyService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 专业管理Controller
 *
 * @author stu
 * @date 2024-10-23
 */
@RestController
@RequestMapping("/system/specialty")
public class SysSpecialtyController extends BaseController {
    @Autowired
    private ISysSpecialtyService sysSpecialtyService;

    /**
     * 查询专业管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:specialty:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSpecialty sysSpecialty) {
        startPage();
        List<SysSpecialty> list = sysSpecialtyService.selectSysSpecialtyList(sysSpecialty);
        return getDataTable(list);
    }

    /**
     * 导出专业管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:specialty:export')")
    @Log(title = "专业管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSpecialty sysSpecialty) {
        List<SysSpecialty> list = sysSpecialtyService.selectSysSpecialtyList(sysSpecialty);
        ExcelUtil<SysSpecialty> util = new ExcelUtil<SysSpecialty>(SysSpecialty.class);
        util.exportExcel(response, list, "专业管理数据");
    }

    @Log(title = "专业信息导入", businessType = BusinessType.IMPORT)
    // @PreAuthorize("@ss.hasPermi('system:admissionInfo:import')")
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysSpecialty> util = new ExcelUtil<>(SysSpecialty.class);
        List<SysSpecialty> sysAdmissionInfoList = util.importExcel(file.getInputStream());
        String operatorName = getUsername();
        String message = sysSpecialtyService.importSysSpecialtyInfo(sysAdmissionInfoList, operatorName);
        return success(message);
    }


    /**
     * 获取专业管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:specialty:query')")
    @GetMapping(value = "/{specialtyId}")
    public AjaxResult getInfo(@PathVariable("specialtyId") Long specialtyId) {
        return success(sysSpecialtyService.selectSysSpecialtyBySpecialtyId(specialtyId));
    }

    /**
     * 新增专业管理
     */
    @PreAuthorize("@ss.hasPermi('system:specialty:add')")
    @Log(title = "专业管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSpecialty sysSpecialty) {
        return toAjax(sysSpecialtyService.insertSysSpecialty(sysSpecialty));
    }

    /**
     * 修改专业管理
     */
    @PreAuthorize("@ss.hasPermi('system:specialty:edit')")
    @Log(title = "专业管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSpecialty sysSpecialty) {
        return toAjax(sysSpecialtyService.updateSysSpecialty(sysSpecialty));
    }

    /**
     * 删除专业管理
     */
    @PreAuthorize("@ss.hasPermi('system:specialty:remove')")
    @Log(title = "专业管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{specialtyIds}")
    public AjaxResult remove(@PathVariable Long[] specialtyIds) {
        return toAjax(sysSpecialtyService.deleteSysSpecialtyBySpecialtyIds(specialtyIds));
    }
}
