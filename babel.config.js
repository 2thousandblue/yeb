module.exports = {
    presets: [
        '@vue/cli-plugin-babel/preset'
    ],
    "plugins": [
        "transform-vue-jsx",
        "transform-runtime",
        [
            "component",
            {
                "libraryName": "element-ui",
                "style": true
            }
        ]
    ]
}
