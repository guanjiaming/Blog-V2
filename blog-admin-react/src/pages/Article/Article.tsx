import '@toast-ui/editor/dist/toastui-editor.css';
import {Editor} from "@toast-ui/editor";

import React, {useEffect, useState} from "react";
import {useHistory, useParams} from "react-router-dom";
import Layout from "../../components/Layout/Layout";
import {Button, Col, Form, Input, Row, Select, message} from "antd";
import service from '../../apis/apis'
import DateUtils from "../../utils/DateUtils";


const Article = () => {
    const {id} = useParams<any>();
    const history = useHistory();
    let editor: Editor;
    const [form] = Form.useForm();
    const [tagOptions, setTagOptions] = useState<Array<JSX.Element>>([])
    const {Option} = Select;

    useEffect(() => {
        if(!editor) {
            // @ts-ignore
            window.editor = editor = new Editor({
                // @ts-ignore
                el: document.querySelector('#editorSection'),
                previewStyle: 'vertical',
                height: '600px',
            });
        }
        // 处理页面首次数据
        getPageData()

    }, [])
    async function getPageData() {
        try {
            // 初始化标签数据
            const res: any = await service.fetchTagList();
            const map = res.items.map((item: any, index: number) => {
                return <Option value={item.id} key={item.id}>{item.name}</Option>
            })
            setTagOptions(map);

            if(id == "add") return
            // 文章数据回显
            const res2: any = await service.fetchArticleDetail(id);
            form.setFieldsValue({name: res2.title, tags: res2.tagIds})
            editor.setHTML(res2.content)

        } catch (e) {
            console.log(e);
        }
    }

    /**
     * 保存
     */
    async function onSave(values: any) {
        try {
            // console.log(values);
            // @ts-ignore
            // console.log(window.editor.getHTML()); replaceDataLanguage(window.editor.getHTML()),
            const param = {
                title: values.name,
                // @ts-ignore
                content: window.editor.getHTML(),
                tagIds: values.tags,
                isRecommend: false,
                isPublish: true
            }
            let apiName: string = "fetchSaveArticle"
            if( id != "add") {
                apiName = "fetchEditArticle"
                // @ts-ignore
                param.id = id;
            }

            // @ts-ignore
            const res: any = await service[apiName](param)
            if(res.status === 201 || res.status === 204) {
                await message.success("保存成功")
                history.go(-1);
            }
            if(res.code === 500) {
                message.error("保存失败")
            }

        }catch (e) {
            console.log(e);
        }
    }

    return (
        <Layout>
            <Form
                form={form}
                name="article-editor-form"
                onFinish={onSave}
            >
                <Row gutter={20} style={{margin: "20px 0"}}>
                    <Col span={9}>
                        <Form.Item
                            name="name"
                            label="文章标题"
                            rules={[{required: true, message: '请输入文章标题'}]}
                        >
                            <Input placeholder="文章标题" name="articleName"/>
                        </Form.Item>
                    </Col>
                    <Col span={9}>
                        <Form.Item
                            name="tags"
                            label="标签"
                            rules={[{required: true, message: '请选择标签'}]}
                        >
                            <Select
                                mode="multiple"
                                style={{width: '100%'}}
                                placeholder="请选择标签"
                            >
                                {tagOptions}
                            </Select>
                        </Form.Item>
                    </Col>
                    {/* 保存 */}
                    <Col span={6} style={{textAlign: "right"}}>
                        <Button style={{width: "160px"}} type="primary" htmlType="submit" size="large"> 保存 </Button>
                    </Col>
                </Row>

            </Form>

            <section id="editorSection">
            </section>
        </Layout>
    )
}

export default Article;
