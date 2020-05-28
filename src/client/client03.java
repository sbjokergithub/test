package client;

import javax.sound.sampled.Port;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class client03 {
    private static Socket socket;
    public static String serverName = "localhost";
    public static int port = 6060;
    public static int clientNumber;


    public static void main(String[] args) {
        clientNumber = 3;
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

            new Thread(new Client_listen(socket,  clientNumber)).start();
            new Thread(new Client_send(socket,  clientNumber)).start();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}

