<template>
    <div class="container">
        <div class="top">
            <div class="logo">
                <span style="font-weight: 500; font-size: 1rem">#projectName</span>
            </div>
            <div class="space"></div>

            <div class="info">
                <el-popover placement="top" width="100" v-model="pageState.isInfoHintShow">
                    <p style="text-align: center">退出登录 ?</p>
                    <div style="text-align: right; margin: 0">
                        <el-button size="mini" type="text" @click="pageState.isInfoHintShow = false">否</el-button>
                        <el-button type="primary" size="mini" @click="logOut">是</el-button>
                    </div>
                    <div slot="reference" style="margin: auto;">
                        <span style="color: #303133">{{ this.$store.state.currentUser == null ? "" :
                            this.$store.state.currentUser.username }}</span>
                        <span style="color: #909399">【{{ this.$store.state.currentUser == null ? "" :
                            this.$store.state.currentUser.nickname }}】</span>
                    </div>
                </el-popover>
            </div>
        </div>
        <div class="center">
            <div class="aside" @mouseenter="pageState.isCollapse = false" @mouseleave="pageState.isCollapse = true">
                <el-menu style="height: 100%" :default-active="$route.path" class="el-menu-vertical-demo"
                         :collapse="false" text-color="#303133" active-text-color="#409EFF"
                         background-color="rgba(102,204,255, 0.1)">
#pageIndex
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
                    isInfoHintShow: false
                }
            };
        },
        methods: {
            logOut() {
                this.pageState.isInfoHintShow = false;
                this.$store.commit("setToken", null);
                this.$store.commit("setCurrentUser", null);
                this.$router.replace("/");
            }
        },
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
        background-color: rgba(102, 204, 255, 0.1);
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
        padding-left: 6vw;
        padding-right: 6vw;
        /* color: rgb(230, 141, 41); */
        display: flex;
    }

    .space {
        width: 65vw;
    }

    .logo {
        width: 20vw;
        font-size: 80%;
        display: flex;
        align-items: center;
        color: #303133;
        font-weight: 300;
        letter-spacing: 1px;
    }

    .info {
        width: 15vw;
        display: flex;
        align-items: center;
        font-size: 68%;
        cursor: pointer;
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