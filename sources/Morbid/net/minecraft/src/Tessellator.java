package net.minecraft.src;

import java.nio.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class Tessellator
{
    private static boolean convertQuadsToTriangles;
    private static boolean tryVBO;
    private ByteBuffer byteBuffer;
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;
    private ShortBuffer shortBuffer;
    private int[] rawBuffer;
    private int vertexCount;
    private double textureU;
    private double textureV;
    private int brightness;
    private int color;
    private boolean hasColor;
    private boolean hasTexture;
    private boolean hasBrightness;
    private boolean hasNormals;
    private int rawBufferIndex;
    private int addedVertices;
    private boolean isColorDisabled;
    public int drawMode;
    public double xOffset;
    public double yOffset;
    public double zOffset;
    private int normal;
    public static Tessellator instance;
    public boolean isDrawing;
    private boolean useVBO;
    private IntBuffer vertexBuffers;
    private int vboIndex;
    private int vboCount;
    private int bufferSize;
    private boolean renderingChunk;
    private static boolean littleEndianByteOrder;
    public static boolean renderingWorldRenderer;
    public boolean defaultTexture;
    public int textureID;
    public boolean autoGrow;
    private RenderEngine renderEngine;
    private VertexData[] vertexDatas;
    private boolean[] drawnIcons;
    private TextureStitched[] vertexQuadIcons;
    
    static {
        Tessellator.convertQuadsToTriangles = false;
        Tessellator.tryVBO = false;
        Tessellator.instance = new Tessellator(524288);
        Tessellator.littleEndianByteOrder = (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN);
        Tessellator.renderingWorldRenderer = false;
    }
    
    public Tessellator() {
        this(65536);
        this.defaultTexture = false;
    }
    
    public Tessellator(final int par1) {
        this.renderingChunk = false;
        this.defaultTexture = true;
        this.textureID = 0;
        this.autoGrow = true;
        this.renderEngine = Config.getRenderEngine();
        this.vertexDatas = null;
        this.drawnIcons = new boolean[256];
        this.vertexQuadIcons = null;
        this.vertexCount = 0;
        this.hasColor = false;
        this.hasTexture = false;
        this.hasBrightness = false;
        this.hasNormals = false;
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
        this.isColorDisabled = false;
        this.isDrawing = false;
        this.useVBO = false;
        this.vboIndex = 0;
        this.vboCount = 10;
        this.bufferSize = par1;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(par1 * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.shortBuffer = this.byteBuffer.asShortBuffer();
        this.rawBuffer = new int[par1];
        this.useVBO = (Tessellator.tryVBO && GLContext.getCapabilities().GL_ARB_vertex_buffer_object);
        if (this.useVBO) {
            ARBBufferObject.glGenBuffersARB(this.vertexBuffers = GLAllocation.createDirectIntBuffer(this.vboCount));
        }
        this.vertexDatas = new VertexData[4];
        for (int var2 = 0; var2 < this.vertexDatas.length; ++var2) {
            this.vertexDatas[var2] = new VertexData();
        }
    }
    
    private void draw(final int var1, final int var2) {
        final int var3 = var2 - var1;
        if (var3 > 0) {
            final int var4 = var1 * 4;
            final int var5 = var3 * 4;
            if (this.useVBO) {
                throw new IllegalStateException("VBO not implemented");
            }
            this.floatBuffer.position(3);
            GL11.glTexCoordPointer(2, 32, this.floatBuffer);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            this.shortBuffer.position(14);
            GL11.glTexCoordPointer(2, 32, this.shortBuffer);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            this.byteBuffer.position(20);
            GL11.glColorPointer(4, true, 32, this.byteBuffer);
            this.floatBuffer.position(0);
            GL11.glVertexPointer(3, 32, this.floatBuffer);
            if (this.drawMode == 7 && Tessellator.convertQuadsToTriangles) {
                GL11.glDrawArrays(4, var4, var5);
            }
            else {
                GL11.glDrawArrays(this.drawMode, var4, var5);
            }
        }
    }
    
    private int drawForIcon(final TextureStitched var1, final int var2) {
        GL11.glBindTexture(3553, var1.tileTexture.getGlTextureId());
        int var3 = -1;
        int var4 = -1;
        final int var5 = this.addedVertices / 4;
        for (int var6 = var2; var6 < var5; ++var6) {
            final TextureStitched var7 = this.vertexQuadIcons[var6];
            if (var7 == var1) {
                if (var4 < 0) {
                    var4 = var6;
                }
            }
            else if (var4 >= 0) {
                this.draw(var4, var6);
                var4 = -1;
                if (var3 < 0) {
                    var3 = var6;
                }
            }
        }
        if (var4 >= 0) {
            this.draw(var4, var5);
        }
        if (var3 < 0) {
            var3 = var5;
        }
        return var3;
    }
    
    public int draw() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not tesselating!");
        }
        this.isDrawing = false;
        if (this.vertexCount > 0) {
            if (this.renderingChunk && Config.isMultiTexture()) {
                this.intBuffer.clear();
                this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
                this.byteBuffer.position(0);
                this.byteBuffer.limit(this.rawBufferIndex * 4);
                GL11.glEnableClientState(32888);
                GL11.glEnableClientState(32886);
                GL11.glEnableClientState(32884);
                final int var1 = this.renderEngine.textureMapBlocks.getMaxTextureIndex();
                if (this.drawnIcons.length < var1) {
                    this.drawnIcons = new boolean[var1 + 1];
                }
                Arrays.fill(this.drawnIcons, false);
                if (this.vertexQuadIcons == null) {
                    this.vertexQuadIcons = new TextureStitched[this.bufferSize / 4];
                }
                int var2 = 0;
                int var3 = -1;
                for (int var4 = this.addedVertices / 4, var5 = 0; var5 < var4; ++var5) {
                    final TextureStitched var6 = this.vertexQuadIcons[var5];
                    final int var7 = var6.getIndexInMap();
                    if (!this.drawnIcons[var7]) {
                        if (var6 == TextureUtils.iconGrassSideOverlay) {
                            if (var3 < 0) {
                                var3 = var5;
                            }
                        }
                        else {
                            var5 = this.drawForIcon(var6, var5) - 1;
                            ++var2;
                            this.drawnIcons[var7] = true;
                        }
                    }
                }
                if (var3 >= 0) {
                    this.drawForIcon((TextureStitched)TextureUtils.iconGrassSideOverlay, var3);
                }
                GL11.glDisableClientState(32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                GL11.glDisableClientState(32888);
                OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                GL11.glDisableClientState(32886);
                GL11.glDisableClientState(32884);
            }
            else {
                this.intBuffer.clear();
                this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
                this.byteBuffer.position(0);
                this.byteBuffer.limit(this.rawBufferIndex * 4);
                if (this.useVBO) {
                    this.vboIndex = (this.vboIndex + 1) % this.vboCount;
                    ARBBufferObject.glBindBufferARB(34962, this.vertexBuffers.get(this.vboIndex));
                    ARBBufferObject.glBufferDataARB(34962, this.byteBuffer, 35040);
                }
                if (this.hasTexture) {
                    if (this.useVBO) {
                        GL11.glTexCoordPointer(2, 5126, 32, 12L);
                    }
                    else {
                        this.floatBuffer.position(3);
                        GL11.glTexCoordPointer(2, 32, this.floatBuffer);
                    }
                    GL11.glEnableClientState(32888);
                }
                if (this.hasBrightness) {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                    if (this.useVBO) {
                        GL11.glTexCoordPointer(2, 5122, 32, 28L);
                    }
                    else {
                        this.shortBuffer.position(14);
                        GL11.glTexCoordPointer(2, 32, this.shortBuffer);
                    }
                    GL11.glEnableClientState(32888);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                }
                if (this.hasColor) {
                    if (this.useVBO) {
                        GL11.glColorPointer(4, 5121, 32, 20L);
                    }
                    else {
                        this.byteBuffer.position(20);
                        GL11.glColorPointer(4, true, 32, this.byteBuffer);
                    }
                    GL11.glEnableClientState(32886);
                }
                if (this.hasNormals) {
                    if (this.useVBO) {
                        GL11.glNormalPointer(5121, 32, 24L);
                    }
                    else {
                        this.byteBuffer.position(24);
                        GL11.glNormalPointer(32, this.byteBuffer);
                    }
                    GL11.glEnableClientState(32885);
                }
                if (this.useVBO) {
                    GL11.glVertexPointer(3, 5126, 32, 0L);
                }
                else {
                    this.floatBuffer.position(0);
                    GL11.glVertexPointer(3, 32, this.floatBuffer);
                }
                GL11.glEnableClientState(32884);
                if (this.drawMode == 7 && Tessellator.convertQuadsToTriangles) {
                    GL11.glDrawArrays(4, 0, this.vertexCount);
                }
                else {
                    GL11.glDrawArrays(this.drawMode, 0, this.vertexCount);
                }
                GL11.glDisableClientState(32884);
                if (this.hasTexture) {
                    GL11.glDisableClientState(32888);
                }
                if (this.hasBrightness) {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
                    GL11.glDisableClientState(32888);
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                }
                if (this.hasColor) {
                    GL11.glDisableClientState(32886);
                }
                if (this.hasNormals) {
                    GL11.glDisableClientState(32885);
                }
            }
        }
        final int var1 = this.rawBufferIndex * 4;
        this.reset();
        return var1;
    }
    
    private void reset() {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }
    
    public void startDrawingQuads() {
        this.startDrawing(7);
    }
    
    public void startDrawing(final int par1) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already tesselating!");
        }
        this.isDrawing = true;
        this.reset();
        this.drawMode = par1;
        this.hasNormals = false;
        this.hasColor = false;
        this.hasTexture = false;
        this.hasBrightness = false;
        this.isColorDisabled = false;
    }
    
    public void setTextureUV(final double par1, final double par3) {
        this.hasTexture = true;
        this.textureU = par1;
        this.textureV = par3;
    }
    
    public void setBrightness(final int par1) {
        this.hasBrightness = true;
        this.brightness = par1;
    }
    
    public void setColorOpaque_F(final float par1, final float par2, final float par3) {
        this.setColorOpaque((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f));
    }
    
    public void setColorRGBA_F(final float par1, final float par2, final float par3, final float par4) {
        this.setColorRGBA((int)(par1 * 255.0f), (int)(par2 * 255.0f), (int)(par3 * 255.0f), (int)(par4 * 255.0f));
    }
    
    public void setColorOpaque(final int par1, final int par2, final int par3) {
        this.setColorRGBA(par1, par2, par3, 255);
    }
    
    public void setColorRGBA(int par1, int par2, int par3, int par4) {
        if (!this.isColorDisabled) {
            if (par1 > 255) {
                par1 = 255;
            }
            if (par2 > 255) {
                par2 = 255;
            }
            if (par3 > 255) {
                par3 = 255;
            }
            if (par4 > 255) {
                par4 = 255;
            }
            if (par1 < 0) {
                par1 = 0;
            }
            if (par2 < 0) {
                par2 = 0;
            }
            if (par3 < 0) {
                par3 = 0;
            }
            if (par4 < 0) {
                par4 = 0;
            }
            this.hasColor = true;
            if (Tessellator.littleEndianByteOrder) {
                this.color = (par4 << 24 | par3 << 16 | par2 << 8 | par1);
            }
            else {
                this.color = (par1 << 24 | par2 << 16 | par3 << 8 | par4);
            }
        }
    }
    
    public void addVertexWithUV(double par1, double par3, double par5, double par7, double par9) {
        if (this.renderingChunk && Config.isMultiTexture()) {
            final int var11 = this.addedVertices % 4;
            final VertexData var12 = this.vertexDatas[var11];
            var12.x = par1;
            var12.y = par3;
            var12.z = par5;
            var12.u = par7;
            var12.v = par9;
            var12.color = this.color;
            var12.brightness = this.brightness;
            if (var11 != 3) {
                ++this.addedVertices;
            }
            else {
                this.addedVertices -= 3;
                final double var13 = (this.vertexDatas[0].u + this.vertexDatas[1].u + this.vertexDatas[2].u + this.vertexDatas[3].u) / 4.0;
                final double var14 = (this.vertexDatas[0].v + this.vertexDatas[1].v + this.vertexDatas[2].v + this.vertexDatas[3].v) / 4.0;
                TextureStitched var15 = this.renderEngine.textureMapBlocks.getIconByUV(var13, var14);
                if (var15 == null) {
                    var15 = this.renderEngine.textureMapBlocks.getMissingTextureStiched();
                }
                final double var16 = var15.baseU;
                final double var17 = var15.baseV;
                if (this.vertexQuadIcons == null) {
                    this.vertexQuadIcons = new TextureStitched[this.bufferSize / 4];
                }
                final int var18 = this.addedVertices;
                this.vertexQuadIcons[var18 / 4] = var15;
                final int var19 = var15.getSheetWidth() / var15.getWidth();
                final int var20 = var15.getSheetHeight() / var15.getHeight();
                final int var21 = this.color;
                final int var22 = this.brightness;
                for (int var23 = 0; var23 < 4; ++var23) {
                    final VertexData var24 = this.vertexDatas[var23];
                    par1 = var24.x;
                    par3 = var24.y;
                    par5 = var24.z;
                    par7 = var24.u;
                    par9 = var24.v;
                    par7 -= var16;
                    par9 -= var17;
                    par7 *= var19;
                    par9 *= var20;
                    this.color = var24.color;
                    this.brightness = var24.brightness;
                    this.setTextureUV(par7, par9);
                    this.addVertex(par1, par3, par5);
                }
                this.color = var21;
                this.brightness = var22;
            }
        }
        else {
            this.setTextureUV(par7, par9);
            this.addVertex(par1, par3, par5);
        }
    }
    
    public void addVertex(final double par1, final double par3, final double par5) {
        if (this.autoGrow && this.rawBufferIndex >= this.bufferSize - 32) {
            Config.dbg("Expand tessellator buffer, old: " + this.bufferSize + ", new: " + this.bufferSize * 2);
            this.bufferSize *= 2;
            final int[] var7 = new int[this.bufferSize];
            System.arraycopy(this.rawBuffer, 0, var7, 0, this.rawBuffer.length);
            this.rawBuffer = var7;
            this.byteBuffer = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
            this.intBuffer = this.byteBuffer.asIntBuffer();
            this.floatBuffer = this.byteBuffer.asFloatBuffer();
            this.shortBuffer = this.byteBuffer.asShortBuffer();
            if (this.vertexQuadIcons != null) {
                final TextureStitched[] var8 = new TextureStitched[this.bufferSize / 4];
                System.arraycopy(this.vertexQuadIcons, 0, var8, 0, this.vertexQuadIcons.length);
                this.vertexQuadIcons = var8;
            }
        }
        ++this.addedVertices;
        if (this.drawMode == 7 && Tessellator.convertQuadsToTriangles && this.addedVertices % 4 == 0) {
            for (int var9 = 0; var9 < 2; ++var9) {
                final int var10 = 8 * (3 - var9);
                if (this.hasTexture) {
                    this.rawBuffer[this.rawBufferIndex + 3] = this.rawBuffer[this.rawBufferIndex - var10 + 3];
                    this.rawBuffer[this.rawBufferIndex + 4] = this.rawBuffer[this.rawBufferIndex - var10 + 4];
                }
                if (this.hasBrightness) {
                    this.rawBuffer[this.rawBufferIndex + 7] = this.rawBuffer[this.rawBufferIndex - var10 + 7];
                }
                if (this.hasColor) {
                    this.rawBuffer[this.rawBufferIndex + 5] = this.rawBuffer[this.rawBufferIndex - var10 + 5];
                }
                this.rawBuffer[this.rawBufferIndex + 0] = this.rawBuffer[this.rawBufferIndex - var10 + 0];
                this.rawBuffer[this.rawBufferIndex + 1] = this.rawBuffer[this.rawBufferIndex - var10 + 1];
                this.rawBuffer[this.rawBufferIndex + 2] = this.rawBuffer[this.rawBufferIndex - var10 + 2];
                ++this.vertexCount;
                this.rawBufferIndex += 8;
            }
        }
        if (this.hasTexture) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float)this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float)this.textureV);
        }
        if (this.hasBrightness) {
            this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
        }
        if (this.hasColor) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }
        if (this.hasNormals) {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
        }
        this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float)(par1 + this.xOffset));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float)(par3 + this.yOffset));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float)(par5 + this.zOffset));
        this.rawBufferIndex += 8;
        ++this.vertexCount;
        if (!this.autoGrow && this.addedVertices % 4 == 0 && this.rawBufferIndex >= this.bufferSize - 32) {
            this.draw();
            this.isDrawing = true;
        }
    }
    
    public void setColorOpaque_I(final int par1) {
        final int var2 = par1 >> 16 & 0xFF;
        final int var3 = par1 >> 8 & 0xFF;
        final int var4 = par1 & 0xFF;
        this.setColorOpaque(var2, var3, var4);
    }
    
    public void setColorRGBA_I(final int par1, final int par2) {
        final int var3 = par1 >> 16 & 0xFF;
        final int var4 = par1 >> 8 & 0xFF;
        final int var5 = par1 & 0xFF;
        this.setColorRGBA(var3, var4, var5, par2);
    }
    
    public void disableColor() {
        this.isColorDisabled = true;
    }
    
    public void setNormal(final float par1, final float par2, final float par3) {
        this.hasNormals = true;
        final byte var4 = (byte)(par1 * 127.0f);
        final byte var5 = (byte)(par2 * 127.0f);
        final byte var6 = (byte)(par3 * 127.0f);
        this.normal = ((var4 & 0xFF) | (var5 & 0xFF) << 8 | (var6 & 0xFF) << 16);
    }
    
    public void setTranslation(final double par1, final double par3, final double par5) {
        this.xOffset = par1;
        this.yOffset = par3;
        this.zOffset = par5;
    }
    
    public void addTranslation(final float par1, final float par2, final float par3) {
        this.xOffset += par1;
        this.yOffset += par2;
        this.zOffset += par3;
    }
    
    public boolean isRenderingChunk() {
        return this.renderingChunk;
    }
    
    public void setRenderingChunk(final boolean var1) {
        this.renderingChunk = var1;
    }
}
