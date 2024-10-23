package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysArchiveReceiverMapper;
import com.stu.system.domain.SysArchiveReceiver;
import com.stu.system.service.ISysArchiveReceiverService;

/**
 * 档案接收人管理Service业务层处理
 * 
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysArchiveReceiverServiceImpl implements ISysArchiveReceiverService 
{
    @Autowired
    private SysArchiveReceiverMapper sysArchiveReceiverMapper;

    /**
     * 查询档案接收人管理
     * 
     * @param receiverId 档案接收人管理主键
     * @return 档案接收人管理
     */
    @Override
    public SysArchiveReceiver selectSysArchiveReceiverByReceiverId(Long receiverId)
    {
        return sysArchiveReceiverMapper.selectSysArchiveReceiverByReceiverId(receiverId);
    }

    /**
     * 查询档案接收人管理列表
     * 
     * @param sysArchiveReceiver 档案接收人管理
     * @return 档案接收人管理
     */
    @Override
    public List<SysArchiveReceiver> selectSysArchiveReceiverList(SysArchiveReceiver sysArchiveReceiver)
    {
        return sysArchiveReceiverMapper.selectSysArchiveReceiverList(sysArchiveReceiver);
    }

    /**
     * 新增档案接收人管理
     * 
     * @param sysArchiveReceiver 档案接收人管理
     * @return 结果
     */
    @Override
    public int insertSysArchiveReceiver(SysArchiveReceiver sysArchiveReceiver)
    {
        sysArchiveReceiver.setCreateTime(DateUtils.getNowDate());
        return sysArchiveReceiverMapper.insertSysArchiveReceiver(sysArchiveReceiver);
    }

    /**
     * 修改档案接收人管理
     * 
     * @param sysArchiveReceiver 档案接收人管理
     * @return 结果
     */
    @Override
    public int updateSysArchiveReceiver(SysArchiveReceiver sysArchiveReceiver)
    {
        sysArchiveReceiver.setUpdateTime(DateUtils.getNowDate());
        return sysArchiveReceiverMapper.updateSysArchiveReceiver(sysArchiveReceiver);
    }

    /**
     * 批量删除档案接收人管理
     * 
     * @param receiverIds 需要删除的档案接收人管理主键
     * @return 结果
     */
    @Override
    public int deleteSysArchiveReceiverByReceiverIds(Long[] receiverIds)
    {
        return sysArchiveReceiverMapper.deleteSysArchiveReceiverByReceiverIds(receiverIds);
    }

    /**
     * 删除档案接收人管理信息
     * 
     * @param receiverId 档案接收人管理主键
     * @return 结果
     */
    @Override
    public int deleteSysArchiveReceiverByReceiverId(Long receiverId)
    {
        return sysArchiveReceiverMapper.deleteSysArchiveReceiverByReceiverId(receiverId);
    }
}
