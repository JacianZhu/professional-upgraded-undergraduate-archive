package com.stu.system.service.impl;

import java.util.List;
import com.stu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stu.system.mapper.SysArchiveMapper;
import com.stu.system.domain.SysArchive;
import com.stu.system.service.ISysArchiveService;

/**
 * 档案信息Service业务层处理
 * 
 * @author stu
 * @date 2024-11-03
 */
@Service
public class SysArchiveServiceImpl implements ISysArchiveService 
{
    @Autowired
    private SysArchiveMapper sysArchiveMapper;

    /**
     * 查询档案信息
     * 
     * @param archiveId 档案信息主键
     * @return 档案信息
     */
    @Override
    public SysArchive selectSysArchiveByArchiveId(Long archiveId)
    {
        return sysArchiveMapper.selectSysArchiveByArchiveId(archiveId);
    }

    /**
     * 查询档案信息列表
     * 
     * @param sysArchive 档案信息
     * @return 档案信息
     */
    @Override
    public List<SysArchive> selectSysArchiveList(SysArchive sysArchive)
    {
        return sysArchiveMapper.selectSysArchiveList(sysArchive);
    }

    /**
     * 新增档案信息
     * 
     * @param sysArchive 档案信息
     * @return 结果
     */
    @Override
    public int insertSysArchive(SysArchive sysArchive)
    {
        sysArchive.setCreateTime(DateUtils.getNowDate());
        return sysArchiveMapper.insertSysArchive(sysArchive);
    }

    /**
     * 修改档案信息
     * 
     * @param sysArchive 档案信息
     * @return 结果
     */
    @Override
    public int updateSysArchive(SysArchive sysArchive)
    {
        return sysArchiveMapper.updateSysArchive(sysArchive);
    }

    /**
     * 批量删除档案信息
     * 
     * @param archiveIds 需要删除的档案信息主键
     * @return 结果
     */
    @Override
    public int deleteSysArchiveByArchiveIds(Long[] archiveIds)
    {
        return sysArchiveMapper.deleteSysArchiveByArchiveIds(archiveIds);
    }

    /**
     * 删除档案信息信息
     * 
     * @param archiveId 档案信息主键
     * @return 结果
     */
    @Override
    public int deleteSysArchiveByArchiveId(Long archiveId)
    {
        return sysArchiveMapper.deleteSysArchiveByArchiveId(archiveId);
    }
}
