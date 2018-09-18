package com.onyx.my_encode_decode.common;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.nio.ByteOrder;

/**
 * 自定义序列化  buff工厂
 * MySerialProtocol4
 *  netty 3
 */
public class BufferFactory {
	
	public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

	/**
	 * 获取一个buffer
	 */
	public static ChannelBuffer getBuffer() {
		ChannelBuffer dynamicBuffer = ChannelBuffers.dynamicBuffer();
		return dynamicBuffer;
	}

	/**
	 * 将数据写入buffer
	 */
	public static ChannelBuffer getBuffer(byte[] bytes) {
		ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer(bytes);
		return copiedBuffer;
	}

}
