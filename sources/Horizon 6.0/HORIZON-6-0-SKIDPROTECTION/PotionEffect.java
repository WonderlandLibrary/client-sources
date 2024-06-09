package HORIZON-6-0-SKIDPROTECTION;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect
{
    private static final Logger HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private boolean Âµá€;
    private boolean Ó;
    private boolean à;
    private boolean Ø;
    private static final String áŒŠÆ = "CL_00001529";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public PotionEffect(final int id, final int effectDuration) {
        this(id, effectDuration, 0);
    }
    
    public PotionEffect(final int id, final int effectDuration, final int effectAmplifier) {
        this(id, effectDuration, effectAmplifier, false, true);
    }
    
    public PotionEffect(final int id, final int effectDuration, final int effectAmplifier, final boolean ambient, final boolean showParticles) {
        this.Â = id;
        this.Ý = effectDuration;
        this.Ø­áŒŠá = effectAmplifier;
        this.Ó = ambient;
        this.Ø = showParticles;
    }
    
    public PotionEffect(final PotionEffect other) {
        this.Â = other.Â;
        this.Ý = other.Ý;
        this.Ø­áŒŠá = other.Ø­áŒŠá;
        this.Ó = other.Ó;
        this.Ø = other.Ø;
    }
    
    public void HorizonCode_Horizon_È(final PotionEffect other) {
        if (this.Â != other.Â) {
            PotionEffect.HorizonCode_Horizon_È.warn("This method should only be called for matching effects!");
        }
        if (other.Ø­áŒŠá > this.Ø­áŒŠá) {
            this.Ø­áŒŠá = other.Ø­áŒŠá;
            this.Ý = other.Ý;
        }
        else if (other.Ø­áŒŠá == this.Ø­áŒŠá && this.Ý < other.Ý) {
            this.Ý = other.Ý;
        }
        else if (!other.Ó && this.Ó) {
            this.Ó = other.Ó;
        }
        this.Ø = other.Ø;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public int Â() {
        return this.Ý;
    }
    
    public int Ý() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final boolean splashPotion) {
        this.Âµá€ = splashPotion;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Ó;
    }
    
    public boolean Âµá€() {
        return this.Ø;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityLivingBase entityIn) {
        if (this.Ý > 0) {
            if (Potion.HorizonCode_Horizon_È[this.Â].Â(this.Ý, this.Ø­áŒŠá)) {
                this.Â(entityIn);
            }
            this.Ø();
        }
        return this.Ý > 0;
    }
    
    private int Ø() {
        return --this.Ý;
    }
    
    public void Â(final EntityLivingBase entityIn) {
        if (this.Ý > 0) {
            Potion.HorizonCode_Horizon_È[this.Â].HorizonCode_Horizon_È(entityIn, this.Ø­áŒŠá);
        }
    }
    
    public String Ó() {
        return Potion.HorizonCode_Horizon_È[this.Â].Ø­áŒŠá();
    }
    
    @Override
    public int hashCode() {
        return this.Â;
    }
    
    @Override
    public String toString() {
        String var1 = "";
        if (this.Ý() > 0) {
            var1 = String.valueOf(this.Ó()) + " x " + (this.Ý() + 1) + ", Duration: " + this.Â();
        }
        else {
            var1 = String.valueOf(this.Ó()) + ", Duration: " + this.Â();
        }
        if (this.Âµá€) {
            var1 = String.valueOf(var1) + ", Splash: true";
        }
        if (!this.Ø) {
            var1 = String.valueOf(var1) + ", Particles: false";
        }
        return Potion.HorizonCode_Horizon_È[this.Â].áŒŠÆ() ? ("(" + var1 + ")") : var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof PotionEffect)) {
            return false;
        }
        final PotionEffect var2 = (PotionEffect)p_equals_1_;
        return this.Â == var2.Â && this.Ø­áŒŠá == var2.Ø­áŒŠá && this.Ý == var2.Ý && this.Âµá€ == var2.Âµá€ && this.Ó == var2.Ó;
    }
    
    public NBTTagCompound HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        nbt.HorizonCode_Horizon_È("Id", (byte)this.HorizonCode_Horizon_È());
        nbt.HorizonCode_Horizon_È("Amplifier", (byte)this.Ý());
        nbt.HorizonCode_Horizon_È("Duration", this.Â());
        nbt.HorizonCode_Horizon_È("Ambient", this.Ø­áŒŠá());
        nbt.HorizonCode_Horizon_È("ShowParticles", this.Âµá€());
        return nbt;
    }
    
    public static PotionEffect Â(final NBTTagCompound nbt) {
        final byte var1 = nbt.Ø­áŒŠá("Id");
        if (var1 >= 0 && var1 < Potion.HorizonCode_Horizon_È.length && Potion.HorizonCode_Horizon_È[var1] != null) {
            final byte var2 = nbt.Ø­áŒŠá("Amplifier");
            final int var3 = nbt.Ó("Duration");
            final boolean var4 = nbt.£á("Ambient");
            boolean var5 = true;
            if (nbt.Â("ShowParticles", 1)) {
                var5 = nbt.£á("ShowParticles");
            }
            return new PotionEffect(var1, var3, var2, var4, var5);
        }
        return null;
    }
    
    public void Â(final boolean maxDuration) {
        this.à = maxDuration;
    }
    
    public boolean à() {
        return this.à;
    }
}
