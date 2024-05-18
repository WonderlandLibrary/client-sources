package me.finz0.osiris.mixin.mixins;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.PlayerJumpEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer {
    @Shadow public abstract String getName();

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void onJump(CallbackInfo ci){
        if(Minecraft.getMinecraft().player.getName() == this.getName()){
            AuroraMod.EVENT_BUS.post(new PlayerJumpEvent());
        }
    }
}
