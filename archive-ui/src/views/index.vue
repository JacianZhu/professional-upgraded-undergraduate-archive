<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" :headerStaticData="headerStaticData"/>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="line_char_data"/>
    </el-row>

    <el-row :gutter="32">
<!--      <el-col :xs="24" :sm="24" :lg="8">-->
      <!--        <div class="chart-wrapper">-->
      <!--          <raddar-chart :line_char_data="raddar_chart"/>-->
      <!--        </div>-->
      <!--      </el-col>-->
      <el-col :xs="24" :sm="24" :lg="24">
        <div class="chart-wrapper">
          <pie-chart :pie_char="pie_char"/>
        </div>
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
import {getStatisticData, statisticsAdmissionSchool, statisticsMajor} from "@/api/dashboard";

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
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  created() {
    this.init()
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      headerStaticData: {},
      line_char_data: {},
      raddar_chart: null,
      pie_char: null
    }
  },
  methods: {
    async init() {
      let getStatisticDataRes = await getStatisticData();
      this.headerStaticData = getStatisticDataRes.data
      let statisticsMajorRes = await statisticsMajor();
      this.line_char_data = statisticsMajorRes.data

      let statisticsAdmissionSchoolRes = await statisticsAdmissionSchool();
      let temp_pie_arr = []
      Object.keys(statisticsAdmissionSchoolRes.data).forEach((item) => {
        let res = {}
        res.name = item
        res.value = statisticsAdmissionSchoolRes.data[item]
        temp_pie_arr.push(res)
      })
      this.pie_char = temp_pie_arr
      console.log(this.pie_char)
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
