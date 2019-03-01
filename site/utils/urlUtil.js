//获取query参数
function getQstr(name, url = window.location.href) {
    let reg = new RegExp("" + name + "=([^&]+?)(#|&|$)");
    let index = url.indexOf('?');
    let r = url.substr(index).match(reg);
    if (r !== null) {
        return decodeURIComponent(r[1]);
    }
    // if(name == 'wxid') {
    //   return 'wxed919467f00a05b0'
    // }
    return null;
}

function removeQstr(name, str = window.location.href) {
    let reg1 = new RegExp('' + name + '=(.*?)&')
    let reg2 = new RegExp('[&?]?' + name + '=(.*?)#')
    let url = str
    if (url.match(reg1) !== null) { // 匹配&结尾的则去除字段包含＆
        return url.replace(reg1, '');
    } else if (url.match(reg2) !== null) { // 匹配#结尾的则去除字段包含#
        return url.replace(reg2, '#');
    }
    return url
}

module.exports  = {getQstr , removeQstr}
