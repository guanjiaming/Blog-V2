import './Login.scss';
import React, {
    // useEffect,
    useState
} from 'react';
import md5 from "md5";
import {Button, Input, Space, message} from "antd";
import {EyeInvisibleOutlined, EyeTwoTone} from "@ant-design/icons";
import {useHistory} from "react-router-dom";
import service from "../../apis/apis"
// @ts-ignore
import Cookies from "js-cookie";

function Login() {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    // useEffect(getData, [username, password]) // 相当于监听这个变量，变量改变时会触发这个函数
    const history: any = useHistory();

    async function onLogin() {
        try {
            if (username === "") {
            message.warning("pleas enter username")
            return
        }
        if (password === "") {
            message.warning("pleas enter password")
            return
        }
        // todo use api
        const formData = new FormData();
        formData.append("username", username);
        formData.append("password", md5(password));
        const res: any = await service.login(formData);
        console.log("password:" + md5(password));
        if(res.code == 200) {
            localStorage.setItem("user", JSON.stringify(res.data))
            // sessionStorage.setItem("token", res.data.token);
            Cookies.set("UM_TOKEN", res.data.token)
        // window.location.replace("/");
        // history.push("/")
        history.replace("/profile")
        } else {
            message.error(res.msg)
        }
        } catch (e) {
            console.error(e);
        }
    }

    // function getData() {
    //     console.log(username + " : " + password)
    // }

    function onChangeUsername(e: React.ChangeEvent<HTMLInputElement>) {
        setUsername(e.target.value)
    }

    function onChangePassword(e: React.ChangeEvent<HTMLInputElement>) {
        setPassword(e.target.value)
    }

    return (
        <section className="login-page">
            <div className="container">

              <h1 className="heading">UM, Login</h1>
              <Space direction="vertical">

                <Input
                  placeholder="input username"
                  value={username}
                  onChange={onChangeUsername}
                />

                <Input.Password
                  placeholder="input password"
                  iconRender={visible => (visible ? <EyeTwoTone/> : <EyeInvisibleOutlined/>)}
                  value={password}
                  onChange={onChangePassword}
                  onPressEnter={onLogin}
                />
              </Space>
              <Button type="primary" className="login_button" onClick={onLogin}> 登录 </Button>

            </div>
        </section>
    )
}

export default Login;
