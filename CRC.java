package com.mycompany.crc;
import java.util.Scanner;

public class CRC {

    static String msg;
    static String genPoly = "110010";
    static char t[] = new char[128];
    static char cs[] = new char[128];
    static char g[] = new char[128];
    static int mlen, glen, x, c, flag = 0, test;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter message to be transferred");
        msg = sc.nextLine();
        mlen = msg.length();

        for (int i = 0; i < mlen; i++) {
            t[i] = msg.charAt(i);
        }

        System.out.println("Predefined genPoly: " + genPoly);
        g = genPoly.toCharArray();
        glen = genPoly.length();

        for (x = mlen; x < (mlen + glen - 1); x++) {
            t[x] = '0';
        }

        System.out.println("Zero extended message is: " + new String(t));

        crc();

        System.out.println("Checksum is: " + new String(cs));

        for (x = mlen; x < mlen + glen - 1; x++) {
            t[x] = cs[x - mlen];
        }

        System.out.println("Final codeword generated is: " + new String(t));

        System.out.println("Enter test error detection 1(yes) or 0(no): ");
        test = sc.nextInt();

        if (test == 1) {
            System.out.println("Enter the position where error is inserted: ");
            x = sc.nextInt();
            t[x] = (t[x] == '0') ? '1' : '0';
            System.out.println("Erroneous data: " + new String(t));

            crc();

            for (x = 0; x < (glen - 1); x++) {
                if (cs[x] == '1') {
                    flag = 1;
                    break;
                }
            }

            if (flag == 1) {
                System.out.println("Error was detected during transfer");
            } else {
                System.out.println("No error was detected during transfer");
            }
        }
    }

    public static void crc() {
        for (x = 0; x < glen; x++) {
            cs[x] = t[x];
        }

        do {
            if (cs[0] == '1') {
                xor();
            }
            for (c = 0; c < glen - 1; c++) {
                cs[c] = cs[c + 1];
            }
            cs[c] = t[x++];
        } while (x <= mlen + glen - 1);
    }

    public static void xor() {
        for (c = 1; c < glen; c++) {
            cs[c] = (cs[c] == g[c]) ? '0' : '1';
        }
    }
}
