<template>
  <div class="dashboard-editor-container">

    <el-row :gutter="32">

      <el-col :span="9">
        <personal-info>

        </personal-info>
      </el-col>
      <el-col :span="15">
        <el-row>
          <el-col :span="24">
            <panel-group @handleSetLineChartData="handleSetLineChartData" :headerStaticData="headerStaticData" />
          </el-col>
          <el-col :span="24">
            <el-table size="mini" v-loading="loading" height="275px" :data="noticeList"
              header-row-class-name="header_color" :header-row-style="{ backgroundColor: '#42b983' }">
              <el-table-column label="序号" align="center" prop="noticeId" width="100" />
              <el-table-column label="公告标题" align="center" prop="noticeTitle" :show-overflow-tooltip="true">

                <template #default="{ row }">
                  <!-- 检查 noticeTitle 是否是链接 -->
                  <span v-if="row.noticeType == '3'">
                    <!-- 如果是链接，显示为 a 标签并添加跳转 -->
                    <el-link type="primary">

                      <a :href="extractUrls(row.noticeContent)" target="_blank">{{ row.noticeTitle }}</a>
                    </el-link>
                  </span>
                  <span v-else>
                    <!-- 否则显示普通文本 -->
                    {{ row.noticeTitle }}
                  </span>
                </template>
              </el-table-column>

            </el-table>

            <pagination
              style="position: relative; margin-top: 0;height: 40px;line-height: 40px;display: flex;justify-content: center;align-items: center"
              small v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize" @pagination="initData" />
          </el-col>

        </el-row>

      </el-col>

    </el-row>

    <el-row :gutter="32" v-if="roles != 'teacher'">
      <!--      <el-col :xs="24" :sm="24" :lg="8">-->
      <!--        <div class="chart-wrapper">-->
      <!--          <raddar-chart :line_char_data="raddar_chart"/>-->
      <!--        </div>-->
      <!--      </el-col>-->
      <el-col :xs="24" :sm="24" :lg="12">


        <router-link to="/dataImport/admissionInfo">
          <div class="chart-wrapper">
            <zhuzhuangtu :chart-data="line_char_data"></zhuzhuangtu>
          </div>
        </router-link>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="12">
        <router-link to="/dataImport/admissionInfo">
          <div class="chart-wrapper">
            <line-chart :chart-data="pie_char" />

          </div>
        </router-link>
      </el-col>
      <!--      <el-col :xs="24" :sm="24" :lg="8">-->
      <!--        <div class="chart-wrapper">-->
      <!--          <bar-chart/>-->
      <!--        </div>-->
      <!--      </el-col>-->
    </el-row>


  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import { getStatisticData, statisticsAdmissionSchool, statisticsMajor } from "@/api/dashboard";
import PersonalInfo from "@/views/dashboard/personalInfo.vue";
import { listNotice } from "@/api/system/notice";
import Zhuzhuangtu from "@/views/dashboard/zhuzhuangtu.vue";

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    Zhuzhuangtu,
    PersonalInfo,
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  created() {

    this.init()
    this.initData()
    this.roles = this.$store.state.user.roles[0];
  },
  data() {
    return {

      total: 0,
      loading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      // 公告表格数据
      noticeList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeTitle: undefined,
        createBy: undefined,
        status: undefined
      },

      lineChartData: lineChartData.newVisitis,
      headerStaticData: {},
      line_char_data: {},
      raddar_chart: null,
      pie_char: {},
      roles: ''

    }
  },
  methods: {
    extractUrls(text) {
      if (!text) {
        return ""
      }
      // const urlPattern = /\b((https?:\/\/|www\.)[^\s]+\.[^\s]{2,})\b/g;
      const urlPattern = /((https?:\/\/|www\.)[a-zA-Z0-9-]+(\.[a-zA-Z]{2,})+([\/?&=#.]?[^\s<]*)?)/g;
      let NormalUrl = text.match(urlPattern) || [];
      if (NormalUrl[0] && !NormalUrl[0].startsWith('http://') && !NormalUrl[0].startsWith('https://')) {
        return `https://${NormalUrl[0]}`
      }
      return NormalUrl[0]
    },
    async initData() {

      let listNodeRes = await listNotice();
      this.noticeList = listNodeRes.rows;
      this.total = listNodeRes.total;
      this.loading = false;
    },
    async init() {
      let getStatisticDataRes = await getStatisticData();
      this.headerStaticData = getStatisticDataRes.data
      let statisticsMajorRes = await statisticsMajor();
      this.line_char_data = statisticsMajorRes.data

      let statisticsAdmissionSchoolRes = await statisticsAdmissionSchool();
      // let temp_pie_arr = []
      // Object.keys(statisticsAdmissionSchoolRes.data).forEach((item) => {
      //   let res = {}
      //   res.name = item
      //   res.value = statisticsAdmissionSchoolRes.data[item]
      //   temp_pie_arr.push(res)
      // })

      this.pie_char = statisticsAdmissionSchoolRes.data
    },
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
