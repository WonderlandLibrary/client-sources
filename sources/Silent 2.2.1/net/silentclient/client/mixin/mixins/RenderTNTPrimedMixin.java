package net.silentclient.client.mixin.mixins;

import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.silentclient.client.Client;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderTNTPrimed.class)
public class RenderTNTPrimedMixin {
    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityTNTPrimed;DDDFF)V", at = @At("HEAD"))
    public void tntTimer(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if(Client.getInstance().getModInstances().getTntTimerMod().isEnabled()) {
            Client.getInstance().getModInstances().getTntTimerMod().doRender((RenderTNTPrimed) (Object) this, entity, x, y, z, partialTicks);
        }
    }
}
