module.exports = {
    devServer: {
        proxy: {
            //设置代理
            '/api': {
                target: 'http://jdk:8081',
                changeOrigin: true,
                secure: false, //如果是http接口，需要配置该参数
            }
        }
    }
}