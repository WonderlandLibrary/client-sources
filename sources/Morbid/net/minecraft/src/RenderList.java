package net.minecraft.src;

import java.nio.*;
import org.lwjgl.opengl.*;

public class RenderList
{
    private int field_78429_a;
    private int field_78427_b;
    private int field_78428_c;
    private double field_78425_d;
    private double field_78426_e;
    private double field_78423_f;
    private IntBuffer field_78424_g;
    private boolean field_78430_h;
    private boolean field_78431_i;
    
    public RenderList() {
        this.field_78424_g = GLAllocation.createDirectIntBuffer(65536);
        this.field_78430_h = false;
        this.field_78431_i = false;
    }
    
    public void func_78422_a(final int par1, final int par2, final int par3, final double par4, final double par6, final double par8) {
        this.field_78430_h = true;
        this.field_78424_g.clear();
        this.field_78429_a = par1;
        this.field_78427_b = par2;
        this.field_78428_c = par3;
        this.field_78425_d = par4;
        this.field_78426_e = par6;
        this.field_78423_f = par8;
    }
    
    public boolean func_78418_a(final int par1, final int par2, final int par3) {
        return this.field_78430_h && (par1 == this.field_78429_a && par2 == this.field_78427_b && par3 == this.field_78428_c);
    }
    
    public void func_78420_a(final int par1) {
        this.field_78424_g.put(par1);
        if (this.field_78424_g.remaining() == 0) {
            this.func_78419_a();
        }
    }
    
    public void func_78419_a() {
        if (this.field_78430_h) {
            if (!this.field_78431_i) {
                this.field_78424_g.flip();
                this.field_78431_i = true;
            }
            if (this.field_78424_g.remaining() > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.field_78429_a - this.field_78425_d), (float)(this.field_78427_b - this.field_78426_e), (float)(this.field_78428_c - this.field_78423_f));
                GL11.glCallLists(this.field_78424_g);
                GL11.glPopMatrix();
            }
        }
    }
    
    public void func_78421_b() {
        this.field_78430_h = false;
        this.field_78431_i = false;
    }
}
