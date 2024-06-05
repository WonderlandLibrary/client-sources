package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBed;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.Team;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.optifine.entity.model.CustomEntityModels;
import optifine.Config;
import optifine.Reflector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import shadersmod.client.Shaders;
import zamorozka.event.events.EventNameTag;
import zamorozka.gui.GuiIngameHook;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.ModuleManager;
import zamorozka.modules.PLAYER.NoWalkAnimation;
import zamorozka.modules.VISUALLY.CustomHitColor;
import zamorozka.modules.VISUALLY.Esp;
import zamorozka.modules.VISUALLY.HeadOverHeels;
import zamorozka.modules.VISUALLY.NameTags;
import zamorozka.modules.VISUALLY.Outline;
import zamorozka.modules.VISUALLY.ShaderESP;
import zamorozka.modules.VISUALLY.ItemEsp;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.Colors;
import zamorozka.ui.OutlineUtil;
import zamorozka.ui.OutlineUtils;

public abstract class RenderLivingBase<T extends EntityLivingBase> extends Render<T> {
	Minecraft mc = Minecraft.getMinecraft();
	private static final Logger LOGGER = LogManager.getLogger();
	private static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);
	public ModelBase mainModel;
	protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
	protected List<LayerRenderer<T>> layerRenderers = Lists.<LayerRenderer<T>>newArrayList();
	protected boolean renderMarker;
	public static float NAME_TAG_RANGE = 64.0F;
	public static float NAME_TAG_RANGE_SNEAK = 32.0F;
	public float renderLimbSwing;
	public float renderLimbSwingAmount;
	public float renderAgeInTicks;
	public float renderHeadYaw;
	public float renderHeadPitch;
	public float renderScaleFactor;
	public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");

	public RenderLivingBase(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
		super(renderManagerIn);
		this.mainModel = modelBaseIn;
		this.shadowSize = shadowSizeIn;
	}

	public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layer) {
		return this.layerRenderers.add((LayerRenderer<T>) layer);
	}

	public ModelBase getMainModel() {
		return this.mainModel;
	}

	/**
	 * Returns a rotation angle that is inbetween two other rotation angles. par1
	 * and par2 are the angles between which to interpolate, par3 is probably a
	 * float between 0.0 and 1.0 that tells us where "between" the two angles we
	 * are. Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
	 */
	protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
		float f;

		for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) {
			;
		}

		while (f >= 180.0F) {
			f -= 360.0F;
		}

		return prevYawOffset + partialTicks * f;
	}

	public void transformHeldFull3DItemLayer() {
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, entity, this, partialTicks, x, y, z)) {
			if (animateModelLiving) {
				entity.limbSwingAmount = 1.0F;
			}

			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
			this.mainModel.isRiding = entity.isRiding();

			if (Reflector.ForgeEntity_shouldRiderSit.exists()) {
				this.mainModel.isRiding = entity.isRiding() && entity.getRidingEntity() != null && Reflector.callBoolean(entity.getRidingEntity(), Reflector.ForgeEntity_shouldRiderSit);
			}

			this.mainModel.isChild = entity.isChild();

			try {
				float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
				float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
				float f2 = f1 - f;

				if (this.mainModel.isRiding && entity.getRidingEntity() instanceof EntityLivingBase) {
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity.getRidingEntity();
					f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
					f2 = f1 - f;
					float f3 = MathHelper.wrapDegrees(f2);

					if (f3 < -85.0F) {
						f3 = -85.0F;
					}

					if (f3 >= 85.0F) {
						f3 = 85.0F;
					}

					f = f1 - f3;

					if (f3 * f3 > 2500.0F) {
						f += f3 * 0.2F;
					}

					f2 = f1 - f;
				}

				float f7 = (entity instanceof EntityPlayer && entity == Minecraft.getMinecraft().player) ? entity.prevRotationPitchHead + (entity.rotationPitchHead - entity.prevRotationPitchHead) * partialTicks
						: entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
				this.renderLivingAt(entity, x, y, z);
				float f8 = this.handleRotationFloat(entity, partialTicks);
				this.rotateCorpse(entity, f8, f, partialTicks);
				float f4 = this.prepareScale(entity, partialTicks);
				float f5 = 0.0F;
				float f6 = 0.0F;

				if (!entity.isRiding()) {
					if (ModuleManager.getModule(NoWalkAnimation.class).getState()) {

					} else
						f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
					f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

					if (entity.isChild()) {
						f6 *= 3.0F;
					}

					if (f5 > 1.0F) {
						f5 = 1.0F;
					}
				}

				GlStateManager.enableAlpha();
				this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
				this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

				if (CustomEntityModels.isActive()) {
					this.renderLimbSwing = f6;
					this.renderLimbSwingAmount = f5;
					this.renderAgeInTicks = f8;
					this.renderHeadYaw = f2;
					this.renderHeadPitch = f7;
					this.renderScaleFactor = f4;
				}

				if (this.renderOutlines) {
					boolean flag1 = this.setScoreTeamColor(entity);
					if (ModuleManager.getModule(ShaderESP.class).getState()) {
						int color1 = (int) Zamorozka.settingsManager.getSettingByName("ShaderRed").getValDouble();
						int color2 = (int) Zamorozka.settingsManager.getSettingByName("ShaderGreen").getValDouble();
						int color3 = (int) Zamorozka.settingsManager.getSettingByName("ShaderBlue").getValDouble();
						int colorn = new Color(color1, color2, color3, 1).getRGB();
						GlStateManager.enableColorMaterial();
						GlStateManager.enableOutlineMode(colorn);
					} else {
						GlStateManager.enableColorMaterial();
						GlStateManager.enableOutlineMode(getTeamColor(entity));
					}
					if (!this.renderMarker) {
						this.renderModel(entity, f6, f5, f8, f2, f7, f4);
					}

					if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
						this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
					}

					GlStateManager.disableOutlineMode();
					GlStateManager.disableColorMaterial();

					if (flag1) {
						this.unsetScoreTeamColor();
					}
				} else {
					boolean flag = this.setDoRenderBrightness(entity, partialTicks);
					this.renderModel(entity, f6, f5, f8, f2, f7, f4);

					if (flag) {
						this.unsetBrightness();
					}

					GlStateManager.depthMask(true);

					if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator()) {
						this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
					}
				}

				GlStateManager.disableRescaleNormal();
			} catch (Exception exception1) {
				LOGGER.error("Couldn't render entity", (Throwable) exception1);
			}

			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.enableTexture2D();
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
			super.doRender(entity, x, y, z, entityYaw, partialTicks);

			if (Reflector.RenderLivingEvent_Post_Constructor.exists()) {
				Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, entity, this, partialTicks, x, y, z);
			}
		}
	}

	public float prepareScale(T entitylivingbaseIn, float partialTicks) {
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		this.preRenderCallback(entitylivingbaseIn, partialTicks);
		float f = 0.0625F;
		GlStateManager.translate(0.0F, -1.501F, 0.0F);
		return 0.0625F;
	}

	protected boolean setScoreTeamColor(T entityLivingBaseIn) {
		GlStateManager.disableLighting();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		return true;
	}

	protected void unsetScoreTeamColor() {
		GlStateManager.enableLighting();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	protected void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		boolean flag = this.func_193115_c(entitylivingbaseIn);
		boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);
        boolean flag4 = (ModuleManager.getModule(Outline.class).getState() && entitylivingbaseIn instanceof EntityPlayer);

		if (flag || flag1) {
			if (!this.bindEntityTexture(entitylivingbaseIn)) {
				return;
			}

			if (flag1) {
				GlStateManager.pushMatrix();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
				GlStateManager.depthMask(false);
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(770, 771);
				GlStateManager.alphaFunc(516, 0.003921569F);
			}
			
            if (flag4) {
                OutlineUtil.disableFastRender();
                GlStateManager.resetColor();
                final Color color = entitylivingbaseIn.hurtTime >= 1 ? new Color(255, 50, 50) : new Color(255, 255, 255);
                OutlineUtil.renderOne((float) Zamorozka.settingsManager.getSettingByName("OutlineWidth").getValDouble());
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                OutlineUtil.renderTwo();
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                OutlineUtil.renderThree();
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                OutlineUtil.renderFour(color);
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                OutlineUtil.renderFive();
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
            }

			if (Zamorozka.theClient.moduleManager.getModule(Esp.class).getState()) {
				if (Zamorozka.settingsManager.getSettingByName("PlayerLine").getValBoolean()) {
					if (entitylivingbaseIn instanceof EntityPlayer) {

						Color n = new Color(19, 143, 255);
						OutlineUtils.setColor(n);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderOne(1.8f);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderTwo();
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderThree();
						OutlineUtils.renderFour();
						OutlineUtils.setColor(n);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderFive();
						OutlineUtils.setColor(Color.WHITE);
					}

				}
			}

			if (Zamorozka.theClient.moduleManager.getModule(Esp.class).getState()) {
				if (Zamorozka.settingsManager.getSettingByName("Mobs").getValBoolean()) {
					if (entitylivingbaseIn instanceof EntityAnimal) {

						Color n = new Color(173, 214, 255);
						OutlineUtils.setColor(n);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderOne(0.8f);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderTwo();
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderThree();
						OutlineUtils.renderFour();
						OutlineUtils.setColor(n);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderFive();
						OutlineUtils.setColor(Color.WHITE);
					}
					if (entitylivingbaseIn instanceof EntityMob) {

						Color n = new Color(255, 19, 41);
						OutlineUtils.setColor(n);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderOne(0.8f);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderTwo();
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderThree();
						OutlineUtils.renderFour();
						OutlineUtils.setColor(n);
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
						OutlineUtils.renderFive();
						OutlineUtils.setColor(Color.WHITE);
					}
				}
			}
			boolean flag3 = (ModuleManager.getModule(Esp.class).getState() && Zamorozka.settingsManager.getSettingByName("Outline").getValBoolean() && entitylivingbaseIn instanceof EntityPlayer && mc.gameSettings.ofFastRender == false);
			if (flag3) {
				OutlineUtil.disableFastRender();
				GlStateManager.resetColor();
				final Color color = new Color(255, 255, 255);
				OutlineUtil.renderOne(2);
				this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
				OutlineUtil.renderTwo();
				this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
				OutlineUtil.renderThree();
				this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
				OutlineUtil.renderFour(color);
				this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
				OutlineUtil.renderFive();

			}
			this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			if (ModuleManager.getModule(Esp.class).getState()) {
				if (Zamorozka.settingsManager.getSettingByName("FullStroke").getValBoolean()) {
					String mode2 = Zamorozka.instance.settingsManager.getSettingByName("Array Mode").getValString();
					if ((entitylivingbaseIn instanceof EntityPlayer)) {

						GL11.glPushMatrix();
						{

							GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
							{

								OutlineUtils.setColor(Zamorozka.getClientColors());

								GL11.glDisable(GL11.GL_ALPHA_TEST);
								GL11.glDisable(GL11.GL_TEXTURE_2D);
								GL11.glDisable(GL11.GL_LIGHTING);
								GL11.glEnable(GL11.GL_BLEND);
								GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
								GL11.glLineWidth(0.8F);
								GL11.glDisable(GL11.GL_DEPTH_TEST);
								this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
								GL11.glEnable(GL11.GL_DEPTH_TEST);
								GL11.glEnable(GL11.GL_LIGHTING);
								GL11.glEnable(GL11.GL_LINE_SMOOTH);
								GL11.glEnable(GL11.GL_STENCIL_TEST);
								GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);

							}
							this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
							GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
							GL11.glEnable(GL11.GL_DEPTH_TEST);
							GL11.glDepthMask(true);
							GL11.glDisable(GL11.GL_STENCIL_TEST);
							GL11.glDisable(GL11.GL_LINE_SMOOTH);
							GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
							GL11.glEnable(GL11.GL_BLEND);

							GL11.glEnable(GL11.GL_TEXTURE_2D);
							GL11.glEnable(GL11.GL_ALPHA_TEST);
							GL11.glPopAttrib();
							OutlineUtils.setColor(Zamorozka.getClientColors());

						}
						GL11.glPopMatrix();
					} else {
						this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
					}
				}
				// this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount,
				// ageInTicks, netHeadYaw, headPitch, scaleFactor);

				if (flag1) {
					GlStateManager.disableBlend();
					GlStateManager.alphaFunc(516, 0.1F);
					GlStateManager.popMatrix();
					GlStateManager.depthMask(true);
				}

			} else {
				this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

				if (flag1) {
					GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
				}
			}
		}
	}

	protected boolean func_193115_c(T p_193115_1_) {
		return !p_193115_1_.isInvisible() || this.renderOutlines;
	}

	protected boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks) {
		return this.setBrightness(entityLivingBaseIn, partialTicks, true);
	}

	protected boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures) {
		float f = entitylivingbaseIn.getBrightness();
		int i = this.getColorMultiplier(entitylivingbaseIn, f, partialTicks);
		boolean flag = (i >> 24 & 255) > 0;
		boolean flag1 = entitylivingbaseIn.hurtTime > 0 || entitylivingbaseIn.deathTime > 0;

		if (!flag && !flag1) {
			return false;
		} else if (!flag && !combineTextures) {
			return false;
		} else {
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			GlStateManager.enableTexture2D();
			GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GlStateManager.enableTexture2D();
			GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
			this.brightnessBuffer.position(0);

			if (flag1) {
				if (ModuleManager.getModule(CustomHitColor.class).getState()) {
					float red = (float) (Zamorozka.settingsManager.getSettingByName("DamageRed").getValDouble() / 255);
					float blue = (float) (Zamorozka.settingsManager.getSettingByName("DamageBlue").getValDouble() / 255);
					float green = (float) (Zamorozka.settingsManager.getSettingByName("DamageGreen").getValDouble() / 255);
					float alpha = (float) (Zamorozka.settingsManager.getSettingByName("DamageAlpha").getValDouble());
					this.brightnessBuffer.put(red);
					this.brightnessBuffer.put(green);
					this.brightnessBuffer.put(blue);
					this.brightnessBuffer.put(alpha);

					if (Config.isShaders()) {
						Shaders.setEntityColor(red, green, blue, alpha);
					}
				} else {
					this.brightnessBuffer.put(1.0F);
					this.brightnessBuffer.put(0.0F);
					this.brightnessBuffer.put(0.0F);
					this.brightnessBuffer.put(0.3F);

					if (Config.isShaders()) {
						Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
					}
				}

			} else {
				float f1 = (float) (i >> 24 & 255) / 255.0F;
				float f2 = (float) (i >> 16 & 255) / 255.0F;
				float f3 = (float) (i >> 8 & 255) / 255.0F;
				float f4 = (float) (i & 255) / 255.0F;
				this.brightnessBuffer.put(f2);
				this.brightnessBuffer.put(f3);
				this.brightnessBuffer.put(f4);
				this.brightnessBuffer.put(1.0F - f1);

				if (Config.isShaders()) {
					Shaders.setEntityColor(f2, f3, f4, 1.0F - f1);
				}
			}

			this.brightnessBuffer.flip();
			GlStateManager.glTexEnv(8960, 8705, this.brightnessBuffer);
			GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
			GlStateManager.enableTexture2D();
			GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
			GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
			GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
			return true;
		}
	}

	protected void unsetBrightness() {
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
		GlStateManager.disableTexture2D();
		GlStateManager.bindTexture(0);
		GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
		GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		if (Config.isShaders()) {
			Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
		}
	}

	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(T entityLivingBaseIn, double x, double y, double z) {
		GlStateManager.translate((float) x, (float) y, (float) z);
	}

	protected void rotateCorpse(T entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
		GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);

		if (entityLiving.deathTime > 0) {
			float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = MathHelper.sqrt(f);

			if (f > 1.0F) {
				f = 1.0F;
			}

			GlStateManager.rotate(f * this.getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
		} else {
			String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());

			if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s)) && (!(entityLiving instanceof EntityPlayer) || ((EntityPlayer) entityLiving).isWearing(EnumPlayerModelParts.CAPE))) {
				GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			}
			if (s != null && ModuleManager.getModule(HeadOverHeels.class).getState() && entityLiving instanceof EntityPlayerSP) {
				GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
				GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
			}

		}
	}

	/**
	 * Returns where in the swing animation the living entity is (from 0 to 1). Args
	 * : entity, partialTickTime
	 */
	protected float getSwingProgress(T livingBase, float partialTickTime) {
		return livingBase.getSwingProgress(partialTickTime);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	protected float handleRotationFloat(T livingBase, float partialTicks) {
		return (float) livingBase.ticksExisted + partialTicks;
	}

	protected void renderLayers(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
		for (LayerRenderer<T> layerrenderer : this.layerRenderers) {
			boolean flag = this.setBrightness(entitylivingbaseIn, partialTicks, layerrenderer.shouldCombineTextures());
			layerrenderer.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);

			if (flag) {
				this.unsetBrightness();
			}
		}
	}

	protected float getDeathMaxRotation(T entityLivingBaseIn) {
		return 90.0F;
	}

	/**
	 * Gets an RGBA int color multiplier to apply.
	 */
	protected int getColorMultiplier(T entitylivingbaseIn, float lightBrightness, float partialTickTime) {
		return 0;
	}

	/**
	 * Allows the render to do state modifications necessary before the model is
	 * rendered.
	 */
	protected void preRenderCallback(T entitylivingbaseIn, float partialTickTime) {
	}

	public void renderName(T entity, double x, double y, double z) {
		if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor, entity, this, x, y, z)) {
			if (this.canRenderName(entity)) {
				double d0 = entity.getDistanceSqToEntity(this.renderManager.renderViewEntity);
				float f = entity.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;

				if (d0 < (double) (f * f)) {
					EventNameTag eventNameTag = new EventNameTag(entity, entity.getDisplayName().getFormattedText());
					eventNameTag.call();
					if (eventNameTag.isCancelled())
						return;
					String s = entity.getDisplayName().getFormattedText();

					GlStateManager.alphaFunc(516, 0.1F);
					this.renderEntityName(entity, x, y, z, s, d0);
				}

			}

			if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists()) {
				Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor, entity, this, x, y, z);
			}
		}
	}

	protected boolean canRenderName(T entity) {
		EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
		boolean flag = !entity.isInvisibleToPlayer(entityplayersp);

		if (entity != entityplayersp) {
			Team team = entity.getTeam();
			Team team1 = entityplayersp.getTeam();

			if (team != null) {
				Team.EnumVisible team$enumvisible = team.getNameTagVisibility();

				switch (team$enumvisible) {
				case ALWAYS:
					return flag;

				case NEVER:
					return false;

				case HIDE_FOR_OTHER_TEAMS:
					return team1 == null ? flag : team.isSameTeam(team1) && (team.getSeeFriendlyInvisiblesEnabled() || flag);

				case HIDE_FOR_OWN_TEAM:
					return team1 == null ? flag : !team.isSameTeam(team1) && flag;

				default:
					return true;
				}
			}
		}

		return Minecraft.isGuiEnabled() && entity != this.renderManager.renderViewEntity && flag && !entity.isBeingRidden();
	}

	public List<LayerRenderer<T>> getLayerRenderers() {
		return this.layerRenderers;
	}

	static {
		int[] aint = TEXTURE_BRIGHTNESS.getTextureData();

		for (int i = 0; i < 256; ++i) {
			aint[i] = -1;
		}

		TEXTURE_BRIGHTNESS.updateDynamicTexture();
	}
}