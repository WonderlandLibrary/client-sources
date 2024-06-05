package net.minecraft.client.renderer.block.model;

static final class SwitchSpanFacing
{
    static final int[] field_178390_a;
    
    static {
        field_178390_a = new int[SpanFacing.values().length];
        try {
            SwitchSpanFacing.field_178390_a[SpanFacing.UP.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchSpanFacing.field_178390_a[SpanFacing.DOWN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchSpanFacing.field_178390_a[SpanFacing.LEFT.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchSpanFacing.field_178390_a[SpanFacing.RIGHT.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
