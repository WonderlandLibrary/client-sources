package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.silentclient.client.gui.minecraft.GuiConnecting;
import net.silentclient.client.mixin.ducks.GuiMultiplayerExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiMultiplayer.class)
public abstract class GuiMultiplayerMixin extends GuiScreen implements GuiMultiplayerExt {
    @Shadow private GuiScreen parentScreen;
    @Shadow private boolean directConnect;

    @Shadow private ServerData selectedServer;

    /**
     * @author kirillsaint
     * @reason custom gui connecting screen
     */
    @Overwrite
    private void connectToServer(ServerData server){
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, server));
    }

    @Override
    public void silent$setParentScreen(Object a) {
        if(a == null) {
            this.parentScreen = null;
            return;
        }
        this.parentScreen = (GuiScreen) a;
    }

    @Override
    public Object silent$getParentScreen() {
        return this.parentScreen;
    }

    @Override
    public void silent$setDirectConnect(boolean a) {
        this.directConnect = a;
    }

    @Override
    public Object silent$getSelectedServer() {
        return selectedServer;
    }

    @Override
    public void silent$setSelectedServer(Object a) {
        if(a == null) {
            this.selectedServer = null;
            return;
        }
        this.selectedServer = (ServerData) a;
    }
}
