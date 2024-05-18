package net.minecraft.entity.ai.attributes;

public interface IAttribute
{
    boolean getShouldWatch();
    
    double getDefaultValue();
    
    IAttribute func_180372_d();
    
    double clampValue(final double p0);
    
    String getAttributeUnlocalizedName();
}
