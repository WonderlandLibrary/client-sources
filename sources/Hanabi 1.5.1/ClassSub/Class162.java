package ClassSub;

import java.net.*;
import java.io.*;

public class Class162 extends Thread
{
    Class124 mSocket;
    
    
    public Class162(final Class124 mSocket) {
        this.mSocket = mSocket;
        this.connect();
    }
    
    @Override
    public void run() {
    Label_0004:
        while (true) {
            break Label_0004;
            while (true) {
                try {
                    while (true) {
                        Thread.sleep(1000L);
                        if (Class203.heartBeatTimer.isDelayComplete(8000L)) {
                            this.mSocket.socket.sendUrgentData(255);
                        }
                    }
                }
                catch (Exception ex) {
                    Class203.output("�ͻ��˶Ͽ����ӣ�����������...");
                    this.connect();
                    continue;
                }
                continue Label_0004;
            }
            break;
        }
    }
    
    public void connect() {
        try {
            this.mSocket.socket = new Socket("dx.pfcraft.cn", 7577);
            this.mSocket.writer = new PrintWriter(this.mSocket.socket.getOutputStream(), true);
            Class203.loginPacket.sendPacketToServer(this.mSocket.writer);
            Class203.output("����IRC�������ɹ���");
        }
        catch (Exception ex) {
            Class203.output("����IRC������ʧ��...");
            ex.printStackTrace();
        }
    }
}
