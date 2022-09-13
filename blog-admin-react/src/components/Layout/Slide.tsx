import "./Slide.scss"
import React from 'react';
import {NavLink} from 'react-router-dom'
function Slide() {
    return (
        <section className="layout-slide">

            <section className="admin_name">
                小明的博客系统
            </section>

                <ul className="layout-slide-menu">
                    <li className="layout-slide-menu-item">
                        <NavLink to="/profile" activeClassName="cur"> 概况 </NavLink>
                    </li>
                    <li className="layout-slide-menu-item">
                        <NavLink to="/tags" activeClassName="cur"> 标签 </NavLink>
                    </li>
                    <li className="layout-slide-menu-item">
                        <NavLink to="/articles" activeClassName="cur"> 文章 </NavLink>
                    </li>
                    <li className="layout-slide-menu-item">
                        <NavLink to="/images" activeClassName="cur"> 素材管理 </NavLink>
                    </li>
                </ul>
        </section>
    )
}

export default Slide;
