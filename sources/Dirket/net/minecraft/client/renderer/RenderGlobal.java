package net.minecraft.client.renderer;

import java.io.IOException;
import java.lang.reflect.Field;
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

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.Particle;
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
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.src.ChunkUtils;
import net.minecraft.src.CloudRenderer;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.src.CustomSky;
import net.minecraft.src.DynamicLights;
import net.minecraft.src.Lagometer;
import net.minecraft.src.RandomMobs;
import net.minecraft.src.Reflector;
import net.minecraft.src.RenderInfoLazy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;
import us.dev.direkt.Direkt;
import us.dev.direkt.module.internal.render.NoRender;

public class RenderGlobal implements IWorldEventListener, IResourceManagerReloadListener {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("textures/environment/moon_phases.png");
	private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("textures/environment/sun.png");
	private static final ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("textures/environment/clouds.png");
	private static final ResourceLocation END_SKY_TEXTURES = new ResourceLocation("textures/environment/end_sky.png");
	private static final ResourceLocation FORCEFIELD_TEXTURES = new ResourceLocation("textures/misc/forcefield.png");

	/** A reference to the Minecraft object. */
	public final Minecraft mc;

	/** The RenderEngine instance used by RenderGlobal */
	private final TextureManager renderEngine;
	private final RenderManager renderManager;
	private WorldClient theWorld;
	private Set<RenderChunk> chunksToUpdate = Sets.<RenderChunk> newLinkedHashSet();
	private List<RenderGlobal.ContainerLocalRenderInformation> renderInfos = Lists.<RenderGlobal.ContainerLocalRenderInformation> newArrayListWithCapacity(69696);
	private final Set<TileEntity> setTileEntities = Sets.<TileEntity> newHashSet();
	private ViewFrustum viewFrustum;

	/** The star GL Call list */
	private int starGLCallList = -1;

	/** OpenGL sky list */
	private int glSkyList = -1;

	/** OpenGL sky list 2 */
	private int glSkyList2 = -1;
	private final VertexFormat vertexBufferFormat;
	private net.minecraft.client.renderer.vertex.VertexBuffer starVBO;
	private net.minecraft.client.renderer.vertex.VertexBuffer skyVBO;
	private net.minecraft.client.renderer.vertex.VertexBuffer sky2VBO;

	/**
	 * counts the cloud render updates. Used with mod to stagger some updates
	 */
	private int cloudTickCounter;
	public final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.<Integer, DestroyBlockProgress> newHashMap();
	private final Map<BlockPos, ISound> mapSoundPositions = Maps.<BlockPos, ISound> newHashMap();
	private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
	private Framebuffer entityOutlineFramebuffer;

	/** Stores the shader group for the entity_outline shader */
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
	private ChunkRenderDispatcher renderDispatcher;
	private ChunkRenderContainer renderContainer;
	private int renderDistanceChunks = -1;

	/** Render entities startup counter (init value=2) */
	private int renderEntitiesStartupCounter = 2;

	/** Count entities total */
	private int countEntitiesTotal;

	/** Count entities rendered */
	private int countEntitiesRendered;

