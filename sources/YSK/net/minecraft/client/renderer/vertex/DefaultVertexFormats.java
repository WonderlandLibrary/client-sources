package net.minecraft.client.renderer.vertex;

public class DefaultVertexFormats
{
    public static final VertexFormat OLDMODEL_POSITION_TEX_NORMAL;
    public static final VertexFormat POSITION_NORMAL;
    public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP;
    public static final VertexFormat POSITION_TEX_COLOR;
    public static final VertexFormat BLOCK;
    public static final VertexFormatElement PADDING_1B;
    public static final VertexFormat POSITION_TEX_LMAP_COLOR;
    public static final VertexFormat ITEM;
    public static final VertexFormatElement COLOR_4UB;
    public static final VertexFormat POSITION_TEX_COLOR_NORMAL;
    public static final VertexFormatElement TEX_2S;
    public static final VertexFormatElement NORMAL_3B;
    public static final VertexFormatElement POSITION_3F;
    public static final VertexFormat POSITION;
    public static final VertexFormatElement TEX_2F;
    public static final VertexFormat POSITION_TEX_NORMAL;
    public static final VertexFormat POSITION_TEX;
    public static final VertexFormat POSITION_COLOR;
    
    static {
        BLOCK = new VertexFormat();
        ITEM = new VertexFormat();
        OLDMODEL_POSITION_TEX_NORMAL = new VertexFormat();
        PARTICLE_POSITION_TEX_COLOR_LMAP = new VertexFormat();
        POSITION = new VertexFormat();
        POSITION_COLOR = new VertexFormat();
        POSITION_TEX = new VertexFormat();
        POSITION_NORMAL = new VertexFormat();
        POSITION_TEX_COLOR = new VertexFormat();
        POSITION_TEX_NORMAL = new VertexFormat();
        POSITION_TEX_LMAP_COLOR = new VertexFormat();
        POSITION_TEX_COLOR_NORMAL = new VertexFormat();
        POSITION_3F = new VertexFormatElement("".length(), VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, "   ".length());
        COLOR_4UB = new VertexFormatElement("".length(), VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 0x88 ^ 0x8C);
        TEX_2F = new VertexFormatElement("".length(), VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, "  ".length());
        TEX_2S = new VertexFormatElement(" ".length(), VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, "  ".length());
        NORMAL_3B = new VertexFormatElement("".length(), VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, "   ".length());
        PADDING_1B = new VertexFormatElement("".length(), VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, " ".length());
        DefaultVertexFormats.BLOCK.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.BLOCK.func_181721_a(DefaultVertexFormats.COLOR_4UB);
        DefaultVertexFormats.BLOCK.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.BLOCK.func_181721_a(DefaultVertexFormats.TEX_2S);
        DefaultVertexFormats.ITEM.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.ITEM.func_181721_a(DefaultVertexFormats.COLOR_4UB);
        DefaultVertexFormats.ITEM.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.ITEM.func_181721_a(DefaultVertexFormats.NORMAL_3B);
        DefaultVertexFormats.ITEM.func_181721_a(DefaultVertexFormats.PADDING_1B);
        DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.NORMAL_3B);
        DefaultVertexFormats.OLDMODEL_POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.PADDING_1B);
        DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP.func_181721_a(DefaultVertexFormats.COLOR_4UB);
        DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP.func_181721_a(DefaultVertexFormats.TEX_2S);
        DefaultVertexFormats.POSITION.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_COLOR.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_COLOR.func_181721_a(DefaultVertexFormats.COLOR_4UB);
        DefaultVertexFormats.POSITION_TEX.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_TEX.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.POSITION_NORMAL.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_NORMAL.func_181721_a(DefaultVertexFormats.NORMAL_3B);
        DefaultVertexFormats.POSITION_NORMAL.func_181721_a(DefaultVertexFormats.PADDING_1B);
        DefaultVertexFormats.POSITION_TEX_COLOR.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_TEX_COLOR.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.POSITION_TEX_COLOR.func_181721_a(DefaultVertexFormats.COLOR_4UB);
        DefaultVertexFormats.POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.NORMAL_3B);
        DefaultVertexFormats.POSITION_TEX_NORMAL.func_181721_a(DefaultVertexFormats.PADDING_1B);
        DefaultVertexFormats.POSITION_TEX_LMAP_COLOR.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_TEX_LMAP_COLOR.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.POSITION_TEX_LMAP_COLOR.func_181721_a(DefaultVertexFormats.TEX_2S);
        DefaultVertexFormats.POSITION_TEX_LMAP_COLOR.func_181721_a(DefaultVertexFormats.COLOR_4UB);
        DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.func_181721_a(DefaultVertexFormats.POSITION_3F);
        DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.func_181721_a(DefaultVertexFormats.TEX_2F);
        DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.func_181721_a(DefaultVertexFormats.COLOR_4UB);
        DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.func_181721_a(DefaultVertexFormats.NORMAL_3B);
        DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.func_181721_a(DefaultVertexFormats.PADDING_1B);
    }
    
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
