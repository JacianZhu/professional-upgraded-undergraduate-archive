<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['system:archiveInfo:add']"
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
          v-hasPermi="['system:archiveInfo:edit']"
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
          v-hasPermi="['system:archiveInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:archiveInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="archiveInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="档案编号" align="center" prop="archiveId"/>
      <el-table-column label="学生姓名" align="center" prop="studentName"/>
      <el-table-column label="学生性别" align="center" prop="studentGender">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.studentGender"/>
        </template>
      </el-table-column>
      <el-table-column label="学生出生日期" align="center" prop="studentDateOfBirth" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.studentDateOfBirth, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="学生身份证号" align="center" prop="studentNationalId"/>
      <el-table-column label="毕业院校" align="center" prop="graduationSchool"/>
      <el-table-column label="毕业专业" align="center" prop="graduationMajor"/>
      <el-table-column label="接收方式" align="center" prop="receiveMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_receive_type" :value="scope.row.receiveMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="邮寄单号" align="center" prop="trackingNumber"/>
      <el-table-column label="接收日期" align="center" prop="receiveDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receiveDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮寄人" align="center" prop="sender"/>
      <el-table-column label="是否移交班主任" align="center" prop="handedToTeacher">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.handedToTeacher"/>
        </template>
      </el-table-column>
      <el-table-column label="是否拆封" align="center" prop="opened">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.opened"/>
        </template>
      </el-table-column>
      <el-table-column label="移交方式" align="center" prop="transferMethod">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_transfer_type" :value="scope.row.transferMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="移交日期" align="center" prop="transferDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.transferDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="移交人" align="center" prop="transferPerson"/>
      <el-table-column label="档案接收人" align="center" prop="recipient"/>
      <el-table-column label="联系电话" align="center" prop="contactPhone"/>
      <el-table-column label="邮寄地址" align="center" prop="mailingAddress"/>
      <el-table-column label="档案是否完整" align="center" prop="archiveComplete">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.archiveComplete"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks"/>
      <el-table-column label="档案状态" align="center" prop="archiveStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_archive_status" :value="scope.row.archiveStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:archiveInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:archiveInfo:remove']"
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

    <!-- 添加或修改档案信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名"/>
        </el-form-item>
        <el-form-item label="学生性别" prop="studentGender">
          <el-select v-model="form.studentGender" placeholder="请选择学生性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学生出生日期" prop="studentDateOfBirth">
          <el-date-picker clearable
                          v-model="form.studentDateOfBirth"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择学生出生日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="学生身份证号" prop="studentNationalId">
          <el-input v-model="form.studentNationalId" placeholder="请输入学生身份证号"/>
        </el-form-item>
        <el-form-item label="毕业院校" prop="graduationSchool">
          <el-input v-model="form.graduationSchool" placeholder="请输入毕业院校"/>
        </el-form-item>
        <el-form-item label="毕业专业" prop="graduationMajor">
          <el-input v-model="form.graduationMajor" placeholder="请输入毕业专业"/>
        </el-form-item>
        <el-form-item label="接收方式" prop="receiveMethod">
          <el-select v-model="form.receiveMethod" placeholder="请选择接收方式" clearable>
            <el-option
              v-for="dict in dict.type.sys_receive_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <template v-if="form.receiveMethod=='mail'">
          <el-form-item label="邮寄单号" prop="trackingNumber">
            <el-input v-model="form.trackingNumber" placeholder="请输入邮寄单号"/>
          </el-form-item>
          <el-form-item label="接收日期" prop="receiveDate" v-if="form.receiveMethod=='mail'">
            <el-date-picker clearable
                            v-model="form.receiveDate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择接收日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="邮寄人" prop="sender" v-if="form.receiveMethod=='mail'">
            <el-input v-model="form.sender" placeholder="请输入邮寄人"/>
          </el-form-item>
        </template>
        <template v-if="form.receiveMethod=='byo'">
          <el-form-item label="是否移交班主任" prop="handedToTeacher" v-if="form.receiveMethod=='byo'">
            <el-select v-model="form.handedToTeacher" placeholder="请选择是否移交班主任">
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否拆封" prop="opened" v-if="form.receiveMethod=='byo'">
            <el-select v-model="form.opened" placeholder="请选择是否拆封">
              <el-option
                v-for="dict in dict.type.sys_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </template>
        <el-form-item label="移交方式" prop="transferMethod">
          <el-select v-model="form.transferMethod" placeholder="请选择移交方式" clearable>
            <el-option
              v-for="dict in dict.type.sys_transfer_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <template v-if="form.transferMethod=='pickup'">
          <el-form-item label="移交日期" prop="transferDate" v-if="form.transferMethod=='pickup'">
            <el-date-picker clearable
                            v-model="form.transferDate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择移交日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="移交人" prop="transferPerson" v-if="form.transferMethod=='pickup'">
            <el-input v-model="form.transferPerson" placeholder="请输入移交人"/>
          </el-form-item>
        </template>
        <template v-if="form.transferMethod=='email'">
          <el-form-item label="档案接收人" prop="recipient">
            <el-input v-model="form.recipient" placeholder="请输入档案接收人"/>
          </el-form-item>
          <el-form-item label="联系电话" prop="contactPhone">
            <el-input v-model="form.contactPhone" placeholder="请输入联系电话"/>
          </el-form-item>
          <el-form-item label="邮寄地址" prop="mailingAddress" v-if="form.transferMethod=='email'">
            <el-input v-model="form.mailingAddress" placeholder="请输入邮寄地址"/>
          </el-form-item>
        </template>


        <el-form-item label="档案是否完整" prop="archiveComplete">
          <el-select v-model="form.archiveComplete" placeholder="请选择档案是否完整" clearable>
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="档案状态" prop="archiveStatus">
          <el-select v-model="form.archiveStatus" placeholder="请选择档案状态">
            <el-option
              v-for="dict in dict.type.sys_archive_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listArchiveInfo,
  getArchiveInfo,
  delArchiveInfo,
  addArchiveInfo,
  updateArchiveInfo
} from "@/api/system/archiveInfo";

