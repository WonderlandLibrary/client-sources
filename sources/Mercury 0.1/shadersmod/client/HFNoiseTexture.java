/*
 * Decompiled with CFR 0.145.
 */
package shadersmod.client;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class HFNoiseTexture {
    public int texID = GL11.glGenTextures();
    public int textureUnit = 15;

    public HFNoiseTexture(int width, int height) {
        byte[] image = this.genHFNoiseImage(width, height);
        ByteBuffer data = BufferUtils.createByteBuffer(image.length);
        data.put(image);
        data.flip();
        GlStateManager.func_179144_i(this.texID);
        GL11.glTexImage2D(3553, 0, 6407, width, height, 0, 6407, 5121, data);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10241, 9729);
        GlStateManager.func_179144_i(0);
    }

    public int getID() {
        return this.texID;
    }

    public void destroy() {
        GlStateManager.func_179150_h(this.texID);
        this.texID = 0;
    }

    private int random(int seed) {
        seed ^= seed << 13;
        seed ^= seed >> 17;
        seed ^= seed << 5;
        return seed;
    }

    private byte random(int x2, int y2, int z2) {
        int seed = (this.random(x2) + this.random(y2 * 19)) * this.random(z2 * 23) - z2;
        return (byte)(this.random(seed) % 128);
    }

    private byte[] genHFNoiseImage(int width, int height) {
        byte[] image = new byte[width * height * 3];
        int index = 0;
        for (int y2 = 0; y2 < height; ++y2) {
            for (int x2 = 0; x2 < width; ++x2) {
                for (int z2 = 1; z2 < 4; ++z2) {
                    image[index++] = this.random(x2, y2, z2);
                }
            }
        }
        return image;
    }
}

