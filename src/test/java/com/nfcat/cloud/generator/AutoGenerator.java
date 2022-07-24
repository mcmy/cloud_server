package com.nfcat.cloud.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.*;

public class AutoGenerator {

    private final String url = "jdbc:mysql://localhost:3306/cloud?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true";
    private final String username = "cloud";
    private final String password = "MzpMkssGLa2RjZXf";

    @Test
    public void fast() {
        String entryFile = System.getProperty("user.dir") + "/src/main/java";
        String mapperFile = System.getProperty("user.dir") + "/src/main/resources/mybatis/mapper";
//        String entryFile = "D:\\cache\\1";
//        String mapperFile = "D:\\cache\\1";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("nfcat")// 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(entryFile) // 指定输出目录
                            .disableOpenDir(); //打开文件夹
                })
                .packageConfig(builder -> {
                    String rubbish = "D:\\cache";
                    Map<OutputFile, String> outMap = new HashMap<>();
                    outMap.put(OutputFile.xml, mapperFile);
//                    outMap.put(OutputFile.xml, rubbish);
//                    outMap.put(OutputFile.mapper, rubbish);
//                    outMap.put(OutputFile.entity, rubbish);
                    outMap.put(OutputFile.controller, rubbish);
//                    outMap.put(OutputFile.service, rubbish);
//                    outMap.put(OutputFile.serviceImpl, rubbish);
                    builder.parent("com.nfcat.cloud") // 设置父包名
                            .moduleName("sql")// 设置父包模块名
                            .pathInfo(outMap);
                })
                .strategyConfig(builder -> {
                    builder
//                            .addTablePrefix("nf_") // 设置过滤表前缀
//                            .addInclude("nf_shopping_cart") // 设置需要生成的表名
                            .addInclude(getTables("all"))
                            .entityBuilder() //实体类配置
                            .enableLombok() //使用lombok
                            .enableChainModel() //链式设置
                            .fileOverride() //覆盖
                            .enableTableFieldAnnotation()//实体类字段注解
                            .serviceBuilder()
                            .mapperBuilder()
                            .fileOverride() //覆盖
                            .enableMapperAnnotation()//开启mapper注解
                            .enableBaseResultMap()//启用 BaseResultMap 生成
                            .enableBaseColumnList();//启用 BaseColumnList
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
