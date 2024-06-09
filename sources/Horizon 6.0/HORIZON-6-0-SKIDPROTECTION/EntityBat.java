package HORIZON-6-0-SKIDPROTECTION;

import java.util.Calendar;

public class EntityBat extends EntityAmbientCreature
{
    private BlockPos HorizonCode_Horizon_È;
    private static final String Â = "CL_00001637";
    
    public EntityBat(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.5f, 0.9f);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, new Byte((byte)0));
    }
    
    @Override
    protected float ˆÂ() {
        return 0.1f;
    }
    
    @Override
    protected float áŒŠÈ() {
        return super.áŒŠÈ() * 0.95f;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return (this.Ø() && this.ˆáƒ.nextInt(4) != 0) ? null : "mob.bat.idle";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.bat.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.bat.death";
    }
    
    @Override
    public boolean £à() {
        return false;
    }
    
    @Override
    protected void £à(final Entity p_82167_1_) {
    }
    
    @Override
    protected void ŠáŒŠà¢() {
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(6.0);
    }
    
    public boolean Ø() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x1) != 0x0;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_82236_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_82236_1_) {
            this.£Ó.Â(16, (byte)(var2 | 0x1));
        }
        else {
            this.£Ó.Â(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    @Override
    public void á() {
        super.á();
        if (this.Ø()) {
            final double çžé = 0.0;
            this.ÇŽÕ = çžé;
            this.ˆá = çžé;
            this.ÇŽÉ = çžé;
            this.Çªà¢ = MathHelper.Ý(this.Çªà¢) + 1.0 - this.£ÂµÄ;
        }
        else {
            this.ˆá *= 0.6000000238418579;
        }
    }
    
    @Override
    protected void ˆØ() {
        super.ˆØ();
        final BlockPos var1 = new BlockPos(this);
        final BlockPos var2 = var1.Ø­áŒŠá();
        if (this.Ø()) {
            if (!this.Ï­Ðƒà.Â(var2).Ý().Ø()) {
                this.HorizonCode_Horizon_È(false);
                this.Ï­Ðƒà.HorizonCode_Horizon_È(null, 1015, var1, 0);
            }
            else {
                if (this.ˆáƒ.nextInt(200) == 0) {
                    this.ÂµÕ = this.ˆáƒ.nextInt(360);
                }
                if (this.Ï­Ðƒà.HorizonCode_Horizon_È(this, 4.0) != null) {
                    this.HorizonCode_Horizon_È(false);
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(null, 1015, var1, 0);
                }
            }
        }
        else {
            if (this.HorizonCode_Horizon_È != null && (!this.Ï­Ðƒà.Ø­áŒŠá(this.HorizonCode_Horizon_È) || this.HorizonCode_Horizon_È.Â() < 1)) {
                this.HorizonCode_Horizon_È = null;
            }
            if (this.HorizonCode_Horizon_È == null || this.ˆáƒ.nextInt(30) == 0 || this.HorizonCode_Horizon_È.Ý((int)this.ŒÏ, (int)this.Çªà¢, (int)this.Ê) < 4.0) {
                this.HorizonCode_Horizon_È = new BlockPos((int)this.ŒÏ + this.ˆáƒ.nextInt(7) - this.ˆáƒ.nextInt(7), (int)this.Çªà¢ + this.ˆáƒ.nextInt(6) - 2, (int)this.Ê + this.ˆáƒ.nextInt(7) - this.ˆáƒ.nextInt(7));
            }
            final double var3 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È() + 0.5 - this.ŒÏ;
            final double var4 = this.HorizonCode_Horizon_È.Â() + 0.1 - this.Çªà¢;
            final double var5 = this.HorizonCode_Horizon_È.Ý() + 0.5 - this.Ê;
            this.ÇŽÉ += (Math.signum(var3) * 0.5 - this.ÇŽÉ) * 0.10000000149011612;
            this.ˆá += (Math.signum(var4) * 0.699999988079071 - this.ˆá) * 0.10000000149011612;
            this.ÇŽÕ += (Math.signum(var5) * 0.5 - this.ÇŽÕ) * 0.10000000149011612;
            final float var6 = (float)(Math.atan2(this.ÇŽÕ, this.ÇŽÉ) * 180.0 / 3.141592653589793) - 90.0f;
            final float var7 = MathHelper.à(var6 - this.É);
            this.Ï­áˆºÓ = 0.5f;
            this.É += var7;
            if (this.ˆáƒ.nextInt(100) == 0 && this.Ï­Ðƒà.Â(var2).Ý().Ø()) {
                this.HorizonCode_Horizon_È(true);
            }
        }
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_, final BlockPos p_180433_5_) {
    }
    
    @Override
    public boolean Ñ¢à() {
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (!this.Ï­Ðƒà.ŠÄ && this.Ø()) {
            this.HorizonCode_Horizon_È(false);
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.£Ó.Â(16, tagCompund.Ø­áŒŠá("BatFlags"));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("BatFlags", this.£Ó.HorizonCode_Horizon_È(16));
    }
    
    @Override
    public boolean µà() {
        final BlockPos var1 = new BlockPos(this.ŒÏ, this.£É().Â, this.Ê);
        if (var1.Â() >= 63) {
            return false;
        }
        final int var2 = this.Ï­Ðƒà.ˆÏ­(var1);
        byte var3 = 4;
        if (this.HorizonCode_Horizon_È(this.Ï­Ðƒà.Õ())) {
            var3 = 7;
        }
        else if (this.ˆáƒ.nextBoolean()) {
            return false;
        }
        return var2 <= this.ˆáƒ.nextInt(var3) && super.µà();
    }
    
    private boolean HorizonCode_Horizon_È(final Calendar p_175569_1_) {
        return (p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20) || (p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3);
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.£ÂµÄ / 2.0f;
    }
}
