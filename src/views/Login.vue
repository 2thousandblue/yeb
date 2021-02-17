<template>
  <div id="bg">
    <el-form ref="loginForm" :rules="rules" v-loading="loading"
             element-loading-text="正在登录..."
             element-loading-spinner="el-icon-loading"
             element-loading-background="rgba(20, 150, 200, 0.1)"
             :model="loginForm"
             class="loginContainer">
      <h3 class="loginTitle">办公登录系统</h3>

      <el-form-item prop="username">
        <el-input type="text" v-model="loginForm.username"
                  placeholder="请输入用户名" style="width: 80%;">
          <template slot="prepend">账号</template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" auto-complete="off"
                  placeholder="请输入密码" style="width: 80%;">
          <template slot="prepend">密码</template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code" label="验证码"
                    label-width="65px">

        <el-input type="text" v-model="loginForm.code" auto-complete="off"
                  placeholder="点击图片更换验证码" @keydown.enter.native="submitLogin" style="width: 60%;">

        </el-input>
        <img :src="captchaUrl" @click="updateCaptcha" alt="" style="cursor: pointer">
      </el-form-item>
      <el-checkbox size="normal" class="loginRemember" v-model="checked">记住我</el-checkbox>
      <div class="Button">
        <el-button type="primary" class="loginButton" v-on:click="submitLogin">登录
        </el-button>
        <!--        <el-button type="primary" class="registerButton" router-link to="hello">注册-->
        <!--        </el-button>-->
      </div>
    </el-form>
  </div>
</template>

<script>

export default {
  name: "Login",
  data() {
    return {
      loading: false,
      captchaUrl: '/captcha.jpg?time=' + new Date().getTime(),
      loginForm: {
        username: 'admin',
        password: '123',
        code: ''
      },
      checked: true,
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}],
        code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
      }
    }
  },
  methods: {
    updateCaptcha() {
      this.captchaUrl = '/captcha.jpg?time=' + new Date().getTime();
    },
    submitLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.postRequest('/login', this.loginForm).then(resp => {
            this.loading = false;
            if (resp) {
              this.$store.commit('INIT_CURRENTHR', resp.obj);
              window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
              let path = this.$route.query.redirect;
              this.$router.replace((path == '/' || path == undefined) ? '/home' : path);
            } else {
              this.captchaUrl = '/captcha.jpg?time=' + new Date().getTime();
            }
          })
        } else {
          return false;
        }
      });
    }
  }
}
</script>

<style>
#bg {
  background: url("../assets/bg1.jpg") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;

}

.loginContainer {
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  /*justify-content: center;*/
  /*align-items: center;*/
  width: 350px;
  height: auto;
  padding: 45px 35px 40px 35px;
  background: #fff;
  border: 2px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.Button {
  display: flex;
  justify-content: space-around;
}

.loginButton {
  display: flex;
  width: 30%;

}

.registerButton {
  display: flex;
  width: 30%;
}


.loginTitle {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.loginRemember {
  text-align: left;
  margin: 0px 0px 15px 0px;
}

.el-form-item__content {
  display: flex;
  align-items: center;
}
</style>
