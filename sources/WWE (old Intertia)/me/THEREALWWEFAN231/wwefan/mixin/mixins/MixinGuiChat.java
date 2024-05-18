package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import wwe.modules.exploits.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiChat.class }, priority = 9999)
public class MixinGuiChat
{
    @Shadow
    protected GuiTextField field_146415_a;
    public lHwY commandHelper;
    
    public MixinGuiChat() {
        super();
    }
    
    @Inject(method = { "initGui" }, at = { @At("RETURN") }, cancellable = true)
    public void initGui(final CallbackInfo callbackInfo) {
        if (vlwv.hNxW.qHPF((Class<InfiniteChatLength>)PECw.class).IuiN()) {
            this.inputField.setMaxStringLength(0);
        }
        this.commandHelper = new lHwY(this.inputField);
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("RETURN") }, cancellable = true)
    public void drawScreen(final int n, final int n2, final float n3, final CallbackInfo callbackInfo) {
        this.commandHelper.eGiF(n, n2, n3);
    }
}
