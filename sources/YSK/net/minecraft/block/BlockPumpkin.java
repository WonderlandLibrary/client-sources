package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.init.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class BlockPumpkin extends BlockDirectional
{
    private static final String[] I;
    private BlockPattern golemPattern;
    private BlockPattern golemBasePattern;
    private BlockPattern snowmanBasePattern;
    private static final Predicate<IBlockState> field_181085_Q;
    private BlockPattern snowmanPattern;
    
    private void trySpawnGolem(final World world, final BlockPos blockPos) {
        final BlockPattern.PatternHelper match;
        if ((match = this.getSnowmanPattern().match(world, blockPos)) != null) {
            int i = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i < this.getSnowmanPattern().getThumbLength()) {
                world.setBlockState(match.translateOffset("".length(), i, "".length()).getPos(), Blocks.air.getDefaultState(), "  ".length());
                ++i;
            }
            final EntitySnowman entitySnowman = new EntitySnowman(world);
            final BlockPos pos = match.translateOffset("".length(), "  ".length(), "".length()).getPos();
            entitySnowman.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 0.05, pos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntityInWorld(entitySnowman);
            int j = "".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
            while (j < (0x2E ^ 0x56)) {
                world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, pos.getX() + world.rand.nextDouble(), pos.getY() + world.rand.nextDouble() * 2.5, pos.getZ() + world.rand.nextDouble(), 0.0, 0.0, 0.0, new int["".length()]);
                ++j;
            }
            int k = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (k < this.getSnowmanPattern().getThumbLength()) {
                world.notifyNeighborsRespectDebug(match.translateOffset("".length(), k, "".length()).getPos(), Blocks.air);
                ++k;
            }
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            final BlockPattern.PatternHelper match2;
            if ((match2 = this.getGolemPattern().match(world, blockPos)) != null) {
                int l = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (l < this.getGolemPattern().getPalmLength()) {
                    int length = "".length();
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                    while (length < this.getGolemPattern().getThumbLength()) {
                        world.setBlockState(match2.translateOffset(l, length, "".length()).getPos(), Blocks.air.getDefaultState(), "  ".length());
                        ++length;
                    }
                    ++l;
                }
                final BlockPos pos2 = match2.translateOffset(" ".length(), "  ".length(), "".length()).getPos();
                final EntityIronGolem entityIronGolem = new EntityIronGolem(world);
                entityIronGolem.setPlayerCreated(" ".length() != 0);
                entityIronGolem.setLocationAndAngles(pos2.getX() + 0.5, pos2.getY() + 0.05, pos2.getZ() + 0.5, 0.0f, 0.0f);
                world.spawnEntityInWorld(entityIronGolem);
                int length2 = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (length2 < (0x1B ^ 0x63)) {
                    world.spawnParticle(EnumParticleTypes.SNOWBALL, pos2.getX() + world.rand.nextDouble(), pos2.getY() + world.rand.nextDouble() * 3.9, pos2.getZ() + world.rand.nextDouble(), 0.0, 0.0, 0.0, new int["".length()]);
                    ++length2;
                }
                int length3 = "".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
                while (length3 < this.getGolemPattern().getPalmLength()) {
                    int length4 = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    while (length4 < this.getGolemPattern().getThumbLength()) {
                        world.notifyNeighborsRespectDebug(match2.translateOffset(length3, length4, "".length()).getPos(), Blocks.air);
                        ++length4;
                    }
                    ++length3;
                }
            }
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.trySpawnGolem(world, blockPos);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockPumpkin.FACING, EnumFacing.getHorizontal(n));
    }
    
    protected BlockPattern getGolemBasePattern() {
        if (this.golemBasePattern == null) {
            final FactoryBlockPattern start = FactoryBlockPattern.start();
            final String[] array = new String["   ".length()];
            array["".length()] = BlockPumpkin.I[0xB8 ^ 0xBE];
            array[" ".length()] = BlockPumpkin.I[0x98 ^ 0x9F];
            array["  ".length()] = BlockPumpkin.I[0x92 ^ 0x9A];
            this.golemBasePattern = start.aisle(array).where((char)(0x4E ^ 0x6D), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.iron_block))).where((char)(0xDC ^ 0xA2), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.golemBasePattern;
    }
    
    protected BlockPattern getSnowmanPattern() {
        if (this.snowmanPattern == null) {
            final FactoryBlockPattern start = FactoryBlockPattern.start();
            final String[] array = new String["   ".length()];
            array["".length()] = BlockPumpkin.I["   ".length()];
            array[" ".length()] = BlockPumpkin.I[0x2B ^ 0x2F];
            array["  ".length()] = BlockPumpkin.I[0x9 ^ 0xC];
            this.snowmanPattern = start.aisle(array).where((char)(0xB ^ 0x55), BlockWorldState.hasState(BlockPumpkin.field_181085_Q)).where((char)(0x28 ^ 0xB), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.snowmanPattern;
    }
    
    protected BlockPattern getGolemPattern() {
        if (this.golemPattern == null) {
            final FactoryBlockPattern start = FactoryBlockPattern.start();
            final String[] array = new String["   ".length()];
            array["".length()] = BlockPumpkin.I[0xA4 ^ 0xAD];
            array[" ".length()] = BlockPumpkin.I[0x1D ^ 0x17];
            array["  ".length()] = BlockPumpkin.I[0x65 ^ 0x6E];
            this.golemPattern = start.aisle(array).where((char)(0xCB ^ 0x95), BlockWorldState.hasState(BlockPumpkin.field_181085_Q)).where((char)(0x87 ^ 0xA4), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.iron_block))).where((char)(0x7 ^ 0x79), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.golemPattern;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable() && World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<EnumFacing>)BlockPumpkin.FACING).getHorizontalIndex();
    }
    
    private static void I() {
        (I = new String[0x20 ^ 0x2C])["".length()] = I("Z", "zzaSv");
        BlockPumpkin.I[" ".length()] = I("D", "gtNLv");
        BlockPumpkin.I["  ".length()] = I("d", "GcHsi");
        BlockPumpkin.I["   ".length()] = I("\u0017", "IgdEw");
        BlockPumpkin.I[0x8E ^ 0x8A] = I("o", "LOIvc");
        BlockPumpkin.I[0xAF ^ 0xAA] = I("P", "sLDuV");
        BlockPumpkin.I[0x42 ^ 0x44] = I("\u0013u'", "mUYOt");
        BlockPumpkin.I[0x5A ^ 0x5D] = I("kPq", "HsRBF");
        BlockPumpkin.I[0xCD ^ 0xC5] = I("\u001d`5", "cCKcG");
        BlockPumpkin.I[0x5 ^ 0xC] = I("14\u001b", "Ojexg");
        BlockPumpkin.I[0xA8 ^ 0xA2] = I("sSR", "PpqEs");
        BlockPumpkin.I[0x4C ^ 0x47] = I("\bS\u0007", "vpyMW");
    }
    
    public boolean canDispenserPlace(final World world, final BlockPos blockPos) {
        if (this.getSnowmanBasePattern().match(world, blockPos) == null && this.getGolemBasePattern().match(world, blockPos) == null) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    protected BlockPattern getSnowmanBasePattern() {
        if (this.snowmanBasePattern == null) {
            final FactoryBlockPattern start = FactoryBlockPattern.start();
            final String[] array = new String["   ".length()];
            array["".length()] = BlockPumpkin.I["".length()];
            array[" ".length()] = BlockPumpkin.I[" ".length()];
            array["  ".length()] = BlockPumpkin.I["  ".length()];
            this.snowmanBasePattern = start.aisle(array).where((char)(0x7 ^ 0x24), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.snow))).build();
        }
        return this.snowmanBasePattern;
    }
    
    protected BlockPumpkin() {
        super(Material.gourd, MapColor.adobeColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockPumpkin.FACING, EnumFacing.NORTH));
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockPumpkin.FACING, entityLivingBase.getHorizontalFacing().getOpposite());
    }
    
    static {
        I();
        field_181085_Q = (Predicate)new Predicate<IBlockState>() {
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
                    if (0 == -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final IBlockState blockState) {
                if (blockState != null && (blockState.getBlock() == Blocks.pumpkin || blockState.getBlock() == Blocks.lit_pumpkin)) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((IBlockState)o);
            }
        };
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockPumpkin.FACING;
        return new BlockState(this, array);
    }
}
