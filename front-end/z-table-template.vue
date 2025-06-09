<template>
    <div class="main">
#detailDialog
#createDialog
        <div class="container">
            <div class="search">
                <el-row :gutter="20" style="margin-top: 20px;">
#topCreate
#topSearch
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
#tableDisplayFields
                    <el-table-column label="创建时间" min-width="8" :show-overflow-tooltip="true">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                <span>{{ $util.timestamp2Str(scope.row.createTime) }}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" min-width="10">
                        <template slot-scope="scope">
#detailBtn
#updateBtn
#deleteBtn
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
#searchCondition
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
#getTable
        },
#deleteClick
#detailClick
#updateClick
#createClick
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