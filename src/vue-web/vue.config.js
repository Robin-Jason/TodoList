const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:28088",
        changeOrigin: true,
        pathRewrite: {
          "^/api": ""		//此处表示实际连接删去的字符串
        }
      }
    }
  }
})
