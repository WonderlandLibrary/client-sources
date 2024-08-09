package ez.h.animengine;

import java.util.*;

public class Animengine
{
    static long tickDelay;
    static Thread running;
    public static List<Anim> anims;
    
    void runTickThread() {
        final Iterator<Anim> iterator;
        (Animengine.running = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Animengine.anims.iterator();
                    while (iterator.hasNext()) {
                        iterator.next().calculateNext();
                        Thread.sleep(Animengine.tickDelay);
                    }
                }
            }
            catch (Exception ex) {}
        })).start();
    }
    
    public Animengine() {
        this.runTickThread();
    }
    
    static {
        Animengine.anims = new ArrayList<Anim>();
    }
    
    public static void setTicksPerSecond(final long n) {
        if (n == 0L) {
            Animengine.tickDelay = 1L;
            return;
        }
        Animengine.tickDelay = 1000L / n;
    }
    
    public static void registerAnims(final Anim... array) {
        Animengine.anims.addAll(Arrays.asList(array));
    }
    
    public static void stopEngine() {
        Animengine.anims.clear();
        Animengine.running.interrupt();
        System.out.println("[Animengine] Engine has been interrupted successfully");
    }
    
    public static void setTickDelay(final long tickDelay) {
        Animengine.tickDelay = tickDelay;
    }
}
