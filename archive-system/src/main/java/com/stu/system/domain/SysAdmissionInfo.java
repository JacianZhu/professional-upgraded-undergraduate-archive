package com.stu.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stu.common.annotation.Excel;
import com.stu.common.core.domain.BaseEntity;

/**
 * 录取信息导入对象 sys_admission_info
 * 
 * @author stu
 * @date 2024-10-23
 */
public class SysAdmissionInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 录取编号 */
    @Excel(name = "录取编号")
    private Long admissionId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String studentName;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNumber;

    /** 学号 */
    @Excel(name = "学号")
    private String studentId;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String graduateSchool;

    /** 专科专业 */
    @Excel(name = "专科专业")
    private String diplomaSpecialty;

    /** 毕业时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "毕业时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date graduationDate;

    /** 录取本科院校 */
    @Excel(name = "录取本科院校")
    private String admittedUniversity;

    /** 本科专业 */
    @Excel(name = "本科专业")
    private String admittedSpecialty;

    /** 录取批次 */
    @Excel(name = "录取批次")
    private String admissionBatch;

    /** 录取日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录取日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date admissionDate;

    /** 录取状态 */
    @Excel(name = "录取状态")
    private String admissionStatus;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 联系地址 */
    @Excel(name = "联系地址")
    private String contactAddress;

    /** 录取类型 */
    @Excel(name = "录取类型")
    private String admissionType;

    /** 考生类别 */
    @Excel(name = "考生类别")
    private String candidateType;

    /** 准考证号 */
    @Excel(name = "准考证号")
    private String examTicketNumber;

    /** 成绩 */
    @Excel(name = "成绩")
    private BigDecimal examScore;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setAdmissionId(Long admissionId) 
    {
        this.admissionId = admissionId;
    }

    public Long getAdmissionId() 
    {
        return admissionId;
    }
    public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setIdNumber(String idNumber) 
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber() 
    {
        return idNumber;
    }
    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
    }

    public String getStudentId() 
    {
        return studentId;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setGraduateSchool(String graduateSchool) 
    {
        this.graduateSchool = graduateSchool;
    }

    public String getGraduateSchool() 
    {
        return graduateSchool;
    }
    public void setDiplomaSpecialty(String diplomaSpecialty) 
    {
        this.diplomaSpecialty = diplomaSpecialty;
    }

    public String getDiplomaSpecialty() 
    {
        return diplomaSpecialty;
    }
    public void setGraduationDate(Date graduationDate) 
    {
        this.graduationDate = graduationDate;
    }

    public Date getGraduationDate() 
    {
        return graduationDate;
    }
    public void setAdmittedUniversity(String admittedUniversity) 
    {
        this.admittedUniversity = admittedUniversity;
    }

    public String getAdmittedUniversity() 
    {
        return admittedUniversity;
    }
    public void setAdmittedSpecialty(String admittedSpecialty) 
    {
        this.admittedSpecialty = admittedSpecialty;
    }

    public String getAdmittedSpecialty() 
    {
        return admittedSpecialty;
    }
    public void setAdmissionBatch(String admissionBatch) 
    {
        this.admissionBatch = admissionBatch;
    }

    public String getAdmissionBatch() 
    {
        return admissionBatch;
    }
    public void setAdmissionDate(Date admissionDate) 
    {
        this.admissionDate = admissionDate;
    }

    public Date getAdmissionDate() 
    {
        return admissionDate;
    }
    public void setAdmissionStatus(String admissionStatus) 
    {
        this.admissionStatus = admissionStatus;
    }

    public String getAdmissionStatus() 
    {
        return admissionStatus;
    }
    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() 
    {
        return contactNumber;
    }
    public void setContactAddress(String contactAddress) 
    {
        this.contactAddress = contactAddress;
    }

    public String getContactAddress() 
    {
        return contactAddress;
    }
    public void setAdmissionType(String admissionType) 
    {
        this.admissionType = admissionType;
    }

    public String getAdmissionType() 
    {
        return admissionType;
    }
    public void setCandidateType(String candidateType) 
    {
        this.candidateType = candidateType;
    }

    public String getCandidateType() 
    {
        return candidateType;
    }
    public void setExamTicketNumber(String examTicketNumber) 
    {
        this.examTicketNumber = examTicketNumber;
    }

    public String getExamTicketNumber() 
    {
        return examTicketNumber;
    }
    public void setExamScore(BigDecimal examScore) 
    {
        this.examScore = examScore;
    }

    public BigDecimal getExamScore() 
    {
        return examScore;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("admissionId", getAdmissionId())
            .append("studentName", getStudentName())
            .append("idNumber", getIdNumber())
            .append("studentId", getStudentId())
            .append("gender", getGender())
            .append("graduateSchool", getGraduateSchool())
            .append("diplomaSpecialty", getDiplomaSpecialty())
            .append("graduationDate", getGraduationDate())
            .append("admittedUniversity", getAdmittedUniversity())
            .append("admittedSpecialty", getAdmittedSpecialty())
            .append("admissionBatch", getAdmissionBatch())
            .append("admissionDate", getAdmissionDate())
            .append("admissionStatus", getAdmissionStatus())
            .append("contactNumber", getContactNumber())
            .append("contactAddress", getContactAddress())
            .append("admissionType", getAdmissionType())
            .append("candidateType", getCandidateType())
            .append("examTicketNumber", getExamTicketNumber())
            .append("examScore", getExamScore())
            .append("remarks", getRemarks())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
