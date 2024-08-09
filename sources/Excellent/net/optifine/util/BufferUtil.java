package net.optifine.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class BufferUtil {
    public static String getBufferHex(BufferBuilder bb) {
        int i = bb.getDrawMode();
        String s = "";
        int j = -1;

        if (i == 7) {
            s = "quad";
            j = 4;
        } else {
            if (i != 4) {
                return "Invalid draw mode: " + i;
            }

            s = "triangle";
            j = 3;
        }

        StringBuilder stringbuffer = new StringBuilder();
        int k = bb.getVertexCount();

        for (int l = 0; l < k; ++l) {
            if (l % j == 0) {
                stringbuffer.append(s).append(" ").append(l / j).append("\n");
            }

            String s1 = getVertexHex(l, bb);
            stringbuffer.append(s1);
            stringbuffer.append("\n");
        }

        return stringbuffer.toString();
    }

    private static String getVertexHex(int vertex, BufferBuilder bb) {
        StringBuilder stringbuffer = new StringBuilder();
        ByteBuffer bytebuffer = bb.getByteBuffer();
        VertexFormat vertexformat = bb.getVertexFormat();
        int i = bb.getStartPosition() + vertex * vertexformat.getSize();

        for (VertexFormatElement vertexformatelement : vertexformat.getElements()) {
            if (vertexformatelement.getElementCount() > 0) {
                stringbuffer.append("(");
            }

            for (int j = 0; j < vertexformatelement.getElementCount(); ++j) {
                if (j > 0) {
                    stringbuffer.append(" ");
                }

                switch (vertexformatelement.getType()) {
                    case FLOAT:
                        stringbuffer.append(bytebuffer.getFloat(i));
                        break;

                    case UBYTE:
                    case BYTE:
                        stringbuffer.append(bytebuffer.get(i));
                        break;

                    case USHORT:
                    case SHORT, UINT, INT:
                        stringbuffer.append(bytebuffer.getShort(i));
                        break;

                    default:
                        stringbuffer.append("??");
                }

                i += vertexformatelement.getType().getSize();
            }

            if (vertexformatelement.getElementCount() > 0) {
                stringbuffer.append(")");
            }
        }

        return stringbuffer.toString();
    }

    public static String getBufferString(IntBuffer buf) {
        if (buf == null) {
            return "null";
        } else {
            StringBuilder stringbuffer = new StringBuilder();
            stringbuffer.append("(pos=").append(buf.position()).append(" lim=").append(buf.limit()).append(" cap=").append(buf.capacity()).append(")");
            stringbuffer.append("[");
            int i = Math.min(buf.limit(), 1024);

            for (int j = 0; j < i; ++j) {
                if (j > 0) {
                    stringbuffer.append(", ");
                }

                stringbuffer.append(buf.get(j));
            }

            stringbuffer.append("]");
            return stringbuffer.toString();
        }
    }

    public static int[] toArray(IntBuffer buf) {
        int[] aint = new int[buf.limit()];

        for (int i = 0; i < aint.length; ++i) {
            aint[i] = buf.get(i);
        }

        return aint;
    }
}
