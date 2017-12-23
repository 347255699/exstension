# exstension
> exstension工具包，基于 Vert.x 组件开发，目标用于快速构建和启动基于 Vert.x 开发的系统应用。

## base
> 基础工具包，仅依赖了 `commons-lang3` 工具包，用于扫描 properties 和加载配置文件内容，并提供包扫描器，用于快速扫描和实例化某包下的类，如用于统一部署 Veticle 实例。

实例化某包下一类对象，可使用 `PackageScanner` 实例对象的 `scan` 方法。

统一扫描和部署 Verticle 实例：  
```java
new PackageScanner<Verticle>().scan("org.demo.verticle", Verticle.class)
                .forEach(verticle -> {
                    // 部署该包下的所有Verticle
                    Vertx.vertx().deployVerticle(verticle);
                 });
```
加载某路径下的 properties 文件，可通过 `SysConfigHolder` 的静态方法 `init`。（注意该路径应该为 classpath 下的相对路径）

加载 properties 文件并取得所有配置参数实例：
```java
SysConfigHolder.init("config.properties");
// 返回值为Map<String,Object>类型
SysConfigHolder.properties();
```
若采用 base 提供的 properties 文件默认加载路径，可以给 `init` 方法传入一个 `null` 参数。默认加载路径为 classpath 路径下的 `config.properties`。

适用默认加载路径实例：
```java
SysConfigHolder.init(null);
```

## web-launcher
> 基于 base 扩展包和 vertx-web 组件构建的单 web 应用启动器，内置 Route 和 Verticle 快速扫描，单一 Http 服务器启动等功能。

启动一个单一Http服务实例并部署所有Verticle仅需两步。

Step1，在propertes配置文件中配置参数

propertes文件配置清单：
* Http 监听端口：web.listen.port=8080
* Route 对象扫描和实例化，Route 对象所在的包：web.route.package=org.demo.route
* Verticle 对象部署，Verticle 对象所在的包：sys.verticle.package=org.demo.verticle

Step2，通过 `WebService` 的静态方法 `launch` 来启动服务和部署Verticle。
```java
WebService.launch("config.propertes");
```
若采用默认 properties 文件的加载路径，给 `launch` 方法传入参数 `null` 即可。默认加载路径为 classpath 路径下的 `config.properties`。

```java
WebService.launch(null);
```
另外，该扩展包提供了 Vertx 对象的持有者，可通过 `SysHolder` 的静态方法 `vertx()` 来取得 Vertx 对象，同样可以通过 `SysHolder` 的静态方法 `eventBus` 来取得 EventBus 对象。

取得 Vertx 和 EventBus实例：
```java
SysHolder.vertx();
SysHolder.eventBus();
```
取得 Router 对象，web-launcher 默认通过 `Route` 接口的 `route` 方法来传递 Router 对象，你仅需要将 Route 扫描包下的对象实现 `Route` 接口，即可在  `route` 方法下取得 Router 对象。

取得 JSON 格式的 properties 参数，通过 `SysConfigHolder` 的静态方法 `asJson` 即可取得。

取得 properties 参数实例：
```java
// JSON格式的properties参数
SysConfigHolder.asJson();
```
