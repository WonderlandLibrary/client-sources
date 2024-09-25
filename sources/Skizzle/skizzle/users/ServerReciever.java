/*
 * Decompiled with CFR 0.150.
 */
package skizzle.users;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerReciever {
    public static boolean running;

    public ServerReciever() {
        ServerReciever Nigga;
    }

    public static {
        throw throwable;
    }

    public static void start() {
        try {
            DatagramSocket Nigga = new DatagramSocket(65534);
            while (running) {
                System.out.println(Qprot0.0("\u3b47\u71ca,"));
                byte[] Nigga2 = new byte[1024];
                DatagramPacket Nigga3 = new DatagramPacket(Nigga2, Nigga2.length);
                System.out.println(Qprot0.0("\u3b47\u71ca,\u61a6"));
                Nigga.receive(Nigga3);
                System.out.println(Qprot0.0("\u3b47\u71ca,\u61a7"));
                System.out.println(Nigga3.getData());
            }
            Nigga.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

