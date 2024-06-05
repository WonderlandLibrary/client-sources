package net.minecraft.src;

import org.lwjgl.opengl.*;
import java.util.*;

public class WorldRendererSmooth extends WorldRenderer
{
    private WrUpdateState updateState;
    public int activeSet;
    public int[] activeListIndex;
    public int[][][] glWorkLists;
    public boolean[] tempSkipRenderPass;
    
    public WorldRendererSmooth(final World var1, final List var2, final int var3, final int var4, final int var5, final int var6) {
        super(var1, var2, var3, var4, var5, var6);
        this.updateState = new WrUpdateState();
        this.activeSet = 0;
        this.activeListIndex = new int[2];
        this.glWorkLists = new int[2][2][16];
        this.tempSkipRenderPass = new boolean[2];
        final int var7 = 393216 + 64 * (this.glRenderList / 3);
        for (int var8 = 0; var8 < 2; ++var8) {
            final int var9 = var7 + var8 * 2 * 16;
            for (int var10 = 0; var10 < 2; ++var10) {
                final int var11 = var9 + var10 * 16;
                for (int var12 = 0; var12 < 16; ++var12) {
                    this.glWorkLists[var8][var10][var12] = var11 + var12;
                }
            }
        }
    }
    
    @Override
    public void setPosition(final int var1, final int var2, final int var3) {
        if (this.isUpdating) {
            this.updateRenderer();
        }
        super.setPosition(var1, var2, var3);
    }
    
    @Override
    public void updateRenderer() {
        if (this.worldObj != null) {
            this.updateRenderer(0L);
            this.finishUpdate();
        }
    }
    
