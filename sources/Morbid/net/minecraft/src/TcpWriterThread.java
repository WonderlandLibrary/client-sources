package net.minecraft.src;

import java.io.*;

class TcpWriterThread extends Thread
{
    final TcpConnection theTcpConnection;
    
    TcpWriterThread(final TcpConnection par1TcpConnection, final String par2Str) {
        super(par2Str);
        this.theTcpConnection = par1TcpConnection;
    }
    
    @Override
    public void run() {
        TcpConnection.field_74469_b.getAndIncrement();
        try {
            while (TcpConnection.isRunning(this.theTcpConnection)) {
                boolean var1 = false;
                while (TcpConnection.sendNetworkPacket(this.theTcpConnection)) {
                    var1 = true;
                }
                try {
                    if (var1 && TcpConnection.getOutputStream(this.theTcpConnection) != null) {
                        TcpConnection.getOutputStream(this.theTcpConnection).flush();
                    }
                }
                catch (IOException var2) {
                    if (!TcpConnection.isTerminating(this.theTcpConnection)) {
                        TcpConnection.sendError(this.theTcpConnection, var2);
                    }
                    var2.printStackTrace();
                }
                try {
                    Thread.sleep(2L);
                }
                catch (InterruptedException ex) {}
            }
        }
        finally {
            TcpConnection.field_74469_b.getAndDecrement();
        }
        TcpConnection.field_74469_b.getAndDecrement();
    }
}
