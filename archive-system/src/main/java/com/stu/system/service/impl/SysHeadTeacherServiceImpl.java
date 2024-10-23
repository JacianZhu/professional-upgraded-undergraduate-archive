package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysHeadTeacherMapper;
import com.stu.system.domain.SysHeadTeacher;
import com.stu.system.service.ISysHeadTeacherService;

/**
 * 班主任管理Service业务层处理
 * 
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysHeadTeacherServiceImpl implements ISysHeadTeacherService 
{
    @Autowired
    private SysHeadTeacherMapper sysHeadTeacherMapper;

    /**
     * 查询班主任管理
     * 
     * @param headTeacherId 班主任管理主键
     * @return 班主任管理
     */
    @Override
    public SysHeadTeacher selectSysHeadTeacherByHeadTeacherId(Long headTeacherId)
    {
        return sysHeadTeacherMapper.selectSysHeadTeacherByHeadTeacherId(headTeacherId);
    }

    /**
     * 查询班主任管理列表
     * 
     * @param sysHeadTeacher 班主任管理
     * @return 班主任管理
     */
    @Override
    public List<SysHeadTeacher> selectSysHeadTeacherList(SysHeadTeacher sysHeadTeacher)
    {
        return sysHeadTeacherMapper.selectSysHeadTeacherList(sysHeadTeacher);
    }

    /**
     * 新增班主任管理
     * 
     * @param sysHeadTeacher 班主任管理
     * @return 结果
     */
    @Override
    public int insertSysHeadTeacher(SysHeadTeacher sysHeadTeacher)
    {
        sysHeadTeacher.setCreateTime(DateUtils.getNowDate());
        return sysHeadTeacherMapper.insertSysHeadTeacher(sysHeadTeacher);
    }

    /**
     * 修改班主任管理
     * 
     * @param sysHeadTeacher 班主任管理
     * @return 结果
     */
    @Override
    public int updateSysHeadTeacher(SysHeadTeacher sysHeadTeacher)
    {
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
    public int deleteSysHeadTeacherByHeadTeacherIds(Long[] headTeacherIds)
    {
        return sysHeadTeacherMapper.deleteSysHeadTeacherByHeadTeacherIds(headTeacherIds);
    }

    /**
     * 删除班主任管理信息
     * 
     * @param headTeacherId 班主任管理主键
     * @return 结果
     */
    @Override
    public int deleteSysHeadTeacherByHeadTeacherId(Long headTeacherId)
    {
        return sysHeadTeacherMapper.deleteSysHeadTeacherByHeadTeacherId(headTeacherId);
    }
}
