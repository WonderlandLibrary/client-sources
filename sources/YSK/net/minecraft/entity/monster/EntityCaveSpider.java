package net.minecraft.entity.monster;

import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;

public class EntityCaveSpider extends EntitySpider
{
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        return entityLivingData;
    }
    
    @Override
    public float getEyeHeight() {
        return 0.45f;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                int length = "".length();
                if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
                    length = (0x74 ^ 0x73);
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                }
                else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                    length = (0x9A ^ 0x95);
                }
                if (length > 0) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.poison.id, length * (0x7A ^ 0x6E), "".length()));
                }
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityCaveSpider(final World world) {
        super(world);
        this.setSize(0.7f, 0.5f);
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
    }
}
