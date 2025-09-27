const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  outputDir: 'dist',
  assetsDir: 'static',
  css: {
    extract: true,
    sourceMap: false,
    loaderOptions: {
      css: {},
      postcss: {},
    },
  },
  devServer: {
    port: 8080,
  },
};

