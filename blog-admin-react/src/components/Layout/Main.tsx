import "./Main.scss"
import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom'
import {Button} from "antd";

function Main(props: any) {

    let [isLogin, setIsLogin] = useState(false);
    let [user, setUser] = useState({nickname: "", username: "", token: ""});

    useEffect(() => {
        setIsLogin(!!localStorage.getItem("user"));
        console.log(isLogin);
        if(isLogin) {
            setUser(JSON.parse(localStorage.getItem("user") as string));
        }
    }, [isLogin])

    function UserGreeting() {
        return (
            <>
                <span className="user"> 欢迎回来：{user.nickname} </span>
                <Button className="logout-btn"> 退出登录 </Button>
            </>
        )
    }

    function GuestGreeting() {
        return (
            <Link to="/login">请 登录 </Link>
        )
    }

    return (
        <section className="layout-main">
            <header className="header">
                {isLogin ? <UserGreeting/> : <GuestGreeting/>}
            </header>
            <div className="container">
                {props.children}
            </div>
        </section>
    )
}

export default Main;
