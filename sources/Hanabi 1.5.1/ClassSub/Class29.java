package cn.Hanabi.modules.World;

import java.io.*;

class Class29 extends Thread
{
    final IRC_ this$0;
    
    
    Class29(final IRC_ this$0) {
        ((Class29)this).this$0 = this$0;
    }
    
    @Override
    public void run() {
        this.setName("ReadMessage");
        try {
            while (IRC_.access$000(((Class29)this).this$0)) {
                final String line;
                if ((line = ((Class29)this).this$0.br.readLine()) != null) {
                    IRC_.processMessage(line);
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
