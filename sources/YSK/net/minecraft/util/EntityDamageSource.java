package net.minecraft.util;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class EntityDamageSource extends DamageSource
{
    private static final String[] I;
    protected Entity damageSourceEntity;
    private boolean isThornsDamage;
    
    @Override
    public boolean isDifficultyScaled() {
        if (this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof EntityPlayer)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public Entity getEntity() {
        return this.damageSourceEntity;
    }
    
    public EntityDamageSource setIsThornsDamage() {
        this.isThornsDamage = (" ".length() != 0);
        return this;
    }
    
    static {
        I();
    }
    
    public EntityDamageSource(final String s, final Entity damageSourceEntity) {
        super(s);
        this.isThornsDamage = ("".length() != 0);
        this.damageSourceEntity = damageSourceEntity;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("%\f&;<o\b3;5\"\u0002i", "AiGOT");
        EntityDamageSource.I[" ".length()] = I("g0\u001c\t>", "IYhlS");
    }
    
    @Override
    public IChatComponent getDeathMessage(final EntityLivingBase entityLivingBase) {
        ItemStack heldItem;
        if (this.damageSourceEntity instanceof EntityLivingBase) {
            heldItem = ((EntityLivingBase)this.damageSourceEntity).getHeldItem();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            heldItem = null;
        }
        final ItemStack itemStack = heldItem;
        final String string = EntityDamageSource.I["".length()] + this.damageType;
        final String string2 = String.valueOf(string) + EntityDamageSource.I[" ".length()];
        ChatComponentTranslation chatComponentTranslation;
        if (itemStack != null && itemStack.hasDisplayName() && StatCollector.canTranslate(string2)) {
            final String s;
            final Object[] array;
            chatComponentTranslation = new ChatComponentTranslation(s, array);
            s = string2;
            array = new Object["   ".length()];
            array["".length()] = entityLivingBase.getDisplayName();
            array[" ".length()] = this.damageSourceEntity.getDisplayName();
            array["  ".length()] = itemStack.getChatComponent();
            "".length();
            if (1 <= 0) {
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
            array2[" ".length()] = this.damageSourceEntity.getDisplayName();
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean getIsThornsDamage() {
        return this.isThornsDamage;
    }
}
