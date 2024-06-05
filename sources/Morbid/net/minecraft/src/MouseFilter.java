package net.minecraft.src;

public class MouseFilter
{
    private float field_76336_a;
    private float field_76334_b;
    private float field_76335_c;
    
    public float smooth(float par1, final float par2) {
        this.field_76336_a += par1;
        par1 = (this.field_76336_a - this.field_76334_b) * par2;
        this.field_76335_c += (par1 - this.field_76335_c) * 0.5f;
        if ((par1 > 0.0f && par1 > this.field_76335_c) || (par1 < 0.0f && par1 < this.field_76335_c)) {
            par1 = this.field_76335_c;
        }
        this.field_76334_b += par1;
        return par1;
    }
}
