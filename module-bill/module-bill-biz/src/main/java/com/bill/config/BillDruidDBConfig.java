package com.bill.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alipay.sofa.tracer.plugins.datasource.SmartDataSource;
import com.bill.model.BillNacosProperties;

/**
 * Druid config
 *
 * @author king
 * @date 2022/05/23 14:53
 **/
@Configuration
public class BillDruidDBConfig implements EnvironmentAware {
    private Logger logger = LoggerFactory.getLogger(BillDruidDBConfig.class);

    private org.springframework.core.env.Environment environment;

    @Override
    public void setEnvironment(org.springframework.core.env.Environment environment) {
        this.environment = environment;
    }

    /**
     * 配置监控统计功能 访问路径 http://127.0.0.1:8081/CISP/druid/login.html 1.配置Servlet
     *
     * @return org.springframework.boot.web.servlet.ServletRegistrationBean
     */
    @Bean("billServletRegistrationBean")
    public ServletRegistrationBean
        druidServlet(@Autowired @Qualifier("billNacosProperties") BillNacosProperties commonNacosProperties) {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        // 用户名也可以自己设置，及直接写死
        reg.addInitParameter("loginUsername",
            commonNacosProperties.getProperty("spring.datasource.bill.loginUsername"));
        // 密码也可以自己设置，及直接写死
        reg.addInitParameter("loginPassword",
            commonNacosProperties.getProperty("spring.datasource.bill.loginPassword"));
        // 慢SQL记录
        reg.addInitParameter("logSlowSql", commonNacosProperties.getProperty("spring.datasource.bill.username"));
        // IP白名单 (没有配置或者为空，则允许所有访问，若配置多个则用逗号隔开)
        // reg.addInitParameter("allow", "101.6.244.30");
        // 禁用HTML页面上的“Reset All”功能
        reg.addInitParameter("resetEnable", "false");
        return reg;
    }

    /**
     * 2.配置Filter
     *
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean("billFilterRegistrationBean")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略资源
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    /**
     * 声明其为Bean实例
     *
     * @return javax.sql.DataSource
     */
    @Bean("billDataSource")
    public DataSource
        dataSource(@Autowired @Qualifier("billNacosProperties") BillNacosProperties commonNacosProperties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(commonNacosProperties.getProperty("spring.datasource.bill.url"));
        datasource.setUsername(commonNacosProperties.getProperty("spring.datasource.bill.username"));
        datasource.setPassword(commonNacosProperties.getProperty("spring.datasource.bill.password"));
        datasource.setDriverClassName(commonNacosProperties.getProperty("spring.datasource.bill.driver"));

        // configuration
        datasource
            .setInitialSize(commonNacosProperties.getProperty("spring.datasource.bill.initialSize", Integer.class));
        datasource.setMinIdle(commonNacosProperties.getProperty("spring.datasource.bill.minIdle", Integer.class));
        datasource.setMaxActive(commonNacosProperties.getProperty("spring.datasource.bill.maxActive", Integer.class));
        datasource.setMaxWait(commonNacosProperties.getProperty("spring.datasource.bill.maxWait", Integer.class));
        datasource.setTimeBetweenEvictionRunsMillis(
            commonNacosProperties.getProperty("spring.datasource.bill.timeBetweenEvictionRunsMillis", Integer.class));
        datasource.setMinEvictableIdleTimeMillis(
            commonNacosProperties.getProperty("spring.datasource.bill.minEvictableIdleTimeMillis", Integer.class));
        datasource.setValidationQuery(commonNacosProperties.getProperty("spring.datasource.bill.validationQuery"));
        datasource
            .setTestWhileIdle(commonNacosProperties.getProperty("spring.datasource.bill.testWhileIdle", boolean.class));
        datasource
            .setTestOnBorrow(commonNacosProperties.getProperty("spring.datasource.bill.testOnBorrow", boolean.class));
        datasource
            .setTestOnReturn(commonNacosProperties.getProperty("spring.datasource.bill.testOnReturn", boolean.class));
        datasource.setPoolPreparedStatements(
            commonNacosProperties.getProperty("spring.datasource.bill.poolPreparedStatements", boolean.class));
        datasource.setMaxPoolPreparedStatementPerConnectionSize(commonNacosProperties
            .getProperty("spring.datasource.bill.maxPoolPreparedStatementPerConnectionSize", Integer.class));
        try {
            datasource.setFilters(commonNacosProperties.getProperty("spring.datasource.bill.filters"));
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource
            .setConnectionProperties(commonNacosProperties.getProperty("spring.datasource.bill.connectionProperties"));

        return datasource;
    }

    @Bean("billSqlSessionFactory")
    public SqlSessionFactoryBean
        mysqlSqlSessionFactory(@Autowired @Qualifier("billSmartDataSource") DataSource dataSource) throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setEnvironment(new Environment("billEnv", new JdbcTransactionFactory(), dataSource));
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setDataSource(dataSource);

        // 手动扫描映射文件
        sqlSessionFactory
            .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*.xml"));
        return sqlSessionFactory;
    }

    @Bean("billMapperScanner")
    public MapperScannerConfigurer mysqlMapperScanner(
        @Autowired @Qualifier("billSqlSessionFactory") SqlSessionFactoryBean sqlSessionFactory) throws Exception {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setSqlSessionFactory(sqlSessionFactory.getObject());
        scanner.setBasePackage("com.bill.dal.mapper");
        return scanner;
    }

    @Bean("billSmartDataSource")
    public SmartDataSource initTracer(@Autowired @Qualifier("billDataSource") DataSource dataSource,
        @Autowired @Qualifier("billNacosProperties") BillNacosProperties commonNacosProperties) throws Exception {
        SmartDataSource smartDataSource = new SmartDataSource();
        smartDataSource.setAppName(commonNacosProperties.getProperty("spring.application.name"));
        smartDataSource.setDelegate(dataSource);
        smartDataSource.setDatabase("corebankingdb");
        smartDataSource.setDbType("MYSQL");
        smartDataSource.init();
        return smartDataSource;
    }
}
