package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.utils.culling.EntityCulling;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItemFrame.class)
public class RenderItemFrameMixin {
    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItemFrame;DDDFF)V", at = @At("HEAD"), cancellable = true)
    private void silent$cancelRender(EntityItemFrame entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Item Frames").getValBoolean()) {
            ci.cancel();
            return;
        }

        if (entity != null) {
            ItemStack displayedItem = entity.getDisplayedItem();
            if (displayedItem != null) {
                Item item = displayedItem.getItem();
                if (Client.getInstance().getSettingsManager().getSettingByClass(FPSBoostMod.class, "Hide Maps In Item Frames").getValBoolean() && item != null && item == Items.filled_map) {
                    ci.cancel();
                    return;
                }
            }
        }

        if (EntityCulling.renderItem(entity)) {
            ci.cancel();
        }
    }
}
