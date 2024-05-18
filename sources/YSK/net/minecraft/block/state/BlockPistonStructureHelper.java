package net.minecraft.block.state;

import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;

public class BlockPistonStructureHelper
{
    private final World world;
    private final BlockPos blockToMove;
    private final BlockPos pistonPos;
    private final List<BlockPos> toDestroy;
    private final EnumFacing moveDirection;
    private final List<BlockPos> toMove;
    
    private void func_177255_a(final int n, final int n2) {
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList();
        final ArrayList arrayList3 = Lists.newArrayList();
        arrayList.addAll(this.toMove.subList("".length(), n2));
        arrayList2.addAll(this.toMove.subList(this.toMove.size() - n, this.toMove.size()));
        arrayList3.addAll(this.toMove.subList(n2, this.toMove.size() - n));
        this.toMove.clear();
        this.toMove.addAll(arrayList);
        this.toMove.addAll(arrayList2);
        this.toMove.addAll(arrayList3);
    }
    
    public boolean canMove() {
        this.toMove.clear();
        this.toDestroy.clear();
        final Block block = this.world.getBlockState(this.blockToMove).getBlock();
        if (!BlockPistonBase.canPush(block, this.world, this.blockToMove, this.moveDirection, "".length() != 0)) {
            if (block.getMobilityFlag() != " ".length()) {
                return "".length() != 0;
            }
            this.toDestroy.add(this.blockToMove);
            return " ".length() != 0;
        }
        else {
            if (!this.func_177251_a(this.blockToMove)) {
                return "".length() != 0;
            }
            int i = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (i < this.toMove.size()) {
                final BlockPos blockPos = this.toMove.get(i);
                if (this.world.getBlockState(blockPos).getBlock() == Blocks.slime_block && !this.func_177250_b(blockPos)) {
                    return "".length() != 0;
                }
                ++i;
            }
            return " ".length() != 0;
        }
    }
    
    public List<BlockPos> getBlocksToDestroy() {
        return this.toDestroy;
    }
    
    private boolean func_177251_a(final BlockPos blockPos) {
        Block block = this.world.getBlockState(blockPos).getBlock();
        if (block.getMaterial() == Material.air) {
            return " ".length() != 0;
        }
        if (!BlockPistonBase.canPush(block, this.world, blockPos, this.moveDirection, "".length() != 0)) {
            return " ".length() != 0;
        }
        if (blockPos.equals(this.pistonPos)) {
            return " ".length() != 0;
        }
        if (this.toMove.contains(blockPos)) {
            return " ".length() != 0;
        }
        int length = " ".length();
        if (length + this.toMove.size() > (0x1D ^ 0x11)) {
            return "".length() != 0;
        }
        while (block == Blocks.slime_block) {
            final BlockPos offset = blockPos.offset(this.moveDirection.getOpposite(), length);
            block = this.world.getBlockState(offset).getBlock();
            if (block.getMaterial() == Material.air || !BlockPistonBase.canPush(block, this.world, offset, this.moveDirection, "".length() != 0)) {
                break;
            }
            if (offset.equals(this.pistonPos)) {
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                break;
            }
            else {
                if (++length + this.toMove.size() > (0x3B ^ 0x37)) {
                    return "".length() != 0;
                }
                continue;
            }
        }
        int length2 = "".length();
        int i = length - " ".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i >= 0) {
            this.toMove.add(blockPos.offset(this.moveDirection.getOpposite(), i));
            ++length2;
            --i;
        }
        int length3 = " ".length();
        do {
            final BlockPos offset2 = blockPos.offset(this.moveDirection, length3);
            final int index = this.toMove.indexOf(offset2);
            if (index > -" ".length()) {
                this.func_177255_a(length2, index);
                int j = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                while (j <= index + length2) {
                    final BlockPos blockPos2 = this.toMove.get(j);
                    if (this.world.getBlockState(blockPos2).getBlock() == Blocks.slime_block && !this.func_177250_b(blockPos2)) {
                        return "".length() != 0;
                    }
                    ++j;
                }
                return " ".length() != 0;
            }
            else {
                final Block block2 = this.world.getBlockState(offset2).getBlock();
                if (block2.getMaterial() == Material.air) {
                    return " ".length() != 0;
                }
                if (!BlockPistonBase.canPush(block2, this.world, offset2, this.moveDirection, " ".length() != 0) || offset2.equals(this.pistonPos)) {
                    return "".length() != 0;
                }
                if (block2.getMobilityFlag() == " ".length()) {
                    this.toDestroy.add(offset2);
                    return " ".length() != 0;
                }
                if (this.toMove.size() >= (0xA6 ^ 0xAA)) {
                    return "".length() != 0;
                }
                this.toMove.add(offset2);
                ++length2;
                ++length3;
                "".length();
            }
        } while (!false);
        throw null;
    }
    
    public List<BlockPos> getBlocksToMove() {
        return this.toMove;
    }
    
    public BlockPistonStructureHelper(final World world, final BlockPos pistonPos, final EnumFacing moveDirection, final boolean b) {
        this.toMove = (List<BlockPos>)Lists.newArrayList();
        this.toDestroy = (List<BlockPos>)Lists.newArrayList();
        this.world = world;
        this.pistonPos = pistonPos;
        if (b) {
            this.moveDirection = moveDirection;
            this.blockToMove = pistonPos.offset(moveDirection);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            this.moveDirection = moveDirection.getOpposite();
            this.blockToMove = pistonPos.offset(moveDirection, "  ".length());
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean func_177250_b(final BlockPos blockPos) {
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing = values[i];
            if (enumFacing.getAxis() != this.moveDirection.getAxis() && !this.func_177251_a(blockPos.offset(enumFacing))) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
