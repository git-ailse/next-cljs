const withStylus = require('@zeit/next-stylus')
const withCss = require('@zeit/next-css')
const nodeExternals = require('webpack-node-externals')

module.exports = withStylus(withCss({
  webpack: (config, { buildId, dev, isServer, defaultLoaders }) => {
    // 报XMLHttpRequest不存在
    // config.externals.push({
      // xmlhttprequest:'{XMLHttpRequest:XMLHttpRequest}'
    // })
    // 忽略所有node_modules包，报window未定义
    // config.externals = [nodeExternals()]
    // alias起作用，但也不行，仍报child_process fs不存在，考虑拿源码来改
    // config.resolve.alias = {
    //   xmlhttprequest: '@pupeno/xmlhttprequest/lib/XMLHttpRequest.js'
    // }
    return config
  },
  webpackDevMiddleware: config => {
    return config
  }
}))
