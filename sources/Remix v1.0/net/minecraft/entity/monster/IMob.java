package net.minecraft.entity.monster;

import net.minecraft.entity.passive.*;
import com.google.common.base.*;
import net.minecraft.entity.*;

public interface IMob extends IAnimals
{
    public static final Predicate mobSelector = new Predicate() {
        public boolean func_179983_a(final Entity p_179983_1_) {
            return p_179983_1_ instanceof IMob;
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.func_179983_a((Entity)p_apply_1_);
        }
    };
    public static final Predicate field_175450_e = new Predicate() {
        public boolean func_179982_a(final Entity p_179982_1_) {
            return p_179982_1_ instanceof IMob && !p_179982_1_.isInvisible();
        }
        
        public boolean apply(final Object p_apply_1_) {
            return this.func_179982_a((Entity)p_apply_1_);
        }
    };
}
