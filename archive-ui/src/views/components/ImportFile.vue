<template>
  <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
    <el-upload
      ref="upload"
      :limit="1"
      accept=".xlsx, .xls"
      :headers="upload.headers"
      :action="upload.url"
      :disabled="upload.isUploading"
      :on-progress="handleFileUploadProgress"
      :on-success="handleFileSuccess"
      :auto-upload="false"
      drag
    >
      <!--    + '?updateSupport=' + upload.updateSupport"-->
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip text-center" slot="tip">
        <!--        <div class="el-upload__tip" slot="tip">-->
        <!--          <el-checkbox v-model="upload.updateSupport"/>-->
        <!--          是否更新已经存在的用户数据-->
        <!--        </div>-->
        <span>仅允许导入xls、xlsx格式文件。</span>
        <!--        <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"-->
        <!--                 @click="importTemplate">下载模板-->
        <!--        </el-link>-->
      </div>
    </el-upload>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitFileForm">确 定</el-button>
      <el-button @click="upload.open = false">取 消</el-button>
    </div>
  </el-dialog>

</template>

<script>
export default {
  name: "ImportFile",
  props: {
    upload: {
      type: Object,
    }
  },
  methods: {
    importTemplate() {
      this.download(this.upload.download_url, {}, `user_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("" +
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.$emit("submmit_after")

    },
    // 提交上传文件
    submitFileForm() {
      // this.upload.open = false
      this.$refs.upload.submit();
    },
  }
}
</script>

<style scoped>

</style>
