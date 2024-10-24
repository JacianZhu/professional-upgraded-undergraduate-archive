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
import com.stu.system.domain.SysArchiveReceiver;
import com.stu.system.service.ISysArchiveReceiverService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 档案接收人管理Controller
 *
 * @author stu
 * @date 2024-10-23
 */
@RestController
@RequestMapping("/system/receiver")
public class SysArchiveReceiverController extends BaseController {
    @Autowired
    private ISysArchiveReceiverService sysArchiveReceiverService;

    /**
     * 查询档案接收人管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:receiver:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysArchiveReceiver sysArchiveReceiver) {
        startPage();
        List<SysArchiveReceiver> list = sysArchiveReceiverService.selectSysArchiveReceiverList(sysArchiveReceiver);
        return getDataTable(list);
    }

    /**
     * 导出档案接收人管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:receiver:export')")
    @Log(title = "档案接收人管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysArchiveReceiver sysArchiveReceiver) {
        List<SysArchiveReceiver> list = sysArchiveReceiverService.selectSysArchiveReceiverList(sysArchiveReceiver);
        ExcelUtil<SysArchiveReceiver> util = new ExcelUtil<SysArchiveReceiver>(SysArchiveReceiver.class);
        util.exportExcel(response, list, "档案接收人管理数据");
    }

    @Log(title = "档案接收人管理导入", businessType = BusinessType.IMPORT)
    // @PreAuthorize("@ss.hasPermi('system:admissionInfo:import')")
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysArchiveReceiver> util = new ExcelUtil<>(SysArchiveReceiver.class);
        List<SysArchiveReceiver> sysArchiveReceiverList = util.importExcel(file.getInputStream());
        String operatorName = getUsername();
        String message = sysArchiveReceiverService.importArchiveReceiverInfo(sysArchiveReceiverList, operatorName);
        return success(message);
    }


    /**
     * 获取档案接收人管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:receiver:query')")
    @GetMapping(value = "/{receiverId}")
    public AjaxResult getInfo(@PathVariable("receiverId") Long receiverId) {
        return success(sysArchiveReceiverService.selectSysArchiveReceiverByReceiverId(receiverId));
    }

    /**
     * 新增档案接收人管理
     */
    @PreAuthorize("@ss.hasPermi('system:receiver:add')")
    @Log(title = "档案接收人管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysArchiveReceiver sysArchiveReceiver) {
        return toAjax(sysArchiveReceiverService.insertSysArchiveReceiver(sysArchiveReceiver));
    }

    /**
     * 修改档案接收人管理
     */
    @PreAuthorize("@ss.hasPermi('system:receiver:edit')")
    @Log(title = "档案接收人管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysArchiveReceiver sysArchiveReceiver) {
        return toAjax(sysArchiveReceiverService.updateSysArchiveReceiver(sysArchiveReceiver));
    }

    /**
     * 删除档案接收人管理
     */
    @PreAuthorize("@ss.hasPermi('system:receiver:remove')")
    @Log(title = "档案接收人管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{receiverIds}")
    public AjaxResult remove(@PathVariable Long[] receiverIds) {
        return toAjax(sysArchiveReceiverService.deleteSysArchiveReceiverByReceiverIds(receiverIds));
    }

}
