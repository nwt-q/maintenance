<template>
    <div class="main">
        <el-dialog :visible.sync="isCreateDialogShow" width="45%" center>
            <div slot="title" class="dialog-title">
                编辑
            </div>
            <el-form :model="createObj" status-icon label-width="150px" class="demo-ruleForm" style="font-size: 10px">
                <el-form-item label="维修类别 :">
                    <el-select style="width: 60%" clearable v-model="createObj.categoryId"  placeholder="选择类别">
                        <el-option v-for="item in tableList2" :key="item.id" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="维修人 :">
                    <el-select style="width: 60%" clearable v-model="createObj.maintenanceId"  placeholder="选择维修人">
                        <el-option v-for="item in tableList1" :key="item.id" :label="item.username" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div style="display: flex;align-items: center;">
                <el-button  style="margin: auto; margin-top: 2vh; width: 50%" type="primary" @click="handleOk">确认提交</el-button>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="isDetailDialogShow" width="45%" center>
            <div slot="title" class="dialog-title">
                维修记录
            </div>
            <el-form :model="detailObj" status-icon label-width="150px" class="demo-ruleForm" style="font-size: 10px">
                <el-form-item label="故障原因 :">
                    <el-input clearable v-model="detailObj.cause" style="width: 85%"></el-input>
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
                        <el-input clearable size="mini" placeholder="设备名称" suffix-icon="el-icon-search"
                            v-model="searchCondition.equipmentName" @change="getTableData"></el-input>
                    </el-col>
                    <el-col :span="3" :offset="0">
                        <el-select style="width: 100%" clearable v-model="searchCondition.categoryName"  placeholder="选择类别" @change="getTableData">
                            <el-option v-for="item in tableList2" :key="item.id" :label="item.name" :value="item.name"></el-option>
                        </el-select>
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
                    <el-table-column label="设备名称" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.equipmentName }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="图片" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <img :src="'http://127.0.0.1:18082/local2Url?name=' + scope.row.pic" style="width: 100px; height: 100px;">
                        </template>
                    </el-table-column>
                    <el-table-column label="维修类别" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-if="scope.row.categoryName">{{ scope.row.categoryName }}</span>
                            <span v-else>待处理</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="维修人" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-if="scope.row.maintenanceName">{{ scope.row.maintenanceName }}</span>
                            <span v-else>待添加</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="维修记录" min-width="10" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-if="scope.row.cause">{{ scope.row.cause }}</span>
                            <span v-else>待添加</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作" min-width="18">
                        <template slot-scope="scope">
                            <el-button v-if="!scope.row.cause" size="mini" type="primary" @click="handleEdit(scope.row)" icon="el-icon-edit">发起维修</el-button>
                            <el-button v-else size="mini" type="info" icon="el-icon-edit" disabled>发起维修</el-button>
                            <el-button size="mini" type="primary" @click="handleDetail(scope.row)" icon="el-icon-tickets">维修记录</el-button>
                            <el-button v-if="scope.row.maintenanceId && !scope.row.cause" size="mini" type="success" @click="handle(scope.row)" icon="el-icon-phone">催办</el-button>
                            <el-button v-else disabled size="mini" type="info" icon="el-icon-phone">催办</el-button>
                            <el-button size="mini" type="danger" @click="handleDelete(scope.row)" icon="el-icon-delete">删除</el-button>
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
                equipmentName: '',
                categoryName: ''

            },
            tableList: [],
            tableList1: [],
            tableList2: [],
			detailObj: {},
			createObj: {},
			isDetailDialogShow: false,
			isCreateDialogShow: false
        };
    },
    methods: {
        getTableData() {
            this.$http.viewRecordTable(this.searchCondition.equipmentName,this.searchCondition.categoryName).then(res => {
                this.tableList = res.data;
            })

        },
        handleDelete(row){
            let message = "将<span style='color: orange;font-size:15px'> [永久删除] </span>此条数据，是否继续？"
            this.$hint.confirm(message).then(res => {
                if (res) {
                    this.$http.deleteRecord(row.id).then(res => {
                        if (res.code === 200) {
                            this.getTableData();
                        }
                    })
                }
            })
        },

        handle(row){
            let message = "确认发送<span style='color: orange;font-size:15px'> [催办函] </span>，是否继续？"
            this.$hint.confirm(message).then(res => {
                if (res) {
                    this.$http.sendTextMailMessage(row.id).then(res => {
                        if (res.code === 200) {
                            this.getTableData();
                        }
                    })
                }
            })
        },

        handleEdit(row) {
            let that = this;
            that.createObj = row;

            that.$http.viewMaintenanceByEquipment2(this.createObj.equipmentId).then(res => {
                that.tableList1 = res.data;
            });

            that.isCreateDialogShow = true;
        },

        handleDetail(row) {
            this.detailObj = row;
            this.isDetailDialogShow = true;
        },


        handleCreate() {
            this.$http.updateRecord2(this.detailObj).then(res => {
                if (res.code === 200) {
                    this.isDetailDialogShow = false;
                    this.getTableData();
                }
            })
        },

        handleOk() {
            this.$http.updateRecord(this.createObj).then(res => {
                if (res.code === 200) {
                    this.isCreateDialogShow = false;
                    this.getTableData();
                }
            })
        },

    },
    created() {
        this.getTableData();
        this.$http.viewCategoryTable(this.searchCondition.name).then(res => {
            this.tableList2 = res.data;
        })
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