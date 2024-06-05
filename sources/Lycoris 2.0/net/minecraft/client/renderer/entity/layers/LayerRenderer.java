package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLivingBase;

public interface LayerRenderer<E extends EntityLivingBase> {
	void doRenderLayer(E entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks,
			float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale);

	boolean shouldCombineTextures();
}
