package HORIZON-6-0-SKIDPROTECTION;

import java.util.UUID;

public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable
{
    protected EntityAISit ŒÂ;
    private static final String Ï­Ï = "CL_00001561";
    
    public EntityTameable(final World worldIn) {
        super(worldIn);
        this.ŒÂ = new EntityAISit(this);
        this.¥Ê();
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(17, "");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        if (this.Â() == null) {
            tagCompound.HorizonCode_Horizon_È("OwnerUUID", "");
        }
        else {
            tagCompound.HorizonCode_Horizon_È("OwnerUUID", this.Â());
        }
        tagCompound.HorizonCode_Horizon_È("Sitting", this.áˆºÕ());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        String var2 = "";
        if (tagCompund.Â("OwnerUUID", 8)) {
            var2 = tagCompund.áˆºÑ¢Õ("OwnerUUID");
        }
        else {
            final String var3 = tagCompund.áˆºÑ¢Õ("Owner");
            var2 = PreYggdrasilConverter.HorizonCode_Horizon_È(var3);
        }
        if (var2.length() > 0) {
            this.HorizonCode_Horizon_È(var2);
            this.á(true);
        }
        this.ŒÂ.HorizonCode_Horizon_È(tagCompund.£á("Sitting"));
        this.£á(tagCompund.£á("Sitting"));
    }
    
    protected void ˆÏ­(final boolean p_70908_1_) {
        EnumParticleTypes var2 = EnumParticleTypes.áƒ;
        if (!p_70908_1_) {
            var2 = EnumParticleTypes.á;
        }
        for (int var3 = 0; var3 < 7; ++var3) {
            final double var4 = this.ˆáƒ.nextGaussian() * 0.02;
            final double var5 = this.ˆáƒ.nextGaussian() * 0.02;
            final double var6 = this.ˆáƒ.nextGaussian() * 0.02;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var2, this.ŒÏ + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, this.Çªà¢ + 0.5 + this.ˆáƒ.nextFloat() * this.£ÂµÄ, this.Ê + this.ˆáƒ.nextFloat() * this.áŒŠ * 2.0f - this.áŒŠ, var4, var5, var6, new int[0]);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 7) {
            this.ˆÏ­(true);
        }
        else if (p_70103_1_ == 6) {
            this.ˆÏ­(false);
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    public boolean ÐƒÓ() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x4) != 0x0;
    }
    
    public void á(final boolean p_70903_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70903_1_) {
            this.£Ó.Â(16, (byte)(var2 | 0x4));
        }
        else {
            this.£Ó.Â(16, (byte)(var2 & 0xFFFFFFFB));
        }
        this.¥Ê();
    }
    
    protected void ¥Ê() {
    }
    
    public boolean áˆºÕ() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x1) != 0x0;
    }
    
    public void £á(final boolean p_70904_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70904_1_) {
            this.£Ó.Â(16, (byte)(var2 | 0x1));
        }
        else {
            this.£Ó.Â(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    @Override
    public String Â() {
        return this.£Ó.Âµá€(17);
    }
    
    public void HorizonCode_Horizon_È(final String p_152115_1_) {
        this.£Ó.Â(17, p_152115_1_);
    }
    
    public EntityLivingBase ŒÐƒà() {
        try {
            final UUID var1 = UUID.fromString(this.Â());
            return (var1 == null) ? null : this.Ï­Ðƒà.HorizonCode_Horizon_È(var1);
        }
        catch (IllegalArgumentException var2) {
            return null;
        }
    }
    
    public boolean Âµá€(final EntityLivingBase p_152114_1_) {
        return p_152114_1_ == this.ŒÐƒà();
    }
    
    public EntityAISit ÐƒáˆºÄ() {
        return this.ŒÂ;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityLivingBase p_142018_1_, final EntityLivingBase p_142018_2_) {
        return true;
    }
    
    @Override
    public Team Çªáˆºá() {
        if (this.ÐƒÓ()) {
            final EntityLivingBase var1 = this.ŒÐƒà();
            if (var1 != null) {
                return var1.Çªáˆºá();
            }
        }
        return super.Çªáˆºá();
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityLivingBase p_142014_1_) {
        if (this.ÐƒÓ()) {
            final EntityLivingBase var2 = this.ŒÐƒà();
            if (p_142014_1_ == var2) {
                return true;
            }
            if (var2 != null) {
                return var2.Ø­áŒŠá(p_142014_1_);
            }
        }
        return super.Ø­áŒŠá(p_142014_1_);
    }
    
    @Override
    public void Â(final DamageSource cause) {
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ðƒà.Çªà¢().Â("showDeathMessages") && this.j_() && this.ŒÐƒà() instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.ŒÐƒà()).HorizonCode_Horizon_È(this.ÇŽØ().Â());
        }
        super.Â(cause);
    }
    
    @Override
    public Entity y_() {
        return this.ŒÐƒà();
    }
}
