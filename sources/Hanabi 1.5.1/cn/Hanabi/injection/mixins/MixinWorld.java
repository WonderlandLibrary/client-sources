package cn.Hanabi.injection.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.events.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ World.class })
public class MixinWorld
{
    @Inject(at = { @At("HEAD") }, method = { "playSoundAtEntity" }, cancellable = true)
    public void playSoundAtEntity(final Entity entityIn, final String name, final float volume, final float pitch, final CallbackInfo ci) {
        final EventSoundPlay e = new EventSoundPlay(entityIn, name);
        if (e.cancel) {
            ci.cancel();
        }
    }
}
