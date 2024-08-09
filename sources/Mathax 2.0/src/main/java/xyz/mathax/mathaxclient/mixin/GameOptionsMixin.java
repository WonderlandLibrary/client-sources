package xyz.mathax.mathaxclient.mixin;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.events.game.ChangePerspectiveEvent;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.render.Freecam;
import xyz.mathax.mathaxclient.utils.input.KeyBinds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
    @Shadow
    @Final
    @Mutable
    public KeyBinding[] allKeys;

    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;allKeys:[Lnet/minecraft/client/option/KeyBinding;", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void onInitAfterKeysAll(MinecraftClient client, File optionsFile, CallbackInfo info) {
        allKeys = KeyBinds.apply(allKeys);
    }

    @Inject(method = "setPerspective", at = @At("HEAD"), cancellable = true)
    private void setPerspective(Perspective perspective, CallbackInfo info) {
        ChangePerspectiveEvent event = MatHax.EVENT_BUS.post(ChangePerspectiveEvent.get(perspective));
        if (event.isCancelled()) {
            info.cancel();
        }

        if (Modules.get().isEnabled(Freecam.class)) {
            info.cancel();
        }
    }
}
