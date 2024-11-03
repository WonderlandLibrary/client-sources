package net.silentclient.client.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.IChatComponent;
import net.silentclient.client.Client;
import net.silentclient.client.mods.render.ChatMod;
import net.silentclient.client.utils.MathUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(GuiNewChat.class)
public abstract class GuiNewChatMixin extends Gui {
    @Shadow public abstract float getChatScale();

    @Shadow public abstract int getLineCount();

    @Shadow @Final private Minecraft mc;
    @Shadow private boolean isScrolled;
    private float percentComplete;
    private int newLines;
    private long prevMillis = System.currentTimeMillis();
    private float animationPercent;
    private int lineBeingDrawn;

    private void updatePercentage(long diff) {
        if (percentComplete < 1) {
            percentComplete += (Client.getInstance().getSettingsManager().getSettingByClass(ChatMod.class, "Smooth Speed").getValDouble() / 1000) * (float) diff;
        }
        percentComplete = MathUtils.clamp(percentComplete, 0, 1);
    }

    @Inject(method = "drawChat", at = @At("HEAD"), cancellable = true)
    private void modifyChatRendering(CallbackInfo ci) {
        long current = System.currentTimeMillis();
        long diff = current - prevMillis;
        prevMillis = current;
        updatePercentage(diff);
        float t = percentComplete;
        animationPercent = MathUtils.clamp(1 - (--t) * t * t * t, 0, 1);
    }

    @Inject(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", ordinal = 0, shift = At.Shift.AFTER))
    private void translate(CallbackInfo ci) {
        float y = 0;
        if (Client.getInstance().getModInstances().getModByClass(ChatMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(ChatMod.class, "Smooth").getValBoolean() && !this.isScrolled) {
            y += (9 - 9 * animationPercent) * this.getChatScale();
        }
        GlStateManager.translate(0, y, 0);
    }

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    private void transparentBackground(int left, int top, int right, int bottom, int color) {

        boolean transparent = !Client.getInstance().getModInstances().getModByClass(ChatMod.class).isEnabled() || (Client.getInstance().getModInstances().getModByClass(ChatMod.class).isEnabled() &&
                !Client.getInstance().getSettingsManager().getSettingByClass(ChatMod.class, "Disable Background").getValBoolean());

        if (transparent) {
            drawRect(left, top, right, bottom, color);
        }
    }

    @ModifyArg(method = "drawChat", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", ordinal = 0, remap = false), index = 0)
    private int getLineBeingDrawn(int line) {
        lineBeingDrawn = line;
        return line;
    }

    @ModifyArg(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"), index = 3)
    private int modifyTextOpacity(int original) {
        if (Client.getInstance().getModInstances().getModByClass(ChatMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(ChatMod.class, "Smooth").getValBoolean() && lineBeingDrawn <= newLines) {
            int opacity = (original >> 24) & 0xFF;
            opacity *= animationPercent;
            return (original & ~(0xFF << 24)) | (opacity << 24);
        } else {
            return original;
        }
    }

    @Inject(method = "printChatMessageWithOptionalDeletion", at = @At("HEAD"))
    private void printChatMessageWithOptionalDeletion(CallbackInfo ci) {
        percentComplete = 0;
    }

    @ModifyVariable(method = "setChatLine", at = @At("STORE"), ordinal = 0)
    private List<IChatComponent> setNewLines(List<IChatComponent> original) {
        newLines = original.size() - 1;
        return original;
    }

    @Inject(method = "getChatComponent", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiNewChat;scrollPos:I"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void getChatComponent(int mouseX, int mouseY, CallbackInfoReturnable<IChatComponent> cir, ScaledResolution scaledresolution, int i, float f, int j, int k, int l) {
        int line = k / mc.fontRendererObj.FONT_HEIGHT;
        if (line >= getLineCount()) cir.setReturnValue(null);
    }

    @Redirect(method = "deleteChatLine", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getChatLineID()I"))
    private int deleteChatLine(ChatLine instance) {
        if (instance == null) return -1;
        return instance.getChatLineID();
    }

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    public int renderShadow(FontRenderer instance, String text, float x, float y, int color) {
        return instance.drawString(text, x, y, color, Client.getInstance().getModInstances().getModByClass(ChatMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(ChatMod.class, "Font Shadow").getValBoolean());
    }
}
