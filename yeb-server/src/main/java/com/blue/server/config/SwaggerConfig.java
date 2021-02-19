package com.blue.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.blue.server.controller")).paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("云E办").description("api文档")
                .contact(new Contact("blue", "https://2thousandblue.store", "1244503766@qq.com"))
                .version("1.0").build();
    }

    private List<ApiKey> securitySchemes() {
        //设置请求头
        ArrayList<ApiKey> res = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        res.add(apiKey);
        return res;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录的认证路径
        ArrayList<SecurityContext> res = new ArrayList<>();
        res.add(getContextByPath("/hello/.*"));
        return res;
    }

    private SecurityContext getContextByPath(String s) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(s)).build();
    }

    private List<SecurityReference> defaultAuth() {
        ArrayList<SecurityReference> res = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        //添加默认授权
        res.add(new SecurityReference("Authorization", new AuthorizationScope[]{authorizationScope}));
        return res;
    }
}
