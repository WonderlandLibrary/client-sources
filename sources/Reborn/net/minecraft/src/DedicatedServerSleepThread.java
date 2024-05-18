package net.minecraft.src;

class DedicatedServerSleepThread extends Thread
{
    final DedicatedServer theDecitatedServer;
    
    DedicatedServerSleepThread(final DedicatedServer par1DedicatedServer) {
        this.theDecitatedServer = par1DedicatedServer;
        this.setDaemon(true);
        this.start();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    Thread.sleep(2147483647L);
                }
            }
            catch (InterruptedException ex) {
                continue;
            }
            break;
        }
    }
}
