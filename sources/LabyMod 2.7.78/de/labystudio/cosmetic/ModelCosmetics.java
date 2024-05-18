package de.labystudio.cosmetic;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Timings;
import de.labystudio.utils.LeftHand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModelCosmetics extends ModelBiped
{
    private ModelRenderer bipedDeadmau5Head;
    ModelRenderer wolfTail;
    private ModelRenderer wing;
    private ModelRenderer wingTip;
    ModelRenderer ocelotTail;
    ModelRenderer ocelotTail2;
    HashMap<UUID, Long> wingState;
    private ModelRenderer[] witherBody;
    private ModelRenderer[] witherHeads;
    private ModelRenderer[] blazeSticks;
    private ModelRenderer witchHat;
    private ModelRenderer halo;
    private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    private static final ResourceLocation blazeTexture = new ResourceLocation("textures/entity/blaze.png");
    private static final ResourceLocation witherTexture = new ResourceLocation("textures/entity/wither/wither.png");

    public ModelCosmetics(float p_i46304_1_, boolean p_i46304_2_)
    {
        super(p_i46304_1_, 0.0F, 64, 64);
        this.wingState = new HashMap();
        this.blazeSticks = new ModelRenderer[12];
        this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
        this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
        this.wolfTail = new ModelRenderer(this, 19, 18);
        this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.wolfTail.setRotationPoint(-0.2F, 10.0F, 3.0F);
        this.wolfTail.isHidden = true;
        this.setTextureOffset("body.scale", 220, 53);
        this.setTextureOffset("body.body", 0, 0);
        this.setTextureOffset("wingtip.bone", 112, 136);
        this.setTextureOffset("wing.skin", -56, 88);
        this.setTextureOffset("wing.bone", 112, 88);
        this.setTextureOffset("wingtip.skin", -56, 144);
        int i = this.textureWidth;
        int j = this.textureHeight;
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.wing = new ModelRenderer(this, "wing");
        this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
        this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
        this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 1, 56);
        this.wing.isHidden = true;
        this.wingTip = new ModelRenderer(this, "wingtip");
        this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
        this.wingTip.isHidden = true;
        this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
        this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 1, 56);
        this.wing.addChild(this.wingTip);
        this.textureWidth = i;
        this.textureHeight = j;
        this.ocelotTail = new ModelRenderer(this, 58, 13);
        this.ocelotTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
        this.ocelotTail.rotateAngleX = 0.9F;
        this.ocelotTail.setRotationPoint(0.0F, 15.0F, 8.0F);
        this.ocelotTail2 = new ModelRenderer(this, 58, 15);
        this.ocelotTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 1);
        this.ocelotTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
        this.ocelotTail.isHidden = true;
        this.ocelotTail2.isHidden = true;

        for (int k = 0; k < this.blazeSticks.length; ++k)
        {
            this.blazeSticks[k] = new ModelRenderer(this, 0, 16);
            this.blazeSticks[k].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
            this.blazeSticks[k].isHidden = true;
        }

        this.textureWidth = 64;
        this.textureHeight = 64;
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
        this.textureWidth = i;
        this.textureHeight = j;
        this.witchHat = (new ModelRenderer(this)).setTextureSize(64, 128);
        this.witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
        this.witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
        this.halo = new ModelRenderer(this, 0, 0);
        this.halo.addBox(-3.0F, -6.0F, -1.0F, 6, 1, 1, p_i46304_1_);
        this.halo.isHidden = true;
        ModelRenderer modelrenderer2 = (new ModelRenderer(this)).setTextureSize(64, 128);
        modelrenderer2.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelrenderer2.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
        modelrenderer2.rotateAngleX = -0.05235988F;
        modelrenderer2.rotateAngleZ = 0.02617994F;
        this.witchHat.addChild(modelrenderer2);
        ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(64, 128);
        modelrenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelrenderer.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
        modelrenderer.rotateAngleX = -0.10471976F;
        modelrenderer.rotateAngleZ = 0.05235988F;
        modelrenderer2.addChild(modelrenderer);
        ModelRenderer modelrenderer1 = (new ModelRenderer(this)).setTextureSize(64, 128);
        modelrenderer1.setRotationPoint(1.75F, -2.0F, 2.0F);
        modelrenderer1.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
        modelrenderer1.rotateAngleX = -0.20943952F;
        modelrenderer1.rotateAngleZ = 0.10471976F;
        modelrenderer.addChild(modelrenderer1);
        this.witchHat.isHidden = true;
    }

    public ModelCosmetics(float p_i46304_1_, float f, int i, int j)
    {
        this(p_i46304_1_, false);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale)
    {
        if (LeftHand.use(entityIn))
        {
            GlStateManager.scale(-1.0F, 1.0F, 1.0F);
        }

        super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
        GlStateManager.pushMatrix();

        if (entityIn.isSneaking())
        {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        if (LeftHand.use(entityIn))
        {
            GlStateManager.scale(-1.0F, 1.0F, 1.0F);
        }

        if (ConfigManager.settings.cosmetics)
        {
            Timings.start("Cosmetic Renderer");
            ArrayList<Cosmetic> arraylist = LabyMod.getInstance().getCosmeticManager().getCosmetic(entityIn);

            if (arraylist != null && entityIn instanceof AbstractClientPlayer)
            {
                AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entityIn;

                if (abstractclientplayer != null && abstractclientplayer.getLocationSkin() != null && !abstractclientplayer.isSpectator() && !abstractclientplayer.isPotionActive(Potion.invisibility))
                {
                    float f = Minecraft.getMinecraft().currentScreen == null ? LabyMod.getInstance().getPartialTicks() : 1.0F;
                    ResourceLocation resourcelocation = abstractclientplayer.getLocationSkin();

                    for (Cosmetic cosmetic : arraylist)
                    {
                        if (cosmetic != null)
                        {
                            Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation);
                            GlStateManager.color(1.0F, 1.0F, 1.0F);
                            GL11.glColor3d(1.0D, 1.0D, 1.0D);
                            GlStateManager.resetColor();

                            if (cosmetic.getType() == EnumCosmetic.HALO)
                            {
                                for (int i = 0; i < 4; ++i)
                                {
                                    float f1 = abstractclientplayer.prevRotationYawHead + (abstractclientplayer.rotationYawHead - abstractclientplayer.prevRotationYawHead) * f - (abstractclientplayer.prevRenderYawOffset + (abstractclientplayer.renderYawOffset - abstractclientplayer.prevRenderYawOffset) * f);
                                    float f20 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * f;
                                    GlStateManager.pushMatrix();
                                    GlStateManager.color(3.9F, 3.0F, 0.0F);

                                    if (LabyMod.getInstance().getCosmeticManager().colorPicker && entityIn == Minecraft.getMinecraft().thePlayer)
                                    {
                                        GL11.glColor3d((double)LabyMod.getInstance().getCosmeticManager().colorR * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorG * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorB * 0.01D);
                                    }
                                    else if (cosmetic.getData() != null)
                                    {
                                        GlStateManager.color(1.0F, 1.0F, 1.0F);
                                        GL11.glColor3d(cosmetic.a * 0.01D, cosmetic.b * 0.01D, cosmetic.c * 0.01D);
                                    }

                                    GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
                                    GlStateManager.rotate((float)(90 * i), 0.0F, 1.0F, 0.0F);
                                    float f2 = 0.2F;

                                    switch (i)
                                    {
                                        case 0:
                                            GlStateManager.translate(0.0F, 0.0F, 0.01F - f2);
                                            break;

                                        case 1:
                                            GlStateManager.translate(-0.19F + f2, 0.0F, -0.19F);
                                            break;

                                        case 2:
                                            GlStateManager.translate(0.0F, 0.0F, -0.38F + f2);
                                            break;

                                        case 3:
                                            GlStateManager.translate(0.19F - f2, 0.0F, -0.19F);
                                    }

                                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/misc/forcefield.png"));
                                    float f3 = p_78088_4_ / 10.0F;
                                    f3 = MathHelper.cos(f3);
                                    f3 = f3 * 0.03F;
                                    GlStateManager.translate(0.0F, -0.4F - p_78088_3_ * 0.1F - f3, 0.0F);
                                    this.halo.isHidden = false;
                                    this.halo.render(scale);
                                    this.halo.isHidden = true;
                                    GlStateManager.popMatrix();
                                }
                            }

                            if (cosmetic.getType() == EnumCosmetic.TOOL)
                            {
                                GlStateManager.pushMatrix();

                                if (entityIn.isSneaking())
                                {
                                    GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
                                }

                                GlStateManager.scale(0.8D, 0.8D, 0.8D);
                                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                                GlStateManager.translate(0.0D, 0.3D, -0.22D);
                                ItemStack itemstack;

                                if (cosmetic.getData() == null)
                                {
                                    itemstack = ((AbstractClientPlayer)entityIn).getHeldItem();
                                }
                                else
                                {
                                    itemstack = new ItemStack(Item.getItemById((int)cosmetic.b));
                                }

                                Minecraft.getMinecraft().getItemRenderer().renderItem((EntityLivingBase)entityIn, itemstack, ItemCameraTransforms.TransformType.FIXED);
                                GlStateManager.popMatrix();
                            }

                            if (cosmetic.getType() == EnumCosmetic.HAT)
                            {
                                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/entity/witch.png"));
                                float f9 = abstractclientplayer.prevRotationYawHead + (abstractclientplayer.rotationYawHead - abstractclientplayer.prevRotationYawHead) * f - (abstractclientplayer.prevRenderYawOffset + (abstractclientplayer.renderYawOffset - abstractclientplayer.prevRenderYawOffset) * f);
                                float f13 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * f;
                                GlStateManager.pushMatrix();

                                if (entityIn.isSneaking())
                                {
                                    GlStateManager.translate(0.0D, 0.1D, 0.0D);
                                }

                                if (LabyMod.getInstance().getCosmeticManager().colorPicker && entityIn == Minecraft.getMinecraft().thePlayer)
                                {
                                    GL11.glColor3d((double)LabyMod.getInstance().getCosmeticManager().colorR * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorG * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorB * 0.01D);
                                }
                                else if (cosmetic.getData() != null)
                                {
                                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                                    GL11.glColor3d(cosmetic.a * 0.01D, cosmetic.b * 0.01D, cosmetic.c * 0.01D);
                                }

                                GlStateManager.rotate(f9, 0.0F, 1.0F, 0.0F);
                                GlStateManager.rotate(f13, 1.0F, 0.0F, 0.0F);
                                this.witchHat.isHidden = false;
                                this.witchHat.render(scale);
                                this.witchHat.isHidden = true;
                                GlStateManager.popMatrix();
                            }

                            if (cosmetic.getType() == EnumCosmetic.WITHER)
                            {
                                for (ModelRenderer modelrenderer : this.witherHeads)
                                {
                                    modelrenderer.isHidden = false;
                                    modelrenderer.render(scale);
                                    modelrenderer.isHidden = true;
                                }

                                Minecraft.getMinecraft().getTextureManager().bindTexture(witherTexture);

                                for (ModelRenderer modelrenderer1 : this.witherBody)
                                {
                                    modelrenderer1.isHidden = false;
                                    modelrenderer1.render(scale);
                                    modelrenderer1.isHidden = true;
                                }
                            }

                            if (cosmetic.getType() == EnumCosmetic.BLAZE)
                            {
                                float f10 = p_78088_4_;
                                float f14 = p_78088_4_ * (float)Math.PI * -0.01F;

                                for (int l = 0; l < 4; ++l)
                                {
                                    this.blazeSticks[l].rotationPointY = -2.0F + MathHelper.cos(((float)((double)l * 1.5D) + f10) * 0.2F);
                                    this.blazeSticks[l].rotationPointX = MathHelper.cos(f14) * 10.0F;
                                    this.blazeSticks[l].rotationPointZ = MathHelper.sin(f14) * 10.0F;
                                    ++f14;
                                }

                                f14 = ((float)Math.PI / 4F) + f10 * (float)Math.PI * 0.01F;

                                for (int i1 = 4; i1 < 8; ++i1)
                                {
                                    this.blazeSticks[i1].rotationPointY = 2.0F + MathHelper.cos(((float)(i1 * 2) + f10) * 0.2F);
                                    this.blazeSticks[i1].rotationPointX = MathHelper.cos(f14) * 9.0F;
                                    this.blazeSticks[i1].rotationPointZ = MathHelper.sin(f14) * 9.0F;
                                    ++f14;
                                }

                                f14 = 0.47123894F + f10 * (float)Math.PI * -0.01F;

                                for (int j1 = 8; j1 < 12; ++j1)
                                {
                                    this.blazeSticks[j1].rotationPointY = 11.0F + MathHelper.cos(((float)j1 * 1.5F + f10) * 0.5F);
                                    this.blazeSticks[j1].rotationPointX = MathHelper.cos(f14) * 5.0F;
                                    this.blazeSticks[j1].rotationPointZ = MathHelper.sin(f14) * 5.0F;
                                    ++f14;
                                }

                                Minecraft.getMinecraft().getTextureManager().bindTexture(blazeTexture);

                                if (LabyMod.getInstance().getCosmeticManager().colorPicker && entityIn == Minecraft.getMinecraft().thePlayer)
                                {
                                    GL11.glColor3d((double)LabyMod.getInstance().getCosmeticManager().colorR * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorG * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorB * 0.01D);
                                }
                                else if (cosmetic.getData() != null)
                                {
                                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                                    GL11.glColor3d(cosmetic.a * 0.01D, cosmetic.b * 0.01D, cosmetic.c * 0.01D);
                                }

                                for (int k1 = 0; k1 < this.blazeSticks.length; ++k1)
                                {
                                    this.blazeSticks[k1].isHidden = false;
                                    this.blazeSticks[k1].render(scale);
                                    this.blazeSticks[k1].isHidden = true;
                                }
                            }

                            if (cosmetic.getType() == EnumCosmetic.DEADMAU5)
                            {
                                for (int k = 0; k < 2; ++k)
                                {
                                    float f15 = abstractclientplayer.prevRotationYawHead + (abstractclientplayer.rotationYawHead - abstractclientplayer.prevRotationYawHead) * f - (abstractclientplayer.prevRenderYawOffset + (abstractclientplayer.renderYawOffset - abstractclientplayer.prevRenderYawOffset) * f);
                                    float f16 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * f;
                                    GlStateManager.pushMatrix();
                                    GlStateManager.rotate(f15, 0.0F, 1.0F, 0.0F);
                                    GlStateManager.rotate(f16, 1.0F, 0.0F, 0.0F);
                                    GlStateManager.translate(0.375F * (float)(k * 2 - 1), 0.0F, 0.0F);
                                    GlStateManager.translate(0.0F, -0.375F, 0.0F);
                                    GlStateManager.rotate(-f16, 1.0F, 0.0F, 0.0F);
                                    GlStateManager.rotate(-f15, 0.0F, 1.0F, 0.0F);
                                    float f17 = 1.3333334F;
                                    GlStateManager.scale(f17, f17, f17);
                                    this.renderDeadmau5Head(0.0625F);
                                    GlStateManager.popMatrix();
                                }
                            }

                            if (cosmetic.getType() == EnumCosmetic.OCELOTTAIL)
                            {
                                this.ocelotTail.rotationPointY = 15.0F;
                                this.ocelotTail.rotationPointZ = 8.0F;
                                this.ocelotTail2.rotationPointY = 20.0F;
                                this.ocelotTail2.rotationPointZ = 14.0F;
                                this.ocelotTail.rotateAngleX = 0.9F;
                                GlStateManager.pushMatrix();

                                if (entityIn.isSneaking())
                                {
                                    GlStateManager.translate(0.0F, -0.35F, -0.33F);
                                    ++this.ocelotTail.rotationPointY;
                                    this.ocelotTail2.rotationPointY += -4.0F;
                                    this.ocelotTail2.rotationPointZ += 2.0F;
                                    this.ocelotTail.rotateAngleX = ((float)Math.PI / 2F);
                                    this.ocelotTail2.rotateAngleX = ((float)Math.PI / 2F);
                                }
                                else if (entityIn.isSprinting())
                                {
                                    GlStateManager.translate(0.0F, -0.2F, -0.61F);
                                    this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
                                    this.ocelotTail2.rotationPointZ += 2.0F;
                                    this.ocelotTail.rotateAngleX = ((float)Math.PI / 2F);
                                    this.ocelotTail2.rotateAngleX = ((float)Math.PI / 2F);
                                    this.ocelotTail2.rotateAngleX = 1.7278761F + ((float)Math.PI / 10F) * MathHelper.cos(p_78088_2_) * p_78088_3_;
                                }

                                if (!entityIn.isSprinting() && !entityIn.isSneaking())
                                {
                                    GlStateManager.translate(0.0F, -0.35F, -0.61F);
                                    this.ocelotTail2.rotateAngleX = 1.7278761F + ((float)Math.PI / 4F) * MathHelper.cos(p_78088_2_) * p_78088_3_;
                                }
                                else
                                {
                                    this.ocelotTail2.rotateAngleX = 1.7278761F + 0.47123894F * MathHelper.cos(p_78088_2_) * p_78088_3_;
                                }

                                this.ocelotTail.isHidden = false;
                                this.ocelotTail2.isHidden = false;
                                this.ocelotTail.render(scale);
                                this.ocelotTail2.render(scale);
                                this.ocelotTail.isHidden = true;
                                this.ocelotTail2.isHidden = true;
                                GlStateManager.popMatrix();
                            }

                            if (cosmetic.getType() == EnumCosmetic.WOLFTAIL)
                            {
                                GlStateManager.pushMatrix();

                                if (entityIn.isSneaking())
                                {
                                    GlStateManager.translate(0.0F, 0.2F, -0.25F);
                                    GlStateManager.rotate(45.0F, 45.0F, 0.0F, 0.0F);
                                }
                                else
                                {
                                    GlStateManager.translate(0.0F, 0.1F, -0.25F);
                                    GlStateManager.rotate(15.0F, 15.0F, 0.0F, 0.0F);
                                }

                                if (cosmetic.getData() != null && cosmetic.getData().equalsIgnoreCase("emotions"))
                                {
                                    float f11 = abstractclientplayer.getHealth();

                                    if (f11 > 20.0F)
                                    {
                                        f11 = 20.0F;
                                    }

                                    if (f11 < 0.0F)
                                    {
                                        f11 = 0.0F;
                                    }

                                    GlStateManager.translate(0.0F, f11 / 80.0F, f11 / 50.0F * -1.0F);
                                    GlStateManager.rotate(f11 * 2.0F, f11 * 2.0F, 0.0F, 0.0F);
                                }

                                this.wolfTail.isHidden = false;
                                this.wolfTail.renderWithRotation(scale);
                                this.wolfTail.isHidden = true;
                                GlStateManager.popMatrix();
                            }

                            if (cosmetic.getType() == EnumCosmetic.WINGS)
                            {
                                GlStateManager.pushMatrix();
                                float f12 = 100.0F;
                                boolean flag2 = this.wingState.containsKey(entityIn.getUniqueID());
                                boolean flag3 = entityIn.onGround;

                                if (!flag3 || flag2)
                                {
                                    f12 = 10.0F;
                                }

                                if (!flag2 && !flag3)
                                {
                                    this.wingState.put(entityIn.getUniqueID(), Long.valueOf(0L));
                                }

                                float f18 = p_78088_3_ + p_78088_4_ / f12;
                                float f19 = p_78088_3_ + p_78088_4_ / 100.0F;
                                float f4 = f18 * (float)Math.PI * 2.0F;
                                float f5 = 0.125F - (float)Math.cos((double)f4) * 0.2F;
                                float f6 = f19 * (float)Math.PI * 2.0F;
                                float f7 = 0.125F - (float)Math.cos((double)f6) * 0.2F;

                                if (this.wingState.containsKey(entityIn.getUniqueID()) && (int)(f5 * 100.0F) == (int)(f7 * 100.0F) && flag3)
                                {
                                    this.wingState.remove(entityIn.getUniqueID());
                                    f12 = 100.0F;
                                }

                                Minecraft.getMinecraft().getTextureManager().bindTexture(enderDragonTextures);
                                GlStateManager.scale(0.15D, 0.15D, 0.15D);
                                GlStateManager.translate(0.0D, -0.3D, 1.1D);
                                GlStateManager.rotate(50.0F, -50.0F, 0.0F, 0.0F);
                                boolean flag = false;
                                boolean flag1 = false;

                                if (LabyMod.getInstance().getCosmeticManager().colorPicker && entityIn == Minecraft.getMinecraft().thePlayer)
                                {
                                    GL11.glColor3d((double)LabyMod.getInstance().getCosmeticManager().colorR * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorG * 0.01D, (double)LabyMod.getInstance().getCosmeticManager().colorB * 0.01D);
                                }
                                else if (cosmetic.getData() != null)
                                {
                                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                                    GL11.glColor3d(cosmetic.a * 0.01D, cosmetic.b * 0.01D, cosmetic.c * 0.01D);

                                    if (cosmetic.a == -1.0D && cosmetic.b == -1.0D && cosmetic.c == -1.0D)
                                    {
                                        int j = 600;
                                        flag1 = Minecraft.getSystemTime() % (long)j / (long)(j / 2) == 0L;
                                        flag = true;

                                        if (flag1)
                                        {
                                            GL11.glColor3d(18.0D, 0.0D, 0.0D);
                                        }
                                        else
                                        {
                                            GL11.glColor3d(0.0D, 0.0D, 18.0D);
                                        }
                                    }
                                }

                                GlStateManager.disableLight(0);
                                GlStateManager.disableLight(1);

                                for (int l1 = 0; l1 < 2; ++l1)
                                {
                                    GlStateManager.enableCull();
                                    float f8 = f18 * (float)Math.PI * 2.0F;
                                    this.wing.rotateAngleX = 0.125F - (float)Math.cos((double)f8) * 0.2F;
                                    this.wing.rotateAngleY = 0.25F;
                                    this.wing.rotateAngleZ = (float)(Math.sin((double)f8) + 1.225D) * 0.3F;
                                    this.wingTip.rotateAngleZ = -((float)(Math.sin((double)(f8 + 2.0F)) + 0.5D)) * 0.75F;
                                    this.wing.isHidden = false;
                                    this.wingTip.isHidden = false;
                                    this.wing.render(scale);
                                    this.wing.isHidden = true;
                                    this.wingTip.isHidden = true;
                                    GlStateManager.scale(-1.0F, 1.0F, 1.0F);

                                    if (l1 == 0)
                                    {
                                        GlStateManager.cullFace(1028);

                                        if (flag)
                                        {
                                            if (flag1)
                                            {
                                                GL11.glColor3d(0.0D, 0.0D, 18.0D);
                                            }
                                            else
                                            {
                                                GL11.glColor3d(18.0D, 0.0D, 0.0D);
                                            }
                                        }
                                    }
                                }

                                GlStateManager.enableLight(0);
                                GlStateManager.enableLight(1);
                                GlStateManager.cullFace(1029);
                                GlStateManager.disableCull();
                                GlStateManager.enableDepth();
                                GlStateManager.popMatrix();
                            }
                        }
                    }

                    Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation);
                    GL11.glColor3d(1.0D, 1.0D, 1.0D);
                }
            }

            Timings.stop("Cosmetic Renderer");
        }

        if (LeftHand.use(entityIn))
        {
            GlStateManager.scale(-1.0F, 1.0F, 1.0F);
        }

        GlStateManager.popMatrix();
    }

    public void renderDeadmau5Head(float p_178727_1_)
    {
        copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
        this.bipedDeadmau5Head.rotationPointX = 0.0F;
        this.bipedDeadmau5Head.rotationPointY = 0.0F;
        this.bipedDeadmau5Head.render(p_178727_1_);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn)
    {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entityIn);
        this.wolfTail.rotateAngleY = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
        this.wolfTail.rotateAngleX = p_78087_2_;
    }

    public void setInvisible(boolean invisible)
    {
        super.setInvisible(invisible);
        this.bipedDeadmau5Head.showModel = invisible;
        this.wolfTail.showModel = invisible;
        this.wing.showModel = invisible;
        this.wingTip.showModel = invisible;
        this.ocelotTail.showModel = invisible;
        this.ocelotTail2.showModel = invisible;
    }
}
