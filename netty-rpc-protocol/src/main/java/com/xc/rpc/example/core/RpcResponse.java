package com.xc.rpc.example.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义消息协议请求响应
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 3:09 PM
 */
@Data
public class RpcResponse implements Serializable {

    private Object data;
    private String msg;

}
