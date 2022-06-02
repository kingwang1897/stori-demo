package com.account.config;

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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.account.model.AccountNacosProperties;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alipay.sofa.tracer.plugins.datasource.SmartDataSource;

/**
 * Druid config
 *
 * @author king
 * @date 2022/05/23 14:53
 **/
@Configuration
public class AccountDruidDBConfig implements EnvironmentAware {
    private Logger logger = LoggerFactory.getLogger(AccountDruidDBConfig.class);

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
    @Primary
    @Bean("accountServletRegistrationBean")
    public ServletRegistrationBean
        druidServlet(@Autowired @Qualifier("accountNacosProperties") AccountNacosProperties commonNacosProperties) {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        // 用户名也可以自己设置，及直接写死
        reg.addInitParameter("loginUsername",
            commonNacosProperties.getProperty("spring.datasource.account.loginUsername"));
        // 密码也可以自己设置，及直接写死
        reg.addInitParameter("loginPassword",
            commonNacosProperties.getProperty("spring.datasource.account.loginPassword"));
        // 慢SQL记录
        reg.addInitParameter("logSlowSql", commonNacosProperties.getProperty("spring.datasource.account.username"));
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
    @Primary
    @Bean("accountFilterRegistrationBean")
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
    @Bean("accountDataSource")
    public DataSource
        dataSource(@Autowired @Qualifier("accountNacosProperties") AccountNacosProperties commonNacosProperties) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(commonNacosProperties.getProperty("spring.datasource.account.url"));
        datasource.setUsername(commonNacosProperties.getProperty("spring.datasource.account.username"));
        datasource.setPassword(commonNacosProperties.getProperty("spring.datasource.account.password"));
        datasource.setDriverClassName(commonNacosProperties.getProperty("spring.datasource.account.driver"));

        // configuration
        datasource
            .setInitialSize(commonNacosProperties.getProperty("spring.datasource.account.initialSize", Integer.class));
        datasource.setMinIdle(commonNacosProperties.getProperty("spring.datasource.account.minIdle", Integer.class));
        datasource
            .setMaxActive(commonNacosProperties.getProperty("spring.datasource.account.maxActive", Integer.class));
        datasource.setMaxWait(commonNacosProperties.getProperty("spring.datasource.account.maxWait", Integer.class));
        datasource.setTimeBetweenEvictionRunsMillis(commonNacosProperties
            .getProperty("spring.datasource.account.timeBetweenEvictionRunsMillis", Integer.class));
        datasource.setMinEvictableIdleTimeMillis(
            commonNacosProperties.getProperty("spring.datasource.account.minEvictableIdleTimeMillis", Integer.class));
        datasource.setValidationQuery(commonNacosProperties.getProperty("spring.datasource.account.validationQuery"));
        datasource.setTestWhileIdle(
            commonNacosProperties.getProperty("spring.datasource.account.testWhileIdle", boolean.class));
        datasource.setTestOnBorrow(
            commonNacosProperties.getProperty("spring.datasource.account.testOnBorrow", boolean.class));
        datasource.setTestOnReturn(
            commonNacosProperties.getProperty("spring.datasource.account.testOnReturn", boolean.class));
        datasource.setPoolPreparedStatements(
            commonNacosProperties.getProperty("spring.datasource.account.poolPreparedStatements", boolean.class));
        datasource.setMaxPoolPreparedStatementPerConnectionSize(commonNacosProperties
            .getProperty("spring.datasource.account.maxPoolPreparedStatementPerConnectionSize", Integer.class));
        try {
            datasource.setFilters(commonNacosProperties.getProperty("spring.datasource.account.filters"));
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(
            commonNacosProperties.getProperty("spring.datasource.account.connectionProperties"));

        return datasource;
    }

    @Primary
    @Bean("accountSqlSessionFactory")
    public SqlSessionFactoryBean
        mysqlSqlSessionFactory(@Autowired @Qualifier("accountSmartDataSource") DataSource dataSource) throws Exception {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setEnvironment(new Environment("accountEnv", new JdbcTransactionFactory(), dataSource));
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setDataSource(dataSource);

        // 手动扫描映射文件
        sqlSessionFactory
            .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/*.xml"));
        return sqlSessionFactory;
    }

    @Primary
    @Bean("accountMapperScanner")
    public MapperScannerConfigurer mysqlMapperScanner(
        @Autowired @Qualifier("accountSqlSessionFactory") SqlSessionFactoryBean sqlSessionFactory) throws Exception {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setSqlSessionFactory(sqlSessionFactory.getObject());
        scanner.setBasePackage("com.account.dal.mapper");
        return scanner;
    }

    @Primary
    @Bean("accountSmartDataSource")
    public SmartDataSource initTracer(@Autowired @Qualifier("accountDataSource") DataSource dataSource,
        @Autowired @Qualifier("accountNacosProperties") AccountNacosProperties commonNacosProperties) throws Exception {
        SmartDataSource smartDataSource = new SmartDataSource();
        smartDataSource.setAppName(commonNacosProperties.getProperty("spring.application.name"));
        smartDataSource.setDelegate(dataSource);
        smartDataSource.setDatabase("corebankingdb");
        smartDataSource.setDbType("MYSQL");
        smartDataSource.init();
        return smartDataSource;
    }
}
