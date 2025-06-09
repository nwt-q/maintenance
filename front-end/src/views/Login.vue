<template>
  <div class="container">
    <div class="form-structor">
      <div class="signup" :class="pageState.isLoginPage ? 'slide-up' : ''">
        <h2 class="form-title" id="signup" @click="() => this.pageState.isLoginPage = false">
          <span>或</span>注册
        </h2>
        <div class="form-holder">
            <input type="text" v-model="registerPage.mobile" class="input" placeholder="手机号"/>
            <input type="password" v-model="registerPage.password" class="input" placeholder="密码"/>

        </div>
        <button class="submit-btn" @click="doSignUp">确定</button>
      </div>
      <div class="login" :class="pageState.isLoginPage ? '' : 'slide-up'">
        <div class="center">
          <h2 class="form-title" id="login" @click="() => this.pageState.isLoginPage = true">
            <span>或</span>
          </h2>
          <h4 style="text-align: center">故障收集系统</h4>
          <div class="form-holder">
            <input type="text" v-model="loginPage.mobile" class="input" placeholder="手机号"/>
            <input type="password" v-model="loginPage.password" class="input" placeholder="密码"/>

          </div>
          <button class="submit-btn" @click="doLogin">登录</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
  
<script>
export default {
  data() {
    return {
      pageState: {
        isLoginPage: true,
      },
      loginPage: {
        mobile: '',
        password: '',

      },
      registerPage: {
        mobile: '',
        password: '',

      },
    };
  },
  methods: {
    doLogin() {
      if (!this.$verify.checkPhone(this.loginPage.mobile)) {
        this.$hint.message.error('手机号不合法');
        return;
      }
      if (!this.$verify.checkPassword(this.loginPage.password)) {
        this.$hint.message.error('密码必须包含数字、大小写字母、特殊字符，长度8-16位');
        return;
      }

      this.$verify.emptyCheckObject(this.loginPage).then(obj => {
        this.$http.doLogin(obj.mobile,obj.password).then(res => {
          if (res.code === 200) {
            let user = res.data.e2;
            this.$store.commit('setCurrentUser', user);
            this.$store.commit('setToken', res.data.e1);
            if (user.identity === '管理员') {
                this.$router.replace('/MangeIndex');
            }
            if (user.identity === '学生') {
                this.$router.replace('/StudentIndex');
            } 
            if( user.identity === '维修人员') {
                this.$router.replace('/RepairIndex');
            }
          }
        })

      }).catch(err => {
        this.$hint.message.error(err);
      })
    },
    doSignUp() {
      if (!this.$verify.checkPhone(this.registerPage.mobile)) {
        this.$hint.message.error('手机号不合法');
        return;
      }
      if (!this.$verify.checkPassword(this.registerPage.password)) {
        this.$hint.message.error('密码必须包含数字、大小写字母、特殊字符，长度8-16位');
        return;
      }
      this.registerPage.identity ='学生';
      this.$verify.emptyCheckObject(this.registerPage).then(obj => {
        this.$http.createUser(obj).then(res => {
          if (res.code === 200) {
            this.pageState.isLoginPage = true;
            this.$util.trimFieldsToEmpty(this.registerPage, []);
          }
        })

      }).catch(err => {
        this.$hint.message.error(err);
      })
    },
  },
};
</script>
  
  
<style scoped>
.container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
}

.form-structor {
  margin: auto;
  background-color: #222;
  border-radius: 15px;
  height: 550px;
  width: 350px;
  position: relative;
  overflow: hidden;
}

.form-structor::after {
  content: "";
  opacity: 0.8;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-repeat: no-repeat;
  background-position: left bottom;
  background-size: 500px;
  background-image: url('../static/background_top.jpg');
  opacity: 0.8;
}

.form-structor .signup {
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  width: 65%;
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}

.form-structor .signup.slide-up {
  top: 5%;
  -webkit-transform: translate(-50%, 0%);
  -webkit-transition: all 0.3s ease;
}

.form-structor .signup.slide-up .form-holder,
.form-structor .signup.slide-up .submit-btn {
  opacity: 0;
  visibility: hidden;
}

