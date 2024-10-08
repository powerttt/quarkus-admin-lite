import { color } from "@chakra-ui/react";


export const STATUS = {
    0: "开启",
    1: "关闭",
    2: "永久关闭",
}
export const STATUS_OPTIONS = [
    { label: "开启", value: 0, color: "green" },
    { label: "关闭", value: 1, color: "red.800" },
    { label: "永久关闭", value: 2, color: "red" },
];

export const ROLE = {
    0: "Admin",
    1: "Member",
}
export const ROLE_OPTIONS = [
    { label: "Admin", value: "Admin", color: "blue" },
    { label: "Member", value: "Member", color: "green" },
];
