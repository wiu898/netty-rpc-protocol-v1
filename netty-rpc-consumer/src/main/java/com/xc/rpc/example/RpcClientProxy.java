package com.xc.rpc.example;

import java.lang.reflect.Proxy;

/**
 * 客户端动态代理类，代理Service
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 6:17 PM
 */
public class RpcClientProxy {

    public <T> T clientProxy(final Class<T> interfaceClass, final String host, int port){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new RpcInvokerProxy(host,port));
    }
}
