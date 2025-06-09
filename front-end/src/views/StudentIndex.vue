<template>
    <div class="container">
        <div class="top">
            <div class="logo">
                <span style="font-weight: 500; font-size: 1rem">故障收集系统</span>
            </div>
            <div class="space">
                <span style="margin-left: 450px; color: yellow;font-size: 15px;font-weight: bold">{{ currentDate }}</span>
                <span style="margin-left: 15px; color: wheat;font-size: 15px;font-weight: bold">{{ currentDay }}</span>
                <span style="margin-left: 15px; color: powderblue;font-size: 15px;font-weight: bold">{{ currentTime }}</span>
            </div>

            <div class="info">
                <el-popover placement="top" width="100" v-model="pageState.isInfoHintShow">
                    <p style="text-align: center">退出登录 ?</p>
                    <div style="text-align: right; margin: 0">
                        <el-button size="mini" type="text" @click="pageState.isInfoHintShow = false">否</el-button>
                        <el-button type="primary" size="mini" @click="logOut">是</el-button>
                    </div>
                    <div slot="reference" style="margin: auto;">
                        <span>{{ this.$store.state.currentUser == null ? "" :
                            this.$store.state.currentUser.nickname }}</span>
                        <span>【{{ this.$store.state.currentUser == null ? "" :
                            this.$store.state.currentUser.identity }}】</span>
                    </div>
                </el-popover>
            </div>
        </div>
        <div class="center">
            <div class="aside" @mouseenter="pageState.isCollapse = false" @mouseleave="pageState.isCollapse = true" style="overflow: auto;height: 80vh">
                <el-menu style="height: 100%" :default-active="$route.path" class="el-menu-vertical-demo"
                         :collapse="false" text-color="#303133" active-text-color="#409EFF"
                         background-color="rgb(249,249,249)">
                    <el-menu-item index="/StudentIndex" @click="$router.replace('/StudentIndex')">
                        <i class="el-icon-folder-opened"></i>
                        <span slot="title">查看设备</span>
                    </el-menu-item>
                    <el-menu-item index="/StudentIndex/StudentRecordCondition" @click="$router.replace('/StudentIndex/StudentRecordCondition')">
                        <i class="el-icon-tickets"></i>
                        <span slot="title">故障上报记录</span>
                    </el-menu-item>
                    <el-menu-item index="/StudentIndex/Mine" @click="$router.replace('/StudentIndex/Mine')">
                        <i class="el-icon-s-custom"></i>
                        <span slot="title">个人中心</span>
                    </el-menu-item>
                    <el-menu-item index="/index/store" @click="logOut">
                        <i class="el-icon-switch-button"></i>
                        <span slot="title">退出登录</span>
                    </el-menu-item>
                </el-menu>
            </div>
            <div class="main">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                pageState: {
                    isCollapse: true,
                    isInfoHintShow: false,
                    currentDate: '',
                    currentDay: '',
                    currentTime: '',
                }
            };
        },
        methods: {
            logOut() {
                this.pageState.isInfoHintShow = false;
                this.$store.commit("setToken", null);
                this.$store.commit("setCurrentUser", null);
                this.$router.replace("/");
            },
            updateTime() {
                const now = new Date();
                // 获取日期
                this.currentDate = now.toLocaleDateString();
                // 获取星期
                const days = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
                this.currentDay = days[now.getDay()];
                // 获取时间
                this.currentTime = now.toLocaleTimeString();
            },

        },

        created() {
            this.updateTime();
            // 每秒更新一次时间
            setInterval(() => {
                this.updateTime();
                this.$forceUpdate();
            }, 1000);
        }
    };
</script>
<style scoped>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 12vw;
        min-height: 80vh;
    }

    .el-menu {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    /deep/.el-submenu__title {
        font-size: 85%;
    }

    /deep/.el-menu-item-group__title {
        font-size: 40%;
    }

    /deep/.el-menu-item {
        font-size: 60%;
    }

    .el-menu-item:hover {
        background-color: rgb(204, 204, 204) !important;
    }

    .container {
        width: 100vw;
        height: 100vh;
        display: flex;
        flex-direction: column;
    }

    .top {
        flex: 1;
        border-bottom: 1px solid #C0C4CC;
        background-color: rgb(23, 37, 85);
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
        padding-left: 6vw;
        padding-right: 6vw;
        /* color: rgb(230, 141, 41); */
        display: flex;
    }

    .space {
        width: 50vw;
        display: flex;
        align-items: center;
        font-size: 68%;
        cursor: pointer;
    }

    .logo {
        width: 20vw;
        font-size: 80%;
        display: flex;
        align-items: center;
        font-weight: 300;
        letter-spacing: 1px;
        color: white;
    }

    .info {
        width: 15vw;
        display: flex;
        align-items: center;
        font-size: 68%;
        cursor: pointer;
        color: white;
        margin-left: 50px;
    }

    .center {
        flex: 13;
        display: flex;
        background-color: rgba(188, 232, 252, 0.1);
    }

    .main {
        flex: 12;
    }

    .footer {
        flex: 1;
        border-top: 1px solid #C0C4CC;
        background-color: rgba(102, 204, 255, 0.1);
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
        display: flex;
    }

    /deep/ .el-submenu .el-menu-item {
        min-width: 0px !important;
    }

    .footer span {
        flex: 1;
        margin: auto;
        text-align: center;
        font-size: 0.8rem;
        letter-spacing: 2px;
        font-weight: 200px;
        color: #606266;
        cursor: pointer;
    }</style>