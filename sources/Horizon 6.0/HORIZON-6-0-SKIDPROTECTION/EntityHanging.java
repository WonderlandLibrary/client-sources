package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.Validate;

public abstract class EntityHanging extends Entity
{
    private int Ý;
    protected BlockPos HorizonCode_Horizon_È;
    public EnumFacing Â;
    private static final String Ø­áŒŠá = "CL_00001546";
    
    public EntityHanging(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.5f, 0.5f);
    }
    
    public EntityHanging(final World worldIn, final BlockPos p_i45853_2_) {
        this(worldIn);
        this.HorizonCode_Horizon_È = p_i45853_2_;
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    protected void HorizonCode_Horizon_È(final EnumFacing p_174859_1_) {
        Validate.notNull((Object)p_174859_1_);
        Validate.isTrue(p_174859_1_.á().Ø­áŒŠá());
        this.Â = p_174859_1_;
        final float n = this.Â.Ý() * 90;
        this.É = n;
        this.á€ = n;
        this.µà();
    }
    
    private void µà() {
        if (this.Â != null) {
            double var1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È() + 0.5;
            double var2 = this.HorizonCode_Horizon_È.Â() + 0.5;
            double var3 = this.HorizonCode_Horizon_È.Ý() + 0.5;
            final double var4 = 0.46875;
            final double var5 = this.HorizonCode_Horizon_È(this.Ø());
            final double var6 = this.HorizonCode_Horizon_È(this.áŒŠÆ());
            var1 -= this.Â.Ø() * 0.46875;
            var3 -= this.Â.áˆºÑ¢Õ() * 0.46875;
            var2 += var6;
            final EnumFacing var7 = this.Â.à();
            var1 += var5 * var7.Ø();
            var3 += var5 * var7.áˆºÑ¢Õ();
            this.ŒÏ = var1;
            this.Çªà¢ = var2;
            this.Ê = var3;
            double var8 = this.Ø();
            double var9 = this.áŒŠÆ();
            double var10 = this.Ø();
            if (this.Â.á() == EnumFacing.HorizonCode_Horizon_È.Ý) {
                var10 = 1.0;
            }
            else {
                var8 = 1.0;
            }
            var8 /= 32.0;
            var9 /= 32.0;
            var10 /= 32.0;
            this.HorizonCode_Horizon_È(new AxisAlignedBB(var1 - var8, var2 - var9, var3 - var10, var1 + var8, var2 + var9, var3 + var10));
        }
    }
    
    private double HorizonCode_Horizon_È(final int p_174858_1_) {
        return (p_174858_1_ % 32 == 0) ? 0.5 : 0.0;
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        if (this.Ý++ == 100 && !this.Ï­Ðƒà.ŠÄ) {
            this.Ý = 0;
            if (!this.ˆáŠ && !this.à()) {
                this.á€();
                this.Â((Entity)null);
            }
        }
    }
    
    public boolean à() {
        if (!this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É()).isEmpty()) {
            return false;
        }
        final int var1 = Math.max(1, this.Ø() / 16);
        final int var2 = Math.max(1, this.áŒŠÆ() / 16);
        final BlockPos var3 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â.Âµá€());
        final EnumFacing var4 = this.Â.à();
        for (int var5 = 0; var5 < var1; ++var5) {
            for (int var6 = 0; var6 < var2; ++var6) {
                final BlockPos var7 = var3.HorizonCode_Horizon_È(var4, var5).Â(var6);
                final Block var8 = this.Ï­Ðƒà.Â(var7).Ý();
                if (!var8.Ó().Â() && !BlockRedstoneDiode.Ø­áŒŠá(var8)) {
                    return false;
                }
            }
        }
        final List var9 = this.Ï­Ðƒà.Â(this, this.£É());
        for (final Entity var11 : var9) {
            if (var11 instanceof EntityHanging) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean Ô() {
        return true;
    }
    
    @Override
    public boolean áŒŠÆ(final Entity entityIn) {
        return entityIn instanceof EntityPlayer && this.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È((EntityPlayer)entityIn), 0.0f);
    }
    
    @Override
    public EnumFacing ˆà¢() {
        return this.Â;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (!this.ˆáŠ && !this.Ï­Ðƒà.ŠÄ) {
            this.á€();
            this.Ï();
            this.Â(source.áˆºÑ¢Õ());
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double x, final double y, final double z) {
        if (!this.Ï­Ðƒà.ŠÄ && !this.ˆáŠ && x * x + y * y + z * z > 0.0) {
            this.á€();
            this.Â((Entity)null);
        }
    }
    
    @Override
    public void à(final double x, final double y, final double z) {
        if (!this.Ï­Ðƒà.ŠÄ && !this.ˆáŠ && x * x + y * y + z * z > 0.0) {
            this.á€();
            this.Â((Entity)null);
        }
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("Facing", (byte)this.Â.Ý());
        tagCompound.HorizonCode_Horizon_È("TileX", this.ˆÏ­().HorizonCode_Horizon_È());
        tagCompound.HorizonCode_Horizon_È("TileY", this.ˆÏ­().Â());
        tagCompound.HorizonCode_Horizon_È("TileZ", this.ˆÏ­().Ý());
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.HorizonCode_Horizon_È = new BlockPos(tagCompund.Ó("TileX"), tagCompund.Ó("TileY"), tagCompund.Ó("TileZ"));
        EnumFacing var2;
        if (tagCompund.Â("Direction", 99)) {
            var2 = EnumFacing.Â(tagCompund.Ø­áŒŠá("Direction"));
            this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var2);
        }
        else if (tagCompund.Â("Facing", 99)) {
            var2 = EnumFacing.Â(tagCompund.Ø­áŒŠá("Facing"));
        }
        else {
            var2 = EnumFacing.Â(tagCompund.Ø­áŒŠá("Dir"));
        }
        this.HorizonCode_Horizon_È(var2);
    }
    
    public abstract int Ø();
    
    public abstract int áŒŠÆ();
    
    public abstract void Â(final Entity p0);
    
    @Override
    protected boolean ÇªÓ() {
        return false;
    }
    
    @Override
    public void Ý(final double x, final double y, final double z) {
        this.ŒÏ = x;
        this.Çªà¢ = y;
        this.Ê = z;
        final BlockPos var7 = this.HorizonCode_Horizon_È;
        this.HorizonCode_Horizon_È = new BlockPos(x, y, z);
        if (!this.HorizonCode_Horizon_È.equals(var7)) {
            this.µà();
            this.áŒŠÏ = true;
        }
    }
    
    public BlockPos ˆÏ­() {
        return this.HorizonCode_Horizon_È;
    }
}
