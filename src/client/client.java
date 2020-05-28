package client;

import java.net.*;
import java.io.*;


public class client {
    private static Socket socket;
    public static String serverName = "localhost";
    public static int port = 6060;
    private static int clientNumber;

    public static void main(String[] args) {
        clientNumber = 0;
        while (true) {
            connect(serverName, port);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void connect(String serverName, int port) {
        try {
            socket = new Socket(serverName, port);
            Thread t1 = new Thread(new Client_listen(socket, clientNumber));
            Thread t2 = new Thread(new Client_send(socket, clientNumber));

            t1.start();
            t2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Client_listen implements Runnable {
    private Socket socket;
    int number;

    Client_listen(Socket socket, int number) {
        this.socket = socket;
        this.number = number;
    }

    @Override
    public void run() {
        try {
            boolean t = true;
            while (t) {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String s = ois.readUTF();
                if (s.length() > 0) {
                    System.out.println(s);
                    t = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Client_send implements Runnable {
    private Socket socket;
    private int number;

    Client_send(Socket socket, int number) {
        this.socket = socket;
        this.number = number;
    }

    @Override
    public void run() {
        boolean t = true;
        try {
            while (t) {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeUTF("" + number + " is connect");
                oos.flush();
                t = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
}
