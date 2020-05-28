package server;

import java.io.IOException;

public class server03 {
    public static int n = 4;
    public static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        int port = 6063;
        int number = 3;
        try {
            logSet logSet = new logSet();
            Thread.sleep(1000);

            Thread t1 = new Thread(new thread_server(port, number, logSet,lock));
            t1.start();

            Thread t2 = new Thread(new thread_PBFT(port, number, n, logSet, lock));
            t2.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
