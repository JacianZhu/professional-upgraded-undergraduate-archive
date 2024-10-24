package com.stu.system.service.impl;

import java.util.List;

import com.stu.common.exception.ServiceException;
import com.stu.common.utils.DateUtils;
import com.stu.common.utils.StringUtils;
import com.stu.common.utils.bean.BeanValidators;
import com.stu.system.domain.SysAdmissionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysCourierCompanyMapper;
import com.stu.system.domain.SysCourierCompany;
import com.stu.system.service.ISysCourierCompanyService;

import javax.validation.Validator;

/**
 * 快递公司管理Service业务层处理
 *
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysCourierCompanyServiceImpl implements ISysCourierCompanyService {

    private static final Logger log = LoggerFactory.getLogger(SysCourierCompanyServiceImpl.class);

    @Autowired
    protected Validator validator;

    @Autowired
    private SysCourierCompanyMapper sysCourierCompanyMapper;

    /**
     * 查询快递公司管理
     *
     * @param courierCompanyId 快递公司管理主键
     * @return 快递公司管理
     */
    @Override
    public SysCourierCompany selectSysCourierCompanyByCourierCompanyId(Long courierCompanyId) {
        return sysCourierCompanyMapper.selectSysCourierCompanyByCourierCompanyId(courierCompanyId);
    }

    /**
     * 查询快递公司管理列表
     *
     * @param sysCourierCompany 快递公司管理
     * @return 快递公司管理
     */
    @Override
    public List<SysCourierCompany> selectSysCourierCompanyList(SysCourierCompany sysCourierCompany) {
        return sysCourierCompanyMapper.selectSysCourierCompanyList(sysCourierCompany);
    }

    /**
     * 新增快递公司管理
     *
     * @param sysCourierCompany 快递公司管理
     * @return 结果
     */
    @Override
    public int insertSysCourierCompany(SysCourierCompany sysCourierCompany) {
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
    public int updateSysCourierCompany(SysCourierCompany sysCourierCompany) {
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
    public int deleteSysCourierCompanyByCourierCompanyIds(Long[] courierCompanyIds) {
        return sysCourierCompanyMapper.deleteSysCourierCompanyByCourierCompanyIds(courierCompanyIds);
    }

    /**
     * 删除快递公司管理信息
     *
     * @param courierCompanyId 快递公司管理主键
     * @return 结果
     */
    @Override
    public int deleteSysCourierCompanyByCourierCompanyId(Long courierCompanyId) {
        return sysCourierCompanyMapper.deleteSysCourierCompanyByCourierCompanyId(courierCompanyId);
    }

    @Override
    public String importSysCourierCompany(List<SysCourierCompany> sysCourierCompanyList, String operatorName) {
        if (StringUtils.isNull(sysCourierCompanyList) || sysCourierCompanyList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysCourierCompany item : sysCourierCompanyList) {
            try {
                // 验证是否存在这个用户
                SysCourierCompany u = sysCourierCompanyMapper.selectSysCourierCompanyByCourierCompanyId(item.getCourierCompanyId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    item.setCreateBy(operatorName);
                    sysCourierCompanyMapper.insertSysCourierCompany(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getCourierCompanyId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setCourierCompanyId(u.getCourierCompanyId());
                    item.setUpdateBy(operatorName);
                    sysCourierCompanyMapper.updateSysCourierCompany(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getCourierCompanyId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getCourierCompanyId() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
