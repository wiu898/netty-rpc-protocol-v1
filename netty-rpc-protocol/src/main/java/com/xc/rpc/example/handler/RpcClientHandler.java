package com.xc.rpc.example.handler;

import com.xc.rpc.example.core.RequestHolder;
import com.xc.rpc.example.core.RpcFuture;
import com.xc.rpc.example.core.RpcProtocol;
import com.xc.rpc.example.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义客户端Handler
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 7:07 PM
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("receive Rpc Server Result");
        long requestId = msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> future = RequestHolder.REQUEST_MAP.remove(requestId);
        future.getPromise().setSuccess(msg.getContent());  //返回结果
    }

}
