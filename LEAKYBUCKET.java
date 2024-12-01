package com.mycompany.leakybucket;
import java.util.*;

public class LEAKYBUCKET {
    public static void main(String args[]) {
        int i, remainder = 0, sent = 0;
        String accept;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of packets");
        int n = sc.nextInt();
        int arr[] = new int[n];

        System.out.println("Enter the packet values");
        for (i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.println("The Entered values are ");
        for (i = 0; i < n; i++) {
            System.out.println(arr[i]);
        }

        System.out.println("Enter the Bucket Size");
        int bucketsize = sc.nextInt();

        System.out.println("Enter the Flow Rate");
        int flowrate = sc.nextInt();

        System.out.println("Clock \t Packet-Size \t Accept \t Sent \t Remaining");

        for (i = 0; i < n; i++) {
            int clk = i + 1;
            int packetsize = arr[i];

            if (packetsize <= bucketsize) {
                accept = Integer.toString(packetsize);
                packetsize += remainder;

                if (packetsize > flowrate) {
                    sent = flowrate;
                    remainder = packetsize - flowrate;
                } else {
                    sent = packetsize;
                    remainder = 0; 
                }
            } else {
                accept = "dropped";
                sent = 0;
            }

            System.out.println(clk + "\t " + arr[i] + "\t\t " + accept + "\t " + sent + "\t " + remainder);
        }
    }
}
