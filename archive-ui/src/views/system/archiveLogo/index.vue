<template>
    <div class="app-container">
        <el-descriptions class="margin-top" title="个人档案" :column="3" size="medium" border>
            <el-descriptions-item>
                <template slot="label">
                    学生姓名
                </template>
                {{ list.studentName }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    学生性别
                </template>
                {{ list.studentGender }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    学生出生日期
                </template>
                {{ list.studentDateOfBirth }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    学生身份证号
                </template>
                {{ list.studentNationalId }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    毕业院校
                </template>
                {{ list.graduationSchool }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    毕业专业
                </template>
                {{ list.graduationMajor }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    接收方式
                </template>
                {{ list.receiveMethod }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    邮寄单号
                </template>
                {{ list.trackingNumber }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    接收日期
                </template>
                {{ list.receiveDate }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    邮寄人
                </template>
                {{ list.sender }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    是否移交班主任
                </template>
                {{ list.handedToTeacher }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    是否拆封
                </template>
                {{ list.opened }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    移交方式
                </template>
                {{ list.transferMethod }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    移交日期
                </template>
                {{ list.transferDate }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    移交人
                </template>
                {{ list.transferPerson }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    档案接收人
                </template>
                {{ list.recipient }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    联系电话
                </template>
                {{ list.contactPhone }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    邮寄地址
                </template>
                {{ list.mailingAddress }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    档案是否完整
                </template>
                {{ list.archiveComplete }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    备注
                </template>
                {{ list.remarks }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    档案状态
                </template>
                {{ list.archiveStatus }}
            </el-descriptions-item>
        </el-descriptions>
    </div>
</template>

<script>
import { selectSysArchiveByName } from "@/api/system/archiveLogo"

export default {
    name: "ArchiveLogo",
    data() {
        return {
            list: {} //个人档案
        };
    },
    created() {
        this.addSelectSysArchiveByName()
    },

    methods: {
        // 获取单个档案
        addSelectSysArchiveByName() {
            let username = this.$store.state.user.name;
            selectSysArchiveByName(username).then(response => {
                this.list = response
                console.log(this.list);

            });
        }
    }
};
</script>