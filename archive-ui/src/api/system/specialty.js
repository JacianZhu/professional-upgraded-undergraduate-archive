import request from '@/utils/request'

// 查询专业管理列表
export function listSpecialty(query) {
  return request({
    url: '/system/specialty/list',
    method: 'get',
    params: query
  })
}

// 查询专业管理详细
export function getSpecialty(specialtyId) {
  return request({
    url: '/system/specialty/' + specialtyId,
    method: 'get'
  })
}

// 新增专业管理
export function addSpecialty(data) {
  return request({
    url: '/system/specialty',
    method: 'post',
    data: data
  })
}

// 修改专业管理
export function updateSpecialty(data) {
  return request({
    url: '/system/specialty',
    method: 'put',
    data: data
  })
}

// 删除专业管理
export function delSpecialty(specialtyId) {
  return request({
    url: '/system/specialty/' + specialtyId,
    method: 'delete'
  })
}
