package cn.onyx.heartcheck;
//心跳检测
/**
 *
 * netty 提供的工具类IdleStateHandle  进行心跳检测


 public IdleStateHandler(
 int readerIdleTimeSeconds,
 int writerIdleTimeSeconds,
 int allIdleTimeSeconds) {

 this(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds,
 TimeUnit.SECONDS);
 }

 1）readerIdleTime：为读超时时间（即测试端一定时间内未接受到被测试端消息）

 2）writerIdleTime：为写超时时间（即测试端一定时间内向被测试端发送消息）

 3）allIdleTime：所有类型的超时时间


 ALL_IDLE : 一段时间内没有数据接收或者发送
 READER_IDLE ： 一段时间内没有数据接收
 WRITER_IDLE ： 一段时间内没有数据发送
 *
 * */
