package com.xc.rpc.example.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义消息协议请求头
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 3:09 PM
 */
@Data
@AllArgsConstructor
public class Header implements Serializable {

    private short magic;        //魔数 2个字节
    private byte serialType;    //序列化类型 1字节
    private byte reqType;       //消息类型 1字节
    private long requestId;     //请求Id 8字节
    private int length;         //消息体长度 4字节

}
