
/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.injection.mixin.event;

import de.dietrichpaul.clientbase.event.StrafeInputListener;
import de.dietrichpaul.clientbase.ClientBase;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {

    @Inject(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/KeyboardInput;sneaking:Z", shift = At.Shift.AFTER))
    public void onTick(boolean slowDown, float f, CallbackInfo ci) {
        final StrafeInputListener.StrafeInputEvent strafeInputEvent = ClientBase.INSTANCE.getEventDispatcher().post(new StrafeInputListener.StrafeInputEvent((int) movementForward, (int) movementSideways, jumping, sneaking));

        movementForward = strafeInputEvent.moveForward;
        movementSideways = strafeInputEvent.moveSideways;
        jumping = strafeInputEvent.jumping;
        sneaking = strafeInputEvent.sneaking;
    }
}
