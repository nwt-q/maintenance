<template>
    <div class="main">
        <div class="container">
            <!-- 统计信息区域 -->
            <div class="statistics-area">
                <el-row :gutter="20">
                    <el-col :span="8">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>待处理工单</span>
                            </div>
                            <div class="order-count">
                                <div class="count-number">{{ pendingOrderCount }}</div>
                                <div class="count-text">当前待处理工单数量</div>
                                <el-progress :percentage="pendingOrderPercentage" :color="pendingColorMethod"
                                    :stroke-width="18"></el-progress>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>超时工单</span>
                            </div>
                            <div class="order-count">
                                <div class="count-number">{{ overdueOrderCount }}</div>
                                <div class="count-text">当前超时工单数量</div>
                                <el-progress :percentage="overdueOrderPercentage" :color="overdueColorMethod"
                                    :stroke-width="18"></el-progress>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card class="box-card">
                            <div slot="header" class="clearfix">
                                <span>零件库存状态</span>
                            </div>
                            <div class="parts-status">
                                <div class="parts-info">
                                    <div class="parts-count">{{ lowStockPartsCount }}</div>
                                    <div class="parts-text">低库存零件数量</div>
                                </div>
                                <el-button size="mini" type="warning" icon="el-icon-goods"
                                    @click="navigateToPartsManagement">管理零件</el-button>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>

            <!-- 图表区域 -->
            <div class="charts-container">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-card class="chart-card">
                            <div slot="header" class="clearfix">
                                <span>零件使用统计</span>
                            </div>
                            <div id="partsChart" class="chart"></div>
                        </el-card>
                    </el-col>
                    <el-col :span="12">
                        <el-card class="chart-card">
                            <div slot="header" class="clearfix">
                                <span>工单状态分布</span>
                            </div>
                            <div id="orderStatusChart" class="chart"></div>
                        </el-card>
                    </el-col>
                </el-row>
            </div>

            <!-- 工单列表区域 -->
            <div class="order-list-container">
                <el-card class="order-list-card">
                    <div slot="header" class="clearfix">
                        <span>我的工单列表</span>
                        <el-button style="float: right; padding: 3px 0" type="text"
                            @click="refreshData">刷新数据</el-button>
                    </div>
                    <el-tabs v-model="activeTab" @tab-click="handleTabClick">

                        <!-- <el-tab-pane label="待处理工单" name="pending">
                            <el-table :data="pendingOrders" style="width: 100%" height="300px" max-height="300px"
                                empty-text="无待处理工单">
                                <el-table-column label="工单号" prop="id" min-width="3"
                                    :show-overflow-tooltip="true"></el-table-column>
                                <el-table-column label="设备名称" prop="deviceName" min-width="5"
                                    :show-overflow-tooltip="true"></el-table-column>
                                <el-table-column label="创建时间" min-width="5" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                        <span>{{ $util.timestamp2Str(scope.row.createTime) }}</span>
                                    </template>
</el-table-column>
<el-table-column label="到期时间" min-width="5" :show-overflow-tooltip="true">
    <template slot-scope="scope">
                                        <span v-if="scope.row.dueTime">{{ $util.timestamp2Str(scope.row.dueTime)
                                            }}</span>
                                        <span v-else>无到期时间</span>
                                    </template>
</el-table-column>
<el-table-column label="操作" min-width="5">
    <template slot-scope="scope">
                                        <el-button size="mini" type="primary"
                                            @click="navigateToWorkOrder(scope.row)">查看详情</el-button>
                                    </template>
