package net.minecraft.src;

import org.lwjgl.opengl.*;
import java.util.*;

public class WorldRenderer
{
    public World worldObj;
    protected int glRenderList;
    public static volatile int chunksUpdated;
    public int posX;
    public int posY;
    public int posZ;
    public int posXMinus;
    public int posYMinus;
    public int posZMinus;
    public int posXClip;
    public int posYClip;
    public int posZClip;
    public boolean isInFrustum;
    public boolean[] skipRenderPass;
    public int posXPlus;
    public int posYPlus;
    public int posZPlus;
    public volatile boolean needsUpdate;
    public AxisAlignedBB rendererBoundingBox;
    public int chunkIndex;
    public boolean isVisible;
    public boolean isWaitingOnOcclusionQuery;
    public int glOcclusionQuery;
    public boolean isChunkLit;
    protected boolean isInitialized;
    public List tileEntityRenderers;
    protected List tileEntities;
    protected int bytesDrawn;
    public boolean isVisibleFromPosition;
    public double visibleFromX;
    public double visibleFromY;
    public double visibleFromZ;
    public boolean isInFrustrumFully;
    protected boolean needsBoxUpdate;
    public volatile boolean isUpdating;
    public static int globalChunkOffsetX;
    public static int globalChunkOffsetZ;
    
    static {
        WorldRenderer.chunksUpdated = 0;
        WorldRenderer.globalChunkOffsetX = 0;
        WorldRenderer.globalChunkOffsetZ = 0;
    }
    
    public WorldRenderer(final World par1World, final List par2List, final int par3, final int par4, final int par5, final int par6) {
        this.glRenderList = -1;
        this.isInFrustum = false;
        this.skipRenderPass = new boolean[2];
        this.isVisible = true;
        this.isInitialized = false;
        this.tileEntityRenderers = new ArrayList();
        this.isVisibleFromPosition = false;
        this.isInFrustrumFully = false;
        this.needsBoxUpdate = false;
        this.isUpdating = false;
        this.worldObj = par1World;
        this.tileEntities = par2List;
        this.glRenderList = par6;
        this.posX = -999;
        this.setPosition(par3, par4, par5);
        this.needsUpdate = false;
    }
    
    public void setPosition(final int par1, final int par2, final int par3) {
        if (par1 != this.posX || par2 != this.posY || par3 != this.posZ) {
            this.setDontDraw();
            this.posX = par1;
            this.posY = par2;
            this.posZ = par3;
            this.posXPlus = par1 + 8;
            this.posYPlus = par2 + 8;
            this.posZPlus = par3 + 8;
            this.posXClip = (par1 & 0x3FF);
            this.posYClip = par2;
            this.posZClip = (par3 & 0x3FF);
            this.posXMinus = par1 - this.posXClip;
            this.posYMinus = par2 - this.posYClip;
            this.posZMinus = par3 - this.posZClip;
            final float var4 = 0.0f;
            this.rendererBoundingBox = AxisAlignedBB.getBoundingBox(par1 - var4, par2 - var4, par3 - var4, par1 + 16 + var4, par2 + 16 + var4, par3 + 16 + var4);
            this.needsBoxUpdate = true;
            this.markDirty();
            this.isVisibleFromPosition = false;
        }
    }
    
    private void setupGLTranslation() {
        GL11.glTranslatef(this.posXClip, this.posYClip, this.posZClip);
    }
    
