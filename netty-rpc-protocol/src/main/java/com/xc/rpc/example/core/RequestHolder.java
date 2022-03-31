package com.xc.rpc.example.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 请求ID管理类
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 6:29 PM
 */
public class RequestHolder {

    //生成请求ID的线程安全对象
    public static final AtomicLong REQUEST_ID = new AtomicLong();

    //保存请求和当前异步返回future的关联关系
    public static final Map<Long,RpcFuture> REQUEST_MAP = new ConcurrentHashMap<>();

}
