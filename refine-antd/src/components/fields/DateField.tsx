import React from "react";
import { DateFieldProps } from "@refinedev/antd";
import dayjs from "dayjs";

export const DateField: React.FC<DateFieldProps> = ({ value, format = "YYYY-MM-DD HH:mm:ss" }) => {
    if (!value) {
        return null;
    }
    // 如果是今天，则显示时间，否则显示日期
    if (dayjs(value).isSame(dayjs(), "day")) {
        return <span>{dayjs(value).format('HH:mm:ss')}</span>;
    } else {
        return <span>{dayjs(value).format(format)}</span>;
    }
};