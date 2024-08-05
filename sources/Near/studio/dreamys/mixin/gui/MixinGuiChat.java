package studio.dreamys.mixin.gui;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiChat.class)
public class MixinGuiChat extends GuiScreen {

    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiChat;drawRect(IIIII)V"))
    public void transparent(int a, int b, int c, int d, int e) {}

//    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiTextField;<init>(ILnet/minecraft/client/gui/FontRenderer;IIII)V"))
//    public FontRenderer font(FontRenderer fontRenderer) {
//        return Fonts.font40RobotoMedium;
//    }
}
