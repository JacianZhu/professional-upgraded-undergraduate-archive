package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysSpecialtyMapper;
import com.stu.system.domain.SysSpecialty;
import com.stu.system.service.ISysSpecialtyService;

/**
 * 专业管理Service业务层处理
 * 
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysSpecialtyServiceImpl implements ISysSpecialtyService 
{
    @Autowired
    private SysSpecialtyMapper sysSpecialtyMapper;

    /**
     * 查询专业管理
     * 
     * @param specialtyId 专业管理主键
     * @return 专业管理
     */
    @Override
    public SysSpecialty selectSysSpecialtyBySpecialtyId(Long specialtyId)
    {
        return sysSpecialtyMapper.selectSysSpecialtyBySpecialtyId(specialtyId);
    }

    /**
     * 查询专业管理列表
     * 
     * @param sysSpecialty 专业管理
     * @return 专业管理
     */
    @Override
    public List<SysSpecialty> selectSysSpecialtyList(SysSpecialty sysSpecialty)
    {
        return sysSpecialtyMapper.selectSysSpecialtyList(sysSpecialty);
    }

    /**
     * 新增专业管理
     * 
     * @param sysSpecialty 专业管理
     * @return 结果
     */
    @Override
    public int insertSysSpecialty(SysSpecialty sysSpecialty)
    {
        sysSpecialty.setCreateTime(DateUtils.getNowDate());
        return sysSpecialtyMapper.insertSysSpecialty(sysSpecialty);
    }

    /**
     * 修改专业管理
     * 
     * @param sysSpecialty 专业管理
     * @return 结果
     */
    @Override
    public int updateSysSpecialty(SysSpecialty sysSpecialty)
    {
        sysSpecialty.setUpdateTime(DateUtils.getNowDate());
        return sysSpecialtyMapper.updateSysSpecialty(sysSpecialty);
    }

    /**
     * 批量删除专业管理
     * 
     * @param specialtyIds 需要删除的专业管理主键
     * @return 结果
     */
    @Override
    public int deleteSysSpecialtyBySpecialtyIds(Long[] specialtyIds)
    {
        return sysSpecialtyMapper.deleteSysSpecialtyBySpecialtyIds(specialtyIds);
    }

    /**
     * 删除专业管理信息
     * 
     * @param specialtyId 专业管理主键
     * @return 结果
     */
    @Override
    public int deleteSysSpecialtyBySpecialtyId(Long specialtyId)
    {
        return sysSpecialtyMapper.deleteSysSpecialtyBySpecialtyId(specialtyId);
    }
}
