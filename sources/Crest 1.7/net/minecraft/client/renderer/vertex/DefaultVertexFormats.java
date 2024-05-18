// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.renderer.vertex;

public class DefaultVertexFormats
{
    public static final VertexFormat field_176600_a;
    public static final VertexFormat field_176599_b;
    private static final String __OBFID = "CL_00002403";
    
    static {
        (field_176600_a = new VertexFormat()).setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        DefaultVertexFormats.field_176600_a.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
        DefaultVertexFormats.field_176600_a.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
        DefaultVertexFormats.field_176600_a.setElement(new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2));
        (field_176599_b = new VertexFormat()).setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        DefaultVertexFormats.field_176599_b.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
        DefaultVertexFormats.field_176599_b.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
        DefaultVertexFormats.field_176599_b.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
        DefaultVertexFormats.field_176599_b.setElement(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.PADDING, 1));
    }
}
