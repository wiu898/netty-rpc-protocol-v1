package com.xc.rpc.example;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        RpcClientProxy rcp = new RpcClientProxy();
        IUserService userService = rcp.clientProxy(IUserService.class,"localhost",8080);
        System.out.println(userService.saveUser("Mic"));
    }
}
