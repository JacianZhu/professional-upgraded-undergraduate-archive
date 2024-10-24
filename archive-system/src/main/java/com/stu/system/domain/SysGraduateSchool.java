package com.stu.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 毕业院校管理对象 sys_graduate_school
 * 
 * @author stu
 * @date 2024-10-23
 */
public class SysGraduateSchool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 毕业院校编号 */
    @Excel(name = "毕业院校编号")
    private Long graduateSchoolId;

    /** 毕业院校名称 */
    @Excel(name = "毕业院校名称")
    private String graduateSchoolName;

    /** 专科专业名称 */
    @Excel(name = "专科专业名称")
    private String specialtyName;

    public void setGraduateSchoolId(Long graduateSchoolId) 
    {
        this.graduateSchoolId = graduateSchoolId;
    }

    public Long getGraduateSchoolId() 
    {
        return graduateSchoolId;
    }
    public void setGraduateSchoolName(String graduateSchoolName) 
    {
        this.graduateSchoolName = graduateSchoolName;
    }

    public String getGraduateSchoolName() 
    {
        return graduateSchoolName;
    }
    public void setSpecialtyName(String specialtyName) 
    {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyName() 
    {
        return specialtyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("graduateSchoolId", getGraduateSchoolId())
            .append("graduateSchoolName", getGraduateSchoolName())
            .append("specialtyName", getSpecialtyName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
