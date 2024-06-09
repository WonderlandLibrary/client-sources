package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.Validate;
import java.util.Random;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.UUID;

public class AttributeModifier
{
    private final double HorizonCode_Horizon_È;
    private final int Â;
    private final String Ý;
    private final UUID Ø­áŒŠá;
    private boolean Âµá€;
    private static final String Ó = "CL_00001564";
    
    public AttributeModifier(final String p_i1605_1_, final double p_i1605_2_, final int p_i1605_4_) {
        this(MathHelper.HorizonCode_Horizon_È((Random)ThreadLocalRandom.current()), p_i1605_1_, p_i1605_2_, p_i1605_4_);
    }
    
    public AttributeModifier(final UUID p_i1606_1_, final String p_i1606_2_, final double p_i1606_3_, final int p_i1606_5_) {
        this.Âµá€ = true;
        this.Ø­áŒŠá = p_i1606_1_;
        this.Ý = p_i1606_2_;
        this.HorizonCode_Horizon_È = p_i1606_3_;
        this.Â = p_i1606_5_;
        Validate.notEmpty((CharSequence)p_i1606_2_, "Modifier name cannot be empty", new Object[0]);
        Validate.inclusiveBetween(0L, 2L, (long)p_i1606_5_, "Invalid operation");
    }
    
    public UUID HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public String Â() {
        return this.Ý;
    }
    
    public int Ý() {
        return this.Â;
    }
    
    public double Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean Âµá€() {
        return this.Âµá€;
    }
    
    public AttributeModifier HorizonCode_Horizon_È(final boolean p_111168_1_) {
        this.Âµá€ = p_111168_1_;
        return this;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            final AttributeModifier var2 = (AttributeModifier)p_equals_1_;
            if (this.Ø­áŒŠá != null) {
                if (!this.Ø­áŒŠá.equals(var2.Ø­áŒŠá)) {
                    return false;
                }
            }
            else if (var2.Ø­áŒŠá != null) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (this.Ø­áŒŠá != null) ? this.Ø­áŒŠá.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        return "AttributeModifier{amount=" + this.HorizonCode_Horizon_È + ", operation=" + this.Â + ", name='" + this.Ý + '\'' + ", id=" + this.Ø­áŒŠá + ", serialize=" + this.Âµá€ + '}';
    }
}
