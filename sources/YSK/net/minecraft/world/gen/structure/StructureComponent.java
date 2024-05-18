package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public abstract class StructureComponent
{
    protected StructureBoundingBox boundingBox;
    protected int componentType;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    protected EnumFacing coordBaseMode;
    private static final String[] I;
    
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }
    
    protected int getXWithOffset(final int n, final int n2) {
        if (this.coordBaseMode == null) {
            return n;
        }
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
            case 3:
            case 4: {
                return this.boundingBox.minX + n;
            }
            case 5: {
                return this.boundingBox.maxX - n2;
            }
            case 6: {
                return this.boundingBox.minX + n2;
            }
            default: {
                return n;
            }
        }
    }
    
    protected void fillWithAir(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        int i = n2;
        "".length();
        if (-1 == 0) {
            throw null;
        }
        while (i <= n5) {
            int j = n;
            "".length();
            if (true != true) {
                throw null;
            }
            while (j <= n4) {
                int k = n3;
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (k <= n6) {
                    this.setBlockState(world, Blocks.air.getDefaultState(), j, i, k, structureBoundingBox);
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    public void readStructureBaseNBT(final World world, final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey(StructureComponent.I[0xA4 ^ 0xA0])) {
            this.boundingBox = new StructureBoundingBox(nbtTagCompound.getIntArray(StructureComponent.I[0xAA ^ 0xAF]));
        }
        final int integer = nbtTagCompound.getInteger(StructureComponent.I[0x53 ^ 0x55]);
        EnumFacing horizontal;
        if (integer == -" ".length()) {
            horizontal = null;
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            horizontal = EnumFacing.getHorizontal(integer);
        }
        this.coordBaseMode = horizontal;
        this.componentType = nbtTagCompound.getInteger(StructureComponent.I[0x10 ^ 0x17]);
        this.readStructureFromNBT(nbtTagCompound);
    }
    
    public int getComponentType() {
        return this.componentType;
    }
    
    protected boolean isLiquidInStructureBoundingBox(final World world, final StructureBoundingBox structureBoundingBox) {
        final int max = Math.max(this.boundingBox.minX - " ".length(), structureBoundingBox.minX);
        final int max2 = Math.max(this.boundingBox.minY - " ".length(), structureBoundingBox.minY);
        final int max3 = Math.max(this.boundingBox.minZ - " ".length(), structureBoundingBox.minZ);
        final int min = Math.min(this.boundingBox.maxX + " ".length(), structureBoundingBox.maxX);
        final int min2 = Math.min(this.boundingBox.maxY + " ".length(), structureBoundingBox.maxY);
        final int min3 = Math.min(this.boundingBox.maxZ + " ".length(), structureBoundingBox.maxZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = max;
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i <= min) {
            int j = max3;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (j <= min3) {
                if (world.getBlockState(mutableBlockPos.func_181079_c(i, max2, j)).getBlock().getMaterial().isLiquid()) {
                    return " ".length() != 0;
                }
                if (world.getBlockState(mutableBlockPos.func_181079_c(i, min2, j)).getBlock().getMaterial().isLiquid()) {
                    return " ".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        int k = max;
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (k <= min) {
            int l = max2;
            "".length();
            if (2 == -1) {
                throw null;
            }
            while (l <= min2) {
                if (world.getBlockState(mutableBlockPos.func_181079_c(k, l, max3)).getBlock().getMaterial().isLiquid()) {
                    return " ".length() != 0;
                }
                if (world.getBlockState(mutableBlockPos.func_181079_c(k, l, min3)).getBlock().getMaterial().isLiquid()) {
                    return " ".length() != 0;
                }
                ++l;
            }
            ++k;
        }
        int n = max3;
        "".length();
        if (4 == 0) {
            throw null;
        }
        while (n <= min3) {
            int n2 = max2;
            "".length();
            if (4 == 0) {
                throw null;
            }
            while (n2 <= min2) {
                if (world.getBlockState(mutableBlockPos.func_181079_c(max, n2, n)).getBlock().getMaterial().isLiquid()) {
                    return " ".length() != 0;
                }
                if (world.getBlockState(mutableBlockPos.func_181079_c(min, n2, n)).getBlock().getMaterial().isLiquid()) {
                    return " ".length() != 0;
                }
                ++n2;
            }
            ++n;
        }
        return "".length() != 0;
    }
    
    protected IBlockState getBlockStateFromPos(final World world, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        IBlockState blockState;
        if (!structureBoundingBox.isVecInside(blockPos)) {
            blockState = Blocks.air.getDefaultState();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            blockState = world.getBlockState(blockPos);
        }
        return blockState;
    }
    
    static {
        I();
    }
    
    protected void fillWithRandomizedBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final Random random, final BlockSelector blockSelector) {
        int i = n2;
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i <= n5) {
            int j = n;
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (j <= n4) {
                int k = n3;
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (k <= n6) {
                    if (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                        final int n7 = j;
                        final int n8 = i;
                        final int n9 = k;
                        int n10;
                        if (i != n2 && i != n5 && j != n && j != n4 && k != n3 && k != n6) {
                            n10 = "".length();
                            "".length();
                            if (-1 >= 3) {
                                throw null;
                            }
                        }
                        else {
                            n10 = " ".length();
                        }
                        blockSelector.selectBlocks(random, n7, n8, n9, n10 != 0);
                        this.setBlockState(world, blockSelector.getBlockState(), j, i, k, structureBoundingBox);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    public NBTTagCompound createStructureBaseNBT() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setString(StructureComponent.I["".length()], MapGenStructureIO.getStructureComponentName(this));
        nbtTagCompound.setTag(StructureComponent.I[" ".length()], this.boundingBox.toNBTTagIntArray());
        final NBTTagCompound nbtTagCompound2 = nbtTagCompound;
        final String s = StructureComponent.I["  ".length()];
        int horizontalIndex;
        if (this.coordBaseMode == null) {
            horizontalIndex = -" ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            horizontalIndex = this.coordBaseMode.getHorizontalIndex();
        }
        nbtTagCompound2.setInteger(s, horizontalIndex);
        nbtTagCompound.setInteger(StructureComponent.I["   ".length()], this.componentType);
        this.writeStructureToNBT(nbtTagCompound);
        return nbtTagCompound;
    }
    
    public void func_181138_a(final int n, final int n2, final int n3) {
        this.boundingBox.offset(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0x64 ^ 0x6C])["".length()] = I("\"3", "KWjij");
        StructureComponent.I[" ".length()] = I("\u0017\u0010", "URNkb");
        StructureComponent.I["  ".length()] = I(")", "fVljs");
        StructureComponent.I["   ".length()] = I("\u00100", "WtcPh");
        StructureComponent.I[0x25 ^ 0x21] = I("1\u0011", "sSjQP");
        StructureComponent.I[0xB5 ^ 0xB0] = I("1\t", "sKtRW");
        StructureComponent.I[0x1 ^ 0x7] = I("!", "nXgNE");
        StructureComponent.I[0x98 ^ 0x9F] = I("\u0013\u0010", "TThOS");
    }
    
    protected abstract void writeStructureToNBT(final NBTTagCompound p0);
    
    public StructureComponent() {
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
            if (-1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void fillWithBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState, final IBlockState blockState2, final boolean b) {
        int i = n2;
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i <= n5) {
            int j = n;
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (j <= n4) {
                int k = n3;
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (k <= n6) {
                    if (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                        if (i != n2 && i != n5 && j != n && j != n4 && k != n3 && k != n6) {
                            this.setBlockState(world, blockState2, j, i, k, structureBoundingBox);
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                        }
                        else {
                            this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    public void buildComponent(final StructureComponent structureComponent, final List<StructureComponent> list, final Random random) {
    }
    
    protected boolean generateChestContents(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final List<WeightedRandomChestContent> list, final int n4) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos) && world.getBlockState(blockPos).getBlock() != Blocks.chest) {
            world.setBlockState(blockPos, Blocks.chest.correctFacing(world, blockPos, Blocks.chest.getDefaultState()), "  ".length());
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest) {
                WeightedRandomChestContent.generateChestContents(random, list, (IInventory)tileEntity, n4);
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void replaceAirAndLiquidDownwards(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final int xWithOffset = this.getXWithOffset(n, n3);
        int yWithOffset = this.getYWithOffset(n2);
        final int zWithOffset = this.getZWithOffset(n, n3);
        if (structureBoundingBox.isVecInside(new BlockPos(xWithOffset, yWithOffset, zWithOffset))) {
            "".length();
            if (1 < 0) {
                throw null;
            }
            while ((world.isAirBlock(new BlockPos(xWithOffset, yWithOffset, zWithOffset)) || world.getBlockState(new BlockPos(xWithOffset, yWithOffset, zWithOffset)).getBlock().getMaterial().isLiquid()) && yWithOffset > " ".length()) {
                world.setBlockState(new BlockPos(xWithOffset, yWithOffset, zWithOffset), blockState, "  ".length());
                --yWithOffset;
            }
        }
    }
    
    protected void setBlockState(final World world, final IBlockState blockState, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos)) {
            world.setBlockState(blockPos, blockState, "  ".length());
        }
    }
    
    protected boolean generateDispenserContents(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final int n4, final List<WeightedRandomChestContent> list, final int n5) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos) && world.getBlockState(blockPos).getBlock() != Blocks.dispenser) {
            world.setBlockState(blockPos, Blocks.dispenser.getStateFromMeta(this.getMetadataWithOffset(Blocks.dispenser, n4)), "  ".length());
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityDispenser) {
                WeightedRandomChestContent.generateDispenserContents(random, list, (TileEntityDispenser)tileEntity, n5);
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = StructureComponent.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x24 ^ 0x22);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x57 ^ 0x53);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x41 ^ 0x44);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return StructureComponent.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    public static StructureComponent findIntersecting(final List<StructureComponent> list, final StructureBoundingBox structureBoundingBox) {
        final Iterator<StructureComponent> iterator = list.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final StructureComponent structureComponent = iterator.next();
            if (structureComponent.getBoundingBox() != null && structureComponent.getBoundingBox().intersectsWith(structureBoundingBox)) {
                return structureComponent;
            }
        }
        return null;
    }
    
    protected abstract void readStructureFromNBT(final NBTTagCompound p0);
    
    public BlockPos getBoundingBoxCenter() {
        return new BlockPos(this.boundingBox.getCenter());
    }
    
    protected int getYWithOffset(final int n) {
        int n2;
        if (this.coordBaseMode == null) {
            n2 = n;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n2 = n + this.boundingBox.minY;
        }
        return n2;
    }
    
    protected void clearCurrentPositionBlocksUpwards(final World world, final int n, final int n2, final int n3, final StructureBoundingBox structureBoundingBox) {
        BlockPos up = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(up)) {
            "".length();
            if (0 == -1) {
                throw null;
            }
            while (!world.isAirBlock(up) && up.getY() < 34 + 142 - 116 + 195) {
                world.setBlockState(up, Blocks.air.getDefaultState(), "  ".length());
                up = up.up();
            }
        }
    }
    
    protected int getZWithOffset(final int n, final int n2) {
        if (this.coordBaseMode == null) {
            return n2;
        }
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.coordBaseMode.ordinal()]) {
            case 3: {
                return this.boundingBox.maxZ - n2;
            }
            case 4: {
                return this.boundingBox.minZ + n2;
            }
            case 5:
            case 6: {
                return this.boundingBox.minZ + n;
            }
            default: {
                return n2;
            }
        }
    }
    
    protected void randomlyRareFillWithBlocks(final World world, final StructureBoundingBox structureBoundingBox, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final IBlockState blockState, final boolean b) {
        final float n7 = n4 - n + " ".length();
        final float n8 = n5 - n2 + " ".length();
        final float n9 = n6 - n3 + " ".length();
        final float n10 = n + n7 / 2.0f;
        final float n11 = n3 + n9 / 2.0f;
        int i = n2;
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i <= n5) {
            final float n12 = (i - n2) / n8;
            int j = n;
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (j <= n4) {
                final float n13 = (j - n10) / (n7 * 0.5f);
                int k = n3;
                "".length();
                if (4 < -1) {
                    throw null;
                }
                while (k <= n6) {
                    final float n14 = (k - n11) / (n9 * 0.5f);
                    if ((!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air) && n13 * n13 + n12 * n12 + n14 * n14 <= 1.05f) {
                        this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    protected StructureComponent(final int componentType) {
        this.componentType = componentType;
    }
    
    protected void placeDoorCurrentPosition(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
        final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
        if (structureBoundingBox.isVecInside(blockPos)) {
            ItemDoor.placeDoor(world, blockPos, enumFacing.rotateYCCW(), Blocks.oak_door);
        }
    }
    
    protected void randomlyPlaceBlock(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final float n, final int n2, final int n3, final int n4, final IBlockState blockState) {
        if (random.nextFloat() < n) {
            this.setBlockState(world, blockState, n2, n3, n4, structureBoundingBox);
        }
    }
    
    protected void func_175805_a(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final float n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final IBlockState blockState, final IBlockState blockState2, final boolean b) {
        int i = n3;
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i <= n6) {
            int j = n2;
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (j <= n5) {
                int k = n4;
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                while (k <= n7) {
                    if (random.nextFloat() <= n && (!b || this.getBlockStateFromPos(world, j, i, k, structureBoundingBox).getBlock().getMaterial() != Material.air)) {
                        if (i != n3 && i != n6 && j != n2 && j != n5 && k != n4 && k != n7) {
                            this.setBlockState(world, blockState2, j, i, k, structureBoundingBox);
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                        }
                        else {
                            this.setBlockState(world, blockState, j, i, k, structureBoundingBox);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    protected int getMetadataWithOffset(final Block block, final int n) {
        if (block == Blocks.rail) {
            if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.EAST) {
                if (n == " ".length()) {
                    return "".length();
                }
                return " ".length();
            }
        }
        else if (block instanceof BlockDoor) {
            if (this.coordBaseMode == EnumFacing.SOUTH) {
                if (n == 0) {
                    return "  ".length();
                }
                if (n == "  ".length()) {
                    return "".length();
                }
            }
            else {
                if (this.coordBaseMode == EnumFacing.WEST) {
                    return n + " ".length() & "   ".length();
                }
                if (this.coordBaseMode == EnumFacing.EAST) {
                    return n + "   ".length() & "   ".length();
                }
            }
        }
        else if (block != Blocks.stone_stairs && block != Blocks.oak_stairs && block != Blocks.nether_brick_stairs && block != Blocks.stone_brick_stairs && block != Blocks.sandstone_stairs) {
            if (block == Blocks.ladder) {
                if (this.coordBaseMode == EnumFacing.SOUTH) {
                    if (n == EnumFacing.NORTH.getIndex()) {
                        return EnumFacing.SOUTH.getIndex();
                    }
                    if (n == EnumFacing.SOUTH.getIndex()) {
                        return EnumFacing.NORTH.getIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.WEST) {
                    if (n == EnumFacing.NORTH.getIndex()) {
                        return EnumFacing.WEST.getIndex();
                    }
                    if (n == EnumFacing.SOUTH.getIndex()) {
                        return EnumFacing.EAST.getIndex();
                    }
                    if (n == EnumFacing.WEST.getIndex()) {
                        return EnumFacing.NORTH.getIndex();
                    }
                    if (n == EnumFacing.EAST.getIndex()) {
                        return EnumFacing.SOUTH.getIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.EAST) {
                    if (n == EnumFacing.NORTH.getIndex()) {
                        return EnumFacing.EAST.getIndex();
                    }
                    if (n == EnumFacing.SOUTH.getIndex()) {
                        return EnumFacing.WEST.getIndex();
                    }
                    if (n == EnumFacing.WEST.getIndex()) {
                        return EnumFacing.NORTH.getIndex();
                    }
                    if (n == EnumFacing.EAST.getIndex()) {
                        return EnumFacing.SOUTH.getIndex();
                    }
                }
            }
            else if (block == Blocks.stone_button) {
                if (this.coordBaseMode == EnumFacing.SOUTH) {
                    if (n == "   ".length()) {
                        return 0x9E ^ 0x9A;
                    }
                    if (n == (0x7E ^ 0x7A)) {
                        return "   ".length();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.WEST) {
                    if (n == "   ".length()) {
                        return " ".length();
                    }
                    if (n == (0x53 ^ 0x57)) {
                        return "  ".length();
                    }
                    if (n == "  ".length()) {
                        return "   ".length();
                    }
                    if (n == " ".length()) {
                        return 0x2 ^ 0x6;
                    }
                }
                else if (this.coordBaseMode == EnumFacing.EAST) {
                    if (n == "   ".length()) {
                        return "  ".length();
                    }
                    if (n == (0x82 ^ 0x86)) {
                        return " ".length();
                    }
                    if (n == "  ".length()) {
                        return "   ".length();
                    }
                    if (n == " ".length()) {
                        return 0xB7 ^ 0xB3;
                    }
                }
            }
            else if (block != Blocks.tripwire_hook && !(block instanceof BlockDirectional)) {
                if (block == Blocks.piston || block == Blocks.sticky_piston || block == Blocks.lever || block == Blocks.dispenser) {
                    if (this.coordBaseMode == EnumFacing.SOUTH) {
                        if (n == EnumFacing.NORTH.getIndex() || n == EnumFacing.SOUTH.getIndex()) {
                            return EnumFacing.getFront(n).getOpposite().getIndex();
                        }
                    }
                    else if (this.coordBaseMode == EnumFacing.WEST) {
                        if (n == EnumFacing.NORTH.getIndex()) {
                            return EnumFacing.WEST.getIndex();
                        }
                        if (n == EnumFacing.SOUTH.getIndex()) {
                            return EnumFacing.EAST.getIndex();
                        }
                        if (n == EnumFacing.WEST.getIndex()) {
                            return EnumFacing.NORTH.getIndex();
                        }
                        if (n == EnumFacing.EAST.getIndex()) {
                            return EnumFacing.SOUTH.getIndex();
                        }
                    }
                    else if (this.coordBaseMode == EnumFacing.EAST) {
                        if (n == EnumFacing.NORTH.getIndex()) {
                            return EnumFacing.EAST.getIndex();
                        }
                        if (n == EnumFacing.SOUTH.getIndex()) {
                            return EnumFacing.WEST.getIndex();
                        }
                        if (n == EnumFacing.WEST.getIndex()) {
                            return EnumFacing.NORTH.getIndex();
                        }
                        if (n == EnumFacing.EAST.getIndex()) {
                            return EnumFacing.SOUTH.getIndex();
                        }
                    }
                }
            }
            else {
                final EnumFacing horizontal = EnumFacing.getHorizontal(n);
                if (this.coordBaseMode == EnumFacing.SOUTH) {
                    if (horizontal == EnumFacing.SOUTH || horizontal == EnumFacing.NORTH) {
                        return horizontal.getOpposite().getHorizontalIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.WEST) {
                    if (horizontal == EnumFacing.NORTH) {
                        return EnumFacing.WEST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.SOUTH) {
                        return EnumFacing.EAST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.WEST) {
                        return EnumFacing.NORTH.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.EAST) {
                        return EnumFacing.SOUTH.getHorizontalIndex();
                    }
                }
                else if (this.coordBaseMode == EnumFacing.EAST) {
                    if (horizontal == EnumFacing.NORTH) {
                        return EnumFacing.EAST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.SOUTH) {
                        return EnumFacing.WEST.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.WEST) {
                        return EnumFacing.NORTH.getHorizontalIndex();
                    }
                    if (horizontal == EnumFacing.EAST) {
                        return EnumFacing.SOUTH.getHorizontalIndex();
                    }
                }
            }
        }
        else if (this.coordBaseMode == EnumFacing.SOUTH) {
            if (n == "  ".length()) {
                return "   ".length();
            }
            if (n == "   ".length()) {
                return "  ".length();
            }
        }
        else if (this.coordBaseMode == EnumFacing.WEST) {
            if (n == 0) {
                return "  ".length();
            }
            if (n == " ".length()) {
                return "   ".length();
            }
            if (n == "  ".length()) {
                return "".length();
            }
            if (n == "   ".length()) {
                return " ".length();
            }
        }
        else if (this.coordBaseMode == EnumFacing.EAST) {
            if (n == 0) {
                return "  ".length();
            }
            if (n == " ".length()) {
                return "   ".length();
            }
            if (n == "  ".length()) {
                return " ".length();
            }
            if (n == "   ".length()) {
                return "".length();
            }
        }
        return n;
    }
    
    public abstract boolean addComponentParts(final World p0, final Random p1, final StructureBoundingBox p2);
    
    public abstract static class BlockSelector
    {
        protected IBlockState blockstate;
        
        public BlockSelector() {
            this.blockstate = Blocks.air.getDefaultState();
        }
        
        public IBlockState getBlockState() {
            return this.blockstate;
        }
        
        public abstract void selectBlocks(final Random p0, final int p1, final int p2, final int p3, final boolean p4);
        
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
                if (-1 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
