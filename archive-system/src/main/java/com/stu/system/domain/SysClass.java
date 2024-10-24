package com.stu.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 班级管理对象 sys_class
 * 
 * @author stu
 * @date 2024-10-23
 */
public class SysClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 班级编号 */
    @Excel(name = "班级编号")
    private Long classId;

    /** 班级名称 */
    @Excel(name = "班级名称")
    private String className;

    private Long specialtyId;

    private Long headTeacherId;

    /** 专业 */
    @Excel(name = "专业")
    private String specialtyName;

    /** 班主任 */
    @Excel(name = "班主任")
    private String headTeacherName;


    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getHeadTeacherName() {
        return headTeacherName;
    }

    public void setHeadTeacherName(String headTeacherName) {
        this.headTeacherName = headTeacherName;
    }

    public void setClassId(Long classId)
    {
        this.classId = classId;
    }

    public Long getClassId() 
    {
        return classId;
    }
    public void setClassName(String className) 
    {
        this.className = className;
    }

    public String getClassName() 
    {
        return className;
    }
    public void setSpecialtyId(Long specialtyId) 
    {
        this.specialtyId = specialtyId;
    }

    public Long getSpecialtyId() 
    {
        return specialtyId;
    }
    public void setHeadTeacherId(Long headTeacherId) 
    {
        this.headTeacherId = headTeacherId;
    }

    public Long getHeadTeacherId() 
    {
        return headTeacherId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classId", getClassId())
            .append("className", getClassName())
            .append("specialtyId", getSpecialtyId())
            .append("headTeacherId", getHeadTeacherId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
