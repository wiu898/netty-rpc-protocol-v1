package com.xc.rpc.example.serial;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化管理类
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 4:18 PM
 */
public class SerializerManager {

    private final static ConcurrentHashMap<Byte,ISerializer> serializer = new ConcurrentHashMap<>();

    static {
        ISerializer json = new JsonSerializer();
        ISerializer java = new JavaSerializer();
        serializer.put(json.getType(),json);
        serializer.put(java.getType(),java);
    }

    public static ISerializer getSerializer(byte key){
        ISerializer iSerializer = serializer.get(key);
        if(iSerializer == null){
            return new JavaSerializer();
        }else {
            return iSerializer;
        }
    }

}
