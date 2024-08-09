/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.optifine.shaders.SMCLog;
import net.optifine.shaders.Shaders;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL30;

public class FixedFramebuffer {
    private String name;
    private int width;
    private int height;
    private int[] colorTextures;
    private int[] colorAttachments;
    private boolean depthFilterNearest;
    private boolean depthFilterHardware;
    private int glFramebuffer;
    private int depthTexture;
    private IntBuffer glDrawBuffers;

    public FixedFramebuffer(String string, int n, int n2, int[] nArray, int[] nArray2, boolean bl, boolean bl2) {
        this.name = string;
        this.width = n;
        this.height = n2;
        this.colorTextures = nArray;
        this.colorAttachments = nArray2;
        this.depthFilterNearest = bl;
        this.depthFilterHardware = bl2;
    }

    public void setup() {
        int n;
        if (this.exists()) {
            this.delete();
        }
        this.glFramebuffer = EXTFramebufferObject.glGenFramebuffersEXT();
        this.bindFramebuffer();
        GL30.glDrawBuffers(0);
        GL30.glReadBuffer(0);
        this.depthTexture = GL30.glGenTextures();
        GlStateManager.bindTexture(this.depthTexture);
        GL30.glTexParameteri(3553, 10242, 33071);
        GL30.glTexParameteri(3553, 10243, 33071);
        int n2 = this.depthFilterNearest ? 9728 : 9729;
        GL30.glTexParameteri(3553, 10241, n2);
        GL30.glTexParameteri(3553, 10240, n2);
        if (this.depthFilterHardware) {
            GL30.glTexParameteri(3553, 34892, 34894);
        }
        GL30.glTexImage2D(3553, 0, 6402, this.width, this.height, 0, 6402, 5126, (FloatBuffer)null);
        EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, this.depthTexture, 0);
        Shaders.checkGLError("FBS " + this.name + " depth");
        for (n = 0; n < this.colorTextures.length; ++n) {
            EXTFramebufferObject.glFramebufferTexture2DEXT(36160, this.colorAttachments[n], 3553, this.colorTextures[n], 0);
            Shaders.checkGLError("FBS " + this.name + " color");
        }
        GlStateManager.bindTexture(0);
        if (this.colorTextures.length > 0) {
            this.glDrawBuffers = BufferUtils.createIntBuffer(this.colorAttachments.length);
            for (n = 0; n < this.colorAttachments.length; ++n) {
                int n3 = this.colorAttachments[n];
                this.glDrawBuffers.put(n, n3);
            }
            GL30.glDrawBuffers(this.glDrawBuffers);
            GL30.glReadBuffer(0);
        }
        if ((n = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160)) != 36053) {
            Shaders.printChatAndLogError("[Shaders] Error creating framebuffer: " + this.name + ", status: " + n);
        } else {
            SMCLog.info("Framebuffer created: " + this.name);
        }
    }

    public void bindFramebuffer() {
        GlStateManager.bindFramebuffer(36160, this.glFramebuffer);
    }

    public void delete() {
        if (this.glFramebuffer != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(this.glFramebuffer);
            this.glFramebuffer = 0;
        }
        if (this.depthTexture != 0) {
            GlStateManager.deleteTexture(this.depthTexture);
            this.depthTexture = 0;
        }
        this.glDrawBuffers = null;
    }

    public boolean exists() {
        return this.glFramebuffer != 0;
    }

    public String toString() {
        return this.name;
    }
}

