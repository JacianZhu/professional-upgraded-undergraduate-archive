import request from '@/utils/request'

// 查询档案信息列表
export function listArchiveInfo(query) {
  return request({
    url: '/system/archiveInfo/list',
    method: 'get',
    params: query
  })
}

// 查询档案信息详细
export function getArchiveInfo(archiveId) {
  return request({
    url: '/system/archiveInfo/' + archiveId,
    method: 'get'
  })
}

// 新增档案信息
export function addArchiveInfo(data) {
  return request({
    url: '/system/archiveInfo',
    method: 'post',
    data: data
  })
}

// 修改档案信息
export function updateArchiveInfo(data) {
  return request({
    url: '/system/archiveInfo',
    method: 'put',
    data: data
  })
}

// 删除档案信息
export function delArchiveInfo(archiveId) {
  return request({
    url: '/system/archiveInfo/' + archiveId,
    method: 'delete'
  })
}

export function getAllHeadteacher() {
  return request({
    url: '/system/archiveInfo/getAllHeadTeacherList',
    method: 'get'
  })
}

export function getAllReceiveList() {
  return request({
    url: '/system/archiveInfo/getAllReceiveList',
    method: 'get'
  })
}


export function updateTeacherReceiveFlag(username) {
  return request({
    url: '/system/archiveInfo/updateTeacherReceiveFlag?username=' + username,
    method: 'get',
  })
}

