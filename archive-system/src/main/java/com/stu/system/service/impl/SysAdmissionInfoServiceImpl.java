package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysAdmissionInfoMapper;
import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.service.ISysAdmissionInfoService;

/**
 * 录取信息导入Service业务层处理
 * 
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysAdmissionInfoServiceImpl implements ISysAdmissionInfoService 
{
    @Autowired
    private SysAdmissionInfoMapper sysAdmissionInfoMapper;

    /**
     * 查询录取信息导入
     * 
     * @param admissionId 录取信息导入主键
     * @return 录取信息导入
     */
    @Override
    public SysAdmissionInfo selectSysAdmissionInfoByAdmissionId(Long admissionId)
    {
        return sysAdmissionInfoMapper.selectSysAdmissionInfoByAdmissionId(admissionId);
    }

    /**
     * 查询录取信息导入列表
     * 
     * @param sysAdmissionInfo 录取信息导入
     * @return 录取信息导入
     */
    @Override
    public List<SysAdmissionInfo> selectSysAdmissionInfoList(SysAdmissionInfo sysAdmissionInfo)
    {
        return sysAdmissionInfoMapper.selectSysAdmissionInfoList(sysAdmissionInfo);
    }

    /**
     * 新增录取信息导入
     * 
     * @param sysAdmissionInfo 录取信息导入
     * @return 结果
     */
    @Override
    public int insertSysAdmissionInfo(SysAdmissionInfo sysAdmissionInfo)
    {
        sysAdmissionInfo.setCreateTime(DateUtils.getNowDate());
        return sysAdmissionInfoMapper.insertSysAdmissionInfo(sysAdmissionInfo);
    }

    /**
     * 修改录取信息导入
     * 
     * @param sysAdmissionInfo 录取信息导入
     * @return 结果
     */
    @Override
    public int updateSysAdmissionInfo(SysAdmissionInfo sysAdmissionInfo)
    {
        sysAdmissionInfo.setUpdateTime(DateUtils.getNowDate());
        return sysAdmissionInfoMapper.updateSysAdmissionInfo(sysAdmissionInfo);
    }

    /**
     * 批量删除录取信息导入
     * 
     * @param admissionIds 需要删除的录取信息导入主键
     * @return 结果
     */
    @Override
    public int deleteSysAdmissionInfoByAdmissionIds(Long[] admissionIds)
    {
        return sysAdmissionInfoMapper.deleteSysAdmissionInfoByAdmissionIds(admissionIds);
    }

    /**
     * 删除录取信息导入信息
     * 
     * @param admissionId 录取信息导入主键
     * @return 结果
     */
    @Override
    public int deleteSysAdmissionInfoByAdmissionId(Long admissionId)
    {
        return sysAdmissionInfoMapper.deleteSysAdmissionInfoByAdmissionId(admissionId);
    }
}
