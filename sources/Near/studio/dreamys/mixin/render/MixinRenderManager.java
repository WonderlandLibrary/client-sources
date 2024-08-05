package studio.dreamys.mixin.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import studio.dreamys.entityculling.Config;
import studio.dreamys.entityculling.access.Cullable;
import studio.dreamys.entityculling.access.EntityRendererInter;
import studio.dreamys.near;

@Mixin(RenderManager.class)
public abstract class MixinRenderManager {
    @Overwrite
    private void renderDebugBoundingBox(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
        AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX -  entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z);
        RenderGlobal.drawOutlinedBoundingBox(axisalignedbb1, 255, 255, 255, 255);

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
    }

    @Shadow
    public abstract <T extends Entity> Render<T> getEntityRenderObject(Entity p_getEntityRenderObject_1_);

    @Inject(at = @At("HEAD"), method = "doRenderEntity", cancellable = true)
    public void doRenderEntity(Entity entity, double p_doRenderEntity_2_, double d1, double d2, float tickDelta, float p_doRenderEntity_9_, boolean p_doRenderEntity_10_, CallbackInfoReturnable<Boolean> info) {
        Cullable cullable = (Cullable) entity;
//        if (entity instanceof EntityWither) {
//            System.out.println(((EntityWither) entity).getRenderSizeModifier());
//        }
        if (cullable.isForcedVisible() && cullable.isCulled()) {
            //noinspection unchecked
            EntityRendererInter<Entity> entityRenderer = (EntityRendererInter<Entity>) getEntityRenderObject(entity);
//            if (entity instanceof EntityWither) {
//                System.out.println(((EntityWither) entity).getRenderSizeModifier());
//            }
            if (Config.renderNametagsThroughWalls && entityRenderer.shadowShouldShowName(entity)) {
                entityRenderer.shadowRenderNameTag(entity, p_doRenderEntity_2_, d1, d2);
            }
            near.skippedEntities++;
            info.cancel();
            return;
        }
        near.renderedEntities++;
    }
}
