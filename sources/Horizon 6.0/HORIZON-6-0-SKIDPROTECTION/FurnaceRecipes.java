package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.Map;

public class FurnaceRecipes
{
    private static final FurnaceRecipes HorizonCode_Horizon_È;
    private Map Â;
    private Map Ý;
    private static final String Ø­áŒŠá = "CL_00000085";
    
    static {
        HorizonCode_Horizon_È = new FurnaceRecipes();
    }
    
    public static FurnaceRecipes HorizonCode_Horizon_È() {
        return FurnaceRecipes.HorizonCode_Horizon_È;
    }
    
    private FurnaceRecipes() {
        this.Â = Maps.newHashMap();
        this.Ý = Maps.newHashMap();
        this.HorizonCode_Horizon_È(Blocks.µà, new ItemStack(Items.áˆºÑ¢Õ), 0.7f);
        this.HorizonCode_Horizon_È(Blocks.£à, new ItemStack(Items.ÂµÈ), 1.0f);
        this.HorizonCode_Horizon_È(Blocks.£Ï, new ItemStack(Items.áŒŠÆ), 1.0f);
        this.HorizonCode_Horizon_È(Blocks.£á, new ItemStack(Blocks.Ï­Ðƒà), 0.1f);
        this.HorizonCode_Horizon_È(Items.£Â, new ItemStack(Items.£Ó), 0.35f);
        this.HorizonCode_Horizon_È(Items.Çª, new ItemStack(Items.ÇŽÄ), 0.35f);
        this.HorizonCode_Horizon_È(Items.ˆÈ, new ItemStack(Items.ˆÅ), 0.35f);
        this.HorizonCode_Horizon_È(Items.ÇŽà, new ItemStack(Items.ŠáˆºÂ), 0.35f);
        this.HorizonCode_Horizon_È(Items.ÇªÉ, new ItemStack(Items.ŠÏ­áˆºá), 0.35f);
        this.HorizonCode_Horizon_È(Blocks.Ó, new ItemStack(Blocks.Ý), 0.1f);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.£áƒ, 1, BlockStoneBrick.à¢), new ItemStack(Blocks.£áƒ, 1, BlockStoneBrick.¥à), 0.1f);
        this.HorizonCode_Horizon_È(Items.áŒŠá€, new ItemStack(Items.Šà), 0.3f);
        this.HorizonCode_Horizon_È(Blocks.£É, new ItemStack(Blocks.Ø­), 0.35f);
        this.HorizonCode_Horizon_È(Blocks.Ñ¢Ç, new ItemStack(Items.áŒŠÔ, 1, EnumDyeColor.£á.Ý()), 0.2f);
        this.HorizonCode_Horizon_È(Blocks.¥Æ, new ItemStack(Items.Ø, 1, 1), 0.15f);
        this.HorizonCode_Horizon_È(Blocks.Ø­à, new ItemStack(Items.Ø, 1, 1), 0.15f);
        this.HorizonCode_Horizon_È(Blocks.µÐƒÓ, new ItemStack(Items.µ), 1.0f);
        this.HorizonCode_Horizon_È(Items.ˆÂ, new ItemStack(Items.áŒŠÈ), 0.35f);
        this.HorizonCode_Horizon_È(Blocks.áŒŠÔ, new ItemStack(Items.ÇªÈ), 0.1f);
        this.HorizonCode_Horizon_È(new ItemStack(Blocks.Šáƒ, 1, 1), new ItemStack(Blocks.Šáƒ, 1, 0), 0.15f);
        for (final ItemFishFood.HorizonCode_Horizon_È var4 : ItemFishFood.HorizonCode_Horizon_È.values()) {
            if (var4.à()) {
                this.HorizonCode_Horizon_È(new ItemStack(Items.Ñ¢Ó, 1, var4.HorizonCode_Horizon_È()), new ItemStack(Items.Ø­Æ, 1, var4.HorizonCode_Horizon_È()), 0.35f);
            }
        }
        this.HorizonCode_Horizon_È(Blocks.ˆà, new ItemStack(Items.Ø), 0.1f);
        this.HorizonCode_Horizon_È(Blocks.Ñ¢à, new ItemStack(Items.ÇŽá), 0.7f);
        this.HorizonCode_Horizon_È(Blocks.áŒŠà, new ItemStack(Items.áŒŠÔ, 1, EnumDyeColor.á.Ý()), 0.2f);
        this.HorizonCode_Horizon_È(Blocks.ÐƒáˆºÄ, new ItemStack(Items.ÇªÅ), 0.2f);
    }
    
    public void HorizonCode_Horizon_È(final Block p_151393_1_, final ItemStack p_151393_2_, final float p_151393_3_) {
        this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(p_151393_1_), p_151393_2_, p_151393_3_);
    }
    
    public void HorizonCode_Horizon_È(final Item_1028566121 p_151396_1_, final ItemStack p_151396_2_, final float p_151396_3_) {
        this.HorizonCode_Horizon_È(new ItemStack(p_151396_1_, 1, 32767), p_151396_2_, p_151396_3_);
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_151394_1_, final ItemStack p_151394_2_, final float p_151394_3_) {
        this.Â.put(p_151394_1_, p_151394_2_);
        this.Ý.put(p_151394_2_, p_151394_3_);
    }
    
    public ItemStack HorizonCode_Horizon_È(final ItemStack p_151395_1_) {
        for (final Map.Entry var3 : this.Â.entrySet()) {
            if (this.HorizonCode_Horizon_È(p_151395_1_, var3.getKey())) {
                return var3.getValue();
            }
        }
        return null;
    }
    
    private boolean HorizonCode_Horizon_È(final ItemStack p_151397_1_, final ItemStack p_151397_2_) {
        return p_151397_2_.HorizonCode_Horizon_È() == p_151397_1_.HorizonCode_Horizon_È() && (p_151397_2_.Ø() == 32767 || p_151397_2_.Ø() == p_151397_1_.Ø());
    }
    
    public Map Â() {
        return this.Â;
    }
    
    public float Â(final ItemStack p_151398_1_) {
        for (final Map.Entry var3 : this.Ý.entrySet()) {
            if (this.HorizonCode_Horizon_È(p_151398_1_, var3.getKey())) {
                return var3.getValue();
            }
        }
        return 0.0f;
    }
}
