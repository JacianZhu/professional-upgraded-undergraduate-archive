package com.stu.system.service;

import java.util.List;

import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.domain.SysArchiveReceiver;

/**
 * 档案接收人管理Service接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface ISysArchiveReceiverService 
{
    /**
     * 查询档案接收人管理
     * 
     * @param receiverId 档案接收人管理主键
     * @return 档案接收人管理
     */
    public SysArchiveReceiver selectSysArchiveReceiverByReceiverId(Long receiverId);

    /**
     * 查询档案接收人管理列表
     * 
     * @param sysArchiveReceiver 档案接收人管理
     * @return 档案接收人管理集合
     */
    public List<SysArchiveReceiver> selectSysArchiveReceiverList(SysArchiveReceiver sysArchiveReceiver);

    /**
     * 新增档案接收人管理
     * 
     * @param sysArchiveReceiver 档案接收人管理
     * @return 结果
     */
    public int insertSysArchiveReceiver(SysArchiveReceiver sysArchiveReceiver);

    /**
     * 修改档案接收人管理
     * 
     * @param sysArchiveReceiver 档案接收人管理
     * @return 结果
     */
    public int updateSysArchiveReceiver(SysArchiveReceiver sysArchiveReceiver);

    /**
     * 批量删除档案接收人管理
     * 
     * @param receiverIds 需要删除的档案接收人管理主键集合
     * @return 结果
     */
    public int deleteSysArchiveReceiverByReceiverIds(Long[] receiverIds);

    /**
     * 删除档案接收人管理信息
     * 
     * @param receiverId 档案接收人管理主键
     * @return 结果
     */
    public int deleteSysArchiveReceiverByReceiverId(Long receiverId);



    /**
     * 导入用户数据
     *
     * @param sysAdmissionInfoList 用户数据列表
     * @param operatorName 操作用户
     * @return 结果
     */
    public String importArchiveReceiverInfo(List<SysArchiveReceiver> sysAdmissionInfoList, String operatorName);


}
