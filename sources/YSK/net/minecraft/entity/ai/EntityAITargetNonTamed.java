package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import com.google.common.base.*;
import net.minecraft.entity.*;

public class EntityAITargetNonTamed<T extends EntityLivingBase> extends EntityAINearestAttackableTarget
{
    private EntityTameable theTameable;
    
    @Override
    public boolean shouldExecute() {
        if (!this.theTameable.isTamed() && super.shouldExecute()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityAITargetNonTamed(final EntityTameable theTameable, final Class<T> clazz, final boolean b, final Predicate<? super T> predicate) {
        super(theTameable, clazz, 0x57 ^ 0x5D, b, "".length() != 0, predicate);
        this.theTameable = theTameable;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
