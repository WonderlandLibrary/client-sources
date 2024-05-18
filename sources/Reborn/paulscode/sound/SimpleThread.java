package paulscode.sound;

public class SimpleThread extends Thread
{
    private static final boolean GET = false;
    private static final boolean SET = true;
    private static final boolean XXX = false;
    private boolean alive;
    private boolean kill;
    
    public SimpleThread() {
        this.alive = true;
        this.kill = false;
    }
    
    protected void cleanup() {
        this.kill(true, true);
        this.alive(true, false);
    }
    
    public void run() {
        this.cleanup();
    }
    
    public void restart() {
        new SimpleThread$1(this).start();
    }
    
    private void rerun() {
        this.kill(true, true);
        while (this.alive(false, false)) {
            this.snooze(100L);
        }
        this.alive(true, true);
        this.kill(true, false);
        this.run();
    }
    
    public boolean alive() {
        return this.alive(false, false);
    }
    
    public void kill() {
        this.kill(true, true);
    }
    
    protected boolean dying() {
        return this.kill(false, false);
    }
    
    private synchronized boolean alive(final boolean b, final boolean alive) {
        if (b) {
            this.alive = alive;
        }
        return this.alive;
    }
    
    private synchronized boolean kill(final boolean b, final boolean kill) {
        if (b) {
            this.kill = kill;
        }
        return this.kill;
    }
    
    protected void snooze(final long n) {
        try {
            Thread.sleep(n);
        }
        catch (InterruptedException ex) {}
    }
}