export default {
  name: "ArchiveInfo",
  dicts: ['sys_receive_type', 'sys_yes_no', 'sys_archive_status', 'sys_user_sex', 'sys_transfer_type'],
  data() {
    return {
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
      // 档案信息表格数据
      archiveInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
    /** 查询档案信息列表 */
    getList() {
      this.loading = true;
      listArchiveInfo(this.queryParams).then(response => {
        this.archiveInfoList = response.rows;
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
        archiveId: null,
        studentName: null,
        studentGender: null,
        studentDateOfBirth: null,
        studentNationalId: null,
        graduationSchool: null,
        graduationMajor: null,
        receiveMethod: null,
        trackingNumber: null,
        receiveDate: null,
        sender: null,
        handedToTeacher: null,
        opened: null,
        transferMethod: null,
        transferDate: null,
        transferPerson: null,
        recipient: null,
        contactPhone: null,
        mailingAddress: null,
        archiveComplete: null,
        remarks: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        archiveStatus: null
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
      this.ids = selection.map(item => item.archiveId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加档案信息";
      console.log(this.dict.type.sys_receive_type);
      console.log(this.dict.type.sys_transfer_type);
      console.log(this.dict.type.sys_yes_no);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const archiveId = row.archiveId || this.ids
      getArchiveInfo(archiveId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改档案信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.archiveId != null) {
            updateArchiveInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addArchiveInfo(this.form).then(response => {
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
      const archiveIds = row.archiveId || this.ids;
      this.$modal.confirm('是否确认删除档案信息编号为"' + archiveIds + '"的数据项？').then(function () {
        return delArchiveInfo(archiveIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/archiveInfo/export', {
        ...this.queryParams
      }, `archiveInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
