import React from "react";
import { useShow } from "@refinedev/core";
import {
    Show,
    NumberField,
    TagField,
    TextField,
    EmailField,
    DateField,
} from "@refinedev/antd";
import { Typography } from "antd";

const { Title } = Typography;

export const MemberUserShow = ({ query }) => {
    // const { query } = useShow({ id });
    const { data, isLoading } = query;

    const record = data?.data;

    return (
        <Show isLoading={isLoading}>
            <Title level={5}>Id</Title>
            <NumberField value={record?.id ?? ""} />
            <Title level={5}>Nickname</Title>
            <TextField value={record?.nickname} />
            <Title level={5}>Roles</Title>
            {record?.roles?.map((item: any) => (
                <TagField value={item} key={item} />
            ))}
            <Title level={5}>Status</Title>
            <NumberField value={record?.status ?? ""} />
            <Title level={5}>Mobile</Title>
            <NumberField value={record?.mobile ?? ""} />
            <Title level={5}>Email</Title>
            <EmailField value={record?.email} />
            <Title level={5}>Register Ip</Title>
            <TextField value={record?.registerIp} />
            <Title level={5}>Register At</Title>
            <DateField value={record?.registerAt} />
            <Title level={5}>Login Ip</Title>
            <TextField value={record?.loginIp} />
            <Title level={5}>Login At</Title>
            <DateField value={record?.loginAt} />
            <Title level={5}>Update At</Title>
            <DateField value={record?.updateAt} />
            <Title level={5}>Updater</Title>
            <NumberField value={record?.updater ?? ""} />
            <Title level={5}>Ref Code</Title>
            <NumberField value={record?.refCode ?? ""} />
        </Show>
    );
};
