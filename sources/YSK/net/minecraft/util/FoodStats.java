package net.minecraft.util;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class FoodStats
{
    private static final String[] I;
    private int foodTimer;
    private float foodExhaustionLevel;
    private float foodSaturationLevel;
    private int prevFoodLevel;
    private int foodLevel;
    
    public boolean needFood() {
        if (this.foodLevel < (0x6E ^ 0x7A)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getFoodLevel() {
        return this.foodLevel;
    }
    
    public void addStats(final int n, final float n2) {
        this.foodLevel = Math.min(n + this.foodLevel, 0x6E ^ 0x7A);
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + n * n2 * 2.0f, this.foodLevel);
    }
    
    public void onUpdate(final EntityPlayer entityPlayer) {
        final EnumDifficulty difficulty = entityPlayer.worldObj.getDifficulty();
        this.prevFoodLevel = this.foodLevel;
        if (this.foodExhaustionLevel > 4.0f) {
            this.foodExhaustionLevel -= 4.0f;
            if (this.foodSaturationLevel > 0.0f) {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0f, 0.0f);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else if (difficulty != EnumDifficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - " ".length(), "".length());
            }
        }
        if (entityPlayer.worldObj.getGameRules().getBoolean(FoodStats.I["".length()]) && this.foodLevel >= (0x2 ^ 0x10) && entityPlayer.shouldHeal()) {
            this.foodTimer += " ".length();
            if (this.foodTimer >= (0x42 ^ 0x12)) {
                entityPlayer.heal(1.0f);
                this.addExhaustion(3.0f);
                this.foodTimer = "".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
        }
        else if (this.foodLevel <= 0) {
            this.foodTimer += " ".length();
            if (this.foodTimer >= (0xEA ^ 0xBA)) {
                if (entityPlayer.getHealth() > 10.0f || difficulty == EnumDifficulty.HARD || (entityPlayer.getHealth() > 1.0f && difficulty == EnumDifficulty.NORMAL)) {
                    entityPlayer.attackEntityFrom(DamageSource.starve, 1.0f);
                }
                this.foodTimer = "".length();
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
        }
        else {
            this.foodTimer = "".length();
        }
    }
    
    static {
        I();
    }
    
    public float getSaturationLevel() {
        return this.foodSaturationLevel;
    }
    
    public FoodStats() {
        this.foodLevel = (0x99 ^ 0x8D);
        this.foodSaturationLevel = 5.0f;
        this.prevFoodLevel = (0x80 ^ 0x94);
    }
    
    public void addExhaustion(final float n) {
        this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + n, 40.0f);
    }
    
    public void addStats(final ItemFood itemFood, final ItemStack itemStack) {
        this.addStats(itemFood.getHealAmount(itemStack), itemFood.getSaturationModifier(itemStack));
    }
    
    public void setFoodSaturationLevel(final float foodSaturationLevel) {
        this.foodSaturationLevel = foodSaturationLevel;
    }
    
    public int getPrevFoodLevel() {
        return this.prevFoodLevel;
    }
    
    public void readNBT(final NBTTagCompound nbtTagCompound) {
        if (nbtTagCompound.hasKey(FoodStats.I[" ".length()], 0x3F ^ 0x5C)) {
            this.foodLevel = nbtTagCompound.getInteger(FoodStats.I["  ".length()]);
            this.foodTimer = nbtTagCompound.getInteger(FoodStats.I["   ".length()]);
            this.foodSaturationLevel = nbtTagCompound.getFloat(FoodStats.I[0x19 ^ 0x1D]);
            this.foodExhaustionLevel = nbtTagCompound.getFloat(FoodStats.I[0x36 ^ 0x33]);
        }
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
            if (3 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setFoodLevel(final int foodLevel) {
        this.foodLevel = foodLevel;
    }
    
    public void writeNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setInteger(FoodStats.I[0x95 ^ 0x93], this.foodLevel);
        nbtTagCompound.setInteger(FoodStats.I[0xB9 ^ 0xBE], this.foodTimer);
        nbtTagCompound.setFloat(FoodStats.I[0x57 ^ 0x5F], this.foodSaturationLevel);
        nbtTagCompound.setFloat(FoodStats.I[0x8A ^ 0x83], this.foodExhaustionLevel);
    }
    
    private static void I() {
        (I = new String[0x5C ^ 0x56])["".length()] = I("\u001e\u0016=\u0001\u001e\u0011\u001b\u001b\u0011\u000b\u0015\u0019,\u0006\r\u0004\u001e&\u001a", "pwItl");
        FoodStats.I[" ".length()] = I(",\u000e!+\u001b/\u0017+#", "JaNOW");
        FoodStats.I["  ".length()] = I("%\u00185\u0000\u0002&\u0001?\b", "CwZdN");
        FoodStats.I["   ".length()] = I("\f\u001c8-\u0002\u0003\u0010<\u001d?\u0007\u0016%", "jsWIV");
        FoodStats.I[0xBD ^ 0xB9] = I("\b\f\u0006\u0006%\u000f\u0017\u001c\u0010\u0017\u001a\n\u0006\f:\u000b\u0015\f\u000e", "ncibv");
        FoodStats.I[0x1E ^ 0x1B] = I("3\u001b8+!-\u001c6:\u0017!\u001d8!(0\u00022#", "UtWOd");
        FoodStats.I[0x86 ^ 0x80] = I("\u0000>\u001a,\u0003\u0003'\u0010$", "fQuHO");
        FoodStats.I[0x1F ^ 0x18] = I("\u00027\u000e\n\u0017\r;\n:*\t=\u0013", "dXanC");
        FoodStats.I[0x60 ^ 0x68] = I("\u0001 \u0015(2\u0006;\u000f>\u0000\u0013&\u0015\"-\u00029\u001f ", "gOzLa");
        FoodStats.I[0x47 ^ 0x4E] = I("\f\u00187\u001c\t\u0012\u001f9\r?\u001e\u001e7\u0016\u0000\u000f\u0001=\u0014", "jwXxL");
    }
}
