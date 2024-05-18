package net.minecraft.entity.monster;

import net.minecraft.entity.passive.*;
import com.google.common.base.*;
import net.minecraft.entity.*;

public interface IMob extends IAnimals
{
    public static final Predicate<Entity> mobSelector = new Predicate<Entity>() {
        public boolean apply(final Object o) {
            return this.apply((Entity)o);
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
                if (-1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean apply(final Entity entity) {
            return entity instanceof IMob;
        }
    };
    public static final Predicate<Entity> VISIBLE_MOB_SELECTOR = new Predicate<Entity>() {
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean apply(final Entity entity) {
            if (entity instanceof IMob && !entity.isInvisible()) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public boolean apply(final Object o) {
            return this.apply((Entity)o);
        }
    };
}
