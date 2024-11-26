package com.stu.system.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import com.stu.common.core.domain.entity.SysUser;
import com.stu.common.exception.ServiceException;
import com.stu.common.utils.DateUtils;
import com.stu.common.utils.SecurityUtils;
import com.stu.common.utils.StringUtils;
import com.stu.common.utils.bean.BeanValidators;
import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.mapper.SysAdmissionInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysArchiveMapper;
import com.stu.system.domain.SysArchive;
import com.stu.system.service.ISysArchiveService;

import javax.validation.Validator;

/**
 * 档案信息Service业务层处理
 *
 * @author stu
 * @date 2024-11-03
 */
@Service
public class SysArchiveServiceImpl implements ISysArchiveService {


    private static final Logger log = LoggerFactory.getLogger(SysAdmissionInfoServiceImpl.class);

    @Autowired
    private SysAdmissionInfoMapper sysAdmissionInfoMapper;

    @Autowired
    protected Validator validator;


    @Autowired
    private SysArchiveMapper sysArchiveMapper;

    /**
     * 查询档案信息
     *
     * @param archiveId 档案信息主键
     * @return 档案信息
     */
    @Override
    public SysArchive selectSysArchiveByArchiveId(Long archiveId) {
        return sysArchiveMapper.selectSysArchiveByArchiveId(archiveId);
    }

    /**
     * 查询档案信息列表
     *
     * @param sysArchive 档案信息
     * @return 档案信息
     */
    @Override
    public List<SysArchive> selectSysArchiveList(SysArchive sysArchive) {
        return sysArchiveMapper.selectSysArchiveList(sysArchive);
    }

    /**
     * 新增档案信息
     *
     * @param sysArchive 档案信息
     * @return 结果
     */
    @Override
    public int insertSysArchive(SysArchive sysArchive) {
        sysArchive.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isNotNull(sysArchive.getReceiveDate())) {
            LocalDate localDate = sysArchive.getReceiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            sysArchive.setYear(String.valueOf(localDate.getYear()));
        }

        return sysArchiveMapper.insertSysArchive(sysArchive);
    }

    /**
     * 修改档案信息
     *
     * @param sysArchive 档案信息
     * @return 结果
     */
    @Override
    public int updateSysArchive(SysArchive sysArchive) {
        if (StringUtils.isNotNull(sysArchive.getReceiveDate())) {
            LocalDate localDate = sysArchive.getReceiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            sysArchive.setYear(String.valueOf(localDate.getYear()));
        }
        return sysArchiveMapper.updateSysArchive(sysArchive);
    }

    /**
     * 批量删除档案信息
     *
     * @param archiveIds 需要删除的档案信息主键
     * @return 结果
     */
    @Override
    public int deleteSysArchiveByArchiveIds(Long[] archiveIds) {
        return sysArchiveMapper.deleteSysArchiveByArchiveIds(archiveIds);
    }

    /**
     * 删除档案信息信息
     *
     * @param archiveId 档案信息主键
     * @return 结果
     */
    @Override
    public int deleteSysArchiveByArchiveId(Long archiveId) {
        return sysArchiveMapper.deleteSysArchiveByArchiveId(archiveId);
    }

    @Override
    public String importArchiveInfo(List<SysArchive> archiveInfoList, String operatorName) {
        if (StringUtils.isNull(archiveInfoList) || archiveInfoList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysArchive item : archiveInfoList) {
            try {
                // 验证是否存在这个用户
                SysArchive u = sysArchiveMapper.selectSysArchiveByArchiveId(item.getArchiveId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    item.setCreateBy(operatorName);
                    if (StringUtils.isNotNull(item.getReceiveDate())) {
                        LocalDate localDate = item.getReceiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        item.setYear(String.valueOf(localDate.getYear()));
                    }
                    sysArchiveMapper.insertSysArchive(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getArchiveId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setArchiveId(u.getArchiveId());
                    item.setUpdateBy(operatorName);
                    if (StringUtils.isNotNull(item.getReceiveDate())) {
                        LocalDate localDate = item.getReceiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        item.setYear(String.valueOf(localDate.getYear()));
                    }
                    sysArchiveMapper.updateSysArchive(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getArchiveId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getArchiveId() + " 导入失败：";
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
