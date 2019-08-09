package com.houseWork.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("SpringBootPro")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.hu.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }

//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                //加了ApiOperation注解的类，才生成接口文档
//                .apis(RequestHandlerSelectors.basePackage("com.hu.controller"))
//                //包下的类，才生成接口文档
//                //.apis(RequestHandlerSelectors.basePackage("io.sysssc.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .securitySchemes(security());         //主要关注点--统一填写一次token
//    }
//
//    private List<ApiKey> security() {
//        return newArrayList(
//                new ApiKey("Authorization", "认证token", "header")
//        );
//    }


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //加了ApiOperation注解的类，才生成接口文档
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.houseWork.controller"))
                //包下的类，才生成接口文档
                //.apis(RequestHandlerSelectors.basePackage("io.sysssc.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(globalOperation());       // 主要关注点----每个接口调用都填写token
    }

    private List<Parameter> globalOperation(){
        //添加head参数配置start
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("Authorization").description("认证token")
                .modelRef(new ModelRef("string")).parameterType("header").defaultValue("Bearer ")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数


        return pars;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API")
                .description("绿泡泡小程序")
                .termsOfServiceUrl("https://www.cnblogs.com/xiebq/")
                .version("1.0")
                .build();
    }


}
