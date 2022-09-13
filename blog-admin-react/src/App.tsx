import React from 'react';
import {
    BrowserRouter as Router, Route,
    Switch,
    // Route
} from 'react-router-dom'
import './App.less';
import "./theme.less"

import RouterAuth from "./route/RouterAuth";
// import Article from "./pages/Article/Article";

function App() {
    return (
        <Router basename="/blog/manage">
            <Switch>
                <RouterAuth />
                {/*<Route exact path="/">*/}
                {/*    <Profile/>*/}
                {/*</Route>*/}
                {/*<Route path="/article/:id">*/}
                {/*    <Article />*/}
                {/*</Route>*/}
                {/*<Route path="/login">*/}
                {/*    <Login/>*/}
                {/*</Route>*/}
            </Switch>
        </Router>
    );
}

export default App;
