package net.minecraft.entity.monster;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.potion.*;

public static class GroupData implements IEntityLivingData
{
    public int field_111105_a;
    
    public void func_111104_a(final Random p_111104_1_) {
        final int var2 = p_111104_1_.nextInt(5);
        if (var2 <= 1) {
            this.field_111105_a = Potion.moveSpeed.id;
        }
        else if (var2 <= 2) {
            this.field_111105_a = Potion.damageBoost.id;
        }
        else if (var2 <= 3) {
            this.field_111105_a = Potion.regeneration.id;
        }
        else if (var2 <= 4) {
            this.field_111105_a = Potion.invisibility.id;
        }
    }
}