    public void updateRenderer() {
        if (this.worldObj != null && this.needsUpdate) {
            if (this.needsBoxUpdate) {
                final float var1 = 0.0f;
                GL11.glNewList(this.glRenderList + 2, 4864);
                Render.renderAABB(AxisAlignedBB.getAABBPool().getAABB(this.posXClip - var1, this.posYClip - var1, this.posZClip - var1, this.posXClip + 16 + var1, this.posYClip + 16 + var1, this.posZClip + 16 + var1));
                GL11.glEndList();
                this.needsBoxUpdate = false;
            }
            this.isVisible = true;
            this.isVisibleFromPosition = false;
            this.needsUpdate = false;
            final int var2 = this.posX;
            final int var3 = this.posY;
            final int var4 = this.posZ;
            final int var5 = this.posX + 16;
            final int var6 = this.posY + 16;
            final int var7 = this.posZ + 16;
            for (int var8 = 0; var8 < 2; ++var8) {
                this.skipRenderPass[var8] = true;
            }
            if (Reflector.LightCache.exists()) {
                final Object var9 = Reflector.getFieldValue(Reflector.LightCache_cache);
                Reflector.callVoid(var9, Reflector.LightCache_clear, new Object[0]);
                Reflector.callVoid(Reflector.BlockCoord_resetPool, new Object[0]);
            }
            Chunk.isLit = false;
            final HashSet var10 = new HashSet();
            var10.addAll(this.tileEntityRenderers);
            this.tileEntityRenderers.clear();
            final byte var11 = 1;
            final ChunkCache var12 = new ChunkCache(this.worldObj, var2 - var11, var3 - var11, var4 - var11, var5 + var11, var6 + var11, var7 + var11, var11);
            if (!var12.extendedLevelsInChunkCache()) {
                ++WorldRenderer.chunksUpdated;
                final RenderBlocks var13 = new RenderBlocks(var12);
                this.bytesDrawn = 0;
                final Tessellator var14 = Tessellator.instance;
                final boolean var15 = Reflector.ForgeHooksClient.exists();
                for (int var16 = 0; var16 < 2; ++var16) {
                    boolean var17 = false;
                    boolean var18 = false;
                    boolean var19 = false;
                    for (int var20 = var3; var20 < var6; ++var20) {
                        for (int var21 = var4; var21 < var7; ++var21) {
                            for (int var22 = var2; var22 < var5; ++var22) {
                                final int var23 = var12.getBlockId(var22, var20, var21);
                                if (var23 > 0) {
                                    if (!var19) {
                                        var19 = true;
                                        GL11.glNewList(this.glRenderList + var16, 4864);
                                        var14.setRenderingChunk(true);
                                        var14.startDrawingQuads();
                                        var14.setTranslation(-WorldRenderer.globalChunkOffsetX, 0.0, -WorldRenderer.globalChunkOffsetZ);
                                    }
                                    final Block var24 = Block.blocksList[var23];
                                    if (var24 != null) {
                                        if (var16 == 0 && var24.hasTileEntity()) {
                                            final TileEntity var25 = var12.getBlockTileEntity(var22, var20, var21);
                                            if (TileEntityRenderer.instance.hasSpecialRenderer(var25)) {
                                                this.tileEntityRenderers.add(var25);
                                            }
                                        }
                                        final int var26 = var24.getRenderBlockPass();
                                        boolean var27 = true;
                                        if (var26 != var16) {
                                            var17 = true;
                                            var27 = false;
                                        }
                                        if (var15) {
                                            var27 = Reflector.callBoolean(var24, Reflector.ForgeBlock_canRenderInPass, var16);
                                        }
                                        if (var27) {
                                            var18 |= var13.renderBlockByRenderType(var24, var22, var20, var21);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (var19) {
                        this.bytesDrawn += var14.draw();
                        GL11.glEndList();
                        var14.setRenderingChunk(false);
                        var14.setTranslation(0.0, 0.0, 0.0);
                    }
                    else {
                        var18 = false;
                    }
                    if (var18) {
                        this.skipRenderPass[var16] = false;
                    }
                    if (!var17) {
                        break;
                    }
                }
            }
            final HashSet var28 = new HashSet();
            var28.addAll(this.tileEntityRenderers);
            var28.removeAll(var10);
            this.tileEntities.addAll(var28);
            var10.removeAll(this.tileEntityRenderers);
            this.tileEntities.removeAll(var10);
            this.isChunkLit = Chunk.isLit;
            this.isInitialized = true;
        }
    }
    
    public float distanceToEntitySquared(final Entity par1Entity) {
        final float var2 = (float)(par1Entity.posX - this.posXPlus);
        final float var3 = (float)(par1Entity.posY - this.posYPlus);
        final float var4 = (float)(par1Entity.posZ - this.posZPlus);
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public void setDontDraw() {
        for (int var1 = 0; var1 < 2; ++var1) {
            this.skipRenderPass[var1] = true;
        }
        this.isInFrustum = false;
        this.isInitialized = false;
    }
    
    public void stopRendering() {
        this.setDontDraw();
        this.worldObj = null;
    }
    
    public int getGLCallListForPass(final int par1) {
        return this.isInFrustum ? (this.skipRenderPass[par1] ? -1 : (this.glRenderList + par1)) : -1;
    }
    
    public void updateInFrustum(final ICamera par1ICamera) {
        this.isInFrustum = par1ICamera.isBoundingBoxInFrustum(this.rendererBoundingBox);
        if (this.isInFrustum && Config.isOcclusionEnabled() && Config.isOcclusionFancy()) {
            this.isInFrustrumFully = par1ICamera.isBoundingBoxInFrustumFully(this.rendererBoundingBox);
        }
        else {
            this.isInFrustrumFully = false;
        }
    }
    
    public void callOcclusionQueryList() {
        GL11.glCallList(this.glRenderList + 2);
    }
    
    public boolean skipAllRenderPasses() {
        return this.isInitialized && (this.skipRenderPass[0] && this.skipRenderPass[1]);
    }
    
    public void markDirty() {
        this.needsUpdate = true;
    }
}
