package me.travis.wurstplus.module.modules.combat;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.BlockInteractionHelper;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

@Module.Info(name="Surround", category=Module.Category.COMBAT)
public class Surround
extends Module {
    private final Vec3d[] surroundTargets = new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};
    private Setting<Boolean> triggerable = this.register(Settings.b("Triggerable", true));
    private Setting<Integer> timeoutTicks = this.register(Settings.integerBuilder("TimeoutTicks").withMinimum(1).withValue(20).withMaximum(100).build());
    private Setting<Integer> blocksPerTick = this.register(Settings.integerBuilder("Blocks per Tick").withMinimum(1).withValue(4).withMaximum(9).build());
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", true));
    private Setting<Boolean> hybrid = this.register(Settings.b("Hybrid", true));
    private Setting<Boolean> announceUsage;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int offsetStep = 0;
    private int totalTickRuns = 0;
    private boolean isSneaking = false;
    private boolean flag = false;
    private int yHeight;

    public Surround() {
        this.announceUsage =  this.register(Settings.b("Announce Usage", true));
    }
    @Override
    protected void onEnable() {
        this.flag = false;
        if (mc.player == null) {
            this.disable();
            return;
        }
        this.playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
        this.lastHotbarSlot = -1;
        this.yHeight = (int) Math.round(mc.player.posY);
        if (ModuleManager.getModuleByName("Unco Aura").isEnabled()) {
            this.flag = true;
            ModuleManager.getModuleByName("Unco Aura").disable();
        }
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("we surrounding");
        }
    }

    @Override
    protected void onDisable() {
        if (mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        if (this.flag) {
            ModuleManager.getModuleByName("Unco Aura").enable();
            this.flag = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("we aint surrounding no more");
        }
    }

    @Override
    public void onUpdate() {
        if (this.isDisabled() && this.flag) {
            ModuleManager.getModuleByName("Unco Aura").enable();
            this.flag = false;
        }
        if (mc.player == null) {
            return;
        }
        if (hybrid.getValue()) {
            if ( ((int) Math.round(mc.player.posY)) != this.yHeight) {
                this.disable();
            }
        }
        if (this.triggerable.getValue().booleanValue() && this.totalTickRuns >= this.timeoutTicks.getValue()) {
            this.totalTickRuns = 0;
            this.disable();
            return;
        }
        int blocksPlaced = 0;
        while (blocksPlaced < this.blocksPerTick.getValue()) {
            if (this.offsetStep >= this.surroundTargets.length) {
                this.offsetStep = 0;
                break;
            }
            BlockPos offsetPos = new BlockPos(this.surroundTargets[this.offsetStep]);
            BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.x, offsetPos.y, offsetPos.z);
            boolean shouldTryToPlace = true;
            if (!Wrapper.getWorld().getBlockState(targetPos).getMaterial().isReplaceable()) {
                shouldTryToPlace = false;
            }
            for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                shouldTryToPlace = false;
                break;
            }
            if (shouldTryToPlace && this.placeBlock(targetPos)) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (blocksPlaced > 0 && this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
        ++this.totalTickRuns;
    }

    private boolean placeBlock(BlockPos pos) {
        if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!BlockInteractionHelper.checkForNeighbours(pos)) {
            return false;
        }
        for (EnumFacing side : EnumFacing.values()) {
            Block neighborPos;
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockInteractionHelper.canBeClicked(neighbor)) continue;
            int obiSlot = this.findObiInHotbar();
            if (obiSlot == -1) {
                this.disable();
                return false;
            }
            if (this.lastHotbarSlot != obiSlot) {
                Wrapper.getPlayer().inventory.currentItem = obiSlot;
                this.lastHotbarSlot = obiSlot;
            }
            if (BlockInteractionHelper.blackList.contains((Object)(neighborPos = mc.world.getBlockState(neighbor).getBlock()))) {
                mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
                this.isSneaking = true;
            }
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (this.rotate.getValue().booleanValue()) {
                BlockInteractionHelper.faceVectorPacketInstant(hitVec);
            }
            mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return false;
    }

    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            slot = i;
            break;
        }
        return slot;
    }
}

