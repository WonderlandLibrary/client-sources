package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import net.aaB;
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
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.ChunkRenderContainer;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal$ContainerLocalRenderInformation;
import net.minecraft.client.renderer.RenderGlobal$RenderGlobal$2;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VboRenderList;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Matrix4f;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.optifine.ChunkUtils;
import net.optifine.CloudRenderer;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lagometer;
import net.optifine.RandomMobs;
import net.optifine.Reflector;
import net.optifine.RenderInfoLazy;
import net.shadersmod.client.Shaders;
import net.shadersmod.client.ShadersRender;
import net.shadersmod.client.ShadowUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
   public static boolean ak;
   public final Minecraft mc;
   private final TextureManager renderEngine;
   private final RenderManager renderManager;
   private WorldClient theWorld;
   private Set chunksToUpdate = Sets.newLinkedHashSet();
   private List renderInfos = Lists.newArrayListWithCapacity(69696);
   private final Set field_181024_n = Sets.newHashSet();
   public ViewFrustum viewFrustum;
   private int starGLCallList = -1;
   private int glSkyList = -1;
   private int glSkyList2 = -1;
   private final VertexFormat vertexBufferFormat;
   private VertexBuffer starVBO;
   private VertexBuffer skyVBO;
   private VertexBuffer sky2VBO;
   private int cloudTickCounter;
   public final Map damagedBlocks = Maps.newHashMap();
   private final Map mapSoundPositions = Maps.newHashMap();
   private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
   private Framebuffer entityOutlineFramebuffer;
   private ShaderGroup entityOutlineShader;
   private double frustumUpdatePosX = Double.MIN_VALUE;
   private double frustumUpdatePosY = Double.MIN_VALUE;
   private double frustumUpdatePosZ = Double.MIN_VALUE;
   private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
   private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
   private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
   private double lastViewEntityX = Double.MIN_VALUE;
   private double lastViewEntityY = Double.MIN_VALUE;
   private double lastViewEntityZ = Double.MIN_VALUE;
   private double lastViewEntityPitch = Double.MIN_VALUE;
   private double lastViewEntityYaw = Double.MIN_VALUE;
   private final ChunkRenderDispatcher renderDispatcher = new ChunkRenderDispatcher();
   private ChunkRenderContainer renderContainer;
   private int renderDistanceChunks = -1;
   private int renderEntitiesStartupCounter = 2;
   private int countEntitiesTotal;
   private int countEntitiesRendered;
   private int countEntitiesHidden;
   private boolean debugFixTerrainFrustum = false;
   private ClippingHelper debugFixedClippingHelper;
   private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
   private final Vector3d debugTerrainFrustumPosition = new Vector3d();
   private boolean vboEnabled;
   IRenderChunkFactory renderChunkFactory;
   private double prevRenderSortX;
   private double prevRenderSortY;
   private double prevRenderSortZ;
   public boolean displayListEntitiesDirty = true;
   private final CloudRenderer cloudRenderer;
   public Entity Q;
   public Set chunksToResortTransparency = new LinkedHashSet();
   public Set chunksToUpdateForced = new LinkedHashSet();
   private final Deque visibilityDeque = new ArrayDeque();
   private List renderInfosEntities = new ArrayList(1024);
   private List renderInfosTileEntities = new ArrayList(1024);
   private final List renderInfosNormal = new ArrayList(1024);
   private final List renderInfosEntitiesNormal = new ArrayList(1024);
   private final List renderInfosTileEntitiesNormal = new ArrayList(1024);
   private final List renderInfosShadow = new ArrayList(1024);
   private final List renderInfosEntitiesShadow = new ArrayList(1024);
   private final List renderInfosTileEntitiesShadow = new ArrayList(1024);
   private int renderDistance = 0;
   private int renderDistanceSq = 0;
   private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList(EnumFacing.VALUES)));
   private int countTileEntitiesRendered;

   public RenderGlobal(Minecraft var1) {
      this.cloudRenderer = new CloudRenderer(var1);
      this.mc = var1;
      this.renderManager = var1.getRenderManager();
      this.renderEngine = var1.getTextureManager();
      this.renderEngine.bindTexture(locationForcefieldPng);
      GL11.glTexParameteri(3553, 10242, 10497);
      GL11.glTexParameteri(3553, 10243, 10497);
      GlStateManager.bindTexture(0);
      this.updateDestroyBlockIcons();
      this.vboEnabled = OpenGlHelper.useVbo();
      if(this.vboEnabled) {
         this.renderContainer = new VboRenderList();
         this.renderChunkFactory = new VboChunkFactory();
      } else {
         this.renderContainer = new RenderList();
         this.renderChunkFactory = new ListChunkFactory();
      }

      this.vertexBufferFormat = new VertexFormat();
      this.vertexBufferFormat.func_181721_a(new VertexFormatElement(0, VertexFormatElement$EnumType.FLOAT, VertexFormatElement$EnumUsage.POSITION, 3));
      this.generateStars();
      this.generateSky();
      this.generateSky2();
   }

   public void onResourceManagerReload(IResourceManager var1) {
      this.updateDestroyBlockIcons();
   }

   private void updateDestroyBlockIcons() {
      TextureMap var1 = this.mc.getTextureMapBlocks();

      for(int var2 = 0; var2 < this.destroyBlockIcons.length; ++var2) {
         this.destroyBlockIcons[var2] = var1.getAtlasSprite("minecraft:blocks/destroy_stage_" + var2);
      }

   }

   public void makeEntityOutlineShader() {
      if(OpenGlHelper.shadersSupported) {
         if(ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
         }

         ResourceLocation var1 = new ResourceLocation("shaders/post/entity_outline.json");

         try {
            this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), var1);
            this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
         } catch (JsonSyntaxException | IOException var3) {
            LOGGER.warn("Failed to load shader: " + var1, var3);
            this.entityOutlineShader = null;
            this.entityOutlineFramebuffer = null;
         }
      } else {
         this.entityOutlineShader = null;
         this.entityOutlineFramebuffer = null;
      }

   }

   public void renderEntityOutlineFramebuffer() {
      if(this.isRenderEntityOutlines()) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
         this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
         GlStateManager.disableBlend();
      }

   }

   protected boolean isRenderEntityOutlines() {
      return false;
   }

   private void generateSky2() {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      if(this.sky2VBO != null) {
         this.sky2VBO.deleteGlBuffers();
      }

      if(this.glSkyList2 >= 0) {
         GLAllocation.deleteDisplayLists(this.glSkyList2);
         this.glSkyList2 = -1;
      }

      if(this.vboEnabled) {
         this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
         this.renderSky(var2, -16.0F, true);
         var2.finishDrawing();
         var2.reset();
         this.sky2VBO.func_181722_a(var2.getByteBuffer());
      } else {
         this.glSkyList2 = GLAllocation.generateDisplayLists(1);
         GL11.glNewList(this.glSkyList2, 4864);
         this.renderSky(var2, -16.0F, true);
         var1.draw();
         GL11.glEndList();
      }

   }

   private void generateSky() {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      if(this.skyVBO != null) {
         this.skyVBO.deleteGlBuffers();
      }

      if(this.glSkyList >= 0) {
         GLAllocation.deleteDisplayLists(this.glSkyList);
         this.glSkyList = -1;
      }

      if(this.vboEnabled) {
         this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
         this.renderSky(var2, 16.0F, false);
         var2.finishDrawing();
         var2.reset();
         this.skyVBO.func_181722_a(var2.getByteBuffer());
      } else {
         this.glSkyList = GLAllocation.generateDisplayLists(1);
         GL11.glNewList(this.glSkyList, 4864);
         this.renderSky(var2, 16.0F, false);
         var1.draw();
         GL11.glEndList();
      }

   }

   private void renderSky(WorldRenderer var1, float var2, boolean var3) {
      boolean var4 = true;
      boolean var5 = true;
      var1.begin(7, DefaultVertexFormats.POSITION);

      for(int var6 = -384; var6 <= 384; var6 += 64) {
         for(int var7 = -384; var7 <= 384; var7 += 64) {
            float var8 = (float)var6;
            float var9 = (float)(var6 + 64);
            var9 = (float)var6;
            var8 = (float)(var6 + 64);
            var1.pos((double)var8, (double)var2, (double)var7).endVertex();
            var1.pos((double)var9, (double)var2, (double)var7).endVertex();
            var1.pos((double)var9, (double)var2, (double)(var7 + 64)).endVertex();
            var1.pos((double)var8, (double)var2, (double)(var7 + 64)).endVertex();
         }
      }

   }

   private void generateStars() {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      if(this.starVBO != null) {
         this.starVBO.deleteGlBuffers();
      }

      if(this.starGLCallList >= 0) {
         GLAllocation.deleteDisplayLists(this.starGLCallList);
         this.starGLCallList = -1;
      }

      if(this.vboEnabled) {
         this.starVBO = new VertexBuffer(this.vertexBufferFormat);
         this.renderStars(var2);
         var2.finishDrawing();
         var2.reset();
         this.starVBO.func_181722_a(var2.getByteBuffer());
      } else {
         this.starGLCallList = GLAllocation.generateDisplayLists(1);
         GlStateManager.pushMatrix();
         GL11.glNewList(this.starGLCallList, 4864);
         this.renderStars(var2);
         var1.draw();
         GL11.glEndList();
         GlStateManager.popMatrix();
      }

   }

   private void renderStars(WorldRenderer var1) {
      Random var2 = new Random(10842L);
      var1.begin(7, DefaultVertexFormats.POSITION);

      for(int var3 = 0; var3 < 1500; ++var3) {
         double var4 = (double)(var2.nextFloat() * 2.0F - 1.0F);
         double var6 = (double)(var2.nextFloat() * 2.0F - 1.0F);
         double var8 = (double)(var2.nextFloat() * 2.0F - 1.0F);
         double var10 = (double)(0.15F + var2.nextFloat() * 0.1F);
         double var12 = var4 * var4 + var6 * var6 + var8 * var8;
         if(var12 < 1.0D && var12 > 0.01D) {
            var12 = 1.0D / Math.sqrt(var12);
            var4 = var4 * var12;
            var6 = var6 * var12;
            var8 = var8 * var12;
            double var14 = var4 * 100.0D;
            double var16 = var6 * 100.0D;
            double var18 = var8 * 100.0D;
            double var20 = Math.atan2(var4, var8);
            double var22 = (double)MathHelper.sin(var20);
            double var24 = (double)MathHelper.cos(var20);
            double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
            double var28 = (double)MathHelper.sin(var26);
            double var30 = (double)MathHelper.cos(var26);
            double var32 = var2.nextDouble() * 3.141592653589793D * 2.0D;
            double var34 = (double)MathHelper.sin(var32);
            double var36 = (double)MathHelper.cos(var32);

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
               var1.pos(var14 + var55, var16 + var51, var18 + var57).endVertex();
            }
         }
      }

   }

   public void setWorldAndLoadRenderers(WorldClient var1) {
      if(this.theWorld != null) {
         this.theWorld.removeWorldAccess(this);
      }

      this.frustumUpdatePosX = Double.MIN_VALUE;
      this.frustumUpdatePosY = Double.MIN_VALUE;
      this.frustumUpdatePosZ = Double.MIN_VALUE;
      this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
      this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
      this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
      this.renderManager.set(var1);
      this.theWorld = var1;
      if(Config.al()) {
         DynamicLights.clear();
      }

      var1.addWorldAccess(this);
      this.loadRenderers();
   }

   public void loadRenderers() {
      // $FF: Couldn't be decompiled
   }

   protected void stopChunkUpdates() {
      this.chunksToUpdate.clear();
      this.renderDispatcher.stopChunkUpdates();
   }

   public void createBindEntityOutlineFbs(int var1, int var2) {
      if(OpenGlHelper.shadersSupported && this.entityOutlineShader != null) {
         this.entityOutlineShader.createBindFramebuffers(var1, var2);
      }

   }

   public void renderEntities(Entity param1, ICamera param2, float param3) {
      // $FF: Couldn't be decompiled
   }

   public String getDebugInfoRenders() {
      int var1 = this.viewFrustum.renderChunks.length;
      int var2 = 0;

      for(Object var4 : this.renderInfos) {
         RenderGlobal$ContainerLocalRenderInformation var5 = (RenderGlobal$ContainerLocalRenderInformation)var4;
         CompiledChunk var6 = var5.renderChunk.compiledChunk;
         if(var6 != CompiledChunk.DUMMY && !var6.isEmpty()) {
            ++var2;
         }
      }

      return String.format("C: %d/%d %sD: %d, %s", new Object[]{Integer.valueOf(var2), Integer.valueOf(var1), this.mc.renderChunksMany?"(s) ":"", Integer.valueOf(this.renderDistanceChunks), this.renderDispatcher.getDebugInfo()});
   }

   public String getDebugInfoEntities() {
      return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersionDebug();
   }

   public void setupTerrain(Entity var1, double var2, ICamera var4, int var5, boolean var6) {
      if(this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
         this.loadRenderers();
      }

      this.theWorld.theProfiler.startSection("camera");
      double var7 = var1.posX - this.frustumUpdatePosX;
      double var9 = var1.posY - this.frustumUpdatePosY;
      double var11 = var1.posZ - this.frustumUpdatePosZ;
      if(this.frustumUpdatePosChunkX != var1.chunkCoordX || this.frustumUpdatePosChunkY != var1.chunkCoordY || this.frustumUpdatePosChunkZ != var1.chunkCoordZ || var7 * var7 + var9 * var9 + var11 * var11 > 16.0D) {
         this.frustumUpdatePosX = var1.posX;
         this.frustumUpdatePosY = var1.posY;
         this.frustumUpdatePosZ = var1.posZ;
         this.frustumUpdatePosChunkX = var1.chunkCoordX;
         this.frustumUpdatePosChunkY = var1.chunkCoordY;
         this.frustumUpdatePosChunkZ = var1.chunkCoordZ;
         this.viewFrustum.updateChunkPositions(var1.posX, var1.posZ);
      }

      if(Config.al()) {
         DynamicLights.update(this);
      }

      this.theWorld.theProfiler.endStartSection("renderlistcamera");
      double var13 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * var2;
      double var15 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * var2;
      double var17 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * var2;
      this.renderContainer.initialize(var13, var15, var17);
      this.theWorld.theProfiler.endStartSection("cull");
      if(this.debugFixedClippingHelper != null) {
         Frustum var19 = new Frustum(this.debugFixedClippingHelper);
         var19.setPosition(this.debugTerrainFrustumPosition.field_181059_a, this.debugTerrainFrustumPosition.field_181060_b, this.debugTerrainFrustumPosition.field_181061_c);
         var4 = var19;
      }

      this.mc.mcProfiler.endStartSection("culling");
      BlockPos var37 = new BlockPos(var13, var15 + (double)var1.getEyeHeight(), var17);
      RenderChunk var20 = this.viewFrustum.getRenderChunk(var37);
      BlockPos var21 = new BlockPos(MathHelper.floor_double(var13 / 16.0D) * 16, MathHelper.floor_double(var15 / 16.0D) * 16, MathHelper.floor_double(var17 / 16.0D) * 16);
      this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || var1.posX != this.lastViewEntityX || var1.posY != this.lastViewEntityY || var1.posZ != this.lastViewEntityZ || (double)var1.rotationPitch != this.lastViewEntityPitch || (double)var1.rotationYaw != this.lastViewEntityYaw;
      this.lastViewEntityX = var1.posX;
      this.lastViewEntityY = var1.posY;
      this.lastViewEntityZ = var1.posZ;
      this.lastViewEntityPitch = (double)var1.rotationPitch;
      this.lastViewEntityYaw = (double)var1.rotationYaw;
      boolean var22 = this.debugFixedClippingHelper != null;
      Lagometer.timerVisibility.start();
      if(Shaders.isShadowPass) {
         this.renderInfos = this.renderInfosShadow;
         this.renderInfosEntities = this.renderInfosEntitiesShadow;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
         if(this.displayListEntitiesDirty) {
            this.renderInfos.clear();
            this.renderInfosEntities.clear();
            this.renderInfosTileEntities.clear();
            RenderInfoLazy var23 = new RenderInfoLazy();
            Iterator var24 = ShadowUtils.makeShadowChunkIterator(this.theWorld, var2, var1, this.renderDistanceChunks, this.viewFrustum);

            while(var24.hasNext()) {
               RenderChunk var25 = (RenderChunk)var24.next();
               var23.setRenderChunk(var25);
               if(!var25.compiledChunk.isEmpty() || var25.isNeedsUpdate()) {
                  this.renderInfos.add(var23.getRenderInfo());
               }

               BlockPos var26 = var25.getPosition();
               if(ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(var26))) {
                  this.renderInfosEntities.add(var23.getRenderInfo());
               }

               if(!var25.getCompiledChunk().getTileEntities().isEmpty()) {
                  this.renderInfosTileEntities.add(var23.getRenderInfo());
               }
            }
         }
      } else {
         this.renderInfos = this.renderInfosNormal;
         this.renderInfosEntities = this.renderInfosEntitiesNormal;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
      }

      if(this.displayListEntitiesDirty && !Shaders.isShadowPass) {
         this.displayListEntitiesDirty = false;
         this.renderInfos.clear();
         this.renderInfosEntities.clear();
         this.renderInfosTileEntities.clear();
         this.visibilityDeque.clear();
         Deque var38 = this.visibilityDeque;
         boolean var40 = this.mc.renderChunksMany;
         boolean var42 = false;
         RenderGlobal$ContainerLocalRenderInformation var46 = new RenderGlobal$ContainerLocalRenderInformation(var20, (EnumFacing)null, 0, (Object)null);
         Set var27 = SET_ALL_FACINGS;
         if(var27.size() == 1) {
            Vector3f var28 = this.getViewVector(var1, var2);
            EnumFacing var29 = EnumFacing.getFacingFromVector(var28.x, var28.y, var28.z).getOpposite();
            var27.remove(var29);
         }

         if(var27.isEmpty()) {
            var42 = true;
         }

         this.renderInfos.add(var46);
         EnumFacing[] var44 = EnumFacing.VALUES;
         int var47 = var44.length;

         while(!var38.isEmpty()) {
            RenderGlobal$ContainerLocalRenderInformation var49 = (RenderGlobal$ContainerLocalRenderInformation)var38.poll();
            RenderChunk var50 = var49.renderChunk;
            EnumFacing var51 = var49.facing;
            BlockPos var30 = var50.getPosition();
            if(!var50.compiledChunk.isEmpty() || var50.isNeedsUpdate()) {
               this.renderInfos.add(var49);
            }

            if(ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(var30))) {
               this.renderInfosEntities.add(var49);
            }

            if(!var50.getCompiledChunk().getTileEntities().isEmpty()) {
               this.renderInfosTileEntities.add(var49);
            }

            for(EnumFacing var34 : var44) {
               if(!var49.setFacing.contains(var34.getOpposite()) && var50.getCompiledChunk().isVisible(var51.getOpposite(), var34)) {
                  RenderChunk var35 = this.func_181562_a(var37, var50, var34);
                  if(var35.setFrameIndex(var5) && ((ICamera)var4).isBoundingBoxInFrustum(var35.boundingBox)) {
                     RenderGlobal$ContainerLocalRenderInformation var36 = new RenderGlobal$ContainerLocalRenderInformation(var35, var34, var49.counter + 1, (Object)null);
                     var36.setFacing.addAll(var49.setFacing);
                     var36.setFacing.add(var34);
                     var38.add(var36);
                  }
               }
            }
         }
      }

      if(this.debugFixTerrainFrustum) {
         this.fixTerrainFrustum(var13, var15, var17);
         this.debugFixTerrainFrustum = false;
      }

      Lagometer.timerVisibility.end();
      if(Shaders.isShadowPass) {
         Shaders.mcProfilerEndSection();
      } else {
         this.renderDispatcher.clearChunkUpdates();
         Set var39 = this.chunksToUpdate;
         this.chunksToUpdate = Sets.newLinkedHashSet();
         Iterator var41 = this.renderInfos.iterator();
         Lagometer.timerChunkUpdate.start();

         while(var41.hasNext()) {
            RenderGlobal$ContainerLocalRenderInformation var45 = (RenderGlobal$ContainerLocalRenderInformation)var41.next();
            RenderChunk var48 = var45.renderChunk;
            if(var48.isNeedsUpdate() || var39.contains(var48)) {
               this.displayListEntitiesDirty = true;
               if(this.isPositionInRenderChunk(var21, var45.renderChunk)) {
                  if(!var48.isPlayerUpdate()) {
                     this.chunksToUpdateForced.add(var48);
                  } else {
                     this.mc.mcProfiler.startSection("build near");
                     this.renderDispatcher.updateChunkNow(var48);
                     var48.setNeedsUpdate(false);
                     this.mc.mcProfiler.endSection();
                  }
               } else {
                  this.chunksToUpdate.add(var48);
               }
            }
         }

         Lagometer.timerChunkUpdate.end();
         this.chunksToUpdate.addAll(var39);
         this.mc.mcProfiler.endSection();
      }

   }

   private boolean isPositionInRenderChunk(BlockPos var1, RenderChunk var2) {
      BlockPos var3 = var2.getPosition();
      return MathHelper.abs_int(var1.getX() - var3.getX()) <= 16 && MathHelper.abs_int(var1.getY() - var3.getY()) <= 16 && MathHelper.abs_int(var1.getZ() - var3.getZ()) <= 16;
   }

   private Set getVisibleFacings(BlockPos var1) {
      VisGraph var2 = new VisGraph();
      BlockPos var3 = new BlockPos(var1.getX() >> 4 << 4, var1.getY() >> 4 << 4, var1.getZ() >> 4 << 4);
      Chunk var4 = this.theWorld.getChunkFromBlockCoords(var3);

      for(BlockPos$MutableBlockPos var6 : BlockPos.getAllInBoxMutable(var3, var3.a(15, 15, 15))) {
         if(var4.getBlock(var6).isOpaqueCube()) {
            var2.func_178606_a(var6);
         }
      }

      return var2.func_178609_b(var1);
   }

   private RenderChunk func_181562_a(BlockPos var1, RenderChunk var2, EnumFacing var3) {
      BlockPos var4 = var2.getPositionOffset16(var3);
      if(var4.getY() >= 0 && var4.getY() < 256) {
         int var5 = MathHelper.abs_int(var1.getX() - var4.getX());
         int var6 = MathHelper.abs_int(var1.getZ() - var4.getZ());
         if(Config.aT()) {
            if(var5 > this.renderDistance || var6 > this.renderDistance) {
               return null;
            }
         } else {
            int var7 = var5 * var5 + var6 * var6;
            if(var7 > this.renderDistanceSq) {
               return null;
            }
         }

         return this.viewFrustum.getRenderChunk(var4);
      } else {
         return null;
      }
   }

   private void fixTerrainFrustum(double var1, double var3, double var5) {
      this.debugFixedClippingHelper = new ClippingHelperImpl();
      ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
      Matrix4f var7 = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
      var7.transpose();
      Matrix4f var8 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
      var8.transpose();
      Matrix4f var9 = new Matrix4f();
      Matrix4f.mul(var8, var7, var9);
      var9.invert();
      this.debugTerrainFrustumPosition.field_181059_a = var1;
      this.debugTerrainFrustumPosition.field_181060_b = var3;
      this.debugTerrainFrustumPosition.field_181061_c = var5;
      this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for(int var10 = 0; var10 < 8; ++var10) {
         Matrix4f.transform(var9, this.debugTerrainMatrix[var10], this.debugTerrainMatrix[var10]);
         this.debugTerrainMatrix[var10].x /= this.debugTerrainMatrix[var10].w;
         this.debugTerrainMatrix[var10].y /= this.debugTerrainMatrix[var10].w;
         this.debugTerrainMatrix[var10].z /= this.debugTerrainMatrix[var10].w;
         this.debugTerrainMatrix[var10].w = 1.0F;
      }

   }

   protected Vector3f getViewVector(Entity var1, double var2) {
      float var4 = (float)((double)var1.prevRotationPitch + (double)(var1.rotationPitch - var1.prevRotationPitch) * var2);
      float var5 = (float)((double)var1.prevRotationYaw + (double)(var1.rotationYaw - var1.prevRotationYaw) * var2);
      if(Minecraft.getInstance().gameSettings.thirdPersonView == 2) {
         var4 += 180.0F;
      }

      float var6 = MathHelper.cos(-var5 * 0.017453292F - 3.1415927F);
      float var7 = MathHelper.sin(-var5 * 0.017453292F - 3.1415927F);
      float var8 = -MathHelper.cos(-var4 * 0.017453292F);
      float var9 = MathHelper.sin(-var4 * 0.017453292F);
      return new Vector3f(var7 * var8, var9, var6 * var8);
   }

   public void renderBlockLayer(EnumWorldBlockLayer var1, double var2, int var4, Entity var5) {
      RenderHelper.disableStandardItemLighting();
      if(var1 == EnumWorldBlockLayer.TRANSLUCENT) {
         this.mc.mcProfiler.startSection("translucent_sort");
         double var6 = var5.posX - this.prevRenderSortX;
         double var8 = var5.posY - this.prevRenderSortY;
         double var10 = var5.posZ - this.prevRenderSortZ;
         if(var6 * var6 + var8 * var8 + var10 * var10 > 1.0D) {
            this.prevRenderSortX = var5.posX;
            this.prevRenderSortY = var5.posY;
            this.prevRenderSortZ = var5.posZ;
            int var12 = 0;
            Iterator var13 = this.renderInfos.iterator();
            this.chunksToResortTransparency.clear();

            while(var13.hasNext()) {
               RenderGlobal$ContainerLocalRenderInformation var14 = (RenderGlobal$ContainerLocalRenderInformation)var13.next();
               if(var14.renderChunk.compiledChunk.isLayerStarted(var1) && var12++ < 15) {
                  this.chunksToResortTransparency.add(var14.renderChunk);
               }
            }
         }

         this.mc.mcProfiler.endSection();
      }

      this.mc.mcProfiler.startSection("filterempty");
      int var15 = 0;
      boolean var7 = var1 == EnumWorldBlockLayer.TRANSLUCENT;
      int var16 = this.renderInfos.size() - 1;
      byte var9 = -1;
      byte var17 = -1;

      for(int var11 = var16; var11 != var9; var11 += var17) {
         RenderChunk var18 = ((RenderGlobal$ContainerLocalRenderInformation)this.renderInfos.get(var11)).renderChunk;
         if(!var18.getCompiledChunk().isLayerEmpty(var1)) {
            ++var15;
            this.renderContainer.addRenderChunk(var18, var1);
         }
      }

      if(Config.aT() && this.mc.entityRenderer.fogStandard) {
         GlStateManager.disableFog();
      }

      this.mc.mcProfiler.endStartSection("render_" + var1);
      this.renderBlockLayer(var1);
      this.mc.mcProfiler.endSection();
   }

   private void renderBlockLayer(EnumWorldBlockLayer var1) {
      this.mc.entityRenderer.enableLightmap();
      if(OpenGlHelper.useVbo()) {
         GL11.glEnableClientState('聴');
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glEnableClientState('聸');
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glEnableClientState('聸');
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glEnableClientState('聶');
      }

      if(Config.isShaders()) {
         ShadersRender.preRenderChunkLayer(var1);
      }

      this.renderContainer.renderChunkLayer(var1);
      if(Config.isShaders()) {
         ShadersRender.postRenderChunkLayer(var1);
      }

      if(OpenGlHelper.useVbo()) {
         for(VertexFormatElement var3 : DefaultVertexFormats.BLOCK.getElements()) {
            VertexFormatElement$EnumUsage var4 = var3.getUsage();
            int var5 = var3.getIndex();
            switch(RenderGlobal$RenderGlobal$2.field_178037_a[var4.ordinal()]) {
            case 1:
               GL11.glDisableClientState('聴');
               break;
            case 2:
               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var5);
               GL11.glDisableClientState('聸');
               OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
               break;
            case 3:
               GL11.glDisableClientState('聶');
               GlStateManager.resetColor();
            }
         }
      }

      this.mc.entityRenderer.disableLightmap();
   }

   private void cleanupDamagedBlocks(Iterator var1) {
      while(var1.hasNext()) {
         DestroyBlockProgress var2 = (DestroyBlockProgress)var1.next();
         int var3 = var2.getCreationCloudUpdateTick();
         if(this.cloudTickCounter - var3 > 400) {
            var1.remove();
         }
      }

   }

   public void updateClouds() {
      if(Config.isShaders() && Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
         Shaders.uninit();
         Shaders.loadShaderPack();
      }

      ++this.cloudTickCounter;
      if(this.cloudTickCounter % 20 == 0) {
         this.cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
      }

   }

   private void renderSkyEnd() {
      if(Config.isSkyEnabled()) {
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
            if(var3 == 1) {
               GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var3 == 2) {
               GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var3 == 3) {
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if(var3 == 4) {
               GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if(var3 == 5) {
               GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            var2.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            var2.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(40, 40, 40, 255).endVertex();
            var2.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(40, 40, 40, 255).endVertex();
            var2.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(40, 40, 40, 255).endVertex();
            var2.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(40, 40, 40, 255).endVertex();
            var1.draw();
            GlStateManager.popMatrix();
         }

         GlStateManager.depthMask(true);
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
      }

   }

   public void renderSky(float var1, int var2) {
      if(Reflector.aD.d()) {
         WorldProvider var20 = this.mc.world.provider;
         Object var22 = Reflector.f(var20, Reflector.aD, new Object[0]);
         Reflector.g(var22, Reflector.k, new Object[]{Float.valueOf(var1), this.theWorld, this.mc});
      } else {
         if(this.mc.world.provider.getDimensionId() == 1) {
            this.renderSkyEnd();
         } else if(this.mc.world.provider.isSurfaceWorld()) {
            GlStateManager.disableTexture2D();
            boolean var3 = Config.isShaders();
            Shaders.disableTexture2D();
            Vec3 var4 = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), var1);
            var4 = CustomColors.getSkyColor(var4, this.mc.world, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0D, this.mc.getRenderViewEntity().posZ);
            Shaders.setSkyColor(var4);
            float var5 = (float)var4.xCoord;
            float var6 = (float)var4.yCoord;
            float var7 = (float)var4.zCoord;
            if(var2 != 2) {
               float var8 = (var5 * 30.0F + var6 * 59.0F + var7 * 11.0F) / 100.0F;
               float var9 = (var5 * 30.0F + var6 * 70.0F) / 100.0F;
               float var10 = (var5 * 30.0F + var7 * 70.0F) / 100.0F;
               var5 = var8;
               var6 = var9;
               var7 = var10;
            }

            GlStateManager.color(var5, var6, var7);
            Tessellator var23 = Tessellator.getInstance();
            WorldRenderer var24 = var23.getWorldRenderer();
            GlStateManager.depthMask(false);
            GlStateManager.enableFog();
            Shaders.enableFog();
            GlStateManager.color(var5, var6, var7);
            Shaders.preSkyList();
            if(Config.isSkyEnabled()) {
               if(this.vboEnabled) {
                  this.skyVBO.bindBuffer();
                  GL11.glEnableClientState('聴');
                  GL11.glVertexPointer(3, 5126, 12, 0L);
                  this.skyVBO.drawArrays(7);
                  this.skyVBO.unbindBuffer();
                  GL11.glDisableClientState('聴');
               } else {
                  GlStateManager.callList(this.glSkyList);
               }
            }

            GlStateManager.disableFog();
            Shaders.disableFog();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            float[] var25 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(var1), var1);
            if(Config.isSunMoonEnabled()) {
               GlStateManager.disableTexture2D();
               Shaders.disableTexture2D();
               GlStateManager.shadeModel(7425);
               GlStateManager.pushMatrix();
               GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
               GlStateManager.rotate(MathHelper.sin(this.theWorld.getCelestialAngleRadians(var1)) < 0.0F?180.0F:0.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
               float var11 = var25[0];
               float var12 = var25[1];
               float var13 = var25[2];
               if(var2 != 2) {
                  float var14 = (var11 * 30.0F + var12 * 59.0F + var13 * 11.0F) / 100.0F;
                  float var15 = (var11 * 30.0F + var12 * 70.0F) / 100.0F;
                  float var16 = (var11 * 30.0F + var13 * 70.0F) / 100.0F;
                  var11 = var14;
                  var12 = var15;
                  var13 = var16;
               }

               var24.begin(6, DefaultVertexFormats.POSITION_COLOR);
               var24.pos(0.0D, 100.0D, 0.0D).color(var11, var12, var13, var25[3]).endVertex();

               for(int var31 = 0; var31 <= 16; ++var31) {
                  float var34 = (float)var31 * 3.1415927F * 2.0F / 16.0F;
                  float var36 = MathHelper.sin(var34);
                  float var17 = MathHelper.cos(var34);
                  var24.pos((double)(var36 * 120.0F), (double)(var17 * 120.0F), (double)(-var17 * 40.0F * var25[3])).color(var25[0], var25[1], var25[2], 0.0F).endVertex();
               }

               var23.draw();
               GlStateManager.popMatrix();
               GlStateManager.shadeModel(7424);
            }

            GlStateManager.enableTexture2D();
            Shaders.enableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            GlStateManager.pushMatrix();
            float var26 = 1.0F - this.theWorld.getRainStrength(var1);
            GlStateManager.color(1.0F, 1.0F, 1.0F, var26);
            GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(var1), var26);
            Shaders.preCelestialRotate();
            GlStateManager.rotate(this.theWorld.getCelestialAngle(var1) * 360.0F, 1.0F, 0.0F, 0.0F);
            Shaders.postCelestialRotate();
            float var27 = 30.0F;
            if(Config.isSunTexture()) {
               this.renderEngine.bindTexture(locationSunPng);
               var24.begin(7, DefaultVertexFormats.POSITION_TEX);
               var24.pos((double)(-var27), 100.0D, (double)(-var27)).tex(0.0D, 0.0D).endVertex();
               var24.pos((double)var27, 100.0D, (double)(-var27)).tex(1.0D, 0.0D).endVertex();
               var24.pos((double)var27, 100.0D, (double)var27).tex(1.0D, 1.0D).endVertex();
               var24.pos((double)(-var27), 100.0D, (double)var27).tex(0.0D, 1.0D).endVertex();
               var23.draw();
            }

            var27 = 20.0F;
            if(Config.isMoonTexture()) {
               this.renderEngine.bindTexture(locationMoonPhasesPng);
               int var29 = this.theWorld.getMoonPhase();
               int var32 = var29 % 4;
               int var35 = var29 / 4 % 2;
               float var37 = (float)var32 / 4.0F;
               float var39 = (float)var35 / 2.0F;
               float var18 = (float)(var32 + 1) / 4.0F;
               float var19 = (float)(var35 + 1) / 2.0F;
               var24.begin(7, DefaultVertexFormats.POSITION_TEX);
               var24.pos((double)(-var27), -100.0D, (double)var27).tex((double)var18, (double)var19).endVertex();
               var24.pos((double)var27, -100.0D, (double)var27).tex((double)var37, (double)var19).endVertex();
               var24.pos((double)var27, -100.0D, (double)(-var27)).tex((double)var37, (double)var39).endVertex();
               var24.pos((double)(-var27), -100.0D, (double)(-var27)).tex((double)var18, (double)var39).endVertex();
               var23.draw();
            }

            GlStateManager.disableTexture2D();
            Shaders.disableTexture2D();
            float var30 = this.theWorld.getStarBrightness(var1) * var26;
            if(var30 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
               GlStateManager.color(var30, var30, var30, var30);
               if(this.vboEnabled) {
                  this.starVBO.bindBuffer();
                  GL11.glEnableClientState('聴');
                  GL11.glVertexPointer(3, 5126, 12, 0L);
                  this.starVBO.drawArrays(7);
                  this.starVBO.unbindBuffer();
                  GL11.glDisableClientState('聴');
               } else {
                  GlStateManager.callList(this.starGLCallList);
               }
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableFog();
            Shaders.enableFog();
            GlStateManager.popMatrix();
            GlStateManager.disableTexture2D();
            Shaders.disableTexture2D();
            GlStateManager.color(0.0F, 0.0F, 0.0F);
            double var33 = this.mc.player.getPositionEyes(var1).yCoord - this.theWorld.getHorizon();
            if(var33 < 0.0D) {
               GlStateManager.pushMatrix();
               GlStateManager.translate(0.0F, 12.0F, 0.0F);
               if(this.vboEnabled) {
                  this.sky2VBO.bindBuffer();
                  GL11.glEnableClientState('聴');
                  GL11.glVertexPointer(3, 5126, 12, 0L);
                  this.sky2VBO.drawArrays(7);
                  this.sky2VBO.unbindBuffer();
                  GL11.glDisableClientState('聴');
               } else {
                  GlStateManager.callList(this.glSkyList2);
               }

               GlStateManager.popMatrix();
               float var38 = -((float)(var33 + 65.0D));
               var24.begin(7, DefaultVertexFormats.POSITION_COLOR);
               var24.pos(-1.0D, (double)var38, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, (double)var38, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, (double)var38, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, (double)var38, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, (double)var38, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, (double)var38, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, (double)var38, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, (double)var38, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
               var24.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
               var23.draw();
            }

            if(this.theWorld.provider.isSkyColored()) {
               GlStateManager.color(var5 * 0.2F + 0.04F, var6 * 0.2F + 0.04F, var7 * 0.6F + 0.1F);
            } else {
               GlStateManager.color(var5, var6, var7);
            }

            if(this.mc.gameSettings.renderDistanceChunks <= 4) {
               GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -((float)(var33 - 16.0D)), 0.0F);
            if(Config.isSkyEnabled()) {
               GlStateManager.callList(this.glSkyList2);
            }

            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            Shaders.enableTexture2D();
            GlStateManager.depthMask(true);
         }

      }
   }

   public void renderClouds(float var1, int var2) {
      if(!Config.isCloudsOff()) {
         if(Reflector.br.d()) {
            WorldProvider var24 = this.mc.world.provider;
            Object var25 = Reflector.f(var24, Reflector.br, new Object[0]);
            Reflector.g(var25, Reflector.k, new Object[]{Float.valueOf(var1), this.theWorld, this.mc});
            return;
         }

         if(this.mc.world.provider.isSurfaceWorld()) {
            if(Config.isShaders()) {
               Shaders.beginClouds();
            }

            if(Config.isCloudsFancy()) {
               this.renderCloudsFancy(var1, var2);
            } else {
               this.cloudRenderer.a(false, this.cloudTickCounter, var1);
               var1 = 0.0F;
               GlStateManager.disableCull();
               float var3 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * (double)var1);
               Tessellator var4 = Tessellator.getInstance();
               WorldRenderer var5 = var4.getWorldRenderer();
               this.renderEngine.bindTexture(locationCloudsPng);
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               if(this.cloudRenderer.shouldUpdateGlList()) {
                  this.cloudRenderer.startUpdateGlList();
                  Vec3 var6 = this.theWorld.getCloudColour(var1);
                  float var7 = (float)var6.xCoord;
                  float var8 = (float)var6.yCoord;
                  float var9 = (float)var6.zCoord;
                  if(var2 != 2) {
                     float var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
                     float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
                     float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
                     var7 = var10;
                     var8 = var11;
                     var9 = var12;
                  }

                  double var26 = (double)((float)this.cloudTickCounter + var1);
                  double var27 = this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * (double)var1 + var26 * 0.029999999329447746D;
                  double var14 = this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * (double)var1;
                  int var16 = MathHelper.floor_double(var27 / 2048.0D);
                  int var17 = MathHelper.floor_double(var14 / 2048.0D);
                  var27 = var27 - (double)(var16 * 2048);
                  var14 = var14 - (double)(var17 * 2048);
                  float var18 = this.theWorld.provider.getCloudHeight() - var3 + 0.33F;
                  var18 = var18 + this.mc.gameSettings.ofCloudsHeight * 128.0F;
                  float var19 = (float)(var27 * 4.8828125E-4D);
                  float var20 = (float)(var14 * 4.8828125E-4D);
                  var5.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

                  for(int var21 = -256; var21 < 256; var21 += 32) {
                     for(int var22 = -256; var22 < 256; var22 += 32) {
                        var5.pos((double)var21, (double)var18, (double)(var22 + 32)).tex((double)((float)var21 * 4.8828125E-4F + var19), (double)((float)(var22 + 32) * 4.8828125E-4F + var20)).color(var7, var8, var9, 0.8F).endVertex();
                        var5.pos((double)(var21 + 32), (double)var18, (double)(var22 + 32)).tex((double)((float)(var21 + 32) * 4.8828125E-4F + var19), (double)((float)(var22 + 32) * 4.8828125E-4F + var20)).color(var7, var8, var9, 0.8F).endVertex();
                        var5.pos((double)(var21 + 32), (double)var18, (double)var22).tex((double)((float)(var21 + 32) * 4.8828125E-4F + var19), (double)((float)var22 * 4.8828125E-4F + var20)).color(var7, var8, var9, 0.8F).endVertex();
                        var5.pos((double)var21, (double)var18, (double)var22).tex((double)((float)var21 * 4.8828125E-4F + var19), (double)((float)var22 * 4.8828125E-4F + var20)).color(var7, var8, var9, 0.8F).endVertex();
                     }
                  }

                  var4.draw();
                  this.cloudRenderer.endUpdateGlList();
               }

               this.cloudRenderer.renderGlList();
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.disableBlend();
               GlStateManager.enableCull();
            }

            if(Config.isShaders()) {
               Shaders.endClouds();
            }
         }
      }

   }

   public boolean hasCloudFog(double var1, double var3, double var5, float var7) {
      return false;
   }

   private void renderCloudsFancy(float var1, int var2) {
      this.cloudRenderer.a(true, this.cloudTickCounter, var1);
      var1 = 0.0F;
      GlStateManager.disableCull();
      float var3 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * (double)var1);
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      double var6 = (double)((float)this.cloudTickCounter + var1);
      double var8 = (this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * (double)var1 + var6 * 0.029999999329447746D) / 12.0D;
      double var10 = (this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * (double)var1) / 12.0D + 0.33000001311302185D;
      float var12 = this.theWorld.provider.getCloudHeight() - var3 + 0.33F;
      var12 = var12 + this.mc.gameSettings.ofCloudsHeight * 128.0F;
      int var13 = MathHelper.floor_double(var8 / 2048.0D);
      int var14 = MathHelper.floor_double(var10 / 2048.0D);
      var8 = var8 - (double)(var13 * 2048);
      var10 = var10 - (double)(var14 * 2048);
      this.renderEngine.bindTexture(locationCloudsPng);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      Vec3 var15 = this.theWorld.getCloudColour(var1);
      float var16 = (float)var15.xCoord;
      float var17 = (float)var15.yCoord;
      float var18 = (float)var15.zCoord;
      if(var2 != 2) {
         float var19 = (var16 * 30.0F + var17 * 59.0F + var18 * 11.0F) / 100.0F;
         float var20 = (var16 * 30.0F + var17 * 70.0F) / 100.0F;
         float var21 = (var16 * 30.0F + var18 * 70.0F) / 100.0F;
         var16 = var19;
         var17 = var20;
         var18 = var21;
      }

      float var43 = var16 * 0.9F;
      float var44 = var17 * 0.9F;
      float var45 = var18 * 0.9F;
      float var22 = var16 * 0.7F;
      float var23 = var17 * 0.7F;
      float var24 = var18 * 0.7F;
      float var25 = var16 * 0.8F;
      float var26 = var17 * 0.8F;
      float var27 = var18 * 0.8F;
      float var28 = (float)MathHelper.floor_double(var8) * 0.00390625F;
      float var29 = (float)MathHelper.floor_double(var10) * 0.00390625F;
      float var30 = (float)(var8 - (double)MathHelper.floor_double(var8));
      float var31 = (float)(var10 - (double)MathHelper.floor_double(var10));
      GlStateManager.scale(12.0F, 1.0F, 12.0F);

      for(int var32 = 0; var32 < 2; ++var32) {
         GlStateManager.colorMask(false, false, false, false);
         this.cloudRenderer.renderGlList();
      }

      if(this.cloudRenderer.shouldUpdateGlList()) {
         this.cloudRenderer.startUpdateGlList();

         for(int var46 = -3; var46 <= 4; ++var46) {
            for(int var33 = -3; var33 <= 4; ++var33) {
               var5.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
               float var34 = (float)(var46 * 8);
               float var35 = (float)(var33 * 8);
               float var36 = var34 - var30;
               float var37 = var35 - var31;
               if(var12 > -5.0F) {
                  var5.pos((double)(var36 + 0.0F), (double)(var12 + 0.0F), (double)(var37 + 8.0F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var22, var23, var24, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                  var5.pos((double)(var36 + 8.0F), (double)(var12 + 0.0F), (double)(var37 + 8.0F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var22, var23, var24, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                  var5.pos((double)(var36 + 8.0F), (double)(var12 + 0.0F), (double)(var37 + 0.0F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var22, var23, var24, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                  var5.pos((double)(var36 + 0.0F), (double)(var12 + 0.0F), (double)(var37 + 0.0F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var22, var23, var24, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
               }

               if(var12 <= 5.0F) {
                  var5.pos((double)(var36 + 0.0F), (double)(var12 + 4.0F - 9.765625E-4F), (double)(var37 + 8.0F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var16, var17, var18, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                  var5.pos((double)(var36 + 8.0F), (double)(var12 + 4.0F - 9.765625E-4F), (double)(var37 + 8.0F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var16, var17, var18, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                  var5.pos((double)(var36 + 8.0F), (double)(var12 + 4.0F - 9.765625E-4F), (double)(var37 + 0.0F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var16, var17, var18, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                  var5.pos((double)(var36 + 0.0F), (double)(var12 + 4.0F - 9.765625E-4F), (double)(var37 + 0.0F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var16, var17, var18, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
               }

               if(var46 > -1) {
                  for(int var38 = 0; var38 < 8; ++var38) {
                     var5.pos((double)(var36 + (float)var38 + 0.0F), (double)(var12 + 0.0F), (double)(var37 + 8.0F)).tex((double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                     var5.pos((double)(var36 + (float)var38 + 0.0F), (double)(var12 + 4.0F), (double)(var37 + 8.0F)).tex((double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                     var5.pos((double)(var36 + (float)var38 + 0.0F), (double)(var12 + 4.0F), (double)(var37 + 0.0F)).tex((double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                     var5.pos((double)(var36 + (float)var38 + 0.0F), (double)(var12 + 0.0F), (double)(var37 + 0.0F)).tex((double)((var34 + (float)var38 + 0.5F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                  }
               }

               if(var46 <= 1) {
                  for(int var47 = 0; var47 < 8; ++var47) {
                     var5.pos((double)(var36 + (float)var47 + 1.0F - 9.765625E-4F), (double)(var12 + 0.0F), (double)(var37 + 8.0F)).tex((double)((var34 + (float)var47 + 0.5F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                     var5.pos((double)(var36 + (float)var47 + 1.0F - 9.765625E-4F), (double)(var12 + 4.0F), (double)(var37 + 8.0F)).tex((double)((var34 + (float)var47 + 0.5F) * 0.00390625F + var28), (double)((var35 + 8.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                     var5.pos((double)(var36 + (float)var47 + 1.0F - 9.765625E-4F), (double)(var12 + 4.0F), (double)(var37 + 0.0F)).tex((double)((var34 + (float)var47 + 0.5F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                     var5.pos((double)(var36 + (float)var47 + 1.0F - 9.765625E-4F), (double)(var12 + 0.0F), (double)(var37 + 0.0F)).tex((double)((var34 + (float)var47 + 0.5F) * 0.00390625F + var28), (double)((var35 + 0.0F) * 0.00390625F + var29)).color(var43, var44, var45, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                  }
               }

               if(var33 > -1) {
                  for(int var48 = 0; var48 < 8; ++var48) {
                     var5.pos((double)(var36 + 0.0F), (double)(var12 + 4.0F), (double)(var37 + (float)var48 + 0.0F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + (float)var48 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                     var5.pos((double)(var36 + 8.0F), (double)(var12 + 4.0F), (double)(var37 + (float)var48 + 0.0F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + (float)var48 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                     var5.pos((double)(var36 + 8.0F), (double)(var12 + 0.0F), (double)(var37 + (float)var48 + 0.0F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + (float)var48 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                     var5.pos((double)(var36 + 0.0F), (double)(var12 + 0.0F), (double)(var37 + (float)var48 + 0.0F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + (float)var48 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                  }
               }

               if(var33 <= 1) {
                  for(int var49 = 0; var49 < 8; ++var49) {
                     var5.pos((double)(var36 + 0.0F), (double)(var12 + 4.0F), (double)(var37 + (float)var49 + 1.0F - 9.765625E-4F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + (float)var49 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                     var5.pos((double)(var36 + 8.0F), (double)(var12 + 4.0F), (double)(var37 + (float)var49 + 1.0F - 9.765625E-4F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + (float)var49 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                     var5.pos((double)(var36 + 8.0F), (double)(var12 + 0.0F), (double)(var37 + (float)var49 + 1.0F - 9.765625E-4F)).tex((double)((var34 + 8.0F) * 0.00390625F + var28), (double)((var35 + (float)var49 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                     var5.pos((double)(var36 + 0.0F), (double)(var12 + 0.0F), (double)(var37 + (float)var49 + 1.0F - 9.765625E-4F)).tex((double)((var34 + 0.0F) * 0.00390625F + var28), (double)((var35 + (float)var49 + 0.5F) * 0.00390625F + var29)).color(var25, var26, var27, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
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

   public void updateChunks(long var1) {
      var1 = (long)((double)var1 + 1.0E8D);
      this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(var1);
      if(!this.chunksToUpdateForced.isEmpty()) {
         Iterator var3 = this.chunksToUpdateForced.iterator();

         while(var3.hasNext()) {
            RenderChunk var4 = (RenderChunk)var3.next();
            if(!this.renderDispatcher.updateChunkLater(var4)) {
               break;
            }

            var4.setNeedsUpdate(false);
            var3.remove();
            this.chunksToUpdate.remove(var4);
            this.chunksToResortTransparency.remove(var4);
         }
      }

      if(!this.chunksToResortTransparency.isEmpty()) {
         Iterator var9 = this.chunksToResortTransparency.iterator();
         if(var9.hasNext()) {
            RenderChunk var11 = (RenderChunk)var9.next();
            if(this.renderDispatcher.b(var11)) {
               var9.remove();
            }
         }
      }

      int var10 = 0;
      int var12 = Config.getUpdatesPerFrame();
      int var5 = var12 * 2;
      Iterator var6 = this.chunksToUpdate.iterator();

      while(var6.hasNext()) {
         RenderChunk var7 = (RenderChunk)var6.next();
         if(!this.renderDispatcher.updateChunkLater(var7)) {
            break;
         }

         var7.setNeedsUpdate(false);
         var6.remove();
         if(var7.getCompiledChunk().isEmpty() && var12 < var5) {
            ++var12;
         }

         ++var10;
         if(var10 >= var12) {
            break;
         }
      }

   }

   public void renderWorldBorder(Entity var1, float var2) {
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      WorldBorder var5 = this.theWorld.getWorldBorder();
      double var6 = (double)(this.mc.gameSettings.renderDistanceChunks * 16);
      if(var1.posX >= var5.maxX() - var6 || var1.posX <= var5.minX() + var6 || var1.posZ >= var5.maxZ() - var6 || var1.posZ <= var5.minZ() + var6) {
         double var8 = 1.0D - var5.getClosestDistance(var1) / var6;
         var8 = Math.pow(var8, 4.0D);
         double var10 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
         double var12 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
         double var14 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         this.renderEngine.bindTexture(locationForcefieldPng);
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         int var16 = var5.getStatus().getID();
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
         var4.begin(7, DefaultVertexFormats.POSITION_TEX);
         var4.setTranslation(-var10, -var12, -var14);
         double var24 = Math.max((double)MathHelper.floor_double(var14 - var6), var5.minZ());
         double var26 = Math.min((double)MathHelper.ceiling_double_int(var14 + var6), var5.maxZ());
         if(var10 > var5.maxX() - var6) {
            float var28 = 0.0F;

            for(double var29 = var24; var29 < var26; var28 += 0.5F) {
               double var31 = Math.min(1.0D, var26 - var29);
               float var33 = (float)var31 * 0.5F;
               var4.pos(var5.maxX(), 256.0D, var29).tex((double)(var20 + var28), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var5.maxX(), 256.0D, var29 + var31).tex((double)(var20 + var33 + var28), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var5.maxX(), 0.0D, var29 + var31).tex((double)(var20 + var33 + var28), (double)(var20 + 128.0F)).endVertex();
               var4.pos(var5.maxX(), 0.0D, var29).tex((double)(var20 + var28), (double)(var20 + 128.0F)).endVertex();
               ++var29;
            }
         }

         if(var10 < var5.minX() + var6) {
            float var37 = 0.0F;

            for(double var40 = var24; var40 < var26; var37 += 0.5F) {
               double var43 = Math.min(1.0D, var26 - var40);
               float var46 = (float)var43 * 0.5F;
               var4.pos(var5.minX(), 256.0D, var40).tex((double)(var20 + var37), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var5.minX(), 256.0D, var40 + var43).tex((double)(var20 + var46 + var37), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var5.minX(), 0.0D, var40 + var43).tex((double)(var20 + var46 + var37), (double)(var20 + 128.0F)).endVertex();
               var4.pos(var5.minX(), 0.0D, var40).tex((double)(var20 + var37), (double)(var20 + 128.0F)).endVertex();
               ++var40;
            }
         }

         var24 = Math.max((double)MathHelper.floor_double(var10 - var6), var5.minX());
         var26 = Math.min((double)MathHelper.ceiling_double_int(var10 + var6), var5.maxX());
         if(var14 > var5.maxZ() - var6) {
            float var38 = 0.0F;

            for(double var41 = var24; var41 < var26; var38 += 0.5F) {
               double var44 = Math.min(1.0D, var26 - var41);
               float var47 = (float)var44 * 0.5F;
               var4.pos(var41, 256.0D, var5.maxZ()).tex((double)(var20 + var38), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var41 + var44, 256.0D, var5.maxZ()).tex((double)(var20 + var47 + var38), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var41 + var44, 0.0D, var5.maxZ()).tex((double)(var20 + var47 + var38), (double)(var20 + 128.0F)).endVertex();
               var4.pos(var41, 0.0D, var5.maxZ()).tex((double)(var20 + var38), (double)(var20 + 128.0F)).endVertex();
               ++var41;
            }
         }

         if(var14 < var5.minZ() + var6) {
            float var39 = 0.0F;

            for(double var42 = var24; var42 < var26; var39 += 0.5F) {
               double var45 = Math.min(1.0D, var26 - var42);
               float var48 = (float)var45 * 0.5F;
               var4.pos(var42, 256.0D, var5.minZ()).tex((double)(var20 + var39), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var42 + var45, 256.0D, var5.minZ()).tex((double)(var20 + var48 + var39), (double)(var20 + 0.0F)).endVertex();
               var4.pos(var42 + var45, 0.0D, var5.minZ()).tex((double)(var20 + var48 + var39), (double)(var20 + 128.0F)).endVertex();
               var4.pos(var42, 0.0D, var5.minZ()).tex((double)(var20 + var39), (double)(var20 + 128.0F)).endVertex();
               ++var42;
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

   private void preRenderDamagedBlocks() {
      GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
      GlStateManager.enableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
      GlStateManager.doPolygonOffset(-3.0F, -3.0F);
      GlStateManager.enablePolygonOffset();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableAlpha();
      GlStateManager.pushMatrix();
      if(Config.isShaders()) {
         ShadersRender.beginBlockDamage();
      }

   }

   private void postRenderDamagedBlocks() {
      GlStateManager.disableAlpha();
      GlStateManager.doPolygonOffset(0.0F, 0.0F);
      GlStateManager.disablePolygonOffset();
      GlStateManager.enableAlpha();
      GlStateManager.depthMask(true);
      GlStateManager.popMatrix();
      if(Config.isShaders()) {
         ShadersRender.endBlockDamage();
      }

   }

   public void drawBlockDamageTexture(Tessellator var1, WorldRenderer var2, Entity var3, float var4) {
      double var5 = var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * (double)var4;
      double var7 = var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * (double)var4;
      double var9 = var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * (double)var4;
      if(!this.damagedBlocks.isEmpty()) {
         this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
         this.preRenderDamagedBlocks();
         var2.begin(7, DefaultVertexFormats.BLOCK);
         var2.setTranslation(-var5, -var7, -var9);
         var2.markDirty();
         Iterator var11 = this.damagedBlocks.values().iterator();

         while(var11.hasNext()) {
            DestroyBlockProgress var12 = (DestroyBlockProgress)var11.next();
            BlockPos var13 = var12.getPosition();
            double var14 = (double)var13.getX() - var5;
            double var16 = (double)var13.getY() - var7;
            double var18 = (double)var13.getZ() - var9;
            Block var20 = this.theWorld.getBlockState(var13).getBlock();
            if(Reflector.aP.d()) {
               boolean var22 = var20 instanceof BlockChest || var20 instanceof BlockEnderChest || var20 instanceof BlockSign || var20 instanceof BlockSkull;
               TileEntity var23 = this.theWorld.getTileEntity(var13);
               var22 = Reflector.d(var23, Reflector.aP, new Object[0]);
               boolean var26 = true;
            } else {
               boolean var21 = !(var20 instanceof BlockChest) && !(var20 instanceof BlockEnderChest) && !(var20 instanceof BlockSign) && !(var20 instanceof BlockSkull);
            }

            if(var14 * var14 + var16 * var16 + var18 * var18 > 1024.0D) {
               var11.remove();
            } else {
               IBlockState var28 = this.theWorld.getBlockState(var13);
               if(var28.getBlock().getMaterial() != Material.air) {
                  int var29 = var12.getPartialBlockDamage();
                  TextureAtlasSprite var24 = this.destroyBlockIcons[var29];
                  BlockRendererDispatcher var25 = this.mc.getBlockRendererDispatcher();
                  var25.renderBlockDamage(var28, var13, var24, this.theWorld);
               }
            }
         }

         var1.draw();
         var2.setTranslation(0.0D, 0.0D, 0.0D);
         this.postRenderDamagedBlocks();
      }

   }

   public void drawSelectionBox(EntityPlayer var1, MovingObjectPosition var2, int var3, float var4) {
      if(var2.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
         GL11.glLineWidth(2.0F);
         GlStateManager.disableTexture2D();
         if(Config.isShaders()) {
            Shaders.disableTexture2D();
         }

         GlStateManager.depthMask(false);
         float var5 = 0.002F;
         BlockPos var6 = var2.getBlockPos();
         Block var7 = this.theWorld.getBlockState(var6).getBlock();
         if(var7.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(var6)) {
            var7.setBlockBoundsBasedOnState(this.theWorld, var6);
            double var8 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var4;
            double var10 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var4;
            double var12 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var4;
            func_181561_a(var7.getSelectedBoundingBox(this.theWorld, var6).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-var8, -var10, -var12));
         }

         GlStateManager.depthMask(true);
         GlStateManager.enableTexture2D();
         if(Config.isShaders()) {
            Shaders.enableTexture2D();
         }

         GlStateManager.disableBlend();
      }

   }

   public static void func_181561_a(AxisAlignedBB var0) {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var1.draw();
      var2.begin(1, DefaultVertexFormats.POSITION);
      var2.pos(var0.minX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.minZ).endVertex();
      var2.pos(var0.maxX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.maxX, var0.maxY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.minY, var0.maxZ).endVertex();
      var2.pos(var0.minX, var0.maxY, var0.maxZ).endVertex();
      var1.draw();
   }

   public static void func_181563_a(AxisAlignedBB var0, int var1, int var2, int var3, int var4) {
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      var6.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var5.draw();
      var6.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.minX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var5.draw();
      var6.begin(1, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(var0.minX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.minX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.minY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.maxY, var0.minZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.maxX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.minX, var0.minY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var6.pos(var0.minX, var0.maxY, var0.maxZ).color(var1, var2, var3, var4).endVertex();
      var5.draw();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void markBlocksForUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.viewFrustum.markBlocksForUpdate(var1, var2, var3, var4, var5, var6);
   }

   public void markBlockForUpdate(BlockPos var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      int var4 = var1.getZ();
      this.markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   public void notifyLightSet(BlockPos var1) {
      int var2 = var1.getX();
      int var3 = var1.getY();
      int var4 = var1.getZ();
      this.markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   public void markBlockRangeForRenderUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.markBlocksForUpdate(var1 - 1, var2 - 1, var3 - 1, var4 + 1, var5 + 1, var6 + 1);
   }

   public void playRecord(String var1, BlockPos var2) {
      ISound var3 = (ISound)this.mapSoundPositions.get(var2);
      this.mc.getSoundHandler().stopSound(var3);
      this.mapSoundPositions.remove(var2);
      ItemRecord var4 = ItemRecord.getRecord(var1);
      this.mc.ingameGUI.setRecordPlayingMessage(var4.getRecordNameLocal());
      ResourceLocation var5 = null;
      if(Reflector.bH.d()) {
         var5 = (ResourceLocation)Reflector.f(var4, Reflector.bH, new Object[]{var1});
      }

      var5 = new ResourceLocation(var1);
      PositionedSoundRecord var6 = PositionedSoundRecord.create(var5, (float)var2.getX(), (float)var2.getY(), (float)var2.getZ());
      this.mapSoundPositions.put(var2, var6);
      this.mc.getSoundHandler().playSound(var6);
   }

   public void playSound(String var1, double var2, double var4, double var6, float var8, float var9) {
   }

   public void playSoundToNearExcept(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9, float var10) {
   }

   public void spawnParticle(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      RenderGlobal var10000 = this;
      int var10001 = var1;
      boolean var10002 = var2;
      double var10003 = var3;
      double var10004 = var5;
      double var10005 = var7;
      double var10006 = var9;
      double var10007 = var11;
      double var10008 = var13;
      int[] var10009 = var15;

      try {
         var10000.spawnEntityFX(var10001, var10002, var10003, var10004, var10005, var10006, var10007, var10008, var10009);
      } catch (Throwable var19) {
         CrashReport var17 = CrashReport.makeCrashReport(var19, "Exception while adding particle");
         CrashReportCategory var18 = var17.makeCategory("Particle being added");
         var18.addCrashSection("ID", Integer.valueOf(var1));
         var18.addCrashSection("Parameters", var15);
         var18.addCrashSectionCallable("Position", RenderGlobal::lambda$spawnParticle$0);
         throw new ReportedException(var17);
      }
   }

   private void spawnParticle(EnumParticleTypes var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      this.spawnParticle(var1.getParticleID(), var1.getShouldIgnoreRange(), var2, var4, var6, var8, var10, var12, var14);
   }

   private EntityFX spawnEntityFX(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      if(this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
         int var16 = this.mc.gameSettings.particleSetting;
         if(var16 == 1 && this.theWorld.rand.nextInt(3) == 0) {
            var16 = 2;
         }

         double var10000 = this.mc.getRenderViewEntity().posX - var3;
         var10000 = this.mc.getRenderViewEntity().posY - var5;
         var10000 = this.mc.getRenderViewEntity().posZ - var7;
         return var1 == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion()?null:(var1 == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion()?null:(var1 == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion()?null:(var1 == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles()?null:(var1 == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles()?null:(var1 == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke()?null:(var1 == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke()?null:(var1 == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles()?null:(var1 == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles()?null:(var1 == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles()?null:(var1 == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles()?null:(var1 == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles()?null:(var1 == EnumParticleTypes.PORTAL.getParticleID() && !Config.isAnimatedPortal()?null:(var1 == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame()?null:(var1 == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone()?null:(var1 == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava()?null:(var1 == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava()?null:(var1 == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles()?null:aaB.a(this.mc.effectRenderer, var1, var3, var5, var7, var9, var11, var13, var15))))))))))))))))));
      } else {
         return null;
      }
   }

   public void onEntityAdded(Entity var1) {
      RandomMobs.entityLoaded(var1, this.theWorld);
      if(Config.al()) {
         DynamicLights.entityAdded(var1, this);
      }

   }

   public void onEntityRemoved(Entity var1) {
      if(Config.al()) {
         DynamicLights.entityRemoved(var1, this);
      }

   }

   public void deleteAllDisplayLists() {
   }

   public void broadcastSound(int var1, BlockPos var2, int var3) {
      switch(var1) {
      case 1013:
      case 1018:
         if(this.mc.getRenderViewEntity() != null) {
            double var4 = (double)var2.getX() - this.mc.getRenderViewEntity().posX;
            double var6 = (double)var2.getY() - this.mc.getRenderViewEntity().posY;
            double var8 = (double)var2.getZ() - this.mc.getRenderViewEntity().posZ;
            double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
            double var12 = this.mc.getRenderViewEntity().posX;
            double var14 = this.mc.getRenderViewEntity().posY;
            double var16 = this.mc.getRenderViewEntity().posZ;
            if(var10 > 0.0D) {
               var12 += var4 / var10 * 2.0D;
               var14 += var6 / var10 * 2.0D;
               var16 += var8 / var10 * 2.0D;
            }

            if(var1 == 1013) {
               this.theWorld.playSound(var12, var14, var16, "mob.wither.spawn", 1.0F, 1.0F, false);
            } else {
               this.theWorld.playSound(var12, var14, var16, "mob.enderdragon.end", 5.0F, 1.0F, false);
            }
         }
      default:
      }
   }

   public void playAuxSFX(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      Random var5 = this.theWorld.rand;
      switch(var2) {
      case 1000:
         this.theWorld.playSoundAtPos(var3, "random.click", 1.0F, 1.0F, false);
         break;
      case 1001:
         this.theWorld.playSoundAtPos(var3, "random.click", 1.0F, 1.2F, false);
         break;
      case 1002:
         this.theWorld.playSoundAtPos(var3, "random.bow", 1.0F, 1.2F, false);
         break;
      case 1003:
         this.theWorld.playSoundAtPos(var3, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1004:
         this.theWorld.playSoundAtPos(var3, "random.fizz", 0.5F, 2.6F + (var5.nextFloat() - var5.nextFloat()) * 0.8F, false);
         break;
      case 1005:
         if(Item.getItemById(var4) instanceof ItemRecord) {
            this.theWorld.playRecord(var3, "records." + ((ItemRecord)Item.getItemById(var4)).recordName);
         } else {
            this.theWorld.playRecord(var3, (String)null);
         }
         break;
      case 1006:
         this.theWorld.playSoundAtPos(var3, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1007:
         this.theWorld.playSoundAtPos(var3, "mob.ghast.charge", 10.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1008:
         this.theWorld.playSoundAtPos(var3, "mob.ghast.fireball", 10.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1009:
         this.theWorld.playSoundAtPos(var3, "mob.ghast.fireball", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1010:
         this.theWorld.playSoundAtPos(var3, "mob.zombie.wood", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1011:
         this.theWorld.playSoundAtPos(var3, "mob.zombie.metal", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1012:
         this.theWorld.playSoundAtPos(var3, "mob.zombie.woodbreak", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1014:
         this.theWorld.playSoundAtPos(var3, "mob.wither.shoot", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1015:
         this.theWorld.playSoundAtPos(var3, "mob.bat.takeoff", 0.05F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1016:
         this.theWorld.playSoundAtPos(var3, "mob.zombie.infect", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1017:
         this.theWorld.playSoundAtPos(var3, "mob.zombie.unfect", 2.0F, (var5.nextFloat() - var5.nextFloat()) * 0.2F + 1.0F, false);
         break;
      case 1020:
         this.theWorld.playSoundAtPos(var3, "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1021:
         this.theWorld.playSoundAtPos(var3, "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 1022:
         this.theWorld.playSoundAtPos(var3, "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2000:
         int var6 = var4 % 3 - 1;
         int var7 = var4 / 3 % 3 - 1;
         double var8 = (double)var3.getX() + (double)var6 * 0.6D + 0.5D;
         double var10 = (double)var3.getY() + 0.5D;
         double var12 = (double)var3.getZ() + (double)var7 * 0.6D + 0.5D;

         for(int var39 = 0; var39 < 10; ++var39) {
            double var40 = var5.nextDouble() * 0.2D + 0.01D;
            double var41 = var8 + (double)var6 * 0.01D + (var5.nextDouble() - 0.5D) * (double)var7 * 0.5D;
            double var42 = var10 + (var5.nextDouble() - 0.5D) * 0.5D;
            double var44 = var12 + (double)var7 * 0.01D + (var5.nextDouble() - 0.5D) * (double)var6 * 0.5D;
            double var45 = (double)var6 * var40 + var5.nextGaussian() * 0.01D;
            double var46 = -0.03D + var5.nextGaussian() * 0.01D;
            double var48 = (double)var7 * var40 + var5.nextGaussian() * 0.01D;
            this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var41, var42, var44, var45, var46, var48, new int[0]);
         }

         return;
      case 2001:
         Block var14 = Block.getBlockById(var4 & 4095);
         if(var14.getMaterial() != Material.air) {
            this.mc.getSoundHandler().playSound(new PositionedSoundRecord(new ResourceLocation(var14.stepSound.getBreakSound()), (var14.stepSound.getVolume() + 1.0F) / 2.0F, var14.stepSound.getFrequency() * 0.8F, (float)var3.getX() + 0.5F, (float)var3.getY() + 0.5F, (float)var3.getZ() + 0.5F));
         }

         this.mc.effectRenderer.addBlockDestroyEffects(var3, var14.getStateFromMeta(var4 >> 12 & 255));
         break;
      case 2002:
         double var15 = (double)var3.getX();
         double var17 = (double)var3.getY();
         double var19 = (double)var3.getZ();

         for(int var21 = 0; var21 < 8; ++var21) {
            this.spawnParticle(EnumParticleTypes.ITEM_CRACK, var15, var17, var19, var5.nextGaussian() * 0.15D, var5.nextDouble() * 0.2D, var5.nextGaussian() * 0.15D, new int[]{Item.b(Items.potionitem), var4});
         }

         int var43 = Items.potionitem.getColorFromDamage(var4);
         float var22 = (float)(var43 >> 16 & 255) / 255.0F;
         float var23 = (float)(var43 >> 8 & 255) / 255.0F;
         float var24 = (float)(var43 & 255) / 255.0F;
         EnumParticleTypes var25 = EnumParticleTypes.SPELL;
         if(Items.potionitem.isEffectInstant(var4)) {
            var25 = EnumParticleTypes.SPELL_INSTANT;
         }

         for(int var47 = 0; var47 < 100; ++var47) {
            double var27 = var5.nextDouble() * 4.0D;
            double var29 = var5.nextDouble() * 3.141592653589793D * 2.0D;
            double var31 = (double)MathHelper.cos(var29) * var27;
            double var51 = 0.01D + var5.nextDouble() * 0.5D;
            double var52 = (double)MathHelper.sin(var29) * var27;
            EntityFX var53 = this.spawnEntityFX(var25.getParticleID(), var25.getShouldIgnoreRange(), var15 + var31 * 0.1D, var17 + 0.3D, var19 + var52 * 0.1D, var31, var51, var52, new int[0]);
            float var38 = 0.75F + var5.nextFloat() * 0.25F;
            var53.setRBGColorF(var22 * var38, var23 * var38, var24 * var38);
            var53.multiplyVelocity((float)var27);
         }

         this.theWorld.playSoundAtPos(var3, "game.potion.smash", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
         break;
      case 2003:
         double var26 = (double)var3.getX() + 0.5D;
         double var28 = (double)var3.getY();
         double var30 = (double)var3.getZ() + 0.5D;

         for(int var49 = 0; var49 < 8; ++var49) {
            this.spawnParticle(EnumParticleTypes.ITEM_CRACK, var26, var28, var30, var5.nextGaussian() * 0.15D, var5.nextDouble() * 0.2D, var5.nextGaussian() * 0.15D, new int[]{Item.b(Items.ender_eye)});
         }

         for(double var50 = 0.0D; var50 < 6.283185307179586D; var50 += 0.15707963267948966D) {
            this.spawnParticle(EnumParticleTypes.PORTAL, var26 + (double)MathHelper.cos(var50) * 5.0D, var28 - 0.4D, var30 + (double)MathHelper.sin(var50) * 5.0D, (double)MathHelper.cos(var50) * -5.0D, 0.0D, (double)MathHelper.sin(var50) * -5.0D, new int[0]);
            this.spawnParticle(EnumParticleTypes.PORTAL, var26 + (double)MathHelper.cos(var50) * 5.0D, var28 - 0.4D, var30 + (double)MathHelper.sin(var50) * 5.0D, (double)MathHelper.cos(var50) * -7.0D, 0.0D, (double)MathHelper.sin(var50) * -7.0D, new int[0]);
         }

         return;
      case 2004:
         for(int var32 = 0; var32 < 20; ++var32) {
            double var33 = (double)var3.getX() + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            double var35 = (double)var3.getY() + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            double var37 = (double)var3.getZ() + 0.5D + ((double)this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
            this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var33, var35, var37, 0.0D, 0.0D, 0.0D, new int[0]);
            this.theWorld.spawnParticle(EnumParticleTypes.FLAME, var33, var35, var37, 0.0D, 0.0D, 0.0D, new int[0]);
         }

         return;
      case 2005:
         ItemDye.spawnBonemealParticles(this.theWorld, var3, var4);
      }

   }

   public void sendBlockBreakProgress(int var1, BlockPos var2, int var3) {
      if(var3 < 10) {
         DestroyBlockProgress var4 = (DestroyBlockProgress)this.damagedBlocks.get(Integer.valueOf(var1));
         if(var4.getPosition().getX() != var2.getX() || var4.getPosition().getY() != var2.getY() || var4.getPosition().getZ() != var2.getZ()) {
            var4 = new DestroyBlockProgress(var1, var2);
            this.damagedBlocks.put(Integer.valueOf(var1), var4);
         }

         var4.setPartialBlockDamage(var3);
         var4.setCloudUpdateTick(this.cloudTickCounter);
      } else {
         this.damagedBlocks.remove(Integer.valueOf(var1));
      }

   }

   public void setDisplayListEntitiesDirty() {
      this.displayListEntitiesDirty = true;
   }

   public void resetClouds() {
      this.cloudRenderer.reset();
   }

   public int getCountRenderers() {
      return this.viewFrustum.renderChunks.length;
   }

   public int getCountActiveRenderers() {
      return this.renderInfos.size();
   }

   public int getCountEntitiesRendered() {
      return this.countEntitiesRendered;
   }

   public int getCountTileEntitiesRendered() {
      return this.countTileEntitiesRendered;
   }

   public RenderChunk getRenderChunk(BlockPos var1) {
      return this.viewFrustum.getRenderChunk(var1);
   }

   public RenderChunk a(RenderChunk var1, EnumFacing var2) {
      return null;
   }

   public WorldClient getWorld() {
      return this.theWorld;
   }

   public void func_181023_a(Collection param1, Collection param2) {
      // $FF: Couldn't be decompiled
   }

   private static String lambda$spawnParticle$0(double var0, double var2, double var4) throws Exception {
      return CrashReportCategory.getCoordinateInfo(var0, var2, var4);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
