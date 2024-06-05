/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPostUpdate;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.misc.BlockUtils;
import drunkclient.beta.IMPL.managers.ModuleManager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.world.Timer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class BlockFly
extends Module {
    private BlockPos currentPos;
    Timer timer = new Timer();
    private EnumFacing currentFacing;
    private boolean rotated = false;
    public Option<Boolean> tower = new Option<Boolean>("Tower", "Tower", true);
    public Mode<Enum> towerMode = new Mode("Tower", "Tower", (Enum[])TowerMode.values(), (Enum)TowerMode.NCP);

    public BlockFly() {
        super("BlockFLY", new String[0], Type.MISC, "");
        this.addValues(this.tower, this.towerMode);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate E) {
        this.rotated = false;
        this.currentFacing = null;
        this.currentPos = null;
        BlockPos pos = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ);
        if (!(BlockFly.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) return;
        this.setBlockAndFacing(pos);
        if (this.currentPos == null) return;
        float[] facing = BlockUtils.getDirectionToBlock(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ(), this.currentFacing);
        if (((Boolean)this.tower.getValue()).booleanValue()) {
            if (BlockFly.mc.gameSettings.keyBindJump.pressed && !ModuleManager.getModuleByName("Speed").isEnabled()) {
                switch (this.towerMode.getModeAsString()) {
                    case "NCP": {
                        Minecraft.thePlayer.motionY = 0.4196;
                        break;
                    }
                }
            }
        }
        float yaw = facing[0];
        float pitch = Math.min(90.0f, facing[1] + 9.0f);
        this.rotated = true;
        E.setYaw(yaw);
        E.setPitch(pitch);
    }

    @EventHandler
    public void onUpdatePost(EventPostUpdate e) {
        if (this.currentPos == null) return;
        if (!this.timer.hasReached(40.0)) return;
        if (Minecraft.thePlayer.getHeldItem() == null) return;
        if (!(Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) return;
        if (!BlockFly.mc.playerController.onPlayerRightClick(Minecraft.thePlayer, BlockFly.mc.theWorld, Minecraft.thePlayer.getHeldItem(), this.currentPos, this.currentFacing, new Vec3(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ()))) return;
        this.timer.reset();
        Minecraft.thePlayer.swingItem();
    }

    private void setBlockAndFacing(BlockPos var1) {
        if (BlockFly.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            this.currentFacing = EnumFacing.UP;
            return;
        }
        if (BlockFly.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            this.currentFacing = EnumFacing.EAST;
            return;
        }
        if (BlockFly.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            this.currentFacing = EnumFacing.WEST;
            return;
        }
        if (BlockFly.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
            return;
        }
        if (BlockFly.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
            return;
        }
        this.currentFacing = null;
        this.currentPos = null;
    }

    static enum TowerMode {
        NCP;

    }
}

