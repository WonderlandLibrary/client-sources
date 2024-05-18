package net.minecraft.block;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockFlowerPot extends BlockContainer
{
    public static final PropertyInteger LEGACY_DATA;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType;
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
    public static final PropertyEnum<EnumFlowerType> CONTENTS;
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType = BlockFlowerPot.$SWITCH_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType;
        if ($switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType != null) {
            return $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2 = new int[BlockFlower.EnumFlowerType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.ALLIUM.ordinal()] = (0x19 ^ 0x1D);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.BLUE_ORCHID.ordinal()] = "   ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.DANDELION.ordinal()] = " ".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.HOUSTONIA.ordinal()] = (0x82 ^ 0x87);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.ORANGE_TULIP.ordinal()] = (0xAF ^ 0xA8);
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.OXEYE_DAISY.ordinal()] = (0x42 ^ 0x48);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.PINK_TULIP.ordinal()] = (0x93 ^ 0x9A);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.POPPY.ordinal()] = "  ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.RED_TULIP.ordinal()] = (0x58 ^ 0x5E);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2[BlockFlower.EnumFlowerType.WHITE_TULIP.ordinal()] = (0x13 ^ 0x1B);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        return BlockFlowerPot.$SWITCH_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType = $switch_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType2;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            final Item flowerPotItem = ((TileEntityFlowerPot)tileEntity).getFlowerPotItem();
            if (flowerPotItem instanceof ItemBlock) {
                return Block.getBlockFromItem(flowerPotItem).colorMultiplier(blockAccess, blockPos, n);
            }
        }
        return 6398716 + 15078819 - 5339473 + 639153;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        if (tileEntity != null && tileEntity.getFlowerPotItem() != null) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(tileEntity.getFlowerPotItem(), " ".length(), tileEntity.getFlowerPotData()));
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType = BlockFlowerPot.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        if ($switch_TABLE$net$minecraft$block$BlockPlanks$EnumType != null) {
            return $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2 = new int[BlockPlanks.EnumType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.ACACIA.ordinal()] = (0x83 ^ 0x86);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.BIRCH.ordinal()] = "   ".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.DARK_OAK.ordinal()] = (0x75 ^ 0x73);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.JUNGLE.ordinal()] = (0x3A ^ 0x3E);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.OAK.ordinal()] = " ".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.SPRUCE.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockFlowerPot.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType = $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2;
    }
    
    @Override
    public boolean isFlowerPot() {
        return " ".length() != 0;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        Block block = null;
        int n2 = "".length();
        switch (n) {
            case 1: {
                block = Blocks.red_flower;
                n2 = BlockFlower.EnumFlowerType.POPPY.getMeta();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                block = Blocks.yellow_flower;
                "".length();
                if (!true) {
                    throw null;
                }
                break;
            }
            case 3: {
                block = Blocks.sapling;
                n2 = BlockPlanks.EnumType.OAK.getMetadata();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                block = Blocks.sapling;
                n2 = BlockPlanks.EnumType.SPRUCE.getMetadata();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                break;
            }
            case 5: {
                block = Blocks.sapling;
                n2 = BlockPlanks.EnumType.BIRCH.getMetadata();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                break;
            }
            case 6: {
                block = Blocks.sapling;
                n2 = BlockPlanks.EnumType.JUNGLE.getMetadata();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                break;
            }
            case 7: {
                block = Blocks.red_mushroom;
                "".length();
                if (1 < 1) {
                    throw null;
                }
                break;
            }
            case 8: {
                block = Blocks.brown_mushroom;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                break;
            }
            case 9: {
                block = Blocks.cactus;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
                break;
            }
            case 10: {
                block = Blocks.deadbush;
                "".length();
                if (4 == -1) {
                    throw null;
                }
                break;
            }
            case 11: {
                block = Blocks.tallgrass;
                n2 = BlockTallGrass.EnumType.FERN.getMeta();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                break;
            }
            case 12: {
                block = Blocks.sapling;
                n2 = BlockPlanks.EnumType.ACACIA.getMetadata();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                break;
            }
            case 13: {
                block = Blocks.sapling;
                n2 = BlockPlanks.EnumType.DARK_OAK.getMetadata();
                break;
            }
        }
        return new TileEntityFlowerPot(Item.getItemFromBlock(block), n2);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        Item item;
        if (tileEntity != null && tileEntity.getFlowerPotItem() != null) {
            item = tileEntity.getFlowerPotItem();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            item = Items.flower_pot;
        }
        return item;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\b\u001d#\u0004*\u001d' \u0004=\u0005", "dxDeI");
        BlockFlowerPot.I[" ".length()] = I("0\t8,\u0012=\u0012%", "SfVXw");
        BlockFlowerPot.I["  ".length()] = I("-?$*m\"'.0&6\u001b.3m**,\"", "DKAGC");
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.375f;
        final float n2 = n / 2.0f;
        this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, n, 0.5f + n2);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem == null || !(currentItem.getItem() instanceof ItemBlock)) {
            return "".length() != 0;
        }
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        if (tileEntity == null) {
            return "".length() != 0;
        }
        if (tileEntity.getFlowerPotItem() != null) {
            return "".length() != 0;
        }
        if (!this.canNotContain(Block.getBlockFromItem(currentItem.getItem()), currentItem.getMetadata())) {
            return "".length() != 0;
        }
        tileEntity.setFlowerPotData(currentItem.getItem(), currentItem.getMetadata());
        tileEntity.markDirty();
        world.markBlockForUpdate(blockPos);
        entityPlayer.triggerAchievement(StatList.field_181736_T);
        if (!entityPlayer.capabilities.isCreativeMode) {
            final ItemStack itemStack = currentItem;
            if ((itemStack.stackSize -= " ".length()) <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.flower_pot;
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    public BlockFlowerPot() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFlowerPot.CONTENTS, EnumFlowerType.EMPTY).withProperty((IProperty<Comparable>)BlockFlowerPot.LEGACY_DATA, "".length()));
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockFlowerPot.LEGACY_DATA);
    }
    
    private TileEntityFlowerPot getTileEntity(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        TileEntityFlowerPot tileEntityFlowerPot;
        if (tileEntity instanceof TileEntityFlowerPot) {
            tileEntityFlowerPot = (TileEntityFlowerPot)tileEntity;
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            tileEntityFlowerPot = null;
        }
        return tileEntityFlowerPot;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (super.canPlaceBlockAt(world, blockPos) && World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        EnumFlowerType enumFlowerType = EnumFlowerType.EMPTY;
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityFlowerPot) {
            final TileEntityFlowerPot tileEntityFlowerPot = (TileEntityFlowerPot)tileEntity;
            final Item flowerPotItem = tileEntityFlowerPot.getFlowerPotItem();
            if (flowerPotItem instanceof ItemBlock) {
                final int flowerPotData = tileEntityFlowerPot.getFlowerPotData();
                final Block blockFromItem = Block.getBlockFromItem(flowerPotItem);
                if (blockFromItem == Blocks.sapling) {
                    switch ($SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType()[BlockPlanks.EnumType.byMetadata(flowerPotData).ordinal()]) {
                        case 1: {
                            enumFlowerType = EnumFlowerType.OAK_SAPLING;
                            "".length();
                            if (2 < 1) {
                                throw null;
                            }
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.SPRUCE_SAPLING;
                            "".length();
                            if (2 >= 4) {
                                throw null;
                            }
                            break;
                        }
                        case 3: {
                            enumFlowerType = EnumFlowerType.BIRCH_SAPLING;
                            "".length();
                            if (3 <= 1) {
                                throw null;
                            }
                            break;
                        }
                        case 4: {
                            enumFlowerType = EnumFlowerType.JUNGLE_SAPLING;
                            "".length();
                            if (3 < 1) {
                                throw null;
                            }
                            break;
                        }
                        case 5: {
                            enumFlowerType = EnumFlowerType.ACACIA_SAPLING;
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                            break;
                        }
                        case 6: {
                            enumFlowerType = EnumFlowerType.DARK_OAK_SAPLING;
                            "".length();
                            if (-1 == 3) {
                                throw null;
                            }
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.tallgrass) {
                    switch (flowerPotData) {
                        case 0: {
                            enumFlowerType = EnumFlowerType.DEAD_BUSH;
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                            break;
                        }
                        case 2: {
                            enumFlowerType = EnumFlowerType.FERN;
                            "".length();
                            if (2 <= -1) {
                                throw null;
                            }
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            "".length();
                            if (-1 >= 1) {
                                throw null;
                            }
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.yellow_flower) {
                    enumFlowerType = EnumFlowerType.DANDELION;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (blockFromItem == Blocks.red_flower) {
                    switch ($SWITCH_TABLE$net$minecraft$block$BlockFlower$EnumFlowerType()[BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, flowerPotData).ordinal()]) {
                        case 2: {
                            enumFlowerType = EnumFlowerType.POPPY;
                            "".length();
                            if (3 >= 4) {
                                throw null;
                            }
                            break;
                        }
                        case 3: {
                            enumFlowerType = EnumFlowerType.BLUE_ORCHID;
                            "".length();
                            if (0 < 0) {
                                throw null;
                            }
                            break;
                        }
                        case 4: {
                            enumFlowerType = EnumFlowerType.ALLIUM;
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                            break;
                        }
                        case 5: {
                            enumFlowerType = EnumFlowerType.HOUSTONIA;
                            "".length();
                            if (4 == 3) {
                                throw null;
                            }
                            break;
                        }
                        case 6: {
                            enumFlowerType = EnumFlowerType.RED_TULIP;
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                            break;
                        }
                        case 7: {
                            enumFlowerType = EnumFlowerType.ORANGE_TULIP;
                            "".length();
                            if (1 >= 2) {
                                throw null;
                            }
                            break;
                        }
                        case 8: {
                            enumFlowerType = EnumFlowerType.WHITE_TULIP;
                            "".length();
                            if (4 == 3) {
                                throw null;
                            }
                            break;
                        }
                        case 9: {
                            enumFlowerType = EnumFlowerType.PINK_TULIP;
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                            break;
                        }
                        case 10: {
                            enumFlowerType = EnumFlowerType.OXEYE_DAISY;
                            "".length();
                            if (false) {
                                throw null;
                            }
                            break;
                        }
                        default: {
                            enumFlowerType = EnumFlowerType.EMPTY;
                            "".length();
                            if (3 < -1) {
                                throw null;
                            }
                            break;
                        }
                    }
                }
                else if (blockFromItem == Blocks.red_mushroom) {
                    enumFlowerType = EnumFlowerType.MUSHROOM_RED;
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else if (blockFromItem == Blocks.brown_mushroom) {
                    enumFlowerType = EnumFlowerType.MUSHROOM_BROWN;
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else if (blockFromItem == Blocks.deadbush) {
                    enumFlowerType = EnumFlowerType.DEAD_BUSH;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (blockFromItem == Blocks.cactus) {
                    enumFlowerType = EnumFlowerType.CACTUS;
                }
            }
        }
        return blockState.withProperty(BlockFlowerPot.CONTENTS, enumFlowerType);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    private boolean canNotContain(final Block block, final int n) {
        int n2;
        if (block != Blocks.yellow_flower && block != Blocks.red_flower && block != Blocks.cactus && block != Blocks.brown_mushroom && block != Blocks.red_mushroom && block != Blocks.sapling && block != Blocks.deadbush) {
            if (block == Blocks.tallgrass && n == BlockTallGrass.EnumType.FERN.getMeta()) {
                n2 = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
    
    static {
        I();
        LEGACY_DATA = PropertyInteger.create(BlockFlowerPot.I["".length()], "".length(), 0x57 ^ 0x58);
        CONTENTS = PropertyEnum.create(BlockFlowerPot.I[" ".length()], EnumFlowerType.class);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(BlockFlowerPot.I["  ".length()]);
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
        int n;
        if (tileEntity != null && tileEntity.getFlowerPotItem() != null) {
            n = tileEntity.getFlowerPotData();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockFlowerPot.CONTENTS;
        array[" ".length()] = BlockFlowerPot.LEGACY_DATA;
        return new BlockState(this, array);
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
        if (entityPlayer.capabilities.isCreativeMode) {
            final TileEntityFlowerPot tileEntity = this.getTileEntity(world, blockPos);
            if (tileEntity != null) {
                tileEntity.setFlowerPotData(null, "".length());
            }
        }
    }
    
    public enum EnumFlowerType implements IStringSerializable
    {
        BLUE_ORCHID(EnumFlowerType.I[0x23 ^ 0x27], "  ".length(), EnumFlowerType.I[0x36 ^ 0x33]), 
        ACACIA_SAPLING(EnumFlowerType.I[0x33 ^ 0x2D], 0x24 ^ 0x2B, EnumFlowerType.I[0x16 ^ 0x9]), 
        JUNGLE_SAPLING(EnumFlowerType.I[0x19 ^ 0x5], 0xA3 ^ 0xAD, EnumFlowerType.I[0x85 ^ 0x98]), 
        DARK_OAK_SAPLING(EnumFlowerType.I[0xB4 ^ 0x94], 0xB0 ^ 0xA0, EnumFlowerType.I[0x91 ^ 0xB0]), 
        EMPTY(EnumFlowerType.I["".length()], "".length(), EnumFlowerType.I[" ".length()]);
        
        private static final EnumFlowerType[] ENUM$VALUES;
        private static final String[] I;
        
        POPPY(EnumFlowerType.I["  ".length()], " ".length(), EnumFlowerType.I["   ".length()]), 
        DANDELION(EnumFlowerType.I[0xA5 ^ 0xB1], 0x3 ^ 0x9, EnumFlowerType.I[0xD7 ^ 0xC2]), 
        MUSHROOM_RED(EnumFlowerType.I[0x7B ^ 0x59], 0x0 ^ 0x11, EnumFlowerType.I[0x8F ^ 0xAC]), 
        OXEYE_DAISY(EnumFlowerType.I[0xB0 ^ 0xA2], 0x55 ^ 0x5C, EnumFlowerType.I[0xD ^ 0x1E]), 
        WHITE_TULIP(EnumFlowerType.I[0x27 ^ 0x29], 0x71 ^ 0x76, EnumFlowerType.I[0x84 ^ 0x8B]), 
        MUSHROOM_BROWN(EnumFlowerType.I[0x49 ^ 0x6D], 0xD1 ^ 0xC3, EnumFlowerType.I[0xC ^ 0x29]), 
        OAK_SAPLING(EnumFlowerType.I[0xAA ^ 0xBC], 0xA4 ^ 0xAF, EnumFlowerType.I[0xA2 ^ 0xB5]), 
        ORANGE_TULIP(EnumFlowerType.I[0x84 ^ 0x88], 0x53 ^ 0x55, EnumFlowerType.I[0x4D ^ 0x40]), 
        HOUSTONIA(EnumFlowerType.I[0x48 ^ 0x40], 0xA1 ^ 0xA5, EnumFlowerType.I[0x2F ^ 0x26]), 
        FERN(EnumFlowerType.I[0x57 ^ 0x7F], 0xB8 ^ 0xAC, EnumFlowerType.I[0xBA ^ 0x93]), 
        CACTUS(EnumFlowerType.I[0x11 ^ 0x3B], 0x17 ^ 0x2, EnumFlowerType.I[0x77 ^ 0x5C]), 
        BIRCH_SAPLING(EnumFlowerType.I[0x4F ^ 0x55], 0x1 ^ 0xC, EnumFlowerType.I[0x68 ^ 0x73]), 
        PINK_TULIP(EnumFlowerType.I[0x22 ^ 0x32], 0xA4 ^ 0xAC, EnumFlowerType.I[0x75 ^ 0x64]), 
        SPRUCE_SAPLING(EnumFlowerType.I[0x5A ^ 0x42], 0x12 ^ 0x1E, EnumFlowerType.I[0x33 ^ 0x2A]), 
        ALLIUM(EnumFlowerType.I[0x5D ^ 0x5B], "   ".length(), EnumFlowerType.I[0x9D ^ 0x9A]);
        
        private final String name;
        
        RED_TULIP(EnumFlowerType.I[0xAA ^ 0xA0], 0xAC ^ 0xA9, EnumFlowerType.I[0xBC ^ 0xB7]), 
        DEAD_BUSH(EnumFlowerType.I[0xB4 ^ 0x92], 0x51 ^ 0x42, EnumFlowerType.I[0x40 ^ 0x67]);
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            I();
            final EnumFlowerType[] enum$VALUES = new EnumFlowerType[0x14 ^ 0x2];
            enum$VALUES["".length()] = EnumFlowerType.EMPTY;
            enum$VALUES[" ".length()] = EnumFlowerType.POPPY;
            enum$VALUES["  ".length()] = EnumFlowerType.BLUE_ORCHID;
            enum$VALUES["   ".length()] = EnumFlowerType.ALLIUM;
            enum$VALUES[0xA3 ^ 0xA7] = EnumFlowerType.HOUSTONIA;
            enum$VALUES[0x6A ^ 0x6F] = EnumFlowerType.RED_TULIP;
            enum$VALUES[0x68 ^ 0x6E] = EnumFlowerType.ORANGE_TULIP;
            enum$VALUES[0x9A ^ 0x9D] = EnumFlowerType.WHITE_TULIP;
            enum$VALUES[0x93 ^ 0x9B] = EnumFlowerType.PINK_TULIP;
            enum$VALUES[0x3A ^ 0x33] = EnumFlowerType.OXEYE_DAISY;
            enum$VALUES[0x98 ^ 0x92] = EnumFlowerType.DANDELION;
            enum$VALUES[0x68 ^ 0x63] = EnumFlowerType.OAK_SAPLING;
            enum$VALUES[0xB8 ^ 0xB4] = EnumFlowerType.SPRUCE_SAPLING;
            enum$VALUES[0x4F ^ 0x42] = EnumFlowerType.BIRCH_SAPLING;
            enum$VALUES[0x5F ^ 0x51] = EnumFlowerType.JUNGLE_SAPLING;
            enum$VALUES[0xA7 ^ 0xA8] = EnumFlowerType.ACACIA_SAPLING;
            enum$VALUES[0x3D ^ 0x2D] = EnumFlowerType.DARK_OAK_SAPLING;
            enum$VALUES[0x96 ^ 0x87] = EnumFlowerType.MUSHROOM_RED;
            enum$VALUES[0x2B ^ 0x39] = EnumFlowerType.MUSHROOM_BROWN;
            enum$VALUES[0x69 ^ 0x7A] = EnumFlowerType.DEAD_BUSH;
            enum$VALUES[0xE ^ 0x1A] = EnumFlowerType.FERN;
            enum$VALUES[0x6D ^ 0x78] = EnumFlowerType.CACTUS;
            ENUM$VALUES = enum$VALUES;
        }
        
        private EnumFlowerType(final String s, final int n, final String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return this.name;
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
                if (3 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x57 ^ 0x7B])["".length()] = I("?&$<\r", "zkthT");
            EnumFlowerType.I[" ".length()] = I(")(&?\t", "LEVKp");
            EnumFlowerType.I["  ".length()] = I("<\u0004\u0013;?", "lKCkf");
            EnumFlowerType.I["   ".length()] = I("\u0019\n\u0016.", "keeKi");
            EnumFlowerType.I[0x4C ^ 0x48] = I("\u000b\b?\u0010\n\u0006\u0016)\u001d\u001c\r", "IDjUU");
            EnumFlowerType.I[0x55 ^ 0x50] = I("\b\u0003\u0002\u0014\u001c\u0005\u001d\u0014\u0019*\u000e", "jowqC");
            EnumFlowerType.I[0xA2 ^ 0xA4] = I("\u0017;\u000680\u001b", "VwJqe");
            EnumFlowerType.I[0x25 ^ 0x22] = I("\u000f!\n#\u0012\u0003", "nMfJg");
            EnumFlowerType.I[0x6E ^ 0x66] = I("\u001b\u0018\u001e\u00019\u001c\u0019\u0002\u0013", "SWKRm");
            EnumFlowerType.I[0x0 ^ 0x9] = I("$?&\u0018\u0003#>:\n", "LPSkw");
            EnumFlowerType.I[0x4E ^ 0x44] = I(";\u0002&9\"<\u000b+6", "iGbfv");
            EnumFlowerType.I[0x25 ^ 0x2E] = I("'\u001f\u001c\u0012\u0001 \u0016\u0011=", "UzxMu");
            EnumFlowerType.I[0x63 ^ 0x6F] = I("\u0004'\u0011'\u0006\u000e*\u0004<\r\u0002%", "KuPiA");
            EnumFlowerType.I[0x76 ^ 0x7B] = I("!&2 \u000f+\u000b';\u0004'$", "NTSNh");
            EnumFlowerType.I[0x1A ^ 0x14] = I("\u0005\u001f!\u001f,\r\u0003=\u0007 \u0002", "RWhKi");
            EnumFlowerType.I[0xB1 ^ 0xBE] = I("'#;<\u0004\u000f?'$\b ", "PKRHa");
            EnumFlowerType.I[0x0 ^ 0x10] = I(" \u001e/:%$\u0002-8*", "pWaqz");
            EnumFlowerType.I[0xA9 ^ 0xB8] = I("\u0013'>\u001e\u000e\u0017;<\u001c!", "cNPuQ");
            EnumFlowerType.I[0x87 ^ 0x95] = I("(\u0015)*78\t-:!>", "gMlsr");
            EnumFlowerType.I[0x76 ^ 0x65] = I("$\u000b5\f.\u0014\u00171\u001c82", "KsPuK");
            EnumFlowerType.I[0x79 ^ 0x6D] = I("\u00029'\u001e!\n1&\u0014", "FxiZd");
            EnumFlowerType.I[0x5 ^ 0x10] = I("\u0003\u0011!!5\u000b\u0019 +", "gpOEP");
            EnumFlowerType.I[0x1E ^ 0x8] = I("?9\u0011\u001a\t1(\u0016\f\u00147", "pxZEZ");
            EnumFlowerType.I[0xD4 ^ 0xC3] = I("&\u0006!'\u0001(\u0017&\u0011\u001c.", "IgJxr");
            EnumFlowerType.I[0xDA ^ 0xC2] = I("\u0005\u00016\u0006\b\u0013\u000e7\u0012\u001b\u001a\u0018*\u0014", "VQdSK");
            EnumFlowerType.I[0x53 ^ 0x4A] = I("+\u00125;\u0001==4/\u00124\u000b))", "XbGNb");
            EnumFlowerType.I[0x3A ^ 0x20] = I("\u0015>\u0010\u0006%\b$\u0003\u0015!\u001e9\u0005", "WwBEm");
            EnumFlowerType.I[0x98 ^ 0x83] = I("\u0012<\u001b%=/&\b69\u0019;\u000e", "pUiFU");
            EnumFlowerType.I[0xC ^ 0x10] = I("\r&\u0016=\u0019\u0002,\u000b;\u0005\u000b:\u0016=", "GsXzU");
            EnumFlowerType.I[0x8F ^ 0x92] = I("%\u001a-\t$*00\u000f8#\u0006-\t", "OoCnH");
            EnumFlowerType.I[0x5B ^ 0x45] = I(";:.7?;&<5&60!3", "zyotv");
            EnumFlowerType.I[0x62 ^ 0x7D] = I(",\u0006\u000b\u0013\u0010,:\u0019\u0011\t!\f\u0004\u0017", "Mejpy");
            EnumFlowerType.I[0x9A ^ 0xBA] = I("\"\u0000\u001b=3)\u0000\u0002)?'\u0011\u0005?\"!", "fAIvl");
            EnumFlowerType.I[0x6D ^ 0x4C] = I("7\u0007;\"\u001b<\u0007\"\u001672\u0016% *4", "SfIID");
            EnumFlowerType.I[0xBB ^ 0x99] = I(")\u00184\u0012(+\u0002*\u0005(!\t", "dMgZz");
            EnumFlowerType.I[0xA9 ^ 0x8A] = I("\u001a\u0004\u0017\u0000\u0001\u0018\u001e\t7\u0001\u0012\u0015", "wqdhs");
            EnumFlowerType.I[0x77 ^ 0x53] = I("\u0014!1$(\u0016;/38\u000b;5\"", "Ytblz");
            EnumFlowerType.I[0x5B ^ 0x7E] = I("\f3;)\u0016\u000e)%\u001e\u0006\u0013)?/", "aFHAd");
            EnumFlowerType.I[0x1E ^ 0x38] = I("1\u0010;\u0005\u000f7\u0000)\t", "uUzAP");
            EnumFlowerType.I[0x6A ^ 0x4D] = I("\u0003\u000e\b\u0012\u0006\u0005\u001e\u001a\u001e", "gkivY");
            EnumFlowerType.I[0x75 ^ 0x5D] = I("\n?\u0015+", "LzGeU");
            EnumFlowerType.I[0x75 ^ 0x5C] = I(" \u0003*\r", "FfXcn");
            EnumFlowerType.I[0x5B ^ 0x71] = I("\u0013&\u001b\u0011-\u0003", "PgXEx");
            EnumFlowerType.I[0x32 ^ 0x19] = I("\t\u0007!86\u0019", "jfBLC");
        }
    }
}
