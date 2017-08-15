package com.cn.ruolan;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Created by wuyinlei on 2017/8/15.
 */
public class Application extends ResourceConfig {

   public Application() {

        packages("com.cn.ruolan.service");

        register(JacksonJaxbJsonProvider.class);  //默认的Jackson解析器

        //注册日志打印输出
        register(Logger.class);
    }
}
