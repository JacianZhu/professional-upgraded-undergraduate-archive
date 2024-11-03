import request from '@/utils/request'

// 查询定时任务调度列表
export function getStatisticData() {
  return request({
    url: '/dashboard/getStatisticData',
    method: 'get',
  })
}

export function statisticsMajor() {
  return request({
    url: '/dashboard/statisticsMajor\n',
    method: 'get',
  })
}

export function statisticsAdmissionSchool() {
  return request({
    url: '/dashboard/statisticsAdmissionSchool',
    method: 'get',
  })
}
