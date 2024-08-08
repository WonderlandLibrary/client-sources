package me.zeroeightsix.kami.module.modules.dev;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.module.modules.exploits.NoBreakAnimation;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.BlockInteractionHelper;
import me.zeroeightsix.kami.util.EntityUtil;
import me.zeroeightsix.kami.util.Friends;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.zeroeightsix.kami.util.BlockInteractionHelper.canBeClicked;
import static me.zeroeightsix.kami.util.BlockInteractionHelper.faceVectorPacketInstant;

/**
 * Created 6 August 2019 by hub
 * Updated 28 November 2019 by hub
 * updated by vik
 */
@Module.Info(name = "TrapMineplex", category = Module.Category.DEV)
public class TrapMineplex extends Module {

    private Setting<Double> range = register(Settings.doubleBuilder("Range").withMinimum(3.5).withValue(4.5).withMaximum(32.0).build());
    private Setting<Integer> blocksPerTick = register(Settings.integerBuilder("BlocksPerTick").withMinimum(1).withValue(2).withMaximum(23).build());
    private Setting<Integer> tickDelay = register(Settings.integerBuilder("TickDelay").withMinimum(0).withValue(2).withMaximum(10).build());
    private Setting<Cage> cage = register(Settings.e("Cage", Cage.TRAP));
    private Setting<Boolean> rotate = register(Settings.b("Rotate", false));
    private Setting<Boolean> noGlitchBlocks = register(Settings.b("NoGlitchBlocks", true));
    private Setting<Boolean> activeInFreecam = register(Settings.b("ActiveInFreecam", true));
    private Setting<Boolean> announceUsage = register(Settings.b("AnnounceUsage", true));
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int delayStep = 0;
    private boolean isSneaking = false;
    private int offsetStep = 0;
    private boolean firstRun;

    @Override
    protected void onEnable() {

        if (mc.player == null) {
            this.disable();
            return;
        }

        firstRun = true;

        // save initial player hand
        playerHotbarSlot = mc.player.inventory.currentItem;
        lastHotbarSlot = -1;

    }

    @Override
    protected void onDisable() {

        if (mc.player == null) {
            return;
        }

        if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = playerHotbarSlot;
        }

        if (isSneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            isSneaking = false;
        }

        playerHotbarSlot = -1;
        lastHotbarSlot = -1;

