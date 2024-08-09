package dev.luvbeeq.baritone.utils;

import dev.luvbeeq.baritone.api.utils.input.Input;
import net.minecraft.util.MovementInput;

public class PlayerMovementInput extends MovementInput {

    private final InputOverrideHandler handler;

    PlayerMovementInput(InputOverrideHandler handler) {
        this.handler = handler;
    }

    @Override
    public void tickMovement(boolean p_225607_1_) {
        this.forwardKeyDown = this.handler.isInputForcedDown(Input.MOVE_FORWARD);
        this.backKeyDown = this.handler.isInputForcedDown(Input.MOVE_BACK);
        this.leftKeyDown = this.handler.isInputForcedDown(Input.MOVE_LEFT);
        this.rightKeyDown = this.handler.isInputForcedDown(Input.MOVE_RIGHT);
        this.moveForward = this.forwardKeyDown == this.backKeyDown ? 0.0F : (this.forwardKeyDown ? 1.0F : -1.0F);
        this.moveStrafe = this.leftKeyDown == this.rightKeyDown ? 0.0F : (this.leftKeyDown ? 1.0F : -1.0F);

        this.jump = this.handler.isInputForcedDown(Input.JUMP);
        this.sneaking = this.handler.isInputForcedDown(Input.SNEAK);

        if (this.sneaking) {
            this.moveStrafe = (float) (this.moveStrafe * 0.3D);
            this.moveForward = (float) (this.moveForward * 0.3D);
        }

//        this.moveStrafe = 0.0F;
//        this.moveForward = 0.0F;
//
//        this.jump = handler.isInputForcedDown(Input.JUMP); // oppa gangnam style
//
//        if (this.forwardKeyDown = handler.isInputForcedDown(Input.MOVE_FORWARD)) {
//            this.moveForward=++;
//        }
//
//        if (this.backKeyDown = handler.isInputForcedDown(Input.MOVE_BACK)) {
//            this.moveForward--;
//        }
//
//        if (this.leftKeyDown = handler.isInputForcedDown(Input.MOVE_LEFT)) {
//            this.moveStrafe++;
//        }
//
//        if (this.rightKeyDown = handler.isInputForcedDown(Input.MOVE_RIGHT)) {
//            this.moveStrafe--;
//        }
//
//        if (this.sneaking = handler.isInputForcedDown(Input.SNEAK)) {
//            this.moveStrafe *= 0.3F;
//            this.moveForward *= 0.3F;
//        }
    }
}
