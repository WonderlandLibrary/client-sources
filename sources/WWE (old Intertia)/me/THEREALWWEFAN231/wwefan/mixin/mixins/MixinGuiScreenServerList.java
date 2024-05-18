package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiScreenServerList.class }, priority = 9999)
public class MixinGuiScreenServerList extends GuiScreen
{
    public MixinGuiScreenServerList() {
        super();
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("HEAD") })
    public void actionPerformed(final GuiButton guiButton, final CallbackInfo callbackInfo) {
        if (guiButton.id == 0) {
            vlwv.FjUS = yYGD.gpTU(GuiScreenServerList.class.cast(this));
        }
    }
}
