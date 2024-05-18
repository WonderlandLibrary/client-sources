package net.minecraft.entity.ai.attributes;

import java.util.*;

public interface IAttributeInstance
{
    double getAttributeValue();
    
    Collection<AttributeModifier> func_111122_c();
    
    void removeAllModifiers();
    
    boolean hasModifier(final AttributeModifier p0);
    
    AttributeModifier getModifier(final UUID p0);
    
    Collection<AttributeModifier> getModifiersByOperation(final int p0);
    
    double getBaseValue();
    
    IAttribute getAttribute();
    
    void setBaseValue(final double p0);
    
    void removeModifier(final AttributeModifier p0);
    
    void applyModifier(final AttributeModifier p0);
}
