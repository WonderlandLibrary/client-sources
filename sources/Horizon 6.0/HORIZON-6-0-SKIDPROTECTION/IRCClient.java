package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Scanner;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class IRCClient implements Runnable
{
    private Thread HorizonCode_Horizon_È;
    private Thread Â;
    private Thread Ý;
    private InetAddress Ø­áŒŠá;
    private DatagramSocket Âµá€;
    private int Ó;
    private boolean à;
    private int Ø;
    
    public IRCClient(final int port) {
        this.HorizonCode_Horizon_È("WORK IN PROGRESS", port);
        this.à = true;
        (this.HorizonCode_Horizon_È = new Thread(this, "Client Thread")).start();
    }
    
    public void HorizonCode_Horizon_È() {
        final String Name = Horizon.É;
        this.HorizonCode_Horizon_È(("/c/" + Name).getBytes());
        Horizon.ÇŽÕ = true;
    }
    
    public void Â() {
        Horizon.ÇŽÕ = false;
        this.HorizonCode_Horizon_È(("/d/" + this.Ó).getBytes());
    }
    
    public void HorizonCode_Horizon_È(final String ip, final int port) {
        try {
            this.Ø = port;
            this.Âµá€ = new DatagramSocket();
            this.Ø­áŒŠá = InetAddress.getByName(ip);
        }
        catch (Exception ex) {}
    }
    
    public int Ý() {
        return this.Ø;
    }
    
    public int Ø­áŒŠá() {
        return this.Ó;
    }
    
    @Override
    public void run() {
        this.Âµá€();
        final Scanner s = new Scanner(System.in);
        while (this.à) {
            final String input = s.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                this.Â();
                new Thread("halt") {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000L);
                        }
                        catch (InterruptedException ex) {}
                        System.exit(0);
                    }
                }.start();
            }
            else {
                this.HorizonCode_Horizon_È(("/m/" + this.Ó + "/o/" + input).getBytes());
            }
        }
        s.close();
    }
    
    public void Âµá€() {
        (this.Â = new Thread("Receive") {
            @Override
            public void run() {
                while (IRCClient.this.à) {
                    final byte[] data = new byte[1024];
                    final DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        IRCClient.this.Âµá€.receive(packet);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    IRCClient.this.HorizonCode_Horizon_È(packet);
                }
            }
        }).start();
    }
    
    public void HorizonCode_Horizon_È(final DatagramPacket packet) {
        String str = new String(packet.getData());
        str = Filter.HorizonCode_Horizon_È(str);
        if (str.startsWith("/c/")) {
            final String raw = str.substring(3, str.length());
            final int id = Integer.valueOf(raw);
            this.Ó = id;
            System.out.println("Connected to IRC-Server with id " + id);
        }
        if (str.startsWith("/m/")) {
            final String raw = str.substring(3, str.length());
            final String by = raw.split("/o/")[0];
            final String msg = raw.split("/o/")[1];
            System.out.println(String.valueOf(by) + ": " + msg);
            if (by.equalsIgnoreCase(Horizon.É)) {
                GuiIRCChat.Â.add(new IRCMessage(by, msg, -12601874, true));
            }
            else {
                GuiIRCChat.Â.add(new IRCMessage(by, msg));
            }
        }
        if (str.startsWith("/i/")) {
            this.HorizonCode_Horizon_È(("/i/" + this.Ó).getBytes());
        }
        if (str.startsWith("/q/")) {
            final String raw = str.substring(3, str.length());
            GuiIRCChat.Â.add(new IRCMessage("System", String.valueOf(raw) + " disconnected.", -1421774));
        }
        if (str.startsWith("/a/")) {
            final String raw = str.substring(3, str.length());
            GuiIRCChat.Â.add(new IRCMessage("System", String.valueOf(raw) + " connected.", -14368954));
        }
        if (str.contains("@" + Horizon.É)) {
            if (!(Minecraft.áŒŠà().¥Æ instanceof GuiIRCChat)) {
                final String raw = str.substring(3, str.length());
                final String by = raw.split("/o/")[0];
                final String msg = raw.split("/o/")[1];
                Horizon.à¢.Ï­à.HorizonCode_Horizon_È("IRC", String.valueOf(by) + "> " + msg, 5);
            }
            else {
                final String raw = str.substring(3, str.length());
                final String by = raw.split("/o/")[0];
                final String msg = raw.split("/o/")[1];
                if (by.equalsIgnoreCase(Horizon.É)) {
                    return;
                }
                Horizon.à¢.ÇŽá€.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/sounds/notification.wav");
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final byte[] data) {
        (this.Ý = new Thread("Send") {
            @Override
            public void run() {
                final DatagramPacket packet = new DatagramPacket(data, data.length, IRCClient.this.Ø­áŒŠá, IRCClient.this.Ø);
                try {
                    IRCClient.this.Âµá€.send(packet);
                }
                catch (IOException ex) {}
            }
        }).start();
    }
}
