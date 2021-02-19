import axios from 'axios'
import {Message} from 'element-ui';
import {mymessage} from '@/utils/mymessage';
import router from "@/router";
import store from "@/store";
//请求拦截器,用于更新请求头让后端鉴权
axios.interceptors.request.use(config => {
    //已有token
    let token = store.state.token;
    if (token) {
        console.log(token);
        config.headers['Authorization'] = token;
    } else if (config.url !== '/login'){ //无token,可能是第一次登录,也可能是token失效,需要重新获取
        console.log('no token');
        router.replace('/login');
    }
    return config
}, error => console.log(error))

//响应拦截器
axios.interceptors.response.use(success => {
    //业务逻辑错误
    if (success.status && success.status === 200 && success.data.code !== 200) {
        Message.error({message: success.data.msg})
        return;
    }
    if (success.data.msg) {
        mymessage.success({message: success.data.msg})
    }
    return success.data;
}, error => {
    //接口访问失败
    if (error.response.status === 504 || error.response.status === 404) {
        Message.error({message: '服务器故障'})
    } else if (error.response.status === 403) {
        Message.error({message: '权限不足，请联系管理员'})
    } else if (error.response.status === 401) {
        mymessage.error({message: error.response.data.msg ? error.response.data.msg : '尚未登录，请登录'})
        store.commit('logout')
        router.replace('/login');
    } else {
        if (error.response.data.msg) {
            Message.error({message: error.response.data.msg})
        } else {
            Message.error({message: '未知错误!'})
        }
    }
    return;
})

export const postKeyValueRequest = (url, params) => {
    return axios
        .post(`${url}`, {
            data: params,
            transformRequest: [function (data) {
                let ret = '';
                for (let i in data) {
                    ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
                }
                return ret;
            }],
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
}
export const postRequest = (url, params) => {
    return axios
        .post(`${url}`, params)
}
export const putRequest = (url, params) => {
    return axios
        .put(`${url}`, params)
}
export const getRequest = (url, params) => {
    return axios
        .get(`${url}`, params)
}
export const deleteRequest = (url, params) => {
    return axios
        .delete(`${url}`, params)
}
