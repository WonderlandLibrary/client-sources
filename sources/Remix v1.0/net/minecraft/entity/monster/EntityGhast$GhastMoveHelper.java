package net.minecraft.entity.monster;

import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

class GhastMoveHelper extends EntityMoveHelper
{
    private EntityGhast field_179927_g;
    private int field_179928_h;
    
    public GhastMoveHelper() {
        super(EntityGhast.this);
        this.field_179927_g = EntityGhast.this;
    }
    
    @Override
    public void onUpdateMoveHelper() {
        if (this.update) {
            final double var1 = this.posX - this.field_179927_g.posX;
            final double var2 = this.posY - this.field_179927_g.posY;
            final double var3 = this.posZ - this.field_179927_g.posZ;
            double var4 = var1 * var1 + var2 * var2 + var3 * var3;
            if (this.field_179928_h-- <= 0) {
                this.field_179928_h += this.field_179927_g.getRNG().nextInt(5) + 2;
                var4 = MathHelper.sqrt_double(var4);
                if (this.func_179926_b(this.posX, this.posY, this.posZ, var4)) {
                    final EntityGhast field_179927_g = this.field_179927_g;
                    field_179927_g.motionX += var1 / var4 * 0.1;
                    final EntityGhast field_179927_g2 = this.field_179927_g;
                    field_179927_g2.motionY += var2 / var4 * 0.1;
                    final EntityGhast field_179927_g3 = this.field_179927_g;
                    field_179927_g3.motionZ += var3 / var4 * 0.1;
                }
                else {
                    this.update = false;
                }
            }
        }
    }
    
    private boolean func_179926_b(final double p_179926_1_, final double p_179926_3_, final double p_179926_5_, final double p_179926_7_) {
        final double var9 = (p_179926_1_ - this.field_179927_g.posX) / p_179926_7_;
        final double var10 = (p_179926_3_ - this.field_179927_g.posY) / p_179926_7_;
        final double var11 = (p_179926_5_ - this.field_179927_g.posZ) / p_179926_7_;
        AxisAlignedBB var12 = this.field_179927_g.getEntityBoundingBox();
        for (int var13 = 1; var13 < p_179926_7_; ++var13) {
            var12 = var12.offset(var9, var10, var11);
            if (!this.field_179927_g.worldObj.getCollidingBoundingBoxes(this.field_179927_g, var12).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
