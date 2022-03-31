package com.xc.rpc.example;

import com.xc.rpc.example.constants.ReqType;
import com.xc.rpc.example.constants.RpcConstant;
import com.xc.rpc.example.constants.SerialType;
import com.xc.rpc.example.core.Header;
import com.xc.rpc.example.core.RequestHolder;
import com.xc.rpc.example.core.RpcFuture;
import com.xc.rpc.example.core.RpcProtocol;
import com.xc.rpc.example.core.RpcRequest;
import com.xc.rpc.example.core.RpcResponse;
import com.xc.rpc.example.protocol.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * description
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 6:20 PM
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private String host;
    private int port;

    public RpcInvokerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("被代理执行");
        log.info("begin invoke target server");
        RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
        //请求头构建
        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        Header header = new Header(RpcConstant.MAGIC, SerialType.JSON_SERIAL.code(),
                ReqType.REQUEST.code(), requestId,0);
        reqProtocol.setHeader(header);
        //请求报文构建
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        reqProtocol.setContent(request);

        NettyClient nettyClient = new NettyClient(host, port);
        RpcFuture<RpcResponse> future
                = new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId, future);
        nettyClient.sendRequest(reqProtocol);

        return future.getPromise().get().getData();
    }

}
