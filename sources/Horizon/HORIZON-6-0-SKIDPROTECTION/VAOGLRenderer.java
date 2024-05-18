package HORIZON-6-0-SKIDPROTECTION;

import java.nio.DoubleBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;

public class VAOGLRenderer extends ImmediateModeOGLRenderer
{
    private static final int áˆºÏ = 20;
    public static final int Ô = -1;
    public static final int ÇªÓ = 5000;
    private int ˆáƒ;
    private float[] Œ;
    private float[] £Ï;
    private int Ø­á;
    private float[] ˆÉ;
    private float[] Ï­Ï­Ï;
    private float[] £Â;
    private FloatBuffer £Ó;
    private FloatBuffer ˆÐƒØ­à;
    private FloatBuffer £Õ;
    private int Ï­Ô;
    
    public VAOGLRenderer() {
        this.ˆáƒ = -1;
        this.Œ = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
        this.£Ï = new float[] { 0.0f, 0.0f };
        this.ˆÉ = new float[15000];
        this.Ï­Ï­Ï = new float[20000];
        this.£Â = new float[15000];
        this.£Ó = BufferUtils.createFloatBuffer(15000);
        this.ˆÐƒØ­à = BufferUtils.createFloatBuffer(20000);
        this.£Õ = BufferUtils.createFloatBuffer(10000);
        this.Ï­Ô = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int width, final int height) {
        super.HorizonCode_Horizon_È(width, height);
        this.ÂµÈ();
        GL11.glEnableClientState(32884);
        GL11.glEnableClientState(32888);
        GL11.glEnableClientState(32886);
    }
    
    private void ÂµÈ() {
        this.Ø­á = 0;
    }
    
    private void á() {
        if (this.Ø­á == 0) {
            return;
        }
        if (this.ˆáƒ == -1) {
            return;
        }
        if (this.Ø­á < 20) {
            GL11.glBegin(this.ˆáƒ);
            for (int i = 0; i < this.Ø­á; ++i) {
                GL11.glColor4f(this.Ï­Ï­Ï[i * 4 + 0], this.Ï­Ï­Ï[i * 4 + 1], this.Ï­Ï­Ï[i * 4 + 2], this.Ï­Ï­Ï[i * 4 + 3]);
                GL11.glTexCoord2f(this.£Â[i * 2 + 0], this.£Â[i * 2 + 1]);
                GL11.glVertex3f(this.ˆÉ[i * 3 + 0], this.ˆÉ[i * 3 + 1], this.ˆÉ[i * 3 + 2]);
            }
            GL11.glEnd();
            this.ˆáƒ = -1;
            return;
        }
        this.£Ó.clear();
        this.ˆÐƒØ­à.clear();
        this.£Õ.clear();
        this.£Ó.put(this.ˆÉ, 0, this.Ø­á * 3);
        this.ˆÐƒØ­à.put(this.Ï­Ï­Ï, 0, this.Ø­á * 4);
        this.£Õ.put(this.£Â, 0, this.Ø­á * 2);
        this.£Ó.flip();
        this.ˆÐƒØ­à.flip();
        this.£Õ.flip();
        GL11.glVertexPointer(3, 0, this.£Ó);
        GL11.glColorPointer(4, 0, this.ˆÐƒØ­à);
        GL11.glTexCoordPointer(2, 0, this.£Õ);
        GL11.glDrawArrays(this.ˆáƒ, 0, this.Ø­á);
        this.ˆáƒ = -1;
    }
    
    private void ˆÏ­() {
        if (this.Ï­Ô > 0) {
            return;
        }
        if (this.Ø­á != 0) {
            this.á();
            this.ÂµÈ();
        }
        super.Â(this.Œ[0], this.Œ[1], this.Œ[2], this.Œ[3]);
    }
    
    @Override
    public void Ó() {
        super.Ó();
        this.ˆÏ­();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int geomType) {
        if (this.Ï­Ô > 0) {
            super.HorizonCode_Horizon_È(geomType);
            return;
        }
        if (this.ˆáƒ != geomType) {
            this.ˆÏ­();
            this.ˆáƒ = geomType;
        }
    }
    
