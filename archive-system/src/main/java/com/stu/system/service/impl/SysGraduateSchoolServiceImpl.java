package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysGraduateSchoolMapper;
import com.stu.system.domain.SysGraduateSchool;
import com.stu.system.service.ISysGraduateSchoolService;

/**
 * 毕业院校管理Service业务层处理
 * 
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysGraduateSchoolServiceImpl implements ISysGraduateSchoolService 
{
    @Autowired
    private SysGraduateSchoolMapper sysGraduateSchoolMapper;

    /**
     * 查询毕业院校管理
     * 
     * @param graduateSchoolId 毕业院校管理主键
     * @return 毕业院校管理
     */
    @Override
    public SysGraduateSchool selectSysGraduateSchoolByGraduateSchoolId(Long graduateSchoolId)
    {
        return sysGraduateSchoolMapper.selectSysGraduateSchoolByGraduateSchoolId(graduateSchoolId);
    }

    /**
     * 查询毕业院校管理列表
     * 
     * @param sysGraduateSchool 毕业院校管理
     * @return 毕业院校管理
     */
    @Override
    public List<SysGraduateSchool> selectSysGraduateSchoolList(SysGraduateSchool sysGraduateSchool)
    {
        return sysGraduateSchoolMapper.selectSysGraduateSchoolList(sysGraduateSchool);
    }

    /**
     * 新增毕业院校管理
     * 
     * @param sysGraduateSchool 毕业院校管理
     * @return 结果
     */
    @Override
    public int insertSysGraduateSchool(SysGraduateSchool sysGraduateSchool)
    {
        sysGraduateSchool.setCreateTime(DateUtils.getNowDate());
        return sysGraduateSchoolMapper.insertSysGraduateSchool(sysGraduateSchool);
    }

    /**
     * 修改毕业院校管理
     * 
     * @param sysGraduateSchool 毕业院校管理
     * @return 结果
     */
    @Override
    public int updateSysGraduateSchool(SysGraduateSchool sysGraduateSchool)
    {
        sysGraduateSchool.setUpdateTime(DateUtils.getNowDate());
        return sysGraduateSchoolMapper.updateSysGraduateSchool(sysGraduateSchool);
    }

    /**
     * 批量删除毕业院校管理
     * 
     * @param graduateSchoolIds 需要删除的毕业院校管理主键
     * @return 结果
     */
    @Override
    public int deleteSysGraduateSchoolByGraduateSchoolIds(Long[] graduateSchoolIds)
    {
        return sysGraduateSchoolMapper.deleteSysGraduateSchoolByGraduateSchoolIds(graduateSchoolIds);
    }

    /**
     * 删除毕业院校管理信息
     * 
     * @param graduateSchoolId 毕业院校管理主键
     * @return 结果
     */
    @Override
    public int deleteSysGraduateSchoolByGraduateSchoolId(Long graduateSchoolId)
    {
        return sysGraduateSchoolMapper.deleteSysGraduateSchoolByGraduateSchoolId(graduateSchoolId);
    }
}
