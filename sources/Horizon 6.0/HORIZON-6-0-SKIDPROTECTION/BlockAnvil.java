package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;

public class BlockAnvil extends BlockFalling
{
    public static final PropertyDirection Õ;
    public static final PropertyInteger à¢;
    private static final String ¥à = "CL_00000192";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
        à¢ = PropertyInteger.HorizonCode_Horizon_È("damage", 0, 2);
    }
    
    protected BlockAnvil() {
        super(Material.à);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockAnvil.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockAnvil.à¢, 0));
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final EnumFacing var9 = placer.ˆà¢().Ó();
        return super.HorizonCode_Horizon_È(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).HorizonCode_Horizon_È(BlockAnvil.Õ, var9).HorizonCode_Horizon_È(BlockAnvil.à¢, meta >> 2);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!worldIn.ŠÄ) {
            playerIn.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(worldIn, pos));
        }
        return true;
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockAnvil.à¢);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final EnumFacing var3 = (EnumFacing)access.Â(pos).HorizonCode_Horizon_È(BlockAnvil.Õ);
        if (var3.á() == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.125f, 1.0f, 1.0f, 0.875f);
        }
        else {
            this.HorizonCode_Horizon_È(0.125f, 0.0f, 0.0f, 0.875f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
        list.add(new ItemStack(itemIn, 1, 2));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityFallingBlock fallingEntity) {
        fallingEntity.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void áŒŠÆ(final World worldIn, final BlockPos pos) {
        worldIn.Â(1022, pos, 0);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public IBlockState à(final IBlockState state) {
        return this.¥à().HorizonCode_Horizon_È(BlockAnvil.Õ, EnumFacing.Ø­áŒŠá);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockAnvil.Õ, EnumFacing.Â(meta & 0x3)).HorizonCode_Horizon_È(BlockAnvil.à¢, (meta & 0xF) >> 2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockAnvil.Õ)).Ý();
        var3 |= (int)state.HorizonCode_Horizon_È(BlockAnvil.à¢) << 2;
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockAnvil.Õ, BlockAnvil.à¢ });
    }
    
    public static class HorizonCode_Horizon_È implements IInteractionObject
    {
        private final World HorizonCode_Horizon_È;
        private final BlockPos Â;
        private static final String Ý = "CL_00002144";
        
        public HorizonCode_Horizon_È(final World worldIn, final BlockPos pos) {
            this.HorizonCode_Horizon_È = worldIn;
            this.Â = pos;
        }
        
        @Override
        public String v_() {
            return "anvil";
        }
        
        @Override
        public boolean j_() {
            return false;
        }
        
        @Override
        public IChatComponent Ý() {
            return new ChatComponentTranslation(String.valueOf(Blocks.ÇªÅ.Çªà¢()) + ".name", new Object[0]);
        }
        
        @Override
        public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
            return new ContainerRepair(playerInventory, this.HorizonCode_Horizon_È, this.Â, playerIn);
        }
        
        @Override
        public String Ø­áŒŠá() {
            return "minecraft:anvil";
        }
    }
}
