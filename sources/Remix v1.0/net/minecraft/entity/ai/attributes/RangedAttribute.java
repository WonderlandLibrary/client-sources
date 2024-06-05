package net.minecraft.entity.ai.attributes;

import net.minecraft.util.*;

public class RangedAttribute extends BaseAttribute
{
    private final double minimumValue;
    private final double maximumValue;
    private String description;
    
    public RangedAttribute(final IAttribute p_i45891_1_, final String p_i45891_2_, final double p_i45891_3_, final double p_i45891_5_, final double p_i45891_7_) {
        super(p_i45891_1_, p_i45891_2_, p_i45891_3_);
        this.minimumValue = p_i45891_5_;
        this.maximumValue = p_i45891_7_;
        if (p_i45891_5_ > p_i45891_7_) {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        if (p_i45891_3_ < p_i45891_5_) {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        if (p_i45891_3_ > p_i45891_7_) {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public RangedAttribute setDescription(final String p_111117_1_) {
        this.description = p_111117_1_;
        return this;
    }
    
    @Override
    public double clampValue(double p_111109_1_) {
        p_111109_1_ = MathHelper.clamp_double(p_111109_1_, this.minimumValue, this.maximumValue);
        return p_111109_1_;
    }
}
