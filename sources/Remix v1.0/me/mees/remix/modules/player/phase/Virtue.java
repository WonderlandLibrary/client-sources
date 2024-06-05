package me.mees.remix.modules.player.phase;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.player.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;
import net.minecraft.util.*;

public class Virtue extends Mode<Phase>
{
    public Virtue(final Phase parent) {
        super(parent, "Virtue");
    }
    
    @Subscriber
    public void blockPush(final EventPushOutOfBlocks event) {
        if (((Phase)this.parent).isInsideBlock() && this.mc.thePlayer.isSneaking()) {
            event.setCancelled(true);
        }
    }
    
    @Subscriber
    public void eventBBSet(final EventBBSet event) {
        if (event.getBoundingBox() != null && this.mc.thePlayer != null && event.getBoundingBox().maxY > this.mc.thePlayer.boundingBox.minY && this.mc.thePlayer.isSneaking()) {
            event.setBoundingBox(null);
        }
    }
    
    @Subscriber
    public void eventMove(final EventMove tick) {
        if (((Phase)this.parent).isInsideBlock() && this.mc.thePlayer.isSneaking()) {
            final AxisAlignedBB boundingBox = this.mc.thePlayer.boundingBox;
            final MovementInput movementInput = this.mc.thePlayer.movementInput;
            final double n = MovementInput.moveForward * 2.6 * Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final MovementInput movementInput2 = this.mc.thePlayer.movementInput;
            final double par1 = n + MovementInput.moveStrafe * 2.6 * Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final double par2 = 0.0;
            final MovementInput movementInput3 = this.mc.thePlayer.movementInput;
            final double n2 = MovementInput.moveForward * 2.6 * Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final MovementInput movementInput4 = this.mc.thePlayer.movementInput;
            boundingBox.offsetAndUpdate(par1, par2, n2 - MovementInput.moveStrafe * 2.6 * Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f)));
        }
    }
}
