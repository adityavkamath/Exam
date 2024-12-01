package com.mycompany.socketserver;

import java.net.*;
import java.io.*;

public class SocketServer {

    public static void main(String args[]) throws Exception {
        BufferedReader fileRead;
        PrintWriter pwrite;
        try (ServerSocket servSocket = new ServerSocket(4000)) {
            System.out.println("****Server Side****");
            System.out.println("Server ready for connection");
            try (Socket connSock = servSocket.accept()) {
                System.out.println("Connection is successful and ready for file transfer");
                InputStream istream = connSock.getInputStream();
                fileRead = new BufferedReader(new InputStreamReader(istream));
                String fname = fileRead.readLine();
                File fileName = new File(fname);
                OutputStream ostream = connSock.getOutputStream();
                pwrite = new PrintWriter(ostream, true);
                if (fileName.exists()) {
                    try (BufferedReader contentRead = new BufferedReader(new FileReader(fname))) {
                        System.out.println("Writing file contents to the socket");

                        String str;
                        while ((str = contentRead.readLine()) != null) {
                            pwrite.println(str);
                        }
                    }
                } else {
                    System.out.println("Requested file does not exist");
                    String msg = "Requested file does not exist at server side";
                    pwrite.println(msg);
                }
            }
        }
        fileRead.close();
        pwrite.close();
    }
}
