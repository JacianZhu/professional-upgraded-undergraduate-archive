import request from '@/utils/request'

// 查询班级管理列表
export function listClass(query) {
  return request({
    url: '/system/class/list',
    method: 'get',
    params: query
  })
}

// 查询班级管理详细
export function getClass(classId) {
  return request({
    url: '/system/class/' + classId,
    method: 'get'
  })
}

// 新增班级管理
export function addClass(data) {
  return request({
    url: '/system/class',
    method: 'post',
    data: data
  })
}

// 修改班级管理
export function updateClass(data) {
  return request({
    url: '/system/class',
    method: 'put',
    data: data
  })
}

// 删除班级管理
export function delClass(classId) {
  return request({
    url: '/system/class/' + classId,
    method: 'delete'
  })
}
export function getSpecialtyList() {
  return request({
    url: '/system/class/getSpecialtyList',
    method: 'get'
  })
}
export function getHeadTeacherList () {
  return request({
    url: '/system/class/getHeadTeacherList',
    method: 'get'
  })
}

export function getSchoolList () {
  return request({
    url: '/system/class/getSchoolList',
    method: 'get'
  })
}
