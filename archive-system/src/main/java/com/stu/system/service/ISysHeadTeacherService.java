package com.stu.system.service;

import java.util.List;
import com.stu.system.domain.SysHeadTeacher;

/**
 * 班主任管理Service接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface ISysHeadTeacherService 
{
    /**
     * 查询班主任管理
     * 
     * @param headTeacherId 班主任管理主键
     * @return 班主任管理
     */
    public SysHeadTeacher selectSysHeadTeacherByHeadTeacherId(Long headTeacherId);

    /**
     * 查询班主任管理列表
     * 
     * @param sysHeadTeacher 班主任管理
     * @return 班主任管理集合
     */
    public List<SysHeadTeacher> selectSysHeadTeacherList(SysHeadTeacher sysHeadTeacher);

    /**
     * 新增班主任管理
     * 
     * @param sysHeadTeacher 班主任管理
     * @return 结果
     */
    public int insertSysHeadTeacher(SysHeadTeacher sysHeadTeacher);

    /**
     * 修改班主任管理
     * 
     * @param sysHeadTeacher 班主任管理
     * @return 结果
     */
    public int updateSysHeadTeacher(SysHeadTeacher sysHeadTeacher);

    /**
     * 批量删除班主任管理
     * 
     * @param headTeacherIds 需要删除的班主任管理主键集合
     * @return 结果
     */
    public int deleteSysHeadTeacherByHeadTeacherIds(Long[] headTeacherIds);

    /**
     * 删除班主任管理信息
     * 
     * @param headTeacherId 班主任管理主键
     * @return 结果
     */
    public int deleteSysHeadTeacherByHeadTeacherId(Long headTeacherId);
}
