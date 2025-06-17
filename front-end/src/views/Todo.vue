<template>
  <div class="main">
    <!-- 编辑维修记录对话框 -->
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

        <el-form-item label="验收说明:" v-if="createObj.status === '已完成'">
          <el-input
            clearable
            v-model="createObj.acceptanceNote"
            style="width: 85%"
            :rows="5"
            type="textarea"
            placeholder="请填写验收说明"
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

    <!-- 打印服务确认单对话框 -->
    <el-dialog :visible.sync="isPrintDialogShow" width="60%" center>
      <div slot="title" class="dialog-title">打印服务确认单</div>
      <div class="print-content" ref="printContent">
        <h2 style="text-align: center">维修服务确认单</h2>
        <div class="print-info">
          <p><strong>工单号：</strong>{{ printObj.id }}</p>
          <p><strong>设备名称：</strong>{{ printObj.deviceName }}</p>
          <p><strong>维修描述：</strong>{{ printObj.description }}</p>
          <p>
            <strong>维修时间：</strong
            >{{ $util.timestamp2Str(printObj.createTime) }}
          </p>
          <p><strong>维修状态：</strong>{{ printObj.status }}</p>
          <p v-if="printObj.acceptanceNote">
            <strong>验收说明：</strong>{{ printObj.acceptanceNote }}
          </p>
          <div v-if="printObj.photo">
            <p><strong>维修图片：</strong></p>
            <img
              :src="printObj.photo"
              style="max-width: 300px; max-height: 300px"
            />
          </div>
          <div class="signature-area">
            <p><strong>用户签名：</strong>________________________</p>
            <p><strong>日期：</strong>________________________</p>
          </div>
        </div>
      </div>
      <div style="display: flex; justify-content: center; margin-top: 20px">
        <el-button type="primary" @click="printServiceConfirmation"
          >打印确认单</el-button
        >
      </div>
    </el-dialog>

    <div class="container">
      <!-- 统计信息区域 -->
      <div class="statistics-area">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>本月工单完成率</span>
              </div>
              <div class="completion-rate">
                <el-progress
                  :percentage="completionRate"
                  :color="customColorMethod"
                  :format="format"
                  :stroke-width="18"
                ></el-progress>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>未读消息</span>
              </div>
              <div class="message-count">
                <el-badge
                  :value="unreadMessageCount"
                  :max="99"
                  class="message-badge"
                >
                  <i class="el-icon-message" style="font-size: 24px"></i>
                </el-badge>
                <span class="message-text"
                  >您有 {{ unreadMessageCount }} 条未读消息</span
                >
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span>离线功能</span>
              </div>
              <div class="offline-function">
                <el-switch
                  v-model="offlineMode"
                  active-text="启用离线模式"
                  inactive-text="在线模式"
                  @change="toggleOfflineMode"
                ></el-switch>
                <div v-if="offlineMode" class="offline-status">
                  <i class="el-icon-download"></i>
                  <span>已缓存 {{ cachedWorkOrderCount }} 个工单</span>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 搜索和功能区域 -->
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

          <el-col :span="4">
            <el-select
              v-model="sortType"
              placeholder="排序方式"
              size="mini"
              @change="handleSortChange"
            >
              <el-option
                label="创建时间降序"
                value="createTimeDesc"
              ></el-option>
              <el-option label="到期时间升序" value="dueTimeAsc"></el-option>
              <el-option label="距离远近排序" value="distanceAsc"></el-option>
            </el-select>
          </el-col>

          <el-col :span="8" :offset="1">
            <el-button-group>
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-download"
                @click="exportToExcel"
                >导出工单记录</el-button
              >
              <el-button
                size="mini"
                type="success"
                icon="el-icon-refresh"
                @click="getTableData"
                >刷新数据</el-button
              >
              <el-button
                size="mini"
                type="warning"
                icon="el-icon-message"
                @click="checkMessages"
                >查看消息</el-button
              >
            </el-button-group>
          </el-col>
        </el-row>
      </div>

      <!-- 表格区域 -->
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
            min-width="4"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="设备名"
            min-width="4"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.deviceName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="工单状态"
            min-width="5"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <el-tag
                :type="scope.row.status === '已完成' ? 'success' : 'warning'"
              >
                {{ scope.row.status }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="维修记录描述"
            min-width="6"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.description }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="维修记录图片"
            min-width="6"
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
            label="地址"
            min-width="10"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <div v-if="scope.row.address">
                <el-button
                  size="mini"
                  type="info"
                  icon="el-icon-location"
                  @click="navigateToLocation(scope.row)"
                >
                  {{ scope.row.address }}
                </el-button>
              </div>
              <span v-else>暂无地址信息</span>
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
          <el-table-column
            label="到期时间"
            min-width="8"
            :show-overflow-tooltip="true"
          >
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <span v-if="scope.row.dueTime">{{
                  $util.timestamp2Str(scope.row.dueTime)
                }}</span>
                <span v-else>无到期时间</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" min-width="15">
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
                type="success"
                @click="handleComplete(scope.row)"
                icon="el-icon-check"
                :disabled="scope.row.status === '已完成'"
                >完成工单</el-button
              >
              <el-button
                size="mini"
                type="info"
                @click="handlePrint(scope.row)"
                icon="el-icon-printer"
                >打印确认单</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页区域 -->
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

    <!-- 消息通知抽屉 -->
    <el-drawer
      title="工单消息通知"
      :visible.sync="messageDrawerVisible"
      direction="rtl"
      size="30%"
    >
      <div class="message-list">
        <el-empty
          v-if="messages.length === 0"
          description="暂无消息"
        ></el-empty>
        <el-card
          v-for="(message, index) in messages"
          :key="index"
          class="message-card"
          shadow="hover"
        >
          <div slot="header" class="clearfix">
            <span>{{ message.title }}</span>
            <el-tag
              size="mini"
              :type="message.read ? 'info' : 'danger'"
              style="float: right; margin-top: 3px"
            >
              {{ message.read ? "已读" : "未读" }}
            </el-tag>
          </div>
          <div class="message-content">{{ message.content }}</div>
          <div class="message-time">
            {{ $util.timestamp2Str(message.time) }}
          </div>
          <div class="message-actions">
            <el-button
              size="mini"
              type="text"
              @click="markAsRead(index)"
              :disabled="message.read"
              >标为已读</el-button
            >
            <el-button size="mini" type="text" @click="deleteMessage(index)"
              >删除</el-button
            >
          </div>
        </el-card>
      </div>
    </el-drawer>
  </div>
