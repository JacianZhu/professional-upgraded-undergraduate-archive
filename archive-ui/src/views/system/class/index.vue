<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="班级名称" prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入班级名称"
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
          v-hasPermi="['system:class:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:class:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:class:remove']"
        >删除
        </el-button>
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
          v-hasPermi="['system:class:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="classList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="班级编号" align="center" prop="classId"/>
      <el-table-column label="班级名称" align="center" prop="className"/>
      <el-table-column label="专业" align="center" prop="specialtyName"/>
      <el-table-column label="班主任" align="center" prop="headTeacherName"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:class:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:class:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改班级管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="班级名称" prop="className">
          <el-input v-model="form.className" placeholder="请输入班级名称"/>
        </el-form-item>
        <el-form-item label="老师" prop="className">
          <el-select style="width: 100%" v-model="form.headTeacherId" placeholder="请选择">
            <el-option
              v-for="item in teacher_list"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="专业" prop="className">
          <el-select style="width: 100%" v-model="form.specialtyId" placeholder="请选择">
            <el-option
              v-for="item in profession_list"
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
import {
  listClass,
  getClass,
  delClass,
  addClass,
  updateClass,
  getSpecialtyList,
  getHeadTeacherList
} from "@/api/system/class";
import ImportFile from "@/views/components/ImportFile.vue";
import teacher from "@/views/system/teacher/index.vue";
import item from "@/layout/components/Sidebar/Item.vue";
import {getToken} from "@/utils/auth";

export default {
  name: "Class",
  computed: {
    teacher() {
      return teacher
    }
  },
  components: {
    ImportFile
  },
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
        url: process.env.VUE_APP_BASE_API + "/system/class/import",
        download_url:"system/user/importTemplate"
      },
      teacher_list: [],
      profession_list: [],
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
      // 班级管理表格数据
      classList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        className: null,
        specialtyId: null,
        headTeacherId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {

      this.upload.title = "班级导入";
      this.upload.open = true;
    },
    // 提交上传文件
    submitFileForm() {
      this.upload.open = false
      this.getList()
      // this.$refs.upload.submit();
    },
    /** 查询班级管理列表 */
    getList() {
      this.loading = true;
      listClass(this.queryParams).then(response => {
        this.classList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        classId: null,
        className: null,
        specialtyId: null,
        headTeacherId: null,
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
      this.ids = selection.map(item => item.classId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // todo 有问题，细节再说

    get_teacher_and_profession_list() {
      return Promise.all([getSpecialtyList(), getHeadTeacherList()]).then(([res1, res2]) => {
        this.profession_list = res1.data.map((item) => {
          return {
            value: item.specialtyId,
            label: item.specialtyName,
          }
        })
        this.teacher_list = res2.data.map(item => {
          return {
            value: item.headTeacherId,
            label: item.headTeacherName
          }
        })
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.get_teacher_and_profession_list()
      this.title = "添加班级管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const classId = row.classId || this.ids
      this.get_teacher_and_profession_list().then(res => {
        getClass(classId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改班级管理";
        });
      })

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let dat = JSON.parse(JSON.stringify(this.form))
          this.profession_list.map((item) => {
            console.log(item)
            if (item.value === dat.specialtyId) {
              dat.specialtyName = item.label
            }
          })
          this.teacher_list.map((item) => {
            if (item.value === dat.headTeacherId) {
              dat.headTeacherName = item.label
            }
          })
          if (this.form.classId != null) {
            updateClass(dat).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {

            addClass(dat).then(response => {
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
      const classIds = row.classId || this.ids;
      this.$modal.confirm('是否确认删除班级管理编号为"' + classIds + '"的数据项？').then(function () {
        return delClass(classIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/class/export', {
        ...this.queryParams
      }, `class_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
