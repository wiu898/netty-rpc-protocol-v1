package com.xc.rpc.example.constants;

/**
 * description
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 3:33 PM
 */
public enum SerialType {

    JSON_SERIAL((byte)1),
    JAVA_SERIAL((byte)2);

    private byte code;

    SerialType(byte code){
        this.code = code;
    }

    public byte code(){
        return this.code;
    }

}
