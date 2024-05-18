package net.minecraft.client.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Project;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ICrashReportDetail;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.src.Lagometer;
import net.minecraft.src.RandomMobs;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorForge;
import net.minecraft.src.TextureUtils;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;
import us.dev.direkt.Direkt;
import us.dev.direkt.event.internal.events.game.render.EventRender2DNoScale;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.event.internal.events.game.render.EventRenderCamera;
import us.dev.direkt.module.internal.render.CaveFinder;
import us.dev.direkt.module.internal.render.NoRender;
import us.dev.direkt.module.internal.render.Tracers;

public class EntityRenderer implements IResourceManagerReloadListener {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
	private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");
	public static boolean anaglyphEnable;

	/** Anaglyph field (0=R, 1=GB) */
	public static int anaglyphField;

	/** A reference to the Minecraft object. */
	private final Minecraft mc;
	private final IResourceManager resourceManager;
	private final Random random = new Random();
	private float farPlaneDistance;
	public ItemRenderer itemRenderer;
	private final MapItemRenderer theMapItemRenderer;

	/** Entity renderer update count */
	private int rendererUpdateCount;

	/** Pointed entity */
	private Entity pointedEntity;
	private MouseFilter mouseFilterXAxis = new MouseFilter();
	private MouseFilter mouseFilterYAxis = new MouseFilter();
	private final float thirdPersonDistance = 4.0F;

	/** Previous third person distance */
	private float thirdPersonDistancePrev = 4.0F;

	/** Smooth cam yaw */
	private float smoothCamYaw;

	/** Smooth cam pitch */
	private float smoothCamPitch;

	/** Smooth cam filter X */
	private float smoothCamFilterX;

	/** Smooth cam filter Y */
	private float smoothCamFilterY;

	/** Smooth cam partial ticks */
	private float smoothCamPartialTicks;

	/** FOV modifier hand */
	private float fovModifierHand;

	/** FOV modifier hand prev */
	private float fovModifierHandPrev;
	private float bossColorModifier;
	private float bossColorModifierPrev;

	/** Cloud fog mode */
	private boolean cloudFog;
	private boolean renderHand = true;
	private boolean drawBlockOutline = true;
	private long timeWorldIcon;

	/** Previous frame time in milliseconds */
	private long prevFrameTime = Minecraft.getSystemTime();

	/** End time of last render (ns) */
	private long renderEndNanoTime;

	/**
	 * The texture id of the blocklight/skylight texture used for lighting effects
	 */
	private final DynamicTexture lightmapTexture;

	/**
	 * Colors computed in updateLightmap() and loaded into the lightmap emptyTexture
	 */
	private final int[] lightmapColors;
	private final ResourceLocation locationLightMap;

	/**
	 * Is set, updateCameraAndRender() calls updateLightmap(); set by updateTorchFlicker()
	 */
	private boolean lightmapUpdateNeeded;

	/** Torch flicker X */
	private float torchFlickerX;
	private float torchFlickerDX;

	/** Rain sound counter */
	private int rainSoundCounter;
	private final float[] rainXCoords = new float[1024];
	private final float[] rainYCoords = new float[1024];

	/** Fog color buffer */
	private final FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
	public float fogColorRed;
	public float fogColorGreen;
	public float fogColorBlue;

	/** Fog color 2 */
	private float fogColor2;

