package wwe.modules.movement;

import java.text.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import wwe.*;

public class EntitySpeed extends qMIe
{
    private final DecimalFormat PxbF;
    public NumberValue speed;
    
    public EntitySpeed() {
        super("Entity Speed", "Allows you to go faster on entities", 0, Category.MOVEMENT);
        this.PxbF = new DecimalFormat("#.#");
    }
    
    @Override
    public void HrDr() {
        this.speed = new NumberValue("Speed", this, "The speed of the entity you are riding", 3.5, 0.0, 5.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        this.owgW("Entity Speed " + TextFormatting.DARK_GRAY + this.PxbF.format(((IcRI<Object>)this.speed).OnGi()));
        if (yYGD.AXSL((Entity)Wrapper.mc.player) != null) {
            final MovementInput movementInput = Wrapper.mc.player.movementInput;
            float moveForward = movementInput.moveForward;
            float moveStrafe = movementInput.moveStrafe;
            float rotationYaw = Wrapper.mc.player.rotationYaw;
            if (moveForward == 0.0 && moveStrafe == 0.0) {
                yYGD.AXSL((Entity)Wrapper.mc.player).motionX = 0.0;
                yYGD.AXSL((Entity)Wrapper.mc.player).motionZ = 0.0;
                if (EntitySpeed.\u200a\u200f\u200c\u200a\u200c\u200d\u200f\u2003\u2009) {
                    throw null;
                }
            }
            else {
                if (moveForward != 0.0) {
                    if (moveStrafe > 0.0) {
                        final float n = rotationYaw;
                        int n2;
                        if (moveForward > 0.0) {
                            n2 = -45;
                            if (EntitySpeed.\u200a\u200f\u200c\u200a\u200c\u200d\u200f\u2003\u2009) {
                                throw null;
                            }
                        }
                        else {
                            n2 = 45;
                        }
                        rotationYaw = n + n2;
                        if (EntitySpeed.\u200a\u200f\u200c\u200a\u200c\u200d\u200f\u2003\u2009) {
                            throw null;
                        }
                    }
                    else if (moveStrafe < 0.0) {
                        final float n3 = rotationYaw;
                        int n4;
                        if (moveForward > 0.0) {
                            n4 = 45;
                            if (EntitySpeed.\u200a\u200f\u200c\u200a\u200c\u200d\u200f\u2003\u2009) {
                                throw null;
                            }
                        }
                        else {
                            n4 = -45;
                        }
                        rotationYaw = n3 + n4;
                    }
                    moveStrafe = 0.0f;
                    if (moveForward > 0.0) {
                        moveForward = 1.0f;
                        if (EntitySpeed.\u200a\u200f\u200c\u200a\u200c\u200d\u200f\u2003\u2009) {
                            throw null;
                        }
                    }
                    else if (moveForward < 0.0) {
                        moveForward = -1.0f;
                    }
                }
                if (moveStrafe > 0.0) {
                    moveStrafe = 1.0f;
                    if (EntitySpeed.\u200a\u200f\u200c\u200a\u200c\u200d\u200f\u2003\u2009) {
                        throw null;
                    }
                }
                else if (moveStrafe < 0.0) {
                    moveStrafe = -1.0f;
                }
                yYGD.AXSL((Entity)Wrapper.mc.player).motionX = moveForward * this.speed.OnGi() * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * this.speed.OnGi() * Math.sin(Math.toRadians(rotationYaw + 90.0f));
                yYGD.AXSL((Entity)Wrapper.mc.player).motionZ = moveForward * this.speed.OnGi() * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * this.speed.OnGi() * Math.cos(Math.toRadians(rotationYaw + 90.0f));
            }
        }
    }
}
