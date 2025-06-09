const path = require("path");

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  chainWebpack: (config) => {
    config.resolve.alias.set("@", resolve("./src"));
  },
  devServer: {
    host: "0.0.0.0", // 或者 'localhost'
    port: 8080,
    open: true,

    proxy: {
      "/api": {
        target: "http://39.105.211.152:43749",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
      // 可以配置更多代理规则
    },
  },
  pwa: {
    iconPaths: {
      favicon32: "favicon.ico",
      favicon16: "favicon.ico",
      appleTouchIcon: "favicon.ico",
      maskIcon: "favicon.ico",
      msTileImage: "favicon.ico",
    },
  },
};

