// layouts
import React from 'react'
import App, { Container } from 'next/app'
import axios from 'axios'
import Head from 'next/head'
import '../styles/base.styl'
import 'antd/dist/antd.css'
import 'antd-mobile/dist/antd-mobile.css'
import cookie from '../utils/cookie'
import clj from '../utils/clj.js'
import json_to_clj from '../utils/json_to_clj.js'
import { getQstr } from '../utils/urlUtil'
import { APP_ID, nodeAPI } from "../utils/constant"
export default class MyApp extends App {
/*  getInitialProps入参对象的属性如下：
  pathname - URL 的 path 部分
  query - URL 的 query 部分，并被解析成对象
  asPath - 显示在浏览器中的实际路径（包含查询部分），为String类型
  req - HTTP 请求对象 (只有服务器端有)
  res - HTTP 返回对象 (只有服务器端有)
  jsonPageRes - 获取数据响应对象 (只有客户端有)
  err - 渲染过程中的任何错误
*/
  static async getInitialProps({Component, router, ctx}) {
    let pageProps = {}

    // 如果是服务端
    if(ctx.req){
      const Host = process.env.NODE_ENV === "production" ? "cli.ailse.cn" : "localhost:3000"
      console.log("NODE_ENV",process.env.NODE_ENV,  Host)
      ctx.req.originalUrl = Host + ctx.req.url
      const openid = getQstr('openid', Host+ctx.req.url)
      const wxid = getQstr('wxid', ctx.req.originalUrl) || cookie.get('appid', ctx).appid || APP_ID
      let jwt
    //
    //   //如果header里面有cookie,取出来,并将user_id挂载到content上,便于asyncData用
      if(ctx.req.headers.cookie) {
        const localCookie = cookie.get(wxid, ctx)
        // console.log('localCookie', localCookie, '111');
        jwt = localCookie.jwt
        global.jwt =  localCookie.jwt
        ctx.userId = localCookie.user_id
      }
    //   //本地测试用
    //   // if (Host.match(/^(localhost|(\d+\.){3}\d+):\d+/) ){
    //   //   cookie.set(wxid,
    //   //     {
    //   //       "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE4MzM3Nzk4MTMsInVzZXJfaWQiOjEsImlhdCI6MTUyMjczOTgxM30.UeQNYJebTLqCp-AJ0b1TK7Bv0xnhBlLHVtP6-_XgqJU",
    //   //       "user_id": 47,
    //   //       "openid": 'o1zp9xET8-P58VP4-1Opvb2vGX5k'
    //   //     }, ctx)
    //   //   // router.replace(`http://${Host}${ctx.req.url}`)
    //   // }
    //
    //   // 授权跳转
      if (!jwt && !openid && (ctx.req.headers['user-agent'].toLocaleLowerCase().indexOf('micromessenger') >= 0)) {
        let url = encodeURIComponent(`http://${Host}${ctx.req.url}`)
        console.log("跳转授权")
        ctx.res.writeHead(301, {'Location': `https://api.diandianyy.com/util/weixin/app/auth?callback=${url}&wxid=${wxid}` });
        ctx.res.end()
      } else if (openid) {
        try{
          let res = await axios.post(`${nodeAPI}getUser`, { openid, wxid })
          console.log("新接口", res.data)
          cookie.set(wxid, {
          user_id  : res.data.user.id,
            openid : res.data.user.openid,
            hos_id : res.data.user.hos_id,
              name : res.data.user.name,
            avatar : res.data.user.avatar,
               jwt : res.data.jwt,
          } , ctx, 1)
          cookie.set("jwt",res.data.jwt)
          let url = ctx.req.url.replace(`openid=${openid}`, ``)
          url=== "/" ? url = "/index" : url
          console.log("获取用户信息后跳转页面", res.data,  `http://${Host}${url}`)
          ctx.res.writeHead(301, {'Location': `http://${Host}${url}`});

          ctx.res.end()
        }catch(e){
          console.log('e', e);
        }
      }
      ctx.userInfo = cookie.get(wxid, ctx)

    }else{
      const wxid = cookie.get('appid', ctx).appid || APP_ID
      ctx.jsonPageRes = cookie.get(wxid)
      window.jwt = cookie.get(wxid).jwt
      
    }
    ctx.userInfo = ctx.userInfo || ctx.jsonPageRes
    // 如果有初始化函数加载
    if (Component.getInitialProps) {
      const data = await Component.getInitialProps(ctx)
      // console.log("1", await Component.getInitialProps(ctx), "1")
      pageProps = { data, userInfo: ctx.userInfo }
      // @todo: 直接传递clj格式, 如何避免<Component {...pageProps} />强行转对象？
      // console.log(typeof data)
      // pageProps = clj.stringify(pageProps, {keys_are_keywords: true})
      // pageProps = json_to_clj(JSON.stringify(pageProps))
      // console.log(pageProps)
    }else{
      pageProps = {...pageProps, userInfo: ctx.userInfo }
    }
    return { pageProps }
  }

  render () {
    const { Component, pageProps } = this.props

    return (

      <Container>
        <Head>
          <title>诊所微信端</title>
          <meta name="viewport" content="initial-scale=1.0, width=device-width" />
          <link rel="stylesheet" href="//at.alicdn.com/t/font_977107_iy90w86sbp.css" />
        </Head>
        {/*<p>引处加layout</p>*/}
        <Component { ...pageProps} />
      </Container>
    )
  }
}
