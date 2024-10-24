package com.stu.system.service;

import java.util.List;

import com.stu.system.domain.SysAdmissionInfo;
import com.stu.system.domain.SysCourierCompany;

/**
 * 快递公司管理Service接口
 * 
 * @author stu
 * @date 2024-10-23
 */
public interface ISysCourierCompanyService 
{
    /**
     * 查询快递公司管理
     * 
     * @param courierCompanyId 快递公司管理主键
     * @return 快递公司管理
     */
    public SysCourierCompany selectSysCourierCompanyByCourierCompanyId(Long courierCompanyId);

    /**
     * 查询快递公司管理列表
     * 
     * @param sysCourierCompany 快递公司管理
     * @return 快递公司管理集合
     */
    public List<SysCourierCompany> selectSysCourierCompanyList(SysCourierCompany sysCourierCompany);

    /**
     * 新增快递公司管理
     * 
     * @param sysCourierCompany 快递公司管理
     * @return 结果
     */
    public int insertSysCourierCompany(SysCourierCompany sysCourierCompany);

    /**
     * 修改快递公司管理
     * 
     * @param sysCourierCompany 快递公司管理
     * @return 结果
     */
    public int updateSysCourierCompany(SysCourierCompany sysCourierCompany);

    /**
     * 批量删除快递公司管理
     * 
     * @param courierCompanyIds 需要删除的快递公司管理主键集合
     * @return 结果
     */
    public int deleteSysCourierCompanyByCourierCompanyIds(Long[] courierCompanyIds);

    /**
     * 删除快递公司管理信息
     * 
     * @param courierCompanyId 快递公司管理主键
     * @return 结果
     */
    public int deleteSysCourierCompanyByCourierCompanyId(Long courierCompanyId);

    /**
     * 导入用户数据
     *
     * @param sysCourierCompanyList 用户数据列表
     * @param operatorName 操作用户
     * @return 结果
     */
    public String importSysCourierCompany(List<SysCourierCompany> sysCourierCompanyList, String operatorName);
}
