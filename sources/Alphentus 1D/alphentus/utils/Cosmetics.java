package alphentus.utils;

import alphentus.init.Init;
import alphentus.mod.mods.combat.NoFriends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class Cosmetics extends ModelBiped {

    private ModelRenderer wing;
    private ModelRenderer wingTip;
    private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
    alphentus.mod.mods.visuals.Cosmetics cosmetics;

    public Cosmetics(float p_i46304_1_, boolean p_i46304_2_) {
        super(p_i46304_1_, 0.0F, 64, 64);

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
    }

    public Cosmetics(float p_i46304_1_, float f, int i, int j) {
        this(p_i46304_1_, false);
    }


    @Override
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
        super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);

        if (cosmetics == null) {
            cosmetics = Init.getInstance().modManager.getModuleByClass(alphentus.mod.mods.visuals.Cosmetics.class);
        }

        AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer) entityIn;
        ResourceLocation resourcelocation = abstractclientplayer.getLocationSkin();

        if (!cosmetics.getState())
            return;
        if (!(Init.getInstance().friendSystem.getFriends().contains(entityIn.getName()) && !Init.getInstance().modManager.getModuleByClass(NoFriends.class).getState() || entityIn == Minecraft.getMinecraft().thePlayer))
            return;

        if (cosmetics.settingWings.isState()) {
            final float[] counter = {0};

            boolean flag2 = Minecraft.getMinecraft().thePlayer.isInvisible();
            GlStateManager.pushMatrix();
            float f24 = 100.0F;
            float f35 = p_78088_3_ + p_78088_4_ / f24;
            Minecraft.getMinecraft().getTextureManager().bindTexture(enderDragonTextures);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(1, 1);
            GlStateManager.scale(0.0012D * (double) (125), 0.0012D * (double) (125), 0.0012D * (double) 125);
            GlStateManager.translate(0.0D, -0.3D, 1.1D);
            GlStateManager.rotate(50.0F, -50.0F, 0.0F, 0.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            if (cosmetics.rainbow.isState())
                GL11.glColor4f((float) (getRainbow(2500, -60 * counter[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) & 255) / 255.0F, 1);
            if (!cosmetics.rainbow.isState())
                GL11.glColor4f(colors(Init.getInstance().CLIENT_COLOR)[0], colors(Init.getInstance().CLIENT_COLOR)[1], colors(Init.getInstance().CLIENT_COLOR)[2], 1);

            for (int i4 = 0; i4 < 2; ++i4) {
                GlStateManager.enableCull();
                float f7 = f35 * (float) Math.PI * 2.0F;
                this.wing.rotateAngleX = (float) Math.cos((double) f7) * 0.2F;
                this.wing.rotateAngleY = 0.25F;
                this.wing.rotateAngleZ = (float) (Math.sin((double) f7) + 1) * 0.8F;
                this.wingTip.rotateAngleZ = -((float) (Math.sin((double) (f7 + 2F)) + 0.5D)) * 1F;
                this.wing.isHidden = false;
                this.wingTip.isHidden = false;
                this.wing.render(scale);
                this.wing.isHidden = true;
                this.wingTip.isHidden = true;
                GlStateManager.scale(-1.0F, 1.0F, 1.0F);

                if (i4 == 0) {
                    GlStateManager.cullFace(1028);
                    if (cosmetics.rainbow.isState())
                        GL11.glColor4f((float) (getRainbow(2500, -60 * counter[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) & 255) / 255.0F, 1);
                    if (!cosmetics.rainbow.isState())
                        GL11.glColor4f(colors(Init.getInstance().CLIENT_COLOR)[0], colors(Init.getInstance().CLIENT_COLOR)[1], colors(Init.getInstance().CLIENT_COLOR)[2], 1);
                }
                counter[0] += 0.25F;
            }

            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(flag2);
            GlStateManager.enableLight(0);
            GlStateManager.enableLight(1);
            GlStateManager.cullFace(1029);
            GlStateManager.disableCull();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }

        if (cosmetics.settingAura.isState()) {

            boolean flag2 = Minecraft.getMinecraft().thePlayer.isInvisible();
            final float[] counter = {0};
            GlStateManager.depthMask(!flag2);
            if (cosmetics.settingAuraModes.getSelectedCombo().equals("Wither")) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/entity/wither/wither_armor.png"));
            } else {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/entity/creeper/creeper_armor.png"));
            }

            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f;
            f = (Minecraft.getMinecraft().thePlayer.ticksExisted + Minecraft.getMinecraft().timer.renderPartialTicks) / cosmetics.settingAuraSpeed.getCurrent();
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f1 = 0.5F;
            if (cosmetics.rainbow.isState())
                GL11.glColor4f((float) (getRainbow(2500, -60 * counter[0]) >> 16 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) >> 8 & 255) / 255.0F, (float) (getRainbow(2500, -60 * counter[0]) & 255) / 255.0F, 1);
            if (!cosmetics.rainbow.isState())
                GlStateManager.color(cosmetics.settingAuraRed.getCurrent() / 255F, cosmetics.settingAuraGreen.getCurrent() / 255F, cosmetics.settingAuraBlue.getCurrent() / 255F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(1, 1);
            super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(flag2);
            counter[0] += 0.25F;
        }


        Minecraft.getMinecraft().getTextureManager().bindTexture(resourcelocation);
        GL11.glColor3d(1.0D, 1.0D, 1.0D);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.resetColor();

    }

    public static int getRainbow(final int speed, final double d) {
        float hue = (float) ((System.currentTimeMillis() - (d % speed) / 0.25) % speed);
        hue /= speed;
        return Color.getHSBColor(hue, (float) (0.55f), (float) (1F)).getRGB();
    }

    public float[] colors(Color c) {
        return new float[]{c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f};
    }


}

