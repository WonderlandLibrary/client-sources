package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.silentclient.client.Client;
import net.silentclient.client.event.impl.EventTransformFirstPersonItem;
import net.silentclient.client.mods.render.AnimationsMod;
import net.silentclient.client.mods.render.PackTweaksMod;
import net.silentclient.client.utils.animations.AnimationHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow private ItemStack itemToRender;

    @Shadow private float prevEquippedProgress;

    @Shadow private float equippedProgress;

    @Shadow @Final private Minecraft mc;

    @Shadow private int equippedItemSlot;

    @Inject(method = "transformFirstPersonItem", at = @At("HEAD"))
    public void transformFirstPersonItem(float equipProgress, float swingProgress, CallbackInfo ci) {
        EventTransformFirstPersonItem event = new EventTransformFirstPersonItem(itemToRender, equipProgress, swingProgress);
        event.call();
    }

    @Inject(method = "renderItemInFirstPerson", at = @At("HEAD"), cancellable = true)
    public void renderItemInFirstPerson(float partialTicks, CallbackInfo ci) {
        if (itemToRender != null) {
            ItemRenderer $this = (ItemRenderer) (Object) this;
            float equipProgress = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * partialTicks;
            if (AnimationHandler.getInstance().renderItemInFirstPerson($this, itemToRender, equipProgress, partialTicks)) {
                ci.cancel();
            }
        }
    }

    @ModifyArg(method = "updateEquippedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;clamp_float(FFF)F"), index = 0)
    private float handleItemSwitch(float original) {
        EntityPlayer entityplayer = Minecraft.getMinecraft().thePlayer;
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (AnimationsMod.getSettingBoolean("Item Switching Animation") && this.equippedItemSlot == entityplayer.inventory.currentItem && ItemStack.areItemsEqual(this.itemToRender, itemstack)) {
            return 1.0f - this.equippedProgress;
        }
        return original;
    }

    @Inject(method = "renderWaterOverlayTexture", at = @At("HEAD"), cancellable = true)
    public void cancelWaterOverlay(float partialTicks, CallbackInfo ci) {
        if (Client.getInstance().getModInstances().getModByClass(PackTweaksMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(PackTweaksMod.class, "Water Fog").getValBoolean() == false) {
            ci.cancel();
        }
    }

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
    private void silent$changeHeightAndFixOverlay(CallbackInfo ci) {
        if (this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1").getFrameCount() == 0) {
            ci.cancel();
            return;
        }

        GlStateManager.pushMatrix();
        boolean tweaked = Client.getInstance().getModInstances().getModByClass(PackTweaksMod.class).isEnabled();
        if(tweaked) {
            GlStateManager.translate(0, Client.getInstance().getSettingsManager().getSettingByClass(PackTweaksMod.class, "Fire Height").getValFloat(), 0);
        }
    }

    @Inject(method = "renderFireInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift = At.Shift.AFTER))
    private void silent$enableFireOpacity(CallbackInfo ci) {
        boolean tweaked = Client.getInstance().getModInstances().getModByClass(PackTweaksMod.class).isEnabled();
        float fireOpacity = Client.getInstance().getSettingsManager().getSettingByClass(PackTweaksMod.class, "Fire Opacity").getValFloat();
        if(fireOpacity != 1 && tweaked) {
            GlStateManager.color(1, 1, 1, fireOpacity);
        }
    }

    @Inject(method = "renderFireInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;popMatrix()V"))
    private void silent$disableFireOpacity(CallbackInfo ci) {
        GlStateManager.color(1, 1, 1, 1);
    }

    @Inject(method = "renderFireInFirstPerson", at = @At("TAIL"))
    private void silent$popMatrix(CallbackInfo ci) {
        GlStateManager.popMatrix();
    }
}
