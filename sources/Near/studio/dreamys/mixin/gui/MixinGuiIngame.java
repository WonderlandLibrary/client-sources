package studio.dreamys.mixin.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.boss.BossStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Objects;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

//    @Overwrite //custom font
//    public FontRenderer getFontRenderer() {
//        return Fonts.font35MontserratMedium;
//    }

    @Redirect(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawRect(IIIII)V"))
    public void transparentBackground(int a, int b, int c, int d, int e) {

    }

//    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"), index = 1)
//    public int fixSpacing(int a) {
//        return 0;
//    }

    @ModifyArg(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"), index = 3)
    public int fixColor(int x) {
        return Color.WHITE.getRGB();
    }

    @Redirect(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I", ordinal = 1))
    public int removeRedNumbers(FontRenderer instance, String text, int x, int y, int color) {
        return x;
    }

    @Inject(method = "renderBossHealth", at = @At("HEAD"), cancellable = true)
    public void removeWitherborn(CallbackInfo ci) {
        if (Objects.equals(ChatFormatting.stripFormatting(BossStatus.bossName), "Wither")) {
            ci.cancel();
        }
    }

    @Overwrite //anti pumpkin overlay
    protected void renderPumpkinOverlay(ScaledResolution scaledRes) {

    }
}
