<template>
  <div class="main">
    <!-- 上传文件对话框 -->
    <el-dialog :visible.sync="isUploadDialogShow" width="45%" center>
      <div slot="title" class="dialog-title">
        {{ dialogType }}
      </div>
      <el-form
        :model="uploadObj"
        status-icon
        label-width="150px"
        class="demo-ruleForm"
        style="font-size: 10px"
      >
        <el-form-item label="手册名称 :">
          <el-input
            clearable
            v-model="uploadObj.filename"
            style="width: 85%"
          ></el-input>
        </el-form-item>
        <el-form-item label="描述 :">
          <el-input
            type="textarea"
            :rows="3"
            clearable
            v-model="uploadObj.description"
            style="width: 85%"
          ></el-input>
        </el-form-item>
        <el-form-item label="文件 :">
          <el-upload
            class="upload-demo"
            action="#"
            :http-request="uploadFile"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传PDF文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div style="display: flex; align-items: center">
        <el-button
          style="margin: auto; margin-top: 2vh; width: 50%"
          type="primary"
          @click="handleUpload"
          >确认提交</el-button
        >
      </div>
    </el-dialog>

    <div class="container">
      <div class="search">
        <el-row :gutter="20" style="margin-top: 20px">
          <el-col :span="2" :offset="1">
            <el-button
              size="small"
              style="width: 100px"
              type="primary"
              icon="el-icon-circle-plus-outline"
              @click="
                () => {
                  this.isUploadDialogShow = true;
                  this.dialogType = '上传文件';
                  this.fileList = [];
                  this.uploadObj = {};
                }
              "
              >增加</el-button
            >
          </el-col>

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

          <el-col :span="3">
            <el-input
              clearable
              size="mini"
              placeholder="名称"
              suffix-icon="el-icon-search"
              v-model="searchCondition.name"
              @change="getTableData"
            ></el-input>
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
            label="序号"
            min-width="3"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="手册名称"
            min-width="10"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.filename }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="描述"
            min-width="15"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.description }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="下载量"
            min-width="5"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.download }}</span>
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
          <el-table-column label="操作" min-width="15">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="success"
                @click="handleDownload(scope.row)"
                icon="el-icon-download"
                >下载</el-button
              >
              <el-button
                size="mini"
                type="primary"
                @click="handleEdit(scope.row)"
                icon="el-icon-edit"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="warning"
                @click="handlePrint(scope.row)"
                icon="el-icon-printer"
                >打印</el-button
              >
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)"
                icon="el-icon-delete"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="bottom">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
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
        name: "",
        startTime: null,
        endTime: null,
      },
      tableList: [],
      uploadObj: {},
      isUploadDialogShow: false,
      dialogType: "",
      fileList: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      selectedFile: null,
    };
  },
  methods: {
    getTableData() {
      this.$http
        .getKnowledgeBaseList({
          name: this.searchCondition.name,
          startTime: this.searchCondition.startTime,
          endTime: this.searchCondition.endTime,
          page: this.currentPage,
          pageSize: this.pageSize,
        })
        .then((res) => {
          if (res.code === 200) {
            this.tableList = res.data;
            this.total = res.data.length;
          }
        })
        .catch((err) => {
          console.error("获取知识库列表失败", err);
          // 模拟数据，实际开发时删除
          this.mockData();
        });
    },

    // 模拟数据，实际开发时删除
    mockData() {
      const mockList = [];
      for (let i = 0; i < 10; i++) {
        mockList.push({
          id: i + 1,
          name: `维修手册${i + 1}`,
          description: `这是维修手册${i + 1}的详细描述，包含了相关设备的维修指南和注意事项。`,
          downloadCount: Math.floor(Math.random() * 100),
          createTime:
            Date.now() - Math.floor(Math.random() * 30) * 24 * 60 * 60 * 1000,
          fileUrl: `file_${i + 1}.pdf`,
        });
      }
      this.tableList = mockList;
      this.total = 100;
    },

    handleSizeChange(val) {
      this.pageSize = val;
      this.getTableData();
    },

    handleCurrentChange(val) {
      this.currentPage = val;
      this.getTableData();
    },

    uploadFile(params) {
      // 保存选中的文件
      this.selectedFile = params.file;
      // 这里不需要实际上传，只是保存文件对象，在提交表单时一起上传
    },

    handleUpload() {
      if (!this.uploadObj.filename) {
        this.$message.error("请输入手册名称");
        return;
      }

      if (!this.selectedFile && this.dialogType === "上传文件") {
        this.$message.error("请选择要上传的文件");
        return;
      }

      // 创建FormData对象
      const formData = new FormData();
      formData.append("name", this.uploadObj.filename);
      formData.append("description", this.uploadObj.description || "");

      if (this.selectedFile) {
        formData.append("file", this.selectedFile);
      }

      if (this.dialogType === "上传文件") {
        // 调用上传API
        this.$http
          .uploadKnowledgeBase(formData)
          .then((res) => {
            if (res.code === 200) {
              this.$message.success("上传成功");
              this.isUploadDialogShow = false;
              this.getTableData();
            }
          })
          .catch((err) => {
            console.error("上传失败", err);
            this.$message.error("上传失败，请重试");
          });
      } else {
        // 修改操作
        formData.append("id", this.uploadObj.id);
        this.$http
          .updateKnowledgeBase(formData)
          .then((res) => {
            if (res.code === 200) {
              this.$message.success("修改成功");
              this.isUploadDialogShow = false;
              this.getTableData();
            }
          })
          .catch((err) => {
            console.error("修改失败", err);
            this.$message.error("修改失败，请重试");
          });
      }
    },

    handlePreview() {
      // 预览文件
      console.log("预览文件");
    },

    handleRemove() {
      this.selectedFile = null;
    },

    beforeRemove() {
      return this.$confirm(`确定移除文件？`);
    },

    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`,
      );
    },

    handleEdit(row) {
      this.dialogType = "修改文件";
      this.uploadObj = JSON.parse(JSON.stringify(row));
      this.fileList = [];
      this.isUploadDialogShow = true;
    },

    handleDelete(row) {
      let message =
        "将<span style='color: orange;font-size:15px'> [永久删除] </span>此条数据，是否继续？";
      this.$hint.confirm(message).then((res) => {
        if (res) {
          this.$http
            .deleteKnowledgeBase(row.filename)
            .then((res) => {
              if (res.code === 200) {
                this.$message.success("删除成功");
                this.getTableData();
              }
            })
            .catch((err) => {
              console.error("删除失败", err);
              this.$message.error("删除失败，请重试");
            });
        }
      });
    },

    handleDownload(row) {
      // 下载文件访问链接
      window.location.href =
        "http://127.0.0.1:18082/downloadPdf?path=" + row.filename;
      this.getTableData();
    },

    handlePrint(row) {
      // 打印功能
      const printWindow = window.open("", "_blank");
      printWindow.document.write(`
                        <html>
                            <head>
                                <title>${row.filename}</title>
                                <style>
                                    body { font-family: Arial, sans-serif; }
                                    .header { text-align: center; margin-bottom: 20px; }
                                    .content { margin: 20px; }
                                </style>
                            </head>
                            <body>
                                <div class="header">
                                    <h1>${row.filename}</h1>
                                    <p>创建时间: ${this.$util.timestamp2Str(row.createTime)}</p>
                                </div>
                                <div class="content">
                                    <h2>描述</h2>
                                    <p>${row.description || "无描述"}</p>
                                    <h2>下载量</h2>
                                    <p>${row.download || 0}</p>
                                </div>
                            </body>
                        </html>
                    `);
      printWindow.document.close();
      printWindow.print();
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
  background-color: rgb(255, 255, 255);
}

.search {
  flex: 2;
}

.container {
  height: 100%;
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

.bottom {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}

/deep/ .el-table {
  background-color: rgba(243, 245, 237, 0) !important;
  font-size: 12px;
  font-weight: 400;
  letter-spacing: 1px;
  margin: auto;
}

.dialog-title {
  text-align: center;
  font-size: 18px;
  font-weight: bold;
}

.upload-demo {
  width: 85%;
}
</style>

