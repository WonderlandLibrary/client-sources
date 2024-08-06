package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.MovementInputEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.HypixelScaffold;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.InventoryMove;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends InputMixin {

    @Shadow
    @Final
    private GameOptions settings;

    private boolean isPressedOnKeyboard(KeyBinding keyBinding) {
        return InputUtil.isKeyPressed(
            C.mc.getWindow().getHandle(),
            ((KeybindingAccessor) keyBinding).getBoundKey().getCode()
        );
    }

    @Inject(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/input/KeyboardInput;sneaking:Z",
            shift = At.Shift.AFTER
        ),
        allow = 1
    )
    private void injectMovementInputEvent(
        boolean slowDown,
        float f,
        CallbackInfo ci
    ) {
        MovementInputEvent event = new MovementInputEvent(
            this.pressingForward,
            this.pressingBack,
            this.pressingLeft,
            this.pressingRight,
            this.sneaking,
            this.jumping
        );
        Bus.post(event);

        this.jumping = event.jumping;
        this.sneaking = event.sneaking;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectMovementInputEvent_post(
        boolean slowDown,
        float f,
        CallbackInfo ci
    ) {
        if (HypixelScaffold.justStarted || HypixelScaffold.startedCounter > 0) {
            this.movementSideways = 0;
            this.movementForward = 0;
        }
    }

    /**
     * Hook inventory move module
     */

    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"
        )
    )
    private boolean hookInventoryMove(KeyBinding keyBinding) {
        if (!(C.mc.currentScreen instanceof ChatScreen)) {
            // hmmm autism.
            if (
                keyBinding
                    .getBoundKeyLocalizedText()
                    .equals(C.mc.options.sneakKey.getBoundKeyLocalizedText()) &&
                !InventoryMove.AllowCrouching
            ) return keyBinding.isPressed();
            return ModuleManager.isEnabled(InventoryMove.class)
                ? isPressedOnKeyboard(keyBinding)
                : keyBinding.isPressed();
        }
        return false;
    }

    @Inject(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/input/KeyboardInput;pressingBack:Z",
            ordinal = 0
        )
    )
    private void hookInventoryMoveSprint(
        boolean slowDown,
        float f,
        CallbackInfo ci
    ) {
        if (
            ModuleManager.isEnabled(InventoryMove.class) &&
            InventoryMove.mode != InventoryMove.Mode.Hypixel
        ) {
            if (!(C.mc.currentScreen instanceof ChatScreen)) {
                settings.sprintKey.setPressed(
                    isPressedOnKeyboard(settings.sprintKey)
                );
            }
        }
    }
}
