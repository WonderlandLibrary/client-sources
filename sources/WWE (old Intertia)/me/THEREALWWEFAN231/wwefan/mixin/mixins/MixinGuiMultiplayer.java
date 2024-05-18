package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.multiplayer.*;
import wwe.*;

@Mixin(value = { GuiMultiplayer.class }, priority = 9999)
public class MixinGuiMultiplayer extends GuiScreen
{
    public MixinGuiMultiplayer() {
        super();
    }
    
    @Inject(method = { "createButtons" }, at = { @At("RETURN") })
    public void init(final CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(21, 7, 7, 40, 18, "Login"));
        this.buttonList.add(new GuiButton(22, this.width - 47, 7, 40, 18, "Name"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    public void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        if (guiButton.id == 21) {
            this.mc.displayGuiScreen((GuiScreen)new NJJw(this));
        }
        if (guiButton.id == 22) {
            this.mc.displayGuiScreen((GuiScreen)new BmDl(this));
        }
    }
    
    @Inject(method = { "connectToServer" }, at = { @At("RETURN") })
    private void connectToServer(final ServerData fjUS, final CallbackInfo callbackInfo) {
        vlwv.FjUS = fjUS;
    }
}
