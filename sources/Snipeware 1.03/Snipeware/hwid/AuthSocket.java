package Snipeware.hwid;

import java.io.DataOutputStream;
import java.net.Socket;

public class AuthSocket {
    /**
     * Ignore i was trynna do something
     */
    public static void init() {
        try {
            Socket socket = new Socket("localhost", 2000);
            DataOutputStream dout =new DataOutputStream(socket.getOutputStream());
            dout.writeUTF("has anyone seen the size of :flushed:");
            dout.flush();
            dout.close();
            socket.close();
        }catch (Exception e){
        }
    }
}
