package me.travis.wurstplus.module.modules.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;
import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.BlockInteractionHelper;
import me.travis.wurstplus.util.EntityUtil;
import me.travis.wurstplus.util.Friends;
import me.travis.wurstplus.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
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

@Module.Info(name="AutoTrap", category=Module.Category.COMBAT)
public class AutoTrap
extends Module {
    private final Vec3d[] offsetsDefault = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] offsetsExtra = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0, 0.0)};
    private Setting<Double> range = this.register(Settings.d("Range", 5.5));
    private Setting<Integer> blockPerTick = this.register(Settings.i("Blocks per Tick", 4));
    private Setting<Boolean> rotate = this.register(Settings.b("Rotate", true));
    private Setting<Boolean> extrablock = this.register(Settings.b("Extra Block", true));
    private Setting<Boolean> chad = this.register(Settings.b("Chad Mode", false));
    private Setting<Boolean> announceUsage;
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private boolean isSneaking = false;
    private int offsetStep = 0;
    private boolean firstRun;
    private double yHeight;
    private double xPos;
    private double zPos;

    public AutoTrap() {
        this.announceUsage =  this.register(Settings.b("Announce Usage", true));
    }

    @Override
    protected void onEnable() {
        if (AutoTrap.mc.player == null) {
            this.disable();
            return;
        }
        this.yHeight = (int) Math.ceil(mc.player.posY);
        this.xPos = (int) Math.ceil(mc.player.posX);
        this.zPos = (int) Math.ceil(mc.player.posZ);
        this.firstRun = true;
        this.playerHotbarSlot = Wrapper.getPlayer().inventory.currentItem;
        this.lastHotbarSlot = -1;
    }

    @Override
    protected void onDisable() {
        if (AutoTrap.mc.player == null) {
            return;
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue()) {
            Command.sendChatMessage("we aint \u00A7l\u00A74trapping\u00A7r no more");
        }
    }

    @Override
    public void onUpdate() {
        if (AutoTrap.mc.player == null) {
            return;
        }
        if (chad.getValue()) {
            if ( (Math.ceil(mc.player.posY)) != this.yHeight || (Math.ceil(mc.player.posX)) != this.xPos || (Math.ceil(mc.player.posY)) != this.zPos) {
                Command.sendChatMessage("c: "+Math.ceil(mc.player.posX)+" "+Math.ceil(mc.player.posY)+" "+Math.ceil(mc.player.posZ));
                Command.sendChatMessage("d: "+this.xPos+" "+this.yHeight+" "+this.zPos);
                this.disable();
            }
        }
        this.findClosestTarget();
        if (this.closestTarget == null) {
            if (this.firstRun) {
                this.firstRun = false;
                if (this.announceUsage.getValue()) {
                    Command.sendChatMessage("we \u00A7l\u00A72trapping\u00A7r");
                }
            }
            return;
        }
        if (this.firstRun) {
            this.firstRun = false;
            this.lastTickTargetName = this.closestTarget.getName();
        } else if (!this.lastTickTargetName.equals(this.closestTarget.getName())) {
            this.lastTickTargetName = this.closestTarget.getName();
            this.offsetStep = 0;
        }
        ArrayList placeTargets = new ArrayList();
        if (extrablock.getValue()) {
            Collections.addAll(placeTargets, this.offsetsExtra);
        } 
        else {
            Collections.addAll(placeTargets, this.offsetsDefault);
        } 
        int blocksPlaced = 0;
        while (blocksPlaced < this.blockPerTick.getValue()) {
            if (this.offsetStep >= placeTargets.size()) {
                this.offsetStep = 0;
                break;
            }
            BlockPos offsetPos = new BlockPos((Vec3d)placeTargets.get(this.offsetStep));
            BlockPos targetPos = new BlockPos(this.closestTarget.getPositionVector()).down().add(offsetPos.x, offsetPos.y, offsetPos.z);
            boolean shouldTryToPlace = true;
            if (!Wrapper.getWorld().getBlockState(targetPos).getMaterial().isReplaceable()) {
                shouldTryToPlace = false;
            }
            for (Entity entity : AutoTrap.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                shouldTryToPlace = false;
                break;
            }
            if (shouldTryToPlace && this.placeBlock(targetPos)) {
                ++blocksPlaced;
            }
            ++this.offsetStep;
        }
        if (blocksPlaced > 0) {
            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                Wrapper.getPlayer().inventory.currentItem = this.playerHotbarSlot;
                this.lastHotbarSlot = this.playerHotbarSlot;
            }
            if (this.isSneaking) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.isSneaking = false;
            }
        }
    }

    private boolean placeBlock(BlockPos pos) {
        if (!AutoTrap.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!BlockInteractionHelper.checkForNeighbours(pos)) {
            return false;
        }
        Vec3d eyesPos = new Vec3d(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + (double)Wrapper.getPlayer().getEyeHeight(), Wrapper.getPlayer().posZ);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3d hitVec;
            Block neighborPos;
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!AutoTrap.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(AutoTrap.mc.world.getBlockState(neighbor), false) || eyesPos.distanceTo(hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5))) > this.range.getValue()) continue;
            int obiSlot = this.findObiInHotbar();
            if (obiSlot == -1) {
                this.disable();
                return false;
            }
            if (this.lastHotbarSlot != obiSlot) {
                Wrapper.getPlayer().inventory.currentItem = obiSlot;
                this.lastHotbarSlot = obiSlot;
            }
            if (BlockInteractionHelper.blackList.contains((Object)(neighborPos = AutoTrap.mc.world.getBlockState(neighbor).getBlock()))) {
                AutoTrap.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AutoTrap.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                this.isSneaking = true;
            }
            if (this.rotate.getValue().booleanValue()) {
                BlockInteractionHelper.faceVectorPacketInstant(hitVec);
            }
            AutoTrap.mc.playerController.processRightClickBlock(AutoTrap.mc.player, AutoTrap.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            AutoTrap.mc.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return false;
    }

    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 1; i < 10; i++) {
            Block block;
            ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            slot = i;
            break;
        }
        return slot;
    }

    private void findClosestTarget() {
        List<EntityPlayer> entities = new ArrayList<EntityPlayer>();
        entities.addAll(mc.world.playerEntities.stream().filter(entityPlayer -> !Friends.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
        this.closestTarget = null;
        for (EntityPlayer target : entities) {
            if (target.getName() == AutoTrap.mc.player.getName() || Friends.isFriend(target.getName()) || !EntityUtil.isLiving((Entity)target) || target.getHealth() <= 0.0f) continue;
            if (this.closestTarget == null) {
                this.closestTarget = target;
                continue;
            }
            if (!(Wrapper.getPlayer().getDistance((Entity)target) < Wrapper.getPlayer().getDistance((Entity)this.closestTarget))) continue;
            this.closestTarget = target;
        }
    }
}

