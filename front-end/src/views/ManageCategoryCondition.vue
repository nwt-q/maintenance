<template>
    <div class="main">
        <el-dialog :visible.sync="isCreateDialogShow" width="45%" center>
            <div slot="title" class="dialog-title">
                新增<span style="color: red">（{{ this.dialogType }}）</span>
            </div>
            <el-form :model="createObj" status-icon label-width="150px" class="demo-ruleForm" style="font-size: 10px">
                <el-form-item label="类别名 :">
                    <el-input clearable v-model="createObj.name" style="width: 85%"></el-input>
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

                    <el-col :span="3" :offset="2">
                        <el-input clearable size="mini" placeholder="类别名" suffix-icon="el-icon-search"
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
                    <el-table-column label="类别名" min-width="10" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span>{{ scope.row.name }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column label="创建时间" min-width="8" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                <span>{{ $util.timestamp2Str(scope.row.createTime) }}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" min-width="10">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" @click="handleEdit(scope.row)" icon="el-icon-edit">编辑</el-button>
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
                name: ''

            },
            tableList: [],
			detailObj: {},
			createObj: {},
			isDetailDialogShow: false,
			isCreateDialogShow: false
        };
    },
    methods: {
        getTableData() {
            this.$http.viewCategoryTable(this.searchCondition.name).then(res => {
                this.tableList = res.data;
            })

        },

        handleEdit(row) {
            let that = this;
            that.dialogType = "编辑";
            that.createObj = row;
            that.isCreateDialogShow = true;
        },

        handleDelete(row){
            let message = "将<span style='color: orange;font-size:15px'> [永久删除] </span>此条数据，是否继续？"
            this.$hint.confirm(message).then(res => {
                if (res) {
                    this.$http.deleteCategory(row.id).then(res => {
                        if (res.code === 200) {
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
            if (this.dialogType === '新增') {
                this.$http.createCategory(this.createObj).then(res => {
                    if (res.code === 200) {
                        this.isCreateDialogShow = false;
                        this.getTableData();
                    }
                })
            } else {
                this.$http.updateCategory(this.createObj).then(res => {
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