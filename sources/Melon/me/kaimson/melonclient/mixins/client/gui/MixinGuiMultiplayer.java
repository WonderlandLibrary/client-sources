package me.kaimson.melonclient.mixins.client.gui;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ azh.class })
public class MixinGuiMultiplayer
{
    @Inject(method = { "connectToServer" }, at = { @At("HEAD") })
    private void connectToServer(final bde server, final CallbackInfo ci) {
        if (ave.A().f != null) {
            ave.A().f.H();
        }
    }
}
