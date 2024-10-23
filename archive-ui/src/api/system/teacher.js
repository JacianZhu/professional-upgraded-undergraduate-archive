import request from '@/utils/request'

// 查询班主任管理列表
export function listTeacher(query) {
  return request({
    url: '/system/teacher/list',
    method: 'get',
    params: query
  })
}

// 查询班主任管理详细
export function getTeacher(headTeacherId) {
  return request({
    url: '/system/teacher/' + headTeacherId,
    method: 'get'
  })
}

// 新增班主任管理
export function addTeacher(data) {
  return request({
    url: '/system/teacher',
    method: 'post',
    data: data
  })
}

// 修改班主任管理
export function updateTeacher(data) {
  return request({
    url: '/system/teacher',
    method: 'put',
    data: data
  })
}

// 删除班主任管理
export function delTeacher(headTeacherId) {
  return request({
    url: '/system/teacher/' + headTeacherId,
    method: 'delete'
  })
}
