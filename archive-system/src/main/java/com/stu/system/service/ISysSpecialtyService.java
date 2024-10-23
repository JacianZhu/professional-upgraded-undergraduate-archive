package com.stu.system.service;

import java.util.List;
import com.stu.system.domain.SysSpecialty;

/**
 * 专业管理Service接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface ISysSpecialtyService 
{
    /**
     * 查询专业管理
     * 
     * @param specialtyId 专业管理主键
     * @return 专业管理
     */
    public SysSpecialty selectSysSpecialtyBySpecialtyId(Long specialtyId);

    /**
     * 查询专业管理列表
     * 
     * @param sysSpecialty 专业管理
     * @return 专业管理集合
     */
    public List<SysSpecialty> selectSysSpecialtyList(SysSpecialty sysSpecialty);

    /**
     * 新增专业管理
     * 
     * @param sysSpecialty 专业管理
     * @return 结果
     */
    public int insertSysSpecialty(SysSpecialty sysSpecialty);

    /**
     * 修改专业管理
     * 
     * @param sysSpecialty 专业管理
     * @return 结果
     */
    public int updateSysSpecialty(SysSpecialty sysSpecialty);

    /**
     * 批量删除专业管理
     * 
     * @param specialtyIds 需要删除的专业管理主键集合
     * @return 结果
     */
    public int deleteSysSpecialtyBySpecialtyIds(Long[] specialtyIds);

    /**
     * 删除专业管理信息
     * 
     * @param specialtyId 专业管理主键
     * @return 结果
     */
    public int deleteSysSpecialtyBySpecialtyId(Long specialtyId);
}
