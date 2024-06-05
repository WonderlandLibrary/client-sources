package net.minecraft.src;

import net.minecraft.client.*;
import java.nio.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import java.util.*;
import java.util.concurrent.*;
import org.lwjgl.input.*;

public class RenderGlobal implements IWorldAccess
{
    public List tileEntities;
    public WorldClient theWorld;
    public final RenderEngine renderEngine;
    public CompactArrayList worldRenderersToUpdate;
    private WorldRenderer[] sortedWorldRenderers;
    private WorldRenderer[] worldRenderers;
    private int renderChunksWide;
    private int renderChunksTall;
    private int renderChunksDeep;
    private int glRenderListBase;
    public Minecraft mc;
    public RenderBlocks globalRenderBlocks;
    private IntBuffer glOcclusionQueryBase;
    private boolean occlusionEnabled;
    private int cloudTickCounter;
    private int starGLCallList;
    private int glSkyList;
    private int glSkyList2;
    private int minBlockX;
    private int minBlockY;
    private int minBlockZ;
    private int maxBlockX;
    private int maxBlockY;
    private int maxBlockZ;
    public Map damagedBlocks;
    private Icon[] destroyBlockIcons;
    private int renderDistance;
    private int renderEntitiesStartupCounter;
    private int countEntitiesTotal;
    private int countEntitiesRendered;
    private int countEntitiesHidden;
    int[] dummyBuf50k;
    IntBuffer occlusionResult;
    private int renderersLoaded;
    private int renderersBeingClipped;
    private int renderersBeingOccluded;
    private int renderersBeingRendered;
    private int renderersSkippingRenderPass;
    private int dummyRenderInt;
    private int worldRenderersCheckIndex;
    private IntBuffer glListBuffer;
    double prevSortX;
    double prevSortY;
    double prevSortZ;
    int frustumCheckOffset;
    double prevReposX;
    double prevReposY;
    double prevReposZ;
    public Entity renderedEntity;
    private long lastMovedTime;
    private long lastActionTime;
    
    public RenderGlobal(final Minecraft par1Minecraft, final RenderEngine par2RenderEngine) {
        this.tileEntities = new ArrayList();
        this.worldRenderersToUpdate = new CompactArrayList(100, 0.8f);
        this.occlusionEnabled = false;
        this.cloudTickCounter = 0;
        this.damagedBlocks = new HashMap();
        this.renderDistance = -1;
        this.renderEntitiesStartupCounter = 2;
        this.dummyBuf50k = new int[50000];
        this.occlusionResult = GLAllocation.createDirectIntBuffer(64);
        this.glListBuffer = BufferUtils.createIntBuffer(65536);
        this.prevSortX = -9999.0;
        this.prevSortY = -9999.0;
        this.prevSortZ = -9999.0;
        this.frustumCheckOffset = 0;
        this.lastMovedTime = System.currentTimeMillis();
        this.lastActionTime = System.currentTimeMillis();
        this.mc = par1Minecraft;
        this.renderEngine = par2RenderEngine;
        final byte var3 = 65;
        final byte var4 = 16;
        this.glRenderListBase = GLAllocation.generateDisplayLists(var3 * var3 * var4 * 3);
        this.occlusionEnabled = OpenGlCapsChecker.checkARBOcclusion();
        if (this.occlusionEnabled) {
            this.occlusionResult.clear();
            (this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(var3 * var3 * var4)).clear();
            this.glOcclusionQueryBase.position(0);
            this.glOcclusionQueryBase.limit(var3 * var3 * var4);
            ARBOcclusionQuery.glGenQueriesARB(this.glOcclusionQueryBase);
        }
        this.starGLCallList = GLAllocation.generateDisplayLists(3);
        GL11.glPushMatrix();
        GL11.glNewList(this.starGLCallList, 4864);
        this.renderStars();
        GL11.glEndList();
        GL11.glPopMatrix();
        final Tessellator var5 = Tessellator.instance;
        GL11.glNewList(this.glSkyList = this.starGLCallList + 1, 4864);
        final byte var6 = 64;
        final int var7 = 256 / var6 + 2;
        float var8 = 16.0f;
        for (int var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6) {
            for (int var10 = -var6 * var7; var10 <= var6 * var7; var10 += var6) {
                var5.startDrawingQuads();
                var5.addVertex(var9 + 0, var8, var10 + 0);
                var5.addVertex(var9 + var6, var8, var10 + 0);
                var5.addVertex(var9 + var6, var8, var10 + var6);
                var5.addVertex(var9 + 0, var8, var10 + var6);
                var5.draw();
            }
        }
        GL11.glEndList();
        GL11.glNewList(this.glSkyList2 = this.starGLCallList + 2, 4864);
        var8 = -16.0f;
        var5.startDrawingQuads();
        for (int var9 = -var6 * var7; var9 <= var6 * var7; var9 += var6) {
            for (int var10 = -var6 * var7; var10 <= var6 * var7; var10 += var6) {
                var5.addVertex(var9 + var6, var8, var10 + 0);
                var5.addVertex(var9 + 0, var8, var10 + 0);
                var5.addVertex(var9 + 0, var8, var10 + var6);
                var5.addVertex(var9 + var6, var8, var10 + var6);
            }
        }
        var5.draw();
        GL11.glEndList();
        this.renderEngine.updateDynamicTextures();
    }
    
    private void renderStars() {
        final Random var1 = new Random(10842L);
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        for (int var3 = 0; var3 < 1500; ++var3) {
            double var4 = var1.nextFloat() * 2.0f - 1.0f;
            double var5 = var1.nextFloat() * 2.0f - 1.0f;
            double var6 = var1.nextFloat() * 2.0f - 1.0f;
            final double var7 = 0.15f + var1.nextFloat() * 0.1f;
            double var8 = var4 * var4 + var5 * var5 + var6 * var6;
            if (var8 < 1.0 && var8 > 0.01) {
                var8 = 1.0 / Math.sqrt(var8);
                var4 *= var8;
                var5 *= var8;
                var6 *= var8;
                final double var9 = var4 * 100.0;
                final double var10 = var5 * 100.0;
                final double var11 = var6 * 100.0;
                final double var12 = Math.atan2(var4, var6);
                final double var13 = Math.sin(var12);
                final double var14 = Math.cos(var12);
                final double var15 = Math.atan2(Math.sqrt(var4 * var4 + var6 * var6), var5);
                final double var16 = Math.sin(var15);
                final double var17 = Math.cos(var15);
                final double var18 = var1.nextDouble() * 3.141592653589793 * 2.0;
                final double var19 = Math.sin(var18);
                final double var20 = Math.cos(var18);
                for (int var21 = 0; var21 < 4; ++var21) {
                    final double var22 = 0.0;
                    final double var23 = ((var21 & 0x2) - 1) * var7;
                    final double var24 = ((var21 + 1 & 0x2) - 1) * var7;
                    final double var25 = var23 * var20 - var24 * var19;
                    final double var26 = var24 * var20 + var23 * var19;
                    final double var27 = var25 * var16 + var22 * var17;
                    final double var28 = var22 * var16 - var25 * var17;
                    final double var29 = var28 * var13 - var26 * var14;
                    final double var30 = var26 * var13 + var28 * var14;
                    var2.addVertex(var9 + var29, var10 + var27, var11 + var30);
                }
            }
        }
        var2.draw();
    }
    
