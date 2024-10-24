package com.stu.system.service.impl;

import java.util.List;

import com.stu.common.exception.ServiceException;
import com.stu.common.utils.DateUtils;
import com.stu.common.utils.StringUtils;
import com.stu.common.utils.bean.BeanValidators;
import com.stu.system.domain.SysAdmissionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysArchiveReceiverMapper;
import com.stu.system.domain.SysArchiveReceiver;
import com.stu.system.service.ISysArchiveReceiverService;

import javax.validation.Validator;

/**
 * 档案接收人管理Service业务层处理
 *
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysArchiveReceiverServiceImpl implements ISysArchiveReceiverService {

    private static final Logger log = LoggerFactory.getLogger(SysArchiveReceiverServiceImpl.class);

    @Autowired
    private SysArchiveReceiverMapper sysArchiveReceiverMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询档案接收人管理
     *
     * @param receiverId 档案接收人管理主键
     * @return 档案接收人管理
     */
    @Override
    public SysArchiveReceiver selectSysArchiveReceiverByReceiverId(Long receiverId) {
        return sysArchiveReceiverMapper.selectSysArchiveReceiverByReceiverId(receiverId);
    }

    /**
     * 查询档案接收人管理列表
     *
     * @param sysArchiveReceiver 档案接收人管理
     * @return 档案接收人管理
     */
    @Override
    public List<SysArchiveReceiver> selectSysArchiveReceiverList(SysArchiveReceiver sysArchiveReceiver) {
        return sysArchiveReceiverMapper.selectSysArchiveReceiverList(sysArchiveReceiver);
    }

    /**
     * 新增档案接收人管理
     *
     * @param sysArchiveReceiver 档案接收人管理
     * @return 结果
     */
    @Override
    public int insertSysArchiveReceiver(SysArchiveReceiver sysArchiveReceiver) {
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
    public int updateSysArchiveReceiver(SysArchiveReceiver sysArchiveReceiver) {
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
    public int deleteSysArchiveReceiverByReceiverIds(Long[] receiverIds) {
        return sysArchiveReceiverMapper.deleteSysArchiveReceiverByReceiverIds(receiverIds);
    }

    /**
     * 删除档案接收人管理信息
     *
     * @param receiverId 档案接收人管理主键
     * @return 结果
     */
    @Override
    public int deleteSysArchiveReceiverByReceiverId(Long receiverId) {
        return sysArchiveReceiverMapper.deleteSysArchiveReceiverByReceiverId(receiverId);
    }

    @Override
    public String importArchiveReceiverInfo(List<SysArchiveReceiver> sysArchiveReceiverList, String operatorName) {
        if (StringUtils.isNull(sysArchiveReceiverList) || sysArchiveReceiverList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysArchiveReceiver item : sysArchiveReceiverList) {
            try {
                // 验证是否存在这个用户
                SysArchiveReceiver u = sysArchiveReceiverMapper.selectSysArchiveReceiverByReceiverId(item.getReceiverId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    item.setCreateBy(operatorName);
                    sysArchiveReceiverMapper.insertSysArchiveReceiver(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getReceiverId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setReceiverId(u.getReceiverId());
                    item.setUpdateBy(operatorName);
                    sysArchiveReceiverMapper.updateSysArchiveReceiver(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getReceiverId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getReceiverId() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
