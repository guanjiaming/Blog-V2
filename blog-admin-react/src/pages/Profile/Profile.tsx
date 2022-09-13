
import React, {CSSProperties, useEffect} from 'react'
import Layout from "../../components/Layout/Layout";
import {Line} from '@antv/g2plot';

function Profile() {

    useEffect(() => {
        const data = [
            {month: '1', value: 3},
            {month: '2', value: 4},
            {month: '3', value: 2},
            {month: '4', value: 0},
            {month: '5', value: 0},
            {month: '6', value: 0},
            {month: '7', value: 0},
            {month: '8', value: 0},
            {month: '9', value: 0},
            {month: '10', value: 0},
            {month: '11', value: 0},
            {month: '12', value: 0},
        ];
        const line: Line = new Line('line-demo', {
            data,
            xField: 'month',
            yField: 'value',
            smooth: true,
            point: {
                size: 5,
                shape: 'diamond',
                style: {
                    fill: 'white',
                    stroke: 'teal',
                    lineWidth: 2,
                }
            },
            // animation: {
            //     appear: {
            //         animation: 'path-in',
            //         duration: 3000,
            //     },
            // },
        });
        line.render();
    }, []);

    return (
        <Layout>
            <h1 style={HeadingStyle}>2022年发表文章数量</h1>
            <br/>
            <br/>
            <div id="line-demo"/>
        </Layout>
    )
}

const HeadingStyle: CSSProperties = {
    textAlign: "center",
}

export default Profile;