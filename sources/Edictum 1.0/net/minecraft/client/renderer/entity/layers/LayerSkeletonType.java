package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.SkeletonType;
import net.minecraft.util.ResourceLocation;

public class LayerSkeletonType implements LayerRenderer<EntitySkeleton>
{
    private static final ResourceLocation field_190092_a = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
    private final RenderLivingBase<?> field_190093_b;
    private ModelSkeleton field_190094_c;

    public LayerSkeletonType(RenderLivingBase<?> p_i47131_1_)
    {
        this.field_190093_b = p_i47131_1_;
        this.field_190094_c = new ModelSkeleton(0.25F, true);
    }

    public void doRenderLayer(EntitySkeleton entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.func_189771_df() == SkeletonType.STRAY)
        {
            this.field_190094_c.setModelAttributes(this.field_190093_b.getMainModel());
            this.field_190094_c.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.field_190093_b.bindTexture(field_190092_a);
            this.field_190094_c.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}
