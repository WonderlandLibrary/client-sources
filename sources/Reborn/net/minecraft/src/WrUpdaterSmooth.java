package net.minecraft.src;

import java.util.*;

public class WrUpdaterSmooth implements IWrUpdater
{
    private long lastUpdateStartTimeNs;
    private long updateStartTimeNs;
    private long updateTimeNs;
    private WorldRendererSmooth currentUpdateRenderer;
    private int renderersUpdated;
    private int renderersFound;
    
    public WrUpdaterSmooth() {
        this.lastUpdateStartTimeNs = 0L;
        this.updateStartTimeNs = 0L;
        this.updateTimeNs = 10000000L;
        this.currentUpdateRenderer = null;
        this.renderersUpdated = 0;
        this.renderersFound = 0;
    }
    
    @Override
    public void initialize() {
    }
    
    @Override
    public void terminate() {
    }
    
    @Override
    public WorldRenderer makeWorldRenderer(final World var1, final List var2, final int var3, final int var4, final int var5, final int var6) {
        return new WorldRendererSmooth(var1, var2, var3, var4, var5, var6);
    }
    
    @Override
    public boolean updateRenderers(final RenderGlobal var1, final EntityLiving var2, final boolean var3) {
        this.lastUpdateStartTimeNs = this.updateStartTimeNs;
        this.updateStartTimeNs = System.nanoTime();
        final long var4 = this.updateStartTimeNs + this.updateTimeNs;
        int var5 = Config.getUpdatesPerFrame();
        if (Config.isDynamicUpdates() && !var1.isMoving(var2)) {
            var5 *= 3;
        }
        this.renderersUpdated = 0;
        do {
            this.renderersFound = 0;
            this.updateRenderersImpl(var1, var2, var3);
        } while (this.renderersFound > 0 && System.nanoTime() - var4 < 0L);
        if (this.renderersFound > 0) {
            var5 = Math.min(var5, this.renderersFound);
            final long var6 = 400000L;
            if (this.renderersUpdated > var5) {
                this.updateTimeNs -= 2L * var6;
            }
            if (this.renderersUpdated < var5) {
                this.updateTimeNs += var6;
            }
        }
        else {
            this.updateTimeNs = 0L;
            this.updateTimeNs -= 200000L;
        }
        if (this.updateTimeNs < 0L) {
            this.updateTimeNs = 0L;
        }
        return this.renderersUpdated > 0;
    }
    
    private void updateRenderersImpl(final RenderGlobal var1, final EntityLiving var2, final boolean var3) {
        this.renderersFound = 0;
        boolean var4 = true;
        if (this.currentUpdateRenderer != null) {
            ++this.renderersFound;
            var4 = this.updateRenderer(this.currentUpdateRenderer);
            if (var4) {
                ++this.renderersUpdated;
            }
        }
        if (var1.worldRenderersToUpdate.size() > 0) {
            final byte var5 = 4;
            WorldRendererSmooth var6 = null;
            float var7 = Float.MAX_VALUE;
            int var8 = -1;
            for (int var9 = 0; var9 < var1.worldRenderersToUpdate.size(); ++var9) {
                final WorldRendererSmooth var10 = (WorldRendererSmooth)var1.worldRenderersToUpdate.get(var9);
                if (var10 != null) {
                    ++this.renderersFound;
                    if (!var10.needsUpdate) {
                        var1.worldRenderersToUpdate.set(var9, null);
                    }
                    else {
                        float var11 = var10.distanceToEntitySquared(var2);
                        if (var11 <= 256.0f && var1.isActingNow()) {
                            var10.updateRenderer();
                            var10.needsUpdate = false;
                            var1.worldRenderersToUpdate.set(var9, null);
                            ++this.renderersUpdated;
                        }
                        else {
                            if (!var10.isInFrustum) {
                                var11 *= var5;
                            }
                            if (var6 == null) {
                                var6 = var10;
                                var7 = var11;
                                var8 = var9;
                            }
                            else if (var11 < var7) {
                                var6 = var10;
                                var7 = var11;
                                var8 = var9;
                            }
                        }
                    }
                }
            }
            if (this.currentUpdateRenderer == null || var4) {
                if (var6 != null) {
                    var1.worldRenderersToUpdate.set(var8, null);
                    if (!this.updateRenderer(var6)) {
                        return;
                    }
                    ++this.renderersUpdated;
                    if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
                        return;
                    }
                    final float var12 = var7 / 5.0f;
                    for (int var13 = 0; var13 < var1.worldRenderersToUpdate.size(); ++var13) {
                        final WorldRendererSmooth var14 = (WorldRendererSmooth)var1.worldRenderersToUpdate.get(var13);
                        if (var14 != null) {
                            float var15 = var14.distanceToEntitySquared(var2);
                            if (!var14.isInFrustum) {
                                var15 *= var5;
                            }
                            final float var16 = Math.abs(var15 - var7);
                            if (var16 < var12) {
                                var1.worldRenderersToUpdate.set(var13, null);
                                if (!this.updateRenderer(var14)) {
                                    return;
                                }
                                ++this.renderersUpdated;
                                if (System.nanoTime() > this.updateStartTimeNs + this.updateTimeNs) {
                                    break;
                                }
                            }
                        }
                    }
                }
                if (this.renderersFound == 0) {
                    var1.worldRenderersToUpdate.clear();
                }
                if (var1.worldRenderersToUpdate.size() > 100 && this.renderersFound < var1.worldRenderersToUpdate.size() * 4 / 5) {
                    int var9 = 0;
                    for (int var13 = 0; var13 < var1.worldRenderersToUpdate.size(); ++var13) {
                        final Object var17 = var1.worldRenderersToUpdate.get(var13);
                        if (var17 != null) {
                            if (var13 != var9) {
                                var1.worldRenderersToUpdate.set(var9, var17);
                            }
                            ++var9;
                        }
                    }
                    for (int var13 = var1.worldRenderersToUpdate.size() - 1; var13 >= var9; --var13) {
                        var1.worldRenderersToUpdate.remove(var13);
                    }
                }
            }
        }
    }
    
    private boolean updateRenderer(final WorldRendererSmooth var1) {
        final long var2 = this.updateStartTimeNs + this.updateTimeNs;
        var1.needsUpdate = false;
        final boolean var3 = var1.updateRenderer(var2);
        if (!var3) {
            this.currentUpdateRenderer = var1;
            return false;
        }
        var1.finishUpdate();
        this.currentUpdateRenderer = null;
        return true;
    }
    
    @Override
    public void finishCurrentUpdate() {
    }
    
    @Override
    public void resumeBackgroundUpdates() {
    }
    
    @Override
    public void pauseBackgroundUpdates() {
    }
    
    @Override
    public void preRender(final RenderGlobal var1, final EntityLiving var2) {
    }
    
    @Override
    public void postRender() {
    }
}
