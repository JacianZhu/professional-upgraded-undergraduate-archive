package com.stu.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 班主任管理对象 sys_head_teacher
 * 
 * @author stu
 * @date 2024-10-23
 */
public class SysHeadTeacher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 班主任编号 */
    @Excel(name = "班主任编号")
    private Long headTeacherId;

    /** 班主任姓名 */
    @Excel(name = "班主任姓名")
    private String headTeacherName;

    /** 所属部门 */
    @Excel(name = "所属部门")
    private String departmentName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    @Excel(name = "班主任身份证号")
    private String idNumber;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setHeadTeacherId(Long headTeacherId) 
    {
        this.headTeacherId = headTeacherId;
    }

    public Long getHeadTeacherId() 
    {
        return headTeacherId;
    }
    public void setHeadTeacherName(String headTeacherName) 
    {
        this.headTeacherName = headTeacherName;
    }

    public String getHeadTeacherName() 
    {
        return headTeacherName;
    }
    public void setDepartmentName(String departmentName) 
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() 
    {
        return departmentName;
    }
    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() 
    {
        return contactNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("headTeacherId", getHeadTeacherId())
            .append("headTeacherName", getHeadTeacherName())
            .append("departmentName", getDepartmentName())
            .append("contactNumber", getContactNumber())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
