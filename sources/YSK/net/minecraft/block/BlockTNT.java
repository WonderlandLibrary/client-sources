package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.state.*;

public class BlockTNT extends Block
{
    private static final String[] I;
    public static final PropertyBool EXPLODE;
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        super.onBlockAdded(world, blockToAir, blockState);
        if (world.isBlockPowered(blockToAir)) {
            this.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty((IProperty<Comparable>)BlockTNT.EXPLODE, (boolean)(" ".length() != 0)));
            world.setBlockToAir(blockToAir);
        }
    }
    
    public BlockTNT() {
        super(Material.tnt);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockTNT.EXPLODE, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabRedstone);
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
        if (!world.isRemote) {
            final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, explosion.getExplosivePlacedBy());
            entityTNTPrimed.fuse = world.rand.nextInt(entityTNTPrimed.fuse / (0x77 ^ 0x73)) + entityTNTPrimed.fuse / (0x2F ^ 0x27);
            world.spawnEntityInWorld(entityTNTPrimed);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool explode = BlockTNT.EXPLODE;
        int n2;
        if ((n & " ".length()) > 0) {
            n2 = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return defaultState.withProperty((IProperty<Comparable>)explode, n2 != 0);
    }
    
    static {
        I();
        EXPLODE = PropertyBool.create(BlockTNT.I["".length()]);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockToAir, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (entityPlayer.getCurrentEquippedItem() != null) {
            final Item item = entityPlayer.getCurrentEquippedItem().getItem();
            if (item == Items.flint_and_steel || item == Items.fire_charge) {
                this.explode(world, blockToAir, blockState.withProperty((IProperty<Comparable>)BlockTNT.EXPLODE, (boolean)(" ".length() != 0)), entityPlayer);
                world.setBlockToAir(blockToAir);
                if (item == Items.flint_and_steel) {
                    entityPlayer.getCurrentEquippedItem().damageItem(" ".length(), entityPlayer);
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
                else if (!entityPlayer.capabilities.isCreativeMode) {
                    final ItemStack currentEquippedItem = entityPlayer.getCurrentEquippedItem();
                    currentEquippedItem.stackSize -= " ".length();
                }
                return " ".length() != 0;
            }
        }
        return super.onBlockActivated(world, blockToAir, blockState, entityPlayer, enumFacing, n, n2, n3);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockToAir, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && entity instanceof EntityArrow) {
            final EntityArrow entityArrow = (EntityArrow)entity;
            if (entityArrow.isBurning()) {
                final IBlockState withProperty = world.getBlockState(blockToAir).withProperty((IProperty<Comparable>)BlockTNT.EXPLODE, " ".length() != 0);
                EntityLivingBase entityLivingBase;
                if (entityArrow.shootingEntity instanceof EntityLivingBase) {
                    entityLivingBase = (EntityLivingBase)entityArrow.shootingEntity;
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                }
                else {
                    entityLivingBase = null;
                }
                this.explode(world, blockToAir, withProperty, entityLivingBase);
                world.setBlockToAir(blockToAir);
            }
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n;
        if (blockState.getValue((IProperty<Boolean>)BlockTNT.EXPLODE)) {
            n = " ".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("6.\u00015%73", "SVqYJ");
        BlockTNT.I[" ".length()] = I("\u0010\u0015\b5V\u0003\u001a\u0011~\b\u0005\u001d\b5\u001c", "wtePx");
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.explode(world, blockPos, blockState, null);
    }
    
    @Override
    public boolean canDropFromExplosion(final Explosion explosion) {
        return "".length() != 0;
    }
    
    public void explode(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase) {
        if (!world.isRemote && blockState.getValue((IProperty<Boolean>)BlockTNT.EXPLODE)) {
            final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, blockPos.getX() + 0.5f, blockPos.getY(), blockPos.getZ() + 0.5f, entityLivingBase);
            world.spawnEntityInWorld(entityTNTPrimed);
            world.playSoundAtEntity(entityTNTPrimed, BlockTNT.I[" ".length()], 1.0f, 1.0f);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (world.isBlockPowered(blockToAir)) {
            this.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty((IProperty<Comparable>)BlockTNT.EXPLODE, (boolean)(" ".length() != 0)));
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockTNT.EXPLODE;
        return new BlockState(this, array);
    }
}
