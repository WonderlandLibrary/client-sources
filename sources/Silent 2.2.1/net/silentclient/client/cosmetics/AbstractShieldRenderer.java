package net.silentclient.client.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.silentclient.client.Client;
import net.silentclient.client.blc.BlcGlStateManager;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mods.settings.CosmeticsMod;

public class AbstractShieldRenderer extends ModelBase implements LayerRenderer<AbstractClientPlayer> {
	private final RenderPlayer playerRenderer;

	public AbstractShieldRenderer(RenderPlayer playerRendererIn)
    {
        this.playerRenderer = playerRendererIn;
    }

	@Override
	public void doRenderLayer(AbstractClientPlayer entity, float p_177141_2_, float p_177141_3_,
			float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        if(((AbstractClientPlayerExt) entity).silent$getShield() == null || !Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Shields").getValBoolean() || entity.isInvisible() || !Client.getInstance().getCosmetics().shieldModels.containsKey(((AbstractClientPlayerExt) entity).silent$getShield().getModel()) || !Client.getInstance().getCosmetics().shieldModels.get(((AbstractClientPlayerExt) entity).silent$getShield().getModel()).loadModel()) {
			return;
		}
		BlcGlStateManager.t();
	    BlcGlStateManager.s();
	    BlcGlStateManager.a(1.0F, 1.0F, 1.0F, 1.0F);
	    BlcGlStateManager.g();
	    BlcGlStateManager.a(770, 771);
	    BlcGlStateManager.q();
	    BlcGlStateManager.d();
	    BlcGlStateManager.t();
	    if (playerRenderer.getMainModel().bipedLeftArm.rotateAngleZ != 0.0F) {
            GlStateManager.rotate(playerRenderer.getMainModel().bipedLeftArm.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
        }

        if (playerRenderer.getMainModel().bipedLeftArm.rotateAngleY != 0.0F) {
            GlStateManager.rotate(playerRenderer.getMainModel().bipedLeftArm.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (playerRenderer.getMainModel().bipedLeftArm.rotateAngleX != 0.0F) {
            GlStateManager.rotate(playerRenderer.getMainModel().bipedLeftArm.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        }
		ItemStack chestplate = entity.getCurrentArmor(2);
	    applyArmTransformations(((AbstractClientPlayerExt) entity).silent$getShield().getModel(), entity.isSneaking(), entity.getSkinType().equals("slim"), chestplate != null);
	    double d = getShieldScale(((AbstractClientPlayerExt) entity).silent$getShield().getModel());
	    BlcGlStateManager.a(d, d, d);
	    BlcGlStateManager.k();
		((AbstractClientPlayerExt) entity).silent$getShield().getTexture().bindTexture();
	    Client.getInstance().getCosmetics().shieldModels.get(((AbstractClientPlayerExt) entity).silent$getShield().getModel()).renderModel();
	    BlcGlStateManager.c();
	    BlcGlStateManager.u();
	    BlcGlStateManager.c(1029);
	    BlcGlStateManager.l();
	    BlcGlStateManager.r();
	    BlcGlStateManager.u();
	    GlStateManager.enableTexture2D();
		((AbstractClientPlayerExt) entity).silent$getShield().getTexture().update(partialTicks);
	}

	public double getShieldScale(String model) {
		if(model.equals("roundshield") || model.equals("hexagon_shield") || model.equals("shield_dollar") || model.equals("zzv4shield2") || model.equals("shield_v4")) {
			return 0.100;
		}
		return 0.007;
	}

	private void applyArmTransformations(String model, boolean paramBoolean1, boolean paramBoolean2, boolean chestplate) {
		switch(model) {
			case "shield":
				GlStateManager.translate(0.33, 0.45, 0.38);
				BlcGlStateManager.b(180.0f, 0.0f, 1.0f, 1.0f);
				BlcGlStateManager.b(-90.0f, 1.0f, 0.0f, 0.0f);
				BlcGlStateManager.b(-90.0f, 0.0f, 1.0f, 0.0f);
				BlcGlStateManager.b(paramBoolean1 ? -0.5799999833106995 : -0.4000000059604645, paramBoolean1 ? -0.72 : -0.5, paramBoolean2 ? 0.13700000524520874 : 0.2);
				if(chestplate) {
					GlStateManager.translate(0, 0, 0.055 + (paramBoolean2 ? 0.055 : 0));
				}
				break;
			case "roundshield":
			case "zzv4shield2":
				GlStateManager.translate(0.33, 0.35, 0);
				BlcGlStateManager.b(-90.0F, 0.0F, 1.0F, 0.0F);
				BlcGlStateManager.b(paramBoolean1 ? -0.10000000149011612D : 0.0D, paramBoolean1 ? 0.45D : 0.25D, paramBoolean2 ? -0.1399999964237213D : -0.2D);
				BlcGlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
				if(chestplate) {
					GlStateManager.translate(0, 0, -0.06 + (paramBoolean2 ? -0.055 : 0));
				}
				break;
			case "hexagon_shield":
				GlStateManager.translate(0.75F, 0.35, 0);
				BlcGlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
				BlcGlStateManager.b(paramBoolean1 ? 0.2 : 0, paramBoolean1 ? 0.45D : 0.25D, (paramBoolean2 ? -0.28D : -0.2D) + (paramBoolean1 ? -0.02 : 0));
				BlcGlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
				if(chestplate) {
					GlStateManager.translate(0, 0, 0.06 + (paramBoolean2 ? 0.051 : 0));
				}
				break;
			case "shield_dollar":
				GlStateManager.translate(0.70F, 0.35, 0);
				BlcGlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
				BlcGlStateManager.b(paramBoolean1 ? 0.2 : 0, paramBoolean1 ? 0.45D : 0.25D, (paramBoolean2 ? -0.265D : -0.2D) + (paramBoolean1 ? -0.02 : 0));
				BlcGlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
				if(chestplate) {
					GlStateManager.translate(0, 0, 0.06 + (paramBoolean2 ? 0.055 : 0));
				}
				break;
			case "shield_v4":
				GlStateManager.translate(0.33, 0.35, 0);
				BlcGlStateManager.b(-90.0F, 0.0F, 1.0F, 0.0F);
				BlcGlStateManager.b(paramBoolean1 ? -0.10000000149011612D : 0.0D, paramBoolean1 ? 0.45D : 0.25D, paramBoolean2 ? -0.1399999964237213D : -0.2D);
				BlcGlStateManager.b(180.0F, 90.0F, 0.0F, 1.0F);
				if(chestplate) {
					GlStateManager.translate(0, 0, -0.06 + (paramBoolean2 ? -0.055 : 0));
				}
				break;
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}
