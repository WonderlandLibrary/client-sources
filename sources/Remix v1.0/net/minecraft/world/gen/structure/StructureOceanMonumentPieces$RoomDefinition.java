package net.minecraft.world.gen.structure;

import net.minecraft.util.*;

static class RoomDefinition
{
    int field_175967_a;
    RoomDefinition[] field_175965_b;
    boolean[] field_175966_c;
    boolean field_175963_d;
    boolean field_175964_e;
    int field_175962_f;
    
    public RoomDefinition(final int p_i45584_1_) {
        this.field_175965_b = new RoomDefinition[6];
        this.field_175966_c = new boolean[6];
        this.field_175967_a = p_i45584_1_;
    }
    
    public void func_175957_a(final EnumFacing p_175957_1_, final RoomDefinition p_175957_2_) {
        this.field_175965_b[p_175957_1_.getIndex()] = p_175957_2_;
        p_175957_2_.field_175965_b[p_175957_1_.getOpposite().getIndex()] = this;
    }
    
    public void func_175958_a() {
        for (int var1 = 0; var1 < 6; ++var1) {
            this.field_175966_c[var1] = (this.field_175965_b[var1] != null);
        }
    }
    
    public boolean func_175959_a(final int p_175959_1_) {
        if (this.field_175964_e) {
            return true;
        }
        this.field_175962_f = p_175959_1_;
        for (int var2 = 0; var2 < 6; ++var2) {
            if (this.field_175965_b[var2] != null && this.field_175966_c[var2] && this.field_175965_b[var2].field_175962_f != p_175959_1_ && this.field_175965_b[var2].func_175959_a(p_175959_1_)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean func_175961_b() {
        return this.field_175967_a >= 75;
    }
    
    public int func_175960_c() {
        int var1 = 0;
        for (int var2 = 0; var2 < 6; ++var2) {
            if (this.field_175966_c[var2]) {
                ++var1;
            }
        }
        return var1;
    }
}
