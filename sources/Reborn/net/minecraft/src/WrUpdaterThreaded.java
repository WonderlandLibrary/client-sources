package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class WrUpdaterThreaded implements IWrUpdater
{
    private WrUpdateThread updateThread;
    private float timePerUpdateMs;
    private long updateStartTimeNs;
    private boolean firstUpdate;
    private int updateTargetNum;
    
    public WrUpdaterThreaded() {
        this.updateThread = null;
        this.timePerUpdateMs = 10.0f;
        this.updateStartTimeNs = 0L;
        this.firstUpdate = true;
        this.updateTargetNum = 0;
    }
    
    @Override
    public void terminate() {
        if (this.updateThread != null) {
            this.updateThread.terminate();
            this.updateThread.unpauseToEndOfUpdate();
        }
    }
    
    @Override
    public void initialize() {
    }
    
    private void delayedInit() {
        if (this.updateThread == null) {
            this.createUpdateThread(Display.getDrawable());
        }
    }
    
    @Override
    public WorldRenderer makeWorldRenderer(final World var1, final List var2, final int var3, final int var4, final int var5, final int var6) {
        return new WorldRendererThreaded(var1, var2, var3, var4, var5, var6);
    }
    
    public WrUpdateThread createUpdateThread(final Drawable var1) {
        if (this.updateThread != null) {
            throw new IllegalStateException("UpdateThread is already existing");
        }
        try {
            final Pbuffer var2 = new Pbuffer(1, 1, new PixelFormat(), var1);
            (this.updateThread = new WrUpdateThread(var2)).setPriority(1);
            this.updateThread.start();
            this.updateThread.pause();
            return this.updateThread;
        }
        catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
    
    public boolean isUpdateThread() {
        return Thread.currentThread() == this.updateThread;
    }
    
    public static boolean isBackgroundChunkLoading() {
        return true;
    }
    
    @Override
    public void preRender(final RenderGlobal var1, final EntityLiving var2) {
        this.updateTargetNum = 0;
        if (this.updateThread != null) {
            if (this.updateStartTimeNs == 0L) {
                this.updateStartTimeNs = System.nanoTime();
            }
            if (this.updateThread.hasWorkToDo()) {
                this.updateTargetNum = Config.getUpdatesPerFrame();
                if (Config.isDynamicUpdates() && !var1.isMoving(var2)) {
                    this.updateTargetNum *= 3;
                }
                this.updateTargetNum = Math.min(this.updateTargetNum, this.updateThread.getPendingUpdatesCount());
                if (this.updateTargetNum > 0) {
                    this.updateThread.unpause();
                }
            }
        }
    }
    
    @Override
    public void postRender() {
        if (this.updateThread != null) {
            final float var1 = 0.0f;
            if (this.updateTargetNum > 0) {
                final long var2 = System.nanoTime() - this.updateStartTimeNs;
                final float var3 = this.timePerUpdateMs * (1.0f + (this.updateTargetNum - 1) / 2.0f);
                if (var3 > 0.0f) {
                    final int var4 = (int)var3;
                    Config.sleep(var4);
                }
                this.updateThread.pause();
            }
            final float var5 = 0.2f;
            if (this.updateTargetNum > 0) {
                final int var6 = this.updateThread.resetUpdateCount();
                if (var6 < this.updateTargetNum) {
                    this.timePerUpdateMs += var5;
                }
                if (var6 > this.updateTargetNum) {
                    this.timePerUpdateMs -= var5;
                }
                if (var6 == this.updateTargetNum) {
                    this.timePerUpdateMs -= var5;
                }
            }
            else {
                this.timePerUpdateMs -= var5 / 5.0f;
            }
            if (this.timePerUpdateMs < 0.0f) {
                this.timePerUpdateMs = 0.0f;
            }
            this.updateStartTimeNs = System.nanoTime();
        }
    }
    
    @Override
    public boolean updateRenderers(final RenderGlobal var1, final EntityLiving var2, final boolean var3) {
        this.delayedInit();
        if (var1.worldRenderersToUpdate.size() <= 0) {
            return true;
        }
        int var4 = 0;
        final byte var5 = 4;
        int var6 = 0;
        WorldRenderer var7 = null;
        float var8 = Float.MAX_VALUE;
        int var9 = -1;
        for (int var10 = 0; var10 < var1.worldRenderersToUpdate.size(); ++var10) {
            final WorldRenderer var11 = (WorldRenderer)var1.worldRenderersToUpdate.get(var10);
            if (var11 != null) {
                ++var6;
                if (!var11.isUpdating) {
                    if (!var11.needsUpdate) {
                        var1.worldRenderersToUpdate.set(var10, null);
                    }
                    else {
                        float var12 = var11.distanceToEntitySquared(var2);
                        if (var12 < 512.0f) {
                            if ((var12 < 256.0f && var1.isActingNow() && var11.isInFrustum) || this.firstUpdate) {
                                if (this.updateThread != null) {
                                    this.updateThread.unpauseToEndOfUpdate();
                                }
                                var11.updateRenderer();
                                var11.needsUpdate = false;
                                var1.worldRenderersToUpdate.set(var10, null);
                                ++var4;
                                continue;
                            }
                            if (this.updateThread != null) {
                                this.updateThread.addRendererToUpdate(var11, true);
                                var11.needsUpdate = false;
                                var1.worldRenderersToUpdate.set(var10, null);
                                ++var4;
                                continue;
                            }
                        }
                        if (!var11.isInFrustum) {
                            var12 *= var5;
                        }
                        if (var7 == null) {
                            var7 = var11;
                            var8 = var12;
                            var9 = var10;
                        }
                        else if (var12 < var8) {
                            var7 = var11;
                            var8 = var12;
                            var9 = var10;
                        }
                    }
                }
            }
        }
        int var10 = Config.getUpdatesPerFrame();
        boolean var13 = false;
        if (Config.isDynamicUpdates() && !var1.isMoving(var2)) {
            var10 *= 3;
            var13 = true;
        }
        if (this.updateThread != null) {
            var10 = this.updateThread.getUpdateCapacity();
            if (var10 <= 0) {
                return true;
            }
        }
        if (var7 != null) {
            this.updateRenderer(var7);
            var1.worldRenderersToUpdate.set(var9, null);
            ++var4;
            final float var12 = var8 / 5.0f;
            for (int var14 = 0; var14 < var1.worldRenderersToUpdate.size() && var4 < var10; ++var14) {
                final WorldRenderer var15 = (WorldRenderer)var1.worldRenderersToUpdate.get(var14);
                if (var15 != null && !var15.isUpdating) {
                    float var16 = var15.distanceToEntitySquared(var2);
                    if (!var15.isInFrustum) {
                        var16 *= var5;
                    }
                    final float var17 = Math.abs(var16 - var8);
                    if (var17 < var12) {
                        this.updateRenderer(var15);
                        var1.worldRenderersToUpdate.set(var14, null);
                        ++var4;
                    }
                }
            }
        }
        if (var6 == 0) {
            var1.worldRenderersToUpdate.clear();
        }
        if (var1.worldRenderersToUpdate.size() > 100 && var6 < var1.worldRenderersToUpdate.size() * 4 / 5) {
            int var18 = 0;
            for (int var14 = 0; var14 < var1.worldRenderersToUpdate.size(); ++var14) {
                final Object var19 = var1.worldRenderersToUpdate.get(var14);
                if (var19 != null) {
                    if (var14 != var18) {
                        var1.worldRenderersToUpdate.set(var18, var19);
                    }
                    ++var18;
                }
            }
            for (int var14 = var1.worldRenderersToUpdate.size() - 1; var14 >= var18; --var14) {
                var1.worldRenderersToUpdate.remove(var14);
            }
        }
        this.firstUpdate = false;
        return true;
    }
    
    private void updateRenderer(final WorldRenderer var1) {
        final WrUpdateThread var2 = this.updateThread;
        if (var2 != null) {
            var2.addRendererToUpdate(var1, false);
            var1.needsUpdate = false;
        }
        else {
            var1.updateRenderer();
            var1.needsUpdate = false;
            var1.isUpdating = false;
        }
    }
    
    @Override
    public void finishCurrentUpdate() {
        if (this.updateThread != null) {
            this.updateThread.unpauseToEndOfUpdate();
        }
    }
    
    @Override
    public void resumeBackgroundUpdates() {
        if (this.updateThread != null) {
            this.updateThread.unpause();
        }
    }
    
    @Override
    public void pauseBackgroundUpdates() {
        if (this.updateThread != null) {
            this.updateThread.pause();
        }
    }
}
