import React from 'react';
import Slide from './Slide'
import Main from './Main'
import './Layout.css'

function Layout(props: any) {
    return (
        <section className="layout">
            <Slide/>
            <Main>{props.children}</Main>
        </section>
    )
}

export default Layout;