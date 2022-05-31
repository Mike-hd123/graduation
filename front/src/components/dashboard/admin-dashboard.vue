<template>
  <div>
    <!--个人信息-->
    <el-card class="content" style="width: 60%;margin: 12px">
      <div class="title">
        <i class="el-icon-user-solid"></i> 个人信息
        <div style="float: right">
          <el-link :underline="false" class="edit" @click="editInfo">修改资料</el-link>
        </div>
      </div>
      <el-row style="color: #666666;padding: 20px">
        <el-col :span="6" style="margin-left: 10px;margin-top: 10px" class="upload" v-loading="loading">
            <el-upload
              action=""
              :http-request="submitUpload"
              :on-change="onchange"
              :show-file-list="false"
              accept=".png,.jpg,.jpeg"
            >
              <div class="header">
                <el-image :src="imageUrl" class="header imgStyle">
                  <div slot="error" class="image-slot">
                    <div style="width: 120px; height: 140px">
                      <i class="el-icon-user-solid"></i>
                    </div>
                  </div>
                </el-image>
              </div>
              <div class="camera" @click="submitUpload"><i class="el-icon-camera"></i></div>
            </el-upload>
        </el-col>
        <el-col :span="14" style="margin-left: 50px">
          <h2 class="distance" style="margin-bottom: 35px;margin-left: 15px">{{userInfo.realName}}</h2>
          <div class="distance">
            <i class="el-icon-male" v-if="userInfo.sex === 0" title="性别"> {{sexName}}</i>
            <i class="el-icon-female" v-else title="性别"> {{sexName}}</i>{{'&#12288'}}{{'&#12288'}}
            <i class="el-icon-phone-outline" title="联系方式"> {{userInfo.phone}}</i> {{'&#12288&#12288'}}
            <i class="el-icon-message" title="邮箱"> {{userInfo.email}}</i>
          </div>
        </el-col>
      </el-row>
    </el-card>
    <editInfo ref="editInfo_model" @refresh="refresh"></editInfo>
    <VmUpload ref="upload_model" @propUrl="propUrl"></VmUpload>
  </div>
</template>

<script>
  import VmUpload from './model/upload-model'
  import editInfo from './model/admin-edit-model'
  export default {
    name: "dashboard",
    data () {
      return {
        imageUrl: '',
        userInfo: {},
        sexName: '男',
        loading:''
      }
    },
    methods: {
      getUserInfo () {
        let userInfo = this.userInfo;
        this.sexName = userInfo.sex === 0 ? '男' : '女';
      },
      editInfo () {
        let userInfo = Object.assign({}, this.userInfo);
        this.$refs.editInfo_model.init(userInfo);
      },
      refresh () {
        // 刷新页面
        this.reload();
      },
      propUrl (url) {
        this.imageUrl = url;
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
        }, 500);
      },
      onchange () {
        const event = window.event;
        const data = event.target.files[0];
        const reader = new FileReader();
        //转base64
        reader.onload = e => {
          const imageUrl = e.target.result; //将图片路径赋值给src
          this.$refs.upload_model.init(imageUrl, this.form)
        };
        reader.readAsDataURL(data);
      },
      submitUpload (params) {
        const file = params.file;
        const fileType = file.type;
        const isImage = fileType.indexOf("image") !== -1;
        const isLt2M = file.size / 1024 / 1024 < 2;
        // 这里常规检验，看项目需求而定
        if (!isImage) {
          this.$message.warning({
            message: '只能上传图片格式png、jpg!'
          });
          return;
        }
        if (!isLt2M) {
          this.$message.warning({
            message: '只能上传图片大小小于2M'
          });
          return;
        }
        // 根据后台需求数据格式
        this.form = new FormData();
        // 文件对象
        this.form.append("file", file);
        this.form.append("id", this.userInfo.id);
        this.form.append("type", this.userInfo.type);
      },
      getHead() {
        const obj = {
          userId: this.userInfo.id,
          type: this.userInfo.type
        };
        this.axios.get('/user/getFile').then(
          response => {
            this.imageUrl = response.data.data;
          }).catch(error => {
          this.$message.error({
            message: '获取头像失败'
          }, error)
        })
      }
    },
    computed: {
      collapse() {
        return this.$store.state.collapse;
      }
    },
    watch: {
      collapse() {
        if (this.$refs['chart']) {
          setTimeout(() => {
            this.$refs['chart'].resize()
          }, 150)
        }
      }
    },
    mounted() {
      this.userInfo = JSON.parse(localStorage.userInfo);
      this.getUserInfo();
      this.getHead();
    },
    components: {
      editInfo, VmUpload
    }
  }
</script>

<style scoped>
  .header {
    background-color: #ccc;
    font-size: 80px;
    color: white;
    width: 120px;
    border-radius: 5px;
    height: 140px;
    line-height: 140px;
    text-align: center;
    transition: .2s;
  }
  .upload:hover .imgStyle{
    opacity: 0.2;
  }
  /*“修改我的头像” 样式*/
  .upload:hover .header::after {
    transition: .2s;
    content: "修改我的头像";
    font-size: 13px;
    position: absolute;
    right: 20px;
    top: 30px;
    color: #333;
  }
  /*父组件大小*/
  .upload {
    position: relative;
    top: 0;
    left: 0;
    width: 120px;
  }
  /*照相icon样式*/
  .camera {
    color: #555;
    font-size: 35px;
    position: absolute;
    top: 35px;
    right: 42px;
    opacity: 0;
    cursor: pointer;
  }
  /*显示照相icon*/
  .upload:hover .camera {
    transition: .2s;
    opacity: 1;
  }

  .distance {
    margin: 15px 0;
  }
  .title {
    font-size: 18px;
    background-color: #f4fcfe;
    border-bottom: 1px solid #dcdcdc;
    height:50px;
    line-height: 50px;
    padding: 0 20px 0 20px;
    color: #0089AB;
  }
  .content {
    height: 300px;
    float: left;
  }
  .content:hover {
    background-color: #F5FAF5;
    box-shadow: 5px 8px 6px 0px rgba(70,88,131,0.2);
  }
  .edit:hover {
    transform:  scale(1.1)
  }
</style>
<style>
  #app , body {
    padding: 0;
    margin: 0;
  }
</style>
