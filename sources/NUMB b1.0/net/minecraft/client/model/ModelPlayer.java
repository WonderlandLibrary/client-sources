package net.minecraft.client.model;

import us.loki.legit.Client;
import us.loki.legit.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ModelPlayer extends ModelBiped {
	public ModelRenderer field_178734_a;
	public ModelRenderer field_178732_b;
	public ModelRenderer field_178733_c;
	public ModelRenderer field_178731_d;
	public ModelRenderer field_178730_v;
	private ModelRenderer field_178729_w;
	private ModelRenderer field_178736_x;
	private ModelRenderer wing;
	private ModelRenderer wingTip;
	public ModelRenderer villagerNose;
	private ModelRenderer bipedDeadmau5Head;
	private ModelRenderer[] blazeSticks;
	private ModelRenderer horn;
	private ModelRenderer halo;
	private ModelRenderer[] witherBody;
	private ModelRenderer[] witherHeads;
	private boolean field_178735_y;
	boolean flying = false;
	private static final ResourceLocation enderDragonTextures = new ResourceLocation(
			"textures/entity/enderdragon/dragon.png");
	private static final ResourceLocation blazeTexture = new ResourceLocation("textures/entity/blaze.png");
	private static final ResourceLocation haloTexture = new ResourceLocation("halo.png");
	private static final ResourceLocation witherTexture = new ResourceLocation("textures/entity/wither/wither.png");
	private static final String __OBFID = "CL_00002626";

	public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_) {
		super(p_i46304_1_, 0.0f, 64, 64);
		this.field_178735_y = p_i46304_2_;
		this.blazeSticks = new ModelRenderer[12];
		for (int k = 0; k < this.blazeSticks.length; ++k) {
			this.blazeSticks[k] = new ModelRenderer(this, 0, 16);
			this.blazeSticks[k].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
			this.blazeSticks[k].isHidden = true;
		}
		this.witherBody = new ModelRenderer[2];
		this.witherBody[0] = new ModelRenderer(this, 0, 16);
		this.witherBody[0].addBox(-10.0F, -1.9F, -0.5F, 20, 3, 3, p_i46304_1_);
		this.witherBody[0].isHidden = true;
		this.witherBody[1] = (new ModelRenderer(this)).setTextureSize(this.textureWidth, this.textureHeight);
		this.witherBody[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
		this.witherBody[1].isHidden = true;
		this.witherHeads = new ModelRenderer[2];
		this.witherHeads[0] = new ModelRenderer(this, 3, 3);
		this.witherHeads[0].addBox(-4.0F, -11.0F, -4.0F, 6, 6, 6, p_i46304_1_);
		this.witherHeads[0].rotationPointX = -8.0F;
		this.witherHeads[0].rotationPointY = 4.0F;
		this.witherHeads[0].isHidden = true;
		this.witherHeads[1] = new ModelRenderer(this, 3, 3);
		this.witherHeads[1].addBox(-4.0F, -11.0F, -4.0F, 6, 6, 6, p_i46304_1_);
		this.witherHeads[1].rotationPointX = 10.0F;
		this.witherHeads[1].rotationPointY = 4.0F;
		this.witherHeads[1].isHidden = true;
		this.setTextureOffset("body.scale", 220, 53);
		this.setTextureOffset("body.body", 0, 0);
		this.halo = new ModelRenderer(this, 0, 0);
		this.halo.addBox(-3.0F, -6.0F, -1.0F, 6, 1, 1, p_i46304_1_);
		this.halo.isHidden = true;
		this.horn = new ModelRenderer(this, 24, 0);
		this.horn.addBox(-3.0F, -6.0F, -1.0F, 2, 4, 1, p_i46304_1_);
		this.horn.isHidden = true;
		this.field_178736_x = new ModelRenderer(this, 24, 0);
		this.field_178736_x.addBox(-3.0f, -6.0f, -1.0f, 6, 6, 1, p_i46304_1_);
		this.field_178729_w = new ModelRenderer(this, 0, 0);
		this.field_178729_w.setTextureSize(64, 32);
		this.field_178729_w.addBox(-5.0f, 0.0f, -1.0f, 10, 16, 1, p_i46304_1_);
		this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
		this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
		this.bipedDeadmau5Head.isHidden = true;
		this.setTextureOffset("wingtip.bone", 112, 136);
		this.setTextureOffset("wing.skin", -56, 88);
		this.setTextureOffset("wing.bone", 112, 88);
		this.setTextureOffset("wingtip.skin", -56, 144);
		int bw2 = this.textureWidth;
		int bh2 = this.textureWidth;
		this.textureWidth = 256;
		this.textureWidth = 256;
		this.wing = new ModelRenderer(this, "wing");
		this.wing.setTextureSize(256, 256);
		this.wing.setRotationPoint(-12.0f, 5.0f, 2.0f);
		this.wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
		this.wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
		this.wing.isHidden = true;
		this.wingTip = new ModelRenderer(this, "wingtip");
		this.wingTip.setTextureSize(256, 256);
		this.wingTip.setRotationPoint(-56.0f, 0.0f, 0.0f);
		this.wingTip.isHidden = true;
		this.wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
		this.wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
		this.wing.addChild(this.wingTip);
		this.textureWidth = bw2;
		this.textureWidth = bh2;
		if (p_i46304_2_) {
			this.bipedLeftArm = new ModelRenderer(this, 32, 48);
			this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_);
			this.bipedLeftArm.setRotationPoint(5.0f, 2.5f, 0.0f);
			this.bipedRightArm = new ModelRenderer(this, 40, 16);
			this.bipedRightArm.addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_);
			this.bipedRightArm.setRotationPoint(-5.0f, 2.5f, 0.0f);
			this.field_178734_a = new ModelRenderer(this, 48, 48);
			this.field_178734_a.addBox(-1.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_ + 0.25f);
			this.field_178734_a.setRotationPoint(5.0f, 2.5f, 0.0f);
			this.field_178732_b = new ModelRenderer(this, 40, 32);
			this.field_178732_b.addBox(-2.0f, -2.0f, -2.0f, 3, 12, 4, p_i46304_1_ + 0.25f);
			this.field_178732_b.setRotationPoint(-5.0f, 2.5f, 10.0f);
		} else {
			this.bipedLeftArm = new ModelRenderer(this, 32, 48);
			this.bipedLeftArm.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, p_i46304_1_);
			this.bipedLeftArm.setRotationPoint(5.0f, 2.0f, 0.0f);
			this.field_178734_a = new ModelRenderer(this, 48, 48);
			this.field_178734_a.addBox(-1.0f, -2.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
			this.field_178734_a.setRotationPoint(5.0f, 2.0f, 0.0f);
			this.field_178732_b = new ModelRenderer(this, 40, 32);
			this.field_178732_b.addBox(-3.0f, -2.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
			this.field_178732_b.setRotationPoint(-5.0f, 2.0f, 10.0f);
		}
		this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
		this.bipedLeftLeg.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i46304_1_);
		this.bipedLeftLeg.setRotationPoint(1.9f, 12.0f, 0.0f);
		this.field_178733_c = new ModelRenderer(this, 0, 48);
		this.field_178733_c.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
		this.field_178733_c.setRotationPoint(1.9f, 12.0f, 0.0f);
		this.field_178731_d = new ModelRenderer(this, 0, 32);
		this.field_178731_d.addBox(-2.0f, 0.0f, -2.0f, 4, 12, 4, p_i46304_1_ + 0.25f);
		this.field_178731_d.setRotationPoint(-1.9f, 12.0f, 0.0f);
		this.field_178730_v = new ModelRenderer(this, 16, 32);
		this.field_178730_v.addBox(-4.0f, 0.0f, -2.0f, 8, 12, 4, p_i46304_1_ + 0.25f);
		this.field_178730_v.setRotationPoint(0.0f, 0.0f, 0.0f);
	}

	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
			float p_78088_6_, float p_78088_7_) {
		super.render(p_78088_1_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_);
		GlStateManager.pushMatrix();
		if (this.isChild) {
			float var8 = 2.0f;
			GlStateManager.scale(1.0f / var8, 1.0f / var8, 1.0f / var8);
			GlStateManager.translate(0.0f, 24.0f * p_78088_7_, 0.0f);
			this.field_178733_c.render(p_78088_7_);
			this.field_178731_d.render(p_78088_7_);
			this.field_178734_a.render(p_78088_7_);
			this.field_178732_b.render(p_78088_7_);
			this.field_178730_v.render(p_78088_7_);
		} else {
			if (p_78088_1_.isSneaking()) {
				GlStateManager.translate(0.0f, 0.2f, 0.0f);
			}
			this.field_178733_c.render(p_78088_7_);
			this.field_178731_d.render(p_78088_7_);
			this.field_178734_a.render(p_78088_7_);
			this.field_178732_b.render(p_78088_7_);
			this.field_178730_v.render(p_78088_7_);
			Minecraft.getMinecraft();
			if (p_78088_1_ == Minecraft.getMinecraft().thePlayer) {
				GlStateManager.pushMatrix();
				float anSpeed = 100.0f;
				if (!p_78088_1_.onGround || this.flying) {
					anSpeed = 10.0f;
					this.flying = true;
				}
				float f1 = p_78088_3_ + p_78088_4_ / anSpeed;
				float f2 = p_78088_3_ + p_78088_4_ / 100.0f;
				float f3 = f1 * 3.1415927f * 2.0f;
				float f4 = 0.125f - (float) Math.cos(f3) * 0.2f;
				float fs5 = f2 * 3.1415927f * 2.0f;
				float f7 = 0.125f - (float) Math.cos(fs5) * 0.2f;
				if (this.flying && (int) (f4 * 100.0f) == (int) (f7 * 100.0f)) {
					this.flying = false;
					anSpeed = 100.0f;
				}
				if (Client.instance.setmgr.getSettingByName("Freakshow").getValBoolean()) {
					for (ModelRenderer modelrenderer5 : this.witherHeads) {
						modelrenderer5.isHidden = false;
						modelrenderer5.render(p_78088_7_);
						modelrenderer5.isHidden = true;
					}

					Minecraft.getMinecraft().getTextureManager().bindTexture(witherTexture);

					for (ModelRenderer modelrenderer6 : this.witherBody) {
						modelrenderer6.isHidden = false;
						modelrenderer6.render(p_78088_7_);
						modelrenderer6.isHidden = true;
					}
				}

				if (Client.instance.setmgr.getSettingByName("BlazeSticks").getValBoolean()) {
					{
						float f21 = p_78088_4_;
						float f25 = p_78088_4_ * (float) Math.PI * -0.01F;

						for (int k1 = 0; k1 < 4; ++k1) {
							this.blazeSticks[k1].rotationPointY = -2.0F
									+ MathHelper.cos(((float) ((double) k1 * 1.5D) + f21) * 0.2F);
							this.blazeSticks[k1].rotationPointX = MathHelper.cos(f25) * 10.0F;
							this.blazeSticks[k1].rotationPointZ = MathHelper.sin(f25) * 10.0F;
							++f25;
						}

						f25 = ((float) Math.PI / 4F) + f21 * (float) Math.PI * 0.01F;

						for (int l1 = 4; l1 < 8; ++l1) {
							this.blazeSticks[l1].rotationPointY = 2.0F
									+ MathHelper.cos(((float) (l1 * 2) + f21) * 0.2F);
							this.blazeSticks[l1].rotationPointX = MathHelper.cos(f25) * 9.0F;
							this.blazeSticks[l1].rotationPointZ = MathHelper.sin(f25) * 9.0F;
							++f25;
						}

						f25 = 0.47123894F + f21 * (float) Math.PI * -0.01F;

						for (int i2 = 8; i2 < 12; ++i2) {
							this.blazeSticks[i2].rotationPointY = 11.0F
									+ MathHelper.cos(((float) i2 * 1.5F + f21) * 0.5F);
							this.blazeSticks[i2].rotationPointX = MathHelper.cos(f25) * 5.0F;
							this.blazeSticks[i2].rotationPointZ = MathHelper.sin(f25) * 5.0F;
							++f25;
						}

						Minecraft.getMinecraft().getTextureManager().bindTexture(blazeTexture);

						for (int j2 = 0; j2 < this.blazeSticks.length; ++j2) {
							this.blazeSticks[j2].isHidden = false;
							this.blazeSticks[j2].render(p_78088_7_);
							this.blazeSticks[j2].isHidden = true;
						}
					}
				}
				if (Client.instance.setmgr.getSettingByName("Angel").getValBoolean()) {
					{
						for (int i = 0; i < 4; ++i) {
							GlStateManager.pushMatrix();
							GlStateManager.color(3.9F, 3.0F, 0.0F);
							GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
							GlStateManager.rotate((float) (90 * i), 0.0F, 1.0F, 0.0F);
							float f24 = 0.2F;

							switch (i) {
							case 0:
								GlStateManager.translate(0.0F, 0.0F, 0.01F - f24);
								break;

							case 1:
								GlStateManager.translate(-0.19F + f24, 0.0F, -0.19F);
								break;

							case 2:
								GlStateManager.translate(0.0F, 0.0F, -0.38F + f24);
								break;

							case 3:
								GlStateManager.translate(0.19F - f24, 0.0F, -0.19F);
							}

							Minecraft.getMinecraft().getTextureManager().bindTexture(haloTexture);
							float f28 = p_78088_4_ / 10.0F;
							f28 = MathHelper.cos(f28);
							f28 = f28 * 0.03F;
							GlStateManager.translate(0.0F, -0.4F - p_78088_3_ * 0.1F - f28, 0.0F);
							this.halo.isHidden = false;
							this.halo.render(p_78088_7_);
							this.halo.isHidden = true;
							GlStateManager.popMatrix();
						}
					}
				}
				if (Client.instance.setmgr.getSettingByName("Demon").getValBoolean()) {
					{
						for (int k = 0; k < 2; ++k) {
							for (int i1 = 0; i1 < 3; ++i1) {
								GlStateManager.pushMatrix();
								GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
								GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
								GlStateManager.translate(0.0D, 0.0D, k == 0 ? -0.04D : 0.04D);
								GlStateManager.rotate((float) (k == 0 ? 180 : 0), 0.0F, 1.0F, 0.0F);

								switch (i1) {
								case 0:
									GlStateManager.translate(0.03D, -0.3D, 0.0D);
									GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
									break;

								case 1:
									GlStateManager.translate(-0.28D, -0.45D, -0.02D);
									GlStateManager.scale(0.8D, 0.8D, 0.8D);
									GlStateManager.rotate(35.0F, 0.0F, 0.0F, 1.0F);
									break;

								case 2:
									GlStateManager.translate(-0.1D, -0.88D, -0.04D);
									GlStateManager.scale(0.4D, 0.4D, 0.4D);
									GlStateManager.rotate(-55.0F, 0.0F, 0.0F, 1.0F);
								}

								float f29 = 1.3333334F;
								GlStateManager.scale(f29, f29, f29);
								this.horn.rotationPointX = 0.0F;
								this.horn.rotationPointY = 0.0F;
								this.horn.isHidden = false;
								this.horn.render(0.0625F);
								this.horn.isHidden = true;
								GlStateManager.popMatrix();
							}
						}
					}
				}

				if (Minecraft.getMinecraft().thePlayer != null) {
					if (Client.instance.setmgr.getSettingByName("Wings").getValBoolean()) {
						Minecraft.getMinecraft().getTextureManager().bindTexture(enderDragonTextures);
						GlStateManager.scale(0.15, 0.15, 0.15);
						GlStateManager.translate(0.0, -0.3, 1.1);
						GlStateManager.rotate(50.0f, -50.0f, 0.0f, 0.0f);
						boolean x2 = false;
						boolean index = false;
						int i2 = 0;
						while (i2 < 2) {
							GlStateManager.color(0.0f, 0.475f, 0.7f);
							GlStateManager.enableCull();
							float f6 = f1 * 3.1415927f * 2.0f;
							this.wing.rotateAngleX = 0.125f - (float) Math.cos(f6) * 0.2f;
							this.wing.rotateAngleY = 0.25f;
							this.wing.rotateAngleZ = (float) (Math.sin(f6) + 1.225) * 0.3f;
							this.wingTip.rotateAngleZ = (-(float) (Math.sin(f6 + 2.0f) + 0.5)) * 0.75f;
							this.wing.isHidden = false;
							this.wingTip.isHidden = false;
							this.wing.render(p_78088_7_);
							this.wing.isHidden = true;
							this.wingTip.isHidden = true;
							GlStateManager.color(1.0f, 1.0f, 1.0f);
							if (i2 == 0) {
								GlStateManager.scale(-1.0f, 1.0f, 1.0f);
							}
							++i2;
						}
					}
					GlStateManager.popMatrix();
				}
			}
			GlStateManager.popMatrix();
		}
	}

	public void func_178727_b(float p_178727_1_) {
		ModelPlayer.func_178685_a(this.bipedHead, this.field_178736_x);
		this.field_178736_x.rotationPointX = 0.0f;
		this.field_178736_x.rotationPointY = 0.0f;
		this.field_178736_x.render(p_178727_1_);
	}

	public void renderDeadmau5Head(float p_178727_1_) {
		ModelPlayer.func_178685_a(this.bipedHead, this.bipedDeadmau5Head);
		this.bipedDeadmau5Head.rotationPointX = 0.0F;
		this.bipedDeadmau5Head.rotationPointY = 0.0F;
		this.bipedDeadmau5Head.render(p_178727_1_);
	}

	public void func_178728_c(float p_178728_1_) {
		this.field_178729_w.render(p_178728_1_);
	}

	@Override
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_,
			float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
		super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
		ModelPlayer.func_178685_a(this.bipedLeftLeg, this.field_178733_c);
		ModelPlayer.func_178685_a(this.bipedRightLeg, this.field_178731_d);
		ModelPlayer.func_178685_a(this.bipedLeftArm, this.field_178734_a);
		ModelPlayer.func_178685_a(this.bipedRightArm, this.field_178732_b);
		ModelPlayer.func_178685_a(this.bipedBody, this.field_178730_v);
		this.field_178729_w.rotationPointY = p_78087_7_.isSneaking() ? 2.0f : 0.0f;
	}

	public void func_178725_a() {
		this.bipedRightArm.render(0.0625f);
		this.field_178732_b.render(0.0625f);
	}

	public void func_178726_b() {
		this.bipedLeftArm.render(0.0625f);
		this.field_178734_a.render(0.0625f);
	}

	@Override
	public void func_178719_a(boolean p_178719_1_) {
		super.func_178719_a(p_178719_1_);
		this.bipedDeadmau5Head.showModel = p_178719_1_;
		this.field_178734_a.showModel = p_178719_1_;
		this.field_178732_b.showModel = p_178719_1_;
		this.field_178733_c.showModel = p_178719_1_;
		this.field_178731_d.showModel = p_178719_1_;
		this.field_178730_v.showModel = p_178719_1_;
		this.field_178729_w.showModel = p_178719_1_;
		this.field_178736_x.showModel = p_178719_1_;
		if (Minecraft.getMinecraft().thePlayer != null) {
			this.wing.showModel = true;
			this.wingTip.showModel = true;
		}
	}

	@Override
	public void postRenderHiddenArm(float p_178718_1_) {
		if (this.field_178735_y) {
			this.bipedRightArm.rotationPointX += 1.0f;
			this.bipedRightArm.postRender(p_178718_1_);
			this.bipedRightArm.rotationPointX -= 1.0f;
		} else {
			this.bipedRightArm.postRender(p_178718_1_);
		}
	}
}
