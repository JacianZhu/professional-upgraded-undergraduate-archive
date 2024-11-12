import request from '@/utils/request'

// 查询档案信息列表
export function selectSysArchiveByName(username) {
    return request({
        url: `/system/archiveInfo/selectSysArchiveByName?username=${username}`,
        method: 'get',
    })
}