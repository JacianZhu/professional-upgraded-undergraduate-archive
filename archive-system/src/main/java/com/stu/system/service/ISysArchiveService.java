package com.stu.system.service;

import java.util.List;
import com.stu.system.domain.SysArchive;

/**
 * 档案信息Service接口
 * 
 * @author stu
 * @date 2024-10-24
 */
public interface ISysArchiveService 
{
    /**
     * 查询档案信息
     * 
     * @param archiveId 档案信息主键
     * @return 档案信息
     */
    public SysArchive selectSysArchiveByArchiveId(Long archiveId);

    /**
     * 查询档案信息列表
     * 
     * @param sysArchive 档案信息
     * @return 档案信息集合
     */
    public List<SysArchive> selectSysArchiveList(SysArchive sysArchive);

    /**
     * 新增档案信息
     * 
     * @param sysArchive 档案信息
     * @return 结果
     */
    public int insertSysArchive(SysArchive sysArchive);

    /**
     * 修改档案信息
     * 
     * @param sysArchive 档案信息
     * @return 结果
     */
    public int updateSysArchive(SysArchive sysArchive);

    /**
     * 批量删除档案信息
     * 
     * @param archiveIds 需要删除的档案信息主键集合
     * @return 结果
     */
    public int deleteSysArchiveByArchiveIds(Long[] archiveIds);

    /**
     * 删除档案信息信息
     * 
     * @param archiveId 档案信息主键
     * @return 结果
     */
    public int deleteSysArchiveByArchiveId(Long archiveId);
}