	/** Count entities hidden */
	private int countEntitiesHidden;
	private boolean debugFixTerrainFrustum;
	private ClippingHelper debugFixedClippingHelper;
	private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
	private final Vector3d debugTerrainFrustumPosition = new Vector3d();
	private boolean vboEnabled;
	IRenderChunkFactory renderChunkFactory;
	private double prevRenderSortX;
	private double prevRenderSortY;
	private double prevRenderSortZ;
	public boolean displayListEntitiesDirty = true;
	private boolean entityOutlinesRendered;
	private final Set<BlockPos> setLightUpdates = Sets.<BlockPos> newHashSet();
	private CloudRenderer cloudRenderer;
	public Entity renderedEntity;
	public Set chunksToResortTransparency = new LinkedHashSet();
	public Set chunksToUpdateForced = new LinkedHashSet();
	private Deque visibilityDeque = new ArrayDeque();
	private List<RenderGlobal.ContainerLocalRenderInformation> renderInfosEntities = new ArrayList(1024);
	private List<RenderGlobal.ContainerLocalRenderInformation> renderInfosTileEntities = new ArrayList(1024);
	private List renderInfosNormal = new ArrayList(1024);
	private List renderInfosEntitiesNormal = new ArrayList(1024);
	private List renderInfosTileEntitiesNormal = new ArrayList(1024);
	private List renderInfosShadow = new ArrayList(1024);
	private List renderInfosEntitiesShadow = new ArrayList(1024);
	private List renderInfosTileEntitiesShadow = new ArrayList(1024);
	private int renderDistance = 0;
	private int renderDistanceSq = 0;
	private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList(EnumFacing.VALUES)));
	private int countTileEntitiesRendered;
	private IChunkProvider worldChunkProvider = null;
	private Long2ObjectMap<Chunk> worldChunkProviderMap = null;
	private int countLoadedChunksPrev = 0;
	static Deque<RenderGlobal.ContainerLocalRenderInformation> renderInfoCache = new ArrayDeque();

	public RenderGlobal(Minecraft mcIn) {
		this.cloudRenderer = new CloudRenderer(mcIn);
		this.mc = mcIn;
		this.renderManager = mcIn.getRenderManager();
		this.renderEngine = mcIn.getTextureManager();
		this.renderEngine.bindTexture(FORCEFIELD_TEXTURES);
		GlStateManager.glTexParameteri(3553, 10242, 10497);
		GlStateManager.glTexParameteri(3553, 10243, 10497);
		GlStateManager.bindTexture(0);
		this.updateDestroyBlockIcons();
		this.vboEnabled = OpenGlHelper.useVbo();

		if (this.vboEnabled) {
			this.renderContainer = new VboRenderList();
			this.renderChunkFactory = new VboChunkFactory();
		} else {
			this.renderContainer = new RenderList();
			this.renderChunkFactory = new ListChunkFactory();
		}

		this.vertexBufferFormat = new VertexFormat();
		this.vertexBufferFormat.addElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
		this.generateStars();
		this.generateSky();
		this.generateSky2();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		this.updateDestroyBlockIcons();
	}

	private void updateDestroyBlockIcons() {
		TextureMap texturemap = this.mc.getTextureMapBlocks();

		for (int i = 0; i < this.destroyBlockIcons.length; ++i) {
			this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
		}
	}

	/**
	 * Creates the entity outline shader to be stored in RenderGlobal.entityOutlineShader
	 */
	public void makeEntityOutlineShader() {
		if (OpenGlHelper.shadersSupported) {
			if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
				ShaderLinkHelper.setNewStaticShaderLinkHelper();
			}

			ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");

			try {
				this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocation);
				this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
				this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
			} catch (IOException ioexception) {
				LOGGER.warn("Failed to load shader: {}", new Object[] { resourcelocation, ioexception });
				this.entityOutlineShader = null;
				this.entityOutlineFramebuffer = null;
			} catch (JsonSyntaxException jsonsyntaxexception) {
				LOGGER.warn("Failed to load shader: {}", new Object[] { resourcelocation, jsonsyntaxexception });
				this.entityOutlineShader = null;
				this.entityOutlineFramebuffer = null;
			}
		} else {
			this.entityOutlineShader = null;
			this.entityOutlineFramebuffer = null;
		}
	}

	public void renderEntityOutlineFramebuffer() {
		if (this.isRenderEntityOutlines()) {
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
			this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
			GlStateManager.disableBlend();
		}
	}

	protected boolean isRenderEntityOutlines() {
		return !Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing() ? (this.entityOutlineFramebuffer != null) && (this.entityOutlineShader != null) && (this.mc.thePlayer != null)
				: false;
	}

	private void generateSky2() {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();

		if (this.sky2VBO != null) {
			this.sky2VBO.deleteGlBuffers();
		}

		if (this.glSkyList2 >= 0) {
			GLAllocation.deleteDisplayLists(this.glSkyList2);
			this.glSkyList2 = -1;
		}

		if (this.vboEnabled) {
			this.sky2VBO = new net.minecraft.client.renderer.vertex.VertexBuffer(this.vertexBufferFormat);
			this.renderSky(vertexbuffer, -16.0F, true);
			vertexbuffer.finishDrawing();
			vertexbuffer.reset();
			this.sky2VBO.bufferData(vertexbuffer.getByteBuffer());
		} else {
			this.glSkyList2 = GLAllocation.generateDisplayLists(1);
			GlStateManager.glNewList(this.glSkyList2, 4864);
			this.renderSky(vertexbuffer, -16.0F, true);
			tessellator.draw();
			GlStateManager.glEndList();
		}
	}

	private void generateSky() {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();

		if (this.skyVBO != null) {
			this.skyVBO.deleteGlBuffers();
		}

		if (this.glSkyList >= 0) {
			GLAllocation.deleteDisplayLists(this.glSkyList);
			this.glSkyList = -1;
		}

		if (this.vboEnabled) {
			this.skyVBO = new net.minecraft.client.renderer.vertex.VertexBuffer(this.vertexBufferFormat);
			this.renderSky(vertexbuffer, 16.0F, false);
			vertexbuffer.finishDrawing();
			vertexbuffer.reset();
			this.skyVBO.bufferData(vertexbuffer.getByteBuffer());
		} else {
			this.glSkyList = GLAllocation.generateDisplayLists(1);
			GlStateManager.glNewList(this.glSkyList, 4864);
			this.renderSky(vertexbuffer, 16.0F, false);
			tessellator.draw();
			GlStateManager.glEndList();
		}
	}

	private void renderSky(VertexBuffer worldRendererIn, float posY, boolean reverseX) {
		int i = 64;
		int j = 6;
		worldRendererIn.begin(7, DefaultVertexFormats.POSITION);

		for (int k = -384; k <= 384; k += 64) {
			for (int l = -384; l <= 384; l += 64) {
				float f = k;
				float f1 = k + 64;

				if (reverseX) {
					f1 = k;
					f = k + 64;
				}

				worldRendererIn.pos(f, posY, l).endVertex();
				worldRendererIn.pos(f1, posY, l).endVertex();
				worldRendererIn.pos(f1, posY, l + 64).endVertex();
				worldRendererIn.pos(f, posY, l + 64).endVertex();
			}
		}
	}

	private void generateStars() {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();

		if (this.starVBO != null) {
			this.starVBO.deleteGlBuffers();
		}

		if (this.starGLCallList >= 0) {
			GLAllocation.deleteDisplayLists(this.starGLCallList);
			this.starGLCallList = -1;
		}

		if (this.vboEnabled) {
			this.starVBO = new net.minecraft.client.renderer.vertex.VertexBuffer(this.vertexBufferFormat);
			this.renderStars(vertexbuffer);
			vertexbuffer.finishDrawing();
			vertexbuffer.reset();
			this.starVBO.bufferData(vertexbuffer.getByteBuffer());
		} else {
			this.starGLCallList = GLAllocation.generateDisplayLists(1);
			GlStateManager.pushMatrix();
			GlStateManager.glNewList(this.starGLCallList, 4864);
			this.renderStars(vertexbuffer);
			tessellator.draw();
			GlStateManager.glEndList();
			GlStateManager.popMatrix();
		}
	}

	private void renderStars(VertexBuffer worldRendererIn) {
		Random random = new Random(10842L);
		worldRendererIn.begin(7, DefaultVertexFormats.POSITION);

		for (int i = 0; i < 1500; ++i) {
			double d0 = (random.nextFloat() * 2.0F) - 1.0F;
			double d1 = (random.nextFloat() * 2.0F) - 1.0F;
			double d2 = (random.nextFloat() * 2.0F) - 1.0F;
			double d3 = 0.15F + (random.nextFloat() * 0.1F);
			double d4 = (d0 * d0) + (d1 * d1) + (d2 * d2);

			if ((d4 < 1.0D) && (d4 > 0.01D)) {
				d4 = 1.0D / Math.sqrt(d4);
				d0 = d0 * d4;
				d1 = d1 * d4;
				d2 = d2 * d4;
				double d5 = d0 * 100.0D;
				double d6 = d1 * 100.0D;
				double d7 = d2 * 100.0D;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt((d0 * d0) + (d2 * d2)), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = random.nextDouble() * Math.PI * 2.0D;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for (int j = 0; j < 4; ++j) {
					double d17 = 0.0D;
					double d18 = ((j & 2) - 1) * d3;
					double d19 = (((j + 1) & 2) - 1) * d3;
					double d20 = 0.0D;
					double d21 = (d18 * d16) - (d19 * d15);
					double d22 = (d19 * d16) + (d18 * d15);
					double d23 = (d21 * d12) + (0.0D * d13);
					double d24 = (0.0D * d12) - (d21 * d13);
					double d25 = (d24 * d9) - (d22 * d10);
					double d26 = (d22 * d9) + (d24 * d10);
					worldRendererIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
				}
			}
		}
	}

	/**
	 * set null to clear
	 */
	public void setWorldAndLoadRenderers(@Nullable WorldClient worldClientIn) {
		if (this.theWorld != null) {
			this.theWorld.removeEventListener(this);
		}

		this.frustumUpdatePosX = Double.MIN_VALUE;
		this.frustumUpdatePosY = Double.MIN_VALUE;
		this.frustumUpdatePosZ = Double.MIN_VALUE;
		this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
		this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
		this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
		this.renderManager.set(worldClientIn);
		this.theWorld = worldClientIn;

		if (Config.isDynamicLights()) {
			DynamicLights.clear();
		}

		if (worldClientIn != null) {
			worldClientIn.addEventListener(this);
			this.loadRenderers();
		} else {
			this.chunksToUpdate.clear();
			this.renderInfos.clear();
			this.viewFrustum = null;

			if (this.renderDispatcher != null) {
				this.renderDispatcher.stopWorkerThreads();
			}

			this.renderDispatcher = null;
		}
	}

	/**
	 * Loads all the renderers and sets up the basic settings usage
	 */
	public void loadRenderers() {
		if (this.theWorld != null) {
			if (this.renderDispatcher == null) {
				this.renderDispatcher = new ChunkRenderDispatcher();
			}

			this.displayListEntitiesDirty = true;
			Blocks.LEAVES.setGraphicsLevel(Config.isTreesFancy());
			Blocks.LEAVES2.setGraphicsLevel(Config.isTreesFancy());
			BlockModelRenderer.updateAoLightValue();
			renderInfoCache.clear();

			if (Config.isDynamicLights()) {
				DynamicLights.clear();
			}

			this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
			this.renderDistance = this.renderDistanceChunks * 16;
			this.renderDistanceSq = this.renderDistance * this.renderDistance;
			boolean flag = this.vboEnabled;
			this.vboEnabled = OpenGlHelper.useVbo();

			if (flag && !this.vboEnabled) {
				this.renderContainer = new RenderList();
				this.renderChunkFactory = new ListChunkFactory();
			} else if (!flag && this.vboEnabled) {
				this.renderContainer = new VboRenderList();
				this.renderChunkFactory = new VboChunkFactory();
			}

			if (flag != this.vboEnabled) {
				this.generateStars();
				this.generateSky();
				this.generateSky2();
			}

			if (this.viewFrustum != null) {
				this.viewFrustum.deleteGlResources();
			}

			this.stopChunkUpdates();

			synchronized (this.setTileEntities) {
				this.setTileEntities.clear();
			}

			this.viewFrustum = new ViewFrustum(this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);

			if (this.theWorld != null) {
				Entity entity = this.mc.getRenderViewEntity();

				if (entity != null) {
					this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
				}
			}

			this.renderEntitiesStartupCounter = 2;
		}
	}

	protected void stopChunkUpdates() {
		this.chunksToUpdate.clear();
		this.renderDispatcher.stopChunkUpdates();
	}

	public void createBindEntityOutlineFbs(int width, int height) {
		if (OpenGlHelper.shadersSupported && (this.entityOutlineShader != null)) {
			this.entityOutlineShader.createBindFramebuffers(width, height);
		}
	}

	public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
		int i = 0;

		if (Reflector.MinecraftForgeClient_getRenderPass.exists()) {
			i = Reflector.callInt(Reflector.MinecraftForgeClient_getRenderPass, new Object[0]);
		}

		if (this.renderEntitiesStartupCounter > 0) {
			if (i > 0) { return; }

			--this.renderEntitiesStartupCounter;
		} else {
			double d0 = renderViewEntity.prevPosX + ((renderViewEntity.posX - renderViewEntity.prevPosX) * partialTicks);
			double d1 = renderViewEntity.prevPosY + ((renderViewEntity.posY - renderViewEntity.prevPosY) * partialTicks);
			double d2 = renderViewEntity.prevPosZ + ((renderViewEntity.posZ - renderViewEntity.prevPosZ) * partialTicks);
			this.theWorld.theProfiler.startSection("prepare");
			TileEntityRendererDispatcher.instance.func_190056_a(this.theWorld, this.mc.getTextureManager(), this.mc.fontRendererObj, this.mc.getRenderViewEntity(), this.mc.objectMouseOver,
					partialTicks);
			this.renderManager.cacheActiveRenderInfo(this.theWorld, this.mc.fontRendererObj, this.mc.getRenderViewEntity(), this.mc.pointedEntity, this.mc.gameSettings, partialTicks);

			if (i == 0) {
				this.countEntitiesTotal = 0;
				this.countEntitiesRendered = 0;
				this.countEntitiesHidden = 0;
				this.countTileEntitiesRendered = 0;
			}

			Entity entity = this.mc.getRenderViewEntity();
			double d3 = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * partialTicks);
			double d4 = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partialTicks);
			double d5 = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * partialTicks);
			TileEntityRendererDispatcher.staticPlayerX = d3;
			TileEntityRendererDispatcher.staticPlayerY = d4;
			TileEntityRendererDispatcher.staticPlayerZ = d5;
			this.renderManager.setRenderPosition(d3, d4, d5);
			this.mc.entityRenderer.enableLightmap();
			this.theWorld.theProfiler.endStartSection("global");
			List<Entity> list = this.theWorld.getLoadedEntityList();

			if (i == 0) {
				this.countEntitiesTotal = list.size();
			}

			if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
				GlStateManager.disableFog();
			}

			boolean flag = Reflector.ForgeEntity_shouldRenderInPass.exists();
			boolean flag1 = Reflector.ForgeTileEntity_shouldRenderInPass.exists();

			for (int j = 0; j < this.theWorld.weatherEffects.size(); ++j) {
				Entity entity1 = this.theWorld.weatherEffects.get(j);

				if (!flag || Reflector.callBoolean(entity1, Reflector.ForgeEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })) {
					++this.countEntitiesRendered;

					if (entity1.isInRangeToRender3d(d0, d1, d2)) {
						this.renderManager.renderEntityStatic(entity1, partialTicks, false);
					}
				}
			}

			this.theWorld.theProfiler.endStartSection("entities");
			boolean flag4 = Config.isShaders();

			if (flag4) {
				Shaders.beginEntities();
			}

			boolean flag5 = this.mc.gameSettings.fancyGraphics;
			this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
			List<Entity> list1 = Lists.<Entity> newArrayList();
			List<Entity> list2 = Lists.<Entity> newArrayList();
			BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

			for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfosEntities) {
				Chunk chunk = this.theWorld.getChunkFromBlockCoords(renderglobal$containerlocalrenderinformation.renderChunk.getPosition());
				ClassInheritanceMultiMap<Entity> classinheritancemultimap = chunk.getEntityLists()[renderglobal$containerlocalrenderinformation.renderChunk.getPosition().getY() / 16];

				if (!classinheritancemultimap.isEmpty()) {
					for (Entity entity2 : classinheritancemultimap) {
						if (!flag || Reflector.callBoolean(entity2, Reflector.ForgeEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })) {
							boolean flag2 = this.renderManager.shouldRender(entity2, camera, d0, d1, d2) || entity2.isRidingOrBeingRiddenBy(this.mc.thePlayer);

							if (flag2) {
								boolean flag3 = this.mc.getRenderViewEntity() instanceof EntityLivingBase ? ((EntityLivingBase) this.mc.getRenderViewEntity()).isPlayerSleeping() : false;

								if (((entity2 != this.mc.getRenderViewEntity()) || (this.mc.gameSettings.thirdPersonView != 0) || flag3)
										&& ((entity2.posY < 0.0D) || (entity2.posY >= 256.0D) || this.theWorld.isBlockLoaded(blockpos$pooledmutableblockpos.func_189535_a(entity2)))) {
									++this.countEntitiesRendered;
									this.renderedEntity = entity2;

									if (flag4) {
										Shaders.nextEntity(entity2);
									}

									this.renderManager.renderEntityStatic(entity2, partialTicks, false);
									this.renderedEntity = null;

									if (this.isOutlineActive(entity2, entity, camera)) {
										list1.add(entity2);
									}

									if (this.renderManager.isRenderMultipass(entity2)) {
										list2.add(entity2);
									}
								}
							}
						}
					}
				}
			}

			blockpos$pooledmutableblockpos.release();

			if (!list2.isEmpty()) {
				for (Entity entity3 : list2) {
					if (!flag || Reflector.callBoolean(entity3, Reflector.ForgeEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })) {
						if (flag4) {
							Shaders.nextEntity(entity3);
						}

						this.renderManager.renderMultipass(entity3, partialTicks);
					}
				}
			}

			if ((i == 0) && this.isRenderEntityOutlines() && (!list1.isEmpty() || this.entityOutlinesRendered)) {
				this.theWorld.theProfiler.endStartSection("entityOutlines");
				this.entityOutlineFramebuffer.framebufferClear();
				this.entityOutlinesRendered = !list1.isEmpty();

				if (!list1.isEmpty()) {
					GlStateManager.depthFunc(519);
					GlStateManager.disableFog();
					this.entityOutlineFramebuffer.bindFramebuffer(false);
					RenderHelper.disableStandardItemLighting();
					this.renderManager.setRenderOutlines(true);

					for (int k = 0; k < ((List) list1).size(); ++k) {
						Entity entity4 = list1.get(k);

						if (!flag || Reflector.callBoolean(entity4, Reflector.ForgeEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })) {
							if (flag4) {
								Shaders.nextEntity(entity4);
							}

							this.renderManager.renderEntityStatic(entity4, partialTicks, false);
						}
					}

					this.renderManager.setRenderOutlines(false);
					RenderHelper.enableStandardItemLighting();
					GlStateManager.depthMask(false);
					this.entityOutlineShader.loadShaderGroup(partialTicks);
					GlStateManager.enableLighting();
					GlStateManager.depthMask(true);
					GlStateManager.enableFog();
					GlStateManager.enableBlend();
					GlStateManager.enableColorMaterial();
					GlStateManager.depthFunc(515);
					GlStateManager.enableDepth();
					GlStateManager.enableAlpha();
				}

				this.mc.getFramebuffer().bindFramebuffer(false);
			}

			if (!this.isRenderEntityOutlines() && (!list1.isEmpty() || this.entityOutlinesRendered)) {
				this.theWorld.theProfiler.endStartSection("entityOutlines");
				this.entityOutlinesRendered = !list1.isEmpty();

				if (!list1.isEmpty()) {
					GlStateManager.disableFog();
					GlStateManager.disableDepth();
					this.mc.entityRenderer.disableLightmap();
					RenderHelper.disableStandardItemLighting();
					this.renderManager.setRenderOutlines(true);

					for (int l = 0; l < ((List) list1).size(); ++l) {
						Entity entity5 = list1.get(l);

						if (!flag || Reflector.callBoolean(entity5, Reflector.ForgeEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })) {
							if (flag4) {
								Shaders.nextEntity(entity5);
							}

							this.renderManager.renderEntityStatic(entity5, partialTicks, false);
						}
					}

					this.renderManager.setRenderOutlines(false);
					RenderHelper.enableStandardItemLighting();
					this.mc.entityRenderer.enableLightmap();
					GlStateManager.enableDepth();
					GlStateManager.enableFog();
				}
			}

			this.mc.gameSettings.fancyGraphics = flag5;
			FontRenderer fontrenderer = TileEntityRendererDispatcher.instance.getFontRenderer();

			if (flag4) {
				Shaders.endEntities();
				Shaders.beginBlockEntities();
			}

			this.theWorld.theProfiler.endStartSection("blockentities");
			RenderHelper.enableStandardItemLighting();

			if (Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch.exists()) {
				Reflector.call(TileEntityRendererDispatcher.instance, Reflector.ForgeTileEntityRendererDispatcher_preDrawBatch, new Object[0]);
			}

			label1622:

			for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 : this.renderInfosTileEntities) {
				List<TileEntity> list3 = renderglobal$containerlocalrenderinformation1.renderChunk.getCompiledChunk().getTileEntities();

				if (!list3.isEmpty()) {
					Iterator iterator = list3.iterator();

					while (true) {
						TileEntity tileentity1;

						while (true) {
							if (!iterator.hasNext()) {
								continue label1622;
							}

							tileentity1 = (TileEntity) iterator.next();

							if (!flag1) {
								break;
							}

							if (Reflector.callBoolean(tileentity1, Reflector.ForgeTileEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })) {
								AxisAlignedBB axisalignedbb = (AxisAlignedBB) Reflector.call(tileentity1, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object[0]);

								if ((axisalignedbb == null) || camera.isBoundingBoxInFrustum(axisalignedbb)) {
									break;
								}
							}
						}

						Class oclass = tileentity1.getClass();

						if ((oclass == TileEntitySign.class) && !Config.zoomMode) {
							EntityPlayer entityplayer = this.mc.thePlayer;
							double d6 = tileentity1.getDistanceSq(entityplayer.posX, entityplayer.posY, entityplayer.posZ);

							if (d6 > 256.0D) {
								fontrenderer.enabled = false;
							}
						}

						if (flag4) {
							Shaders.nextBlockEntity(tileentity1);
						}

						TileEntityRendererDispatcher.instance.renderTileEntity(tileentity1, partialTicks, -1);
						++this.countTileEntitiesRendered;
						fontrenderer.enabled = true;
					}
				}
			}

			synchronized (this.setTileEntities) {
				for (TileEntity tileentity : this.setTileEntities) {
					if (!flag1 || Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })) {
						if (flag4) {
							Shaders.nextBlockEntity(tileentity);
						}

						TileEntityRendererDispatcher.instance.renderTileEntity(tileentity, partialTicks, -1);
					}
				}
			}

			if (Reflector.ForgeTileEntityRendererDispatcher_drawBatch.exists()) {
				Reflector.callVoid(TileEntityRendererDispatcher.instance, Reflector.ForgeTileEntityRendererDispatcher_drawBatch, new Object[] { Integer.valueOf(i) });
			}

			this.preRenderDamagedBlocks();

			for (DestroyBlockProgress destroyblockprogress : this.damagedBlocks.values()) {
				BlockPos blockpos = destroyblockprogress.getPosition();
				TileEntity tileentity2 = this.theWorld.getTileEntity(blockpos);

				if (tileentity2 instanceof TileEntityChest) {
					TileEntityChest tileentitychest = (TileEntityChest) tileentity2;

					if (tileentitychest.adjacentChestXNeg != null) {
						blockpos = blockpos.offset(EnumFacing.WEST);
						tileentity2 = this.theWorld.getTileEntity(blockpos);
					} else if (tileentitychest.adjacentChestZNeg != null) {
						blockpos = blockpos.offset(EnumFacing.NORTH);
						tileentity2 = this.theWorld.getTileEntity(blockpos);
					}
				}

				Block block = this.theWorld.getBlockState(blockpos).getBlock();
				boolean flag6;

				if (flag1) {
					flag6 = false;

					if ((tileentity2 != null) && Reflector.callBoolean(tileentity2, Reflector.ForgeTileEntity_shouldRenderInPass, new Object[] { Integer.valueOf(i) })
							&& Reflector.callBoolean(tileentity2, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0])) {
						AxisAlignedBB axisalignedbb1 = (AxisAlignedBB) Reflector.call(tileentity2, Reflector.ForgeTileEntity_getRenderBoundingBox, new Object[0]);

						if (axisalignedbb1 != null) {
							flag6 = camera.isBoundingBoxInFrustum(axisalignedbb1);
						}
					}
				} else {
					flag6 = (tileentity2 != null) && ((block instanceof BlockChest) || (block instanceof BlockEnderChest) || (block instanceof BlockSign) || (block instanceof BlockSkull));
				}

				if (flag6) {
					if (flag4) {
						Shaders.nextBlockEntity(tileentity2);
					}

					TileEntityRendererDispatcher.instance.renderTileEntity(tileentity2, partialTicks, destroyblockprogress.getPartialBlockDamage());
				}
			}

			this.postRenderDamagedBlocks();
			this.mc.entityRenderer.disableLightmap();
			this.mc.mcProfiler.endSection();
		}
	}

	private boolean isOutlineActive(Entity p_184383_1_, Entity p_184383_2_, ICamera p_184383_3_) {
		boolean flag = (p_184383_2_ instanceof EntityLivingBase) && ((EntityLivingBase) p_184383_2_).isPlayerSleeping();
		return (p_184383_1_ == p_184383_2_) && (this.mc.gameSettings.thirdPersonView == 0) && !flag ? false
				: (p_184383_1_.isGlowing() ? true
						: (this.mc.thePlayer.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown() && (p_184383_1_ instanceof EntityPlayer)
								? p_184383_1_.ignoreFrustumCheck || p_184383_3_.isBoundingBoxInFrustum(p_184383_1_.getEntityBoundingBox()) || p_184383_1_.isRidingOrBeingRiddenBy(this.mc.thePlayer)
								: false));
	}

	/**
	 * Gets the render info for use on the Debug screen
	 */
	public String getDebugInfoRenders() {
		int i = this.viewFrustum.renderChunks.length;
		int j = this.getRenderedChunks();
		return String.format("C: %d/%d %sD: %d, L: %d, %s", new Object[] { Integer.valueOf(j), Integer.valueOf(i), this.mc.renderChunksMany ? "(s) " : "", Integer.valueOf(this.renderDistanceChunks),
				Integer.valueOf(this.setLightUpdates.size()), this.renderDispatcher == null ? "null" : this.renderDispatcher.getDebugInfo() });
	}

	protected int getRenderedChunks() {
		int i = 0;

		for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
			CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;

			if ((compiledchunk != CompiledChunk.DUMMY) && !compiledchunk.isEmpty()) {
				++i;
			}
		}

		return i;
	}

	/**
	 * Gets the entities info for use on the Debug screen
	 */
	public String getDebugInfoEntities() {
		return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", " + Config.getVersionDebug();
	}

	public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
		if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
			this.loadRenderers();
		}

		this.theWorld.theProfiler.startSection("camera");
		double d0 = viewEntity.posX - this.frustumUpdatePosX;
		double d1 = viewEntity.posY - this.frustumUpdatePosY;
		double d2 = viewEntity.posZ - this.frustumUpdatePosZ;

		if ((this.frustumUpdatePosChunkX != viewEntity.chunkCoordX) || (this.frustumUpdatePosChunkY != viewEntity.chunkCoordY) || (this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ)
				|| (((d0 * d0) + (d1 * d1) + (d2 * d2)) > 16.0D)) {
			this.frustumUpdatePosX = viewEntity.posX;
			this.frustumUpdatePosY = viewEntity.posY;
			this.frustumUpdatePosZ = viewEntity.posZ;
			this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
			this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
			this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
			this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
		}

		if (Config.isDynamicLights()) {
			DynamicLights.update(this);
		}

		this.theWorld.theProfiler.endStartSection("renderlistcamera");
		double d3 = viewEntity.lastTickPosX + ((viewEntity.posX - viewEntity.lastTickPosX) * partialTicks);
		double d4 = viewEntity.lastTickPosY + ((viewEntity.posY - viewEntity.lastTickPosY) * partialTicks);
		double d5 = viewEntity.lastTickPosZ + ((viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks);
		this.renderContainer.initialize(d3, d4, d5);
		this.theWorld.theProfiler.endStartSection("cull");

		if (this.debugFixedClippingHelper != null) {
			Frustum frustum = new Frustum(this.debugFixedClippingHelper);
			frustum.setPosition(this.debugTerrainFrustumPosition.x, this.debugTerrainFrustumPosition.y, this.debugTerrainFrustumPosition.z);
			camera = frustum;
		}

		this.mc.mcProfiler.endStartSection("culling");
		BlockPos blockpos2 = new BlockPos(d3, d4 + viewEntity.getEyeHeight(), d5);
		RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos2);
		new BlockPos(MathHelper.floor_double(d3 / 16.0D) * 16, MathHelper.floor_double(d4 / 16.0D) * 16, MathHelper.floor_double(d5 / 16.0D) * 16);
		this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || (viewEntity.posX != this.lastViewEntityX) || (viewEntity.posY != this.lastViewEntityY)
				|| (viewEntity.posZ != this.lastViewEntityZ) || (viewEntity.rotationPitch != this.lastViewEntityPitch) || (viewEntity.rotationYaw != this.lastViewEntityYaw);
		this.lastViewEntityX = viewEntity.posX;
		this.lastViewEntityY = viewEntity.posY;
		this.lastViewEntityZ = viewEntity.posZ;
		this.lastViewEntityPitch = viewEntity.rotationPitch;
		this.lastViewEntityYaw = viewEntity.rotationYaw;
		boolean flag = this.debugFixedClippingHelper != null;
		this.mc.mcProfiler.endStartSection("update");
		Lagometer.timerVisibility.start();
		int i = this.getCountLoadedChunks();

		if (i != this.countLoadedChunksPrev) {
			this.countLoadedChunksPrev = i;
			this.displayListEntitiesDirty = true;
		}

		if (Shaders.isShadowPass) {
			this.renderInfos = this.renderInfosShadow;
			this.renderInfosEntities = this.renderInfosEntitiesShadow;
			this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;

			if (!flag && this.displayListEntitiesDirty) {
				this.renderInfos.clear();
				this.renderInfosEntities.clear();
				this.renderInfosTileEntities.clear();
				RenderInfoLazy renderinfolazy = new RenderInfoLazy();

				for (RenderChunk renderchunk1 : this.viewFrustum.renderChunks) {
					renderinfolazy.setRenderChunk(renderchunk1);

					if (!renderchunk1.compiledChunk.isEmpty() || renderchunk1.isNeedsUpdate()) {
						this.renderInfos.add(renderinfolazy.getRenderInfo());
					}

					BlockPos blockpos = renderchunk1.getPosition();

					if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(blockpos))) {
						this.renderInfosEntities.add(renderinfolazy.getRenderInfo());
					}

					if (renderchunk1.getCompiledChunk().getTileEntities().size() > 0) {
						this.renderInfosTileEntities.add(renderinfolazy.getRenderInfo());
					}
				}
			}
		} else {
			this.renderInfos = this.renderInfosNormal;
			this.renderInfosEntities = this.renderInfosEntitiesNormal;
			this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
		}

		if (!flag && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
			this.displayListEntitiesDirty = false;

			for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 : this.renderInfos) {
				this.freeRenderInformation(renderglobal$containerlocalrenderinformation1);
			}

			this.renderInfos.clear();
			this.renderInfosEntities.clear();
			this.renderInfosTileEntities.clear();
			this.visibilityDeque.clear();
			Deque deque = this.visibilityDeque;
			Entity.setRenderDistanceWeight(MathHelper.clamp_double(this.mc.gameSettings.renderDistanceChunks / 8.0D, 1.0D, 2.5D));
			boolean flag2 = this.mc.renderChunksMany;

			if (renderchunk != null) {
				boolean flag3 = false;
				RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 = new RenderGlobal.ContainerLocalRenderInformation(renderchunk, (EnumFacing) null, 0);
				Set set1 = SET_ALL_FACINGS;

				if (set1.size() == 1) {
					Vector3f vector3f = this.getViewVector(viewEntity, partialTicks);
					EnumFacing enumfacing = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
					set1.remove(enumfacing);
				}

				if (set1.isEmpty()) {
					flag3 = true;
				}

				if (flag3 && !playerSpectator) {
					this.renderInfos.add(renderglobal$containerlocalrenderinformation3);
				} else {
					if (playerSpectator && this.theWorld.getBlockState(blockpos2).isOpaqueCube()) {
						flag2 = false;
					}

					renderchunk.setFrameIndex(frameCount);
					deque.add(renderglobal$containerlocalrenderinformation3);
				}
			} else {
				int k = blockpos2.getY() > 0 ? 248 : 8;

				for (int l = -this.renderDistanceChunks; l <= this.renderDistanceChunks; ++l) {
					for (int j1 = -this.renderDistanceChunks; j1 <= this.renderDistanceChunks; ++j1) {
						RenderChunk renderchunk4 = this.viewFrustum.getRenderChunk(new BlockPos((l << 4) + 8, k, (j1 << 4) + 8));

						if ((renderchunk4 != null) && camera.isBoundingBoxInFrustum(renderchunk4.boundingBox)) {
							renderchunk4.setFrameIndex(frameCount);
							deque.add(new RenderGlobal.ContainerLocalRenderInformation(renderchunk4, (EnumFacing) null, 0));
						}
					}
				}
			}

			this.mc.mcProfiler.startSection("iteration");
			EnumFacing[] aenumfacing = EnumFacing.VALUES;
			int i1 = aenumfacing.length;

			while (!deque.isEmpty()) {
				RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation4 = (RenderGlobal.ContainerLocalRenderInformation) deque.poll();
				RenderChunk renderchunk5 = renderglobal$containerlocalrenderinformation4.renderChunk;
				EnumFacing enumfacing2 = renderglobal$containerlocalrenderinformation4.facing;
				BlockPos blockpos1 = renderchunk5.getPosition();
				boolean flag1 = false;

				if (!renderchunk5.compiledChunk.isEmpty() || renderchunk5.isNeedsUpdate()) {
					this.renderInfos.add(renderglobal$containerlocalrenderinformation4);
					flag1 = true;
				}

				if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(blockpos1))) {
					this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation4);
					flag1 = true;
				}

				if (renderchunk5.getCompiledChunk().getTileEntities().size() > 0) {
					this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation4);
					flag1 = true;
				}

				for (int j = 0; j < i1; ++j) {
					EnumFacing enumfacing1 = aenumfacing[j];

					if ((!flag2 || !renderglobal$containerlocalrenderinformation4.func_189560_a(enumfacing1.getOpposite()))
							&& (!flag2 || (enumfacing2 == null) || renderchunk5.getCompiledChunk().isVisible(enumfacing2.getOpposite(), enumfacing1))) {
						RenderChunk renderchunk2 = this.getRenderChunkOffset(blockpos2, renderchunk5, enumfacing1);

						 //TODO: Direkt: Camera fix thing
                        if (renderchunk2 != null && renderchunk2.setFrameIndex(frameCount) && ((!Direkt.getInstance().getModuleManager().getModule(NoRender.class).isRunning()) || ((ICamera) camera).isBoundingBoxInFrustum(renderchunk2.boundingBox))) {
							RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = this.allocateRenderInformation(renderchunk2, enumfacing1,
									renderglobal$containerlocalrenderinformation4.counter + 1);
							renderglobal$containerlocalrenderinformation.func_189561_a(renderglobal$containerlocalrenderinformation4.setFacing, enumfacing1);
							deque.add(renderglobal$containerlocalrenderinformation);
						}
					}
				}

				if (!flag1) {
					this.freeRenderInformation(renderglobal$containerlocalrenderinformation4);
				}
			}

			this.mc.mcProfiler.endSection();
		}

		this.mc.mcProfiler.endStartSection("captureFrustum");

		if (this.debugFixTerrainFrustum) {
			this.fixTerrainFrustum(d3, d4, d5);
			this.debugFixTerrainFrustum = false;
		}

		Lagometer.timerVisibility.end();

		if (Shaders.isShadowPass) {
			Shaders.mcProfilerEndSection();
		} else {
			this.mc.mcProfiler.endStartSection("rebuildNear");
			Set<RenderChunk> set = this.chunksToUpdate;
			this.chunksToUpdate = Sets.<RenderChunk> newLinkedHashSet();
			Lagometer.timerChunkUpdate.start();

			for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 : this.renderInfos) {
				RenderChunk renderchunk3 = renderglobal$containerlocalrenderinformation2.renderChunk;

				if (renderchunk3.isNeedsUpdate() || set.contains(renderchunk3)) {
					this.displayListEntitiesDirty = true;
					BlockPos blockpos3 = renderchunk3.getPosition();
					boolean flag4 = blockpos2.distanceSq(blockpos3.getX() + 8, blockpos3.getY() + 8, blockpos3.getZ() + 8) < 768.0D;

					if (!flag4) {
						this.chunksToUpdate.add(renderchunk3);
					} else if (!renderchunk3.isPlayerUpdate()) {
						this.chunksToUpdateForced.add(renderchunk3);
					} else {
						this.mc.mcProfiler.startSection("build near");
						this.renderDispatcher.updateChunkNow(renderchunk3);
						renderchunk3.clearNeedsUpdate();
						this.mc.mcProfiler.endSection();
					}
				}
			}

			Lagometer.timerChunkUpdate.end();
			this.chunksToUpdate.addAll(set);
			this.mc.mcProfiler.endSection();
		}
	}

	private Set<EnumFacing> getVisibleFacings(BlockPos pos) {
		VisGraph visgraph = new VisGraph();
		BlockPos blockpos = new BlockPos((pos.getX() >> 4) << 4, (pos.getY() >> 4) << 4, (pos.getZ() >> 4) << 4);
		Chunk chunk = this.theWorld.getChunkFromBlockCoords(blockpos);

		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos.add(15, 15, 15))) {
			if (chunk.getBlockState(blockpos$mutableblockpos).isOpaqueCube()) {
				visgraph.setOpaqueCube(blockpos$mutableblockpos);
			}
		}
		return visgraph.getVisibleFacings(pos);
	}

	@Nullable

	/**
	 * Returns RenderChunk offset from given RenderChunk in given direction, or null if it can't be seen by player at given BlockPos.
	 */
	private RenderChunk getRenderChunkOffset(BlockPos playerPos, RenderChunk renderChunkBase, EnumFacing facing) {
		BlockPos blockpos = renderChunkBase.getBlockPosOffset16(facing);

		if ((blockpos.getY() >= 0) && (blockpos.getY() < 256)) {
			int i = MathHelper.abs_int(playerPos.getX() - blockpos.getX());
			int j = MathHelper.abs_int(playerPos.getZ() - blockpos.getZ());

			if (Config.isFogOff()) {
				if ((i > this.renderDistance) || (j > this.renderDistance)) { return null; }
			} else {
				int k = (i * i) + (j * j);

				if (k > this.renderDistanceSq) { return null; }
			}

			return this.viewFrustum.getRenderChunk(blockpos);
		} else {
			return null;
		}
	}

	private void fixTerrainFrustum(double x, double y, double z) {
		this.debugFixedClippingHelper = new ClippingHelperImpl();
		((ClippingHelperImpl) this.debugFixedClippingHelper).init();
		Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
		matrix4f.transpose();
		Matrix4f matrix4f1 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
		matrix4f1.transpose();
		Matrix4f matrix4f2 = new Matrix4f();
		org.lwjgl.util.vector.Matrix4f.mul(matrix4f1, matrix4f, matrix4f2);
		matrix4f2.invert();
		this.debugTerrainFrustumPosition.x = x;
		this.debugTerrainFrustumPosition.y = y;
		this.debugTerrainFrustumPosition.z = z;
		this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
		this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
		this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

		for (int i = 0; i < 8; ++i) {
			org.lwjgl.util.vector.Matrix4f.transform(matrix4f2, this.debugTerrainMatrix[i], this.debugTerrainMatrix[i]);
			this.debugTerrainMatrix[i].x /= this.debugTerrainMatrix[i].w;
			this.debugTerrainMatrix[i].y /= this.debugTerrainMatrix[i].w;
			this.debugTerrainMatrix[i].z /= this.debugTerrainMatrix[i].w;
			this.debugTerrainMatrix[i].w = 1.0F;
		}
	}

	protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
		float f = (float) (entityIn.prevRotationPitch + ((entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks));
		float f1 = (float) (entityIn.prevRotationYaw + ((entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks));

		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
			f += 180.0F;
		}

		float f2 = MathHelper.cos((-f1 * 0.017453292F) - (float) Math.PI);
		float f3 = MathHelper.sin((-f1 * 0.017453292F) - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		return new Vector3f(f3 * f4, f5, f2 * f4);
	}

	public int renderBlockLayer(BlockRenderLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
		RenderHelper.disableStandardItemLighting();

		if (blockLayerIn == BlockRenderLayer.TRANSLUCENT) {
			this.mc.mcProfiler.startSection("translucent_sort");
			double d0 = entityIn.posX - this.prevRenderSortX;
			double d1 = entityIn.posY - this.prevRenderSortY;
			double d2 = entityIn.posZ - this.prevRenderSortZ;

			if (((d0 * d0) + (d1 * d1) + (d2 * d2)) > 1.0D) {
				this.prevRenderSortX = entityIn.posX;
				this.prevRenderSortY = entityIn.posY;
				this.prevRenderSortZ = entityIn.posZ;
				int k = 0;
				this.chunksToResortTransparency.clear();

				for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
					if (renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) && (k++ < 15)) {
						this.chunksToResortTransparency.add(renderglobal$containerlocalrenderinformation.renderChunk);
					}
				}
			}

			this.mc.mcProfiler.endSection();
		}

		this.mc.mcProfiler.startSection("filterempty");
		int l = 0;
		boolean flag = blockLayerIn == BlockRenderLayer.TRANSLUCENT;
		int i1 = flag ? this.renderInfos.size() - 1 : 0;
		int i = flag ? -1 : this.renderInfos.size();
		int j1 = flag ? -1 : 1;

		for (int j = i1; j != i; j += j1) {
			RenderChunk renderchunk = this.renderInfos.get(j).renderChunk;

			if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) {
				++l;
				this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
			}
		}

		if (l == 0) {
			this.mc.mcProfiler.endSection();
			return l;
		} else {
			if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
				GlStateManager.disableFog();
			}

			this.mc.mcProfiler.endStartSection("render_" + blockLayerIn);
			this.renderBlockLayer(blockLayerIn);
			this.mc.mcProfiler.endSection();
			return l;
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void renderBlockLayer(BlockRenderLayer blockLayerIn) {
		this.mc.entityRenderer.enableLightmap();

		if (OpenGlHelper.useVbo()) {
			GlStateManager.glEnableClientState(32884);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.glEnableClientState(32888);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.glEnableClientState(32888);
			OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.glEnableClientState(32886);
		}

		if (Config.isShaders()) {
			ShadersRender.preRenderChunkLayer();
		}

		this.renderContainer.renderChunkLayer(blockLayerIn);

		if (Config.isShaders()) {
			ShadersRender.postRenderChunkLayer();
		}

		if (OpenGlHelper.useVbo()) {
			for (VertexFormatElement vertexformatelement : DefaultVertexFormats.BLOCK.getElements()) {
				VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
				int i = vertexformatelement.getIndex();

				switch (vertexformatelement$enumusage) {
				case POSITION:
					GlStateManager.glDisableClientState(32884);
					break;

				case UV:
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + i);
					GlStateManager.glDisableClientState(32888);
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
					break;

				case COLOR:
					GlStateManager.glDisableClientState(32886);
					GlStateManager.resetColor();
				}
			}
		}

		this.mc.entityRenderer.disableLightmap();
	}

	private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iteratorIn) {
		while (iteratorIn.hasNext()) {
			DestroyBlockProgress destroyblockprogress = iteratorIn.next();
			int i = destroyblockprogress.getCreationCloudUpdateTick();

			if ((this.cloudTickCounter - i) > 400) {
				iteratorIn.remove();
			}
		}
	}

	public void updateClouds() {
		if (Config.isShaders() && Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
			Shaders.uninit();
			Shaders.loadShaderPack();
		}

		++this.cloudTickCounter;

		if ((this.cloudTickCounter % 20) == 0) {
			this.cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
		}

		if (!this.setLightUpdates.isEmpty() && !this.renderDispatcher.hasNoFreeRenderBuilders() && this.chunksToUpdate.isEmpty()) {
			Iterator<BlockPos> iterator = this.setLightUpdates.iterator();

			while (iterator.hasNext()) {
				BlockPos blockpos = iterator.next();
				iterator.remove();
				int i = blockpos.getX();
				int j = blockpos.getY();
				int k = blockpos.getZ();
				this.markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1, false);
			}
		}
	}

	private void renderSkyEnd() {
		if (Config.isSkyEnabled()) {
			GlStateManager.disableFog();
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.depthMask(false);
			this.renderEngine.bindTexture(END_SKY_TEXTURES);
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tessellator.getBuffer();

			for (int i = 0; i < 6; ++i) {
				GlStateManager.pushMatrix();

				if (i == 1) {
					GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				}

				if (i == 2) {
					GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				}

				if (i == 3) {
					GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
				}

				if (i == 4) {
					GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				}

				if (i == 5) {
					GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
				}

				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				vertexbuffer.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(40, 40, 40, 255).endVertex();
				vertexbuffer.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(40, 40, 40, 255).endVertex();
				vertexbuffer.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(40, 40, 40, 255).endVertex();
				vertexbuffer.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(40, 40, 40, 255).endVertex();
				tessellator.draw();
				GlStateManager.popMatrix();
			}

			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
		}
	}

	public void renderSky(float partialTicks, int pass) {
		if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
			WorldProvider worldprovider = this.mc.theWorld.provider;
			Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);

			if (object != null) {
				Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
				return;
			}
		}

		if (this.mc.theWorld.provider.getDimensionType() == DimensionType.THE_END) {
			this.renderSkyEnd();
		} else if (this.mc.theWorld.provider.isSurfaceWorld()) {
			GlStateManager.disableTexture2D();
			boolean flag = Config.isShaders();

			if (flag) {
				Shaders.disableTexture2D();
			}

			Vec3d vec3d = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
			vec3d = CustomColors.getSkyColor(vec3d, this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0D, this.mc.getRenderViewEntity().posZ);

			if (flag) {
				Shaders.setSkyColor(vec3d);
			}

			float f = (float) vec3d.xCoord;
			float f1 = (float) vec3d.yCoord;
			float f2 = (float) vec3d.zCoord;

			if (pass != 2) {
				float f3 = ((f * 30.0F) + (f1 * 59.0F) + (f2 * 11.0F)) / 100.0F;
				float f4 = ((f * 30.0F) + (f1 * 70.0F)) / 100.0F;
				float f5 = ((f * 30.0F) + (f2 * 70.0F)) / 100.0F;
				f = f3;
				f1 = f4;
				f2 = f5;
			}

			GlStateManager.color(f, f1, f2);
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tessellator.getBuffer();
			GlStateManager.depthMask(false);
			GlStateManager.enableFog();

			if (flag) {
				Shaders.enableFog();
			}

			GlStateManager.color(f, f1, f2);

			if (flag) {
				Shaders.preSkyList();
			}

			if (Config.isSkyEnabled()) {
				if (this.vboEnabled) {
					this.skyVBO.bindBuffer();
					GlStateManager.glEnableClientState(32884);
					GlStateManager.glVertexPointer(3, 5126, 12, 0);
					this.skyVBO.drawArrays(7);
					this.skyVBO.unbindBuffer();
					GlStateManager.glDisableClientState(32884);
				} else {
					GlStateManager.callList(this.glSkyList);
				}
			}

			GlStateManager.disableFog();

			if (flag) {
				Shaders.disableFog();
			}

			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderHelper.disableStandardItemLighting();
			float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);

			if ((afloat != null) && Config.isSunMoonEnabled()) {
				GlStateManager.disableTexture2D();

				if (flag) {
					Shaders.disableTexture2D();
				}

				GlStateManager.shadeModel(7425);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				float f6 = afloat[0];
				float f7 = afloat[1];
				float f8 = afloat[2];

				if (pass != 2) {
					float f9 = ((f6 * 30.0F) + (f7 * 59.0F) + (f8 * 11.0F)) / 100.0F;
					float f10 = ((f6 * 30.0F) + (f7 * 70.0F)) / 100.0F;
					float f11 = ((f6 * 30.0F) + (f8 * 70.0F)) / 100.0F;
					f6 = f9;
					f7 = f10;
					f8 = f11;
				}

				vertexbuffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
				vertexbuffer.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
				int j = 16;

				for (int l = 0; l <= 16; ++l) {
					float f18 = (l * ((float) Math.PI * 2F)) / 16.0F;
					float f12 = MathHelper.sin(f18);
					float f13 = MathHelper.cos(f18);
					vertexbuffer.pos(f12 * 120.0F, f13 * 120.0F, -f13 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
				}

				tessellator.draw();
				GlStateManager.popMatrix();
				GlStateManager.shadeModel(7424);
			}

			GlStateManager.enableTexture2D();

			if (flag) {
				Shaders.enableTexture2D();
			}

			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.pushMatrix();
			float f15 = 1.0F - this.theWorld.getRainStrength(partialTicks);
			GlStateManager.color(1.0F, 1.0F, 1.0F, f15);
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
			CustomSky.renderSky(this.theWorld, this.renderEngine, this.theWorld.getCelestialAngle(partialTicks), f15);

			if (flag) {
				Shaders.preCelestialRotate();
			}

			GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);

			if (flag) {
				Shaders.postCelestialRotate();
			}

			float f16 = 30.0F;

			if (Config.isSunTexture()) {
				this.renderEngine.bindTexture(SUN_TEXTURES);
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
				vertexbuffer.pos((-f16), 100.0D, (-f16)).tex(0.0D, 0.0D).endVertex();
				vertexbuffer.pos(f16, 100.0D, (-f16)).tex(1.0D, 0.0D).endVertex();
				vertexbuffer.pos(f16, 100.0D, f16).tex(1.0D, 1.0D).endVertex();
				vertexbuffer.pos((-f16), 100.0D, f16).tex(0.0D, 1.0D).endVertex();
				tessellator.draw();
			}

			f16 = 20.0F;

			if (Config.isMoonTexture()) {
				this.renderEngine.bindTexture(MOON_PHASES_TEXTURES);
				int i = this.theWorld.getMoonPhase();
				int k = i % 4;
				int i1 = (i / 4) % 2;
				float f19 = (k + 0) / 4.0F;
				float f21 = (i1 + 0) / 2.0F;
				float f23 = (k + 1) / 4.0F;
				float f14 = (i1 + 1) / 2.0F;
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
				vertexbuffer.pos((-f16), -100.0D, f16).tex(f23, f14).endVertex();
				vertexbuffer.pos(f16, -100.0D, f16).tex(f19, f14).endVertex();
				vertexbuffer.pos(f16, -100.0D, (-f16)).tex(f19, f21).endVertex();
				vertexbuffer.pos((-f16), -100.0D, (-f16)).tex(f23, f21).endVertex();
				tessellator.draw();
			}

			GlStateManager.disableTexture2D();

			if (flag) {
				Shaders.disableTexture2D();
			}

			float f17 = this.theWorld.getStarBrightness(partialTicks) * f15;

			if ((f17 > 0.0F) && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
				GlStateManager.color(f17, f17, f17, f17);

				if (this.vboEnabled) {
					this.starVBO.bindBuffer();
					GlStateManager.glEnableClientState(32884);
					GlStateManager.glVertexPointer(3, 5126, 12, 0);
					this.starVBO.drawArrays(7);
					this.starVBO.unbindBuffer();
					GlStateManager.glDisableClientState(32884);
				} else {
					GlStateManager.callList(this.starGLCallList);
				}
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.enableFog();

			if (flag) {
				Shaders.enableFog();
			}

			GlStateManager.popMatrix();
			GlStateManager.disableTexture2D();

			if (flag) {
				Shaders.disableTexture2D();
			}

			GlStateManager.color(0.0F, 0.0F, 0.0F);
			double d0 = this.mc.thePlayer.getPositionEyes(partialTicks).yCoord - this.theWorld.getHorizon();

			if (d0 < 0.0D) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(0.0F, 12.0F, 0.0F);

				if (this.vboEnabled) {
					this.sky2VBO.bindBuffer();
					GlStateManager.glEnableClientState(32884);
					GlStateManager.glVertexPointer(3, 5126, 12, 0);
					this.sky2VBO.drawArrays(7);
					this.sky2VBO.unbindBuffer();
					GlStateManager.glDisableClientState(32884);
				} else {
					GlStateManager.callList(this.glSkyList2);
				}

				GlStateManager.popMatrix();
				float f20 = 1.0F;
				float f22 = -((float) (d0 + 65.0D));
				float f24 = -1.0F;
				vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
				vertexbuffer.pos(-1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				vertexbuffer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
			}

			if (this.theWorld.provider.isSkyColored()) {
				GlStateManager.color((f * 0.2F) + 0.04F, (f1 * 0.2F) + 0.04F, (f2 * 0.6F) + 0.1F);
			} else {
				GlStateManager.color(f, f1, f2);
			}

			if (this.mc.gameSettings.renderDistanceChunks <= 4) {
				GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, -((float) (d0 - 16.0D)), 0.0F);

			if (Config.isSkyEnabled()) {
				GlStateManager.callList(this.glSkyList2);
			}

			GlStateManager.popMatrix();
			GlStateManager.enableTexture2D();

			if (flag) {
				Shaders.enableTexture2D();
			}

			GlStateManager.depthMask(true);
		}
	}

	public void renderClouds(float partialTicks, int pass) {
		if (!Config.isCloudsOff()) {
			if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
				WorldProvider worldprovider = this.mc.theWorld.provider;
				Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);

				if (object != null) {
					Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
					return;
				}
			}

			if (this.mc.theWorld.provider.isSurfaceWorld()) {
				if (Config.isShaders()) {
					Shaders.beginClouds();
				}

				if (Config.isCloudsFancy()) {
					this.renderCloudsFancy(partialTicks, pass);
				} else {
					this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, partialTicks);
					partialTicks = 0.0F;
					GlStateManager.disableCull();
					Entity entity = this.mc.getRenderViewEntity();
					float f = (float) (entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partialTicks));
					int i = 32;
					int j = 8;
					Tessellator tessellator = Tessellator.getInstance();
					VertexBuffer vertexbuffer = tessellator.getBuffer();
					this.renderEngine.bindTexture(CLOUDS_TEXTURES);
					GlStateManager.enableBlend();
					GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);

					if (this.cloudRenderer.shouldUpdateGlList()) {
						this.cloudRenderer.startUpdateGlList();
						Vec3d vec3d = this.theWorld.getCloudColour(partialTicks);
						float f1 = (float) vec3d.xCoord;
						float f2 = (float) vec3d.yCoord;
						float f3 = (float) vec3d.zCoord;

						if (pass != 2) {
							float f4 = ((f1 * 30.0F) + (f2 * 59.0F) + (f3 * 11.0F)) / 100.0F;
							float f5 = ((f1 * 30.0F) + (f2 * 70.0F)) / 100.0F;
							float f6 = ((f1 * 30.0F) + (f3 * 70.0F)) / 100.0F;
							f1 = f4;
							f2 = f5;
							f3 = f6;
						}

						float f10 = 4.8828125E-4F;
						double d2 = this.cloudTickCounter + partialTicks;
						double d0 = entity.prevPosX + ((entity.posX - entity.prevPosX) * partialTicks) + (d2 * 0.029999999329447746D);
						double d1 = entity.prevPosZ + ((entity.posZ - entity.prevPosZ) * partialTicks);
						int k = MathHelper.floor_double(d0 / 2048.0D);
						int l = MathHelper.floor_double(d1 / 2048.0D);
						d0 = d0 - k * 2048;
						d1 = d1 - l * 2048;
						float f7 = (this.theWorld.provider.getCloudHeight() - f) + 0.33F;
						f7 = f7 + (this.mc.gameSettings.ofCloudsHeight * 128.0F);
						float f8 = (float) (d0 * 4.8828125E-4D);
						float f9 = (float) (d1 * 4.8828125E-4D);
						vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

						for (int i1 = -256; i1 < 256; i1 += 32) {
							for (int j1 = -256; j1 < 256; j1 += 32) {
								vertexbuffer.pos(i1 + 0, f7, j1 + 32).tex(((i1 + 0) * 4.8828125E-4F) + f8, ((j1 + 32) * 4.8828125E-4F) + f9).color(f1, f2, f3, 0.8F).endVertex();
								vertexbuffer.pos(i1 + 32, f7, j1 + 32).tex(((i1 + 32) * 4.8828125E-4F) + f8, ((j1 + 32) * 4.8828125E-4F) + f9).color(f1, f2, f3, 0.8F).endVertex();
								vertexbuffer.pos(i1 + 32, f7, j1 + 0).tex(((i1 + 32) * 4.8828125E-4F) + f8, ((j1 + 0) * 4.8828125E-4F) + f9).color(f1, f2, f3, 0.8F).endVertex();
								vertexbuffer.pos(i1 + 0, f7, j1 + 0).tex(((i1 + 0) * 4.8828125E-4F) + f8, ((j1 + 0) * 4.8828125E-4F) + f9).color(f1, f2, f3, 0.8F).endVertex();
							}
						}

						tessellator.draw();
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

	/**
	 * Checks if the given position is to be rendered with cloud fog
	 */
	public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
		return false;
	}

	private void renderCloudsFancy(float partialTicks, int pass) {
		this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks);
		partialTicks = 0.0F;
		GlStateManager.disableCull();
		Entity entity = this.mc.getRenderViewEntity();
		float f = (float) (entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partialTicks));
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		float f1 = 12.0F;
		float f2 = 4.0F;
		double d0 = this.cloudTickCounter + partialTicks;
		double d1 = (entity.prevPosX + ((entity.posX - entity.prevPosX) * partialTicks) + (d0 * 0.029999999329447746D)) / 12.0D;
		double d2 = ((entity.prevPosZ + ((entity.posZ - entity.prevPosZ) * partialTicks)) / 12.0D) + 0.33000001311302185D;
		float f3 = (this.theWorld.provider.getCloudHeight() - f) + 0.33F;
		f3 = f3 + (this.mc.gameSettings.ofCloudsHeight * 128.0F);
		int i = MathHelper.floor_double(d1 / 2048.0D);
		int j = MathHelper.floor_double(d2 / 2048.0D);
		d1 = d1 - i * 2048;
		d2 = d2 - j * 2048;
		this.renderEngine.bindTexture(CLOUDS_TEXTURES);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		Vec3d vec3d = this.theWorld.getCloudColour(partialTicks);
		float f4 = (float) vec3d.xCoord;
		float f5 = (float) vec3d.yCoord;
		float f6 = (float) vec3d.zCoord;

		if (pass != 2) {
			float f7 = ((f4 * 30.0F) + (f5 * 59.0F) + (f6 * 11.0F)) / 100.0F;
			float f8 = ((f4 * 30.0F) + (f5 * 70.0F)) / 100.0F;
			float f9 = ((f4 * 30.0F) + (f6 * 70.0F)) / 100.0F;
			f4 = f7;
			f5 = f8;
			f6 = f9;
		}

		float f26 = f4 * 0.9F;
		float f27 = f5 * 0.9F;
		float f28 = f6 * 0.9F;
		float f10 = f4 * 0.7F;
		float f11 = f5 * 0.7F;
		float f12 = f6 * 0.7F;
		float f13 = f4 * 0.8F;
		float f14 = f5 * 0.8F;
		float f15 = f6 * 0.8F;
		float f16 = 0.00390625F;
		float f17 = MathHelper.floor_double(d1) * 0.00390625F;
		float f18 = MathHelper.floor_double(d2) * 0.00390625F;
		float f19 = (float) (d1 - MathHelper.floor_double(d1));
		float f20 = (float) (d2 - MathHelper.floor_double(d2));
		int k = 8;
		int l = 4;
		float f21 = 9.765625E-4F;
		GlStateManager.scale(12.0F, 1.0F, 12.0F);

		for (int i1 = 0; i1 < 2; ++i1) {
			if (i1 == 0) {
				GlStateManager.colorMask(false, false, false, false);
			} else {
				switch (pass) {
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

			for (int l1 = -3; l1 <= 4; ++l1) {
				for (int j1 = -3; j1 <= 4; ++j1) {
					vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
					float f22 = l1 * 8;
					float f23 = j1 * 8;
					float f24 = f22 - f19;
					float f25 = f23 - f20;

					if (f3 > -5.0F) {
						vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, f25 + 8.0F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18).color(f10, f11, f12, 0.8F)
								.normal(0.0F, -1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, f25 + 8.0F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18).color(f10, f11, f12, 0.8F)
								.normal(0.0F, -1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, f25 + 0.0F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18).color(f10, f11, f12, 0.8F)
								.normal(0.0F, -1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, f25 + 0.0F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18).color(f10, f11, f12, 0.8F)
								.normal(0.0F, -1.0F, 0.0F).endVertex();
					}

					if (f3 <= 5.0F) {
						vertexbuffer.pos(f24 + 0.0F, (f3 + 4.0F) - 9.765625E-4F, f25 + 8.0F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18).color(f4, f5, f6, 0.8F)
								.normal(0.0F, 1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, (f3 + 4.0F) - 9.765625E-4F, f25 + 8.0F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18).color(f4, f5, f6, 0.8F)
								.normal(0.0F, 1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 8.0F, (f3 + 4.0F) - 9.765625E-4F, f25 + 0.0F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18).color(f4, f5, f6, 0.8F)
								.normal(0.0F, 1.0F, 0.0F).endVertex();
						vertexbuffer.pos(f24 + 0.0F, (f3 + 4.0F) - 9.765625E-4F, f25 + 0.0F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18).color(f4, f5, f6, 0.8F)
								.normal(0.0F, 1.0F, 0.0F).endVertex();
					}

					if (l1 > -1) {
						for (int k1 = 0; k1 < 8; ++k1) {
							vertexbuffer.pos(f24 + k1 + 0.0F, f3 + 0.0F, f25 + 8.0F).tex(((f22 + k1 + 0.5F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18).color(f26, f27, f28, 0.8F)
									.normal(-1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + k1 + 0.0F, f3 + 4.0F, f25 + 8.0F).tex(((f22 + k1 + 0.5F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18).color(f26, f27, f28, 0.8F)
									.normal(-1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + k1 + 0.0F, f3 + 4.0F, f25 + 0.0F).tex(((f22 + k1 + 0.5F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18).color(f26, f27, f28, 0.8F)
									.normal(-1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos(f24 + k1 + 0.0F, f3 + 0.0F, f25 + 0.0F).tex(((f22 + k1 + 0.5F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18).color(f26, f27, f28, 0.8F)
									.normal(-1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (l1 <= 1) {
						for (int i2 = 0; i2 < 8; ++i2) {
							vertexbuffer.pos((f24 + i2 + 1.0F) - 9.765625E-4F, f3 + 0.0F, f25 + 8.0F).tex(((f22 + i2 + 0.5F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18)
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos((f24 + i2 + 1.0F) - 9.765625E-4F, f3 + 4.0F, f25 + 8.0F).tex(((f22 + i2 + 0.5F) * 0.00390625F) + f17, ((f23 + 8.0F) * 0.00390625F) + f18)
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos((f24 + i2 + 1.0F) - 9.765625E-4F, f3 + 4.0F, f25 + 0.0F).tex(((f22 + i2 + 0.5F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18)
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							vertexbuffer.pos((f24 + i2 + 1.0F) - 9.765625E-4F, f3 + 0.0F, f25 + 0.0F).tex(((f22 + i2 + 0.5F) * 0.00390625F) + f17, ((f23 + 0.0F) * 0.00390625F) + f18)
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (j1 > -1) {
						for (int j2 = 0; j2 < 8; ++j2) {
							vertexbuffer.pos(f24 + 0.0F, f3 + 4.0F, f25 + j2 + 0.0F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + j2 + 0.5F) * 0.00390625F) + f18).color(f13, f14, f15, 0.8F)
									.normal(0.0F, 0.0F, -1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 4.0F, f25 + j2 + 0.0F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + j2 + 0.5F) * 0.00390625F) + f18).color(f13, f14, f15, 0.8F)
									.normal(0.0F, 0.0F, -1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, f25 + j2 + 0.0F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + j2 + 0.5F) * 0.00390625F) + f18).color(f13, f14, f15, 0.8F)
									.normal(0.0F, 0.0F, -1.0F).endVertex();
							vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, f25 + j2 + 0.0F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + j2 + 0.5F) * 0.00390625F) + f18).color(f13, f14, f15, 0.8F)
									.normal(0.0F, 0.0F, -1.0F).endVertex();
						}
					}

					if (j1 <= 1) {
						for (int k2 = 0; k2 < 8; ++k2) {
							vertexbuffer.pos(f24 + 0.0F, f3 + 4.0F, (f25 + k2 + 1.0F) - 9.765625E-4F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + k2 + 0.5F) * 0.00390625F) + f18)
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 4.0F, (f25 + k2 + 1.0F) - 9.765625E-4F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + k2 + 0.5F) * 0.00390625F) + f18)
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							vertexbuffer.pos(f24 + 8.0F, f3 + 0.0F, (f25 + k2 + 1.0F) - 9.765625E-4F).tex(((f22 + 8.0F) * 0.00390625F) + f17, ((f23 + k2 + 0.5F) * 0.00390625F) + f18)
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							vertexbuffer.pos(f24 + 0.0F, f3 + 0.0F, (f25 + k2 + 1.0F) - 9.765625E-4F).tex(((f22 + 0.0F) * 0.00390625F) + f17, ((f23 + k2 + 0.5F) * 0.00390625F) + f18)
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
						}
					}

					tessellator.draw();
				}
			}

			this.cloudRenderer.endUpdateGlList();
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
	}

	public void updateChunks(long finishTimeNano) {
		finishTimeNano = (long) (finishTimeNano + 1.0E8D);
		this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(finishTimeNano);

		if (this.chunksToUpdateForced.size() > 0) {
			Iterator iterator = this.chunksToUpdateForced.iterator();

			while (iterator.hasNext()) {
				RenderChunk renderchunk = (RenderChunk) iterator.next();

				if (!this.renderDispatcher.updateChunkLater(renderchunk)) {
					break;
				}

				renderchunk.clearNeedsUpdate();
				iterator.remove();
				this.chunksToUpdate.remove(renderchunk);
				this.chunksToResortTransparency.remove(renderchunk);
			}
		}

		if (this.chunksToResortTransparency.size() > 0) {
			Iterator iterator2 = this.chunksToResortTransparency.iterator();

			if (iterator2.hasNext()) {
				RenderChunk renderchunk2 = (RenderChunk) iterator2.next();

				if (this.renderDispatcher.updateTransparencyLater(renderchunk2)) {
					iterator2.remove();
				}
			}
		}

		int j = 0;
		int k = Config.getUpdatesPerFrame();
		int i = k * 2;

		if (!this.chunksToUpdate.isEmpty()) {
			Iterator<RenderChunk> iterator1 = this.chunksToUpdate.iterator();

			while (iterator1.hasNext()) {
				RenderChunk renderchunk1 = iterator1.next();
				boolean flag;

				if (renderchunk1.isNeedsUpdateCustom()) {
					flag = this.renderDispatcher.updateChunkNow(renderchunk1);
				} else {
					flag = this.renderDispatcher.updateChunkLater(renderchunk1);
				}

				if (!flag) {
					break;
				}

				renderchunk1.clearNeedsUpdate();
				iterator1.remove();

				if (renderchunk1.getCompiledChunk().isEmpty() && (k < i)) {
					++k;
				}

				++j;

				if (j >= k) {
					break;
				}
			}
		}
	}

	public void renderWorldBorder(Entity entityIn, float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		WorldBorder worldborder = this.theWorld.getWorldBorder();
		double d0 = this.mc.gameSettings.renderDistanceChunks * 16;

		if ((entityIn.posX >= (worldborder.maxX() - d0)) || (entityIn.posX <= (worldborder.minX() + d0)) || (entityIn.posZ >= (worldborder.maxZ() - d0))
				|| (entityIn.posZ <= (worldborder.minZ() + d0))) {
			double d1 = 1.0D - (worldborder.getClosestDistance(entityIn) / d0);
			d1 = Math.pow(d1, 4.0D);
			double d2 = entityIn.lastTickPosX + ((entityIn.posX - entityIn.lastTickPosX) * partialTicks);
			double d3 = entityIn.lastTickPosY + ((entityIn.posY - entityIn.lastTickPosY) * partialTicks);
			double d4 = entityIn.lastTickPosZ + ((entityIn.posZ - entityIn.lastTickPosZ) * partialTicks);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			this.renderEngine.bindTexture(FORCEFIELD_TEXTURES);
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			int i = worldborder.getStatus().getID();
			float f = ((i >> 16) & 255) / 255.0F;
			float f1 = ((i >> 8) & 255) / 255.0F;
			float f2 = (i & 255) / 255.0F;
			GlStateManager.color(f, f1, f2, (float) d1);
			GlStateManager.doPolygonOffset(-3.0F, -3.0F);
			GlStateManager.enablePolygonOffset();
			GlStateManager.alphaFunc(516, 0.1F);
			GlStateManager.enableAlpha();
			GlStateManager.disableCull();
			float f3 = Minecraft.getSystemTime() % 3000L / 3000.0F;
			float f4 = 0.0F;
			float f5 = 0.0F;
			float f6 = 128.0F;
			vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
			vertexbuffer.setTranslation(-d2, -d3, -d4);
			double d5 = Math.max(MathHelper.floor_double(d4 - d0), worldborder.minZ());
			double d6 = Math.min(MathHelper.ceiling_double_int(d4 + d0), worldborder.maxZ());

			if (d2 > (worldborder.maxX() - d0)) {
				float f7 = 0.0F;

				for (double d7 = d5; d7 < d6; f7 += 0.5F) {
					double d8 = Math.min(1.0D, d6 - d7);
					float f8 = (float) d8 * 0.5F;
					vertexbuffer.pos(worldborder.maxX(), 256.0D, d7).tex(f3 + f7, f3 + 0.0F).endVertex();
					vertexbuffer.pos(worldborder.maxX(), 256.0D, d7 + d8).tex(f3 + f8 + f7, f3 + 0.0F).endVertex();
					vertexbuffer.pos(worldborder.maxX(), 0.0D, d7 + d8).tex(f3 + f8 + f7, f3 + 128.0F).endVertex();
					vertexbuffer.pos(worldborder.maxX(), 0.0D, d7).tex(f3 + f7, f3 + 128.0F).endVertex();
					++d7;
				}
			}

			if (d2 < (worldborder.minX() + d0)) {
				float f9 = 0.0F;

				for (double d9 = d5; d9 < d6; f9 += 0.5F) {
					double d12 = Math.min(1.0D, d6 - d9);
					float f12 = (float) d12 * 0.5F;
					vertexbuffer.pos(worldborder.minX(), 256.0D, d9).tex(f3 + f9, f3 + 0.0F).endVertex();
					vertexbuffer.pos(worldborder.minX(), 256.0D, d9 + d12).tex(f3 + f12 + f9, f3 + 0.0F).endVertex();
					vertexbuffer.pos(worldborder.minX(), 0.0D, d9 + d12).tex(f3 + f12 + f9, f3 + 128.0F).endVertex();
					vertexbuffer.pos(worldborder.minX(), 0.0D, d9).tex(f3 + f9, f3 + 128.0F).endVertex();
					++d9;
				}
			}

			d5 = Math.max(MathHelper.floor_double(d2 - d0), worldborder.minX());
			d6 = Math.min(MathHelper.ceiling_double_int(d2 + d0), worldborder.maxX());

			if (d4 > (worldborder.maxZ() - d0)) {
				float f10 = 0.0F;

				for (double d10 = d5; d10 < d6; f10 += 0.5F) {
					double d13 = Math.min(1.0D, d6 - d10);
					float f13 = (float) d13 * 0.5F;
					vertexbuffer.pos(d10, 256.0D, worldborder.maxZ()).tex(f3 + f10, f3 + 0.0F).endVertex();
					vertexbuffer.pos(d10 + d13, 256.0D, worldborder.maxZ()).tex(f3 + f13 + f10, f3 + 0.0F).endVertex();
					vertexbuffer.pos(d10 + d13, 0.0D, worldborder.maxZ()).tex(f3 + f13 + f10, f3 + 128.0F).endVertex();
					vertexbuffer.pos(d10, 0.0D, worldborder.maxZ()).tex(f3 + f10, f3 + 128.0F).endVertex();
					++d10;
				}
			}

			if (d4 < (worldborder.minZ() + d0)) {
				float f11 = 0.0F;

				for (double d11 = d5; d11 < d6; f11 += 0.5F) {
					double d14 = Math.min(1.0D, d6 - d11);
					float f14 = (float) d14 * 0.5F;
					vertexbuffer.pos(d11, 256.0D, worldborder.minZ()).tex(f3 + f11, f3 + 0.0F).endVertex();
					vertexbuffer.pos(d11 + d14, 256.0D, worldborder.minZ()).tex(f3 + f14 + f11, f3 + 0.0F).endVertex();
					vertexbuffer.pos(d11 + d14, 0.0D, worldborder.minZ()).tex(f3 + f14 + f11, f3 + 128.0F).endVertex();
					vertexbuffer.pos(d11, 0.0D, worldborder.minZ()).tex(f3 + f11, f3 + 128.0F).endVertex();
					++d11;
				}
			}

			tessellator.draw();
			vertexbuffer.setTranslation(0.0D, 0.0D, 0.0D);
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
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
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

	private void postRenderDamagedBlocks() {
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

	public void drawBlockDamageTexture(Tessellator tessellatorIn, VertexBuffer worldRendererIn, Entity entityIn, float partialTicks) {
		double d0 = entityIn.lastTickPosX + ((entityIn.posX - entityIn.lastTickPosX) * partialTicks);
		double d1 = entityIn.lastTickPosY + ((entityIn.posY - entityIn.lastTickPosY) * partialTicks);
		double d2 = entityIn.lastTickPosZ + ((entityIn.posZ - entityIn.lastTickPosZ) * partialTicks);

		if (!this.damagedBlocks.isEmpty()) {
			this.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			this.preRenderDamagedBlocks();
			worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
			worldRendererIn.setTranslation(-d0, -d1, -d2);
			worldRendererIn.noColor();
			Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();

			while (iterator.hasNext()) {
				DestroyBlockProgress destroyblockprogress = iterator.next();
				BlockPos blockpos = destroyblockprogress.getPosition();
				double d3 = blockpos.getX() - d0;
				double d4 = blockpos.getY() - d1;
				double d5 = blockpos.getZ() - d2;
				Block block = this.theWorld.getBlockState(blockpos).getBlock();
				boolean flag;

				if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
					boolean flag1 = (block instanceof BlockChest) || (block instanceof BlockEnderChest) || (block instanceof BlockSign) || (block instanceof BlockSkull);

					if (!flag1) {
						TileEntity tileentity = this.theWorld.getTileEntity(blockpos);

						if (tileentity != null) {
							flag1 = Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
						}
					}

					flag = !flag1;
				} else {
					flag = !(block instanceof BlockChest) && !(block instanceof BlockEnderChest) && !(block instanceof BlockSign) && !(block instanceof BlockSkull);
				}

				if (flag) {
					if (((d3 * d3) + (d4 * d4) + (d5 * d5)) > 1024.0D) {
						iterator.remove();
					} else {
						IBlockState iblockstate = this.theWorld.getBlockState(blockpos);

						if (iblockstate.getMaterial() != Material.AIR) {
							int i = destroyblockprogress.getPartialBlockDamage();
							TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[i];
							BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
							blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite, this.theWorld);
						}
					}
				}
			}

			tessellatorIn.draw();
			worldRendererIn.setTranslation(0.0D, 0.0D, 0.0D);
			this.postRenderDamagedBlocks();
		}
	}

	/**
	 * Draws the selection box for the player.
	 */
	public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
		if ((execute == 0) && (movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK)) {
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.glLineWidth(2.0F);
			GlStateManager.disableTexture2D();

			if (Config.isShaders()) {
				Shaders.disableTexture2D();
			}

			GlStateManager.depthMask(false);
			BlockPos blockpos = movingObjectPositionIn.getBlockPos();
			IBlockState iblockstate = this.theWorld.getBlockState(blockpos);

			if ((iblockstate.getMaterial() != Material.AIR) && this.theWorld.getWorldBorder().contains(blockpos)) {
				double d0 = player.lastTickPosX + ((player.posX - player.lastTickPosX) * partialTicks);
				double d1 = player.lastTickPosY + ((player.posY - player.lastTickPosY) * partialTicks);
				double d2 = player.lastTickPosZ + ((player.posZ - player.lastTickPosZ) * partialTicks);
				func_189697_a(iblockstate.getSelectedBoundingBox(this.theWorld, blockpos).expandXyz(0.0020000000949949026D).offset(-d0, -d1, -d2), 0.0F, 0.0F, 0.0F, 0.4F);
			}

			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();

			if (Config.isShaders()) {
				Shaders.enableTexture2D();
			}

			GlStateManager.disableBlend();
		}
	}

	public static void func_189697_a(AxisAlignedBB p_189697_0_, float p_189697_1_, float p_189697_2_, float p_189697_3_, float p_189697_4_) {
		func_189694_a(p_189697_0_.minX, p_189697_0_.minY, p_189697_0_.minZ, p_189697_0_.maxX, p_189697_0_.maxY, p_189697_0_.maxZ, p_189697_1_, p_189697_2_, p_189697_3_, p_189697_4_);
	}

	public static void func_189694_a(double p_189694_0_, double p_189694_2_, double p_189694_4_, double p_189694_6_, double p_189694_8_, double p_189694_10_, float p_189694_12_, float p_189694_13_,
			float p_189694_14_, float p_189694_15_) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		func_189698_a(vertexbuffer, p_189694_0_, p_189694_2_, p_189694_4_, p_189694_6_, p_189694_8_, p_189694_10_, p_189694_12_, p_189694_13_, p_189694_14_, p_189694_15_);
		tessellator.draw();
	}

	public static void func_189698_a(VertexBuffer p_189698_0_, double p_189698_1_, double p_189698_3_, double p_189698_5_, double p_189698_7_, double p_189698_9_, double p_189698_11_,
			float p_189698_13_, float p_189698_14_, float p_189698_15_, float p_189698_16_) {
		p_189698_0_.pos(p_189698_1_, p_189698_3_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_3_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_3_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_1_, p_189698_3_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_1_, p_189698_3_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_1_, p_189698_9_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_9_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_9_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_1_, p_189698_9_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_1_, p_189698_9_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, 0.0F).endVertex();
		p_189698_0_.pos(p_189698_1_, p_189698_9_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_1_, p_189698_3_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_3_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, 0.0F).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_9_, p_189698_11_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_9_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, 0.0F).endVertex();
		p_189698_0_.pos(p_189698_7_, p_189698_3_, p_189698_5_).color(p_189698_13_, p_189698_14_, p_189698_15_, p_189698_16_).endVertex();
	}

	public static void func_189696_b(AxisAlignedBB p_189696_0_, float p_189696_1_, float p_189696_2_, float p_189696_3_, float p_189696_4_) {
		func_189695_b(p_189696_0_.minX, p_189696_0_.minY, p_189696_0_.minZ, p_189696_0_.maxX, p_189696_0_.maxY, p_189696_0_.maxZ, p_189696_1_, p_189696_2_, p_189696_3_, p_189696_4_);
	}

	public static void func_189695_b(double p_189695_0_, double p_189695_2_, double p_189695_4_, double p_189695_6_, double p_189695_8_, double p_189695_10_, float p_189695_12_, float p_189695_13_,
			float p_189695_14_, float p_189695_15_) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(5, DefaultVertexFormats.POSITION_COLOR);
		func_189693_b(vertexbuffer, p_189695_0_, p_189695_2_, p_189695_4_, p_189695_6_, p_189695_8_, p_189695_10_, p_189695_12_, p_189695_13_, p_189695_14_, p_189695_15_);
		tessellator.draw();
	}

	public static void func_189693_b(VertexBuffer p_189693_0_, double p_189693_1_, double p_189693_3_, double p_189693_5_, double p_189693_7_, double p_189693_9_, double p_189693_11_,
			float p_189693_13_, float p_189693_14_, float p_189693_15_, float p_189693_16_) {
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
		p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
	}

	private void markBlocksForUpdate(int p_184385_1_, int p_184385_2_, int p_184385_3_, int p_184385_4_, int p_184385_5_, int p_184385_6_, boolean p_184385_7_) {
		this.viewFrustum.markBlocksForUpdate(p_184385_1_, p_184385_2_, p_184385_3_, p_184385_4_, p_184385_5_, p_184385_6_, p_184385_7_);
	}

	@Override
	public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		this.markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1, (flags & 8) != 0);
	}

	@Override
	public void notifyLightSet(BlockPos pos) {
		this.setLightUpdates.add(pos.toImmutable());
	}

	/**
	 * On the client, re-renders all blocks in this range, inclusive. On the server, does nothing.
	 */
	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
		this.markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1, false);
	}

	@Override
	public void playRecord(@Nullable SoundEvent soundIn, BlockPos pos) {
		ISound isound = this.mapSoundPositions.get(pos);

		if (isound != null) {
			this.mc.getSoundHandler().stopSound(isound);
			this.mapSoundPositions.remove(pos);
		}

		if (soundIn != null) {
			ItemRecord itemrecord = ItemRecord.getBySound(soundIn);

			if (itemrecord != null) {
				this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
			}

			PositionedSoundRecord positionedsoundrecord = PositionedSoundRecord.getRecordSoundRecord(soundIn, pos.getX(), pos.getY(), pos.getZ());
			this.mapSoundPositions.put(pos, positionedsoundrecord);
			this.mc.getSoundHandler().playSound(positionedsoundrecord);
		}
	}

	@Override
	public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {
	}

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, final double xCoord, final double yCoord, final double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
		try {
			this.spawnEntityFX(particleID, ignoreRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
			crashreportcategory.addCrashSection("ID", Integer.valueOf(particleID));

			if (parameters != null) {
				crashreportcategory.addCrashSection("Parameters", parameters);
			}

			crashreportcategory.func_189529_a("Position", new ICrashReportDetail<String>() {
				@Override
				public String call() throws Exception {
					return CrashReportCategory.getCoordinateInfo(xCoord, yCoord, zCoord);
				}
			});
			throw new ReportedException(crashreport);
		}
	}

	private void spawnParticle(EnumParticleTypes particleIn, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
		this.spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
	}

	@Nullable
	private Particle spawnEntityFX(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
		Entity entity = this.mc.getRenderViewEntity();

		if ((this.mc != null) && (entity != null) && (this.mc.effectRenderer != null)) {
			int i = this.mc.gameSettings.particleSetting;

			if ((i == 1) && (this.theWorld.rand.nextInt(3) == 0)) {
				i = 2;
			}

			double d0 = entity.posX - xCoord;
			double d1 = entity.posY - yCoord;
			double d2 = entity.posZ - zCoord;

			if ((particleID == EnumParticleTypes.EXPLOSION_HUGE.getParticleID()) && !Config.isAnimatedExplosion()) {
				return null;
			} else if ((particleID == EnumParticleTypes.EXPLOSION_LARGE.getParticleID()) && !Config.isAnimatedExplosion()) {
				return null;
			} else if ((particleID == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID()) && !Config.isAnimatedExplosion()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SUSPENDED.getParticleID()) && !Config.isWaterParticles()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID()) && !Config.isVoidParticles()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SMOKE_NORMAL.getParticleID()) && !Config.isAnimatedSmoke()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SMOKE_LARGE.getParticleID()) && !Config.isAnimatedSmoke()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SPELL_MOB.getParticleID()) && !Config.isPotionParticles()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID()) && !Config.isPotionParticles()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SPELL.getParticleID()) && !Config.isPotionParticles()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SPELL_INSTANT.getParticleID()) && !Config.isPotionParticles()) {
				return null;
			} else if ((particleID == EnumParticleTypes.SPELL_WITCH.getParticleID()) && !Config.isPotionParticles()) {
				return null;
			} else if ((particleID == EnumParticleTypes.PORTAL.getParticleID()) && !Config.isAnimatedPortal()) {
				return null;
			} else if ((particleID == EnumParticleTypes.FLAME.getParticleID()) && !Config.isAnimatedFlame()) {
				return null;
			} else if ((particleID == EnumParticleTypes.REDSTONE.getParticleID()) && !Config.isAnimatedRedstone()) {
				return null;
			} else if ((particleID == EnumParticleTypes.DRIP_WATER.getParticleID()) && !Config.isDrippingWaterLava()) {
				return null;
			} else if ((particleID == EnumParticleTypes.DRIP_LAVA.getParticleID()) && !Config.isDrippingWaterLava()) {
				return null;
			} else if ((particleID == EnumParticleTypes.FIREWORKS_SPARK.getParticleID()) && !Config.isFireworkParticles()) {
				return null;
			} else {
				if (!ignoreRange) {
					double d3 = 1024.0D;

					if (particleID == EnumParticleTypes.CRIT.getParticleID()) {
						d3 = 38416.0D;
					}

					if (((d0 * d0) + (d1 * d1) + (d2 * d2)) > d3) { return null; }

					if (i > 1) { return null; }
				}

				Particle particle = this.mc.effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);

				if (particleID == EnumParticleTypes.WATER_BUBBLE.getParticleID()) {
					CustomColors.updateWaterFX(particle, this.theWorld, xCoord, yCoord, zCoord);
				}

				if (particleID == EnumParticleTypes.WATER_SPLASH.getParticleID()) {
					CustomColors.updateWaterFX(particle, this.theWorld, xCoord, yCoord, zCoord);
				}

				if (particleID == EnumParticleTypes.WATER_DROP.getParticleID()) {
					CustomColors.updateWaterFX(particle, this.theWorld, xCoord, yCoord, zCoord);
				}

				if (particleID == EnumParticleTypes.TOWN_AURA.getParticleID()) {
					CustomColors.updateMyceliumFX(particle);
				}

				if (particleID == EnumParticleTypes.PORTAL.getParticleID()) {
					CustomColors.updatePortalFX(particle);
				}

				if (particleID == EnumParticleTypes.REDSTONE.getParticleID()) {
					CustomColors.updateReddustFX(particle, this.theWorld, xCoord, yCoord, zCoord);
				}

				return particle;
			}
		} else {
			return null;
		}
	}

	/**
	 * Called on all IWorldAccesses when an entity is created or loaded. On client worlds, starts downloading any necessary textures. On server worlds, adds the entity to the entity tracker.
	 */
	@Override
	public void onEntityAdded(Entity entityIn) {
		RandomMobs.entityLoaded(entityIn, this.theWorld);

		if (Config.isDynamicLights()) {
			DynamicLights.entityAdded(entityIn, this);
		}
	}

	/**
	 * Called on all IWorldAccesses when an entity is unloaded or destroyed. On client worlds, releases any downloaded textures. On server worlds, removes the entity from the entity tracker.
	 */
	@Override
	public void onEntityRemoved(Entity entityIn) {
		if (Config.isDynamicLights()) {
			DynamicLights.entityRemoved(entityIn, this);
		}
	}

	/**
	 * Deletes all display lists
	 */
	public void deleteAllDisplayLists() {
	}

	@Override
	public void broadcastSound(int soundID, BlockPos pos, int data) {
		switch (soundID) {
		case 1023:
		case 1028:
			Entity entity = this.mc.getRenderViewEntity();

			if (entity != null) {
				double d0 = pos.getX() - entity.posX;
				double d1 = pos.getY() - entity.posY;
				double d2 = pos.getZ() - entity.posZ;
				double d3 = Math.sqrt((d0 * d0) + (d1 * d1) + (d2 * d2));
				double d4 = entity.posX;
				double d5 = entity.posY;
				double d6 = entity.posZ;

				if (d3 > 0.0D) {
					d4 += (d0 / d3) * 2.0D;
					d5 += (d1 / d3) * 2.0D;
					d6 += (d2 / d3) * 2.0D;
				}

				if (soundID == 1023) {
					this.theWorld.playSound(d4, d5, d6, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
				} else {
					this.theWorld.playSound(d4, d5, d6, SoundEvents.ENTITY_ENDERDRAGON_DEATH, SoundCategory.HOSTILE, 5.0F, 1.0F, false);
				}
			}

		default:
		}
	}

	@Override
	public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {
		Random random = this.theWorld.rand;

		switch (type) {
		case 1000:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			break;

		case 1001:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
			break;

		case 1002:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
			break;

		case 1003:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
			break;

		case 1004:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_FIREWORK_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
			break;

		case 1005:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1006:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1007:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1008:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1009:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + ((random.nextFloat() - random.nextFloat()) * 0.8F), false);
			break;

		case 1010:
			if (Item.getItemById(data) instanceof ItemRecord) {
				this.theWorld.playRecord(blockPosIn, ((ItemRecord) Item.getItemById(data)).getSound());
			} else {
				this.theWorld.playRecord(blockPosIn, (SoundEvent) null);
			}

			break;

		case 1011:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1012:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1013:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1014:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1015:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_GHAST_WARN, SoundCategory.HOSTILE, 10.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1016:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.HOSTILE, 10.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1017:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDERDRAGON_SHOOT, SoundCategory.HOSTILE, 10.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1018:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.HOSTILE, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1019:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, SoundCategory.HOSTILE, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1020:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.HOSTILE, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1021:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.HOSTILE, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1022:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1024:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.HOSTILE, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1025:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.NEUTRAL, 0.05F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1026:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.HOSTILE, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1027:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F) + 1.0F, false);
			break;

		case 1029:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1030:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1031:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1032:
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_PORTAL_TRAVEL, (random.nextFloat() * 0.4F) + 0.8F));
			break;

		case 1033:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			break;

		case 1034:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			break;

		case 1035:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			break;

		case 1036:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 1037:
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 2000:
			int i = (data % 3) - 1;
			int j = ((data / 3) % 3) - 1;
			double d0 = blockPosIn.getX() + (i * 0.6D) + 0.5D;
			double d1 = blockPosIn.getY() + 0.5D;
			double d2 = blockPosIn.getZ() + (j * 0.6D) + 0.5D;

			for (int j1 = 0; j1 < 10; ++j1) {
				double d15 = (random.nextDouble() * 0.2D) + 0.01D;
				double d16 = d0 + (i * 0.01D) + ((random.nextDouble() - 0.5D) * j * 0.5D);
				double d17 = d1 + ((random.nextDouble() - 0.5D) * 0.5D);
				double d18 = d2 + (j * 0.01D) + ((random.nextDouble() - 0.5D) * i * 0.5D);
				double d19 = (i * d15) + (random.nextGaussian() * 0.01D);
				double d20 = -0.03D + (random.nextGaussian() * 0.01D);
				double d21 = (j * d15) + (random.nextGaussian() * 0.01D);
				this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d16, d17, d18, d19, d20, d21, new int[0]);
			}

			return;

		case 2001:
			Block block = Block.getBlockById(data & 4095);

			if (block.getDefaultState().getMaterial() != Material.AIR) {
				SoundType soundtype = block.getSoundType();
				this.theWorld.playSound(blockPosIn, soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
			}

			this.mc.effectRenderer.addBlockDestroyEffects(blockPosIn, block.getStateFromMeta((data >> 12) & 255));
			break;

		case 2002:
			double d3 = blockPosIn.getX();
			double d4 = blockPosIn.getY();
			double d5 = blockPosIn.getZ();

			for (int k = 0; k < 8; ++k) {
				this.spawnParticle(EnumParticleTypes.ITEM_CRACK, d3, d4, d5, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D,
						new int[] { Item.getIdFromItem(Items.SPLASH_POTION) });
			}

			PotionType potiontype = PotionType.getPotionTypeForID(data);
			int l = PotionUtils.getPotionColor(potiontype);
			float f = ((l >> 16) & 255) / 255.0F;
			float f1 = ((l >> 8) & 255) / 255.0F;
			float f2 = ((l >> 0) & 255) / 255.0F;
			EnumParticleTypes enumparticletypes = potiontype.hasInstantEffect() ? EnumParticleTypes.SPELL_INSTANT : EnumParticleTypes.SPELL;

			for (int k1 = 0; k1 < 100; ++k1) {
				double d7 = random.nextDouble() * 4.0D;
				double d9 = random.nextDouble() * Math.PI * 2.0D;
				double d11 = Math.cos(d9) * d7;
				double d24 = 0.01D + (random.nextDouble() * 0.5D);
				double d26 = Math.sin(d9) * d7;
				Particle particle1 = this.spawnEntityFX(enumparticletypes.getParticleID(), enumparticletypes.getShouldIgnoreRange(), d3 + (d11 * 0.1D), d4 + 0.3D, d5 + (d26 * 0.1D), d11, d24, d26,
						new int[0]);

				if (particle1 != null) {
					float f5 = 0.75F + (random.nextFloat() * 0.25F);
					particle1.setRBGColorF(f * f5, f1 * f5, f2 * f5);
					particle1.multiplyVelocity((float) d7);
				}
			}

			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 2003:
			double d6 = blockPosIn.getX() + 0.5D;
			double d8 = blockPosIn.getY();
			double d10 = blockPosIn.getZ() + 0.5D;

			for (int i2 = 0; i2 < 8; ++i2) {
				this.spawnParticle(EnumParticleTypes.ITEM_CRACK, d6, d8, d10, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D,
						new int[] { Item.getIdFromItem(Items.ENDER_EYE) });
			}

			for (double d22 = 0.0D; d22 < (Math.PI * 2D); d22 += 0.15707963267948966D) {
				this.spawnParticle(EnumParticleTypes.PORTAL, d6 + (Math.cos(d22) * 5.0D), d8 - 0.4D, d10 + (Math.sin(d22) * 5.0D), Math.cos(d22) * -5.0D, 0.0D, Math.sin(d22) * -5.0D, new int[0]);
				this.spawnParticle(EnumParticleTypes.PORTAL, d6 + (Math.cos(d22) * 5.0D), d8 - 0.4D, d10 + (Math.sin(d22) * 5.0D), Math.cos(d22) * -7.0D, 0.0D, Math.sin(d22) * -7.0D, new int[0]);
			}

			return;

		case 2004:
			for (int l1 = 0; l1 < 20; ++l1) {
				double d23 = blockPosIn.getX() + 0.5D + ((this.theWorld.rand.nextFloat() - 0.5D) * 2.0D);
				double d25 = blockPosIn.getY() + 0.5D + ((this.theWorld.rand.nextFloat() - 0.5D) * 2.0D);
				double d27 = blockPosIn.getZ() + 0.5D + ((this.theWorld.rand.nextFloat() - 0.5D) * 2.0D);
				this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d23, d25, d27, 0.0D, 0.0D, 0.0D, new int[0]);
				this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d23, d25, d27, 0.0D, 0.0D, 0.0D, new int[0]);
			}

			return;

		case 2005:
			ItemDye.spawnBonemealParticles(this.theWorld, blockPosIn, data);
			break;

		case 2006:
			for (int i1 = 0; i1 < 200; ++i1) {
				float f3 = random.nextFloat() * 4.0F;
				float f4 = random.nextFloat() * ((float) Math.PI * 2F);
				double d12 = MathHelper.cos(f4) * f3;
				double d13 = 0.01D + (random.nextDouble() * 0.5D);
				double d14 = MathHelper.sin(f4) * f3;
				Particle particle = this.spawnEntityFX(EnumParticleTypes.DRAGON_BREATH.getParticleID(), false, blockPosIn.getX() + (d12 * 0.1D), blockPosIn.getY() + 0.3D,
						blockPosIn.getZ() + (d14 * 0.1D), d12, d13, d14, new int[0]);

				if (particle != null) {
					particle.multiplyVelocity(f3);
				}
			}

			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDERDRAGON_FIREBALL_EPLD, SoundCategory.HOSTILE, 1.0F, (this.theWorld.rand.nextFloat() * 0.1F) + 0.9F, false);
			break;

		case 3000:
			this.theWorld.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, true, blockPosIn.getX() + 0.5D, blockPosIn.getY() + 0.5D, blockPosIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
			this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_END_GATEWAY_SPAWN, SoundCategory.BLOCKS, 10.0F,
					(1.0F + ((this.theWorld.rand.nextFloat() - this.theWorld.rand.nextFloat()) * 0.2F)) * 0.7F, false);
			break;

		case 3001:
			this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.HOSTILE, 64.0F, 0.8F + (this.theWorld.rand.nextFloat() * 0.3F), false);
		}
	}

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
		if ((progress >= 0) && (progress < 10)) {
			DestroyBlockProgress destroyblockprogress = this.damagedBlocks.get(Integer.valueOf(breakerId));

			if ((destroyblockprogress == null) || (destroyblockprogress.getPosition().getX() != pos.getX()) || (destroyblockprogress.getPosition().getY() != pos.getY())
					|| (destroyblockprogress.getPosition().getZ() != pos.getZ())) {
				destroyblockprogress = new DestroyBlockProgress(breakerId, pos);
				this.damagedBlocks.put(Integer.valueOf(breakerId), destroyblockprogress);
			}

			destroyblockprogress.setPartialBlockDamage(progress);
			destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
		} else {
			this.damagedBlocks.remove(Integer.valueOf(breakerId));
		}
	}

	public boolean hasNoChunkUpdates() {
		return this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasChunkUpdates();
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

	public int getCountLoadedChunks() {
		if (this.theWorld == null) {
			return 0;
		} else {
			IChunkProvider ichunkprovider = this.theWorld.getChunkProvider();

			if (ichunkprovider == null) {
				return 0;
			} else {
				if (ichunkprovider != this.worldChunkProvider) {
					this.worldChunkProvider = ichunkprovider;
					Field field = Reflector.getField(ichunkprovider.getClass(), Long2ObjectMap.class);

					if (field != null) {
						field.setAccessible(true);

						try {
							this.worldChunkProviderMap = (Long2ObjectMap) field.get(ichunkprovider);
						} catch (Exception exception) {
							exception.printStackTrace();
						}
					}
				}

				return this.worldChunkProviderMap == null ? 0 : this.worldChunkProviderMap.size();
			}
		}
	}

	public RenderChunk getRenderChunk(BlockPos p_getRenderChunk_1_) {
		return this.viewFrustum.getRenderChunk(p_getRenderChunk_1_);
	}

	public WorldClient getWorld() {
		return this.theWorld;
	}

	public void updateTileEntities(Collection<TileEntity> tileEntitiesToRemove, Collection<TileEntity> tileEntitiesToAdd) {
		synchronized (this.setTileEntities) {
			this.setTileEntities.removeAll(tileEntitiesToRemove);
			this.setTileEntities.addAll(tileEntitiesToAdd);
		}
	}

	private RenderGlobal.ContainerLocalRenderInformation allocateRenderInformation(RenderChunk p_allocateRenderInformation_1_, EnumFacing p_allocateRenderInformation_2_,
			int p_allocateRenderInformation_3_) {
		RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = null;

		if (renderInfoCache.isEmpty()) {
			renderglobal$containerlocalrenderinformation = new RenderGlobal.ContainerLocalRenderInformation(p_allocateRenderInformation_1_, p_allocateRenderInformation_2_,
					p_allocateRenderInformation_3_);
		} else {
			renderglobal$containerlocalrenderinformation = renderInfoCache.pollLast();
			renderglobal$containerlocalrenderinformation.initialize(p_allocateRenderInformation_1_, p_allocateRenderInformation_2_, p_allocateRenderInformation_3_);
		}

		renderglobal$containerlocalrenderinformation.cacheable = true;
		return renderglobal$containerlocalrenderinformation;
	}

	private void freeRenderInformation(RenderGlobal.ContainerLocalRenderInformation p_freeRenderInformation_1_) {
		if (p_freeRenderInformation_1_.cacheable) {
			renderInfoCache.add(p_freeRenderInformation_1_);
		}
	}

	public static class ContainerLocalRenderInformation {
		RenderChunk renderChunk;
		EnumFacing facing;
		byte setFacing;
		int counter;
		boolean cacheable = false;

		public ContainerLocalRenderInformation(RenderChunk p_i7_1_, EnumFacing p_i7_2_, @Nullable int p_i7_3_) {
			this.renderChunk = p_i7_1_;
			this.facing = p_i7_2_;
			this.counter = p_i7_3_;
		}

		public void func_189561_a(byte p_189561_1_, EnumFacing p_189561_2_) {
			this.setFacing = (byte) (this.setFacing | p_189561_1_ | (1 << p_189561_2_.ordinal()));
		}

		public boolean func_189560_a(EnumFacing p_189560_1_) {
			return (this.setFacing & (1 << p_189560_1_.ordinal())) > 0;
		}

		private void initialize(RenderChunk p_initialize_1_, EnumFacing p_initialize_2_, int p_initialize_3_) {
			this.setFacing = 0;
			this.renderChunk = p_initialize_1_;
			this.facing = p_initialize_2_;
			this.counter = p_initialize_3_;
		}
	}
}
