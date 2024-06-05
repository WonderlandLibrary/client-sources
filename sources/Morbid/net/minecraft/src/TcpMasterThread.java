package net.minecraft.src;

class TcpMasterThread extends Thread
{
    final TcpConnection theTcpConnection;
    
    TcpMasterThread(final TcpConnection par1TcpConnection) {
        this.theTcpConnection = par1TcpConnection;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(5000L);
            if (TcpConnection.getReadThread(this.theTcpConnection).isAlive()) {
                try {
                    TcpConnection.getReadThread(this.theTcpConnection).stop();
                }
                catch (Throwable t) {}
            }
            if (TcpConnection.getWriteThread(this.theTcpConnection).isAlive()) {
                try {
                    TcpConnection.getWriteThread(this.theTcpConnection).stop();
                }
                catch (Throwable t2) {}
            }
        }
        catch (InterruptedException var4) {
            var4.printStackTrace();
        }
    }
}
