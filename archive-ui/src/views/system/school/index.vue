<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="毕业院校名称" prop="graduateSchoolName">
        <el-input
          v-model="queryParams.graduateSchoolName"
          placeholder="请输入毕业院校名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="专科专业名称" prop="specialtyName">
        <el-input
          v-model="queryParams.specialtyName"
          placeholder="请输入专科专业名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:school:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:school:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:school:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['system:user:import']"
        >导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:school:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="schoolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="毕业院校编号" align="center" prop="graduateSchoolId" />
      <el-table-column label="毕业院校名称" align="center" prop="graduateSchoolName" />
      <el-table-column label="专科专业名称" align="center" prop="specialtyName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:school:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:school:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改毕业院校管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="毕业院校名称" prop="graduateSchoolName">
          <el-input v-model="form.graduateSchoolName" placeholder="请输入毕业院校名称" />
        </el-form-item>
        <el-form-item label="专科专业名称" prop="specialtyName">
          <el-select v-model="form.specialtyName" placeholder="请选择专科专业名称" style="width: 100%">
            <el-option
              v-for="item in specialtyList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <import-file :upload="upload" ref="user_import" @submmit_after="submitFileForm"></import-file>

  </div>
</template>

<script>
import { listSchool, getSchool, delSchool, addSchool, updateSchool } from "@/api/system/school";
import {
  listClass,
  getClass,
  delClass,
  addClass,
  updateClass,
  getSpecialtyList,
  getHeadTeacherList
} from "@/api/system/class";
import {getToken} from "@/utils/auth";
import ImportFile from "@/views/components/ImportFile.vue";

export default {
  name: "School",
  components: {ImportFile},
  data() {
    return {
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/school/import",
        download_url:"system/user/importTemplate"
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 毕业院校管理表格数据
      schoolList: [],
      specialtyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        graduateSchoolName: null,
        specialtyName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        graduateSchoolName: [
          { required: true, message: "毕业院校名称不能为空", trigger: "blur" }
        ],
        specialtyName: [
          { required: true, message: "专科专业名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getSpecialtyList();
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "毕业院校导入";
      this.upload.open = true;
    },
    // 提交上传文件
    submitFileForm() {
      this.upload.open = false
      this.getList()
      // this.$refs.upload.submit();
    },
    /** 查询毕业院校管理列表 */
    getList() {
      this.loading = true;
      listSchool(this.queryParams).then(response => {
        this.schoolList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getSpecialtyList() {
      getSpecialtyList().then(response => {
        this.specialtyList = response.data.map(item => ({
          value: item.specialtyName,
          label: item.specialtyName
        }));
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        graduateSchoolId: null,
        graduateSchoolName: null,
        specialtyName: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.graduateSchoolId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加毕业院校管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const graduateSchoolId = row.graduateSchoolId || this.ids
      getSchool(graduateSchoolId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改毕业院校管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.graduateSchoolId != null) {
            updateSchool(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSchool(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const graduateSchoolIds = row.graduateSchoolId || this.ids;
      this.$modal.confirm('是否确认删除毕业院校管理编号为"' + graduateSchoolIds + '"的数据项？').then(function() {
        return delSchool(graduateSchoolIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/school/export', {
        ...this.queryParams
      }, `school_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
