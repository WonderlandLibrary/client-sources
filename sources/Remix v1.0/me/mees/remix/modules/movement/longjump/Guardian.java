package me.mees.remix.modules.movement.longjump;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import net.minecraft.util.*;
import me.satisfactory.base.events.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import pw.stamina.causam.scan.method.model.*;

public class Guardian extends Mode<Longjump>
{
    public Guardian(final Longjump parent) {
        super(parent, "Guardian");
    }
    
    public Block getBlock(final BlockPos pos) {
        return this.mc.theWorld.getBlockState(pos).getBlock();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        final BlockPos belowPlayerPos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ);
        final Block block = this.getBlock(belowPlayerPos);
        if ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) && !(block instanceof BlockStairs)) {
            if (this.mc.thePlayer.onGround) {
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                if (this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0, this.mc.thePlayer.posZ)).getBlock() == Blocks.air) {
                    this.mc.thePlayer.setSpeed(9.5);
                }
                this.mc.thePlayer.motionY = 0.4;
            }
            else if (this.mc.thePlayer.motionY < 0.0) {
                final EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.motionY += 0.02;
                this.mc.thePlayer.setSpeed((float)Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ));
            }
        }
        else {
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
        }
    }
}
