/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class TextureUtil {
    private static final Logger LOGGER = LogManager.getLogger();

    public static int generateTextureId() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        if (SharedConstants.developmentMode) {
            int[] nArray = new int[ThreadLocalRandom.current().nextInt(15) + 1];
            GlStateManager.genTextures(nArray);
            int n = GlStateManager.genTexture();
            GlStateManager.deleteTextures(nArray);
            return n;
        }
        return GlStateManager.genTexture();
    }

    public static void releaseTextureId(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.deleteTexture(n);
    }

    public static void prepareImage(int n, int n2, int n3) {
        TextureUtil.prepareImage(NativeImage.PixelFormatGLCode.RGBA, n, 0, n2, n3);
    }

    public static void prepareImage(NativeImage.PixelFormatGLCode pixelFormatGLCode, int n, int n2, int n3) {
        TextureUtil.prepareImage(pixelFormatGLCode, n, 0, n2, n3);
    }

    public static void prepareImage(int n, int n2, int n3, int n4) {
        TextureUtil.prepareImage(NativeImage.PixelFormatGLCode.RGBA, n, n2, n3, n4);
    }

    public static void prepareImage(NativeImage.PixelFormatGLCode pixelFormatGLCode, int n, int n2, int n3, int n4) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        TextureUtil.bindTexture(n);
        if (n2 >= 0) {
            GlStateManager.texParameter(3553, 33085, n2);
            GlStateManager.texParameter(3553, 33082, 0);
            GlStateManager.texParameter(3553, 33083, n2);
            GlStateManager.texParameter(3553, 34049, 0.0f);
        }
        for (int i = 0; i <= n2; ++i) {
            GlStateManager.texImage2D(3553, i, pixelFormatGLCode.getGlFormat(), n3 >> i, n4 >> i, 0, 6408, 5121, null);
        }
    }

    private static void bindTexture(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
        GlStateManager.bindTexture(n);
    }

    public static ByteBuffer readToBuffer(InputStream inputStream) throws IOException {
        ByteBuffer byteBuffer;
        if (inputStream instanceof FileInputStream) {
            FileInputStream fileInputStream = (FileInputStream)inputStream;
            FileChannel fileChannel = fileInputStream.getChannel();
            byteBuffer = MemoryUtil.memAlloc((int)fileChannel.size() + 1);
            while (fileChannel.read(byteBuffer) != -1) {
            }
        } else {
            byteBuffer = MemoryUtil.memAlloc(8192);
            ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
            while (readableByteChannel.read(byteBuffer) != -1) {
                if (byteBuffer.remaining() != 0) continue;
                byteBuffer = MemoryUtil.memRealloc(byteBuffer, byteBuffer.capacity() * 2);
            }
        }
        return byteBuffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String readResourceAsString(InputStream inputStream) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        ByteBuffer byteBuffer = null;
        try {
            byteBuffer = TextureUtil.readToBuffer(inputStream);
            int n = byteBuffer.position();
            byteBuffer.rewind();
            String string = MemoryUtil.memASCII(byteBuffer, n);
            return string;
        } catch (IOException iOException) {
        } finally {
            if (byteBuffer != null) {
                MemoryUtil.memFree(byteBuffer);
            }
        }
        return null;
    }

    public static void initTexture(IntBuffer intBuffer, int n, int n2) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GL11.glPixelStorei(3312, 0);
        GL11.glPixelStorei(3313, 0);
        GL11.glPixelStorei(3314, 0);
        GL11.glPixelStorei(3315, 0);
        GL11.glPixelStorei(3316, 0);
        GL11.glPixelStorei(3317, 4);
        GL11.glTexImage2D(3553, 0, 6408, n, n2, 0, 32993, 33639, intBuffer);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 10240, 9728);
        GL11.glTexParameteri(3553, 10241, 9729);
    }
}

