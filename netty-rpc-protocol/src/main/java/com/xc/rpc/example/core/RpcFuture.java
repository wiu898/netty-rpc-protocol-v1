package com.xc.rpc.example.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * 自定义异步返回的Future
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 6:55 PM
 */
@Data
public class RpcFuture<T> {

    private Promise<T> promise;

    public RpcFuture(Promise<T> promise) {
        this.promise = promise;
    }


}
