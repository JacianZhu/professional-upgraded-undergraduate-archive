package com.stu.system.service.impl;

import java.util.List;

import com.stu.common.exception.ServiceException;
import com.stu.common.utils.DateUtils;
import com.stu.common.utils.StringUtils;
import com.stu.common.utils.bean.BeanValidators;
import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.domain.SysHeadTeacher;
import com.stu.system.domain.SysSpecialty;
import com.stu.system.mapper.SysHeadTeacherMapper;
import com.stu.system.mapper.SysSpecialtyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysClassMapper;
import com.stu.system.domain.SysClass;
import com.stu.system.service.ISysClassService;

import javax.validation.Validator;

/**
 * 班级管理Service业务层处理
 *
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysClassServiceImpl implements ISysClassService {

    private static final Logger log = LoggerFactory.getLogger(SysAdmissionInfoServiceImpl.class);

    @Autowired
    protected Validator validator;


    @Autowired
    private SysClassMapper sysClassMapper;

    @Autowired
    private SysSpecialtyMapper sysSpecialtyMapper;

    @Autowired
    private SysHeadTeacherMapper sysHeadTeacherMapper;

    /**
     * 查询班级管理
     *
     * @param classId 班级管理主键
     * @return 班级管理
     */
    @Override
    public SysClass selectSysClassByClassId(Long classId) {
        return sysClassMapper.selectSysClassByClassId(classId);
    }

    /**
     * 查询班级管理列表
     *
     * @param sysClass 班级管理
     * @return 班级管理
     */
    @Override
    public List<SysClass> selectSysClassList(SysClass sysClass) {
        return sysClassMapper.selectSysClassList(sysClass);
    }

    /**
     * 新增班级管理
     *
     * @param sysClass 班级管理
     * @return 结果
     */
    @Override
    public int insertSysClass(SysClass sysClass) {
        sysClass.setCreateTime(DateUtils.getNowDate());
        return sysClassMapper.insertSysClass(sysClass);
    }

    /**
     * 修改班级管理
     *
     * @param sysClass 班级管理
     * @return 结果
     */
    @Override
    public int updateSysClass(SysClass sysClass) {
        sysClass.setUpdateTime(DateUtils.getNowDate());
        return sysClassMapper.updateSysClass(sysClass);
    }

    /**
     * 批量删除班级管理
     *
     * @param classIds 需要删除的班级管理主键
     * @return 结果
     */
    @Override
    public int deleteSysClassByClassIds(Long[] classIds) {
        return sysClassMapper.deleteSysClassByClassIds(classIds);
    }

    /**
     * 删除班级管理信息
     *
     * @param classId 班级管理主键
     * @return 结果
     */
    @Override
    public int deleteSysClassByClassId(Long classId) {
        return sysClassMapper.deleteSysClassByClassId(classId);
    }

    @Override
    public String importSysClassInfo(List<SysClass> sysClassInfoList, String operatorName) {
        if (StringUtils.isNull(sysClassInfoList) || sysClassInfoList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysClass item : sysClassInfoList) {
            try {
                // 验证是否存在这个用户
                SysClass u = sysClassMapper.selectSysClassByClassId(item.getClassId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    List<SysSpecialty> sysSpecialties = sysSpecialtyMapper.selectSysSpecialtyList(new SysSpecialty());
                    if(StringUtils.isNotEmpty(sysSpecialties)){
                        sysSpecialties.forEach(sysSpecialty->{
                            if(sysSpecialty.getSpecialtyName().equals(item.getSpecialtyName())){
                                item.setSpecialtyId(sysSpecialty.getSpecialtyId());
                            }
                        });
                    }
                    List<SysHeadTeacher> headTeachers = sysHeadTeacherMapper.selectSysHeadTeacherList(new SysHeadTeacher());
                    if(StringUtils.isNotEmpty(headTeachers)){
                        headTeachers.forEach(headTeacher->{
                            if(headTeacher.getHeadTeacherName().equals(item.getHeadTeacherName())){
                                item.setHeadTeacherId(headTeacher.getHeadTeacherId());
                            }
                        });
                    }
                    item.setCreateBy(operatorName);
                    sysClassMapper.insertSysClass(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getClassId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setClassId(u.getClassId());
                    item.setUpdateBy(operatorName);
                    List<SysSpecialty> sysSpecialties = sysSpecialtyMapper.selectSysSpecialtyList(new SysSpecialty());
                    if(StringUtils.isNotEmpty(sysSpecialties)){
                        sysSpecialties.forEach(sysSpecialty->{
                            if(sysSpecialty.getSpecialtyName().equals(item.getSpecialtyName())){
                                item.setSpecialtyId(sysSpecialty.getSpecialtyId());
                            }
                        });
                    }
                    List<SysHeadTeacher> headTeachers = sysHeadTeacherMapper.selectSysHeadTeacherList(new SysHeadTeacher());
                    if(StringUtils.isNotEmpty(headTeachers)){
                        headTeachers.forEach(headTeacher->{
                            if(headTeacher.getHeadTeacherName().equals(item.getHeadTeacherName())){
                                item.setHeadTeacherId(headTeacher.getHeadTeacherId());
                            }
                        });
                    }
                    sysClassMapper.updateSysClass(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getClassId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getClassId() + " 导入失败：";
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
