package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension
  extends Block
{
  public static final PropertyDirection field_176326_a = PropertyDirection.create("facing");
  public static final PropertyEnum field_176325_b = PropertyEnum.create("type", EnumPistonType.class);
  public static final PropertyBool field_176327_M = PropertyBool.create("short");
  private static final String __OBFID = "CL_00000367";
  
  public BlockPistonExtension()
  {
    super(Material.piston);
    setDefaultState(this.blockState.getBaseState().withProperty(field_176326_a, EnumFacing.NORTH).withProperty(field_176325_b, EnumPistonType.DEFAULT).withProperty(field_176327_M, Boolean.valueOf(false)));
    setStepSound(soundTypePiston);
    setHardness(0.5F);
  }
  
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn)
  {
    if (playerIn.capabilities.isCreativeMode)
    {
      EnumFacing var5 = (EnumFacing)state.getValue(field_176326_a);
      if (var5 != null)
      {
        BlockPos var6 = pos.offset(var5.getOpposite());
        Block var7 = worldIn.getBlockState(var6).getBlock();
        if ((var7 == Blocks.piston) || (var7 == Blocks.sticky_piston)) {
          worldIn.setBlockToAir(var6);
        }
      }
    }
    super.onBlockHarvested(worldIn, pos, state, playerIn);
  }
  
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
  {
    super.breakBlock(worldIn, pos, state);
    EnumFacing var4 = ((EnumFacing)state.getValue(field_176326_a)).getOpposite();
    pos = pos.offset(var4);
    IBlockState var5 = worldIn.getBlockState(pos);
    if (((var5.getBlock() == Blocks.piston) || (var5.getBlock() == Blocks.sticky_piston)) && (((Boolean)var5.getValue(BlockPistonBase.EXTENDED)).booleanValue()))
    {
      var5.getBlock().dropBlockAsItem(worldIn, pos, var5, 0);
      worldIn.setBlockToAir(pos);
    }
  }
  
  public boolean isOpaqueCube()
  {
    return false;
  }
  
  public boolean isFullCube()
  {
    return false;
  }
  
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
  {
    return false;
  }
  
  public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
  {
    return false;
  }
  
  public int quantityDropped(Random random)
  {
    return 0;
  }
  
  public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
  {
    func_176324_d(state);
    super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    func_176323_e(state);
    super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
  }
  
  private void func_176323_e(IBlockState p_176323_1_)
  {
    float var2 = 0.25F;
    float var3 = 0.375F;
    float var4 = 0.625F;
    float var5 = 0.25F;
    float var6 = 0.75F;
    switch (SwitchEnumFacing.field_177247_a[((EnumFacing)p_176323_1_.getValue(field_176326_a)).ordinal()])
    {
    case 1: 
      setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
      break;
    case 2: 
      setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
      break;
    case 3: 
      setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
      break;
    case 4: 
      setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
      break;
    case 5: 
      setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
      break;
    case 6: 
      setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
    }
  }
  
  public void setBlockBoundsBasedOnState(IBlockAccess access, BlockPos pos)
  {
    func_176324_d(access.getBlockState(pos));
  }
  
  public void func_176324_d(IBlockState p_176324_1_)
  {
    float var2 = 0.25F;
    EnumFacing var3 = (EnumFacing)p_176324_1_.getValue(field_176326_a);
    if (var3 != null) {
      switch (SwitchEnumFacing.field_177247_a[var3.ordinal()])
      {
      case 1: 
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
        break;
      case 2: 
        setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
        break;
      case 3: 
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
        break;
      case 4: 
        setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
        break;
      case 5: 
        setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
        break;
      case 6: 
        setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }
    }
  }
  
  public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
  {
    EnumFacing var5 = (EnumFacing)state.getValue(field_176326_a);
    BlockPos var6 = pos.offset(var5.getOpposite());
    IBlockState var7 = worldIn.getBlockState(var6);
    if ((var7.getBlock() != Blocks.piston) && (var7.getBlock() != Blocks.sticky_piston)) {
      worldIn.setBlockToAir(pos);
    } else {
      var7.getBlock().onNeighborBlockChange(worldIn, var6, var7, neighborBlock);
    }
  }
  
  public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
  {
    return true;
  }
  
  public static EnumFacing func_176322_b(int p_176322_0_)
  {
    int var1 = p_176322_0_ & 0x7;
    return var1 > 5 ? null : EnumFacing.getFront(var1);
  }
  
  public Item getItem(World worldIn, BlockPos pos)
  {
    return worldIn.getBlockState(pos).getValue(field_176325_b) == EnumPistonType.STICKY ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
  }
  
  public IBlockState getStateFromMeta(int meta)
  {
    return getDefaultState().withProperty(field_176326_a, func_176322_b(meta)).withProperty(field_176325_b, (meta & 0x8) > 0 ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
  }
  
  public int getMetaFromState(IBlockState state)
  {
    byte var2 = 0;
    int var3 = var2 | ((EnumFacing)state.getValue(field_176326_a)).getIndex();
    if (state.getValue(field_176325_b) == EnumPistonType.STICKY) {
      var3 |= 0x8;
    }
    return var3;
  }
  
  protected BlockState createBlockState()
  {
    return new BlockState(this, new IProperty[] { field_176326_a, field_176325_b, field_176327_M });
  }
  
  public static enum EnumPistonType
    implements IStringSerializable
  {
    private final String field_176714_c;
    private static final EnumPistonType[] $VALUES = { DEFAULT, STICKY };
    private static final String __OBFID = "CL_00002035";
    
    private EnumPistonType(String p_i45666_1_, int p_i45666_2_, String p_i45666_3_)
    {
      this.field_176714_c = p_i45666_3_;
    }
    
    public String toString()
    {
      return this.field_176714_c;
    }
    
    public String getName()
    {
      return this.field_176714_c;
    }
  }
  
  static final class SwitchEnumFacing
  {
    static final int[] field_177247_a = new int[EnumFacing.values().length];
    private static final String __OBFID = "CL_00002036";
    
    SwitchEnumFacing() {}
    
    static
    {
      try
      {
        field_177247_a[EnumFacing.DOWN.ordinal()] = 1;
      }
      catch (NoSuchFieldError localNoSuchFieldError1) {}
      try
      {
        field_177247_a[EnumFacing.UP.ordinal()] = 2;
      }
      catch (NoSuchFieldError localNoSuchFieldError2) {}
      try
      {
        field_177247_a[EnumFacing.NORTH.ordinal()] = 3;
      }
      catch (NoSuchFieldError localNoSuchFieldError3) {}
      try
      {
        field_177247_a[EnumFacing.SOUTH.ordinal()] = 4;
      }
      catch (NoSuchFieldError localNoSuchFieldError4) {}
      try
      {
        field_177247_a[EnumFacing.WEST.ordinal()] = 5;
      }
      catch (NoSuchFieldError localNoSuchFieldError5) {}
      try
      {
        field_177247_a[EnumFacing.EAST.ordinal()] = 6;
      }
      catch (NoSuchFieldError localNoSuchFieldError6) {}
    }
  }
}
