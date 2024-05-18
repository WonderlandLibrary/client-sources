package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.world.biome.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class BlockDoublePlant extends BlockBush implements IGrowable
{
    public static final PropertyEnum<EnumBlockHalf> HALF;
    private static final String[] I;
    public static final PropertyEnum<EnumPlantType> VARIANT;
    public static final PropertyEnum<EnumFacing> field_181084_N;
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        Block.spawnAsEntity(world, blockPos, new ItemStack(this, " ".length(), this.getVariant(world, blockPos).getMeta()));
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != this) {
            return " ".length() != 0;
        }
        final EnumPlantType enumPlantType = this.getActualState(blockState, world, blockPos).getValue(BlockDoublePlant.VARIANT);
        if (enumPlantType != EnumPlantType.FERN && enumPlantType != EnumPlantType.GRASS) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            final IBlockState blockState = blockAccess.getBlockState(blockPos.down());
            if (blockState.getBlock() == this) {
                withProperty = withProperty.withProperty(BlockDoublePlant.VARIANT, (EnumPlantType)blockState.getValue((IProperty<V>)BlockDoublePlant.VARIANT));
            }
        }
        return withProperty;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        int n;
        if (blockState.getValue(BlockDoublePlant.HALF) != EnumBlockHalf.UPPER && blockState.getValue(BlockDoublePlant.VARIANT) != EnumPlantType.GRASS) {
            n = blockState.getValue(BlockDoublePlant.VARIANT).getMeta();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (super.canPlaceBlockAt(world, blockPos) && world.isAirBlock(blockPos.up())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean onHarvest(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        final EnumPlantType enumPlantType = blockState.getValue(BlockDoublePlant.VARIANT);
        if (enumPlantType != EnumPlantType.FERN && enumPlantType != EnumPlantType.GRASS) {
            return "".length() != 0;
        }
        entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
        BlockTallGrass.EnumType enumType;
        if (enumPlantType == EnumPlantType.GRASS) {
            enumType = BlockTallGrass.EnumType.GRASS;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            enumType = BlockTallGrass.EnumType.FERN;
        }
        Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, "  ".length(), enumType.getMeta()));
        return " ".length() != 0;
    }
    
    public EnumPlantType getVariant(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            return this.getActualState(blockState, blockAccess, blockPos).getValue(BlockDoublePlant.VARIANT);
        }
        return EnumPlantType.FERN;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(">\u000f\u0015\r&&\u001a", "HngdG");
        BlockDoublePlant.I[" ".length()] = I("\u00016\b(", "iWdNR");
        BlockDoublePlant.I["  ".length()] = I("\u0016\n\"\u001b\r\u00175;\u0018\u000f\u0006", "reWya");
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockDoublePlant.I["".length()], EnumPlantType.class);
        HALF = PropertyEnum.create(BlockDoublePlant.I[" ".length()], EnumBlockHalf.class);
        field_181084_N = BlockDirectional.FACING;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return " ".length() != 0;
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (world.isRemote || entityPlayer.getCurrentEquippedItem() == null || entityPlayer.getCurrentEquippedItem().getItem() != Items.shears || blockState.getValue(BlockDoublePlant.HALF) != EnumBlockHalf.LOWER || !this.onHarvest(world, blockPos, blockState, entityPlayer)) {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final EnumPlantType variant = this.getVariant(blockAccess, blockPos);
        int grassColorAtPos;
        if (variant != EnumPlantType.GRASS && variant != EnumPlantType.FERN) {
            grassColorAtPos = 10469079 + 15578283 - 24376003 + 15105856;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            grassColorAtPos = BiomeColorHelper.getGrassColorAtPos(blockAccess, blockPos);
        }
        return grassColorAtPos;
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int meta;
        if (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            meta = ((0x73 ^ 0x7B) | blockState.getValue(BlockDoublePlant.field_181084_N).getHorizontalIndex());
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            meta = blockState.getValue(BlockDoublePlant.VARIANT).getMeta();
        }
        return meta;
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return this.getVariant(world, blockPos).getMeta();
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        final EnumPlantType variant = this.getVariant(world, blockPos);
        if (variant != EnumPlantType.GRASS && variant != EnumPlantType.FERN) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumPlantType[] values;
        final int length = (values = EnumPlantType.values()).length;
        int i = "".length();
        "".length();
        if (false == true) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMeta()));
            ++i;
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockDoublePlant.HALF;
        array[" ".length()] = BlockDoublePlant.VARIANT;
        array["  ".length()] = BlockDoublePlant.field_181084_N;
        return new BlockState(this, array);
    }
    
    public BlockDoublePlant() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDoublePlant.VARIANT, EnumPlantType.SUNFLOWER).withProperty(BlockDoublePlant.HALF, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.field_181084_N, EnumFacing.NORTH));
        this.setHardness(0.0f);
        this.setStepSound(BlockDoublePlant.soundTypeGrass);
        this.setUnlocalizedName(BlockDoublePlant.I["  ".length()]);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            if (world.getBlockState(blockPos.down()).getBlock() == this) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        else {
            final IBlockState blockState2 = world.getBlockState(blockPos.up());
            if (blockState2.getBlock() == this && super.canBlockStay(world, blockPos, blockState2)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    @Override
    protected void checkAndDropBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            int n;
            if (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
                n = " ".length();
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            BlockPos up;
            if (n2 != 0) {
                up = blockPos;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                up = blockPos.up();
            }
            final BlockPos blockPos2 = up;
            BlockPos down;
            if (n2 != 0) {
                down = blockPos.down();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                down = blockPos;
            }
            final BlockPos blockPos3 = down;
            Block block;
            if (n2 != 0) {
                block = this;
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                block = world.getBlockState(blockPos2).getBlock();
            }
            final Block block2 = block;
            Block block3;
            if (n2 != 0) {
                block3 = world.getBlockState(blockPos3).getBlock();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                block3 = this;
            }
            final Block block4 = block3;
            if (block2 == this) {
                world.setBlockState(blockPos2, Blocks.air.getDefaultState(), "  ".length());
            }
            if (block4 == this) {
                world.setBlockState(blockPos3, Blocks.air.getDefaultState(), "   ".length());
                if (n2 == 0) {
                    this.dropBlockAsItem(world, blockPos3, blockState, "".length());
                }
            }
        }
    }
    
    public void placeAt(final World world, final BlockPos blockPos, final EnumPlantType enumPlantType, final int n) {
        world.setBlockState(blockPos, this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.VARIANT, enumPlantType), n);
        world.setBlockState(blockPos.up(), this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.UPPER), n);
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            if (world.getBlockState(blockPos.down()).getBlock() == this) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    final IBlockState blockState2 = world.getBlockState(blockPos.down());
                    final EnumPlantType enumPlantType = blockState2.getValue(BlockDoublePlant.VARIANT);
                    if (enumPlantType != EnumPlantType.FERN && enumPlantType != EnumPlantType.GRASS) {
                        world.destroyBlock(blockPos.down(), " ".length() != 0);
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                    else if (!world.isRemote) {
                        if (entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
                            this.onHarvest(world, blockPos, blockState2, entityPlayer);
                            world.setBlockToAir(blockPos.down());
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            world.destroyBlock(blockPos.down(), " ".length() != 0);
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                        }
                    }
                    else {
                        world.setBlockToAir(blockPos.down());
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                }
                else {
                    world.setBlockToAir(blockPos.down());
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
            }
        }
        else if (entityPlayer.capabilities.isCreativeMode && world.getBlockState(blockPos.up()).getBlock() == this) {
            world.setBlockState(blockPos.up(), Blocks.air.getDefaultState(), "  ".length());
        }
        super.onBlockHarvested(world, blockPos, blockState, entityPlayer);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        if (blockState.getValue(BlockDoublePlant.HALF) == EnumBlockHalf.UPPER) {
            return null;
        }
        final EnumPlantType enumPlantType = blockState.getValue(BlockDoublePlant.VARIANT);
        Item item;
        if (enumPlantType == EnumPlantType.FERN) {
            item = null;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (enumPlantType == EnumPlantType.GRASS) {
            if (random.nextInt(0x23 ^ 0x2B) == 0) {
                item = Items.wheat_seeds;
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                item = null;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
        }
        else {
            item = Item.getItemFromBlock(this);
        }
        return item;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos.up(), this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.UPPER), "  ".length());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        IBlockState blockState;
        if ((n & (0xB7 ^ 0xBF)) > 0) {
            blockState = this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.UPPER);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            blockState = this.getDefaultState().withProperty(BlockDoublePlant.HALF, EnumBlockHalf.LOWER).withProperty(BlockDoublePlant.VARIANT, EnumPlantType.byMetadata(n & (0x6C ^ 0x6B)));
        }
        return blockState;
    }
    
    public enum EnumBlockHalf implements IStringSerializable
    {
        private static final EnumBlockHalf[] ENUM$VALUES;
        private static final String[] I;
        
        UPPER(EnumBlockHalf.I["".length()], "".length()), 
        LOWER(EnumBlockHalf.I[" ".length()], " ".length());
        
        static {
            I();
            final EnumBlockHalf[] enum$VALUES = new EnumBlockHalf["  ".length()];
            enum$VALUES["".length()] = EnumBlockHalf.UPPER;
            enum$VALUES[" ".length()] = EnumBlockHalf.LOWER;
            ENUM$VALUES = enum$VALUES;
        }
        
        private EnumBlockHalf(final String s, final int n) {
        }
        
        private static void I() {
            (I = new String[0x5C ^ 0x58])["".length()] = I(",\u001b\u0000\u0014\u001a", "yKPQH");
            EnumBlockHalf.I[" ".length()] = I(":$\u001e.8", "vkIkj");
            EnumBlockHalf.I["  ".length()] = I("09=/\u0019", "EIMJk");
            EnumBlockHalf.I["   ".length()] = I("\u001e&\u000f\u00061", "rIxcC");
        }
        
        @Override
        public String getName() {
            String s;
            if (this == EnumBlockHalf.UPPER) {
                s = EnumBlockHalf.I["  ".length()];
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                s = EnumBlockHalf.I["   ".length()];
            }
            return s;
        }
        
        @Override
        public String toString() {
            return this.getName();
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
                if (1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    public enum EnumPlantType implements IStringSerializable
    {
        SUNFLOWER(EnumPlantType.I["".length()], "".length(), "".length(), EnumPlantType.I[" ".length()]);
        
        private static final EnumPlantType[] ENUM$VALUES;
        private final String name;
        private final int meta;
        
        ROSE(EnumPlantType.I[0xBD ^ 0xB7], 0x89 ^ 0x8D, 0x4C ^ 0x48, EnumPlantType.I[0xA4 ^ 0xAF], EnumPlantType.I[0x8B ^ 0x87]), 
        FERN(EnumPlantType.I[0x0 ^ 0x7], "   ".length(), "   ".length(), EnumPlantType.I[0x72 ^ 0x7A], EnumPlantType.I[0x41 ^ 0x48]);
        
        private static final String[] I;
        
        PAEONIA(EnumPlantType.I[0xC ^ 0x1], 0x20 ^ 0x25, 0x91 ^ 0x94, EnumPlantType.I[0x59 ^ 0x57]);
        
        private final String unlocalizedName;
        
        GRASS(EnumPlantType.I[0x70 ^ 0x74], "  ".length(), "  ".length(), EnumPlantType.I[0xA9 ^ 0xAC], EnumPlantType.I[0x29 ^ 0x2F]), 
        SYRINGA(EnumPlantType.I["  ".length()], " ".length(), " ".length(), EnumPlantType.I["   ".length()]);
        
        private static final EnumPlantType[] META_LOOKUP;
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumPlantType(final String s, final int n, final int n2, final String s2) {
            this(s, n, n2, s2, s2);
        }
        
        private static void I() {
            (I = new String[0xCB ^ 0xC4])["".length()] = I("\u0015\u0002\u0006+\n\t\u0000\r?", "FWHmF");
            EnumPlantType.I[" ".length()] = I("\u001c\u001c4\u000b\u0001\u0000\u001e?\u001f", "oiZmm");
            EnumPlantType.I["  ".length()] = I("1+\u001f\u000e\u0017%3", "brMGY");
            EnumPlantType.I["   ".length()] = I("\u0017\f\"\u0011\u0018\u0003\u0014", "duPxv");
            EnumPlantType.I[0x29 ^ 0x2D] = I("/\u0001;\u001e7", "hSzMd");
            EnumPlantType.I[0x28 ^ 0x2D] = I("7?\u00060\t6\u000f\u0014 \u0004 #", "SPsRe");
            EnumPlantType.I[0x3 ^ 0x5] = I("/\u001e\u0018\u001c\u0018", "Hlyok");
            EnumPlantType.I[0xC ^ 0xB] = I("\u001230+", "Tvbeh");
            EnumPlantType.I[0x74 ^ 0x7C] = I("\u0017  \u000b\b\u0016\u00103\f\u0016\u001d", "sOUid");
            EnumPlantType.I[0xB4 ^ 0xBD] = I("/\u0002\u0017\b", "IgefN");
            EnumPlantType.I[0xB4 ^ 0xBE] = I("\u0006!\u0004\r", "TnWHV");
            EnumPlantType.I[0x9E ^ 0x95] = I("\u0002\u001a\u0007\u0015\u0005\u0003*\u0000\u0018\u001a\u0003", "furwi");
            EnumPlantType.I[0xB5 ^ 0xB9] = I(";\u00027'", "ImDBb");
            EnumPlantType.I[0x21 ^ 0x2C] = I("\b\f\u000e? \u0011\f", "XMKpn");
            EnumPlantType.I[0xBF ^ 0xB1] = I("80\u0012\u0004\u0017!0", "HQwky");
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
                if (-1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final EnumPlantType[] enum$VALUES = new EnumPlantType[0xBC ^ 0xBA];
            enum$VALUES["".length()] = EnumPlantType.SUNFLOWER;
            enum$VALUES[" ".length()] = EnumPlantType.SYRINGA;
            enum$VALUES["  ".length()] = EnumPlantType.GRASS;
            enum$VALUES["   ".length()] = EnumPlantType.FERN;
            enum$VALUES[0x6 ^ 0x2] = EnumPlantType.ROSE;
            enum$VALUES[0x93 ^ 0x96] = EnumPlantType.PAEONIA;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumPlantType[values().length];
            final EnumPlantType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (i < length) {
                final EnumPlantType enumPlantType = values[i];
                EnumPlantType.META_LOOKUP[enumPlantType.getMeta()] = enumPlantType;
                ++i;
            }
        }
        
        private EnumPlantType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static EnumPlantType byMetadata(int length) {
            if (length < 0 || length >= EnumPlantType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumPlantType.META_LOOKUP[length];
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public int getMeta() {
            return this.meta;
        }
    }
}
