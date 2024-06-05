package net.minecraft.src;

public class EntityBodyHelper
{
    private EntityLiving theLiving;
    private int field_75666_b;
    private float field_75667_c;
    
    public EntityBodyHelper(final EntityLiving par1EntityLiving) {
        this.field_75666_b = 0;
        this.field_75667_c = 0.0f;
        this.theLiving = par1EntityLiving;
    }
    
    public void func_75664_a() {
        final double var1 = this.theLiving.posX - this.theLiving.prevPosX;
        final double var2 = this.theLiving.posZ - this.theLiving.prevPosZ;
        if (var1 * var1 + var2 * var2 > 2.500000277905201E-7) {
            this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
            this.theLiving.rotationYawHead = this.func_75665_a(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0f);
            this.field_75667_c = this.theLiving.rotationYawHead;
            this.field_75666_b = 0;
        }
        else {
            float var3 = 75.0f;
            if (Math.abs(this.theLiving.rotationYawHead - this.field_75667_c) > 15.0f) {
                this.field_75666_b = 0;
                this.field_75667_c = this.theLiving.rotationYawHead;
            }
            else {
                ++this.field_75666_b;
                if (this.field_75666_b > 10) {
                    var3 = Math.max(1.0f - (this.field_75666_b - 10) / 10.0f, 0.0f) * 75.0f;
                }
            }
            this.theLiving.renderYawOffset = this.func_75665_a(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, var3);
        }
    }
    
    private float func_75665_a(final float par1, final float par2, final float par3) {
        float var4 = MathHelper.wrapAngleTo180_float(par1 - par2);
        if (var4 < -par3) {
            var4 = -par3;
        }
        if (var4 >= par3) {
            var4 = par3;
        }
        return par1 - var4;
    }
}