    public void setWorldAndLoadRenderers(final WorldClient par1WorldClient) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }
        this.prevSortX = -9999.0;
        this.prevSortY = -9999.0;
        this.prevSortZ = -9999.0;
        RenderManager.instance.set(par1WorldClient);
        this.theWorld = par1WorldClient;
        this.globalRenderBlocks = new RenderBlocks(par1WorldClient);
        if (par1WorldClient != null) {
            par1WorldClient.addWorldAccess(this);
            this.loadRenderers();
        }
    }
    
    public void loadRenderers() {
        if (this.theWorld != null) {
            Block.leaves.setGraphicsLevel(Config.isTreesFancy());
            this.renderDistance = this.mc.gameSettings.renderDistance;
            if (this.worldRenderers != null) {
                for (int var1 = 0; var1 < this.worldRenderers.length; ++var1) {
                    this.worldRenderers[var1].stopRendering();
                }
            }
            int var1 = 64 << 3 - this.renderDistance;
            final short var2 = 512;
            var1 = 2 * this.mc.gameSettings.ofRenderDistanceFine;
            if (Config.isLoadChunksFar() && var1 < var2) {
                var1 = var2;
            }
            var1 += Config.getPreloadedChunks() * 2 * 16;
            short var3 = 400;
            if (this.mc.gameSettings.ofRenderDistanceFine > 256) {
                var3 = 1024;
            }
            if (var1 > var3) {
                var1 = var3;
            }
            this.prevReposX = -9999.0;
            this.prevReposY = -9999.0;
            this.prevReposZ = -9999.0;
            this.renderChunksWide = var1 / 16 + 1;
            this.renderChunksTall = 16;
            this.renderChunksDeep = var1 / 16 + 1;
            this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
            int var4 = 0;
            int var5 = 0;
            this.minBlockX = 0;
            this.minBlockY = 0;
            this.minBlockZ = 0;
            this.maxBlockX = this.renderChunksWide;
            this.maxBlockY = this.renderChunksTall;
            this.maxBlockZ = this.renderChunksDeep;
            for (int var6 = 0; var6 < this.worldRenderersToUpdate.size(); ++var6) {
                final WorldRenderer var7 = (WorldRenderer)this.worldRenderersToUpdate.get(var6);
                if (var7 != null) {
                    var7.needsUpdate = false;
                }
            }
            this.worldRenderersToUpdate.clear();
            this.tileEntities.clear();
            for (int var6 = 0; var6 < this.renderChunksWide; ++var6) {
                for (int var8 = 0; var8 < this.renderChunksTall; ++var8) {
                    for (int var9 = 0; var9 < this.renderChunksDeep; ++var9) {
                        final int var10 = (var9 * this.renderChunksTall + var8) * this.renderChunksWide + var6;
                        this.worldRenderers[var10] = WrUpdates.makeWorldRenderer(this.theWorld, this.tileEntities, var6 * 16, var8 * 16, var9 * 16, this.glRenderListBase + var4);
                        if (this.occlusionEnabled) {
                            this.worldRenderers[var10].glOcclusionQuery = this.glOcclusionQueryBase.get(var5);
                        }
                        this.worldRenderers[var10].isWaitingOnOcclusionQuery = false;
                        this.worldRenderers[var10].isVisible = true;
                        this.worldRenderers[var10].isInFrustum = false;
                        this.worldRenderers[var10].chunkIndex = var5++;
                        this.sortedWorldRenderers[var10] = this.worldRenderers[var10];
                        if (this.theWorld.chunkExists(var6, var9)) {
                            this.worldRenderers[var10].markDirty();
                            this.worldRenderersToUpdate.add(this.worldRenderers[var10]);
                        }
                        var4 += 3;
                    }
                }
            }
            if (this.theWorld != null) {
                Object var11 = this.mc.renderViewEntity;
                if (var11 == null) {
                    var11 = Minecraft.thePlayer;
                }
                if (var11 != null) {
                    this.markRenderersForNewPosition(MathHelper.floor_double(((EntityLiving)var11).posX), MathHelper.floor_double(((EntityLiving)var11).posY), MathHelper.floor_double(((EntityLiving)var11).posZ));
                    Arrays.sort(this.sortedWorldRenderers, new EntitySorter((Entity)var11));
                }
            }
            this.renderEntitiesStartupCounter = 2;
        }
    }
    
    public void renderEntities(final Vec3 par1Vec3, final ICamera par2ICamera, final float par3) {
        if (this.renderEntitiesStartupCounter > 0) {
            --this.renderEntitiesStartupCounter;
        }
        else {
            this.theWorld.theProfiler.startSection("prepare");
            TileEntityRenderer.instance.cacheActiveRenderInfo(this.theWorld, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, par3);
            RenderManager.instance.cacheActiveRenderInfo(this.theWorld, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.pointedEntityLiving, this.mc.gameSettings, par3);
            this.countEntitiesTotal = 0;
            this.countEntitiesRendered = 0;
            this.countEntitiesHidden = 0;
            final EntityLiving var4 = this.mc.renderViewEntity;
            RenderManager.renderPosX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * par3;
            RenderManager.renderPosY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * par3;
            RenderManager.renderPosZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * par3;
            TileEntityRenderer.staticPlayerX = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * par3;
            TileEntityRenderer.staticPlayerY = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * par3;
            TileEntityRenderer.staticPlayerZ = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * par3;
            this.mc.entityRenderer.enableLightmap(par3);
            this.theWorld.theProfiler.endStartSection("global");
            final List var5 = this.theWorld.getLoadedEntityList();
            this.countEntitiesTotal = var5.size();
            for (int var6 = 0; var6 < this.theWorld.weatherEffects.size(); ++var6) {
                final Entity var7 = this.theWorld.weatherEffects.get(var6);
                ++this.countEntitiesRendered;
                if (var7.isInRangeToRenderVec3D(par1Vec3)) {
                    RenderManager.instance.renderEntity(var7, par3);
                }
            }
            this.theWorld.theProfiler.endStartSection("entities");
            final boolean var8 = this.mc.gameSettings.fancyGraphics;
            this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
            for (int var6 = 0; var6 < var5.size(); ++var6) {
                final Entity var7 = var5.get(var6);
                if (var7.isInRangeToRenderVec3D(par1Vec3) && (var7.ignoreFrustumCheck || par2ICamera.isBoundingBoxInFrustum(var7.boundingBox) || var7.riddenByEntity == Minecraft.thePlayer) && (var7 != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView != 0 || this.mc.renderViewEntity.isPlayerSleeping()) && this.theWorld.blockExists(MathHelper.floor_double(var7.posX), 0, MathHelper.floor_double(var7.posZ))) {
                    ++this.countEntitiesRendered;
                    if (var7.getClass() == EntityItemFrame.class) {
                        var7.renderDistanceWeight = 0.06;
                    }
                    this.renderedEntity = var7;
                    RenderManager.instance.renderEntity(var7, par3);
                    this.renderedEntity = null;
                }
            }
            this.mc.gameSettings.fancyGraphics = var8;
            this.theWorld.theProfiler.endStartSection("tileentities");
            RenderHelper.enableStandardItemLighting();
            for (int var6 = 0; var6 < this.tileEntities.size(); ++var6) {
                final TileEntity var9 = this.tileEntities.get(var6);
                final Class var10 = var9.getClass();
                if (var10 == TileEntitySign.class && !Config.zoomMode) {
                    final EntityClientPlayerMP var11 = Minecraft.thePlayer;
                    final double var12 = var9.getDistanceFrom(var11.posX, var11.posY, var11.posZ);
                    if (var12 > 256.0) {
                        final FontRenderer var13 = TileEntityRenderer.instance.getFontRenderer();
                        var13.enabled = false;
                        TileEntityRenderer.instance.renderTileEntity(var9, par3);
                        var13.enabled = true;
                        continue;
                    }
                }
                if (var10 == TileEntityChest.class) {
                    final int var14 = this.theWorld.getBlockId(var9.xCoord, var9.yCoord, var9.zCoord);
                    final Block var15 = Block.blocksList[var14];
                    if (!(var15 instanceof BlockChest)) {
                        continue;
                    }
                }
                TileEntityRenderer.instance.renderTileEntity(var9, par3);
            }
            this.mc.entityRenderer.disableLightmap(par3);
            this.theWorld.theProfiler.endSection();
        }
    }
    
    public String getDebugInfoRenders() {
        return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
    }
    
    public String getDebugInfoEntities() {
        return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersion();
    }
    
    private void markRenderersForNewPosition(int par1, int par2, int par3) {
        par1 -= 8;
        par2 -= 8;
        par3 -= 8;
        this.minBlockX = Integer.MAX_VALUE;
        this.minBlockY = Integer.MAX_VALUE;
        this.minBlockZ = Integer.MAX_VALUE;
        this.maxBlockX = Integer.MIN_VALUE;
        this.maxBlockY = Integer.MIN_VALUE;
        this.maxBlockZ = Integer.MIN_VALUE;
        final int var4 = this.renderChunksWide * 16;
        final int var5 = var4 / 2;
        for (int var6 = 0; var6 < this.renderChunksWide; ++var6) {
            int var7 = var6 * 16;
            int var8 = var7 + var5 - par1;
            if (var8 < 0) {
                var8 -= var4 - 1;
            }
            var8 /= var4;
            var7 -= var8 * var4;
            if (var7 < this.minBlockX) {
                this.minBlockX = var7;
            }
            if (var7 > this.maxBlockX) {
                this.maxBlockX = var7;
            }
            for (int var9 = 0; var9 < this.renderChunksDeep; ++var9) {
                int var10 = var9 * 16;
                int var11 = var10 + var5 - par3;
                if (var11 < 0) {
                    var11 -= var4 - 1;
                }
                var11 /= var4;
                var10 -= var11 * var4;
                if (var10 < this.minBlockZ) {
                    this.minBlockZ = var10;
                }
                if (var10 > this.maxBlockZ) {
                    this.maxBlockZ = var10;
                }
                for (int var12 = 0; var12 < this.renderChunksTall; ++var12) {
                    final int var13 = var12 * 16;
                    if (var13 < this.minBlockY) {
                        this.minBlockY = var13;
                    }
                    if (var13 > this.maxBlockY) {
                        this.maxBlockY = var13;
                    }
                    final WorldRenderer var14 = this.worldRenderers[(var9 * this.renderChunksTall + var12) * this.renderChunksWide + var6];
                    final boolean var15 = var14.needsUpdate;
                    var14.setPosition(var7, var13, var10);
                    if (!var15 && var14.needsUpdate) {
                        this.worldRenderersToUpdate.add(var14);
                    }
                }
            }
        }
    }
    
    public int sortAndRender(final EntityLiving par1EntityLiving, final int par2, final double par3) {
        final Profiler var5 = this.theWorld.theProfiler;
        var5.startSection("sortchunks");
        if (this.worldRenderersToUpdate.size() < 10) {
            final byte var6 = 10;
            for (int var7 = 0; var7 < var6; ++var7) {
                this.worldRenderersCheckIndex = (this.worldRenderersCheckIndex + 1) % this.worldRenderers.length;
                final WorldRenderer var8 = this.worldRenderers[this.worldRenderersCheckIndex];
                if (var8.needsUpdate && !this.worldRenderersToUpdate.contains(var8)) {
                    this.worldRenderersToUpdate.add(var8);
                }
            }
        }
        if (this.mc.gameSettings.renderDistance != this.renderDistance && !Config.isLoadChunksFar()) {
            this.loadRenderers();
        }
        if (par2 == 0) {
            this.renderersLoaded = 0;
            this.dummyRenderInt = 0;
            this.renderersBeingClipped = 0;
            this.renderersBeingOccluded = 0;
            this.renderersBeingRendered = 0;
            this.renderersSkippingRenderPass = 0;
        }
        final double var9 = par1EntityLiving.lastTickPosX + (par1EntityLiving.posX - par1EntityLiving.lastTickPosX) * par3;
        final double var10 = par1EntityLiving.lastTickPosY + (par1EntityLiving.posY - par1EntityLiving.lastTickPosY) * par3;
        final double var11 = par1EntityLiving.lastTickPosZ + (par1EntityLiving.posZ - par1EntityLiving.lastTickPosZ) * par3;
        final double var12 = par1EntityLiving.posX - this.prevSortX;
        final double var13 = par1EntityLiving.posY - this.prevSortY;
        final double var14 = par1EntityLiving.posZ - this.prevSortZ;
        final double var15 = var12 * var12 + var13 * var13 + var14 * var14;
        if (var15 > 16.0) {
            this.prevSortX = par1EntityLiving.posX;
            this.prevSortY = par1EntityLiving.posY;
            this.prevSortZ = par1EntityLiving.posZ;
            final int var16 = Config.getPreloadedChunks() * 16;
            final double var17 = par1EntityLiving.posX - this.prevReposX;
            final double var18 = par1EntityLiving.posY - this.prevReposY;
            final double var19 = par1EntityLiving.posZ - this.prevReposZ;
            final double var20 = var17 * var17 + var18 * var18 + var19 * var19;
            if (var20 > var16 * var16 + 16.0) {
                this.prevReposX = par1EntityLiving.posX;
                this.prevReposY = par1EntityLiving.posY;
                this.prevReposZ = par1EntityLiving.posZ;
                this.markRenderersForNewPosition(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ));
            }
            Arrays.sort(this.sortedWorldRenderers, new EntitySorter(par1EntityLiving));
            final int var21 = (int)par1EntityLiving.posX;
            final int var22 = (int)par1EntityLiving.posZ;
            final short var23 = 2000;
            if (Math.abs(var21 - WorldRenderer.globalChunkOffsetX) > var23 || Math.abs(var22 - WorldRenderer.globalChunkOffsetZ) > var23) {
                WorldRenderer.globalChunkOffsetX = var21;
                WorldRenderer.globalChunkOffsetZ = var22;
                this.loadRenderers();
            }
        }
        RenderHelper.disableStandardItemLighting();
        WrUpdates.preRender(this, par1EntityLiving);
        if (this.mc.gameSettings.ofSmoothFps && par2 == 0) {
            GL11.glFinish();
        }
        final byte var24 = 0;
        int var25 = 0;
        int var16;
        if (this.occlusionEnabled && this.mc.gameSettings.advancedOpengl && !this.mc.gameSettings.anaglyph && par2 == 0) {
            final byte var26 = 0;
            final byte var27 = 20;
            this.checkOcclusionQueryResult(var26, var27, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
            for (int var28 = var26; var28 < var27; ++var28) {
                this.sortedWorldRenderers[var28].isVisible = true;
            }
            var5.endStartSection("render");
            var16 = var24 + this.renderSortedRenderers(var26, var27, par2, par3);
            int var28 = var27;
            int var29 = 0;
            final byte var30 = 40;
            final int var21 = this.renderChunksWide;
            while (var28 < this.sortedWorldRenderers.length) {
                var5.endStartSection("occ");
                final int var31 = var28;
                if (var29 < var21) {
                    ++var29;
                }
                else {
                    --var29;
                }
                var28 += var29 * var30;
                if (var28 <= var31) {
                    var28 = var31 + 10;
                }
                if (var28 > this.sortedWorldRenderers.length) {
                    var28 = this.sortedWorldRenderers.length;
                }
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(3008);
                GL11.glDisable(2912);
                GL11.glColorMask(false, false, false, false);
                GL11.glDepthMask(false);
                var5.startSection("check");
                this.checkOcclusionQueryResult(var31, var28, par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
                var5.endSection();
                GL11.glPushMatrix();
                float var32 = 0.0f;
                float var33 = 0.0f;
                float var34 = 0.0f;
                for (int var35 = var31; var35 < var28; ++var35) {
                    final WorldRenderer var36 = this.sortedWorldRenderers[var35];
                    if (var36.skipAllRenderPasses()) {
                        var36.isInFrustum = false;
                    }
                    else if (var36.isUpdating) {
                        var36.isVisible = true;
                    }
                    else if (var36.isInFrustum) {
                        if (Config.isOcclusionFancy() && !var36.isInFrustrumFully) {
                            var36.isVisible = true;
                        }
                        else if (var36.isInFrustum && !var36.isWaitingOnOcclusionQuery) {
                            if (var36.isVisibleFromPosition) {
                                final float var37 = Math.abs((float)(var36.visibleFromX - par1EntityLiving.posX));
                                final float var38 = Math.abs((float)(var36.visibleFromY - par1EntityLiving.posY));
                                final float var39 = Math.abs((float)(var36.visibleFromZ - par1EntityLiving.posZ));
                                final float var40 = var37 + var38 + var39;
                                if (var40 < 10.0 + var35 / 1000.0) {
                                    var36.isVisible = true;
                                    continue;
                                }
                                var36.isVisibleFromPosition = false;
                            }
                            final float var37 = (float)(var36.posXMinus - var9);
                            final float var38 = (float)(var36.posYMinus - var10);
                            final float var39 = (float)(var36.posZMinus - var11);
                            final float var40 = var37 - var32;
                            final float var41 = var38 - var33;
                            final float var42 = var39 - var34;
                            if (var40 != 0.0f || var41 != 0.0f || var42 != 0.0f) {
                                GL11.glTranslatef(var40, var41, var42);
                                var32 += var40;
                                var33 += var41;
                                var34 += var42;
                            }
                            var5.startSection("bb");
                            ARBOcclusionQuery.glBeginQueryARB(35092, var36.glOcclusionQuery);
                            var36.callOcclusionQueryList();
                            ARBOcclusionQuery.glEndQueryARB(35092);
                            var5.endSection();
                            var36.isWaitingOnOcclusionQuery = true;
                            ++var25;
                        }
                    }
                }
                GL11.glPopMatrix();
                if (this.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else {
                        GL11.glColorMask(true, false, false, true);
                    }
                }
                else {
                    GL11.glColorMask(true, true, true, true);
                }
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glEnable(2912);
                var5.endStartSection("render");
                var16 += this.renderSortedRenderers(var31, var28, par2, par3);
            }
        }
        else {
            var5.endStartSection("render");
            var16 = var24 + this.renderSortedRenderers(0, this.sortedWorldRenderers.length, par2, par3);
        }
        var5.endSection();
        WrUpdates.postRender();
        return var16;
    }
    
    private void checkOcclusionQueryResult(final int var1, final int var2, final double var3, final double var5, final double var7) {
        for (int var8 = var1; var8 < var2; ++var8) {
            final WorldRenderer var9 = this.sortedWorldRenderers[var8];
            if (var9.isWaitingOnOcclusionQuery) {
                this.occlusionResult.clear();
                ARBOcclusionQuery.glGetQueryObjectuARB(var9.glOcclusionQuery, 34919, this.occlusionResult);
                if (this.occlusionResult.get(0) != 0) {
                    var9.isWaitingOnOcclusionQuery = false;
                    this.occlusionResult.clear();
                    ARBOcclusionQuery.glGetQueryObjectuARB(var9.glOcclusionQuery, 34918, this.occlusionResult);
                    final boolean var10 = var9.isVisible;
                    var9.isVisible = (this.occlusionResult.get(0) > 0);
                    if (var10 && var9.isVisible) {
                        var9.isVisibleFromPosition = true;
                        var9.visibleFromX = var3;
                        var9.visibleFromY = var5;
                        var9.visibleFromZ = var7;
                    }
                }
            }
        }
    }
    
    private int renderSortedRenderers(final int par1, final int par2, final int par3, final double par4) {
        this.glListBuffer.clear();
        int var6 = 0;
        for (int var7 = par1; var7 < par2; ++var7) {
            final WorldRenderer var8 = this.sortedWorldRenderers[var7];
            if (par3 == 0) {
                ++this.renderersLoaded;
                if (var8.skipRenderPass[par3]) {
                    ++this.renderersSkippingRenderPass;
                }
                else if (!var8.isInFrustum) {
                    ++this.renderersBeingClipped;
                }
                else if (this.occlusionEnabled && !var8.isVisible) {
                    ++this.renderersBeingOccluded;
                }
                else {
                    ++this.renderersBeingRendered;
                }
            }
            if (var8.isInFrustum && !var8.skipRenderPass[par3] && (!this.occlusionEnabled || var8.isVisible)) {
                final int var9 = var8.getGLCallListForPass(par3);
                if (var9 >= 0) {
                    this.glListBuffer.put(var9);
                    ++var6;
                }
            }
        }
        if (var6 == 0) {
            return 0;
        }
        if (Config.isFogOff()) {
            GL11.glDisable(2912);
        }
        this.glListBuffer.flip();
        final EntityLiving var10 = this.mc.renderViewEntity;
        final double var11 = var10.lastTickPosX + (var10.posX - var10.lastTickPosX) * par4 - WorldRenderer.globalChunkOffsetX;
        final double var12 = var10.lastTickPosY + (var10.posY - var10.lastTickPosY) * par4;
        final double var13 = var10.lastTickPosZ + (var10.posZ - var10.lastTickPosZ) * par4 - WorldRenderer.globalChunkOffsetZ;
        this.mc.entityRenderer.enableLightmap(par4);
        GL11.glTranslatef((float)(-var11), (float)(-var12), (float)(-var13));
        GL11.glCallLists(this.glListBuffer);
        GL11.glTranslatef((float)var11, (float)var12, (float)var13);
        this.mc.entityRenderer.disableLightmap(par4);
        return var6;
    }
    
    public void renderAllRenderLists(final int par1, final double par2) {
    }
    
    public void updateClouds() {
        ++this.cloudTickCounter;
        if (this.cloudTickCounter % 20 == 0) {
            final Iterator var1 = this.damagedBlocks.values().iterator();
            while (var1.hasNext()) {
                final DestroyBlockProgress var2 = var1.next();
                final int var3 = var2.getCreationCloudUpdateTick();
                if (this.cloudTickCounter - var3 > 400) {
                    var1.remove();
                }
            }
        }
    }
    
    public void renderSky(final float par1) {
        if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
            final WorldProvider var2 = Minecraft.theWorld.provider;
            final Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);
            if (var3 != null) {
                Reflector.callVoid(var3, Reflector.IRenderHandler_render, par1, this.theWorld, this.mc);
                return;
            }
        }
        if (Minecraft.theWorld.provider.dimensionId == 1) {
            if (!Config.isSkyEnabled()) {
                return;
            }
            GL11.glDisable(2912);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            RenderHelper.disableStandardItemLighting();
            GL11.glDepthMask(false);
            this.renderEngine.bindTexture("/misc/tunnel.png");
            final Tessellator var4 = Tessellator.instance;
            for (int var5 = 0; var5 < 6; ++var5) {
                GL11.glPushMatrix();
                if (var5 == 1) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var5 == 2) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var5 == 3) {
                    GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var5 == 4) {
                    GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                }
                if (var5 == 5) {
                    GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                }
                var4.startDrawingQuads();
                var4.setColorOpaque_I(2631720);
                var4.addVertexWithUV(-100.0, -100.0, -100.0, 0.0, 0.0);
                var4.addVertexWithUV(-100.0, -100.0, 100.0, 0.0, 16.0);
                var4.addVertexWithUV(100.0, -100.0, 100.0, 16.0, 16.0);
                var4.addVertexWithUV(100.0, -100.0, -100.0, 16.0, 0.0);
                var4.draw();
                GL11.glPopMatrix();
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
        }
        else if (Minecraft.theWorld.provider.isSurfaceWorld()) {
            GL11.glDisable(3553);
            Vec3 var6 = this.theWorld.getSkyColor(this.mc.renderViewEntity, par1);
            var6 = CustomColorizer.getSkyColor(var6, Minecraft.theWorld, this.mc.renderViewEntity.posX, this.mc.renderViewEntity.posY + 1.0, this.mc.renderViewEntity.posZ);
            float var7 = (float)var6.xCoord;
            float var8 = (float)var6.yCoord;
            float var9 = (float)var6.zCoord;
            if (this.mc.gameSettings.anaglyph) {
                final float var10 = (var7 * 30.0f + var8 * 59.0f + var9 * 11.0f) / 100.0f;
                final float var11 = (var7 * 30.0f + var8 * 70.0f) / 100.0f;
                final float var12 = (var7 * 30.0f + var9 * 70.0f) / 100.0f;
                var7 = var10;
                var8 = var11;
                var9 = var12;
            }
            GL11.glColor3f(var7, var8, var9);
            final Tessellator var13 = Tessellator.instance;
            GL11.glDepthMask(false);
            GL11.glEnable(2912);
            GL11.glColor3f(var7, var8, var9);
            if (Config.isSkyEnabled()) {
                GL11.glCallList(this.glSkyList);
            }
            GL11.glDisable(2912);
            GL11.glDisable(3008);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            RenderHelper.disableStandardItemLighting();
            final float[] var14 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(par1), par1);
            if (var14 != null && Config.isSunMoonEnabled()) {
                GL11.glDisable(3553);
                GL11.glShadeModel(7425);
                GL11.glPushMatrix();
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef((MathHelper.sin(this.theWorld.getCelestialAngleRadians(par1)) < 0.0f) ? 180.0f : 0.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                float var12 = var14[0];
                float var15 = var14[1];
                float var16 = var14[2];
                if (this.mc.gameSettings.anaglyph) {
                    final float var17 = (var12 * 30.0f + var15 * 59.0f + var16 * 11.0f) / 100.0f;
                    final float var18 = (var12 * 30.0f + var15 * 70.0f) / 100.0f;
                    final float var19 = (var12 * 30.0f + var16 * 70.0f) / 100.0f;
                    var12 = var17;
                    var15 = var18;
                    var16 = var19;
                }
                var13.startDrawing(6);
                var13.setColorRGBA_F(var12, var15, var16, var14[3]);
                var13.addVertex(0.0, 100.0, 0.0);
                final byte var20 = 16;
                var13.setColorRGBA_F(var14[0], var14[1], var14[2], 0.0f);
                for (int var21 = 0; var21 <= var20; ++var21) {
                    final float var19 = var21 * 3.1415927f * 2.0f / var20;
                    final float var22 = MathHelper.sin(var19);
                    final float var23 = MathHelper.cos(var19);
                    var13.addVertex(var22 * 120.0f, var23 * 120.0f, -var23 * 40.0f * var14[3]);
                }
                var13.draw();
                GL11.glPopMatrix();
                GL11.glShadeModel(7424);
            }
            GL11.glEnable(3553);
            GL11.glBlendFunc(770, 1);
            GL11.glPushMatrix();
            float var12 = 1.0f - this.theWorld.getRainStrength(par1);
            float var15 = 0.0f;
            float var16 = 0.0f;
            float var17 = 0.0f;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, var12);
            GL11.glTranslatef(var15, var16, var17);
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(par1), var12);
            GL11.glRotatef(this.theWorld.getCelestialAngle(par1) * 360.0f, 1.0f, 0.0f, 0.0f);
            if (Config.isSunMoonEnabled()) {
                float var18 = 30.0f;
                this.renderEngine.bindTexture("/environment/sun.png");
                var13.startDrawingQuads();
                var13.addVertexWithUV(-var18, 100.0, -var18, 0.0, 0.0);
                var13.addVertexWithUV(var18, 100.0, -var18, 1.0, 0.0);
                var13.addVertexWithUV(var18, 100.0, var18, 1.0, 1.0);
                var13.addVertexWithUV(-var18, 100.0, var18, 0.0, 1.0);
                var13.draw();
                var18 = 20.0f;
                this.renderEngine.bindTexture("/environment/moon_phases.png");
                final int var24 = this.theWorld.getMoonPhase();
                final int var25 = var24 % 4;
                final int var21 = var24 / 4 % 2;
                final float var22 = (var25 + 0) / 4.0f;
                final float var23 = (var21 + 0) / 2.0f;
                final float var26 = (var25 + 1) / 4.0f;
                final float var27 = (var21 + 1) / 2.0f;
                var13.startDrawingQuads();
                var13.addVertexWithUV(-var18, -100.0, var18, var26, var27);
                var13.addVertexWithUV(var18, -100.0, var18, var22, var27);
                var13.addVertexWithUV(var18, -100.0, -var18, var22, var23);
                var13.addVertexWithUV(-var18, -100.0, -var18, var26, var23);
                var13.draw();
            }
            GL11.glDisable(3553);
            final float var19 = this.theWorld.getStarBrightness(par1) * var12;
            if (var19 > 0.0f && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
                GL11.glColor4f(var19, var19, var19, var19);
                GL11.glCallList(this.starGLCallList);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(3008);
            GL11.glEnable(2912);
            GL11.glPopMatrix();
            GL11.glDisable(3553);
            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            final double var28 = Minecraft.thePlayer.getPosition(par1).yCoord - this.theWorld.getHorizon();
            if (var28 < 0.0) {
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 12.0f, 0.0f);
                GL11.glCallList(this.glSkyList2);
                GL11.glPopMatrix();
                var16 = 1.0f;
                var17 = -(float)(var28 + 65.0);
                final float var18 = -var16;
                var13.startDrawingQuads();
                var13.setColorRGBA_I(0, 255);
                var13.addVertex(-var16, var17, var16);
                var13.addVertex(var16, var17, var16);
                var13.addVertex(var16, var18, var16);
                var13.addVertex(-var16, var18, var16);
                var13.addVertex(-var16, var18, -var16);
                var13.addVertex(var16, var18, -var16);
                var13.addVertex(var16, var17, -var16);
                var13.addVertex(-var16, var17, -var16);
                var13.addVertex(var16, var18, -var16);
                var13.addVertex(var16, var18, var16);
                var13.addVertex(var16, var17, var16);
                var13.addVertex(var16, var17, -var16);
                var13.addVertex(-var16, var17, -var16);
                var13.addVertex(-var16, var17, var16);
                var13.addVertex(-var16, var18, var16);
                var13.addVertex(-var16, var18, -var16);
                var13.addVertex(-var16, var18, -var16);
                var13.addVertex(-var16, var18, var16);
                var13.addVertex(var16, var18, var16);
                var13.addVertex(var16, var18, -var16);
                var13.draw();
            }
            if (this.theWorld.provider.isSkyColored()) {
                GL11.glColor3f(var7 * 0.2f + 0.04f, var8 * 0.2f + 0.04f, var9 * 0.6f + 0.1f);
            }
            else {
                GL11.glColor3f(var7, var8, var9);
            }
            if (this.mc.gameSettings.ofRenderDistanceFine <= 64) {
                GL11.glColor3f(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, -(float)(var28 - 16.0), 0.0f);
            if (Config.isSkyEnabled()) {
                GL11.glCallList(this.glSkyList2);
            }
            GL11.glPopMatrix();
            GL11.glEnable(3553);
            GL11.glDepthMask(true);
        }
    }
    
    public void renderClouds(final float par1) {
        if (!Config.isCloudsOff()) {
            if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
                final WorldProvider var2 = Minecraft.theWorld.provider;
                final Object var3 = Reflector.call(var2, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
                if (var3 != null) {
                    Reflector.callVoid(var3, Reflector.IRenderHandler_render, par1, this.theWorld, this.mc);
                    return;
                }
            }
            if (Minecraft.theWorld.provider.isSurfaceWorld()) {
                if (Config.isCloudsFancy()) {
                    this.renderCloudsFancy(par1);
                }
                else {
                    GL11.glDisable(2884);
                    final float var4 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * par1);
                    final byte var5 = 32;
                    final int var6 = 256 / var5;
                    final Tessellator var7 = Tessellator.instance;
                    this.renderEngine.bindTexture("/environment/clouds.png");
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    final Vec3 var8 = this.theWorld.getCloudColour(par1);
                    float var9 = (float)var8.xCoord;
                    float var10 = (float)var8.yCoord;
                    float var11 = (float)var8.zCoord;
                    if (this.mc.gameSettings.anaglyph) {
                        final float var12 = (var9 * 30.0f + var10 * 59.0f + var11 * 11.0f) / 100.0f;
                        final float var13 = (var9 * 30.0f + var10 * 70.0f) / 100.0f;
                        final float var14 = (var9 * 30.0f + var11 * 70.0f) / 100.0f;
                        var9 = var12;
                        var10 = var13;
                        var11 = var14;
                    }
                    final float var12 = 4.8828125E-4f;
                    final double var15 = this.cloudTickCounter + par1;
                    double var16 = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * par1 + var15 * 0.029999999329447746;
                    double var17 = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * par1;
                    final int var18 = MathHelper.floor_double(var16 / 2048.0);
                    final int var19 = MathHelper.floor_double(var17 / 2048.0);
                    var16 -= var18 * 2048;
                    var17 -= var19 * 2048;
                    float var20 = this.theWorld.provider.getCloudHeight() - var4 + 0.33f;
                    var20 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
                    final float var21 = (float)(var16 * var12);
                    final float var22 = (float)(var17 * var12);
                    var7.startDrawingQuads();
                    var7.setColorRGBA_F(var9, var10, var11, 0.8f);
                    for (int var23 = -var5 * var6; var23 < var5 * var6; var23 += var5) {
                        for (int var24 = -var5 * var6; var24 < var5 * var6; var24 += var5) {
                            var7.addVertexWithUV(var23 + 0, var20, var24 + var5, (var23 + 0) * var12 + var21, (var24 + var5) * var12 + var22);
                            var7.addVertexWithUV(var23 + var5, var20, var24 + var5, (var23 + var5) * var12 + var21, (var24 + var5) * var12 + var22);
                            var7.addVertexWithUV(var23 + var5, var20, var24 + 0, (var23 + var5) * var12 + var21, (var24 + 0) * var12 + var22);
                            var7.addVertexWithUV(var23 + 0, var20, var24 + 0, (var23 + 0) * var12 + var21, (var24 + 0) * var12 + var22);
                        }
                    }
                    var7.draw();
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glDisable(3042);
                    GL11.glEnable(2884);
                }
            }
        }
    }
    
    public boolean hasCloudFog(final double par1, final double par3, final double par5, final float par7) {
        return false;
    }
    
    public void renderCloudsFancy(final float par1) {
        GL11.glDisable(2884);
        final float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * par1);
        final Tessellator var3 = Tessellator.instance;
        final float var4 = 12.0f;
        final float var5 = 4.0f;
        final double var6 = this.cloudTickCounter + par1;
        double var7 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * par1 + var6 * 0.029999999329447746) / var4;
        double var8 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * par1) / var4 + 0.33000001311302185;
        float var9 = this.theWorld.provider.getCloudHeight() - var2 + 0.33f;
        var9 += this.mc.gameSettings.ofCloudsHeight * 128.0f;
        final int var10 = MathHelper.floor_double(var7 / 2048.0);
        final int var11 = MathHelper.floor_double(var8 / 2048.0);
        var7 -= var10 * 2048;
        var8 -= var11 * 2048;
        this.renderEngine.bindTexture("/environment/clouds.png");
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final Vec3 var12 = this.theWorld.getCloudColour(par1);
        float var13 = (float)var12.xCoord;
        float var14 = (float)var12.yCoord;
        float var15 = (float)var12.zCoord;
        if (this.mc.gameSettings.anaglyph) {
            final float var16 = (var13 * 30.0f + var14 * 59.0f + var15 * 11.0f) / 100.0f;
            final float var17 = (var13 * 30.0f + var14 * 70.0f) / 100.0f;
            final float var18 = (var13 * 30.0f + var15 * 70.0f) / 100.0f;
            var13 = var16;
            var14 = var17;
            var15 = var18;
        }
        float var16 = (float)(var7 * 0.0);
        float var17 = (float)(var8 * 0.0);
        final float var18 = 0.00390625f;
        var16 = MathHelper.floor_double(var7) * var18;
        var17 = MathHelper.floor_double(var8) * var18;
        final float var19 = (float)(var7 - MathHelper.floor_double(var7));
        final float var20 = (float)(var8 - MathHelper.floor_double(var8));
        final byte var21 = 8;
        final byte var22 = 4;
        final float var23 = 9.765625E-4f;
        GL11.glScalef(var4, 1.0f, var4);
        for (int var24 = 0; var24 < 2; ++var24) {
            if (var24 == 0) {
                GL11.glColorMask(false, false, false, false);
            }
            else if (this.mc.gameSettings.anaglyph) {
                if (EntityRenderer.anaglyphField == 0) {
                    GL11.glColorMask(false, true, true, true);
                }
                else {
                    GL11.glColorMask(true, false, false, true);
                }
            }
            else {
                GL11.glColorMask(true, true, true, true);
            }
            for (int var25 = -var22 + 1; var25 <= var22; ++var25) {
                for (int var26 = -var22 + 1; var26 <= var22; ++var26) {
                    var3.startDrawingQuads();
                    final float var27 = var25 * var21;
                    final float var28 = var26 * var21;
                    final float var29 = var27 - var19;
                    final float var30 = var28 - var20;
                    if (var9 > -var5 - 1.0f) {
                        var3.setColorRGBA_F(var13 * 0.7f, var14 * 0.7f, var15 * 0.7f, 0.8f);
                        var3.setNormal(0.0f, -1.0f, 0.0f);
                        var3.addVertexWithUV(var29 + 0.0f, var9 + 0.0f, var30 + var21, (var27 + 0.0f) * var18 + var16, (var28 + var21) * var18 + var17);
                        var3.addVertexWithUV(var29 + var21, var9 + 0.0f, var30 + var21, (var27 + var21) * var18 + var16, (var28 + var21) * var18 + var17);
                        var3.addVertexWithUV(var29 + var21, var9 + 0.0f, var30 + 0.0f, (var27 + var21) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                        var3.addVertexWithUV(var29 + 0.0f, var9 + 0.0f, var30 + 0.0f, (var27 + 0.0f) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                    }
                    if (var9 <= var5 + 1.0f) {
                        var3.setColorRGBA_F(var13, var14, var15, 0.8f);
                        var3.setNormal(0.0f, 1.0f, 0.0f);
                        var3.addVertexWithUV(var29 + 0.0f, var9 + var5 - var23, var30 + var21, (var27 + 0.0f) * var18 + var16, (var28 + var21) * var18 + var17);
                        var3.addVertexWithUV(var29 + var21, var9 + var5 - var23, var30 + var21, (var27 + var21) * var18 + var16, (var28 + var21) * var18 + var17);
                        var3.addVertexWithUV(var29 + var21, var9 + var5 - var23, var30 + 0.0f, (var27 + var21) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                        var3.addVertexWithUV(var29 + 0.0f, var9 + var5 - var23, var30 + 0.0f, (var27 + 0.0f) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                    }
                    var3.setColorRGBA_F(var13 * 0.9f, var14 * 0.9f, var15 * 0.9f, 0.8f);
                    if (var25 > -1) {
                        var3.setNormal(-1.0f, 0.0f, 0.0f);
                        for (int var31 = 0; var31 < var21; ++var31) {
                            var3.addVertexWithUV(var29 + var31 + 0.0f, var9 + 0.0f, var30 + var21, (var27 + var31 + 0.5f) * var18 + var16, (var28 + var21) * var18 + var17);
                            var3.addVertexWithUV(var29 + var31 + 0.0f, var9 + var5, var30 + var21, (var27 + var31 + 0.5f) * var18 + var16, (var28 + var21) * var18 + var17);
                            var3.addVertexWithUV(var29 + var31 + 0.0f, var9 + var5, var30 + 0.0f, (var27 + var31 + 0.5f) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                            var3.addVertexWithUV(var29 + var31 + 0.0f, var9 + 0.0f, var30 + 0.0f, (var27 + var31 + 0.5f) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                        }
                    }
                    if (var25 <= 1) {
                        var3.setNormal(1.0f, 0.0f, 0.0f);
                        for (int var31 = 0; var31 < var21; ++var31) {
                            var3.addVertexWithUV(var29 + var31 + 1.0f - var23, var9 + 0.0f, var30 + var21, (var27 + var31 + 0.5f) * var18 + var16, (var28 + var21) * var18 + var17);
                            var3.addVertexWithUV(var29 + var31 + 1.0f - var23, var9 + var5, var30 + var21, (var27 + var31 + 0.5f) * var18 + var16, (var28 + var21) * var18 + var17);
                            var3.addVertexWithUV(var29 + var31 + 1.0f - var23, var9 + var5, var30 + 0.0f, (var27 + var31 + 0.5f) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                            var3.addVertexWithUV(var29 + var31 + 1.0f - var23, var9 + 0.0f, var30 + 0.0f, (var27 + var31 + 0.5f) * var18 + var16, (var28 + 0.0f) * var18 + var17);
                        }
                    }
                    var3.setColorRGBA_F(var13 * 0.8f, var14 * 0.8f, var15 * 0.8f, 0.8f);
                    if (var26 > -1) {
                        var3.setNormal(0.0f, 0.0f, -1.0f);
                        for (int var31 = 0; var31 < var21; ++var31) {
                            var3.addVertexWithUV(var29 + 0.0f, var9 + var5, var30 + var31 + 0.0f, (var27 + 0.0f) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                            var3.addVertexWithUV(var29 + var21, var9 + var5, var30 + var31 + 0.0f, (var27 + var21) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                            var3.addVertexWithUV(var29 + var21, var9 + 0.0f, var30 + var31 + 0.0f, (var27 + var21) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                            var3.addVertexWithUV(var29 + 0.0f, var9 + 0.0f, var30 + var31 + 0.0f, (var27 + 0.0f) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                        }
                    }
                    if (var26 <= 1) {
                        var3.setNormal(0.0f, 0.0f, 1.0f);
                        for (int var31 = 0; var31 < var21; ++var31) {
                            var3.addVertexWithUV(var29 + 0.0f, var9 + var5, var30 + var31 + 1.0f - var23, (var27 + 0.0f) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                            var3.addVertexWithUV(var29 + var21, var9 + var5, var30 + var31 + 1.0f - var23, (var27 + var21) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                            var3.addVertexWithUV(var29 + var21, var9 + 0.0f, var30 + var31 + 1.0f - var23, (var27 + var21) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                            var3.addVertexWithUV(var29 + 0.0f, var9 + 0.0f, var30 + var31 + 1.0f - var23, (var27 + 0.0f) * var18 + var16, (var28 + var31 + 0.5f) * var18 + var17);
                        }
                    }
                    var3.draw();
                }
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(2884);
    }
    
    public boolean updateRenderers(final EntityLiving par1EntityLiving, final boolean par2) {
        if (WrUpdates.hasWrUpdater()) {
            return WrUpdates.updateRenderers(this, par1EntityLiving, par2);
        }
        if (this.worldRenderersToUpdate.size() <= 0) {
            return false;
        }
        int var3 = 0;
        int var4 = Config.getUpdatesPerFrame();
        if (Config.isDynamicUpdates() && !this.isMoving(par1EntityLiving)) {
            var4 *= 3;
        }
        final byte var5 = 4;
        int var6 = 0;
        WorldRenderer var7 = null;
        float var8 = Float.MAX_VALUE;
        int var9 = -1;
        for (int var10 = 0; var10 < this.worldRenderersToUpdate.size(); ++var10) {
            final WorldRenderer var11 = (WorldRenderer)this.worldRenderersToUpdate.get(var10);
            if (var11 != null) {
                ++var6;
                if (!var11.needsUpdate) {
                    this.worldRenderersToUpdate.set(var10, null);
                }
                else {
                    float var12 = var11.distanceToEntitySquared(par1EntityLiving);
                    if (var12 <= 256.0f && this.isActingNow()) {
                        var11.updateRenderer();
                        var11.needsUpdate = false;
                        this.worldRenderersToUpdate.set(var10, null);
                        ++var3;
                    }
                    else {
                        if (var12 > 256.0f && var3 >= var4) {
                            break;
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
        if (var7 != null) {
            var7.updateRenderer();
            var7.needsUpdate = false;
            this.worldRenderersToUpdate.set(var9, null);
            ++var3;
            final float var13 = var8 / 5.0f;
            for (int var14 = 0; var14 < this.worldRenderersToUpdate.size() && var3 < var4; ++var14) {
                final WorldRenderer var15 = (WorldRenderer)this.worldRenderersToUpdate.get(var14);
                if (var15 != null) {
                    float var16 = var15.distanceToEntitySquared(par1EntityLiving);
                    if (!var15.isInFrustum) {
                        var16 *= var5;
                    }
                    final float var17 = Math.abs(var16 - var8);
                    if (var17 < var13) {
                        var15.updateRenderer();
                        var15.needsUpdate = false;
                        this.worldRenderersToUpdate.set(var14, null);
                        ++var3;
                    }
                }
            }
        }
        if (var6 == 0) {
            this.worldRenderersToUpdate.clear();
        }
        this.worldRenderersToUpdate.compact();
        return true;
    }
    
    public void drawBlockBreaking(final EntityPlayer par1EntityPlayer, final MovingObjectPosition par2MovingObjectPosition, final int par3, final ItemStack par4ItemStack, final float par5) {
        final Tessellator var6 = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, (MathHelper.sin(Minecraft.getSystemTime() / 100.0f) * 0.2f + 0.4f) * 0.5f);
        if (par3 != 0 && par4ItemStack != null) {
            GL11.glBlendFunc(770, 771);
            final float var7 = MathHelper.sin(Minecraft.getSystemTime() / 100.0f) * 0.2f + 0.8f;
            GL11.glColor4f(var7, var7, var7, MathHelper.sin(Minecraft.getSystemTime() / 200.0f) * 0.2f + 0.5f);
            this.renderEngine.bindTexture("/terrain.png");
        }
        GL11.glDisable(3042);
        GL11.glDisable(3008);
    }
    
    public void drawBlockDamageTexture(final Tessellator par1Tessellator, final EntityPlayer par2EntityPlayer, final float par3) {
        this.drawBlockDamageTexture(par1Tessellator, par2EntityPlayer, par3);
    }
    
    public void drawBlockDamageTexture(final Tessellator var1, final EntityLiving var2, final float var3) {
        final double var4 = var2.lastTickPosX + (var2.posX - var2.lastTickPosX) * var3;
        final double var5 = var2.lastTickPosY + (var2.posY - var2.lastTickPosY) * var3;
        final double var6 = var2.lastTickPosZ + (var2.posZ - var2.lastTickPosZ) * var3;
        if (!this.damagedBlocks.isEmpty()) {
            GL11.glBlendFunc(774, 768);
            this.renderEngine.bindTexture("/terrain.png");
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
            GL11.glPushMatrix();
            GL11.glDisable(3008);
            GL11.glPolygonOffset(-3.0f, -3.0f);
            GL11.glEnable(32823);
            GL11.glEnable(3008);
            var1.startDrawingQuads();
            var1.setTranslation(-var4, -var5, -var6);
            var1.disableColor();
            final Iterator var7 = this.damagedBlocks.values().iterator();
            while (var7.hasNext()) {
                final DestroyBlockProgress var8 = var7.next();
                final double var9 = var8.getPartialBlockX() - var4;
                final double var10 = var8.getPartialBlockY() - var5;
                final double var11 = var8.getPartialBlockZ() - var6;
                if (var9 * var9 + var10 * var10 + var11 * var11 > 1024.0) {
                    var7.remove();
                }
                else {
                    final int var12 = this.theWorld.getBlockId(var8.getPartialBlockX(), var8.getPartialBlockY(), var8.getPartialBlockZ());
                    Block var13 = (var12 > 0) ? Block.blocksList[var12] : null;
                    if (var13 == null) {
                        var13 = Block.stone;
                    }
                    this.globalRenderBlocks.renderBlockUsingTexture(var13, var8.getPartialBlockX(), var8.getPartialBlockY(), var8.getPartialBlockZ(), this.destroyBlockIcons[var8.getPartialBlockDamage()]);
                }
            }
            var1.draw();
            var1.setTranslation(0.0, 0.0, 0.0);
            GL11.glDisable(3008);
            GL11.glPolygonOffset(0.0f, 0.0f);
            GL11.glDisable(32823);
            GL11.glEnable(3008);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
    
    public void drawSelectionBox(final EntityPlayer par1EntityPlayer, final MovingObjectPosition par2MovingObjectPosition, final int par3, final ItemStack par4ItemStack, final float par5) {
        if (par3 == 0 && par2MovingObjectPosition.typeOfHit == EnumMovingObjectType.TILE) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(3553);
            GL11.glDepthMask(false);
            final float var6 = 0.002f;
            final int var7 = this.theWorld.getBlockId(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
            if (var7 > 0) {
                Block.blocksList[var7].setBlockBoundsBasedOnState(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                final double var8 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * par5;
                final double var9 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * par5;
                final double var10 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * par5;
                this.drawOutlinedBoundingBox(Block.blocksList[var7].getSelectedBoundingBoxFromPool(this.theWorld, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand(var6, var6, var6).getOffsetBoundingBox(-var8, -var9, -var10));
            }
            GL11.glDepthMask(true);
            GL11.glEnable(3553);
            GL11.glDisable(3042);
        }
    }
    
    private void drawOutlinedBoundingBox(final AxisAlignedBB par1AxisAlignedBB) {
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawing(3);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.draw();
        var2.startDrawing(3);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.draw();
        var2.startDrawing(1);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
        var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
        var2.draw();
    }
    
    public void markBlocksForUpdate(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final int var7 = MathHelper.bucketInt(par1, 16);
        final int var8 = MathHelper.bucketInt(par2, 16);
        final int var9 = MathHelper.bucketInt(par3, 16);
        final int var10 = MathHelper.bucketInt(par4, 16);
        final int var11 = MathHelper.bucketInt(par5, 16);
        final int var12 = MathHelper.bucketInt(par6, 16);
        for (int var13 = var7; var13 <= var10; ++var13) {
            int var14 = var13 % this.renderChunksWide;
            if (var14 < 0) {
                var14 += this.renderChunksWide;
            }
            for (int var15 = var8; var15 <= var11; ++var15) {
                int var16 = var15 % this.renderChunksTall;
                if (var16 < 0) {
                    var16 += this.renderChunksTall;
                }
                for (int var17 = var9; var17 <= var12; ++var17) {
                    int var18 = var17 % this.renderChunksDeep;
                    if (var18 < 0) {
                        var18 += this.renderChunksDeep;
                    }
                    final int var19 = (var18 * this.renderChunksTall + var16) * this.renderChunksWide + var14;
                    final WorldRenderer var20 = this.worldRenderers[var19];
                    if (var20 != null && !var20.needsUpdate) {
                        this.worldRenderersToUpdate.add(var20);
                        var20.markDirty();
                    }
                }
            }
        }
    }
    
    @Override
    public void markBlockForUpdate(final int par1, final int par2, final int par3) {
        this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
    }
    
    @Override
    public void markBlockForRenderUpdate(final int par1, final int par2, final int par3) {
        this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par1 + 1, par2 + 1, par3 + 1);
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.markBlocksForUpdate(par1 - 1, par2 - 1, par3 - 1, par4 + 1, par5 + 1, par6 + 1);
    }
    
    public void clipRenderersByFrustum(final ICamera par1ICamera, final float par2) {
        for (int var3 = 0; var3 < this.worldRenderers.length; ++var3) {
            if (!this.worldRenderers[var3].skipAllRenderPasses()) {
                this.worldRenderers[var3].updateInFrustum(par1ICamera);
            }
        }
        ++this.frustumCheckOffset;
    }
    
    @Override
    public void playRecord(final String par1Str, final int par2, final int par3, final int par4) {
        final ItemRecord var5 = ItemRecord.getRecord(par1Str);
        if (par1Str != null && var5 != null) {
            this.mc.ingameGUI.setRecordPlayingMessage(var5.getRecordTitle());
        }
        this.mc.sndManager.playStreaming(par1Str, par2, par3, par4);
    }
    
    @Override
    public void playSound(final String par1Str, final double par2, final double par4, final double par6, final float par8, final float par9) {
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer par1EntityPlayer, final String par2Str, final double par3, final double par5, final double par7, final float par9, final float par10) {
    }
    
    @Override
    public void spawnParticle(final String par1Str, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        try {
            this.doSpawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
        }
        catch (Throwable var16) {
            final CrashReport var15 = CrashReport.makeCrashReport(var16, "Exception while adding particle");
            final CrashReportCategory var17 = var15.makeCategory("Particle being added");
            var17.addCrashSection("Name", par1Str);
            var17.addCrashSectionCallable("Position", new CallableParticlePositionInfo(this, par2, par4, par6));
            throw new ReportedException(var15);
        }
    }
    
    public EntityFX doSpawnParticle(final String par1Str, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        if (this.mc == null || this.mc.renderViewEntity == null || this.mc.effectRenderer == null) {
            return null;
        }
        int var14 = this.mc.gameSettings.particleSetting;
        if (var14 == 1 && this.theWorld.rand.nextInt(3) == 0) {
            var14 = 2;
        }
        final double var15 = this.mc.renderViewEntity.posX - par2;
        final double var16 = this.mc.renderViewEntity.posY - par4;
        final double var17 = this.mc.renderViewEntity.posZ - par6;
        EntityFX var18 = null;
        if (par1Str.equals("hugeexplosion")) {
            if (Config.isAnimatedExplosion()) {
                this.mc.effectRenderer.addEffect(var18 = new EntityHugeExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12));
            }
        }
        else if (par1Str.equals("largeexplode")) {
            if (Config.isAnimatedExplosion()) {
                this.mc.effectRenderer.addEffect(var18 = new EntityLargeExplodeFX(this.renderEngine, this.theWorld, par2, par4, par6, par8, par10, par12));
            }
        }
        else if (par1Str.equals("fireworksSpark")) {
            this.mc.effectRenderer.addEffect(var18 = new EntityFireworkSparkFX(this.theWorld, par2, par4, par6, par8, par10, par12, this.mc.effectRenderer));
        }
        if (var18 != null) {
            return var18;
        }
        double var19 = 16.0;
        final double var20 = 16.0;
        if (par1Str.equals("crit")) {
            var19 = 196.0;
        }
        if (var15 * var15 + var16 * var16 + var17 * var17 > var19 * var19) {
            return null;
        }
        if (var14 > 1) {
            return null;
        }
        if (par1Str.equals("bubble")) {
            var18 = new EntityBubbleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateWaterFX(var18, this.theWorld);
        }
        else if (par1Str.equals("suspended")) {
            if (Config.isWaterParticles()) {
                var18 = new EntitySuspendFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("depthsuspend")) {
            if (Config.isVoidParticles()) {
                var18 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("townaura")) {
            var18 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateMyceliumFX(var18);
        }
        else if (par1Str.equals("crit")) {
            var18 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("magicCrit")) {
            var18 = new EntityCritFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            var18.setRBGColorF(var18.getRedColorF() * 0.3f, var18.getGreenColorF() * 0.8f, var18.getBlueColorF());
            var18.nextTextureIndexX();
        }
        else if (par1Str.equals("smoke")) {
            if (Config.isAnimatedSmoke()) {
                var18 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("mobSpell")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0, 0.0, 0.0);
                var18.setRBGColorF((float)par8, (float)par10, (float)par12);
            }
        }
        else if (par1Str.equals("mobSpellAmbient")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, 0.0, 0.0, 0.0);
                var18.setAlphaF(0.15f);
                var18.setRBGColorF((float)par8, (float)par10, (float)par12);
            }
        }
        else if (par1Str.equals("spell")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("instantSpell")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                ((EntitySpellParticleFX)var18).setBaseSpellTextureIndex(144);
            }
        }
        else if (par1Str.equals("witchMagic")) {
            if (Config.isPotionParticles()) {
                var18 = new EntitySpellParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                ((EntitySpellParticleFX)var18).setBaseSpellTextureIndex(144);
                final float var21 = this.theWorld.rand.nextFloat() * 0.5f + 0.35f;
                var18.setRBGColorF(1.0f * var21, 0.0f * var21, 1.0f * var21);
            }
        }
        else if (par1Str.equals("note")) {
            var18 = new EntityNoteFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("portal")) {
            if (Config.isPortalParticles()) {
                var18 = new EntityPortalFX(this.theWorld, par2, par4, par6, par8, par10, par12);
                CustomColorizer.updatePortalFX(var18);
            }
        }
        else if (par1Str.equals("enchantmenttable")) {
            var18 = new EntityEnchantmentTableParticleFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("explode")) {
            if (Config.isAnimatedExplosion()) {
                var18 = new EntityExplodeFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("flame")) {
            if (Config.isAnimatedFlame()) {
                var18 = new EntityFlameFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            }
        }
        else if (par1Str.equals("lava")) {
            var18 = new EntityLavaFX(this.theWorld, par2, par4, par6);
        }
        else if (par1Str.equals("footstep")) {
            var18 = new EntityFootStepFX(this.renderEngine, this.theWorld, par2, par4, par6);
        }
        else if (par1Str.equals("splash")) {
            var18 = new EntitySplashFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            CustomColorizer.updateWaterFX(var18, this.theWorld);
        }
        else if (par1Str.equals("largesmoke")) {
            if (Config.isAnimatedSmoke()) {
                var18 = new EntitySmokeFX(this.theWorld, par2, par4, par6, par8, par10, par12, 2.5f);
            }
        }
        else if (par1Str.equals("cloud")) {
            var18 = new EntityCloudFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("reddust")) {
            if (Config.isAnimatedRedstone()) {
                var18 = new EntityReddustFX(this.theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
                CustomColorizer.updateReddustFX(var18, this.theWorld, var15, var16, var17);
            }
        }
        else if (par1Str.equals("snowballpoof")) {
            var18 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.snowball, this.renderEngine);
        }
        else if (par1Str.equals("dripWater")) {
            if (Config.isDrippingWaterLava()) {
                var18 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.water);
            }
        }
        else if (par1Str.equals("dripLava")) {
            if (Config.isDrippingWaterLava()) {
                var18 = new EntityDropParticleFX(this.theWorld, par2, par4, par6, Material.lava);
            }
        }
        else if (par1Str.equals("snowshovel")) {
            var18 = new EntitySnowShovelFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("slime")) {
            var18 = new EntityBreakingFX(this.theWorld, par2, par4, par6, Item.slimeBall, this.renderEngine);
        }
        else if (par1Str.equals("heart")) {
            var18 = new EntityHeartFX(this.theWorld, par2, par4, par6, par8, par10, par12);
        }
        else if (par1Str.equals("angryVillager")) {
            var18 = new EntityHeartFX(this.theWorld, par2, par4 + 0.5, par6, par8, par10, par12);
            var18.setParticleTextureIndex(81);
            var18.setRBGColorF(1.0f, 1.0f, 1.0f);
        }
        else if (par1Str.equals("happyVillager")) {
            var18 = new EntityAuraFX(this.theWorld, par2, par4, par6, par8, par10, par12);
            var18.setParticleTextureIndex(82);
            var18.setRBGColorF(1.0f, 1.0f, 1.0f);
        }
        else if (par1Str.startsWith("iconcrack_")) {
            final int var22 = Integer.parseInt(par1Str.substring(par1Str.indexOf("_") + 1));
            var18 = new EntityBreakingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Item.itemsList[var22], this.renderEngine);
        }
        else if (par1Str.startsWith("tilecrack_")) {
            final String[] var23 = par1Str.split("_", 3);
            final int var24 = Integer.parseInt(var23[1]);
            final int var25 = Integer.parseInt(var23[2]);
            var18 = new EntityDiggingFX(this.theWorld, par2, par4, par6, par8, par10, par12, Block.blocksList[var24], 0, var25, this.renderEngine).applyRenderColor(var25);
        }
        if (var18 != null) {
            this.mc.effectRenderer.addEffect(var18);
        }
        return var18;
    }
    
    @Override
    public void onEntityCreate(final Entity par1Entity) {
        par1Entity.updateCloak();
        if (par1Entity.skinUrl != null) {
            this.renderEngine.obtainImageData(par1Entity.skinUrl, new ImageBufferDownload());
        }
        if (par1Entity.cloakUrl != null) {
            this.renderEngine.obtainImageData(par1Entity.cloakUrl, new ImageBufferDownload());
            if (par1Entity instanceof EntityPlayer) {
                final EntityPlayer var2 = (EntityPlayer)par1Entity;
                final ThreadDownloadImageData var3 = this.renderEngine.obtainImageData(var2.cloakUrl, new ImageBufferDownload());
                this.renderEngine.releaseImageData(var2.cloakUrl);
                final String var4 = "http://s.optifine.net/capes/" + StringUtils.stripControlCodes(var2.username) + ".png";
                final ThreadDownloadImage var5 = new ThreadDownloadImage(var3, var4, new ImageBufferDownload());
                var5.start();
                if (!Config.isShowCapes()) {
                    var2.cloakUrl = "";
                }
            }
        }
        if (Config.isRandomMobs()) {
            RandomMobs.entityLoaded(par1Entity);
        }
    }
    
    @Override
    public void onEntityDestroy(final Entity par1Entity) {
        if (par1Entity.skinUrl != null) {
            this.renderEngine.releaseImageData(par1Entity.skinUrl);
        }
        if (par1Entity.cloakUrl != null) {
            this.renderEngine.releaseImageData(par1Entity.cloakUrl);
        }
    }
    
    public void deleteAllDisplayLists() {
        GLAllocation.deleteDisplayLists(this.glRenderListBase);
    }
    
    @Override
    public void broadcastSound(final int par1, final int par2, final int par3, final int par4, final int par5) {
        final Random var6 = this.theWorld.rand;
        switch (par1) {
            case 1013:
            case 1018: {
                if (this.mc.renderViewEntity == null) {
                    break;
                }
                final double var7 = par2 - this.mc.renderViewEntity.posX;
                final double var8 = par3 - this.mc.renderViewEntity.posY;
                final double var9 = par4 - this.mc.renderViewEntity.posZ;
                final double var10 = Math.sqrt(var7 * var7 + var8 * var8 + var9 * var9);
                double var11 = this.mc.renderViewEntity.posX;
                double var12 = this.mc.renderViewEntity.posY;
                double var13 = this.mc.renderViewEntity.posZ;
                if (var10 > 0.0) {
                    var11 += var7 / var10 * 2.0;
                    var12 += var8 / var10 * 2.0;
                    var13 += var9 / var10 * 2.0;
                }
                if (par1 == 1013) {
                    this.theWorld.playSound(var11, var12, var13, "mob.wither.spawn", 1.0f, 1.0f, false);
                    break;
                }
                if (par1 == 1018) {
                    this.theWorld.playSound(var11, var12, var13, "mob.enderdragon.end", 5.0f, 1.0f, false);
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void playAuxSFX(final EntityPlayer par1EntityPlayer, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final Random var7 = this.theWorld.rand;
        switch (par2) {
            case 1000: {
                this.theWorld.playSound(par3, par4, par5, "random.click", 1.0f, 1.0f, false);
                break;
            }
            case 1001: {
                this.theWorld.playSound(par3, par4, par5, "random.click", 1.0f, 1.2f, false);
                break;
            }
            case 1002: {
                this.theWorld.playSound(par3, par4, par5, "random.bow", 1.0f, 1.2f, false);
                break;
            }
            case 1003: {
                if (Math.random() < 0.5) {
                    this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "random.door_open", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                    break;
                }
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "random.door_close", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1004: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.fizz", 0.5f, 2.6f + (var7.nextFloat() - var7.nextFloat()) * 0.8f, false);
                break;
            }
            case 1005: {
                if (Item.itemsList[par6] instanceof ItemRecord) {
                    this.theWorld.playRecord(((ItemRecord)Item.itemsList[par6]).recordName, par3, par4, par5);
                    break;
                }
                this.theWorld.playRecord(null, par3, par4, par5);
                break;
            }
            case 1007: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.ghast.charge", 10.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1008: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.ghast.fireball", 10.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1009: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.ghast.fireball", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1010: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.wood", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1011: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.metal", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1012: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.woodbreak", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1014: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.wither.shoot", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1015: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.bat.takeoff", 0.05f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1016: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.infect", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1017: {
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "mob.zombie.unfect", 2.0f, (var7.nextFloat() - var7.nextFloat()) * 0.2f + 1.0f, false);
                break;
            }
            case 1020: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.anvil_break", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1021: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.anvil_use", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 1022: {
                this.theWorld.playSound(par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, "random.anvil_land", 0.3f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2000: {
                final int var8 = par6 % 3 - 1;
                final int var9 = par6 / 3 % 3 - 1;
                final double var10 = par3 + var8 * 0.6 + 0.5;
                final double var11 = par4 + 0.5;
                final double var12 = par5 + var9 * 0.6 + 0.5;
                for (int var13 = 0; var13 < 10; ++var13) {
                    final double var14 = var7.nextDouble() * 0.2 + 0.01;
                    final double var15 = var10 + var8 * 0.01 + (var7.nextDouble() - 0.5) * var9 * 0.5;
                    final double var16 = var11 + (var7.nextDouble() - 0.5) * 0.5;
                    final double var17 = var12 + var9 * 0.01 + (var7.nextDouble() - 0.5) * var8 * 0.5;
                    final double var18 = var8 * var14 + var7.nextGaussian() * 0.01;
                    final double var19 = -0.03 + var7.nextGaussian() * 0.01;
                    final double var20 = var9 * var14 + var7.nextGaussian() * 0.01;
                    this.spawnParticle("smoke", var15, var16, var17, var18, var19, var20);
                }
            }
            case 2001: {
                final int var21 = par6 & 0xFFF;
                if (var21 > 0) {
                    final Block var22 = Block.blocksList[var21];
                    this.mc.sndManager.playSound(var22.stepSound.getBreakSound(), par3 + 0.5f, par4 + 0.5f, par5 + 0.5f, (var22.stepSound.getVolume() + 1.0f) / 2.0f, var22.stepSound.getPitch() * 0.8f);
                }
                this.mc.effectRenderer.addBlockDestroyEffects(par3, par4, par5, par6 & 0xFFF, par6 >> 12 & 0xFF);
                break;
            }
            case 2002: {
                final double var23 = par3;
                final double var10 = par4;
                final double var11 = par5;
                final String var24 = "iconcrack_" + Item.potion.itemID;
                for (int var25 = 0; var25 < 8; ++var25) {
                    this.spawnParticle(var24, var23, var10, var11, var7.nextGaussian() * 0.15, var7.nextDouble() * 0.2, var7.nextGaussian() * 0.15);
                }
                int var25 = Item.potion.getColorFromDamage(par6);
                final float var26 = (var25 >> 16 & 0xFF) / 255.0f;
                final float var27 = (var25 >> 8 & 0xFF) / 255.0f;
                final float var28 = (var25 >> 0 & 0xFF) / 255.0f;
                String var29 = "spell";
                if (Item.potion.isEffectInstant(par6)) {
                    var29 = "instantSpell";
                }
                for (int var21 = 0; var21 < 100; ++var21) {
                    final double var16 = var7.nextDouble() * 4.0;
                    final double var17 = var7.nextDouble() * 3.141592653589793 * 2.0;
                    final double var18 = Math.cos(var17) * var16;
                    final double var19 = 0.01 + var7.nextDouble() * 0.5;
                    final double var20 = Math.sin(var17) * var16;
                    final EntityFX var30 = this.doSpawnParticle(var29, var23 + var18 * 0.1, var10 + 0.3, var11 + var20 * 0.1, var18, var19, var20);
                    if (var30 != null) {
                        final float var31 = 0.75f + var7.nextFloat() * 0.25f;
                        var30.setRBGColorF(var26 * var31, var27 * var31, var28 * var31);
                        var30.multiplyVelocity((float)var16);
                    }
                }
                this.theWorld.playSound(par3 + 0.5, par4 + 0.5, par5 + 0.5, "random.glass", 1.0f, this.theWorld.rand.nextFloat() * 0.1f + 0.9f, false);
                break;
            }
            case 2003: {
                final double var23 = par3 + 0.5;
                final double var10 = par4;
                final double var11 = par5 + 0.5;
                final String var24 = "iconcrack_" + Item.eyeOfEnder.itemID;
                for (int var25 = 0; var25 < 8; ++var25) {
                    this.spawnParticle(var24, var23, var10, var11, var7.nextGaussian() * 0.15, var7.nextDouble() * 0.2, var7.nextGaussian() * 0.15);
                }
                for (double var32 = 0.0; var32 < 6.283185307179586; var32 += 0.15707963267948966) {
                    this.spawnParticle("portal", var23 + Math.cos(var32) * 5.0, var10 - 0.4, var11 + Math.sin(var32) * 5.0, Math.cos(var32) * -5.0, 0.0, Math.sin(var32) * -5.0);
                    this.spawnParticle("portal", var23 + Math.cos(var32) * 5.0, var10 - 0.4, var11 + Math.sin(var32) * 5.0, Math.cos(var32) * -7.0, 0.0, Math.sin(var32) * -7.0);
                }
            }
            case 2004: {
                for (int var33 = 0; var33 < 20; ++var33) {
                    final double var34 = par3 + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double var35 = par4 + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    final double var36 = par5 + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle("smoke", var34, var35, var36, 0.0, 0.0, 0.0);
                    this.theWorld.spawnParticle("flame", var34, var35, var36, 0.0, 0.0, 0.0);
                }
            }
            case 2005: {
                ItemDye.func_96603_a(this.theWorld, par3, par4, par5, par6);
                break;
            }
        }
    }
    
    @Override
    public void destroyBlockPartially(final int par1, final int par2, final int par3, final int par4, final int par5) {
        if (par5 >= 0 && par5 < 10) {
            DestroyBlockProgress var6 = this.damagedBlocks.get(par1);
            if (var6 == null || var6.getPartialBlockX() != par2 || var6.getPartialBlockY() != par3 || var6.getPartialBlockZ() != par4) {
                var6 = new DestroyBlockProgress(par1, par2, par3, par4);
                this.damagedBlocks.put(par1, var6);
            }
            var6.setPartialBlockDamage(par5);
            var6.setCloudUpdateTick(this.cloudTickCounter);
        }
        else {
            this.damagedBlocks.remove(par1);
        }
    }
    
    public void registerDestroyBlockIcons(final IconRegister par1IconRegister) {
        this.destroyBlockIcons = new Icon[10];
        for (int var2 = 0; var2 < this.destroyBlockIcons.length; ++var2) {
            this.destroyBlockIcons[var2] = par1IconRegister.registerIcon("destroy_" + var2);
        }
    }
    
    public void setAllRenderersVisible() {
        if (this.worldRenderers != null) {
            for (int var1 = 0; var1 < this.worldRenderers.length; ++var1) {
                this.worldRenderers[var1].isVisible = true;
            }
        }
    }
    
    public boolean isMoving(final EntityLiving var1) {
        final boolean var2 = this.isMovingNow(var1);
        if (var2) {
            this.lastMovedTime = System.currentTimeMillis();
            return true;
        }
        return System.currentTimeMillis() - this.lastMovedTime < 2000L;
    }
    
    private boolean isMovingNow(final EntityLiving var1) {
        final double var2 = 0.001;
        return var1.isJumping || var1.isSneaking() || var1.prevSwingProgress > var2 || this.mc.mouseHelper.deltaX != 0 || this.mc.mouseHelper.deltaY != 0 || Math.abs(var1.posX - var1.prevPosX) > var2 || Math.abs(var1.posY - var1.prevPosY) > var2 || Math.abs(var1.posZ - var1.prevPosZ) > var2;
    }
    
    public boolean isActing() {
        final boolean var1 = this.isActingNow();
        if (var1) {
            this.lastActionTime = System.currentTimeMillis();
            return true;
        }
        return System.currentTimeMillis() - this.lastActionTime < 500L;
    }
    
    public boolean isActingNow() {
        return Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
    }
    
    public int renderAllSortedRenderers(final int var1, final double var2) {
        return this.renderSortedRenderers(0, this.sortedWorldRenderers.length, var1, var2);
    }
    
    public void updateCapes() {
        if (this.theWorld != null) {
            final boolean var1 = Config.isShowCapes();
            final List var2 = this.theWorld.playerEntities;
            for (int var3 = 0; var3 < var2.size(); ++var3) {
                final Entity var4 = var2.get(var3);
                if (var4 instanceof EntityPlayer) {
                    final EntityPlayer var5 = (EntityPlayer)var4;
                    if (var1) {
                        var5.cloakUrl = var5.cloakUrl;
                    }
                    else {
                        var5.cloakUrl = "";
                    }
                }
            }
        }
    }
}
