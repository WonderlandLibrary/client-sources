package net.minecraft.client.renderer.vertex;

static final class SwitchEnumUseage
{
    static final int[] field_177382_a;
    
    static {
        field_177382_a = new int[VertexFormatElement.EnumUseage.values().length];
        try {
            SwitchEnumUseage.field_177382_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEnumUseage.field_177382_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchEnumUseage.field_177382_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
