// @ts-ignore
const proxy = require('http-proxy-middleware')
// TS1208: 'setupProxy.ts' cannot be compiled under '--isolatedModules' because it is considered a global script file. Add an import, export, or an empty 'export {}' statement to make it a module.
module.exports = function (app) {
  app.use(proxy.createProxyMiddleware('/apis', {
    target: 'http://127.0.0.1:8080',
    secure: false,
    changeOrigin: true,
    pathRewrite: {
      "^/apis": "/apis"
    }
  }))
}