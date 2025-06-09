<template>
  <div class="main">
    <el-dialog :visible.sync="isCreateDialogShow" width="45%" center>
      <div slot="title" class="dialog-title">
        编辑维修记录<span style="color: red">（{{ this.dialogType }}）</span>
      </div>
      <el-form
        :model="createObj"
        status-icon
        label-width="150px"
        class="demo-ruleForm"
        style="font-size: 10px"
      >
        <el-form-item label="维修记录图片:">
          <el-upload
            action=""
            :show-file-list="false"
            :on-change="handleChange"
          >
            <img v-if="createObj.photo" :src="createObj.photo" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="维修记录描述:">
          <el-input
            clearable
            v-model="createObj.description"
            style="width: 85%"
            :rows="10"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <div style="display: flex; align-items: center">
        <el-button
          style="margin: auto; margin-top: 2vh; width: 50%"
          type="primary"
          @click="handleSubmit"
          >确认提交</el-button
        >
      </div>
    </el-dialog>
    <div class="container">
      <div class="search">
        <el-row :gutter="20" style="margin-top: 20px">
          <el-col :span="3" :offset="1">
            <el-date-picker
              v-model="searchCondition.startTime"
              type="datetime"
              size="mini"
              placeholder="开始时间"
              value-format="timestamp"
              @change="getTableData"
            >
            </el-date-picker>
          </el-col>

          <el-col :span="3">
            <el-date-picker
              v-model="searchCondition.endTime"
              type="datetime"
              size="mini"
              placeholder="结束时间"
              value-format="timestamp"
              @change="getTableData"
            >
            </el-date-picker>
          </el-col>
        </el-row>
      </div>

      <div class="table">
        <el-table
          :data="tableList"
          style="width: 95%"
          height="100%"
          max-height="100%"
          empty-text="无匹配行"
          :cell-style="{ padding: '5px' }"
        >
          <el-table-column
            label="工单号"
            min-width="3"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="设备名"
            min-width="3"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.deviceName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="工单状态"
            min-width="3"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.status }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="维修记录描述"
            min-width="10"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.description }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="维修记录图片"
            min-width="10"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <el-image
                style="width: 80px; height: 80px"
                :src="scope.row.photo"
                :preview-src-list="[scope.row.photo]"
              >
              </el-image>
            </template>
          </el-table-column>

          <el-table-column
            label="创建时间"
            min-width="8"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <span>{{ $util.timestamp2Str(scope.row.createTime) }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="10">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleEdit(scope.row)"
                icon="el-icon-edit"
                >编辑维修记录</el-button
              >
              <el-button
                size="mini"
                type="primary"
                @click="handleComplete(scope.row)"
                icon="el-icon-edit"
                :disabled="scope.row.status === '已完成'"
                >完成工单</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="bottom">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          layout="total, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      searchCondition: {
        startTime: null,
        endTime: null,
      },
      total: 0,
      currentPage: 1,
      pageSize: 7,
      tableList: [],
      detailObj: {},
      createObj: {},
      isDetailDialogShow: false,
      isCreateDialogShow: false,
    };
  },
  methods: {
    getTableData() {
      this.$http
        .getWorkOrder(
          this.currentPage,
          this.pageSize,
          this.searchCondition.startTime,
          this.searchCondition.endTime,
        )
        .then((res) => {
          if (res.code === 200) {
            this.tableList = res.data;
            this.tableList.sort((a, b) => {
              const aTime = new Date(a.createTime);
              const bTime = new Date(b.createTime);
              return bTime - aTime;
            });
            this.total = res.data.length;
          }
        })
        .catch((err) => {
          console.error("获取列表失败", err);
        });
    },

    handleCurrentChange(e) {
      this.getTableData(e, this.pageSize);
    },

    async handleComplete(row) {
      row.status = "已完成";
      const params = {
        id: row.id,
        device_name: row.deviceName,
        create_time: row.createTime,
        photo: row.photo,
        status: row.status,
        description: row.description,
      };
      await this.$http.updateWorkOrder(params);
      this.getTableData(this.currentPage, this.pageSize);
    },

    handleEdit(row) {
      let that = this;
      that.dialogType = "编辑";
      that.createObj = row;
      that.isCreateDialogShow = true;
    },

    handleChange(file) {
      this.createObj.photo = URL.createObjectURL(file.raw);
    },

    async handleSubmit() {
      const params = {
        id: this.createObj.id,
        device_name: this.createObj.deviceName,
        create_time: this.createObj.createTime,
        photo: this.createObj.photo,
        status: this.createObj.status,
        description: this.createObj.description,
      };
      await this.$http.updateWorkOrder(params);
      this.getTableData(this.currentPage, this.pageSize);
      this.isCreateDialogShow = false;
    },
  },

  created() {
    this.getTableData();
  },
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
  background-color: rgb(255, 255, 255);
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

.bottom {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
