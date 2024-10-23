import request from '@/utils/request'

// 查询档案接收人管理列表
export function listReceiver(query) {
  return request({
    url: '/system/receiver/list',
    method: 'get',
    params: query
  })
}

// 查询档案接收人管理详细
export function getReceiver(receiverId) {
  return request({
    url: '/system/receiver/' + receiverId,
    method: 'get'
  })
}

// 新增档案接收人管理
export function addReceiver(data) {
  return request({
    url: '/system/receiver',
    method: 'post',
    data: data
  })
}

// 修改档案接收人管理
export function updateReceiver(data) {
  return request({
    url: '/system/receiver',
    method: 'put',
    data: data
  })
}

// 删除档案接收人管理
export function delReceiver(receiverId) {
  return request({
    url: '/system/receiver/' + receiverId,
    method: 'delete'
  })
}
