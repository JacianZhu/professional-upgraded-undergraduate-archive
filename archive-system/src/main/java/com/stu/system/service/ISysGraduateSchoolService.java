package com.stu.system.service;

import java.util.List;

import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.domain.SysGraduateSchool;

/**
 * 毕业院校管理Service接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface ISysGraduateSchoolService 
{
    /**
     * 查询毕业院校管理
     * 
     * @param graduateSchoolId 毕业院校管理主键
     * @return 毕业院校管理
     */
    public SysGraduateSchool selectSysGraduateSchoolByGraduateSchoolId(Long graduateSchoolId);

    /**
     * 查询毕业院校管理列表
     * 
     * @param sysGraduateSchool 毕业院校管理
     * @return 毕业院校管理集合
     */
    public List<SysGraduateSchool> selectSysGraduateSchoolList(SysGraduateSchool sysGraduateSchool);

    /**
     * 新增毕业院校管理
     * 
     * @param sysGraduateSchool 毕业院校管理
     * @return 结果
     */
    public int insertSysGraduateSchool(SysGraduateSchool sysGraduateSchool);

    /**
     * 修改毕业院校管理
     * 
     * @param sysGraduateSchool 毕业院校管理
     * @return 结果
     */
    public int updateSysGraduateSchool(SysGraduateSchool sysGraduateSchool);

    /**
     * 批量删除毕业院校管理
     * 
     * @param graduateSchoolIds 需要删除的毕业院校管理主键集合
     * @return 结果
     */
    public int deleteSysGraduateSchoolByGraduateSchoolIds(Long[] graduateSchoolIds);

    /**
     * 删除毕业院校管理信息
     * 
     * @param graduateSchoolId 毕业院校管理主键
     * @return 结果
     */
    public int deleteSysGraduateSchoolByGraduateSchoolId(Long graduateSchoolId);


    /**
     * 导入用户数据
     *
     * @param sysGraduateSchoolList 用户数据列表
     * @param operatorName 操作用户
     * @return 结果
     */
    public String importSysGraduateSchoolInfo(List<SysGraduateSchool> sysGraduateSchoolList, String operatorName);
}
