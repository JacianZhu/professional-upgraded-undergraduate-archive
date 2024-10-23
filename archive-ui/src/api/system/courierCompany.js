import request from '@/utils/request'

// 查询快递公司管理列表
export function listCourierCompany(query) {
  return request({
    url: '/system/courierCompany/list',
    method: 'get',
    params: query
  })
}

// 查询快递公司管理详细
export function getCourierCompany(courierCompanyId) {
  return request({
    url: '/system/courierCompany/' + courierCompanyId,
    method: 'get'
  })
}

// 新增快递公司管理
export function addCourierCompany(data) {
  return request({
    url: '/system/courierCompany',
    method: 'post',
    data: data
  })
}

// 修改快递公司管理
export function updateCourierCompany(data) {
  return request({
    url: '/system/courierCompany',
    method: 'put',
    data: data
  })
}

// 删除快递公司管理
export function delCourierCompany(courierCompanyId) {
  return request({
    url: '/system/courierCompany/' + courierCompanyId,
    method: 'delete'
  })
}
