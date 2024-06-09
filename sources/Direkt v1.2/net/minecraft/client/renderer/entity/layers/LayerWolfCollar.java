package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.src.Config;
import net.minecraft.src.CustomColors;
import net.minecraft.util.ResourceLocation;

public class LayerWolfCollar implements LayerRenderer<EntityWolf> {
	private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
	private final RenderWolf wolfRenderer;

	public LayerWolfCollar(RenderWolf wolfRendererIn) {
		this.wolfRenderer = wolfRendererIn;
	}

	@Override
	public void doRenderLayer(EntityWolf entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible()) {
			this.wolfRenderer.bindTexture(WOLF_COLLAR);
			EnumDyeColor enumdyecolor = EnumDyeColor.byMetadata(entitylivingbaseIn.getCollarColor().getMetadata());
			float[] afloat = EntitySheep.getDyeRgb(enumdyecolor);

			if (Config.isCustomColors()) {
				afloat = CustomColors.getWolfCollarColors(enumdyecolor, afloat);
			}

			GlStateManager.color(afloat[0], afloat[1], afloat[2]);
			this.wolfRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
