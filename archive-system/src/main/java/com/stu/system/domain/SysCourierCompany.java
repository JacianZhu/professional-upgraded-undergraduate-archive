package com.stu.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 快递公司管理对象 sys_courier_company
 * 
 * @author stu
 * @date 2024-10-23
 */
public class SysCourierCompany extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 快递公司编号 */
    private Long courierCompanyId;

    /** 快递公司名称 */
    @Excel(name = "快递公司名称")
    private String courierCompanyName;

    /** 业务员 */
    @Excel(name = "业务员")
    private String businessPerson;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    public void setCourierCompanyId(Long courierCompanyId) 
    {
        this.courierCompanyId = courierCompanyId;
    }

    public Long getCourierCompanyId() 
    {
        return courierCompanyId;
    }
    public void setCourierCompanyName(String courierCompanyName) 
    {
        this.courierCompanyName = courierCompanyName;
    }

    public String getCourierCompanyName() 
    {
        return courierCompanyName;
    }
    public void setBusinessPerson(String businessPerson) 
    {
        this.businessPerson = businessPerson;
    }

    public String getBusinessPerson() 
    {
        return businessPerson;
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
            .append("courierCompanyId", getCourierCompanyId())
            .append("courierCompanyName", getCourierCompanyName())
            .append("businessPerson", getBusinessPerson())
            .append("contactNumber", getContactNumber())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
