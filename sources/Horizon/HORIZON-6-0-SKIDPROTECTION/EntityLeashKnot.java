package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class EntityLeashKnot extends EntityHanging
{
    private static final String Ý = "CL_00001548";
    
    public EntityLeashKnot(final World worldIn) {
        super(worldIn);
    }
    
    public EntityLeashKnot(final World worldIn, final BlockPos p_i45851_2_) {
        super(worldIn, p_i45851_2_);
        this.Ý(p_i45851_2_.HorizonCode_Horizon_È() + 0.5, p_i45851_2_.Â() + 0.5, p_i45851_2_.Ý() + 0.5);
        final float var3 = 0.125f;
        final float var4 = 0.1875f;
        final float var5 = 0.25f;
        this.HorizonCode_Horizon_È(new AxisAlignedBB(this.ŒÏ - 0.1875, this.Çªà¢ - 0.25 + 0.125, this.Ê - 0.1875, this.ŒÏ + 0.1875, this.Çªà¢ + 0.25 + 0.125, this.Ê + 0.1875));
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
    }
    
    public void HorizonCode_Horizon_È(final EnumFacing p_174859_1_) {
    }
    
    @Override
    public int Ø() {
        return 9;
    }
    
    @Override
    public int áŒŠÆ() {
        return 9;
    }
    
    @Override
    public float Ðƒáƒ() {
        return -0.0625f;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final double distance) {
        return distance < 1024.0;
    }
    
    @Override
    public void Â(final Entity p_110128_1_) {
    }
    
    @Override
    public boolean Ø­áŒŠá(final NBTTagCompound tagCompund) {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        final ItemStack var2 = playerIn.Çª();
        boolean var3 = false;
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.áˆºÕ && !this.Ï­Ðƒà.ŠÄ) {
            final double var4 = 7.0;
            final List var5 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityLiving.class, new AxisAlignedBB(this.ŒÏ - var4, this.Çªà¢ - var4, this.Ê - var4, this.ŒÏ + var4, this.Çªà¢ + var4, this.Ê + var4));
            for (final EntityLiving var7 : var5) {
                if (var7.ÇŽà() && var7.ŠáˆºÂ() == playerIn) {
                    var7.HorizonCode_Horizon_È(this, true);
                    var3 = true;
                }
            }
        }
        if (!this.Ï­Ðƒà.ŠÄ && !var3) {
            this.á€();
            if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                final double var4 = 7.0;
                final List var5 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityLiving.class, new AxisAlignedBB(this.ŒÏ - var4, this.Çªà¢ - var4, this.Ê - var4, this.ŒÏ + var4, this.Çªà¢ + var4, this.Ê + var4));
                for (final EntityLiving var7 : var5) {
                    if (var7.ÇŽà() && var7.ŠáˆºÂ() == this) {
                        var7.HorizonCode_Horizon_È(true, false);
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean à() {
        return this.Ï­Ðƒà.Â(this.HorizonCode_Horizon_È).Ý() instanceof BlockFence;
    }
    
    public static EntityLeashKnot HorizonCode_Horizon_È(final World worldIn, final BlockPos p_174862_1_) {
        final EntityLeashKnot var2 = new EntityLeashKnot(worldIn, p_174862_1_);
        var2.Šáƒ = true;
        worldIn.HorizonCode_Horizon_È(var2);
        return var2;
    }
    
    public static EntityLeashKnot Â(final World worldIn, final BlockPos p_174863_1_) {
        final int var2 = p_174863_1_.HorizonCode_Horizon_È();
        final int var3 = p_174863_1_.Â();
        final int var4 = p_174863_1_.Ý();
        final List var5 = worldIn.HorizonCode_Horizon_È(EntityLeashKnot.class, new AxisAlignedBB(var2 - 1.0, var3 - 1.0, var4 - 1.0, var2 + 1.0, var3 + 1.0, var4 + 1.0));
        for (final EntityLeashKnot var7 : var5) {
            if (var7.ˆÏ­().equals(p_174863_1_)) {
                return var7;
            }
        }
        return null;
    }
}
