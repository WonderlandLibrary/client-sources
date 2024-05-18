package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.player.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { WorldClient.class }, priority = 9999)
public class MixinWorldClient
{
    public MixinWorldClient() {
        super();
    }
    
    @Inject(method = { "sendQuittingDisconnectingPacket" }, at = { @At("RETURN") }, cancellable = true)
    public void sendQuittingDisconnectingPacket(final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<JoinLeavemsgs>)JibK.class).IuiN()) {
            vlwv.hNxW.qHPF((Class<JoinLeavemsgs>)JibK.class).GDVp();
        }
        vlwv.hNxW.qHPF((Class<Announcer>)xbUc.class).eydA();
    }
}
