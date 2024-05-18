package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import wwe.*;

@Mixin(value = { GuiIngameMenu.class }, priority = 9999)
public class MixinGuiIngameMenu extends GuiScreen
{
    public MixinGuiIngameMenu() {
        super();
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void init(final CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(21, this.width / 2 + 2, this.height / 4 + 72 - 16, 98, 20, "Color Manager"));
        for (int i = 0; i < this.buttonList.size(); ++i) {
            if (((GuiButton)this.buttonList.get(i)).displayString.equalsIgnoreCase("Open To LAN")) {
                ((GuiButton)this.buttonList.get(i)).width = 98;
            }
        }
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    public void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        if (guiButton.id == 21) {
            this.mc.displayGuiScreen((GuiScreen)new rHwk());
        }
    }
}
