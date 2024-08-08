module.exports = {
    lintOnSave: false,
    configureWebpack: {
        module: {
            rules: [
                {
                    test: /\.mjs$/,
                    include: /node_modules/,
                    type: 'javascript/auto',
                },
            ],
        },
    },
    publicPath: './',
    assetsDir: 'static',
    parallel: false,
}
