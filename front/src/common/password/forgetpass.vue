<template>
  <el-dialog
    v-dialogDrag
    title="忘记密码"
    :visible.sync="dialog"
    :close-on-click-modal="false"
    append-to-body
    class="type-one-dialog"
    width="500px"
  >
    <el-form ref="form" :model="form" :rules="rules">
      <el-form-item label="邮箱：" prop="email" :label-width="formLabelWidth">
        <el-input
          type="text"
          v-model="form.email"
          clearable
          placeholder="邮箱"
        ></el-input>
      </el-form-item>
      <el-form-item label="验证码：" prop="code" :label-width="formLabelWidth">
        <span>
          <el-input
            class="code"
            type="text"
            v-model="form.code"
            maxlength="6"
            clearable
            placeholder="验证码"
          ></el-input>
          <el-button type="primary" ref="codebutton" :disabled="disabled" :loading="disabled" @click="getcode">{{
            state
          }}</el-button>
        </span>
      </el-form-item>
      <el-form-item label="新密码：" prop="newPass" :label-width="formLabelWidth">
        <el-input
          type="password"
          show-password
          v-model="form.newPass"
          maxlength="15"
          clearable
          placeholder="请输入新密码"
        ></el-input>
      </el-form-item>
      </el-form-item>
    </el-form>
    <div slot="footer">
      <el-button type="primary" @click="click">确定</el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import Md5 from "js-md5";
export default {
  name: "forgetpass",
  data() {
    return {
      formLabelWidth: "120px",
      dialog: false,
      disabled: false,
      form: {
        email: "",
        code: "",
        newPass: ""
      },
      pushform:{
        code: "",
        newPass: ""
      },
      state: "获取验证码",
      rules: {
        email: [{ required: true, message: "邮箱不可为空", trigger: "blur" }],
        code: [{ required: true, message: "验证码不能为空", trigger: "blur" }],
        newPass: [
          { required: true, message: "密码不可为空", trigger: "blur" }],
      },
    };
  },
  methods: {
    init() {
      this.dialog = true;
      this.clearForm();
    },
    clearForm() {
      this.form = {
        email:"",
        code: "",
        newPass: ""
      };
    },
    cancel() {
      this.dialog = false;
    },
    getcode() {
      if(this.form.email === ""){
        this.$message.error("邮箱不能为空!");
        return;
      }
      let time = 60;
      let timer = setInterval(() => {
        if (time == 0) {
          clearInterval(timer);
          this.state = "获取验证码";
          this.disabled = false;
        } else {
          this.disabled = true;
          this.state = time + "秒后重试";
          time--;
        }
      }, 1000);
      this.axios.get("/user/getcode/"+this.form.email);
    },
    click() {
      this.$refs["form"].validate(async (valid) => {
        if (valid) {
          this.pushform.code = this.form.code;
          this.pushform.newPass = Md5(this.form.newPass);
          const { data: res } = await this.axios.put("/user/forgotPass", this.pushform);
          if (res.code === 200) {
            this.$message.success({
              message: "密码已重置，请尝试登录！",
            });
            this.$router.push("/login");
            // 退出登录时，清除身份信息
            localStorage.clear();
            this.dialog = false;
          } else {
            this.$message.error(res.msg);
          }
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.code {
  width: 58%;
  margin-right: 7px;
}
</style>
