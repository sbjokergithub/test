package server;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.util.Set;

class thread_client implements Runnable{
    Socket client_socket;
    String server_name;
    int my_port;
    int client_number;
    String to_message;

    public thread_client(int my_port, int client_number, String to_message) {
        this.my_port = my_port;
        this.client_number = client_number;
        this.to_message = to_message;
    }

    thread_client(int port, int number) {
        this.server_name = "localhost";
        this.my_port = port;
        this.client_number = number;
    }

    @Override
    public void run() {
        int to_port;
        if (my_port == 6060)
            to_port = 6061;
        else
            to_port = 6060;
//        to_port = (my_port == 6060) ? 6061 : 6060;
        while (true) {
            try {
                client_socket = new Socket(server_name, to_port);

                Thread t1 = new Thread(new client_listen(client_socket, client_number));
                Thread t2 = new Thread(new client_send(client_socket, client_number));

                t1.start();
                t2.start();

                Thread.sleep(10000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class client_listen implements Runnable {
        Socket socket;
        int number;

        public client_listen(Socket socket, int number) {
            this.socket = socket;
            this.number = number;
        }

        @Override
        public void run() {
            try {
                boolean t = true;
                ObjectInputStream ois = new ObjectInputStream(client_socket.getInputStream());
                while (t) {
                    String s = ois.readUTF();
                    if (s.length() > 0) {
                        panduan(s);
                        t = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    class client_send implements Runnable {
        Socket socket;
        int number;

        public client_send(Socket socket, int number) {
            this.socket = socket;
            this.number = number;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private void panduan(String s) {
        //System.out.println("hahaha ");
    }


}
