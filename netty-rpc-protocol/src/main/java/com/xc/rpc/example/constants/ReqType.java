package com.xc.rpc.example.constants;

/**
 * description
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 3:33 PM
 */
public enum ReqType {

    REQUEST((byte)1),
    RESPONSE((byte)2),
    HEARTBEAT((byte)3);

    private byte code;

    ReqType(byte code){
        this.code = code;
    }

    public byte code(){
        return this.code;
    }

    public static ReqType findByCode(int code){
        for(ReqType reqType : ReqType.values()){
            if(reqType.code == code){
                return reqType;
            }
        }
        return null;
    }

}