</template>
<script>
import * as XLSX from "xlsx";

export default {
  data() {
    return {
      searchCondition: {
        startTime: null,
        endTime: null,
      },
      total: 0,
      currentPage: 1,
      pageSize: 10,
      tableList: [],
      detailObj: {},
      createObj: {},
      printObj: {},
      isDetailDialogShow: false,
      isCreateDialogShow: false,
      isPrintDialogShow: false,
      sortType: "createTimeDesc",
      completionRate: 0,
      offlineMode: false,
      cachedWorkOrderCount: 0,
      unreadMessageCount: 0,
      messages: [],
      messageDrawerVisible: false,
      currentLocation: null,
      dialogType: "",
    };
  },
  methods: {
    // 格式化进度条显示
    format(percentage) {
      return `已完成 ${percentage}%`;
    },

    // 进度条颜色
    customColorMethod(percentage) {
      if (percentage < 30) {
        return "#909399";
      } else if (percentage < 70) {
        return "#e6a23c";
      } else {
        return "#67c23a";
      }
    },

    // 获取工单数据
    getTableData(page = this.currentPage, pageSize = this.pageSize) {
      // 如果是离线模式，从本地存储获取数据
      if (this.offlineMode) {
        const cachedData = localStorage.getItem("cachedWorkOrders");
        if (cachedData) {
          try {
            const parsedData = JSON.parse(cachedData);
            this.tableList = parsedData;
            this.applySorting();
            this.total = parsedData.length;
            this.calculateCompletionRate();
            return;
          } catch (e) {
            console.error("解析缓存数据失败", e);
          }
        }
      }

      // 在线模式，从服务器获取数据
      this.$http
        .getWorkOrder(
          page,
          pageSize,
          this.searchCondition.startTime,
          this.searchCondition.endTime,
        )
        .then((res) => {
          if (res.code === 200) {
            // 为每个工单添加模拟的到期时间和地址
            this.tableList = res.data.map((item) => {
              // 如果没有到期时间，添加一个模拟的到期时间（创建时间后7天）
              if (!item.dueTime) {
                const createDate = new Date(item.createTime);
                const dueDate = new Date(createDate);
                dueDate.setDate(dueDate.getDate() + 7); // 假设工单有效期为7天
                item.dueTime = dueDate.getTime();
              }

              // 如果没有地址，添加一个模拟的地址
              if (!item.address) {
                const addresses = [
                  "北京市海淀区中关村大街1号",
                  "上海市浦东新区张江高科技园区",
                  "广州市天河区天河路385号",
                  "深圳市南山区科技园",
                  "杭州市西湖区西溪路556号",
                ];
                item.address =
                  addresses[Math.floor(Math.random() * addresses.length)];
              }

              // 如果没有验收说明，添加一个空的验收说明字段
              if (!item.acceptanceNote && item.status === "已完成") {
                item.acceptanceNote = "";
              }

              return item;
            });

            // 应用排序
            this.applySorting();

            this.total = res.data.length;

            // 计算本月完成率
            this.calculateCompletionRate();

            // 缓存工单数据用于离线模式
            localStorage.setItem(
              "cachedWorkOrders",
              JSON.stringify(this.tableList),
            );
            this.cachedWorkOrderCount = this.tableList.length;
          }
        })
        .catch((err) => {
          console.error("获取列表失败", err);
          this.$message.error("获取列表失败，请检查网络连接");
        });
    },

    // 应用排序
    applySorting() {
      switch (this.sortType) {
        case "createTimeDesc":
          // 默认按创建时间降序排序
          this.tableList.sort((a, b) => {
            const aTime = new Date(a.createTime);
            const bTime = new Date(b.createTime);
            return bTime - aTime;
          });
          break;
        case "dueTimeAsc":
          // 按到期时间升序排序
          this.tableList.sort((a, b) => {
            const aTime = a.dueTime
              ? new Date(a.dueTime)
              : new Date(8640000000000000);
            const bTime = b.dueTime
              ? new Date(b.dueTime)
              : new Date(8640000000000000);
            return aTime - bTime;
          });
          break;
        case "distanceAsc":
          // 按距离排序（这里是模拟的，实际应用中需要真实的地理位置计算）
          if (this.currentLocation) {
            // 这里应该有真实的距离计算逻辑
            // 由于是模拟，我们使用随机排序来模拟距离排序
            this.tableList.sort(() => Math.random() - 0.5);
          } else {
            // 如果没有当前位置，尝试获取位置
            this.getCurrentLocation();
          }
          break;
      }
    },

    // 排序变化处理
    handleSortChange() {
      this.applySorting();
    },

    // 计算本月完成率
    calculateCompletionRate() {
      // 获取当前月份的开始和结束时间
      const now = new Date();
      const startOfMonth = new Date(
        now.getFullYear(),
        now.getMonth(),
        1,
      ).getTime();
      const endOfMonth = new Date(
        now.getFullYear(),
        now.getMonth() + 1,
        0,
        23,
        59,
        59,
      ).getTime();

      // 筛选本月的工单
      const monthlyOrders = this.tableList.filter((order) => {
        return (
          order.createTime >= startOfMonth && order.createTime <= endOfMonth
        );
      });

      // 计算完成率
      if (monthlyOrders.length > 0) {
        const completedOrders = monthlyOrders.filter(
          (order) => order.status === "已完成",
        );
        this.completionRate = Math.round(
          (completedOrders.length / monthlyOrders.length) * 100,
        );
      } else {
        this.completionRate = 0;
      }
    },

    // 导出Excel
    exportToExcel() {
      // 准备导出数据
      const exportData = this.tableList.map((item) => {
        return {
          工单号: item.id,
          设备名称: item.deviceName,
          工单状态: item.status,
          维修描述: item.description,
          创建时间: this.$util.timestamp2Str(item.createTime),
          到期时间: item.dueTime
            ? this.$util.timestamp2Str(item.dueTime)
            : "无",
          地址: item.address || "无",
          验收说明: item.acceptanceNote || "无",
        };
      });

      // 创建工作簿
      const worksheet = XLSX.utils.json_to_sheet(exportData);
      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "工单记录");

      // 生成文件名
      const fileName = `工单记录_${this.$util.getCurrentDateFormat()}.xlsx`;

      // 导出Excel
      XLSX.writeFile(workbook, fileName);

      this.$message.success("导出成功");
    },

    // 打印服务确认单
    handlePrint(row) {
      this.printObj = JSON.parse(JSON.stringify(row));
      this.isPrintDialogShow = true;
    },

    // 执行打印
    printServiceConfirmation() {
      const printContent = this.$refs.printContent.innerHTML;
      const printWindow = window.open("", "_blank");
      printWindow.document.write(`
        <html>
          <head>
            <title>维修服务确认单</title>
            <style>
              body { font-family: Arial, sans-serif; padding: 20px; }
              .print-info { margin-bottom: 20px; }
              .signature-area { margin-top: 50px; }
            </style>
          </head>
          <body>
            ${printContent}
          </body>
        </html>
      `);
      printWindow.document.close();
      printWindow.print();
      this.isPrintDialogShow = false;
    },

    // 获取当前位置
    getCurrentLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            this.currentLocation = {
              latitude: position.coords.latitude,
              longitude: position.coords.longitude,
            };
            // 获取位置后重新排序（如果当前是按距离排序）
            if (this.sortType === "distanceAsc") {
              this.applySorting();
            }
          },
          (error) => {
            console.error("获取位置失败:", error);
            this.$message.warning("获取位置信息失败，无法按距离排序");
          },
        );
      } else {
        this.$message.warning("您的浏览器不支持地理定位功能");
      }
    },

    // 导航到位置
    navigateToLocation(row) {
      if (row.address) {
        // 在实际应用中，这里应该跳转到地图应用或使用地图API
        // 这里模拟跳转到百度地图
        const url = `https://api.map.baidu.com/geocoder?address=${encodeURIComponent(row.address)}&output=html`;
        window.open(url, "_blank");
      } else {
        this.$message.warning("该工单没有地址信息");
      }
    },

    // 切换离线模式
    toggleOfflineMode(value) {
      if (value) {
        // 启用离线模式，确保已缓存数据
        const cachedData = localStorage.getItem("cachedWorkOrders");
        if (!cachedData) {
          this.$message.warning("没有缓存数据，请先连接网络获取数据");
          this.offlineMode = false;
          return;
        }
        try {
          const parsedData = JSON.parse(cachedData);
          this.cachedWorkOrderCount = parsedData.length;
          this.$message.success(
            `已启用离线模式，共缓存了 ${this.cachedWorkOrderCount} 个工单`,
          );
          this.getTableData(); // 从缓存加载数据
        } catch (e) {
          console.error("解析缓存数据失败", e);
          this.$message.error("缓存数据损坏，请重新连接网络获取数据");
          this.offlineMode = false;
        }
      } else {
        // 禁用离线模式，从服务器获取最新数据
        this.$message.info("已切换到在线模式，将获取最新数据");
        this.getTableData();
      }
    },

    // 查看消息
    checkMessages() {
      this.messageDrawerVisible = true;
    },

    // 标记消息为已读
    markAsRead(index) {
      this.messages[index].read = true;
      this.updateUnreadCount();
      localStorage.setItem("workOrderMessages", JSON.stringify(this.messages));
    },

    // 删除消息
    deleteMessage(index) {
      this.messages.splice(index, 1);
      this.updateUnreadCount();
      localStorage.setItem("workOrderMessages", JSON.stringify(this.messages));
    },

    // 更新未读消息数量
    updateUnreadCount() {
      this.unreadMessageCount = this.messages.filter((msg) => !msg.read).length;
    },

    // 加载消息
    loadMessages() {
      const savedMessages = localStorage.getItem("workOrderMessages");
      if (savedMessages) {
        try {
          this.messages = JSON.parse(savedMessages);
          this.updateUnreadCount();
        } catch (e) {
          console.error("解析消息数据失败", e);
          this.messages = [];
          this.unreadMessageCount = 0;
        }
      } else {
        // 如果没有保存的消息，创建一些模拟消息
        this.createDemoMessages();
      }
    },

    // 创建演示消息
    createDemoMessages() {
      const now = Date.now();
      this.messages = [
        {
          title: "新工单分配通知",
          content: "您有一个新的维修工单已分配，请及时处理。",
          time: now,
          read: false,
        },
        {
          title: "工单即将到期提醒",
          content: "您有3个工单即将到期，请尽快处理。",
          time: now - 86400000, // 1天前
          read: true,
        },
        {
          title: "系统维护通知",
          content: "系统将于今晚22:00-23:00进行维护，请提前做好工作安排。",
          time: now - 172800000, // 2天前
          read: false,
        },
      ];
      this.updateUnreadCount();
      localStorage.setItem("workOrderMessages", JSON.stringify(this.messages));
    },

    handleCurrentChange(e) {
      this.getTableData(e, this.pageSize);
    },

    async handleComplete(row) {
      // 打开编辑对话框，让用户填写验收说明
      this.dialogType = "完成";
      this.createObj = JSON.parse(JSON.stringify(row));
      this.createObj.status = "已完成";
      if (!this.createObj.acceptanceNote) {
        this.createObj.acceptanceNote = "";
      }
      this.isCreateDialogShow = true;
    },

    handleEdit(row) {
      this.dialogType = "编辑";
      this.createObj = JSON.parse(JSON.stringify(row));
      this.isCreateDialogShow = true;
    },

    handleChange(file) {
      this.createObj.photo = URL.createObjectURL(file.raw);
    },

    async handleSubmit() {
      // 验证表单
      if (
        this.createObj.status === "已完成" &&
        !this.createObj.acceptanceNote
      ) {
        this.$message.warning("请填写验收说明");
        return;
      }

      const params = {
        id: this.createObj.id,
        device_name: this.createObj.deviceName,
        create_time: this.createObj.createTime,
        photo: this.createObj.photo,
        status: this.createObj.status,
        description: this.createObj.description,
        acceptance_note: this.createObj.acceptanceNote,
      };

      try {
        await this.$http.updateWorkOrder(params);
        this.$message.success("操作成功");
        this.getTableData(this.currentPage, this.pageSize);
        this.isCreateDialogShow = false;

        // 如果是完成工单操作，添加一条新消息
        if (this.dialogType === "完成") {
          const newMessage = {
            title: "工单完成通知",
            content: `工单 ${this.createObj.id} 已完成处理。`,
            time: Date.now(),
            read: false,
          };
          this.messages.unshift(newMessage);
          this.updateUnreadCount();
          localStorage.setItem(
            "workOrderMessages",
            JSON.stringify(this.messages),
          );
        }
      } catch (error) {
        console.error("更新工单失败", error);
        this.$message.error("操作失败，请重试");
      }
    },
  },

  created() {
    // 获取当前位置
    this.getCurrentLocation();

    // 加载工单数据
    this.getTableData();

    // 加载消息
    this.loadMessages();
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

.container {
  height: 100%;
  /* border: 1px solid red; */
  display: flex;
  width: 100%;
  flex-direction: column;
}

/* 统计信息区域样式 */
.statistics-area {
  margin: 15px auto;
  width: 98%;
}

.box-card {
  margin-bottom: 15px;
  transition: all 0.3s;
}

.box-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.completion-rate {
  padding: 10px 0;
}

.message-count {
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.message-badge {
  margin-right: 15px;
}

.message-text {
  font-size: 14px;
}

.offline-function {
  padding: 10px 0;
}

.offline-status {
  margin-top: 10px;
  font-size: 14px;
  color: #606266;
}

.offline-status i {
  margin-right: 5px;
  color: #409eff;
}

/* 搜索区域样式 */
.search {
  flex: 2;
  margin-bottom: 15px;
}

::v-deep ::-webkit-scrollbar {
  width: 0;
  height: 0;
}

/* 表格区域样式 */
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

/* 分页区域样式 */
.bottom {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
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

/* 上传图片区域样式 */
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

/* 打印内容样式 */
.print-content {
  padding: 20px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  background-color: #fff;
}

.print-info {
  line-height: 1.8;
}

.signature-area {
  margin-top: 50px;
  border-top: 1px dashed #ccc;
  padding-top: 20px;
}

/* 消息抽屉样式 */
.message-list {
  padding: 10px;
}

.message-card {
  margin-bottom: 15px;
}

.message-content {
  padding: 10px 0;
  color: #606266;
  font-size: 14px;
}

.message-time {
  font-size: 12px;
  color: #909399;
  margin-bottom: 10px;
}

.message-actions {
  text-align: right;
  border-top: 1px solid #ebeef5;
  padding-top: 10px;
}

/* 清除浮动 */
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>
