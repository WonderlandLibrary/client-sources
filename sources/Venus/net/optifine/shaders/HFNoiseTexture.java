/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.ByteBuffer;
import net.optifine.shaders.ICustomTexture;
import net.optifine.util.TextureUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class HFNoiseTexture
implements ICustomTexture {
    private int texID = GL11.glGenTextures();
    private int textureUnit = 15;

    public HFNoiseTexture(int n, int n2) {
        byte[] byArray = this.genHFNoiseImage(n, n2);
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.flip();
        GlStateManager.bindTexture(this.texID);
        TextureUtils.resetDataUnpacking();
        GL11.glTexImage2D(3553, 0, 6407, n, n2, 0, 6407, 5121, byteBuffer);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10241, 9729);
        GlStateManager.bindTexture(0);
    }

    public int getID() {
        return this.texID;
    }

    @Override
    public void deleteTexture() {
        GlStateManager.deleteTexture(this.texID);
        this.texID = 0;
    }

    private int random(int n) {
        n ^= n << 13;
        n ^= n >> 17;
        return n ^ n << 5;
    }

    private byte random(int n, int n2, int n3) {
        int n4 = (this.random(n) + this.random(n2 * 19)) * this.random(n3 * 23) - n3;
        return (byte)(this.random(n4) % 128);
    }

    private byte[] genHFNoiseImage(int n, int n2) {
        byte[] byArray = new byte[n * n2 * 3];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int k = 1; k < 4; ++k) {
                    byArray[n3++] = this.random(j, i, k);
                }
            }
        }
        return byArray;
    }

    @Override
    public int getTextureId() {
        return this.texID;
    }

    @Override
    public int getTextureUnit() {
        return this.textureUnit;
    }
}

