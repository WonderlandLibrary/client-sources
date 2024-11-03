package net.silentclient.client.cosmetics;

import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.AngleUtilities;

public class CapeRenderer extends ModelBase implements LayerRenderer<AbstractClientPlayer> {
	private final RenderPlayer playerRenderer;
    
    private ModelRenderer bipedCloakShoulders;
    
    public CapeRenderer(RenderPlayer playerRendererIn)
    {
        this.playerRenderer = playerRendererIn;
        this.bipedCloakShoulders = new ModelRenderer(this, 0, 17);
        this.bipedCloakShoulders.setTextureSize(22, 23);
        this.bipedCloakShoulders.addBox(-5.0F, -1.0F, -2.0F, 2, 1, 5, 0.0F);
        this.bipedCloakShoulders.addBox(3.0F, -1.0F, -2.0F, 2, 1, 5, 0.0F);
    }
	
	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_,
			float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {

		if(((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCape() == null) {
			return;
		}
				
		if (entitylivingbaseIn.isInvisible()) {
			return;
		}
        ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getDynamicCape().ticks(System.nanoTime());
		
		String capeType = ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCapeType();
		
        GlStateManager.pushMatrix();
        ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCape().bindTexture();
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        GlStateManager.disableLighting();
        GlStateManager.color(1, 1, 1);

        double prevX = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * (double)partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * (double)partialTicks);
        double prevY = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * (double)partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * (double)partialTicks);
        double prevZ = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * (double)partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * (double)partialTicks);
        float prevRenderYaw = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
        float prevCameraYaw = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
        double distanceWalkedModified = entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks;

        final double a = AngleUtilities .a((double)(prevRenderYaw * 3.1415927f / 180.0f));
        final double n2 = -AngleUtilities .b(prevRenderYaw * 3.1415927f / 180.0f);
        float a2 = AngleUtilities .a((float)prevY * 10.0f, -6.0f, 32.0f);
        float n3 = (float)(prevX * a + prevZ * n2) * 100.0f;
        final float n4 = (float)(prevX * n2 - prevZ * a) * 100.0f;
       

        if (n3 < 0.0f) {
            n3 = 0.0f;
        }
        if (n3 > 165.0f) {
            n3 = 165.0f;
        }
        
        if(entitylivingbaseIn.isSneaking()) {
        	n3 += 5.0F;
			GlStateManager.translate(0.0F, 0.1F, 0.0F);
			a2 += 25.0F;
			GlStateManager.translate(0.0F, 0.05F, -0.0178F);
        }

        float n5 = a2 + (float) (AngleUtilities.a(6.0f * prevCameraYaw)) * (float) (16.0f * distanceWalkedModified);

        
        
        GlStateManager.scale(0.0625f, 0.0625f, 0.0625f);
        GlStateManager.translate(0.0f, 0.0f, 2.0f);
        GlStateManager.rotate(6.0f + Math.min(n3 / 2.0f + n5, 90.0f), 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n4 / 2.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-n4 / 2.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        
        if(Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Low Graphics Mode").getValBoolean()) {
        	capeType = "rectangle";
        }

        
        switch(capeType) {
        case "dynamic_curved":
        	final float max = Math.max(Math.min(0.0f, n5), -3.0f);
            final float min = Math.min(n3 + n5, 90.0f);
            ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getDynamicCape().renderDynamicCape();
            ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getDynamicCape().update(min, max, true);
            break;
        case "curved_rectangle":
            ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCurvedCape().renderStaticCape();
        	break;
        default:
            ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getStaticCape().renderStaticCape();
        	break;
        }
        
        GlStateManager.translate(0.0f, 0.0f, 0.125f);
        GlStateManager.rotate(6.0f + n3 / 2.0f + n5, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(n4 / 2.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-n4 / 2.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.popMatrix();
        
        if(((AbstractClientPlayerExt) entitylivingbaseIn).silent$getShoulders()) {
            ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCapeShoulders().bindTexture();
        	GlStateManager.pushMatrix();
            if(entitylivingbaseIn.isSneaking()) {
            	GlStateManager.translate(0.0F, 0.2F, 0.0F);
                GlStateManager.rotate(10.0F, 1.0F, 0.0F, 0.0F);
            }
            GlStateManager.rotate(-n4 / 2.0F, 0.0F, 1.0F, 0.0F);
            this.bipedCloakShoulders.render(0.0625F);
            GlStateManager.popMatrix();
		}

        GlStateManager.enableLighting();
        if(((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCape() != null) {
            ((AbstractClientPlayerExt) entitylivingbaseIn).silent$getCape().update(partialTicks);
	    }
	}
	
	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
