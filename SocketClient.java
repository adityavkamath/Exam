package com.mycompany.socketclient;

import java.net.*;
import java.io.*;
import java.util.*;

public class SocketClient {

    public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        try (Socket clientSocket = new Socket("127.0.0.1", 4000)) {
            System.out.println("****Client side****");
            
            System.out.println("Enter the file name to transfer");
            String fname = in.nextLine();
            
            OutputStream ostream = clientSocket.getOutputStream();
            BufferedReader socketRead;
            try (PrintWriter pwrite = new PrintWriter(ostream, true)) {
                pwrite.println(fname);
                InputStream istream = clientSocket.getInputStream();
                socketRead = new BufferedReader(new InputStreamReader(istream));
                System.out.println("Contents of the file " + fname + " are");
                String str;
                while ((str = socketRead.readLine()) != null) {
                    System.out.println(str);
                }
            }
            socketRead.close();
        }
    }
}
