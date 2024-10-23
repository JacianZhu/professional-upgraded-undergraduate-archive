package com.stu.system.mapper;

import java.util.List;
import com.stu.system.domain.SysAdmissionInfo;

/**
 * 录取信息导入Mapper接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface SysAdmissionInfoMapper 
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
     * 删除录取信息导入
     * 
     * @param admissionId 录取信息导入主键
     * @return 结果
     */
    public int deleteSysAdmissionInfoByAdmissionId(Long admissionId);

    /**
     * 批量删除录取信息导入
     * 
     * @param admissionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysAdmissionInfoByAdmissionIds(Long[] admissionIds);
}
