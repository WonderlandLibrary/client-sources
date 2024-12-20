package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderChicken extends RenderLiving<EntityChicken> {
	private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation("textures/entity/chicken.png");

	public RenderChicken(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
		super(renderManagerIn, modelBaseIn, shadowSizeIn);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityChicken entity) {
		return CHICKEN_TEXTURES;
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat(EntityChicken livingBase, float partialTicks) {
		float f = livingBase.oFlap + ((livingBase.wingRotation - livingBase.oFlap) * partialTicks);
		float f1 = livingBase.oFlapSpeed + ((livingBase.destPos - livingBase.oFlapSpeed) * partialTicks);
		return (MathHelper.sin(f) + 1.0F) * f1;
	}
}
