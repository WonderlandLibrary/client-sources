package de.labystudio.cosmetics;

import java.util.HashMap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import us.loki.legit.Client;
import us.loki.legit.modules.ModuleManager;
import us.loki.legit.utils.DrawUtils;

import org.lwjgl.opengl.GL11;

public class ModelCosmetics extends ModelBiped
{
    private ModelRenderer[] blazeSticks;
    private ModelRenderer witchHat;
    private ModelRenderer horn;
    private ModelRenderer halo;
    private ModelRenderer crown;
    private ModelRenderer crownDiamond;
    private ModelRenderer cap;
    public static int fps = 0;
    private float partialTicks;
    private static final HashMap<String, ResourceLocation> flags = new HashMap();
    private static final HashMap<String, ResourceLocation> caps = new HashMap();
    private static final ResourceLocation witchTexture = new ResourceLocation("flags/default.png");
    private static final ResourceLocation capTexture = new ResourceLocation("flags/g59.png");
    private static final ResourceLocation crownTexture = new ResourceLocation("crown.png");

    public ModelCosmetics(float p_i46304_1_, boolean p_i46304_2_)
    {
        super(p_i46304_1_, 0.0F, 64, 64);
        this.witchHat = (new ModelRenderer(this)).setTextureSize(40, 34);
        this.witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
        this.witchHat.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
        ModelRenderer modelrenderer9 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer9.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelrenderer9.setTextureOffset(0, 12).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
        modelrenderer9.rotateAngleX = -0.05235988F;
        modelrenderer9.rotateAngleZ = 0.02617994F;
        this.witchHat.addChild(modelrenderer9);
        ModelRenderer modelrenderer = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelrenderer.setTextureOffset(0, 23).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
        modelrenderer.rotateAngleX = -0.10471976F;
        modelrenderer.rotateAngleZ = 0.05235988F;
        modelrenderer9.addChild(modelrenderer);
        ModelRenderer modelrenderer1 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer1.setRotationPoint(1.75F, -2.0F, 2.0F);
        modelrenderer1.setTextureOffset(0, 31).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
        modelrenderer1.rotateAngleX = -0.20943952F;
        modelrenderer1.rotateAngleZ = 0.10471976F;
        modelrenderer.addChild(modelrenderer1);
        this.witchHat.isHidden = true;
        ModelRenderer modelrenderer2 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer2.setRotationPoint(4.0F, -2.7F, 4.0F);
        modelrenderer2.setTextureOffset(0, 12).addBox(-3.0F, 0.0F, -3.0F, 8, 3, 8);
        modelrenderer2.rotateAngleZ = 0.1F;
        ModelRenderer modelrenderer3 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer3.setRotationPoint(1.0F, -1.7F, 1.0F);
        modelrenderer3.setTextureOffset(0, 12).addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6);
        modelrenderer3.rotateAngleZ = 0.1F;
        modelrenderer2.addChild(modelrenderer3);
        ModelRenderer modelrenderer4 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer4.setRotationPoint(1.0F, -2.0F, 0.0F);
        modelrenderer4.setTextureOffset(0, 12).addBox(-1.0F, 0.0F, -2.0F, 4, 4, 4);
        modelrenderer4.rotateAngleZ = 0.6F;
        modelrenderer3.addChild(modelrenderer4);
        ModelRenderer modelrenderer5 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer5.setRotationPoint(2.0F, -3.0F, 0.0F);
        modelrenderer5.setTextureOffset(0, 12).addBox(-2.0F, 1.4F, -1.5F, 3, 2, 3);
        modelrenderer5.rotateAngleZ = 0.2F;
        modelrenderer4.addChild(modelrenderer5);
        ModelRenderer modelrenderer6 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer6.setRotationPoint(0.0F, 0.0F, 0.0F);
        modelrenderer6.setTextureOffset(0, 12).addBox(-0.5F, 0.5F, -1.0F, 4, 2, 2);
        modelrenderer6.rotateAngleZ = -0.4F;
        modelrenderer5.addChild(modelrenderer6);
        ModelRenderer modelrenderer7 = (new ModelRenderer(this)).setTextureSize(40, 34);
        modelrenderer7.setRotationPoint(0.0F, 0.0F, 0.0F);
        modelrenderer7.setTextureOffset(0, 12).addBox(3.5F, -0.5F, -0.5F, 3, 1, 1);
        modelrenderer7.rotateAngleZ = 0.8F;
        modelrenderer6.addChild(modelrenderer7);
        float f = 0.02F;
        this.crown = (new ModelRenderer(this, 0, 0)).setTextureSize(22, 7);
        this.crown.setTextureOffset(4, 0).addBox(-4.0F, 0.0F, -5.0F, 8, 2, 1, f);
        this.crown.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, -5.0F, 1, 4, 1, f);
        this.crown.setTextureOffset(0, 5).addBox(-4.0F, -1.0F, -5.0F, 1, 1, 1, f);
        this.crown.setTextureOffset(0, 5).addBox(3.0F, -1.0F, -5.0F, 1, 1, 1, f);
        this.crown.setTextureOffset(4, 5).addBox(-1.5F, -1.0F, -5.0F, 3, 1, 1, f);
        this.crown.setTextureOffset(0, 5).addBox(-0.5F, -2.0F, -5.0F, 1, 1, 1, f);
        this.crown.isHidden = true;
        this.crownDiamond = (new ModelRenderer(this, 12, 5)).setTextureSize(22, 7);
        this.crownDiamond.addBox(-0.5F, -0.0F, -6.0F, 1, 1, 1, f);
        this.crownDiamond.rotateAngleZ = 0.8F;
        this.crownDiamond.rotationPointZ = 0.5F;
        this.crownDiamond.rotationPointX = 0.4F;
        this.crownDiamond.isHidden = true;
        float f1 = 0.02F;
        this.cap = (new ModelRenderer(this, 0, 0)).setTextureSize(38, 38);
        this.cap.setTextureOffset(0, 0).addBox(-4.0F, -6.0F, -7.0F, 8, 1, 11, f1);
        this.cap.setTextureOffset(0, 12).addBox(-4.0F, -8.0F, -4.0F, 8, 2, 8, f1);
        this.cap.setTextureOffset(0, 22).addBox(-4.0F, -9.0F, -3.0F, 8, 1, 6, f1);
        this.cap.setTextureOffset(0, 29).addBox(-3.0F, -9.0F, -4.0F, 6, 1, 1, f1);
        this.cap.setTextureOffset(14, 29).addBox(-3.0F, -9.0F, 3.0F, 6, 1, 1, f1);
        this.cap.setTextureOffset(0, 31).addBox(-3.0F, -6.0F, -8.0F, 6, 1, 1, f1);
        this.cap.isHidden = true;
        float f2 = 0.01F;
    }

    public ModelCosmetics(float p_i46304_1_, float f, int i, int j)
    {
        this(p_i46304_1_, false);
    }
    public static int getRealFPS()
    {
        return fps;
    }
    public float getPartialTicks()
    {
        return this.partialTicks;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity p_78088_7_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale)
    {

        super.render(p_78088_7_, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
        GlStateManager.pushMatrix();

        if (p_78088_7_.isSneaking())
        {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        
        if(ModuleManager.getModuleByName("Cosmetics").isEnabled())
        {
                AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)p_78088_7_;
                ResourceLocation resourcelocation = abstractclientplayer.getLocationSkin();
                String username = abstractclientplayer.getNameClear();
                if (abstractclientplayer != null && abstractclientplayer.getLocationSkin() != null && !abstractclientplayer.func_175149_v() && !abstractclientplayer.isPotionActive(Potion.invisibility) && (username.equalsIgnoreCase(Minecraft.getMinecraft().session.getUsername())))
                {
                    float f = getPartialTicks();

                    if (Minecraft.getMinecraft().currentScreen != null && abstractclientplayer == Minecraft.getMinecraft().thePlayer)
                    {
                        f = 1.0F;
                    }

                    float f1 = getFirstRotationX(abstractclientplayer, f);
                    float f2 = getSecondRotationX(abstractclientplayer, f);
                    

                        if (Minecraft.getMinecraft().thePlayer != null)
                        {
                            Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation);
                            GlStateManager.color(1.0F, 1.0F, 1.0F);
                            GL11.glColor3d(1.0D, 1.0D, 1.0D);
                            GlStateManager.func_179117_G();

                          

                            if (Client.instance.setmgr.getSettingByName("Crown").getValBoolean()) {
                                for (int j = 0; j < 4; ++j)
                                {
                                    GlStateManager.pushMatrix();
                                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                                    GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
                                    GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
                                    float f26 = 1.085F;
                                    GlStateManager.scale(f26, f26, f26);

                                    if (p_78088_7_.isSneaking())
                                    {
                                        float f31 = p_78088_7_.rotationPitch * -7.0E-4F;
                                        GlStateManager.translate(0.0D, (double)(0.06F - Math.abs(f31)) + 0.02D, (double)f31);
                                    }

                                    GlStateManager.rotate((float)(90 * j), 0.0F, 1.0F, 0.0F);
                                    GlStateManager.translate(0.0D, -0.4753D, 0.0D);
                                    Minecraft.getMinecraft().getTextureManager().bindTexture(crownTexture);
                                    this.crown.isHidden = false;
                                    this.crown.render(0.0571F);
                                    this.crown.isHidden = true;
                                    this.crownDiamond.isHidden = false;
                                    this.crownDiamond.rotateAngleZ = 0.8F;
                                    this.crownDiamond.rotationPointZ = 0.6F;
                                    this.crownDiamond.rotationPointX = 0.4F;
                                    GlStateManager.translate(-0.22F, 0.0F, 0.0F);
                                    GlStateManager.color(1.0F, 1.0F, 1.0F);

                                    for (int i2 = 0; i2 < 3; ++i2)
                                    {
                                        this.crownDiamond.render(0.0561F);
                                        GlStateManager.translate(0.218F, 0.0F, 0.0F);
                                    }

                                    GL11.glColor3d(1.0D, 1.0D, 1.0D);
                                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                                    this.crownDiamond.isHidden = true;
                                    GlStateManager.popMatrix();
                                }
                            }

                           
                            
                            if (Client.instance.setmgr.getSettingByName("Cap").getValBoolean()) {
                            	
                                GlStateManager.pushMatrix();
                                GlStateManager.color(1.0F, 1.0F, 1.0F);
                                GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
                                GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
                                ResourceLocation resourcelocation1 = capTexture;
                                
                                Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation1);
                                float f25 = 1.226F;
                                GlStateManager.scale(f25, f25, f25);

                                /*if (cosmetic.a == 1.0D)
                                {
                                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                                }*/

                                if (p_78088_7_.isSneaking())
                                {
                                    GlStateManager.translate(0.0D, 0.06D, 0.0D);
                                }

                                this.cap.isHidden = false;
                                this.cap.render(0.0571F);
                                this.cap.isHidden = true;
                                

                                GlStateManager.popMatrix();
                            }

                            if (Client.instance.setmgr.getSettingByName("Witch").getValBoolean()) {
                                ResourceLocation resourcelocation3 = witchTexture;
 
                                Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation3);
                                GlStateManager.pushMatrix();

                                if (p_78088_7_.isSneaking())
                                {
                                    GlStateManager.translate(0.0D, 0.1D, 0.0D);
                                }

                                GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
                                GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
                                this.witchHat.isHidden = false;
                                this.witchHat.render(scale);
                                this.witchHat.isHidden = true;
                                GlStateManager.popMatrix();
                            }
                           

                    Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation);
                    GL11.glColor3d(1.0D, 1.0D, 1.0D);
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GlStateManager.func_179117_G();
                }
                } 

        GlStateManager.popMatrix();
                }
        }

    public static float getFirstRotationX(AbstractClientPlayer clientPlayer, float partialTicks)
    {
        float f = interpolateRotation(clientPlayer.prevRenderYawOffset, clientPlayer.renderYawOffset, partialTicks);
        float f1 = interpolateRotation(clientPlayer.prevRotationYawHead, clientPlayer.rotationYawHead, partialTicks);
        float f2 = f1 - f;

        if (clientPlayer.isRiding() && clientPlayer.ridingEntity instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)clientPlayer.ridingEntity;
            f = interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
            f2 = f1 - f;
            float f3 = MathHelper.wrapAngleTo180_float(f2);

            if (f3 < -85.0F)
            {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F)
            {
                f3 = 85.0F;
            }

            f = f1 - f3;

            if (f3 * f3 > 2500.0F)
            {
                float f4 = f + f3 * 0.2F;
            }
        }

        return f2;
    }

    public static float getSecondRotationX(AbstractClientPlayer clientPlayer, float partialTicks)
    {
        return clientPlayer.prevRotationPitch + (clientPlayer.rotationPitch - clientPlayer.prevRotationPitch) * partialTicks;
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78088_7_)
    {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78088_7_);
    }

    public void setInvisible(boolean invisible)
    {
        super.func_178719_a(invisible);
    }

    public static float interpolateRotation(float par1, float par2, float par3)
    {
        float f;

        for (f = par2 - par1; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return par1 + par3 * f;
    }
}
