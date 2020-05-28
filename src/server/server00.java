package server;

import java.io.IOException;

public class server00 {
    public static int n = 4;
    public static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        int port = 6060;
        int number = 0;
        try {
            logSet logSet = new logSet();
            Thread.sleep(3000);

            Thread t1 = new Thread(new thread_server(port, number, logSet,lock));
            t1.start();

            Thread t2 = new Thread(new thread_PBFT(port, number, n, logSet, lock));
            t2.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
