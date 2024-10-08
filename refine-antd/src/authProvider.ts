import { AuthProvider } from "@refinedev/core";

import { dataProvider } from "./providers/simple-rest/provider";
const API_URL = import.meta.env.VITE_PUBLIC_BACKEND_URL;
const _dataProvider = dataProvider(API_URL)
export const USER_INFO_KEY = "qal_app_user_info";
import { QAL_JWT_TOKEN_NAME } from "@/lib/utils/constants"
import Cookies from "js-cookie"



function logoutHandler() {

  localStorage.removeItem(USER_INFO_KEY);
  Cookies.remove(QAL_JWT_TOKEN_NAME);
}

export const authProvider: AuthProvider = {

  register: async ({ username, email, password }) => {

    return {
      success: false,
      error: {
        name: "LoginError",
        message: "Invalid email or password",
      },
    };
  },
  login: async ({ username, email, password }) => {
    // console.log(username, email, password);

    if (email && password) {

      // 发送请求到后端进行登录
      const { data, code, msg } = await _dataProvider.create({
        resource: "memberUser/login",
        variables: {
          email: email,
          password: password,
        }
      })
      if (code === 0) {
        // login success 
        localStorage.setItem(USER_INFO_KEY, JSON.stringify(data));
        // exp = 1717699996
        Cookies.set(QAL_JWT_TOKEN_NAME, data.token, {
          path: "/",
          expires: new Date(data.exp * 1000),
        });

        return {
          success: true,
          redirectTo: "/",
        }
      } else {

        logoutHandler()

        return {
          success: false,
          error: {
            name: "LoginError",
            message: msg,
          },
        }
      }
    }

    return {
      success: false,
      error: {
        name: "LoginError",
        message: "Invalid email or password",
      },
    };
  },
  logout: async () => {
    logoutHandler()
    return {
      success: true,
      redirectTo: "/login",
    };
  },
  check: async () => {

    const userStr = localStorage.getItem(USER_INFO_KEY);
    const user = userStr ? JSON.parse(userStr) : null;
    if (user) {
      let expFlag = Number(user.exp) * 1000 < Date.now()
      if (expFlag) {
        logoutHandler()

      } else {
        return {
          authenticated: true
        };
      }
    }
    return {
      authenticated: false,
      redirectTo: "/login",
    };
  },
  getPermissions: async () => {
    const userStr = localStorage.getItem(USER_INFO_KEY);
    const user = userStr ? JSON.parse(userStr) : null;
    if (user) {
      return {
        id: user.uid,
        name: user.nickname,
        avatar: user.avatar,
        roles: user.roles,
      };
    }
    return null;
  },
  getIdentity: async () => {
    const userStr = localStorage.getItem(USER_INFO_KEY);
    const user = userStr ? JSON.parse(userStr) : null;
    if (user) {
      return {
        id: user.uid,
        name: user.nickname,
        avatar: user.avatar,
      };
    }
    return null;
  },
  onError: async (error) => {
    if (error.response?.status === 401) {
      return {
        logout: true,
      };
    }

    return { error };
  },
  // forgotPassword: async (params) => {
  //   if (params.email === authCredentials.email) {
  //     //we can send email with reset password link here
  //     return {
  //       success: true,
  //     };
  //   }
  //   return {
  //     success: false,
  //     error: {
  //       message: "Forgot password failed",
  //       name: "Invalid email",
  //     },
  //   };
  // },
};
