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
import com.stu.system.mapper.SysGraduateSchoolMapper;
import com.stu.system.domain.SysGraduateSchool;
import com.stu.system.service.ISysGraduateSchoolService;

import javax.validation.Validator;

/**
 * 毕业院校管理Service业务层处理
 *
 * @author stu
 * @date 2024-10-23
 */
@Service
public class SysGraduateSchoolServiceImpl implements ISysGraduateSchoolService {


    private static final Logger log = LoggerFactory.getLogger(SysGraduateSchoolServiceImpl.class);

    @Autowired
    protected Validator validator;

    @Autowired
    private SysGraduateSchoolMapper sysGraduateSchoolMapper;

    /**
     * 查询毕业院校管理
     *
     * @param graduateSchoolId 毕业院校管理主键
     * @return 毕业院校管理
     */
    @Override
    public SysGraduateSchool selectSysGraduateSchoolByGraduateSchoolId(Long graduateSchoolId) {
        return sysGraduateSchoolMapper.selectSysGraduateSchoolByGraduateSchoolId(graduateSchoolId);
    }

    /**
     * 查询毕业院校管理列表
     *
     * @param sysGraduateSchool 毕业院校管理
     * @return 毕业院校管理
     */
    @Override
    public List<SysGraduateSchool> selectSysGraduateSchoolList(SysGraduateSchool sysGraduateSchool) {
        return sysGraduateSchoolMapper.selectSysGraduateSchoolList(sysGraduateSchool);
    }

    /**
     * 新增毕业院校管理
     *
     * @param sysGraduateSchool 毕业院校管理
     * @return 结果
     */
    @Override
    public int insertSysGraduateSchool(SysGraduateSchool sysGraduateSchool) {
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
    public int updateSysGraduateSchool(SysGraduateSchool sysGraduateSchool) {
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
    public int deleteSysGraduateSchoolByGraduateSchoolIds(Long[] graduateSchoolIds) {
        return sysGraduateSchoolMapper.deleteSysGraduateSchoolByGraduateSchoolIds(graduateSchoolIds);
    }

    /**
     * 删除毕业院校管理信息
     *
     * @param graduateSchoolId 毕业院校管理主键
     * @return 结果
     */
    @Override
    public int deleteSysGraduateSchoolByGraduateSchoolId(Long graduateSchoolId) {
        return sysGraduateSchoolMapper.deleteSysGraduateSchoolByGraduateSchoolId(graduateSchoolId);
    }

    @Override
    public String importSysGraduateSchoolInfo(List<SysGraduateSchool> sysGraduateSchoolList, String operatorName) {
        if (StringUtils.isNull(sysGraduateSchoolList) || sysGraduateSchoolList.isEmpty()) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysGraduateSchool item : sysGraduateSchoolList) {
            try {
                // 验证是否存在这个用户
                SysGraduateSchool u = sysGraduateSchoolMapper.selectSysGraduateSchoolByGraduateSchoolId(item.getGraduateSchoolId());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, item);
                    item.setCreateBy(operatorName);
                    sysGraduateSchoolMapper.insertSysGraduateSchool(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getGraduateSchoolId()).append(" 导入成功");
                } else {
                    BeanValidators.validateWithException(validator, item);
                    item.setGraduateSchoolId(u.getGraduateSchoolId());
                    item.setUpdateBy(operatorName);
                    sysGraduateSchoolMapper.updateSysGraduateSchool(item);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、编号 ").append(item.getGraduateSchoolId()).append(" 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、编号 " + item.getGraduateSchoolId() + " 导入失败：";
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
