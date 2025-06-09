<template>
    <div class="container">
        <div class="form">
            <el-form :label-position="labelPosition" label-width="80px" :model="formData">
                <el-form-item label="昵称">
                    <el-input v-model="formData.nickname"></el-input>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="formData.mobile"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="formData.password"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="formData.username"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="formData.email"></el-input>
                </el-form-item>
                <el-form-item label="QQ号">
                    <el-input v-model="formData.qq"></el-input>
                </el-form-item>
                <el-form-item label="身份">
                    <el-input disabled v-model="formData.identity"></el-input>
                </el-form-item>
                <el-form-item style="display: flex;align-items: center;justify-content: center;  ">
                    <el-button style="width: 20vh;" type="primary" @click="onSubmit">提交</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                formData: this.$store.state.currentUser
            };
        },
        methods: {
            onSubmit() {
                if (!this.$verify.checkPhone(this.formData.mobile)) {
                    this.$hint.message.error('手机号不合法');
                    return;
                }
                if (!this.$verify.checkPassword(this.formData.password)) {
                    this.$hint.message.error('密码必须包含数字、大小写字母、特殊字符，长度8-16位');
                    return;
                }
                this.$http.updateUser(this.formData).then(res => {
                    if (res.code === 200) {
                        this.$store.commit('setCurrentUser', this.formData);
                    }
                })
            }
        },
        created() {
        },
    };
</script>
<style scoped>
    .container {
        height: 100%;
        width: 100%;
        background-color: rgb(249,249,249);
    }

    .form {
        width: 50%;
        margin: auto;
    }
</style>