package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.BetterTab;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    private static final Identifier MATHAX_ICON = new MatHaxIdentifier("icons/128.png");

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0), index = 1)
    private int modifyCount(int count) {
        BetterTab module = Modules.get().get(BetterTab.class);
        return module.isEnabled() ? module.tabSizeSetting.get() : count;
    }

    @Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    public void getPlayerName(PlayerListEntry playerListEntry, CallbackInfoReturnable<Text> infoReturnable) {
        BetterTab betterTab = Modules.get().get(BetterTab.class);
        if (betterTab.isEnabled()) {
            infoReturnable.setReturnValue(betterTab.getPlayerName(playerListEntry));
        }
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"), index = 0)
    private int modifyWidth(int width) {
        BetterTab module = Modules.get().get(BetterTab.class);
        return module.isEnabled() && module.accurateLatencySetting.get() ? width + 30 : width;
    }

    @Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
    private void onRenderLatencyIcon(MatrixStack matrixStack, int width, int x, int y, PlayerListEntry playerListEntry, CallbackInfo info) {
        // MATHAX ICONS

        BetterTab betterTab = Modules.get().get(BetterTab.class);
        if (betterTab.isEnabled() && betterTab.accurateLatencySetting.get()) {
            int latency = Utils.clamp(playerListEntry.getLatency(), 0, 9999);
            String text = latency + "ms";
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            textRenderer.drawWithShadow(matrixStack, text, (float) x + width - textRenderer.getWidth(text), (float) y, latency < 150 ? 0x00E970 : latency < 300 ? 0xE7D020 : 0xD74238);

            info.cancel();
        }
    }
}
