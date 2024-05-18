package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;

public class BlockCauldron extends Block
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000213";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("level", 0, 3);
    }
    
    public BlockCauldron() {
        super(Material.Ó);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockCauldron.Õ, 0));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.3125f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        final float var7 = 0.125f;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, var7, 1.0f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var7);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(1.0f - var7, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f - var7, 1.0f, 1.0f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.ŠÄ();
    }
    
    @Override
    public void ŠÄ() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        final int var5 = (int)state.HorizonCode_Horizon_È(BlockCauldron.Õ);
        final float var6 = pos.Â() + (6.0f + 3 * var5) / 16.0f;
        if (!worldIn.ŠÄ && entityIn.ˆÏ() && var5 > 0 && entityIn.£É().Â <= var6) {
            entityIn.¥à();
            this.Â(worldIn, pos, state, var5 - 1);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final ItemStack var9 = playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var9 == null) {
            return true;
        }
        final int var10 = (int)state.HorizonCode_Horizon_È(BlockCauldron.Õ);
        final Item_1028566121 var11 = var9.HorizonCode_Horizon_È();
        if (var11 == Items.ˆÓ) {
            if (var10 < 3) {
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý, new ItemStack(Items.áŒŠáŠ));
                }
                this.Â(worldIn, pos, state, 3);
            }
            return true;
        }
        if (var11 == Items.Ñ¢ÇŽÏ) {
            if (var10 > 0) {
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    final ItemStack var12 = new ItemStack(Items.µÂ, 1, 0);
                    if (!playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var12)) {
                        worldIn.HorizonCode_Horizon_È(new EntityItem(worldIn, pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 1.5, pos.Ý() + 0.5, var12));
                    }
                    else if (playerIn instanceof EntityPlayerMP) {
                        ((EntityPlayerMP)playerIn).HorizonCode_Horizon_È(playerIn.ŒÂ);
                    }
                    final ItemStack itemStack = var9;
                    --itemStack.Â;
                    if (var9.Â <= 0) {
                        playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
                    }
                }
                this.Â(worldIn, pos, state, var10 - 1);
            }
            return true;
        }
        if (var10 > 0 && var11 instanceof ItemArmor) {
            final ItemArmor var13 = (ItemArmor)var11;
            if (var13.ˆà() == ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È && var13.ÂµÈ(var9)) {
                var13.ˆÏ­(var9);
                this.Â(worldIn, pos, state, var10 - 1);
                return true;
            }
        }
        if (var10 > 0 && var11 instanceof ItemBanner && TileEntityBanner.Ý(var9) > 0) {
            final ItemStack var12 = var9.áˆºÑ¢Õ();
            var12.Â = 1;
            TileEntityBanner.Ø­áŒŠá(var12);
            if (var9.Â <= 1 && !playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý, var12);
            }
            else {
                if (!playerIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(var12)) {
                    worldIn.HorizonCode_Horizon_È(new EntityItem(worldIn, pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 1.5, pos.Ý() + 0.5, var12));
                }
                else if (playerIn instanceof EntityPlayerMP) {
                    ((EntityPlayerMP)playerIn).HorizonCode_Horizon_È(playerIn.ŒÂ);
                }
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    final ItemStack itemStack2 = var9;
                    --itemStack2.Â;
                }
            }
            if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                this.Â(worldIn, pos, state, var10 - 1);
            }
            return true;
        }
        return false;
    }
    
    public void Â(final World worldIn, final BlockPos p_176590_2_, final IBlockState p_176590_3_, final int p_176590_4_) {
        worldIn.HorizonCode_Horizon_È(p_176590_2_, p_176590_3_.HorizonCode_Horizon_È(BlockCauldron.Õ, MathHelper.HorizonCode_Horizon_È(p_176590_4_, 0, 3)), 2);
        worldIn.Âµá€(p_176590_2_, this);
    }
    
    @Override
    public void à(final World worldIn, final BlockPos pos) {
        if (worldIn.Å.nextInt(20) == 1) {
            final IBlockState var3 = worldIn.Â(pos);
            if ((int)var3.HorizonCode_Horizon_È(BlockCauldron.Õ) < 3) {
                worldIn.HorizonCode_Horizon_È(pos, var3.Â(BlockCauldron.Õ), 2);
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.Ï­Ä;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.Ï­Ä;
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        return (int)worldIn.Â(pos).HorizonCode_Horizon_È(BlockCauldron.Õ);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockCauldron.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockCauldron.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockCauldron.Õ });
    }
}
