package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable
{
    private ItemStack Ý;
    private static final String Ø­áŒŠá = "CL_00001727";
    
    public EntityPotion(final World worldIn) {
        super(worldIn);
    }
    
    public EntityPotion(final World worldIn, final EntityLivingBase p_i1789_2_, final int p_i1789_3_) {
        this(worldIn, p_i1789_2_, new ItemStack(Items.µÂ, 1, p_i1789_3_));
    }
    
    public EntityPotion(final World worldIn, final EntityLivingBase p_i1790_2_, final ItemStack p_i1790_3_) {
        super(worldIn, p_i1790_2_);
        this.Ý = p_i1790_3_;
    }
    
    public EntityPotion(final World worldIn, final double p_i1791_2_, final double p_i1791_4_, final double p_i1791_6_, final int p_i1791_8_) {
        this(worldIn, p_i1791_2_, p_i1791_4_, p_i1791_6_, new ItemStack(Items.µÂ, 1, p_i1791_8_));
    }
    
    public EntityPotion(final World worldIn, final double p_i1792_2_, final double p_i1792_4_, final double p_i1792_6_, final ItemStack p_i1792_8_) {
        super(worldIn, p_i1792_2_, p_i1792_4_, p_i1792_6_);
        this.Ý = p_i1792_8_;
    }
    
    @Override
    protected float à() {
        return 0.05f;
    }
    
    @Override
    protected float Ø() {
        return 0.5f;
    }
    
    @Override
    protected float áŒŠÆ() {
        return -20.0f;
    }
    
    public void HorizonCode_Horizon_È(final int p_82340_1_) {
        if (this.Ý == null) {
            this.Ý = new ItemStack(Items.µÂ, 1, 0);
        }
        this.Ý.Â(p_82340_1_);
    }
    
    public int ˆÏ­() {
        if (this.Ý == null) {
            this.Ý = new ItemStack(Items.µÂ, 1, 0);
        }
        return this.Ý.Ø();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70184_1_) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            final List var2 = Items.µÂ.ÂµÈ(this.Ý);
            if (var2 != null && !var2.isEmpty()) {
                final AxisAlignedBB var3 = this.£É().Â(4.0, 2.0, 4.0);
                final List var4 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityLivingBase.class, var3);
                if (!var4.isEmpty()) {
                    for (final EntityLivingBase var6 : var4) {
                        final double var7 = this.Âµá€(var6);
                        if (var7 < 16.0) {
                            double var8 = 1.0 - Math.sqrt(var7) / 4.0;
                            if (var6 == p_70184_1_.Ø­áŒŠá) {
                                var8 = 1.0;
                            }
                            for (final PotionEffect var10 : var2) {
                                final int var11 = var10.HorizonCode_Horizon_È();
                                if (Potion.HorizonCode_Horizon_È[var11].Ý()) {
                                    Potion.HorizonCode_Horizon_È[var11].HorizonCode_Horizon_È(this, this.µà(), var6, var10.Ý(), var8);
                                }
                                else {
                                    final int var12 = (int)(var8 * var10.Â() + 0.5);
                                    if (var12 <= 20) {
                                        continue;
                                    }
                                    var6.HorizonCode_Horizon_È(new PotionEffect(var11, var12, var10.Ý()));
                                }
                            }
                        }
                    }
                }
            }
            this.Ï­Ðƒà.Â(2002, new BlockPos(this), this.ˆÏ­());
            this.á€();
        }
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("Potion", 10)) {
            this.Ý = ItemStack.HorizonCode_Horizon_È(tagCompund.ˆÏ­("Potion"));
        }
        else {
            this.HorizonCode_Horizon_È(tagCompund.Ó("potionValue"));
        }
        if (this.Ý == null) {
            this.á€();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        if (this.Ý != null) {
            tagCompound.HorizonCode_Horizon_È("Potion", this.Ý.Â(new NBTTagCompound()));
        }
    }
}
