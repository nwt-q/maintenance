<template>
    <div class="main">
        <el-dialog :visible.sync="isDialogShow" width="60%" center>
            <div slot="title" class="dialog-title">
                选择"{{detailObj1.name}}"的维修人员
            </div>
            <div>
                <el-table @selection-change="handleSelectionChange" :data="tableList1" style="width: 100%">
                    <el-table-column type="selection"></el-table-column>
                    <el-table-column label="姓名">
                        <template slot-scope="scope">
                            <span>{{scope.row.username}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="联系方式">
                        <template slot-scope="scope">
                            <span>{{scope.row.mobile}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="地址">
                        <template slot-scope="scope">
                            <span>{{scope.row.address}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="邮箱">
                        <template slot-scope="scope">
                            <span>{{ scope.row.mailbox }}</span>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="margin-top: 20px;display: flex;justify-content: center;">
                    <el-button type="primary" size="medium" @click="mergePay">提交选择</el-button>
                </div>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="isDetailDialogShow" width="60%" center>
            <div slot="title" class="dialog-title">
                "{{detailObj.name}}"的维修人员
            </div>
            <div>
                <el-table :data="tableList2" style="width: 100%">
                    <el-table-column label="姓名">
                        <template slot-scope="scope">
                            <span>{{scope.row.username}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="联系方式">
                        <template slot-scope="scope">
                            <span>{{scope.row.mobile}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="地址">
                        <template slot-scope="scope">
                            <span>{{scope.row.address}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="邮箱">
                        <template slot-scope="scope">
                            <span>{{ scope.row.mailbox }}</span>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-dialog>

        <el-dialog :visible.sync="isCreateDialogShow" width="45%" center>
            <div slot="title" class="dialog-title">
                新增<span style="color: red">（{{ this.dialogType }}）</span>
            </div>
            <el-form :model="createObj" status-icon label-width="150px" class="demo-ruleForm" style="font-size: 10px">
                <el-form-item label="设备名 :">
                    <el-input clearable v-model="createObj.name" style="width: 85%"></el-input>
                </el-form-item>
                <el-form-item label="位置 :">
                    <el-input clearable v-model="createObj.location" style="width: 85%"></el-input>
                </el-form-item>
                <el-form-item label="状态 :">
                    <el-select style="width: 60%" clearable v-model="createObj.status"  placeholder="状态">
                        <el-option label="开启" value="开启"></el-option>
                        <el-option label="关闭" value="关闭"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div style="display: flex;align-items: center;">
                <el-button  style="margin: auto; margin-top: 2vh; width: 50%" type="primary" @click="handleCreate">确认提交</el-button>
            </div>
        </el-dialog>

        <div class="container">
            <div class="search">
                <el-row :gutter="20" style="margin-top: 20px;">

                    <el-col :span="2" :offset="1">
                        <el-button size="small" style="width: 100px" type="primary" icon="el-icon-circle-plus-outline"
                                   @click="() => { this.isCreateDialogShow = true;this.dialogType = '新增'  }">新增</el-button>
                    </el-col>
                    <el-col :span="3" :offset="1">
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
                    <el-table-column label="设备名" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.name }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="设备状态信息" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.type }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="创建时间" min-width="6" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                <span>{{ $util.timestamp2Str(scope.row.createTime) }}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" min-width="15">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" @click="handleEdit(scope.row)" icon="el-icon-edit">编辑</el-button>
                            <el-button size="mini" type="danger" @click="handleDelete(scope.row)" icon="el-icon-delete">删除</el-button>
                            <el-button size="mini" type="success" @click="handleEditP(scope.row)" icon="el-icon-edit">编辑维修人员</el-button>
                            <el-button size="mini" type="info" @click="handleDetail(scope.row)" icon="el-icon-view">查看维修人员</el-button>
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
                    name: ''
                },
                tableList: [],
                tableList1: [],
                tableList2: [],
                detailObj: {},
                detailObj1: {},
                createObj: {},
                isDetailDialogShow: false,
                isDialogShow: false,
                isCreateDialogShow: false,
                multipleSelection: [],
            };
        },
        methods: {
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },

            getTableData() {
                this.$http.viewEquipmentTable(this.searchCondition.name).then(res => {
                    this.tableList = res.data;
                })

            },

            handleEdit(row) {
                let that = this;
                that.dialogType = "编辑";
                that.createObj = row;
                that.isCreateDialogShow = true;
            },

            mergePay() {
                this.detailObj1.userIds = '';
                this.multipleSelection.forEach(row => {
                    this.detailObj1.userIds += row.id + ','
                });
                this.$http.updateEquipmentDetail(this.detailObj1).then(res => {
                    if (res.code === 200) {
                        this.isDialogShow = false;
                        this.getTableData();
                    }
                })
            },

            handleEditP(row) {
                let that = this;
                that.$http.viewMaintenanceTable().then(res => {
                    this.tableList1 = res.data;
                });
                that.detailObj1 = row;
                that.isDialogShow = true;
            },

            handleDelete(row){
                let message = "将<span style='color: orange;font-size:15px'> [永久删除] </span>此条数据，是否继续？"
                this.$hint.confirm(message).then(res => {
                    if (res) {
                        this.$http.deleteEquipment(row.id,this.$store.state.currentUser.id).then(res => {
                            if (res.code === 200) {
                                this.getTableData();
                            }
                        })
                    }
                })
            },

            handleDetail(row) {
                this.detailObj = row;
                this.$http.viewMaintenanceByEquipment(this.detailObj.userIds).then(res => {
                    this.tableList2 = res.data;
                });

                this.isDetailDialogShow = true;
            },


            handleCreate() {
                this.createObj.type = "机器状态良好";
                this.createObj.userId = this.$store.state.currentUser.id;
                if (this.dialogType === '新增') {
                    this.$http.createEquipment(this.createObj).then(res => {
                        if (res.code === 200) {
                            this.isCreateDialogShow = false;
                            this.getTableData();
                        }
                    })
                } else {
                    this.$http.updateEquipment(this.createObj).then(res => {
                        if (res.code === 200) {
                            this.isCreateDialogShow = false;
                            this.getTableData();
                        }
                    })
                }

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