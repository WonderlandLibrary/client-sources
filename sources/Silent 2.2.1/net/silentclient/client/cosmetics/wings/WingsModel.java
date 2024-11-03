package net.silentclient.client.cosmetics.wings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.silentclient.client.Client;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.settings.CosmeticsMod;
import org.lwjgl.opengl.GL11;

public class WingsModel implements LayerRenderer<AbstractClientPlayer> {
	private static ModelRenderer wing;
    private static ModelRenderer wingTip;
    boolean flying;
    private final ModelDragonWings modelDragonWings;
    
    public WingsModel(RenderPlayer player) {
    	this.flying = false;
        this.modelDragonWings = new ModelDragonWings(player);
        final int bw = this.modelDragonWings.textureWidth;
        final int bh = this.modelDragonWings.textureHeight;
        this.modelDragonWings.textureWidth = 256;
        this.modelDragonWings.textureHeight = 256;
        (wing = new ModelRenderer(this.modelDragonWings, "wing")).setRotationPoint(-12.0f, 5.0f, 2.0f);
        wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        wing.isHidden = true;
        (wingTip = new ModelRenderer(this.modelDragonWings, "wingTip")).setRotationPoint(-56.0f, 0.0f, 0.0f);
        wingTip.isHidden = true;
        wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        wing.addChild(wingTip);
        this.modelDragonWings.textureWidth = bw;
        this.modelDragonWings.textureWidth = bh;
	}
    
    private class ModelDragonWings extends ModelBase {
    	public ModelDragonWings(RenderPlayer player) {
            this.setTextureOffset("wingTip.bone", 112, 136);
            this.setTextureOffset("wing.skin", -56, 88);
            this.setTextureOffset("wing.bone", 112, 88);
            this.setTextureOffset("wingTip.skin", -56, 144);
		}

		@Override
        public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
            super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.disableLighting();
                GlStateManager.scale(Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings Scale").getValDouble(), Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings Scale").getValDouble(), Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings Scale").getValDouble());
                float f1 = 0.0f;
                if (Minecraft.getMinecraft().thePlayer.capabilities.isFlying) {
                    f1 = ageInTicks / 200.0f;
                }
                else {
                    f1 = ageInTicks / 80.0f;
                }
                ((AbstractClientPlayerExt) entityIn).silent$getWings().bindTexture();
                if (!entityIn.onGround || flying) {
                    flying = true;
                }
                GlStateManager.scale(0.15, 0.15, 0.15);
                GlStateManager.translate(0.0, -0.3, 1.1);
                if(entityIn.isSneaking()) {
                	GlStateManager.translate(-0.04F, 1.1F + 0.2F, -0.04F);
                }
                GlStateManager.rotate(50.0f, -50.0f, 0.0f, 0.0f);
                for (int i = 0; i < 2; ++i) {
                    final float f2 = f1 * 9.141593f * 2.0f;
                    wing.rotateAngleX = 0.125f - (float)Math.cos(f2) * 0.2f;
                    wing.rotateAngleY = 0.25f;
                    wing.rotateAngleZ = (float)(Math.sin(f2) + 1.225) * 0.3f;
                    wingTip.rotateAngleZ = -(float)(Math.sin(f2 + 2.0f) + 0.5) * 0.75f;
                    wing.isHidden = false;
                    wingTip.isHidden = false;
                    if (!entityIn.isInvisible()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.disableLighting();
                        wing.render(scale);
                        GlStateManager.popMatrix();
                    }
                    wing.isHidden = false;
                    wingTip.isHidden = false;
                    if (i == 0) {
                        GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                    }
                }
                GlStateManager.popMatrix();
            }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

	@Override
	public void doRenderLayer(final AbstractClientPlayer player, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float HeadYaw, final float headPitch, final float scale) {
		if (((AbstractClientPlayerExt) player).silent$getWings() != null && Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Wings").getValBoolean()) {
            if (!player.isInvisible()) {
            	GlStateManager.pushMatrix();
                this.modelDragonWings.render(player, limbSwing, limbSwingAmount, ageInTicks, HeadYaw, headPitch, scale);
                this.modelDragonWings.setRotationAngles(scale, limbSwing, limbSwingAmount, ageInTicks, HeadYaw, headPitch, player);
                GL11.glPopMatrix();
            }
        }
	}
}
