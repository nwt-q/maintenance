<template>
  <div class="main">
    <!-- 配件操作对话框 (增加/修改/领取) -->
    <el-dialog :visible.sync="isAccessoryDialogShow" width="45%" center>
      <div slot="title" class="dialog-title">
        {{ dialogType }}
      </div>
      <el-form
        :model="accessoryObj"
        status-icon
        label-width="150px"
        class="demo-ruleForm"
        style="font-size: 10px"
      >
        <el-form-item label="名称 :">
          <!-- 领取时显示为文本，其他模式为输入框 -->
          <span v-if="dialogType === '领取配件'" style="font-size: 12px">{{
            accessoryObj.name
          }}</span>
          <el-input
            v-else
            clearable
            v-model="accessoryObj.name"
            style="width: 85%"
          ></el-input>
        </el-form-item>
        <el-form-item label="代码编号 :">
          <!-- 领取时显示为文本，其他模式为输入框 -->
          <span v-if="dialogType === '领取配件'" style="font-size: 12px">{{
            accessoryObj.codeNumber
          }}</span>
          <el-input
            v-else
            clearable
            v-model="accessoryObj.codeNumber"
            style="width: 85%"
          ></el-input>
        </el-form-item>
        <!-- 根据dialogType条件渲染使用数量字段 -->
        <el-form-item label="使用数量 :" v-if="dialogType === '修改配件'">
          <el-input-number
            v-model="accessoryObj.usageQuantity"
            :min="0"
            style="width: 85%"
          ></el-input-number>
        </el-form-item>

        <!-- 领取数量字段，仅在领取配件时显示 -->
        <el-form-item label="领取数量 :" v-if="dialogType === '领取配件'">
          <el-input-number
            v-model="accessoryObj.claimQuantity"
            :min="1"
            :max="accessoryObj.stockQuantity"
            style="width: 85%"
          ></el-input-number>
        </el-form-item>

        <el-form-item label="库存数量 :">
          <el-input-number
            v-model="accessoryObj.stockQuantity"
            :min="0"
            :disabled="dialogType === '领取配件'"
            style="width: 85%"
          ></el-input-number>
        </el-form-item>
      </el-form>
      <div style="display: flex; align-items: center">
        <el-button
          style="margin: auto; margin-top: 2vh; width: 50%"
          type="primary"
          @click="handleSubmitAccessory"
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
                  isAccessoryDialogShow = true;
                  dialogType = '增加配件';
                  accessoryObj = {
                    // 初始化新配件对象
                    name: '',
                    codeNumber: '',
                    usageQuantity: 0, // 增加时默认设为0
                    stockQuantity: 0,
                  };
                }
              "
              >增加</el-button
            >
          </el-col>

          <el-col :span="3" :offset="1">
            <el-input
              clearable
              size="mini"
              placeholder="名称"
              suffix-icon="el-icon-search"
              v-model="searchCondition.name"
              @change="getTableData"
            ></el-input>
          </el-col>
          <!-- 也可以添加代码编号搜索
          <el-col :span="3">
            <el-input
              clearable
              size="mini"
              placeholder="代码编号"
              suffix-icon="el-icon-search"
              v-model="searchCondition.codeNumber"
              @change="getTableData"
            ></el-input>
          </el-col>
          -->
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
            label="名称"
            min-width="10"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="代码编号"
            min-width="10"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.codeNumber }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="使用数量"
            min-width="5"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.usageQuantity }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="库存数量"
            min-width="5"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.stockQuantity }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="15">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleClaim(scope.row)"
                icon="el-icon-plus"
                >领取</el-button
              >
              <!-- 打印按钮已移除 -->
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
// This is a standard Vue component using ES module syntax.
// If you encounter "SyntaxError: export declarations may only appear at top level of a module" error,
// it usually indicates an issue with your build environment (e.g., Vue CLI setup, Webpack configuration)
// not correctly processing this file as a JavaScript module.
// Ensure your project is set up to transpile .vue files correctly.
export default {
  data() {
    return {
      // 搜索条件
      searchCondition: {
        name: "", // 按名称搜索
        // codeNumber: "" // 如果需要，可以添加代码编号搜索
      },
      // 表格数据列表
      tableList: [],
      // 用于增加/修改/领取配件的表单对象
      accessoryObj: {
        id: null, // 新增id字段用于修改和删除
        name: "",
        codeNumber: "",
        usageQuantity: 0,
        stockQuantity: 0,
        claimQuantity: 0, // 新增：用于领取操作的临时数量
      },
      // 控制操作对话框的显示
      isAccessoryDialogShow: false,
      // 对话框类型（增加配件 / 修改配件 / 领取配件）
      dialogType: "",
      // 分页相关
      currentPage: 1,
      pageSize: 10,
      total: 0,
    };
  },
  methods: {
    /**
     * 获取配件表格数据
     * 在实际应用中，这里会调用后端API来获取数据
     */
    getTableData() {
      // 模拟API调用，实际开发时请替换为你的API请求
      // this.$http.getAccessoriesList({
      //   name: this.searchCondition.name,
      //   page: this.currentPage,
      //   pageSize: this.pageSize,
      // }).then((res) => {
      //   if (res.code === 200) {
      //     this.tableList = res.data.list; // 假设后端返回的数据在res.data.list
      //     this.total = res.data.total; // 假设后端返回总数在res.data.total
      //     // 获取数据后按使用数量降序排序
      //     this.tableList.sort((a, b) => b.usageQuantity - a.usageQuantity);
      //   }
      // }).catch((err) => {
      //   console.error("获取配件列表失败", err);
      //   this.$message.error("获取配件列表失败");
      //   // 模拟数据，实际开发时删除
      //   this.mockData();
      // });

      // 模拟数据，实际开发时删除此行和mockData()方法的调用
      this.mockData();
      // 获取数据后按使用数量降序排序
      this.tableList.sort((a, b) => b.usageQuantity - a.usageQuantity);
    },

    /**
     * 模拟配件数据
     * 实际开发时，此方法应删除
     */
    mockData() {
      const mockList = [];
      const totalItems = 35; // 模拟总条数
      for (let i = 0; i < totalItems; i++) {
        mockList.push({
          id: i + 1,
          name: `螺丝刀型号-${String.fromCharCode(65 + i)}`,
          codeNumber: `ACC-${1000 + i}`,
          usageQuantity: Math.floor(Math.random() * 50),
          stockQuantity: Math.floor(Math.random() * 100) + 50, // 确保库存大于使用
        });
      }

      // 根据当前页和每页大小进行分页模拟
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.tableList = mockList.slice(start, end);
      this.total = totalItems;
    },

    /**
     * 处理每页显示数量变化
     * @param {number} val - 新的每页数量
     */
    handleSizeChange(val) {
      this.pageSize = val;
      this.getTableData();
    },

    /**
     * 处理当前页码变化
     * @param {number} val - 新的页码
     */
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getTableData();
    },

    /**
     * 提交增加、修改或领取配件的表单
     */
    handleSubmitAccessory() {
      // 增加和修改时的校验
      if (this.dialogType === "增加配件" || this.dialogType === "修改配件") {
        if (!this.accessoryObj.name) {
          this.$message.error("请输入配件名称");
          return;
        }
        if (!this.accessoryObj.codeNumber) {
          this.$message.error("请输入代码编号");
          return;
        }
        // 修改配件时，校验使用数量
        if (
          this.dialogType === "修改配件" &&
          (typeof this.accessoryObj.usageQuantity !== "number" ||
            this.accessoryObj.usageQuantity < 0)
        ) {
          this.$message.error("使用数量必须为非负数字");
          return;
        }
        // 增加或修改时，校验库存数量
        if (
          typeof this.accessoryObj.stockQuantity !== "number" ||
          this.accessoryObj.stockQuantity < 0
        ) {
          this.$message.error("库存数量必须为非负数字");
          return;
        }
      }

      // 领取时的校验
      if (this.dialogType === "领取配件") {
        if (
          typeof this.accessoryObj.claimQuantity !== "number" ||
          this.accessoryObj.claimQuantity <= 0
        ) {
          this.$message.error("领取数量必须是大于0的数字");
          return;
        }
        if (this.accessoryObj.claimQuantity > this.accessoryObj.stockQuantity) {
          this.$message.error("领取数量不能大于库存数量");
          return;
        }
      }

      // 提交数据对象，根据dialogType进行不同的处理
      const dataToSubmit = {
        id: this.accessoryObj.id, // ID在增加时可能为null，修改/领取时需要
        name: this.accessoryObj.name,
        codeNumber: this.accessoryObj.codeNumber,
        usageQuantity: this.accessoryObj.usageQuantity,
        stockQuantity: this.accessoryObj.stockQuantity,
      };

      if (this.dialogType === "增加配件") {
        // 模拟增加配件API调用
        console.log("模拟增加配件:", dataToSubmit);
        this.$message.success("模拟增加成功");
      } else if (this.dialogType === "修改配件") {
        // 模拟修改配件API调用
        console.log("模拟修改配件:", dataToSubmit);
        this.$message.success("模拟修改成功");
      } else if (this.dialogType === "领取配件") {
        // 领取操作：更新使用数量和库存数量
        dataToSubmit.usageQuantity =
          this.accessoryObj.usageQuantity + this.accessoryObj.claimQuantity;
        dataToSubmit.stockQuantity =
          this.accessoryObj.stockQuantity - this.accessoryObj.claimQuantity;
        console.log("模拟领取配件:", dataToSubmit);
        this.$message.success(
          `成功领取 ${this.accessoryObj.claimQuantity} 个配件`,
        );
      }

      // 关闭对话框并刷新表格数据
      this.isAccessoryDialogShow = false;
      this.getTableData(); // 刷新并排序
    },

    /**
     * 处理领取操作（原修改按钮的功能）
     * @param {Object} row - 当前行数据
     */
    handleClaim(row) {
      this.dialogType = "领取配件";
      // 深拷贝行数据，并初始化领取数量
      this.accessoryObj = JSON.parse(JSON.stringify(row));
      this.accessoryObj.claimQuantity = 1; // 默认领取数量为1
      this.isAccessoryDialogShow = true;
    },

    /**
     * 处理修改操作 (如果需要一个独立的修改按钮，可以把这个方法绑定到新的修改按钮上)
     * 目前UI上没有直接调用此方法的按钮，但方法逻辑保留
     */
    handleEdit(row) {
      this.dialogType = "修改配件";
      this.accessoryObj = JSON.parse(JSON.stringify(row));
      this.isAccessoryDialogShow = true;
    },

    /**
     * 处理删除操作
     * @param {Object} row - 当前行数据
     */
    handleDelete(row) {
      let message = `将<span style='color: orange;font-size:15px'> [永久删除] </span>配件 [${row.name}]，是否继续？`;
      this.$confirm(message, "提示", {
        dangerouslyUseHTMLString: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 模拟删除配件API调用
          console.log("模拟删除配件:", row.id);
          this.$message.success("模拟删除成功");
          this.getTableData();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    /**
     * 处理打印操作（打印配件详情）
     * @param {Object} row - 当前行数据
     */
    handlePrint(row) {
      const printWindow = window.open("", "_blank");
      printWindow.document.write(`
        <html>
          <head>
            <title>配件详情 - ${row.name}</title>
            <style>
              body { font-family: Arial, sans-serif; margin: 20px; }
              .header { text-align: center; margin-bottom: 30px; }
              .header h1 { font-size: 24px; color: #333; }
              .content { margin-top: 20px; border: 1px solid #eee; padding: 20px; border-radius: 8px;}
              .content p { line-height: 1.8; font-size: 14px; margin-bottom: 10px;}
              .content strong { display: inline-block; width: 100px; color: #555;}
            </style>
          </head>
          <body>
            <div class="header">
              <h1>配件详情</h1>
            </div>
            <div class="content">
              <p><strong>ID:</strong> ${row.id}</p>
              <p><strong>名称:</strong> ${row.name}</p>
              <p><strong>代码编号:</strong> ${row.codeNumber}</p>
              <p><strong>使用数量:</strong> ${row.usageQuantity}</p>
              <p><strong>库存数量:</strong> ${row.stockQuantity}</p>
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
/* 保持原有的样式，仅修改部分选择器或属性以适应新结构 */
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

/* 移除了 upload-demo 样式，因为它不再需要 */
/* .upload-demo {
  width: 85%;
} */
</style>
