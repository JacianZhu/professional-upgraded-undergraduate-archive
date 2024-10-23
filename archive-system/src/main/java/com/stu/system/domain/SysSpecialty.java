package com.stu.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 专业管理对象 sys_specialty
 * 
 * @author stu
 * @date 2024-10-23
 */
public class SysSpecialty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专业编号 */
    private Long specialtyId;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String specialtyName;

    /** 专业代码 */
    @Excel(name = "专业代码")
    private String specialtyCode;

    /** 院系名称 */
    @Excel(name = "院系名称")
    private String departmentName;

    public void setSpecialtyId(Long specialtyId) 
    {
        this.specialtyId = specialtyId;
    }

    public Long getSpecialtyId() 
    {
        return specialtyId;
    }
    public void setSpecialtyName(String specialtyName) 
    {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyName() 
    {
        return specialtyName;
    }
    public void setSpecialtyCode(String specialtyCode) 
    {
        this.specialtyCode = specialtyCode;
    }

    public String getSpecialtyCode() 
    {
        return specialtyCode;
    }
    public void setDepartmentName(String departmentName) 
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() 
    {
        return departmentName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("specialtyId", getSpecialtyId())
            .append("specialtyName", getSpecialtyName())
            .append("specialtyCode", getSpecialtyCode())
            .append("departmentName", getDepartmentName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
