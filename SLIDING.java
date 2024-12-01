package com.mycompany.sliding;

public class SLIDING {

    private final int windowSize;
    private final int[] frames;
    private final boolean[] ack;

    public SLIDING(int windowSize, int frameCount) {
        this.windowSize = windowSize;
        this.frames = new int[frameCount];
        this.ack = new boolean[frameCount];

        for (int i = 0; i < frameCount; i++) {
            frames[i] = i;
            ack[i] = false;
        }
    }

    public void sendFrames() {
        int sendIndex = 0;

        while (sendIndex < frames.length) {
            for (int i = 0; i < windowSize && (sendIndex + i) < frames.length; i++) {
                System.out.println("Sending frame: " + frames[sendIndex + i]);
            }

            for (int i = 0; i < windowSize && (sendIndex + i) < frames.length; i++) {
                ack[sendIndex + i] = receiveAck(sendIndex + i);
            }

            while (sendIndex < frames.length && ack[sendIndex]) {
                sendIndex++;
            }
        }
    }

    private boolean receiveAck(int frame) {
        System.out.println("Receiving ack for frame: " + frame);
        return true;
    }

    public static void main(String[] args) {
        int windowSize = 4;
        int frameCount = 10;

        SLIDING swp = new SLIDING(windowSize, frameCount);
        swp.sendFrames();
    }
}
