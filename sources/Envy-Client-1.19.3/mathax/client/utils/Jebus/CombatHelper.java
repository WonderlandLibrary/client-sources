package mathax.client.utils.Jebus;

import mathax.client.utils.Jebus.Interactions;
import mathax.client.utils.Jebus.PacketManager;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.PlayerUtils;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.world.CardinalDirection;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static mathax.client.MatHax.mc;

public class CombatHelper {

    public enum HoleType {Mixed, Obsidian, Bedrock, Invalid}

    public static class Hole {
        private final BlockPos centerPos;
        private final HoleType holeType;

        public Hole(BlockPos p, HoleType type) {
            centerPos = p;
            holeType = type;
        }

        public BlockPos getPos() {
            return centerPos;
        }

        public HoleType getType() {
            return holeType;
        }
    }


    public static boolean isInHole(PlayerEntity p) {
        BlockPos center = p.getBlockPos();
        for (CardinalDirection cd : CardinalDirection.values()) if (!BlockHelper.isBlastRes(center.offset(cd.toDirection()))) return false;
        return true;
    }

    public static boolean isMoving(PlayerEntity p) {
        if (p == null) return false;
        return p.forwardSpeed != 0 || p.sidewaysSpeed != 0;
    }

    public static boolean isBurrowed(PlayerEntity p) {
        if (p == null) return false;
        return BlockHelper.isBurrowBlock(p.getBlockPos());
    }

    public static boolean isTopTrapped(PlayerEntity p) {
        if (p == null) return false;
        return BlockHelper.isBlastRes(p.getBlockPos().up(2));
    }

    public static boolean isSelfTrapped(PlayerEntity p) {
        if (p == null) return false;
        ArrayList<BlockPos> toScan = BlockHelper.getBlockList(p.getBlockPos(), BlockHelper.BlockListType.SelfTrap);
        int i = 0;
        for (BlockPos t : toScan) if (BlockHelper.isBlastRes(t)) i++;
        return i > 2; // 4 is just all the sides, 5 is all sides + top trap.
    }

    public static boolean isCitied(PlayerEntity p) {
        if (p == null) return false;
        int sb = 0;
        int air = 0;
        BlockPos pPos = p.getBlockPos();
        if (BlockHelper.isAir(pPos.down()) && BlockHelper.isAir(pPos.down(2))) return false; // make sure they are on solid ground
        for (BlockPos pos : BlockHelper.getBlockList(p.getBlockPos(), BlockHelper.BlockListType.Surround)) {
            if (BlockHelper.isAir(pos)) air++;
            if (BlockHelper.isBlastRes(pos)) sb++;
        }
        return sb == 3 && air == 1; /* basically requires surround blocks on 3 sides and air on one to consider them 'citied' */
    }

    public static boolean isWebbed(PlayerEntity p) {
        if (p == null) return false;
        for (BlockPos pos : BlockHelper.getBlockList(p.getBlockPos(), BlockHelper.BlockListType.Web)) if (BlockHelper.isWeb(pos)) return true;
        return false;
    }


    public static boolean isDoubleSurrounded(LivingEntity entity) {
        BlockPos blockPos = entity.getBlockPos();
        return BlockHelper.isBlastRes(blockPos.add(1, 0, 0)) &&
            BlockHelper.isBlastRes(blockPos.add(-1, 0, 0)) &&
            BlockHelper.isBlastRes(blockPos.add(0, 0, 1)) &&
            BlockHelper.isBlastRes(blockPos.add(0, 0, -1)) &&
            BlockHelper.isBlastRes(blockPos.add(1, 1, 0)) &&
            BlockHelper.isBlastRes(blockPos.add(-1, 1, 0)) &&
            BlockHelper.isBlastRes(blockPos.add(0, 1, 1)) &&
            BlockHelper.isBlastRes(blockPos.add(0, 1, -1));
    }

    public static BlockPos getWebPos(PlayerEntity p) {
        if (p == null) return null;
        for (BlockPos pos : BlockHelper.getBlockList(p.getBlockPos(), BlockHelper.BlockListType.Web)) if (BlockHelper.isWeb(pos)) return pos;
        return null;
    }

