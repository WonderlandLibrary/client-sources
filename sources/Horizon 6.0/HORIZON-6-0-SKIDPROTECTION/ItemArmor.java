package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class ItemArmor extends Item_1028566121
{
    private static final int[] ÂµÈ;
    public static final String[] à;
    private static final IBehaviorDispenseItem á;
    public final int Ø;
    public final int áŒŠÆ;
    public final int áˆºÑ¢Õ;
    private final HorizonCode_Horizon_È ˆÏ­;
    private static final String £á = "CL_00001766";
    
    static {
        ÂµÈ = new int[] { 11, 16, 15, 13 };
        à = new String[] { "minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots" };
        á = new BehaviorDefaultDispenseItem() {
            private static final String Â = "CL_00001767";
            
            @Override
            protected ItemStack Â(final IBlockSource source, final ItemStack stack) {
                final BlockPos var3 = source.Âµá€().HorizonCode_Horizon_È(BlockDispenser.Âµá€(source.à()));
                final int var4 = var3.HorizonCode_Horizon_È();
                final int var5 = var3.Â();
                final int var6 = var3.Ý();
                final AxisAlignedBB var7 = new AxisAlignedBB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
                final List var8 = source.HorizonCode_Horizon_È().HorizonCode_Horizon_È(EntityLivingBase.class, var7, Predicates.and(IEntitySelector.Ø­áŒŠá, (Predicate)new IEntitySelector.HorizonCode_Horizon_È(stack)));
                if (var8.size() > 0) {
                    final EntityLivingBase var9 = var8.get(0);
                    final int var10 = (var9 instanceof EntityPlayer) ? 1 : 0;
                    final int var11 = EntityLiving.Â(stack);
                    final ItemStack var12 = stack.áˆºÑ¢Õ();
                    var12.Â = 1;
                    var9.HorizonCode_Horizon_È(var11 - var10, var12);
                    if (var9 instanceof EntityLiving) {
                        ((EntityLiving)var9).HorizonCode_Horizon_È(var11, 2.0f);
                    }
                    --stack.Â;
                    return stack;
                }
                return super.Â(source, stack);
            }
        };
    }
    
    public ItemArmor(final HorizonCode_Horizon_È p_i45325_1_, final int p_i45325_2_, final int p_i45325_3_) {
        this.ˆÏ­ = p_i45325_1_;
        this.Ø = p_i45325_3_;
        this.áˆºÑ¢Õ = p_i45325_2_;
        this.áŒŠÆ = p_i45325_1_.Â(p_i45325_3_);
        this.Ø­áŒŠá(p_i45325_1_.HorizonCode_Horizon_È(p_i45325_3_));
        this.Ø­áŒŠá = 1;
        this.HorizonCode_Horizon_È(CreativeTabs.áˆºÑ¢Õ);
        BlockDispenser.ŠÂµà.HorizonCode_Horizon_È(this, ItemArmor.á);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        if (renderPass > 0) {
            return 16777215;
        }
        int var3 = this.á(stack);
        if (var3 < 0) {
            var3 = 16777215;
        }
        return var3;
    }
    
    @Override
    public int ˆÏ­() {
        return this.ˆÏ­.HorizonCode_Horizon_È();
    }
    
    public HorizonCode_Horizon_È ˆà() {
        return this.ˆÏ­;
    }
    
    public boolean ÂµÈ(final ItemStack p_82816_1_) {
        return this.ˆÏ­ == HorizonCode_Horizon_È.HorizonCode_Horizon_È && p_82816_1_.£á() && p_82816_1_.Å().Â("display", 10) && p_82816_1_.Å().ˆÏ­("display").Â("color", 3);
    }
    
    public int á(final ItemStack p_82814_1_) {
        if (this.ˆÏ­ != HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            return -1;
        }
        final NBTTagCompound var2 = p_82814_1_.Å();
        if (var2 != null) {
            final NBTTagCompound var3 = var2.ˆÏ­("display");
            if (var3 != null && var3.Â("color", 3)) {
                return var3.Ó("color");
            }
        }
        return 10511680;
    }
    
    public void ˆÏ­(final ItemStack p_82815_1_) {
        if (this.ˆÏ­ == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final NBTTagCompound var2 = p_82815_1_.Å();
            if (var2 != null) {
                final NBTTagCompound var3 = var2.ˆÏ­("display");
                if (var3.Ý("color")) {
                    var3.Å("color");
                }
            }
        }
    }
    
    public void Â(final ItemStack p_82813_1_, final int p_82813_2_) {
        if (this.ˆÏ­ != HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        NBTTagCompound var3 = p_82813_1_.Å();
        if (var3 == null) {
            var3 = new NBTTagCompound();
            p_82813_1_.Ø­áŒŠá(var3);
        }
        final NBTTagCompound var4 = var3.ˆÏ­("display");
        if (!var3.Â("display", 10)) {
            var3.HorizonCode_Horizon_È("display", var4);
        }
        var4.HorizonCode_Horizon_È("color", p_82813_2_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack toRepair, final ItemStack repair) {
        return this.ˆÏ­.Â() == repair.HorizonCode_Horizon_È() || super.HorizonCode_Horizon_È(toRepair, repair);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final int var4 = EntityLiving.Â(itemStackIn) - 1;
        final ItemStack var5 = playerIn.ÂµÈ(var4);
        if (var5 == null) {
            playerIn.HorizonCode_Horizon_È(var4, itemStackIn.áˆºÑ¢Õ());
            itemStackIn.Â = 0;
        }
        return itemStackIn;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("LEATHER", 0, "LEATHER", 0, "leather", 5, new int[] { 1, 3, 2, 1 }, 15), 
        Â("CHAIN", 1, "CHAIN", 1, "chainmail", 15, new int[] { 2, 5, 4, 1 }, 12), 
        Ý("IRON", 2, "IRON", 2, "iron", 15, new int[] { 2, 6, 5, 2 }, 9), 
        Ø­áŒŠá("GOLD", 3, "GOLD", 3, "gold", 7, new int[] { 2, 5, 3, 1 }, 25), 
        Âµá€("DIAMOND", 4, "DIAMOND", 4, "diamond", 33, new int[] { 3, 8, 6, 3 }, 10);
        
        private final String Ó;
        private final int à;
        private final int[] Ø;
        private final int áŒŠÆ;
        private static final HorizonCode_Horizon_È[] áˆºÑ¢Õ;
        private static final String ÂµÈ = "CL_00001768";
        
        static {
            á = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
            áˆºÑ¢Õ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45789_1_, final int p_i45789_2_, final String p_i45789_3_, final int p_i45789_4_, final int[] p_i45789_5_, final int p_i45789_6_) {
            this.Ó = p_i45789_3_;
            this.à = p_i45789_4_;
            this.Ø = p_i45789_5_;
            this.áŒŠÆ = p_i45789_6_;
        }
        
        public int HorizonCode_Horizon_È(final int p_78046_1_) {
            return ItemArmor.ÂµÈ[p_78046_1_] * this.à;
        }
        
        public int Â(final int p_78044_1_) {
            return this.Ø[p_78044_1_];
        }
        
        public int HorizonCode_Horizon_È() {
            return this.áŒŠÆ;
        }
        
        public Item_1028566121 Â() {
            return (this == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? Items.£áŒŠá : ((this == HorizonCode_Horizon_È.Â) ? Items.áˆºÑ¢Õ : ((this == HorizonCode_Horizon_È.Ø­áŒŠá) ? Items.ÂµÈ : ((this == HorizonCode_Horizon_È.Ý) ? Items.áˆºÑ¢Õ : ((this == HorizonCode_Horizon_È.Âµá€) ? Items.áŒŠÆ : null))));
        }
        
        public String Ý() {
            return this.Ó;
        }
    }
}
