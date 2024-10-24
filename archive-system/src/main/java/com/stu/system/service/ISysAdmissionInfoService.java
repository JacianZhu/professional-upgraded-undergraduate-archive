package com.stu.system.service;

import java.util.List;

import com.stu.common.core.domain.entity.SysUser;
import com.stu.system.domain.SysAdmissionInfo;

/**
 * 录取信息导入Service接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface ISysAdmissionInfoService 
{
    /**
     * 查询录取信息导入
     * 
     * @param admissionId 录取信息导入主键
     * @return 录取信息导入
     */
    public SysAdmissionInfo selectSysAdmissionInfoByAdmissionId(Long admissionId);

    /**
     * 查询录取信息导入列表
     * 
     * @param sysAdmissionInfo 录取信息导入
     * @return 录取信息导入集合
     */
    public List<SysAdmissionInfo> selectSysAdmissionInfoList(SysAdmissionInfo sysAdmissionInfo);

    /**
     * 新增录取信息导入
     * 
     * @param sysAdmissionInfo 录取信息导入
     * @return 结果
     */
    public int insertSysAdmissionInfo(SysAdmissionInfo sysAdmissionInfo);

    /**
     * 修改录取信息导入
     * 
     * @param sysAdmissionInfo 录取信息导入
     * @return 结果
     */
    public int updateSysAdmissionInfo(SysAdmissionInfo sysAdmissionInfo);

    /**
     * 批量删除录取信息导入
     * 
     * @param admissionIds 需要删除的录取信息导入主键集合
     * @return 结果
     */
    public int deleteSysAdmissionInfoByAdmissionIds(Long[] admissionIds);

    /**
     * 删除录取信息导入信息
     * 
     * @param admissionId 录取信息导入主键
     * @return 结果
     */
    public int deleteSysAdmissionInfoByAdmissionId(Long admissionId);


    /**
     * 导入用户数据
     *
     * @param sysAdmissionInfoList 用户数据列表
     * @param operatorName 操作用户
     * @return 结果
     */
    public String importAdmissionInfo(List<SysAdmissionInfo> sysAdmissionInfoList,String operatorName);
}
