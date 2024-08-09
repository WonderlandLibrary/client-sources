/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Dimension;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.vector.Vector4f;
import net.optifine.shaders.DrawBuffers;
import net.optifine.shaders.FixedFramebuffer;
import net.optifine.shaders.FlipTextures;
import net.optifine.shaders.GlState;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import net.optifine.util.ArrayUtils;
import net.optifine.util.CompoundIntKey;
import net.optifine.util.CompoundKey;
import net.optifine.util.DynamicDimension;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ShadersFramebuffer {
    private String name;
    private int width;
    private int height;
    private int usedColorBuffers;
    private int usedDepthBuffers;
    private int maxDrawBuffers;
    private boolean[] depthFilterNearest;
    private boolean[] depthFilterHardware;
    private boolean[] colorFilterNearest;
    private DynamicDimension[] colorBufferSizes;
    private int[] buffersFormat;
    private int[] colorTextureUnits;
    private int[] depthTextureUnits;
    private int[] colorImageUnits;
    private int glFramebuffer;
    private FlipTextures colorTexturesFlip;
    private IntBuffer depthTextures;
    private final DrawBuffers drawBuffers;
    private DrawBuffers activeDrawBuffers;
    private int[] drawColorTextures;
    private int[] drawColorTexturesMap;
    private boolean[] dirtyColorTextures;
    private Map<CompoundKey, FixedFramebuffer> fixedFramebuffers = new HashMap<CompoundKey, FixedFramebuffer>();

    public ShadersFramebuffer(String string, int n, int n2, int n3, int n4, int n5, boolean[] blArray, boolean[] blArray2, boolean[] blArray3, DynamicDimension[] dynamicDimensionArray, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4, DrawBuffers drawBuffers) {
        this.name = string;
        this.width = n;
        this.height = n2;
        this.usedColorBuffers = n3;
        this.usedDepthBuffers = n4;
        this.maxDrawBuffers = n5;
        this.depthFilterNearest = blArray;
        this.depthFilterHardware = blArray2;
        this.colorFilterNearest = blArray3;
        this.colorBufferSizes = dynamicDimensionArray;
        this.buffersFormat = nArray;
        this.colorTextureUnits = nArray2;
        this.depthTextureUnits = nArray3;
        this.colorImageUnits = nArray4;
        this.drawBuffers = drawBuffers;
    }

    public void setup() {
        Dimension dimension;
        int n;
        int n2;
        if (this.exists()) {
            this.delete();
        }
        this.colorTexturesFlip = new FlipTextures(this.name + "ColorTexturesFlip", this.usedColorBuffers);
        this.depthTextures = BufferUtils.createIntBuffer(this.usedDepthBuffers);
        this.drawColorTextures = new int[this.usedColorBuffers];
        this.drawColorTexturesMap = new int[this.usedColorBuffers];
        this.dirtyColorTextures = new boolean[this.maxDrawBuffers];
        Arrays.fill(this.drawColorTextures, 0);
        Arrays.fill(this.drawColorTexturesMap, -1);
        Arrays.fill(this.dirtyColorTextures, false);
        for (n2 = 0; n2 < this.drawBuffers.limit(); ++n2) {
            this.drawBuffers.put(n2, 36064 + n2);
        }
        this.glFramebuffer = EXTFramebufferObject.glGenFramebuffersEXT();
        this.bindFramebuffer();
        GL30.glDrawBuffers(0);
        GL30.glReadBuffer(0);
        GL30.glGenTextures((IntBuffer)this.depthTextures.clear().limit(this.usedDepthBuffers));
        this.colorTexturesFlip.clear().limit(this.usedColorBuffers).genTextures();
        this.depthTextures.position(0);
        this.colorTexturesFlip.position(0);
        for (n2 = 0; n2 < this.usedDepthBuffers; ++n2) {
            GlStateManager.bindTexture(this.depthTextures.get(n2));
            GL30.glTexParameteri(3553, 10242, 33071);
            GL30.glTexParameteri(3553, 10243, 33071);
            n = this.depthFilterNearest[n2] ? 9728 : 9729;
            GL30.glTexParameteri(3553, 10241, n);
            GL30.glTexParameteri(3553, 10240, n);
            if (this.depthFilterHardware[n2]) {
                GL30.glTexParameteri(3553, 34892, 34894);
            }
            GL30.glTexImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, (FloatBuffer)null);
        }
        this.setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
        Shaders.checkGLError("FBS " + this.name + " depth");
        for (n2 = 0; n2 < this.usedColorBuffers; ++n2) {
            GlStateManager.bindTexture(this.colorTexturesFlip.getA(n2));
            GL30.glTexParameteri(3553, 10242, 33071);
            GL30.glTexParameteri(3553, 10243, 33071);
            n = this.colorFilterNearest[n2] ? 9728 : 9729;
            GL30.glTexParameteri(3553, 10241, n);
            GL30.glTexParameteri(3553, 10240, n);
            dimension = this.colorBufferSizes[n2] != null ? this.colorBufferSizes[n2].getDimension(this.width, this.height) : new Dimension(this.width, this.height);
            GL30.glTexImage2D(3553, 0, this.buffersFormat[n2], dimension.width, dimension.height, 0, Shaders.getPixelFormat(this.buffersFormat[n2]), 33639, (ByteBuffer)null);
            this.setFramebufferTexture2D(36160, 36064 + n2, 3553, this.colorTexturesFlip.getA(n2), 0);
            Shaders.checkGLError("FBS " + this.name + " colorA");
        }
        for (n2 = 0; n2 < this.usedColorBuffers; ++n2) {
            GlStateManager.bindTexture(this.colorTexturesFlip.getB(n2));
            GL30.glTexParameteri(3553, 10242, 33071);
            GL30.glTexParameteri(3553, 10243, 33071);
            n = this.colorFilterNearest[n2] ? 9728 : 9729;
            GL30.glTexParameteri(3553, 10241, n);
            GL30.glTexParameteri(3553, 10240, n);
            dimension = this.colorBufferSizes[n2] != null ? this.colorBufferSizes[n2].getDimension(this.width, this.height) : new Dimension(this.width, this.height);
            GL30.glTexImage2D(3553, 0, this.buffersFormat[n2], dimension.width, dimension.height, 0, Shaders.getPixelFormat(this.buffersFormat[n2]), 33639, (ByteBuffer)null);
            Shaders.checkGLError("FBS " + this.name + " colorB");
        }
        GlStateManager.bindTexture(0);
        if (this.usedColorBuffers > 0) {
            this.setDrawBuffers(this.drawBuffers);
            GL30.glReadBuffer(0);
        }
        if ((n2 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160)) != 36053) {
            Shaders.printChatAndLogError("[Shaders] Error creating framebuffer: " + this.name + ", status: " + n2);
        } else {
            SMCLog.info("Framebuffer created: " + this.name);
        }
    }

    public void delete() {
        if (this.glFramebuffer != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(this.glFramebuffer);
            this.glFramebuffer = 0;
        }
        if (this.colorTexturesFlip != null) {
            this.colorTexturesFlip.deleteTextures();
            this.colorTexturesFlip = null;
        }
        if (this.depthTextures != null) {
            GlStateManager.deleteTextures(this.depthTextures);
            this.depthTextures = null;
        }
        this.drawBuffers.position(0).fill(0);
        for (FixedFramebuffer fixedFramebuffer : this.fixedFramebuffers.values()) {
            fixedFramebuffer.delete();
        }
        this.fixedFramebuffers.clear();
    }

    public String getName() {
        return this.name;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getGlFramebuffer() {
        return this.glFramebuffer;
    }

    public boolean exists() {
        return this.glFramebuffer != 0;
    }

    public void bindFramebuffer() {
        GlState.bindFramebuffer(this);
    }

    public void setColorTextures(boolean bl) {
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            this.setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.get(bl, i), 0);
        }
    }

    public void setDepthTexture() {
        this.setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
    }

    public void setColorBuffersFiltering(int n, int n2) {
        GlStateManager.activeTexture(33984);
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            GlStateManager.bindTexture(this.colorTexturesFlip.getA(i));
            GL11.glTexParameteri(3553, 10241, n);
            GL11.glTexParameteri(3553, 10240, n2);
            GlStateManager.bindTexture(this.colorTexturesFlip.getB(i));
            GL11.glTexParameteri(3553, 10241, n);
            GL11.glTexParameteri(3553, 10240, n2);
        }
        GlStateManager.bindTexture(0);
    }

    public void setFramebufferTexture2D(int n, int n2, int n3, int n4, int n5) {
        int n6 = n2 - 36064;
        if (this.isColorBufferIndex(n6)) {
            if (this.colorBufferSizes[n6] != null) {
                if (this.isColorExtendedIndex(n6)) {
                    return;
                }
                n4 = 0;
            }
            this.drawColorTextures[n6] = n4;
            if (n6 >= this.maxDrawBuffers) {
                int n7 = this.drawColorTexturesMap[n6];
                if (!this.isDrawBufferIndex(n7)) {
                    return;
                }
                n2 = 36064 + n7;
            }
        }
        this.bindFramebuffer();
        EXTFramebufferObject.glFramebufferTexture2DEXT(n, n2, n3, n4, n5);
    }

    public boolean isColorBufferIndex(int n) {
        return n >= 0 && n < this.usedColorBuffers;
    }

    public boolean isColorExtendedIndex(int n) {
        return n >= this.maxDrawBuffers && n < this.usedColorBuffers;
    }

    public boolean isDrawBufferIndex(int n) {
        return n >= 0 && n < this.maxDrawBuffers;
    }

    private void setDrawColorTexturesMap(int[] nArray) {
        int n;
        int n2;
        this.bindFramebuffer();
        for (n2 = 0; n2 < this.maxDrawBuffers; ++n2) {
            if (!this.dirtyColorTextures[n2]) continue;
            n = this.drawColorTextures[n2];
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + n2, 3553, n, 0);
            this.dirtyColorTextures[n2] = false;
        }
        this.drawColorTexturesMap = nArray;
        for (n2 = this.maxDrawBuffers; n2 < this.drawColorTexturesMap.length; ++n2) {
            n = this.drawColorTexturesMap[n2];
            if (n < 0) continue;
            int n3 = this.drawColorTextures[n2];
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + n, 3553, n3, 0);
            this.dirtyColorTextures[n] = true;
        }
    }

    public void setDrawBuffers(DrawBuffers drawBuffers) {
        if (drawBuffers == null) {
            drawBuffers = Shaders.drawBuffersNone;
        }
        this.setDrawColorTexturesMap(drawBuffers.getAttachmentMappings());
        this.activeDrawBuffers = drawBuffers;
        this.bindFramebuffer();
        GL30.glDrawBuffers(drawBuffers.getGlDrawBuffers());
        Shaders.checkGLError("setDrawBuffers");
    }

    public void setDrawBuffers() {
        this.setDrawBuffers(this.drawBuffers);
    }

    public DrawBuffers getDrawBuffers() {
        return this.activeDrawBuffers;
    }

    public void bindDepthTextures(int[] nArray) {
        for (int i = 0; i < this.usedDepthBuffers; ++i) {
            GlStateManager.activeTexture(33984 + nArray[i]);
            GlStateManager.bindTexture(this.depthTextures.get(i));
        }
        GlStateManager.activeTexture(33984);
    }

    public void bindColorTextures(int n) {
        for (int i = n; i < this.usedColorBuffers; ++i) {
            GlStateManager.activeTexture(33984 + this.colorTextureUnits[i]);
            GlStateManager.bindTexture(this.colorTexturesFlip.getA(i));
            this.bindColorImage(i, false);
        }
    }

    public void bindColorImages(boolean bl) {
        if (this.colorImageUnits != null) {
            for (int i = 0; i < this.usedColorBuffers; ++i) {
                this.bindColorImage(i, bl);
            }
        }
    }

    public void bindColorImage(int n, boolean bl) {
        if (this.colorImageUnits != null) {
            if (n >= 0 && n < this.colorImageUnits.length) {
                int n2 = Shaders.getImageFormat(this.buffersFormat[n]);
                GlStateManager.bindImageTexture(this.colorImageUnits[n], this.colorTexturesFlip.get(bl, n), 0, false, 0, 35002, n2);
            }
            GlStateManager.activeTexture(33984);
        }
    }

    public void generateDepthMipmaps(boolean[] blArray) {
        for (int i = 0; i < this.usedDepthBuffers; ++i) {
            if (!blArray[i]) continue;
            GlStateManager.activeTexture(33984 + this.depthTextureUnits[i]);
            GlStateManager.bindTexture(this.depthTextures.get(i));
            GL30.glGenerateMipmap(3553);
            GL30.glTexParameteri(3553, 10241, this.depthFilterNearest[i] ? 9984 : 9987);
        }
        GlStateManager.activeTexture(33984);
    }

    public void generateColorMipmaps(boolean bl, boolean[] blArray) {
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            if (!blArray[i]) continue;
            GlStateManager.activeTexture(33984 + this.colorTextureUnits[i]);
            GlStateManager.bindTexture(this.colorTexturesFlip.get(bl, i));
            GL30.glGenerateMipmap(3553);
            GL30.glTexParameteri(3553, 10241, this.colorFilterNearest[i] ? 9984 : 9987);
        }
        GlStateManager.activeTexture(33984);
    }

    public void genCompositeMipmap(int n) {
        if (Shaders.hasGlGenMipmap) {
            for (int i = 0; i < this.usedColorBuffers; ++i) {
                if ((n & 1 << i) == 0) continue;
                GlStateManager.activeTexture(33984 + this.colorTextureUnits[i]);
                GL30.glTexParameteri(3553, 10241, 9987);
                GL30.glGenerateMipmap(3553);
            }
            GlStateManager.activeTexture(33984);
        }
    }

    public void flipColorTextures(boolean[] blArray) {
        for (int i = 0; i < this.colorTexturesFlip.limit(); ++i) {
            if (!blArray[i]) continue;
            this.flipColorTexture(i);
        }
    }

    public void flipColorTexture(int n) {
        this.colorTexturesFlip.flip(n);
        GlStateManager.activeTexture(33984 + this.colorTextureUnits[n]);
        GlStateManager.bindTexture(this.colorTexturesFlip.getA(n));
        this.bindColorImage(n, false);
        this.setFramebufferTexture2D(36160, 36064 + n, 3553, this.colorTexturesFlip.getB(n), 0);
        GlStateManager.activeTexture(33984);
    }

    public void clearColorBuffers(boolean[] blArray, Vector4f[] vector4fArray) {
        for (int i = 0; i < this.usedColorBuffers; ++i) {
            if (!blArray[i]) continue;
            Vector4f vector4f = vector4fArray[i];
            if (vector4f != null) {
                GL30.glClearColor(vector4f.getX(), vector4f.getY(), vector4f.getZ(), vector4f.getW());
            }
            if (this.colorBufferSizes[i] != null) {
                if (this.colorTexturesFlip.isChanged(i)) {
                    this.clearColorBufferFixedSize(i, true);
                }
                this.clearColorBufferFixedSize(i, false);
                continue;
            }
            if (this.colorTexturesFlip.isChanged(i)) {
                this.setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getB(i), 0);
                this.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
                GL30.glClear(16384);
                this.setFramebufferTexture2D(36160, 36064 + i, 3553, this.colorTexturesFlip.getA(i), 0);
            }
            this.setDrawBuffers(Shaders.drawBuffersColorAtt[i]);
            GL30.glClear(16384);
        }
    }

    private void clearColorBufferFixedSize(int n, boolean bl) {
        Dimension dimension = this.colorBufferSizes[n].getDimension(this.width, this.height);
        if (dimension != null) {
            FixedFramebuffer fixedFramebuffer = this.getFixedFramebuffer(dimension.width, dimension.height, Shaders.drawBuffersColorAtt[n], bl);
            fixedFramebuffer.bindFramebuffer();
            GL30.glClear(16384);
        }
    }

    public void clearDepthBuffer(Vector4f vector4f) {
        this.setFramebufferTexture2D(36160, 36096, 3553, this.depthTextures.get(0), 0);
        GL30.glClearColor(vector4f.getX(), vector4f.getY(), vector4f.getZ(), vector4f.getW());
        GL30.glClear(256);
    }

    public String toString() {
        return this.name + ", width: " + this.width + ", height: " + this.height + ", usedColorBuffers: " + this.usedColorBuffers + ", usedDepthBuffers: " + this.usedDepthBuffers + ", maxDrawBuffers: " + this.maxDrawBuffers;
    }

    public FixedFramebuffer getFixedFramebuffer(int n, int n2, DrawBuffers drawBuffers, boolean bl) {
        IntBuffer intBuffer = drawBuffers.getGlDrawBuffers();
        int n3 = drawBuffers.limit();
        int[] nArray = new int[n3];
        int[] nArray2 = new int[n3];
        for (int i = 0; i < nArray.length; ++i) {
            int n4 = drawBuffers.get(i);
            int n5 = n4 - 36064;
            if (!this.isColorBufferIndex(n5)) continue;
            nArray[i] = this.colorTexturesFlip.get(bl, n5);
            nArray2[i] = intBuffer.get(i);
        }
        CompoundKey compoundKey = new CompoundKey(new CompoundIntKey(nArray), new CompoundIntKey(nArray2));
        FixedFramebuffer fixedFramebuffer = this.fixedFramebuffers.get(compoundKey);
        if (fixedFramebuffer == null) {
            String string = this.name + ", [" + ArrayUtils.arrayToString(nArray) + "], [" + ArrayUtils.arrayToString(nArray2) + "]";
            fixedFramebuffer = new FixedFramebuffer(string, n, n2, nArray, nArray2, this.depthFilterNearest[0], this.depthFilterHardware[0]);
            fixedFramebuffer.setup();
            this.fixedFramebuffers.put(compoundKey, fixedFramebuffer);
        }
        return fixedFramebuffer;
    }
}

