package com.ubihacks.robopick;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by UBAID IFTIKHAR on 11/24/2017.
 */

 public class ConnectToDevice {

     public void SignalToDevice(final String ipAddress, final int portNo, final String message){

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    DatagramSocket s = null;
                    try {
                        s = new DatagramSocket();

                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    InetAddress ip = null;
                    try {
                        ip = InetAddress.getByName(ipAddress);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    int msg_length=message.length();
                    byte[] messageBytes = message.getBytes();
                    DatagramPacket p = new DatagramPacket(messageBytes, msg_length,ip, portNo);
                    try {
                        s.send(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {

                }
            }
        });
        thread.start();


    }
}
