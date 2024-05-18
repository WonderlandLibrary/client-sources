package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockTallGrass extends BlockBush implements IGrowable
{
    private static final String[] I;
    public static final PropertyEnum<EnumType> TYPE;
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockTallGrass.TYPE;
        return new BlockState(this, array);
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, " ".length(), blockState.getValue(BlockTallGrass.TYPE).getMeta()));
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return " ".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockTallGrass.TYPE, EnumType.byMetadata(n));
    }
    
    protected BlockTallGrass() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockTallGrass.TYPE, EnumType.DEAD_BUSH));
        final float n = 0.4f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.8f, 0.5f + n);
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        if (blockState.getValue(BlockTallGrass.TYPE) != EnumType.DEAD_BUSH) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0003/\u0018\u0011", "wVhty");
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return " ".length() + random.nextInt(n * "  ".length() + " ".length());
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        int grassColor;
        if (blockState.getValue(BlockTallGrass.TYPE) == EnumType.DEAD_BUSH) {
            grassColor = 9295192 + 15035561 - 20415696 + 12862158;
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            grassColor = ColorizerGrass.getGrassColor(0.5, 1.0);
        }
        return grassColor;
    }
    
    static {
        I();
        TYPE = PropertyEnum.create(BlockTallGrass.I["".length()], EnumType.class);
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XYZ;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        BlockDoublePlant.EnumPlantType enumPlantType = BlockDoublePlant.EnumPlantType.GRASS;
        if (blockState.getValue(BlockTallGrass.TYPE) == EnumType.FERN) {
            enumPlantType = BlockDoublePlant.EnumPlantType.FERN;
        }
        if (Blocks.double_plant.canPlaceBlockAt(world, blockPos)) {
            Blocks.double_plant.placeAt(world, blockPos, enumPlantType, "  ".length());
        }
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        int i = " ".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < "   ".length()) {
            list.add(new ItemStack(item, " ".length(), i));
            ++i;
        }
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return " ".length() != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockTallGrass.TYPE).getMeta();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        Item wheat_seeds;
        if (random.nextInt(0xCB ^ 0xC3) == 0) {
            wheat_seeds = Items.wheat_seeds;
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            wheat_seeds = null;
        }
        return wheat_seeds;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final int meta;
        private static final EnumType[] META_LOOKUP;
        
        GRASS(EnumType.I["  ".length()], " ".length(), " ".length(), EnumType.I["   ".length()]), 
        FERN(EnumType.I[0x2 ^ 0x6], "  ".length(), "  ".length(), EnumType.I[0x92 ^ 0x97]), 
        DEAD_BUSH(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()]);
        
        private final String name;
        private static final EnumType[] ENUM$VALUES;
        private static final String[] I;
        
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
                if (1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private EnumType(final String s, final int n, final int meta, final String name) {
            this.meta = meta;
            this.name = name;
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType["   ".length()];
            enum$VALUES["".length()] = EnumType.DEAD_BUSH;
            enum$VALUES[" ".length()] = EnumType.GRASS;
            enum$VALUES["  ".length()] = EnumType.FERN;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMeta()] = enumType;
                ++i;
            }
        }
        
        private static void I() {
            (I = new String[0x73 ^ 0x75])["".length()] = I("72\u0007#-1\"\u0015/", "swFgr");
            EnumType.I[" ".length()] = I("\b\r\u0002&%\u000e\u001d\u0010*", "lhcBz");
            EnumType.I["  ".length()] = I("\u0012\u0010\u000f\t\u0017", "UBNZD");
            EnumType.I["   ".length()] = I("\u0015%6\u0015\u0013\u00066;\n?", "aDZyL");
            EnumType.I[0xA3 ^ 0xA7] = I("/+\u0000:", "inRtH");
            EnumType.I[0x46 ^ 0x43] = I("\u0016\f=#", "piOMA");
        }
    }
}
