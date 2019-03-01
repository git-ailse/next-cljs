const { getQstr } = require('./urlUtil')
const cookie = {
  get(key, context) {
    let data
    try{
      data = document.cookie
      key || (key = getQstr('wxid', window.location.href))
    }catch(e){
      data = context.req.headers.cookie || ''
      key || (key = getQstr('wxid', context.req.originalUrl))
    }
    // 获取key第一次出现的位置

    // 判断上面是否取到key，否则从cookie取
    key || (key = this.get('appid', context).appid)

    let startIndex = data.indexOf(key + '=')
    if(startIndex > -1) {
      // value起始位置
      startIndex = startIndex + key.length + 1
      let endIndex = data.indexOf(';', startIndex)

      //如果结尾位置未找到;则获取后面全部内容
      endIndex = endIndex < 0 ? data.length : endIndex

      return JSON.parse(decodeURIComponent(data.substring(startIndex, endIndex)))
    }
    return ''
  },

  /**
   *
   * @param key
   * @param value Object
   * @param time 过期时间单位是天
   */
  set(key, value, context, time = 1) {
    // 当前时间
    let curTime = new Date()

    // 如果设置过期时间
    if(time) {
      curTime  = new Date()

      curTime.setTime(curTime.getTime()+time*24*3600*1000)
    }
    try {
      let data = document.cookie
      key || (key = getQstr('wxid', window.location.href) || this.get('appid', context).appid)
      document.cookie = key + '=' + encodeURIComponent(JSON.stringify(value)) + ';expires=' + (time === undefined ? '' : curTime.toISOString())
    } catch (e) {
      context.res.setHeader('Set-Cookie', [`${key}=${encodeURIComponent(JSON.stringify(value))};expires=${time === undefined ? '' : curTime.toISOString()}`])
    }
  },

  del(key, context) {
    const data = this.get(key, context)

    if(!data) {
      this.set(key, data, context, -1)
    }
    console.log('cookie', this.get(null, context))
  }
}

module.exports = cookie
