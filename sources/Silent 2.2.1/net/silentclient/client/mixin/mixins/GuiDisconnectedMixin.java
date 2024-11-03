package net.silentclient.client.mixin.mixins;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import net.silentclient.client.Client;
import net.silentclient.client.event.impl.ServerLeaveEvent;
import net.silentclient.client.gui.minecraft.GuiConnecting;
import net.silentclient.client.gui.multiplayer.SilentMultiplayerGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiDisconnected.class)
public class GuiDisconnectedMixin extends GuiScreen {
    @Shadow private int field_175353_i;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(GuiScreen screen,
                      String reasonLocalizationKey,
                      IChatComponent chatComp,
                      CallbackInfo ci) {
        new ServerLeaveEvent().call();
    }

    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    public <E> boolean addButtons(List instance, E e) {
        if(Client.getInstance().lastServerData != null) {
            instance.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, "Retry"));
        }
        instance.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + (Client.getInstance().lastServerData != null ? 23 : 0), I18n.format("gui.toMenu", new Object[0])));

        return false;
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    public void reconnect(GuiButton button, CallbackInfo ci) {
        if(button.id == 1 && Client.getInstance().lastServerData != null) {
            this.mc.displayGuiScreen(new GuiConnecting(new SilentMultiplayerGui(Client.getInstance().getMainMenu()), this.mc, Client.getInstance().lastServerData));
            ci.cancel();
        }
    }
}
