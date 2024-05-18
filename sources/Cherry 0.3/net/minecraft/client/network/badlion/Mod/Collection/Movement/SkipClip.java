// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Movement;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockHopper;
import net.minecraft.world.World;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.badlion.Events.EventBBSet;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.util.Timer;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.Mod.Mod;

public class SkipClip extends Mod
{
    private boolean Vanilla;
    private TimeMeme timer;
    private int resetNext;
    
    public SkipClip() {
        super("SkipClip", Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        this.timer = new TimeMeme();
        EventManager.register(this);
        this.Vanilla = true;
    }
    
    @Override
    public void onDisable() {
        Timer.timerSpeed = 1.0f;
        this.mc.thePlayer.noClip = false;
        EventManager.unregister(this);
        this.timer.reset();
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        this.setRenderName(String.format("%s %s", this.getModName()));
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (this.Vanilla) {
            --this.resetNext;
            double xOff = 0.0;
            double zOff = 0.0;
            final double multiplier = 2.6;
            final double mx = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final double mz = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            xOff = MovementInput.moveForward * 2.6 * mx + MovementInput.moveStrafe * 2.6 * mz;
            zOff = MovementInput.moveForward * 2.6 * mz - MovementInput.moveStrafe * 2.6 * mx;
            if (this.isInsideBlock() && this.mc.thePlayer.isSneaking()) {
                this.resetNext = 1;
            }
            if (this.resetNext > 0) {
                this.mc.thePlayer.boundingBox.offsetAndUpdate(xOff, 0.0, zOff);
            }
        }
        else if (this.timer.hasPassed(150.0) && this.mc.thePlayer.isCollidedHorizontally) {
            float yaw = this.mc.thePlayer.rotationYaw;
            if (this.mc.thePlayer.moveForward < 0.0f) {
                yaw += 180.0f;
            }
            if (this.mc.thePlayer.moveStrafing > 0.0f) {
                yaw -= 90.0f * ((this.mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((this.mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
            }
            if (this.mc.thePlayer.moveStrafing < 0.0f) {
                yaw += 90.0f * ((this.mc.thePlayer.moveForward > 0.0f) ? 0.5f : ((this.mc.thePlayer.moveForward < 0.0f) ? -0.5f : 1.0f));
            }
            final double horizontalMultiplier = 0.3;
            final double xOffset = (float)Math.cos((yaw + 90.0f) * 3.141592653589793 / 180.0) * 0.3;
            final double zOffset = (float)Math.sin((yaw + 90.0f) * 3.141592653589793 / 180.0) * 0.3;
            double yOffset = 0.0;
            for (int i = 0; i < 3; ++i) {
                yOffset += 0.01;
                this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - yOffset, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
                this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + xOffset * i, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + zOffset * i, this.mc.thePlayer.onGround));
            }
        }
        else if (!this.mc.thePlayer.isCollidedHorizontally) {
            this.timer.reset();
        }
    }
    
    @EventTarget
    private void onSetBB(final EventBBSet event) {
        if ((this.isInsideBlock() && Minecraft.gameSettings.keyBindJump.pressed) || (!this.isInsideBlock() && event.boundingBox != null && event.boundingBox.maxY > this.mc.thePlayer.boundingBox.minY && this.Vanilla && this.mc.thePlayer.isSneaking())) {
            event.boundingBox = null;
        }
    }
    
    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(this.mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(this.mc.thePlayer.boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(this.mc.thePlayer.boundingBox.minY); y < MathHelper.floor_double(this.mc.thePlayer.boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(this.mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(this.mc.thePlayer.boundingBox.maxZ) + 1; ++z) {
                    final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(this.mc.theWorld, new BlockPos(x, y, z), this.mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && this.mc.thePlayer.boundingBox.intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
