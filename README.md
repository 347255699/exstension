# exstension
>exstension工具包，基于Vert.x组件开发，目标用于快速构建和启动基于Vert.x开发的系统应用。

## base
> 基础工具包，仅依赖了`commons-lang3`工具包，用于扫描properties和加载配置文件内容，并提供扫描工具包，用于快速扫描和加载某包的的应用，如用于同一快速部署Veticle实例。

## web-launcher
> 基于base扩展包和Vertx-web组件构建的单web应用启动器，内置Route和Verticle快速扫描，单一Http服务器启动等功能。

