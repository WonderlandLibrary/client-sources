/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.font;

import java.nio.ByteBuffer;

public class TextureData {
    private final int textureId;
    private final int width;
    private final int height;
    private final ByteBuffer buffer;

    public TextureData(int textureId, int width, int height, ByteBuffer buffer) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
        this.buffer = buffer;
    }

    public int getTextureId() {
        return this.textureId;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }
}

