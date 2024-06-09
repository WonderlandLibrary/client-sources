/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class SVertexFormat {
    public static final int vertexSizeBlock = 14;
    public static final int offsetMidTexCoord = 8;
    public static final int offsetTangent = 10;
    public static final int offsetEntity = 12;
    public static final VertexFormat defVertexFormatTextured = SVertexFormat.makeDefVertexFormatTextured();

    public static VertexFormat makeDefVertexFormatBlock() {
        VertexFormat vf2 = new VertexFormat();
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
        vf2.func_177349_a(new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.PADDING, 1));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.PADDING, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
        return vf2;
    }

    public static VertexFormat makeDefVertexFormatItem() {
        VertexFormat vf2 = new VertexFormat();
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.PADDING, 1));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.PADDING, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
        return vf2;
    }

    public static VertexFormat makeDefVertexFormatTextured() {
        VertexFormat vf2 = new VertexFormat();
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.PADDING, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.PADDING, 1));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.PADDING, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
        return vf2;
    }

    public static void setDefBakedFormat(VertexFormat vf2) {
        vf2.clear();
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.PADDING, 1));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.PADDING, 2));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
        vf2.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.PADDING, 4));
    }
}

