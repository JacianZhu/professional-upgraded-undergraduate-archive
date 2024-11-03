package com.stu.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 档案信息对象 sys_archive
 * 
 * @author stu
 * @date 2024-11-03
 */
public class SysArchive extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 档案编号 */
    @Excel(name = "档案编号")
    private Long archiveId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 学生性别 */
    @Excel(name = "学生性别")
    private String studentGender;

    /** 学生出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "学生出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date studentDateOfBirth;

    /** 学生身份证号 */
    @Excel(name = "学生身份证号")
    private String studentNationalId;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String graduationSchool;

    /** 毕业专业 */
    @Excel(name = "毕业专业")
    private String graduationMajor;

    /** 接收方式（邮寄或自带） */
    @Excel(name = "接收方式", readConverterExp = "邮=寄或自带")
    private String receiveMethod;

    /** 邮寄单号（仅邮寄方式有效） */
    @Excel(name = "邮寄单号", readConverterExp = "仅=邮寄方式有效")
    private String trackingNumber;

    /** 接收日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "接收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveDate;

    /** 邮寄人（仅邮寄方式有效） */
    @Excel(name = "邮寄人", readConverterExp = "仅=邮寄方式有效")
    private String sender;

    /** 是否移交班主任（仅自带方式有效） */
    @Excel(name = "是否移交班主任", readConverterExp = "仅=自带方式有效")
    private String handedToTeacher;

    /** 是否拆封（仅自带方式有效） */
    @Excel(name = "是否拆封", readConverterExp = "仅=自带方式有效")
    private String opened;

    /** 移交方式（自提或邮寄） */
    @Excel(name = "移交方式", readConverterExp = "自=提或邮寄")
    private String transferMethod;

    /** 移交日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "移交日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transferDate;

    /** 移交人（仅自提方式有效） */
    @Excel(name = "移交人", readConverterExp = "仅=自提方式有效")
    private String transferPerson;

    /** 档案接收人（仅邮寄方式有效） */
    @Excel(name = "档案接收人", readConverterExp = "仅=邮寄方式有效")
    private String recipient;

    /** 联系电话（仅邮寄方式有效） */
    @Excel(name = "联系电话", readConverterExp = "仅=邮寄方式有效")
    private String contactPhone;

    /** 邮寄地址（仅邮寄方式有效） */
    @Excel(name = "邮寄地址", readConverterExp = "仅=邮寄方式有效")
    private String mailingAddress;

    /** 档案是否完整 */
    @Excel(name = "档案是否完整")
    private Integer archiveComplete;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 档案状态 */
    @Excel(name = "档案状态")
    private String archiveStatus;

    public void setArchiveId(Long archiveId) 
    {
        this.archiveId = archiveId;
    }

    public Long getArchiveId() 
    {
        return archiveId;
    }
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setStudentGender(String studentGender) 
    {
        this.studentGender = studentGender;
    }

    public String getStudentGender() 
    {
        return studentGender;
    }
    public void setStudentDateOfBirth(Date studentDateOfBirth) 
    {
        this.studentDateOfBirth = studentDateOfBirth;
    }

    public Date getStudentDateOfBirth() 
    {
        return studentDateOfBirth;
    }
    public void setStudentNationalId(String studentNationalId) 
    {
        this.studentNationalId = studentNationalId;
    }

    public String getStudentNationalId() 
    {
        return studentNationalId;
    }
    public void setGraduationSchool(String graduationSchool) 
    {
        this.graduationSchool = graduationSchool;
    }

    public String getGraduationSchool() 
    {
        return graduationSchool;
    }
    public void setGraduationMajor(String graduationMajor) 
    {
        this.graduationMajor = graduationMajor;
    }

    public String getGraduationMajor() 
    {
        return graduationMajor;
    }
    public void setReceiveMethod(String receiveMethod) 
    {
        this.receiveMethod = receiveMethod;
    }

    public String getReceiveMethod() 
    {
        return receiveMethod;
    }
    public void setTrackingNumber(String trackingNumber) 
    {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() 
    {
        return trackingNumber;
    }
    public void setReceiveDate(Date receiveDate) 
    {
        this.receiveDate = receiveDate;
    }

    public Date getReceiveDate() 
    {
        return receiveDate;
    }
    public void setSender(String sender) 
    {
        this.sender = sender;
    }

    public String getSender() 
    {
        return sender;
    }
    public void setHandedToTeacher(String handedToTeacher) 
    {
        this.handedToTeacher = handedToTeacher;
    }

    public String getHandedToTeacher() 
    {
        return handedToTeacher;
    }
    public void setOpened(String opened) 
    {
        this.opened = opened;
    }

    public String getOpened() 
    {
        return opened;
    }
    public void setTransferMethod(String transferMethod) 
    {
        this.transferMethod = transferMethod;
    }

    public String getTransferMethod() 
    {
        return transferMethod;
    }
    public void setTransferDate(Date transferDate) 
    {
        this.transferDate = transferDate;
    }

    public Date getTransferDate() 
    {
        return transferDate;
    }
    public void setTransferPerson(String transferPerson) 
    {
        this.transferPerson = transferPerson;
    }

    public String getTransferPerson() 
    {
        return transferPerson;
    }
    public void setRecipient(String recipient) 
    {
        this.recipient = recipient;
    }

    public String getRecipient() 
    {
        return recipient;
    }
    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }
    public void setMailingAddress(String mailingAddress) 
    {
        this.mailingAddress = mailingAddress;
    }

    public String getMailingAddress() 
    {
        return mailingAddress;
    }
    public void setArchiveComplete(Integer archiveComplete) 
    {
        this.archiveComplete = archiveComplete;
    }

    public Integer getArchiveComplete() 
    {
        return archiveComplete;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setArchiveStatus(String archiveStatus) 
    {
        this.archiveStatus = archiveStatus;
    }

    public String getArchiveStatus() 
    {
        return archiveStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("archiveId", getArchiveId())
            .append("studentName", getStudentName())
            .append("studentGender", getStudentGender())
            .append("studentDateOfBirth", getStudentDateOfBirth())
            .append("studentNationalId", getStudentNationalId())
            .append("graduationSchool", getGraduationSchool())
            .append("graduationMajor", getGraduationMajor())
            .append("receiveMethod", getReceiveMethod())
            .append("trackingNumber", getTrackingNumber())
            .append("receiveDate", getReceiveDate())
            .append("sender", getSender())
            .append("handedToTeacher", getHandedToTeacher())
            .append("opened", getOpened())
            .append("transferMethod", getTransferMethod())
            .append("transferDate", getTransferDate())
            .append("transferPerson", getTransferPerson())
            .append("recipient", getRecipient())
            .append("contactPhone", getContactPhone())
            .append("mailingAddress", getMailingAddress())
            .append("archiveComplete", getArchiveComplete())
            .append("remarks", getRemarks())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("archiveStatus", getArchiveStatus())
            .toString();
    }
}
