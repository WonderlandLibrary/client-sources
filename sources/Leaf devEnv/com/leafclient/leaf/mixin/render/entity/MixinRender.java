package com.leafclient.leaf.mixin.render.entity;

import com.leafclient.leaf.event.game.entity.EntityTeamColorEvent;
import com.leafclient.leaf.management.event.EventManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(Render.class)
public abstract class MixinRender {

    @Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true)
    private void inject$teamColor(Entity entity, CallbackInfoReturnable<Integer> info) {
        final Color color = EventManager.INSTANCE
                .publish(new EntityTeamColorEvent(entity))
                .getColor();

        if(color != null)
            info.setReturnValue(color.getRGB());
    }

}
