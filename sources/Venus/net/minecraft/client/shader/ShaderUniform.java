/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.shader.IShaderManager;
import net.minecraft.client.shader.ShaderDefault;
import net.minecraft.util.math.vector.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryUtil;

public class ShaderUniform
extends ShaderDefault
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private int uniformLocation;
    private final int uniformCount;
    private final int uniformType;
    private final IntBuffer uniformIntBuffer;
    private final FloatBuffer uniformFloatBuffer;
    private final String shaderName;
    private boolean dirty;
    private final IShaderManager shaderManager;

    public ShaderUniform(String string, int n, int n2, IShaderManager iShaderManager) {
        this.shaderName = string;
        this.uniformCount = n2;
        this.uniformType = n;
        this.shaderManager = iShaderManager;
        if (n <= 3) {
            this.uniformIntBuffer = MemoryUtil.memAllocInt(n2);
            this.uniformFloatBuffer = null;
        } else {
            this.uniformIntBuffer = null;
            this.uniformFloatBuffer = MemoryUtil.memAllocFloat(n2);
        }
        this.uniformLocation = -1;
        this.markDirty();
    }

    public static int func_227806_a_(int n, CharSequence charSequence) {
        return GlStateManager.getUniformLocation(n, charSequence);
    }

    public static void func_227805_a_(int n, int n2) {
        RenderSystem.glUniform1i(n, n2);
    }

    public static int func_227807_b_(int n, CharSequence charSequence) {
        return GlStateManager.getAttribLocation(n, charSequence);
    }

    @Override
    public void close() {
        if (this.uniformIntBuffer != null) {
            MemoryUtil.memFree(this.uniformIntBuffer);
        }
        if (this.uniformFloatBuffer != null) {
            MemoryUtil.memFree(this.uniformFloatBuffer);
        }
    }

    private void markDirty() {
        this.dirty = true;
        if (this.shaderManager != null) {
            this.shaderManager.markDirty();
        }
    }

    public static int parseType(String string) {
        int n = -1;
        if ("int".equals(string)) {
            n = 0;
        } else if ("float".equals(string)) {
            n = 4;
        } else if (string.startsWith("matrix")) {
            if (string.endsWith("2x2")) {
                n = 8;
            } else if (string.endsWith("3x3")) {
                n = 9;
            } else if (string.endsWith("4x4")) {
                n = 10;
            }
        }
        return n;
    }

    public void setUniformLocation(int n) {
        this.uniformLocation = n;
    }

    public String getShaderName() {
        return this.shaderName;
    }

    @Override
    public void set(float f) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(0, f);
        this.markDirty();
    }

    @Override
    public void set(float f, float f2) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(0, f);
        this.uniformFloatBuffer.put(1, f2);
        this.markDirty();
    }

    @Override
    public void set(float f, float f2, float f3) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(0, f);
        this.uniformFloatBuffer.put(1, f2);
        this.uniformFloatBuffer.put(2, f3);
        this.markDirty();
    }

    @Override
    public void set(float f, float f2, float f3, float f4) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(f);
        this.uniformFloatBuffer.put(f2);
        this.uniformFloatBuffer.put(f3);
        this.uniformFloatBuffer.put(f4);
        this.uniformFloatBuffer.flip();
        this.markDirty();
    }

    @Override
    public void setSafe(float f, float f2, float f3, float f4) {
        this.uniformFloatBuffer.position(0);
        if (this.uniformType >= 4) {
            this.uniformFloatBuffer.put(0, f);
        }
        if (this.uniformType >= 5) {
            this.uniformFloatBuffer.put(1, f2);
        }
        if (this.uniformType >= 6) {
            this.uniformFloatBuffer.put(2, f3);
        }
        if (this.uniformType >= 7) {
            this.uniformFloatBuffer.put(3, f4);
        }
        this.markDirty();
    }

    @Override
    public void set(int n, int n2, int n3, int n4) {
        this.uniformIntBuffer.position(0);
        if (this.uniformType >= 0) {
            this.uniformIntBuffer.put(0, n);
        }
        if (this.uniformType >= 1) {
            this.uniformIntBuffer.put(1, n2);
        }
        if (this.uniformType >= 2) {
            this.uniformIntBuffer.put(2, n3);
        }
        if (this.uniformType >= 3) {
            this.uniformIntBuffer.put(3, n4);
        }
        this.markDirty();
    }

    @Override
    public void set(float[] fArray) {
        if (fArray.length < this.uniformCount) {
            LOGGER.warn("Uniform.set called with a too-small value array (expected {}, got {}). Ignoring.", (Object)this.uniformCount, (Object)fArray.length);
        } else {
            this.uniformFloatBuffer.position(0);
            this.uniformFloatBuffer.put(fArray);
            this.uniformFloatBuffer.position(0);
            this.markDirty();
        }
    }

    @Override
    public void set(Matrix4f matrix4f) {
        this.uniformFloatBuffer.position(0);
        matrix4f.write(this.uniformFloatBuffer);
        this.markDirty();
    }

    public void upload() {
        if (!this.dirty) {
            // empty if block
        }
        this.dirty = false;
        if (this.uniformType <= 3) {
            this.uploadInt();
        } else if (this.uniformType <= 7) {
            this.uploadFloat();
        } else {
            if (this.uniformType > 10) {
                LOGGER.warn("Uniform.upload called, but type value ({}) is not a valid type. Ignoring.", (Object)this.uniformType);
                return;
            }
            this.uploadFloatMatrix();
        }
    }

    private void uploadInt() {
        this.uniformFloatBuffer.clear();
        switch (this.uniformType) {
            case 0: {
                RenderSystem.glUniform1(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            case 1: {
                RenderSystem.glUniform2(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            case 2: {
                RenderSystem.glUniform3(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            case 3: {
                RenderSystem.glUniform4(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            default: {
                LOGGER.warn("Uniform.upload called, but count value ({}) is  not in the range of 1 to 4. Ignoring.", (Object)this.uniformCount);
            }
        }
    }

    private void uploadFloat() {
        this.uniformFloatBuffer.clear();
        switch (this.uniformType) {
            case 4: {
                RenderSystem.glUniform1(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            case 5: {
                RenderSystem.glUniform2(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            case 6: {
                RenderSystem.glUniform3(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            case 7: {
                RenderSystem.glUniform4(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            default: {
                LOGGER.warn("Uniform.upload called, but count value ({}) is not in the range of 1 to 4. Ignoring.", (Object)this.uniformCount);
            }
        }
    }

    private void uploadFloatMatrix() {
        this.uniformFloatBuffer.clear();
        switch (this.uniformType) {
            case 8: {
                RenderSystem.glUniformMatrix2(this.uniformLocation, false, this.uniformFloatBuffer);
                break;
            }
            case 9: {
                RenderSystem.glUniformMatrix3(this.uniformLocation, false, this.uniformFloatBuffer);
                break;
            }
            case 10: {
                RenderSystem.glUniformMatrix4(this.uniformLocation, false, this.uniformFloatBuffer);
            }
        }
    }
}

