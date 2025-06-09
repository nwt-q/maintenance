<template>
    <div class="main">
        <div class="container">
            <div class="search">
                <el-row :gutter="20" style="margin-top: 20px;">
                    <el-col :span="3" :offset="2">
                        <el-input clearable size="mini" placeholder="设备名称" suffix-icon="el-icon-search"
                            v-model="searchCondition.equipmentName" @change="getTableData"></el-input>
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
                    <el-table-column label="图片" min-width="6" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <img :src="'http://127.0.0.1:18082/local2Url?name=' + scope.row.pic" style="width: 100px; height: 100px;">
                        </template>
                    </el-table-column>
                    <el-table-column label="维修类别" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-if="scope.row.categoryName">{{ scope.row.categoryName }}</span>
                            <span v-else>待管理员处理</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="维修人" min-width="5" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-if="scope.row.maintenanceName">{{ scope.row.maintenanceName }}</span>
                            <span v-else>待管理员处理</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="维修记录" min-width="10" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <span v-if="scope.row.cause">{{ scope.row.cause }}</span>
                            <span v-else>待维修人员处理</span>
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
                            <el-button v-if="!scope.row.categoryId" size="mini" type="danger" @click="handleDelete(scope.row)" icon="el-icon-delete">撤销</el-button>
                            <el-button v-else size="mini" type="info"  icon="el-icon-delete" disabled>撤销</el-button>
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
			detailObj: {},
			createObj: {},
			isDetailDialogShow: false,
			isCreateDialogShow: false
        };
    },
    methods: {
        getTableData() {
            this.$http.viewRecordTable(this.searchCondition.equipmentName,this.searchCondition.categoryName,this.$store.state.currentUser.id).then(res => {
                this.tableList = res.data;
            })

        },
        handleDelete(row){
            let message = "将<span style='color: orange;font-size:15px'> [确认撤销] </span>，是否继续？"
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