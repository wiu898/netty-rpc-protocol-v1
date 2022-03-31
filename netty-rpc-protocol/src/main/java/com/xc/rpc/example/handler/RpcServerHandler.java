package com.xc.rpc.example.handler;

import com.xc.rpc.example.constants.ReqType;
import com.xc.rpc.example.core.Header;
import com.xc.rpc.example.core.RpcProtocol;
import com.xc.rpc.example.core.RpcRequest;
import com.xc.rpc.example.core.RpcResponse;
import com.xc.rpc.example.spring.SpringBeanManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义服务端Handler
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 5:32 PM
 */
@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        RpcProtocol<RpcResponse> resProtocol = new RpcProtocol();
        Header header = msg.getHeader();
        header.setReqType(ReqType.RESPONSE.code());
        Object result = invoke(msg.getContent());
        resProtocol.setHeader(header);
        RpcResponse response = new RpcResponse();
        response.setData(result);
        response.setMsg("success");
        resProtocol.setContent(response);
        ctx.writeAndFlush(resProtocol);
    }

    /*
     * 反射调用的方法
     */
    private Object invoke(RpcRequest request){
        try {
            Class<?> clazz = Class.forName(request.getClassName());
            //从spring容器中获取对象实例
            Object bean = SpringBeanManager.getBean(clazz);
            //获取需要调用的方法
            Method method = clazz.getDeclaredMethod(request.getMethodName(),request.getParameterTypes());
            //反射调用方法be
            return method.invoke(bean,request.getParams());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
