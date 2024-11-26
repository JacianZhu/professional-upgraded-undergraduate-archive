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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysAdmissionInfoMapper;
import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.service.ISysAdmissionInfoService;

import javax.validation.Validator;

/**
 * 录取信息导入Service业务层处理
 *
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysAdmissionInfoServiceImpl implements ISysAdmissionInfoService {


    private static final Logger log = LoggerFactory.getLogger(SysAdmissionInfoServiceImpl.class);

    @Autowired
    private SysAdmissionInfoMapper sysAdmissionInfoMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private SysUserServiceImpl userService;

    /**
     * 查询录取信息导入
     *
     * @param admissionId 录取信息导入主键
     * @return 录取信息导入
     */
    @Override
    public SysAdmissionInfo selectSysAdmissionInfoByAdmissionId(Long admissionId) {
        return sysAdmissionInfoMapper.selectSysAdmissionInfoByAdmissionId(admissionId);
    }

    /**
     * 查询录取信息导入列表
     *
     * @param sysAdmissionInfo 录取信息导入
     * @return 录取信息导入
     */
    @Override
    public List<SysAdmissionInfo> selectSysAdmissionInfoList(SysAdmissionInfo sysAdmissionInfo) {
        return sysAdmissionInfoMapper.selectSysAdmissionInfoList(sysAdmissionInfo);
    }

    /**
     * 新增录取信息导入
     *
     * @param sysAdmissionInfo 录取信息导入
     * @return 结果
     */
    @Override
    public int insertSysAdmissionInfo(SysAdmissionInfo sysAdmissionInfo) {
        sysAdmissionInfo.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isNotNull(sysAdmissionInfo.getAdmissionDate())) {
            LocalDate localDate = sysAdmissionInfo.getAdmissionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            sysAdmissionInfo.setYear(String.valueOf(localDate.getYear()));
        }
        SysUser sysUser = new SysUser();
        sysUser.setNickName(sysAdmissionInfo.getStudentName());
        sysUser.setUserName(sysAdmissionInfo.getStudentName());
        sysUser.setPhonenumber(sysAdmissionInfo.getContactNumber());
        sysUser.setSex("男".equals(sysAdmissionInfo.getGender()) ? "1" : "0");
        sysUser.setPassword(SecurityUtils.encryptPassword(StringUtils.isNotEmpty(sysAdmissionInfo.getIdNumber()) ? sysAdmissionInfo.getIdNumber() : "123456"));
        sysUser.setRoleIds(new Long[]{2L});
        userService.insertUser(sysUser);
        return sysAdmissionInfoMapper.insertSysAdmissionInfo(sysAdmissionInfo);
    }

    /**
     * 修改录取信息导入
     *
     * @param sysAdmissionInfo 录取信息导入
     * @return 结果
     */
    @Override
    public int updateSysAdmissionInfo(SysAdmissionInfo sysAdmissionInfo) {
        sysAdmissionInfo.setUpdateTime(DateUtils.getNowDate());
        if (StringUtils.isNotNull(sysAdmissionInfo.getAdmissionDate())) {
            LocalDate localDate = sysAdmissionInfo.getAdmissionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            sysAdmissionInfo.setYear(String.valueOf(localDate.getYear()));
        }
        return sysAdmissionInfoMapper.updateSysAdmissionInfo(sysAdmissionInfo);
    }

    /**
     * 批量删除录取信息导入
     *
     * @param admissionIds 需要删除的录取信息导入主键
     * @return 结果
     */
    @Override
    public int deleteSysAdmissionInfoByAdmissionIds(Long[] admissionIds) {
        return sysAdmissionInfoMapper.deleteSysAdmissionInfoByAdmissionIds(admissionIds);
    }

    /**
     * 删除录取信息导入信息
     *
     * @param admissionId 录取信息导入主键
     * @return 结果
     */
    @Override
    public int deleteSysAdmissionInfoByAdmissionId(Long admissionId) {
        return sysAdmissionInfoMapper.deleteSysAdmissionInfoByAdmissionId(admissionId);
    }

    @Override
    public String importAdmissionInfo(List<SysAdmissionInfo> sysAdmissionInfoList, String operatorName) {
        if (StringUtils.isNull(sysAdmissionInfoList) || sysAdmissionInfoList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysAdmissionInfo item : sysAdmissionInfoList) {
            try {
                // 验证是否存在这个用户
                SysAdmissionInfo u = sysAdmissionInfoMapper.selectSysAdmissionInfoByAdmissionId(item.getAdmissionId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    item.setCreateBy(operatorName);
                    if (StringUtils.isNotNull(item.getAdmissionDate())) {
                        LocalDate localDate = item.getAdmissionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        item.setYear(String.valueOf(localDate.getYear()));
                    }
                    sysAdmissionInfoMapper.insertSysAdmissionInfo(item);
                    SysUser sysUser = new SysUser();
                    sysUser.setNickName(item.getStudentName());
                    sysUser.setUserName(item.getStudentName());
                    sysUser.setPhonenumber(item.getContactNumber());
                    sysUser.setSex("男".equals(item.getGender()) ? "1" : "0");
                    sysUser.setPassword(SecurityUtils.encryptPassword(StringUtils.isNotEmpty(item.getIdNumber()) ? item.getIdNumber() : "123456"));
                    sysUser.setRoleIds(new Long[]{2L});
                    userService.insertUser(sysUser);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getAdmissionId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setAdmissionId(u.getAdmissionId());
                    item.setUpdateBy(operatorName);
                    if (StringUtils.isNotNull(item.getAdmissionDate())) {
                        LocalDate localDate = item.getAdmissionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        item.setYear(String.valueOf(localDate.getYear()));
                    }
                    sysAdmissionInfoMapper.updateSysAdmissionInfo(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getAdmissionId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getAdmissionId() + " 导入失败：";
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
