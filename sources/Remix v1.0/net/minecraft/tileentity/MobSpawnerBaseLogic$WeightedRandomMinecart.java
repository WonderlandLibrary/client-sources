package net.minecraft.tileentity;

import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;

public class WeightedRandomMinecart extends WeightedRandom.Item
{
    private final NBTTagCompound field_98222_b;
    private final String entityType;
    
    public WeightedRandomMinecart(final MobSpawnerBaseLogic this$0, final NBTTagCompound p_i1945_2_) {
        this(this$0, p_i1945_2_.getCompoundTag("Properties"), p_i1945_2_.getString("Type"), p_i1945_2_.getInteger("Weight"));
    }
    
    public WeightedRandomMinecart(final MobSpawnerBaseLogic this$0, final NBTTagCompound p_i1946_2_, final String p_i1946_3_) {
        this(this$0, p_i1946_2_, p_i1946_3_, 1);
    }
    
    private WeightedRandomMinecart(final NBTTagCompound p_i45757_2_, String p_i45757_3_, final int p_i45757_4_) {
        super(p_i45757_4_);
        if (p_i45757_3_.equals("Minecart")) {
            if (p_i45757_2_ != null) {
                p_i45757_3_ = EntityMinecart.EnumMinecartType.func_180038_a(p_i45757_2_.getInteger("Type")).func_180040_b();
            }
            else {
                p_i45757_3_ = "MinecartRideable";
            }
        }
        this.field_98222_b = p_i45757_2_;
        this.entityType = p_i45757_3_;
    }
    
    public NBTTagCompound func_98220_a() {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.setTag("Properties", this.field_98222_b);
        var1.setString("Type", this.entityType);
        var1.setInteger("Weight", this.itemWeight);
        return var1;
    }
}
