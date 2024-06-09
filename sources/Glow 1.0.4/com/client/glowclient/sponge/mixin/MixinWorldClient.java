package com.client.glowclient.sponge.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.profiler.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.*;

@Mixin({ WorldClient.class })
public abstract class MixinWorldClient extends World
{
    public MixinWorldClient() {
        super((ISaveHandler)null, (WorldInfo)null, (WorldProvider)null, (Profiler)null, true);
    }
    
    @Inject(method = { "tick" }, at = { @At("RETURN") }, cancellable = true)
    public void postTick(final CallbackInfo callbackInfo) {
        HookTranslator.m59(WorldClient.class.cast(this));
    }
    
    @Inject(method = { "doPreChunk" }, at = { @At("HEAD") }, cancellable = true)
    public void preDoPreChunk(final int n, final int n2, final boolean b, final CallbackInfo callbackInfo) {
        HookTranslator.m60(WorldClient.class.cast(this), n, n2, b);
    }
    
    @Inject(method = { "removeEntityFromWorld" }, at = { @At("HEAD") }, cancellable = true)
    public void preRemoveEntityFromWorld(final int n, final CallbackInfoReturnable<Entity> callbackInfoReturnable) {
        HookTranslator.m61(WorldClient.class.cast(this), n);
    }
}
