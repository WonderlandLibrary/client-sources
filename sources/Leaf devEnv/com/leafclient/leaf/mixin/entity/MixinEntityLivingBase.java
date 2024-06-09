package com.leafclient.leaf.mixin.entity;

import com.leafclient.leaf.event.game.entity.PlayerJumpEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("all")
@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity {

    private float jumpYaw;

    @Shadow public float renderYawOffset;
    @Shadow public float rotationYawHead;

    /**
     * Injects the {@link PlayerJumpEvent}
     */
    @Inject(
            method = "jump",
            at = @At("HEAD")
    )
    private void inject$livingUpdate(CallbackInfo info) {
        if((Object)this == Minecraft.getMinecraft().player) {
            PlayerJumpEvent e = EventManager.INSTANCE.publish(new PlayerJumpEvent(
                    jumpYaw = rotationYaw
            ));

            rotationYaw = e.getRotationYaw();
        }
    }

    @Inject(
            method = "jump",
            at = @At("TAIL")
    )
    private void inject$postlivingUpdate(CallbackInfo info) {
        if((Object)this == Minecraft.getMinecraft().player) {
            rotationYaw = jumpYaw;
        }
    }

}
