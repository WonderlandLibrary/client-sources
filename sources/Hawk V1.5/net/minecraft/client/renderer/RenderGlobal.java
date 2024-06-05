package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import optifine.ChunkUtils;
import optifine.CloudRenderer;
import optifine.Config;
import optifine.CustomColors;
import optifine.CustomSky;
import optifine.DynamicLights;
import optifine.Lagometer;
import optifine.RandomMobs;
import optifine.Reflector;
import optifine.RenderInfoLazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {
   public boolean displayListEntitiesDirty = true;
   private final Vector4f[] field_175004_V = new Vector4f[8];
   private double field_174987_D = Double.MIN_VALUE;
   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
   private int countEntitiesHidden;
   private double prevRenderSortX;
   private final RenderManager field_175010_j;
   private VertexFormat field_175014_r;
   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
   private int starGLCallList = -1;
   private double prevRenderSortZ;
   private Set field_175009_l = Sets.newLinkedHashSet();
   private final ChunkRenderDispatcher field_174995_M = new ChunkRenderDispatcher();
   private Framebuffer field_175015_z;
   private int renderDistance = 0;
   private final Vector3d field_175003_W = new Vector3d();
   private ChunkRenderContainer field_174996_N;
   private ClippingHelper field_175001_U;
   private CloudRenderer cloudRenderer;
   private boolean field_175005_X = false;
   private int renderEntitiesStartupCounter = 2;
   public final Map damagedBlocks = Maps.newHashMap();
   IRenderChunkFactory field_175007_a;
   private boolean field_175002_T = false;
   private int renderDistanceSq = 0;
   public final Minecraft mc;
   private int field_174988_E = Integer.MIN_VALUE;
   private int cloudTickCounter;
   private List renderInfosTileEntities = new ArrayList(1024);
   private int countEntitiesTotal;
   private List renderInfosShadow = new ArrayList(1024);
   private int glSkyList = -1;
   private double field_175000_K = Double.MIN_VALUE;
   private double field_174992_B = Double.MIN_VALUE;
   private Deque visibilityDeque = new ArrayDeque();
   private int field_174990_G = Integer.MIN_VALUE;
   private List renderInfosEntitiesNormal = new ArrayList(1024);
   private double field_174993_C = Double.MIN_VALUE;
   private final TextureManager renderEngine;
   private int renderDistanceChunks = -1;
   private int countEntitiesRendered;
   private WorldClient theWorld;
   private double field_174994_L = Double.MIN_VALUE;
   private static final Logger logger = LogManager.getLogger();
   private List renderInfosTileEntitiesNormal = new ArrayList(1024);
   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation field_175006_g = new ResourceLocation("textures/misc/forcefield.png");
   private ViewFrustum field_175008_n;
   private double field_174997_H = Double.MIN_VALUE;
   public Set chunksToResortTransparency = new LinkedHashSet();
   private VertexBuffer field_175013_s;
   private double field_174999_J = Double.MIN_VALUE;
   private VertexBuffer field_175012_t;
   private double prevRenderSortY;
   private static final String __OBFID = "CL_00000954";
   private int glSkyList2 = -1;
   private static final Set SET_ALL_FACINGS;
   private int field_174989_F = Integer.MIN_VALUE;
   public Set chunksToUpdateForced = new LinkedHashSet();
   public Entity renderedEntity;
   private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
   private VertexBuffer field_175011_u;
   private int countTileEntitiesRendered;
   private ShaderGroup field_174991_A;
   private List renderInfosTileEntitiesShadow = new ArrayList(1024);
   private List glRenderLists = Lists.newArrayListWithCapacity(69696);
   private List renderInfosEntitiesShadow = new ArrayList(1024);
   private List renderInfosEntities = new ArrayList(1024);
   private List renderInfosNormal = new ArrayList(1024);
   private final Map mapSoundPositions = Maps.newHashMap();
   private double field_174998_I = Double.MIN_VALUE;
   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");

   public WorldClient getWorld() {
      return this.theWorld;
   }

   public void func_174981_a(Tessellator var1, WorldRenderer var2, Entity var3, float var4) {
      double var5 = var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * (double)var4;
      double var7 = var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)var4;
      double var9 = var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * (double)var4;
      if (!this.damagedBlocks.isEmpty()) {
         this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
         this.func_180443_s();
         var2.startDrawingQuads();
         var2.setVertexFormat(DefaultVertexFormats.field_176600_a);
         var2.setTranslation(-var5, -var7, -var9);
         var2.markDirty();
         Iterator var11 = this.damagedBlocks.values().iterator();

         while(var11.hasNext()) {
            DestroyBlockProgress var12 = (DestroyBlockProgress)var11.next();
            BlockPos var13 = var12.func_180246_b();
            double var14 = (double)var13.getX() - var5;
            double var16 = (double)var13.getY() - var7;
            double var18 = (double)var13.getZ() - var9;
            Block var20 = this.theWorld.getBlockState(var13).getBlock();
            boolean var21;
            if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
               boolean var22 = var20 instanceof BlockChest || var20 instanceof BlockEnderChest || var20 instanceof BlockSign || var20 instanceof BlockSkull;
               if (!var22) {
                  TileEntity var23 = this.theWorld.getTileEntity(var13);
                  if (var23 != null) {
                     var22 = Reflector.callBoolean(var23, Reflector.ForgeTileEntity_canRenderBreaking);
                  }
               }

               var21 = !var22;
            } else {
               var21 = !(var20 instanceof BlockChest) && !(var20 instanceof BlockEnderChest) && !(var20 instanceof BlockSign) && !(var20 instanceof BlockSkull);
            }

            if (var21) {
               if (var14 * var14 + var16 * var16 + var18 * var18 > 1024.0D) {
                  var11.remove();
               } else {
                  IBlockState var26 = this.theWorld.getBlockState(var13);
                  if (var26.getBlock().getMaterial() != Material.air) {
                     int var27 = var12.getPartialBlockDamage();
                     TextureAtlasSprite var24 = this.destroyBlockIcons[var27];
                     BlockRendererDispatcher var25 = this.mc.getBlockRendererDispatcher();
                     var25.func_175020_a(var26, var13, var24, this.theWorld);
                  }
               }
            }
         }

         var1.draw();
         var2.setTranslation(0.0D, 0.0D, 0.0D);
         this.func_174969_t();
      }

   }

   protected boolean func_174985_d() {
      return !Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing() ? this.field_175015_z != null && this.field_174991_A != null && this.mc.thePlayer != null && this.mc.thePlayer.func_175149_v() && this.mc.gameSettings.field_178883_an.getIsKeyPressed() : false;
   }

   public void func_180447_b(float var1, int var2) {
      if (!Config.isCloudsOff()) {
         if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
            WorldProvider var3 = this.mc.theWorld.provider;
            Object var4 = Reflector.call(var3, Reflector.ForgeWorldProvider_getCloudRenderer);
            if (var4 != null) {
               Reflector.callVoid(var4, Reflector.IRenderHandler_render, var1, this.theWorld, this.mc);
               return;
            }
         }

         if (this.mc.theWorld.provider.isSurfaceWorld()) {
            if (Config.isShaders()) {
               Shaders.beginClouds();
            }

            if (Config.isCloudsFancy()) {
               this.func_180445_c(var1, var2);
            } else {
               this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, var1);
               var1 = 0.0F;
               GlStateManager.disableCull();
               float var26 = (float)(this.mc.func_175606_aa().lastTickPosY + (this.mc.func_175606_aa().posY - this.mc.func_175606_aa().lastTickPosY) * (double)var1);
               boolean var27 = true;
               boolean var5 = true;
               Tessellator var6 = Tessellator.getInstance();
               WorldRenderer var7 = var6.getWorldRenderer();
               this.renderEngine.bindTexture(locationCloudsPng);
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               if (this.cloudRenderer.shouldUpdateGlList()) {
                  this.cloudRenderer.startUpdateGlList();
                  Vec3 var8 = this.theWorld.getCloudColour(var1);
                  float var9 = (float)var8.xCoord;
                  float var10 = (float)var8.yCoord;
                  float var11 = (float)var8.zCoord;
                  float var12;
                  if (var2 != 2) {
                     var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
                     float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
                     float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
                     var9 = var12;
                     var10 = var13;
                     var11 = var14;
                  }

                  var12 = 4.8828125E-4F;
                  double var28 = (double)((float)this.cloudTickCounter + var1);
                  double var15 = this.mc.func_175606_aa().prevPosX + (this.mc.func_175606_aa().posX - this.mc.func_175606_aa().prevPosX) * (double)var1 + var28 * 0.029999999329447746D;
                  double var17 = this.mc.func_175606_aa().prevPosZ + (this.mc.func_175606_aa().posZ - this.mc.func_175606_aa().prevPosZ) * (double)var1;
                  int var19 = MathHelper.floor_double(var15 / 2048.0D);
                  int var20 = MathHelper.floor_double(var17 / 2048.0D);
                  var15 -= (double)(var19 * 2048);
                  var17 -= (double)(var20 * 2048);
                  float var21 = this.theWorld.provider.getCloudHeight() - var26 + 0.33F;
                  var21 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
                  float var22 = (float)(var15 * 4.8828125E-4D);
                  float var23 = (float)(var17 * 4.8828125E-4D);
                  var7.startDrawingQuads();
                  var7.func_178960_a(var9, var10, var11, 0.8F);

                  for(int var24 = -256; var24 < 256; var24 += 32) {
                     for(int var25 = -256; var25 < 256; var25 += 32) {
                        var7.addVertexWithUV((double)var24, (double)var21, (double)(var25 + 32), (double)((float)var24 * 4.8828125E-4F + var22), (double)((float)(var25 + 32) * 4.8828125E-4F + var23));
                        var7.addVertexWithUV((double)(var24 + 32), (double)var21, (double)(var25 + 32), (double)((float)(var24 + 32) * 4.8828125E-4F + var22), (double)((float)(var25 + 32) * 4.8828125E-4F + var23));
                        var7.addVertexWithUV((double)(var24 + 32), (double)var21, (double)var25, (double)((float)(var24 + 32) * 4.8828125E-4F + var22), (double)((float)var25 * 4.8828125E-4F + var23));
                        var7.addVertexWithUV((double)var24, (double)var21, (double)var25, (double)((float)var24 * 4.8828125E-4F + var22), (double)((float)var25 * 4.8828125E-4F + var23));
                     }
                  }

                  var6.draw();
                  this.cloudRenderer.endUpdateGlList();
               }

               this.cloudRenderer.renderGlList();
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.disableBlend();
               GlStateManager.enableCull();
            }

            if (Config.isShaders()) {
               Shaders.endClouds();
            }
         }
      }

   }

   private void func_174980_p() {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      if (this.field_175012_t != null) {
         this.field_175012_t.func_177362_c();
      }

      if (this.glSkyList >= 0) {
         GLAllocation.deleteDisplayLists(this.glSkyList);
         this.glSkyList = -1;
      }

      if (this.field_175005_X) {
         this.field_175012_t = new VertexBuffer(this.field_175014_r);
         this.func_174968_a(var2, 16.0F, false);
         var2.draw();
         var2.reset();
         this.field_175012_t.func_177360_a(var2.func_178966_f(), var2.func_178976_e());
      } else {
         this.glSkyList = GLAllocation.generateDisplayLists(1);
         GL11.glNewList(this.glSkyList, 4864);
         this.func_174968_a(var2, 16.0F, false);
         var1.draw();
         GL11.glEndList();
      }

   }

   private void func_180444_a(WorldRenderer var1) {
      Random var2 = new Random(10842L);
      var1.startDrawingQuads();

      for(int var3 = 0; var3 < 1500; ++var3) {
         double var4 = (double)(var2.nextFloat() * 2.0F - 1.0F);
         double var6 = (double)(var2.nextFloat() * 2.0F - 1.0F);
         double var8 = (double)(var2.nextFloat() * 2.0F - 1.0F);
         double var10 = (double)(0.15F + var2.nextFloat() * 0.1F);
         double var12 = var4 * var4 + var6 * var6 + var8 * var8;
         if (var12 < 1.0D && var12 > 0.01D) {
            var12 = 1.0D / Math.sqrt(var12);
            var4 *= var12;
            var6 *= var12;
            var8 *= var12;
            double var14 = var4 * 100.0D;
            double var16 = var6 * 100.0D;
            double var18 = var8 * 100.0D;
            double var20 = Math.atan2(var4, var8);
            double var22 = Math.sin(var20);
            double var24 = Math.cos(var20);
            double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
            double var28 = Math.sin(var26);
            double var30 = Math.cos(var26);
            double var32 = var2.nextDouble() * 3.141592653589793D * 2.0D;
            double var34 = Math.sin(var32);
            double var36 = Math.cos(var32);

            for(int var38 = 0; var38 < 4; ++var38) {
               double var39 = 0.0D;
               double var41 = (double)((var38 & 2) - 1) * var10;
               double var43 = (double)((var38 + 1 & 2) - 1) * var10;
               double var45 = 0.0D;
               double var47 = var41 * var36 - var43 * var34;
               double var49 = var43 * var36 + var41 * var34;
               double var51 = var47 * var28 + 0.0D * var30;
               double var53 = 0.0D * var28 - var47 * var30;
               double var55 = var53 * var22 - var49 * var24;
               double var57 = var49 * var22 + var53 * var24;
               var1.addVertex(var14 + var55, var16 + var51, var18 + var57);
            }
         }
      }

   }

   public int getCountRenderers() {
      return this.field_175008_n.field_178164_f.length;
   }

   public void func_174976_a(float var1, int var2) {
      if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
         WorldProvider var3 = this.mc.theWorld.provider;
         Object var4 = Reflector.call(var3, Reflector.ForgeWorldProvider_getSkyRenderer);
         if (var4 != null) {
            Reflector.callVoid(var4, Reflector.IRenderHandler_render, var1, this.theWorld, this.mc);
            return;
         }
      }

      if (this.mc.theWorld.provider.getDimensionId() == 1) {
         this.func_180448_r();
      } else if (this.mc.theWorld.provider.isSurfaceWorld()) {
         GlStateManager.func_179090_x();
         boolean var24 = Config.isShaders();
         if (var24) {
            Shaders.disableTexture2D();
         }

         Vec3 var25 = this.theWorld.getSkyColor(this.mc.func_175606_aa(), var1);
         var25 = CustomColors.getSkyColor(var25, this.mc.theWorld, this.mc.func_175606_aa().posX, this.mc.func_175606_aa().posY + 1.0D, this.mc.func_175606_aa().posZ);
         if (var24) {
            Shaders.setSkyColor(var25);
         }

         float var5 = (float)var25.xCoord;
         float var6 = (float)var25.yCoord;
         float var7 = (float)var25.zCoord;
         if (var2 != 2) {
            float var8 = (var5 * 30.0F + var6 * 59.0F + var7 * 11.0F) / 100.0F;
            float var9 = (var5 * 30.0F + var6 * 70.0F) / 100.0F;
            float var10 = (var5 * 30.0F + var7 * 70.0F) / 100.0F;
            var5 = var8;
            var6 = var9;
            var7 = var10;
         }

         GlStateManager.color(var5, var6, var7);
         Tessellator var26 = Tessellator.getInstance();
         WorldRenderer var27 = var26.getWorldRenderer();
         GlStateManager.depthMask(false);
         GlStateManager.enableFog();
         if (var24) {
            Shaders.enableFog();
         }

         GlStateManager.color(var5, var6, var7);
         if (var24) {
            Shaders.preSkyList();
         }

         if (Config.isSkyEnabled()) {
            if (this.field_175005_X) {
               this.field_175012_t.func_177359_a();
               GL11.glEnableClientState(32884);
               GL11.glVertexPointer(3, 5126, 12, 0L);
               this.field_175012_t.func_177358_a(7);
               this.field_175012_t.func_177361_b();
               GL11.glDisableClientState(32884);
            } else {
               GlStateManager.callList(this.glSkyList);
            }
         }

         GlStateManager.disableFog();
         if (var24) {
            Shaders.disableFog();
         }

         GlStateManager.disableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.disableStandardItemLighting();
         float[] var28 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(var1), var1);
         float var11;
         float var12;
         float var13;
         float var14;
         float var15;
         float var16;
         int var17;
         float var18;
         float var19;
         if (var28 != null && Config.isSunMoonEnabled()) {
            GlStateManager.func_179090_x();
            if (var24) {
               Shaders.disableTexture2D();
            }

            GlStateManager.shadeModel(7425);
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(MathHelper.sin(this.theWorld.getCelestialAngleRadians(var1)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            var11 = var28[0];
            var12 = var28[1];
            var13 = var28[2];
            if (var2 != 2) {
               var14 = (var11 * 30.0F + var12 * 59.0F + var13 * 11.0F) / 100.0F;
               var15 = (var11 * 30.0F + var12 * 70.0F) / 100.0F;
               var16 = (var11 * 30.0F + var13 * 70.0F) / 100.0F;
               var11 = var14;
               var12 = var15;
               var13 = var16;
            }

            var27.startDrawing(6);
            var27.func_178960_a(var11, var12, var13, var28[3]);
            var27.addVertex(0.0D, 100.0D, 0.0D);
            boolean var20 = true;
            var27.func_178960_a(var28[0], var28[1], var28[2], 0.0F);

            for(var17 = 0; var17 <= 16; ++var17) {
               var16 = (float)var17 * 3.1415927F * 2.0F / 16.0F;
               var18 = MathHelper.sin(var16);
               var19 = MathHelper.cos(var16);
               var27.addVertex((double)(var18 * 120.0F), (double)(var19 * 120.0F), (double)(-var19 * 40.0F * var28[3]));
            }

            var26.draw();
            GlStateManager.popMatrix();
            GlStateManager.shadeModel(7424);
         }

         GlStateManager.func_179098_w();
         if (var24) {
            Shaders.enableTexture2D();
         }

         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         GlStateManager.pushMatrix();
         var11 = 1.0F - this.theWorld.getRainStrength(var1);
         var12 = 0.0F;
         var13 = 0.0F;
         var14 = 0.0F;
         GlStateManager.color(1.0F, 1.0F, 1.0F, var11);
         GlStateManager.translate(0.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(var1), var11);
         if (var24) {
            Shaders.preCelestialRotate();
         }

         GlStateManager.rotate(this.theWorld.getCelestialAngle(var1) * 360.0F, 1.0F, 0.0F, 0.0F);
         if (var24) {
            Shaders.postCelestialRotate();
         }

         if (Config.isSunMoonEnabled()) {
            var15 = 30.0F;
            this.renderEngine.bindTexture(locationSunPng);
            var27.startDrawingQuads();
            var27.addVertexWithUV((double)(-var15), 100.0D, (double)(-var15), 0.0D, 0.0D);
            var27.addVertexWithUV((double)var15, 100.0D, (double)(-var15), 1.0D, 0.0D);
            var27.addVertexWithUV((double)var15, 100.0D, (double)var15, 1.0D, 1.0D);
            var27.addVertexWithUV((double)(-var15), 100.0D, (double)var15, 0.0D, 1.0D);
            var26.draw();
            var15 = 20.0F;
            this.renderEngine.bindTexture(locationMoonPhasesPng);
            int var29 = this.theWorld.getMoonPhase();
            int var21 = var29 % 4;
            var17 = var29 / 4 % 2;
            var18 = (float)var21 / 4.0F;
            var19 = (float)var17 / 2.0F;
            float var22 = (float)(var21 + 1) / 4.0F;
            float var23 = (float)(var17 + 1) / 2.0F;
            var27.startDrawingQuads();
            var27.addVertexWithUV((double)(-var15), -100.0D, (double)var15, (double)var22, (double)var23);
            var27.addVertexWithUV((double)var15, -100.0D, (double)var15, (double)var18, (double)var23);
            var27.addVertexWithUV((double)var15, -100.0D, (double)(-var15), (double)var18, (double)var19);
            var27.addVertexWithUV((double)(-var15), -100.0D, (double)(-var15), (double)var22, (double)var19);
            var26.draw();
         }

         GlStateManager.func_179090_x();
         if (var24) {
            Shaders.disableTexture2D();
         }

         var16 = this.theWorld.getStarBrightness(var1) * var11;
         if (var16 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
            GlStateManager.color(var16, var16, var16, var16);
            if (this.field_175005_X) {
               this.field_175013_s.func_177359_a();
               GL11.glEnableClientState(32884);
               GL11.glVertexPointer(3, 5126, 12, 0L);
               this.field_175013_s.func_177358_a(7);
               this.field_175013_s.func_177361_b();
               GL11.glDisableClientState(32884);
            } else {
               GlStateManager.callList(this.starGLCallList);
            }
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.enableFog();
         if (var24) {
            Shaders.enableFog();
         }

         GlStateManager.popMatrix();
         GlStateManager.func_179090_x();
         if (var24) {
            Shaders.disableTexture2D();
         }

         GlStateManager.color(0.0F, 0.0F, 0.0F);
         double var30 = this.mc.thePlayer.func_174824_e(var1).yCoord - this.theWorld.getHorizon();
         if (var30 < 0.0D) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 12.0F, 0.0F);
            if (this.field_175005_X) {
               this.field_175011_u.func_177359_a();
               GL11.glEnableClientState(32884);
               GL11.glVertexPointer(3, 5126, 12, 0L);
               this.field_175011_u.func_177358_a(7);
               this.field_175011_u.func_177361_b();
               GL11.glDisableClientState(32884);
            } else {
               GlStateManager.callList(this.glSkyList2);
            }

            GlStateManager.popMatrix();
            var13 = 1.0F;
            var14 = -((float)(var30 + 65.0D));
            var15 = -1.0F;
            var27.startDrawingQuads();
            var27.func_178974_a(0, 255);
            var27.addVertex(-1.0D, (double)var14, 1.0D);
            var27.addVertex(1.0D, (double)var14, 1.0D);
            var27.addVertex(1.0D, -1.0D, 1.0D);
            var27.addVertex(-1.0D, -1.0D, 1.0D);
            var27.addVertex(-1.0D, -1.0D, -1.0D);
            var27.addVertex(1.0D, -1.0D, -1.0D);
            var27.addVertex(1.0D, (double)var14, -1.0D);
            var27.addVertex(-1.0D, (double)var14, -1.0D);
            var27.addVertex(1.0D, -1.0D, -1.0D);
            var27.addVertex(1.0D, -1.0D, 1.0D);
            var27.addVertex(1.0D, (double)var14, 1.0D);
            var27.addVertex(1.0D, (double)var14, -1.0D);
            var27.addVertex(-1.0D, (double)var14, -1.0D);
            var27.addVertex(-1.0D, (double)var14, 1.0D);
            var27.addVertex(-1.0D, -1.0D, 1.0D);
            var27.addVertex(-1.0D, -1.0D, -1.0D);
            var27.addVertex(-1.0D, -1.0D, -1.0D);
            var27.addVertex(-1.0D, -1.0D, 1.0D);
            var27.addVertex(1.0D, -1.0D, 1.0D);
            var27.addVertex(1.0D, -1.0D, -1.0D);
            var26.draw();
         }

         if (this.theWorld.provider.isSkyColored()) {
            GlStateManager.color(var5 * 0.2F + 0.04F, var6 * 0.2F + 0.04F, var7 * 0.6F + 0.1F);
         } else {
            GlStateManager.color(var5, var6, var7);
         }

         if (this.mc.gameSettings.renderDistanceChunks <= 4) {
            GlStateManager.color(this.mc.entityRenderer.field_175080_Q, this.mc.entityRenderer.field_175082_R, this.mc.entityRenderer.field_175081_S);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -((float)(var30 - 16.0D)), 0.0F);
         if (Config.isSkyEnabled()) {
            GlStateManager.callList(this.glSkyList2);
         }

         GlStateManager.popMatrix();
         GlStateManager.func_179098_w();
         if (var24) {
            Shaders.enableTexture2D();
         }

         GlStateManager.depthMask(true);
      }

   }

   public void notifyLightSet(BlockPos var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      int var4 = var1.getZ();
      this.markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   public void resetClouds() {
      this.cloudRenderer.reset();
   }

   protected Vector3f func_174962_a(Entity var1, double var2) {
      float var4 = (float)((double)var1.prevRotationPitch + (double)(var1.rotationPitch - var1.prevRotationPitch) * var2);
      float var5 = (float)((double)var1.prevRotationYaw + (double)(var1.rotationYaw - var1.prevRotationYaw) * var2);
      if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
         var4 += 180.0F;
      }

      float var6 = MathHelper.cos(-var5 * 0.017453292F - 3.1415927F);
      float var7 = MathHelper.sin(-var5 * 0.017453292F - 3.1415927F);
      float var8 = -MathHelper.cos(-var4 * 0.017453292F);
      float var9 = MathHelper.sin(-var4 * 0.017453292F);
      return new Vector3f(var7 * var8, var9, var6 * var8);
   }

   public void func_180442_a(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      try {
         this.func_174974_b(var1, var2, var3, var5, var7, var9, var11, var13, var15);
      } catch (Throwable var19) {
         CrashReport var17 = CrashReport.makeCrashReport(var19, "Exception while adding particle");
         CrashReportCategory var18 = var17.makeCategory("Particle being added");
         var18.addCrashSection("ID", var1);
         if (var15 != null) {
            var18.addCrashSection("Parameters", var15);
         }

         var18.addCrashSectionCallable("Position", new Callable(this, var3, var5, var7) {
            private final double val$p_180442_5_;
            private final double val$p_180442_7_;
            final RenderGlobal this$0;
            private final double val$p_180442_3_;

            public Object call() throws Exception {
               return this.call();
            }

            {
               this.this$0 = var1;
               this.val$p_180442_3_ = var2;
               this.val$p_180442_5_ = var4;
               this.val$p_180442_7_ = var6;
            }

            public String call() {
               return CrashReportCategory.getCoordinateInfo(this.val$p_180442_3_, this.val$p_180442_5_, this.val$p_180442_7_);
            }
         });
         throw new ReportedException(var17);
      }
   }

   private void func_180448_r() {
      if (Config.isSkyEnabled()) {
         GlStateManager.disableFog();
         GlStateManager.disableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.depthMask(false);
         this.renderEngine.bindTexture(locationEndSkyPng);
         Tessellator var1 = Tessellator.getInstance();
         WorldRenderer var2 = var1.getWorldRenderer();

         for(int var3 = 0; var3 < 6; ++var3) {
            GlStateManager.pushMatrix();
            if (var3 == 1) {
               GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var3 == 2) {
               GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var3 == 3) {
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var3 == 4) {
               GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (var3 == 5) {
               GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            var2.startDrawingQuads();
            var2.func_178991_c(2631720);
            var2.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
            var2.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
            var2.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
            var2.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
            var1.draw();
            GlStateManager.popMatrix();
         }

         GlStateManager.depthMask(true);
         GlStateManager.func_179098_w();
         GlStateManager.enableAlpha();
      }

   }

   public int getCountTileEntitiesRendered() {
      return this.countTileEntitiesRendered;
   }

   public String getDebugInfoRenders() {
      int var1 = this.field_175008_n.field_178164_f.length;
      int var2 = 0;
      Iterator var3 = this.glRenderLists.iterator();

      while(var3.hasNext()) {
         RenderGlobal.ContainerLocalRenderInformation var4 = (RenderGlobal.ContainerLocalRenderInformation)var3.next();
         CompiledChunk var5 = var4.field_178036_a.field_178590_b;
         if (var5 != CompiledChunk.field_178502_a && !var5.func_178489_a()) {
            ++var2;
         }
      }

      return String.format("C: %d/%d %sD: %d, %s", var2, var1, this.mc.field_175612_E ? "(s) " : "", this.renderDistanceChunks, this.field_174995_M.func_178504_a());
   }

   public RenderGlobal(Minecraft var1) {
      this.cloudRenderer = new CloudRenderer(var1);
      this.mc = var1;
      this.field_175010_j = var1.getRenderManager();
      this.renderEngine = var1.getTextureManager();
      this.renderEngine.bindTexture(field_175006_g);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GlStateManager.func_179144_i(0);
      this.func_174971_n();
      this.field_175005_X = OpenGlHelper.func_176075_f();
      if (this.field_175005_X) {
         this.field_174996_N = new VboRenderList();
         this.field_175007_a = new VboChunkFactory();
      } else {
         this.field_174996_N = new RenderList();
         this.field_175007_a = new ListChunkFactory();
      }

      this.field_175014_r = new VertexFormat();
      this.field_175014_r.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
      this.func_174963_q();
      this.func_174980_p();
      this.func_174964_o();
   }

   public void func_174966_b() {
      if (OpenGlHelper.shadersSupported) {
         if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
         }

         ResourceLocation var1 = new ResourceLocation("shaders/post/entity_outline.json");

         try {
            this.field_174991_A = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), var1);
            this.field_174991_A.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.field_175015_z = this.field_174991_A.func_177066_a("final");
         } catch (IOException var3) {
            logger.warn(String.valueOf((new StringBuilder("Failed to load shader: ")).append(var1)), var3);
            this.field_174991_A = null;
            this.field_175015_z = null;
         } catch (JsonSyntaxException var4) {
            logger.warn(String.valueOf((new StringBuilder("Failed to load shader: ")).append(var1)), var4);
            this.field_174991_A = null;
            this.field_175015_z = null;
         }
      } else {
         this.field_174991_A = null;
         this.field_175015_z = null;
      }

   }

   public RenderChunk getRenderChunk(RenderChunk var1, EnumFacing var2) {
      if (var1 == null) {
         return null;
      } else {
         BlockPos var3 = var1.getPositionOffset16(var2);
         return this.field_175008_n.func_178161_a(var3);
      }
   }

   private void markBlocksForUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field_175008_n.func_178162_a(var1, var2, var3, var4, var5, var6);
   }

   public void func_180439_a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      Random var5 = this.theWorld.rand;
      double var6;
      double var8;
      double var10;
      int var12;
      int var13;
      double var14;
      double var16;
      double var18;
      double var20;
      double var22;
      double var24;
      switch(var2) {
      case 1000:
         this.theWorld.func_175731_a(var3, "random.click", 1.0F, 1.0F, false);
         break;
      case 1001:
         this.theWorld.func_175731_a(var3, "random.click", 1.0F, 1.2F, false);
         break;
      case 1002:
         this.theWorld.func_175731_a(var3, "random.bow", 1.0F, 1.2F, false);
         break;
      case 1003:
         this.theWorld.func_175731_a(var3, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1004:
         this.theWorld.func_175731_a(var3, "random.fizz", 0.5F, 2.6F + (var5.nextFloat() - var5.nextFloat()) * 0.8F, false);
         break;
      case 1005:
         if (Item.getItemById(var4) instanceof ItemRecord) {
            this.theWorld.func_175717_a(var3, String.valueOf((new StringBuilder("records.")).append(((ItemRecord)Item.getItemById(var4)).recordName)));
         } else {
            this.theWorld.func_175717_a(var3, (String)null);
         }
         break;
      case 1006:
         this.theWorld.func_175731_a(var3, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1007:
         this.theWorld.func_175731_a(var3, "mob.ghast.charge", 10.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1008:
         this.theWorld.func_175731_a(var3, "mob.ghast.fireball", 10.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1009:
         this.theWorld.func_175731_a(var3, "mob.ghast.fireball", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1010:
         this.theWorld.func_175731_a(var3, "mob.zombie.wood", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1011:
         this.theWorld.func_175731_a(var3, "mob.zombie.metal", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1012:
         this.theWorld.func_175731_a(var3, "mob.zombie.woodbreak", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1014:
         this.theWorld.func_175731_a(var3, "mob.wither.shoot", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1015:
         this.theWorld.func_175731_a(var3, "mob.bat.takeoff", 0.05F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1016:
         this.theWorld.func_175731_a(var3, "mob.zombie.infect", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1017:
         this.theWorld.func_175731_a(var3, "mob.zombie.unfect", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1020:
         this.theWorld.func_175731_a(var3, "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1021:
         this.theWorld.func_175731_a(var3, "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1022:
         this.theWorld.func_175731_a(var3, "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2000:
         int var26 = var4 % 3 - 1;
         int var27 = var4 / 3 % 3 - 1;
         var8 = (double)var3.getX() + (double)var26 * 0.6D + 0.5D;
         var10 = (double)var3.getY() + 0.5D;
         var20 = (double)var3.getZ() + (double)var27 * 0.6D + 0.5D;

         for(int var39 = 0; var39 < 10; ++var39) {
            double var40 = var5.nextDouble() * 0.2D + 0.01D;
            double var41 = var8 + (double)var26 * 0.01D + (var5.nextDouble() - 0.5D) * (double)var27 * 0.5D;
            var22 = var10 + (var5.nextDouble() - 0.5D) * 0.5D;
            var24 = var20 + (double)var27 * 0.01D + (var5.nextDouble() - 0.5D) * (double)var26 * 0.5D;
            double var42 = (double)var26 * var40 + var5.nextGaussian() * 0.01D;
            double var35 = -0.03D + var5.nextGaussian() * 0.01D;
            double var37 = (double)var27 * var40 + var5.nextGaussian() * 0.01D;
            this.func_174972_a(EnumParticleTypes.SMOKE_NORMAL, var41, var22, var24, var42, var35, var37);
         }

         return;
      case 2001:
         Block var28 = Block.getBlockById(var4 & 4095);
         if (var28.getMaterial() != Material.air) {
            this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var28.stepSound.getBreakSound()), (var28.stepSound.getVolume() + 1.0F) / 2.0F, var28.stepSound.getFrequency() * 0.8F, (float)var3.getX() + 0.5F, (float)var3.getY() + 0.5F, (float)var3.getZ() + 0.5F));
         }

         this.mc.effectRenderer.func_180533_a(var3, var28.getStateFromMeta(var4 >> 12 & 255));
         break;
      case 2002:
         var6 = (double)var3.getX();
         var8 = (double)var3.getY();
         var10 = (double)var3.getZ();

         for(var12 = 0; var12 < 8; ++var12) {
            this.func_174972_a(EnumParticleTypes.ITEM_CRACK, var6, var8, var10, var5.nextGaussian() * 0.15D, var5.nextDouble() * 0.2D, var5.nextGaussian() * 0.15D, Item.getIdFromItem(Items.potionitem), var4);
         }

         var12 = Items.potionitem.getColorFromDamage(var4);
         float var29 = (float)(var12 >> 16 & 255) / 255.0F;
         float var30 = (float)(var12 >> 8 & 255) / 255.0F;
         float var31 = (float)(var12 >> 0 & 255) / 255.0F;
         EnumParticleTypes var32 = EnumParticleTypes.SPELL;
         if (Items.potionitem.isEffectInstant(var4)) {
            var32 = EnumParticleTypes.SPELL_INSTANT;
         }

         for(var13 = 0; var13 < 100; ++var13) {
            var14 = var5.nextDouble() * 4.0D;
            var16 = var5.nextDouble() * 3.141592653589793D * 2.0D;
            var18 = Math.cos(var16) * var14;
            var22 = 0.01D + var5.nextDouble() * 0.5D;
            var24 = Math.sin(var16) * var14;
            EntityFX var33 = this.func_174974_b(var32.func_179348_c(), var32.func_179344_e(), var6 + var18 * 0.1D, var8 + 0.3D, var10 + var24 * 0.1D, var18, var22, var24);
            if (var33 != null) {
               float var34 = 0.75F + var5.nextFloat() * 0.25F;
               var33.setRBGColorF(var29 * var34, var30 * var34, var31 * var34);
               var33.multiplyVelocity((float)var14);
            }
         }

         this.theWorld.func_175731_a(var3, "game.potion.smash", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2003:
         var6 = (double)var3.getX() + 0.5D;
         var8 = (double)var3.getY();
         var10 = (double)var3.getZ() + 0.5D;

         for(var12 = 0; var12 < 8; ++var12) {
            this.func_174972_a(EnumParticleTypes.ITEM_CRACK, var6, var8, var10, var5.nextGaussian() * 0.15D, var5.nextDouble() * 0.2D, var5.nextGaussian() * 0.15D, Item.getIdFromItem(Items.ender_eye));
         }

         for(var20 = 0.0D; var20 < 6.283185307179586D; var20 += 0.15707963267948966D) {
            this.func_174972_a(EnumParticleTypes.PORTAL, var6 + Math.cos(var20) * 5.0D, var8 - 0.4D, var10 + Math.sin(var20) * 5.0D, Math.cos(var20) * -5.0D, 0.0D, Math.sin(var20) * -5.0D);
            this.func_174972_a(EnumParticleTypes.PORTAL, var6 + Math.cos(var20) * 5.0D, var8 - 0.4D, var10 + Math.sin(var20) * 5.0D, Math.cos(var20) * -7.0D, 0.0D, Math.sin(var20) * -7.0D);
         }

         return;
      case 2004:
         for(var13 = 0; var13 < 20; ++var13) {
            var14 = (double)var3.getX() + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            var16 = (double)var3.getY() + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            var18 = (double)var3.getZ() + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var14, var16, var18, 0.0D, 0.0D, 0.0D, new int[0]);
            this.theWorld.spawnParticle(EnumParticleTypes.FLAME, var14, var16, var18, 0.0D, 0.0D, 0.0D, new int[0]);
         }

         return;
      case 2005:
         ItemDye.func_180617_a(this.theWorld, var3, var4);
      }

   }

   public void onResourceManagerReload(IResourceManager var1) {
      this.func_174971_n();
   }

   public void setWorldAndLoadRenderers(WorldClient var1) {
      if (this.theWorld != null) {
         this.theWorld.removeWorldAccess(this);
      }

      this.field_174992_B = Double.MIN_VALUE;
      this.field_174993_C = Double.MIN_VALUE;
      this.field_174987_D = Double.MIN_VALUE;
      this.field_174988_E = Integer.MIN_VALUE;
      this.field_174989_F = Integer.MIN_VALUE;
      this.field_174990_G = Integer.MIN_VALUE;
      this.field_175010_j.set(var1);
      this.theWorld = var1;
      if (Config.isDynamicLights()) {
         DynamicLights.clear();
      }

      if (var1 != null) {
         var1.addWorldAccess(this);
         this.loadRenderers();
      }

   }

   private void func_174968_a(WorldRenderer var1, float var2, boolean var3) {
      boolean var4 = true;
      boolean var5 = true;
      var1.startDrawingQuads();

      for(int var6 = -384; var6 <= 384; var6 += 64) {
         for(int var7 = -384; var7 <= 384; var7 += 64) {
            float var8 = (float)var6;
            float var9 = (float)(var6 + 64);
            if (var3) {
               var9 = (float)var6;
               var8 = (float)(var6 + 64);
            }

            var1.addVertex((double)var8, (double)var2, (double)var7);
            var1.addVertex((double)var9, (double)var2, (double)var7);
            var1.addVertex((double)var9, (double)var2, (double)(var7 + 64));
            var1.addVertex((double)var8, (double)var2, (double)(var7 + 64));
         }
      }

   }

   public boolean hasCloudFog(double var1, double var3, double var5, float var7) {
      return false;
   }

   private boolean func_174983_a(BlockPos var1, RenderChunk var2) {
      BlockPos var3 = var2.func_178568_j();
      return MathHelper.abs_int(var1.getX() - var3.getX()) > 16 ? false : (MathHelper.abs_int(var1.getY() - var3.getY()) > 16 ? false : MathHelper.abs_int(var1.getZ() - var3.getZ()) <= 16);
   }

   private void func_174963_q() {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      if (this.field_175013_s != null) {
         this.field_175013_s.func_177362_c();
      }

      if (this.starGLCallList >= 0) {
         GLAllocation.deleteDisplayLists(this.starGLCallList);
         this.starGLCallList = -1;
      }

      if (this.field_175005_X) {
         this.field_175013_s = new VertexBuffer(this.field_175014_r);
         this.func_180444_a(var2);
         var2.draw();
         var2.reset();
         this.field_175013_s.func_177360_a(var2.func_178966_f(), var2.func_178976_e());
      } else {
         this.starGLCallList = GLAllocation.generateDisplayLists(1);
         GlStateManager.pushMatrix();
         GL11.glNewList(this.starGLCallList, 4864);
         this.func_180444_a(var2);
         var1.draw();
         GL11.glEndList();
         GlStateManager.popMatrix();
      }

   }

   private void func_174965_a(Iterator var1) {
      while(var1.hasNext()) {
         DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
         int var3 = var2.getCreationCloudUpdateTick();
         if (this.cloudTickCounter - var3 > 400) {
            var1.remove();
         }
      }

   }

   private RenderChunk getRenderChunkOffset(BlockPos var1, RenderChunk var2, EnumFacing var3) {
      BlockPos var4 = var2.getPositionOffset16(var3);
      if (var4.getY() >= 0 && var4.getY() < 256) {
         int var5 = MathHelper.abs_int(var1.getX() - var4.getX());
         int var6 = MathHelper.abs_int(var1.getZ() - var4.getZ());
         if (Config.isFogOff()) {
            if (var5 > this.renderDistance || var6 > this.renderDistance) {
               return null;
            }
         } else {
            int var7 = var5 * var5 + var6 * var6;
            if (var7 > this.renderDistanceSq) {
               return null;
            }
         }

         return this.field_175008_n.func_178161_a(var4);
      } else {
         return null;
      }
   }

   public int getCountActiveRenderers() {
      return this.glRenderLists.size();
   }

   public void func_174975_c() {
      if (this.func_174985_d()) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
         this.field_175015_z.func_178038_a(this.mc.displayWidth, this.mc.displayHeight, false);
         GlStateManager.disableBlend();
      }

   }

   public int getCountEntitiesRendered() {
      return this.countEntitiesRendered;
   }

   public void playSoundToNearExcept(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9, float var10) {
   }

   private Set func_174978_c(BlockPos var1) {
      VisGraph var2 = new VisGraph();
      BlockPos var3 = new BlockPos(var1.getX() >> 4 << 4, var1.getY() >> 4 << 4, var1.getZ() >> 4 << 4);
      Chunk var4 = this.theWorld.getChunkFromBlockCoords(var3);
      Iterator var5 = BlockPos.getAllInBoxMutable(var3, var3.add(15, 15, 15)).iterator();

      while(var5.hasNext()) {
         BlockPos.MutableBlockPos var6 = (BlockPos.MutableBlockPos)var5.next();
         if (var4.getBlock(var6).isOpaqueCube()) {
            var2.func_178606_a(var6);
         }
      }

      return var2.func_178609_b(var1);
   }

   private void func_180443_s() {
      GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
      GlStateManager.enableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
      GlStateManager.doPolygonOffset(-3.0F, -3.0F);
      GlStateManager.enablePolygonOffset();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableAlpha();
      GlStateManager.pushMatrix();
      if (Config.isShaders()) {
         ShadersRender.beginBlockDamage();
      }

   }

   public void playSound(String var1, double var2, double var4, double var6, float var8, float var9) {
   }

   public void updateClouds() {
      if (Config.isShaders() && Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
         Shaders.uninit();
      }

      ++this.cloudTickCounter;
      if (this.cloudTickCounter % 20 == 0) {
         this.func_174965_a(this.damagedBlocks.values().iterator());
      }

   }

   public void loadRenderers() {
      if (this.theWorld != null) {
         this.displayListEntitiesDirty = true;
         Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
         Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
         BlockModelRenderer.updateAoLightValue();
         if (Config.isDynamicLights()) {
            DynamicLights.clear();
         }

         this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
         this.renderDistance = this.renderDistanceChunks * 16;
         this.renderDistanceSq = this.renderDistance * this.renderDistance;
         boolean var1 = this.field_175005_X;
         this.field_175005_X = OpenGlHelper.func_176075_f();
         if (var1 && !this.field_175005_X) {
            this.field_174996_N = new RenderList();
            this.field_175007_a = new ListChunkFactory();
         } else if (!var1 && this.field_175005_X) {
            this.field_174996_N = new VboRenderList();
            this.field_175007_a = new VboChunkFactory();
         }

         if (var1 != this.field_175005_X) {
            this.func_174963_q();
            this.func_174980_p();
            this.func_174964_o();
         }

         if (this.field_175008_n != null) {
            this.field_175008_n.func_178160_a();
         }

         this.func_174986_e();
         this.field_175008_n = new ViewFrustum(this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.field_175007_a);
         if (this.theWorld != null) {
            Entity var2 = this.mc.func_175606_aa();
            if (var2 != null) {
               this.field_175008_n.func_178163_a(var2.posX, var2.posZ);
            }
         }

         this.renderEntitiesStartupCounter = 2;
      }

   }

   private void func_180445_c(float var1, int var2) {
      this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, var1);
      var1 = 0.0F;
      GlStateManager.disableCull();
      float var3 = (float)(this.mc.func_175606_aa().lastTickPosY + (this.mc.func_175606_aa().posY - this.mc.func_175606_aa().lastTickPosY) * (double)var1);
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      float var6 = 12.0F;
      float var7 = 4.0F;
      double var8 = (double)((float)this.cloudTickCounter + var1);
      double var10 = (this.mc.func_175606_aa().prevPosX + (this.mc.func_175606_aa().posX - this.mc.func_175606_aa().prevPosX) * (double)var1 + var8 * 0.029999999329447746D) / 12.0D;
      double var12 = (this.mc.func_175606_aa().prevPosZ + (this.mc.func_175606_aa().posZ - this.mc.func_175606_aa().prevPosZ) * (double)var1) / 12.0D + 0.33000001311302185D;
      float var14 = this.theWorld.provider.getCloudHeight() - var3 + 0.33F;
      var14 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
      int var15 = MathHelper.floor_double(var10 / 2048.0D);
      int var16 = MathHelper.floor_double(var12 / 2048.0D);
      var10 -= (double)(var15 * 2048);
      var12 -= (double)(var16 * 2048);
      this.renderEngine.bindTexture(locationCloudsPng);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      Vec3 var17 = this.theWorld.getCloudColour(var1);
      float var18 = (float)var17.xCoord;
      float var19 = (float)var17.yCoord;
      float var20 = (float)var17.zCoord;
      float var21;
      float var22;
      float var23;
      if (var2 != 2) {
         var21 = (var18 * 30.0F + var19 * 59.0F + var20 * 11.0F) / 100.0F;
         var22 = (var18 * 30.0F + var19 * 70.0F) / 100.0F;
         var23 = (var18 * 30.0F + var20 * 70.0F) / 100.0F;
         var18 = var21;
         var19 = var22;
         var20 = var23;
      }

      var21 = 0.00390625F;
      var22 = (float)MathHelper.floor_double(var10) * 0.00390625F;
      var23 = (float)MathHelper.floor_double(var12) * 0.00390625F;
      float var24 = (float)(var10 - (double)MathHelper.floor_double(var10));
      float var25 = (float)(var12 - (double)MathHelper.floor_double(var12));
      boolean var26 = true;
      boolean var27 = true;
      float var28 = 9.765625E-4F;
      GlStateManager.scale(12.0F, 1.0F, 12.0F);

      int var29;
      for(var29 = 0; var29 < 2; ++var29) {
         if (var29 == 0) {
            GlStateManager.colorMask(false, false, false, false);
         } else {
            switch(var2) {
            case 0:
               GlStateManager.colorMask(false, true, true, true);
               break;
            case 1:
               GlStateManager.colorMask(true, false, false, true);
               break;
            case 2:
               GlStateManager.colorMask(true, true, true, true);
            }
         }

         this.cloudRenderer.renderGlList();
      }

      if (this.cloudRenderer.shouldUpdateGlList()) {
         this.cloudRenderer.startUpdateGlList();

         for(var29 = -3; var29 <= 4; ++var29) {
            for(int var30 = -3; var30 <= 4; ++var30) {
               var5.startDrawingQuads();
               float var31 = (float)(var29 * 8);
               float var32 = (float)(var30 * 8);
               float var33 = var31 - var24;
               float var34 = var32 - var25;
               if (var14 > -5.0F) {
                  var5.func_178960_a(var18 * 0.7F, var19 * 0.7F, var20 * 0.7F, 0.8F);
                  var5.func_178980_d(0.0F, -1.0F, 0.0F);
                  var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 0.0F), (double)(var34 + 8.0F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                  var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 0.0F), (double)(var34 + 8.0F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                  var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 0.0F), (double)(var34 + 0.0F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
                  var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 0.0F), (double)(var34 + 0.0F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
               }

               if (var14 <= 5.0F) {
                  var5.func_178960_a(var18, var19, var20, 0.8F);
                  var5.func_178980_d(0.0F, 1.0F, 0.0F);
                  var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var34 + 8.0F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                  var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var34 + 8.0F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                  var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var34 + 0.0F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
                  var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 4.0F - 9.765625E-4F), (double)(var34 + 0.0F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
               }

               var5.func_178960_a(var18 * 0.9F, var19 * 0.9F, var20 * 0.9F, 0.8F);
               int var35;
               if (var29 > -1) {
                  var5.func_178980_d(-1.0F, 0.0F, 0.0F);

                  for(var35 = 0; var35 < 8; ++var35) {
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 0.0F), (double)(var14 + 0.0F), (double)(var34 + 8.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 0.0F), (double)(var14 + 4.0F), (double)(var34 + 8.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 0.0F), (double)(var14 + 4.0F), (double)(var34 + 0.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 0.0F), (double)(var14 + 0.0F), (double)(var34 + 0.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
                  }
               }

               if (var29 <= 1) {
                  var5.func_178980_d(1.0F, 0.0F, 0.0F);

                  for(var35 = 0; var35 < 8; ++var35) {
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 1.0F - 9.765625E-4F), (double)(var14 + 0.0F), (double)(var34 + 8.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 1.0F - 9.765625E-4F), (double)(var14 + 4.0F), (double)(var34 + 8.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 8.0F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 1.0F - 9.765625E-4F), (double)(var14 + 4.0F), (double)(var34 + 0.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + (float)var35 + 1.0F - 9.765625E-4F), (double)(var14 + 0.0F), (double)(var34 + 0.0F), (double)((var31 + (float)var35 + 0.5F) * 0.00390625F + var22), (double)((var32 + 0.0F) * 0.00390625F + var23));
                  }
               }

               var5.func_178960_a(var18 * 0.8F, var19 * 0.8F, var20 * 0.8F, 0.8F);
               if (var30 > -1) {
                  var5.func_178980_d(0.0F, 0.0F, -1.0F);

                  for(var35 = 0; var35 < 8; ++var35) {
                     var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 4.0F), (double)(var34 + (float)var35 + 0.0F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 4.0F), (double)(var34 + (float)var35 + 0.0F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 0.0F), (double)(var34 + (float)var35 + 0.0F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 0.0F), (double)(var34 + (float)var35 + 0.0F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                  }
               }

               if (var30 <= 1) {
                  var5.func_178980_d(0.0F, 0.0F, 1.0F);

                  for(var35 = 0; var35 < 8; ++var35) {
                     var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 4.0F), (double)(var34 + (float)var35 + 1.0F - 9.765625E-4F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 4.0F), (double)(var34 + (float)var35 + 1.0F - 9.765625E-4F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + 8.0F), (double)(var14 + 0.0F), (double)(var34 + (float)var35 + 1.0F - 9.765625E-4F), (double)((var31 + 8.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                     var5.addVertexWithUV((double)(var33 + 0.0F), (double)(var14 + 0.0F), (double)(var34 + (float)var35 + 1.0F - 9.765625E-4F), (double)((var31 + 0.0F) * 0.00390625F + var22), (double)((var32 + (float)var35 + 0.5F) * 0.00390625F + var23));
                  }
               }

               var4.draw();
            }
         }

         this.cloudRenderer.endUpdateGlList();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.enableCull();
   }

   public int func_174977_a(EnumWorldBlockLayer var1, double var2, int var4, Entity var5) {
      RenderHelper.disableStandardItemLighting();
      if (var1 == EnumWorldBlockLayer.TRANSLUCENT) {
         this.mc.mcProfiler.startSection("translucent_sort");
         double var6 = var5.posX - this.prevRenderSortX;
         double var8 = var5.posY - this.prevRenderSortY;
         double var10 = var5.posZ - this.prevRenderSortZ;
         if (var6 * var6 + var8 * var8 + var10 * var10 > 1.0D) {
            this.prevRenderSortX = var5.posX;
            this.prevRenderSortY = var5.posY;
            this.prevRenderSortZ = var5.posZ;
            int var12 = 0;
            Iterator var13 = this.glRenderLists.iterator();
            this.chunksToResortTransparency.clear();

            while(var13.hasNext()) {
               RenderGlobal.ContainerLocalRenderInformation var14 = (RenderGlobal.ContainerLocalRenderInformation)var13.next();
               if (var14.field_178036_a.field_178590_b.func_178492_d(var1) && var12++ < 15) {
                  this.chunksToResortTransparency.add(var14.field_178036_a);
               }
            }
         }

         this.mc.mcProfiler.endSection();
      }

      this.mc.mcProfiler.startSection("filterempty");
      int var15 = 0;
      boolean var7 = var1 == EnumWorldBlockLayer.TRANSLUCENT;
      int var16 = var7 ? this.glRenderLists.size() - 1 : 0;
      int var9 = var7 ? -1 : this.glRenderLists.size();
      int var17 = var7 ? -1 : 1;

      for(int var11 = var16; var11 != var9; var11 += var17) {
         RenderChunk var18 = ((RenderGlobal.ContainerLocalRenderInformation)this.glRenderLists.get(var11)).field_178036_a;
         if (!var18.func_178571_g().func_178491_b(var1)) {
            ++var15;
            this.field_174996_N.func_178002_a(var18, var1);
         }
      }

      if (var15 == 0) {
         this.mc.mcProfiler.endSection();
         return var15;
      } else {
         if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GlStateManager.disableFog();
         }

         this.mc.mcProfiler.endStartSection(String.valueOf((new StringBuilder("render_")).append(var1)));
         this.func_174982_a(var1);
         this.mc.mcProfiler.endSection();
         return var15;
      }
   }

   public void markBlockForUpdate(BlockPos var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      int var4 = var1.getZ();
      this.markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   public void func_174967_a(long var1) {
      this.displayListEntitiesDirty |= this.field_174995_M.func_178516_a(var1);
      Iterator var3;
      RenderChunk var4;
      if (this.chunksToUpdateForced.size() > 0) {
         var3 = this.chunksToUpdateForced.iterator();

         while(var3.hasNext()) {
            var4 = (RenderChunk)var3.next();
            if (!this.field_174995_M.func_178507_a(var4)) {
               break;
            }

            var4.func_178575_a(false);
            var3.remove();
            this.field_175009_l.remove(var4);
            this.chunksToResortTransparency.remove(var4);
         }
      }

      if (this.chunksToResortTransparency.size() > 0) {
         var3 = this.chunksToResortTransparency.iterator();
         if (var3.hasNext()) {
            var4 = (RenderChunk)var3.next();
            if (this.field_174995_M.func_178509_c(var4)) {
               var3.remove();
            }
         }
      }

      int var5 = 0;
      int var6 = Config.getUpdatesPerFrame();
      int var7 = var6 * 2;
      Iterator var8 = this.field_175009_l.iterator();

      while(var8.hasNext()) {
         RenderChunk var9 = (RenderChunk)var8.next();
         if (!this.field_174995_M.func_178507_a(var9)) {
            break;
         }

         var9.func_178575_a(false);
         var8.remove();
         if (var9.func_178571_g().func_178489_a() && var6 < var7) {
            ++var6;
         }

         ++var5;
         if (var5 >= var6) {
            break;
         }
      }

   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB var0, int var1) {
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      var3.startDrawing(3);
      if (var1 != -1) {
         var3.func_178991_c(var1);
      }

      var3.addVertex(var0.minX, var0.minY, var0.minZ);
      var3.addVertex(var0.maxX, var0.minY, var0.minZ);
      var3.addVertex(var0.maxX, var0.minY, var0.maxZ);
      var3.addVertex(var0.minX, var0.minY, var0.maxZ);
      var3.addVertex(var0.minX, var0.minY, var0.minZ);
      var2.draw();
      var3.startDrawing(3);
      if (var1 != -1) {
         var3.func_178991_c(var1);
      }

      var3.addVertex(var0.minX, var0.maxY, var0.minZ);
      var3.addVertex(var0.maxX, var0.maxY, var0.minZ);
      var3.addVertex(var0.maxX, var0.maxY, var0.maxZ);
      var3.addVertex(var0.minX, var0.maxY, var0.maxZ);
      var3.addVertex(var0.minX, var0.maxY, var0.minZ);
      var2.draw();
      var3.startDrawing(1);
      if (var1 != -1) {
         var3.func_178991_c(var1);
      }

      var3.addVertex(var0.minX, var0.minY, var0.minZ);
      var3.addVertex(var0.minX, var0.maxY, var0.minZ);
      var3.addVertex(var0.maxX, var0.minY, var0.minZ);
      var3.addVertex(var0.maxX, var0.maxY, var0.minZ);
      var3.addVertex(var0.maxX, var0.minY, var0.maxZ);
      var3.addVertex(var0.maxX, var0.maxY, var0.maxZ);
      var3.addVertex(var0.minX, var0.minY, var0.maxZ);
      var3.addVertex(var0.minX, var0.maxY, var0.maxZ);
      var2.draw();
   }

   public void drawSelectionBox(EntityPlayer var1, MovingObjectPosition var2, int var3, float var4) {
      if (var3 == 0 && var2.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
         GL11.glLineWidth(2.0F);
         GlStateManager.func_179090_x();
         if (Config.isShaders()) {
            Shaders.disableTexture2D();
         }

         GlStateManager.depthMask(false);
         float var5 = 0.002F;
         BlockPos var6 = var2.func_178782_a();
         Block var7 = this.theWorld.getBlockState(var6).getBlock();
         if (var7.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(var6)) {
            var7.setBlockBoundsBasedOnState(this.theWorld, var6);
            double var8 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var4;
            double var10 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var4;
            double var12 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var4;
            drawOutlinedBoundingBox(var7.getSelectedBoundingBox(this.theWorld, var6).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-var8, -var10, -var12), -1);
         }

         GlStateManager.depthMask(true);
         GlStateManager.func_179098_w();
         if (Config.isShaders()) {
            Shaders.enableTexture2D();
         }

         GlStateManager.disableBlend();
      }

   }

   public void onEntityRemoved(Entity var1) {
      if (Config.isDynamicLights()) {
         DynamicLights.entityRemoved(var1, this);
      }

   }

   private void func_174964_o() {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      if (this.field_175011_u != null) {
         this.field_175011_u.func_177362_c();
      }

      if (this.glSkyList2 >= 0) {
         GLAllocation.deleteDisplayLists(this.glSkyList2);
         this.glSkyList2 = -1;
      }

      if (this.field_175005_X) {
         this.field_175011_u = new VertexBuffer(this.field_175014_r);
         this.func_174968_a(var2, -16.0F, true);
         var2.draw();
         var2.reset();
         this.field_175011_u.func_177360_a(var2.func_178966_f(), var2.func_178976_e());
      } else {
         this.glSkyList2 = GLAllocation.generateDisplayLists(1);
         GL11.glNewList(this.glSkyList2, 4864);
         this.func_174968_a(var2, -16.0F, true);
         var1.draw();
         GL11.glEndList();
      }

   }

   public void func_180449_a(Entity var1, float var2) {
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      WorldBorder var5 = this.theWorld.getWorldBorder();
      double var6 = (double)(this.mc.gameSettings.renderDistanceChunks * 16);
      if (var1.posX >= var5.maxX() - var6 || var1.posX <= var5.minX() + var6 || var1.posZ >= var5.maxZ() - var6 || var1.posZ <= var5.minZ() + var6) {
         double var8 = 1.0D - var5.getClosestDistance(var1) / var6;
         var8 = Math.pow(var8, 4.0D);
         double var10 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
         double var12 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
         double var14 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         this.renderEngine.bindTexture(field_175006_g);
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         int var16 = var5.getStatus().func_177766_a();
         float var17 = (float)(var16 >> 16 & 255) / 255.0F;
         float var18 = (float)(var16 >> 8 & 255) / 255.0F;
         float var19 = (float)(var16 & 255) / 255.0F;
         GlStateManager.color(var17, var18, var19, (float)var8);
         GlStateManager.doPolygonOffset(-3.0F, -3.0F);
         GlStateManager.enablePolygonOffset();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.enableAlpha();
         GlStateManager.disableCull();
         float var20 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F;
         float var21 = 0.0F;
         float var22 = 0.0F;
         float var23 = 128.0F;
         var4.startDrawingQuads();
         var4.setTranslation(-var10, -var12, -var14);
         var4.markDirty();
         double var24 = Math.max((double)MathHelper.floor_double(var14 - var6), var5.minZ());
         double var26 = Math.min((double)MathHelper.ceiling_double_int(var14 + var6), var5.maxZ());
         float var28;
         double var29;
         double var31;
         float var33;
         if (var10 > var5.maxX() - var6) {
            var28 = 0.0F;

            for(var29 = var24; var29 < var26; var28 += 0.5F) {
               var31 = Math.min(1.0D, var26 - var29);
               var33 = (float)var31 * 0.5F;
               var4.addVertexWithUV(var5.maxX(), 256.0D, var29, (double)(var20 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var5.maxX(), 256.0D, var29 + var31, (double)(var20 + var33 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var5.maxX(), 0.0D, var29 + var31, (double)(var20 + var33 + var28), (double)(var20 + 128.0F));
               var4.addVertexWithUV(var5.maxX(), 0.0D, var29, (double)(var20 + var28), (double)(var20 + 128.0F));
               ++var29;
            }
         }

         if (var10 < var5.minX() + var6) {
            var28 = 0.0F;

            for(var29 = var24; var29 < var26; var28 += 0.5F) {
               var31 = Math.min(1.0D, var26 - var29);
               var33 = (float)var31 * 0.5F;
               var4.addVertexWithUV(var5.minX(), 256.0D, var29, (double)(var20 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var5.minX(), 256.0D, var29 + var31, (double)(var20 + var33 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var5.minX(), 0.0D, var29 + var31, (double)(var20 + var33 + var28), (double)(var20 + 128.0F));
               var4.addVertexWithUV(var5.minX(), 0.0D, var29, (double)(var20 + var28), (double)(var20 + 128.0F));
               ++var29;
            }
         }

         var24 = Math.max((double)MathHelper.floor_double(var10 - var6), var5.minX());
         var26 = Math.min((double)MathHelper.ceiling_double_int(var10 + var6), var5.maxX());
         if (var14 > var5.maxZ() - var6) {
            var28 = 0.0F;

            for(var29 = var24; var29 < var26; var28 += 0.5F) {
               var31 = Math.min(1.0D, var26 - var29);
               var33 = (float)var31 * 0.5F;
               var4.addVertexWithUV(var29, 256.0D, var5.maxZ(), (double)(var20 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var29 + var31, 256.0D, var5.maxZ(), (double)(var20 + var33 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var29 + var31, 0.0D, var5.maxZ(), (double)(var20 + var33 + var28), (double)(var20 + 128.0F));
               var4.addVertexWithUV(var29, 0.0D, var5.maxZ(), (double)(var20 + var28), (double)(var20 + 128.0F));
               ++var29;
            }
         }

         if (var14 < var5.minZ() + var6) {
            var28 = 0.0F;

            for(var29 = var24; var29 < var26; var28 += 0.5F) {
               var31 = Math.min(1.0D, var26 - var29);
               var33 = (float)var31 * 0.5F;
               var4.addVertexWithUV(var29, 256.0D, var5.minZ(), (double)(var20 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var29 + var31, 256.0D, var5.minZ(), (double)(var20 + var33 + var28), (double)(var20 + 0.0F));
               var4.addVertexWithUV(var29 + var31, 0.0D, var5.minZ(), (double)(var20 + var33 + var28), (double)(var20 + 128.0F));
               var4.addVertexWithUV(var29, 0.0D, var5.minZ(), (double)(var20 + var28), (double)(var20 + 128.0F));
               ++var29;
            }
         }

         var3.draw();
         var4.setTranslation(0.0D, 0.0D, 0.0D);
         GlStateManager.enableCull();
         GlStateManager.disableAlpha();
         GlStateManager.doPolygonOffset(0.0F, 0.0F);
         GlStateManager.disablePolygonOffset();
         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
      }

   }

   private void func_174971_n() {
      TextureMap var1 = this.mc.getTextureMapBlocks();

      for(int var2 = 0; var2 < this.destroyBlockIcons.length; ++var2) {
         this.destroyBlockIcons[var2] = var1.getAtlasSprite(String.valueOf((new StringBuilder("minecraft:blocks/destroy_stage_")).append(var2)));
      }

   }

   private EntityFX func_174974_b(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      if (this.mc != null && this.mc.func_175606_aa() != null && this.mc.effectRenderer != null) {
         int var16 = this.mc.gameSettings.particleSetting;
         if (var16 == 1 && this.theWorld.rand.nextInt(3) == 0) {
            var16 = 2;
         }

         double var17 = this.mc.func_175606_aa().posX - var3;
         double var19 = this.mc.func_175606_aa().posY - var5;
         double var21 = this.mc.func_175606_aa().posZ - var7;
         if (var1 == EnumParticleTypes.EXPLOSION_HUGE.func_179348_c() && !Config.isAnimatedExplosion()) {
            return null;
         } else if (var1 == EnumParticleTypes.EXPLOSION_LARGE.func_179348_c() && !Config.isAnimatedExplosion()) {
            return null;
         } else if (var1 == EnumParticleTypes.EXPLOSION_NORMAL.func_179348_c() && !Config.isAnimatedExplosion()) {
            return null;
         } else if (var1 == EnumParticleTypes.SUSPENDED.func_179348_c() && !Config.isWaterParticles()) {
            return null;
         } else if (var1 == EnumParticleTypes.SUSPENDED_DEPTH.func_179348_c() && !Config.isVoidParticles()) {
            return null;
         } else if (var1 == EnumParticleTypes.SMOKE_NORMAL.func_179348_c() && !Config.isAnimatedSmoke()) {
            return null;
         } else if (var1 == EnumParticleTypes.SMOKE_LARGE.func_179348_c() && !Config.isAnimatedSmoke()) {
            return null;
         } else if (var1 == EnumParticleTypes.SPELL_MOB.func_179348_c() && !Config.isPotionParticles()) {
            return null;
         } else if (var1 == EnumParticleTypes.SPELL_MOB_AMBIENT.func_179348_c() && !Config.isPotionParticles()) {
            return null;
         } else if (var1 == EnumParticleTypes.SPELL.func_179348_c() && !Config.isPotionParticles()) {
            return null;
         } else if (var1 == EnumParticleTypes.SPELL_INSTANT.func_179348_c() && !Config.isPotionParticles()) {
            return null;
         } else if (var1 == EnumParticleTypes.SPELL_WITCH.func_179348_c() && !Config.isPotionParticles()) {
            return null;
         } else if (var1 == EnumParticleTypes.PORTAL.func_179348_c() && !Config.isAnimatedPortal()) {
            return null;
         } else if (var1 == EnumParticleTypes.FLAME.func_179348_c() && !Config.isAnimatedFlame()) {
            return null;
         } else if (var1 == EnumParticleTypes.REDSTONE.func_179348_c() && !Config.isAnimatedRedstone()) {
            return null;
         } else if (var1 == EnumParticleTypes.DRIP_WATER.func_179348_c() && !Config.isDrippingWaterLava()) {
            return null;
         } else if (var1 == EnumParticleTypes.DRIP_LAVA.func_179348_c() && !Config.isDrippingWaterLava()) {
            return null;
         } else if (var1 == EnumParticleTypes.FIREWORKS_SPARK.func_179348_c() && !Config.isFireworkParticles()) {
            return null;
         } else if (var2) {
            return this.mc.effectRenderer.func_178927_a(var1, var3, var5, var7, var9, var11, var13, var15);
         } else {
            double var23 = 16.0D;
            double var25 = 256.0D;
            if (var1 == EnumParticleTypes.CRIT.func_179348_c()) {
               var25 = 38416.0D;
            }

            if (var17 * var17 + var19 * var19 + var21 * var21 > var25) {
               return null;
            } else if (var16 > 1) {
               return null;
            } else {
               EntityFX var27 = this.mc.effectRenderer.func_178927_a(var1, var3, var5, var7, var9, var11, var13, var15);
               if (var1 == EnumParticleTypes.WATER_BUBBLE.func_179348_c()) {
                  CustomColors.updateWaterFX(var27, this.theWorld, var3, var5, var7);
               }

               if (var1 == EnumParticleTypes.WATER_SPLASH.func_179348_c()) {
                  CustomColors.updateWaterFX(var27, this.theWorld, var3, var5, var7);
               }

               if (var1 == EnumParticleTypes.WATER_DROP.func_179348_c()) {
                  CustomColors.updateWaterFX(var27, this.theWorld, var3, var5, var7);
               }

               if (var1 == EnumParticleTypes.TOWN_AURA.func_179348_c()) {
                  CustomColors.updateMyceliumFX(var27);
               }

               if (var1 == EnumParticleTypes.PORTAL.func_179348_c()) {
                  CustomColors.updatePortalFX(var27);
               }

               if (var1 == EnumParticleTypes.REDSTONE.func_179348_c()) {
                  CustomColors.updateReddustFX(var27, this.theWorld, var3, var5, var7);
               }

               return var27;
            }
         }
      } else {
         return null;
      }
   }

   public void markBlockRangeForRenderUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.markBlocksForUpdate(var1 - 1, var2 - 1, var3 - 1, var4 + 1, var5 + 1, var6 + 1);
   }

   public String getDebugInfoEntities() {
      return String.valueOf((new StringBuilder("E: ")).append(this.countEntitiesRendered).append("/").append(this.countEntitiesTotal).append(", B: ").append(this.countEntitiesHidden).append(", I: ").append(this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered).append(", ").append(Config.getVersionDebug()));
   }

   protected void func_174986_e() {
      this.field_175009_l.clear();
      this.field_174995_M.func_178514_b();
   }

   public void deleteAllDisplayLists() {
   }

   public void onEntityAdded(Entity var1) {
      RandomMobs.entityLoaded(var1, this.theWorld);
      if (Config.isDynamicLights()) {
         DynamicLights.entityAdded(var1, this);
      }

   }

   public void func_174970_a(Entity var1, double var2, ICamera var4, int var5, boolean var6) {
      if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
         this.loadRenderers();
      }

      this.theWorld.theProfiler.startSection("camera");
      double var7 = var1.posX - this.field_174992_B;
      double var9 = var1.posY - this.field_174993_C;
      double var11 = var1.posZ - this.field_174987_D;
      if (this.field_174988_E != var1.chunkCoordX || this.field_174989_F != var1.chunkCoordY || this.field_174990_G != var1.chunkCoordZ || var7 * var7 + var9 * var9 + var11 * var11 > 16.0D) {
         this.field_174992_B = var1.posX;
         this.field_174993_C = var1.posY;
         this.field_174987_D = var1.posZ;
         this.field_174988_E = var1.chunkCoordX;
         this.field_174989_F = var1.chunkCoordY;
         this.field_174990_G = var1.chunkCoordZ;
         this.field_175008_n.func_178163_a(var1.posX, var1.posZ);
      }

      if (Config.isDynamicLights()) {
         DynamicLights.update(this);
      }

      this.theWorld.theProfiler.endStartSection("renderlistcamera");
      double var13 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2;
      double var15 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2;
      double var17 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2;
      this.field_174996_N.func_178004_a(var13, var15, var17);
      this.theWorld.theProfiler.endStartSection("cull");
      if (this.field_175001_U != null) {
         Frustrum var19 = new Frustrum(this.field_175001_U);
         var19.setPosition(this.field_175003_W.x, this.field_175003_W.y, this.field_175003_W.z);
         var4 = var19;
      }

      this.mc.mcProfiler.endStartSection("culling");
      BlockPos var35 = new BlockPos(var13, var15 + (double)var1.getEyeHeight(), var17);
      RenderChunk var20 = this.field_175008_n.func_178161_a(var35);
      BlockPos var21 = new BlockPos(MathHelper.floor_double(var13) / 16 * 16, MathHelper.floor_double(var15) / 16 * 16, MathHelper.floor_double(var17) / 16 * 16);
      this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.field_175009_l.isEmpty() || var1.posX != this.field_174997_H || var1.posY != this.field_174998_I || var1.posZ != this.field_174999_J || (double)var1.rotationPitch != this.field_175000_K || (double)var1.rotationYaw != this.field_174994_L;
      this.field_174997_H = var1.posX;
      this.field_174998_I = var1.posY;
      this.field_174999_J = var1.posZ;
      this.field_175000_K = (double)var1.rotationPitch;
      this.field_174994_L = (double)var1.rotationYaw;
      boolean var22 = this.field_175001_U != null;
      Lagometer.timerVisibility.start();
      if (Shaders.isShadowPass) {
         this.glRenderLists = this.renderInfosShadow;
         this.renderInfosEntities = this.renderInfosEntitiesShadow;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
         if (!var22 && this.displayListEntitiesDirty) {
            this.glRenderLists.clear();
            this.renderInfosEntities.clear();
            this.renderInfosTileEntities.clear();
            RenderInfoLazy var23 = new RenderInfoLazy();

            for(int var24 = 0; var24 < this.field_175008_n.field_178164_f.length; ++var24) {
               RenderChunk var25 = this.field_175008_n.field_178164_f[var24];
               var23.setRenderChunk(var25);
               if (!var25.field_178590_b.func_178489_a() || var25.func_178569_m()) {
                  this.glRenderLists.add(var23.getRenderInfo());
               }

               BlockPos var26 = var25.func_178568_j();
               if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(var26))) {
                  this.renderInfosEntities.add(var23.getRenderInfo());
               }

               if (var25.func_178571_g().func_178485_b().size() > 0) {
                  this.renderInfosTileEntities.add(var23.getRenderInfo());
               }
            }
         }
      } else {
         this.glRenderLists = this.renderInfosNormal;
         this.renderInfosEntities = this.renderInfosEntitiesNormal;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
      }

      RenderGlobal.ContainerLocalRenderInformation var36;
      RenderChunk var37;
      if (!var22 && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
         this.displayListEntitiesDirty = false;
         this.glRenderLists.clear();
         this.renderInfosEntities.clear();
         this.renderInfosTileEntities.clear();
         this.visibilityDeque.clear();
         Deque var38 = this.visibilityDeque;
         boolean var40 = this.mc.field_175612_E;
         int var27;
         EnumFacing var32;
         if (var20 != null) {
            boolean var42 = false;
            RenderGlobal.ContainerLocalRenderInformation var44 = new RenderGlobal.ContainerLocalRenderInformation(var20, (EnumFacing)null, 0, (Object)null);
            Set var46 = SET_ALL_FACINGS;
            if (!var46.isEmpty() && var46.size() == 1) {
               Vector3f var31 = this.func_174962_a(var1, var2);
               var32 = EnumFacing.func_176737_a(var31.x, var31.y, var31.z).getOpposite();
               var46.remove(var32);
            }

            if (var46.isEmpty()) {
               var42 = true;
            }

            if (var42 && !var6) {
               this.glRenderLists.add(var44);
            } else {
               if (var6 && this.theWorld.getBlockState(var35).getBlock().isOpaqueCube()) {
                  var40 = false;
               }

               var20.func_178577_a(var5);
               var38.add(var44);
            }
         } else {
            int var28 = var35.getY() > 0 ? 248 : 8;

            for(var27 = -this.renderDistanceChunks; var27 <= this.renderDistanceChunks; ++var27) {
               for(int var29 = -this.renderDistanceChunks; var29 <= this.renderDistanceChunks; ++var29) {
                  RenderChunk var30 = this.field_175008_n.func_178161_a(new BlockPos((var27 << 4) + 8, var28, (var29 << 4) + 8));
                  if (var30 != null && ((ICamera)var4).isBoundingBoxInFrustum(var30.field_178591_c)) {
                     var30.func_178577_a(var5);
                     var38.add(new RenderGlobal.ContainerLocalRenderInformation(var30, (EnumFacing)null, 0, (Object)null));
                  }
               }
            }
         }

         EnumFacing[] var43 = EnumFacing.VALUES;
         var27 = var43.length;

         while(!var38.isEmpty()) {
            var36 = (RenderGlobal.ContainerLocalRenderInformation)var38.poll();
            var37 = var36.field_178036_a;
            EnumFacing var45 = var36.field_178034_b;
            BlockPos var47 = var37.func_178568_j();
            if (!var37.field_178590_b.func_178489_a() || var37.func_178569_m()) {
               this.glRenderLists.add(var36);
            }

            if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(var47))) {
               this.renderInfosEntities.add(var36);
            }

            if (var37.func_178571_g().func_178485_b().size() > 0) {
               this.renderInfosTileEntities.add(var36);
            }

            for(int var48 = 0; var48 < var27; ++var48) {
               var32 = var43[var48];
               if ((!var40 || !var36.field_178035_c.contains(var32.getOpposite())) && (!var40 || var45 == null || var37.func_178571_g().func_178495_a(var45.getOpposite(), var32))) {
                  RenderChunk var33 = this.getRenderChunkOffset(var35, var37, var32);
                  if (var33 != null && var33.func_178577_a(var5) && ((ICamera)var4).isBoundingBoxInFrustum(var33.field_178591_c)) {
                     RenderGlobal.ContainerLocalRenderInformation var34 = new RenderGlobal.ContainerLocalRenderInformation(var33, var32, var36.field_178032_d + 1, (Object)null);
                     var34.field_178035_c.addAll(var36.field_178035_c);
                     var34.field_178035_c.add(var32);
                     var38.add(var34);
                  }
               }
            }
         }
      }

      if (this.field_175002_T) {
         this.func_174984_a(var13, var15, var17);
         this.field_175002_T = false;
      }

      Lagometer.timerVisibility.end();
      if (Shaders.isShadowPass) {
         Shaders.mcProfilerEndSection();
      } else {
         this.field_174995_M.func_178513_e();
         Set var39 = this.field_175009_l;
         this.field_175009_l = Sets.newLinkedHashSet();
         Iterator var41 = this.glRenderLists.iterator();
         Lagometer.timerChunkUpdate.start();

         while(true) {
            do {
               if (!var41.hasNext()) {
                  Lagometer.timerChunkUpdate.end();
                  this.field_175009_l.addAll(var39);
                  this.mc.mcProfiler.endSection();
                  return;
               }

               var36 = (RenderGlobal.ContainerLocalRenderInformation)var41.next();
               var37 = var36.field_178036_a;
            } while(!var37.func_178569_m() && !var39.contains(var37));

            this.displayListEntitiesDirty = true;
            if (this.func_174983_a(var21, var36.field_178036_a)) {
               if (!var37.isPlayerUpdate()) {
                  this.chunksToUpdateForced.add(var37);
               } else {
                  this.mc.mcProfiler.startSection("build near");
                  this.field_174995_M.func_178505_b(var37);
                  var37.func_178575_a(false);
                  this.mc.mcProfiler.endSection();
               }
            } else {
               this.field_175009_l.add(var37);
            }
         }
      }
   }

   public void func_174979_m() {
      this.displayListEntitiesDirty = true;
   }

   private void func_174982_a(EnumWorldBlockLayer var1) {
      this.mc.entityRenderer.func_180436_i();
      if (OpenGlHelper.func_176075_f()) {
         GL11.glEnableClientState(32884);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glEnableClientState(32888);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glEnableClientState(32888);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glEnableClientState(32886);
      }

      if (Config.isShaders()) {
         ShadersRender.preRenderChunkLayer();
      }

      this.field_174996_N.func_178001_a(var1);
      if (Config.isShaders()) {
         ShadersRender.postRenderChunkLayer();
      }

      if (OpenGlHelper.func_176075_f()) {
         List var2 = DefaultVertexFormats.field_176600_a.func_177343_g();
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            VertexFormatElement var4 = (VertexFormatElement)var3.next();
            VertexFormatElement.EnumUseage var5 = var4.func_177375_c();
            int var6 = var4.func_177369_e();
            switch(var5) {
            case POSITION:
               GL11.glDisableClientState(32884);
               break;
            case UV:
               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var6);
               GL11.glDisableClientState(32888);
               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
               break;
            case COLOR:
               GL11.glDisableClientState(32886);
               GlStateManager.func_179117_G();
            }
         }
      }

      this.mc.entityRenderer.func_175072_h();
   }

   public void func_174961_a(String var1, BlockPos var2) {
      ISound var3 = (ISound)this.mapSoundPositions.get(var2);
      if (var3 != null) {
         this.mc.getSoundHandler().stopSound(var3);
         this.mapSoundPositions.remove(var2);
      }

      if (var1 != null) {
         ItemRecord var4 = ItemRecord.getRecord(var1);
         if (var4 != null) {
            this.mc.ingameGUI.setRecordPlayingMessage(var4.getRecordNameLocal());
         }

         ResourceLocation var5 = null;
         if (Reflector.ForgeItemRecord_getRecordResource.exists() && var4 != null) {
            var5 = (ResourceLocation)Reflector.call(var4, Reflector.ForgeItemRecord_getRecordResource, var1);
         }

         if (var5 == null) {
            var5 = new ResourceLocation(var1);
         }

         PositionedSoundRecord var6 = PositionedSoundRecord.createRecordSoundAtPosition(var5, (float)var2.getX(), (float)var2.getY(), (float)var2.getZ());
         this.mapSoundPositions.put(var2, var6);
         this.mc.getSoundHandler().playSound(var6);
      }

   }

   public void func_180446_a(Entity var1, ICamera var2, float var3) {
      int var4 = 0;
      if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
         var4 = Reflector.callInt(Reflector.MinecraftForgeClient_getRenderPass);
      }

      if (this.renderEntitiesStartupCounter > 0) {
         if (var4 > 0) {
            return;
         }

         --this.renderEntitiesStartupCounter;
      } else {
         double var5 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var3;
         double var7 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var3;
         double var9 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var3;
         this.theWorld.theProfiler.startSection("prepare");
         TileEntityRendererDispatcher.instance.func_178470_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRendererObj, this.mc.func_175606_aa(), var3);
         this.field_175010_j.func_180597_a(this.theWorld, this.mc.fontRendererObj, this.mc.func_175606_aa(), this.mc.pointedEntity, this.mc.gameSettings, var3);
         if (var4 == 0) {
            this.countEntitiesTotal = 0;
            this.countEntitiesRendered = 0;
            this.countEntitiesHidden = 0;
            this.countTileEntitiesRendered = 0;
         }

         Entity var11 = this.mc.func_175606_aa();
         double var12 = var11.lastTickPosX + (var11.posX - var11.lastTickPosX) * (double)var3;
         double var14 = var11.lastTickPosY + (var11.posY - var11.lastTickPosY) * (double)var3;
         double var16 = var11.lastTickPosZ + (var11.posZ - var11.lastTickPosZ) * (double)var3;
         TileEntityRendererDispatcher.staticPlayerX = var12;
         TileEntityRendererDispatcher.staticPlayerY = var14;
         TileEntityRendererDispatcher.staticPlayerZ = var16;
         this.field_175010_j.func_178628_a(var12, var14, var16);
         this.mc.entityRenderer.func_180436_i();
         this.theWorld.theProfiler.endStartSection("global");
         List var18 = this.theWorld.getLoadedEntityList();
         if (var4 == 0) {
            this.countEntitiesTotal = var18.size();
         }

         if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GlStateManager.disableFog();
         }

         boolean var19 = Reflector.ForgeEntity_shouldRenderInPass.exists();
         boolean var20 = Reflector.ForgeTileEntity_shouldRenderInPass.exists();

         int var21;
         Entity var22;
         for(var21 = 0; var21 < this.theWorld.weatherEffects.size(); ++var21) {
            var22 = (Entity)this.theWorld.weatherEffects.get(var21);
            if (!var19 || Reflector.callBoolean(var22, Reflector.ForgeEntity_shouldRenderInPass, var4)) {
               ++this.countEntitiesRendered;
               if (var22.isInRangeToRender3d(var5, var7, var9)) {
                  this.field_175010_j.renderEntitySimple(var22, var3);
               }
            }
         }

         boolean var23;
         if (this.func_174985_d()) {
            GlStateManager.depthFunc(519);
            GlStateManager.disableFog();
            this.field_175015_z.framebufferClear();
            this.field_175015_z.bindFramebuffer(false);
            this.theWorld.theProfiler.endStartSection("entityOutlines");
            RenderHelper.disableStandardItemLighting();
            this.field_175010_j.func_178632_c(true);
            var21 = 0;

            while(true) {
               if (var21 >= var18.size()) {
                  this.field_175010_j.func_178632_c(false);
                  RenderHelper.enableStandardItemLighting();
                  GlStateManager.depthMask(false);
                  this.field_174991_A.loadShaderGroup(var3);
                  GlStateManager.depthMask(true);
                  this.mc.getFramebuffer().bindFramebuffer(false);
                  GlStateManager.enableFog();
                  GlStateManager.depthFunc(515);
                  GlStateManager.enableDepth();
                  GlStateManager.enableAlpha();
                  break;
               }

               var22 = (Entity)var18.get(var21);
               if (!var19 || Reflector.callBoolean(var22, Reflector.ForgeEntity_shouldRenderInPass, var4)) {
                  var23 = this.mc.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.func_175606_aa()).isPlayerSleeping();
                  boolean var24 = var22.isInRangeToRender3d(var5, var7, var9) && (var22.ignoreFrustumCheck || var2.isBoundingBoxInFrustum(var22.getEntityBoundingBox()) || var22.riddenByEntity == this.mc.thePlayer) && var22 instanceof EntityPlayer;
                  if ((var22 != this.mc.func_175606_aa() || this.mc.gameSettings.thirdPersonView != 0 || var23) && var24) {
                     this.field_175010_j.renderEntitySimple(var22, var3);
                  }
               }

               ++var21;
            }
         }

         this.theWorld.theProfiler.endStartSection("entities");
         var23 = Config.isShaders();
         if (var23) {
            Shaders.beginEntities();
         }

         Iterator var34 = this.renderInfosEntities.iterator();
         boolean var25 = this.mc.gameSettings.fancyGraphics;
         this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();

         RenderGlobal.ContainerLocalRenderInformation var26;
         label304:
         while(var34.hasNext()) {
            var26 = (RenderGlobal.ContainerLocalRenderInformation)var34.next();
            Chunk var27 = this.theWorld.getChunkFromBlockCoords(var26.field_178036_a.func_178568_j());
            Iterator var28 = var27.getEntityLists()[var26.field_178036_a.func_178568_j().getY() / 16].iterator();

            while(true) {
               Entity var29;
               boolean var30;
               while(true) {
                  do {
                     if (!var28.hasNext()) {
                        continue label304;
                     }

                     var29 = (Entity)var28.next();
                  } while(var19 && !Reflector.callBoolean(var29, Reflector.ForgeEntity_shouldRenderInPass, var4));

                  var30 = this.field_175010_j.func_178635_a(var29, var2, var5, var7, var9) || var29.riddenByEntity == this.mc.thePlayer;
                  if (!var30) {
                     break;
                  }

                  boolean var31 = this.mc.func_175606_aa() instanceof EntityLivingBase ? ((EntityLivingBase)this.mc.func_175606_aa()).isPlayerSleeping() : false;
                  if ((var29 != this.mc.func_175606_aa() || this.mc.gameSettings.thirdPersonView != 0 || var31) && (!(var29.posY >= 0.0D) || !(var29.posY < 256.0D) || this.theWorld.isBlockLoaded(new BlockPos(var29)))) {
                     ++this.countEntitiesRendered;
                     if (var29.getClass() == EntityItemFrame.class) {
                        var29.renderDistanceWeight = 0.06D;
                     }

                     this.renderedEntity = var29;
                     if (var23) {
                        Shaders.nextEntity(var29);
                     }

                     this.field_175010_j.renderEntitySimple(var29, var3);
                     this.renderedEntity = null;
                     break;
                  }
               }

               if (!var30 && var29 instanceof EntityWitherSkull) {
                  if (var23) {
                     Shaders.nextEntity(var29);
                  }

                  this.mc.getRenderManager().func_178630_b(var29, var3);
               }
            }
         }

         this.mc.gameSettings.fancyGraphics = var25;
         FontRenderer var35 = TileEntityRendererDispatcher.instance.getFontRenderer();
         if (var23) {
            Shaders.endEntities();
            Shaders.beginBlockEntities();
         }

         this.theWorld.theProfiler.endStartSection("blockentities");
         RenderHelper.enableStandardItemLighting();
         if (Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch.exists()) {
            Reflector.call(TileEntityRendererDispatcher.instance, Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch);
         }

         var34 = this.renderInfosTileEntities.iterator();

         TileEntity var36;
         label268:
         while(var34.hasNext()) {
            var26 = (RenderGlobal.ContainerLocalRenderInformation)var34.next();
            Iterator var37 = var26.field_178036_a.func_178571_g().func_178485_b().iterator();

            while(true) {
               while(true) {
                  if (!var37.hasNext()) {
                     continue label268;
                  }

                  var36 = (TileEntity)var37.next();
                  if (!var20) {
                     break;
                  }

                  if (Reflector.callBoolean(var36, Reflector.ForgeTileEntity_shouldRenderInPass, var4)) {
                     AxisAlignedBB var39 = (AxisAlignedBB)Reflector.call(var36, Reflector.ForgeTileEntity_getRenderBoundingBox);
                     if (var39 == null || var2.isBoundingBoxInFrustum(var39)) {
                        break;
                     }
                  }
               }

               Class var40 = var36.getClass();
               if (var40 == TileEntitySign.class && !Config.zoomMode) {
                  EntityPlayerSP var42 = this.mc.thePlayer;
                  double var32 = var36.getDistanceSq(var42.posX, var42.posY, var42.posZ);
                  if (var32 > 256.0D) {
                     var35.enabled = false;
                  }
               }

               if (var23) {
                  Shaders.nextBlockEntity(var36);
               }

               TileEntityRendererDispatcher.instance.func_180546_a(var36, var3, -1);
               ++this.countTileEntitiesRendered;
               var35.enabled = true;
            }
         }

         if (Reflector.ForgeTileEntityRendererDispatcher_drawBatch.exists()) {
            Reflector.call(TileEntityRendererDispatcher.instance, Reflector.ForgeTileEntityRendererDispatcher_drawBatch, var4);
         }

         this.func_180443_s();
         var34 = this.damagedBlocks.values().iterator();

         while(var34.hasNext()) {
            DestroyBlockProgress var38 = (DestroyBlockProgress)var34.next();
            BlockPos var41 = var38.func_180246_b();
            var36 = this.theWorld.getTileEntity(var41);
            if (var36 instanceof TileEntityChest) {
               TileEntityChest var43 = (TileEntityChest)var36;
               if (var43.adjacentChestXNeg != null) {
                  var41 = var41.offset(EnumFacing.WEST);
                  var36 = this.theWorld.getTileEntity(var41);
               } else if (var43.adjacentChestZNeg != null) {
                  var41 = var41.offset(EnumFacing.NORTH);
                  var36 = this.theWorld.getTileEntity(var41);
               }
            }

            Block var44 = this.theWorld.getBlockState(var41).getBlock();
            boolean var45;
            if (var20) {
               var45 = false;
               if (var36 != null && Reflector.callBoolean(var36, Reflector.ForgeTileEntity_shouldRenderInPass, var4) && Reflector.callBoolean(var36, Reflector.ForgeTileEntity_canRenderBreaking)) {
                  AxisAlignedBB var33 = (AxisAlignedBB)Reflector.call(var36, Reflector.ForgeTileEntity_getRenderBoundingBox);
                  if (var33 != null) {
                     var45 = var2.isBoundingBoxInFrustum(var33);
                  }
               }
            } else {
               var45 = var36 != null && (var44 instanceof BlockChest || var44 instanceof BlockEnderChest || var44 instanceof BlockSign || var44 instanceof BlockSkull);
            }

            if (var45) {
               if (var23) {
                  Shaders.nextBlockEntity(var36);
               }

               TileEntityRendererDispatcher.instance.func_180546_a(var36, var3, var38.getPartialBlockDamage());
            }
         }

         this.func_174969_t();
         this.mc.entityRenderer.func_175072_h();
         this.mc.mcProfiler.endSection();
      }

   }

   public void checkOcclusionQueryResult(int var1, int var2) {
      if (OpenGlHelper.shadersSupported && this.field_174991_A != null) {
         this.field_174991_A.createBindFramebuffers(var1, var2);
      }

   }

   static {
      SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList(EnumFacing.VALUES)));
   }

   private void func_174969_t() {
      GlStateManager.disableAlpha();
      GlStateManager.doPolygonOffset(0.0F, 0.0F);
      GlStateManager.disablePolygonOffset();
      GlStateManager.enableAlpha();
      GlStateManager.depthMask(true);
      GlStateManager.popMatrix();
      if (Config.isShaders()) {
         ShadersRender.endBlockDamage();
      }

   }

   private void func_174984_a(double var1, double var3, double var5) {
      this.field_175001_U = new ClippingHelperImpl();
      ((ClippingHelperImpl)this.field_175001_U).init();
      Matrix4f var7 = new Matrix4f(this.field_175001_U.field_178626_c);
      var7.transpose();
      Matrix4f var8 = new Matrix4f(this.field_175001_U.field_178625_b);
      var8.transpose();
      Matrix4f var9 = new Matrix4f();
      var9.mul(var8, var7);
      var9.invert();
      this.field_175003_W.x = var1;
      this.field_175003_W.y = var3;
      this.field_175003_W.z = var5;
      this.field_175004_V[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.field_175004_V[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.field_175004_V[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.field_175004_V[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.field_175004_V[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.field_175004_V[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.field_175004_V[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_175004_V[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for(int var10 = 0; var10 < 8; ++var10) {
         var9.transform(this.field_175004_V[var10]);
         Vector4f var10000 = this.field_175004_V[var10];
         var10000.x /= this.field_175004_V[var10].w;
         var10000 = this.field_175004_V[var10];
         var10000.y /= this.field_175004_V[var10].w;
         var10000 = this.field_175004_V[var10];
         var10000.z /= this.field_175004_V[var10].w;
         this.field_175004_V[var10].w = 1.0F;
      }

   }

   public void func_180440_a(int var1, BlockPos var2, int var3) {
      switch(var1) {
      case 1013:
      case 1018:
         if (this.mc.func_175606_aa() != null) {
            double var4 = (double)var2.getX() - this.mc.func_175606_aa().posX;
            double var6 = (double)var2.getY() - this.mc.func_175606_aa().posY;
            double var8 = (double)var2.getZ() - this.mc.func_175606_aa().posZ;
            double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
            double var12 = this.mc.func_175606_aa().posX;
            double var14 = this.mc.func_175606_aa().posY;
            double var16 = this.mc.func_175606_aa().posZ;
            if (var10 > 0.0D) {
               var12 += var4 / var10 * 2.0D;
               var14 += var6 / var10 * 2.0D;
               var16 += var8 / var10 * 2.0D;
            }

            if (var1 == 1013) {
               this.theWorld.playSound(var12, var14, var16, "mob.wither.spawn", 1.0F, 1.0F, false);
            } else {
               this.theWorld.playSound(var12, var14, var16, "mob.enderdragon.end", 5.0F, 1.0F, false);
            }
         }
      default:
      }
   }

   public void sendBlockBreakProgress(int var1, BlockPos var2, int var3) {
      if (var3 >= 0 && var3 < 10) {
         DestroyBlockProgress var4 = (DestroyBlockProgress)this.damagedBlocks.get(var1);
         if (var4 == null || var4.func_180246_b().getX() != var2.getX() || var4.func_180246_b().getY() != var2.getY() || var4.func_180246_b().getZ() != var2.getZ()) {
            var4 = new DestroyBlockProgress(var1, var2);
            this.damagedBlocks.put(var1, var4);
         }

         var4.setPartialBlockDamage(var3);
         var4.setCloudUpdateTick(this.cloudTickCounter);
      } else {
         this.damagedBlocks.remove(var1);
      }

   }

   public RenderChunk getRenderChunk(BlockPos var1) {
      return this.field_175008_n.func_178161_a(var1);
   }

   private void func_174972_a(EnumParticleTypes var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      this.func_180442_a(var1.func_179348_c(), var1.func_179344_e(), var2, var4, var6, var8, var10, var12, var14);
   }

   static final class SwitchEnumUseage {
      static final int[] field_178037_a = new int[VertexFormatElement.EnumUseage.values().length];

      static {
         try {
            field_178037_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178037_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178037_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static class ContainerLocalRenderInformation {
      final EnumFacing field_178034_b;
      final Set field_178035_c;
      final int field_178032_d;
      final RenderChunk field_178036_a;

      ContainerLocalRenderInformation(RenderChunk var1, EnumFacing var2, int var3, Object var4) {
         this(var1, var2, var3);
      }

      public ContainerLocalRenderInformation(RenderChunk var1, EnumFacing var2, int var3) {
         this.field_178035_c = EnumSet.noneOf(EnumFacing.class);
         this.field_178036_a = var1;
         this.field_178034_b = var2;
         this.field_178032_d = var3;
      }
   }
}
