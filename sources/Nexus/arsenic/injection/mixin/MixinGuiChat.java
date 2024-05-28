package arsenic.injection.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import arsenic.main.Nexus;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

@Mixin(value = GuiChat.class)
public class MixinGuiChat extends GuiScreen {

    @Shadow
    protected GuiTextField inputField;

    private String trimmedAutoCompletion;
    private String lastArg;
    private Boolean isLastArgValidArg;

    /**
     * @author kv
     * @reason because green
     */
    @Inject(method = "keyTyped", at = @At("RETURN"))
    public void keyTypedReturn(char typedChar, int keyCode, CallbackInfo ci) {
        if (inputField.getText().startsWith(".")) {
            inputField.setTextColor(Nexus.getNexus().getThemeManager().getCurrentTheme().getMainColor());

            if (keyCode != 15 && keyCode != 1) {
                Nexus.getNexus().getCommandManager().updateAutoCompletions(inputField.getText());
            }

            String latestAutoCompletion = Nexus.getNexus().getCommandManager().getAutoCompletionWithoutRotation();
            lastArg = inputField.getText().substring(inputField.getText().lastIndexOf((inputField.getText().contains(" ") ? ' ' : '.')) + 1);
            trimmedAutoCompletion = latestAutoCompletion.toLowerCase().replaceFirst(lastArg.toLowerCase(), "");
            isLastArgValidArg = (trimmedAutoCompletion.length() == latestAutoCompletion.length() || latestAutoCompletion.length() < lastArg.length()) && lastArg.length() != 0;
        } else {
            inputField.setTextColor(0xE0E0E0);
        }
    }

    @Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true)
    public void keyTypedHead(char typedChar, int keyCode, CallbackInfo ci) {
        if (inputField.getText().startsWith(".") && keyCode == 15) {
             inputField.setText(inputField.getText().substring(0,
             inputField.getText().lastIndexOf((inputField.getText().contains(" ") ? ' ' : '.')) + 1));
             inputField.writeText(Nexus.getNexus().getCommandManager().getAutoCompletion());
             keyTypedReturn(typedChar, keyCode, ci);
             ci.cancel();
        }
    }

    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (inputField.getText().startsWith(".")) {
            if(isLastArgValidArg) {
                mc.fontRendererObj.drawStringWithShadow(
                        trimmedAutoCompletion,
                        inputField.xPosition + mc.fontRendererObj.getStringWidth(inputField.getText().replace(lastArg, "")),
                        inputField.yPosition - mc.fontRendererObj.FONT_HEIGHT * 1.2f,
                        0x999999
                );
            } else {
                mc.fontRendererObj.drawStringWithShadow(
                        trimmedAutoCompletion,
                        inputField.xPosition + mc.fontRendererObj.getStringWidth(inputField.getText()),
                        inputField.yPosition,
                        0x999999);
            }
        }
    }
}
