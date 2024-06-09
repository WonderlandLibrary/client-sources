package com.client.glowclient.sponge.mixin;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiScreenBook.class })
public abstract class MixinGuiScreenBook extends GuiScreen
{
    @Shadow
    private String field_146482_z;
    
    public MixinGuiScreenBook() {
        super();
        this.bookTitle = "";
    }
    
    @Inject(method = { "keyTypedInTitle" }, at = { @At("HEAD") }, cancellable = true)
    public void preKeyTypedInTitle(final char c, final int n, final CallbackInfo callbackInfo) {
        if (GuiScreen.isKeyComboCtrlV(n)) {
            this.bookTitle += GuiScreen.getClipboardString();
        }
    }
}
