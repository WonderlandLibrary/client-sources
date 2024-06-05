package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.*;

static final class SwitchEnumUseage
{
    static final int[] field_178037_a;
    
    static {
        field_178037_a = new int[VertexFormatElement.EnumUseage.values().length];
        try {
            SwitchEnumUseage.field_178037_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumUseage.field_178037_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumUseage.field_178037_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
