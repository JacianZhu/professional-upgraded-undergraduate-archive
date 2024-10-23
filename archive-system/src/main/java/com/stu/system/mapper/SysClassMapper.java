package com.stu.system.mapper;

import java.util.List;
import com.stu.system.domain.SysClass;

/**
 * 班级管理Mapper接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface SysClassMapper 
{
    /**
     * 查询班级管理
     * 
     * @param classId 班级管理主键
     * @return 班级管理
     */
    public SysClass selectSysClassByClassId(Long classId);

    /**
     * 查询班级管理列表
     * 
     * @param sysClass 班级管理
     * @return 班级管理集合
     */
    public List<SysClass> selectSysClassList(SysClass sysClass);

    /**
     * 新增班级管理
     * 
     * @param sysClass 班级管理
     * @return 结果
     */
    public int insertSysClass(SysClass sysClass);

    /**
     * 修改班级管理
     * 
     * @param sysClass 班级管理
     * @return 结果
     */
    public int updateSysClass(SysClass sysClass);

    /**
     * 删除班级管理
     * 
     * @param classId 班级管理主键
     * @return 结果
     */
    public int deleteSysClassByClassId(Long classId);

    /**
     * 批量删除班级管理
     * 
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysClassByClassIds(Long[] classIds);
}
