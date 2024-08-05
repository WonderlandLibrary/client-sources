package studio.dreamys.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.dreamys.accessor.AccessMinecraft;
import studio.dreamys.font.Fonts;
import studio.dreamys.util.RenderUtils;

@Mixin(Minecraft.class)
public class MixinMinecraft implements AccessMinecraft {

    @Shadow
    private int rightClickDelayTimer;

    @Unique
    public FontRenderer mcFontRendererObj;

    @Shadow
    public FontRenderer fontRendererObj;

    @Shadow
    public GameSettings gameSettings;

    @Shadow
    public TextureManager renderEngine;

    private long lastFrame = getTime();
    Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    private void runGameLoop(CallbackInfo callbackInfo) {
        long currentTime = getTime();
        int deltaTime = (int) (currentTime - lastFrame);
        lastFrame = currentTime;

        RenderUtils.deltaTime = deltaTime;
    }

    @Redirect(method = "startGame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;fontRendererObj:Lnet/minecraft/client/gui/FontRenderer;", opcode = Opcodes.PUTFIELD))
    public void customFont(Minecraft instance, FontRenderer value) {
        fontRendererObj = Fonts.font35MontserratMedium;
        mcFontRendererObj = new FontRenderer(gameSettings, new ResourceLocation("textures/font/ascii.png"), renderEngine, false);
    }

//    ArrayList<KeyBinding> keybindArray = new ArrayList<>();
//
//    @Inject(method = "setIngameNotInFocus", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;unPressAllKeys()V"))
//    public void setIngameNotInFocus(CallbackInfo ci) {
//        keybindArray.clear();
//        for (KeyBinding binding : KeyBinding.keybindArray) {
//            keybindArray.add(clone(binding));
//        }
//        PlayerUtils.addMessage("called1");
//    }
//
//    public KeyBinding clone(KeyBinding key) {
//        return new KeyBinding(key.getKeyDescription(), key.getKeyCode(), key.getKeyCategory());
//    }
//
//    @Inject(method = "setIngameFocus", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V", shift = At.Shift.AFTER))
//    public void setIngameFocus(CallbackInfo ci) {
//        KeyBinding.keybindArray.clear();
//        KeyBinding.keybindArray.addAll(keybindArray);
//        keybindArray.clear();
//        PlayerUtils.addMessage("called2");
//    }

    @Overwrite
    public int getLimitFramerate() {
        return Minecraft.getMinecraft().gameSettings.limitFramerate;
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    @Override
    public FontRenderer getMcFontRendererObj() {
        return mcFontRendererObj;
    }
}
