package com.bill.config;

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
import com.stori.sofa.security.secrets.SecretsManager;

/**
 * Druid config
 *
 * @author wangkai
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
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        // 用户名也可以自己设置，及直接写死
        reg.addInitParameter("loginUsername", "root");
        // 密码也可以自己设置，及直接写死
        reg.addInitParameter("loginPassword", "root");
        // 慢SQL记录
        reg.addInitParameter("logSlowSql", environment.getProperty("spring.datasource.bill.username"));
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
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(environment.getProperty("spring.datasource.bill.url"));
        datasource.setUsername(environment.getProperty("spring.datasource.bill.username"));
        datasource.setPassword(SecretsManager.decrypt(environment.getProperty("spring.datasource.bill.password")));
        datasource.setDriverClassName(environment.getProperty("spring.datasource.bill.driver"));

        // // configuration
        // datasource.setInitialSize(environment.getProperty("spring.datasource.bill.initialSize", Integer.class));
        // datasource.setMinIdle(environment.getProperty("spring.datasource.bill.minIdle", Integer.class));
        // datasource.setMaxActive(environment.getProperty("spring.datasource.bill.maxActive", Integer.class));
        // datasource.setMaxWait(environment.getProperty("spring.datasource.bill.maxWait", Integer.class));
        // datasource.setTimeBetweenEvictionRunsMillis(
        // environment.getProperty("spring.datasource.bill.timeBetweenEvictionRunsMillis", Integer.class));
        // datasource.setMinEvictableIdleTimeMillis(
        // environment.getProperty("spring.datasource.bill.minEvictableIdleTimeMillis", Integer.class));
        // datasource.setValidationQuery(environment.getProperty("spring.datasource.bill.validationQuery"));
        // datasource.setTestWhileIdle(environment.getProperty("spring.datasource.bill.testWhileIdle", boolean.class));
        // datasource.setTestOnBorrow(environment.getProperty("spring.datasource.bill.testOnBorrow", boolean.class));
        // datasource.setTestOnReturn(environment.getProperty("spring.datasource.bill.testOnReturn", boolean.class));
        // datasource.setPoolPreparedStatements(
        // environment.getProperty("spring.datasource.bill.poolPreparedStatements", boolean.class));
        // datasource.setMaxPoolPreparedStatementPerConnectionSize(
        // environment.getProperty("spring.datasource.bill.maxPoolPreparedStatementPerConnectionSize", Integer.class));
        // try {
        // datasource.setFilters(environment.getProperty("spring.datasource.bill.filters"));
        // } catch (SQLException e) {
        // logger.error("druid configuration initialization filter", e);
        // }
        // datasource.setConnectionProperties(environment.getProperty("spring.datasource.bill.connectionProperties"));

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
    public SmartDataSource initTracer(@Autowired @Qualifier("billDataSource") DataSource dataSource) throws Exception {
        SmartDataSource smartDataSource = new SmartDataSource();
        smartDataSource.setAppName("module-bill-biz");
        // smartDataSource.setAppName(environment.getProperty("spring.application.name"));
        smartDataSource.setDelegate(dataSource);
        smartDataSource.setDatabase("corebankingdb");
        smartDataSource.setDbType("MYSQL");
        smartDataSource.init();
        return smartDataSource;
    }
}
