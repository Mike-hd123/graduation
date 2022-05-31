<template>
  <div style="margin-top: 5px;">
    <el-form label-width="20px" class="form">
      <!--账号-->
      <el-form-item style="margin-top: 30px">
        <el-row>
          <el-col :span="2">
            <i class="el-icon-user"></i>
          </el-col>
          <el-col :span="22">
            <el-input v-model="form.username" maxlength="15"
                      style="width: 300px" clearable autofocus placeholder="请输入账号"></el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <!--密码-->
      <el-form-item>
        <el-row>
          <el-col :span="2">
            <a class="el-icon-lock"></a>
          </el-col>
          <el-col :span="22">
            <el-input type="password" v-model="prepassword" maxlength="30"
                      clearable placeholder="请输入密码" show-password></el-input>
          </el-col>
        </el-row>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="login()" class="loginMain" >
          <span>
            登&#12288陆
          </span>
        </el-button>
      </el-form-item>
    </el-form>

  <div class="tips">              
     <el-link type="white" style="font-size: 13px;" @click="forget">忘记密码</el-link>
  </div>

  <forgetpass ref="forget_password"></forgetpass>

  </div>
</template>

<script>
import ElSelectDropdown from "element-ui/packages/select/src/select-dropdown";
import forgetpass from "../../common/password/forgetpass";
import Md5 from 'js-md5'
export default {
  name: "Form",
  components: {
    ElSelectDropdown
  },
  data() {
      return {
          prepassword:'',
          form: {
              username: '',
              password: ''
          }
      }
  },
  methods: {
      clearForm () {
        this.form =  {
          username: '',
          password: ''
        };
        this.prepassword='';
      },
      async login () {
        // 登陆判断
        if(this.form.username === '') {
          this.$message.warning({
            message: '账号不能为空'
          })
        } else if (this.prepassword === '') {
          this.$message.warning({
            message: '密码不能为空'
          })
        } else {
          this.form.password = Md5(this.prepassword);
          const { data: res } = await this.axios.post('/user/login', this.form);
          if (res.code !== 200) return this.$message.error(res.msg);
          window.localStorage.setItem("Token", res.data);
          await this.getUser();
          this.$router.push("/dashboard");
        }
      },
      async getUser() {
      const { data: res } = await this.axios.get("/user/me");
      console.log(res.data);
      this.$store.commit("SAVE_USERINFO", res.data);
      this.userInfo = JSON.parse(localStorage.userInfo);
      },
      forget(){
        this.$refs.forget_password.init();
      }
  },
  created () {
      // 登录界面时，清除身份信息
      if(window.localStorage.getItem("Token") !== null){
        this.$message.success("已登录！");
        this.$router.push("/dashboard");
      }else{
        window.localStorage.removeItem("userInfo")
      }
  },
  components: {
    forgetpass,
  },
}
</script>

<style>
  .tips{
    float:left;
    margin-left: 162px;
  }

  /*登录表单的顶部*/
  .el-tabs--border-card > .el-tabs__header .el-tabs__item {
    height: 60px;
    width: 100px;
    font-size: 22px;
    line-height: 60px;
    font-weight: bold;
    text-align: center;
  }

  /*.el-tabs, .el-tabs__header {*/
  /*border-radius: 18px;*/
  /*}*/
</style>
<style scoped>
  /deep/ .form {
    margin-right: 20px;
  }
  /deep/ .loginMain {
    color: white;
    font-size: 18px;
    width: 200px;
    border-radius: 100px;
    margin-left: 70px;
    margin-top: -10px;
  }
  .loginMain span {
     cursor: pointer;
     display: inline-block;
     position: relative;
     transition: 0.4s;
  }
  .loginMain span:after {
    font-size: 28px;
    /*  \00bb: ">>"符号  */
    content: '\00bb';
    position: absolute;
    opacity: 0;
    top: -8px;
    right: -35px;
    transition: 0.4s;
  }
  .loginMain:hover span {
    padding-right: 35px;
  }
  .loginMain:hover span:after {
    opacity: 1;
    right: 0;
  }
  /deep/ .el-tabs {
    border-radius: 8px !important;
  }
  .deleteCook {
    float: right;
    height: 10px;
    width: 10px;
    margin-top: -18px;
  }
  .deleteCook:hover {
    transform: scale(1.2);
    font-weight: bold;
  }
  .circle {
    width: 4px;
    height: 4px;
    border-radius: 50%;
    background-color: gray;
    float: left;
    margin: 1px;
  }
  .userIcon {
    font-size: 18px;
    float: left;
    height: 50px;
    width: 35px;
    line-height: 50px;
  }
</style>
