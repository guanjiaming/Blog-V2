/* Created by guanjiaming on 2021/12/17 */
import axios, { AxiosInstance, AxiosPromise, AxiosRequestConfig, AxiosError } from "axios";

/**
 * 通过自定义request
 * @param config
 * @constructor
 */
function Request(config: AxiosRequestConfig): AxiosPromise {
  const $axios: AxiosInstance = axios.create();
  let baseURL = "";
  console.log("import.meta.env.PROD", import.meta.env.PROD)
  if (import.meta.env.PROD) {
    // baseURL = "https://apis.guanjm.cn/"
    baseURL = "http://43.138.23.58:8080/"
  }
  config.baseURL = baseURL;

  serRequestHeader(config);
  interceptors($axios);

  // 设置请求头
  function serRequestHeader(config: AxiosRequestConfig) {
    if(config.method === "GET") {
      console.log(config.data);
      config.params = config.data
    }
    return config
  }

  // 拦截器
  function interceptors(instance: AxiosInstance): void {
    // 添加请求拦截器
    instance.interceptors.request.use(
      config => {
        return config;
      },
      (error: AxiosError) => {
        return Promise.reject(error);
      }
    );
    // 添加响应拦截器
    instance.interceptors.response.use(
      response => {
        // 对响应数据做点什么
        const { data } = response;
        return data;
      },
      (error: AxiosError) => {
        console.log(error);
        // 对响应错误做点什么
        return Promise.reject(error);
      }
    );
  }

  return $axios(config);
}

Request.get = function get(config: AxiosRequestConfig): AxiosPromise {
  config.method = "GET";
  return Request(config);
};

Request.post = function post(config: AxiosRequestConfig): AxiosPromise {
  config.method = "POST";
  return Request(config);
};

export default Request;
