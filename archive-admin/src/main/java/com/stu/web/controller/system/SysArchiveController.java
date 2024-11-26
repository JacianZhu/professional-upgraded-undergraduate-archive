package com.stu.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.stu.common.core.domain.entity.SysUser;
import com.stu.common.core.domain.model.LoginUser;
import com.stu.common.utils.SecurityUtils;
import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.domain.SysArchiveReceiver;
import com.stu.system.domain.SysHeadTeacher;
import com.stu.system.service.ISysArchiveReceiverService;
import com.stu.system.service.ISysHeadTeacherService;
import com.stu.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stu.common.annotation.Log;
import com.stu.common.core.controller.BaseController;
import com.stu.common.core.domain.AjaxResult;
import com.stu.common.enums.BusinessType;
import com.stu.system.domain.SysArchive;
import com.stu.system.service.ISysArchiveService;
import com.stu.common.utils.poi.ExcelUtil;
import com.stu.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 档案信息Controller
 *
 * @author stu
 * @date 2024-11-03
 */
@RestController
@RequestMapping("/system/archiveInfo")
public class SysArchiveController extends BaseController {
    @Autowired
    private ISysArchiveService sysArchiveService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysArchiveReceiverService sysArchiveReceiverService;

    @Autowired
    private ISysHeadTeacherService sysHeadTeacherService;

    /**
     * 查询档案信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysArchive sysArchive) {
        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser.getUser().getRoles().get(0).getRoleId()==3){
            sysArchive.setTeacherReceiveFlag("1");
            sysArchive.setRecipient(loginUser.getUsername());
        }
        List<SysArchive> list = sysArchiveService.selectSysArchiveList(sysArchive);
        return getDataTable(list);
    }

    /**
     * 导出档案信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:export')")
    @Log(title = "档案信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysArchive sysArchive) {
        List<SysArchive> list = sysArchiveService.selectSysArchiveList(sysArchive);
        ExcelUtil<SysArchive> util = new ExcelUtil<SysArchive>(SysArchive.class);
        util.exportExcel(response, list, "档案信息数据");
    }

    @Log(title = "档案信息导入", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysArchive> util = new ExcelUtil<>(SysArchive.class);
        List<SysArchive> sysAdmissionInfoList = util.importExcel(file.getInputStream());
        String operatorName = getUsername();
        String message = sysArchiveService.importArchiveInfo(sysAdmissionInfoList, operatorName);
        return success(message);
    }

    /**
     * 获取档案信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:query')")
    @GetMapping(value = "/{archiveId}")
    public AjaxResult getInfo(@PathVariable("archiveId") Long archiveId) {
        return success(sysArchiveService.selectSysArchiveByArchiveId(archiveId));
    }

    /**
     * 新增档案信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:add')")
    @Log(title = "档案信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysArchive sysArchive) {
        return toAjax(sysArchiveService.insertSysArchive(sysArchive));
    }

    /**
     * 修改档案信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:edit')")
    @Log(title = "档案信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysArchive sysArchive) {
        return toAjax(sysArchiveService.updateSysArchive(sysArchive));
    }

    /**
     * 删除档案信息
     */
    @PreAuthorize("@ss.hasPermi('system:archiveInfo:remove')")
    @Log(title = "档案信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{archiveIds}")
    public AjaxResult remove(@PathVariable Long[] archiveIds) {
        return toAjax(sysArchiveService.deleteSysArchiveByArchiveIds(archiveIds));
    }

    @GetMapping("/selectSysArchiveByName")
    public SysArchive list(@RequestParam("username") String username) {
        SysArchive sysArchive = new SysArchive();
        sysArchive.setStudentName(username);
        List<SysArchive> list = sysArchiveService.selectSysArchiveList(sysArchive);
        return list.get(0);
    }


    @GetMapping(value = "/getAllReceiveList")
    public AjaxResult getAllReceiveList() {
        return success(sysArchiveReceiverService.selectSysArchiveReceiverList(new SysArchiveReceiver()));
    }

    @GetMapping(value = "/getAllHeadTeacherList")
    public AjaxResult getAllHeadTeacherList() {
        return success(sysHeadTeacherService.selectSysHeadTeacherList(new SysHeadTeacher()));
    }

    @GetMapping(value = "/updateTeacherReceiveFlag")
    public AjaxResult updateTeacherReceiveFlag(@RequestParam("username") String username) {
        List<SysArchive> list = sysArchiveService.selectSysArchiveList(new SysArchive());
        list.forEach(item->{
            if(username.equals(item.getRecipient())){
                item.setTeacherReceiveFlag("1");
            }
            sysArchiveService.updateSysArchive(item);
        });

        return success();
    }

}