</el-table-column>
</el-table>
</el-tab-pane> -->

                        <el-tab-pane label="待处理工单" name="pending">
                            <el-table :data="pendingOrders" style="width: 95%" height="100vh" max-height="100%"
                                empty-text="无匹配行" :cell-style="{ padding: '5px' }">
                                <el-table-column label="工单号" min-width="4" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <span>{{ scope.row.id }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="设备名" min-width="3" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <span>{{ scope.row.deviceName }}</span>
                                    </template>
                                </el-table-column>

                                <el-table-column label="维修记录图片" min-width="6" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <el-image style="width: 80px; height: 80px" :src="'http://127.0.0.1:18082/local2Url?name=' + scope.row.photo" :preview-src-list="[scope.row.photo]">
                                    </el-image>
                                    </template>
                                </el-table-column>

                                <el-table-column label="工单状态" min-width="5" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <el-tag :type="scope.row.status === '已完成' ? 'success' : 'warning'">
                                        {{ scope.row.status }}
                                    </el-tag>
                                    </template>
                                </el-table-column>

                                <el-table-column label="等级" min-width="4" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <span>{{ scope.row.emergencyLevel }}</span>
                                    </template>
                                </el-table-column>

                                <el-table-column label="维修类型" min-width="4" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <span>{{ scope.row.categoryName }}</span>
                                    </template>
                                </el-table-column>

                                <el-table-column label="时限" min-width="3" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <span>{{ scope.row.timeoutMinutes }}</span>
                                    </template>
                                </el-table-column>

                                <el-table-column label="维修记录描述" min-width="10" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <span>{{ scope.row.description }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="地址" min-width="8" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <div v-if="scope.row.address">
                                        <el-button size="mini" type="info" icon="el-icon-location" @click="navigateToLocation(scope.row)">
                                        {{ scope.row.address }}
                                        </el-button>
                                    </div>
                                    <span v-else>暂无地址信息</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="创建时间" min-width="8" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <div slot="reference" class="name-wrapper">
                                        <span>{{ $util.timestamp2Str(scope.row.createTime) }}</span>
                                    </div>
                                    </template>
                                </el-table-column>
                                <!-- <el-table-column label="到期时间" min-width="8" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                    <div slot="reference" class="name-wrapper">
                                        <span v-if="scope.row.dueTime">{{ $util.timestamp2Str(scope.row.dueTime) }}</span>
                                        <span v-else>无到期时间</span>
                                    </div>
                                    </template>
                                </el-table-column> -->
                                <!-- <el-table-column label="操作" min-width="15">
                                    <template slot-scope="scope">
                                        <el-button size="mini" type="primary" @click="handleEdit(scope.row)"
                                            icon="el-icon-edit">编辑维修记录</el-button>
                                        <el-button size="mini" type="success" @click="handleComplete(scope.row)"
                                            icon="el-icon-check" :disabled="scope.row.status === '已完成'">完成工单</el-button>
                                        <el-button size="mini" type="info" @click="handlePrint(scope.row)"
                                            icon="el-icon-printer">打印确认单</el-button>
                                    </template>
                                </el-table-column> -->
                            </el-table>
                        </el-tab-pane>

                        <!-- <el-tab-pane label="超时工单" name="overdue">
                            <el-table :data="overdueOrders" style="width: 100%" height="300px" max-height="300px"
                                empty-text="无超时工单">
                                <el-table-column label="工单号" prop="id" min-width="3"
                                    :show-overflow-tooltip="true"></el-table-column>
                                <el-table-column label="设备名称" prop="deviceName" min-width="5"
                                    :show-overflow-tooltip="true"></el-table-column>
                                <el-table-column label="创建时间" min-width="5" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                        <span>{{ $util.timestamp2Str(scope.row.createTime) }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="到期时间" min-width="5" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                        <span v-if="scope.row.dueTime" class="overdue-time">{{
                                            $util.timestamp2Str(scope.row.dueTime) }}</span>
                                        <span v-else>无到期时间</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="超时时长" min-width="5" :show-overflow-tooltip="true">
                                    <template slot-scope="scope">
                                        <span class="overdue-duration">{{ calculateOverdueDuration(scope.row.dueTime)
                                        }}</span>
                                    </template>
                                </el-table-column>
                                <el-table-column label="操作" min-width="5">
                                    <template slot-scope="scope">
                                        <el-button size="mini" type="danger"
                                            @click="navigateToWorkOrder(scope.row)">立即处理</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </el-tab-pane> -->
                    </el-tabs>
                </el-card>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // 工单统计数据
            pendingOrderCount: 0,
            overdueOrderCount: 0,
            pendingOrderPercentage: 0,
            overdueOrderPercentage: 0,
            lowStockPartsCount: 0,
            completedCount : 0,

            // 图表实例
            partsChart: null,
            orderStatusChart: null,

            // 工单数据

            allOrders: [],
            pendingOrders: [],
            overdueOrders: [],

            activeTab: 'pending',

            // 零件数据
            partsList: [],
        };
    },
    methods: {
        // 初始化零件使用统计图表
        initPartsChart() {
            if (this.partsChart) {
                this.partsChart.dispose();
            }
            this.partsChart = this.$echarts.init(document.getElementById('partsChart'));

            this.$http.getWorkInfo().then(res => {

                // 模拟零件数据
                let partsData = [
                    { name: '螺丝刀', usage: 45, stock: 100 },
                    { name: '扳手', usage: 32, stock: 80 },
                    { name: '电线', usage: 60, stock: 75 },
                    { name: '电容', usage: 28, stock: 50 },
                    { name: '电阻', usage: 15, stock: 120 },
                    { name: '开关', usage: 22, stock: 40 },
                ];

                partsData = res.data

                let option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {
                        data: ['使用量', '库存量']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: partsData.map(item => item.name),
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '使用量',
                            type: 'bar',
                            barWidth: '40%',
                            data: partsData.map(item => item.usage),
                            itemStyle: {
                                color: '#409EFF'
                            }
                        },
                        {
                            name: '库存量',
                            type: 'bar',
                            barWidth: '40%',
                            data: partsData.map(item => item.stock),
                            itemStyle: {
                                color: '#67C23A'
                            }
                        }
                    ]
                };

                this.partsChart.setOption(option);

                // 计算低库存零件数量（库存量小于使用量的1.5倍）
                this.lowStockPartsCount = partsData.filter(item => item.stock < item.usage * 1.5).length;
            })
        },

        // 初始化工单状态分布图表
        initOrderStatusChart() {
            if (this.orderStatusChart) {
                this.orderStatusChart.dispose();
            }
            this.orderStatusChart = this.$echarts.init(document.getElementById('orderStatusChart'));
            const user = this.$store.state.currentUser;



            this.$http.getMainTenanceInfo(user.mobile).then(res => {

                console.log("[2] : ", res.data);

                const pendingCount = res.data.pendingCount || 0;
                const overdueCount = res.data.overdueCount || 0;
                const completedCount = res.data.completedCount || 0;

                this.pendingOrderCount = res.data.pendingCount || 0;
                this.overdueOrderCount = res.data.overdueCount || 0;
                this.completedCount = res.data.completedCount || 0;

                this.processOrderData();
                const option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 10,
                        data: ['待处理', '已超时', '已完成']
                    },
                    series: [
                        {
                            name: '工单状态',
                            type: 'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: '18',
                                    fontWeight: 'bold'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: [
                                { value: pendingCount, name: '待处理', itemStyle: { color: '#E6A23C' } },
                                { value: overdueCount, name: '已超时', itemStyle: { color: '#F56C6C' } },
                                { value: completedCount, name: '已完成', itemStyle: { color: '#67C23A' } }
                            ]
                        }
                    ]
                };

                this.orderStatusChart.setOption(option);
            })
        },

        // 获取工单数据
        getWorkOrderData() {
            // 获取用户信息
            const user = this.$store.state.currentUser;

            if (!user || !user.mobile) {
                this.$message.error('获取用户信息失败');
                return;
            }

            // 获取待处理工单
            this.$http.getDaiCL(user.mobile).then(res => {
                console.log("[77] : ", res);
                // this.allOrders = res.data;
                this.pendingOrders = res.data;
                console.log("[89]: ", this.pendingOrders)
                this.processOrderData();
            }).catch(e => {
                console.err("获取待处理工单失败", e)
                // 使用模拟数据
                this.mockWorkOrderData();
                this.processOrderData();
            })

            // 获取超时工单

        },

        // 处理工单数据
        processOrderData() {
            console.log( "[64]"+ this.pendingOrderCount)
             console.log( "[65]"+ this.overdueOrderCount)

             const allCount = this.pendingOrderCount + this.overdueOrderCount + this.completedCount;
            // 计算百分比
            this.pendingOrderPercentage = this.pendingOrderCount > 0 ? Math.round((this.completedCount / allCount) * 100) : 100;
            this.overdueOrderPercentage = this.overdueOrderCount > 0 ? Math.round((this.overdueOrderCount / allCount) * 100) : 0;
            console.log("[66]" + this.pendingOrderPercentage)
            console.log("[67]" + this.overdueOrderPercentage)
            // 更新图表
        },

        // 模拟工单数据（当API调用失败时使用）
        mockWorkOrderData() {
            const now = Date.now();
            const oneDay = 24 * 60 * 60 * 1000;

            // 创建模拟数据
            this.allOrders = [];

            // 模拟待处理工单
            for (let i = 0; i < 12; i++) {
                const createTime = now - (Math.random() * 10 * oneDay);
                const dueTime = createTime + (7 * oneDay); // 7天后到期

                this.allOrders.push({
                    id: `WO-${2023000 + i}`,
                    deviceName: `设备-${String.fromCharCode(65 + i % 26)}`,
                    status: '待处理',
                    createTime: createTime,
                    dueTime: dueTime,
                    description: `维修工单描述 ${i + 1}`
                });
            }

            // 模拟超时工单
            for (let i = 0; i < 5; i++) {
                const createTime = now - (15 * oneDay);
                const dueTime = now - (Math.random() * 5 * oneDay); // 已经超时

                this.allOrders.push({
                    id: `WO-${2023100 + i}`,
                    deviceName: `设备-${String.fromCharCode(75 + i % 26)}`,
                    status: '待处理',
                    createTime: createTime,
                    dueTime: dueTime,
                    description: `超时工单描述 ${i + 1}`
                });
            }

            // 模拟已完成工单
            for (let i = 0; i < 25; i++) {
                const createTime = now - (Math.random() * 30 * oneDay);
                const dueTime = createTime + (7 * oneDay);

                this.allOrders.push({
                    id: `WO-${2023200 + i}`,
                    deviceName: `设备-${String.fromCharCode(85 + i % 26)}`,
                    status: '已完成',
                    createTime: createTime,
                    dueTime: dueTime,
                    description: `已完成工单描述 ${i + 1}`
                });
            }

            this.processOrderData();
        },

        // 计算超时时长
        calculateOverdueDuration(dueTime) {
            if (!dueTime) return '无到期时间';

            const now = Date.now();
            const overdueDuration = now - dueTime;

            if (overdueDuration <= 0) return '未超时';

            const days = Math.floor(overdueDuration / (24 * 60 * 60 * 1000));
            const hours = Math.floor((overdueDuration % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000));

            return `${days}天${hours}小时`;
        },

        // 待处理工单颜色
        pendingColorMethod(percentage) {
            console.log(percentage)
            return '#E6A23C';
        },

        // 超时工单颜色
        overdueColorMethod(percentage) {
            console.log(percentage)
            return '#F56C6C';
        },

        // 处理标签页点击
        handleTabClick(tab) {
            // 可以在这里添加额外的处理逻辑
            console.log(tab)
        },

        // 跳转到工单详情
        navigateToWorkOrder(row) {
            console.log(row)
            this.$router.push('/MaintenanceIndex/Todo');
        },

        // 跳转到零件管理
        navigateToPartsManagement() {
            this.$router.push('/MaintenanceIndex/MaintenanceParts');
        },

        // 刷新数据
        refreshData() {
            this.getWorkOrderData();
            this.initPartsChart();
        },

        // 窗口大小变化时重新调整图表大小
        handleResize() {
            if (this.partsChart) {
                this.partsChart.resize();
            }
            if (this.orderStatusChart) {
                this.orderStatusChart.resize();
            }
        }
    },
    mounted() {
        this.initOrderStatusChart();
        this.getWorkOrderData();
        this.initPartsChart();
        
        // 添加窗口大小变化监听
        window.addEventListener('resize', this.handleResize);
    },
    beforeDestroy() {
        // 移除窗口大小变化监听
        window.removeEventListener('resize', this.handleResize);

        // 销毁图表实例
        if (this.partsChart) {
            this.partsChart.dispose();
        }
        if (this.orderStatusChart) {
            this.orderStatusChart.dispose();
        }
    },
};

