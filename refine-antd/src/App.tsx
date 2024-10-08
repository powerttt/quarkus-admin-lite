import {
  GitHubBanner,
  Refine,
  type AuthProvider,
  Authenticated,
} from "@refinedev/core";
import {
  useNotificationProvider,
  ThemedLayoutV2,
  ErrorComponent,
  AuthPage,
  RefineThemes,
} from "@refinedev/antd";
import {
  GoogleOutlined,
  GithubOutlined,
  DashboardOutlined,
  UserOutlined,
} from "@ant-design/icons";

import routerProvider, {
  NavigateToResource,
  CatchAllNavigate,
  UnsavedChangesNotifier,
  DocumentTitleHandler,
} from "@refinedev/react-router-v6";
import { BrowserRouter, Routes, Route, Outlet } from "react-router-dom";
import { App as AntdApp, ConfigProvider } from "antd";

import "@refinedev/antd/dist/reset.css";


import { MemberUserList, MemberUserCreate, MemberUserEdit, MemberUserShow } from "../src/pages/memberusers";
import { DashboardPage } from "../src/pages/dashboard";

import { dataProvider } from "./providers/simple-rest/provider";
import { authProvider } from "./authProvider";


// /api/admin-007fgVT0S
const API_URL = import.meta.env.VITE_PUBLIC_BACKEND_URL;

/**
 *  mock auth credentials to simulate authentication
 */
const authCredentials = {
  email: "admin@qal.fast",
  password: "123456",
};

const App: React.FC = () => {

  return (
    (<BrowserRouter>
      <GitHubBanner />
      <ConfigProvider theme={RefineThemes.Blue}>
        <AntdApp>
          <Refine
            authProvider={authProvider}
            dataProvider={dataProvider(API_URL)}
            routerProvider={routerProvider}
            resources={[{
              name: "dashboard",
              list: "/",
              meta: {
                label: "Dashboard",
                // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
                icon: <DashboardOutlined />,
              },
            }, {
              meta: {
                label: "用户管理",
                icon: <UserOutlined />,
              },
              name: "memberUser",
              list: "/memberUser",
              create: "/memberUser/create",
              edit: "/memberUser/edit/:id",
              show: "/memberUser/show/:id"
            }]}
            notificationProvider={useNotificationProvider}
            options={{
              syncWithLocation: true,
              warnWhenUnsavedChanges: true,
              title: {
                text: "Quarkus Admin Lite",
                icon: <svg xmlns="http://www.w3.org/2000/svg"><text x="50%" y="50%" font-size="12" fill="#0093fa" font-family="system-ui, sans-serif" text-anchor="middle" dominant-baseline="middle">
                  QAL
                </text></svg>
              }
            }}
          >
            <Routes>
              <Route
                element={
                  <Authenticated
                    key="authenticated-routes"
                    fallback={<CatchAllNavigate to="/login" />}
                  // 默认密码

                  >
                    <ThemedLayoutV2>
                      <Outlet />
                    </ThemedLayoutV2>
                  </Authenticated>
                }
              >
                <Route index element={<DashboardPage />} />

                <Route path="/memberUser">
                  <Route index element={<MemberUserList />} />
                  <Route path="create" element={<MemberUserCreate />} />
                  <Route path="edit/:id" element={<MemberUserEdit />} />
                  <Route path="show/:id" element={<MemberUserShow />} />
                </Route>
              </Route>

              <Route
                element={
                  <Authenticated key="auth-pages" fallback={<Outlet />}>
                    <NavigateToResource resource="memberUser" />
                  </Authenticated>
                }
              >
                <Route
                  path="/login"
                  element={
                    <AuthPage
                      type="login"
                      formProps={{
                        initialValues: {
                          ...authCredentials,
                        },
                      }}
                    // providers={[
                    //   {
                    //     name: "google",
                    //     label: "Sign in with Google",
                    //     icon: (
                    //       // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
                    //       (<GoogleOutlined
                    //         style={{
                    //           fontSize: 24,
                    //           lineHeight: 0,
                    //         }}
                    //       />)
                    //     ),
                    //   },
                    //   {
                    //     name: "github",
                    //     label: "Sign in with GitHub",
                    //     icon: (
                    //       // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
                    //       (<GithubOutlined
                    //         style={{
                    //           fontSize: 24,
                    //           lineHeight: 0,
                    //         }}
                    //       />)
                    //     ),
                    //   },
                    // ]}
                    />
                  }
                />
                <Route
                  path="/register"
                  element={
                    <AuthPage
                      type="register"
                      providers={[
                        {
                          name: "google",
                          label: "Sign in with Google",
                          icon: (
                            // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
                            (<GoogleOutlined
                              style={{
                                fontSize: 24,
                                lineHeight: 0,
                              }}
                            />)
                          ),
                        },
                        {
                          name: "github",
                          label: "Sign in with GitHub",
                          icon: (
                            // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
                            (<GithubOutlined
                              style={{
                                fontSize: 24,
                                lineHeight: 0,
                              }}
                            />)
                          ),
                        },
                      ]}
                    />
                  }
                />
                <Route
                  path="/forgot-password"
                  element={<AuthPage type="forgotPassword" />}
                />
                <Route
                  path="/update-password"
                  element={<AuthPage type="updatePassword" />}
                />
              </Route>

              <Route
                element={
                  <Authenticated key="catch-all">
                    <ThemedLayoutV2>
                      <Outlet />
                    </ThemedLayoutV2>
                  </Authenticated>
                }
              >
                <Route path="*" element={<ErrorComponent />} />
              </Route>
            </Routes>
            <UnsavedChangesNotifier />
            <DocumentTitleHandler />
          </Refine>
        </AntdApp>
      </ConfigProvider>
    </BrowserRouter>)
  );
};

export default App;
