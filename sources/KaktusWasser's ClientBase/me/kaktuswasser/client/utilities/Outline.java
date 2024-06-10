// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.utilities;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

import me.kaktuswasser.client.*;
import me.kaktuswasser.client.module.modules.*;

import org.lwjgl.*;
import java.nio.*;

public class Outline
{
    private float outlineSize;
    private int sampleRadius;
    private final int targetTextureID;
    private final int textureWidth;
    private final int textureHeight;
    private final int renderWidth;
    private final int renderHeight;
    private int fboTextureID;
    private int fboID;
    private int renderBufferID;
    private static int vertexShaderID;
    private static int fragmentShaderID;
    private static int shaderProgramID;
    private static int diffuseSamperUniformID;
    private static int texelSizeUniformID;
    private static int sampleRadiusUniformID;
    
    static {
        Outline.vertexShaderID = -1;
        Outline.fragmentShaderID = -1;
        Outline.shaderProgramID = -1;
        Outline.diffuseSamperUniformID = -1;
        Outline.texelSizeUniformID = -1;
        Outline.sampleRadiusUniformID = -1;
    }
    
    public Outline(final int targetTextureID, final int textureWidth, final int textureHeight, final int dispWidth, final int dispHeight, final float outlineSize, final int sampleRadius) {
        this.fboTextureID = -1;
        this.fboID = -1;
        this.renderBufferID = -1;
        Outline.shaderProgramID = -1;
        Outline.fragmentShaderID = -1;
        Outline.vertexShaderID = -1;
        Outline.diffuseSamperUniformID = -1;
        Outline.texelSizeUniformID = -1;
        Outline.sampleRadiusUniformID = -1;
        this.fboTextureID = -1;
        this.fboID = -1;
        this.renderBufferID = -1;
        this.targetTextureID = targetTextureID;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.renderWidth = dispWidth;
        this.renderHeight = dispHeight;
        this.outlineSize = outlineSize;
        this.sampleRadius = sampleRadius;
        this.generateFBO();
        this.initShaders();
    }
    
    public Outline setOutlineSize(final float size) {
        this.outlineSize = size;
        return this;
    }
    
    public Outline setSampleRadius(final int radius) {
        this.sampleRadius = radius;
        return this;
    }
    
    public int getTextureID() {
        return this.fboTextureID;
    }
    
