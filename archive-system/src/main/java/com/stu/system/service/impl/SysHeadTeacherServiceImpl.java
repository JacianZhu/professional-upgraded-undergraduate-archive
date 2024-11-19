package com.stu.system.service.impl;

import java.util.List;

import com.stu.common.core.domain.entity.SysUser;
import com.stu.common.exception.ServiceException;
import com.stu.common.utils.DateUtils;
import com.stu.common.utils.SecurityUtils;
import com.stu.common.utils.StringUtils;
import com.stu.common.utils.bean.BeanValidators;
import com.stu.system.domain.SysAdmissionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysHeadTeacherMapper;
import com.stu.system.domain.SysHeadTeacher;
import com.stu.system.service.ISysHeadTeacherService;

import javax.validation.Validator;

/**
 * 班主任管理Service业务层处理
 *
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysHeadTeacherServiceImpl implements ISysHeadTeacherService {

    private static final Logger log = LoggerFactory.getLogger(SysHeadTeacherServiceImpl.class);

    @Autowired
    protected Validator validator;

    @Autowired
    private SysHeadTeacherMapper sysHeadTeacherMapper;
    @Autowired
    private SysUserServiceImpl userService;

    /**
     * 查询班主任管理
     *
     * @param headTeacherId 班主任管理主键
     * @return 班主任管理
     */
    @Override
    public SysHeadTeacher selectSysHeadTeacherByHeadTeacherId(Long headTeacherId) {
        return sysHeadTeacherMapper.selectSysHeadTeacherByHeadTeacherId(headTeacherId);
    }

    /**
     * 查询班主任管理列表
     *
     * @param sysHeadTeacher 班主任管理
     * @return 班主任管理
     */
    @Override
    public List<SysHeadTeacher> selectSysHeadTeacherList(SysHeadTeacher sysHeadTeacher) {
        return sysHeadTeacherMapper.selectSysHeadTeacherList(sysHeadTeacher);
    }

    /**
     * 新增班主任管理
     *
     * @param sysHeadTeacher 班主任管理
     * @return 结果
     */
    @Override
    public int insertSysHeadTeacher(SysHeadTeacher sysHeadTeacher) {
        sysHeadTeacher.setCreateTime(DateUtils.getNowDate());
        SysUser sysUser = new SysUser();
        sysUser.setNickName(sysHeadTeacher.getHeadTeacherName());
        sysUser.setUserName(sysHeadTeacher.getHeadTeacherName());
        sysUser.setPhonenumber(sysHeadTeacher.getContactNumber());
        sysUser.setSex("1");
        sysUser.setPassword(SecurityUtils.encryptPassword(StringUtils.isNotEmpty(sysHeadTeacher.getIdNumber()) ? sysHeadTeacher.getIdNumber() : "123456"));
        sysUser.setRoleIds(new Long[]{3L});
        userService.insertUser(sysUser);
        return sysHeadTeacherMapper.insertSysHeadTeacher(sysHeadTeacher);
    }

    /**
     * 修改班主任管理
     *
     * @param sysHeadTeacher 班主任管理
     * @return 结果
     */
    @Override
    public int updateSysHeadTeacher(SysHeadTeacher sysHeadTeacher) {
        sysHeadTeacher.setUpdateTime(DateUtils.getNowDate());
        return sysHeadTeacherMapper.updateSysHeadTeacher(sysHeadTeacher);
    }

    /**
     * 批量删除班主任管理
     *
     * @param headTeacherIds 需要删除的班主任管理主键
     * @return 结果
     */
    @Override
    public int deleteSysHeadTeacherByHeadTeacherIds(Long[] headTeacherIds) {
        return sysHeadTeacherMapper.deleteSysHeadTeacherByHeadTeacherIds(headTeacherIds);
    }

    /**
     * 删除班主任管理信息
     *
     * @param headTeacherId 班主任管理主键
     * @return 结果
     */
    @Override
    public int deleteSysHeadTeacherByHeadTeacherId(Long headTeacherId) {
        return sysHeadTeacherMapper.deleteSysHeadTeacherByHeadTeacherId(headTeacherId);
    }

    @Override
    public String importSysHeadTeacherInfo(List<SysHeadTeacher> sysHeadTeacherList, String operatorName) {
        if (StringUtils.isNull(sysHeadTeacherList) || sysHeadTeacherList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysHeadTeacher item : sysHeadTeacherList) {
            try {
                // 验证是否存在这个用户
                SysHeadTeacher u = sysHeadTeacherMapper.selectSysHeadTeacherByHeadTeacherId(item.getHeadTeacherId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    item.setCreateBy(operatorName);
                    sysHeadTeacherMapper.insertSysHeadTeacher(item);
                    SysUser sysUser = new SysUser();
                    sysUser.setNickName(item.getHeadTeacherName());
                    sysUser.setUserName(item.getHeadTeacherName());
                    sysUser.setPhonenumber(item.getContactNumber());
                    sysUser.setSex("1");
                    sysUser.setPassword(SecurityUtils.encryptPassword(StringUtils.isNotEmpty(item.getIdNumber()) ? item.getIdNumber() : "123456"));
                    sysUser.setRoleIds(new Long[]{3L});
                    userService.insertUser(sysUser);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getHeadTeacherId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setHeadTeacherId(u.getHeadTeacherId());
                    item.setUpdateBy(operatorName);
                    sysHeadTeacherMapper.updateSysHeadTeacher(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getHeadTeacherId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getHeadTeacherId() + " 导入失败：";
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
