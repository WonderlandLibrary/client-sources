/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Base64;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.util.LWJGLMemoryUntracker;
import net.optifine.Config;
import net.optifine.util.NativeMemory;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBIWriteCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public final class NativeImage
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<StandardOpenOption> OPEN_OPTIONS = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    private final PixelFormat pixelFormat;
    private final int width;
    private final int height;
    private final boolean stbiPointer;
    private long imagePointer;
    private final long size;

    public NativeImage(int n, int n2, boolean bl) {
        this(PixelFormat.RGBA, n, n2, bl);
    }

    public NativeImage(PixelFormat pixelFormat, int n, int n2, boolean bl) {
        this.pixelFormat = pixelFormat;
        this.width = n;
        this.height = n2;
        this.size = (long)n * (long)n2 * (long)pixelFormat.getPixelSize();
        this.stbiPointer = false;
        this.imagePointer = bl ? MemoryUtil.nmemCalloc(1L, this.size) : MemoryUtil.nmemAlloc(this.size);
        this.checkImage();
        NativeMemory.imageAllocated(this);
    }

    private NativeImage(PixelFormat pixelFormat, int n, int n2, boolean bl, long l) {
        this.pixelFormat = pixelFormat;
        this.width = n;
        this.height = n2;
        this.stbiPointer = bl;
        this.imagePointer = l;
        this.size = n * n2 * pixelFormat.getPixelSize();
    }

    public String toString() {
        return "NativeImage[" + this.pixelFormat + " " + this.width + "x" + this.height + "@" + this.imagePointer + (this.stbiPointer ? "S" : "N") + "]";
    }

    public static NativeImage read(InputStream inputStream) throws IOException {
        return NativeImage.read(PixelFormat.RGBA, inputStream);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NativeImage read(@Nullable PixelFormat pixelFormat, InputStream inputStream) throws IOException {
        NativeImage nativeImage;
        ByteBuffer byteBuffer = null;
        try {
            byteBuffer = TextureUtil.readToBuffer(inputStream);
            byteBuffer.rewind();
            nativeImage = NativeImage.read(pixelFormat, byteBuffer);
        } finally {
            MemoryUtil.memFree(byteBuffer);
            IOUtils.closeQuietly(inputStream);
        }
        return nativeImage;
    }

    public static NativeImage read(ByteBuffer byteBuffer) throws IOException {
        return NativeImage.read(PixelFormat.RGBA, byteBuffer);
    }

    public static NativeImage read(@Nullable PixelFormat pixelFormat, ByteBuffer byteBuffer) throws IOException {
        NativeImage nativeImage;
        if (pixelFormat != null && !pixelFormat.isSerializable()) {
            throw new UnsupportedOperationException("Don't know how to read format " + pixelFormat);
        }
        if (MemoryUtil.memAddress(byteBuffer) == 0L) {
            throw new IllegalArgumentException("Invalid buffer");
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            IntBuffer intBuffer3 = memoryStack.mallocInt(1);
            ByteBuffer byteBuffer2 = STBImage.stbi_load_from_memory(byteBuffer, intBuffer, intBuffer2, intBuffer3, pixelFormat == null ? 0 : pixelFormat.pixelSize);
            if (byteBuffer2 == null) {
                throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }
            nativeImage = new NativeImage(pixelFormat == null ? PixelFormat.fromChannelCount(intBuffer3.get(0)) : pixelFormat, intBuffer.get(0), intBuffer2.get(0), true, MemoryUtil.memAddress(byteBuffer2));
            NativeMemory.imageAllocated(nativeImage);
        }
        return nativeImage;
    }

    public static void setWrapST(boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (bl) {
            GlStateManager.texParameter(3553, 10242, 33071);
            GlStateManager.texParameter(3553, 10243, 33071);
        } else {
            GlStateManager.texParameter(3553, 10242, 10497);
            GlStateManager.texParameter(3553, 10243, 10497);
        }
    }

    public static void setMinMagFilters(boolean bl, boolean bl2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (bl) {
            GlStateManager.texParameter(3553, 10241, bl2 ? 9987 : 9729);
            GlStateManager.texParameter(3553, 10240, 9729);
        } else {
            int n = Config.getMipmapType();
            GlStateManager.texParameter(3553, 10241, bl2 ? n : 9728);
            GlStateManager.texParameter(3553, 10240, 9728);
        }
    }

    private void checkImage() {
        if (this.imagePointer == 0L) {
            throw new IllegalStateException("Image is not allocated.");
        }
    }

    @Override
    public void close() {
        if (this.imagePointer != 0L) {
            if (this.stbiPointer) {
                STBImage.nstbi_image_free(this.imagePointer);
            } else {
                MemoryUtil.nmemFree(this.imagePointer);
            }
            NativeMemory.imageFreed(this);
        }
        this.imagePointer = 0L;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public PixelFormat getFormat() {
        return this.pixelFormat;
    }

    public int getPixelRGBA(int n, int n2) {
        if (this.pixelFormat != PixelFormat.RGBA) {
            throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", new Object[]{this.pixelFormat}));
        }
        if (n >= 0 && n2 >= 0 && n < this.width && n2 < this.height) {
            this.checkImage();
            long l = (n + n2 * this.width) * 4;
            return MemoryUtil.memGetInt(this.imagePointer + l);
        }
        throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", n, n2, this.width, this.height));
    }

    public void setPixelRGBA(int n, int n2, int n3) {
        if (this.pixelFormat != PixelFormat.RGBA) {
            throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", new Object[]{this.pixelFormat}));
        }
        if (n < 0 || n2 < 0 || n >= this.width || n2 >= this.height) {
            throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", n, n2, this.width, this.height));
        }
        this.checkImage();
        long l = (n + n2 * this.width) * 4;
        MemoryUtil.memPutInt(this.imagePointer + l, n3);
    }

    public byte getPixelLuminanceOrAlpha(int n, int n2) {
        if (!this.pixelFormat.hasLuminanceOrAlpha()) {
            throw new IllegalArgumentException(String.format("no luminance or alpha in %s", new Object[]{this.pixelFormat}));
        }
        if (n >= 0 && n2 >= 0 && n < this.width && n2 < this.height) {
            int n3 = (n + n2 * this.width) * this.pixelFormat.getPixelSize() + this.pixelFormat.getOffsetAlphaBits() / 8;
            return MemoryUtil.memGetByte(this.imagePointer + (long)n3);
        }
        throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", n, n2, this.width, this.height));
    }

    @Deprecated
    public int[] makePixelArray() {
        if (this.pixelFormat != PixelFormat.RGBA) {
            throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
        }
        this.checkImage();
        int[] nArray = new int[this.getWidth() * this.getHeight()];
        for (int i = 0; i < this.getHeight(); ++i) {
            for (int j = 0; j < this.getWidth(); ++j) {
                int n;
                int n2 = this.getPixelRGBA(j, i);
                int n3 = NativeImage.getAlpha(n2);
                int n4 = NativeImage.getBlue(n2);
                int n5 = NativeImage.getGreen(n2);
                int n6 = NativeImage.getRed(n2);
                nArray[j + i * this.getWidth()] = n = n3 << 24 | n6 << 16 | n5 << 8 | n4;
            }
        }
        return nArray;
    }

    public void uploadTextureSub(int n, int n2, int n3, boolean bl) {
        this.uploadTextureSub(n, n2, n3, 0, 0, this.width, this.height, false, bl);
    }

    public void uploadTextureSub(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, boolean bl2) {
        this.uploadTextureSub(n, n2, n3, n4, n5, n6, n7, false, false, bl, bl2);
    }

    public void uploadTextureSub(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> this.lambda$uploadTextureSub$0(n, n2, n3, n4, n5, n6, n7, bl, bl2, bl3, bl4));
        } else {
            this.uploadTextureSubRaw(n, n2, n3, n4, n5, n6, n7, bl, bl2, bl3, bl4);
        }
    }

    private void uploadTextureSubRaw(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        this.checkImage();
        NativeImage.setMinMagFilters(bl, bl3);
        NativeImage.setWrapST(bl2);
        if (n6 == this.getWidth()) {
            GlStateManager.pixelStore(3314, 0);
        } else {
            GlStateManager.pixelStore(3314, this.getWidth());
        }
        GlStateManager.pixelStore(3316, n4);
        GlStateManager.pixelStore(3315, n5);
        this.pixelFormat.setGlUnpackAlignment();
        GlStateManager.texSubImage2D(3553, n, n2, n3, n6, n7, this.pixelFormat.getGlFormat(), 5121, this.imagePointer);
        if (bl4) {
            this.close();
        }
    }

    public void downloadFromTexture(int n, boolean bl) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        this.checkImage();
        this.pixelFormat.setGlPackAlignment();
        GlStateManager.getTexImage(3553, n, this.pixelFormat.getGlFormat(), 5121, this.imagePointer);
        if (bl && this.pixelFormat.hasAlpha()) {
            for (int i = 0; i < this.getHeight(); ++i) {
                for (int j = 0; j < this.getWidth(); ++j) {
                    this.setPixelRGBA(j, i, this.getPixelRGBA(j, i) | 255 << this.pixelFormat.getOffsetAlpha());
                }
            }
        }
    }

    public void write(File file) throws IOException {
        this.write(file.toPath());
    }

    public void renderGlyph(STBTTFontinfo sTBTTFontinfo, int n, int n2, int n3, float f, float f2, float f3, float f4, int n4, int n5) {
        if (n4 >= 0 && n4 + n2 <= this.getWidth() && n5 >= 0 && n5 + n3 <= this.getHeight()) {
            if (this.pixelFormat.getPixelSize() != 1) {
                throw new IllegalArgumentException("Can only write fonts into 1-component images.");
            }
        } else {
            throw new IllegalArgumentException(String.format("Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", n4, n5, n2, n3, this.getWidth(), this.getHeight()));
        }
        STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(sTBTTFontinfo.address(), this.imagePointer + (long)n4 + (long)(n5 * this.getWidth()), n2, n3, this.getWidth(), f, f2, f3, f4, n);
    }

    public void write(Path path) throws IOException {
        if (!this.pixelFormat.isSerializable()) {
            throw new UnsupportedOperationException("Don't know how to write format " + this.pixelFormat);
        }
        this.checkImage();
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, OPEN_OPTIONS, new FileAttribute[0]);){
            if (!this.write(seekableByteChannel)) {
                throw new IOException("Could not write image to the PNG file \"" + path.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
            }
        }
    }

    public byte[] getBytes() throws IOException {
        byte[] byArray;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             WritableByteChannel writableByteChannel = Channels.newChannel(byteArrayOutputStream);){
            if (!this.write(writableByteChannel)) {
                throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
            }
            byArray = byteArrayOutputStream.toByteArray();
        }
        return byArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean write(WritableByteChannel writableByteChannel) throws IOException {
        boolean bl;
        WriteCallback writeCallback = new WriteCallback(writableByteChannel);
        try {
            int n = Math.min(this.getHeight(), Integer.MAX_VALUE / this.getWidth() / this.pixelFormat.getPixelSize());
            if (n < this.getHeight()) {
                LOGGER.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", (Object)this.getHeight(), (Object)n);
            }
            if (STBImageWrite.nstbi_write_png_to_func(writeCallback.address(), 0L, this.getWidth(), n, this.pixelFormat.getPixelSize(), this.imagePointer, 0) == 0) {
                boolean bl2 = false;
                return bl2;
            }
            writeCallback.propagateException();
            bl = true;
        } finally {
            writeCallback.free();
        }
        return bl;
    }

    public void copyImageData(NativeImage nativeImage) {
        if (nativeImage.getFormat() != this.pixelFormat) {
            throw new UnsupportedOperationException("Image formats don't match.");
        }
        int n = this.pixelFormat.getPixelSize();
        this.checkImage();
        nativeImage.checkImage();
        if (this.width == nativeImage.width) {
            MemoryUtil.memCopy(nativeImage.imagePointer, this.imagePointer, Math.min(this.size, nativeImage.size));
        } else {
            int n2 = Math.min(this.getWidth(), nativeImage.getWidth());
            int n3 = Math.min(this.getHeight(), nativeImage.getHeight());
            for (int i = 0; i < n3; ++i) {
                int n4 = i * nativeImage.getWidth() * n;
                int n5 = i * this.getWidth() * n;
                MemoryUtil.memCopy(nativeImage.imagePointer + (long)n4, this.imagePointer + (long)n5, (long)n2 * (long)n);
            }
        }
    }

    public void fillAreaRGBA(int n, int n2, int n3, int n4, int n5) {
        for (int i = n2; i < n2 + n4; ++i) {
            for (int j = n; j < n + n3; ++j) {
                this.setPixelRGBA(j, i, n5);
            }
        }
    }

    public void copyAreaRGBA(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, boolean bl2) {
        for (int i = 0; i < n6; ++i) {
            for (int j = 0; j < n5; ++j) {
                int n7 = bl ? n5 - 1 - j : j;
                int n8 = bl2 ? n6 - 1 - i : i;
                int n9 = this.getPixelRGBA(n + j, n2 + i);
                this.setPixelRGBA(n + n3 + n7, n2 + n4 + n8, n9);
            }
        }
    }

    public void flip() {
        this.checkImage();
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            int n = this.pixelFormat.getPixelSize();
            int n2 = this.getWidth() * n;
            long l = memoryStack.nmalloc(n2);
            for (int i = 0; i < this.getHeight() / 2; ++i) {
                int n3 = i * this.getWidth() * n;
                int n4 = (this.getHeight() - 1 - i) * this.getWidth() * n;
                MemoryUtil.memCopy(this.imagePointer + (long)n3, l, n2);
                MemoryUtil.memCopy(this.imagePointer + (long)n4, this.imagePointer + (long)n3, n2);
                MemoryUtil.memCopy(l, this.imagePointer + (long)n4, n2);
            }
        }
    }

    public void resizeSubRectTo(int n, int n2, int n3, int n4, NativeImage nativeImage) {
        this.checkImage();
        if (nativeImage.getFormat() != this.pixelFormat) {
            throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
        }
        int n5 = this.pixelFormat.getPixelSize();
        STBImageResize.nstbir_resize_uint8(this.imagePointer + (long)((n + n2 * this.getWidth()) * n5), n3, n4, this.getWidth() * n5, nativeImage.imagePointer, nativeImage.getWidth(), nativeImage.getHeight(), 0, n5);
    }

    public void untrack() {
        LWJGLMemoryUntracker.untrack(this.imagePointer);
    }

    public static NativeImage readBase64(String string) throws IOException {
        NativeImage nativeImage;
        byte[] byArray = Base64.getDecoder().decode(string.replaceAll("\n", "").getBytes(Charsets.UTF_8));
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            ByteBuffer byteBuffer = memoryStack.malloc(byArray.length);
            byteBuffer.put(byArray);
            byteBuffer.rewind();
            nativeImage = NativeImage.read(byteBuffer);
        }
        return nativeImage;
    }

    public static int getAlpha(int n) {
        return n >> 24 & 0xFF;
    }

    public static int getRed(int n) {
        return n >> 0 & 0xFF;
    }

    public static int getGreen(int n) {
        return n >> 8 & 0xFF;
    }

    public static int getBlue(int n) {
        return n >> 16 & 0xFF;
    }

    public static int getCombined(int n, int n2, int n3, int n4) {
        return (n & 0xFF) << 24 | (n2 & 0xFF) << 16 | (n3 & 0xFF) << 8 | (n4 & 0xFF) << 0;
    }

    public IntBuffer getBufferRGBA() {
        if (this.pixelFormat != PixelFormat.RGBA) {
            throw new IllegalArgumentException(String.format("getBuffer only works on RGBA images; have %s", new Object[]{this.pixelFormat}));
        }
        this.checkImage();
        return MemoryUtil.memIntBuffer(this.imagePointer, (int)this.size);
    }

    public void fillRGBA(int n) {
        if (this.pixelFormat != PixelFormat.RGBA) {
            throw new IllegalArgumentException(String.format("getBuffer only works on RGBA images; have %s", new Object[]{this.pixelFormat}));
        }
        this.checkImage();
        MemoryUtil.memSet(this.imagePointer, n, this.size);
    }

    public long getSize() {
        return this.size;
    }

    public void downloadFromFramebuffer(boolean bl) {
        this.checkImage();
        this.pixelFormat.setGlPackAlignment();
        if (bl) {
            GlStateManager.pixelTransfer(3357, Float.MAX_VALUE);
        }
        GlStateManager.readPixels(0, 0, this.width, this.height, this.pixelFormat.getGlFormat(), 5121, this.imagePointer);
        if (bl) {
            GlStateManager.pixelTransfer(3357, 0.0f);
        }
    }

    private void lambda$uploadTextureSub$0(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.uploadTextureSubRaw(n, n2, n3, n4, n5, n6, n7, bl, bl2, bl3, bl4);
    }

    public static enum PixelFormat {
        RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
        RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
        LUMINANCE_ALPHA(2, 6410, false, false, false, true, true, 255, 255, 255, 0, 8, true),
        LUMINANCE(1, 6409, false, false, false, true, false, 0, 0, 0, 0, 255, true);

        private final int pixelSize;
        private final int glFormat;
        private final boolean red;
        private final boolean green;
        private final boolean blue;
        private final boolean hasLuminance;
        private final boolean hasAlpha;
        private final int offsetRed;
        private final int offsetGreen;
        private final int offsetBlue;
        private final int offsetLuminance;
        private final int offsetAlpha;
        private final boolean serializable;

        private PixelFormat(int n2, int n3, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, int n4, int n5, int n6, int n7, int n8, boolean bl6) {
            this.pixelSize = n2;
            this.glFormat = n3;
            this.red = bl;
            this.green = bl2;
            this.blue = bl3;
            this.hasLuminance = bl4;
            this.hasAlpha = bl5;
            this.offsetRed = n4;
            this.offsetGreen = n5;
            this.offsetBlue = n6;
            this.offsetLuminance = n7;
            this.offsetAlpha = n8;
            this.serializable = bl6;
        }

        public int getPixelSize() {
            return this.pixelSize;
        }

        public void setGlPackAlignment() {
            RenderSystem.assertThread(RenderSystem::isOnRenderThread);
            GlStateManager.pixelStore(3333, this.getPixelSize());
        }

        public void setGlUnpackAlignment() {
            RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
            GlStateManager.pixelStore(3317, this.getPixelSize());
        }

        public int getGlFormat() {
            return this.glFormat;
        }

        public boolean hasAlpha() {
            return this.hasAlpha;
        }

        public int getOffsetAlpha() {
            return this.offsetAlpha;
        }

        public boolean hasLuminanceOrAlpha() {
            return this.hasLuminance || this.hasAlpha;
        }

        public int getOffsetAlphaBits() {
            return this.hasLuminance ? this.offsetLuminance : this.offsetAlpha;
        }

        public boolean isSerializable() {
            return this.serializable;
        }

        private static PixelFormat fromChannelCount(int n) {
            switch (n) {
                case 1: {
                    return LUMINANCE;
                }
                case 2: {
                    return LUMINANCE_ALPHA;
                }
                case 3: {
                    return RGB;
                }
            }
            return RGBA;
        }
    }

    static class WriteCallback
    extends STBIWriteCallback {
        private final WritableByteChannel channel;
        @Nullable
        private IOException exception;

        private WriteCallback(WritableByteChannel writableByteChannel) {
            this.channel = writableByteChannel;
        }

        @Override
        public void invoke(long l, long l2, int n) {
            ByteBuffer byteBuffer = WriteCallback.getData(l2, n);
            try {
                this.channel.write(byteBuffer);
            } catch (IOException iOException) {
                this.exception = iOException;
            }
        }

        public void propagateException() throws IOException {
            if (this.exception != null) {
                throw this.exception;
            }
        }
    }

    public static enum PixelFormatGLCode {
        RGBA(6408),
        RGB(6407),
        LUMINANCE_ALPHA(6410),
        LUMINANCE(6409),
        INTENSITY(32841);

        private final int glConstant;

        private PixelFormatGLCode(int n2) {
            this.glConstant = n2;
        }

        int getGlFormat() {
            return this.glConstant;
        }
    }
}

