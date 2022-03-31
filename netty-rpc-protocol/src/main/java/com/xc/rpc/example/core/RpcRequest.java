package com.xc.rpc.example.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义消息协议请求体
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 3:09 PM
 */
@Data
public class RpcRequest implements Serializable {

    private String className;           //类名
    private String methodName;          //请求目标方法
    private Object[] params;            //请求参数
    private Class<?>[] parameterTypes;  //参数类型

}
