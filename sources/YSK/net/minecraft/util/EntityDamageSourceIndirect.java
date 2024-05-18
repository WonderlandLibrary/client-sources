package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.item.*;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
    private static final String[] I;
    private Entity indirectEntity;
    
    @Override
    public Entity getSourceOfDamage() {
        return this.damageSourceEntity;
    }
    
    public EntityDamageSourceIndirect(final String s, final Entity entity, final Entity indirectEntity) {
        super(s, entity);
        this.indirectEntity = indirectEntity;
    }
    
    @Override
    public Entity getEntity() {
        return this.indirectEntity;
    }
    
    @Override
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        IChatComponent chatComponent;
        if (this.indirectEntity == null) {
            chatComponent = this.damageSourceEntity.getDisplayName();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            chatComponent = this.indirectEntity.getDisplayName();
        }
        final IChatComponent chatComponent2 = chatComponent;
        ItemStack heldItem;
        if (this.indirectEntity instanceof EntityLivingBase) {
            heldItem = ((EntityLivingBase)this.indirectEntity).getHeldItem();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            heldItem = null;
        }
        final ItemStack itemStack = heldItem;
        final String string = EntityDamageSourceIndirect.I["".length()] + this.damageType;
        final String string2 = String.valueOf(string) + EntityDamageSourceIndirect.I[" ".length()];
        ChatComponentTranslation chatComponentTranslation;
        if (itemStack != null && itemStack.hasDisplayName() && StatCollector.canTranslate(string2)) {
            final String s;
            final Object[] array;
            chatComponentTranslation = new ChatComponentTranslation(s, array);
            s = string2;
            array = new Object["   ".length()];
            array["".length()] = entityLivingBase.getDisplayName();
            array[" ".length()] = chatComponent2;
            array["  ".length()] = itemStack.getChatComponent();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            final String s2;
            final Object[] array2;
            chatComponentTranslation = new ChatComponentTranslation(s2, array2);
            s2 = string;
            array2 = new Object["  ".length()];
            array2["".length()] = entityLivingBase.getDisplayName();
            array2[" ".length()] = chatComponent2;
        }
        return chatComponentTranslation;
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(" \r\u0017\u0011\u0018j\t\u0002\u0011\u0011'\u0003X", "Dhvep");
        EntityDamageSourceIndirect.I[" ".length()] = I("c\u000b\u001c\u0013.", "MbhvC");
    }
}
