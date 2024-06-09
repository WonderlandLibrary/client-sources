package HORIZON-6-0-SKIDPROTECTION;

public interface IBehaviorDispenseItem
{
    public static final IBehaviorDispenseItem HorizonCode_Horizon_È = new IBehaviorDispenseItem() {
        private static final String Â = "CL_00001200";
        
        @Override
        public ItemStack HorizonCode_Horizon_È(final IBlockSource source, final ItemStack stack) {
            return stack;
        }
    };
    
    ItemStack HorizonCode_Horizon_È(final IBlockSource p0, final ItemStack p1);
}
