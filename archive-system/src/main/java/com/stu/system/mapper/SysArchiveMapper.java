package com.stu.system.mapper;

import java.util.List;
import com.stu.system.domain.SysArchive;

/**
 * 档案信息Mapper接口
 * 
 * @author stu
 * @date 2024-11-03
 */
public interface SysArchiveMapper 
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
     * 删除档案信息
     * 
     * @param archiveId 档案信息主键
     * @return 结果
     */
    public int deleteSysArchiveByArchiveId(Long archiveId);

    /**
     * 批量删除档案信息
     * 
     * @param archiveIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysArchiveByArchiveIds(Long[] archiveIds);
}