    public boolean updateRenderer(final long var1) {
        if (this.worldObj == null) {
            return true;
        }
        this.needsUpdate = false;
        if (!this.isUpdating) {
            if (this.needsBoxUpdate) {
                final float var2 = 0.0f;
                GL11.glNewList(this.glRenderList + 2, 4864);
                Render.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip - var2, this.posYClip - var2, this.posZClip - var2, this.posXClip + 16 + var2, this.posYClip + 16 + var2, this.posZClip + 16 + var2));
                GL11.glEndList();
                this.needsBoxUpdate = false;
            }
            if (Reflector.LightCache.exists()) {
                final Object var3 = Reflector.getFieldValue(Reflector.LightCache_cache);
                Reflector.callVoid(var3, Reflector.LightCache_clear, new Object[0]);
                Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
            }
            Chunk.isLit = false;
        }
        final int var4 = this.posX;
        final int var5 = this.posY;
        final int var6 = this.posZ;
        final int var7 = this.posX + 16;
        final int var8 = this.posY + 16;
        final int var9 = this.posZ + 16;
        ChunkCache var10 = null;
        RenderBlocks var11 = null;
        HashSet var12 = null;
        if (!this.isUpdating) {
            for (int var13 = 0; var13 < 2; ++var13) {
                this.tempSkipRenderPass[var13] = true;
            }
            final byte var14 = 1;
            var10 = new ChunkCache(this.worldObj, var4 - var14, var5 - var14, var6 - var14, var7 + var14, var8 + var14, var9 + var14, var14);
            var11 = new RenderBlocks(var10);
            var12 = new HashSet();
            var12.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
        }
        if (this.isUpdating || !var10.extendedLevelsInChunkCache()) {
            this.bytesDrawn = 0;
            final Tessellator var15 = Tessellator.instance;
            final boolean var16 = Reflector.ForgeHooksClient.exists();
            for (int var17 = 0; var17 < 2; ++var17) {
                boolean var18 = false;
                boolean var19 = false;
                boolean var20 = false;
                for (int var21 = var5; var21 < var8; ++var21) {
                    if (this.isUpdating) {
                        this.isUpdating = false;
                        var10 = this.updateState.chunkcache;
                        var11 = this.updateState.renderblocks;
                        var12 = this.updateState.setOldEntityRenders;
                        var17 = this.updateState.renderPass;
                        var21 = this.updateState.y;
                        var18 = this.updateState.flag;
                        var19 = this.updateState.hasRenderedBlocks;
                        var20 = this.updateState.hasGlList;
                        if (var20) {
                            GL11.glNewList(this.glWorkLists[this.activeSet][var17][this.activeListIndex[var17]], 4864);
                            var15.setRenderingChunk(true);
                            var15.startDrawingQuads();
                            var15.setTranslation(-WorldRendererSmooth.globalChunkOffsetX, 0.0, -WorldRendererSmooth.globalChunkOffsetZ);
                        }
                    }
                    else if (var20 && var1 != 0L && System.nanoTime() - var1 > 0L && this.activeListIndex[var17] < 15) {
                        var15.draw();
                        GL11.glEndList();
                        var15.setRenderingChunk(false);
                        var15.setTranslation(0.0, 0.0, 0.0);
                        final int[] activeListIndex = this.activeListIndex;
                        final int n = var17;
                        ++activeListIndex[n];
                        this.updateState.chunkcache = var10;
                        this.updateState.renderblocks = var11;
                        this.updateState.setOldEntityRenders = var12;
                        this.updateState.renderPass = var17;
                        this.updateState.y = var21;
                        this.updateState.flag = var18;
                        this.updateState.hasRenderedBlocks = var19;
                        this.updateState.hasGlList = var20;
                        this.isUpdating = true;
                        return false;
                    }
                    for (int var22 = var6; var22 < var9; ++var22) {
                        for (int var23 = var4; var23 < var7; ++var23) {
                            final int var24 = var10.getBlockId(var23, var21, var22);
                            if (var24 > 0) {
                                if (!var20) {
                                    var20 = true;
                                    GL11.glNewList(this.glWorkLists[this.activeSet][var17][this.activeListIndex[var17]], 4864);
                                    var15.setRenderingChunk(true);
                                    var15.startDrawingQuads();
                                    var15.setTranslation(-WorldRendererSmooth.globalChunkOffsetX, 0.0, -WorldRendererSmooth.globalChunkOffsetZ);
                                }
                                final Block var25 = Block.blocksList[var24];
                                if (var17 == 0 && var25.hasTileEntity()) {
                                    final TileEntity var26 = var10.getBlockTileEntity(var23, var21, var22);
                                    if (TileEntityRenderer.instance.hasSpecialRenderer(var26)) {
                                        this.tileEntityRenderers.add(var26);
                                    }
                                }
                                final int var27 = var25.getRenderBlockPass();
                                boolean var28 = true;
                                if (var27 != var17) {
                                    var18 = true;
                                    var28 = false;
                                }
                                if (var16) {
                                    var28 = Reflector.callBoolean(var25, Reflector.ForgeBlock_canRenderInPass, var17);
                                }
                                if (var28) {
                                    var19 |= var11.renderBlockByRenderType(var25, var23, var21, var22);
                                }
                            }
                        }
                    }
                }
                if (var20) {
                    this.bytesDrawn += var15.draw();
                    GL11.glEndList();
                    var15.setRenderingChunk(false);
                    var15.setTranslation(0.0, 0.0, 0.0);
                }
                else {
                    var19 = false;
                }
                if (var19) {
                    this.tempSkipRenderPass[var17] = false;
                }
                if (!var18) {
                    break;
                }
            }
        }
        final HashSet var29 = new HashSet();
        var29.addAll(this.tileEntityRenderers);
        var29.removeAll(var12);
        this.tileEntities.addAll(var29);
        var12.removeAll(this.tileEntityRenderers);
        this.tileEntities.removeAll(var12);
        this.isChunkLit = Chunk.isLit;
        this.isInitialized = true;
        ++WorldRendererSmooth.chunksUpdated;
        this.isVisible = true;
        this.isVisibleFromPosition = false;
        this.skipRenderPass[0] = this.tempSkipRenderPass[0];
        this.skipRenderPass[1] = this.tempSkipRenderPass[1];
        this.isUpdating = false;
        return true;
    }
    
    public void finishUpdate() {
        for (int var1 = 0; var1 < 2; ++var1) {
            if (!this.skipRenderPass[var1]) {
                GL11.glNewList(this.glRenderList + var1, 4864);
                for (int var2 = 0; var2 <= this.activeListIndex[var1]; ++var2) {
                    final int var3 = this.glWorkLists[this.activeSet][var1][var2];
                    GL11.glCallList(var3);
                }
                GL11.glEndList();
            }
        }
        if (this.activeSet == 0) {
            this.activeSet = 1;
        }
        else {
            this.activeSet = 0;
        }
        for (int var1 = 0; var1 < 2; ++var1) {
            if (!this.skipRenderPass[var1]) {
                for (int var2 = 0; var2 <= this.activeListIndex[var1]; ++var2) {
                    final int var3 = this.glWorkLists[this.activeSet][var1][var2];
                    GL11.glNewList(var3, 4864);
                    GL11.glEndList();
                }
            }
        }
        for (int var1 = 0; var1 < 2; ++var1) {
            this.activeListIndex[var1] = 0;
        }
    }
}
