/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import me.thekirkayt.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Outline {
    private float outlineSize;
    private int sampleRadius;
    private final int targetTextureID;
    private final int textureWidth;
    private final int textureHeight;
    private final int renderWidth;
    private final int renderHeight;
    private int fboTextureID = -1;
    private int fboID = -1;
    private int renderBufferID = -1;
    private static int vertexShaderID = -1;
    private static int fragmentShaderID = -1;
    private static int shaderProgramID = -1;
    private static int diffuseSamperUniformID = -1;
    private static int texelSizeUniformID = -1;
    private static int sampleRadiusUniformID = -1;

    public Outline(int targetTextureID, int textureWidth, int textureHeight, int dispWidth, int dispHeight, float outlineSize, int sampleRadius) {
        shaderProgramID = -1;
        fragmentShaderID = -1;
        vertexShaderID = -1;
        diffuseSamperUniformID = -1;
        texelSizeUniformID = -1;
        sampleRadiusUniformID = -1;
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

    public Outline setOutlineSize(float size) {
        this.outlineSize = size;
        return this;
    }

    public Outline setSampleRadius(int radius) {
        this.sampleRadius = radius;
        return this;
    }

    public int getTextureID() {
        return this.fboTextureID;
    }

    public void delete() {
        if (this.renderBufferID > -1) {
            EXTFramebufferObject.glDeleteRenderbuffersEXT((int)this.renderBufferID);
        }
        if (this.fboID > -1) {
            EXTFramebufferObject.glDeleteFramebuffersEXT((int)this.fboID);
        }
        if (this.fboTextureID > -1) {
            GL11.glDeleteTextures((int)this.fboTextureID);
        }
    }

    public Outline update() {
        if (this.fboID == -1 || this.renderBufferID == -1 || shaderProgramID == -1) {
            throw new RuntimeException("Invalid IDs!");
        }
        EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)this.fboID);
        int var9 = Math.max(Minecraft.func_175610_ah(), 30);
        GL11.glClear((int)16640);
        Minecraft.getMinecraft().entityRenderer.updateFogColor(Minecraft.getMinecraft().entityRenderer.renderEndNanoTime + (long)(1000000000 / var9));
        ARBShaderObjects.glUseProgramObjectARB((int)shaderProgramID);
        ARBShaderObjects.glUniform1iARB((int)diffuseSamperUniformID, (int)0);
        GL13.glActiveTexture((int)33984);
        GL11.glEnable((int)3553);
        GL11.glBindTexture((int)3553, (int)this.targetTextureID);
        FloatBuffer texelSizeBuffer = BufferUtils.createFloatBuffer((int)2);
        texelSizeBuffer.position(0);
        texelSizeBuffer.put(1.0f / (float)this.textureWidth * this.outlineSize);
        texelSizeBuffer.put(1.0f / (float)this.textureHeight * this.outlineSize);
        texelSizeBuffer.flip();
        ARBShaderObjects.glUniform2ARB((int)texelSizeUniformID, (FloatBuffer)texelSizeBuffer);
        IntBuffer sampleRadiusBuffer = BufferUtils.createIntBuffer((int)1);
        sampleRadiusBuffer.position(0);
        sampleRadiusBuffer.put(this.sampleRadius);
        sampleRadiusBuffer.flip();
        ARBShaderObjects.glUniform1ARB((int)sampleRadiusUniformID, (IntBuffer)sampleRadiusBuffer);
        GL11.glDisable((int)3553);
        GL11.glBegin((int)4);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)0.0);
        GL11.glVertex2d((double)0.0, (double)this.renderHeight);
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)this.renderWidth, (double)this.renderHeight);
        GL11.glTexCoord2d((double)1.0, (double)0.0);
        GL11.glVertex2d((double)this.renderWidth, (double)this.renderHeight);
        GL11.glTexCoord2d((double)1.0, (double)1.0);
        GL11.glVertex2d((double)this.renderWidth, (double)0.0);
        GL11.glTexCoord2d((double)0.0, (double)1.0);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glEnd();
        ARBShaderObjects.glUseProgramObjectARB((int)0);
        return this;
    }

    private void generateFBO() {
        this.fboID = EXTFramebufferObject.glGenFramebuffersEXT();
        this.fboTextureID = GL11.glGenTextures();
        this.renderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
        GL11.glBindTexture((int)3553, (int)this.fboTextureID);
        GL11.glTexParameterf((int)3553, (int)10241, (float)9729.0f);
        GL11.glTexParameterf((int)3553, (int)10240, (float)9729.0f);
        GL11.glTexParameterf((int)3553, (int)10242, (float)10496.0f);
        GL11.glTexParameterf((int)3553, (int)10243, (float)10496.0f);
        GL11.glBindTexture((int)3553, (int)0);
        GL11.glBindTexture((int)3553, (int)this.fboTextureID);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)this.textureWidth, (int)this.textureHeight, (int)0, (int)6408, (int)5121, null);
        EXTFramebufferObject.glBindFramebufferEXT((int)36160, (int)this.fboID);
        EXTFramebufferObject.glFramebufferTexture2DEXT((int)36160, (int)36064, (int)3553, (int)this.fboTextureID, (int)0);
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)this.renderBufferID);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)this.textureWidth, (int)this.textureHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)this.renderBufferID);
        this.checkFBO();
    }

    private void checkFBO() {
    }

    public void initShaders() {
        if (shaderProgramID == -1) {
            shaderProgramID = ARBShaderObjects.glCreateProgramObjectARB();
            try {
                if (vertexShaderID == -1) {
                    String vertexShaderCode = "#version 120 \nvoid main() { \ngl_TexCoord[0] = gl_MultiTexCoord0; \ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex; \n}";
                    vertexShaderID = RenderUtils.createShader("#version 120 \nvoid main() { \ngl_TexCoord[0] = gl_MultiTexCoord0; \ngl_Position = gl_ModelViewProjectionMatrix * gl_Vertex; \n}", 35633);
                }
                if (fragmentShaderID == -1) {
                    String fragmentShaderCode = "#version 120 \nuniform sampler2D DiffuseSamper; \nuniform vec2 TexelSize; \nuniform int SampleRadius; \nvoid main(){ \nvec4 centerCol = texture2D(DiffuseSamper, gl_TexCoord[0].st); \nif(centerCol.a != 0.0F) { \ngl_FragColor = vec4(0, 0, 0, 0); \nreturn; \n} \nvec4 colAvg = vec4(0, 0, 0, 0); \nfor(int xo = -SampleRadius; xo <= SampleRadius; xo++) { \nfor(int yo = -SampleRadius; yo <= SampleRadius; yo++) { \nvec4 currCol = texture2D(DiffuseSamper, gl_TexCoord[0].st + vec2(xo * TexelSize.x, yo * TexelSize.y)); \nif(currCol.r != 0.0F || currCol.g != 0.0F || currCol.b != 0.0F || currCol.a != 0.0F) { \ncolAvg += vec4(1, 1, 1, max(0, (SampleRadius*1.0F - sqrt(xo * xo * 0.75f + yo * yo * 0.9f)) / SampleRadius * 1.00F)); \n} \n} \n} \ncolAvg.a /= SampleRadius*SampleRadius * 1.0F / 2.0F; \ngl_FragColor = colAvg; \n}";
                    System.out.println("2");
                    fragmentShaderCode = "#version 120 \nuniform sampler2D DiffuseSamper; \nuniform vec2 TexelSize; \nuniform int SampleRadius; \nvoid main(){ \nvec4 centerCol = texture2D(DiffuseSamper, gl_TexCoord[0].st); \nif(centerCol.a != 0.0F) { \ngl_FragColor = vec4(0, 0, 0, 0); \nreturn; \n} \nvec4 colAvg = vec4(0, 0, 0, 0); \nfor(int xo = -SampleRadius; xo <= SampleRadius; xo++) { \nfor(int yo = -SampleRadius; yo <= SampleRadius; yo++) { \nvec4 currCol = texture2D(DiffuseSamper, gl_TexCoord[0].st + vec2(xo * TexelSize.x, yo * TexelSize.y)); \nif(currCol.r != 0.0F || currCol.g != 0.0F || currCol.b != 0.0F || currCol.a != 0.0F) { \ncolAvg += vec4(1, 1, 1, max(0, (SampleRadius*1.0F - sqrt(xo * xo * 0.75f + yo * yo * 0.9f)) / SampleRadius * 1.00F)); \n} \n} \n} \ncolAvg.a /= SampleRadius*SampleRadius * 1.0F / 2.0F; \ngl_FragColor = colAvg; \n}";
                    fragmentShaderID = RenderUtils.createShader(fragmentShaderCode, 35632);
                }
            }
            catch (Exception ex) {
                shaderProgramID = -1;
                vertexShaderID = -1;
                fragmentShaderID = -1;
                ex.printStackTrace();
            }
            if (shaderProgramID != -1) {
                ARBShaderObjects.glAttachObjectARB((int)shaderProgramID, (int)vertexShaderID);
                ARBShaderObjects.glAttachObjectARB((int)shaderProgramID, (int)fragmentShaderID);
                ARBShaderObjects.glLinkProgramARB((int)shaderProgramID);
                if (ARBShaderObjects.glGetObjectParameteriARB((int)shaderProgramID, (int)35714) == 0) {
                    System.err.println(RenderUtils.getLogInfo(shaderProgramID));
                    return;
                }
                ARBShaderObjects.glValidateProgramARB((int)shaderProgramID);
                if (ARBShaderObjects.glGetObjectParameteriARB((int)shaderProgramID, (int)35715) == 0) {
                    System.err.println(RenderUtils.getLogInfo(shaderProgramID));
                    return;
                }
                ARBShaderObjects.glUseProgramObjectARB((int)0);
                diffuseSamperUniformID = ARBShaderObjects.glGetUniformLocationARB((int)shaderProgramID, (CharSequence)"DiffuseSamper");
                texelSizeUniformID = ARBShaderObjects.glGetUniformLocationARB((int)shaderProgramID, (CharSequence)"TexelSize");
                sampleRadiusUniformID = ARBShaderObjects.glGetUniformLocationARB((int)shaderProgramID, (CharSequence)"SampleRadius");
            }
        }
    }
}

