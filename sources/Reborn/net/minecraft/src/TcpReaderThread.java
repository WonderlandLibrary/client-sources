package net.minecraft.src;

class TcpReaderThread extends Thread
{
    final TcpConnection theTcpConnection;
    
    TcpReaderThread(final TcpConnection par1TcpConnection, final String par2Str) {
        super(par2Str);
        this.theTcpConnection = par1TcpConnection;
    }
    
    @Override
    public void run() {
        TcpConnection.field_74471_a.getAndIncrement();
        try {
            while (TcpConnection.isRunning(this.theTcpConnection)) {
                if (TcpConnection.isServerTerminating(this.theTcpConnection)) {
                    break;
                }
                if (TcpConnection.readNetworkPacket(this.theTcpConnection)) {
                    continue;
                }
                try {
                    Thread.sleep(2L);
                }
                catch (InterruptedException ex) {}
            }
        }
        finally {
            TcpConnection.field_74471_a.getAndDecrement();
        }
        TcpConnection.field_74471_a.getAndDecrement();
    }
}
