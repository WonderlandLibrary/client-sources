package HORIZON-6-0-SKIDPROTECTION;

import com.mojang.authlib.GameProfile;

public class EntityOtherPlayerMP extends AbstractClientPlayer
{
    private boolean HorizonCode_Horizon_È;
    private int Â;
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private double à;
    private static final String Ø = "CL_00000939";
    
    public EntityOtherPlayerMP(final World worldIn, final GameProfile p_i45075_2_) {
        super(worldIn, p_i45075_2_);
        this.Ô = 0.0f;
        this.ÇªÓ = true;
        this.¥áŠ = 0.25f;
        this.¥Æ = 10.0;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        this.Ý = p_180426_1_;
        this.Ø­áŒŠá = p_180426_3_;
        this.Âµá€ = p_180426_5_;
        this.Ó = p_180426_7_;
        this.à = p_180426_8_;
        this.Â = p_180426_9_;
    }
    
    @Override
    public void á() {
        this.¥áŠ = 0.0f;
        super.á();
        this.Šà = this.áŒŠá€;
        final double var1 = this.ŒÏ - this.áŒŠà;
        final double var2 = this.Ê - this.Ñ¢á;
        float var3 = MathHelper.HorizonCode_Horizon_È(var1 * var1 + var2 * var2) * 4.0f;
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        this.áŒŠá€ += (var3 - this.áŒŠá€) * 0.4f;
        this.¥Ï += this.áŒŠá€;
        if (!this.HorizonCode_Horizon_È && this.áŒŠáŠ() && this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Ø­Ñ¢Ï­Ø­áˆº.Ý] != null) {
            final ItemStack var4 = this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Ø­Ñ¢Ï­Ø­áˆº.Ý];
            this.Â(this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Ø­Ñ¢Ï­Ø­áˆº.Ý], var4.HorizonCode_Horizon_È().Ø­áŒŠá(var4));
            this.HorizonCode_Horizon_È = true;
        }
        else if (this.HorizonCode_Horizon_È && !this.áŒŠáŠ()) {
            this.ŠÕ();
            this.HorizonCode_Horizon_È = false;
        }
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Â > 0) {
            final double var1 = this.ŒÏ + (this.Ý - this.ŒÏ) / this.Â;
            final double var2 = this.Çªà¢ + (this.Ø­áŒŠá - this.Çªà¢) / this.Â;
            final double var3 = this.Ê + (this.Âµá€ - this.Ê) / this.Â;
            double var4;
            for (var4 = this.Ó - this.É; var4 < -180.0; var4 += 360.0) {}
            while (var4 >= 180.0) {
                var4 -= 360.0;
            }
            this.É += (float)(var4 / this.Â);
            this.áƒ += (float)((this.à - this.áƒ) / this.Â);
            --this.Â;
            this.Ý(var1, var2, var3);
            this.Â(this.É, this.áƒ);
        }
        this.Çªà = this.¥Å;
        this.µÏ();
        float var5 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
        float var6 = (float)Math.atan(-this.ˆá * 0.20000000298023224) * 15.0f;
        if (var5 > 0.1f) {
            var5 = 0.1f;
        }
        if (!this.ŠÂµà || this.Ï­Ä() <= 0.0f) {
            var5 = 0.0f;
        }
        if (this.ŠÂµà || this.Ï­Ä() <= 0.0f) {
            var6 = 0.0f;
        }
        this.¥Å += (var5 - this.¥Å) * 0.4f;
        this.£É += (var6 - this.£É) * 0.8f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIn, final ItemStack itemStackIn) {
        if (slotIn == 0) {
            this.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È[this.Ø­Ñ¢Ï­Ø­áˆº.Ý] = itemStackIn;
        }
        else {
            this.Ø­Ñ¢Ï­Ø­áˆº.Â[slotIn - 1] = itemStackIn;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChatComponent message) {
        Minecraft.áŒŠà().Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(message);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int permissionLevel, final String command) {
        return false;
    }
    
    @Override
    public BlockPos £á() {
        return new BlockPos(this.ŒÏ + 0.5, this.Çªà¢ + 0.5, this.Ê + 0.5);
    }
}
