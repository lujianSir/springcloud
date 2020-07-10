package com.lujian.eureka_admin.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 图片绝对地址与虚拟地址映射
 */

@Component
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 虚拟路径配置1
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String newrootPath = System.getProperty("user.dir") + "/upload";
        newrootPath = newrootPath.replaceAll("\\\\", "/");
         System.out.println("file:/" + newrootPath+"");
        registry.addResourceHandler("/image/user/**").addResourceLocations("file:/" + newrootPath + "/user/");
        registry.addResourceHandler("/image/video/**").addResourceLocations("file:/" + newrootPath + "/video/");
        registry.addResourceHandler("/image/picture/**").addResourceLocations("file:/" + newrootPath + "/picture/");
//        registry.addResourceHandler("/image/picture/**")
//                .addResourceLocations("file:/mnt/javaweb/smartconstruction/upload/picture/");// linux
//        registry.addResourceHandler("/image/video/**").addResourceLocations("file:/mnt/javaweb/smartconstruction/upload/video/");//
//        registry.addResourceHandler("/image/picture/**")
//                .addResourceLocations("file:/mnt/javaweb/smartconstruction/upload/picture/");// linux
//        registry.addResourceHandler("/image/user/**").addResourceLocations("file:/mnt/javaweb/smartconstruction/upload/user/");//
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
