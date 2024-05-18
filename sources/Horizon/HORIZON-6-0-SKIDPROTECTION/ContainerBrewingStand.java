package HORIZON-6-0-SKIDPROTECTION;

public class ContainerBrewingStand extends Container
{
    private IInventory HorizonCode_Horizon_È;
    private final Slot Ó;
    private int à;
    private static final String Ø = "CL_00001737";
    
    public ContainerBrewingStand(final InventoryPlayer p_i45802_1_, final IInventory p_i45802_2_) {
        this.HorizonCode_Horizon_È = p_i45802_2_;
        this.Â(new Â(p_i45802_1_.Ø­áŒŠá, p_i45802_2_, 0, 56, 46));
        this.Â(new Â(p_i45802_1_.Ø­áŒŠá, p_i45802_2_, 1, 79, 53));
        this.Â(new Â(p_i45802_1_.Ø­áŒŠá, p_i45802_2_, 2, 102, 46));
        this.Ó = this.Â(new HorizonCode_Horizon_È(p_i45802_2_, 3, 79, 17));
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.Â(new Slot(p_i45802_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.Â(new Slot(p_i45802_1_, var3, 8 + var3 * 18, 142));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICrafting p_75132_1_) {
        super.HorizonCode_Horizon_È(p_75132_1_);
        p_75132_1_.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void Ý() {
        super.Ý();
        for (int var1 = 0; var1 < this.Âµá€.size(); ++var1) {
            final ICrafting var2 = this.Âµá€.get(var1);
            if (this.à != this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0)) {
                var2.HorizonCode_Horizon_È(this, 0, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0));
            }
        }
        this.à = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_75137_1_, final int p_75137_2_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_75137_1_, p_75137_2_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È.Ø­áŒŠá(playerIn);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if ((index < 0 || index > 2) && index != 3) {
                if (!this.Ó.Â() && this.Ó.HorizonCode_Horizon_È(var5)) {
                    if (!this.HorizonCode_Horizon_È(var5, 3, 4, false)) {
                        return null;
                    }
                }
                else if (Â.Ø­áŒŠá(var3)) {
                    if (!this.HorizonCode_Horizon_È(var5, 0, 3, false)) {
                        return null;
                    }
                }
                else if (index >= 4 && index < 31) {
                    if (!this.HorizonCode_Horizon_È(var5, 31, 40, false)) {
                        return null;
                    }
                }
                else if (index >= 31 && index < 40) {
                    if (!this.HorizonCode_Horizon_È(var5, 4, 31, false)) {
                        return null;
                    }
                }
                else if (!this.HorizonCode_Horizon_È(var5, 4, 40, false)) {
                    return null;
                }
            }
            else {
                if (!this.HorizonCode_Horizon_È(var5, 4, 40, true)) {
                    return null;
                }
                var4.HorizonCode_Horizon_È(var5, var3);
            }
            if (var5.Â == 0) {
                var4.Â(null);
            }
            else {
                var4.Ý();
            }
            if (var5.Â == var3.Â) {
                return null;
            }
            var4.HorizonCode_Horizon_È(playerIn, var5);
        }
        return var3;
    }
    
    class HorizonCode_Horizon_È extends Slot
    {
        private static final String Ó = "CL_00001738";
        
        public HorizonCode_Horizon_È(final IInventory p_i1803_2_, final int p_i1803_3_, final int p_i1803_4_, final int p_i1803_5_) {
            super(p_i1803_2_, p_i1803_3_, p_i1803_4_, p_i1803_5_);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final ItemStack stack) {
            return stack != null && stack.HorizonCode_Horizon_È().Ó(stack);
        }
        
        @Override
        public int Ø­áŒŠá() {
            return 64;
        }
    }
    
    static class Â extends Slot
    {
        private EntityPlayer HorizonCode_Horizon_È;
        private static final String Ó = "CL_00001740";
        
        public Â(final EntityPlayer p_i1804_1_, final IInventory p_i1804_2_, final int p_i1804_3_, final int p_i1804_4_, final int p_i1804_5_) {
            super(p_i1804_2_, p_i1804_3_, p_i1804_4_, p_i1804_5_);
            this.HorizonCode_Horizon_È = p_i1804_1_;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final ItemStack stack) {
            return Ø­áŒŠá(stack);
        }
        
        @Override
        public int Ø­áŒŠá() {
            return 1;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final ItemStack stack) {
            if (stack.HorizonCode_Horizon_È() == Items.µÂ && stack.Ø() > 0) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(AchievementList.ŒÏ);
            }
            super.HorizonCode_Horizon_È(playerIn, stack);
        }
        
        public static boolean Ø­áŒŠá(final ItemStack p_75243_0_) {
            return p_75243_0_ != null && (p_75243_0_.HorizonCode_Horizon_È() == Items.µÂ || p_75243_0_.HorizonCode_Horizon_È() == Items.Ñ¢ÇŽÏ);
        }
    }
}
