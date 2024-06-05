package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class BlockAnvil extends BlockFalling
{
    public static final PropertyDirection FACING;
    public static final PropertyInteger DAMAGE;
    
    protected BlockAnvil() {
        super(Material.anvil);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockAnvil.FACING, EnumFacing.NORTH).withProperty(BlockAnvil.DAMAGE, 0));
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final EnumFacing var9 = placer.func_174811_aO().rotateY();
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockAnvil.FACING, var9).withProperty(BlockAnvil.DAMAGE, meta >> 2);
    }
    
    @Override
    public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.displayGui(new Anvil(worldIn, pos));
        }
        return true;
    }
    
    @Override
    public int damageDropped(final IBlockState state) {
        return (int)state.getValue(BlockAnvil.DAMAGE);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
        final EnumFacing var3 = (EnumFacing)access.getBlockState(pos).getValue(BlockAnvil.FACING);
        if (var3.getAxis() == EnumFacing.Axis.X) {
            this.setBlockBounds(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
        }
        else {
            this.setBlockBounds(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
        list.add(new ItemStack(itemIn, 1, 2));
    }
    
    @Override
    protected void onStartFalling(final EntityFallingBlock fallingEntity) {
        fallingEntity.setHurtEntities(true);
    }
    
    @Override
    public void onEndFalling(final World worldIn, final BlockPos pos) {
        worldIn.playAuxSFX(1022, pos, 0);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState state) {
        return this.getDefaultState().withProperty(BlockAnvil.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockAnvil.FACING, EnumFacing.getHorizontal(meta & 0x3)).withProperty(BlockAnvil.DAMAGE, (meta & 0xF) >> 2);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.getValue(BlockAnvil.FACING)).getHorizontalIndex();
        var3 |= (int)state.getValue(BlockAnvil.DAMAGE) << 2;
        return var3;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockAnvil.FACING, BlockAnvil.DAMAGE });
    }
    
    static {
        FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        DAMAGE = PropertyInteger.create("damage", 0, 2);
    }
    
    public static class Anvil implements IInteractionObject
    {
        private final World world;
        private final BlockPos position;
        
        public Anvil(final World worldIn, final BlockPos pos) {
            this.world = worldIn;
            this.position = pos;
        }
        
        @Override
        public String getName() {
            return "anvil";
        }
        
        @Override
        public boolean hasCustomName() {
            return false;
        }
        
        @Override
        public IChatComponent getDisplayName() {
            return new ChatComponentTranslation(Blocks.anvil.getUnlocalizedName() + ".name", new Object[0]);
        }
        
        @Override
        public Container createContainer(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
            return new ContainerRepair(playerInventory, this.world, this.position, playerIn);
        }
        
        @Override
        public String getGuiID() {
            return "minecraft:anvil";
        }
    }
}
