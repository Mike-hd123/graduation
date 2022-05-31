<template>
  <div class="login" :style="note">
    <div class="title">
      <h2><i class="el-icon-eleme"></i> 学生成绩管理系统</h2>
    </div>
    <div class="loginForm">
      <el-tabs type="border-card" @tab-click="tabClick" style="height: 300px">
        <Form ref="admin_tab"></Form>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import Form from "./form";

export default {
  data() {
    return {
      note: {
        backgroundImage: "url(" + require("../../assets/home.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100%",
      },
    };
  },
  methods: {
    tabClick(data) {
      this.$refs.admin_tab.clearForm();
    },
  },
  components: {
    Form,
  },
};
</script>
<style>
* {
  margin: 0;
  padding: 0;
}

html,
body,
#app,
.login {
  height: 100%;
}
</style>
<style scoped>
/deep/ .el-tabs {
  /*3d效果*/
  transform-style: preserve-3d;
  -webkit-perspective: 1000px;
  transition: 0.8s;
}

.loginForm {
  width: 400px;
  margin: 150px 580px 0 580px;
}
.title {
  padding: 10px 10px;
}
.oblique {
  background-color: #d5d5d5;
  transform: rotate(120deg);
  box-sizing: border-box;
}
</style>
