package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.silentclient.client.Client;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerListEntryNormal.class)
public abstract class ServerListEntryNormalMixin {
    @Shadow protected abstract void prepareServerIcon();
    @Shadow @Final private ServerData server;

    @Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ServerListEntryNormal;prepareServerIcon()V"))
    private void silent$resolveCrash(ServerListEntryNormal serverListEntryNormal) {
        try {
            prepareServerIcon();
        } catch (Exception e) {
            Client.logger.error("Failed to prepare server icon, setting to default.", e);
            server.setBase64EncodedIconData(null);
        }
    }
}
