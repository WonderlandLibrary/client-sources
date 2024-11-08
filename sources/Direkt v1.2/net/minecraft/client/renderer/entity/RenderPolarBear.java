package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.util.ResourceLocation;

public class RenderPolarBear extends RenderLiving<EntityPolarBear> {
	private static final ResourceLocation field_190090_a = new ResourceLocation("textures/entity/bear/polarbear.png");

	public RenderPolarBear(RenderManager p_i47132_1_, ModelBase p_i47132_2_, float p_i47132_3_) {
		super(p_i47132_1_, p_i47132_2_, p_i47132_3_);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityPolarBear entity) {
		return field_190090_a;
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	@Override
	public void doRender(EntityPolarBear entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Allows the render to do state modifications necessary before the model is rendered.
	 */
	@Override
	protected void preRenderCallback(EntityPolarBear entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(1.2F, 1.2F, 1.2F);
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
	}
}
