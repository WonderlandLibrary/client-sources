// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import com.mojang.authlib.GameProfile;
import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.event.events.MoveC2SPacketEvent;
import dev.lvstrng.argon.event.events.PlayerTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPlayerEntity.class})
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow
    @Final
    protected MinecraftClient client;

    public ClientPlayerEntityMixin(final ClientWorld world, final GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = {"sendMovementPackets"}, at = {@At("HEAD")})
    private void onSendMovementPackets(final CallbackInfo ci) {
        EventBus.postEvent(new MoveC2SPacketEvent());
    }

    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void onPlayerTick(final CallbackInfo ci) {
        EventBus.postEvent(new PlayerTickEvent());
    }
}
