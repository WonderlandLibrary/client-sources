package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.EXTSecondaryColor;
import org.lwjgl.opengl.GLContext;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.DoubleBuffer;
import org.lwjgl.opengl.GL11;

public class ImmediateModeOGLRenderer implements SGL
{
    private int Ô;
    private int ÇªÓ;
    private float[] áˆºÏ;
    protected float HorizonCode_Horizon_È;
    
    public ImmediateModeOGLRenderer() {
        this.áˆºÏ = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
        this.HorizonCode_Horizon_È = 1.0f;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int width, final int height) {
        this.Ô = width;
        this.ÇªÓ = height;
        final String extensions = GL11.glGetString(7939);
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glViewport(0, 0, width, height);
        GL11.glMatrixMode(5888);
    }
    
    @Override
    public void Â(final int xsize, final int ysize) {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)this.Ô, (double)this.ÇªÓ, 0.0, 1.0, -1.0);
        GL11.glMatrixMode(5888);
        GL11.glTranslatef((float)((this.Ô - xsize) / 2), (float)((this.ÇªÓ - ysize) / 2), 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int geomType) {
        GL11.glBegin(geomType);
    }
    
    @Override
    public void Ý(final int target, final int id) {
        GL11.glBindTexture(target, id);
    }
    
    @Override
    public void Ø­áŒŠá(final int src, final int dest) {
        GL11.glBlendFunc(src, dest);
    }
    
    @Override
    public void Â(final int id) {
        GL11.glCallList(id);
    }
    
    @Override
    public void Ý(final int value) {
        GL11.glClear(value);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float red, final float green, final float blue, final float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int plane, final DoubleBuffer buffer) {
        GL11.glClipPlane(plane, buffer);
    }
    
    @Override
    public void Â(final float r, final float g, final float b, float a) {
        a *= this.HorizonCode_Horizon_È;
        GL11.glColor4f(this.áˆºÏ[0] = r, this.áˆºÏ[1] = g, this.áˆºÏ[2] = b, this.áˆºÏ[3] = a);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        GL11.glColorMask(red, green, blue, alpha);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int target, final int level, final int internalFormat, final int x, final int y, final int width, final int height, final int border) {
        GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IntBuffer buffer) {
        GL11.glDeleteTextures(buffer);
    }
    
    @Override
    public void Ø­áŒŠá(final int item) {
        GL11.glDisable(item);
    }
    
    @Override
    public void Âµá€(final int item) {
        GL11.glEnable(item);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        GL11.glEnd();
    }
    
    @Override
    public void Â() {
        GL11.glEndList();
    }
    
    @Override
    public int Ó(final int count) {
        return GL11.glGenLists(count);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final FloatBuffer ret) {
        GL11.glGetFloat(id, ret);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final IntBuffer ret) {
        GL11.glGetInteger(id, ret);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int target, final int level, final int format, final int type, final ByteBuffer pixels) {
        GL11.glGetTexImage(target, level, format, type, pixels);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float width) {
        GL11.glLineWidth(width);
    }
    
    @Override
    public void Ý() {
        GL11.glLoadIdentity();
    }
    
    @Override
    public void Âµá€(final int id, final int option) {
        GL11.glNewList(id, option);
    }
    
    @Override
    public void Â(final float size) {
        GL11.glPointSize(size);
    }
    
    @Override
    public void Ø­áŒŠá() {
        GL11.glPopMatrix();
    }
    
    @Override
    public void Âµá€() {
        GL11.glPushMatrix();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final int width, final int height, final int format, final int type, final ByteBuffer pixels) {
        GL11.glReadPixels(x, y, width, height, format, type, pixels);
    }
    
    @Override
    public void Ý(final float angle, final float x, final float y, final float z) {
        GL11.glRotatef(angle, x, y, z);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float z) {
        GL11.glScalef(x, y, z);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final int width, final int height) {
        GL11.glScissor(x, y, width, height);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float u, final float v) {
        GL11.glTexCoord2f(u, v);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int target, final int mode, final int value) {
        GL11.glTexEnvi(target, mode, value);
    }
    
    @Override
    public void Â(final float x, final float y, final float z) {
        GL11.glTranslatef(x, y, z);
    }
    
    @Override
    public void Â(final float x, final float y) {
        GL11.glVertex2f(x, y);
    }
    
    @Override
    public void Ý(final float x, final float y, final float z) {
        GL11.glVertex3f(x, y, z);
    }
    
    @Override
    public void Ó() {
    }
    
    @Override
    public void Â(final int target, final int param, final int value) {
        GL11.glTexParameteri(target, param, value);
    }
    
    @Override
    public float[] à() {
        return this.áˆºÏ;
    }
    
    @Override
    public void Ó(final int list, final int count) {
        GL11.glDeleteLists(list, count);
    }
    
    @Override
    public void Ý(final float value) {
        GL11.glClearDepth((double)value);
    }
    
    @Override
    public void à(final int func) {
        GL11.glDepthFunc(func);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean mask) {
        GL11.glDepthMask(mask);
    }
    
    @Override
    public void Ø­áŒŠá(final float alphaScale) {
        this.HorizonCode_Horizon_È = alphaScale;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final FloatBuffer buffer) {
        GL11.glLoadMatrix(buffer);
    }
    
    @Override
    public void Â(final IntBuffer ids) {
        GL11.glGenTextures(ids);
    }
    
    @Override
    public void Ø() {
        GL11.glGetError();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int target, final int i, final int dstPixelFormat, final int width, final int height, final int j, final int srcPixelFormat, final int glUnsignedByte, final ByteBuffer textureBuffer) {
        GL11.glTexImage2D(target, i, dstPixelFormat, width, height, j, srcPixelFormat, glUnsignedByte, textureBuffer);
    }
    
    @Override
    public void Â(final int glTexture2d, final int i, final int pageX, final int pageY, final int width, final int height, final int glBgra, final int glUnsignedByte, final ByteBuffer scratchByteBuffer) {
        GL11.glTexSubImage2D(glTexture2d, i, pageX, pageY, width, height, glBgra, glUnsignedByte, scratchByteBuffer);
    }
    
    @Override
    public boolean áŒŠÆ() {
        return GLContext.getCapabilities().GL_EXT_texture_mirror_clamp;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return GLContext.getCapabilities().GL_EXT_secondary_color;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte b, final byte c, final byte d) {
        EXTSecondaryColor.glSecondaryColor3ubEXT(b, c, d);
    }
}
