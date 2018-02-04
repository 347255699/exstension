# exstension
> exstension工具包，以Vert.x组件为服务目标开发，用于快速构建和启动基于Vert.x组件开发的应用。

## base
> 基础工具包，仅依赖了`commons-lang3`工具包，用于扫描properties和加载配置文件内容，并提供包扫描器，用于快速扫描和实例化某包下的类，如用于统一部署Veticle实例。

实例化某包下同一类对象，可使用`PackageScanner`实例对象的`scan`方法。

统一扫描和部署Verticle实例：  
```java
new PackageScanner<SimpleAbstractVerticle>().scan("org.demo.verticle", SimpleAbstractVerticle.class)
                .forEach(v -> {
                    // 部署该包下的所有Verticle
                    Vertx.vertx().deployVerticle(v, v.getDeploymentOptions());
                 });
```
> 注意，实例中的`SimpleAbstractVerticle`对象为`web-launch`扩展包中的一个类，该类的用途是批量部署可配置的Verticle。
加载某路径下的properties文件，可通过`ConfigHolder`的静态方法`init`。（注意该路径应该为classpath下的相对路径）

加载properties文件并取得所有配置参数实例：
```java
ConfigHolder.init("config.properties");
// 返回值为Map<String,Object>类型
ConfigHolder.properties();
```
若采用base提供的properties文件默认加载路径，调用无参`init`方法即可。默认加载路径为classpath路径下的`config/config.properties`。

## web-launcher
> 基于base扩展包和vertx-web组件构建的单web应用启动器，内置Route和Verticle快速扫描，单一Http服务器启动等功能。

启动一个单一Http服务实例并部署所有Verticle仅需两步。

Step1，在propertes配置文件中配置参数

propertes文件配置清单：
* WebServerVerticle部署实例数量，若不配置默认为一个实例：web.server.instant.number=2
* Http监听端口：web.listen.port=8080
* Route对象扫描和实例化，Route对象所在的包：web.route.package=org.demo.route
* Verticle对象部署，Verticle对象所在的包：sys.verticle.package=org.demo.verticle
* 配置vertx日志框架，若不配置该参数则启用JDK(JUL)： sys.logging.factory=io.vertx.core.logging.SLF4JLogDelegateFactory
* 配置hazelcast日志框架，若不配置该参数则启用JDK(JUL)：hazelcast.logging.type=slf4j

Step2，通过`WebService`的静态方法`launch`来启动服务和部署Verticle（默认`properties`文件路径为classpath路径下的`config/config.properties`）。
```java
WebService.launch();
```
web-launcher提供多种启动方式。
```java
// 启动本地模式和默认配置
WebService.launch();
// 启动本地模式自定义properties路径
WebService.launch("config/config.properties");
...

// 启动集群模式和默认配置
WebService.launchCluster();
// 启动集群模式自定义properties路径
WebService.launchCluster("config/config.properties");
// 启动集群模式自定义properties路径和vertx选项
...
```
> 注意，启用集群模式前，确保classpath路径下已加载hazelcast.jar。

系统中的Verticle必须继承至`SimpleAbstractVerticle`而非`AbstractVerticle`。原因是批量部署不能对单个Verticle进行参数定制，为此需要覆盖一个`getDeploymentOptions`方法来进行参数定制。如下所示：
```java
DeploymentOptions getDeploymentOptions(){
    return new DeploymentOptions().setConfig(jsonConfig);
}
```

另外，该扩展包提供了Vertx对象的持有者，可通过`VertxUtils`的静态方法`vertx()`来取得Vertx对象，同样可以通过`VertxUtils`的静态方法`eventBus`来取得EventBus对象等。

取得Vertx、EventBus或SharedData实例：
```java
VertxUtils.vertx();
VertxUtils.eventBus();
VertxUtils.sharedData();
```
取得Router对象，web-launcher默认通过`Route`接口的`route`方法来传递Router对象，你仅需要将Route扫描包下的对象实现`Route`接口，即可在`route`方法下取得Router对象。

取得JSON格式的properties参数，通过`SysConfig`的静态方法`asJson`即可取得。

取得properties参数实例：
```java
// JSON格式的properties参数
Config.asJson();
```
