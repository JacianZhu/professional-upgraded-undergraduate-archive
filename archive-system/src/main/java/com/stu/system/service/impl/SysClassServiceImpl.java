package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysClassMapper;
import com.stu.system.domain.SysClass;
import com.stu.system.service.ISysClassService;

/**
 * 班级管理Service业务层处理
 * 
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysClassServiceImpl implements ISysClassService 
{
    @Autowired
    private SysClassMapper sysClassMapper;

    /**
     * 查询班级管理
     * 
     * @param classId 班级管理主键
     * @return 班级管理
     */
    @Override
    public SysClass selectSysClassByClassId(Long classId)
    {
        return sysClassMapper.selectSysClassByClassId(classId);
    }

    /**
     * 查询班级管理列表
     * 
     * @param sysClass 班级管理
     * @return 班级管理
     */
    @Override
    public List<SysClass> selectSysClassList(SysClass sysClass)
    {
        return sysClassMapper.selectSysClassList(sysClass);
    }

    /**
     * 新增班级管理
     * 
     * @param sysClass 班级管理
     * @return 结果
     */
    @Override
    public int insertSysClass(SysClass sysClass)
    {
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
    public int updateSysClass(SysClass sysClass)
    {
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
    public int deleteSysClassByClassIds(Long[] classIds)
    {
        return sysClassMapper.deleteSysClassByClassIds(classIds);
    }

    /**
     * 删除班级管理信息
     * 
     * @param classId 班级管理主键
     * @return 结果
     */
    @Override
    public int deleteSysClassByClassId(Long classId)
    {
        return sysClassMapper.deleteSysClassByClassId(classId);
    }
}
