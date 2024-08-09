/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class BufferUtil {
    public static String getBufferHex(BufferBuilder bufferBuilder) {
        int n = bufferBuilder.getDrawMode();
        String string = "";
        int n2 = -1;
        if (n == 7) {
            string = "quad";
            n2 = 4;
        } else {
            if (n != 4) {
                return "Invalid draw mode: " + n;
            }
            string = "triangle";
            n2 = 3;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int n3 = bufferBuilder.getVertexCount();
        for (int i = 0; i < n3; ++i) {
            if (i % n2 == 0) {
                stringBuffer.append(string + " " + i / n2 + "\n");
            }
            String string2 = BufferUtil.getVertexHex(i, bufferBuilder);
            stringBuffer.append(string2);
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    private static String getVertexHex(int n, BufferBuilder bufferBuilder) {
        StringBuffer stringBuffer = new StringBuffer();
        ByteBuffer byteBuffer = bufferBuilder.getByteBuffer();
        VertexFormat vertexFormat = bufferBuilder.getVertexFormat();
        int n2 = bufferBuilder.getStartPosition() + n * vertexFormat.getSize();
        for (VertexFormatElement vertexFormatElement : vertexFormat.getElements()) {
            if (vertexFormatElement.getElementCount() > 0) {
                stringBuffer.append("(");
            }
            for (int i = 0; i < vertexFormatElement.getElementCount(); ++i) {
                if (i > 0) {
                    stringBuffer.append(" ");
                }
                switch (1.$SwitchMap$net$minecraft$client$renderer$vertex$VertexFormatElement$Type[vertexFormatElement.getType().ordinal()]) {
                    case 1: {
                        stringBuffer.append(byteBuffer.getFloat(n2));
                        break;
                    }
                    case 2: 
                    case 3: {
                        stringBuffer.append(byteBuffer.get(n2));
                        break;
                    }
                    case 4: 
                    case 5: {
                        stringBuffer.append(byteBuffer.getShort(n2));
                        break;
                    }
                    case 6: 
                    case 7: {
                        stringBuffer.append(byteBuffer.getShort(n2));
                        break;
                    }
                    default: {
                        stringBuffer.append("??");
                    }
                }
                n2 += vertexFormatElement.getType().getSize();
            }
            if (vertexFormatElement.getElementCount() <= 0) continue;
            stringBuffer.append(")");
        }
        return stringBuffer.toString();
    }

    public static String getBufferString(IntBuffer intBuffer) {
        if (intBuffer == null) {
            return "null";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("(pos=" + intBuffer.position() + " lim=" + intBuffer.limit() + " cap=" + intBuffer.capacity() + ")");
        stringBuffer.append("[");
        int n = Math.min(intBuffer.limit(), 1024);
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(intBuffer.get(i));
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public static int[] toArray(IntBuffer intBuffer) {
        int[] nArray = new int[intBuffer.limit()];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = intBuffer.get(i);
        }
        return nArray;
    }
}

