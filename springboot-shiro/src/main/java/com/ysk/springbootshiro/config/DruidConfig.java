package com.ysk.springbootshiro.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource") //来获取application.yml配置的属性
    @Bean
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
    //配置Druid的监控
    //1.配置一个管理后台的servlet
    @Bean
    public ServletRegistrationBean StatViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "1234");
        initParams.put("allow", ""); //默认运行所有访问
        initParams.put("deny", "192.168.0.105");
        bean.setInitParameters(initParams);
        return bean;
    }
    //配置一个web监控的filter
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();

        initParams.put("exclusions", "*.js, *.css, *.images, /druid/*");
        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}