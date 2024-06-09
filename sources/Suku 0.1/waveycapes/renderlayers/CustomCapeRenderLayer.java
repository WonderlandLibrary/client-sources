package waveycapes.renderlayers;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.util.misc.MathUtils;
import io.github.liticane.clients.util.player.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class CustomCapeRenderLayer implements LayerRenderer<AbstractClientPlayer> {
	static final int partCount = 16;
	private ModelRenderer[] customCape = new ModelRenderer[16];

	private final RenderPlayer playerRenderer;

	private SmoothCapeRenderer smoothCapeRenderer = new SmoothCapeRenderer();

	public CustomCapeRenderLayer(RenderPlayer playerRenderer) {
		this.playerRenderer = playerRenderer;
	}

	public void doRenderLayer(AbstractClientPlayer abstractClientPlayer, float paramFloat1, float paramFloat2,
			float deltaTick, float animationTick, float paramFloat5, float paramFloat6, float paramFloat7) {
		if (abstractClientPlayer.isInvisible())
			return;
		if (!abstractClientPlayer.hasPlayerInfo() || abstractClientPlayer.isInvisible())
			return;

//        if (!abstractClientPlayer.func_152122_n() || abstractClientPlayer.func_82150_aj() || !abstractClientPlayer.func_175148_a(EnumPlayerModelParts.CAPE) || abstractClientPlayer.func_110303_q() == null) {
//            return;
//        } // correct cape check. please use your config method/field files to correctly change the methods/fields being used if any are wrong.
        abstractClientPlayer.updateSimulation(abstractClientPlayer, partCount);
		double d0 = abstractClientPlayer.prevChasingPosX + (abstractClientPlayer.chasingPosX - abstractClientPlayer.prevChasingPosX) * (double) Minecraft.getInstance().timer.renderPartialTicks - (abstractClientPlayer.prevPosX + (abstractClientPlayer.posX - abstractClientPlayer.prevPosX) * (double) Minecraft.getInstance().timer.renderPartialTicks);
		double d2 = abstractClientPlayer.prevChasingPosZ + (abstractClientPlayer.chasingPosZ - abstractClientPlayer.prevChasingPosZ) * (double) Minecraft.getInstance().timer.renderPartialTicks - (abstractClientPlayer.prevPosZ + (abstractClientPlayer.posZ - abstractClientPlayer.prevPosZ) * (double) Minecraft.getInstance().timer.renderPartialTicks);

		float f = abstractClientPlayer.prevRenderYawOffset + (abstractClientPlayer.renderYawOffset
				- abstractClientPlayer.prevRenderYawOffset) * Minecraft.getInstance().timer.renderPartialTicks;

		if (abstractClientPlayer instanceof EntityPlayerSP) {
			f = MathUtils.interpolate(abstractClientPlayer.prevRotationYaw, abstractClientPlayer.rotationYaw, Minecraft.getInstance().timer.renderPartialTicks);
		}

		double d3 = MathHelper.sin(f * (float) Math.PI / 180.0F);
		double d4 = -MathHelper.cos(f * (float) Math.PI / 180.0F);
		float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;

		GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
		this.playerRenderer.bindTexture(new ResourceLocation("suku/UWqODyF.png"), ColorUtil.withAlpha(ColorUtil.interpolateColorsBackAndForth(20, 20, Client.INSTANCE.getThemeManager().getTheme().getFirstColor(), Client.INSTANCE.getThemeManager().getTheme().getSecondColor(), false), 255)); // should use abstractClientPlayer.getLocationCape(), i didnt have a way to test this with a real cape so im rendering my own.
		this.smoothCapeRenderer.renderSmoothCape(this, abstractClientPlayer, deltaTick); // smooth mode, below is a blocky sort of cut up cape mode.
	}


	float getNatrualWindSwing(int part) {
		long highlightedPart = (System.currentTimeMillis() / 3) % 360;
		float relativePart = (float) (part + 1) / partCount;
		return (float) Math.sin(Math.toRadians((relativePart) * 360 - (highlightedPart))) * 3;
	}

	private static float easeOutSine(float x) {
		return (float) Math.sin(x * Math.PI / 2.0D);
	}

	public boolean func_177142_b() {
		return false;
	}

	@Override
	public boolean shouldCombineTextures() {
		// TODO Auto-generated method stub
		return false;
	}
}
