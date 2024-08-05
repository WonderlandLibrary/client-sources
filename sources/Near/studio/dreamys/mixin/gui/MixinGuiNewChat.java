package studio.dreamys.mixin.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat extends Gui {

    @Final
    @Shadow
    private Minecraft mc;

    @Shadow
    public static int calculateChatboxHeight(float scale) {
        return 0;
    }

    @Shadow
    public boolean getChatOpen() {
        return false;
    }

    @Overwrite
    public int getChatHeight() {
        return calculateChatboxHeight(getChatOpen() ? mc.gameSettings.chatHeightFocused * 2.5F : mc.gameSettings.chatHeightUnfocused);
    }

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    public void transparent(int a, int b, int c, int d, int e) {}

//    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
//    public int font(FontRenderer instance, String text, float x, float y, int color) {
//        return Fonts.font35RobotoMedium.drawStringWithShadow(text, x, y, color);
//    }
}
