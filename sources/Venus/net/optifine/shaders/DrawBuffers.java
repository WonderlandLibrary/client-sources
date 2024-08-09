/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.nio.IntBuffer;
import java.util.Arrays;
import net.optifine.util.ArrayUtils;
import net.optifine.util.BufferUtil;
import org.lwjgl.BufferUtils;

public class DrawBuffers {
    private String name;
    private final int maxColorBuffers;
    private final int maxDrawBuffers;
    private final IntBuffer drawBuffers;
    private int[] attachmentMappings;
    private IntBuffer glDrawBuffers;

    public DrawBuffers(String string, int n, int n2) {
        this.name = string;
        this.maxColorBuffers = n;
        this.maxDrawBuffers = n2;
        this.drawBuffers = IntBuffer.wrap(new int[n2]);
    }

    public int get(int n) {
        return this.drawBuffers.get(n);
    }

    public DrawBuffers put(int n) {
        this.resetMappings();
        this.drawBuffers.put(n);
        return this;
    }

    public DrawBuffers put(int n, int n2) {
        this.resetMappings();
        this.drawBuffers.put(n, n2);
        return this;
    }

    public int position() {
        return this.drawBuffers.position();
    }

    public DrawBuffers position(int n) {
        this.resetMappings();
        this.drawBuffers.position(n);
        return this;
    }

    public int limit() {
        return this.drawBuffers.limit();
    }

    public DrawBuffers limit(int n) {
        this.resetMappings();
        this.drawBuffers.limit(n);
        return this;
    }

    public int capacity() {
        return this.drawBuffers.capacity();
    }

    public DrawBuffers fill(int n) {
        for (int i = 0; i < this.drawBuffers.limit(); ++i) {
            this.drawBuffers.put(i, n);
        }
        this.resetMappings();
        return this;
    }

    private void resetMappings() {
        this.attachmentMappings = null;
        this.glDrawBuffers = null;
    }

    public int[] getAttachmentMappings() {
        if (this.attachmentMappings == null) {
            this.attachmentMappings = DrawBuffers.makeAttachmentMappings(this.drawBuffers, this.maxColorBuffers, this.maxDrawBuffers);
        }
        return this.attachmentMappings;
    }

    private static int[] makeAttachmentMappings(IntBuffer intBuffer, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int[] nArray = new int[n];
        Arrays.fill(nArray, -1);
        for (n5 = 0; n5 < intBuffer.limit(); ++n5) {
            n4 = intBuffer.get(n5);
            n3 = n4 - 36064;
            if (n3 < 0 || n3 >= n2) continue;
            nArray[n3] = n3;
        }
        for (n5 = 0; n5 < intBuffer.limit(); ++n5) {
            n4 = intBuffer.get(n5);
            n3 = n4 - 36064;
            if (n3 < n2 || n3 >= n) continue;
            int n6 = DrawBuffers.getMappingIndex(n3, n2, nArray);
            if (n6 < 0) {
                throw new RuntimeException("Too many draw buffers, mapping: " + ArrayUtils.arrayToString(nArray));
            }
            nArray[n3] = n6;
        }
        return nArray;
    }

    private static int getMappingIndex(int n, int n2, int[] nArray) {
        if (n < n2) {
            return n;
        }
        if (nArray[n] >= 0) {
            return nArray[n];
        }
        for (int i = 0; i < n2; ++i) {
            if (ArrayUtils.contains(nArray, i)) continue;
            return i;
        }
        return 1;
    }

    public IntBuffer getGlDrawBuffers() {
        if (this.glDrawBuffers == null) {
            this.glDrawBuffers = DrawBuffers.makeGlDrawBuffers(this.drawBuffers, this.getAttachmentMappings());
        }
        return this.glDrawBuffers;
    }

    private static IntBuffer makeGlDrawBuffers(IntBuffer intBuffer, int[] nArray) {
        IntBuffer intBuffer2 = BufferUtils.createIntBuffer(intBuffer.capacity());
        for (int i = 0; i < intBuffer.limit(); ++i) {
            int n = intBuffer.get(i);
            int n2 = n - 36064;
            int n3 = 0;
            if (n2 >= 0 && n2 < nArray.length) {
                n3 = 36064 + nArray[n2];
            }
            intBuffer2.put(i, n3);
        }
        intBuffer2.limit(intBuffer.limit());
        intBuffer2.position(intBuffer.position());
        return intBuffer2;
    }

    public String getInfo(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.drawBuffers.limit(); ++i) {
            Object object;
            int n = this.drawBuffers.get(i);
            int n2 = n - 36064;
            if (bl) {
                object = this.getAttachmentMappings();
                if (n2 >= 0 && n2 < ((int[])object).length) {
                    n2 = object[n2];
                }
            }
            object = this.getIndexName(n2);
            stringBuffer.append((String)object);
        }
        return stringBuffer.toString();
    }

    private String getIndexName(int n) {
        return n >= 0 && n < this.maxColorBuffers ? "" + n : "N";
    }

    public int indexOf(int n) {
        for (int i = 0; i < this.limit(); ++i) {
            if (this.get(i) != n) continue;
            return i;
        }
        return 1;
    }

    public String toString() {
        return this.name + ": " + BufferUtil.getBufferString(this.drawBuffers) + ", mapping: " + ArrayUtils.arrayToString(this.attachmentMappings) + ", glDrawBuffers: " + BufferUtil.getBufferString(this.glDrawBuffers);
    }
}

