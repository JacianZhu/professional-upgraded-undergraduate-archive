<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="idNumber">
        <el-input
          v-model="queryParams.idNumber"
          placeholder="请输入身份证号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学号" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="录取状态" prop="admissionStatus">
        <el-select v-model="queryParams.admissionStatus" placeholder="请选择录取状态" clearable>
          <el-option label="已录取" value="已录取"></el-option>
          <el-option label="待录取" value="待录取"></el-option>
          <el-option label="未录取" value="未录取"></el-option>
        </el-select>
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
          v-hasPermi="['system:admissionInfo:add']"
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
          v-hasPermi="['system:admissionInfo:edit']"
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
          v-hasPermi="['system:admissionInfo:remove']"
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
          v-hasPermi="['system:admissionInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="admissionInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="录取编号" align="center" prop="admissionId" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="身份证号" align="center" prop="idNumber" />
      <el-table-column label="学号" align="center" prop="studentId" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="毕业院校" align="center" prop="graduateSchool" />
      <el-table-column label="专科专业" align="center" prop="diplomaSpecialty" />
      <el-table-column label="毕业时间" align="center" prop="graduationDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.graduationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="录取本科院校" align="center" prop="admittedUniversity" />
      <el-table-column label="本科专业" align="center" prop="admittedSpecialty" />
      <el-table-column label="录取批次" align="center" prop="admissionBatch" />
      <el-table-column label="录取日期" align="center" prop="admissionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.admissionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="录取状态" align="center" prop="admissionStatus" />
      <el-table-column label="联系电话" align="center" prop="contactNumber" />
      <el-table-column label="联系地址" align="center" prop="contactAddress" />
      <el-table-column label="录取类型" align="center" prop="admissionType" />
      <el-table-column label="考生类别" align="center" prop="candidateType" />
      <el-table-column label="准考证号" align="center" prop="examTicketNumber" />
      <el-table-column label="成绩" align="center" prop="examScore" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:admissionInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:admissionInfo:remove']"
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

    <!-- 添加或修改录取信息导入对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <el-input v-model="form.idNumber" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="毕业院校" prop="graduateSchool">
          <el-input v-model="form.graduateSchool" placeholder="请输入毕业院校" />
        </el-form-item>
        <el-form-item label="专科专业" prop="diplomaSpecialty">
          <el-input v-model="form.diplomaSpecialty" placeholder="请输入专科专业" />
        </el-form-item>
        <el-form-item label="毕业时间" prop="graduationDate">
          <el-date-picker clearable
            v-model="form.graduationDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择毕业时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="录取本科院校" prop="admittedUniversity">
          <el-input v-model="form.admittedUniversity" placeholder="请输入录取本科院校" />
        </el-form-item>
        <el-form-item label="本科专业" prop="admittedSpecialty">
          <el-input v-model="form.admittedSpecialty" placeholder="请输入本科专业" />
        </el-form-item>
        <el-form-item label="录取批次" prop="admissionBatch">
          <el-input v-model="form.admissionBatch" placeholder="请输入录取批次" />
        </el-form-item>
        <el-form-item label="录取日期" prop="admissionDate">
          <el-date-picker clearable
            v-model="form.admissionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择录取日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="联系地址" prop="contactAddress">
          <el-input v-model="form.contactAddress" placeholder="请输入联系地址" />
        </el-form-item>
        <el-form-item label="准考证号" prop="examTicketNumber">
          <el-input v-model="form.examTicketNumber" placeholder="请输入准考证号" />
        </el-form-item>
        <el-form-item label="成绩" prop="examScore">
          <el-input v-model="form.examScore" placeholder="请输入成绩" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="录取类型" prop="admissionType">
          <el-select v-model="form.admissionType" placeholder="请选择录取类型">
            <el-option label="统招" value="统招"></el-option>
            <el-option label="普通录取" value="普通录取"></el-option>
            <el-option label="提前录取" value="提前录取"></el-option>
            <el-option label="自主招生" value="自主招生"></el-option>
            <el-option label="保送生" value="保送生"></el-option>
            <el-option label="特长生" value="特长生"></el-option>
            <el-option label="国际生" value="国际生"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="考生类别" prop="candidateType">
          <el-select v-model="form.candidateType" placeholder="请选择考生类别">
            <el-option label="普通考生" value="普通考生"></el-option>
            <el-option label="艺术类考生" value="艺术类考生"></el-option>
            <el-option label="体育类考生" value="体育类考生"></el-option>
            <el-option label="少数民族考生" value="少数民族考生"></el-option>
            <el-option label="自主招生考生" value="自主招生考生"></el-option>
            <el-option label="国际考生" value="国际考生"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="录取状态" prop="admissionStatus">
          <el-select v-model="form.admissionStatus" placeholder="请选择录取状态">
            <el-option label="已录取" value="已录取"></el-option>
            <el-option label="待录取" value="待录取"></el-option>
            <el-option label="未录取" value="未录取"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
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
import { listAdmissionInfo, getAdmissionInfo, delAdmissionInfo, addAdmissionInfo, updateAdmissionInfo } from "@/api/system/admissionInfo";
import {getToken} from "@/utils/auth";
import ImportFile from "@/views/components/ImportFile.vue";
export default {
  name: "AdmissionInfo",
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
        url: process.env.VUE_APP_BASE_API + "/system/admissionInfo/import",
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
      // 录取信息导入表格数据
      admissionInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentName: null,
        idNumber: null,
        studentId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    // 提交上传文件
    submitFileForm() {
      this.upload.open = false
      this.getList()
      // this.$refs.upload.submit();
    },

    /** 查询录取信息导入列表 */
    getList() {
      this.loading = true;
      listAdmissionInfo(this.queryParams).then(response => {
        this.admissionInfoList = response.rows;
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
        admissionId: null,
        studentName: null,
        idNumber: null,
        studentId: null,
        gender: null,
        graduateSchool: null,
        diplomaSpecialty: null,
        graduationDate: null,
        admittedUniversity: null,
        admittedSpecialty: null,
        admissionBatch: null,
        admissionDate: null,
        admissionStatus: null,
        contactNumber: null,
        contactAddress: null,
        admissionType: null,
        candidateType: null,
        examTicketNumber: null,
        examScore: null,
        remarks: null,
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
      this.ids = selection.map(item => item.admissionId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加录取信息导入";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const admissionId = row.admissionId || this.ids
      getAdmissionInfo(admissionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改录取信息导入";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.admissionId != null) {
            updateAdmissionInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAdmissionInfo(this.form).then(response => {
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
      const admissionIds = row.admissionId || this.ids;
      this.$modal.confirm('是否确认删除录取信息导入编号为"' + admissionIds + '"的数据项？').then(function() {
        return delAdmissionInfo(admissionIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/admissionInfo/export', {
        ...this.queryParams
      }, `admissionInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
