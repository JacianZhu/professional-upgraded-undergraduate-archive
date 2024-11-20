<template>
  <el-row :gutter="40" class="panel-group">
    <el-col style="margin-bottom: 32px" :span="24">
      <div class="card-panel">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            姓名:{{ nickName }}
          </div>
          <div class="card-panel-text" style="flex: 1;display: flex;align-items: end; font-size: 12px">
            角色: {{ roles[0] }}
          </div>
          <div class="card-panel-text" style="flex: 1;display: flex;align-items: end; font-size: 12px">
            上一次登录时间: {{ formatterTimer(date) }}
          </div>
        </div>
      </div>
    </el-col>
    <!--    <el-col :span="24">-->
    <!--      <el-table size="mini" v-loading="loading"-->
    <!--                height="275px"-->
    <!--                :data="noticeList"-->
    <!--                header-row-class-name="header_color"-->
    <!--                :header-row-style="{backgroundColor: '#42b983'}"-->
    <!--      >-->
    <!--        <el-table-column label="序号" align="center" prop="noticeId" width="100"/>-->
    <!--        <el-table-column-->
    <!--          label="公告标题"-->
    <!--          align="center"-->
    <!--          prop="noticeTitle"-->
    <!--          :show-overflow-tooltip="true"-->
    <!--        >-->

    <!--          <template #default="{ row }">-->
    <!--            &lt;!&ndash; 检查 noticeTitle 是否是链接 &ndash;&gt;-->
    <!--            <span v-if="row.noticeType=='2'">-->
    <!--      &lt;!&ndash; 如果是链接，显示为 a 标签并添加跳转 &ndash;&gt;-->
    <!--              <el-link type="primary">-->

    <!--      <a :href="extractUrls (row.noticeContent)" target="_blank">{{ row.noticeTitle }}</a>-->
    <!--              </el-link>-->
    <!--    </span>-->
    <!--            <span v-else>-->
    <!--      &lt;!&ndash; 否则显示普通文本 &ndash;&gt;-->
    <!--      {{ row.noticeTitle }}-->
    <!--    </span>-->
    <!--          </template>-->
    <!--        </el-table-column>-->

    <!--      </el-table>-->

    <!--      <pagination-->
    <!--        style="position: relative; margin-top: 0;height: 40px;line-height: 40px;display: flex;justify-content: center;align-items: center"-->
    <!--        small-->
    <!--        v-show="total>0"-->
    <!--        :total="total"-->
    <!--        :page.sync="queryParams.pageNum"-->
    <!--        :limit.sync="queryParams.pageSize"-->
    <!--        @pagination="initData"-->
    <!--      />-->

    <!--    </el-col>-->
  </el-row>
</template>

<script>
import CountTo from "vue-count-to";
import { mapGetters } from "vuex";
import { listNotice } from "@/api/system/notice";
import role from "@/views/system/role/index.vue";

export default {
  name: "personalInfo",
  components: { CountTo },
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
    }
  },
  computed: {
    ...mapGetters([
      'avatar',
      "name",
      "roles",
      "date",
      "nickName"
    ]),
  },
  created() {
    this.initData()
  },

  methods: {

    async initData() {

      let listNodeRes = await listNotice();
      this.noticeList = listNodeRes.rows;
      this.total = listNodeRes.total;
      this.loading = false;
    },

    formatterTimer(dateStr) {
      // const dateStr = "2024-11-10T21:57:22.000+08:00";
      const date = new Date(dateStr);

      // 格式化为 "YYYY-MM-DD HH:mm:ss" 格式
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始，所以加1
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');

      const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      return formattedDate
    },
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
    isLink(text) {
      const urlPattern = new RegExp(
        '^(https?:\\/\\/)?' + // 协议
        '((([a-zA-Z0-9$-_@.&+!*\'(),])+\\.)+[a-zA-Z]{2,})' + // 域名
        '(\\/[a-zA-Z0-9$-_@.&+!*\'(),]*)*$', 'i'
      );
      return urlPattern.test(text);
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep .header_color {
  background-color: #42b983 !important;
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
    position: relative;
  }


  .card-panel {
    //height: 100%;
    height: 450px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    display: flex;
    align-items: center;
    justify-content: center;

    .avatar-wrapper {
      width: 120px;
      display: flex;
      justify-content: flex-end;

      .user-avatar {

        cursor: pointer;
        width: 80px;
        height: 80px;
        clip-path: circle(50%);
        border-radius: 10px;
      }

    }

    .card-panel-description {
      display: flex;
      flex-direction: column;
      flex: 1;
      justify-content: space-around;
      align-items: center;
    }

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
