package net.minecraft.util;

import com.google.common.base.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public final class EntitySelectors
{
    public static final Predicate<Entity> IS_STANDALONE;
    public static final Predicate<Entity> selectInventories;
    public static final Predicate<Entity> selectAnything;
    public static final Predicate<Entity> NOT_SPECTATING;
    
    static {
        selectAnything = (Predicate)new Predicate<Entity>() {
            public boolean apply(final Entity entity) {
                return entity.isEntityAlive();
            }
            
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
                    if (2 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        IS_STANDALONE = (Predicate)new Predicate<Entity>() {
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
                    if (3 != 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                if (entity.isEntityAlive() && entity.riddenByEntity == null && entity.ridingEntity == null) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        };
        selectInventories = (Predicate)new Predicate<Entity>() {
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
                    if (4 == -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                if (entity instanceof IInventory && entity.isEntityAlive()) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        };
        NOT_SPECTATING = (Predicate)new Predicate<Entity>() {
            public boolean apply(final Entity entity) {
                if (entity instanceof EntityPlayer && ((EntityPlayer)entity).isSpectator()) {
                    return "".length() != 0;
                }
                return " ".length() != 0;
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
                    if (3 < 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
        };
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static class ArmoredMob implements Predicate<Entity>
    {
        private final ItemStack armor;
        
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
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean apply(final Entity entity) {
            if (!entity.isEntityAlive()) {
                return "".length() != 0;
            }
            if (!(entity instanceof EntityLivingBase)) {
                return "".length() != 0;
            }
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            int n;
            if (entityLivingBase.getEquipmentInSlot(EntityLiving.getArmorPosition(this.armor)) != null) {
                n = "".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else if (entityLivingBase instanceof EntityLiving) {
                n = (((EntityLiving)entityLivingBase).canPickUpLoot() ? 1 : 0);
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else if (entityLivingBase instanceof EntityArmorStand) {
                n = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n = ((entityLivingBase instanceof EntityPlayer) ? 1 : 0);
            }
            return n != 0;
        }
        
        public ArmoredMob(final ItemStack armor) {
            this.armor = armor;
        }
        
        public boolean apply(final Object o) {
            return this.apply((Entity)o);
        }
    }
}
