/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Deque;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.audio.PositionedSoundRecord;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
/*      */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*      */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.ListChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.chunk.VboChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.VisGraph;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemDye;
/*      */ import net.minecraft.item.ItemRecord;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.Matrix4f;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.util.Vector3d;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.IWorldAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import optfine.ChunkUtils;
/*      */ import optfine.CloudRenderer;
/*      */ import optfine.Config;
/*      */ import optfine.CustomColorizer;
/*      */ import optfine.CustomSky;
/*      */ import optfine.Lagometer;
/*      */ import optfine.RandomMobs;
/*      */ import optfine.Reflector;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.vector.Matrix4f;
/*      */ import org.lwjgl.util.vector.Vector3f;
/*      */ import org.lwjgl.util.vector.Vector4f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RenderGlobal
/*      */   implements IWorldAccess, IResourceManagerReloadListener
/*      */ {
/*  113 */   private static final Logger logger = LogManager.getLogger();
/*  114 */   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
/*  115 */   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
/*  116 */   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
/*  117 */   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
/*  118 */   private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
/*      */   
/*      */   public final Minecraft mc;
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */   
/*      */   private final RenderManager renderManager;
/*      */   
/*      */   private WorldClient theWorld;
/*  127 */   private Set chunksToUpdate = Sets.newLinkedHashSet();
/*      */ 
/*      */   
/*  130 */   private List renderInfos = Lists.newArrayListWithCapacity(69696);
/*  131 */   private final Set field_181024_n = Sets.newHashSet();
/*      */   
/*      */   private ViewFrustum viewFrustum;
/*      */   
/*  135 */   private int starGLCallList = -1;
/*      */ 
/*      */   
/*  138 */   private int glSkyList = -1;
/*      */ 
/*      */   
/*  141 */   private int glSkyList2 = -1;
/*      */ 
/*      */   
/*      */   private VertexFormat vertexBufferFormat;
/*      */ 
/*      */   
/*      */   private VertexBuffer starVBO;
/*      */ 
/*      */   
/*      */   private VertexBuffer skyVBO;
/*      */   
/*      */   private VertexBuffer sky2VBO;
/*      */   
/*      */   private int cloudTickCounter;
/*      */   
/*  156 */   public final Map damagedBlocks = Maps.newHashMap();
/*      */ 
/*      */   
/*  159 */   private final Map mapSoundPositions = Maps.newHashMap();
/*  160 */   private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
/*      */   
/*      */   private Framebuffer entityOutlineFramebuffer;
/*      */   
/*      */   private ShaderGroup entityOutlineShader;
/*  165 */   private double frustumUpdatePosX = Double.MIN_VALUE;
/*  166 */   private double frustumUpdatePosY = Double.MIN_VALUE;
/*  167 */   private double frustumUpdatePosZ = Double.MIN_VALUE;
/*  168 */   private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  169 */   private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  170 */   private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  171 */   private double lastViewEntityX = Double.MIN_VALUE;
/*  172 */   private double lastViewEntityY = Double.MIN_VALUE;
/*  173 */   private double lastViewEntityZ = Double.MIN_VALUE;
/*  174 */   private double lastViewEntityPitch = Double.MIN_VALUE;
/*  175 */   private double lastViewEntityYaw = Double.MIN_VALUE;
/*  176 */   private final ChunkRenderDispatcher renderDispatcher = new ChunkRenderDispatcher();
/*      */   private ChunkRenderContainer renderContainer;
/*  178 */   private int renderDistanceChunks = -1;
/*      */ 
/*      */   
/*  181 */   private int renderEntitiesStartupCounter = 2;
/*      */   
/*      */   private int countEntitiesTotal;
/*      */   
/*      */   private int countEntitiesRendered;
/*      */   
/*      */   private int countEntitiesHidden;
/*      */   
/*      */   private boolean debugFixTerrainFrustum = false;
/*      */   
/*      */   private ClippingHelper debugFixedClippingHelper;
/*      */   
/*  193 */   private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
/*  194 */   private final Vector3d debugTerrainFrustumPosition = new Vector3d();
/*      */   private boolean vboEnabled = false;
/*      */   IRenderChunkFactory renderChunkFactory;
/*      */   private double prevRenderSortX;
/*      */   private double prevRenderSortY;
/*      */   private double prevRenderSortZ;
/*      */   public boolean displayListEntitiesDirty = true;
/*      */   private static final String __OBFID = "CL_00000954";
/*      */   private CloudRenderer cloudRenderer;
/*      */   public Entity renderedEntity;
/*  204 */   public Set chunksToResortTransparency = new LinkedHashSet();
/*  205 */   public Set chunksToUpdateForced = new LinkedHashSet();
/*  206 */   private Deque visibilityDeque = new ArrayDeque();
/*  207 */   private List renderInfosEntities = new ArrayList(1024);
/*  208 */   private List renderInfosTileEntities = new ArrayList(1024);
/*  209 */   private int renderDistance = 0;
/*  210 */   private int renderDistanceSq = 0;
/*  211 */   private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList((Object[])EnumFacing.VALUES)));
/*      */   
/*      */   private int countTileEntitiesRendered;
/*      */   
/*      */   public RenderGlobal(Minecraft mcIn) {
/*  216 */     this.cloudRenderer = new CloudRenderer(mcIn);
/*  217 */     this.mc = mcIn;
/*  218 */     this.renderManager = mcIn.getRenderManager();
/*  219 */     this.renderEngine = mcIn.getTextureManager();
/*  220 */     this.renderEngine.bindTexture(locationForcefieldPng);
/*  221 */     GL11.glTexParameteri(3553, 10242, 10497);
/*  222 */     GL11.glTexParameteri(3553, 10243, 10497);
/*  223 */     GlStateManager.bindTexture(0);
/*  224 */     updateDestroyBlockIcons();
/*  225 */     this.vboEnabled = OpenGlHelper.useVbo();
/*      */     
/*  227 */     if (this.vboEnabled) {
/*      */       
/*  229 */       this.renderContainer = new VboRenderList();
/*  230 */       this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */     }
/*      */     else {
/*      */       
/*  234 */       this.renderContainer = new RenderList();
/*  235 */       this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */     } 
/*      */     
/*  238 */     this.vertexBufferFormat = new VertexFormat();
/*  239 */     this.vertexBufferFormat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/*  240 */     generateStars();
/*  241 */     generateSky();
/*  242 */     generateSky2();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  247 */     updateDestroyBlockIcons();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDestroyBlockIcons() {
/*  252 */     TextureMap texturemap = this.mc.getTextureMapBlocks();
/*      */     
/*  254 */     for (int i = 0; i < this.destroyBlockIcons.length; i++)
/*      */     {
/*  256 */       this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeEntityOutlineShader() {
/*  265 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  267 */       if (ShaderLinkHelper.getStaticShaderLinkHelper() == null)
/*      */       {
/*  269 */         ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */       }
/*      */       
/*  272 */       ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");
/*      */ 
/*      */       
/*      */       try {
/*  276 */         this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocation);
/*  277 */         this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  278 */         this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
/*      */       }
/*  280 */       catch (IOException ioexception) {
/*      */         
/*  282 */         logger.warn("Failed to load shader: " + resourcelocation, ioexception);
/*  283 */         this.entityOutlineShader = null;
/*  284 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*  286 */       catch (JsonSyntaxException jsonsyntaxexception) {
/*      */         
/*  288 */         logger.warn("Failed to load shader: " + resourcelocation, (Throwable)jsonsyntaxexception);
/*  289 */         this.entityOutlineShader = null;
/*  290 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  295 */       this.entityOutlineShader = null;
/*  296 */       this.entityOutlineFramebuffer = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderEntityOutlineFramebuffer() {
/*  302 */     if (isRenderEntityOutlines()) {
/*      */       
/*  304 */       GlStateManager.enableBlend();
/*  305 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/*  306 */       this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
/*  307 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isRenderEntityOutlines() {
/*  320 */     return (this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.thePlayer != null && this.mc.thePlayer.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown());
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateSky2() {
/*  325 */     Tessellator tessellator = Tessellator.getInstance();
/*  326 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  328 */     if (this.sky2VBO != null)
/*      */     {
/*  330 */       this.sky2VBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  333 */     if (this.glSkyList2 >= 0) {
/*      */       
/*  335 */       GLAllocation.deleteDisplayLists(this.glSkyList2);
/*  336 */       this.glSkyList2 = -1;
/*      */     } 
/*      */     
/*  339 */     if (this.vboEnabled) {
/*      */       
/*  341 */       this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
/*  342 */       renderSky(worldrenderer, -16.0F, true);
/*  343 */       worldrenderer.finishDrawing();
/*  344 */       worldrenderer.reset();
/*  345 */       this.sky2VBO.func_181722_a(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  349 */       this.glSkyList2 = GLAllocation.generateDisplayLists(1);
/*  350 */       GL11.glNewList(this.glSkyList2, 4864);
/*  351 */       renderSky(worldrenderer, -16.0F, true);
/*  352 */       tessellator.draw();
/*  353 */       GL11.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateSky() {
/*  359 */     Tessellator tessellator = Tessellator.getInstance();
/*  360 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  362 */     if (this.skyVBO != null)
/*      */     {
/*  364 */       this.skyVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  367 */     if (this.glSkyList >= 0) {
/*      */       
/*  369 */       GLAllocation.deleteDisplayLists(this.glSkyList);
/*  370 */       this.glSkyList = -1;
/*      */     } 
/*      */     
/*  373 */     if (this.vboEnabled) {
/*      */       
/*  375 */       this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
/*  376 */       renderSky(worldrenderer, 16.0F, false);
/*  377 */       worldrenderer.finishDrawing();
/*  378 */       worldrenderer.reset();
/*  379 */       this.skyVBO.func_181722_a(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  383 */       this.glSkyList = GLAllocation.generateDisplayLists(1);
/*  384 */       GL11.glNewList(this.glSkyList, 4864);
/*  385 */       renderSky(worldrenderer, 16.0F, false);
/*  386 */       tessellator.draw();
/*  387 */       GL11.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSky(WorldRenderer worldRendererIn, float p_174968_2_, boolean p_174968_3_) {
/*  393 */     boolean flag = true;
/*  394 */     boolean flag1 = true;
/*  395 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
/*      */     
/*  397 */     for (int i = -384; i <= 384; i += 64) {
/*      */       
/*  399 */       for (int j = -384; j <= 384; j += 64) {
/*      */         
/*  401 */         float f = i;
/*  402 */         float f1 = (i + 64);
/*      */         
/*  404 */         if (p_174968_3_) {
/*      */           
/*  406 */           f1 = i;
/*  407 */           f = (i + 64);
/*      */         } 
/*      */         
/*  410 */         worldRendererIn.pos(f, p_174968_2_, j).endVertex();
/*  411 */         worldRendererIn.pos(f1, p_174968_2_, j).endVertex();
/*  412 */         worldRendererIn.pos(f1, p_174968_2_, (j + 64)).endVertex();
/*  413 */         worldRendererIn.pos(f, p_174968_2_, (j + 64)).endVertex();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateStars() {
/*  420 */     Tessellator tessellator = Tessellator.getInstance();
/*  421 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */     
/*  423 */     if (this.starVBO != null)
/*      */     {
/*  425 */       this.starVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  428 */     if (this.starGLCallList >= 0) {
/*      */       
/*  430 */       GLAllocation.deleteDisplayLists(this.starGLCallList);
/*  431 */       this.starGLCallList = -1;
/*      */     } 
/*      */     
/*  434 */     if (this.vboEnabled) {
/*      */       
/*  436 */       this.starVBO = new VertexBuffer(this.vertexBufferFormat);
/*  437 */       renderStars(worldrenderer);
/*  438 */       worldrenderer.finishDrawing();
/*  439 */       worldrenderer.reset();
/*  440 */       this.starVBO.func_181722_a(worldrenderer.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  444 */       this.starGLCallList = GLAllocation.generateDisplayLists(1);
/*  445 */       GlStateManager.pushMatrix();
/*  446 */       GL11.glNewList(this.starGLCallList, 4864);
/*  447 */       renderStars(worldrenderer);
/*  448 */       tessellator.draw();
/*  449 */       GL11.glEndList();
/*  450 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderStars(WorldRenderer worldRendererIn) {
/*  456 */     Random random = new Random(10842L);
/*  457 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
/*      */     
/*  459 */     for (int i = 0; i < 1500; i++) {
/*      */       
/*  461 */       double d0 = (random.nextFloat() * 2.0F - 1.0F);
/*  462 */       double d1 = (random.nextFloat() * 2.0F - 1.0F);
/*  463 */       double d2 = (random.nextFloat() * 2.0F - 1.0F);
/*  464 */       double d3 = (0.15F + random.nextFloat() * 0.1F);
/*  465 */       double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */       
/*  467 */       if (d4 < 1.0D && d4 > 0.01D) {
/*      */         
/*  469 */         d4 = 1.0D / Math.sqrt(d4);
/*  470 */         d0 *= d4;
/*  471 */         d1 *= d4;
/*  472 */         d2 *= d4;
/*  473 */         double d5 = d0 * 100.0D;
/*  474 */         double d6 = d1 * 100.0D;
/*  475 */         double d7 = d2 * 100.0D;
/*  476 */         double d8 = Math.atan2(d0, d2);
/*  477 */         double d9 = Math.sin(d8);
/*  478 */         double d10 = Math.cos(d8);
/*  479 */         double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
/*  480 */         double d12 = Math.sin(d11);
/*  481 */         double d13 = Math.cos(d11);
/*  482 */         double d14 = random.nextDouble() * Math.PI * 2.0D;
/*  483 */         double d15 = Math.sin(d14);
/*  484 */         double d16 = Math.cos(d14);
/*      */         
/*  486 */         for (int j = 0; j < 4; j++) {
/*      */           
/*  488 */           double d17 = 0.0D;
/*  489 */           double d18 = ((j & 0x2) - 1) * d3;
/*  490 */           double d19 = ((j + 1 & 0x2) - 1) * d3;
/*  491 */           double d20 = 0.0D;
/*  492 */           double d21 = d18 * d16 - d19 * d15;
/*  493 */           double d22 = d19 * d16 + d18 * d15;
/*  494 */           double d23 = d21 * d12 + 0.0D * d13;
/*  495 */           double d24 = 0.0D * d12 - d21 * d13;
/*  496 */           double d25 = d24 * d9 - d22 * d10;
/*  497 */           double d26 = d22 * d9 + d24 * d10;
/*  498 */           worldRendererIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldAndLoadRenderers(WorldClient worldClientIn) {
/*  509 */     if (this.theWorld != null)
/*      */     {
/*  511 */       this.theWorld.removeWorldAccess(this);
/*      */     }
/*      */     
/*  514 */     this.frustumUpdatePosX = Double.MIN_VALUE;
/*  515 */     this.frustumUpdatePosY = Double.MIN_VALUE;
/*  516 */     this.frustumUpdatePosZ = Double.MIN_VALUE;
/*  517 */     this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  518 */     this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  519 */     this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  520 */     this.renderManager.set((World)worldClientIn);
/*  521 */     this.theWorld = worldClientIn;
/*      */     
/*  523 */     if (worldClientIn != null) {
/*      */       
/*  525 */       worldClientIn.addWorldAccess(this);
/*  526 */       loadRenderers();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadRenderers() {
/*  535 */     if (this.theWorld != null) {
/*      */       
/*  537 */       this.displayListEntitiesDirty = true;
/*  538 */       Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
/*  539 */       Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
/*  540 */       BlockModelRenderer.updateAoLightValue();
/*  541 */       this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
/*  542 */       this.renderDistance = this.renderDistanceChunks * 16;
/*  543 */       this.renderDistanceSq = this.renderDistance * this.renderDistance;
/*  544 */       boolean flag = this.vboEnabled;
/*  545 */       this.vboEnabled = OpenGlHelper.useVbo();
/*      */       
/*  547 */       if (flag && !this.vboEnabled) {
/*      */         
/*  549 */         this.renderContainer = new RenderList();
/*  550 */         this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */       }
/*  552 */       else if (!flag && this.vboEnabled) {
/*      */         
/*  554 */         this.renderContainer = new VboRenderList();
/*  555 */         this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */       } 
/*      */       
/*  558 */       if (flag != this.vboEnabled) {
/*      */         
/*  560 */         generateStars();
/*  561 */         generateSky();
/*  562 */         generateSky2();
/*      */       } 
/*      */       
/*  565 */       if (this.viewFrustum != null)
/*      */       {
/*  567 */         this.viewFrustum.deleteGlResources();
/*      */       }
/*      */       
/*  570 */       stopChunkUpdates();
/*  571 */       Set var5 = this.field_181024_n;
/*      */       
/*  573 */       synchronized (this.field_181024_n) {
/*      */         
/*  575 */         this.field_181024_n.clear();
/*      */       } 
/*      */       
/*  578 */       this.viewFrustum = new ViewFrustum((World)this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
/*      */       
/*  580 */       if (this.theWorld != null) {
/*      */         
/*  582 */         Entity entity = this.mc.getRenderViewEntity();
/*      */         
/*  584 */         if (entity != null)
/*      */         {
/*  586 */           this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
/*      */         }
/*      */       } 
/*      */       
/*  590 */       this.renderEntitiesStartupCounter = 2;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void stopChunkUpdates() {
/*  596 */     this.chunksToUpdate.clear();
/*  597 */     this.renderDispatcher.stopChunkUpdates();
/*      */   }
/*      */ 
/*      */   
/*      */   public void createBindEntityOutlineFbs(int p_72720_1_, int p_72720_2_) {
/*  602 */     if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null)
/*      */     {
/*  604 */       this.entityOutlineShader.createBindFramebuffers(p_72720_1_, p_72720_2_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore #4
/*      */     //   3: getstatic optfine/Reflector.MinecraftForgeClient_getRenderPass : Loptfine/ReflectorMethod;
/*      */     //   6: invokevirtual exists : ()Z
/*      */     //   9: ifeq -> 24
/*      */     //   12: getstatic optfine/Reflector.MinecraftForgeClient_getRenderPass : Loptfine/ReflectorMethod;
/*      */     //   15: iconst_0
/*      */     //   16: anewarray java/lang/Object
/*      */     //   19: invokestatic callInt : (Loptfine/ReflectorMethod;[Ljava/lang/Object;)I
/*      */     //   22: istore #4
/*      */     //   24: aload_0
/*      */     //   25: getfield renderEntitiesStartupCounter : I
/*      */     //   28: ifle -> 50
/*      */     //   31: iload #4
/*      */     //   33: ifle -> 37
/*      */     //   36: return
/*      */     //   37: aload_0
/*      */     //   38: dup
/*      */     //   39: getfield renderEntitiesStartupCounter : I
/*      */     //   42: iconst_1
/*      */     //   43: isub
/*      */     //   44: putfield renderEntitiesStartupCounter : I
/*      */     //   47: goto -> 2092
/*      */     //   50: aload_1
/*      */     //   51: getfield prevPosX : D
/*      */     //   54: aload_1
/*      */     //   55: getfield posX : D
/*      */     //   58: aload_1
/*      */     //   59: getfield prevPosX : D
/*      */     //   62: dsub
/*      */     //   63: fload_3
/*      */     //   64: f2d
/*      */     //   65: dmul
/*      */     //   66: dadd
/*      */     //   67: dstore #5
/*      */     //   69: aload_1
/*      */     //   70: getfield prevPosY : D
/*      */     //   73: aload_1
/*      */     //   74: getfield posY : D
/*      */     //   77: aload_1
/*      */     //   78: getfield prevPosY : D
/*      */     //   81: dsub
/*      */     //   82: fload_3
/*      */     //   83: f2d
/*      */     //   84: dmul
/*      */     //   85: dadd
/*      */     //   86: dstore #7
/*      */     //   88: aload_1
/*      */     //   89: getfield prevPosZ : D
/*      */     //   92: aload_1
/*      */     //   93: getfield posZ : D
/*      */     //   96: aload_1
/*      */     //   97: getfield prevPosZ : D
/*      */     //   100: dsub
/*      */     //   101: fload_3
/*      */     //   102: f2d
/*      */     //   103: dmul
/*      */     //   104: dadd
/*      */     //   105: dstore #9
/*      */     //   107: aload_0
/*      */     //   108: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   111: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   114: ldc_w 'prepare'
/*      */     //   117: invokevirtual startSection : (Ljava/lang/String;)V
/*      */     //   120: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   123: aload_0
/*      */     //   124: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   127: aload_0
/*      */     //   128: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   131: invokevirtual getTextureManager : ()Lnet/minecraft/client/renderer/texture/TextureManager;
/*      */     //   134: aload_0
/*      */     //   135: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   138: getfield fontRendererObj : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   141: aload_0
/*      */     //   142: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   145: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   148: fload_3
/*      */     //   149: invokevirtual cacheActiveRenderInfo : (Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;F)V
/*      */     //   152: aload_0
/*      */     //   153: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   156: aload_0
/*      */     //   157: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   160: aload_0
/*      */     //   161: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   164: getfield fontRendererObj : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   167: aload_0
/*      */     //   168: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   171: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   174: aload_0
/*      */     //   175: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   178: getfield pointedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   181: aload_0
/*      */     //   182: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   185: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   188: fload_3
/*      */     //   189: invokevirtual cacheActiveRenderInfo : (Lnet/minecraft/world/World;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V
/*      */     //   192: iload #4
/*      */     //   194: ifne -> 217
/*      */     //   197: aload_0
/*      */     //   198: iconst_0
/*      */     //   199: putfield countEntitiesTotal : I
/*      */     //   202: aload_0
/*      */     //   203: iconst_0
/*      */     //   204: putfield countEntitiesRendered : I
/*      */     //   207: aload_0
/*      */     //   208: iconst_0
/*      */     //   209: putfield countEntitiesHidden : I
/*      */     //   212: aload_0
/*      */     //   213: iconst_0
/*      */     //   214: putfield countTileEntitiesRendered : I
/*      */     //   217: aload_0
/*      */     //   218: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   221: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   224: astore #11
/*      */     //   226: aload #11
/*      */     //   228: getfield lastTickPosX : D
/*      */     //   231: aload #11
/*      */     //   233: getfield posX : D
/*      */     //   236: aload #11
/*      */     //   238: getfield lastTickPosX : D
/*      */     //   241: dsub
/*      */     //   242: fload_3
/*      */     //   243: f2d
/*      */     //   244: dmul
/*      */     //   245: dadd
/*      */     //   246: dstore #12
/*      */     //   248: aload #11
/*      */     //   250: getfield lastTickPosY : D
/*      */     //   253: aload #11
/*      */     //   255: getfield posY : D
/*      */     //   258: aload #11
/*      */     //   260: getfield lastTickPosY : D
/*      */     //   263: dsub
/*      */     //   264: fload_3
/*      */     //   265: f2d
/*      */     //   266: dmul
/*      */     //   267: dadd
/*      */     //   268: dstore #14
/*      */     //   270: aload #11
/*      */     //   272: getfield lastTickPosZ : D
/*      */     //   275: aload #11
/*      */     //   277: getfield posZ : D
/*      */     //   280: aload #11
/*      */     //   282: getfield lastTickPosZ : D
/*      */     //   285: dsub
/*      */     //   286: fload_3
/*      */     //   287: f2d
/*      */     //   288: dmul
/*      */     //   289: dadd
/*      */     //   290: dstore #16
/*      */     //   292: dload #12
/*      */     //   294: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerX : D
/*      */     //   297: dload #14
/*      */     //   299: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerY : D
/*      */     //   302: dload #16
/*      */     //   304: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerZ : D
/*      */     //   307: aload_0
/*      */     //   308: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   311: dload #12
/*      */     //   313: dload #14
/*      */     //   315: dload #16
/*      */     //   317: invokevirtual setRenderPosition : (DDD)V
/*      */     //   320: aload_0
/*      */     //   321: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   324: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   327: invokevirtual enableLightmap : ()V
/*      */     //   330: aload_0
/*      */     //   331: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   334: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   337: ldc_w 'global'
/*      */     //   340: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   343: aload_0
/*      */     //   344: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   347: invokevirtual getLoadedEntityList : ()Ljava/util/List;
/*      */     //   350: astore #18
/*      */     //   352: iload #4
/*      */     //   354: ifne -> 368
/*      */     //   357: aload_0
/*      */     //   358: aload #18
/*      */     //   360: invokeinterface size : ()I
/*      */     //   365: putfield countEntitiesTotal : I
/*      */     //   368: invokestatic isFogOff : ()Z
/*      */     //   371: ifeq -> 390
/*      */     //   374: aload_0
/*      */     //   375: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   378: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   381: getfield fogStandard : Z
/*      */     //   384: ifeq -> 390
/*      */     //   387: invokestatic disableFog : ()V
/*      */     //   390: getstatic optfine/Reflector.ForgeEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   393: invokevirtual exists : ()Z
/*      */     //   396: istore #19
/*      */     //   398: getstatic optfine/Reflector.ForgeTileEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   401: invokevirtual exists : ()Z
/*      */     //   404: istore #20
/*      */     //   406: iconst_0
/*      */     //   407: istore #21
/*      */     //   409: goto -> 497
/*      */     //   412: aload_0
/*      */     //   413: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   416: getfield weatherEffects : Ljava/util/List;
/*      */     //   419: iload #21
/*      */     //   421: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   426: checkcast net/minecraft/entity/Entity
/*      */     //   429: astore #22
/*      */     //   431: iload #19
/*      */     //   433: ifeq -> 459
/*      */     //   436: aload #22
/*      */     //   438: getstatic optfine/Reflector.ForgeEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   441: iconst_1
/*      */     //   442: anewarray java/lang/Object
/*      */     //   445: dup
/*      */     //   446: iconst_0
/*      */     //   447: iload #4
/*      */     //   449: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   452: aastore
/*      */     //   453: invokestatic callBoolean : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   456: ifeq -> 494
/*      */     //   459: aload_0
/*      */     //   460: dup
/*      */     //   461: getfield countEntitiesRendered : I
/*      */     //   464: iconst_1
/*      */     //   465: iadd
/*      */     //   466: putfield countEntitiesRendered : I
/*      */     //   469: aload #22
/*      */     //   471: dload #5
/*      */     //   473: dload #7
/*      */     //   475: dload #9
/*      */     //   477: invokevirtual isInRangeToRender3d : (DDD)Z
/*      */     //   480: ifeq -> 494
/*      */     //   483: aload_0
/*      */     //   484: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   487: aload #22
/*      */     //   489: fload_3
/*      */     //   490: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   493: pop
/*      */     //   494: iinc #21, 1
/*      */     //   497: iload #21
/*      */     //   499: aload_0
/*      */     //   500: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   503: getfield weatherEffects : Ljava/util/List;
/*      */     //   506: invokeinterface size : ()I
/*      */     //   511: if_icmplt -> 412
/*      */     //   514: aload_0
/*      */     //   515: invokevirtual isRenderEntityOutlines : ()Z
/*      */     //   518: ifeq -> 842
/*      */     //   521: sipush #519
/*      */     //   524: invokestatic depthFunc : (I)V
/*      */     //   527: invokestatic disableFog : ()V
/*      */     //   530: aload_0
/*      */     //   531: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   534: invokevirtual framebufferClear : ()V
/*      */     //   537: aload_0
/*      */     //   538: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   541: iconst_0
/*      */     //   542: invokevirtual bindFramebuffer : (Z)V
/*      */     //   545: aload_0
/*      */     //   546: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   549: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   552: ldc_w 'entityOutlines'
/*      */     //   555: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   558: invokestatic disableStandardItemLighting : ()V
/*      */     //   561: aload_0
/*      */     //   562: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   565: iconst_1
/*      */     //   566: invokevirtual setRenderOutlines : (Z)V
/*      */     //   569: iconst_0
/*      */     //   570: istore #21
/*      */     //   572: goto -> 768
/*      */     //   575: aload #18
/*      */     //   577: iload #21
/*      */     //   579: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   584: checkcast net/minecraft/entity/Entity
/*      */     //   587: astore #22
/*      */     //   589: iload #19
/*      */     //   591: ifeq -> 617
/*      */     //   594: aload #22
/*      */     //   596: getstatic optfine/Reflector.ForgeEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   599: iconst_1
/*      */     //   600: anewarray java/lang/Object
/*      */     //   603: dup
/*      */     //   604: iconst_0
/*      */     //   605: iload #4
/*      */     //   607: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   610: aastore
/*      */     //   611: invokestatic callBoolean : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   614: ifeq -> 765
/*      */     //   617: aload_0
/*      */     //   618: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   621: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   624: instanceof net/minecraft/entity/EntityLivingBase
/*      */     //   627: ifeq -> 650
/*      */     //   630: aload_0
/*      */     //   631: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   634: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   637: checkcast net/minecraft/entity/EntityLivingBase
/*      */     //   640: invokevirtual isPlayerSleeping : ()Z
/*      */     //   643: ifeq -> 650
/*      */     //   646: iconst_1
/*      */     //   647: goto -> 651
/*      */     //   650: iconst_0
/*      */     //   651: istore #23
/*      */     //   653: aload #22
/*      */     //   655: dload #5
/*      */     //   657: dload #7
/*      */     //   659: dload #9
/*      */     //   661: invokevirtual isInRangeToRender3d : (DDD)Z
/*      */     //   664: ifeq -> 716
/*      */     //   667: aload #22
/*      */     //   669: getfield ignoreFrustumCheck : Z
/*      */     //   672: ifne -> 704
/*      */     //   675: aload_2
/*      */     //   676: aload #22
/*      */     //   678: invokevirtual getEntityBoundingBox : ()Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   681: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   686: ifne -> 704
/*      */     //   689: aload #22
/*      */     //   691: getfield riddenByEntity : Lnet/minecraft/entity/Entity;
/*      */     //   694: aload_0
/*      */     //   695: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   698: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   701: if_acmpne -> 716
/*      */     //   704: aload #22
/*      */     //   706: instanceof net/minecraft/entity/player/EntityPlayer
/*      */     //   709: ifeq -> 716
/*      */     //   712: iconst_1
/*      */     //   713: goto -> 717
/*      */     //   716: iconst_0
/*      */     //   717: istore #24
/*      */     //   719: aload #22
/*      */     //   721: aload_0
/*      */     //   722: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   725: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   728: if_acmpne -> 749
/*      */     //   731: aload_0
/*      */     //   732: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   735: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   738: getfield thirdPersonView : I
/*      */     //   741: ifne -> 749
/*      */     //   744: iload #23
/*      */     //   746: ifeq -> 765
/*      */     //   749: iload #24
/*      */     //   751: ifeq -> 765
/*      */     //   754: aload_0
/*      */     //   755: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   758: aload #22
/*      */     //   760: fload_3
/*      */     //   761: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   764: pop
/*      */     //   765: iinc #21, 1
/*      */     //   768: iload #21
/*      */     //   770: aload #18
/*      */     //   772: invokeinterface size : ()I
/*      */     //   777: if_icmplt -> 575
/*      */     //   780: aload_0
/*      */     //   781: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   784: iconst_0
/*      */     //   785: invokevirtual setRenderOutlines : (Z)V
/*      */     //   788: invokestatic enableStandardItemLighting : ()V
/*      */     //   791: iconst_0
/*      */     //   792: invokestatic depthMask : (Z)V
/*      */     //   795: aload_0
/*      */     //   796: getfield entityOutlineShader : Lnet/minecraft/client/shader/ShaderGroup;
/*      */     //   799: fload_3
/*      */     //   800: invokevirtual loadShaderGroup : (F)V
/*      */     //   803: invokestatic enableLighting : ()V
/*      */     //   806: iconst_1
/*      */     //   807: invokestatic depthMask : (Z)V
/*      */     //   810: aload_0
/*      */     //   811: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   814: invokevirtual getFramebuffer : ()Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   817: iconst_0
/*      */     //   818: invokevirtual bindFramebuffer : (Z)V
/*      */     //   821: invokestatic enableFog : ()V
/*      */     //   824: invokestatic enableBlend : ()V
/*      */     //   827: invokestatic enableColorMaterial : ()V
/*      */     //   830: sipush #515
/*      */     //   833: invokestatic depthFunc : (I)V
/*      */     //   836: invokestatic enableDepth : ()V
/*      */     //   839: invokestatic enableAlpha : ()V
/*      */     //   842: aload_0
/*      */     //   843: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   846: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   849: ldc_w 'entities'
/*      */     //   852: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   855: aload_0
/*      */     //   856: getfield renderInfosEntities : Ljava/util/List;
/*      */     //   859: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   864: astore #21
/*      */     //   866: aload_0
/*      */     //   867: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   870: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   873: getfield fancyGraphics : Z
/*      */     //   876: istore #22
/*      */     //   878: aload_0
/*      */     //   879: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   882: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   885: invokestatic isDroppedItemsFancy : ()Z
/*      */     //   888: putfield fancyGraphics : Z
/*      */     //   891: goto -> 1245
/*      */     //   894: aload #21
/*      */     //   896: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   901: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   904: astore #23
/*      */     //   906: aload_0
/*      */     //   907: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   910: aload #23
/*      */     //   912: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   915: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   918: invokevirtual getChunkFromBlockCoords : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/world/chunk/Chunk;
/*      */     //   921: astore #24
/*      */     //   923: aload #24
/*      */     //   925: invokevirtual getEntityLists : ()[Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   928: aload #23
/*      */     //   930: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   933: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   936: invokevirtual getY : ()I
/*      */     //   939: bipush #16
/*      */     //   941: idiv
/*      */     //   942: aaload
/*      */     //   943: astore #25
/*      */     //   945: aload #25
/*      */     //   947: invokevirtual isEmpty : ()Z
/*      */     //   950: ifne -> 1245
/*      */     //   953: aload #25
/*      */     //   955: invokevirtual iterator : ()Ljava/util/Iterator;
/*      */     //   958: astore #26
/*      */     //   960: aload #26
/*      */     //   962: invokeinterface hasNext : ()Z
/*      */     //   967: ifne -> 973
/*      */     //   970: goto -> 1245
/*      */     //   973: aload #26
/*      */     //   975: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   980: checkcast net/minecraft/entity/Entity
/*      */     //   983: astore #27
/*      */     //   985: iload #19
/*      */     //   987: ifeq -> 1013
/*      */     //   990: aload #27
/*      */     //   992: getstatic optfine/Reflector.ForgeEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   995: iconst_1
/*      */     //   996: anewarray java/lang/Object
/*      */     //   999: dup
/*      */     //   1000: iconst_0
/*      */     //   1001: iload #4
/*      */     //   1003: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1006: aastore
/*      */     //   1007: invokestatic callBoolean : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1010: ifeq -> 960
/*      */     //   1013: aload_0
/*      */     //   1014: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1017: aload #27
/*      */     //   1019: aload_2
/*      */     //   1020: dload #5
/*      */     //   1022: dload #7
/*      */     //   1024: dload #9
/*      */     //   1026: invokevirtual shouldRender : (Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z
/*      */     //   1029: ifne -> 1051
/*      */     //   1032: aload #27
/*      */     //   1034: getfield riddenByEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1037: aload_0
/*      */     //   1038: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1041: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1044: if_acmpeq -> 1051
/*      */     //   1047: iconst_0
/*      */     //   1048: goto -> 1052
/*      */     //   1051: iconst_1
/*      */     //   1052: istore #28
/*      */     //   1054: iload #28
/*      */     //   1056: ifne -> 1062
/*      */     //   1059: goto -> 1216
/*      */     //   1062: aload_0
/*      */     //   1063: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1066: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1069: instanceof net/minecraft/entity/EntityLivingBase
/*      */     //   1072: ifeq -> 1091
/*      */     //   1075: aload_0
/*      */     //   1076: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1079: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1082: checkcast net/minecraft/entity/EntityLivingBase
/*      */     //   1085: invokevirtual isPlayerSleeping : ()Z
/*      */     //   1088: goto -> 1092
/*      */     //   1091: iconst_0
/*      */     //   1092: istore #29
/*      */     //   1094: aload #27
/*      */     //   1096: aload_0
/*      */     //   1097: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1100: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   1103: if_acmpne -> 1124
/*      */     //   1106: aload_0
/*      */     //   1107: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1110: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1113: getfield thirdPersonView : I
/*      */     //   1116: ifne -> 1124
/*      */     //   1119: iload #29
/*      */     //   1121: ifeq -> 960
/*      */     //   1124: aload #27
/*      */     //   1126: getfield posY : D
/*      */     //   1129: dconst_0
/*      */     //   1130: dcmpg
/*      */     //   1131: iflt -> 1165
/*      */     //   1134: aload #27
/*      */     //   1136: getfield posY : D
/*      */     //   1139: ldc2_w 256.0
/*      */     //   1142: dcmpl
/*      */     //   1143: ifge -> 1165
/*      */     //   1146: aload_0
/*      */     //   1147: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1150: new net/minecraft/util/BlockPos
/*      */     //   1153: dup
/*      */     //   1154: aload #27
/*      */     //   1156: invokespecial <init> : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1159: invokevirtual isBlockLoaded : (Lnet/minecraft/util/BlockPos;)Z
/*      */     //   1162: ifeq -> 960
/*      */     //   1165: aload_0
/*      */     //   1166: dup
/*      */     //   1167: getfield countEntitiesRendered : I
/*      */     //   1170: iconst_1
/*      */     //   1171: iadd
/*      */     //   1172: putfield countEntitiesRendered : I
/*      */     //   1175: aload #27
/*      */     //   1177: invokevirtual getClass : ()Ljava/lang/Class;
/*      */     //   1180: ldc_w net/minecraft/entity/item/EntityItemFrame
/*      */     //   1183: if_acmpne -> 1194
/*      */     //   1186: aload #27
/*      */     //   1188: ldc2_w 0.06
/*      */     //   1191: putfield renderDistanceWeight : D
/*      */     //   1194: aload_0
/*      */     //   1195: aload #27
/*      */     //   1197: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1200: aload_0
/*      */     //   1201: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1204: aload #27
/*      */     //   1206: fload_3
/*      */     //   1207: invokevirtual renderEntitySimple : (Lnet/minecraft/entity/Entity;F)Z
/*      */     //   1210: pop
/*      */     //   1211: aload_0
/*      */     //   1212: aconst_null
/*      */     //   1213: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   1216: iload #28
/*      */     //   1218: ifne -> 960
/*      */     //   1221: aload #27
/*      */     //   1223: instanceof net/minecraft/entity/projectile/EntityWitherSkull
/*      */     //   1226: ifeq -> 960
/*      */     //   1229: aload_0
/*      */     //   1230: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1233: invokevirtual getRenderManager : ()Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1236: aload #27
/*      */     //   1238: fload_3
/*      */     //   1239: invokevirtual renderWitherSkull : (Lnet/minecraft/entity/Entity;F)V
/*      */     //   1242: goto -> 960
/*      */     //   1245: aload #21
/*      */     //   1247: invokeinterface hasNext : ()Z
/*      */     //   1252: ifne -> 894
/*      */     //   1255: aload_0
/*      */     //   1256: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1259: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1262: iload #22
/*      */     //   1264: putfield fancyGraphics : Z
/*      */     //   1267: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1270: invokevirtual getFontRenderer : ()Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   1273: astore #23
/*      */     //   1275: aload_0
/*      */     //   1276: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1279: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   1282: ldc_w 'blockentities'
/*      */     //   1285: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   1288: invokestatic enableStandardItemLighting : ()V
/*      */     //   1291: aload_0
/*      */     //   1292: getfield renderInfosTileEntities : Ljava/util/List;
/*      */     //   1295: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1300: astore #25
/*      */     //   1302: goto -> 1538
/*      */     //   1305: aload #25
/*      */     //   1307: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1312: astore #24
/*      */     //   1314: aload #24
/*      */     //   1316: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   1319: astore #26
/*      */     //   1321: aload #26
/*      */     //   1323: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   1326: invokevirtual getCompiledChunk : ()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
/*      */     //   1329: invokevirtual getTileEntities : ()Ljava/util/List;
/*      */     //   1332: astore #27
/*      */     //   1334: aload #27
/*      */     //   1336: invokeinterface isEmpty : ()Z
/*      */     //   1341: ifne -> 1538
/*      */     //   1344: aload #27
/*      */     //   1346: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1351: astore #28
/*      */     //   1353: aload #28
/*      */     //   1355: invokeinterface hasNext : ()Z
/*      */     //   1360: ifne -> 1366
/*      */     //   1363: goto -> 1538
/*      */     //   1366: aload #28
/*      */     //   1368: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1373: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1376: astore #29
/*      */     //   1378: iload #20
/*      */     //   1380: ifne -> 1386
/*      */     //   1383: goto -> 1442
/*      */     //   1386: aload #29
/*      */     //   1388: getstatic optfine/Reflector.ForgeTileEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   1391: iconst_1
/*      */     //   1392: anewarray java/lang/Object
/*      */     //   1395: dup
/*      */     //   1396: iconst_0
/*      */     //   1397: iload #4
/*      */     //   1399: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1402: aastore
/*      */     //   1403: invokestatic callBoolean : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1406: ifeq -> 1353
/*      */     //   1409: aload #29
/*      */     //   1411: getstatic optfine/Reflector.ForgeTileEntity_getRenderBoundingBox : Loptfine/ReflectorMethod;
/*      */     //   1414: iconst_0
/*      */     //   1415: anewarray java/lang/Object
/*      */     //   1418: invokestatic call : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1421: checkcast net/minecraft/util/AxisAlignedBB
/*      */     //   1424: astore #30
/*      */     //   1426: aload #30
/*      */     //   1428: ifnull -> 1442
/*      */     //   1431: aload_2
/*      */     //   1432: aload #30
/*      */     //   1434: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   1439: ifeq -> 1353
/*      */     //   1442: aload #29
/*      */     //   1444: invokevirtual getClass : ()Ljava/lang/Class;
/*      */     //   1447: astore #30
/*      */     //   1449: aload #30
/*      */     //   1451: ldc_w net/minecraft/tileentity/TileEntitySign
/*      */     //   1454: if_acmpne -> 1509
/*      */     //   1457: getstatic optfine/Config.zoomMode : Z
/*      */     //   1460: ifne -> 1509
/*      */     //   1463: aload_0
/*      */     //   1464: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1467: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1470: astore #31
/*      */     //   1472: aload #29
/*      */     //   1474: aload #31
/*      */     //   1476: getfield posX : D
/*      */     //   1479: aload #31
/*      */     //   1481: getfield posY : D
/*      */     //   1484: aload #31
/*      */     //   1486: getfield posZ : D
/*      */     //   1489: invokevirtual getDistanceSq : (DDD)D
/*      */     //   1492: dstore #32
/*      */     //   1494: dload #32
/*      */     //   1496: ldc2_w 256.0
/*      */     //   1499: dcmpl
/*      */     //   1500: ifle -> 1509
/*      */     //   1503: aload #23
/*      */     //   1505: iconst_0
/*      */     //   1506: putfield enabled : Z
/*      */     //   1509: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1512: aload #29
/*      */     //   1514: fload_3
/*      */     //   1515: iconst_m1
/*      */     //   1516: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1519: aload_0
/*      */     //   1520: dup
/*      */     //   1521: getfield countTileEntitiesRendered : I
/*      */     //   1524: iconst_1
/*      */     //   1525: iadd
/*      */     //   1526: putfield countTileEntitiesRendered : I
/*      */     //   1529: aload #23
/*      */     //   1531: iconst_1
/*      */     //   1532: putfield enabled : Z
/*      */     //   1535: goto -> 1353
/*      */     //   1538: aload #25
/*      */     //   1540: invokeinterface hasNext : ()Z
/*      */     //   1545: ifne -> 1305
/*      */     //   1548: aload_0
/*      */     //   1549: getfield field_181024_n : Ljava/util/Set;
/*      */     //   1552: astore #24
/*      */     //   1554: aload_0
/*      */     //   1555: getfield field_181024_n : Ljava/util/Set;
/*      */     //   1558: dup
/*      */     //   1559: astore #25
/*      */     //   1561: monitorenter
/*      */     //   1562: aload_0
/*      */     //   1563: getfield field_181024_n : Ljava/util/Set;
/*      */     //   1566: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1571: astore #27
/*      */     //   1573: goto -> 1741
/*      */     //   1576: aload #27
/*      */     //   1578: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1583: astore #26
/*      */     //   1585: iload #20
/*      */     //   1587: ifeq -> 1652
/*      */     //   1590: aload #26
/*      */     //   1592: getstatic optfine/Reflector.ForgeTileEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   1595: iconst_1
/*      */     //   1596: anewarray java/lang/Object
/*      */     //   1599: dup
/*      */     //   1600: iconst_0
/*      */     //   1601: iload #4
/*      */     //   1603: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1606: aastore
/*      */     //   1607: invokestatic callBoolean : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1610: ifne -> 1616
/*      */     //   1613: goto -> 1741
/*      */     //   1616: aload #26
/*      */     //   1618: getstatic optfine/Reflector.ForgeTileEntity_getRenderBoundingBox : Loptfine/ReflectorMethod;
/*      */     //   1621: iconst_0
/*      */     //   1622: anewarray java/lang/Object
/*      */     //   1625: invokestatic call : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1628: checkcast net/minecraft/util/AxisAlignedBB
/*      */     //   1631: astore #28
/*      */     //   1633: aload #28
/*      */     //   1635: ifnull -> 1652
/*      */     //   1638: aload_2
/*      */     //   1639: aload #28
/*      */     //   1641: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   1646: ifne -> 1652
/*      */     //   1649: goto -> 1741
/*      */     //   1652: aload #26
/*      */     //   1654: invokevirtual getClass : ()Ljava/lang/Class;
/*      */     //   1657: astore #28
/*      */     //   1659: aload #28
/*      */     //   1661: ldc_w net/minecraft/tileentity/TileEntitySign
/*      */     //   1664: if_acmpne -> 1722
/*      */     //   1667: getstatic optfine/Config.zoomMode : Z
/*      */     //   1670: ifne -> 1722
/*      */     //   1673: aload_0
/*      */     //   1674: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1677: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1680: astore #29
/*      */     //   1682: aload #26
/*      */     //   1684: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1687: aload #29
/*      */     //   1689: getfield posX : D
/*      */     //   1692: aload #29
/*      */     //   1694: getfield posY : D
/*      */     //   1697: aload #29
/*      */     //   1699: getfield posZ : D
/*      */     //   1702: invokevirtual getDistanceSq : (DDD)D
/*      */     //   1705: dstore #30
/*      */     //   1707: dload #30
/*      */     //   1709: ldc2_w 256.0
/*      */     //   1712: dcmpl
/*      */     //   1713: ifle -> 1722
/*      */     //   1716: aload #23
/*      */     //   1718: iconst_0
/*      */     //   1719: putfield enabled : Z
/*      */     //   1722: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1725: aload #26
/*      */     //   1727: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1730: fload_3
/*      */     //   1731: iconst_m1
/*      */     //   1732: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1735: aload #23
/*      */     //   1737: iconst_1
/*      */     //   1738: putfield enabled : Z
/*      */     //   1741: aload #27
/*      */     //   1743: invokeinterface hasNext : ()Z
/*      */     //   1748: ifne -> 1576
/*      */     //   1751: aload #25
/*      */     //   1753: monitorexit
/*      */     //   1754: goto -> 1761
/*      */     //   1757: aload #25
/*      */     //   1759: monitorexit
/*      */     //   1760: athrow
/*      */     //   1761: aload_0
/*      */     //   1762: invokespecial preRenderDamagedBlocks : ()V
/*      */     //   1765: aload_0
/*      */     //   1766: getfield damagedBlocks : Ljava/util/Map;
/*      */     //   1769: invokeinterface values : ()Ljava/util/Collection;
/*      */     //   1774: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1779: astore #26
/*      */     //   1781: goto -> 2058
/*      */     //   1784: aload #26
/*      */     //   1786: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1791: astore #25
/*      */     //   1793: aload #25
/*      */     //   1795: checkcast net/minecraft/client/renderer/DestroyBlockProgress
/*      */     //   1798: invokevirtual getPosition : ()Lnet/minecraft/util/BlockPos;
/*      */     //   1801: astore #27
/*      */     //   1803: aload_0
/*      */     //   1804: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1807: aload #27
/*      */     //   1809: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1812: astore #28
/*      */     //   1814: aload #28
/*      */     //   1816: instanceof net/minecraft/tileentity/TileEntityChest
/*      */     //   1819: ifeq -> 1890
/*      */     //   1822: aload #28
/*      */     //   1824: checkcast net/minecraft/tileentity/TileEntityChest
/*      */     //   1827: astore #29
/*      */     //   1829: aload #29
/*      */     //   1831: getfield adjacentChestXNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1834: ifnull -> 1861
/*      */     //   1837: aload #27
/*      */     //   1839: getstatic net/minecraft/util/EnumFacing.WEST : Lnet/minecraft/util/EnumFacing;
/*      */     //   1842: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
/*      */     //   1845: astore #27
/*      */     //   1847: aload_0
/*      */     //   1848: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1851: aload #27
/*      */     //   1853: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1856: astore #28
/*      */     //   1858: goto -> 1890
/*      */     //   1861: aload #29
/*      */     //   1863: getfield adjacentChestZNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1866: ifnull -> 1890
/*      */     //   1869: aload #27
/*      */     //   1871: getstatic net/minecraft/util/EnumFacing.NORTH : Lnet/minecraft/util/EnumFacing;
/*      */     //   1874: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/BlockPos;
/*      */     //   1877: astore #27
/*      */     //   1879: aload_0
/*      */     //   1880: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1883: aload #27
/*      */     //   1885: invokevirtual getTileEntity : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1888: astore #28
/*      */     //   1890: aload_0
/*      */     //   1891: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1894: aload #27
/*      */     //   1896: invokevirtual getBlockState : (Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   1899: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*      */     //   1904: astore #29
/*      */     //   1906: iload #20
/*      */     //   1908: ifeq -> 1992
/*      */     //   1911: iconst_0
/*      */     //   1912: istore #30
/*      */     //   1914: aload #28
/*      */     //   1916: ifnull -> 2036
/*      */     //   1919: aload #28
/*      */     //   1921: getstatic optfine/Reflector.ForgeTileEntity_shouldRenderInPass : Loptfine/ReflectorMethod;
/*      */     //   1924: iconst_1
/*      */     //   1925: anewarray java/lang/Object
/*      */     //   1928: dup
/*      */     //   1929: iconst_0
/*      */     //   1930: iload #4
/*      */     //   1932: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1935: aastore
/*      */     //   1936: invokestatic callBoolean : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1939: ifeq -> 2036
/*      */     //   1942: aload #28
/*      */     //   1944: getstatic optfine/Reflector.ForgeTileEntity_canRenderBreaking : Loptfine/ReflectorMethod;
/*      */     //   1947: iconst_0
/*      */     //   1948: anewarray java/lang/Object
/*      */     //   1951: invokestatic callBoolean : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1954: ifeq -> 2036
/*      */     //   1957: aload #28
/*      */     //   1959: getstatic optfine/Reflector.ForgeTileEntity_getRenderBoundingBox : Loptfine/ReflectorMethod;
/*      */     //   1962: iconst_0
/*      */     //   1963: anewarray java/lang/Object
/*      */     //   1966: invokestatic call : (Ljava/lang/Object;Loptfine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1969: checkcast net/minecraft/util/AxisAlignedBB
/*      */     //   1972: astore #31
/*      */     //   1974: aload #31
/*      */     //   1976: ifnull -> 2036
/*      */     //   1979: aload_2
/*      */     //   1980: aload #31
/*      */     //   1982: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/AxisAlignedBB;)Z
/*      */     //   1987: istore #30
/*      */     //   1989: goto -> 2036
/*      */     //   1992: aload #28
/*      */     //   1994: ifnull -> 2033
/*      */     //   1997: aload #29
/*      */     //   1999: instanceof net/minecraft/block/BlockChest
/*      */     //   2002: ifne -> 2029
/*      */     //   2005: aload #29
/*      */     //   2007: instanceof net/minecraft/block/BlockEnderChest
/*      */     //   2010: ifne -> 2029
/*      */     //   2013: aload #29
/*      */     //   2015: instanceof net/minecraft/block/BlockSign
/*      */     //   2018: ifne -> 2029
/*      */     //   2021: aload #29
/*      */     //   2023: instanceof net/minecraft/block/BlockSkull
/*      */     //   2026: ifeq -> 2033
/*      */     //   2029: iconst_1
/*      */     //   2030: goto -> 2034
/*      */     //   2033: iconst_0
/*      */     //   2034: istore #30
/*      */     //   2036: iload #30
/*      */     //   2038: ifeq -> 2058
/*      */     //   2041: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   2044: aload #28
/*      */     //   2046: fload_3
/*      */     //   2047: aload #25
/*      */     //   2049: checkcast net/minecraft/client/renderer/DestroyBlockProgress
/*      */     //   2052: invokevirtual getPartialBlockDamage : ()I
/*      */     //   2055: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   2058: aload #26
/*      */     //   2060: invokeinterface hasNext : ()Z
/*      */     //   2065: ifne -> 1784
/*      */     //   2068: aload_0
/*      */     //   2069: invokespecial postRenderDamagedBlocks : ()V
/*      */     //   2072: aload_0
/*      */     //   2073: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2076: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   2079: invokevirtual disableLightmap : ()V
/*      */     //   2082: aload_0
/*      */     //   2083: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2086: getfield mcProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   2089: invokevirtual endSection : ()V
/*      */     //   2092: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #610	-> 0
/*      */     //   #612	-> 3
/*      */     //   #614	-> 12
/*      */     //   #617	-> 24
/*      */     //   #619	-> 31
/*      */     //   #621	-> 36
/*      */     //   #624	-> 37
/*      */     //   #625	-> 47
/*      */     //   #628	-> 50
/*      */     //   #629	-> 69
/*      */     //   #630	-> 88
/*      */     //   #631	-> 107
/*      */     //   #632	-> 120
/*      */     //   #633	-> 152
/*      */     //   #635	-> 192
/*      */     //   #637	-> 197
/*      */     //   #638	-> 202
/*      */     //   #639	-> 207
/*      */     //   #640	-> 212
/*      */     //   #643	-> 217
/*      */     //   #644	-> 226
/*      */     //   #645	-> 248
/*      */     //   #646	-> 270
/*      */     //   #647	-> 292
/*      */     //   #648	-> 297
/*      */     //   #649	-> 302
/*      */     //   #650	-> 307
/*      */     //   #651	-> 320
/*      */     //   #652	-> 330
/*      */     //   #653	-> 343
/*      */     //   #655	-> 352
/*      */     //   #657	-> 357
/*      */     //   #660	-> 368
/*      */     //   #662	-> 387
/*      */     //   #665	-> 390
/*      */     //   #666	-> 398
/*      */     //   #668	-> 406
/*      */     //   #670	-> 412
/*      */     //   #672	-> 431
/*      */     //   #674	-> 459
/*      */     //   #676	-> 469
/*      */     //   #678	-> 483
/*      */     //   #668	-> 494
/*      */     //   #683	-> 514
/*      */     //   #685	-> 521
/*      */     //   #686	-> 527
/*      */     //   #687	-> 530
/*      */     //   #688	-> 537
/*      */     //   #689	-> 545
/*      */     //   #690	-> 558
/*      */     //   #691	-> 561
/*      */     //   #693	-> 569
/*      */     //   #695	-> 575
/*      */     //   #697	-> 589
/*      */     //   #699	-> 617
/*      */     //   #701	-> 653
/*      */     //   #703	-> 719
/*      */     //   #705	-> 754
/*      */     //   #693	-> 765
/*      */     //   #710	-> 780
/*      */     //   #711	-> 788
/*      */     //   #712	-> 791
/*      */     //   #713	-> 795
/*      */     //   #714	-> 803
/*      */     //   #715	-> 806
/*      */     //   #716	-> 810
/*      */     //   #717	-> 821
/*      */     //   #718	-> 824
/*      */     //   #719	-> 827
/*      */     //   #720	-> 830
/*      */     //   #721	-> 836
/*      */     //   #722	-> 839
/*      */     //   #725	-> 842
/*      */     //   #726	-> 855
/*      */     //   #727	-> 866
/*      */     //   #728	-> 878
/*      */     //   #731	-> 891
/*      */     //   #733	-> 894
/*      */     //   #734	-> 906
/*      */     //   #735	-> 923
/*      */     //   #737	-> 945
/*      */     //   #739	-> 953
/*      */     //   #748	-> 960
/*      */     //   #750	-> 970
/*      */     //   #753	-> 973
/*      */     //   #755	-> 985
/*      */     //   #757	-> 1013
/*      */     //   #759	-> 1054
/*      */     //   #761	-> 1059
/*      */     //   #764	-> 1062
/*      */     //   #766	-> 1094
/*      */     //   #768	-> 1165
/*      */     //   #770	-> 1175
/*      */     //   #772	-> 1186
/*      */     //   #775	-> 1194
/*      */     //   #776	-> 1200
/*      */     //   #777	-> 1211
/*      */     //   #783	-> 1216
/*      */     //   #785	-> 1229
/*      */     //   #741	-> 1242
/*      */     //   #731	-> 1245
/*      */     //   #791	-> 1255
/*      */     //   #792	-> 1267
/*      */     //   #793	-> 1275
/*      */     //   #794	-> 1288
/*      */     //   #797	-> 1291
/*      */     //   #799	-> 1314
/*      */     //   #800	-> 1321
/*      */     //   #802	-> 1334
/*      */     //   #804	-> 1344
/*      */     //   #812	-> 1353
/*      */     //   #814	-> 1363
/*      */     //   #817	-> 1366
/*      */     //   #819	-> 1378
/*      */     //   #821	-> 1383
/*      */     //   #824	-> 1386
/*      */     //   #826	-> 1409
/*      */     //   #828	-> 1426
/*      */     //   #835	-> 1442
/*      */     //   #837	-> 1449
/*      */     //   #839	-> 1463
/*      */     //   #840	-> 1472
/*      */     //   #842	-> 1494
/*      */     //   #844	-> 1503
/*      */     //   #848	-> 1509
/*      */     //   #849	-> 1519
/*      */     //   #850	-> 1529
/*      */     //   #806	-> 1535
/*      */     //   #797	-> 1538
/*      */     //   #855	-> 1548
/*      */     //   #857	-> 1554
/*      */     //   #859	-> 1562
/*      */     //   #861	-> 1585
/*      */     //   #863	-> 1590
/*      */     //   #865	-> 1613
/*      */     //   #867	-> 1616
/*      */     //   #869	-> 1633
/*      */     //   #871	-> 1649
/*      */     //   #875	-> 1652
/*      */     //   #877	-> 1659
/*      */     //   #879	-> 1673
/*      */     //   #880	-> 1682
/*      */     //   #882	-> 1707
/*      */     //   #884	-> 1716
/*      */     //   #888	-> 1722
/*      */     //   #889	-> 1735
/*      */     //   #859	-> 1741
/*      */     //   #857	-> 1751
/*      */     //   #893	-> 1761
/*      */     //   #895	-> 1765
/*      */     //   #897	-> 1793
/*      */     //   #898	-> 1803
/*      */     //   #900	-> 1814
/*      */     //   #902	-> 1822
/*      */     //   #904	-> 1829
/*      */     //   #906	-> 1837
/*      */     //   #907	-> 1847
/*      */     //   #908	-> 1858
/*      */     //   #909	-> 1861
/*      */     //   #911	-> 1869
/*      */     //   #912	-> 1879
/*      */     //   #916	-> 1890
/*      */     //   #919	-> 1906
/*      */     //   #921	-> 1911
/*      */     //   #923	-> 1914
/*      */     //   #925	-> 1957
/*      */     //   #927	-> 1974
/*      */     //   #929	-> 1979
/*      */     //   #932	-> 1989
/*      */     //   #935	-> 1992
/*      */     //   #938	-> 2036
/*      */     //   #940	-> 2041
/*      */     //   #895	-> 2058
/*      */     //   #944	-> 2068
/*      */     //   #945	-> 2072
/*      */     //   #946	-> 2082
/*      */     //   #948	-> 2092
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	2093	0	this	Lnet/minecraft/client/renderer/RenderGlobal;
/*      */     //   0	2093	1	renderViewEntity	Lnet/minecraft/entity/Entity;
/*      */     //   0	2093	2	camera	Lnet/minecraft/client/renderer/culling/ICamera;
/*      */     //   0	2093	3	partialTicks	F
/*      */     //   3	2090	4	i	I
/*      */     //   69	2023	5	d0	D
/*      */     //   88	2004	7	d1	D
/*      */     //   107	1985	9	d2	D
/*      */     //   226	1866	11	entity	Lnet/minecraft/entity/Entity;
/*      */     //   248	1844	12	d3	D
/*      */     //   270	1822	14	d4	D
/*      */     //   292	1800	16	d5	D
/*      */     //   352	1740	18	list	Ljava/util/List;
/*      */     //   398	1694	19	flag	Z
/*      */     //   406	1686	20	flag1	Z
/*      */     //   409	105	21	j	I
/*      */     //   431	63	22	entity1	Lnet/minecraft/entity/Entity;
/*      */     //   572	208	21	k	I
/*      */     //   589	176	22	entity3	Lnet/minecraft/entity/Entity;
/*      */     //   653	112	23	flag2	Z
/*      */     //   719	46	24	flag3	Z
/*      */     //   866	1226	21	iterator1	Ljava/util/Iterator;
/*      */     //   878	1214	22	flag4	Z
/*      */     //   906	339	23	renderglobal$containerlocalrenderinformation	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   923	322	24	chunk	Lnet/minecraft/world/chunk/Chunk;
/*      */     //   945	300	25	classinheritancemultimap	Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   960	285	26	iterator	Ljava/util/Iterator;
/*      */     //   985	257	27	entity2	Lnet/minecraft/entity/Entity;
/*      */     //   1054	188	28	flag5	Z
/*      */     //   1094	122	29	flag6	Z
/*      */     //   1275	817	23	fontrenderer	Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   1314	224	24	renderglobal$containerlocalrenderinformation10	Ljava/lang/Object;
/*      */     //   1321	217	26	renderglobal$containerlocalrenderinformation1	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   1334	204	27	list1	Ljava/util/List;
/*      */     //   1353	185	28	iterator2	Ljava/util/Iterator;
/*      */     //   1378	157	29	tileentity	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1426	16	30	axisalignedbb	Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   1449	86	30	oclass	Ljava/lang/Class;
/*      */     //   1472	37	31	entityplayer	Lnet/minecraft/entity/player/EntityPlayer;
/*      */     //   1494	15	32	d6	D
/*      */     //   1554	538	24	var32	Ljava/util/Set;
/*      */     //   1585	156	26	tileentity1	Ljava/lang/Object;
/*      */     //   1633	19	28	axisalignedbb1	Lnet/minecraft/util/AxisAlignedBB;
/*      */     //   1659	82	28	oclass1	Ljava/lang/Class;
/*      */     //   1682	40	29	entityplayer1	Lnet/minecraft/entity/player/EntityPlayer;
/*      */     //   1707	15	30	d7	D
/*      */     //   1793	265	25	destroyblockprogress	Ljava/lang/Object;
/*      */     //   1803	255	27	blockpos	Lnet/minecraft/util/BlockPos;
/*      */     //   1814	244	28	tileentity2	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1829	61	29	tileentitychest	Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   1906	152	29	block	Lnet/minecraft/block/Block;
/*      */     //   1914	78	30	flag7	Z
/*      */     //   2036	22	30	flag7	Z
/*      */     //   1974	15	31	axisalignedbb2	Lnet/minecraft/util/AxisAlignedBB;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   1562	1754	1757	finally
/*      */     //   1757	1760	1757	finally
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoRenders() {
/*  955 */     int i = this.viewFrustum.renderChunks.length;
/*  956 */     int j = 0;
/*      */     
/*  958 */     for (Object renderglobal$containerlocalrenderinformation0 : this.renderInfos) {
/*      */       
/*  960 */       ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = (ContainerLocalRenderInformation)renderglobal$containerlocalrenderinformation0;
/*  961 */       CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
/*      */       
/*  963 */       if (compiledchunk != CompiledChunk.DUMMY && !compiledchunk.isEmpty())
/*      */       {
/*  965 */         j++;
/*      */       }
/*      */     } 
/*      */     
/*  969 */     return String.format("C: %d/%d %sD: %d, %s", new Object[] { Integer.valueOf(j), Integer.valueOf(i), this.mc.renderChunksMany ? "(s) " : "", Integer.valueOf(this.renderDistanceChunks), this.renderDispatcher.getDebugInfo() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoEntities() {
/*  977 */     return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered) + ", " + Config.getVersion();
/*      */   }
/*      */   
/*      */   public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*      */     Frustum frustum;
/*  982 */     if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks)
/*      */     {
/*  984 */       loadRenderers();
/*      */     }
/*      */     
/*  987 */     this.theWorld.theProfiler.startSection("camera");
/*  988 */     double d0 = viewEntity.posX - this.frustumUpdatePosX;
/*  989 */     double d1 = viewEntity.posY - this.frustumUpdatePosY;
/*  990 */     double d2 = viewEntity.posZ - this.frustumUpdatePosZ;
/*      */     
/*  992 */     if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX || this.frustumUpdatePosChunkY != viewEntity.chunkCoordY || this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0D) {
/*      */       
/*  994 */       this.frustumUpdatePosX = viewEntity.posX;
/*  995 */       this.frustumUpdatePosY = viewEntity.posY;
/*  996 */       this.frustumUpdatePosZ = viewEntity.posZ;
/*  997 */       this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
/*  998 */       this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
/*  999 */       this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
/* 1000 */       this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
/*      */     } 
/*      */     
/* 1003 */     this.theWorld.theProfiler.endStartSection("renderlistcamera");
/* 1004 */     double d3 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
/* 1005 */     double d4 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
/* 1006 */     double d5 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
/* 1007 */     this.renderContainer.initialize(d3, d4, d5);
/* 1008 */     this.theWorld.theProfiler.endStartSection("cull");
/*      */     
/* 1010 */     if (this.debugFixedClippingHelper != null) {
/*      */       
/* 1012 */       Frustum frustum1 = new Frustum(this.debugFixedClippingHelper);
/* 1013 */       frustum1.setPosition(this.debugTerrainFrustumPosition.field_181059_a, this.debugTerrainFrustumPosition.field_181060_b, this.debugTerrainFrustumPosition.field_181061_c);
/* 1014 */       frustum = frustum1;
/*      */     } 
/*      */     
/* 1017 */     this.mc.mcProfiler.endStartSection("culling");
/* 1018 */     BlockPos blockpos1 = new BlockPos(d3, d4 + viewEntity.getEyeHeight(), d5);
/* 1019 */     RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos1);
/* 1020 */     BlockPos blockpos = new BlockPos(MathHelper.floor_double(d3 / 16.0D) * 16, MathHelper.floor_double(d4 / 16.0D) * 16, MathHelper.floor_double(d5 / 16.0D) * 16);
/* 1021 */     this.displayListEntitiesDirty = !(!this.displayListEntitiesDirty && this.chunksToUpdate.isEmpty() && viewEntity.posX == this.lastViewEntityX && viewEntity.posY == this.lastViewEntityY && viewEntity.posZ == this.lastViewEntityZ && viewEntity.rotationPitch == this.lastViewEntityPitch && viewEntity.rotationYaw == this.lastViewEntityYaw);
/* 1022 */     this.lastViewEntityX = viewEntity.posX;
/* 1023 */     this.lastViewEntityY = viewEntity.posY;
/* 1024 */     this.lastViewEntityZ = viewEntity.posZ;
/* 1025 */     this.lastViewEntityPitch = viewEntity.rotationPitch;
/* 1026 */     this.lastViewEntityYaw = viewEntity.rotationYaw;
/* 1027 */     boolean flag = (this.debugFixedClippingHelper != null);
/* 1028 */     Lagometer.timerVisibility.start();
/*      */     
/* 1030 */     if (!flag && this.displayListEntitiesDirty) {
/*      */       
/* 1032 */       this.displayListEntitiesDirty = false;
/* 1033 */       this.renderInfos.clear();
/* 1034 */       this.renderInfosEntities.clear();
/* 1035 */       this.renderInfosTileEntities.clear();
/* 1036 */       this.visibilityDeque.clear();
/* 1037 */       Deque<ContainerLocalRenderInformation> deque = this.visibilityDeque;
/* 1038 */       boolean flag1 = this.mc.renderChunksMany;
/*      */       
/* 1040 */       if (renderchunk != null) {
/*      */         
/* 1042 */         boolean flag2 = false;
/* 1043 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 = new ContainerLocalRenderInformation(renderchunk, null, 0, null);
/* 1044 */         Set set1 = SET_ALL_FACINGS;
/*      */         
/* 1046 */         if (set1.size() == 1) {
/*      */           
/* 1048 */           Vector3f vector3f = getViewVector(viewEntity, partialTicks);
/* 1049 */           EnumFacing enumfacing = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
/* 1050 */           set1.remove(enumfacing);
/*      */         } 
/*      */         
/* 1053 */         if (set1.isEmpty())
/*      */         {
/* 1055 */           flag2 = true;
/*      */         }
/*      */         
/* 1058 */         if (flag2 && !playerSpectator)
/*      */         {
/* 1060 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation3);
/*      */         }
/*      */         else
/*      */         {
/* 1064 */           if (playerSpectator && this.theWorld.getBlockState(blockpos1).getBlock().isOpaqueCube())
/*      */           {
/* 1066 */             flag1 = false;
/*      */           }
/*      */           
/* 1069 */           renderchunk.setFrameIndex(frameCount);
/* 1070 */           deque.add(renderglobal$containerlocalrenderinformation3);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1075 */         int i = (blockpos1.getY() > 0) ? 248 : 8;
/*      */         
/* 1077 */         for (int j = -this.renderDistanceChunks; j <= this.renderDistanceChunks; j++) {
/*      */           
/* 1079 */           for (int k = -this.renderDistanceChunks; k <= this.renderDistanceChunks; k++) {
/*      */             
/* 1081 */             RenderChunk renderchunk2 = this.viewFrustum.getRenderChunk(new BlockPos((j << 4) + 8, i, (k << 4) + 8));
/*      */             
/* 1083 */             if (renderchunk2 != null && frustum.isBoundingBoxInFrustum(renderchunk2.boundingBox)) {
/*      */               
/* 1085 */               renderchunk2.setFrameIndex(frameCount);
/* 1086 */               deque.add(new ContainerLocalRenderInformation(renderchunk2, null, 0, null));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1092 */       EnumFacing[] aenumfacing = EnumFacing.VALUES;
/* 1093 */       int l = aenumfacing.length;
/*      */       
/* 1095 */       while (!deque.isEmpty()) {
/*      */         
/* 1097 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = deque.poll();
/* 1098 */         RenderChunk renderchunk1 = renderglobal$containerlocalrenderinformation.renderChunk;
/* 1099 */         EnumFacing enumfacing2 = renderglobal$containerlocalrenderinformation.facing;
/* 1100 */         BlockPos blockpos2 = renderchunk1.getPosition();
/*      */         
/* 1102 */         if (!renderchunk1.compiledChunk.isEmpty() || renderchunk1.isNeedsUpdate())
/*      */         {
/* 1104 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation);
/*      */         }
/*      */         
/* 1107 */         if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(blockpos2)))
/*      */         {
/* 1109 */           this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation);
/*      */         }
/*      */         
/* 1112 */         if (renderchunk1.getCompiledChunk().getTileEntities().size() > 0)
/*      */         {
/* 1114 */           this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation);
/*      */         }
/*      */         
/* 1117 */         for (int i1 = 0; i1 < l; i1++) {
/*      */           
/* 1119 */           EnumFacing enumfacing1 = aenumfacing[i1];
/*      */           
/* 1121 */           if ((!flag1 || !renderglobal$containerlocalrenderinformation.setFacing.contains(enumfacing1.getOpposite())) && (!flag1 || enumfacing2 == null || renderchunk1.getCompiledChunk().isVisible(enumfacing2.getOpposite(), enumfacing1))) {
/*      */             
/* 1123 */             RenderChunk renderchunk3 = func_181562_a(blockpos1, renderchunk1, enumfacing1);
/*      */             
/* 1125 */             if (renderchunk3 != null && renderchunk3.setFrameIndex(frameCount) && frustum.isBoundingBoxInFrustum(renderchunk3.boundingBox)) {
/*      */               
/* 1127 */               ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = new ContainerLocalRenderInformation(renderchunk3, enumfacing1, renderglobal$containerlocalrenderinformation.counter + 1, null);
/* 1128 */               renderglobal$containerlocalrenderinformation1.setFacing.addAll(renderglobal$containerlocalrenderinformation.setFacing);
/* 1129 */               renderglobal$containerlocalrenderinformation1.setFacing.add(enumfacing1);
/* 1130 */               deque.add(renderglobal$containerlocalrenderinformation1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1137 */     if (this.debugFixTerrainFrustum) {
/*      */       
/* 1139 */       fixTerrainFrustum(d3, d4, d5);
/* 1140 */       this.debugFixTerrainFrustum = false;
/*      */     } 
/*      */     
/* 1143 */     Lagometer.timerVisibility.end();
/* 1144 */     this.renderDispatcher.clearChunkUpdates();
/* 1145 */     Set set = this.chunksToUpdate;
/* 1146 */     this.chunksToUpdate = Sets.newLinkedHashSet();
/* 1147 */     Iterator<ContainerLocalRenderInformation> iterator = this.renderInfos.iterator();
/* 1148 */     Lagometer.timerChunkUpdate.start();
/*      */     
/* 1150 */     while (iterator.hasNext()) {
/*      */       
/* 1152 */       ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 = iterator.next();
/* 1153 */       RenderChunk renderchunk4 = renderglobal$containerlocalrenderinformation2.renderChunk;
/*      */       
/* 1155 */       if (renderchunk4.isNeedsUpdate() || set.contains(renderchunk4)) {
/*      */         
/* 1157 */         this.displayListEntitiesDirty = true;
/*      */         
/* 1159 */         if (isPositionInRenderChunk(blockpos, renderglobal$containerlocalrenderinformation2.renderChunk)) {
/*      */           
/* 1161 */           if (!Config.isActing()) {
/*      */             
/* 1163 */             this.chunksToUpdateForced.add(renderchunk4);
/*      */             
/*      */             continue;
/*      */           } 
/* 1167 */           this.mc.mcProfiler.startSection("build near");
/* 1168 */           this.renderDispatcher.updateChunkNow(renderchunk4);
/* 1169 */           renderchunk4.setNeedsUpdate(false);
/* 1170 */           this.mc.mcProfiler.endSection();
/*      */           
/*      */           continue;
/*      */         } 
/*      */         
/* 1175 */         this.chunksToUpdate.add(renderchunk4);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1180 */     Lagometer.timerChunkUpdate.end();
/* 1181 */     this.chunksToUpdate.addAll(set);
/* 1182 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isPositionInRenderChunk(BlockPos pos, RenderChunk renderChunkIn) {
/* 1187 */     BlockPos blockpos = renderChunkIn.getPosition();
/* 1188 */     return (MathHelper.abs_int(pos.getX() - blockpos.getX()) > 16) ? false : ((MathHelper.abs_int(pos.getY() - blockpos.getY()) > 16) ? false : ((MathHelper.abs_int(pos.getZ() - blockpos.getZ()) <= 16)));
/*      */   }
/*      */ 
/*      */   
/*      */   private Set getVisibleFacings(BlockPos pos) {
/* 1193 */     VisGraph visgraph = new VisGraph();
/* 1194 */     BlockPos blockpos = new BlockPos(pos.getX() >> 4 << 4, pos.getY() >> 4 << 4, pos.getZ() >> 4 << 4);
/* 1195 */     Chunk chunk = this.theWorld.getChunkFromBlockCoords(blockpos);
/*      */     
/* 1197 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos.add(15, 15, 15))) {
/*      */       
/* 1199 */       if (chunk.getBlock((BlockPos)blockpos$mutableblockpos).isOpaqueCube())
/*      */       {
/* 1201 */         visgraph.func_178606_a((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/* 1204 */     return visgraph.func_178609_b(pos);
/*      */   }
/*      */ 
/*      */   
/*      */   private RenderChunk func_181562_a(BlockPos p_181562_1_, RenderChunk p_181562_2_, EnumFacing p_181562_3_) {
/* 1209 */     BlockPos blockpos = p_181562_2_.getPositionOffset16(p_181562_3_);
/*      */     
/* 1211 */     if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
/*      */       
/* 1213 */       int i = MathHelper.abs_int(p_181562_1_.getX() - blockpos.getX());
/* 1214 */       int j = MathHelper.abs_int(p_181562_1_.getZ() - blockpos.getZ());
/*      */       
/* 1216 */       if (Config.isFogOff()) {
/*      */         
/* 1218 */         if (i > this.renderDistance || j > this.renderDistance)
/*      */         {
/* 1220 */           return null;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1225 */         int k = i * i + j * j;
/*      */         
/* 1227 */         if (k > this.renderDistanceSq)
/*      */         {
/* 1229 */           return null;
/*      */         }
/*      */       } 
/*      */       
/* 1233 */       return this.viewFrustum.getRenderChunk(blockpos);
/*      */     } 
/*      */ 
/*      */     
/* 1237 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fixTerrainFrustum(double x, double y, double z) {
/* 1243 */     this.debugFixedClippingHelper = (ClippingHelper)new ClippingHelperImpl();
/* 1244 */     ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
/* 1245 */     Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
/* 1246 */     matrix4f.transpose();
/* 1247 */     Matrix4f matrix4f1 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
/* 1248 */     matrix4f1.transpose();
/* 1249 */     Matrix4f matrix4f2 = new Matrix4f();
/* 1250 */     Matrix4f.mul((Matrix4f)matrix4f1, (Matrix4f)matrix4f, (Matrix4f)matrix4f2);
/* 1251 */     matrix4f2.invert();
/* 1252 */     this.debugTerrainFrustumPosition.field_181059_a = x;
/* 1253 */     this.debugTerrainFrustumPosition.field_181060_b = y;
/* 1254 */     this.debugTerrainFrustumPosition.field_181061_c = z;
/* 1255 */     this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
/* 1256 */     this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
/* 1257 */     this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
/* 1258 */     this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
/* 1259 */     this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
/* 1260 */     this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
/* 1261 */     this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1262 */     this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/* 1264 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1266 */       Matrix4f.transform((Matrix4f)matrix4f2, this.debugTerrainMatrix[i], this.debugTerrainMatrix[i]);
/* 1267 */       (this.debugTerrainMatrix[i]).x /= (this.debugTerrainMatrix[i]).w;
/* 1268 */       (this.debugTerrainMatrix[i]).y /= (this.debugTerrainMatrix[i]).w;
/* 1269 */       (this.debugTerrainMatrix[i]).z /= (this.debugTerrainMatrix[i]).w;
/* 1270 */       (this.debugTerrainMatrix[i]).w = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
/* 1276 */     float f = (float)(entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks);
/* 1277 */     float f1 = (float)(entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks);
/*      */     
/* 1279 */     if ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2)
/*      */     {
/* 1281 */       f += 180.0F;
/*      */     }
/*      */     
/* 1284 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 1285 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 1286 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 1287 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/* 1288 */     return new Vector3f(f3 * f4, f5, f2 * f4);
/*      */   }
/*      */ 
/*      */   
/*      */   public int renderBlockLayer(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
/* 1293 */     RenderHelper.disableStandardItemLighting();
/*      */     
/* 1295 */     if (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT) {
/*      */       
/* 1297 */       this.mc.mcProfiler.startSection("translucent_sort");
/* 1298 */       double d0 = entityIn.posX - this.prevRenderSortX;
/* 1299 */       double d1 = entityIn.posY - this.prevRenderSortY;
/* 1300 */       double d2 = entityIn.posZ - this.prevRenderSortZ;
/*      */       
/* 1302 */       if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
/*      */         
/* 1304 */         this.prevRenderSortX = entityIn.posX;
/* 1305 */         this.prevRenderSortY = entityIn.posY;
/* 1306 */         this.prevRenderSortZ = entityIn.posZ;
/* 1307 */         int k = 0;
/* 1308 */         Iterator<ContainerLocalRenderInformation> iterator = this.renderInfos.iterator();
/* 1309 */         this.chunksToResortTransparency.clear();
/*      */         
/* 1311 */         while (iterator.hasNext()) {
/*      */           
/* 1313 */           ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = iterator.next();
/*      */           
/* 1315 */           if (renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) && k++ < 15)
/*      */           {
/* 1317 */             this.chunksToResortTransparency.add(renderglobal$containerlocalrenderinformation.renderChunk);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1322 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1325 */     this.mc.mcProfiler.startSection("filterempty");
/* 1326 */     int l = 0;
/* 1327 */     boolean flag = (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT);
/* 1328 */     int i1 = flag ? (this.renderInfos.size() - 1) : 0;
/* 1329 */     int i = flag ? -1 : this.renderInfos.size();
/* 1330 */     int j1 = flag ? -1 : 1;
/*      */     
/* 1332 */     for (int j = i1; j != i; j += j1) {
/*      */       
/* 1334 */       RenderChunk renderchunk = ((ContainerLocalRenderInformation)this.renderInfos.get(j)).renderChunk;
/*      */       
/* 1336 */       if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) {
/*      */         
/* 1338 */         l++;
/* 1339 */         this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
/*      */       } 
/*      */     } 
/*      */     
/* 1343 */     if (l == 0)
/*      */     {
/* 1345 */       return l;
/*      */     }
/*      */ 
/*      */     
/* 1349 */     if (Config.isFogOff() && this.mc.entityRenderer.fogStandard)
/*      */     {
/* 1351 */       GlStateManager.disableFog();
/*      */     }
/*      */     
/* 1354 */     this.mc.mcProfiler.endStartSection("render_" + blockLayerIn);
/* 1355 */     renderBlockLayer(blockLayerIn);
/* 1356 */     this.mc.mcProfiler.endSection();
/* 1357 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderBlockLayer(EnumWorldBlockLayer blockLayerIn) {
/* 1364 */     this.mc.entityRenderer.enableLightmap();
/*      */     
/* 1366 */     if (OpenGlHelper.useVbo()) {
/*      */       
/* 1368 */       GL11.glEnableClientState(32884);
/* 1369 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1370 */       GL11.glEnableClientState(32888);
/* 1371 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1372 */       GL11.glEnableClientState(32888);
/* 1373 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1374 */       GL11.glEnableClientState(32886);
/*      */     } 
/*      */     
/* 1377 */     this.renderContainer.renderChunkLayer(blockLayerIn);
/*      */     
/* 1379 */     if (OpenGlHelper.useVbo())
/*      */     {
/* 1381 */       for (VertexFormatElement vertexformatelement : DefaultVertexFormats.BLOCK.getElements()) {
/*      */         
/* 1383 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
/* 1384 */         int i = vertexformatelement.getIndex();
/*      */         
/* 1386 */         switch (RenderGlobal$2.field_178037_a[vertexformatelement$enumusage.ordinal()]) {
/*      */           
/*      */           case 1:
/* 1389 */             GL11.glDisableClientState(32884);
/*      */ 
/*      */           
/*      */           case 2:
/* 1393 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 1394 */             GL11.glDisableClientState(32888);
/* 1395 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */ 
/*      */           
/*      */           case 3:
/* 1399 */             GL11.glDisableClientState(32886);
/* 1400 */             GlStateManager.resetColor();
/*      */         } 
/*      */       
/*      */       } 
/*      */     }
/* 1405 */     this.mc.entityRenderer.disableLightmap();
/*      */   }
/*      */ 
/*      */   
/*      */   private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iteratorIn) {
/* 1410 */     while (iteratorIn.hasNext()) {
/*      */       
/* 1412 */       DestroyBlockProgress destroyblockprogress = iteratorIn.next();
/* 1413 */       int i = destroyblockprogress.getCreationCloudUpdateTick();
/*      */       
/* 1415 */       if (this.cloudTickCounter - i > 400)
/*      */       {
/* 1417 */         iteratorIn.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateClouds() {
/* 1424 */     this.cloudTickCounter++;
/*      */     
/* 1426 */     if (this.cloudTickCounter % 20 == 0)
/*      */     {
/* 1428 */       cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSkyEnd() {
/* 1434 */     if (Config.isSkyEnabled()) {
/*      */       
/* 1436 */       GlStateManager.disableFog();
/* 1437 */       GlStateManager.disableAlpha();
/* 1438 */       GlStateManager.enableBlend();
/* 1439 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1440 */       RenderHelper.disableStandardItemLighting();
/* 1441 */       GlStateManager.depthMask(false);
/* 1442 */       this.renderEngine.bindTexture(locationEndSkyPng);
/* 1443 */       Tessellator tessellator = Tessellator.getInstance();
/* 1444 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*      */       
/* 1446 */       for (int i = 0; i < 6; i++) {
/*      */         
/* 1448 */         GlStateManager.pushMatrix();
/*      */         
/* 1450 */         if (i == 1)
/*      */         {
/* 1452 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1455 */         if (i == 2)
/*      */         {
/* 1457 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1460 */         if (i == 3)
/*      */         {
/* 1462 */           GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1465 */         if (i == 4)
/*      */         {
/* 1467 */           GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1470 */         if (i == 5)
/*      */         {
/* 1472 */           GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1475 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 1476 */         worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(40, 40, 40, 255).endVertex();
/* 1477 */         worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(40, 40, 40, 255).endVertex();
/* 1478 */         worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(40, 40, 40, 255).endVertex();
/* 1479 */         worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(40, 40, 40, 255).endVertex();
/* 1480 */         tessellator.draw();
/* 1481 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/* 1484 */       GlStateManager.depthMask(true);
/* 1485 */       GlStateManager.enableTexture2D();
/* 1486 */       GlStateManager.enableAlpha();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderSky(float partialTicks, int pass) {
/* 1492 */     if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
/*      */       
/* 1494 */       WorldProvider worldprovider = this.mc.theWorld.provider;
/* 1495 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);
/*      */       
/* 1497 */       if (object != null) {
/*      */         
/* 1499 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1504 */     if (this.mc.theWorld.provider.getDimensionId() == 1) {
/*      */       
/* 1506 */       renderSkyEnd();
/*      */     }
/* 1508 */     else if (this.mc.theWorld.provider.isSurfaceWorld()) {
/*      */       
/* 1510 */       GlStateManager.disableTexture2D();
/* 1511 */       Vec3 vec3 = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 1512 */       vec3 = CustomColorizer.getSkyColor(vec3, (IBlockAccess)this.mc.theWorld, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/* 1513 */       float f14 = (float)vec3.xCoord;
/* 1514 */       float f = (float)vec3.yCoord;
/* 1515 */       float f1 = (float)vec3.zCoord;
/*      */       
/* 1517 */       if (pass != 2) {
/*      */         
/* 1519 */         float f2 = (f14 * 30.0F + f * 59.0F + f1 * 11.0F) / 100.0F;
/* 1520 */         float f3 = (f14 * 30.0F + f * 70.0F) / 100.0F;
/* 1521 */         float f4 = (f14 * 30.0F + f1 * 70.0F) / 100.0F;
/* 1522 */         f14 = f2;
/* 1523 */         f = f3;
/* 1524 */         f1 = f4;
/*      */       } 
/*      */       
/* 1527 */       GlStateManager.color(f14, f, f1);
/* 1528 */       Tessellator tessellator = Tessellator.getInstance();
/* 1529 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1530 */       GlStateManager.depthMask(false);
/* 1531 */       GlStateManager.enableFog();
/* 1532 */       GlStateManager.color(f14, f, f1);
/*      */       
/* 1534 */       if (Config.isSkyEnabled())
/*      */       {
/* 1536 */         if (this.vboEnabled) {
/*      */           
/* 1538 */           this.skyVBO.bindBuffer();
/* 1539 */           GL11.glEnableClientState(32884);
/* 1540 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1541 */           this.skyVBO.drawArrays(7);
/* 1542 */           this.skyVBO.unbindBuffer();
/* 1543 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1547 */           GlStateManager.callList(this.glSkyList);
/*      */         } 
/*      */       }
/*      */       
/* 1551 */       GlStateManager.disableFog();
/* 1552 */       GlStateManager.disableAlpha();
/* 1553 */       GlStateManager.enableBlend();
/* 1554 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1555 */       RenderHelper.disableStandardItemLighting();
/* 1556 */       float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);
/*      */       
/* 1558 */       if (afloat != null && Config.isSunMoonEnabled()) {
/*      */         
/* 1560 */         GlStateManager.disableTexture2D();
/* 1561 */         GlStateManager.shadeModel(7425);
/* 1562 */         GlStateManager.pushMatrix();
/* 1563 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 1564 */         GlStateManager.rotate((MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F) ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
/* 1565 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 1566 */         float f5 = afloat[0];
/* 1567 */         float f6 = afloat[1];
/* 1568 */         float f7 = afloat[2];
/*      */         
/* 1570 */         if (pass != 2) {
/*      */           
/* 1572 */           float f8 = (f5 * 30.0F + f6 * 59.0F + f7 * 11.0F) / 100.0F;
/* 1573 */           float f9 = (f5 * 30.0F + f6 * 70.0F) / 100.0F;
/* 1574 */           float f10 = (f5 * 30.0F + f7 * 70.0F) / 100.0F;
/* 1575 */           f5 = f8;
/* 1576 */           f6 = f9;
/* 1577 */           f7 = f10;
/*      */         } 
/*      */         
/* 1580 */         worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 1581 */         worldrenderer.pos(0.0D, 100.0D, 0.0D).color(f5, f6, f7, afloat[3]).endVertex();
/* 1582 */         boolean flag = true;
/*      */         
/* 1584 */         for (int i = 0; i <= 16; i++) {
/*      */           
/* 1586 */           float f20 = i * 3.1415927F * 2.0F / 16.0F;
/* 1587 */           float f11 = MathHelper.sin(f20);
/* 1588 */           float f12 = MathHelper.cos(f20);
/* 1589 */           worldrenderer.pos((f11 * 120.0F), (f12 * 120.0F), (-f12 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
/*      */         } 
/*      */         
/* 1592 */         tessellator.draw();
/* 1593 */         GlStateManager.popMatrix();
/* 1594 */         GlStateManager.shadeModel(7424);
/*      */       } 
/*      */       
/* 1597 */       GlStateManager.enableTexture2D();
/* 1598 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 1599 */       GlStateManager.pushMatrix();
/* 1600 */       float f15 = 1.0F - this.theWorld.getRainStrength(partialTicks);
/* 1601 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f15);
/* 1602 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 1603 */       CustomSky.renderSky((World)this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(partialTicks), f15);
/* 1604 */       GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
/*      */       
/* 1606 */       if (Config.isSunMoonEnabled()) {
/*      */         
/* 1608 */         float f16 = 30.0F;
/* 1609 */         this.renderEngine.bindTexture(locationSunPng);
/* 1610 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1611 */         worldrenderer.pos(-f16, 100.0D, -f16).tex(0.0D, 0.0D).endVertex();
/* 1612 */         worldrenderer.pos(f16, 100.0D, -f16).tex(1.0D, 0.0D).endVertex();
/* 1613 */         worldrenderer.pos(f16, 100.0D, f16).tex(1.0D, 1.0D).endVertex();
/* 1614 */         worldrenderer.pos(-f16, 100.0D, f16).tex(0.0D, 1.0D).endVertex();
/* 1615 */         tessellator.draw();
/* 1616 */         f16 = 20.0F;
/* 1617 */         this.renderEngine.bindTexture(locationMoonPhasesPng);
/* 1618 */         int l = this.theWorld.getMoonPhase();
/* 1619 */         int j = l % 4;
/* 1620 */         int k = l / 4 % 2;
/* 1621 */         float f21 = (j + 0) / 4.0F;
/* 1622 */         float f22 = (k + 0) / 2.0F;
/* 1623 */         float f23 = (j + 1) / 4.0F;
/* 1624 */         float f13 = (k + 1) / 2.0F;
/* 1625 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1626 */         worldrenderer.pos(-f16, -100.0D, f16).tex(f23, f13).endVertex();
/* 1627 */         worldrenderer.pos(f16, -100.0D, f16).tex(f21, f13).endVertex();
/* 1628 */         worldrenderer.pos(f16, -100.0D, -f16).tex(f21, f22).endVertex();
/* 1629 */         worldrenderer.pos(-f16, -100.0D, -f16).tex(f23, f22).endVertex();
/* 1630 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1633 */       GlStateManager.disableTexture2D();
/* 1634 */       float f24 = this.theWorld.getStarBrightness(partialTicks) * f15;
/*      */       
/* 1636 */       if (f24 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers((World)this.theWorld)) {
/*      */         
/* 1638 */         GlStateManager.color(f24, f24, f24, f24);
/*      */         
/* 1640 */         if (this.vboEnabled) {
/*      */           
/* 1642 */           this.starVBO.bindBuffer();
/* 1643 */           GL11.glEnableClientState(32884);
/* 1644 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1645 */           this.starVBO.drawArrays(7);
/* 1646 */           this.starVBO.unbindBuffer();
/* 1647 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1651 */           GlStateManager.callList(this.starGLCallList);
/*      */         } 
/*      */       } 
/*      */       
/* 1655 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1656 */       GlStateManager.disableBlend();
/* 1657 */       GlStateManager.enableAlpha();
/* 1658 */       GlStateManager.enableFog();
/* 1659 */       GlStateManager.popMatrix();
/* 1660 */       GlStateManager.disableTexture2D();
/* 1661 */       GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 1662 */       double d0 = (this.mc.thePlayer.getPositionEyes(partialTicks)).yCoord - this.theWorld.getHorizon();
/*      */       
/* 1664 */       if (d0 < 0.0D) {
/*      */         
/* 1666 */         GlStateManager.pushMatrix();
/* 1667 */         GlStateManager.translate(0.0F, 12.0F, 0.0F);
/*      */         
/* 1669 */         if (this.vboEnabled) {
/*      */           
/* 1671 */           this.sky2VBO.bindBuffer();
/* 1672 */           GL11.glEnableClientState(32884);
/* 1673 */           GL11.glVertexPointer(3, 5126, 12, 0L);
/* 1674 */           this.sky2VBO.drawArrays(7);
/* 1675 */           this.sky2VBO.unbindBuffer();
/* 1676 */           GL11.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1680 */           GlStateManager.callList(this.glSkyList2);
/*      */         } 
/*      */         
/* 1683 */         GlStateManager.popMatrix();
/* 1684 */         float f17 = 1.0F;
/* 1685 */         float f18 = -((float)(d0 + 65.0D));
/* 1686 */         float f19 = -1.0F;
/* 1687 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1688 */         worldrenderer.pos(-1.0D, f18, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1689 */         worldrenderer.pos(1.0D, f18, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1690 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1691 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1692 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1693 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1694 */         worldrenderer.pos(1.0D, f18, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1695 */         worldrenderer.pos(-1.0D, f18, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1696 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1697 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1698 */         worldrenderer.pos(1.0D, f18, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1699 */         worldrenderer.pos(1.0D, f18, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1700 */         worldrenderer.pos(-1.0D, f18, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1701 */         worldrenderer.pos(-1.0D, f18, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1702 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1703 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1704 */         worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1705 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1706 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 1707 */         worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 1708 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1711 */       if (this.theWorld.provider.isSkyColored()) {
/*      */         
/* 1713 */         GlStateManager.color(f14 * 0.2F + 0.04F, f * 0.2F + 0.04F, f1 * 0.6F + 0.1F);
/*      */       }
/*      */       else {
/*      */         
/* 1717 */         GlStateManager.color(f14, f, f1);
/*      */       } 
/*      */       
/* 1720 */       if (this.mc.gameSettings.renderDistanceChunks <= 4)
/*      */       {
/* 1722 */         GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
/*      */       }
/*      */       
/* 1725 */       GlStateManager.pushMatrix();
/* 1726 */       GlStateManager.translate(0.0F, -((float)(d0 - 16.0D)), 0.0F);
/*      */       
/* 1728 */       if (Config.isSkyEnabled())
/*      */       {
/* 1730 */         GlStateManager.callList(this.glSkyList2);
/*      */       }
/*      */       
/* 1733 */       GlStateManager.popMatrix();
/* 1734 */       GlStateManager.enableTexture2D();
/* 1735 */       GlStateManager.depthMask(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderClouds(float partialTicks, int pass) {
/* 1741 */     if (!Config.isCloudsOff()) {
/*      */       
/* 1743 */       if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
/*      */         
/* 1745 */         WorldProvider worldprovider = this.mc.theWorld.provider;
/* 1746 */         Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
/*      */         
/* 1748 */         if (object != null) {
/*      */           
/* 1750 */           Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 1755 */       if (this.mc.theWorld.provider.isSurfaceWorld())
/*      */       {
/* 1757 */         if (Config.isCloudsFancy()) {
/*      */           
/* 1759 */           renderCloudsFancy(partialTicks, pass);
/*      */         }
/*      */         else {
/*      */           
/* 1763 */           this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, partialTicks);
/* 1764 */           partialTicks = 0.0F;
/* 1765 */           GlStateManager.disableCull();
/* 1766 */           float f9 = (float)((this.mc.getRenderViewEntity()).lastTickPosY + ((this.mc.getRenderViewEntity()).posY - (this.mc.getRenderViewEntity()).lastTickPosY) * partialTicks);
/* 1767 */           boolean flag = true;
/* 1768 */           boolean flag1 = true;
/* 1769 */           Tessellator tessellator = Tessellator.getInstance();
/* 1770 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1771 */           this.renderEngine.bindTexture(locationCloudsPng);
/* 1772 */           GlStateManager.enableBlend();
/* 1773 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */           
/* 1775 */           if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */             
/* 1777 */             this.cloudRenderer.startUpdateGlList();
/* 1778 */             Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
/* 1779 */             float f = (float)vec3.xCoord;
/* 1780 */             float f1 = (float)vec3.yCoord;
/* 1781 */             float f2 = (float)vec3.zCoord;
/*      */             
/* 1783 */             if (pass != 2) {
/*      */               
/* 1785 */               float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 1786 */               float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 1787 */               float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 1788 */               f = f3;
/* 1789 */               f1 = f4;
/* 1790 */               f2 = f5;
/*      */             } 
/*      */             
/* 1793 */             float f10 = 4.8828125E-4F;
/* 1794 */             double d2 = (this.cloudTickCounter + partialTicks);
/* 1795 */             double d0 = (this.mc.getRenderViewEntity()).prevPosX + ((this.mc.getRenderViewEntity()).posX - (this.mc.getRenderViewEntity()).prevPosX) * partialTicks + d2 * 0.029999999329447746D;
/* 1796 */             double d1 = (this.mc.getRenderViewEntity()).prevPosZ + ((this.mc.getRenderViewEntity()).posZ - (this.mc.getRenderViewEntity()).prevPosZ) * partialTicks;
/* 1797 */             int i = MathHelper.floor_double(d0 / 2048.0D);
/* 1798 */             int j = MathHelper.floor_double(d1 / 2048.0D);
/* 1799 */             d0 -= (i * 2048);
/* 1800 */             d1 -= (j * 2048);
/* 1801 */             float f6 = this.theWorld.provider.getCloudHeight() - f9 + 0.33F;
/* 1802 */             f6 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 1803 */             float f7 = (float)(d0 * 4.8828125E-4D);
/* 1804 */             float f8 = (float)(d1 * 4.8828125E-4D);
/* 1805 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*      */             
/* 1807 */             for (int k = -256; k < 256; k += 32) {
/*      */               
/* 1809 */               for (int l = -256; l < 256; l += 32) {
/*      */                 
/* 1811 */                 worldrenderer.pos((k + 0), f6, (l + 32)).tex(((k + 0) * 4.8828125E-4F + f7), ((l + 32) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 1812 */                 worldrenderer.pos((k + 32), f6, (l + 32)).tex(((k + 32) * 4.8828125E-4F + f7), ((l + 32) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 1813 */                 worldrenderer.pos((k + 32), f6, (l + 0)).tex(((k + 32) * 4.8828125E-4F + f7), ((l + 0) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 1814 */                 worldrenderer.pos((k + 0), f6, (l + 0)).tex(((k + 0) * 4.8828125E-4F + f7), ((l + 0) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/*      */               } 
/*      */             } 
/*      */             
/* 1818 */             tessellator.draw();
/* 1819 */             this.cloudRenderer.endUpdateGlList();
/*      */           } 
/*      */           
/* 1822 */           this.cloudRenderer.renderGlList();
/* 1823 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1824 */           GlStateManager.disableBlend();
/* 1825 */           GlStateManager.enableCull();
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
/* 1836 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCloudsFancy(float partialTicks, int pass) {
/* 1841 */     this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks);
/* 1842 */     partialTicks = 0.0F;
/* 1843 */     GlStateManager.disableCull();
/* 1844 */     float f = (float)((this.mc.getRenderViewEntity()).lastTickPosY + ((this.mc.getRenderViewEntity()).posY - (this.mc.getRenderViewEntity()).lastTickPosY) * partialTicks);
/* 1845 */     Tessellator tessellator = Tessellator.getInstance();
/* 1846 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1847 */     float f1 = 12.0F;
/* 1848 */     float f2 = 4.0F;
/* 1849 */     double d0 = (this.cloudTickCounter + partialTicks);
/* 1850 */     double d1 = ((this.mc.getRenderViewEntity()).prevPosX + ((this.mc.getRenderViewEntity()).posX - (this.mc.getRenderViewEntity()).prevPosX) * partialTicks + d0 * 0.029999999329447746D) / 12.0D;
/* 1851 */     double d2 = ((this.mc.getRenderViewEntity()).prevPosZ + ((this.mc.getRenderViewEntity()).posZ - (this.mc.getRenderViewEntity()).prevPosZ) * partialTicks) / 12.0D + 0.33000001311302185D;
/* 1852 */     float f3 = this.theWorld.provider.getCloudHeight() - f + 0.33F;
/* 1853 */     f3 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 1854 */     int i = MathHelper.floor_double(d1 / 2048.0D);
/* 1855 */     int j = MathHelper.floor_double(d2 / 2048.0D);
/* 1856 */     d1 -= (i * 2048);
/* 1857 */     d2 -= (j * 2048);
/* 1858 */     this.renderEngine.bindTexture(locationCloudsPng);
/* 1859 */     GlStateManager.enableBlend();
/* 1860 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1861 */     Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
/* 1862 */     float f4 = (float)vec3.xCoord;
/* 1863 */     float f5 = (float)vec3.yCoord;
/* 1864 */     float f6 = (float)vec3.zCoord;
/*      */     
/* 1866 */     if (pass != 2) {
/*      */       
/* 1868 */       float f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
/* 1869 */       float f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
/* 1870 */       float f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
/* 1871 */       f4 = f7;
/* 1872 */       f5 = f8;
/* 1873 */       f6 = f9;
/*      */     } 
/*      */     
/* 1876 */     float f26 = f4 * 0.9F;
/* 1877 */     float f27 = f5 * 0.9F;
/* 1878 */     float f28 = f6 * 0.9F;
/* 1879 */     float f10 = f4 * 0.7F;
/* 1880 */     float f11 = f5 * 0.7F;
/* 1881 */     float f12 = f6 * 0.7F;
/* 1882 */     float f13 = f4 * 0.8F;
/* 1883 */     float f14 = f5 * 0.8F;
/* 1884 */     float f15 = f6 * 0.8F;
/* 1885 */     float f16 = 0.00390625F;
/* 1886 */     float f17 = MathHelper.floor_double(d1) * 0.00390625F;
/* 1887 */     float f18 = MathHelper.floor_double(d2) * 0.00390625F;
/* 1888 */     float f19 = (float)(d1 - MathHelper.floor_double(d1));
/* 1889 */     float f20 = (float)(d2 - MathHelper.floor_double(d2));
/* 1890 */     boolean flag = true;
/* 1891 */     boolean flag1 = true;
/* 1892 */     float f21 = 9.765625E-4F;
/* 1893 */     GlStateManager.scale(12.0F, 1.0F, 12.0F);
/*      */     
/* 1895 */     for (int k = 0; k < 2; k++) {
/*      */       
/* 1897 */       if (k == 0) {
/*      */         
/* 1899 */         GlStateManager.colorMask(false, false, false, false);
/*      */       }
/*      */       else {
/*      */         
/* 1903 */         switch (pass) {
/*      */           
/*      */           case 0:
/* 1906 */             GlStateManager.colorMask(false, true, true, true);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1910 */             GlStateManager.colorMask(true, false, false, true);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1914 */             GlStateManager.colorMask(true, true, true, true);
/*      */             break;
/*      */         } 
/*      */       } 
/* 1918 */       this.cloudRenderer.renderGlList();
/*      */     } 
/*      */     
/* 1921 */     if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */       
/* 1923 */       this.cloudRenderer.startUpdateGlList();
/*      */       
/* 1925 */       for (int j1 = -3; j1 <= 4; j1++) {
/*      */         
/* 1927 */         for (int l = -3; l <= 4; l++) {
/*      */           
/* 1929 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/* 1930 */           float f22 = (j1 * 8);
/* 1931 */           float f23 = (l * 8);
/* 1932 */           float f24 = f22 - f19;
/* 1933 */           float f25 = f23 - f20;
/*      */           
/* 1935 */           if (f3 > -5.0F) {
/*      */             
/* 1937 */             worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 1938 */             worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 1939 */             worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 1940 */             worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 1943 */           if (f3 <= 5.0F) {
/*      */             
/* 1945 */             worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 8.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1946 */             worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 8.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1947 */             worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 1948 */             worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F - 9.765625E-4F), (f25 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 1951 */           if (j1 > -1)
/*      */           {
/* 1953 */             for (int i1 = 0; i1 < 8; i1++) {
/*      */               
/* 1955 */               worldrenderer.pos((f24 + i1 + 0.0F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + i1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 1956 */               worldrenderer.pos((f24 + i1 + 0.0F), (f3 + 4.0F), (f25 + 8.0F)).tex(((f22 + i1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 1957 */               worldrenderer.pos((f24 + i1 + 0.0F), (f3 + 4.0F), (f25 + 0.0F)).tex(((f22 + i1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 1958 */               worldrenderer.pos((f24 + i1 + 0.0F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + i1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 1962 */           if (j1 <= 1)
/*      */           {
/* 1964 */             for (int k1 = 0; k1 < 8; k1++) {
/*      */               
/* 1966 */               worldrenderer.pos((f24 + k1 + 1.0F - 9.765625E-4F), (f3 + 0.0F), (f25 + 8.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 1967 */               worldrenderer.pos((f24 + k1 + 1.0F - 9.765625E-4F), (f3 + 4.0F), (f25 + 8.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 1968 */               worldrenderer.pos((f24 + k1 + 1.0F - 9.765625E-4F), (f3 + 4.0F), (f25 + 0.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 1969 */               worldrenderer.pos((f24 + k1 + 1.0F - 9.765625E-4F), (f3 + 0.0F), (f25 + 0.0F)).tex(((f22 + k1 + 0.5F) * 0.00390625F + f17), ((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 1973 */           if (l > -1)
/*      */           {
/* 1975 */             for (int l1 = 0; l1 < 8; l1++) {
/*      */               
/* 1977 */               worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F), (f25 + l1 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + l1 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 1978 */               worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F), (f25 + l1 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + l1 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 1979 */               worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + l1 + 0.0F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + l1 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 1980 */               worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + l1 + 0.0F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + l1 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 1984 */           if (l <= 1)
/*      */           {
/* 1986 */             for (int i2 = 0; i2 < 8; i2++) {
/*      */               
/* 1988 */               worldrenderer.pos((f24 + 0.0F), (f3 + 4.0F), (f25 + i2 + 1.0F - 9.765625E-4F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + i2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 1989 */               worldrenderer.pos((f24 + 8.0F), (f3 + 4.0F), (f25 + i2 + 1.0F - 9.765625E-4F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + i2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 1990 */               worldrenderer.pos((f24 + 8.0F), (f3 + 0.0F), (f25 + i2 + 1.0F - 9.765625E-4F)).tex(((f22 + 8.0F) * 0.00390625F + f17), ((f23 + i2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 1991 */               worldrenderer.pos((f24 + 0.0F), (f3 + 0.0F), (f25 + i2 + 1.0F - 9.765625E-4F)).tex(((f22 + 0.0F) * 0.00390625F + f17), ((f23 + i2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 1995 */           tessellator.draw();
/*      */         } 
/*      */       } 
/*      */       
/* 1999 */       this.cloudRenderer.endUpdateGlList();
/*      */     } 
/*      */     
/* 2002 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2003 */     GlStateManager.disableBlend();
/* 2004 */     GlStateManager.enableCull();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateChunks(long finishTimeNano) {
/* 2009 */     this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(finishTimeNano);
/*      */     
/* 2011 */     if (this.chunksToUpdateForced.size() > 0) {
/*      */       
/* 2013 */       Iterator<RenderChunk> iterator = this.chunksToUpdateForced.iterator();
/*      */       
/* 2015 */       while (iterator.hasNext()) {
/*      */         
/* 2017 */         RenderChunk renderchunk = iterator.next();
/*      */         
/* 2019 */         if (!this.renderDispatcher.updateChunkLater(renderchunk)) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2024 */         renderchunk.setNeedsUpdate(false);
/* 2025 */         iterator.remove();
/* 2026 */         this.chunksToUpdate.remove(renderchunk);
/* 2027 */         this.chunksToResortTransparency.remove(renderchunk);
/*      */       } 
/*      */     } 
/*      */     
/* 2031 */     if (this.chunksToResortTransparency.size() > 0) {
/*      */       
/* 2033 */       Iterator<RenderChunk> iterator2 = this.chunksToResortTransparency.iterator();
/*      */       
/* 2035 */       if (iterator2.hasNext()) {
/*      */         
/* 2037 */         RenderChunk renderchunk2 = iterator2.next();
/*      */         
/* 2039 */         if (this.renderDispatcher.updateTransparencyLater(renderchunk2))
/*      */         {
/* 2041 */           iterator2.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2046 */     int j = 0;
/* 2047 */     int k = Config.getUpdatesPerFrame();
/* 2048 */     int i = k * 2;
/* 2049 */     Iterator<RenderChunk> iterator1 = this.chunksToUpdate.iterator();
/*      */     
/* 2051 */     while (iterator1.hasNext()) {
/*      */       
/* 2053 */       RenderChunk renderchunk1 = iterator1.next();
/*      */       
/* 2055 */       if (!this.renderDispatcher.updateChunkLater(renderchunk1)) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/* 2060 */       renderchunk1.setNeedsUpdate(false);
/* 2061 */       iterator1.remove();
/*      */       
/* 2063 */       if (renderchunk1.getCompiledChunk().isEmpty() && k < i)
/*      */       {
/* 2065 */         k++;
/*      */       }
/*      */       
/* 2068 */       j++;
/*      */       
/* 2070 */       if (j >= k) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderWorldBorder(Entity p_180449_1_, float partialTicks) {
/* 2079 */     Tessellator tessellator = Tessellator.getInstance();
/* 2080 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2081 */     WorldBorder worldborder = this.theWorld.getWorldBorder();
/* 2082 */     double d0 = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/* 2084 */     if (p_180449_1_.posX >= worldborder.maxX() - d0 || p_180449_1_.posX <= worldborder.minX() + d0 || p_180449_1_.posZ >= worldborder.maxZ() - d0 || p_180449_1_.posZ <= worldborder.minZ() + d0) {
/*      */       
/* 2086 */       double d1 = 1.0D - worldborder.getClosestDistance(p_180449_1_) / d0;
/* 2087 */       d1 = Math.pow(d1, 4.0D);
/* 2088 */       double d2 = p_180449_1_.lastTickPosX + (p_180449_1_.posX - p_180449_1_.lastTickPosX) * partialTicks;
/* 2089 */       double d3 = p_180449_1_.lastTickPosY + (p_180449_1_.posY - p_180449_1_.lastTickPosY) * partialTicks;
/* 2090 */       double d4 = p_180449_1_.lastTickPosZ + (p_180449_1_.posZ - p_180449_1_.lastTickPosZ) * partialTicks;
/* 2091 */       GlStateManager.enableBlend();
/* 2092 */       GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/* 2093 */       this.renderEngine.bindTexture(locationForcefieldPng);
/* 2094 */       GlStateManager.depthMask(false);
/* 2095 */       GlStateManager.pushMatrix();
/* 2096 */       int i = worldborder.getStatus().getID();
/* 2097 */       float f = (i >> 16 & 0xFF) / 255.0F;
/* 2098 */       float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 2099 */       float f2 = (i & 0xFF) / 255.0F;
/* 2100 */       GlStateManager.color(f, f1, f2, (float)d1);
/* 2101 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 2102 */       GlStateManager.enablePolygonOffset();
/* 2103 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2104 */       GlStateManager.enableAlpha();
/* 2105 */       GlStateManager.disableCull();
/* 2106 */       float f3 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F;
/* 2107 */       float f4 = 0.0F;
/* 2108 */       float f5 = 0.0F;
/* 2109 */       float f6 = 128.0F;
/* 2110 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 2111 */       worldrenderer.setTranslation(-d2, -d3, -d4);
/* 2112 */       double d5 = Math.max(MathHelper.floor_double(d4 - d0), worldborder.minZ());
/* 2113 */       double d6 = Math.min(MathHelper.ceiling_double_int(d4 + d0), worldborder.maxZ());
/*      */       
/* 2115 */       if (d2 > worldborder.maxX() - d0) {
/*      */         
/* 2117 */         float f7 = 0.0F;
/*      */         
/* 2119 */         for (double d7 = d5; d7 < d6; f7 += 0.5F) {
/*      */           
/* 2121 */           double d8 = Math.min(1.0D, d6 - d7);
/* 2122 */           float f8 = (float)d8 * 0.5F;
/* 2123 */           worldrenderer.pos(worldborder.maxX(), 256.0D, d7).tex((f3 + f7), (f3 + 0.0F)).endVertex();
/* 2124 */           worldrenderer.pos(worldborder.maxX(), 256.0D, d7 + d8).tex((f3 + f8 + f7), (f3 + 0.0F)).endVertex();
/* 2125 */           worldrenderer.pos(worldborder.maxX(), 0.0D, d7 + d8).tex((f3 + f8 + f7), (f3 + 128.0F)).endVertex();
/* 2126 */           worldrenderer.pos(worldborder.maxX(), 0.0D, d7).tex((f3 + f7), (f3 + 128.0F)).endVertex();
/* 2127 */           d7++;
/*      */         } 
/*      */       } 
/*      */       
/* 2131 */       if (d2 < worldborder.minX() + d0) {
/*      */         
/* 2133 */         float f9 = 0.0F;
/*      */         
/* 2135 */         for (double d9 = d5; d9 < d6; f9 += 0.5F) {
/*      */           
/* 2137 */           double d12 = Math.min(1.0D, d6 - d9);
/* 2138 */           float f12 = (float)d12 * 0.5F;
/* 2139 */           worldrenderer.pos(worldborder.minX(), 256.0D, d9).tex((f3 + f9), (f3 + 0.0F)).endVertex();
/* 2140 */           worldrenderer.pos(worldborder.minX(), 256.0D, d9 + d12).tex((f3 + f12 + f9), (f3 + 0.0F)).endVertex();
/* 2141 */           worldrenderer.pos(worldborder.minX(), 0.0D, d9 + d12).tex((f3 + f12 + f9), (f3 + 128.0F)).endVertex();
/* 2142 */           worldrenderer.pos(worldborder.minX(), 0.0D, d9).tex((f3 + f9), (f3 + 128.0F)).endVertex();
/* 2143 */           d9++;
/*      */         } 
/*      */       } 
/*      */       
/* 2147 */       d5 = Math.max(MathHelper.floor_double(d2 - d0), worldborder.minX());
/* 2148 */       d6 = Math.min(MathHelper.ceiling_double_int(d2 + d0), worldborder.maxX());
/*      */       
/* 2150 */       if (d4 > worldborder.maxZ() - d0) {
/*      */         
/* 2152 */         float f10 = 0.0F;
/*      */         
/* 2154 */         for (double d10 = d5; d10 < d6; f10 += 0.5F) {
/*      */           
/* 2156 */           double d13 = Math.min(1.0D, d6 - d10);
/* 2157 */           float f13 = (float)d13 * 0.5F;
/* 2158 */           worldrenderer.pos(d10, 256.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 0.0F)).endVertex();
/* 2159 */           worldrenderer.pos(d10 + d13, 256.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 0.0F)).endVertex();
/* 2160 */           worldrenderer.pos(d10 + d13, 0.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 128.0F)).endVertex();
/* 2161 */           worldrenderer.pos(d10, 0.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 128.0F)).endVertex();
/* 2162 */           d10++;
/*      */         } 
/*      */       } 
/*      */       
/* 2166 */       if (d4 < worldborder.minZ() + d0) {
/*      */         
/* 2168 */         float f11 = 0.0F;
/*      */         
/* 2170 */         for (double d11 = d5; d11 < d6; f11 += 0.5F) {
/*      */           
/* 2172 */           double d14 = Math.min(1.0D, d6 - d11);
/* 2173 */           float f14 = (float)d14 * 0.5F;
/* 2174 */           worldrenderer.pos(d11, 256.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 0.0F)).endVertex();
/* 2175 */           worldrenderer.pos(d11 + d14, 256.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 0.0F)).endVertex();
/* 2176 */           worldrenderer.pos(d11 + d14, 0.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 128.0F)).endVertex();
/* 2177 */           worldrenderer.pos(d11, 0.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 128.0F)).endVertex();
/* 2178 */           d11++;
/*      */         } 
/*      */       } 
/*      */       
/* 2182 */       tessellator.draw();
/* 2183 */       worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 2184 */       GlStateManager.enableCull();
/* 2185 */       GlStateManager.disableAlpha();
/* 2186 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2187 */       GlStateManager.disablePolygonOffset();
/* 2188 */       GlStateManager.enableAlpha();
/* 2189 */       GlStateManager.disableBlend();
/* 2190 */       GlStateManager.popMatrix();
/* 2191 */       GlStateManager.depthMask(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void preRenderDamagedBlocks() {
/* 2197 */     GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
/* 2198 */     GlStateManager.enableBlend();
/* 2199 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
/* 2200 */     GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 2201 */     GlStateManager.enablePolygonOffset();
/* 2202 */     GlStateManager.alphaFunc(516, 0.1F);
/* 2203 */     GlStateManager.enableAlpha();
/* 2204 */     GlStateManager.pushMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   private void postRenderDamagedBlocks() {
/* 2209 */     GlStateManager.disableAlpha();
/* 2210 */     GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2211 */     GlStateManager.disablePolygonOffset();
/* 2212 */     GlStateManager.enableAlpha();
/* 2213 */     GlStateManager.depthMask(true);
/* 2214 */     GlStateManager.popMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawBlockDamageTexture(Tessellator tessellatorIn, WorldRenderer worldRendererIn, Entity entityIn, float partialTicks) {
/* 2219 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 2220 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 2221 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/*      */     
/* 2223 */     if (!this.damagedBlocks.isEmpty()) {
/*      */       
/* 2225 */       this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 2226 */       preRenderDamagedBlocks();
/* 2227 */       worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/* 2228 */       worldRendererIn.setTranslation(-d0, -d1, -d2);
/* 2229 */       worldRendererIn.markDirty();
/* 2230 */       Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
/*      */       
/* 2232 */       while (iterator.hasNext()) {
/*      */         boolean flag;
/* 2234 */         DestroyBlockProgress destroyblockprogress = iterator.next();
/* 2235 */         BlockPos blockpos = destroyblockprogress.getPosition();
/* 2236 */         double d3 = blockpos.getX() - d0;
/* 2237 */         double d4 = blockpos.getY() - d1;
/* 2238 */         double d5 = blockpos.getZ() - d2;
/* 2239 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */ 
/*      */         
/* 2242 */         if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
/*      */           
/* 2244 */           boolean flag1 = !(!(block instanceof net.minecraft.block.BlockChest) && !(block instanceof net.minecraft.block.BlockEnderChest) && !(block instanceof net.minecraft.block.BlockSign) && !(block instanceof net.minecraft.block.BlockSkull));
/*      */           
/* 2246 */           if (!flag1) {
/*      */             
/* 2248 */             TileEntity tileentity = this.theWorld.getTileEntity(blockpos);
/*      */             
/* 2250 */             if (tileentity != null)
/*      */             {
/* 2252 */               flag1 = Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
/*      */             }
/*      */           } 
/*      */           
/* 2256 */           flag = !flag1;
/*      */         }
/*      */         else {
/*      */           
/* 2260 */           flag = (!(block instanceof net.minecraft.block.BlockChest) && !(block instanceof net.minecraft.block.BlockEnderChest) && !(block instanceof net.minecraft.block.BlockSign) && !(block instanceof net.minecraft.block.BlockSkull));
/*      */         } 
/*      */         
/* 2263 */         if (flag) {
/*      */           
/* 2265 */           if (d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D) {
/*      */             
/* 2267 */             iterator.remove();
/*      */             
/*      */             continue;
/*      */           } 
/* 2271 */           IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
/*      */           
/* 2273 */           if (iblockstate.getBlock().getMaterial() != Material.air) {
/*      */             
/* 2275 */             int i = destroyblockprogress.getPartialBlockDamage();
/* 2276 */             TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[i];
/* 2277 */             BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/* 2278 */             blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite, (IBlockAccess)this.theWorld);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2284 */       tessellatorIn.draw();
/* 2285 */       worldRendererIn.setTranslation(0.0D, 0.0D, 0.0D);
/* 2286 */       postRenderDamagedBlocks();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int p_72731_3_, float partialTicks) {
/* 2295 */     if (p_72731_3_ == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*      */       
/* 2297 */       GlStateManager.enableBlend();
/* 2298 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 2299 */       GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
/* 2300 */       GL11.glLineWidth(2.0F);
/* 2301 */       GlStateManager.disableTexture2D();
/* 2302 */       GlStateManager.depthMask(false);
/* 2303 */       float f = 0.002F;
/* 2304 */       BlockPos blockpos = movingObjectPositionIn.getBlockPos();
/* 2305 */       Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */       
/* 2307 */       if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockpos)) {
/*      */         
/* 2309 */         block.setBlockBoundsBasedOnState((IBlockAccess)this.theWorld, blockpos);
/* 2310 */         double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
/* 2311 */         double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
/* 2312 */         double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
/* 2313 */         func_181561_a(block.getSelectedBoundingBox((World)this.theWorld, blockpos).expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2));
/*      */       } 
/*      */       
/* 2316 */       GlStateManager.depthMask(true);
/* 2317 */       GlStateManager.enableTexture2D();
/* 2318 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_181561_a(AxisAlignedBB p_181561_0_) {
/* 2324 */     Tessellator tessellator = Tessellator.getInstance();
/* 2325 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2326 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION);
/* 2327 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2328 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2329 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2330 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2331 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2332 */     tessellator.draw();
/* 2333 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION);
/* 2334 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2335 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2336 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2337 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2338 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2339 */     tessellator.draw();
/* 2340 */     worldrenderer.begin(1, DefaultVertexFormats.POSITION);
/* 2341 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2342 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2343 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 2344 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 2345 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2346 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2347 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 2348 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 2349 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_181563_a(AxisAlignedBB p_181563_0_, int p_181563_1_, int p_181563_2_, int p_181563_3_, int p_181563_4_) {
/* 2354 */     Tessellator tessellator = Tessellator.getInstance();
/* 2355 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 2356 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 2357 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2358 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2359 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2360 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2361 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2362 */     tessellator.draw();
/* 2363 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 2364 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2365 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2366 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2367 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2368 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2369 */     tessellator.draw();
/* 2370 */     worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
/* 2371 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2372 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2373 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2374 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.minZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2375 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2376 */     worldrenderer.pos(p_181563_0_.maxX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2377 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.minY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2378 */     worldrenderer.pos(p_181563_0_.minX, p_181563_0_.maxY, p_181563_0_.maxZ).color(p_181563_1_, p_181563_2_, p_181563_3_, p_181563_4_).endVertex();
/* 2379 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void markBlocksForUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 2387 */     this.viewFrustum.markBlocksForUpdate(x1, y1, z1, x2, y2, z2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markBlockForUpdate(BlockPos pos) {
/* 2392 */     int i = pos.getX();
/* 2393 */     int j = pos.getY();
/* 2394 */     int k = pos.getZ();
/* 2395 */     markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/* 2400 */     int i = pos.getX();
/* 2401 */     int j = pos.getY();
/* 2402 */     int k = pos.getZ();
/* 2403 */     markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 2412 */     markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playRecord(String recordName, BlockPos blockPosIn) {
/* 2417 */     ISound isound = (ISound)this.mapSoundPositions.get(blockPosIn);
/*      */     
/* 2419 */     if (isound != null) {
/*      */       
/* 2421 */       this.mc.getSoundHandler().stopSound(isound);
/* 2422 */       this.mapSoundPositions.remove(blockPosIn);
/*      */     } 
/*      */     
/* 2425 */     if (recordName != null) {
/*      */       
/* 2427 */       ItemRecord itemrecord = ItemRecord.getRecord(recordName);
/*      */       
/* 2429 */       if (itemrecord != null)
/*      */       {
/* 2431 */         this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
/*      */       }
/*      */       
/* 2434 */       ResourceLocation resourcelocation = null;
/*      */       
/* 2436 */       if (Reflector.ForgeItemRecord_getRecordResource.exists() && itemrecord != null)
/*      */       {
/* 2438 */         resourcelocation = (ResourceLocation)Reflector.call(itemrecord, Reflector.ForgeItemRecord_getRecordResource, new Object[] { recordName });
/*      */       }
/*      */       
/* 2441 */       if (resourcelocation == null)
/*      */       {
/* 2443 */         resourcelocation = new ResourceLocation(recordName);
/*      */       }
/*      */       
/* 2446 */       PositionedSoundRecord positionedsoundrecord = PositionedSoundRecord.create(resourcelocation, blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
/* 2447 */       this.mapSoundPositions.put(blockPosIn, positionedsoundrecord);
/* 2448 */       this.mc.getSoundHandler().playSound((ISound)positionedsoundrecord);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(String soundName, double x, double y, double z, float volume, float pitch) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(int particleID, boolean ignoreRange, final double xCoord, final double yCoord, final double zCoord, double xOffset, double yOffset, double zOffset, int... p_180442_15_) {
/*      */     try {
/* 2470 */       spawnEntityFX(particleID, ignoreRange, xCoord, yCoord, zCoord, xOffset, yOffset, zOffset, p_180442_15_);
/*      */     }
/* 2472 */     catch (Throwable throwable) {
/*      */       
/* 2474 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
/* 2475 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
/* 2476 */       crashreportcategory.addCrashSection("ID", Integer.valueOf(particleID));
/*      */       
/* 2478 */       if (p_180442_15_ != null)
/*      */       {
/* 2480 */         crashreportcategory.addCrashSection("Parameters", p_180442_15_);
/*      */       }
/*      */       
/* 2483 */       crashreportcategory.addCrashSectionCallable("Position", new Callable()
/*      */           {
/*      */             private static final String __OBFID = "CL_00000955";
/*      */             
/*      */             public String call() throws Exception {
/* 2488 */               return CrashReportCategory.getCoordinateInfo(xCoord, yCoord, zCoord);
/*      */             }
/*      */           });
/* 2491 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticle(EnumParticleTypes particleIn, double p_174972_2_, double p_174972_4_, double p_174972_6_, double p_174972_8_, double p_174972_10_, double p_174972_12_, int... p_174972_14_) {
/* 2497 */     spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), p_174972_2_, p_174972_4_, p_174972_6_, p_174972_8_, p_174972_10_, p_174972_12_, p_174972_14_);
/*      */   }
/*      */ 
/*      */   
/*      */   private EntityFX spawnEntityFX(int p_174974_1_, boolean ignoreRange, double p_174974_3_, double p_174974_5_, double p_174974_7_, double p_174974_9_, double p_174974_11_, double p_174974_13_, int... p_174974_15_) {
/* 2502 */     if (this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
/*      */       
/* 2504 */       int i = this.mc.gameSettings.particleSetting;
/*      */       
/* 2506 */       if (i == 1 && this.theWorld.rand.nextInt(3) == 0)
/*      */       {
/* 2508 */         i = 2;
/*      */       }
/*      */       
/* 2511 */       double d0 = (this.mc.getRenderViewEntity()).posX - p_174974_3_;
/* 2512 */       double d1 = (this.mc.getRenderViewEntity()).posY - p_174974_5_;
/* 2513 */       double d2 = (this.mc.getRenderViewEntity()).posZ - p_174974_7_;
/*      */       
/* 2515 */       if (p_174974_1_ == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2517 */         return null;
/*      */       }
/* 2519 */       if (p_174974_1_ == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2521 */         return null;
/*      */       }
/* 2523 */       if (p_174974_1_ == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2525 */         return null;
/*      */       }
/* 2527 */       if (p_174974_1_ == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles())
/*      */       {
/* 2529 */         return null;
/*      */       }
/* 2531 */       if (p_174974_1_ == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles())
/*      */       {
/* 2533 */         return null;
/*      */       }
/* 2535 */       if (p_174974_1_ == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2537 */         return null;
/*      */       }
/* 2539 */       if (p_174974_1_ == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2541 */         return null;
/*      */       }
/* 2543 */       if (p_174974_1_ == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2545 */         return null;
/*      */       }
/* 2547 */       if (p_174974_1_ == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2549 */         return null;
/*      */       }
/* 2551 */       if (p_174974_1_ == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2553 */         return null;
/*      */       }
/* 2555 */       if (p_174974_1_ == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2557 */         return null;
/*      */       }
/* 2559 */       if (p_174974_1_ == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2561 */         return null;
/*      */       }
/* 2563 */       if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID() && !Config.isAnimatedPortal())
/*      */       {
/* 2565 */         return null;
/*      */       }
/* 2567 */       if (p_174974_1_ == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame())
/*      */       {
/* 2569 */         return null;
/*      */       }
/* 2571 */       if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone())
/*      */       {
/* 2573 */         return null;
/*      */       }
/* 2575 */       if (p_174974_1_ == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2577 */         return null;
/*      */       }
/* 2579 */       if (p_174974_1_ == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2581 */         return null;
/*      */       }
/* 2583 */       if (p_174974_1_ == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles())
/*      */       {
/* 2585 */         return null;
/*      */       }
/* 2587 */       if (ignoreRange)
/*      */       {
/* 2589 */         return this.mc.effectRenderer.spawnEffectParticle(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
/*      */       }
/*      */ 
/*      */       
/* 2593 */       double d3 = 16.0D;
/* 2594 */       double d4 = 256.0D;
/*      */       
/* 2596 */       if (p_174974_1_ == EnumParticleTypes.CRIT.getParticleID())
/*      */       {
/* 2598 */         d4 = 38416.0D;
/*      */       }
/*      */       
/* 2601 */       if (d0 * d0 + d1 * d1 + d2 * d2 > d4)
/*      */       {
/* 2603 */         return null;
/*      */       }
/* 2605 */       if (i > 1)
/*      */       {
/* 2607 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 2611 */       EntityFX entityfx = this.mc.effectRenderer.spawnEffectParticle(p_174974_1_, p_174974_3_, p_174974_5_, p_174974_7_, p_174974_9_, p_174974_11_, p_174974_13_, p_174974_15_);
/*      */       
/* 2613 */       if (p_174974_1_ == EnumParticleTypes.WATER_BUBBLE.getParticleID())
/*      */       {
/* 2615 */         CustomColorizer.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
/*      */       }
/*      */       
/* 2618 */       if (p_174974_1_ == EnumParticleTypes.WATER_SPLASH.getParticleID())
/*      */       {
/* 2620 */         CustomColorizer.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
/*      */       }
/*      */       
/* 2623 */       if (p_174974_1_ == EnumParticleTypes.WATER_DROP.getParticleID())
/*      */       {
/* 2625 */         CustomColorizer.updateWaterFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
/*      */       }
/*      */       
/* 2628 */       if (p_174974_1_ == EnumParticleTypes.TOWN_AURA.getParticleID())
/*      */       {
/* 2630 */         CustomColorizer.updateMyceliumFX(entityfx);
/*      */       }
/*      */       
/* 2633 */       if (p_174974_1_ == EnumParticleTypes.PORTAL.getParticleID())
/*      */       {
/* 2635 */         CustomColorizer.updatePortalFX(entityfx);
/*      */       }
/*      */       
/* 2638 */       if (p_174974_1_ == EnumParticleTypes.REDSTONE.getParticleID())
/*      */       {
/* 2640 */         CustomColorizer.updateReddustFX(entityfx, (IBlockAccess)this.theWorld, p_174974_3_, p_174974_5_, p_174974_7_);
/*      */       }
/*      */       
/* 2643 */       return entityfx;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2649 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityAdded(Entity entityIn) {
/* 2659 */     RandomMobs.entityLoaded(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityRemoved(Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteAllDisplayLists() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void broadcastSound(int p_180440_1_, BlockPos p_180440_2_, int p_180440_3_) {
/* 2679 */     switch (p_180440_1_) {
/*      */       
/*      */       case 1013:
/*      */       case 1018:
/* 2683 */         if (this.mc.getRenderViewEntity() != null) {
/*      */           
/* 2685 */           double d0 = p_180440_2_.getX() - (this.mc.getRenderViewEntity()).posX;
/* 2686 */           double d1 = p_180440_2_.getY() - (this.mc.getRenderViewEntity()).posY;
/* 2687 */           double d2 = p_180440_2_.getZ() - (this.mc.getRenderViewEntity()).posZ;
/* 2688 */           double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 2689 */           double d4 = (this.mc.getRenderViewEntity()).posX;
/* 2690 */           double d5 = (this.mc.getRenderViewEntity()).posY;
/* 2691 */           double d6 = (this.mc.getRenderViewEntity()).posZ;
/*      */           
/* 2693 */           if (d3 > 0.0D) {
/*      */             
/* 2695 */             d4 += d0 / d3 * 2.0D;
/* 2696 */             d5 += d1 / d3 * 2.0D;
/* 2697 */             d6 += d2 / d3 * 2.0D;
/*      */           } 
/*      */           
/* 2700 */           if (p_180440_1_ == 1013) {
/*      */             
/* 2702 */             this.theWorld.playSound(d4, d5, d6, "mob.wither.spawn", 1.0F, 1.0F, false);
/*      */             
/*      */             break;
/*      */           } 
/* 2706 */           this.theWorld.playSound(d4, d5, d6, "mob.enderdragon.end", 5.0F, 1.0F, false);
/*      */         }  break;
/*      */     }  } public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) { int k, l; double d13, d15, d19; int l1; Block block; double d11, d12, d14; int i1, j1;
/*      */     float f, f1, f2;
/*      */     EnumParticleTypes enumparticletypes;
/*      */     int k1;
/*      */     double var7, var9, var11;
/*      */     int var13;
/*      */     double var32;
/*      */     int var18;
/* 2716 */     Random random = this.theWorld.rand;
/*      */     
/* 2718 */     switch (sfxType) {
/*      */       
/*      */       case 1000:
/* 2721 */         this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0F, 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1001:
/* 2725 */         this.theWorld.playSoundAtPos(blockPosIn, "random.click", 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1002:
/* 2729 */         this.theWorld.playSoundAtPos(blockPosIn, "random.bow", 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1003:
/* 2733 */         this.theWorld.playSoundAtPos(blockPosIn, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1004:
/* 2737 */         this.theWorld.playSoundAtPos(blockPosIn, "random.fizz", 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
/*      */         break;
/*      */       
/*      */       case 1005:
/* 2741 */         if (Item.getItemById(p_180439_4_) instanceof ItemRecord) {
/*      */           
/* 2743 */           this.theWorld.playRecord(blockPosIn, "records." + ((ItemRecord)Item.getItemById(p_180439_4_)).recordName);
/*      */           
/*      */           break;
/*      */         } 
/* 2747 */         this.theWorld.playRecord(blockPosIn, null);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1006:
/* 2753 */         this.theWorld.playSoundAtPos(blockPosIn, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1007:
/* 2757 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.charge", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1008:
/* 2761 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1009:
/* 2765 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.ghast.fireball", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1010:
/* 2769 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.wood", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1011:
/* 2773 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.metal", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1012:
/* 2777 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.woodbreak", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1014:
/* 2781 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.wither.shoot", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1015:
/* 2785 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.bat.takeoff", 0.05F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1016:
/* 2789 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.infect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1017:
/* 2793 */         this.theWorld.playSoundAtPos(blockPosIn, "mob.zombie.unfect", 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1020:
/* 2797 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1021:
/* 2801 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1022:
/* 2805 */         this.theWorld.playSoundAtPos(blockPosIn, "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2000:
/* 2809 */         k = p_180439_4_ % 3 - 1;
/* 2810 */         l = p_180439_4_ / 3 % 3 - 1;
/* 2811 */         d13 = blockPosIn.getX() + k * 0.6D + 0.5D;
/* 2812 */         d15 = blockPosIn.getY() + 0.5D;
/* 2813 */         d19 = blockPosIn.getZ() + l * 0.6D + 0.5D;
/*      */         
/* 2815 */         for (l1 = 0; l1 < 10; l1++) {
/*      */           
/* 2817 */           double d20 = random.nextDouble() * 0.2D + 0.01D;
/* 2818 */           double d21 = d13 + k * 0.01D + (random.nextDouble() - 0.5D) * l * 0.5D;
/* 2819 */           double d22 = d15 + (random.nextDouble() - 0.5D) * 0.5D;
/* 2820 */           double d23 = d19 + l * 0.01D + (random.nextDouble() - 0.5D) * k * 0.5D;
/* 2821 */           double d24 = k * d20 + random.nextGaussian() * 0.01D;
/* 2822 */           double d9 = -0.03D + random.nextGaussian() * 0.01D;
/* 2823 */           double d10 = l * d20 + random.nextGaussian() * 0.01D;
/* 2824 */           spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d21, d22, d23, d24, d9, d10, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2001:
/* 2830 */         block = Block.getBlockById(p_180439_4_ & 0xFFF);
/*      */         
/* 2832 */         if (block.getMaterial() != Material.air)
/*      */         {
/* 2834 */           this.mc.getSoundHandler().playSound((ISound)new PositionedSoundRecord(new ResourceLocation(block.stepSound.getBreakSound()), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F, blockPosIn.getX() + 0.5F, blockPosIn.getY() + 0.5F, blockPosIn.getZ() + 0.5F));
/*      */         }
/*      */         
/* 2837 */         this.mc.effectRenderer.addBlockDestroyEffects(blockPosIn, block.getStateFromMeta(p_180439_4_ >> 12 & 0xFF));
/*      */         break;
/*      */       
/*      */       case 2002:
/* 2841 */         d11 = blockPosIn.getX();
/* 2842 */         d12 = blockPosIn.getY();
/* 2843 */         d14 = blockPosIn.getZ();
/*      */         
/* 2845 */         for (i1 = 0; i1 < 8; i1++) {
/*      */           
/* 2847 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, d11, d12, d14, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem((Item)Items.potionitem), p_180439_4_ });
/*      */         } 
/*      */         
/* 2850 */         j1 = Items.potionitem.getColorFromDamage(p_180439_4_);
/* 2851 */         f = (j1 >> 16 & 0xFF) / 255.0F;
/* 2852 */         f1 = (j1 >> 8 & 0xFF) / 255.0F;
/* 2853 */         f2 = (j1 >> 0 & 0xFF) / 255.0F;
/* 2854 */         enumparticletypes = EnumParticleTypes.SPELL;
/*      */         
/* 2856 */         if (Items.potionitem.isEffectInstant(p_180439_4_))
/*      */         {
/* 2858 */           enumparticletypes = EnumParticleTypes.SPELL_INSTANT;
/*      */         }
/*      */         
/* 2861 */         for (k1 = 0; k1 < 100; k1++) {
/*      */           
/* 2863 */           double d16 = random.nextDouble() * 4.0D;
/* 2864 */           double d17 = random.nextDouble() * Math.PI * 2.0D;
/* 2865 */           double d18 = Math.cos(d17) * d16;
/* 2866 */           double d7 = 0.01D + random.nextDouble() * 0.5D;
/* 2867 */           double d8 = Math.sin(d17) * d16;
/* 2868 */           EntityFX entityfx = spawnEntityFX(enumparticletypes.getParticleID(), enumparticletypes.getShouldIgnoreRange(), d11 + d18 * 0.1D, d12 + 0.3D, d14 + d8 * 0.1D, d18, d7, d8, new int[0]);
/*      */           
/* 2870 */           if (entityfx != null) {
/*      */             
/* 2872 */             float f3 = 0.75F + random.nextFloat() * 0.25F;
/* 2873 */             entityfx.setRBGColorF(f * f3, f1 * f3, f2 * f3);
/* 2874 */             entityfx.multiplyVelocity((float)d16);
/*      */           } 
/*      */         } 
/*      */         
/* 2878 */         this.theWorld.playSoundAtPos(blockPosIn, "game.potion.smash", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2003:
/* 2882 */         var7 = blockPosIn.getX() + 0.5D;
/* 2883 */         var9 = blockPosIn.getY();
/* 2884 */         var11 = blockPosIn.getZ() + 0.5D;
/*      */         
/* 2886 */         for (var13 = 0; var13 < 8; var13++) {
/*      */           
/* 2888 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, var7, var9, var11, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem(Items.ender_eye) });
/*      */         } 
/*      */         
/* 2891 */         for (var32 = 0.0D; var32 < 6.283185307179586D; var32 += 0.15707963267948966D) {
/*      */           
/* 2893 */           spawnParticle(EnumParticleTypes.PORTAL, var7 + Math.cos(var32) * 5.0D, var9 - 0.4D, var11 + Math.sin(var32) * 5.0D, Math.cos(var32) * -5.0D, 0.0D, Math.sin(var32) * -5.0D, new int[0]);
/* 2894 */           spawnParticle(EnumParticleTypes.PORTAL, var7 + Math.cos(var32) * 5.0D, var9 - 0.4D, var11 + Math.sin(var32) * 5.0D, Math.cos(var32) * -7.0D, 0.0D, Math.sin(var32) * -7.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2004:
/* 2900 */         for (var18 = 0; var18 < 20; var18++) {
/*      */           
/* 2902 */           double d3 = blockPosIn.getX() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 2903 */           double d4 = blockPosIn.getY() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 2904 */           double d5 = blockPosIn.getZ() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 2905 */           this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
/* 2906 */           this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d3, d4, d5, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2005:
/* 2912 */         ItemDye.spawnBonemealParticles((World)this.theWorld, blockPosIn, p_180439_4_);
/*      */         break;
/*      */     }  }
/*      */ 
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 2918 */     if (progress >= 0 && progress < 10) {
/*      */       
/* 2920 */       DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress)this.damagedBlocks.get(Integer.valueOf(breakerId));
/*      */       
/* 2922 */       if (destroyblockprogress == null || destroyblockprogress.getPosition().getX() != pos.getX() || destroyblockprogress.getPosition().getY() != pos.getY() || destroyblockprogress.getPosition().getZ() != pos.getZ()) {
/*      */         
/* 2924 */         destroyblockprogress = new DestroyBlockProgress(breakerId, pos);
/* 2925 */         this.damagedBlocks.put(Integer.valueOf(breakerId), destroyblockprogress);
/*      */       } 
/*      */       
/* 2928 */       destroyblockprogress.setPartialBlockDamage(progress);
/* 2929 */       destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
/*      */     }
/*      */     else {
/*      */       
/* 2933 */       this.damagedBlocks.remove(Integer.valueOf(breakerId));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisplayListEntitiesDirty() {
/* 2939 */     this.displayListEntitiesDirty = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetClouds() {
/* 2944 */     this.cloudRenderer.reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountRenderers() {
/* 2949 */     return this.viewFrustum.renderChunks.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountActiveRenderers() {
/* 2954 */     return this.renderInfos.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountEntitiesRendered() {
/* 2959 */     return this.countEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountTileEntitiesRendered() {
/* 2964 */     return this.countTileEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_181023_a(Collection<?> p_181023_1_, Collection p_181023_2_) {
/* 2969 */     Set set = this.field_181024_n;
/*      */     
/* 2971 */     synchronized (this.field_181024_n) {
/*      */       
/* 2973 */       this.field_181024_n.removeAll(p_181023_1_);
/* 2974 */       this.field_181024_n.addAll(p_181023_2_);
/*      */     } 
/*      */   }
/*      */   
/*      */   static final class RenderGlobal$2
/*      */   {
/* 2980 */     static final int[] field_178037_a = new int[(VertexFormatElement.EnumUsage.values()).length];
/*      */     
/*      */     private static final String __OBFID = "CL_00002535";
/*      */ 
/*      */     
/*      */     static {
/*      */       try {
/* 2987 */         field_178037_a[VertexFormatElement.EnumUsage.POSITION.ordinal()] = 1;
/*      */       }
/* 2989 */       catch (NoSuchFieldError noSuchFieldError) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 2996 */         field_178037_a[VertexFormatElement.EnumUsage.UV.ordinal()] = 2;
/*      */       }
/* 2998 */       catch (NoSuchFieldError noSuchFieldError) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 3005 */         field_178037_a[VertexFormatElement.EnumUsage.COLOR.ordinal()] = 3;
/*      */       }
/* 3007 */       catch (NoSuchFieldError noSuchFieldError) {}
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class ContainerLocalRenderInformation
/*      */   {
/*      */     final RenderChunk renderChunk;
/*      */     
/*      */     final EnumFacing facing;
/*      */     
/*      */     final Set setFacing;
/*      */     
/*      */     final int counter;
/*      */     private static final String __OBFID = "CL_00002534";
/*      */     
/*      */     private ContainerLocalRenderInformation(RenderChunk renderChunkIn, EnumFacing facingIn, int counterIn) {
/* 3024 */       this.setFacing = EnumSet.noneOf(EnumFacing.class);
/* 3025 */       this.renderChunk = renderChunkIn;
/* 3026 */       this.facing = facingIn;
/* 3027 */       this.counter = counterIn;
/*      */     }
/*      */ 
/*      */     
/*      */     ContainerLocalRenderInformation(RenderChunk p_i4_2_, EnumFacing p_i4_3_, int p_i4_4_, Object p_i4_5_) {
/* 3032 */       this(p_i4_2_, p_i4_3_, p_i4_4_);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\RenderGlobal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */