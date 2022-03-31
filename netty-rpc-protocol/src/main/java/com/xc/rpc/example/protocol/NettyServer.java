package com.xc.rpc.example.protocol;

import com.xc.rpc.example.handler.RpcServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端服务发布
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 5:22 PM
 */
@Slf4j
public class NettyServer {

    private String serverAdderss;   //服务地址
    private int serverPort;         //端口

    public NettyServer(String serverAdderss, int serverPort) {
        this.serverAdderss = serverAdderss;
        this.serverPort = serverPort;
    }

    public void startNettyServer(){
        log.info("begin start Netty server");
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new RpcServerInitializer());
        try {
            ChannelFuture future = bootstrap.bind(this.serverAdderss, this.serverPort).sync();
            log.info("Server started Success on Port:{}",this.serverPort);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }


}
