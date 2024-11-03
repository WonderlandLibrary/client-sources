package net.silentclient.client.mods.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventRenderDamageTint;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.PlayerUtils;

public class DamageTintMod extends Mod {
	private final ResourceLocation shape = new ResourceLocation("silentclient/mods/damagetint/shape.png");
    private SimpleAnimation animation = new SimpleAnimation(0.0F);
    
	public DamageTintMod() {
		super("Damage Tint", ModCategory.MODS, "silentclient/icons/mods/damagetint.png");
	}

	@Override
	public void setup() {
		this.addSliderSetting("Health", this, 5, 1, 16, true);
	}
	
	@Override
	public boolean isForceDisabled() {
		return FPSBoostMod.hudOptimizationEnabled();
	}
	
	@EventTarget
	public void renderEvent(EventRenderDamageTint event) {
		if(isForceDisabled()) {
			return;
		}
		float threshold = (float) Client.getInstance().getSettingsManager().getSettingByName(this, "Health").getValDouble();
		ScaledResolution sr = new ScaledResolution(mc);
		
		if(PlayerUtils.isCreative() || mc.thePlayer.isSpectator()) {
			return;
		}
		
		animation.setAnimation(mc.thePlayer.getHealth() <= threshold ? 1.0F : 0.0F, 10);
		
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);

        GlStateManager.color(0F, animation.getValue(), animation.getValue(), animation.getValue());
        mc.getTextureManager().bindTexture(shape);
        Tessellator tes = Tessellator.getInstance();
        WorldRenderer wr = tes.getWorldRenderer();
        
        wr.begin(7, DefaultVertexFormats.POSITION_TEX);
        wr.pos(0.0D, sr.getScaledHeight_double(), -90.0D).tex(0.0D, 1.0D).endVertex();
        wr.pos(sr.getScaledWidth_double(), sr.getScaledHeight_double(), -90.0D).tex(1.0D, 1.0D).endVertex();
        wr.pos(sr.getScaledWidth_double(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        wr.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tes.draw();
        
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
	}
}
