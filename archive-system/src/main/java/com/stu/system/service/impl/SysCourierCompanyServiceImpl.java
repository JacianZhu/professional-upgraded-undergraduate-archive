package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysCourierCompanyMapper;
import com.stu.system.domain.SysCourierCompany;
import com.stu.system.service.ISysCourierCompanyService;

/**
 * 快递公司管理Service业务层处理
 * 
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysCourierCompanyServiceImpl implements ISysCourierCompanyService 
{
    @Autowired
    private SysCourierCompanyMapper sysCourierCompanyMapper;

    /**
     * 查询快递公司管理
     * 
     * @param courierCompanyId 快递公司管理主键
     * @return 快递公司管理
     */
    @Override
    public SysCourierCompany selectSysCourierCompanyByCourierCompanyId(Long courierCompanyId)
    {
        return sysCourierCompanyMapper.selectSysCourierCompanyByCourierCompanyId(courierCompanyId);
    }

    /**
     * 查询快递公司管理列表
     * 
     * @param sysCourierCompany 快递公司管理
     * @return 快递公司管理
     */
    @Override
    public List<SysCourierCompany> selectSysCourierCompanyList(SysCourierCompany sysCourierCompany)
    {
        return sysCourierCompanyMapper.selectSysCourierCompanyList(sysCourierCompany);
    }

    /**
     * 新增快递公司管理
     * 
     * @param sysCourierCompany 快递公司管理
     * @return 结果
     */
    @Override
    public int insertSysCourierCompany(SysCourierCompany sysCourierCompany)
    {
        sysCourierCompany.setCreateTime(DateUtils.getNowDate());
        return sysCourierCompanyMapper.insertSysCourierCompany(sysCourierCompany);
    }

    /**
     * 修改快递公司管理
     * 
     * @param sysCourierCompany 快递公司管理
     * @return 结果
     */
    @Override
    public int updateSysCourierCompany(SysCourierCompany sysCourierCompany)
    {
        sysCourierCompany.setUpdateTime(DateUtils.getNowDate());
        return sysCourierCompanyMapper.updateSysCourierCompany(sysCourierCompany);
    }

    /**
     * 批量删除快递公司管理
     * 
     * @param courierCompanyIds 需要删除的快递公司管理主键
     * @return 结果
     */
    @Override
    public int deleteSysCourierCompanyByCourierCompanyIds(Long[] courierCompanyIds)
    {
        return sysCourierCompanyMapper.deleteSysCourierCompanyByCourierCompanyIds(courierCompanyIds);
    }

    /**
     * 删除快递公司管理信息
     * 
     * @param courierCompanyId 快递公司管理主键
     * @return 结果
     */
    @Override
    public int deleteSysCourierCompanyByCourierCompanyId(Long courierCompanyId)
    {
        return sysCourierCompanyMapper.deleteSysCourierCompanyByCourierCompanyId(courierCompanyId);
    }
}
