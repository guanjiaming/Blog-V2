import axios, {AxiosPromise, AxiosRequestConfig} from "axios";
// @ts-ignore
import Cookies from "js-cookie";

axios.defaults.timeout = 100000;
if(process.env.NODE_ENV === "production") {
    axios.defaults.baseURL = "http://43.138.23.58:8080/apis";
    // axios.defaults.baseURL = "/apis";
}
if(process.env.NODE_ENV === "development" || process.env.NODE_ENV === "test") {
    console.log("env", process.env.NODE_ENV)
    axios.defaults.baseURL = "/apis";
}

/**
 * 请求拦截器
 */
axios.interceptors.request.use(
    config => {
        config.headers = {
            "token": Cookies.get("UM_TOKEN"),
            "Content-Type": "application/json;charset=utf-8",
        }
        return config;
    },
    error => {
        console.error(error);
    }
)

axios.interceptors.response.use(
    response => {
        if (response && response.status === 200) {
            return response.data
        }
        return response
    },
    error => {
        return error
    }
)

export default function Request(config: AxiosRequestConfig): AxiosPromise {
    return axios(config)
}