import { HttpError } from "@refinedev/core";
import axios from "axios";
import { QAL_JWT_TOKEN_NAME } from "../../../lib/utils/constants"
import Cookies from "js-cookie"

const axiosInstance = axios.create();

axiosInstance.interceptors.request.use(
  (config) => {
    const token = Cookies.get(QAL_JWT_TOKEN_NAME);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

axiosInstance.interceptors.response.use(
  (response) => {

    if (response.data.code != 0 || response.data.msg) {
      const customError: HttpError = {
        message: response?.data?.msg,
        statusCode: response.data.code,
      };

      return Promise.reject(customError);
    }

    return response;
  },
  (error) => {
    console.log("error", error.response?.data);
    const customError: HttpError = {
      ...error,
      message: error.response?.data?.msg,
      statusCode: error.response?.status,
    };

    return Promise.reject(customError);
  },
);

export { axiosInstance };
