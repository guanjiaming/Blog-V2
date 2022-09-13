import React, {useEffect, useState} from "react";
import {Form, Row, Col, Input, Button, Modal, Table, Space, message} from 'antd';
import Layout from "../../components/Layout/Layout";
import {ExclamationCircleOutlined} from "@ant-design/icons";
import service from '../../apis/apis'
import DateUtils from "../../utils/DateUtils";

interface DataType {
    id: number,
    key: React.Key;
    name: string;
    time: string;
}

// rowSelection object indicates the need for row selection
const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: DataType[]) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
    },
    getCheckboxProps: (record: DataType) => ({
        disabled: record.name === 'Disabled User',
        name: record.name,
    }),
};

const Tags = () => {

    const [isShowModal, setIsShowModal] = useState(false);
    const [form] = Form.useForm();

    const [data, setData] = useState([]);

    useEffect(() => {
        service.fetchTagList().then((res: any) => {
            const map = res.items.map((item: any, index: number) => {
                return {
                    id: item.id,
                    key: index,
                    name: item.name,
                    time: DateUtils.formatDate(item.createTime),
                }
            })
            setData(map);
        }).catch(e => {
            console.error(e);
        })
    }, [])

    /**
     * 表单提交
     * @param values 字段对象
     */
    async function onFinish(values: any) {
        try {
            const res: any = await service.fetchTagByName(values.keywords)
            if(res.code !== 200) {
                message.error(res.msg);
                setData([]);
                return
            }
            const resTagItem: DataType[] = [{
                id: res.data.id,
                key: 0,
                name: res.data.name,
                time: DateUtils.formatDate(res.data.createTime),
            }]

            // @ts-ignore
            setData(resTagItem)
        } catch (e) {
            console.log(e);
        }
    }

    /**
     * 重置表单
     */
    async function resetForm() {
        try {
            form.resetFields();
            const res: any = await service.fetchTagList();
            const map = res.items.map((item: any, index: number) => {
                return {
                    id: item.id,
                    key: index,
                    name: item.name,
                    time: DateUtils.formatDate(item.createTime),
                }
            })
            setData(map);
        } catch (e) {
            console.log(e);
        }
    }

    /**
     * 打开新增标签的对话框
     */
    function openModal() {
        setIsShowModal(true);
    }

    const [modalLoading, setModalLoading] = useState(false);
    const [ModalForm] = Form.useForm();

    /**
     * 新增标签的表单提交
     * @param values
     */
    async function modalOnFinish(values: any) {
        try {
            console.log(values)
            setModalLoading(true)
            const apiname = values.id ? "fetchUpdateTag" : "fetchSaveTag"
            const res: any = await service[apiname]({
                id: values.id,
                name: values.tagName
            })
            if(res.status === 201 || res.status === 204) {
                message.success("成功")
                ModalForm.resetFields();
                await resetForm()
                setIsShowModal(false)
            } else {
                message.warning(res.msg)
            }
        } catch (e) {
            console.log(e);
        } finally {
            setModalLoading(false)
        }
    }

    async function onDeleteTag(id: number) {
        Modal.confirm({
            title: '确诊删除吗',
            icon: <ExclamationCircleOutlined/>,
            okText: '确认',
            cancelText: '取消',
            onOk: async () => {
                try {
                    const res: any = await service.fetchDeleteTag(id);
                    if(res.status === 204) {
                        message.success("删除成功")
                        const index = data.findIndex((item: DataType) => item.id === id)
                        data.splice(index, 1)
                        const result = data.map(item => item);
                        setData(result);
                    }
                } catch (e) {
                    console.log(e);
                }
            }
        })
    }

    function onEdit(record: DataType) {
        ModalForm.setFieldsValue({id: record.id, tagName: record.name})
        setIsShowModal(true);
    }

    const columns = [
        {
            title: '标签',
            dataIndex: 'name',
            key: 'name',
            render: (text: string, record: DataType) => <a onClick={() => onEdit(record)}>{text}</a>,
        },
        {
            title: '创建时间',
            dataIndex: 'time',
            key: 'time',
        },
        {
            title: 'Action',
            key: 'action',
            render: (text: string, record: DataType) => (
                <Space size="middle">
                    <a onClick={() => onDeleteTag(record.id)} style={{color: "#f5222d"}}> 删除 </a>
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
                            label="搜索标签"
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

            {/* 新增按钮 */}
            <Button type="primary" onClick={() => openModal()}> 新增标签 </Button>
            <br/>
            <br/>

            {/* 新增标签的表单 */}
            <Modal
                forceRender
                getContainer={false}
                visible={isShowModal}
                title={ModalForm.getFieldValue("id") ? "修改标签" : "新增标签"}
                onCancel={() => setIsShowModal(false)}
                footer={null}
            >
                <Form
                    form={ModalForm}
                    name="addForm"
                    onFinish={modalOnFinish}

                >
                    <Row>
                        <Col span={0}><Form.Item name="id"><Input hidden name="id" value={undefined}/></Form.Item></Col>
                        <Col span={16}>
                            <Form.Item
                                name="tagName"
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入标签名称',
                                    },
                                ]}
                            >
                                <Input placeholder="请输入标签名称" maxLength={20} name="tag name"/>
                            </Form.Item>
                        </Col>
                        <Col>
                            <Button style={{margin: "0 8px"}} type="primary" htmlType="submit" loading={modalLoading}>
                                {ModalForm.getFieldValue("id") ? "修改" : "新增"}
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </Modal>

            {/* table数据展示 */}
            <Table
                pagination={false}
                rowSelection={{
                    type: "checkbox",
                    ...rowSelection,
                }}
                dataSource={data}
                columns={columns}
            />
        </Layout>
    )
}

export default Tags;