    public static BlockPos getCityBlock(PlayerEntity target, boolean legacyMode) {
        if (target == null || mc.player == null) return null;
        ArrayList<BlockPos> available = BlockHelper.getSurroundBlocks(target);
        if (available == null) return null;
        available.removeIf(BlockHelper::isOurSurroundBlock); // remove blocks in our surround
        available.removeIf(p -> !BlockHelper.isObby(p)); // remove bedrock
        if (legacyMode) available.removeIf(pos -> BlockHelper.getBlock(pos.up()) != Blocks.AIR || BlockHelper.getBlock(pos.up(2)) != Blocks.AIR); // for legacy mode, remove blocks without space above
        if (available.isEmpty()) return null;
        available.sort(Comparator.comparingDouble(PlayerUtils::distanceTo));
        return available.get(0);
    }

    public static ArrayList<Hole> getHoles(int radius, int height) {
        List<BlockPos> nearby = BlockHelper.getSphere(mc.player.getBlockPos(), radius, height);
        ArrayList<Hole> holes = new ArrayList<>();
        nearby.removeIf(blockPos -> !BlockHelper.isAir(blockPos)); // remove everything that isn't air
        nearby.sort(Comparator.comparingDouble(PlayerUtils::distanceTo)); // sort by closest -> farthest
        if (nearby.isEmpty()) return null;
        for (BlockPos pos : nearby) {
            if (!isValidHole(pos)) continue;
            HoleType type = getHoleType(pos);
            if (type == HoleType.Invalid) continue;
            holes.add(new Hole(pos, type));
        }
        return holes;
    }

    public static ArrayList<Hole> getHolesNear(PlayerEntity player, int radius, int height) {
        List<BlockPos> nearby = BlockHelper.getSphere(player.getBlockPos(), radius, height);
        ArrayList<Hole> holes = new ArrayList<>();
        nearby.removeIf(blockPos -> !BlockHelper.isAir(blockPos)); // remove everything that isn't air
        nearby.sort(Comparator.comparingDouble(PlayerUtils::distanceTo)); // sort by closest -> farthest
        if (nearby.isEmpty()) return null;
        for (BlockPos pos : nearby) {
            if (!isValidHole(pos)) continue;
            HoleType type = getHoleType(pos);
            if (type == HoleType.Invalid) continue;
            holes.add(new Hole(pos, type));
        }
        return holes;
    }

    // broken
    public static HoleType getHoleType(BlockPos holePos) {
        HoleType type = HoleType.Invalid;
        int obby = 0;
        int bedrock = 0;
        for (CardinalDirection dir : CardinalDirection.values()) {
            BlockPos offset = holePos.offset(dir.toDirection());
            if (BlockHelper.getBlock(offset) == Blocks.BEDROCK) bedrock++;
            if (BlockHelper.getBlock(offset) == Blocks.OBSIDIAN) obby++;
        }
        if (bedrock == 4) return HoleType.Bedrock;
        if (obby == 4) return HoleType.Obsidian;
        if ((bedrock + obby) == 4) return HoleType.Mixed;
        return type;
    }

    public static boolean isValidHole(BlockPos holePos) {
        if (mc.player.getBlockPos().equals(holePos) /* ||BlockHelper.isWeb(holePos) */) return false;
        if (!BlockHelper.isSolid(holePos.down())) return false;
        if (!BlockHelper.isAir(holePos)) return false;
        return BlockHelper.isAir(holePos.up())
            && BlockHelper.isAir(holePos.up(2))
            && BlockHelper.isAir(holePos.up(3));
    }

    // client-side mining stuff

    public static void mine(BlockPos pos, boolean rotate, int priority) {
        if (pos == null) return;
        if (!(mc.player.getMainHandStack().getItem() instanceof PickaxeItem)) {
            FindItemResult pick = Interactions.findPick();
            if (!pick.found()) return;
            Interactions.setSlot(pick.slot(), false);
        }
        if (rotate) {
            Rotations.rotate(Rotations.getYaw(pos), Rotations.getPitch(pos), () -> {
                mc.interactionManager.updateBlockBreakingProgress(pos, Direction.UP);
                PacketManager.swingHand(false);
            });
        } else {
            mc.interactionManager.updateBlockBreakingProgress(pos, Direction.UP);
            PacketManager.swingHand(false);
        }
    }
}
