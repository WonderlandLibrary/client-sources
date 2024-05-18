package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import wwe.*;
import wwe.utils.*;

@Mixin(value = { GuiMainMenu.class }, priority = 9999)
public class MixinGuiMainMenu extends GuiScreen
{
    public MixinGuiMainMenu() {
        super();
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") })
    public void init(final CallbackInfo callbackInfo) {
        this.buttonList.add(new GuiButton(21, 50, 50, 20, 20, "WWE"));
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    public void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        if (guiButton.id == 21) {
            this.mc.displayGuiScreen((GuiScreen)new qdlI());
            ssGO.jMLq = true;
            Wrapper.ypJK().OsgT();
        }
    }
}
