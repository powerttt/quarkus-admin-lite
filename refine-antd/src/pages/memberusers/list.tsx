import React, { useState } from "react";
import { BaseRecord, useShow } from "@refinedev/core";
import dayjs from "dayjs";
import { MemberUserShow } from "./index";
import {
    useTable,
    List,
    EditButton,
    ShowButton,
    TagField,
    EmailField,
    useModalForm,
    DeleteButton
} from "@refinedev/antd";
import { DateField } from "@/components/fields/DateField";

import {
    Table, Space,
    Select,
    DatePicker,
    Form, Input, Modal, Spin, Typography, Avatar,

} from "antd";
import Field from '@ant-design/pro-field';

import { createStyles } from 'antd-style';

import { ROLE_OPTIONS, STATUS_OPTIONS } from "./constants"

const { Title, Text } = Typography;


const useStyles = createStyles(({ token }) => ({
    textDescription: {
        color: token.colorTextDescription,
        fontSize: token.fontSize,
    }
}));

export const MemberUserList = () => {
    const { styles, cx, theme } = useStyles();
    const { tableProps } = useTable({
        syncWithLocation: true,
    });

    // 创建模态框
    const {
        modalProps: createModalProps,
        formProps: createFormProps,
        show: createModalShow,
        formLoading: createFormLoading,
    } = useModalForm({
        action: "create",
        syncWithLocation: true,
    });


    // 编辑模态框
    const {
        modalProps: editModalProps,
        formProps: editFormProps,
        show: editModalShow,
        formLoading: editFormLoading,
        query: editQuery,
    } = useModalForm({
        action: "edit",
        syncWithLocation: true,
    });

    // 显示模态框
    const [visibleShowModal, setVisibleShowModal] = useState<boolean>(false);

    const { query: queryResult, setShowId } = useShow();

    const { data: showQueryResult } = queryResult;
    const record = showQueryResult?.data;

    return (
        <>
            <List
                createButtonProps={{
                    onClick: () => {
                        createModalShow();
                    },
                }}
            >
                <Table {...tableProps} rowKey="id">
                    <Table.Column dataIndex="id" title="Id" />
                    <Table.Column dataIndex="nickname" title="Nickname" />
                    {/* https://www.dicebear.com/styles/thumbs/ */}
                    {/* avatar https://api.dicebear.com/9.x/thumbs/svg?seed=t */}
                    <Table.Column
                        dataIndex="avatar"
                        title="Avatar"
                        render={(value: any) => <Avatar src={value} />}
                    />
                    <Table.Column
                        dataIndex="roles"
                        title="Roles"
                        render={(value: any[]) => (
                            <>
                                {value?.map((item) => (
                                    <TagField value={item} key={item} />
                                ))}
                            </>
                        )}
                    />
                    {/* STATUS_OPTIONS */}
                    <Table.Column
                        dataIndex="status"
                        title="Status"
                        render={(value: any) => (
                            <TagField
                                value={STATUS_OPTIONS.find(
                                    (item) => item.value == value
                                )?.label}
                                color={STATUS_OPTIONS.find(
                                    (item) => item.value == value
                                )?.color}
                            />
                        )}
                    />
                    <Table.Column dataIndex="mobile" title="Mobile" />
                    <Table.Column
                        dataIndex={["email"]}
                        title="Email"
                        render={(value: any) => <EmailField value={value} />}
                    />
                    {/* <Table.Column
                        dataIndex={["registerAt"]}
                        title="Register At"
                        render={(value: any) => <DateField value={value} />}
                    />
                    <Table.Column
                        dataIndex={["loginAt"]}
                        title="Login At"
                        render={(value: any) => <DateField value={value} />}
                    /> */}
                    <Table.Column
                        dataIndex={["updateAt"]}
                        title="Update At"
                        render={(value: any) => <DateField value={value} />}
                    />
                    {/* <Table.Column dataIndex="updater" title="Updater" /> */}
                    {/* <Table.Column dataIndex="registerIp" title="Register Ip" />
                    <Table.Column dataIndex="loginIp" title="Login Ip" /> */}

                    <Table.Column dataIndex="refCode" title="Ref Code" />
                    <Table.Column
                        title="操作"
                        dataIndex="actions"
                        render={(_, record: BaseRecord) => (
                            <Space>
                                <EditButton
                                    hideText
                                    size="small"
                                    recordItemId={record.id}
                                    onClick={() => editModalShow(record.id)}
                                />
                                <ShowButton
                                    hideText
                                    size="small"
                                    recordItemId={record.id}
                                    onClick={() => {
                                        setShowId(record.id);
                                        setVisibleShowModal(true);
                                    }}
                                />
                                <DeleteButton hideText size="small" recordItemId={record.id} />
                            </Space>
                        )}
                    />
                </Table>
            </List>

            {/* 创建模态框 */}
            <Modal {...createModalProps}>
                <Spin spinning={createFormLoading}>

                    <Form {...createFormProps} layout="vertical">
                        <Form.Item
                            label="Id"
                            name={["id"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                        >
                            <Input readOnly disabled />
                        </Form.Item>
                        <Form.Item
                            label="Nickname"
                            name={["nickname"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        {/* https://api.dicebear.com/9.x/thumbs/svg?seed={nickname} */}
                        <Form.Item

                            label="Avatar"
                            name={["avatar"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >

                            <Input
                                placeholder="https://api.dicebear.com/9.x/thumbs/svg?seed=自定义"
                            />

                        </Form.Item>
                        <Form.Item shouldUpdate>
                            {/* avatar */}
                            {() => {
                                return <>
                                    <Title level={5}>格式</Title>
                                    <Text>
                                        https://api.dicebear.com/9.x/thumbs/svg?seed=自定义
                                    </Text>
                                    <Avatar src={(createFormProps.form?.getFieldsValue([['avatar']]))['avatar']} />
                                </>
                            }}
                        </Form.Item>

                        <Form.Item
                            label="Roles"
                            name={["roles"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Select
                                mode="multiple"
                                allowClear
                                style={{ width: "100%" }}
                                placeholder="Please select"
                                options={ROLE_OPTIONS}
                            >
                            </Select>
                        </Form.Item>
                        <Form.Item
                            label="Status"
                            name={["status"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >

                            <Select
                                style={{ width: "100%" }}
                                placeholder="Please select"
                                options={STATUS_OPTIONS}
                            >
                            </Select>

                        </Form.Item>
                        <Form.Item
                            label="Mobile"
                            name={["mobile"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Email"
                            name={["email"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Register Ip"
                            name={["registerIp"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Register At"
                            name={["registerAt"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                            getValueProps={(value) => ({
                                value: value ? dayjs(value) : undefined,
                            })}
                        >
                            <DatePicker
                                format={{
                                    format: 'YYYY-MM-DD HH:mm:ss',
                                    type: 'mask',
                                }} />
                        </Form.Item>


                        
                        <Form.Item
                            label="Ref Code"
                            name={["refCode"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                    </Form>

                </Spin>
            </Modal>

            {/* 编辑模态框 */}
            <Modal {...editModalProps}>
                <Spin spinning={editFormLoading}>
                    <Form {...editFormProps} layout="vertical">
                        <Form.Item
                            label="Id"
                            name={["id"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input readOnly disabled />
                        </Form.Item>
                        <Form.Item
                            label="Nickname"
                            name={["nickname"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item

                            label="Avatar"
                            name={["avatar"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >

                            <Input placeholder="https://api.dicebear.com/9.x/thumbs/svg?seed=自定义"
                            />

                        </Form.Item>
                        <Form.Item shouldUpdate>
                            {/* avatar */}
                            {() => {
                                return <>
                                    <Title level={5}>格式</Title>
                                    <Text>
                                        https://api.dicebear.com/9.x/thumbs/svg?seed=自定义
                                    </Text>
                                    <Avatar src={(editFormProps.form?.getFieldsValue([['avatar']]))['avatar']} />
                                </>
                            }}
                        </Form.Item>

                        <Form.Item
                            label="Roles"
                            name={["roles"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Select
                                mode="multiple"
                                allowClear
                                style={{ width: "100%" }}
                                placeholder="Please select"
                                options={ROLE_OPTIONS}
                            >
                            </Select>
                        </Form.Item>
                        <Form.Item
                            label="Status"
                            name={["status"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >

                            <Select
                                style={{ width: "100%" }}
                                placeholder="Please select"
                                options={STATUS_OPTIONS}
                            >
                            </Select>
                        </Form.Item>
                        <Form.Item
                            label="Mobile"
                            name={["mobile"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Email"
                            name={["email"]}
                            rules={[
                                {
                                    required: true,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Register Ip"
                            name={["registerIp"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Register At"
                            name={["registerAt"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                            getValueProps={(value) => ({
                                value: value ? dayjs(value) : undefined,
                            })}
                        >
                            <DatePicker
                                format={{
                                    format: 'YYYY-MM-DD HH:mm:ss',
                                    type: 'mask',
                                }} />
                        </Form.Item>
                        <Form.Item
                            label="Login Ip"
                            name={["loginIp"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Login At"
                            name={["loginAt"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                            getValueProps={(value) => ({
                                value: value ? dayjs(value) : undefined,
                            })}
                        >
                            <DatePicker
                                format={{
                                    format: 'YYYY-MM-DD HH:mm:ss',
                                    type: 'mask',
                                }} />
                        </Form.Item>
                        <Form.Item
                            label="Update At"
                            name={["updateAt"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                            getValueProps={(value) => ({
                                value: value ? dayjs(value) : undefined,
                            })}
                        >
                            <DatePicker
                                format={{
                                    format: 'YYYY-MM-DD HH:mm:ss',
                                    type: 'mask',
                                }} />
                        </Form.Item>
                        <Form.Item
                            label="Updater"
                            name={["updater"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                        <Form.Item
                            label="Ref Code"
                            name={["refCode"]}
                            rules={[
                                {
                                    required: false,
                                },
                            ]}
                        >
                            <Input />
                        </Form.Item>
                    </Form>
                </Spin>
            </Modal >

            {/* 显示模态框 */}
            <Modal Modal Modal
                visible={visibleShowModal}
                onCancel={() => setVisibleShowModal(false)}
                title="用户详情"
            >
                {/* <Title level={5}>ID</Title>
                <Text>{record?.id}</Text>

                <Title level={5}>昵称</Title>
                <Text>{record?.nickname}</Text> */}

                {/* 添加其他需要显示的用户信息 */}
                <MemberUserShow query={queryResult} />
            </Modal >
        </>
    );
};
