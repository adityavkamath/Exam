package com.mycompany.datagramsocketclient;
import java.net.*;

public class DatagramSocketClient {
    public static void main(String[] args) throws Exception {
        String line = "Connected with Client";

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] sendData = line.getBytes();
        byte[] receiveData = new byte[1024];

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9000);
        clientSocket.send(sendPacket);

        System.out.println("*****Client Display Terminal*****");

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String messageReceived = new String(
                receivePacket.getData(),
                receivePacket.getOffset(),
                receivePacket.getLength()
            );
            System.out.println("Message typed at server side is: " + messageReceived);
        }
    }
}
