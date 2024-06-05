package ClassSub;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class Class149 extends Class54
{
    private static final int TOLERANCE = 20;
    public static final int NONE = -1;
    public static final int MAX_VERTS = 5000;
    private int currentType;
    private float[] color;
    private float[] tex;
    private int vertIndex;
    private float[] verts;
    private float[] cols;
    private float[] texs;
    private FloatBuffer vertices;
    private FloatBuffer colors;
    private FloatBuffer textures;
    private int listMode;
    
    
    public Class149() {
        this.currentType = -1;
        this.color = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
        this.tex = new float[] { 0.0f, 0.0f };
        this.verts = new float[15000];
        this.cols = new float[20000];
        this.texs = new float[15000];
        this.vertices = BufferUtils.createFloatBuffer(15000);
        this.colors = BufferUtils.createFloatBuffer(20000);
        this.textures = BufferUtils.createFloatBuffer(10000);
        this.listMode = 0;
    }
    
    @Override
    public void initDisplay(final int n, final int n2) {
        super.initDisplay(n, n2);
        this.startBuffer();
        GL11.glEnableClientState(32884);
        GL11.glEnableClientState(32888);
        GL11.glEnableClientState(32886);
    }
    
    private void startBuffer() {
        this.vertIndex = 0;
    }
    
    private void flushBuffer() {
        if (this.vertIndex == 0) {
            return;
        }
        if (this.currentType == -1) {
            return;
        }
        if (this.vertIndex < 20) {
            GL11.glBegin(this.currentType);
            for (int i = 0; i < this.vertIndex; ++i) {
                GL11.glColor4f(this.cols[i * 4 + 0], this.cols[i * 4 + 1], this.cols[i * 4 + 2], this.cols[i * 4 + 3]);
                GL11.glTexCoord2f(this.texs[i * 2 + 0], this.texs[i * 2 + 1]);
                GL11.glVertex3f(this.verts[i * 3 + 0], this.verts[i * 3 + 1], this.verts[i * 3 + 2]);
            }
            GL11.glEnd();
            this.currentType = -1;
            return;
        }
        this.vertices.clear();
        this.colors.clear();
        this.textures.clear();
        this.vertices.put(this.verts, 0, this.vertIndex * 3);
        this.colors.put(this.cols, 0, this.vertIndex * 4);
        this.textures.put(this.texs, 0, this.vertIndex * 2);
        this.vertices.flip();
        this.colors.flip();
        this.textures.flip();
        GL11.glVertexPointer(3, 0, this.vertices);
        GL11.glColorPointer(4, 0, this.colors);
        GL11.glTexCoordPointer(2, 0, this.textures);
        GL11.glDrawArrays(this.currentType, 0, this.vertIndex);
        this.currentType = -1;
    }
    
    private void applyBuffer() {
        if (this.listMode > 0) {
            return;
        }
        if (this.vertIndex != 0) {
            this.flushBuffer();
            this.startBuffer();
        }
        super.glColor4f(this.color[0], this.color[1], this.color[2], this.color[3]);
    }
    
    @Override
    public void flush() {
        super.flush();
        this.applyBuffer();
    }
    
    @Override
    public void glBegin(final int currentType) {
        if (this.listMode > 0) {
            super.glBegin(currentType);
            return;
        }
        if (this.currentType != currentType) {
            this.applyBuffer();
            this.currentType = currentType;
        }
    }
    
    @Override
    public void glColor4f(final float n, final float n2, final float n3, float n4) {
        n4 *= this.alphaScale;
        this.color[0] = n;
        this.color[1] = n2;
        this.color[2] = n3;
        this.color[3] = n4;
        if (this.listMode > 0) {
            super.glColor4f(n, n2, n3, n4);
        }
    }
    
    @Override
    public void glEnd() {
        if (this.listMode > 0) {
            super.glEnd();
        }
    }
    
    @Override
    public void glTexCoord2f(final float n, final float n2) {
        if (this.listMode > 0) {
            super.glTexCoord2f(n, n2);
            return;
        }
        this.tex[0] = n;
        this.tex[1] = n2;
    }
    
    @Override
    public void glVertex2f(final float n, final float n2) {
        if (this.listMode > 0) {
            super.glVertex2f(n, n2);
            return;
        }
        this.glVertex3f(n, n2, 0.0f);
    }
    
    @Override
    public void glVertex3f(final float n, final float n2, final float n3) {
        if (this.listMode > 0) {
            super.glVertex3f(n, n2, n3);
            return;
        }
        this.verts[this.vertIndex * 3 + 0] = n;
        this.verts[this.vertIndex * 3 + 1] = n2;
        this.verts[this.vertIndex * 3 + 2] = n3;
        this.cols[this.vertIndex * 4 + 0] = this.color[0];
        this.cols[this.vertIndex * 4 + 1] = this.color[1];
        this.cols[this.vertIndex * 4 + 2] = this.color[2];
        this.cols[this.vertIndex * 4 + 3] = this.color[3];
        this.texs[this.vertIndex * 2 + 0] = this.tex[0];
        this.texs[this.vertIndex * 2 + 1] = this.tex[1];
        ++this.vertIndex;
        if (this.vertIndex > 4950 && this.isSplittable(this.vertIndex, this.currentType)) {
            final int currentType = this.currentType;
            this.applyBuffer();
            this.currentType = currentType;
        }
    }
    
    private boolean isSplittable(final int n, final int n2) {
        switch (n2) {
            case 7: {
                return n % 4 == 0;
            }
            case 4: {
                return n % 3 == 0;
            }
            case 6913: {
                return n % 2 == 0;
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public void glBindTexture(final int n, final int n2) {
        this.applyBuffer();
        super.glBindTexture(n, n2);
    }
    
    @Override
    public void glBlendFunc(final int n, final int n2) {
        this.applyBuffer();
        super.glBlendFunc(n, n2);
    }
    
    @Override
    public void glCallList(final int n) {
        this.applyBuffer();
        super.glCallList(n);
    }
    
    @Override
    public void glClear(final int n) {
        this.applyBuffer();
        super.glClear(n);
    }
    
    @Override
    public void glClipPlane(final int n, final DoubleBuffer doubleBuffer) {
        this.applyBuffer();
        super.glClipPlane(n, doubleBuffer);
    }
    
    @Override
    public void glColorMask(final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        this.applyBuffer();
        super.glColorMask(b, b2, b3, b4);
    }
    
    @Override
    public void glDisable(final int n) {
        this.applyBuffer();
        super.glDisable(n);
    }
    
    @Override
    public void glEnable(final int n) {
        this.applyBuffer();
        super.glEnable(n);
    }
    
    @Override
    public void glLineWidth(final float n) {
        this.applyBuffer();
        super.glLineWidth(n);
    }
    
    @Override
    public void glPointSize(final float n) {
        this.applyBuffer();
        super.glPointSize(n);
    }
    
    @Override
    public void glPopMatrix() {
        this.applyBuffer();
        super.glPopMatrix();
    }
    
    @Override
    public void glPushMatrix() {
        this.applyBuffer();
        super.glPushMatrix();
    }
    
    @Override
    public void glRotatef(final float n, final float n2, final float n3, final float n4) {
        this.applyBuffer();
        super.glRotatef(n, n2, n3, n4);
    }
    
    @Override
    public void glScalef(final float n, final float n2, final float n3) {
        this.applyBuffer();
        super.glScalef(n, n2, n3);
    }
    
    @Override
    public void glScissor(final int n, final int n2, final int n3, final int n4) {
        this.applyBuffer();
        super.glScissor(n, n2, n3, n4);
    }
    
    @Override
    public void glTexEnvi(final int n, final int n2, final int n3) {
        this.applyBuffer();
        super.glTexEnvi(n, n2, n3);
    }
    
    @Override
    public void glTranslatef(final float n, final float n2, final float n3) {
        this.applyBuffer();
        super.glTranslatef(n, n2, n3);
    }
    
    @Override
    public void glEndList() {
        --this.listMode;
        super.glEndList();
    }
    
    @Override
    public void glNewList(final int n, final int n2) {
        ++this.listMode;
        super.glNewList(n, n2);
    }
    
    @Override
    public float[] getCurrentColor() {
        return this.color;
    }
    
    @Override
    public void glLoadMatrix(final FloatBuffer floatBuffer) {
        this.flushBuffer();
        super.glLoadMatrix(floatBuffer);
    }
}
