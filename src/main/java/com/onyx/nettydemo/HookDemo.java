package com.onyx.nettydemo;

/**
 * @author zk
 * @Description: 钩子实验
 * @date 2018-08-17 16:27
 */
public class HookDemo {

    public static void main(String[] args) {

        System.out.println("1");

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1111执行了钩子中的方法了,应该是最后被执行");
                /*Runtime rt=Runtime.getRuntime();
                if((rt.totalMemory()-rt.freeMemory())/(double)rt.maxMemory()>0.90){
                    //内存利用率到90%以上时，执行相关清理工作
                }*/
            }
        }));


        /**
         * 关闭钩子本质上是一个线程（也称为Hook线程），用来监听JVM的关闭。通过使用Runtime的addShutdownHook(Thread hook)可以向JVM注册一个关闭钩子。
         * Hook线程在JVM 正常关闭才会执行，在强制关闭时不会执行。
         *
         * 对于一个JVM中注册的多个关闭钩子它们将会并发执行，所以JVM并不能保证它的执行顺行。当所有的Hook线程执行完毕后，
         * 如果此时runFinalizersOnExit为true，那么JVM将先运行终结器，然后停止。Hook线程会延迟JVM的关闭时间，
         * 这就要求在编写钩子过程中必须要尽可能的减少Hook线程的执行时间。另外由于多个钩子是并发执行的，
         * 那么很可能因为代码不当导致出现竞态条件或死锁等问题，为了避免该问题，强烈建议在一个钩子中执行一系列操作。
         *
         * 另外在使用关闭钩子还要注意以下几点：
         1. 不能在钩子调用System.exit()，否则卡住JVM的关闭过程，但是可以调用Runtime.halt()。
         2. 不能再钩子中再进行钩子的添加和删掉操作，否则将会抛出IllegalStateException。
         3. 在System.exit()之后添加的钩子无效。
         4. 当JVM收到SIGTERM命令（比如操作系统在关闭时）后，如果钩子线程在一定时间没有完成，那么Hook线程可能在执行过程中被终止。
         5. Hool线程中同样会抛出异常，如果抛出异常又不处理，那么钩子的执行序列就会被停止。
         *
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("添加第二个钩子,看看顺序......");
            }
        }));


        try {
            System.out.println("start sleep");
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("after sleep");


    }


}
