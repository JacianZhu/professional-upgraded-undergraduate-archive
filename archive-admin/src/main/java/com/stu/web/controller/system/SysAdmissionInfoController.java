package com.stu.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.stu.common.core.domain.entity.SysUser;
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
import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.service.ISysAdmissionInfoService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 录取信息导入Controller
 *
 * @author stu
 * @date 2024-10-23
 */
@RestController
@RequestMapping("/system/admissionInfo")
public class SysAdmissionInfoController extends BaseController {
    @Autowired
    private ISysAdmissionInfoService sysAdmissionInfoService;

    /**
     * 查询录取信息导入列表
     */
    @PreAuthorize("@ss.hasPermi('system:admissionInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysAdmissionInfo sysAdmissionInfo) {
        startPage();
        List<SysAdmissionInfo> list = sysAdmissionInfoService.selectSysAdmissionInfoList(sysAdmissionInfo);
        return getDataTable(list);
    }

    /**
     * 导出录取信息导入列表
     */
    @PreAuthorize("@ss.hasPermi('system:admissionInfo:export')")
    @Log(title = "录取信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysAdmissionInfo sysAdmissionInfo) {
        List<SysAdmissionInfo> list = sysAdmissionInfoService.selectSysAdmissionInfoList(sysAdmissionInfo);
        ExcelUtil<SysAdmissionInfo> util = new ExcelUtil<SysAdmissionInfo>(SysAdmissionInfo.class);
        util.exportExcel(response, list, "录取信息导入数据");
    }


    @Log(title = "录取信息导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysAdmissionInfo> util = new ExcelUtil<>(SysAdmissionInfo.class);
        List<SysAdmissionInfo> sysAdmissionInfoList = util.importExcel(file.getInputStream());
        String operatorName = getUsername();
        String message = sysAdmissionInfoService.importAdmissionInfo(sysAdmissionInfoList, operatorName);
        return success(message);
    }

    /**
     * 获取录取信息导入详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:admissionInfo:query')")
    @GetMapping(value = "/{admissionId}")
    public AjaxResult getInfo(@PathVariable("admissionId") Long admissionId) {
        return success(sysAdmissionInfoService.selectSysAdmissionInfoByAdmissionId(admissionId));
    }

    /**
     * 新增录取信息导入
     */
    @PreAuthorize("@ss.hasPermi('system:admissionInfo:add')")
    @Log(title = "录取信息导入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAdmissionInfo sysAdmissionInfo) {
        return toAjax(sysAdmissionInfoService.insertSysAdmissionInfo(sysAdmissionInfo));
    }

    /**
     * 修改录取信息导入
     */
    @PreAuthorize("@ss.hasPermi('system:admissionInfo:edit')")
    @Log(title = "录取信息导入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAdmissionInfo sysAdmissionInfo) {
        return toAjax(sysAdmissionInfoService.updateSysAdmissionInfo(sysAdmissionInfo));
    }

    /**
     * 删除录取信息导入
     */
    @PreAuthorize("@ss.hasPermi('system:admissionInfo:remove')")
    @Log(title = "录取信息导入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{admissionIds}")
    public AjaxResult remove(@PathVariable Long[] admissionIds) {
        return toAjax(sysAdmissionInfoService.deleteSysAdmissionInfoByAdmissionIds(admissionIds));
    }
}
