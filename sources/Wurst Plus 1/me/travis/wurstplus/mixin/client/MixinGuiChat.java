package me.travis.wurstplus.mixin.client;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.gui.mc.wurstplusGuiChat;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.text.ITextComponent;


/**
 * Created by 086 on 11/11/2017.
 */
@Mixin(GuiChat.class)
public abstract class MixinGuiChat {

    @Shadow protected GuiTextField inputField;

    @Shadow public String historyBuffer;

    @Shadow public int sentHistoryCursor;

    @Shadow public abstract void initGui();

    @Inject(method = "Lnet/minecraft/client/gui/GuiChat;keyTyped(CI)V", at = @At("RETURN"))
    public void returnKeyTyped(char typedChar, int keyCode, CallbackInfo info) {
        if (!(Wrapper.getMinecraft().currentScreen instanceof GuiChat) || Wrapper.getMinecraft().currentScreen instanceof wurstplusGuiChat) return;
        if (inputField.getText().startsWith(Command.getCommandPrefix())) {
            Wrapper.getMinecraft().displayGuiScreen(new wurstplusGuiChat(inputField.getText(), historyBuffer, sentHistoryCursor));
        }
    }

    // @Override
    // public void setChatLine(final ITextComponent chatComponent, final int chatLineId, final int updateCounter, final boolean displayOnly) {
    //     if (chatLineId != 0) {
    //         this.func_146242_c(chatLineId);
    //     }
    //     for (final ITextComponent itextcomponent : GuiUtilRenderComponents.func_178908_a(chatComponent, MathHelper.func_76141_d(this.func_146228_f() / this.func_146244_h()), this.field_146247_f.field_71466_p, false, false)) {
    //         if (this.func_146241_e() && this.field_146250_j > 0) {
    //             this.field_146251_k = true;
    //             this.func_146229_b(1);
    //         }
    //         this.field_146253_i.add(0, new ChatLine(updateCounter, itextcomponent, chatLineId));
    //     }
    //     final InfiniteChat event = new InfiniteChat();
    //     MinecraftForge.EVENT_BUS.post((Event)event);
    //     if (event.getResult() == Event.Result.ALLOW && !displayOnly) {
    //         this.field_146252_h.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
    //     }
    //     else {
    //         while (this.field_146253_i.size() > 100) {
    //             this.field_146253_i.remove(this.field_146253_i.size() - 1);
    //         }
    //         if (!displayOnly) {
    //             this.field_146252_h.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));
    //             while (this.field_146252_h.size() > 100) {
    //                 this.field_146252_h.remove(this.field_146252_h.size() - 1);
    //             }
    //         }
    //     }
    // }

}
