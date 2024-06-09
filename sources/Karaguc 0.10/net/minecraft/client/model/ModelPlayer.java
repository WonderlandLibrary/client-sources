package net.minecraft.client.model;

import me.Emir.Karaguc.Karaguc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelPlayer extends ModelBiped
{
    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;
    private ModelRenderer bipedCape;
    private ModelRenderer bipedDeadmau5Head;
    private boolean smallArms;
    private static final String __OBFID = "CL_00002626";

    /*
     * Wings
     */
    private ModelRenderer wing;
    private ModelRenderer wingTip;
    private boolean flying = false;
    private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");



    public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_)
    {
        super(p_i46304_1_, 0.0F, 64, 64);
        this.smallArms = p_i46304_2_;
        this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
        this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
        this.bipedCape = new ModelRenderer(this, 0, 0);
        this.bipedCape.setTextureSize(64, 32);
        this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46304_1_);

        /*
         * Wings
         */
        /*if(Karaguc.instance.moduleManager.getModuleByName("Wings").isToggled()) {
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
        }*/



        if (p_i46304_2_)
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
        }
        else
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
        }

        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
        this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
        this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46304_1_ + 0.25F);
        this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale)
    {
        super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
        GlStateManager.pushMatrix();

        if (this.isChild)
        {
            float f = 2.0F;
            GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);
        }
        else
        {
            if (entityIn.isSneaking())
            {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);

            /*
             * Wings
             */
            /*if(Karaguc.instance.moduleManager.getModuleByName("Wings").isToggled()) {
                if (entityIn == Minecraft.getMinecraft().thePlayer) {
                    GlStateManager.pushMatrix();
                    float anSpeed = 100.0f;
                    if (!entityIn.onGround || this.flying) {
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
                    if (Minecraft.getMinecraft().thePlayer != null) {
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
                            this.wing.render(scale);
                            this.wing.isHidden = true;
                            this.wingTip.isHidden = true;
                            GlStateManager.color(1.0f, 1.0f, 1.0f);
                            if (i2 == 0) {
                                GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                            }
                            ++i2;
                        }
                    }
                }
            }*/
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

    public void renderCape(float p_178728_1_)
    {
        this.bipedCape.render(p_178728_1_);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn)
    {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, entityIn);
        copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        copyModelAngles(this.bipedBody, this.bipedBodyWear);
    }

    public void renderRightArm()
    {
        this.bipedRightArm.render(0.0625F);
        this.bipedRightArmwear.render(0.0625F);
    }

    public void renderLeftArm()
    {
        this.bipedLeftArm.render(0.0625F);
        this.bipedLeftArmwear.render(0.0625F);
    }

    public void setInvisible(boolean invisible)
    {
        super.setInvisible(invisible);
        this.bipedLeftArmwear.showModel = invisible;
        this.bipedRightArmwear.showModel = invisible;
        this.bipedLeftLegwear.showModel = invisible;
        this.bipedRightLegwear.showModel = invisible;
        this.bipedBodyWear.showModel = invisible;
        this.bipedCape.showModel = invisible;
        this.bipedDeadmau5Head.showModel = invisible;
    }

    public void postRenderArm(float scale)
    {
        if (this.smallArms)
        {
            ++this.bipedRightArm.rotationPointX;
            this.bipedRightArm.postRender(scale);
            --this.bipedRightArm.rotationPointX;
        }
        else
        {
            this.bipedRightArm.postRender(scale);
        }
    }
}
