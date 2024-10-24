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
import com.stu.system.mapper.SysSpecialtyMapper;
import com.stu.system.domain.SysSpecialty;
import com.stu.system.service.ISysSpecialtyService;

import javax.validation.Validator;

/**
 * 专业管理Service业务层处理
 *
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysSpecialtyServiceImpl implements ISysSpecialtyService {

    private static final Logger log = LoggerFactory.getLogger(SysSpecialtyServiceImpl.class);

    @Autowired
    protected Validator validator;

    @Autowired
    private SysSpecialtyMapper sysSpecialtyMapper;

    /**
     * 查询专业管理
     *
     * @param specialtyId 专业管理主键
     * @return 专业管理
     */
    @Override
    public SysSpecialty selectSysSpecialtyBySpecialtyId(Long specialtyId) {
        return sysSpecialtyMapper.selectSysSpecialtyBySpecialtyId(specialtyId);
    }

    /**
     * 查询专业管理列表
     *
     * @param sysSpecialty 专业管理
     * @return 专业管理
     */
    @Override
    public List<SysSpecialty> selectSysSpecialtyList(SysSpecialty sysSpecialty) {
        return sysSpecialtyMapper.selectSysSpecialtyList(sysSpecialty);
    }

    /**
     * 新增专业管理
     *
     * @param sysSpecialty 专业管理
     * @return 结果
     */
    @Override
    public int insertSysSpecialty(SysSpecialty sysSpecialty) {
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
    public int updateSysSpecialty(SysSpecialty sysSpecialty) {
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
    public int deleteSysSpecialtyBySpecialtyIds(Long[] specialtyIds) {
        return sysSpecialtyMapper.deleteSysSpecialtyBySpecialtyIds(specialtyIds);
    }

    /**
     * 删除专业管理信息
     *
     * @param specialtyId 专业管理主键
     * @return 结果
     */
    @Override
    public int deleteSysSpecialtyBySpecialtyId(Long specialtyId) {
        return sysSpecialtyMapper.deleteSysSpecialtyBySpecialtyId(specialtyId);
    }

    @Override
    public String importSysSpecialtyInfo(List<SysSpecialty> sysSpecialtyInfoList, String operatorName) {
        if (StringUtils.isNull(sysSpecialtyInfoList) || sysSpecialtyInfoList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysSpecialty item : sysSpecialtyInfoList) {
            try {
                // 验证是否存在这个用户
                SysSpecialty u = sysSpecialtyMapper.selectSysSpecialtyBySpecialtyId(item.getSpecialtyId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    item.setCreateBy(operatorName);
                    sysSpecialtyMapper.insertSysSpecialty(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getSpecialtyId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setSpecialtyId(u.getSpecialtyId());
                    item.setUpdateBy(operatorName);
                    sysSpecialtyMapper.updateSysSpecialty(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getSpecialtyId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getSpecialtyId() + " 导入失败：";
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
