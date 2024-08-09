package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.BetterBeacons;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(BeaconScreen.class)
public abstract class BeaconScreenMixin extends HandledScreen<BeaconScreenHandler> {
    @Shadow
    protected abstract <T extends ClickableWidget> void addButton(T button);

    public BeaconScreenMixin(BeaconScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/BeaconScreen;addButton(Lnet/minecraft/client/gui/widget/ClickableWidget;)V", ordinal = 1, shift = At.Shift.AFTER), cancellable = true)
    private void changeButtons(CallbackInfo info) {
        if (!Modules.get().get(BetterBeacons.class).isEnabled()) {
            return;
        }

        List<StatusEffect> effects = Arrays.stream(BeaconBlockEntity.EFFECTS_BY_LEVEL).flatMap(Arrays::stream).toList();
        if (client.currentScreen instanceof BeaconScreen beaconScreen) {
            for (int x = 0; x < 3;x++) {
                for (int y = 0; y < 2; y++) {
                    addButton(beaconScreen.new EffectButtonWidget(this.x + x * 25 + 27, this.y + y * 25 + 32, effects.get(x * 2 + y), true, -1));
                    addButton(beaconScreen.new EffectButtonWidget(this.x + x * 25 + 133, this.y + y * 25 + 32, effects.get(x * 2 + y), false, -1));
                }
            }
        }

        info.cancel();
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void onDrawBackground(MatrixStack matrixStack, float delta, int mouseX, int mouseY, CallbackInfo info) {
        if (!Modules.get().get(BetterBeacons.class).isEnabled()) {
            return;
        }

        // This will clear the background from useless pyramid graphics
        DrawableHelper.fill(matrixStack,this.x + 10,this.y + 7,this.x + 220,this.y + 98, 0xFF212121);
    }

    @Inject(method = "drawForeground", at = @At("HEAD"))
    private void onDrawForeground(MatrixStack matrixStack, int mouseX, int mouseY, CallbackInfo info) {
        if (!Modules.get().get(BetterBeacons.class).isEnabled()) {
            return;
        }

        int beaconLvl = getScreenHandler().getProperties();
        if (beaconLvl < 4) {
            drawCenteredText(matrixStack, this.textRenderer, "Secondary power inactive at beacon lvl " + beaconLvl, backgroundWidth / 2, 86, 14737632);
        }
    }
}
