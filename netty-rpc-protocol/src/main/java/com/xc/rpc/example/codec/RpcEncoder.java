package com.xc.rpc.example.codec;

import com.xc.rpc.example.core.Header;
import com.xc.rpc.example.core.RpcProtocol;
import com.xc.rpc.example.serial.ISerializer;
import com.xc.rpc.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义编码器
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 4:23 PM
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("========begin RpcEncoder========");
        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getReqType());
        out.writeLong(header.getRequestId());
        //获得序列化实现类
        ISerializer serializer = SerializerManager.getSerializer(header.getSerialType());
        //对消息进行编码
        byte[] data = serializer.serialize(msg.getContent());
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
