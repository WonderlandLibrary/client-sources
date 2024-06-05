package ClassSub;

import java.net.*;
import java.io.*;

public class Class124 extends Thread
{
    public Socket socket;
    public PrintWriter writer;
    
    
    @Override
    public void run() {
        new Class162(this).start();
    Label_0015_Outer:
        while (true) {
            while (true) {
                try {
                    while (true) {
                        Class203.processMessage(getSocketMessage(this.socket));
                    }
                }
                catch (Exception ex) {
                    if (Class203.heartBeatTimer.lastMs > 5000L) {
                        Class203.heartBeatTimer.setLastMs(0);
                        Class203.output("������Ϣʱ���ִ���" + ex.getMessage());
                        ex.printStackTrace();
                    }
                    continue Label_0015_Outer;
                }
                continue;
            }
        }
    }
    
    public static String getSocketMessage(final Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK")).readLine();
    }
}
