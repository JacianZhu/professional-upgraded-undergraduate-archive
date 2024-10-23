import request from '@/utils/request'

// 查询录取信息导入列表
export function listAdmissionInfo(query) {
  return request({
    url: '/system/admissionInfo/list',
    method: 'get',
    params: query
  })
}

// 查询录取信息导入详细
export function getAdmissionInfo(admissionId) {
  return request({
    url: '/system/admissionInfo/' + admissionId,
    method: 'get'
  })
}

// 新增录取信息导入
export function addAdmissionInfo(data) {
  return request({
    url: '/system/admissionInfo',
    method: 'post',
    data: data
  })
}

// 修改录取信息导入
export function updateAdmissionInfo(data) {
  return request({
    url: '/system/admissionInfo',
    method: 'put',
    data: data
  })
}

// 删除录取信息导入
export function delAdmissionInfo(admissionId) {
  return request({
    url: '/system/admissionInfo/' + admissionId,
    method: 'delete'
  })
}
