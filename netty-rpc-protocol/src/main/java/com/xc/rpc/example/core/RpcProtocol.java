package com.xc.rpc.example.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义消息协议传输数据包报文实体
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 3:25 PM
 */
@Data
public class RpcProtocol<T> implements Serializable {

    private Header header;
    private T content;

}