.form-structor .signup.slide-up .form-title {
  font-size: 1em;
  cursor: pointer;
}

.form-structor .signup.slide-up .form-title span {
  margin-right: 5px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}

.form-structor .signup .form-title {
  color: #fff;
  font-size: 1.7em;
  text-align: center;
}

.form-structor .signup .form-title span {
  color: rgba(0, 0, 0, 0.4);
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}

.form-structor .signup .form-holder {
  border-radius: 15px;
  background-color: #fff;
  overflow: hidden;
  margin-top: 50px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}

.form-structor .signup .form-holder .input {
  border: 0;
  outline: none;
  box-shadow: none;
  display: block;
  height: 30px;
  line-height: 30px;
  padding: 8px 15px;
  border-bottom: 1px solid #eee;
  width: 100%;
  font-size: 12px;
}

.form-structor .signup .form-holder .input:last-child {
  border-bottom: 0;
}

.form-structor .signup .form-holder .input::-webkit-input-placeholder {
  color: rgba(0, 0, 0, 0.4);
}

.form-structor .signup .submit-btn {
  background-color: rgba(0, 0, 0, 0.4);
  color: rgba(255, 255, 255, 0.7);
  border: 0;
  border-radius: 15px;
  display: block;
  margin: 15px auto;
  padding: 15px 45px;
  width: 100%;
  font-size: 13px;
  font-weight: bold;
  cursor: pointer;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}

.form-structor .signup .submit-btn:hover {
  transition: all 0.3s ease;
  background-color: rgba(0, 0, 0, 0.8);
}

.form-structor .login {
  position: absolute;
  top: 20%;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #fff;
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login::before {
  content: "";
  position: absolute;
  left: 50%;
  top: -20px;
  -webkit-transform: translate(-50%, 0);
  background-color: #fff;
  width: 200%;
  height: 250px;
  border-radius: 50%;
  z-index: 4;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login .center {
  position: absolute;
  top: calc(50% - 10%);
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  width: 65%;
  z-index: 5;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login .center .form-title {
  color: #000;
  font-size: 1.7em;
  text-align: center;
}

.form-structor .login .center .form-title span {
  color: rgba(0, 0, 0, 0.4);
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login .center .form-holder {
  border-radius: 15px;
  background-color: #fff;
  border: 1px solid #eee;
  overflow: hidden;
  margin-top: 50px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login .center .form-holder .input {
  border: 0;
  outline: none;
  box-shadow: none;
  display: block;
  height: 30px;
  line-height: 30px;
  padding: 8px 15px;
  border-bottom: 1px solid #eee;
  width: 100%;
  font-size: 12px;
}

.form-structor .login .center .form-holder .input:last-child {
  border-bottom: 0;
}

.form-structor .login .center .form-holder .input::-webkit-input-placeholder {
  color: rgba(0, 0, 0, 0.4);
}

.form-structor .login .center .submit-btn {
  background-color: #6b92a4;
  color: rgba(255, 255, 255, 0.7);
  border: 0;
  border-radius: 15px;
  display: block;
  margin: 15px auto;
  padding: 15px 45px;
  width: 100%;
  font-size: 13px;
  font-weight: bold;
  cursor: pointer;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login .center .submit-btn:hover {
  transition: all 0.3s ease;
  background-color: rgba(0, 0, 0, 0.8);
}

.form-structor .login.slide-up {
  top: 90%;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login.slide-up .center {
  top: 10%;
  -webkit-transform: translate(-50%, 0%);
  -webkit-transition: all 0.3s ease;
}

.form-structor .login.slide-up .form-holder,
.form-structor .login.slide-up .submit-btn {
  opacity: 0;
  visibility: hidden;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login.slide-up .form-title {
  font-size: 1em;
  margin: 0;
  padding: 0;
  cursor: pointer;
  -webkit-transition: all 0.3s ease;
}

.form-structor .login.slide-up .form-title span {
  margin-right: 5px;
  opacity: 1;
  visibility: visible;
  -webkit-transition: all 0.3s ease;
}
</style>