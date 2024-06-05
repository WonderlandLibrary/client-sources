package net.minecraft.entity;

import net.minecraft.stats.*;

public static class EntityEggInfo
{
    public final int spawnedID;
    public final int primaryColor;
    public final int secondaryColor;
    public final StatBase field_151512_d;
    public final StatBase field_151513_e;
    
    public EntityEggInfo(final int p_i1583_1_, final int p_i1583_2_, final int p_i1583_3_) {
        this.spawnedID = p_i1583_1_;
        this.primaryColor = p_i1583_2_;
        this.secondaryColor = p_i1583_3_;
        this.field_151512_d = StatList.func_151182_a(this);
        this.field_151513_e = StatList.func_151176_b(this);
    }
}
