package dev.excellent.impl.util.other;

import dev.excellent.api.interfaces.game.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class HoleUtility implements IMinecraft {
    public final Vector3i[] VECTOR_PATTERN = {
            new Vector3i(0, 0, 1),
            new Vector3i(0, 0, -1),
            new Vector3i(1, 0, 0),
            new Vector3i(-1, 0, 0)};

    public List<BlockPos> getQuadDirection(BlockPos pos) {
        List<BlockPos> dirList = new ArrayList<>();
        dirList.add(pos);
        if (!isReplaceable(pos))
            return null;
        if (isReplaceable(pos.add(1, 0, 0)) && isReplaceable(pos.add(0, 0, 1)) && isReplaceable(pos.add(1, 0, 1))) {
            dirList.add(pos.add(1, 0, 0));
            dirList.add(pos.add(0, 0, 1));
            dirList.add(pos.add(1, 0, 1));
        }
        if (isReplaceable(pos.add(-1, 0, 0)) && isReplaceable(pos.add(0, 0, -1)) && isReplaceable(pos.add(-1, 0, -1))) {
            dirList.add(pos.add(-1, 0, 0));
            dirList.add(pos.add(0, 0, -1));
            dirList.add(pos.add(-1, 0, -1));
        }
        if (isReplaceable(pos.add(1, 0, 0)) && isReplaceable(pos.add(0, 0, -1)) && isReplaceable(pos.add(1, 0, -1))) {
            dirList.add(pos.add(1, 0, 0));
            dirList.add(pos.add(0, 0, -1));
            dirList.add(pos.add(1, 0, -1));
        }
        if (isReplaceable(pos.add(-1, 0, 0)) && isReplaceable(pos.add(0, 0, 1)) && isReplaceable(pos.add(-1, 0, 1))) {
            dirList.add(pos.add(-1, 0, 0));
            dirList.add(pos.add(0, 0, 1));
            dirList.add(pos.add(-1, 0, 1));
        }
        if (dirList.size() != 4)
            return null;
        return dirList;
    }

    public Vector3i getTwoBlocksDirection(BlockPos pos) {
        for (Vector3i vec : VECTOR_PATTERN) {
            if (isReplaceable(pos.add(vec)))
                return vec;
        }
        return null;
    }

    public boolean validBedrock(BlockPos pos) {
        return isBedrock(pos.add(0, -1, 0))
                && isBedrock(pos.add(1, 0, 0))
                && isBedrock(pos.add(-1, 0, 0))
                && isBedrock(pos.add(0, 0, 1))
                && isBedrock(pos.add(0, 0, -1))
                && isReplaceable(pos)
                && isReplaceable(pos.add(0, 1, 0))
                && isReplaceable(pos.add(0, 2, 0));
    }

    public boolean validTwoBlockBedrock(BlockPos pos) {
        if (!isReplaceable(pos))
            return false;
        Vector3i addVec = getTwoBlocksDirection(pos);
        if (addVec == null)
            return false;
        BlockPos[] checkPoses = new BlockPos[]{pos, pos.add(addVec)};
        for (BlockPos checkPos : checkPoses) {
            BlockPos downPos = checkPos.down();
            if (!isBedrock(downPos))
                return false;
            for (Vector3i vec : VECTOR_PATTERN) {
                BlockPos reducedPos = checkPos.add(vec);
                if (!isBedrock(reducedPos) && !reducedPos.equals(pos) && !reducedPos.equals(pos.add(addVec)))
                    return false;
            }
        }
        return true;
    }

    public boolean validTwoBlockIndestructible(BlockPos pos) {
        if (!isReplaceable(pos))
            return false;
        Vector3i addVec = getTwoBlocksDirection(pos);
        if (addVec == null)
            return false;
        BlockPos[] checkPoses = new BlockPos[]{pos, pos.add(addVec)};
        boolean wasIndestrictible = false;
        for (BlockPos checkPos : checkPoses) {
            BlockPos downPos = checkPos.down();
            if (isIndestructible(downPos))
                wasIndestrictible = true;
            else if (!isBedrock(downPos))
                return false;
            for (Vector3i vec : VECTOR_PATTERN) {
                BlockPos reducedPos = checkPos.add(vec);
                if (isIndestructible(reducedPos)) {
                    wasIndestrictible = true;
                    continue;
                }
                if (!isBedrock(reducedPos) && !reducedPos.equals(pos) && !reducedPos.equals(pos.add(addVec)))
                    return false;
            }
        }
        return wasIndestrictible;
    }

    public boolean validQuadIndestructible(BlockPos pos) {
        List<BlockPos> checkPoses = getQuadDirection(pos);
        if (checkPoses == null)
            return false;
        boolean wasIndestrictible = false;
        for (BlockPos checkPos : checkPoses) {
            BlockPos downPos = checkPos.down();
            if (isIndestructible(downPos)) {
                wasIndestrictible = true;
            } else if (!isBedrock(downPos)) {
                return false;
            }
            for (Vector3i vec : VECTOR_PATTERN) {
                BlockPos reducedPos = checkPos.add(vec);
                if (isIndestructible(reducedPos)) {
                    wasIndestrictible = true;
                    continue;
                }
                if (!isBedrock(reducedPos) && !checkPoses.contains(reducedPos)) {
                    return false;
                }
            }
        }
        return wasIndestrictible;
    }

    public boolean validQuadBedrock(BlockPos pos) {
        List<BlockPos> checkPoses = getQuadDirection(pos);
        if (checkPoses == null)
            return false;
        for (BlockPos checkPos : checkPoses) {
            BlockPos downPos = checkPos.down();
            if (!isBedrock(downPos)) {
                return false;
            }
            for (Vector3i vec : VECTOR_PATTERN) {
                BlockPos reducedPos = checkPos.add(vec);
                if (!isBedrock(reducedPos) && !checkPoses.contains(reducedPos)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isIndestructible(BlockPos bp) {
        if (mc.world == null)
            return false;
        return mc.world.getBlockState(bp).getBlock() == Blocks.OBSIDIAN
                || mc.world.getBlockState(bp).getBlock() == Blocks.NETHERITE_BLOCK
                || mc.world.getBlockState(bp).getBlock() == Blocks.CRYING_OBSIDIAN
                || mc.world.getBlockState(bp).getBlock() == Blocks.RESPAWN_ANCHOR;
    }

    public boolean isBedrock(BlockPos bp) {
        if (mc.world == null)
            return false;
        return mc.world.getBlockState(bp).getBlock() == Blocks.BEDROCK;
    }

    public boolean isReplaceable(BlockPos bp) {
        if (mc.world == null)
            return false;
        return mc.world.getBlockState(bp).isReplaceable();
    }

    public boolean isAir(BlockPos pos) {
        return mc.world.getBlockState(pos).isAir();
    }
}