	/** Fog color 1 */
	private float fogColor1;
	private int debugViewDirection;
	private boolean debugView;
	private double cameraZoom = 1.0D;
	private double cameraYaw;
	private double cameraPitch;
	private ShaderGroup theShaderGroup;
	private static final ResourceLocation[] SHADERS_TEXTURES = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"),
			new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"),
			new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"),
			new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"),
			new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"),
			new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"),
			new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"),
			new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"),
			new ResourceLocation("shaders/post/spider.json") };
	public static final int SHADER_COUNT = SHADERS_TEXTURES.length;
	private int shaderIndex;
	private boolean useShader;
	public int frameCount;
	private boolean initialized = false;
	private World updatedWorld = null;
	private boolean showDebugInfo = false;
	public boolean fogStandard = false;
	private float clipDistance = 128.0F;
	private long lastServerTime = 0L;
	private int lastServerTicks = 0;
	private int serverWaitTime = 0;
	private int serverWaitTimeCurrent = 0;
	private float avgServerTimeDiff = 0.0F;
	private float avgServerTickDiff = 0.0F;
	private long lastErrorCheckTimeMs = 0L;
	private ShaderGroup[] fxaaShaders = new ShaderGroup[10];

	public EntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn) {
		this.shaderIndex = SHADER_COUNT;
		this.mc = mcIn;
		this.resourceManager = resourceManagerIn;
		this.itemRenderer = mcIn.getItemRenderer();
		this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager());
		this.lightmapTexture = new DynamicTexture(16, 16);
		this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
		this.lightmapColors = this.lightmapTexture.getTextureData();
		this.theShaderGroup = null;

		for (int i = 0; i < 32; ++i) {
			for (int j = 0; j < 32; ++j) {
				float f = j - 16;
				float f1 = i - 16;
				float f2 = MathHelper.sqrt_float((f * f) + (f1 * f1));
				this.rainXCoords[(i << 5) | j] = -f1 / f2;
				this.rainYCoords[(i << 5) | j] = f / f2;
			}
		}
	}

	public boolean isShaderActive() {
		return OpenGlHelper.shadersSupported && (this.theShaderGroup != null);
	}

	public void stopUseShader() {
		if (this.theShaderGroup != null) {
			this.theShaderGroup.deleteShaderGroup();
		}

		this.theShaderGroup = null;
		this.shaderIndex = SHADER_COUNT;
	}

	public void switchUseShader() {
		this.useShader = !this.useShader;
	}

	/**
	 * What shader to use when spectating this entity
	 */
	public void loadEntityShader(Entity entityIn) {
		if (OpenGlHelper.shadersSupported) {
			if (this.theShaderGroup != null) {
				this.theShaderGroup.deleteShaderGroup();
			}

			this.theShaderGroup = null;

			if (entityIn instanceof EntityCreeper) {
				this.loadShader(new ResourceLocation("shaders/post/creeper.json"));
			} else if (entityIn instanceof EntitySpider) {
				this.loadShader(new ResourceLocation("shaders/post/spider.json"));
			} else if (entityIn instanceof EntityEnderman) {
				this.loadShader(new ResourceLocation("shaders/post/invert.json"));
			} else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
				Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, new Object[] { entityIn, this });
			}
		}
	}

	private void loadShader(ResourceLocation resourceLocationIn) {
		if (OpenGlHelper.isFramebufferEnabled()) {
			try {
				this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocationIn);
				this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
				this.useShader = true;
			} catch (IOException ioexception) {
				LOGGER.warn("Failed to load shader: {}", new Object[] { resourceLocationIn, ioexception });
				this.shaderIndex = SHADER_COUNT;
				this.useShader = false;
			} catch (JsonSyntaxException jsonsyntaxexception) {
				LOGGER.warn("Failed to load shader: {}", new Object[] { resourceLocationIn, jsonsyntaxexception });
				this.shaderIndex = SHADER_COUNT;
				this.useShader = false;
			}
		}
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		if (this.theShaderGroup != null) {
			this.theShaderGroup.deleteShaderGroup();
		}

		this.theShaderGroup = null;

		if (this.shaderIndex == SHADER_COUNT) {
			this.loadEntityShader(this.mc.getRenderViewEntity());
		} else {
			this.loadShader(SHADERS_TEXTURES[this.shaderIndex]);
		}
	}

	/**
	 * Updates the entity renderer
	 */
	public void updateRenderer() {
		if (OpenGlHelper.shadersSupported && (ShaderLinkHelper.getStaticShaderLinkHelper() == null)) {
			ShaderLinkHelper.setNewStaticShaderLinkHelper();
		}

		this.updateFovModifierHand();
		this.updateTorchFlicker();
		this.fogColor2 = this.fogColor1;
		this.thirdPersonDistancePrev = 4.0F;

		if (this.mc.gameSettings.smoothCamera) {
			float f = (this.mc.gameSettings.mouseSensitivity * 0.6F) + 0.2F;
			float f1 = f * f * f * 8.0F;
			this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * f1);
			this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * f1);
			this.smoothCamPartialTicks = 0.0F;
			this.smoothCamYaw = 0.0F;
			this.smoothCamPitch = 0.0F;
		} else {
			this.smoothCamFilterX = 0.0F;
			this.smoothCamFilterY = 0.0F;
			this.mouseFilterXAxis.reset();
			this.mouseFilterYAxis.reset();
		}

		if (this.mc.getRenderViewEntity() == null) {
			this.mc.setRenderViewEntity(this.mc.thePlayer);
		}

		Entity entity = this.mc.getRenderViewEntity();
		double d2 = entity.posX;
		double d0 = entity.posY + entity.getEyeHeight();
		double d1 = entity.posZ;
		float f2 = this.mc.theWorld.getLightBrightness(new BlockPos(d2, d0, d1));
		float f3 = this.mc.gameSettings.renderDistanceChunks / 16.0F;
		f3 = MathHelper.clamp_float(f3, 0.0F, 1.0F);
		float f4 = (f2 * (1.0F - f3)) + f3;
		this.fogColor1 += (f4 - this.fogColor1) * 0.1F;
		++this.rendererUpdateCount;
		this.itemRenderer.updateEquippedItem();
		this.addRainParticles();
		this.bossColorModifierPrev = this.bossColorModifier;

		if (this.mc.ingameGUI.getBossOverlay().shouldDarkenSky()) {
			this.bossColorModifier += 0.05F;

			if (this.bossColorModifier > 1.0F) {
				this.bossColorModifier = 1.0F;
			}
		} else if (this.bossColorModifier > 0.0F) {
			this.bossColorModifier -= 0.0125F;
		}
	}

	public ShaderGroup getShaderGroup() {
		return this.theShaderGroup;
	}

	public void updateShaderGroupSize(int width, int height) {
		if (OpenGlHelper.shadersSupported) {
			if (this.theShaderGroup != null) {
				this.theShaderGroup.createBindFramebuffers(width, height);
			}

			this.mc.renderGlobal.createBindEntityOutlineFbs(width, height);
		}
	}

	/**
	 * Gets the block or object that is being moused over.
	 */
	public void getMouseOver(float partialTicks) {
		Entity entity = this.mc.getRenderViewEntity();

		if ((entity != null) && (this.mc.theWorld != null)) {
			this.mc.mcProfiler.startSection("pick");
			this.mc.pointedEntity = null;
			double d0 = this.mc.playerController.getBlockReachDistance();
			this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
			Vec3d vec3d = entity.getPositionEyes(partialTicks);
			boolean flag = false;
			int i = 3;
			double d1 = d0;

			if (this.mc.playerController.extendedReach()) {
				d1 = 6.0D;
				d0 = d1;
			} else if (d0 > 3.0D) {
				flag = true;
			}

			if (this.mc.objectMouseOver != null) {
				d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3d);
			}

			Vec3d vec3d1 = entity.getLook(partialTicks);
			Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0);
			this.pointedEntity = null;
			Vec3d vec3d3 = null;
			float f = 1.0F;
			List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity,
					entity.getEntityBoundingBox().addCoord(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0).expand(1.0D, 1.0D, 1.0D),
					Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
						@Override
						public boolean apply(@Nullable Entity p_apply_1_) {
							return (p_apply_1_ != null) && p_apply_1_.canBeCollidedWith();
						}
					}));
			double d2 = d1;

			for (int j = 0; j < list.size(); ++j) {
				Entity entity1 = list.get(j);
				AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(entity1.getCollisionBorderSize());
				RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

				if (axisalignedbb.isVecInside(vec3d)) {
					if (d2 >= 0.0D) {
						this.pointedEntity = entity1;
						vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
						d2 = 0.0D;
					}
				} else if (raytraceresult != null) {
					double d3 = vec3d.distanceTo(raytraceresult.hitVec);

					if ((d3 < d2) || (d2 == 0.0D)) {
						boolean flag1 = false;

						if (Reflector.ForgeEntity_canRiderInteract.exists()) {
							flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
						}

						if (!flag1 && (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity())) {
							if (d2 == 0.0D) {
								this.pointedEntity = entity1;
								vec3d3 = raytraceresult.hitVec;
							}
						} else {
							this.pointedEntity = entity1;
							vec3d3 = raytraceresult.hitVec;
							d2 = d3;
						}
					}
				}
			}

			if ((this.pointedEntity != null) && flag && (vec3d.distanceTo(vec3d3) > 3.0D)) {
				this.pointedEntity = null;
				this.mc.objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing) null, new BlockPos(vec3d3));
			}

			if ((this.pointedEntity != null) && ((d2 < d1) || (this.mc.objectMouseOver == null))) {
				this.mc.objectMouseOver = new RayTraceResult(this.pointedEntity, vec3d3);

				if ((this.pointedEntity instanceof EntityLivingBase) || (this.pointedEntity instanceof EntityItemFrame)) {
					this.mc.pointedEntity = this.pointedEntity;
				}
			}

			this.mc.mcProfiler.endSection();
		}
	}

	/**
	 * Update FOV modifier hand
	 */
	private void updateFovModifierHand() {
		float f = 1.0F;

		if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
			AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer) this.mc.getRenderViewEntity();
			f = abstractclientplayer.getFovModifier();
		}

		this.fovModifierHandPrev = this.fovModifierHand;
		this.fovModifierHand += (f - this.fovModifierHand) * 0.5F;

		if (this.fovModifierHand > 1.5F) {
			this.fovModifierHand = 1.5F;
		}

		if (this.fovModifierHand < 0.1F) {
			this.fovModifierHand = 0.1F;
		}
	}

	/**
	 * Changes the field of view of the player depending on if they are underwater or not
	 */
	private float getFOVModifier(float partialTicks, boolean useFOVSetting) {
		if (this.debugView) {
			return 90.0F;
		} else {
			Entity entity = this.mc.getRenderViewEntity();
			float f = 70.0F;

			if (useFOVSetting) {
				f = this.mc.gameSettings.fovSetting;

				if (Config.isDynamicFov()) {
					f *= this.fovModifierHandPrev + ((this.fovModifierHand - this.fovModifierHandPrev) * partialTicks);
				}
			}

			boolean flag = false;

			if (this.mc.currentScreen == null) {
				GameSettings gamesettings = this.mc.gameSettings;
				flag = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
			}

			if (flag) {
				if (!Config.zoomMode) {
					Config.zoomMode = true;
					this.mc.gameSettings.smoothCamera = true;
				}

				if (Config.zoomMode) {
					f /= 4.0F;
				}
			} else if (Config.zoomMode) {
				Config.zoomMode = false;
				this.mc.gameSettings.smoothCamera = false;
				this.mouseFilterXAxis = new MouseFilter();
				this.mouseFilterYAxis = new MouseFilter();
				this.mc.renderGlobal.displayListEntitiesDirty = true;
			}

			if ((entity instanceof EntityLivingBase) && (((EntityLivingBase) entity).getHealth() <= 0.0F)) {
				float f1 = ((EntityLivingBase) entity).deathTime + partialTicks;
				f /= ((1.0F - (500.0F / (f1 + 500.0F))) * 2.0F) + 1.0F;
			}

			IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);

			if (iblockstate.getMaterial() == Material.WATER) {
				f = (f * 60.0F) / 70.0F;
			}

			return Reflector.ForgeHooksClient_getFOVModifier.exists()
					? Reflector.callFloat(Reflector.ForgeHooksClient_getFOVModifier, new Object[] { this, entity, iblockstate, Float.valueOf(partialTicks), Float.valueOf(f) }) : f;
		}
	}

	private void hurtCameraEffect(float partialTicks) {
		if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) this.mc.getRenderViewEntity();
			float f = entitylivingbase.hurtTime - partialTicks;

			if (entitylivingbase.getHealth() <= 0.0F) {
				float f1 = entitylivingbase.deathTime + partialTicks;
				GlStateManager.rotate(40.0F - (8000.0F / (f1 + 200.0F)), 0.0F, 0.0F, 1.0F);
			}

			if (f < 0.0F) { return; }

			f = f / entitylivingbase.maxHurtTime;
			f = MathHelper.sin(f * f * f * f * (float) Math.PI);
			float f2 = entitylivingbase.attackedAtYaw;
			GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
		}
	}

	/**
	 * Updates the bobbing render effect of the player.
	 */
	private void setupViewBobbing(float partialTicks) {
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();
			float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
			float f1 = -(entityplayer.distanceWalkedModified + (f * partialTicks));
			float f2 = entityplayer.prevCameraYaw + ((entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks);
			float f3 = entityplayer.prevCameraPitch + ((entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks);
			GlStateManager.translate(MathHelper.sin(f1 * (float) Math.PI) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * (float) Math.PI) * f2), 0.0F);
			GlStateManager.rotate(MathHelper.sin(f1 * (float) Math.PI) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(Math.abs(MathHelper.cos((f1 * (float) Math.PI) - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
		}
	}

	/**
	 * sets up player's eye (or camera in third person mode)
	 */
    private void orientCamera(float partialTicks, int mode)
    {
        Entity entity = this.mc.getRenderViewEntity();
        float f = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
        double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + (double)f;
        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;

        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping())
        {
            f = (float)((double)f + 1.0D);
            GlStateManager.translate(0.0F, 0.3F, 0.0F);

            if (!this.mc.gameSettings.debugCamEnable)
            {
                BlockPos blockpos = new BlockPos(entity);
                IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
                Block block = iblockstate.getBlock();

                if (Reflector.ForgeHooksClient_orientBedCamera.exists())
                {
                    Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] {this.mc.theWorld, blockpos, iblockstate, entity});
                }
                else if (block == Blocks.BED)
                {
                    int j = ((EnumFacing)iblockstate.getValue(BlockBed.FACING)).getHorizontalIndex();
                    GlStateManager.rotate((float)(j * 90), 0.0F, 1.0F, 0.0F);
                }

                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0)
        {
            double d3 = (double)(this.thirdPersonDistancePrev + (this.thirdPersonDistance - this.thirdPersonDistancePrev) * partialTicks);

            if (this.mc.gameSettings.debugCamEnable)
            {
                GlStateManager.translate(0.0F, 0.0F, (float)(-d3));
            }
            else
            {
                float f1 = entity.rotationYaw;
                float f2 = entity.rotationPitch;

                if (this.mc.gameSettings.thirdPersonView == 2)
                {
                    f2 += 180.0F;
                }

                double d4 = (double)(-MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
                double d5 = (double)(MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
                double d6 = (double)(-MathHelper.sin(f2 * 0.017453292F)) * d3;

                for (int i = 0; i < 8; ++i)
                {
                    float f3 = (float)((i & 1) * 2 - 1);
                    float f4 = (float)((i >> 1 & 1) * 2 - 1);
                    float f5 = (float)((i >> 2 & 1) * 2 - 1);
                    f3 = f3 * 0.1F;
                    f4 = f4 * 0.1F;
                    f5 = f5 * 0.1F;
                    RayTraceResult raytraceresult = this.mc.theWorld.rayTraceBlocks(new Vec3d(d0 + (double)f3, d1 + (double)f4, d2 + (double)f5), new Vec3d(d0 - d4 + (double)f3 + (double)f5, d1 - d6 + (double)f4, d2 - d5 + (double)f5));

                    if (raytraceresult != null)
                    {
                        double d7 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d1, d2));

                        if (d7 < d3)
                        {
                            d3 = d7;
                        }
                    }
                }

                if (this.mc.gameSettings.thirdPersonView == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.0F, (float)(-d3));
                GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView == 0 && Direkt.getInstance().getModuleManager().getModule(Tracers.class).isRunning() && mode == 1)
        {
            double d3 = (double)(this.thirdPersonDistancePrev + (this.thirdPersonDistance - this.thirdPersonDistancePrev) * partialTicks);

            GlStateManager.translate(0.0F, 0.0F, (float)(-d3) * 0.02);
        }
        
        else 
        {
            GlStateManager.translate(0.0F, 0.0F, 0.05F);
        }

        if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists())
        {
            if (!this.mc.gameSettings.debugCamEnable)
            {
                float f6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F;
                float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                float f8 = 0.0F;

                if (entity instanceof EntityAnimal)
                {
                    EntityAnimal entityanimal1 = (EntityAnimal)entity;
                    f6 = entityanimal1.prevRotationYawHead + (entityanimal1.rotationYawHead - entityanimal1.prevRotationYawHead) * partialTicks + 180.0F;
                }

                IBlockState iblockstate1 = ActiveRenderInfo.getBlockStateAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);
                Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, new Object[] {this, entity, iblockstate1, Float.valueOf(partialTicks), Float.valueOf(f6), Float.valueOf(f7), Float.valueOf(f8)});
                Reflector.postForgeBusEvent(object);
                f8 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getRoll, new Object[0]);
                f7 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getPitch, new Object[0]);
                f6 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getYaw, new Object[0]);
                GlStateManager.rotate(f8, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(f7, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(f6, 0.0F, 1.0F, 0.0F);
            }
        }
        else if (!this.mc.gameSettings.debugCamEnable)
        {
            GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);

            if (entity instanceof EntityAnimal)
            {
                EntityAnimal entityanimal = (EntityAnimal)entity;
                GlStateManager.rotate(entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
            }
        }

        GlStateManager.translate(0.0F, -f, 0.0F);
        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
        d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + (double)f;
        d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
    }

	/**
	 * sets up projection, view effects, camera position/rotation
	 */
	   public void setupCameraTransform(float partialTicks, int pass, int mode)
	    {
	        this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);

	        if (Config.isFogFancy())
	        {
	            this.farPlaneDistance *= 0.95F;
	        }

	        if (Config.isFogFast())
	        {
	            this.farPlaneDistance *= 0.83F;
	        }

	        GlStateManager.matrixMode(5889);
	        GlStateManager.loadIdentity();
	        float f = 0.07F;

	        if (this.mc.gameSettings.anaglyph)
	        {
	            GlStateManager.translate((float)(-(pass * 2 - 1)) * f, 0.0F, 0.0F);
	        }

	        this.clipDistance = this.farPlaneDistance * 2.0F;

	        if (this.clipDistance < 173.0F)
	        {
	            this.clipDistance = 173.0F;
	        }

	        if (this.mc.theWorld.provider.getDimensionType() == DimensionType.THE_END)
	        {
	            this.clipDistance = 256.0F;
	        }

	        if (this.cameraZoom != 1.0D)
	        {
	            GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
	            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
	        }

	        Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.clipDistance);
	        GlStateManager.matrixMode(5888);
	        GlStateManager.loadIdentity();

	        if (this.mc.gameSettings.anaglyph)
	        {
	            GlStateManager.translate((float)(pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
	        }

	        this.hurtCameraEffect(partialTicks);

	        if (this.mc.gameSettings.viewBobbing)
	        {
	            this.setupViewBobbing(partialTicks);
	        }

	        float f1 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;

	        if (f1 > 0.0F)
	        {
	            int i = 20;

	            if (this.mc.thePlayer.isPotionActive(MobEffects.NAUSEA))
	            {
	                i = 7;
	            }

	            float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
	            f2 = f2 * f2;
	            GlStateManager.rotate(((float)this.rendererUpdateCount + partialTicks) * (float)i, 0.0F, 1.0F, 1.0F);
	            GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
	            GlStateManager.rotate(-((float)this.rendererUpdateCount + partialTicks) * (float)i, 0.0F, 1.0F, 1.0F);
	        }
			if(mode == 2){
		        GlStateManager.rotate(180, 1.0F, 0.0F, 0.0F);
		        GlStateManager.rotate(180, 0.0F, 0.0F, 1.0F);
			}
	        this.orientCamera(partialTicks, mode);

	        if (this.debugView)
	        {
	            switch (this.debugViewDirection)
	            {
	                case 0:
	                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
	                    break;

	                case 1:
	                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
	                    break;

	                case 2:
	                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
	                    break;

	                case 3:
	                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	                    break;

	                case 4:
	                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
	            }
	        }
	    }

	/**
	 * Render player hand
	 */
	public void renderHand(float partialTicks, int pass) {
		if (!this.debugView) {
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			float f = 0.07F;

			if (this.mc.gameSettings.anaglyph) {
				GlStateManager.translate((-((pass * 2) - 1)) * 0.07F, 0.0F, 0.0F);
			}

			if (Config.isShaders()) {
				Shaders.applyHandDepth();
			}

			Project.gluPerspective(this.getFOVModifier(partialTicks, false), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
			GlStateManager.matrixMode(5888);
			GlStateManager.loadIdentity();

			if (this.mc.gameSettings.anaglyph) {
				GlStateManager.translate(((pass * 2) - 1) * 0.1F, 0.0F, 0.0F);
			}

			boolean flag = false;

			if (!Config.isShaders() || !Shaders.isHandRendered) {
				GlStateManager.pushMatrix();
				this.hurtCameraEffect(partialTicks);

				if (this.mc.gameSettings.viewBobbing) {
					this.setupViewBobbing(partialTicks);
				}

				flag = (this.mc.getRenderViewEntity() instanceof EntityLivingBase) && ((EntityLivingBase) this.mc.getRenderViewEntity()).isPlayerSleeping();

				if ((this.mc.gameSettings.thirdPersonView == 0) && !flag && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator()) {
					this.enableLightmap();

					if (Config.isShaders()) {
						ShadersRender.renderItemFP(this.itemRenderer, partialTicks);
					} else {
						this.itemRenderer.renderItemInFirstPerson(partialTicks);
					}

					this.disableLightmap();
				}

				GlStateManager.popMatrix();
			}

			if (Config.isShaders() && !Shaders.isCompositeRendered) { return; }

			this.disableLightmap();

			if ((this.mc.gameSettings.thirdPersonView == 0) && !flag) {
				this.itemRenderer.renderOverlays(partialTicks);
				this.hurtCameraEffect(partialTicks);
			}

			if (this.mc.gameSettings.viewBobbing) {
				this.setupViewBobbing(partialTicks);
			}
		}
	}

	public void disableLightmap() {
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		if (Config.isShaders()) {
			Shaders.disableLightmap();
		}
	}

	public void enableLightmap() {
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		float f = 0.00390625F;
		GlStateManager.scale(0.00390625F, 0.00390625F, 0.00390625F);
		GlStateManager.translate(8.0F, 8.0F, 8.0F);
		GlStateManager.matrixMode(5888);
		this.mc.getTextureManager().bindTexture(this.locationLightMap);
		GlStateManager.glTexParameteri(3553, 10241, 9729);
		GlStateManager.glTexParameteri(3553, 10240, 9729);
		GlStateManager.glTexParameteri(3553, 10242, 10496);
		GlStateManager.glTexParameteri(3553, 10243, 10496);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		if (Config.isShaders()) {
			Shaders.enableLightmap();
		}
	}

	/**
	 * Recompute a random value that is applied to block color in updateLightmap()
	 */
	private void updateTorchFlicker() {
		this.torchFlickerDX = (float) (this.torchFlickerDX + ((Math.random() - Math.random()) * Math.random() * Math.random()));
		this.torchFlickerDX = (float) (this.torchFlickerDX * 0.9D);
		this.torchFlickerX += this.torchFlickerDX - this.torchFlickerX;
		this.lightmapUpdateNeeded = true;
	}

	private void updateLightmap(float partialTicks) {
		if (this.lightmapUpdateNeeded) {
			this.mc.mcProfiler.startSection("lightTex");
			World world = this.mc.theWorld;

			if (world != null) {
				if (Config.isCustomColors() && CustomColors.updateLightmap(world, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(MobEffects.NIGHT_VISION))) {
					this.lightmapTexture.updateDynamicTexture();
					this.lightmapUpdateNeeded = false;
					this.mc.mcProfiler.endSection();
					return;
				}

				float f = world.getSunBrightness(1.0F);
				float f1 = (f * 0.95F) + 0.05F;

				for (int i = 0; i < 256; ++i) {
					float f2 = world.provider.getLightBrightnessTable()[i / 16] * f1;
					float f3 = world.provider.getLightBrightnessTable()[i % 16] * ((this.torchFlickerX * 0.1F) + 1.5F);

					if (world.getLastLightningBolt() > 0) {
						f2 = world.provider.getLightBrightnessTable()[i / 16];
					}

					float f4 = f2 * ((f * 0.65F) + 0.35F);
					float f5 = f2 * ((f * 0.65F) + 0.35F);
					float f6 = f3 * ((((f3 * 0.6F) + 0.4F) * 0.6F) + 0.4F);
					float f7 = f3 * ((f3 * f3 * 0.6F) + 0.4F);
					float f8 = f4 + f3;
					float f9 = f5 + f6;
					float f10 = f2 + f7;
					f8 = (f8 * 0.96F) + 0.03F;
					f9 = (f9 * 0.96F) + 0.03F;
					f10 = (f10 * 0.96F) + 0.03F;

					if (this.bossColorModifier > 0.0F) {
						float f11 = this.bossColorModifierPrev + ((this.bossColorModifier - this.bossColorModifierPrev) * partialTicks);
						f8 = (f8 * (1.0F - f11)) + (f8 * 0.7F * f11);
						f9 = (f9 * (1.0F - f11)) + (f9 * 0.6F * f11);
						f10 = (f10 * (1.0F - f11)) + (f10 * 0.6F * f11);
					}

					if (world.provider.getDimensionType().getId() == 1) {
						f8 = 0.22F + (f3 * 0.75F);
						f9 = 0.28F + (f6 * 0.75F);
						f10 = 0.25F + (f7 * 0.75F);
					}

					if (this.mc.thePlayer.isPotionActive(MobEffects.NIGHT_VISION)) {
						float f15 = this.getNightVisionBrightness(this.mc.thePlayer, partialTicks);
						float f12 = 1.0F / f8;

						if (f12 > (1.0F / f9)) {
							f12 = 1.0F / f9;
						}

						if (f12 > (1.0F / f10)) {
							f12 = 1.0F / f10;
						}

						f8 = (f8 * (1.0F - f15)) + (f8 * f12 * f15);
						f9 = (f9 * (1.0F - f15)) + (f9 * f12 * f15);
						f10 = (f10 * (1.0F - f15)) + (f10 * f12 * f15);
					}

					if (f8 > 1.0F) {
						f8 = 1.0F;
					}

					if (f9 > 1.0F) {
						f9 = 1.0F;
					}

					if (f10 > 1.0F) {
						f10 = 1.0F;
					}

					float f16 = this.mc.gameSettings.gammaSetting;
					float f17 = 1.0F - f8;
					float f13 = 1.0F - f9;
					float f14 = 1.0F - f10;
					f17 = 1.0F - (f17 * f17 * f17 * f17);
					f13 = 1.0F - (f13 * f13 * f13 * f13);
					f14 = 1.0F - (f14 * f14 * f14 * f14);
					f8 = (f8 * (1.0F - f16)) + (f17 * f16);
					f9 = (f9 * (1.0F - f16)) + (f13 * f16);
					f10 = (f10 * (1.0F - f16)) + (f14 * f16);
					f8 = (f8 * 0.96F) + 0.03F;
					f9 = (f9 * 0.96F) + 0.03F;
					f10 = (f10 * 0.96F) + 0.03F;

					if (f8 > 1.0F) {
						f8 = 1.0F;
					}

					if (f9 > 1.0F) {
						f9 = 1.0F;
					}

					if (f10 > 1.0F) {
						f10 = 1.0F;
					}

					if (f8 < 0.0F) {
						f8 = 0.0F;
					}

					if (f9 < 0.0F) {
						f9 = 0.0F;
					}

					if (f10 < 0.0F) {
						f10 = 0.0F;
					}

					int j = 255;
					int k = (int) (f8 * 255.0F);
					int l = (int) (f9 * 255.0F);
					int i1 = (int) (f10 * 255.0F);
					this.lightmapColors[i] = -16777216 | (k << 16) | (l << 8) | i1;
				}

				this.lightmapTexture.updateDynamicTexture();
				this.lightmapUpdateNeeded = false;
				this.mc.mcProfiler.endSection();
			}
		}
	}

	private float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {
		int i = entitylivingbaseIn.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
		return i > 200 ? 1.0F : 0.7F + (MathHelper.sin((i - partialTicks) * (float) Math.PI * 0.2F) * 0.3F);
	}

	public void updateCameraAndRender(float partialTicks, long nanoTime) {
		this.frameInit();
		
		boolean flag = Display.isActive();

		if (!flag && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
			if ((Minecraft.getSystemTime() - this.prevFrameTime) > 500L) {
				this.mc.displayInGameMenu();
			}
		} else {
			this.prevFrameTime = Minecraft.getSystemTime();
		}

		this.mc.mcProfiler.startSection("mouse");

		if (flag && Minecraft.IS_RUNNING_ON_MAC && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
			Mouse.setGrabbed(false);
			Mouse.setCursorPosition(Display.getWidth() / 2, (Display.getHeight() / 2) - 20);
			Mouse.setGrabbed(true);
		}

		if (this.mc.inGameHasFocus && flag) {
			this.mc.mouseHelper.mouseXYChange();
			float f = (this.mc.gameSettings.mouseSensitivity * 0.6F) + 0.2F;
			float f1 = f * f * f * 8.0F;
			float f2 = this.mc.mouseHelper.deltaX * f1;
			float f3 = this.mc.mouseHelper.deltaY * f1;
			int i = 1;

			if (this.mc.gameSettings.invertMouse) {
				i = -1;
			}

			if (this.mc.gameSettings.smoothCamera) {
				this.smoothCamYaw += f2;
				this.smoothCamPitch += f3;
				float f4 = partialTicks - this.smoothCamPartialTicks;
				this.smoothCamPartialTicks = partialTicks;
				f2 = this.smoothCamFilterX * f4;
				f3 = this.smoothCamFilterY * f4;
				this.mc.thePlayer.setAngles(f2, f3 * i);
			} else {
				this.smoothCamYaw = 0.0F;
				this.smoothCamPitch = 0.0F;
				this.mc.thePlayer.setAngles(f2, f3 * i);
			}
		}

		this.mc.mcProfiler.endSection();

		if (!this.mc.skipRenderWorld) {
			anaglyphEnable = this.mc.gameSettings.anaglyph;
			final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
			int i1 = scaledresolution.getScaledWidth();
			int j1 = scaledresolution.getScaledHeight();
			final int k1 = (Mouse.getX() * i1) / this.mc.displayWidth;
			final int l1 = j1 - ((Mouse.getY() * j1) / this.mc.displayHeight) - 1;
			int i2 = this.mc.gameSettings.limitFramerate;

			if (this.mc.theWorld == null) {
				GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
				GlStateManager.matrixMode(5889);
				GlStateManager.loadIdentity();
				GlStateManager.matrixMode(5888);
				GlStateManager.loadIdentity();
				this.setupOverlayRendering();
				this.renderEndNanoTime = System.nanoTime();
			} else {
				this.mc.mcProfiler.startSection("level");
				int j = Math.min(Minecraft.getDebugFPS(), i2);
				j = Math.max(j, 60);
				long k = System.nanoTime() - nanoTime;
				long l = Math.max(1000000000 / j / 4 - k, 0L);
				this.renderWorld(partialTicks, System.nanoTime() + l);

				if (this.mc.isSingleplayer() && (this.timeWorldIcon < (Minecraft.getSystemTime() - 1000L))) {
					this.timeWorldIcon = Minecraft.getSystemTime();

					if (!this.mc.getIntegratedServer().isWorldIconSet()) {
						this.createWorldIcon();
					}
				}
                int scale = this.mc.gameSettings.guiScale;
                this.mc.gameSettings.guiScale = 1;
                //TODO: Direkt: EventRender2DNoScale
                Direkt.getInstance().getEventManager().call(new EventRender2DNoScale());
                this.mc.gameSettings.guiScale = scale;
				
				if (OpenGlHelper.shadersSupported) {
					this.mc.renderGlobal.renderEntityOutlineFramebuffer();

					if ((this.theShaderGroup != null) && this.useShader) {
						GlStateManager.matrixMode(5890);
						GlStateManager.pushMatrix();
						GlStateManager.loadIdentity();
						this.theShaderGroup.loadShaderGroup(partialTicks);
						GlStateManager.popMatrix();
					}

					this.mc.getFramebuffer().bindFramebuffer(true);
				}

				this.renderEndNanoTime = System.nanoTime();
				this.mc.mcProfiler.endStartSection("gui");

				if (!this.mc.gameSettings.hideGUI || (this.mc.currentScreen != null)) {
					GlStateManager.alphaFunc(516, 0.1F);
					this.mc.ingameGUI.renderGameOverlay(partialTicks);

					if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
						Config.drawFps();
					}

					if (this.mc.gameSettings.showDebugInfo) {
						Lagometer.showLagometer(scaledresolution);
					}
				}

				this.mc.mcProfiler.endSection();
			}

			if (this.mc.currentScreen != null) {
				GlStateManager.clear(256);

				try {
					if (Reflector.ForgeHooksClient_drawScreen.exists()) {
						Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, new Object[] { this.mc.currentScreen, Integer.valueOf(k1), Integer.valueOf(l1), Float.valueOf(partialTicks) });
					} else {
						this.mc.currentScreen.drawScreen(k1, l1, partialTicks);
					}
				} catch (Throwable throwable) {
					CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering screen");
					CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
					crashreportcategory.func_189529_a("Screen name", new ICrashReportDetail<String>() {
						@Override
						public String call() throws Exception {
							return EntityRenderer.this.mc.currentScreen.getClass().getCanonicalName();
						}
					});
					crashreportcategory.func_189529_a("Mouse location", new ICrashReportDetail<String>() {
						@Override
						public String call() throws Exception {
							return String.format("Scaled: (%d, %d). Absolute: (%d, %d)",
									new Object[] { Integer.valueOf(k1), Integer.valueOf(l1), Integer.valueOf(Mouse.getX()), Integer.valueOf(Mouse.getY()) });
						}
					});
					crashreportcategory.func_189529_a("Screen size", new ICrashReportDetail<String>() {
						@Override
						public String call() throws Exception {
							return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d",
									new Object[] { Integer.valueOf(scaledresolution.getScaledWidth()), Integer.valueOf(scaledresolution.getScaledHeight()),
											Integer.valueOf(EntityRenderer.this.mc.displayWidth), Integer.valueOf(EntityRenderer.this.mc.displayHeight),
											Integer.valueOf(scaledresolution.getScaleFactor()) });
						}
					});
					throw new ReportedException(crashreport);
				}
			}
		}

		this.frameFinish();
		this.waitForServerThread();
		Lagometer.updateLagometer();

		if (this.mc.gameSettings.ofProfiler) {
			this.mc.gameSettings.showDebugProfilerChart = true;
		}
	}

	private void createWorldIcon() {
		if ((this.mc.renderGlobal.getRenderedChunks() > 10) && this.mc.renderGlobal.hasNoChunkUpdates() && !this.mc.getIntegratedServer().isWorldIconSet()) {
			BufferedImage bufferedimage = ScreenShotHelper.createScreenshot(this.mc.displayWidth, this.mc.displayHeight, this.mc.getFramebuffer());
			int i = bufferedimage.getWidth();
			int j = bufferedimage.getHeight();
			int k = 0;
			int l = 0;

			if (i > j) {
				k = (i - j) / 2;
				i = j;
			} else {
				l = (j - i) / 2;
			}

			try {
				BufferedImage bufferedimage1 = new BufferedImage(64, 64, 1);
				Graphics graphics = bufferedimage1.createGraphics();
				graphics.drawImage(bufferedimage, 0, 0, 64, 64, k, l, k + i, l + i, (ImageObserver) null);
				graphics.dispose();
				ImageIO.write(bufferedimage1, "png", this.mc.getIntegratedServer().getWorldIconFile());
			} catch (IOException ioexception) {
				LOGGER.warn("Couldn\'t save auto screenshot", ioexception);
			}
		}
	}

	public void renderStreamIndicator(float partialTicks) {
		this.setupOverlayRendering();
	}

	private boolean isDrawBlockOutline() {
		if (!this.drawBlockOutline) {
			return false;
		} else {
			Entity entity = this.mc.getRenderViewEntity();
			boolean flag = (entity instanceof EntityPlayer) && !this.mc.gameSettings.hideGUI;

			if (flag && !((EntityPlayer) entity).capabilities.allowEdit) {
				ItemStack itemstack = ((EntityPlayer) entity).getHeldItemMainhand();

				if ((this.mc.objectMouseOver != null) && (this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)) {
					BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
					IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
					Block block = iblockstate.getBlock();

					if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
						flag = ReflectorForge.blockHasTileEntity(iblockstate) && (this.mc.theWorld.getTileEntity(blockpos) instanceof IInventory);
					} else {
						flag = (itemstack != null) && (itemstack.canDestroy(block) || itemstack.canPlaceOn(block));
					}
				}
			}

			return flag;
		}
	}

	public void renderWorld(float partialTicks, long finishTimeNano) {
		this.updateLightmap(partialTicks);

		if (this.mc.getRenderViewEntity() == null) {
			this.mc.setRenderViewEntity(this.mc.thePlayer);
		}

		this.getMouseOver(partialTicks);

		if (Config.isShaders()) {
			Shaders.beginRender(this.mc, partialTicks, finishTimeNano);
		}

		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.1F);
		this.mc.mcProfiler.startSection("center");

		if (this.mc.gameSettings.anaglyph) {
			anaglyphField = 0;
			GlStateManager.colorMask(false, true, true, false);
			this.renderWorldPass(0, partialTicks, finishTimeNano);
			anaglyphField = 1;
			GlStateManager.colorMask(true, false, false, false);
			this.renderWorldPass(1, partialTicks, finishTimeNano);
			GlStateManager.colorMask(true, true, true, false);
		} else {
			this.renderWorldPass(2, partialTicks, finishTimeNano);
            this.renderExtWorldPass(2, partialTicks, finishTimeNano);
		}
		
        //TODO: Direkt: EventRender3D
        Direkt.getInstance().getEventManager().call(new EventRenderCamera(null));
        
		this.mc.mcProfiler.endSection();
	}

	public void renderWorldPass(int pass, float partialTicks, long finishTimeNano) {
		boolean flag = Config.isShaders();

		if (flag) {
			Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
		}

		RenderGlobal renderglobal = this.mc.renderGlobal;
		ParticleManager particlemanager = this.mc.effectRenderer;
		boolean flag1 = this.isDrawBlockOutline();
		GlStateManager.enableCull();
		this.mc.mcProfiler.endStartSection("clear");

		if (flag) {
			Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		} else {
			GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		}

		this.updateFogColor(partialTicks);
		GlStateManager.clear(16640);

		if (flag) {
			Shaders.clearRenderBuffer();
		}

		this.mc.mcProfiler.endStartSection("camera");
		this.setupCameraTransform(partialTicks, pass, 0);

		if (flag) {
			Shaders.setCamera(partialTicks);
		}

		ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
		this.mc.mcProfiler.endStartSection("frustum");
		ClippingHelperImpl.getInstance();
		this.mc.mcProfiler.endStartSection("culling");
		ICamera icamera = new Frustum();
		Entity entity = this.mc.getRenderViewEntity();
		double d0 = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * partialTicks);
		double d1 = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partialTicks);
		double d2 = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * partialTicks);

		if (flag) {
			ShadersRender.setFrustrumPosition(icamera, d0, d1, d2);
		} else {
			icamera.setPosition(d0, d1, d2);
		}

		if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
			this.setupFog(-1, partialTicks);
			this.mc.mcProfiler.endStartSection("sky");
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.clipDistance);
			GlStateManager.matrixMode(5888);

			if (flag) {
				Shaders.beginSky();
			}

			renderglobal.renderSky(partialTicks, pass);

			if (flag) {
				Shaders.endSky();
			}

			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.clipDistance);
			GlStateManager.matrixMode(5888);
		} else {
			GlStateManager.disableBlend();
		}

		this.setupFog(0, partialTicks);
		GlStateManager.shadeModel(7425);

		if ((entity.posY + entity.getEyeHeight()) < (128.0D + this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
			this.renderCloudsCheck(renderglobal, partialTicks, pass);
		}

		this.mc.mcProfiler.endStartSection("prepareterrain");
		this.setupFog(0, partialTicks);
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		RenderHelper.disableStandardItemLighting();
		this.mc.mcProfiler.endStartSection("terrain_setup");

		if (flag) {
			ShadersRender.setupTerrain(renderglobal, entity, partialTicks, icamera, this.frameCount++, this.mc.thePlayer.isSpectator());
		} else {
            renderglobal.setupTerrain(entity, (double)partialTicks, icamera, this.frameCount++, !Direkt.getInstance().getModuleManager().getModule(NoRender.class).isRunning());
            //TODO: Direkt: Chunk rendering fix.
            //TODO: Direkt: NoRender.
		}

		if ((pass == 0) || (pass == 2)) {
			this.mc.mcProfiler.endStartSection("updatechunks");
			Lagometer.timerChunkUpload.start();
			this.mc.renderGlobal.updateChunks(finishTimeNano);
			Lagometer.timerChunkUpload.end();
		}

		this.mc.mcProfiler.endStartSection("terrain");
		Lagometer.timerTerrain.start();

		if (this.mc.gameSettings.ofSmoothFps && (pass > 0)) {
			this.mc.mcProfiler.endStartSection("finish");
			GL11.glFinish();
			this.mc.mcProfiler.endStartSection("terrain");
		}

		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.disableAlpha();

		if (flag) {
			ShadersRender.beginTerrainSolid();
		}

		
		//TODO: Direkt: CaveFinder
        if(Direkt.getInstance().getModuleManager().getModule(CaveFinder.class).isRunning())
        	Direkt.getInstance().getModuleManager().getModule(CaveFinder.class).startRenderCaves();
		
		renderglobal.renderBlockLayer(BlockRenderLayer.SOLID, partialTicks, pass, entity);
		GlStateManager.enableAlpha();

		if (flag) {
			ShadersRender.beginTerrainCutoutMipped();
		}
		//TODO: Direkt: CaveFinder
        if(!(Direkt.getInstance().getModuleManager().getModule(CaveFinder.class).isRunning()))
        	renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, (double)partialTicks, pass, entity);
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);

		if (flag) {
			ShadersRender.beginTerrainCutout();
		}

		renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT, partialTicks, pass, entity);
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();

		if (flag) {
			ShadersRender.endTerrain();
		}
		//TODO: Direkt: CaveFinder
        if(Direkt.getInstance().getModuleManager().getModule(CaveFinder.class).isRunning())
        	Direkt.getInstance().getModuleManager().getModule(CaveFinder.class).endRenderCaves();
		
		Lagometer.timerTerrain.end();
		GlStateManager.shadeModel(7424);
		GlStateManager.alphaFunc(516, 0.1F);

		if (!this.debugView) {
			GlStateManager.matrixMode(5888);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			RenderHelper.enableStandardItemLighting();
			this.mc.mcProfiler.endStartSection("entities");

			if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
				Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
			}

			renderglobal.renderEntities(entity, icamera, partialTicks);

			if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
				Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
			}

			RenderHelper.disableStandardItemLighting();
			this.disableLightmap();
		}

		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();

		if (flag1 && (this.mc.objectMouseOver != null) && !entity.isInsideOfMaterial(Material.WATER)) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			GlStateManager.disableAlpha();
			this.mc.mcProfiler.endStartSection("outline");

			if (!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight,
					new Object[] { renderglobal, entityplayer, this.mc.objectMouseOver, Integer.valueOf(0), Float.valueOf(partialTicks) })) {
				renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
			}
			GlStateManager.enableAlpha();
		}

		if (this.mc.debugRenderer.func_190074_a()) {
			this.mc.debugRenderer.func_190073_a(partialTicks, finishTimeNano);
		}

		if (!renderglobal.damagedBlocks.isEmpty()) {
			this.mc.mcProfiler.endStartSection("destroyProgress");
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
			renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), entity, partialTicks);
			this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
			GlStateManager.disableBlend();
		}

		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.disableBlend();

		if (!this.debugView) {
			this.enableLightmap();
			this.mc.mcProfiler.endStartSection("litParticles");

			if (flag) {
				Shaders.beginLitParticles();
			}

			particlemanager.renderLitParticles(entity, partialTicks);
			RenderHelper.disableStandardItemLighting();
			this.setupFog(0, partialTicks);
			this.mc.mcProfiler.endStartSection("particles");

			if (flag) {
				Shaders.beginParticles();
			}

			particlemanager.renderParticles(entity, partialTicks);

			if (flag) {
				Shaders.endParticles();
			}

			this.disableLightmap();
		}

		GlStateManager.depthMask(false);
		GlStateManager.enableCull();
		this.mc.mcProfiler.endStartSection("weather");

		if (flag) {
			Shaders.beginWeather();
		}

		this.renderRainSnow(partialTicks);

		if (flag) {
			Shaders.endWeather();
		}

		GlStateManager.depthMask(true);
		renderglobal.renderWorldBorder(entity, partialTicks);

		if (flag) {
			ShadersRender.renderHand0(this, partialTicks, pass);
			Shaders.preWater();
		}

		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.alphaFunc(516, 0.1F);
		this.setupFog(0, partialTicks);
		GlStateManager.enableBlend();
		GlStateManager.depthMask(false);
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.shadeModel(7425);
		this.mc.mcProfiler.endStartSection("translucent");

		if (flag) {
			Shaders.beginWater();
		}

		renderglobal.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, partialTicks, pass, entity);

		if (flag) {
			Shaders.endWater();
		}

		if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
			RenderHelper.enableStandardItemLighting();
			this.mc.mcProfiler.endStartSection("entities");
			Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
			this.mc.renderGlobal.renderEntities(entity, icamera, partialTicks);
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
			RenderHelper.disableStandardItemLighting();
		}

		GlStateManager.shadeModel(7424);
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.disableFog();

		if ((entity.posY + entity.getEyeHeight()) >= (128.0D + this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
			this.mc.mcProfiler.endStartSection("aboveClouds");
			this.renderCloudsCheck(renderglobal, partialTicks, pass);
		}

		if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
			this.mc.mcProfiler.endStartSection("forge_render_last");
			Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { renderglobal, Float.valueOf(partialTicks) });
		}

		this.mc.mcProfiler.endStartSection("hand");
		boolean flag2 = ReflectorForge.renderFirstPersonHand(this.mc.renderGlobal, partialTicks, pass);

		if (!flag2 && this.renderHand && !Shaders.isShadowPass) {
			if (flag) {
				ShadersRender.renderHand1(this, partialTicks, pass);
				Shaders.renderCompositeFinal();
			}

			GlStateManager.clear(256);

			if (flag) {
				ShadersRender.renderFPOverlay(this, partialTicks, pass);
			} else {
				this.renderHand(partialTicks, pass);
			}
		}

		if (flag) {
			Shaders.endRender();
		}
	}

    public void renderExtWorldPass(int pass, float partialTicks, long finishTimeNano)
    {
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        this.setupCameraTransform(partialTicks, pass, 0);
        GlStateManager.disableFog();
        
        //TODO: Direkt: EventRender3D
        Direkt.getInstance().getEventManager().call(new EventRender3D(partialTicks));
	      
    }
	
	private void renderCloudsCheck(RenderGlobal renderGlobalIn, float partialTicks, int pass) {
		if ((this.mc.gameSettings.renderDistanceChunks >= 4) && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
			this.mc.mcProfiler.endStartSection("clouds");
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
			GlStateManager.matrixMode(5888);
			GlStateManager.pushMatrix();
			this.setupFog(0, partialTicks);
			renderGlobalIn.renderClouds(partialTicks, pass);
			GlStateManager.disableFog();
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.clipDistance);
			GlStateManager.matrixMode(5888);
		}
	}

	private void addRainParticles() {
		float f = this.mc.theWorld.getRainStrength(1.0F);

		if (!Config.isRainFancy()) {
			f /= 2.0F;
		}

		if ((f != 0.0F) && Config.isRainSplash()) {
			this.random.setSeed(this.rendererUpdateCount * 312987231L);
			Entity entity = this.mc.getRenderViewEntity();
			World world = this.mc.theWorld;
			BlockPos blockpos = new BlockPos(entity);
			int i = 10;
			double d0 = 0.0D;
			double d1 = 0.0D;
			double d2 = 0.0D;
			int j = 0;
			int k = (int) (100.0F * f * f);

			if (this.mc.gameSettings.particleSetting == 1) {
				k >>= 1;
			} else if (this.mc.gameSettings.particleSetting == 2) {
				k = 0;
			}

			for (int l = 0; l < k; ++l) {
				BlockPos blockpos1 = world.getPrecipitationHeight(blockpos.add(this.random.nextInt(10) - this.random.nextInt(10), 0, this.random.nextInt(10) - this.random.nextInt(10)));
				Biome biome = world.getBiomeGenForCoords(blockpos1);
				BlockPos blockpos2 = blockpos1.down();
				IBlockState iblockstate = world.getBlockState(blockpos2);

				if ((blockpos1.getY() <= (blockpos.getY() + 10)) && (blockpos1.getY() >= (blockpos.getY() - 10)) && biome.canRain() && (biome.getFloatTemperature(blockpos1) >= 0.15F)) {
					double d3 = this.random.nextDouble();
					double d4 = this.random.nextDouble();
					AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox(world, blockpos2);

					if ((iblockstate.getMaterial() != Material.LAVA) && (iblockstate.getBlock() != Blocks.field_189877_df)) {
						if (iblockstate.getMaterial() != Material.AIR) {
							++j;

							if (this.random.nextInt(j) == 0) {
								d0 = blockpos2.getX() + d3;
								d1 = (blockpos2.getY() + 0.1F + axisalignedbb.maxY) - 1.0D;
								d2 = blockpos2.getZ() + d4;
							}

							this.mc.theWorld.spawnParticle(EnumParticleTypes.WATER_DROP, blockpos2.getX() + d3, blockpos2.getY() + 0.1F + axisalignedbb.maxY, blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D,
									new int[0]);
						}
					} else {
						this.mc.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, blockpos1.getY() + 0.1F - axisalignedbb.minY, blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D,
								new int[0]);
					}
				}
			}

			if ((j > 0) && (this.random.nextInt(3) < this.rainSoundCounter++)) {
				this.rainSoundCounter = 0;

				if ((d1 > blockpos.getY() + 1) && (world.getPrecipitationHeight(blockpos).getY() > MathHelper.floor_float(blockpos.getY()))) {
					this.mc.theWorld.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
				} else {
					this.mc.theWorld.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
				}
			}
		}
	}

	/**
	 * Render rain and snow
	 */
	protected void renderRainSnow(float partialTicks) {
		if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
			WorldProvider worldprovider = this.mc.theWorld.provider;
			Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);

			if (object != null) {
				Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.mc.theWorld, this.mc });
				return;
			}
		}

		float f5 = this.mc.theWorld.getRainStrength(partialTicks);

		if (f5 > 0.0F) {
			if (Config.isRainOff()) { return; }

			this.enableLightmap();
			Entity entity = this.mc.getRenderViewEntity();
			World world = this.mc.theWorld;
			int i = MathHelper.floor_double(entity.posX);
			int j = MathHelper.floor_double(entity.posY);
			int k = MathHelper.floor_double(entity.posZ);
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer vertexbuffer = tessellator.getBuffer();
			GlStateManager.disableCull();
			GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.alphaFunc(516, 0.1F);
			double d0 = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * partialTicks);
			double d1 = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partialTicks);
			double d2 = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * partialTicks);
			int l = MathHelper.floor_double(d1);
			int i1 = 5;

			if (Config.isRainFancy()) {
				i1 = 10;
			}

			int j1 = -1;
			float f = this.rendererUpdateCount + partialTicks;
			vertexbuffer.setTranslation(-d0, -d1, -d2);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int k1 = k - i1; k1 <= (k + i1); ++k1) {
				for (int l1 = i - i1; l1 <= (i + i1); ++l1) {
					int i2 = (((((k1 - k) + 16) * 32) + l1) - i) + 16;
					double d3 = this.rainXCoords[i2] * 0.5D;
					double d4 = this.rainYCoords[i2] * 0.5D;
					blockpos$mutableblockpos.set(l1, 0, k1);
					Biome biome = world.getBiomeGenForCoords(blockpos$mutableblockpos);

					if (biome.canRain() || biome.getEnableSnow()) {
						int j2 = world.getPrecipitationHeight(blockpos$mutableblockpos).getY();
						int k2 = j - i1;
						int l2 = j + i1;

						if (k2 < j2) {
							k2 = j2;
						}

						if (l2 < j2) {
							l2 = j2;
						}

						int i3 = j2;

						if (j2 < l) {
							i3 = l;
						}

						if (k2 != l2) {
							this.random.setSeed(((l1 * l1 * 3121) + (l1 * 45238971)) ^ ((k1 * k1 * 418711) + (k1 * 13761)));
							blockpos$mutableblockpos.set(l1, k2, k1);
							float f1 = biome.getFloatTemperature(blockpos$mutableblockpos);

							if (world.getBiomeProvider().getTemperatureAtHeight(f1, j2) >= 0.15F) {
								if (j1 != 0) {
									if (j1 >= 0) {
										tessellator.draw();
									}

									j1 = 0;
									this.mc.getTextureManager().bindTexture(RAIN_TEXTURES);
									vertexbuffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
								}

								double d5 = (-((double) ((this.rendererUpdateCount + (l1 * l1 * 3121) + (l1 * 45238971) + (k1 * k1 * 418711) + (k1 * 13761)) & 31) + (double) partialTicks) / 32.0D)
										* (3.0D + this.random.nextDouble());
								double d6 = l1 + 0.5F - entity.posX;
								double d7 = k1 + 0.5F - entity.posZ;
								float f2 = MathHelper.sqrt_double((d6 * d6) + (d7 * d7)) / i1;
								float f3 = (((1.0F - (f2 * f2)) * 0.5F) + 0.5F) * f5;
								blockpos$mutableblockpos.set(l1, i3, k1);
								int j3 = world.getCombinedLight(blockpos$mutableblockpos, 0);
								int k3 = (j3 >> 16) & 65535;
								int l3 = j3 & 65535;
								vertexbuffer.pos((l1 - d3) + 0.5D, l2, (k1 - d4) + 0.5D).tex(0.0D, (k2 * 0.25D) + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
								vertexbuffer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D, (k2 * 0.25D) + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
								vertexbuffer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D, (l2 * 0.25D) + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
								vertexbuffer.pos((l1 - d3) + 0.5D, k2, (k1 - d4) + 0.5D).tex(0.0D, (l2 * 0.25D) + d5).color(1.0F, 1.0F, 1.0F, f3).lightmap(k3, l3).endVertex();
							} else {
								if (j1 != 1) {
									if (j1 >= 0) {
										tessellator.draw();
									}

									j1 = 1;
									this.mc.getTextureManager().bindTexture(SNOW_TEXTURES);
									vertexbuffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
								}

								double d8 = -((this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
								double d9 = this.random.nextDouble() + (f * 0.01D * ((float) this.random.nextGaussian()));
								double d10 = this.random.nextDouble() + (f * (float) this.random.nextGaussian() * 0.001D);
								double d11 = l1 + 0.5F - entity.posX;
								double d12 = k1 + 0.5F - entity.posZ;
								float f6 = MathHelper.sqrt_double((d11 * d11) + (d12 * d12)) / i1;
								float f4 = (((1.0F - (f6 * f6)) * 0.3F) + 0.5F) * f5;
								blockpos$mutableblockpos.set(l1, i3, k1);
								int i4 = ((world.getCombinedLight(blockpos$mutableblockpos, 0) * 3) + 15728880) / 4;
								int j4 = (i4 >> 16) & 65535;
								int k4 = i4 & 65535;
								vertexbuffer.pos((l1 - d3) + 0.5D, l2, (k1 - d4) + 0.5D).tex(0.0D + d9, (k2 * 0.25D) + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
								vertexbuffer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D + d9, (k2 * 0.25D) + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
								vertexbuffer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D + d9, (l2 * 0.25D) + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
								vertexbuffer.pos((l1 - d3) + 0.5D, k2, (k1 - d4) + 0.5D).tex(0.0D + d9, (l2 * 0.25D) + d8 + d10).color(1.0F, 1.0F, 1.0F, f4).lightmap(j4, k4).endVertex();
							}
						}
					}
				}
			}

			if (j1 >= 0) {
				tessellator.draw();
			}

			vertexbuffer.setTranslation(0.0D, 0.0D, 0.0D);
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.alphaFunc(516, 0.1F);
			this.disableLightmap();
		}
	}

	/**
	 * Setup orthogonal projection for rendering GUI screen overlays
	 */
	public void setupOverlayRendering() {
		ScaledResolution scaledresolution = new ScaledResolution(this.mc);
		GlStateManager.clear(256);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();
		GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();
		GlStateManager.translate(0.0F, 0.0F, -2000.0F);
	}

	/**
	 * calculates fog and calls glClearColor
	 */
	private void updateFogColor(float partialTicks) {
		World world = this.mc.theWorld;
		Entity entity = this.mc.getRenderViewEntity();
		float f = 0.25F + ((0.75F * this.mc.gameSettings.renderDistanceChunks) / 32.0F);
		f = 1.0F - (float) Math.pow(f, 0.25D);
		Vec3d vec3d = world.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
		vec3d = CustomColors.getWorldSkyColor(vec3d, world, this.mc.getRenderViewEntity(), partialTicks);
		float f1 = (float) vec3d.xCoord;
		float f2 = (float) vec3d.yCoord;
		float f3 = (float) vec3d.zCoord;
		Vec3d vec3d1 = world.getFogColor(partialTicks);
		vec3d1 = CustomColors.getWorldFogColor(vec3d1, world, this.mc.getRenderViewEntity(), partialTicks);
		this.fogColorRed = (float) vec3d1.xCoord;
		this.fogColorGreen = (float) vec3d1.yCoord;
		this.fogColorBlue = (float) vec3d1.zCoord;

		if (this.mc.gameSettings.renderDistanceChunks >= 4) {
			double d0 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) > 0.0F ? -1.0D : 1.0D;
			Vec3d vec3d2 = new Vec3d(d0, 0.0D, 0.0D);
			float f5 = (float) entity.getLook(partialTicks).dotProduct(vec3d2);

			if (f5 < 0.0F) {
				f5 = 0.0F;
			}

			if (f5 > 0.0F) {
				float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);

				if (afloat != null) {
					f5 = f5 * afloat[3];
					this.fogColorRed = (this.fogColorRed * (1.0F - f5)) + (afloat[0] * f5);
					this.fogColorGreen = (this.fogColorGreen * (1.0F - f5)) + (afloat[1] * f5);
					this.fogColorBlue = (this.fogColorBlue * (1.0F - f5)) + (afloat[2] * f5);
				}
			}
		}

		this.fogColorRed += (f1 - this.fogColorRed) * f;
		this.fogColorGreen += (f2 - this.fogColorGreen) * f;
		this.fogColorBlue += (f3 - this.fogColorBlue) * f;
		float f8 = world.getRainStrength(partialTicks);

		if (f8 > 0.0F) {
			float f4 = 1.0F - (f8 * 0.5F);
			float f10 = 1.0F - (f8 * 0.4F);
			this.fogColorRed *= f4;
			this.fogColorGreen *= f4;
			this.fogColorBlue *= f10;
		}

		float f9 = world.getThunderStrength(partialTicks);

		if (f9 > 0.0F) {
			float f11 = 1.0F - (f9 * 0.5F);
			this.fogColorRed *= f11;
			this.fogColorGreen *= f11;
			this.fogColorBlue *= f11;
		}

		IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);

		if (this.cloudFog) {
			Vec3d vec3d3 = world.getCloudColour(partialTicks);
			this.fogColorRed = (float) vec3d3.xCoord;
			this.fogColorGreen = (float) vec3d3.yCoord;
			this.fogColorBlue = (float) vec3d3.zCoord;
		} else if (iblockstate.getMaterial() == Material.WATER) {
			float f12 = 0.0F;

			if (entity instanceof EntityLivingBase) {
				f12 = EnchantmentHelper.getRespirationModifier((EntityLivingBase) entity) * 0.2F;

				if (((EntityLivingBase) entity).isPotionActive(MobEffects.WATER_BREATHING)) {
					f12 = (f12 * 0.3F) + 0.6F;
				}
			}

			this.fogColorRed = 0.02F + f12;
			this.fogColorGreen = 0.02F + f12;
			this.fogColorBlue = 0.2F + f12;
			Vec3d vec3d4 = CustomColors.getUnderwaterColor(this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0D, this.mc.getRenderViewEntity().posZ);

			if (vec3d4 != null) {
				this.fogColorRed = (float) vec3d4.xCoord;
				this.fogColorGreen = (float) vec3d4.yCoord;
				this.fogColorBlue = (float) vec3d4.zCoord;
			}
		} else if (iblockstate.getMaterial() == Material.LAVA) {
			this.fogColorRed = 0.6F;
			this.fogColorGreen = 0.1F;
			this.fogColorBlue = 0.0F;
		}

		float f13 = this.fogColor2 + ((this.fogColor1 - this.fogColor2) * partialTicks);
		this.fogColorRed *= f13;
		this.fogColorGreen *= f13;
		this.fogColorBlue *= f13;
		double d1 = (entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * partialTicks)) * world.provider.getVoidFogYFactor();

		if ((entity instanceof EntityLivingBase) && ((EntityLivingBase) entity).isPotionActive(MobEffects.BLINDNESS)) {
			int i = ((EntityLivingBase) entity).getActivePotionEffect(MobEffects.BLINDNESS).getDuration();

			if (i < 20) {
				d1 *= 1.0F - (i / 20.0F);
			} else {
				d1 = 0.0D;
			}
		}

		if (d1 < 1.0D) {
			if (d1 < 0.0D) {
				d1 = 0.0D;
			}

			d1 = d1 * d1;
			this.fogColorRed = (float) (this.fogColorRed * d1);
			this.fogColorGreen = (float) (this.fogColorGreen * d1);
			this.fogColorBlue = (float) (this.fogColorBlue * d1);
		}

		if (this.bossColorModifier > 0.0F) {
			float f14 = this.bossColorModifierPrev + ((this.bossColorModifier - this.bossColorModifierPrev) * partialTicks);
			this.fogColorRed = (this.fogColorRed * (1.0F - f14)) + (this.fogColorRed * 0.7F * f14);
			this.fogColorGreen = (this.fogColorGreen * (1.0F - f14)) + (this.fogColorGreen * 0.6F * f14);
			this.fogColorBlue = (this.fogColorBlue * (1.0F - f14)) + (this.fogColorBlue * 0.6F * f14);
		}

		if ((entity instanceof EntityLivingBase) && ((EntityLivingBase) entity).isPotionActive(MobEffects.NIGHT_VISION)) {
			float f15 = this.getNightVisionBrightness((EntityLivingBase) entity, partialTicks);
			float f6 = 1.0F / this.fogColorRed;

			if (f6 > (1.0F / this.fogColorGreen)) {
				f6 = 1.0F / this.fogColorGreen;
			}

			if (f6 > (1.0F / this.fogColorBlue)) {
				f6 = 1.0F / this.fogColorBlue;
			}

			this.fogColorRed = (this.fogColorRed * (1.0F - f15)) + (this.fogColorRed * f6 * f15);
			this.fogColorGreen = (this.fogColorGreen * (1.0F - f15)) + (this.fogColorGreen * f6 * f15);
			this.fogColorBlue = (this.fogColorBlue * (1.0F - f15)) + (this.fogColorBlue * f6 * f15);
		}

		if (this.mc.gameSettings.anaglyph) {
			float f16 = ((this.fogColorRed * 30.0F) + (this.fogColorGreen * 59.0F) + (this.fogColorBlue * 11.0F)) / 100.0F;
			float f17 = ((this.fogColorRed * 30.0F) + (this.fogColorGreen * 70.0F)) / 100.0F;
			float f7 = ((this.fogColorRed * 30.0F) + (this.fogColorBlue * 70.0F)) / 100.0F;
			this.fogColorRed = f16;
			this.fogColorGreen = f17;
			this.fogColorBlue = f7;
		}

		if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
			Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor,
					new Object[] { this, entity, iblockstate, Float.valueOf(partialTicks), Float.valueOf(this.fogColorRed), Float.valueOf(this.fogColorGreen), Float.valueOf(this.fogColorBlue) });
			Reflector.postForgeBusEvent(object);
			this.fogColorRed = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getRed, new Object[0]);
			this.fogColorGreen = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getGreen, new Object[0]);
			this.fogColorBlue = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getBlue, new Object[0]);
		}

		Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
	}

	/**
	 * Sets up the fog to be rendered. If the arg passed in is -1 the fog starts at 0 and goes to 80% of far plane distance and is used for sky rendering.
	 */
	private void setupFog(int startCoords, float partialTicks) {
		this.fogStandard = false;
		Entity entity = this.mc.getRenderViewEntity();
		GlStateManager.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
		GlStateManager.glNormal3f(0.0F, -1.0F, 0.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);
		float f = -1.0F;

		if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
			f = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, new Object[] { this, entity, iblockstate, Float.valueOf(partialTicks), Float.valueOf(0.1F) });
		}

		if (f >= 0.0F) {
			GlStateManager.setFogDensity(f);
		} else if ((entity instanceof EntityLivingBase) && ((EntityLivingBase) entity).isPotionActive(MobEffects.BLINDNESS)) {
			float f2 = 5.0F;
			int i = ((EntityLivingBase) entity).getActivePotionEffect(MobEffects.BLINDNESS).getDuration();

			if (i < 20) {
				f2 = 5.0F + ((this.farPlaneDistance - 5.0F) * (1.0F - (i / 20.0F)));
			}

			if (Config.isShaders()) {
				Shaders.setFog(GlStateManager.FogMode.LINEAR);
			} else {
				GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
			}

			if (startCoords == -1) {
				GlStateManager.setFogStart(0.0F);
				GlStateManager.setFogEnd(f2 * 0.8F);
			} else {
				GlStateManager.setFogStart(f2 * 0.25F);
				GlStateManager.setFogEnd(f2);
			}

			if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
				GlStateManager.glFogi(34138, 34139);
			}
		} else if (this.cloudFog) {
			if (Config.isShaders()) {
				Shaders.setFog(GlStateManager.FogMode.EXP);
			} else {
				GlStateManager.setFog(GlStateManager.FogMode.EXP);
			}

			GlStateManager.setFogDensity(0.1F);
		} else if (iblockstate.getMaterial() == Material.WATER) {
			if (Config.isShaders()) {
				Shaders.setFog(GlStateManager.FogMode.EXP);
			} else {
				GlStateManager.setFog(GlStateManager.FogMode.EXP);
			}

			if (entity instanceof EntityLivingBase) {
				if (((EntityLivingBase) entity).isPotionActive(MobEffects.WATER_BREATHING)) {
					GlStateManager.setFogDensity(0.01F);
				} else {
					GlStateManager.setFogDensity(0.1F - (EnchantmentHelper.getRespirationModifier((EntityLivingBase) entity) * 0.03F));
				}
			} else {
				GlStateManager.setFogDensity(0.1F);
			}

			if (Config.isClearWater()) {
				GlStateManager.setFogDensity(0.02F);
			}
		} else if (iblockstate.getMaterial() == Material.LAVA) {
			if (Config.isShaders()) {
				Shaders.setFog(GlStateManager.FogMode.EXP);
			} else {
				GlStateManager.setFog(GlStateManager.FogMode.EXP);
			}

			GlStateManager.setFogDensity(2.0F);
		} else {
			float f1 = this.farPlaneDistance;
			this.fogStandard = true;

			if (Config.isShaders()) {
				Shaders.setFog(GlStateManager.FogMode.LINEAR);
			} else {
				GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
			}

			if (startCoords == -1) {
				GlStateManager.setFogStart(0.0F);
				GlStateManager.setFogEnd(f1);
			} else {
				GlStateManager.setFogStart(f1 * Config.getFogStart());
				GlStateManager.setFogEnd(f1);
			}

			if (GLContext.getCapabilities().GL_NV_fog_distance) {
				if (Config.isFogFancy()) {
					GlStateManager.glFogi(34138, 34139);
				}

				if (Config.isFogFast()) {
					GlStateManager.glFogi(34138, 34140);
				}
			}

			if (this.mc.theWorld.provider.doesXZShowFog((int) entity.posX, (int) entity.posZ) || this.mc.ingameGUI.getBossOverlay().shouldCreateFog()) {
				GlStateManager.setFogStart(f1 * 0.05F);
				GlStateManager.setFogEnd(f1);
			}

			if (Reflector.ForgeHooksClient_onFogRender.exists()) {
				Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, new Object[] { this, entity, iblockstate, Float.valueOf(partialTicks), Integer.valueOf(startCoords), Float.valueOf(f1) });
			}
		}

		GlStateManager.enableColorMaterial();
		GlStateManager.enableFog();
		GlStateManager.colorMaterial(1028, 4608);
	}

	/**
	 * Update and return fogColorBuffer with the RGBA values passed as arguments
	 */
	private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha) {
		if (Config.isShaders()) {
			Shaders.setFogColor(red, green, blue);
		}

		this.fogColorBuffer.clear();
		this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
		this.fogColorBuffer.flip();
		return this.fogColorBuffer;
	}

	public MapItemRenderer getMapItemRenderer() {
		return this.theMapItemRenderer;
	}

	private void waitForServerThread() {
		this.serverWaitTimeCurrent = 0;

		if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
			if (this.mc.isIntegratedServerRunning()) {
				IntegratedServer integratedserver = this.mc.getIntegratedServer();

				if (integratedserver != null) {
					boolean flag = this.mc.isGamePaused();

					if (!flag && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
						if (this.serverWaitTime > 0) {
							Lagometer.timerServer.start();
							Config.sleep(this.serverWaitTime);
							Lagometer.timerServer.end();
							this.serverWaitTimeCurrent = this.serverWaitTime;
						}

						long i = System.nanoTime() / 1000000L;

						if ((this.lastServerTime != 0L) && (this.lastServerTicks != 0)) {
							long j = i - this.lastServerTime;

							if (j < 0L) {
								this.lastServerTime = i;
								j = 0L;
							}

							if (j >= 50L) {
								this.lastServerTime = i;
								int k = integratedserver.getTickCounter();
								int l = k - this.lastServerTicks;

								if (l < 0) {
									this.lastServerTicks = k;
									l = 0;
								}

								if ((l < 1) && (this.serverWaitTime < 100)) {
									this.serverWaitTime += 2;
								}

								if ((l > 1) && (this.serverWaitTime > 0)) {
									--this.serverWaitTime;
								}

								this.lastServerTicks = k;
							}
						} else {
							this.lastServerTime = i;
							this.lastServerTicks = integratedserver.getTickCounter();
							this.avgServerTickDiff = 1.0F;
							this.avgServerTimeDiff = 50.0F;
						}
					} else {
						if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
							Config.sleep(20L);
						}

						this.lastServerTime = 0L;
						this.lastServerTicks = 0;
					}
				}
			}
		} else {
			this.lastServerTime = 0L;
			this.lastServerTicks = 0;
		}
	}

	private void frameInit() {
		if (!this.initialized) {
			TextureUtils.registerResourceListener();

			if ((Config.getBitsOs() == 64) && (Config.getBitsJre() == 32)) {
				Config.setNotify64BitJava(true);
			}

			this.initialized = true;
		}

		Config.checkDisplayMode();
		World world = this.mc.theWorld;

		if (world != null) {
			if (Config.getNewRelease() != null) {
				String s = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
				String s1 = s + " " + Config.getNewRelease();
				TextComponentString textcomponentstring = new TextComponentString(I18n.format("of.message.newVersion", new Object[] { s1 }));
				this.mc.ingameGUI.getChatGUI().printChatMessage(textcomponentstring);
				Config.setNewRelease((String) null);
			}

			if (Config.isNotify64BitJava()) {
				Config.setNotify64BitJava(false);
				TextComponentString textcomponentstring1 = new TextComponentString(I18n.format("of.message.java64Bit", new Object[0]));
				this.mc.ingameGUI.getChatGUI().printChatMessage(textcomponentstring1);
			}
		}

		if (this.mc.currentScreen instanceof GuiMainMenu) {
			this.updateMainMenu((GuiMainMenu) this.mc.currentScreen);
		}

		if (this.updatedWorld != world) {
			RandomMobs.worldChanged(this.updatedWorld, world);
			Config.updateThreadPriorities();
			this.lastServerTime = 0L;
			this.lastServerTicks = 0;
			this.updatedWorld = world;
		}

		if (!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
			Shaders.configAntialiasingLevel = 0;
		}
	}

	private void frameFinish() {
		if (this.mc.theWorld != null) {
			long i = System.currentTimeMillis();

			if (i > (this.lastErrorCheckTimeMs + 10000L)) {
				this.lastErrorCheckTimeMs = i;
				int j = GlStateManager.glGetError();

				if (j != 0) {
					String s = GLU.gluErrorString(j);
					TextComponentString textcomponentstring = new TextComponentString(I18n.format("of.message.openglError", new Object[] { Integer.valueOf(j), s }));
					this.mc.ingameGUI.getChatGUI().printChatMessage(textcomponentstring);
				}
			}
		}
	}

	private void updateMainMenu(GuiMainMenu p_updateMainMenu_1_) {
		try {
			String s = null;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int i = calendar.get(5);
			int j = calendar.get(2) + 1;

			if ((i == 8) && (j == 4)) {
				s = "Happy birthday, OptiFine!";
			}

			if ((i == 14) && (j == 8)) {
				s = "Happy birthday, sp614x!";
			}

			if (s == null) { return; }

			Field[] afield = GuiMainMenu.class.getDeclaredFields();

			for (Field element : afield) {
				if (element.getType() == String.class) {
					element.setAccessible(true);
					element.set(p_updateMainMenu_1_, s);
					break;
				}
			}
		} catch (Throwable var8) {
			;
		}
	}

	public boolean setFxaaShader(int p_setFxaaShader_1_) {
		if (!OpenGlHelper.isFramebufferEnabled()) {
			return false;
		} else if ((this.theShaderGroup != null) && (this.theShaderGroup != this.fxaaShaders[2]) && (this.theShaderGroup != this.fxaaShaders[4])) {
			return true;
		} else if ((p_setFxaaShader_1_ != 2) && (p_setFxaaShader_1_ != 4)) {
			if (this.theShaderGroup == null) {
				return true;
			} else {
				this.theShaderGroup.deleteShaderGroup();
				this.theShaderGroup = null;
				return true;
			}
		} else if ((this.theShaderGroup != null) && (this.theShaderGroup == this.fxaaShaders[p_setFxaaShader_1_])) {
			return true;
		} else if (this.mc.theWorld == null) {
			return true;
		} else {
			this.loadShader(new ResourceLocation("shaders/post/fxaa_of_" + p_setFxaaShader_1_ + "x.json"));
			this.fxaaShaders[p_setFxaaShader_1_] = this.theShaderGroup;
			return this.useShader;
		}
	}

	public static void func_189692_a(FontRenderer p_189692_0_, String p_189692_1_, float p_189692_2_, float p_189692_3_, float p_189692_4_, int p_189692_5_, float p_189692_6_, float p_189692_7_,
			boolean p_189692_8_, boolean p_189692_9_) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(p_189692_2_, p_189692_3_, p_189692_4_);
		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-p_189692_6_, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((p_189692_8_ ? -1 : 1) * p_189692_7_, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-0.025F, -0.025F, 0.025F);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);

		if (!p_189692_9_) {
			GlStateManager.disableDepth();
		}

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		int i = p_189692_0_.getStringWidth(p_189692_1_) / 2;
		GlStateManager.disableTexture2D();
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		vertexbuffer.pos(-i - 1, -1 + p_189692_5_, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		vertexbuffer.pos(-i - 1, 8 + p_189692_5_, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		vertexbuffer.pos(i + 1, 8 + p_189692_5_, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		vertexbuffer.pos(i + 1, -1 + p_189692_5_, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();

		if (!p_189692_9_) {
			p_189692_0_.drawString(p_189692_1_, -p_189692_0_.getStringWidth(p_189692_1_) / 2, p_189692_5_, 553648127);
			GlStateManager.enableDepth();
		}

		GlStateManager.depthMask(true);
		p_189692_0_.drawString(p_189692_1_, -p_189692_0_.getStringWidth(p_189692_1_) / 2, p_189692_5_, p_189692_9_ ? 553648127 : -1);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
