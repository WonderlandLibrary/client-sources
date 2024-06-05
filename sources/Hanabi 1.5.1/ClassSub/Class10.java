package cn.Hanabi.modules.World;

import java.net.*;
import ClassSub.*;
import java.io.*;

class Class10 extends Thread
{
    final IRC_ this$0;
    
    
    Class10(final IRC_ this$0) {
        ((Class10)this).this$0 = this$0;
    }
    
    @Override
    public void run() {
        this.setName("Connect");
        try {
            IRC_.access$002(((Class10)this).this$0, false);
            ((Class10)this).this$0.socket = new Socket("47.244.128.222", 8687);
            (IRC_.pw = new PrintWriter(((Class10)this).this$0.socket.getOutputStream(), true)).println(Class334.username + "|" + Class334.password + "|Hanabi");
            ((Class10)this).this$0.br = new BufferedReader(new InputStreamReader(((Class10)this).this$0.socket.getInputStream(), "UTF-8"));
            Thread.sleep(3000L);
            IRC_.access$002(((Class10)this).this$0, true);
            new IRC_.Class29(((Class10)this).this$0).start();
            if (Class334.password.length() < 32) {
                System.exit(0);
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