    @Override
    public void Â(final float r, final float g, final float b, float a) {
        a *= this.HorizonCode_Horizon_È;
        this.Œ[0] = r;
        this.Œ[1] = g;
        this.Œ[2] = b;
        this.Œ[3] = a;
        if (this.Ï­Ô > 0) {
            super.Â(r, g, b, a);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Ï­Ô > 0) {
            super.HorizonCode_Horizon_È();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float u, final float v) {
        if (this.Ï­Ô > 0) {
            super.HorizonCode_Horizon_È(u, v);
            return;
        }
        this.£Ï[0] = u;
        this.£Ï[1] = v;
    }
    
    @Override
    public void Â(final float x, final float y) {
        if (this.Ï­Ô > 0) {
            super.Â(x, y);
            return;
        }
        this.Ý(x, y, 0.0f);
    }
    
    @Override
    public void Ý(final float x, final float y, final float z) {
        if (this.Ï­Ô > 0) {
            super.Ý(x, y, z);
            return;
        }
        this.ˆÉ[this.Ø­á * 3 + 0] = x;
        this.ˆÉ[this.Ø­á * 3 + 1] = y;
        this.ˆÉ[this.Ø­á * 3 + 2] = z;
        this.Ï­Ï­Ï[this.Ø­á * 4 + 0] = this.Œ[0];
        this.Ï­Ï­Ï[this.Ø­á * 4 + 1] = this.Œ[1];
        this.Ï­Ï­Ï[this.Ø­á * 4 + 2] = this.Œ[2];
        this.Ï­Ï­Ï[this.Ø­á * 4 + 3] = this.Œ[3];
        this.£Â[this.Ø­á * 2 + 0] = this.£Ï[0];
        this.£Â[this.Ø­á * 2 + 1] = this.£Ï[1];
        ++this.Ø­á;
        if (this.Ø­á > 4950 && this.à(this.Ø­á, this.ˆáƒ)) {
            final int type = this.ˆáƒ;
            this.ˆÏ­();
            this.ˆáƒ = type;
        }
    }
    
    private boolean à(final int count, final int type) {
        switch (type) {
            case 7: {
                return count % 4 == 0;
            }
            case 4: {
                return count % 3 == 0;
            }
            case 6913: {
                return count % 2 == 0;
            }
            default: {
                return false;
            }
        }
    }
    
    @Override
    public void Ý(final int target, final int id) {
        this.ˆÏ­();
        super.Ý(target, id);
    }
    
    @Override
    public void Ø­áŒŠá(final int src, final int dest) {
        this.ˆÏ­();
        super.Ø­áŒŠá(src, dest);
    }
    
    @Override
    public void Â(final int id) {
        this.ˆÏ­();
        super.Â(id);
    }
    
    @Override
    public void Ý(final int value) {
        this.ˆÏ­();
        super.Ý(value);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int plane, final DoubleBuffer buffer) {
        this.ˆÏ­();
        super.HorizonCode_Horizon_È(plane, buffer);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        this.ˆÏ­();
        super.HorizonCode_Horizon_È(red, green, blue, alpha);
    }
    
    @Override
    public void Ø­áŒŠá(final int item) {
        this.ˆÏ­();
        super.Ø­áŒŠá(item);
    }
    
    @Override
    public void Âµá€(final int item) {
        this.ˆÏ­();
        super.Âµá€(item);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float width) {
        this.ˆÏ­();
        super.HorizonCode_Horizon_È(width);
    }
    
    @Override
    public void Â(final float size) {
        this.ˆÏ­();
        super.Â(size);
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.ˆÏ­();
        super.Ø­áŒŠá();
    }
    
    @Override
    public void Âµá€() {
        this.ˆÏ­();
        super.Âµá€();
    }
    
    @Override
    public void Ý(final float angle, final float x, final float y, final float z) {
        this.ˆÏ­();
        super.Ý(angle, x, y, z);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y, final float z) {
        this.ˆÏ­();
        super.HorizonCode_Horizon_È(x, y, z);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int x, final int y, final int width, final int height) {
        this.ˆÏ­();
        super.HorizonCode_Horizon_È(x, y, width, height);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int target, final int mode, final int value) {
        this.ˆÏ­();
        super.HorizonCode_Horizon_È(target, mode, value);
    }
    
    @Override
    public void Â(final float x, final float y, final float z) {
        this.ˆÏ­();
        super.Â(x, y, z);
    }
    
    @Override
    public void Â() {
        --this.Ï­Ô;
        super.Â();
    }
    
    @Override
    public void Âµá€(final int id, final int option) {
        ++this.Ï­Ô;
        super.Âµá€(id, option);
    }
    
    @Override
    public float[] à() {
        return this.Œ;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final FloatBuffer buffer) {
        this.á();
        super.HorizonCode_Horizon_È(buffer);
    }
}
