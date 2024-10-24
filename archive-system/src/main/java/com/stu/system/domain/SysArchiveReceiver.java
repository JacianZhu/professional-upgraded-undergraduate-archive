package com.stu.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 档案接收人管理对象 sys_archive_receiver
 * 
 * @author stu
 * @date 2024-10-23
 */
public class SysArchiveReceiver extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 接收人编号 */
    @Excel(name = "接收人编号")
    private Long receiverId;

    /** 接收人姓名 */
    @Excel(name = "接收人姓名")
    private String receiverName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 邮编 */
    @Excel(name = "邮编")
    private String postalCode;

    /** 邮寄地址 */
    @Excel(name = "邮寄地址")
    private String mailingAddress;

    public void setReceiverId(Long receiverId) 
    {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() 
    {
        return receiverId;
    }
    public void setReceiverName(String receiverName) 
    {
        this.receiverName = receiverName;
    }

    public String getReceiverName() 
    {
        return receiverName;
    }
    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() 
    {
        return contactNumber;
    }
    public void setPostalCode(String postalCode) 
    {
        this.postalCode = postalCode;
    }

    public String getPostalCode() 
    {
        return postalCode;
    }
    public void setMailingAddress(String mailingAddress) 
    {
        this.mailingAddress = mailingAddress;
    }

    public String getMailingAddress() 
    {
        return mailingAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("receiverId", getReceiverId())
            .append("receiverName", getReceiverName())
            .append("contactNumber", getContactNumber())
            .append("postalCode", getPostalCode())
            .append("mailingAddress", getMailingAddress())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
