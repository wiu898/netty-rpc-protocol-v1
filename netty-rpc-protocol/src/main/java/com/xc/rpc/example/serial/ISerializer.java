package com.xc.rpc.example.serial;

/**
 * 序列化接口
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 4:04 PM
 */
public interface ISerializer {

    /*
     * 序列化方法
     */
    <T> byte[] serialize(T obj);

    /*
     * 反序列化方法
     */
    <T> T deserialize(byte[] data, Class<T> clazz);

    /*
     * 序列化类型
     */
    byte getType();


}
