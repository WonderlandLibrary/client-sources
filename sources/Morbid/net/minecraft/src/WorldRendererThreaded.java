package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class WorldRendererThreaded extends WorldRenderer
{
    private int glRenderListStable;
    private int glRenderListBoundingBox;
    
    public WorldRendererThreaded(final World var1, final List var2, final int var3, final int var4, final int var5, final int var6) {
        super(var1, var2, var3, var4, var5, var6);
        this.glRenderListStable = this.glRenderList + 393216;
        this.glRenderListBoundingBox = this.glRenderList + 2;
    }
    
    @Override
    public void updateRenderer() {
        if (this.worldObj != null) {
            this.updateRenderer(null);
            this.finishUpdate();
        }
    }
    
    public void updateRenderer(final IWrUpdateListener var1) {
        if (this.worldObj != null) {
            this.needsUpdate = false;
            final int var2 = this.posX;
            final int var3 = this.posY;
            final int var4 = this.posZ;
            final int var5 = this.posX + 16;
            final int var6 = this.posY + 16;
            final int var7 = this.posZ + 16;
            final boolean[] var8 = new boolean[2];
            for (int var9 = 0; var9 < var8.length; ++var9) {
                var8[var9] = true;
            }
            if (Reflector.LightCache.exists()) {
                final Object var10 = Reflector.getFieldValue(Reflector.LightCache_cache);
                Reflector.callVoid(var10, Reflector.LightCache_clear, new Object[0]);
                Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
            }
            Chunk.isLit = false;
            final HashSet var11 = new HashSet();
            var11.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
            final byte var12 = 1;
            final ChunkCache var13 = new ChunkCache(this.worldObj, var2 - var12, var3 - var12, var4 - var12, var5 + var12, var6 + var12, var7 + var12, var12);
            if (!var13.extendedLevelsInChunkCache()) {
                ++WorldRendererThreaded.chunksUpdated;
                final RenderBlocks var14 = new RenderBlocks(var13);
                this.bytesDrawn = 0;
                final Tessellator var15 = Tessellator.instance;
                final boolean var16 = Reflector.ForgeHooksClient.exists();
                final WrUpdateControl var17 = new WrUpdateControl();
                for (int var18 = 0; var18 < 2; ++var18) {
                    var17.setRenderPass(var18);
                    boolean var19 = false;
                    boolean var20 = false;
                    boolean var21 = false;
                    for (int var22 = var3; var22 < var6; ++var22) {
                        if (var20 && var1 != null) {
                            var1.updating(var17);
                        }
                        for (int var23 = var4; var23 < var7; ++var23) {
                            for (int var24 = var2; var24 < var5; ++var24) {
                                final int var25 = var13.getBlockId(var24, var22, var23);
                                if (var25 > 0) {
                                    if (!var21) {
                                        var21 = true;
                                        GL11.glNewList(this.glRenderList + var18, 4864);
                                        var15.setRenderingChunk(true);
                                        var15.startDrawingQuads();
                                        var15.setTranslation(-WorldRendererThreaded.globalChunkOffsetX, 0.0, -WorldRendererThreaded.globalChunkOffsetZ);
                                    }
                                    final Block var26 = Block.blocksList[var25];
                                    if (var18 == 0 && var26.hasTileEntity()) {
                                        final TileEntity var27 = var13.getBlockTileEntity(var24, var22, var23);
                                        if (TileEntityRenderer.instance.hasSpecialRenderer(var27)) {
                                            this.tileEntityRenderers.add(var27);
                                        }
                                    }
                                    final int var28 = var26.getRenderBlockPass();
                                    boolean var29 = true;
                                    if (var28 != var18) {
                                        var19 = true;
                                        var29 = false;
                                    }
                                    if (var16) {
                                        var29 = Reflector.callBoolean(var26, Reflector.ForgeBlock_canRenderInPass, var18);
                                    }
                                    if (var29) {
                                        var20 |= var14.renderBlockByRenderType(var26, var24, var22, var23);
                                    }
                                }
                            }
                        }
                    }
                    if (var21) {
                        if (var1 != null) {
                            var1.updating(var17);
                        }
                        this.bytesDrawn += var15.draw();
                        GL11.glEndList();
                        var15.setRenderingChunk(false);
                        var15.setTranslation(0.0, 0.0, 0.0);
                    }
                    else {
                        var20 = false;
                    }
                    if (var20) {
                        var8[var18] = false;
                    }
                    if (!var19) {
                        break;
                    }
                }
            }
            for (int var30 = 0; var30 < 2; ++var30) {
                this.skipRenderPass[var30] = var8[var30];
            }
            final HashSet var31 = new HashSet();
            var31.addAll(this.tileEntityRenderers);
            var31.removeAll(var11);
            this.tileEntities.addAll(var31);
            var11.removeAll(this.tileEntityRenderers);
            this.tileEntities.removeAll(var11);
            this.isChunkLit = Chunk.isLit;
            this.isInitialized = true;
            this.isVisible = true;
            this.isVisibleFromPosition = false;
        }
    }
    
    public void finishUpdate() {
        final int var1 = this.glRenderList;
        this.glRenderList = this.glRenderListStable;
        this.glRenderListStable = var1;
        for (int var2 = 0; var2 < 2; ++var2) {
            if (!this.skipRenderPass[var2]) {
                GL11.glNewList(this.glRenderList + var2, 4864);
                GL11.glEndList();
            }
        }
        if (this.needsBoxUpdate && !this.skipAllRenderPasses()) {
            final float var3 = 0.0f;
            GL11.glNewList(this.glRenderListBoundingBox, 4864);
            Render.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip - var3, this.posYClip - var3, this.posZClip - var3, this.posXClip + 16 + var3, this.posYClip + 16 + var3, this.posZClip + 16 + var3));
            GL11.glEndList();
            this.needsBoxUpdate = false;
        }
    }
    
    @Override
    public int getGLCallListForPass(final int var1) {
        return this.isInFrustum ? (this.skipRenderPass[var1] ? -1 : (this.glRenderListStable + var1)) : -1;
    }
    
    @Override
    public void callOcclusionQueryList() {
        GL11.glCallList(this.glRenderListBoundingBox);
    }
}
