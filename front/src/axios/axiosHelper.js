import axios from "axios";
import { Message } from "element-ui";

axios.defaults.timeout = 5000; //超时终止请求
axios.defaults.baseURL = "/api"; //配置请求地址

// var loadingInstance
axios.interceptors.request.use(
  (config) => {
    //Ajax请求执行该方法，请求带上token
    var token = localStorage.getItem("Token");
    if (token !== null && token !== undefined && token !== "") {
      config.headers.Authorization = token;
    }
    return config;
  },
  (error) => {
    Message.error({
      message: "加载超时",
    });
    return Promise.reject(error);
  }
);
export default axios; //暴露axios实例
