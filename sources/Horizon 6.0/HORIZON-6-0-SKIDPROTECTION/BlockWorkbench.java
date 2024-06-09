package HORIZON-6-0-SKIDPROTECTION;

public class BlockWorkbench extends Block
{
    private static final String Õ = "CL_00000221";
    
    protected BlockWorkbench() {
        super(Material.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        playerIn.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(worldIn, pos));
        return true;
    }
    
    public static class HorizonCode_Horizon_È implements IInteractionObject
    {
        private final World HorizonCode_Horizon_È;
        private final BlockPos Â;
        private static final String Ý = "CL_00002127";
        
        public HorizonCode_Horizon_È(final World worldIn, final BlockPos p_i45730_2_) {
            this.HorizonCode_Horizon_È = worldIn;
            this.Â = p_i45730_2_;
        }
        
        @Override
        public String v_() {
            return null;
        }
        
        @Override
        public boolean j_() {
            return false;
        }
        
        @Override
        public IChatComponent Ý() {
            return new ChatComponentTranslation(String.valueOf(Blocks.ˆÉ.Çªà¢()) + ".name", new Object[0]);
        }
        
        @Override
        public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
            return new ContainerWorkbench(playerInventory, this.HorizonCode_Horizon_È, this.Â);
        }
        
        @Override
        public String Ø­áŒŠá() {
            return "minecraft:crafting_table";
        }
    }
}
