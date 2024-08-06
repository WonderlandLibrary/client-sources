package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.modules.impl.movement.Sprint;
import net.minecraft.client.input.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Input.class)
public class InputMixin {

    @Shadow
    public boolean pressingRight;

    @Shadow
    public boolean pressingLeft;

    @Shadow
    public boolean pressingBack;

    @Shadow
    public boolean pressingForward;

    @Shadow
    public float movementForward;

    @Shadow
    public float movementSideways;

    @Shadow
    public boolean jumping;

    @Shadow
    public boolean sneaking;

    /**
     * @author scoliosis
     * @reason omnisprint
     */
    @Overwrite
    public boolean hasForwardMovement() {
        if (Sprint.shouldOverrideSprint()) return Sprint.shouldSprint();
        return this.movementForward > 1.0E-5F;
    }
}
