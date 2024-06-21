# RealTime Log
## 介绍
一个简易的实时日志查看器，可以拦截 Springboot 后台的 system.out 和 system.err 输出以及其它日志实时显示在前端页面上。
可以嵌入到 Springboot 项目中，也可以单独部署。
## 使用
你可以发布依赖到自己的仓库
maven 运行 release 环境
```shell
mvn deploy -P release -DskipTests=true
```
也可以打包 jar 包，然后引入到项目中

![image](https://github.com/kiritoko1029/RtLog/assets/43572494/d54b7074-fc22-4aeb-820e-5fc2ecc800ea)
## CHANGELOG
### 0.0.2
- 修复了导入后开发环境 IDEA 显示的日志没有颜色的问题。
- 前端添加清空日志和自定义监听 websocket 链接的功能。
### 0.0.1
- 实现基本功能
- 可以拦截 System.out 和 System.err 输出
- 可以拦截日志输出
- 前端画面实时显示

