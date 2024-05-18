package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;

public abstract class BlockLiquid extends Block
{
    private static final String[] I;
    public static final PropertyInteger LEVEL;
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int combinedLight = blockAccess.getCombinedLight(blockPos, "".length());
        final int combinedLight2 = blockAccess.getCombinedLight(blockPos.up(), "".length());
        final int n = combinedLight & 112 + 83 + 38 + 22;
        final int n2 = combinedLight2 & 21 + 248 - 68 + 54;
        final int n3 = combinedLight >> (0xD0 ^ 0xC0) & 67 + 218 - 48 + 18;
        final int n4 = combinedLight2 >> (0x3F ^ 0x2F) & 119 + 63 - 58 + 131;
        int n5;
        if (n > n2) {
            n5 = n;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n5 = n2;
        }
        int n6;
        if (n3 > n4) {
            n6 = n3;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n6 = n4;
        }
        return n5 | n6 << (0x6D ^ 0x7D);
    }
    
    protected BlockLiquid(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockLiquid.LEVEL, "".length()));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.blockMaterial != Material.lava) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockLiquid.LEVEL;
        return new BlockState(this, array);
    }
    
    public static BlockStaticLiquid getStaticBlock(final Material material) {
        if (material == Material.water) {
            return Blocks.water;
        }
        if (material == Material.lava) {
            return Blocks.lava;
        }
        throw new IllegalArgumentException(BlockLiquid.I[0x66 ^ 0x60]);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        int n;
        if (blockAccess.getBlockState(blockPos).getBlock().getMaterial() == this.blockMaterial) {
            n = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.UP) {
            n = " ".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            n = (super.shouldSideBeRendered(blockAccess, blockPos, enumFacing) ? 1 : 0);
        }
        return n != 0;
    }
    
    protected void triggerMixEffects(final World world, final BlockPos blockPos) {
        final double n = blockPos.getX();
        final double n2 = blockPos.getY();
        final double n3 = blockPos.getZ();
        world.playSoundEffect(n + 0.5, n2 + 0.5, n3 + 0.5, BlockLiquid.I[0x47 ^ 0x43], 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
        int i = "".length();
        "".length();
        if (!true) {
            throw null;
        }
        while (i < (0x5B ^ 0x53)) {
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, n + Math.random(), n2 + 1.2, n3 + Math.random(), 0.0, 0.0, 0.0, new int["".length()]);
            ++i;
        }
    }
    
    protected int getLevel(final IBlockAccess blockAccess, final BlockPos blockPos) {
        int intValue;
        if (blockAccess.getBlockState(blockPos).getBlock().getMaterial() == this.blockMaterial) {
            intValue = blockAccess.getBlockState(blockPos).getValue((IProperty<Integer>)BlockLiquid.LEVEL);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            intValue = -" ".length();
        }
        return intValue;
    }
    
    public static double getFlowDirection(final IBlockAccess blockAccess, final BlockPos blockPos, final Material material) {
        final Vec3 flowVector = getFlowingBlock(material).getFlowVector(blockAccess, blockPos);
        double n;
        if (flowVector.xCoord == 0.0 && flowVector.zCoord == 0.0) {
            n = -1000.0;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            n = MathHelper.func_181159_b(flowVector.zCoord, flowVector.xCoord) - 1.5707963267948966;
        }
        return n;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public int tickRate(final World world) {
        int length;
        if (this.blockMaterial == Material.water) {
            length = (0x65 ^ 0x60);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (this.blockMaterial == Material.lava) {
            if (world.provider.getHasNoSky()) {
                length = (0x85 ^ 0x8F);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                length = (0x74 ^ 0x6A);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    private static void I() {
        (I = new String[0x5 ^ 0x2])["".length()] = I("\u0003\u00130.\u0000", "ovFKl");
        BlockLiquid.I[" ".length()] = I("5\u0007\u0000\u001c\u0001=@\u0006\b\u001c<\u001c", "Ynqih");
        BlockLiquid.I["  ".length()] = I("/&+0\u001f'a6$\u0000\"?55", "COZEv");
        BlockLiquid.I["   ".length()] = I("%\u00115\u00140-V(\u0000/(", "IxDaY");
        BlockLiquid.I[0xA8 ^ 0xAC] = I("\u0015\u0012'\u001c\u001f\n]/\u0011\n\u001d", "gsIxp");
        BlockLiquid.I[0x59 ^ 0x5C] = I("0\u0000\u000f2*\u0010\nY>'\r\u000b\u000b:'\u0015", "ynySF");
        BlockLiquid.I[0x24 ^ 0x22] = I("\u001c\u001d$0 <\u0017r<-!\u0016 8-9", "UsRQL");
    }
    
    protected int getEffectiveFlowDecay(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int level = this.getLevel(blockAccess, blockPos);
        int length;
        if (level >= (0x46 ^ 0x4E)) {
            length = "".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            length = level;
        }
        return length;
    }
    
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        if (b && blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL) == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean checkForMixing(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.blockMaterial == Material.lava) {
            int n = "".length();
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (false == true) {
                throw null;
            }
            while (i < length) {
                final EnumFacing enumFacing = values[i];
                if (enumFacing != EnumFacing.DOWN && world.getBlockState(blockPos.offset(enumFacing)).getBlock().getMaterial() == Material.water) {
                    n = " ".length();
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
            if (n != 0) {
                final Integer n2 = blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL);
                if (n2 == 0) {
                    world.setBlockState(blockPos, Blocks.obsidian.getDefaultState());
                    this.triggerMixEffects(world, blockPos);
                    return " ".length() != 0;
                }
                if (n2 <= (0x5C ^ 0x58)) {
                    world.setBlockState(blockPos, Blocks.cobblestone.getDefaultState());
                    this.triggerMixEffects(world, blockPos);
                    return " ".length() != 0;
                }
            }
        }
        return "".length() != 0;
    }
    
    public boolean func_176364_g(final IBlockAccess blockAccess, final BlockPos blockPos) {
        int i = -" ".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i <= " ".length()) {
            int j = -" ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (j <= " ".length()) {
                final Block block = blockAccess.getBlockState(blockPos.add(i, "".length(), j)).getBlock();
                if (block.getMaterial() != this.blockMaterial && !block.isFullBlock()) {
                    return " ".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getRenderType() {
        return " ".length();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockLiquid.LEVEL, n);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        EnumWorldBlockLayer enumWorldBlockLayer;
        if (this.blockMaterial == Material.water) {
            enumWorldBlockLayer = EnumWorldBlockLayer.TRANSLUCENT;
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            enumWorldBlockLayer = EnumWorldBlockLayer.SOLID;
        }
        return enumWorldBlockLayer;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.checkForMixing(world, blockPos, blockState);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final double n = blockPos.getX();
        final double n2 = blockPos.getY();
        final double n3 = blockPos.getZ();
        if (this.blockMaterial == Material.water) {
            final int intValue = blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL);
            if (intValue > 0 && intValue < (0x31 ^ 0x39)) {
                if (random.nextInt(0x75 ^ 0x35) == 0) {
                    world.playSound(n + 0.5, n2 + 0.5, n3 + 0.5, BlockLiquid.I[" ".length()], random.nextFloat() * 0.25f + 0.75f, random.nextFloat() * 1.0f + 0.5f, "".length() != 0);
                    "".length();
                    if (2 == -1) {
                        throw null;
                    }
                }
            }
            else if (random.nextInt(0x9D ^ 0x97) == 0) {
                world.spawnParticle(EnumParticleTypes.SUSPENDED, n + random.nextFloat(), n2 + random.nextFloat(), n3 + random.nextFloat(), 0.0, 0.0, 0.0, new int["".length()]);
            }
        }
        if (this.blockMaterial == Material.lava && world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air && !world.getBlockState(blockPos.up()).getBlock().isOpaqueCube()) {
            if (random.nextInt(0xF3 ^ 0x97) == 0) {
                final double n4 = n + random.nextFloat();
                final double n5 = n2 + this.maxY;
                final double n6 = n3 + random.nextFloat();
                world.spawnParticle(EnumParticleTypes.LAVA, n4, n5, n6, 0.0, 0.0, 0.0, new int["".length()]);
                world.playSound(n4, n5, n6, BlockLiquid.I["  ".length()], 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, "".length() != 0);
            }
            if (random.nextInt(137 + 48 - 131 + 146) == 0) {
                world.playSound(n, n2, n3, BlockLiquid.I["   ".length()], 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, "".length() != 0);
            }
        }
        if (random.nextInt(0x4C ^ 0x46) == 0 && World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            final Material material = world.getBlockState(blockPos.down("  ".length())).getBlock().getMaterial();
            if (!material.blocksMovement() && !material.isLiquid()) {
                final double n7 = n + random.nextFloat();
                final double n8 = n2 - 1.05;
                final double n9 = n3 + random.nextFloat();
                if (this.blockMaterial == Material.water) {
                    world.spawnParticle(EnumParticleTypes.DRIP_WATER, n7, n8, n9, 0.0, 0.0, 0.0, new int["".length()]);
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
                else {
                    world.spawnParticle(EnumParticleTypes.DRIP_LAVA, n7, n8, n9, 0.0, 0.0, 0.0, new int["".length()]);
                }
            }
        }
    }
    
    @Override
    public boolean isBlockSolid(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final Material material = blockAccess.getBlockState(blockPos).getBlock().getMaterial();
        int n;
        if (material == this.blockMaterial) {
            n = "".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.UP) {
            n = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (material == Material.ice) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = (super.isBlockSolid(blockAccess, blockPos, enumFacing) ? 1 : 0);
        }
        return n != 0;
    }
    
    protected Vec3 getFlowVector(final IBlockAccess blockAccess, final BlockPos blockPos) {
        Vec3 vec3 = new Vec3(0.0, 0.0, 0.0);
        final int effectiveFlowDecay = this.getEffectiveFlowDecay(blockAccess, blockPos);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos offset = blockPos.offset(iterator.next());
            final int effectiveFlowDecay2 = this.getEffectiveFlowDecay(blockAccess, offset);
            if (effectiveFlowDecay2 < 0) {
                if (blockAccess.getBlockState(offset).getBlock().getMaterial().blocksMovement()) {
                    continue;
                }
                final int effectiveFlowDecay3 = this.getEffectiveFlowDecay(blockAccess, offset.down());
                if (effectiveFlowDecay3 < 0) {
                    continue;
                }
                final int n = effectiveFlowDecay3 - (effectiveFlowDecay - (0x3D ^ 0x35));
                vec3 = vec3.addVector((offset.getX() - blockPos.getX()) * n, (offset.getY() - blockPos.getY()) * n, (offset.getZ() - blockPos.getZ()) * n);
                "".length();
                if (3 < -1) {
                    throw null;
                }
                continue;
            }
            else {
                if (effectiveFlowDecay2 < 0) {
                    continue;
                }
                final int n2 = effectiveFlowDecay2 - effectiveFlowDecay;
                vec3 = vec3.addVector((offset.getX() - blockPos.getX()) * n2, (offset.getY() - blockPos.getY()) * n2, (offset.getZ() - blockPos.getZ()) * n2);
            }
        }
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<Integer>)BlockLiquid.LEVEL) >= (0x6D ^ 0x65)) {
            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final EnumFacing enumFacing = iterator2.next();
                final BlockPos offset2 = blockPos.offset(enumFacing);
                if (this.isBlockSolid(blockAccess, offset2, enumFacing) || this.isBlockSolid(blockAccess, offset2.up(), enumFacing)) {
                    vec3 = vec3.normalize().addVector(0.0, -6.0, 0.0);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
            }
        }
        return vec3.normalize();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    public static float getLiquidHeightPercent(int length) {
        if (length >= (0x4B ^ 0x43)) {
            length = "".length();
        }
        return (length + " ".length()) / 9.0f;
    }
    
    static {
        I();
        LEVEL = PropertyInteger.create(BlockLiquid.I["".length()], "".length(), 0x71 ^ 0x7E);
    }
    
    public static BlockDynamicLiquid getFlowingBlock(final Material material) {
        if (material == Material.water) {
            return Blocks.flowing_water;
        }
        if (material == Material.lava) {
            return Blocks.flowing_lava;
        }
        throw new IllegalArgumentException(BlockLiquid.I[0x59 ^ 0x5C]);
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkForMixing(world, blockPos, blockState);
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        int waterColorAtPos;
        if (this.blockMaterial == Material.water) {
            waterColorAtPos = BiomeColorHelper.getWaterColorAtPos(blockAccess, blockPos);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            waterColorAtPos = 8776973 + 2893651 - 4556517 + 9663108;
        }
        return waterColorAtPos;
    }
    
    @Override
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return vec3.add(this.getFlowVector(world, blockPos));
    }
}
