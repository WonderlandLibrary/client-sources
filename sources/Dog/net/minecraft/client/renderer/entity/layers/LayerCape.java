package net.minecraft.client.renderer.entity.layers;

import fr.dog.Dog;
import fr.dog.protection.ProtectedLaunch;
import fr.dog.theme.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class LayerCape implements LayerRenderer<AbstractClientPlayer> {
    private final RenderPlayer playerRenderer;

    public LayerCape(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
    }

    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        if ((entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && (entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE)) || entitylivingbaseIn.getName().equals(Minecraft.getMinecraft().getSession().getUsername())))
        {
            if(entitylivingbaseIn.getLocationCape() != null){
                this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
            }
            if (entitylivingbaseIn.getName().equals(Minecraft.getMinecraft().getSession().getUsername()) && ProtectedLaunch.CUSTOM_CAPE.isEnabled()) {
                this.playerRenderer.bindTexture(ProtectedLaunch.CUSTOM_CAPE.getLocation());
            }
            
            if(!ProtectedLaunch.CUSTOM_CAPE.isEnabled())
                return;

            if(entitylivingbaseIn.getLocationCape() == null && !entitylivingbaseIn.getName().equals(Minecraft.getMinecraft().getSession().getUsername())){
                return;
            }

            Theme theme = Dog.getInstance().getThemeManager().getCurrentTheme();
            Color color = theme.getColor(10, 0);

            if (!ProtectedLaunch.CUSTOM_CAPE.mode.is("Dog"))
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            else
                GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1.0F);

            GlStateManager.pushMatrix();

            GlStateManager.translate(0.0F, 0.0F, 0.125F);

            double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * (double) partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * (double) partialTicks);
            double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * (double) partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * (double) partialTicks);
            double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * (double) partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * (double) partialTicks);
            float f = MathHelper.wrapAngleTo180_float(entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks);
            double d3 = MathHelper.sin(f * (float) Math.PI / 180.0F);
            double d4 = -MathHelper.cos(f * (float) Math.PI / 180.0F);
            float f1 = (float) d1 * 10.0F;
            f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
            float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
            float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;

            if (f2 < 0.0F) {
                f2 = 0.0F;
            }

            if (f2 > 165.0F) {
                f2 = 165.0F;
            }

            if (f1 < -5.0F) {
                f1 = -5.0F;
            }

            float f4 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
            f1 = f1 + MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

            if (entitylivingbaseIn.isSneaking()) {
                f1 += 25.0F;
                GlStateManager.translate(0.0F, 0.142F, -0.0178F);
            }

            GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            this.playerRenderer.getMainModel().renderCape(0.0625F);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
