package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderSlime extends RenderLiving<EntitySlime> {
	private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("textures/entity/slime/slime.png");

	public RenderSlime(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
		super(renderManagerIn, modelBaseIn, shadowSizeIn);
		this.addLayer(new LayerSlimeGel(this));
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	@Override
	public void doRender(EntitySlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.shadowSize = 0.25F * entity.getSlimeSize();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Allows the render to do state modifications necessary before the model is rendered.
	 */
	@Override
	protected void preRenderCallback(EntitySlime entitylivingbaseIn, float partialTickTime) {
		float f = 0.999F;
		GlStateManager.scale(0.999F, 0.999F, 0.999F);
		float f1 = entitylivingbaseIn.getSlimeSize();
		float f2 = (entitylivingbaseIn.prevSquishFactor + ((entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime)) / ((f1 * 0.5F) + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		GlStateManager.scale(f3 * f1, (1.0F / f3) * f1, f3 * f1);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntitySlime entity) {
		return SLIME_TEXTURES;
	}
}
