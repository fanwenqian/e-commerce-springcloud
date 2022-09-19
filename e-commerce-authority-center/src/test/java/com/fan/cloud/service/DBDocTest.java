package com.fan.cloud.service;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DBDocTest {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void buildDBDoc() {
        DataSource dataSource = applicationContext.getBean(DataSource.class);

        //生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 文件生成路径
                .fileOutputDir("E:\\imooc\\project\\e-commerce-springcloud")
                // 是否生成后打开目录
                .openOutputDir(false)
                .fileType(EngineFileType.HTML)
                .produceType(EngineTemplateType.freemarker)
                .build();

        //  生成文档配置，包含自定义版本号、描述等
        Configuration config = Configuration.builder()
                .version("1.0.0")
                .description("e-commerce-springcloud")
                .dataSource(dataSource)
                .engineConfig(engineConfig)
                .produceConfig(getProduceConfig())
                .build();

        // 执行生成
        new DocumentationExecute(config).execute();
    }

    /**
     * 配置想要生成文档的数据表和想要忽略的数据表
     */
    private ProcessConfig getProduceConfig() {
        //想要忽略的数据表
        List<String> ignoreTableName = Collections.singletonList("undo_log");
        //忽略表前缀，忽略a、b开头的数据表
        List<String> ignorePrefix = Arrays.asList("a", "b");
        //忽略表后缀，忽略_test、_Test后缀的数据表
        List<String> ignoreSuffix = Arrays.asList("_test", "_Test");

        return ProcessConfig.builder()
                // 根据名称指定表生成
                .designatedTableName(Collections.emptyList())
                // 根据表前缀生成
                .designatedTablePrefix(Collections.emptyList())
                // 根据表后缀生成
                .designatedTableSuffix(Collections.emptyList())
                // 根据名称忽略表生成
                .ignoreTableName(ignoreTableName)
                // 根据表名前缀忽略表生成
                .ignoreTablePrefix(ignorePrefix)
                // 根据表名后缀忽略表生成
                .ignoreTableSuffix(ignoreSuffix)
                .build();
    }
}
