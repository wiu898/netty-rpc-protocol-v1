package com.xc.rpc.example.serial;

import com.alibaba.fastjson.JSON;
import com.xc.rpc.example.constants.SerialType;

/**
 * Json序列化实现
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 4:10 PM
 */
public class JsonSerializer implements ISerializer{
    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data), clazz);
    }

    @Override
    public byte getType() {
        return SerialType.JSON_SERIAL.code();
    }
}
