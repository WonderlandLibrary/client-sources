package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.silentclient.client.event.impl.EntityAttackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityMixin {
    @Inject(method = "getDisplayName", at = @At("RETURN"))
    private void silent$cachePlayerDisplayName(CallbackInfoReturnable<IChatComponent> cir) {
        super.silent$cacheDisplayName(cir);
    }

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void silent$returnCachedPlayerDisplayName(CallbackInfoReturnable<IChatComponent> cir) {
        super.silent$returnCachedDisplayName(cir);
    }

    @Redirect(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getHoverEvent()Lnet/minecraft/event/HoverEvent;"))
    private HoverEvent silent$onlyGetHoverEventInSinglePlayer(EntityPlayer instance) {
        // Only needed in single player
        return Minecraft.getMinecraft().isIntegratedServerRunning()
                ? ((EntityPlayerMixin) (Object) instance).getHoverEvent()
                : null;
    }

    @Redirect(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ChatStyle;setChatHoverEvent(Lnet/minecraft/event/HoverEvent;)Lnet/minecraft/util/ChatStyle;"))
    private ChatStyle silent$onlySetHoverEventInSinglePlayer(ChatStyle instance, HoverEvent event) {
        return Minecraft.getMinecraft().isIntegratedServerRunning()
                ? instance.setChatHoverEvent(event)
                : null;
    }

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    public void callEntityAttackEvent(Entity targetEntity, CallbackInfo ci) {
        if (targetEntity.canAttackWithItem()) {
            new EntityAttackEvent(targetEntity, ((EntityPlayer) (Object) this)).call();
        }
    }
}
