package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.*;

static final class SwitchEnumUseage
{
    static final int[] field_178959_a;
    
    static {
        field_178959_a = new int[VertexFormatElement.EnumUseage.values().length];
        try {
            SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchEnumUseage.field_178959_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
