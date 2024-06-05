package ClassSub;

import java.nio.*;
import org.lwjgl.opengl.*;

public class Class54 implements Class311
{
    private int width;
    private int height;
    private float[] current;
    protected float alphaScale;
    
    
    public Class54() {
        this.current = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
        this.alphaScale = 1.0f;
    }
    
    @Override
    public void initDisplay(final int width, final int height) {
        this.width = width;
        this.height = height;
        GL11.glGetString(7939);
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
    public void enterOrtho(final int n, final int n2) {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)this.width, (double)this.height, 0.0, 1.0, -1.0);
        GL11.glMatrixMode(5888);
        GL11.glTranslatef((float)((this.width - n) / 2), (float)((this.height - n2) / 2), 0.0f);
    }
    
    @Override
    public void glBegin(final int n) {
        GL11.glBegin(n);
    }
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        GL11.glBindTexture(n, n2);
    }
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        GL11.glBlendFunc(n, n2);
    }
    
    @Override
    public void glCallList(final int n) {
        GL11.glCallList(n);
    }
    
    @Override
    public void glClear(final int n) {
        GL11.glClear(n);
    }
    
    @Override
    public void glClearColor(final float n, final float n2, final float n3, final float n4) {
        GL11.glClearColor(n, n2, n3, n4);
    }
    
    @Override
    public void glClipPlane(final int n, final DoubleBuffer doubleBuffer) {
        GL11.glClipPlane(n, doubleBuffer);
    }
    
    @Override
    public void glColor4f(final float n, final float n2, final float n3, float n4) {
        n4 *= this.alphaScale;
        GL11.glColor4f(this.current[0] = n, this.current[1] = n2, this.current[2] = n3, this.current[3] = n4);
    }
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        GL11.glColorMask(b, b2, b3, b4);
    }
    
    @Override
    public void glCopyTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        GL11.glCopyTexImage2D(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    @Override
    public void glDeleteTextures(final IntBuffer intBuffer) {
        GL11.glDeleteTextures(intBuffer);
    }
    
    @Override
    public void glDisable(final int n) {
        GL11.glDisable(n);
    }
    
    @Override
    public void glEnable(final int n) {
        GL11.glEnable(n);
    }
    
    @Override
    public void glEnd() {
        GL11.glEnd();
    }
    
    @Override
    public void glEndList() {
        GL11.glEndList();
    }
    
    @Override
    public int glGenLists(final int n) {
        return GL11.glGenLists(n);
    }
    
    @Override
    public void glGetFloat(final int n, final FloatBuffer floatBuffer) {
        GL11.glGetFloat(n, floatBuffer);
    }
    
    @Override
    public void glGetInteger(final int n, final IntBuffer intBuffer) {
        GL11.glGetInteger(n, intBuffer);
    }
    
    @Override
    public void glGetTexImage(final int n, final int n2, final int n3, final int n4, final ByteBuffer byteBuffer) {
        GL11.glGetTexImage(n, n2, n3, n4, byteBuffer);
    }
    
    @Override
    public void glLineWidth(final float n) {
        GL11.glLineWidth(n);
    }
    
    @Override
    public void glLoadIdentity() {
        GL11.glLoadIdentity();
    }
    
    @Override
    public void glNewList(final int n, final int n2) {
        GL11.glNewList(n, n2);
    }
    
    @Override
    public void glPointSize(final float n) {
        GL11.glPointSize(n);
    }
    
    @Override
    public void glPopMatrix() {
        GL11.glPopMatrix();
    }
    
    @Override
    public void glPushMatrix() {
        GL11.glPushMatrix();
    }
    
    @Override
    public void glReadPixels(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final ByteBuffer byteBuffer) {
        GL11.glReadPixels(n, n2, n3, n4, n5, n6, byteBuffer);
    }
    
    @Override
    public void glRotatef(final float n, final float n2, final float n3, final float n4) {
        GL11.glRotatef(n, n2, n3, n4);
    }
    
    @Override
    public void glScalef(final float n, final float n2, final float n3) {
        GL11.glScalef(n, n2, n3);
    }
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        GL11.glScissor(n, n2, n3, n4);
    }
    
    @Override
    public void glTexCoord2f(final float n, final float n2) {
        GL11.glTexCoord2f(n, n2);
    }
    
    @Override
    public void glTexEnvi(final int n, final int n2, final int n3) {
        GL11.glTexEnvi(n, n2, n3);
    }
    
    @Override
    public void glTranslatef(final float n, final float n2, final float n3) {
        GL11.glTranslatef(n, n2, n3);
    }
    
    @Override
    public void glVertex2f(final float n, final float n2) {
        GL11.glVertex2f(n, n2);
    }
    
    @Override
    public void glVertex3f(final float n, final float n2, final float n3) {
        GL11.glVertex3f(n, n2, n3);
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void glTexParameteri(final int n, final int n2, final int n3) {
        GL11.glTexParameteri(n, n2, n3);
    }
    
    @Override
    public float[] getCurrentColor() {
        return this.current;
    }
    
    @Override
    public void glDeleteLists(final int n, final int n2) {
        GL11.glDeleteLists(n, n2);
    }
    
    @Override
    public void glClearDepth(final float n) {
        GL11.glClearDepth((double)n);
    }
    
    @Override
    public void glDepthFunc(final int n) {
        GL11.glDepthFunc(n);
    }
    
    @Override
    public void glDepthMask(final boolean b) {
        GL11.glDepthMask(b);
    }
    
    @Override
    public void setGlobalAlphaScale(final float alphaScale) {
        this.alphaScale = alphaScale;
    }
    
    @Override
    public void glLoadMatrix(final FloatBuffer floatBuffer) {
        GL11.glLoadMatrix(floatBuffer);
    }
    
    @Override
    public void glGenTextures(final IntBuffer intBuffer) {
        GL11.glGenTextures(intBuffer);
    }
    
    @Override
    public void glGetError() {
        GL11.glGetError();
    }
    
    @Override
    public void glTexImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        GL11.glTexImage2D(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer);
    }
    
    @Override
    public void glTexSubImage2D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8, final ByteBuffer byteBuffer) {
        GL11.glTexSubImage2D(n, n2, n3, n4, n5, n6, n7, n8, byteBuffer);
    }
    
    @Override
    public boolean canTextureMirrorClamp() {
        return GLContext.getCapabilities().GL_EXT_texture_mirror_clamp;
    }
    
    @Override
    public boolean canSecondaryColor() {
        return GLContext.getCapabilities().GL_EXT_secondary_color;
    }
    
    @Override
    public void glSecondaryColor3ubEXT(final byte b, final byte b2, final byte b3) {
        EXTSecondaryColor.glSecondaryColor3ubEXT(b, b2, b3);
    }
}
