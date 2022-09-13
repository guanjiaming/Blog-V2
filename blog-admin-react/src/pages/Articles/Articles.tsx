import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import {Form, Row, Col, Input, Button, Table, Space, Modal, message} from 'antd';
import {ExclamationCircleOutlined} from '@ant-design/icons';
import Layout from "../../components/Layout/Layout";
import service from "../../apis/apis"

interface DataType {
    id: number
    key: React.Key
    name: string
    tags: string[]
    time: string
}


const Articles = () => {
    const [form] = Form.useForm();
    const history: any = useHistory()

    /**
     * 请求接口
     * @param page
     * @param rows
     * @param keywords
     */
    async function getData(page: number, rows: number, keywords?: string) {
        try {
            const param = {
                page: page,
                rows: rows
            }
            // @ts-ignore
            keywords && (param.keywords = keywords)
            const res:any = await service.fetchArticleListPage(param)
            if(res.code == 404) {
                message.error("示查询到相关文章")
                setTableData([])
                return
            }

            const tagRes: any = await service.fetchTagList();

            const map: Map<number, string> = new Map<number, string>();

            for (let i = 0; i < tagRes.items.length; i++) {
                map.set(tagRes.items[i].id, tagRes.items[i].name);
            }

            const map2 = res.items.map((item: any, index: number) => {
                let tagNames = "";
                item.tagIds.forEach((tag: number, i: number) => {
                    i && (tagNames += "、")
                    tagNames += map.get(tag)
                })
                return {
                    id: item.id,
                    key: index,
                    name: item.title,
                    tags: tagNames,
                    time: item.createTime,
                }
            })
            setTableData(map2)
        } catch (e) {
            console.log(e);
        }
    }

    /**
     * 搜索文章
     * @param values 字段对象
     */
    async function onFinish(values: any) {
        setPage(1);
        setRows(5);
        await getData(page, rows, values.keywords)
    }

    /**
     * 重置表单
     */
    async function resetForm() {
        form.resetFields();
        setPage(1);
        setRows(5);
        await getData(page, rows)
    }

    const rowSelection = {
        onChange: (selectedRowKeys: React.Key[], selectedRows: DataType[]) => {
            console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        },
        getCheckboxProps: (record: DataType) => ({
            disabled: record.name === 'Disabled User',
            name: record.name,
        }),
    };

    /*  表格数据 */
    const [tableData, setTableData] = useState<Array<DataType>>([]);
    const [page, setPage] = useState(1);
    const [rows, setRows] = useState(10);
    // const [sortBy, setSortBy] = useState("createTime") 排序先不做

    useEffect(() => {
        getData(page, rows).catch(e => {
            console.log(e);
        })
    }, [])

    /**
     * 去编辑文章页面
     * @param id {Number}
     */
    function onToEdit(id: number) {
        history.push(`/article/${id}`);
    }

    /**
     * 去新增文章页面
     */
    function onToAdd() {
        history.push(`/article/add`)
    }

    /**
     * 删除文章
     * @param id {Number}
     * @param index
     */
    function onDelete(id: number, index: any) {
        Modal.confirm({
            title: '确诊删除吗',
            icon: <ExclamationCircleOutlined/>,
            okText: '确认',
            cancelText: '取消',
            onOk: async () => {
                try {
                    // alert("delete:" + id)
                    console.log(index)
                    const res = await service.fetchDeleteArticle(id)
                    if(res.status === 204) {
                        message.success("删除成功")
                        tableData.splice(index, 1)
                        const result = tableData.map(item => item)
                        setTableData(result)
                    }
                } catch (e) {
                    console.error(e);
                }

            }
        })
    }

    const columns = [
        {
            title: '文章标题',
            dataIndex: 'name',
            key: 'name'
        },
        {
            title: '标签',
            dataIndex: 'tags',
            key: 'tags',
        },
        {
            title: '发布时间',
            dataIndex: 'time',
            key: 'time',
        },
        {
            title: 'Action',
            key: 'action',
            render: (text: string, record: DataType) => (
                <Space size="middle">
                    <a onClick={() => onDelete(record.id, record.key)} style={{color: "#f5222d"}}> 删除</a>
                    <a onClick={() => onToEdit(record.id)}> 编辑 </a>
                </Space>
            ),
        },
    ];

    return (
        <Layout>
            {/* 查询表单 */}
            <Form
                form={form}
                name="advanced_search"
                className="advanced-search-form"
                onFinish={onFinish}
            >
                <Row>
                    <Col span={8}>
                        <Form.Item
                            name="keywords"
                            label="搜索文章"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入查询关键词',
                                },
                            ]}
                        >
                            <Input placeholder="关键词" name="tag"/>
                        </Form.Item>
                    </Col>
                    <Col span={8}>
                        <Button style={{margin: "0 8px"}} type="primary" htmlType="submit"> 搜索 </Button>
                        <Button onClick={() => resetForm()}> 重置 </Button>
                    </Col>
                </Row>
            </Form>

            {/* 新增 */}
            <Button type="primary" onClick={() => onToAdd()}> 创建一篇文章 </Button>
            <br/>
            <br/>

            {/* table数据展示 */}
            <Table
                rowSelection={{
                    type: "checkbox",
                    ...rowSelection,
                }}
                dataSource={tableData}
                columns={columns}
            />
        </Layout>
    )
}

export default Articles;