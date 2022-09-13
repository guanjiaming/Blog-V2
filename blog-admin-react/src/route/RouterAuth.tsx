// 路由拦截
import {
    Route,
    Redirect,
    useLocation, useParams,
} from "react-router-dom";
// @ts-ignore
import Cookies from "js-cookie";

import routerMap from "./RouterMap";

function RouterAuth() {
    const isLogin = Cookies.get("UM_TOKEN");
    const { pathname } = useLocation();

    /* 查找当前url路径是否有路由配置里有，没有的话就是404 */
    const targetRouterConfig = routerMap.find(
        (item: any) => {

            // 这个判断是为了处理带变量的路径
            if(item.path.match(":")) {
                return pathname.includes(item.path.split(":")[0])
            }
            return item.path === pathname
        }
    )
    // 不需要登录 并且 未登录
    if (targetRouterConfig && !targetRouterConfig.auth && !isLogin) {
        const component = targetRouterConfig.component
        return <Route exact path={pathname} component={component}/>
    }

    if (isLogin) {
        if (pathname === "/login") {
            return <Redirect to="/profile"/>
        } else {
            if (targetRouterConfig) {
                return (
                    <Route path={targetRouterConfig.path} exact component={targetRouterConfig.component}/>
                )
            } else {
                return <Redirect exact to="/404"/>;
            }
        }
    } else { // 未登录
        if (targetRouterConfig && targetRouterConfig.auth) {
            return <Redirect exact to="/login"/>
        } else {
            return <Redirect exact to="/404"/>;
        }
    }

}

export default RouterAuth;