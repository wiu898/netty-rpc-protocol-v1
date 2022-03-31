package com.xc.rpc.example.protocol;

import com.xc.rpc.example.core.RpcProtocol;
import com.xc.rpc.example.core.RpcRequest;
import com.xc.rpc.example.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 *  客户端服务发布
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 6:41 PM
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private String serviceAdderss;
    private int servicePort;


    public NettyClient(String serviceAdderss, int servicePort) {
        log.info("begin init Netty Client:{},{}", serviceAdderss, servicePort);
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());
        this.serviceAdderss = serviceAdderss;
        this.servicePort = servicePort;
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol){
        try {
            ChannelFuture future = bootstrap.connect(this.serviceAdderss, this.servicePort).sync();
            //future异步阻塞队列监听
            future.addListener(listener -> {
                if(future.isSuccess()){
                    log.info("connect rpc server {} success.", this.serviceAdderss);
                }else{
                    log.error("connetct rpc server {} failed.", this.serviceAdderss);
                    future.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();
                }
            });
            future.channel().writeAndFlush(protocol);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
