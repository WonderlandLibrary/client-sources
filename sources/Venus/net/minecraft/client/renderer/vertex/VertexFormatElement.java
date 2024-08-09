/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.function.IntConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Type type;
    private final Usage usage;
    private final int index;
    private final int elementCount;
    private final int sizeBytes;
    private String name;

    public VertexFormatElement(int n, Type type, Usage usage, int n2) {
        if (this.isFirstOrUV(n, usage)) {
            this.usage = usage;
        } else {
            LOGGER.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
            this.usage = Usage.UV;
        }
        this.type = type;
        this.index = n;
        this.elementCount = n2;
        this.sizeBytes = type.getSize() * this.elementCount;
    }

    private boolean isFirstOrUV(int n, Usage usage) {
        return n == 0 || usage == Usage.UV;
    }

    public final Type getType() {
        return this.type;
    }

    public final Usage getUsage() {
        return this.usage;
    }

    public final int getIndex() {
        return this.index;
    }

    public String toString() {
        return this.name != null ? this.name : this.elementCount + "," + this.usage.getDisplayName() + "," + this.type.getDisplayName();
    }

    public final int getSize() {
        return this.sizeBytes;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            VertexFormatElement vertexFormatElement = (VertexFormatElement)object;
            if (this.elementCount != vertexFormatElement.elementCount) {
                return true;
            }
            if (this.index != vertexFormatElement.index) {
                return true;
            }
            if (this.type != vertexFormatElement.type) {
                return true;
            }
            return this.usage == vertexFormatElement.usage;
        }
        return true;
    }

    public int hashCode() {
        int n = this.type.hashCode();
        n = 31 * n + this.usage.hashCode();
        n = 31 * n + this.index;
        return 31 * n + this.elementCount;
    }

    public void setupBufferState(long l, int n) {
        this.usage.setupBufferState(this.elementCount, this.type.getGlConstant(), n, l, this.index);
    }

    public void clearBufferState() {
        this.usage.clearBufferState(this.index);
    }

    public final int getElementCount() {
        return this.elementCount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public static enum Usage {
        POSITION("Position", Usage::lambda$static$0, Usage::lambda$static$1),
        NORMAL("Normal", Usage::lambda$static$2, Usage::lambda$static$3),
        COLOR("Vertex Color", Usage::lambda$static$4, Usage::lambda$static$5),
        UV("UV", Usage::lambda$static$6, Usage::lambda$static$7),
        PADDING("Padding", Usage::lambda$static$8, Usage::lambda$static$9),
        GENERIC("Generic", Usage::lambda$static$10, GlStateManager::glEnableVertexAttribArray);

        private final String displayName;
        private final ISetupState setupState;
        private final IntConsumer clearState;

        private Usage(String string2, ISetupState iSetupState, IntConsumer intConsumer) {
            this.displayName = string2;
            this.setupState = iSetupState;
            this.clearState = intConsumer;
        }

        private void setupBufferState(int n, int n2, int n3, long l, int n4) {
            this.setupState.setupBufferState(n, n2, n3, l, n4);
        }

        public void clearBufferState(int n) {
            this.clearState.accept(n);
        }

        public String getDisplayName() {
            return this.displayName;
        }

        private static void lambda$static$10(int n, int n2, int n3, long l, int n4) {
            GlStateManager.enableVertexAttribArray(n4);
            GlStateManager.vertexAttribPointer(n4, n, n2, false, n3, l);
        }

        private static void lambda$static$9(int n) {
        }

        private static void lambda$static$8(int n, int n2, int n3, long l, int n4) {
        }

        private static void lambda$static$7(int n) {
            GlStateManager.clientActiveTexture(33984 + n);
            GlStateManager.disableClientState(32888);
            GlStateManager.clientActiveTexture(33984);
        }

        private static void lambda$static$6(int n, int n2, int n3, long l, int n4) {
            GlStateManager.clientActiveTexture(33984 + n4);
            GlStateManager.texCoordPointer(n, n2, n3, l);
            GlStateManager.enableClientState(32888);
            GlStateManager.clientActiveTexture(33984);
        }

        private static void lambda$static$5(int n) {
            GlStateManager.disableClientState(32886);
            GlStateManager.clearCurrentColor();
        }

        private static void lambda$static$4(int n, int n2, int n3, long l, int n4) {
            GlStateManager.colorPointer(n, n2, n3, l);
            GlStateManager.enableClientState(32886);
        }

        private static void lambda$static$3(int n) {
            GlStateManager.disableClientState(32885);
        }

        private static void lambda$static$2(int n, int n2, int n3, long l, int n4) {
            GlStateManager.normalPointer(n2, n3, l);
            GlStateManager.enableClientState(32885);
        }

        private static void lambda$static$1(int n) {
            GlStateManager.disableClientState(32884);
        }

        private static void lambda$static$0(int n, int n2, int n3, long l, int n4) {
            GlStateManager.vertexPointer(n, n2, n3, l);
            GlStateManager.enableClientState(32884);
        }

        static interface ISetupState {
            public void setupBufferState(int var1, int var2, int var3, long var4, int var6);
        }
    }

    public static enum Type {
        FLOAT(4, "Float", 5126),
        UBYTE(1, "Unsigned Byte", 5121),
        BYTE(1, "Byte", 5120),
        USHORT(2, "Unsigned Short", 5123),
        SHORT(2, "Short", 5122),
        UINT(4, "Unsigned Int", 5125),
        INT(4, "Int", 5124);

        private final int size;
        private final String displayName;
        private final int glConstant;

        private Type(int n2, String string2, int n3) {
            this.size = n2;
            this.displayName = string2;
            this.glConstant = n3;
        }

        public int getSize() {
            return this.size;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public int getGlConstant() {
            return this.glConstant;
        }
    }
}

