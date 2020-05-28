package server;

import java.io.*;
import java.net.*;

class socket_send implements Runnable {
    String host;
    int to_port;
    Block to_message;

    public socket_send(String host, int to_port, Block to_message) {
        this.host = host;
        this.to_port = to_port;
        this.to_message = to_message;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, to_port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(to_message);
            oos.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
