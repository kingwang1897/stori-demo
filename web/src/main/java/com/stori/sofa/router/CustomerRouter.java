package com.stori.sofa.router;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alipay.sofa.rpc.bootstrap.ConsumerBootstrap;
import com.alipay.sofa.rpc.client.ProviderInfo;
import com.alipay.sofa.rpc.client.Router;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.filter.AutoActive;

/**
 * 自定义路由寻址
 *
 * 1. 该router仅在dev环境下生效 2. 使用application名称的后缀作为迭代标识（或者连调环境标识），当存在后缀相关的服务提供方则优先使用，否则直接返回上一步中获取的服务提供方列表
 */
@Extension(value = "customerRouter", order = -1)
@AutoActive(consumerSide = true)
public class CustomerRouter extends Router {

    /**
     * init
     *
     * @param consumerBootstrap
     */
    @Override
    public void init(ConsumerBootstrap consumerBootstrap) {
        super.init(consumerBootstrap);
    }

    /**
     * router生效条件，仅dev环境生效.
     *
     * 当前使用application名称是否带有 dev 来判断是否为dev环境。
     *
     * @param consumerBootstrap
     *            bootstrap
     * @return router是否生效。 true：router生效；false：router不生效
     */
    @Override
    public boolean needToLoad(ConsumerBootstrap consumerBootstrap) {
        return true;
    }

    /**
     * 自定义路由规则，在sofa-rpc自带的router后执行。 **适用于依赖其他应用发布的服务**
     *
     * 若服务提供方列表中有与服务调用方应用名称同后缀的，则优先使用，否则直接返回上一个router获取的服务提供方列表
     *
     * @param request
     *            sofa request
     * @param providerInfos
     *            sofa service provider list
     * @return 可用的服务提供方列表
     */
    @Override
    public List<ProviderInfo> route(SofaRequest request, List<ProviderInfo> providerInfos) {
        List<ProviderInfo> result = new ArrayList<>();
        for (ProviderInfo providerInfo : providerInfos) {
            if (providerInfo.getAttr("appName").endsWith("dev")) {
                result.add(providerInfo);
            }
        }

        if (CollectionUtils.isEmpty(result)) {
            result = providerInfos;
        }
        return result;
    }
}
