# exstension
> **exstension** 工具包，基于 **Vert.x** 组件开发，目标用于快速构建和启动基于 **Vert.x** 开发的系统应用。

## base
> 基础工具包，仅依赖了 `commons-lang3` 工具包，用于扫描 **properties** 和加载配置文件内容，并提供包扫描器，用于快速扫描和实例化某包下的类，如用于统一部署 **Veticle** 实例。

实例化某包下一类对象，可使用 `PackageScanner` 实例对象的 `scan` 方法。

统一扫描和部署 **Verticle** 实例：  
```java
new PackageScanner<Verticle>().scan("org.demo.verticle", Verticle.class)
                .forEach(verticle -> {
                    // 部署该包下的所有Verticle
                    Vertx.vertx().deployVerticle(verticle);
                 });
```
加载某路径下的 **properties** 文件，可通过 `SysConfigHolder` 的静态方法 `init`。（注意该路径应该为 **classpath** 下的相对路径）

加载 **properties** 文件并取得所有配置参数实例：
```java
SysConfigHolder.init("config.properties");
// 返回值为Map<String,Object>类型
SysConfigHolder.properties();
```
若采用 **base** 提供的 **properties** 文件默认加载路径，可以给 `init` 方法传入一个 `null` 参数。默认加载路径为 **classpath** 路径下的 `config.properties`。

适用默认加载路径实例：
```java
SysConfigHolder.init(null);
```
## web-launcher
> 基于base扩展包和Vertx-web组件构建的单web应用启动器，内置Route和Verticle快速扫描，单一Http服务器启动等功能。

