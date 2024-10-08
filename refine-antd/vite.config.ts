import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";


export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {

      '@': path.resolve(__dirname, './src'),
      '@components': path.resolve(__dirname, './src/components'),
    },
  },
  // 配置跨域
  server: {
    proxy: {
      "^/api": {
        target: "http://127.0.0.1:8099"
      }
    }
  },
});