</script>

<style scoped>
/* 主容器样式 */
.main {
    height: 100%;
    background-color: rgb(255, 255, 255);
}

.container {
    height: 100%;
    width: 100%;
    display: flex;
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

.order-count {
    padding: 10px 0;
}

.count-number {
    font-size: 28px;
    font-weight: bold;
    color: #303133;
    margin-bottom: 5px;
}

.count-text {
    font-size: 14px;
    color: #606266;
    margin-bottom: 10px;
}

.parts-status {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 0;
}

.parts-count {
    font-size: 28px;
    font-weight: bold;
    color: #303133;
    margin-bottom: 5px;
}

.parts-text {
    font-size: 14px;
    color: #606266;
}

/* 图表区域样式 */
.charts-container {
    margin: 0 auto 15px;
    width: 98%;
}

.chart-card {
    margin-bottom: 15px;
}

.chart {
    height: 300px;
}

/* 工单列表区域样式 */
.order-list-container {
    margin: 0 auto 15px;
    width: 98%;
}

.order-list-card {
    margin-bottom: 15px;
}

.overdue-time {
    color: #F56C6C;
}

.overdue-duration {
    color: #F56C6C;
    font-weight: bold;
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

/* 隐藏滚动条 */
::v-deep ::-webkit-scrollbar {
    width: 0;
    height: 0;
}

/* 表格样式 */
/deep/ .el-table {
    background-color: rgba(243, 245, 237, 0) !important;
    font-size: 12px;
    font-weight: 400;
    letter-spacing: 1px;
}

/deep/ .el-table tr {
    background-color: rgba(243, 245, 237, 0) !important;
}

/deep/ .el-table th {
    background-color: rgba(243, 245, 237, 0) !important;
}
</style>