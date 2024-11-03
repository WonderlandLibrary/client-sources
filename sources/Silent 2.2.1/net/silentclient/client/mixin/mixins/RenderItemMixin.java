package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.utils.animations.AnimationHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin {
    @Unique
    private EntityLivingBase lastEntityToRenderFor = null;

    @Inject(method = "renderItemModelForEntity", at = @At("HEAD"))
    public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor,
                                         ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        lastEntityToRenderFor = entityToRenderFor;
    }

    @Inject(method = "renderItemModelTransform", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(" +
                    "Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V")
    )
    public void renderItemModelForEntity_renderItem(ItemStack stack, IBakedModel model,
                                                    ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON &&
                lastEntityToRenderFor instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) lastEntityToRenderFor;
            ItemStack heldStack = p.getHeldItem();
            if (heldStack != null && p.getItemInUseCount() > 0 &&
                    heldStack.getItemUseAction() == EnumAction.BLOCK) {
                AnimationHandler.getInstance().doSwordBlock3rdPersonTransform();
            }
        }
    }

    @Shadow protected abstract void renderEffect(IBakedModel model);

    @Shadow protected abstract void renderModel(IBakedModel model, int color);

    @Shadow @Final private static ResourceLocation RES_ITEM_GLINT;

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderEffect(Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    public void renderItemEffect(RenderItem instance, IBakedModel model) {
        if(Client.getInstance().getModInstances().getModByClass(AnimationsMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(AnimationsMod.class, "1.7 Enchant Glint").getValBoolean()) {
            if(model != null) {
                GlStateManager.depthMask(false);
                GlStateManager.depthFunc(514);
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(768, 1);
                Minecraft.getMinecraft().getTextureManager().bindTexture(RES_ITEM_GLINT);
                GlStateManager.matrixMode(5890);

                GlStateManager.pushMatrix();
                GlStateManager.scale(8.0, 8.0, 8.0);
                GlStateManager.translate((Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-5.0F, 0.0F, 0.0F, 1.0F);
			    renderModel(model, -8372020);
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.scale(8.0, 8.0, 8.0);
                GlStateManager.translate((Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F, 0.0F, 0.0F);
                GlStateManager.rotate(205.0F, 0.0F, 0.0F, 1.0F);
                renderModel(model, -8372020);
                GlStateManager.popMatrix();

                GlStateManager.matrixMode(5888);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.enableLighting();
                GlStateManager.depthFunc(515);
                GlStateManager.depthMask(true);
                Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            }
        } else {
            renderEffect(model);
        }
    }
}
