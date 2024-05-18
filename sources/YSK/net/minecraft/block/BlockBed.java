package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockBed extends BlockDirectional
{
    public static final PropertyBool OCCUPIED;
    private static final String[] I;
    public static final PropertyEnum<EnumPartType> PART;
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.bed;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockBed.FACING);
        if (blockState.getValue(BlockBed.PART) == EnumPartType.HEAD) {
            if (world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock() != this) {
                world.setBlockToAir(blockPos);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
        }
        else if (world.getBlockState(blockPos.offset(enumFacing)).getBlock() != this) {
            world.setBlockToAir(blockPos);
            if (!world.isRemote) {
                this.dropBlockAsItem(world, blockPos, blockState, "".length());
            }
        }
    }
    
    public static BlockPos getSafeExitLocation(final World world, final BlockPos blockPos, int n) {
        final EnumFacing enumFacing = world.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockBed.FACING);
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        int i = "".length();
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (i <= " ".length()) {
            final int n2 = x - enumFacing.getFrontOffsetX() * i - " ".length();
            final int n3 = z - enumFacing.getFrontOffsetZ() * i - " ".length();
            final int n4 = n2 + "  ".length();
            final int n5 = n3 + "  ".length();
            int j = n2;
            "".length();
            if (4 < 4) {
                throw null;
            }
            while (j <= n4) {
                int k = n3;
                "".length();
                if (0 == 2) {
                    throw null;
                }
                while (k <= n5) {
                    final BlockPos blockPos2 = new BlockPos(j, y, k);
                    if (hasRoomForPlayer(world, blockPos2)) {
                        if (n <= 0) {
                            return blockPos2;
                        }
                        --n;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return null;
    }
    
    private EntityPlayer getPlayerInBed(final World world, final BlockPos blockPos) {
        final Iterator<EntityPlayer> iterator = world.playerEntities.iterator();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayer entityPlayer = iterator.next();
            if (entityPlayer.isPlayerSleeping() && entityPlayer.playerLocation.equals(blockPos)) {
                return entityPlayer;
            }
        }
        return null;
    }
    
    protected static boolean hasRoomForPlayer(final World world, final BlockPos blockPos) {
        if (World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && !world.getBlockState(blockPos).getBlock().getMaterial().isSolid() && !world.getBlockState(blockPos.up()).getBlock().getMaterial().isSolid()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean onBlockActivated(final World world, BlockPos offset, IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        if (blockState.getValue(BlockBed.PART) != EnumPartType.HEAD) {
            offset = offset.offset(blockState.getValue((IProperty<EnumFacing>)BlockBed.FACING));
            blockState = world.getBlockState(offset);
            if (blockState.getBlock() != this) {
                return " ".length() != 0;
            }
        }
        if (!world.provider.canRespawnHere() || world.getBiomeGenForCoords(offset) == BiomeGenBase.hell) {
            world.setBlockToAir(offset);
            final BlockPos offset2 = offset.offset(blockState.getValue((IProperty<EnumFacing>)BlockBed.FACING).getOpposite());
            if (world.getBlockState(offset2).getBlock() == this) {
                world.setBlockToAir(offset2);
            }
            world.newExplosion(null, offset.getX() + 0.5, offset.getY() + 0.5, offset.getZ() + 0.5, 5.0f, " ".length() != 0, " ".length() != 0);
            return " ".length() != 0;
        }
        if (blockState.getValue((IProperty<Boolean>)BlockBed.OCCUPIED)) {
            if (this.getPlayerInBed(world, offset) != null) {
                entityPlayer.addChatComponentMessage(new ChatComponentTranslation(BlockBed.I["  ".length()], new Object["".length()]));
                return " ".length() != 0;
            }
            blockState = blockState.withProperty((IProperty<Comparable>)BlockBed.OCCUPIED, "".length() != 0);
            world.setBlockState(offset, blockState, 0x51 ^ 0x55);
        }
        final EntityPlayer.EnumStatus trySleep = entityPlayer.trySleep(offset);
        if (trySleep == EntityPlayer.EnumStatus.OK) {
            blockState = blockState.withProperty((IProperty<Comparable>)BlockBed.OCCUPIED, " ".length() != 0);
            world.setBlockState(offset, blockState, 0x37 ^ 0x33);
            return " ".length() != 0;
        }
        if (trySleep == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(BlockBed.I["   ".length()], new Object["".length()]));
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else if (trySleep == EntityPlayer.EnumStatus.NOT_SAFE) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation(BlockBed.I[0x6A ^ 0x6E], new Object["".length()]));
        }
        return " ".length() != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockBed.FACING;
        array[" ".length()] = BlockBed.PART;
        array["  ".length()] = BlockBed.OCCUPIED;
        return new BlockState(this, array);
    }
    
    static {
        I();
        PART = PropertyEnum.create(BlockBed.I["".length()], EnumPartType.class);
        OCCUPIED = PropertyBool.create(BlockBed.I[" ".length()]);
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockBed.PART) == EnumPartType.FOOT) {
            final IBlockState blockState = blockAccess.getBlockState(blockPos.offset(withProperty.getValue((IProperty<EnumFacing>)BlockBed.FACING)));
            if (blockState.getBlock() == this) {
                withProperty = withProperty.withProperty((IProperty<Comparable>)BlockBed.OCCUPIED, (Boolean)blockState.getValue((IProperty<V>)BlockBed.OCCUPIED));
            }
        }
        return withProperty;
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode && blockState.getValue(BlockBed.PART) == EnumPartType.HEAD) {
            final BlockPos offset = blockPos.offset(blockState.getValue((IProperty<EnumFacing>)BlockBed.FACING).getOpposite());
            if (world.getBlockState(offset).getBlock() == this) {
                world.setBlockToAir(offset);
            }
        }
    }
    
    private void setBedBounds() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5625f, 1.0f);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (blockState.getValue(BlockBed.PART) == EnumPartType.FOOT) {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, "".length());
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return " ".length();
    }
    
    public BlockBed() {
        super(Material.cloth);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBed.PART, EnumPartType.FOOT).withProperty((IProperty<Comparable>)BlockBed.OCCUPIED, (boolean)("".length() != 0)));
        this.setBedBounds();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockBed.FACING).getHorizontalIndex();
        if (blockState.getValue(BlockBed.PART) == EnumPartType.HEAD) {
            n |= (0x37 ^ 0x3F);
            if (blockState.getValue((IProperty<Boolean>)BlockBed.OCCUPIED)) {
                n |= (0x49 ^ 0x4D);
            }
        }
        return n;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final EnumFacing horizontal = EnumFacing.getHorizontal(n);
        IBlockState blockState;
        if ((n & (0x40 ^ 0x48)) > 0) {
            final IBlockState withProperty = this.getDefaultState().withProperty(BlockBed.PART, EnumPartType.HEAD).withProperty((IProperty<Comparable>)BlockBed.FACING, horizontal);
            final PropertyBool occupied = BlockBed.OCCUPIED;
            int n2;
            if ((n & (0x71 ^ 0x75)) > 0) {
                n2 = " ".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            blockState = withProperty.withProperty((IProperty<Comparable>)occupied, n2 != 0);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            blockState = this.getDefaultState().withProperty(BlockBed.PART, EnumPartType.FOOT).withProperty((IProperty<Comparable>)BlockBed.FACING, horizontal);
        }
        return blockState;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        Item bed;
        if (blockState.getValue(BlockBed.PART) == EnumPartType.HEAD) {
            bed = null;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            bed = Items.bed;
        }
        return bed;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBedBounds();
    }
    
    private static void I() {
        (I = new String[0x23 ^ 0x26])["".length()] = I("\u001a.4\u0016", "jOFbY");
        BlockBed.I[" ".length()] = I("\r$\u0017\u0018>\u000b\"\u0010", "bGtmN");
        BlockBed.I["  ".length()] = I("\u001e\u0011\u000f\u0015k\b\u001d\u0007^*\t\u001b\u0016\u0000,\u000f\u001c", "jxcpE");
        BlockBed.I["   ".length()] = I("!3<0X7?4{\u0018:\t<0\u0013%", "UZPUv");
        BlockBed.I[0x7A ^ 0x7E] = I("\u0007?<\u0011@\u001134Z\u0000\u001c\"\u0003\u0015\b\u0016", "sVPtn");
    }
    
    public enum EnumPartType implements IStringSerializable
    {
        HEAD(EnumPartType.I["".length()], "".length(), EnumPartType.I[" ".length()]);
        
        private static final EnumPartType[] ENUM$VALUES;
        private final String name;
        private static final String[] I;
        
        FOOT(EnumPartType.I["  ".length()], " ".length(), EnumPartType.I["   ".length()]);
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private EnumPartType(final String s, final int n, final String name) {
            this.name = name;
        }
        
        static {
            I();
            final EnumPartType[] enum$VALUES = new EnumPartType["  ".length()];
            enum$VALUES["".length()] = EnumPartType.HEAD;
            enum$VALUES[" ".length()] = EnumPartType.FOOT;
            ENUM$VALUES = enum$VALUES;
        }
        
        @Override
        public String getName() {
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
                if (2 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x4C ^ 0x48])["".length()] = I("&\u000e\u0002\u0011", "nKCUJ");
            EnumPartType.I[" ".length()] = I("/-5!", "GHTEm");
            EnumPartType.I["  ".length()] = I("\f>\t7", "JqFcE");
            EnumPartType.I["   ".length()] = I("'7\u0000\u001b", "AXoot");
        }
    }
}
