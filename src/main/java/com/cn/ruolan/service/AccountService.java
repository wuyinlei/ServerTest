package com.cn.ruolan.service;

import com.cn.ruolan.bean.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by wuyinlei on 2017/8/15.
 */

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
    @Consumes(MediaType.APPLICATION_JSON)  //指定请求返回的响应体为JSON
    @Produces(MediaType.APPLICATION_JSON)
    public User post() {
        User user = new User();
        user.setPhone("12123452113");
        user.setUsername("若兰明月");
        return user;
    }

}
