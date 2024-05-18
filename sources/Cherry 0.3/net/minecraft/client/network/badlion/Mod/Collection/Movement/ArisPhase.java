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
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.util.Timer;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.Mod.Mod;

public class ArisPhase extends Mod
{
    private boolean Vanilla;
    private TimeMeme timer;
    private int resetNext;
    
    public ArisPhase() {
        super("ArisPhase", Category.MOVEMENT);
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
        if (this.isInsideBlock() && ClientUtils.player().isSneaking()) {
            final float yaw = ClientUtils.yaw();
            ClientUtils.player().boundingBox.offsetAndUpdate(0.7 * Math.cos(Math.toRadians(yaw + 90.0f)), 0.0, 0.7 * Math.sin(Math.toRadians(yaw + 90.0f)));
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
