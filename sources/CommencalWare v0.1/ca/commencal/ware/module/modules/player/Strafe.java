package ca.commencal.ware.module.modules.player;

import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.ModuleCategory;
import ca.commencal.ware.value.BooleanValue;
import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.MathHelper;

public class Strafe extends Module {

    public BooleanValue autoJump;

    public Strafe() {
        super("Strafe", ModuleCategory.PLAYER);

        autoJump = new BooleanValue("AutoJump", false);
    }
/*

        // movement data variables
        float playerSpeed = 0.2873f;
        float moveForward = mc.player.movementInput.moveForward;
        float moveStrafe = mc.player.movementInput.moveStrafe;
        float rotationPitch = mc.player.rotationPitch;
        float rotationYaw = mc.player.rotationYaw;



        // not movement input, stop all motion
        if (moveForward == 0.0f && moveStrafe == 0.0f)
        {
            p_Event.X = (0.0d);
            p_Event.Z = (0.0d);
        }
        else
        {
            if (moveForward != 0.0f)
            {
                if (moveStrafe > 0.0f)
                {
                    rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
                }
                else if (moveStrafe < 0.0f)
                {
                    rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
                }
                moveStrafe = 0.0f;
                if (moveForward > 0.0f)
                {
                    moveForward = 1.0f;
                }
                else if (moveForward < 0.0f)
                {
                    moveForward = -1.0f;
                }
            }
            p_Event.X = ((moveForward * playerSpeed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))) + (moveStrafe * playerSpeed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))));
            p_Event.Z = ((moveForward * playerSpeed) * Math.sin(Math.toRadians((rotationYaw + 90.0f))) - (moveStrafe * playerSpeed) * Math.cos(Math.toRadians((rotationYaw + 90.0f))));
        } */


}
