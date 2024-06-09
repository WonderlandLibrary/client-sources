package HORIZON-6-0-SKIDPROTECTION;

public class BlockTNT extends Block
{
    public static final PropertyBool Õ;
    private static final String à¢ = "CL_00000324";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("explode");
    }
    
    public BlockTNT() {
        super(Material.µÕ);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockTNT.Õ, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ý(worldIn, pos, state);
        if (worldIn.áŒŠà(pos)) {
            this.Â(worldIn, pos, state.HorizonCode_Horizon_È(BlockTNT.Õ, true));
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (worldIn.áŒŠà(pos)) {
            this.Â(worldIn, pos, state.HorizonCode_Horizon_È(BlockTNT.Õ, true));
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Explosion explosionIn) {
        if (!worldIn.ŠÄ) {
            final EntityTNTPrimed var4 = new EntityTNTPrimed(worldIn, pos.HorizonCode_Horizon_È() + 0.5f, pos.Â() + 0.5f, pos.Ý() + 0.5f, explosionIn.Ý());
            var4.HorizonCode_Horizon_È = worldIn.Å.nextInt(var4.HorizonCode_Horizon_È / 4) + var4.HorizonCode_Horizon_È / 8;
            worldIn.HorizonCode_Horizon_È(var4);
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.HorizonCode_Horizon_È(worldIn, pos, state, (EntityLivingBase)null);
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180692_2_, final IBlockState p_180692_3_, final EntityLivingBase p_180692_4_) {
        if (!worldIn.ŠÄ && (boolean)p_180692_3_.HorizonCode_Horizon_È(BlockTNT.Õ)) {
            final EntityTNTPrimed var5 = new EntityTNTPrimed(worldIn, p_180692_2_.HorizonCode_Horizon_È() + 0.5f, p_180692_2_.Â() + 0.5f, p_180692_2_.Ý() + 0.5f, p_180692_4_);
            worldIn.HorizonCode_Horizon_È(var5);
            worldIn.HorizonCode_Horizon_È(var5, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (playerIn.áŒŠá() != null) {
            final Item_1028566121 var9 = playerIn.áŒŠá().HorizonCode_Horizon_È();
            if (var9 == Items.Ø­áŒŠá || var9 == Items.ÇŽØ) {
                this.HorizonCode_Horizon_È(worldIn, pos, state.HorizonCode_Horizon_È(BlockTNT.Õ, true), (EntityLivingBase)playerIn);
                worldIn.Ø(pos);
                if (var9 == Items.Ø­áŒŠá) {
                    playerIn.áŒŠá().HorizonCode_Horizon_È(1, playerIn);
                }
                else if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    final ItemStack áœŠá = playerIn.áŒŠá();
                    --áœŠá.Â;
                }
                return true;
            }
        }
        return super.HorizonCode_Horizon_È(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (!worldIn.ŠÄ && entityIn instanceof EntityArrow) {
            final EntityArrow var5 = (EntityArrow)entityIn;
            if (var5.ˆÏ()) {
                this.HorizonCode_Horizon_È(worldIn, pos, worldIn.Â(pos).HorizonCode_Horizon_È(BlockTNT.Õ, true), (var5.Ý instanceof EntityLivingBase) ? ((EntityLivingBase)var5.Ý) : null);
                worldIn.Ø(pos);
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final Explosion explosionIn) {
        return false;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockTNT.Õ, (meta & 0x1) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((boolean)state.HorizonCode_Horizon_È(BlockTNT.Õ)) ? 1 : 0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockTNT.Õ });
    }
}
