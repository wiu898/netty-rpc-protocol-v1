package com.xc.rpc.example.handler;

import com.xc.rpc.example.codec.RpcDecoder;
import com.xc.rpc.example.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义客户端ChannelInitializer
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 6:46 PM
 */
@Slf4j
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("begin RpcClientInitializer");
        ch.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(
                        Integer.MAX_VALUE,
                        12,
                        4,
                        0,
                        0))
                .addLast(new LoggingHandler())
                .addLast(new RpcEncoder())
                .addLast(new RpcDecoder())
                .addLast(new RpcClientHandler());
    }
}
