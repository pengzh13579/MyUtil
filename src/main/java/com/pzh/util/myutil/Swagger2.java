package com.pzh.util.myutil;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
// @EnableSwagger2注解启用Swagger2
@EnableSwagger2
public class Swagger2 {

    /***
     * select()函数返回一个ApiSelectorBuilder实例用来控制暴露给Swagger来展现
     * basePackage,这是扫描注解的配置
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pzh.util.myutil"))
                .paths(PathSelectors.any())
                .build();
    }

    /***
     * 创建该Api的基本信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2的应用")
                .description("SpringBoot+Swagger2的应用")
                .termsOfServiceUrl("https://blog.csdn.net/xiaoshiyiqie")
                .version("1.0")
                .build();
    }

}
