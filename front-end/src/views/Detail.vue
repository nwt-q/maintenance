<template>
    <div class="main">
        <el-dialog :visible.sync="isCreateDialogShow" width="45%" center>
            <div slot="title" class="dialog-title">
                编辑
            </div>
            <el-form :model="detailObj" status-icon label-width="150px" class="demo-ruleForm" style="font-size: 10px">
                <el-form-item label="设备名 :">
                    <el-input clearable v-model="detailObj.name" style="width: 85%"></el-input>
                </el-form-item>
                <el-form-item label="设备状态信息 :">
                    <el-input clearable v-model="detailObj.type" style="width: 85%"></el-input>
                </el-form-item>
                <el-form-item label="位置 :">
                    <el-input clearable v-model="detailObj.location" style="width: 85%"></el-input>
                </el-form-item>
                <el-form-item label="状态 :">
                    <el-select style="width: 60%" clearable v-model="detailObj.status"  placeholder="状态">
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
            <div class="left">
                <div style="display: flex;height: 40%;margin-top: 20px">
                    <div style="display: flex;border: 5px cornflowerblue solid;margin: 15px;width: 100%;margin-left: 30px;border-radius: 10px;">
                        <div style="display: flex;flex-direction: row;width: 100%">
                            <div style="display: flex;width: 25%;height: 100%;">
                                <div style="display: flex;flex-direction: column;justify-content: center;align-items: center;width: 100%;height: 100%">
                                    <el-button style="height: 32px;width: 70%;margin-left: 10px" type="primary" disabled></el-button>
                                    <el-button style="margin-top: 13px;height: 32px;width: 70%" type="primary" disabled></el-button>
                                    <el-button style="margin-top: 13px;height: 32px;width: 70%" type="primary" disabled></el-button>
                                </div>
                            </div>
                            <div style="display: flex;width: 50%;height: 100%;flex-direction: column">

                                <div style="margin-top: 30px;font-size: 30px;font-weight: 900;color: RGB(40,142,251);margin-left: 10px;" >
                                    {{detailObj.name}}
                                    <span v-if="detailObj.status === '开启'" @click="handleClose()"
                                          style="margin-left: 20px;font-size: 15px;color: RGB(85,119,33);">
                                        <i style="margin-right: 5px;font-size: 15px;" class="el-icon-video-pause"></i>
                                        <span style="line-height: 0px;margin-top: -10px;">运行中({{detailObj.type}})</span>
                                    </span>
                                    <span  v-if="detailObj.status === '关闭'" @click="handleOpen()"
                                           style="margin-left: 20px;font-size: 15px;color: red;">
                                        <i style="margin-right: 5px;font-size: 15px;" class="el-icon-video-play"></i>
                                        <span style="line-height: 0px;margin-top: -10px;">已关闭({{detailObj.type}})</span>
                                    </span>
                                </div>
                                <div style="margin-top: 20px;margin-left: 10px;font-weight: 900;font-size: 15px">
                                    名称<span style="margin-left: 70px">{{detailObj.name}}</span><span @click="handleEdit()"><i style="margin-left: 5px;" class="el-icon-edit"></i></span>
                                </div>
                                <div style="margin-top: 10px;margin-left: 10px;font-weight: 900;font-size: 15px">
                                    位置<span style="margin-left: 70px">{{detailObj.location}}</span>
                                </div>
                                <div style="margin-top: 10px;margin-left: 10px;font-weight: 900;font-size: 15px">
                                    添加时间<span style="margin-left: 40px">{{ $util.timestamp2Str(detailObj.createTime) }}</span>
                                </div>
                                <div style="margin-top: 10px;margin-left: 10px;font-weight: 900;font-size: 15px">
                                    电流/电压<span style="margin-left: 32px">{{detailObj.current}}mA / {{detailObj.voltage}}V</span>
                                </div>

                            </div>
                            <div style="display: flex;width: 25%;height: 100%">
                                <div style="display: flex;flex-direction: column;justify-content: center;align-items: center;width: 100%;height: 100%">
                                    <el-button style="width: 70%;margin-left: 10px" type="info" disabled icon="el-icon-video-play">开始</el-button>
                                    <el-button style="margin-top: 13px;width: 70%" type="warning" disabled icon="el-icon-video-pause">重启</el-button>
                                    <el-button style="margin-top: 13px;width: 70%" type="primary" disabled icon="el-icon-refresh">刷新</el-button>
                                    <el-button style="margin-top: 13px;width: 70%" type="danger" disabled icon="el-icon-switch-button">关闭</el-button>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <div style="display: flex;height: 50%;">
                    <div style="border: 5px cornflowerblue solid;margin: 15px;width: 100%;margin-left: 30px;border-radius: 10px;">
                        <el-table :data="tableList" style="width: 95%" height="100%" max-height="100%" empty-text="无匹配行"
                                  :cell-style="{ padding: '5px' }">
                            <el-table-column label="维修类别" min-width="3">
                                <template slot-scope="scope">
                                    <span>{{scope.row.categoryName}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="维修人" min-width="5">
                                <template slot-scope="scope">
                                    <span>{{scope.row.maintenanceName}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="维修记录" min-width="10">
                                <template slot-scope="scope">
                                    <span>{{scope.row.cause}}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </div>
            <div class="right" style="margin-top: 25px;margin-right: 40px">
                <div style="display: flex;border: 5px cornflowerblue solid;margin: 15px;width: 100%;height: 87%;border-radius: 10px;justify-content: center;align-items: center;">
                    <img src="../static/1714056500509.jpg" alt="flex: 0 0 auto;margin-right: 10px;" style="height: 90%;width: 90%;">
                </div>
            </div>
        </div>
    </div>
</template>
<script>


export default {
    data() {
        return {
            searchCondition: {
                username: ''

            },
            tableList: [],
			detailObj: {},
			createObj: {},
			isDetailDialogShow: false,
			isCreateDialogShow: false,
            lastId: ''
        };
    },
    methods: {
        getTableData() {
            this.$http.viewEquipment(this.$store.state.detailId).then(res => {
                this.detailObj = res.data;
            });
            this.$http.viewRecord(this.$store.state.detailId).then(res => {
                this.tableList = res.data;
            })

        },

        handleEdit() {
            this.isCreateDialogShow = true;
        },

        handleCreate() {
            this.detailObj.userId = this.$store.state.currentUser.id;
            this.$http.updateEquipment(this.detailObj).then(res => {
                if (res.code === 200) {
                    this.isCreateDialogShow = false;
                    this.getTableData();
                }
            })

        },

        handleClose() {
            this.detailObj.status = '关闭';
            this.detailObj.userId = this.$store.state.currentUser.id;
            this.$http.updateEquipment(this.detailObj).then(res => {
                if (res.code === 200) {
                    this.getTableData();
                }
            })
        },
        handleOpen() {
            this.detailObj.status = '开启';
            this.detailObj.userId = this.$store.state.currentUser.id;
            this.$http.updateEquipment(this.detailObj).then(res => {
                if (res.code === 200) {
                    this.getTableData();
                }
            })
        },

    },
    created() {
        setInterval(() => {
            if (this.lastId !== '') {
                if (this.lastId !== this.$store.state.detailId) {
                    this.getTableData();
                    this.lastId = this.$store.state.detailId;
                }
            }else {
                this.lastId = this.$store.state.detailId;
                this.getTableData();
            }
        },100);
        // this.getTableData();
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
        margin: auto;
        height: 100%;
        /* border: 1px solid red; */
        display: flex;
        width: 100%;
        flex-direction: row;
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

    .left {
        margin-top: 5px;
        width: 70%;
        display: flex;
        flex-direction: column;
    }

    .right {
        margin-top: 5px;
        width: 30%;
        display: flex;
        flex-direction: column;
    }
</style>