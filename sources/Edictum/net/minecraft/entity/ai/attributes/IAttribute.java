package net.minecraft.entity.ai.attributes;

import javax.annotation.Nullable;

public interface IAttribute
{
    String getAttributeUnlocalizedName();

    double clampValue(double p_111109_1_);

    double getDefaultValue();

    boolean getShouldWatch();

    @Nullable
    IAttribute getParent();
}
