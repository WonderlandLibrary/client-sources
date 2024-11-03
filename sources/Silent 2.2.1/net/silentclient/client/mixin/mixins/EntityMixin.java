package net.silentclient.client.mixin.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow protected abstract HoverEvent getHoverEvent();

    @Shadow public boolean onGround;
    private long silent$displayNameCachedAt;

    private IChatComponent silent$cachedDisplayName;

    @Inject(method = "getDisplayName", at = @At("RETURN"))
    protected void silent$cacheDisplayName(CallbackInfoReturnable<IChatComponent> cir) {
        silent$cachedDisplayName = cir.getReturnValue();
        silent$displayNameCachedAt = System.currentTimeMillis();
    }

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    protected void silent$returnCachedDisplayName(CallbackInfoReturnable<IChatComponent> cir) {
        if (System.currentTimeMillis() - silent$displayNameCachedAt < 50L) {
            cir.setReturnValue(silent$cachedDisplayName);
        }
    }

    @Redirect(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getHoverEvent()Lnet/minecraft/event/HoverEvent;"))
    private HoverEvent silent$doNotGetHoverEvent(Entity instance) {
        // When is a non-player entity going to be sending a chat message?
        return null;
    }

    @Redirect(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ChatStyle;setChatHoverEvent(Lnet/minecraft/event/HoverEvent;)Lnet/minecraft/util/ChatStyle;"))
    private ChatStyle silent$doNotSetHoverEvent(ChatStyle instance, HoverEvent event) {
        // Let's not set it to null...
        return null;
    }

    @Redirect(method = "getBrightnessForRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isBlockLoaded(Lnet/minecraft/util/BlockPos;)Z"))
    public boolean silent$alwaysReturnTrue(World world, BlockPos pos) {
        return true;
    }

    @Inject(method = "spawnRunningParticles", at = @At("HEAD"), cancellable = true)
    private void silent$checkGroundState(CallbackInfo ci) {
        if (!this.onGround) ci.cancel();
    }
}
