package server;

import java.net.*;
import java.io.*;

class thread_server implements Runnable {
    int port;
    int number;
    logSet logSet;
    ServerSocket serverSocket;
    Object lock;

    thread_server(int port, int number, logSet logSet, Object lock) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.number = number;
        this.logSet = logSet;
        this.lock = lock;
    }


    @Override
    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Block message = (Block) ois.readObject();

                Thread t = new Thread(new do_thing(message));
                t.start();

                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class do_thing implements Runnable {
        Block message;

        public do_thing(Block message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    if (message.block_n == logSet.block_n) {
                        //System.out.println(message.getNumber() + " " + message.getK());
                        logSet.add(message.getK(), message.getNumber());
                    }
                    lock.notifyAll();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}