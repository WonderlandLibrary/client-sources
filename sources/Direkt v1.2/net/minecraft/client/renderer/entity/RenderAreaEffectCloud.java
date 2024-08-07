package net.minecraft.client.renderer.entity;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.util.ResourceLocation;

public class RenderAreaEffectCloud extends Render<EntityAreaEffectCloud> {
	public RenderAreaEffectCloud(RenderManager manager) {
		super(manager);
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	@Override
	public void doRender(EntityAreaEffectCloud entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityAreaEffectCloud entity) {
		return null;
	}
}
