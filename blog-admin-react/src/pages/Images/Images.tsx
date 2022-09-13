import Layout from "../../components/Layout/Layout";
import {Button, Col, Form, Input, Row} from "antd";
import service from '../../apis/apis'

function Images() {

    const [form] = Form.useForm();

    /**
     * 搜索
     */
    async function onFinish(values: any) {
        try {
            const res = await service.fetchPictureListPage(values.name);
        } catch (e) {

        }
    }

    function resetForm() {
        form.resetFields();

    }


    interface PictureItem {
        src: string;
        name: string;
        id: string;
    }
    /**
     *
     * @param list
     */
    function renderPictureItems(list: Array<PictureItem>) {
        let listArr: any = [];
        for (let i = 0; i < list.length; i++) {
            listArr.push(
                <li>
                    <img src={list[i].src} alt={list[i].name}/>
                </li>
            )
        }
        return (
            listArr
        )
    }

    return (
        <Layout>
            <Form
                form={form}
                name="form"
                onFinish={onFinish}
            >
                <Row>
                    <Col span={8}>
                        <Form.Item
                            name="name"
                            label="图片名称"
                            rules={[{
                                required: true,
                                message: "请输入图片名称"
                            }]}
                        >
                            <Input placeholder="图片名称" name="name"/>
                        </Form.Item>
                    </Col>
                    <Col span={2}>
                        <Button style={{margin: "0 8px"}} type="primary" htmlType="submit">搜索</Button>
                        <Button onClick={resetForm}>重置</Button>
                    </Col>
                </Row>
                <Row>
                    <Button type="primary"> 上传图片 </Button>
                </Row>
            </Form>

            <br/>
            <ul>
                {renderPictureItems([{id: "1", src: "123.png", name: "aaa"},{id: "1", src: "123.png", name: "aaa"}])}
            </ul>

        </Layout>
    )
}

export default Images;