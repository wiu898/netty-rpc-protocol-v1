package com.xc.rpc.example;

import com.xc.rpc.example.protocol.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务提供端启动类-发布spring服务
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 3:07 PM
 */
@ComponentScan(basePackages = {"com.xc.rpc.example.spring","com.xc.rpc.example.service"})
@SpringBootApplication
public class NettyRpcProvider {

    public static void main(String[] args) {
        //启动spring容器
        SpringApplication.run(NettyRpcProvider.class, args);
        //启动Netty服务监听请求
        new NettyServer("localhost",8080).startNettyServer();
    }

}
