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
import com.stu.system.domain.SysArchive;
import com.stu.system.service.ISysArchiveService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;

/**
 * 档案信息Controller
 * 
 * @author stu
 * @date 2024-11-03
 */
@RestController
@RequestMapping("/system/archiveInfo")
public class SysArchiveController extends BaseController
{
    @Autowired
    private ISysArchiveService sysArchiveService;

    /**
     * 查询档案信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysArchive sysArchive)
    {
        startPage();
        List<SysArchive> list = sysArchiveService.selectSysArchiveList(sysArchive);
        return getDataTable(list);
    }

    /**
     * 导出档案信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:export')")
    @Log(title = "档案信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysArchive sysArchive)
    {
        List<SysArchive> list = sysArchiveService.selectSysArchiveList(sysArchive);
        ExcelUtil<SysArchive> util = new ExcelUtil<SysArchive>(SysArchive.class);
        util.exportExcel(response, list, "档案信息数据");
    }

    /**
     * 获取档案信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:query')")
    @GetMapping(value = "/{archiveId}")
    public AjaxResult getInfo(@PathVariable("archiveId") Long archiveId)
    {
        return success(sysArchiveService.selectSysArchiveByArchiveId(archiveId));
    }

    /**
     * 新增档案信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:add')")
    @Log(title = "档案信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysArchive sysArchive)
    {
        return toAjax(sysArchiveService.insertSysArchive(sysArchive));
    }

    /**
     * 修改档案信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:edit')")
    @Log(title = "档案信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysArchive sysArchive)
    {
        return toAjax(sysArchiveService.updateSysArchive(sysArchive));
    }

    /**
     * 删除档案信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:remove')")
    @Log(title = "档案信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{archiveIds}")
    public AjaxResult remove(@PathVariable Long[] archiveIds)
    {
        return toAjax(sysArchiveService.deleteSysArchiveByArchiveIds(archiveIds));
    }
}
