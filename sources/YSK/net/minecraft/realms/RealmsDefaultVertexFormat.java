package net.minecraft.realms;

import net.minecraft.client.renderer.vertex.*;

public class RealmsDefaultVertexFormat
{
    public static final RealmsVertexFormat PARTICLE;
    public static final RealmsVertexFormat POSITION_TEX_NORMAL;
    public static final RealmsVertexFormatElement ELEMENT_POSITION;
    public static final RealmsVertexFormat BLOCK_NORMALS;
    public static final RealmsVertexFormat BLOCK;
    public static final RealmsVertexFormat POSITION_TEX_COLOR_NORMAL;
    public static final RealmsVertexFormatElement ELEMENT_COLOR;
    public static final RealmsVertexFormatElement ELEMENT_UV1;
    public static final RealmsVertexFormatElement ELEMENT_UV0;
    public static final RealmsVertexFormat POSITION_TEX;
    public static final RealmsVertexFormat POSITION_COLOR;
    public static final RealmsVertexFormat POSITION_TEX_COLOR;
    public static final RealmsVertexFormatElement ELEMENT_NORMAL;
    public static final RealmsVertexFormatElement ELEMENT_PADDING;
    public static final RealmsVertexFormat POSITION_TEX2_COLOR;
    public static final RealmsVertexFormat ENTITY;
    public static final RealmsVertexFormat POSITION;
    public static final RealmsVertexFormat POSITION_NORMAL;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        BLOCK = new RealmsVertexFormat(new VertexFormat());
        BLOCK_NORMALS = new RealmsVertexFormat(new VertexFormat());
        ENTITY = new RealmsVertexFormat(new VertexFormat());
        PARTICLE = new RealmsVertexFormat(new VertexFormat());
        POSITION = new RealmsVertexFormat(new VertexFormat());
        POSITION_COLOR = new RealmsVertexFormat(new VertexFormat());
        POSITION_TEX = new RealmsVertexFormat(new VertexFormat());
        POSITION_NORMAL = new RealmsVertexFormat(new VertexFormat());
        POSITION_TEX_COLOR = new RealmsVertexFormat(new VertexFormat());
        POSITION_TEX_NORMAL = new RealmsVertexFormat(new VertexFormat());
        POSITION_TEX2_COLOR = new RealmsVertexFormat(new VertexFormat());
        POSITION_TEX_COLOR_NORMAL = new RealmsVertexFormat(new VertexFormat());
        ELEMENT_POSITION = new RealmsVertexFormatElement(new VertexFormatElement("".length(), VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, "   ".length()));
        ELEMENT_COLOR = new RealmsVertexFormatElement(new VertexFormatElement("".length(), VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 0x63 ^ 0x67));
        ELEMENT_UV0 = new RealmsVertexFormatElement(new VertexFormatElement("".length(), VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, "  ".length()));
        ELEMENT_UV1 = new RealmsVertexFormatElement(new VertexFormatElement(" ".length(), VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, "  ".length()));
        ELEMENT_NORMAL = new RealmsVertexFormatElement(new VertexFormatElement("".length(), VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, "   ".length()));
        ELEMENT_PADDING = new RealmsVertexFormatElement(new VertexFormatElement("".length(), VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, " ".length()));
        RealmsDefaultVertexFormat.BLOCK.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.BLOCK.addElement(RealmsDefaultVertexFormat.ELEMENT_COLOR);
        RealmsDefaultVertexFormat.BLOCK.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.BLOCK.addElement(RealmsDefaultVertexFormat.ELEMENT_UV1);
        RealmsDefaultVertexFormat.BLOCK_NORMALS.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.BLOCK_NORMALS.addElement(RealmsDefaultVertexFormat.ELEMENT_COLOR);
        RealmsDefaultVertexFormat.BLOCK_NORMALS.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.BLOCK_NORMALS.addElement(RealmsDefaultVertexFormat.ELEMENT_NORMAL);
        RealmsDefaultVertexFormat.BLOCK_NORMALS.addElement(RealmsDefaultVertexFormat.ELEMENT_PADDING);
        RealmsDefaultVertexFormat.ENTITY.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.ENTITY.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.ENTITY.addElement(RealmsDefaultVertexFormat.ELEMENT_NORMAL);
        RealmsDefaultVertexFormat.ENTITY.addElement(RealmsDefaultVertexFormat.ELEMENT_PADDING);
        RealmsDefaultVertexFormat.PARTICLE.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.PARTICLE.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.PARTICLE.addElement(RealmsDefaultVertexFormat.ELEMENT_COLOR);
        RealmsDefaultVertexFormat.PARTICLE.addElement(RealmsDefaultVertexFormat.ELEMENT_UV1);
        RealmsDefaultVertexFormat.POSITION.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_COLOR);
        RealmsDefaultVertexFormat.POSITION_TEX.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_TEX.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.POSITION_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_NORMAL);
        RealmsDefaultVertexFormat.POSITION_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_PADDING);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_COLOR);
        RealmsDefaultVertexFormat.POSITION_TEX_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_TEX_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.POSITION_TEX_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_NORMAL);
        RealmsDefaultVertexFormat.POSITION_TEX_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_PADDING);
        RealmsDefaultVertexFormat.POSITION_TEX2_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_TEX2_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.POSITION_TEX2_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_UV1);
        RealmsDefaultVertexFormat.POSITION_TEX2_COLOR.addElement(RealmsDefaultVertexFormat.ELEMENT_COLOR);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_POSITION);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_UV0);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_COLOR);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_NORMAL);
        RealmsDefaultVertexFormat.POSITION_TEX_COLOR_NORMAL.addElement(RealmsDefaultVertexFormat.ELEMENT_PADDING);
    }
}
