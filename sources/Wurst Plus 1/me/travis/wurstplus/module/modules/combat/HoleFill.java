package me.travis.wurstplus.module.modules.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import me.travis.wurstplus.command.Command;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.setting.Setting;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;

// made by travis :D

@Module.Info(name = "HoleFill", category = Module.Category.COMBAT)
public class HoleFill extends Module
{
    private ArrayList<BlockPos> holes;
    private Setting<Double> range;
    private Setting<Boolean> announceUsage;
    private int obiSlot;
    private int oldSlot;
    private boolean flag;
    BlockPos pos;

    public HoleFill() {
        this.holes = new ArrayList<BlockPos>();
        this.range = this.register(Settings.d("Range", 4.0));
        this.obiSlot = -1;
        this.oldSlot = -1;
        this.flag = false;
        this.announceUsage =  this.register(Settings.b("Announce Usage", true));
    }
    
    public void onEnable() {
        if (mc.player == null) {
            this.disable();
            return;
        }
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("We \u00A7l\u00A72filling\u00A7r");
        }
        this.flag = false;
        if (ModuleManager.getModuleByName("Unco Aura").isEnabled()) {
            this.flag = true;
            ModuleManager.getModuleByName("Unco Aura").disable();
        }
    }

    public void onDisable() {
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("We aint \u00A7l\u00A74filling\u00A7r no more");
        }
        if (this.flag) {
            ModuleManager.getModuleByName("Unco Aura").enable();
            this.flag = false;
        }
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() && this.flag) {
            ModuleManager.getModuleByName("Unco Aura").enable();
            this.flag = false;
        }
        this.holes = new ArrayList<BlockPos>();
        this.obiSlot = -1;
        final Iterable<BlockPos> blocks = (Iterable<BlockPos>)BlockPos.getAllInBox(mc.player.getPosition().add(-1.0 * this.range.getValue(), -1.0 * this.range.getValue(), -1.0 * this.range.getValue()), mc.player.getPosition().add((double)this.range.getValue(), (double)this.range.getValue(), (double)this.range.getValue()));
        for (final BlockPos pos : blocks) {
            if (!mc.world.getBlockState(pos).getMaterial().blocksMovement() && !mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial().blocksMovement()) {
                final boolean solidNeighbours = (mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN) && (mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) && (mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) && (mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK | mc.world.getBlockState(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) && mc.world.getBlockState(pos.add(0, 0, 0)).getMaterial() == Material.AIR && mc.world.getBlockState(pos.add(0, 1, 0)).getMaterial() == Material.AIR && mc.world.getBlockState(pos.add(0, 2, 0)).getMaterial() == Material.AIR && !pos.equals((Object)new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ));
                this.hasObi();
                if (!solidNeighbours || !this.isEnabled()) {
                    continue;
                }
                if (this.obiSlot == -1) {
                    continue;
                }
                this.oldSlot = HoleFill.mc.player.inventory.currentItem;
                HoleFill.mc.player.inventory.currentItem = this.obiSlot;
                placeBlock(pos, true);
                HoleFill.mc.player.inventory.currentItem = this.oldSlot;
            }
        }
    }
    
    public boolean hasObi() {
        for (int i = 0; i < 9 && this.obiSlot == -1; ++i) {
            final ItemStack stack = HoleFill.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block == Blocks.OBSIDIAN) {
                    this.obiSlot = i;
                    return true;
                }
            }
        }
        return false;
    }
    
    private static void placeBlock(final BlockPos pos, final boolean spoofRotation) {
        if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            Command.sendChatMessage("BLOCK WAS UNABLE TO BE PLACED :(");
            return;
        }
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (mc.world.getBlockState(neighbor).getBlock().canCollideCheck(mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.45, 0.5, 0.55).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (spoofRotation) {
                    final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
                    final double diffX = hitVec.x - eyesPos.x;
                    final double diffY = hitVec.y - eyesPos.y;
                    final double diffZ = hitVec.z - eyesPos.z;
                    final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
                    final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
                    mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch), mc.player.onGround));
                }
                mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.rightClickDelayTimer = 4;
                return;
            }
        }
    }
}