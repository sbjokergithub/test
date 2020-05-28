package server;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class thread_PBFT implements Runnable {
    int port;
    int number;
    int n;
    int block_number;
    long time;
    logSet logSet;
    ArrayList<Transaction> list;
    int k;    /*k means the process. k=1, pre-prepare. k=2. prepare. k=3, commit.*/
    ExecutorService service;
    Object lock;
    String[] host = new String[20];
    int[] to_port = new int[20];

    public thread_PBFT(int port, int number, int n, logSet logSet, Object lock) {
        this.port = port;
        this.number = number;
        this.n = n;
        this.block_number = 0;
        this.service = Executors.newFixedThreadPool(3 * n);
        this.logSet = logSet;
        this.list = new ArrayList<Transaction>();
        this.lock = lock;
    }

    @Override
    public void run() {

        initial();

        Transaction transaction = new Transaction(0, 1, 100);
        for (int i = 0; i < 10000; i++)
            list.add(transaction);

//        if (number == 0){
//
//        }
        while (block_number < 30) {
            try {
                synchronized (lock) {
                    if (number == 0) {
                        PBFT_start();

                        while (logSet.size(1) < myMin(n) - 1) {
                            lock.wait();
                        }

                        PBFT_commit();

                        while (logSet.size(2) < myMin(n) - 1) {
                            lock.wait();
                        }

                        block_generate();

                        block_number += 1;

                    } else {
                        while (logSet.size(0) == 0)
                            lock.wait();

                        PBFT_prepare();

                        while (logSet.size(1) < myMin(n) - 1) {
                            lock.wait();
                        }

                        PBFT_commit();

                        block_number += 1;

                        logSet.clearAll();

                    }


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private int myMin(int n) {
        return n - (n-1) / 3;
    }


    private void block_generate(){
        long now_time = System.currentTimeMillis();
        System.out.println("Block " + block_number + "is generated at " + now_time);
        System.out.println("A Block used time is " + (now_time - time));
        fileBlockOut(block_number, now_time, list);
        logSet.clearAll();
    }

    private void fileBlockOut(int block_number, long now_time, ArrayList<Transaction> list) {
        String fileName = "block" + block_number;
        File file = new File("E:/Block/test1/" + fileName + ".txt");
        StringBuilder content = new StringBuilder();
        content.append("Block Head").append("\n");
        content.append("block number:").append(block_number).append("\n");
        content.append("timestamp:").append(now_time).append("\n");
        content.append("Block Body").append("\n");
        for (Transaction x : list){
            content.append(x.toString()).append("\n");
        }

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*k = 1
     * */
    private void PBFT_start() {
        System.out.println("PBFT start");
        time = System.currentTimeMillis();
        Block b = new Block(number, 0, "", list, this.block_number);
        PBFT_send(b);
    }

    private void PBFT_prepare() {
        System.out.println("PBFT prepare");
        Block b = new Block(number, 1, "", list, this.block_number);
        PBFT_send(b);
    }

    private void PBFT_commit() {
        System.out.println("PBFT commit");
        Block b = new Block(number, 2, "", list, this.block_number);
        PBFT_send(b);
    }

    private void PBFT_send(Block to_message) {
        for (int i = 0; i < n; i++)
            if (to_port[i] != port) {
                service.submit(new socket_send(host[i], to_port[i], to_message));

            }
    }


    private void initial() {
        host[0] = "47.96.101.47";
        for (int i=1; i<4; i++)
            host[i] = "125.116.213.224";
        for (int i=0; i<4; i++)
            to_port[i] = 6060+i;
    }


}
