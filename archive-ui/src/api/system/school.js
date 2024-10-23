import request from '@/utils/request'

// 查询毕业院校管理列表
export function listSchool(query) {
  return request({
    url: '/system/school/list',
    method: 'get',
    params: query
  })
}

// 查询毕业院校管理详细
export function getSchool(graduateSchoolId) {
  return request({
    url: '/system/school/' + graduateSchoolId,
    method: 'get'
  })
}

// 新增毕业院校管理
export function addSchool(data) {
  return request({
    url: '/system/school',
    method: 'post',
    data: data
  })
}

// 修改毕业院校管理
export function updateSchool(data) {
  return request({
    url: '/system/school',
    method: 'put',
    data: data
  })
}

// 删除毕业院校管理
export function delSchool(graduateSchoolId) {
  return request({
    url: '/system/school/' + graduateSchoolId,
    method: 'delete'
  })
}