    public void delete() {
        if (this.renderBufferID > -1) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT(this.renderBufferID);
        }
        if (this.fboID > -1) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(this.fboID);
        }
        if (this.fboTextureID > -1) {
            GL11.glDeleteTextures(this.fboTextureID);
        }
    }
    
    public Outline update() {
        if (this.fboID == -1 || this.renderBufferID == -1 || Outline.shaderProgramID == -1) {
            throw new RuntimeException("Invalid IDs!");
        }
        EXTFramebufferObject.glBindFramebufferEXT(36160, this.fboID);
        final int var9 = Math.max(Minecraft.func_175610_ah(), 30);
        GL11.glClear(16640);
        Minecraft.getMinecraft().entityRenderer.updateFogColor(Minecraft.getMinecraft().entityRenderer.renderEndNanoTime + 1000000000 / var9);
        ARBShaderObjects.glUseProgramObjectARB(Outline.shaderProgramID);
        ARBShaderObjects.glUniform1iARB(Outline.diffuseSamperUniformID, 0);
        GL13.glActiveTexture(33984);
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, this.targetTextureID);
        final FloatBuffer texelSizeBuffer = BufferUtils.createFloatBuffer(2);
        texelSizeBuffer.position(0);
        texelSizeBuffer.put(1.0f / this.textureWidth * this.outlineSize);
        texelSizeBuffer.put(1.0f / this.textureHeight * this.outlineSize);
        texelSizeBuffer.flip();
        ARBShaderObjects.glUniform2ARB(Outline.texelSizeUniformID, texelSizeBuffer);
        final IntBuffer sampleRadiusBuffer = BufferUtils.createIntBuffer(1);
        sampleRadiusBuffer.position(0);
        sampleRadiusBuffer.put(this.sampleRadius);
        sampleRadiusBuffer.flip();
        ARBShaderObjects.glUniform1ARB(Outline.sampleRadiusUniformID, sampleRadiusBuffer);
        GL11.glDisable(3553);
        GL11.glBegin(4);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glTexCoord2d(0.0, 0.0);
        GL11.glVertex2d(0.0, (double)this.renderHeight);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)this.renderWidth, (double)this.renderHeight);
        GL11.glTexCoord2d(1.0, 0.0);
        GL11.glVertex2d((double)this.renderWidth, (double)this.renderHeight);
        GL11.glTexCoord2d(1.0, 1.0);
        GL11.glVertex2d((double)this.renderWidth, 0.0);
        GL11.glTexCoord2d(0.0, 1.0);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glEnd();
        ARBShaderObjects.glUseProgramObjectARB(0);
        return this;
    }
    
    private void generateFBO() {
        this.fboID = EXTFramebufferObject.glGenFramebuffersEXT();
        this.fboTextureID = GL11.glGenTextures();
        this.renderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        GL11.glBindTexture(3553, this.fboTextureID);
        GL11.glTexParameterf(3553, 10241, 9729.0f);
        GL11.glTexParameterf(3553, 10240, 9729.0f);
        GL11.glTexParameterf(3553, 10242, 10496.0f);
        GL11.glTexParameterf(3553, 10243, 10496.0f);
        GL11.glBindTexture(3553, 0);
        GL11.glBindTexture(3553, this.fboTextureID);
        GL11.glTexImage2D(3553, 0, 32856, this.textureWidth, this.textureHeight, 0, 6408, 5121, (ByteBuffer)null);
        EXTFramebufferObject.glBindFramebufferEXT(36160, this.fboID);
        EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, this.fboTextureID, 0);
        EXTFramebufferObject.glBindRenderbufferEXT(36161, this.renderBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, this.textureWidth, this.textureHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, this.renderBufferID);
        this.checkFBO();
    }
    
    private void checkFBO() {
        final int error = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
        switch (error) {
            case 36053: {}
            case 36054: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT");
            }
            case 36055: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT");
            }
            default: {
                throw new RuntimeException("glCheckFramebufferStatusEXT returned unknown status:" + error);
            }
            case 36057: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT");
            }
            case 36058: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT");
            }
            case 36059: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT");
            }
            case 36060: {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT");
            }
        }
    }
    
    private void initShaders() {
        if (Outline.shaderProgramID == -1) {
            Outline.shaderProgramID = ARBShaderObjects.glCreateProgramObjectARB();
            try {
                if (Outline.vertexShaderID == -1) {
                    final String vertexShaderCode = "#version 120 \nvoid main() { \ngl_TexCoord[0] = gl_MultiTexCoord0; \ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex; \n}";
                    Outline.vertexShaderID = RenderHelper.createShader(vertexShaderCode, 35633);
                }
                if (Outline.fragmentShaderID == -1) {
                    String fragmentShaderCode = "#version 120 \nuniform sampler2D DiffuseSamper; \nuniform vec2 TexelSize; \nuniform int SampleRadius; \nvoid main(){ \nvec4 centerCol = texture2D(DiffuseSamper, gl_TexCoord[0].st); \nif(centerCol.a != 0.0F) { \ngl_FragColor = vec4(0, 0, 0, 0); \nreturn; \n} \nfloat closest = SampleRadius * 1.0F + 1.0F; \nfor(int xo = -SampleRadius; xo <= SampleRadius; xo++) { \nfor(int yo = -SampleRadius; yo <= SampleRadius; yo++) { \nvec4 currCol = texture2D(DiffuseSamper, gl_TexCoord[0].st + vec2(xo * TexelSize.x, yo * TexelSize.y)); \nif(currCol.r != 0.0F || currCol.g != 0.0F || currCol.b != 0.0F || currCol.a != 0.0F) { \nfloat currentDist = sqrt(xo*xo*1.0f + yo*yo*1.0f); \nif(currentDist < closest) { closest = currentDist; } \n} \n} \n} \ngl_FragColor = vec4(1, 1, 1, max(0, ((SampleRadius*1.0F) - (closest - 1)) / (SampleRadius*1.0F))); \n}";
                    Outline.fragmentShaderID = RenderHelper.createShader(fragmentShaderCode, 35632);
                }
            }
            catch (Exception ex) {
                Outline.shaderProgramID = -1;
                Outline.vertexShaderID = -1;
                Outline.fragmentShaderID = -1;
                ex.printStackTrace();
            }
            if (Outline.shaderProgramID != -1) {
                ARBShaderObjects.glAttachObjectARB(Outline.shaderProgramID, Outline.vertexShaderID);
                ARBShaderObjects.glAttachObjectARB(Outline.shaderProgramID, Outline.fragmentShaderID);
                ARBShaderObjects.glLinkProgramARB(Outline.shaderProgramID);
                if (ARBShaderObjects.glGetObjectParameteriARB(Outline.shaderProgramID, 35714) == 0) {
                    System.err.println(RenderHelper.getLogInfo(Outline.shaderProgramID));
                    return;
                }
                ARBShaderObjects.glValidateProgramARB(Outline.shaderProgramID);
                if (ARBShaderObjects.glGetObjectParameteriARB(Outline.shaderProgramID, 35715) == 0) {
                    System.err.println(RenderHelper.getLogInfo(Outline.shaderProgramID));
                    return;
                }
                ARBShaderObjects.glUseProgramObjectARB(0);
                Outline.diffuseSamperUniformID = ARBShaderObjects.glGetUniformLocationARB(Outline.shaderProgramID, (CharSequence)"DiffuseSamper");
                Outline.texelSizeUniformID = ARBShaderObjects.glGetUniformLocationARB(Outline.shaderProgramID, (CharSequence)"TexelSize");
                Outline.sampleRadiusUniformID = ARBShaderObjects.glGetUniformLocationARB(Outline.shaderProgramID, (CharSequence)"SampleRadius");
            }
        }
    }
}
