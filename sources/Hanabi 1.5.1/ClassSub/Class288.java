package cn.Hanabi.modules.World;

import java.io.*;

class Class288 extends Thread
{
    final IRC_ this$0;
    
    
    Class288(final IRC_ this$0) {
        ((Class288)this).this$0 = this$0;
    }
    
    @Override
    public void run() {
        this.setName("Reconnect");
        new IRC_.Class10(((Class288)this).this$0).start();
    Label_0024_Outer:
        while (true) {
            while (true) {
                try {
                    while (true) {
                        Thread.sleep(1000L);
                        ((Class288)this).this$0.socket.sendUrgentData(255);
                    }
                }
                catch (IOException ex3) {
                    if (IRC_.access$100(((Class288)this).this$0).isDelayComplete(2000L)) {
                        IRC_.access$100(((Class288)this).this$0).reset();
                        new IRC_.Class10(((Class288)this).this$0).start();
                    }
                    continue Label_0024_Outer;
                }
                catch (NullPointerException ex) {
                    ex.printStackTrace();
                    continue Label_0024_Outer;
                }
                catch (InterruptedException ex2) {
                    ex2.printStackTrace();
                    continue Label_0024_Outer;
                }
                continue;
            }
        }
    }
}
