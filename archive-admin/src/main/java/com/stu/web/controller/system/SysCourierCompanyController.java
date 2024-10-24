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
import com.stu.system.domain.SysCourierCompany;
import com.stu.system.service.ISysCourierCompanyService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 快递公司管理Controller
 * 
 * @author stu
 * @date 2024-10-23
 */
@RestController
@RequestMapping("/system/courierCompany")
public class SysCourierCompanyController extends BaseController
{
    @Autowired
    private ISysCourierCompanyService sysCourierCompanyService;

    /**
     * 查询快递公司管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:courierCompany:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCourierCompany sysCourierCompany)
    {
        startPage();
        List<SysCourierCompany> list = sysCourierCompanyService.selectSysCourierCompanyList(sysCourierCompany);
        return getDataTable(list);
    }

    /**
     * 导出快递公司管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:courierCompany:export')")
    @Log(title = "快递公司管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCourierCompany sysCourierCompany)
    {
        List<SysCourierCompany> list = sysCourierCompanyService.selectSysCourierCompanyList(sysCourierCompany);
        ExcelUtil<SysCourierCompany> util = new ExcelUtil<SysCourierCompany>(SysCourierCompany.class);
        util.exportExcel(response, list, "快递公司管理数据");
    }

    @Log(title = "快递公司信息导入", businessType = BusinessType.IMPORT)
    // @PreAuthorize("@ss.hasPermi('system:admissionInfo:import')")
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysCourierCompany> util = new ExcelUtil<>(SysCourierCompany.class);
        List<SysCourierCompany> sysCourierCompanyList = util.importExcel(file.getInputStream());
        String operatorName = getUsername();
        String message = sysCourierCompanyService.importSysCourierCompany(sysCourierCompanyList, operatorName);
        return success(message);
    }


    /**
     * 获取快递公司管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:courierCompany:query')")
    @GetMapping(value = "/{courierCompanyId}")
    public AjaxResult getInfo(@PathVariable("courierCompanyId") Long courierCompanyId)
    {
        return success(sysCourierCompanyService.selectSysCourierCompanyByCourierCompanyId(courierCompanyId));
    }

    /**
     * 新增快递公司管理
     */
    @PreAuthorize("@ss.hasPermi('system:courierCompany:add')")
    @Log(title = "快递公司管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCourierCompany sysCourierCompany)
    {
        return toAjax(sysCourierCompanyService.insertSysCourierCompany(sysCourierCompany));
    }

    /**
     * 修改快递公司管理
     */
    @PreAuthorize("@ss.hasPermi('system:courierCompany:edit')")
    @Log(title = "快递公司管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCourierCompany sysCourierCompany)
    {
        return toAjax(sysCourierCompanyService.updateSysCourierCompany(sysCourierCompany));
    }

    /**
     * 删除快递公司管理
     */
    @PreAuthorize("@ss.hasPermi('system:courierCompany:remove')")
    @Log(title = "快递公司管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{courierCompanyIds}")
    public AjaxResult remove(@PathVariable Long[] courierCompanyIds)
    {
        return toAjax(sysCourierCompanyService.deleteSysCourierCompanyByCourierCompanyIds(courierCompanyIds));
    }
}
