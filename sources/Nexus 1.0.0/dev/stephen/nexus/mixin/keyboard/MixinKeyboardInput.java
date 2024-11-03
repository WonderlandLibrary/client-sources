package dev.stephen.nexus.mixin.keyboard;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.event.impl.input.EventMovementInput;
import dev.stephen.nexus.module.modules.movement.InvMove;
import dev.stephen.nexus.utils.mc.KeyboardUtil;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class MixinKeyboardInput extends MixinInput {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
    private boolean tickInject1(KeyBinding keyBinding) {
        return Client.INSTANCE.getModuleManager().getModule(InvMove.class).shouldHandleInputs(keyBinding) ? KeyboardUtil.isPressedOnKeyboard(keyBinding) : keyBinding.isPressed();
    }

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/KeyboardInput;sneaking:Z", shift = At.Shift.AFTER), allow = 1)
    private void tickInject2(boolean slowDown, float f, CallbackInfo ci) {
        float movementForward = KeyboardInput.getMovementMultiplier(this.pressingForward, this.pressingBack);
        float movementSideways = KeyboardInput.getMovementMultiplier(this.pressingLeft, this.pressingRight);

        EventMovementInput eventMovementInput = new EventMovementInput(
                this.pressingForward,
                this.pressingBack,
                this.pressingLeft,
                this.pressingRight,
                this.jumping,
                this.sneaking,
                movementForward,
                movementSideways
        );

        Client.INSTANCE.getEventManager().post(eventMovementInput);

        this.pressingForward = eventMovementInput.isPressingForward();
        this.pressingBack = eventMovementInput.isPressingBack();
        this.pressingLeft = eventMovementInput.isPressingLeft();
        this.pressingRight = eventMovementInput.isPressingRight();
        this.movementForward = eventMovementInput.getMovementForward();
        this.movementSideways = eventMovementInput.getMovementSideways();

        this.jumping = eventMovementInput.isJumping();
        this.sneaking = eventMovementInput.isSneaking();
    }

}