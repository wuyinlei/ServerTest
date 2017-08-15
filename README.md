# IntelliJ IDEA + Tomcat + RestFul + Mysql打造属于自己的服务器(一)

标签（空格分隔）： 开源项目

---

###软件相关版本配置
* IntelliJ IDEA 2017.1.1
* Tomcat 8.0.41
* Mysql-5.7.18-macos10.12-x86_64
* Jdk 1.8.0_121
* Hibernate

###那就愉快的开始吧
####第一步(new project)
![1.png](http://upload-images.jianshu.io/upload_images/1316820-cc7148b0b882553f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![2.png](http://upload-images.jianshu.io/upload_images/1316820-ea0ab1e946e9b211.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



####第二步(配置Tomcat)

![3.png](http://upload-images.jianshu.io/upload_images/1316820-832b70fb80816224.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![4.png](http://upload-images.jianshu.io/upload_images/1316820-a9df8a2701c96aef.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![5.png](http://upload-images.jianshu.io/upload_images/1316820-7eb3cbb6e1981c80.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![6.png](http://upload-images.jianshu.io/upload_images/1316820-f7bec68070ea2690.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![7.png](http://upload-images.jianshu.io/upload_images/1316820-f4dfdbdf0f5b7f78.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
当选择第一个进行点击之后，选择finish，这个时候tomcat就算配置完成了,让我们来检验一下是否配置成功了吧，点击项目的运行按钮运行,项目部署成功之后会默认打开下面的这个网页。
![8.png](http://upload-images.jianshu.io/upload_images/1316820-d978510bb61c40a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们看下为什么会打开如上的网页,因为这个地方的代码这样写的(index.jsp里面的代码)
```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  $END$
  </body>
</html>

```
好了,到这个地方就已经完成了web项目的搭建和Tomcat的配置了,接下来就看看Mysql的连接和Hibernate的相关默认配置,毕竟我们需要写相关的代码进行后台服务器的相关逻辑呀。
####第三步(配置Mysql和RestFul框架)(gradle依赖形式)

![9.png](http://upload-images.jianshu.io/upload_images/1316820-b2e1f93514ddbe2c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![10.png](http://upload-images.jianshu.io/upload_images/1316820-2eadadfa271e4f8d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



```
  //RestFul 接口框架
    compile 'org.glassfish.jersey.core:jersey-client:2.26-b03'
    compile 'org.glassfish.jersey.core:jersey-server:2.26-b03'
    compile 'org.glassfish.jersey.containers:jersey-container-servlet:2.26-b03'
    compile 'org.glassfish.jersey.media:jersey-media-json-jackson:2.26-b03'
    //数据库操作框架
    compile 'org.hibernate:hibernate-core:5.2.9.Final'
    compile 'org.hibernate:hibernate-entitymanager:5.2.9.Final'
    //数据库的连接池
    compile 'org.hibernate:hibernate-c3p0:5.2.9.Final'
    //mysql 驱动库
    compile group: 'mysql', name: 'mysql-connector-java', version: '6.0.6'
```

然后在src/main/resources目录下面创建hibernate.cfg.xml文件并添加以下代码进行相关的默认配置
```
<?xml version='1.0' encoding='utf-8'?>

<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings 数据库连接驱动-->
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://127.0.0.1:3306/CN_TEST?serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=utf8&amp;useSSL=false</property>
        <property name="connection.username">root</property>
        <property name="connection.password">root</property>
        <!--本地地址-->


        <!-- JDBC connection pool (use the built-in) 连接池大小-->
        <property name="connection.pool_size">5</property>

        <!-- SQL dialect -->
        <property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>

        <!-- Enable Hibernate's automatic session context management Hibernate上下文线程池级别-->
        <property name="current_session_context_class">thread</property>

        <!-- Disable the second-level cache  -->
        <property name="cache.provider_class">org.hibernate.c3p0.internal.C3P0ConnectionProvider</property>
        <property name="c3p0.min_size">10</property> <!--在连接池中可用数据库连接的最小数目-->
        <property name="c3p0.max_size">30</property> <!--在连接池中所有数据库连接的最大数目-->
        <property name="c3p0.time_out">2000</property> <!--设定数据库连接的超时时间-->
        <property name="c3p0.max_statement">50</property> <!--可以被缓存的PreparedStatement的最大数目-->
        <!-- configuration pool -->
        <property name="c3p0.acquire_increment">1</property>
        <property name="c3p0.idle_test_period">100</property>
        <property name="c3p0.max_statements">0</property>
        <property name="c3p0.timeout">100</property>

        <property name="show_sql">true</property>
        <property name="format_sql">true</property>


        <!--其实这个hibernate.hbm2ddl.auto参数的作用主要用于：自动创建|更新|验证数据库表结构。如果不是此方面的需求建议set value="none"。-->
        <!--create：-->
        <!--每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。-->
        <!--create-drop ：-->
        <!--每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。-->
        <!--update：-->
        <!--最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），以后加载hibernate时根据 model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等 应用第一次运行起来后才会。-->
        <!--validate ：-->
        <!--每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。-->
        
        <property name="hbm2ddl.auto">update</property>

     

    </session-factory>
</hibernate-configuration>
```

然后在src/main/webapp目录下面创建WEB-INF/web.xml填入下面的相关配置
```
<?xml version="1.0" encoding="UTF-8"?>

<web-app>

    <!--定义项目的名称.-->
    <display-name>CNStudy</display-name>

    <servlet>
        <!--定义Servlet的名称-->
        <servlet-name>CNApiServlet</servlet-name>
        <!--容器   -Servlet的类-->-->
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
        <init-param>
            <!--映射的包名 用于搜索处理-->
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>com.cn.ruolan.service</param-value>   <!--这个要创建对应的包 里面是相关的接口逻辑处理-->
        </init-param>
        <init-param>
            <param-name>javax.ws.rs.Application</param-name>
            <param-value>com.cn.ruolan.Application</param-value>  <!--类似安卓工程里面的全局application-->
        </init-param>

        <!--启动的时候是否加载  true-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!--映射-->
    <servlet-mapping>
        <servlet-name>CNApiServlet</servlet-name>
        <!--访问路径  定义Servlet所对应的RUL   也就是说其他接口要被访问的时候  要加上/api/  这个-->
        <url-pattern>/api/*</url-pattern>
    </servlet-mapping>

    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>


</web-app>
```

好了这个时候基本上就配置完毕了,让我们来检验一下我们部署的是否正确,接下来会分别写一个GET请求和POST请求代码如下
```

//加上下面的这个Path   那么访问地址就是//127.0.0.1/api/account    api就是web.xml里面的映射的那个api
@Path("/account")
public class AccountService {


    @Path("/login")  //请求地址127.0.0.1/api/account/login
    @GET   //get请求  这个地方也可以加入地址path
    public String get() {

        return "登录成功";
    }


    @Path("/login")   //也可以这样  只不过请求是post请求  模拟一下
    @POST
    public User post() {
        User user = new User();
        user.setPhone("12123452113");
        user.setUsername("若兰明月");
        return user;
    }
}

```
User.class类

```
public class User {

    private String username;
    private String phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

```



![11.png](http://upload-images.jianshu.io/upload_images/1316820-a67bfbc19011a783.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



这个时候我们运行一下项目看下请求吧(点击运行按钮,如果什么都没改的话界面出现的仍然是“$END$”),项目运行成功之后我们使用postman来请求一下地址“http://localhost:8080/api/account/login”

![8.png](http://upload-images.jianshu.io/upload_images/1316820-d978510bb61c40a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#####GET请求模拟
![get.png](http://upload-images.jianshu.io/upload_images/1316820-c8bc78bb4f08e24c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#####POST请求模拟
![post.png](http://upload-images.jianshu.io/upload_images/1316820-cc8d43c386c12c4d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
好了,到这里就已经部署成功了,在接下来就是我们的实战了,实战应该会写一个朋友圈的功能,虽然朋友圈的功能我已经利用Bmob实现了,但是总感觉有些瑕疵,哈哈,有空就自己撸一个后台自己写吧。加油

###推荐阅读
[使用 Jersey 和 Apache Tomcat 构建 RESTful Web 服务][1](里面有关于相关软件的下载链接)

[Maven和Gradle对比][2]


[hibernate.hbm2ddl.auto配置详解][3]

[Custom ResourceConfig 自定义资源配置][4]

###代码传送门
https://github.com/wuyinlei/ServerTest
https://github.com/wuyinlei/ServerTest
https://github.com/wuyinlei/ServerTest


  [1]: https://www.ibm.com/developerworks/cn/web/wa-aj-tomcat/
  [2]: http://www.cnblogs.com/huang0925/p/5209563.html
  [3]: http://www.cnblogs.com/talo/articles/1662244.html
  [4]: https://waylau.gitbooks.io/rest-in-action/content/docs/Custom%20ResourceConfig.html