        if (announceUsage.getValue()) {
            Command.sendChatMessage("[TrapMineplex] " + ChatFormatting.RED.toString() + "Disabled" + ChatFormatting.RESET.toString() + "!");
        }

    }

    @Override
    public void onUpdate() {

        if (mc.player == null) {
            return;
        }

        if (!activeInFreecam.getValue() && ModuleManager.isModuleEnabled("Freecam")) {
            return;
        }

        if (!firstRun) {
            if (delayStep < tickDelay.getValue()) {
                delayStep++;
                return;
            } else {
                delayStep = 0;
            }
        }

        findClosestTarget();

        if (closestTarget == null) {
            if (firstRun) {
                firstRun = false;
                if (announceUsage.getValue()) {
                    Command.sendChatMessage("[TrapMineplex] " + ChatFormatting.GREEN.toString() + "Enabled" + ChatFormatting.RESET.toString() + ", waiting for target.");
                }
            }
            return;
        }

        if (firstRun) {
            firstRun = false;
            lastTickTargetName = closestTarget.getName();
            if (announceUsage.getValue()) {
                Command.sendChatMessage("[TrapMineplex] " + ChatFormatting.GREEN.toString() + "Enabled" + ChatFormatting.RESET.toString() + ", target: " + lastTickTargetName);
            }
        } else if (!lastTickTargetName.equals(closestTarget.getName())) {
            lastTickTargetName = closestTarget.getName();
            offsetStep = 0;
            if (announceUsage.getValue()) {
                Command.sendChatMessage("[TrapMineplex] New target: " + lastTickTargetName);
            }
        }

        List<Vec3d> placeTargets = new ArrayList<>();

        if (cage.getValue().equals(Cage.TRAP)) {
            Collections.addAll(placeTargets, Offsets.TRAP);
        }

        if (cage.getValue().equals(Cage.TRAPFULLROOF)) {
            Collections.addAll(placeTargets, Offsets.TRAPFULLROOF);
        }

        if (cage.getValue().equals(Cage.CRYSTALEXA)) {
            Collections.addAll(placeTargets, Offsets.CRYSTALEXA);
        }

        if (cage.getValue().equals(Cage.CRYSTAL)) {
            Collections.addAll(placeTargets, Offsets.CRYSTAL);
        }

        if (cage.getValue().equals(Cage.CRYSTALFULLROOF)) {
            Collections.addAll(placeTargets, Offsets.CRYSTALFULLROOF);
        }

        // TODO: dont use static bridging in offset but calculate them on the fly
        //  based on view direction (or relative direction of target to player)
        //  (add full/half n/e/s/w patterns to append dynamically)

        // TODO: sort offsetList by optimal caging success factor ->
        // sort them by pos y up AND start building behind target

        int blocksPlaced = 0;

        while (blocksPlaced < blocksPerTick.getValue()) {

            if (offsetStep >= placeTargets.size()) {
                offsetStep = 0;
                break;
            }

            BlockPos offsetPos = new BlockPos(placeTargets.get(offsetStep));
            BlockPos targetPos = new BlockPos(closestTarget.getPositionVector()).down().add(offsetPos.x, offsetPos.y, offsetPos.z);

            if (placeBlockInRange(targetPos, range.getValue())) {
                blocksPlaced++;
            }

            offsetStep++;

        }

        if (blocksPlaced > 0) {

            if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
                mc.player.inventory.currentItem = playerHotbarSlot;
                lastHotbarSlot = playerHotbarSlot;
            }

            if (isSneaking) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                isSneaking = false;
            }

        }

    }

    private boolean placeBlockInRange(BlockPos pos, double range) {

        // check if block is already placed
        Block block = mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }

        // check if entity blocks placing
        for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos))) {
            if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                return false;
            }
        }

        EnumFacing side = BlockInteractionHelper.getPlaceableSide(pos);

        // check if we have a block adjacent to blockpos to click at
        if (side == null) {
            return false;
        }

        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();

        // check if neighbor can be right clicked
        if (!canBeClicked(neighbour)) {
            return false;
        }

        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();

        if (mc.player.getPositionVector().distanceTo(hitVec) > range) {
            return false;
        }

        int obiSlot = findObiInHotbar();

        if (obiSlot == -1) {
            this.disable();
        }

        if (lastHotbarSlot != obiSlot) {
            mc.player.inventory.currentItem = obiSlot;
            lastHotbarSlot = obiSlot;
        }

        if (!isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            isSneaking = true;
        }

        if (rotate.getValue()) {
            faceVectorPacketInstant(hitVec);
        }

        mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        mc.player.swingArm(EnumHand.MAIN_HAND);
        mc.rightClickDelayTimer = 4;

        if (noGlitchBlocks.getValue() && !mc.playerController.getCurrentGameType().equals(GameType.CREATIVE)) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, neighbour, opposite));
            if (ModuleManager.getModuleByName("NoBreakAnimation").isEnabled()) {
                ((NoBreakAnimation) ModuleManager.getModuleByName("NoBreakAnimation")).resetMining();
            }
        }

        return true;

    }

    private int findObiInHotbar() {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof Block) {
                slot = i;
                break;
            }

        }

        return slot;

    }

    private void findClosestTarget() {

        List<EntityPlayer> playerList = mc.world.playerEntities;

        closestTarget = null;

        for (EntityPlayer target : playerList) {

            if (target == mc.player) {
                continue;
            }

            if (Friends.isFriend(target.getName())) {
                continue;
            }

            if (!EntityUtil.isLiving(target)) {
                continue;
            }

            if ((target).getHealth() <= 0) {
                continue;
            }

            if (closestTarget == null) {
                closestTarget = target;
                continue;
            }

            if (mc.player.getDistance(target) < mc.player.getDistance(closestTarget)) {
                closestTarget = target;
            }

        }

    }

    @Override
    public String getHudInfo() {
        if (closestTarget != null) {
            return closestTarget.getName().toUpperCase();
        }
        return "NO TARGET";
    }

    private enum Cage {
        TRAP, TRAPFULLROOF, CRYSTALEXA, CRYSTAL, CRYSTALFULLROOF
    }

    private static class Offsets {

        private static final Vec3d[] TRAP = {
                new Vec3d(0, 0, -1),
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(-1, 0, 0),
                new Vec3d(0, 1, -1),
                new Vec3d(1, 1, 0),
                new Vec3d(0, 1, 1),
                new Vec3d(-1, 1, 0),
                new Vec3d(0, 2, -1),
                new Vec3d(1, 2, 0),
                new Vec3d(0, 2, 1),
                new Vec3d(-1, 2, 0),
                new Vec3d(0, 3, -1),
                new Vec3d(0, 3, 0)
        };

        private static final Vec3d[] TRAPFULLROOF = {
                new Vec3d(0, 0, -1),
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(-1, 0, 0),
                new Vec3d(0, 1, -1),
                new Vec3d(1, 1, 0),
                new Vec3d(0, 1, 1),
                new Vec3d(-1, 1, 0),
                new Vec3d(0, 2, -1),
                new Vec3d(1, 2, 0),
                new Vec3d(0, 2, 1),
                new Vec3d(-1, 2, 0),
                new Vec3d(0, 3, -1),
                new Vec3d(1, 3, 0),
                new Vec3d(0, 3, 1),
                new Vec3d(-1, 3, 0),
                new Vec3d(0, 3, 0)
        };

        private static final Vec3d[] CRYSTALEXA = {
                new Vec3d(0, 0, -1),
                new Vec3d(0, 1, -1),
                new Vec3d(0, 2, -1),
                new Vec3d(1, 2, 0),
                new Vec3d(0, 2, 1),
                new Vec3d(-1, 2, 0),
                new Vec3d(-1, 2, -1),
                new Vec3d(1, 2, 1),
                new Vec3d(1, 2, -1),
                new Vec3d(-1, 2, 1),
                new Vec3d(0, 3, -1),
                new Vec3d(0, 3, 0)
        };

        private static final Vec3d[] CRYSTAL = {
                new Vec3d(0, 0, -1),
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(-1, 0, 0),
                new Vec3d(-1, 0, 1),
                new Vec3d(1, 0, -1),
                new Vec3d(-1, 0, -1),
                new Vec3d(1, 0, 1),
                new Vec3d(-1, 1, -1),
                new Vec3d(1, 1, 1),
                new Vec3d(-1, 1, 1),
                new Vec3d(1, 1, -1),
                new Vec3d(0, 2, -1),
                new Vec3d(1, 2, 0),
                new Vec3d(0, 2, 1),
                new Vec3d(-1, 2, 0),
                new Vec3d(-1, 2, 1),
                new Vec3d(1, 2, -1),
                new Vec3d(0, 3, -1),
                new Vec3d(0, 3, 0)
        };

        private static final Vec3d[] CRYSTALFULLROOF = {
                new Vec3d(0, 0, -1),
                new Vec3d(1, 0, 0),
                new Vec3d(0, 0, 1),
                new Vec3d(-1, 0, 0),
                new Vec3d(-1, 0, 1),
                new Vec3d(1, 0, -1),
                new Vec3d(-1, 0, -1),
                new Vec3d(1, 0, 1),
                new Vec3d(-1, 1, -1),
                new Vec3d(1, 1, 1),
                new Vec3d(-1, 1, 1),
                new Vec3d(1, 1, -1),
                new Vec3d(0, 2, -1),
                new Vec3d(1, 2, 0),
                new Vec3d(0, 2, 1),
                new Vec3d(-1, 2, 0),
                new Vec3d(-1, 2, 1),
                new Vec3d(1, 2, -1),
                new Vec3d(0, 3, -1),
                new Vec3d(1, 3, 0),
                new Vec3d(0, 3, 1),
                new Vec3d(-1, 3, 0),
                new Vec3d(0, 3, 0)
        };

    }

}
