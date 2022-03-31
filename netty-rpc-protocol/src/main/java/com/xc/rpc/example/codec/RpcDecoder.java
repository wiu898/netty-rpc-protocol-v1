package com.xc.rpc.example.codec;

import com.xc.rpc.example.constants.ReqType;
import com.xc.rpc.example.constants.RpcConstant;
import com.xc.rpc.example.core.Header;
import com.xc.rpc.example.core.RpcProtocol;
import com.xc.rpc.example.core.RpcRequest;
import com.xc.rpc.example.core.RpcResponse;
import com.xc.rpc.example.serial.ISerializer;
import com.xc.rpc.example.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 自定义解码器
 *
 * @author lichao chao.li07@hand-china.com 3/31/22 4:23 PM
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("========begin RpcDecoder========");
        //可读数据 < 头部长度说明数据有问题，不接收
        if(in.readableBytes() <  RpcConstant.HEAD_TOTAL_LEN){
            return;
        }
        //标记读取开始的索引位置
        in.markReaderIndex();
        //读取2个字节的magic
        short magic = in.readShort();
        if(magic != RpcConstant.MAGIC){
            throw new IllegalArgumentException("Illegal request parameter 'magic':" + magic);
        }

        byte serialType = in.readByte();   //读取一个字节的序列化类型
        byte reqType = in.readByte();      //读取一个字节的消息类型
        long requestId = in.readLong();    //读取请求id
        int dataLength = in.readInt();     //读取数据报文长度
        //剩余可读区域长度如果 < 报文长度代表消息有问题
        if(in.readableBytes() < dataLength){
            in.resetReaderIndex();  //还原已读数据
            return;
        }

        //读取消息内容
        byte[] content = new byte[dataLength];
        in.readBytes(content);

        Header header = new Header(magic, serialType, reqType, requestId, dataLength);
        ISerializer serializer = SerializerManager.getSerializer(serialType);

        ReqType rt = ReqType.findByCode(reqType);
        switch (rt){
            case REQUEST:
                RpcRequest request = serializer.deserialize(content, RpcRequest.class);
                RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
                reqProtocol.setHeader(header);
                reqProtocol.setContent(request);
                out.add(reqProtocol);
                break;
            case RESPONSE:
                RpcResponse response = serializer.deserialize(content, RpcResponse.class);
                RpcProtocol<RpcResponse> resProtocol = new RpcProtocol<>();
                resProtocol.setHeader(header);
                resProtocol.setContent(response);
                out.add(resProtocol);
                break;
            case HEARTBEAT:
                //TODO
                break;
            default:
                break;
        }
    }

}
