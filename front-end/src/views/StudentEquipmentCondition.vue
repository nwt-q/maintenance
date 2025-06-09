<template>
    <div class="main">
        <el-dialog :visible.sync="isCreateDialogShow" width="45%" center>
            <div slot="title" class="dialog-title">
                故障报修
            </div>
            <el-form :model="createObj" status-icon label-width="150px" class="demo-ruleForm" style="font-size: 10px">
                <el-form-item label="故障图片 :">
                    <el-upload class="avatar-uploader" action="" :show-file-list="false" :before-upload="beforeUpload" :http-request="onUpload">
                        <img v-if="createObj.pic != null && createObj.pic !== ''" :src="'http://127.0.0.1:18082/local2Url?name=' + createObj.pic"
                             class="avatar"/>
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div style="display: flex;align-items: center;">
                <el-button  style="margin: auto; margin-top: 2vh; width: 50%" type="primary" @click="handleCreate">确认提交</el-button>
            </div>
        </el-dialog>
        <div class="container">
            <div class="search">
                <el-row :gutter="20" style="margin-top: 20px;">

                    <el-col :span="3" :offset="2">
                        <el-input clearable size="mini" placeholder="设备名" suffix-icon="el-icon-search"
                            v-model="searchCondition.name" @change="getTableData"></el-input>
                    </el-col>

                </el-row>
            </div>
            <div class="table">
                <el-table :data="tableList" style="width: 95%" height="100%" max-height="100%" empty-text="无匹配行"
                    :cell-style="{ padding: '5px' }">
                    <el-table-column label="序号" min-width="3" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.$index + 1 }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="设备名" min-width="10" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.name }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="设备状态信息" min-width="10" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.type }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="位置" min-width="10" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.location }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" min-width="10" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.status }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" min-width="15">
                        <template slot-scope="scope">
                            <el-button size="mini" type="warning" @click="handleEdit(scope.row)" icon="el-icon-phone-outline">故障报修</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="bottom">
            </div>
        </div>
    </div>
</template>
<script>


export default {
    data() {
        return {
            searchCondition: {
                name: '',
                type: '',
                status: ''

            },
            tableList: [],
			detailObj: {},
			createObj: {
                pic:''
            },
			isDetailDialogShow: false,
			isCreateDialogShow: false
        };
    },
    methods: {
        getTableData() {
            this.$http.viewEquipmentTable(this.searchCondition.name,this.searchCondition.type,this.searchCondition.status).then(res => {
                this.tableList = res.data;
            })

        },

        handleEdit(row) {
            this.createObj.equipmentId = row.id;
            this.createObj.equipmentName = row.name;
            this.createObj.userId = this.$store.state.currentUser.id;
            this.isCreateDialogShow = true;
        },

        beforeUpload(file) {
            const isJPG = file.type === "image/jpeg" || file.type === "image/png";
            if (!isJPG) {
                this.$message.error("图片只能是 JPG 或 PNG 格式!");
            }
            return isJPG;
        },

        // 上传
        onUpload(e) {
            let that = this;
            let file = e.file;
            that.$http.uploadFile(file).then(res => {
                if (res.code === 200) {
                    that.createObj.pic = res.data.e1;
                    console.log(res.data.e1)
                }
            });
        },
        handleDelete(row){
            let message = "<span style='color: orange;font-size:15px'> [确认报修] </span>，是否继续？"
            this.$hint.confirm(message).then(res => {
                if (res) {
                    this.createObj.equipmentId = row.id;
                    this.createObj.equipmentName = row.name;
                    this.createObj.userId = this.$store.state.currentUser.id;
                    this.$http.createRecord(this.createObj).then(res => {
                        if (res.code === 200) {
                            this.isCreateDialogShow = false;
                            this.getTableData();
                        }
                    })
                }
            })
        },

        handleDetail(row) {
            this.detailObj = row;
            this.isDetailDialogShow = true;
        },


        handleCreate() {
            this.$http.createRecord(this.createObj).then(res => {
                if (res.code === 200) {
                    this.isCreateDialogShow = false;
                    this.getTableData();
                }
            })
        },

    },
    created() {
        this.getTableData();
    }
};
</script>
<style scoped>
    /deep/ .el-select-dropdown__item span {
        font-size: 12px;
    }

    /deep/ .el-form-item__label {
        font-size: 12px;
        letter-spacing: 1px;
    }

    /deep/ .el-input__inner {
        height: 34px;
    }

    /deep/ .el-form-item {
        margin-bottom: 25px;
    }

    /deep/ .el-input__inner {
        font-size: 12px;
    }

    .main {
        height: 100%;
        /* border: 2px solid red; */
        background-color: rgb(255,255,255);
    }

    .search {
        flex: 2;
    }

    .container {
        height: 100%;
        /* border: 1px solid red; */
        display: flex;
        width: 100%;
        flex-direction: column;
    }

    ::v-deep ::-webkit-scrollbar {
        width: 0;
        height: 0;
    }

    .table {
        flex: 15;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        width: 98%;
        margin: auto;
    }

    /deep/ .el-table {
        background-color: rgba(243, 245, 237, 0) !important;
        font-size: 12px;
        font-weight: 400;
        letter-spacing: 1px;
        margin: auto;
    }

    /deep/ .el-table tr {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-table th {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-tooltip__popper {
        border: 1px solid red;
    }

    .bottom {
        flex: 0.5;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    /deep/ .el-pagination {
        background-color: rgba(243, 245, 237, 0) !important;
        margin: auto;
    }

    /deep/ .el-pagination ul {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-pagination li {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    /deep/ .el-pagination button {
        background-color: rgba(243, 245, 237, 0) !important;
    }

    .avatar-uploader-icon {
        border: 1px dashed rgb(170, 163, 163);
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader-icon:hover {
        border-color: #409eff;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 100px;
        height: 100px;
        line-height: 100px;
        text-align: center;
    }

    .avatar {
        width: 100px;
        height: 100px;
        display: block;
        border-radius: 5px;
    }
</style>