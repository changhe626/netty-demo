package com.onyx.my_encode_decode.common;

/**
 * 自定义编码,解码
 * 请求对象
 */
public class Request {


    /**
     * 请求模块
     */
    private short module;

    /**
     * 命令号
     */
    private short cmd;

    /**
     * 请求数据
     */
    private byte[] data;

    public int getLength(){
        if(data==null){
            return 0;
        }else {
            return data.length;
        }
    }


    public short getModule() {
        return module;
    }

    public void setModule(short module) {
        this.module = module;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
