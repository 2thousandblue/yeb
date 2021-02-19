// // eslint-disable-next-line no-unused-vars
// const path = require('path')
// module.exports = {
//     /* 部署生产环境和开发环境下的URL：可对当前环境进行区分，baseUrl 从 Vue CLI 3.3 起已弃用，要使用publicPath */
//     /* baseUrl: process.env.NODE_ENV === 'production' ? './' : '/' */
//     // publicPath: process.env.NODE_ENV === 'production' ? '/public/' : './',
//     publicPath: '/',
//     /* 输出文件目录：在npm run build时，生成文件的目录名称 */
//     outputDir: 'dist',
//     /* 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录 */
//     assetsDir: "static",
//     /* 是否在构建生产包时生成 sourceMap 文件，false将提高构建速度 */
//     productionSourceMap: false,
//     /* 默认情况下，生成的静态资源在它们的文件名中包含了 hash 以便更好的控制缓存，你可以通过将这个选项设为 false 来关闭文件名哈希。(false的时候就是让原来的文件名不改变) */
//     filenameHashing: false,
//     /* 代码保存时进行eslint检测 */
//     lintOnSave: true,
//     /* webpack-dev-server 相关配置 */
//     devServer: {
//         /* 自动打开浏览器 */
//         open: true,
//         /* 设置为0.0.0.0则所有的地址均能访问 */
//         host: 'localhost',
//         port: 443,
//         https: true,
//         hotOnly: false,
//         /* 使用代理 */
//         // proxy: {
//         //     '/managebooks': {
//         //         target: 'https://localhost:8080',      //    目标代理服务器地址(后端)
//         //         pathRewrite: {  //路径改写
//         //             '^/managebooks': ''
//         //         },
//         //         changeOrigin:true,
//         //         secure: false
//         //
//         //     }
//         // }
//     }
//
// }

let proxyObj = {};

proxyObj['/ws'] = {
    ws: true,
    target: "ws://localhost:8080"
};
proxyObj['/apis'] = {
    ws: false,
    target: 'https://localhost:8080/apis/',
    changeOrigin: true,
    pathRewrite: {
        '^/apis': ''
    }
}

const Timestamp = new Date().getTime();
const CompressionPlugin = require("compression-webpack-plugin");
module.exports = {
    publicPath: '/',
    /* 输出文件目录：在npm run build时，生成文件的目录名称 */
    outputDir: 'D:\\Environment\\nginx-1.18.0\\html\\dist',
    /* 放置生成的静态资源 (js、css、img、fonts) 的 (相对于 outputDir 的) 目录 */
    assetsDir: "static",
    /* 是否在构建生产包时生成 sourceMap 文件，false将提高构建速度 */
    productionSourceMap: false,
    /* 默认情况下，生成的静态资源在它们的文件名中包含了 hash 以便更好的控制缓存，你可以通过将这个选项设为 false 来关闭文件名哈希。(false的时候就是让原来的文件名不改变) */

    devServer: {
        /* 自动打开浏览器 */
        open: true,
        /* 设置为0.0.0.0则所有的地址均能访问 */
        host: 'localhost',
        port: 443,
        https: true,
        hotOnly: false,
        proxy: proxyObj
    }, chainWebpack:  config => {


        // 解决ie11兼容ES6
        // config.entry('main').add('babel-polyfill')
        // 开启图片压缩
        config.module.rule('images')
            .test(/\.(png|jpe?g|gif|svg)(\?.*)?$/)
            .use('image-webpack-loader')
            .loader('image-webpack-loader')
            .options({ bypassOnDebug: true })
        if (process.env.NODE_ENV === 'production'){
            config.plugin('compressionPlugin')
                .use(new CompressionPlugin({
                    test:/\.js$|\.html$|\.css$|/, // 匹配文件名
                    threshold: 1024, // 对超过1k的数据压缩
                    deleteOriginalAssets: false // 不删除源文件
                }))
        }
    },

}
