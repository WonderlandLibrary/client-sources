package net.minecraft.src;

import org.lwjgl.opengl.*;
import java.util.*;

public class WrUpdateThread extends Thread
{
    private Pbuffer pbuffer;
    private Object lock;
    private List updateList;
    private List updatedList;
    private int updateCount;
    private Tessellator mainTessellator;
    private Tessellator threadTessellator;
    private boolean working;
    private WorldRendererThreaded currentRenderer;
    private boolean canWork;
    private boolean canWorkToEndOfUpdate;
    private boolean terminated;
    private static final int MAX_UPDATE_CAPACITY = 10;
    
    public WrUpdateThread(final Pbuffer var1) {
        super("WrUpdateThread");
        this.pbuffer = null;
        this.lock = new Object();
        this.updateList = new LinkedList();
        this.updatedList = new LinkedList();
        this.updateCount = 0;
        this.mainTessellator = Tessellator.instance;
        this.threadTessellator = new Tessellator(2097152);
        this.working = false;
        this.currentRenderer = null;
        this.canWork = false;
        this.canWorkToEndOfUpdate = false;
        this.terminated = false;
        this.pbuffer = var1;
    }
    
    @Override
    public void run() {
        try {
            this.pbuffer.makeCurrent();
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
        final WrUpdateThread$ThreadUpdateListener var9 = new WrUpdateThread$ThreadUpdateListener(this, null);
        while (!Thread.interrupted() && !this.terminated) {
            try {
                final WorldRendererThreaded var10 = this.getRendererToUpdate();
                if (var10 == null) {
                    return;
                }
                this.checkCanWork(null);
                try {
                    this.currentRenderer = var10;
                    Tessellator.instance = this.threadTessellator;
                    var10.updateRenderer(var9);
                }
                finally {
                    Tessellator.instance = this.mainTessellator;
                }
                Tessellator.instance = this.mainTessellator;
                this.rendererUpdated(var10);
            }
            catch (Exception var11) {
                var11.printStackTrace();
                if (this.currentRenderer != null) {
                    this.currentRenderer.isUpdating = false;
                    this.currentRenderer.needsUpdate = true;
                }
                this.currentRenderer = null;
                this.working = false;
            }
        }
    }
    
    public void addRendererToUpdate(final WorldRenderer var1, final boolean var2) {
        final Object var3 = this.lock;
        final Object var4 = this.lock;
        synchronized (this.lock) {
            if (var1.isUpdating) {
                throw new IllegalArgumentException("Renderer already updating");
            }
            if (var2) {
                this.updateList.add(0, var1);
            }
            else {
                this.updateList.add(var1);
            }
            var1.isUpdating = true;
            this.lock.notifyAll();
        }
        // monitorexit(this.lock)
    }
    
    private WorldRendererThreaded getRendererToUpdate() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            while (this.updateList.size() <= 0) {
                try {
                    this.lock.wait(2000L);
                    if (this.terminated) {
                        final WorldRendererThreaded var3 = null;
                        final WorldRendererThreaded worldRendererThreaded;
                        final WorldRendererThreaded var4 = worldRendererThreaded = var3;
                        // monitorexit(this.lock)
                        return worldRendererThreaded;
                    }
                    continue;
                }
                catch (InterruptedException ex) {}
            }
            final WorldRendererThreaded var3 = this.updateList.remove(0);
            this.lock.notifyAll();
            // monitorexit(this.lock)
            return var3;
        }
    }
    
    public boolean hasWorkToDo() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            // monitorexit(this.lock)
            return this.updateList.size() > 0 || this.currentRenderer != null || this.working;
        }
    }
    
    public int getUpdateCapacity() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            // monitorexit(this.lock)
            return (this.updateList.size() > 10) ? 0 : (10 - this.updateList.size());
        }
    }
    
    private void rendererUpdated(final WorldRenderer var1) {
        final Object var2 = this.lock;
        final Object var3 = this.lock;
        synchronized (this.lock) {
            this.updatedList.add(var1);
            ++this.updateCount;
            this.currentRenderer = null;
            this.working = false;
            this.lock.notifyAll();
        }
        // monitorexit(this.lock)
    }
    
    private void finishUpdatedRenderers() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            for (int var3 = 0; var3 < this.updatedList.size(); ++var3) {
                final WorldRendererThreaded var4 = this.updatedList.get(var3);
                var4.finishUpdate();
                var4.isUpdating = false;
            }
            this.updatedList.clear();
        }
        // monitorexit(this.lock)
    }
    
    public void pause() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            this.canWork = false;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();
            while (this.working) {
                try {
                    this.lock.wait();
                }
                catch (InterruptedException ex) {}
            }
            this.finishUpdatedRenderers();
        }
        // monitorexit(this.lock)
    }
    
    public void unpause() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            if (this.working) {
                Config.dbg("UpdateThread still working in unpause()!!!");
            }
            this.canWork = true;
            this.canWorkToEndOfUpdate = false;
            this.lock.notifyAll();
        }
        // monitorexit(this.lock)
    }
    
    public void unpauseToEndOfUpdate() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            if (this.working) {
                Config.dbg("UpdateThread still working in unpause()!!!");
            }
            if (this.currentRenderer != null) {
                while (this.currentRenderer != null) {
                    this.canWork = false;
                    this.canWorkToEndOfUpdate = true;
                    this.lock.notifyAll();
                    try {
                        this.lock.wait();
                    }
                    catch (InterruptedException ex) {}
                }
                this.pause();
            }
        }
        // monitorexit(this.lock)
    }
    
    private void checkCanWork(final IWrUpdateControl var1) {
        Thread.yield();
        final Object var2 = this.lock;
        final Object var3 = this.lock;
        synchronized (this.lock) {
            while (!this.canWork && (!this.canWorkToEndOfUpdate || this.currentRenderer == null)) {
                if (var1 != null) {
                    var1.pause();
                }
                this.working = false;
                this.lock.notifyAll();
                try {
                    this.lock.wait();
                }
                catch (InterruptedException ex) {}
            }
            this.working = true;
            if (var1 != null) {
                var1.resume();
            }
            this.lock.notifyAll();
        }
        // monitorexit(this.lock)
    }
    
    public void clearAllUpdates() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            this.unpauseToEndOfUpdate();
            this.updateList.clear();
            this.lock.notifyAll();
        }
        // monitorexit(this.lock)
    }
    
    public int getPendingUpdatesCount() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            int var3 = this.updateList.size();
            if (this.currentRenderer != null) {
                ++var3;
            }
            // monitorexit(this.lock)
            return var3;
        }
    }
    
    public int resetUpdateCount() {
        final Object var1 = this.lock;
        final Object var2 = this.lock;
        synchronized (this.lock) {
            final int var3 = this.updateCount;
            this.updateCount = 0;
            // monitorexit(this.lock)
            return var3;
        }
    }
    
    public void terminate() {
        this.terminated = true;
    }
    
    static Tessellator access$000(final WrUpdateThread var0) {
        return var0.mainTessellator;
    }
    
    static Tessellator access$100(final WrUpdateThread var0) {
        return var0.threadTessellator;
    }
    
    static void access$300(final WrUpdateThread var0, final IWrUpdateControl var1) {
        var0.checkCanWork(var1);
    }
}
