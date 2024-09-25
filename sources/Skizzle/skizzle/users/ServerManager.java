/*
 * Decompiled with CFR 0.150.
 */
package skizzle.users;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import skizzle.util.Timer;

public class ServerManager {
    public byte[] buf = new byte[256];
    public static Timer resetTimer = new Timer();
    public boolean running;
    public DatagramSocket socket;

    public static String[] getUsersFromServer() {
        String Nigga = Qprot0.0("\u3c50\u71ca\u073c\u38a7\uf2f1\ub9e9\u8c26");
        int Nigga2 = 65534;
        try {
            InetAddress Nigga3 = InetAddress.getByName(Nigga);
            DatagramSocket Nigga4 = new DatagramSocket();
            String Nigga5 = Qprot0.0("\u3c68\u71de\u0720\u38e5\uf2d0\ub9e7\u8c3e\u5000\uc80a\u59bb\u33db\uaf56\ubec0\ued55\u86d7\ua051");
            byte[] Nigga6 = Nigga5.getBytes(StandardCharsets.UTF_8);
            DatagramPacket Nigga7 = new DatagramPacket(Nigga6, Nigga6.length, Nigga3, Nigga2);
            Nigga4.send(Nigga7);
            Nigga6 = new byte[10000];
            DatagramPacket Nigga8 = new DatagramPacket(Nigga6, Nigga6.length);
            Nigga4.setSoTimeout(500);
            Nigga4.receive(Nigga8);
            String Nigga9 = new String(Nigga6, 0, Nigga8.getLength());
            return Nigga9.split("\n");
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static boolean isUser(String Nigga) {
        String Nigga2 = Qprot0.0("\u3c50\u71ca\u073c\u6dcd\ud81b\ub9e9\u8c26");
        int Nigga3 = 65534;
        try {
            InetAddress Nigga4 = InetAddress.getByName(Nigga2);
            DatagramSocket Nigga5 = new DatagramSocket();
            String Nigga6 = Qprot0.0("\u3c7b\u71c3\u0729\u6d80\ud803\ub9d7\u8c3c\u5010\u9d77\u7318") + Nigga;
            byte[] Nigga7 = Nigga6.getBytes(StandardCharsets.UTF_8);
            DatagramPacket Nigga8 = new DatagramPacket(Nigga7, Nigga7.length, Nigga4, Nigga3);
            Nigga5.send(Nigga8);
            DatagramPacket Nigga9 = new DatagramPacket(Nigga7, Nigga7.length);
            Nigga5.setSoTimeout(2000);
            Nigga5.receive(Nigga9);
            String Nigga10 = new String(Nigga7, 0, Nigga9.getLength());
            return Nigga10.equals("1");
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static boolean sendUserData(String Nigga) {
        String Nigga2 = Qprot0.0("\u3c50\u71ca\u073c\u3865\uf2b3\ub9e9\u8c26");
        int Nigga3 = 65534;
        try {
            InetAddress Nigga4 = InetAddress.getByName(Nigga2);
            DatagramSocket Nigga5 = new DatagramSocket();
            String Nigga6 = Qprot0.0("\u3c6b\u71ce\u0722\u382f\uf295\ub9f1\u8c2a\u5007\uc897") + Nigga;
            byte[] Nigga7 = Nigga6.getBytes(StandardCharsets.UTF_8);
            DatagramPacket Nigga8 = new DatagramPacket(Nigga7, Nigga7.length, Nigga4, Nigga3);
            Nigga5.send(Nigga8);
            Nigga7 = new byte[512];
            DatagramPacket Nigga9 = new DatagramPacket(Nigga7, Nigga7.length);
            Nigga5.setSoTimeout(2000);
            Nigga5.receive(Nigga9);
            String Nigga10 = new String(Nigga7, 0, Nigga9.getLength());
            return !Nigga10.equals("0");
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static String[] recieveWithThread(DatagramSocket Nigga, DatagramPacket Nigga2) {
        String[] Nigga3 = new String[]{""};
        Thread Nigga4 = new Thread(Nigga, Nigga2, Nigga3){
            public DatagramSocket val$socket;
            public DatagramPacket val$packet;
            public String[] val$recievedData;

            @Override
            public void run() {
                try {
                    1 Nigga;
                    Nigga.val$socket.receive(Nigga.val$packet);
                    byte[] Nigga2 = new byte[10000];
                    Nigga.val$recievedData[0] = new String(Nigga2, 0, Nigga.val$packet.getLength());
                }
                catch (Exception exception) {}
            }
            {
                1 Nigga;
                Nigga.val$socket = datagramSocket;
                Nigga.val$packet = datagramPacket;
                Nigga.val$recievedData = arrstring;
            }

            public static {
                throw throwable;
            }
        };
        Nigga4.start();
        return Nigga3;
    }

    public ServerManager() {
        ServerManager Nigga;
    }

    public static String sendPacketWithResponse(String Nigga, String Nigga2) {
        String Nigga3 = Qprot0.0("\u3c50\u71ca\u073c\u74af\u2ef9\ub9e9\u8c26");
        int Nigga4 = 65534;
        try {
            InetAddress Nigga5 = InetAddress.getByName(Nigga3);
            DatagramSocket Nigga6 = new DatagramSocket();
            String Nigga7 = String.valueOf(Nigga) + ":" + Nigga2;
            byte[] Nigga8 = Nigga7.getBytes(StandardCharsets.UTF_8);
            DatagramPacket Nigga9 = new DatagramPacket(Nigga8, Nigga8.length, Nigga5, Nigga4);
            Nigga6.send(Nigga9);
            Nigga8 = new byte[10000];
            DatagramPacket Nigga10 = new DatagramPacket(Nigga8, Nigga8.length);
            Nigga6.setSoTimeout(2000);
            Nigga6.receive(Nigga10);
            String Nigga11 = "";
            Nigga11 = new String(Nigga8, 0, Nigga10.getLength());
            if (Nigga11 == null) {
                Nigga11 = Qprot0.0("\u3c6c\u71c2\u0721\u74e4\u2ee5\ub9f7\u8c3b\u5030\u841f\u85a3\u33ca\uaf1c\ubeda\ua141\u5adc\ua053");
            }
            return Nigga11;
        }
        catch (Exception exception) {
            return Qprot0.0("\u3c6c\u71c2\u0721\u74e4\u2ee5\ub9f7\u8c3b\u5030\u841f\u85a3\u33ca\uaf1c\ubeda\ua141\u5adc\ua053");
        }
    }

    public static void sendPacket(String Nigga, String Nigga2) {
        String Nigga3 = Qprot0.0("\u3c50\u71ca\u073c\ue7e8\u5e3e\ub9e9\u8c26");
        int Nigga4 = 65534;
        new Thread(() -> ServerManager.lambda$0(Nigga3, Nigga, Nigga2, Nigga4)).start();
    }

    public static void lambda$0(String string, String string2, String string3, int n) {
        try {
            InetAddress Nigga = InetAddress.getByName(string);
            DatagramSocket Nigga2 = new DatagramSocket();
            String Nigga3 = String.valueOf(string2) + ":" + string3;
            byte[] Nigga4 = Nigga3.getBytes(StandardCharsets.UTF_8);
            DatagramPacket Nigga5 = new DatagramPacket(Nigga4, Nigga4.length, Nigga, n);
            Nigga2.send(Nigga5);
        }
        catch (Exception exception) {}
    }

    public static boolean sendChatMessage(String Nigga) {
        String Nigga2 = Qprot0.0("\u3c50\u71ca\u073c\uc13f\ubd69\ub9e9\u8c26");
        int Nigga3 = 65534;
        try {
            InetAddress Nigga4 = InetAddress.getByName(Nigga2);
            DatagramSocket Nigga5 = new DatagramSocket();
            String Nigga6 = Qprot0.0("\u3c7b\u71c3\u072d\uc165\ubd57\ub9e7\u8c3c\u5006\u3196\u1637\u33ca\uaf56") + Nigga;
            byte[] Nigga7 = Nigga6.getBytes(StandardCharsets.UTF_8);
            DatagramPacket Nigga8 = new DatagramPacket(Nigga7, Nigga7.length, Nigga4, Nigga3);
            Nigga5.send(Nigga8);
            Nigga7 = new byte[512];
            DatagramPacket Nigga9 = new DatagramPacket(Nigga7, Nigga7.length);
            Nigga5.setSoTimeout(2000);
            Nigga5.receive(Nigga9);
            String Nigga10 = new String(Nigga7, 0, Nigga9.getLength());
            return !Nigga10.equals("0");
        }
        catch (Exception exception) {
            return false;
        }
    }
}

