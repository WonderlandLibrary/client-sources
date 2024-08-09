/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public class FoodStats {
    private int foodLevel = 20;
    private float foodSaturationLevel = 5.0f;
    private float foodExhaustionLevel;
    private int foodTimer;
    private int prevFoodLevel = 20;

    public void addStats(int n, float f) {
        this.foodLevel = Math.min(n + this.foodLevel, 20);
        this.foodSaturationLevel = Math.min(this.foodSaturationLevel + (float)n * f * 2.0f, (float)this.foodLevel);
    }

    public void consume(Item item, ItemStack itemStack) {
        if (item.isFood()) {
            Food food = item.getFood();
            this.addStats(food.getHealing(), food.getSaturation());
        }
    }

    public void tick(PlayerEntity playerEntity) {
        boolean bl;
        Difficulty difficulty = playerEntity.world.getDifficulty();
        this.prevFoodLevel = this.foodLevel;
        if (this.foodExhaustionLevel > 4.0f) {
            this.foodExhaustionLevel -= 4.0f;
            if (this.foodSaturationLevel > 0.0f) {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0f, 0.0f);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }
        if ((bl = playerEntity.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION)) && this.foodSaturationLevel > 0.0f && playerEntity.shouldHeal() && this.foodLevel >= 20) {
            ++this.foodTimer;
            if (this.foodTimer >= 10) {
                float f = Math.min(this.foodSaturationLevel, 6.0f);
                playerEntity.heal(f / 6.0f);
                this.addExhaustion(f);
                this.foodTimer = 0;
            }
        } else if (bl && this.foodLevel >= 18 && playerEntity.shouldHeal()) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                playerEntity.heal(1.0f);
                this.addExhaustion(6.0f);
                this.foodTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.foodTimer;
            if (this.foodTimer >= 80) {
                if (playerEntity.getHealth() > 10.0f || difficulty == Difficulty.HARD || playerEntity.getHealth() > 1.0f && difficulty == Difficulty.NORMAL) {
                    playerEntity.attackEntityFrom(DamageSource.STARVE, 1.0f);
                }
                this.foodTimer = 0;
            }
        } else {
            this.foodTimer = 0;
        }
    }

    public void read(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("foodLevel", 0)) {
            this.foodLevel = compoundNBT.getInt("foodLevel");
            this.foodTimer = compoundNBT.getInt("foodTickTimer");
            this.foodSaturationLevel = compoundNBT.getFloat("foodSaturationLevel");
            this.foodExhaustionLevel = compoundNBT.getFloat("foodExhaustionLevel");
        }
    }

    public void write(CompoundNBT compoundNBT) {
        compoundNBT.putInt("foodLevel", this.foodLevel);
        compoundNBT.putInt("foodTickTimer", this.foodTimer);
        compoundNBT.putFloat("foodSaturationLevel", this.foodSaturationLevel);
        compoundNBT.putFloat("foodExhaustionLevel", this.foodExhaustionLevel);
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public boolean needFood() {
        return this.foodLevel < 20;
    }

    public void addExhaustion(float f) {
        this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + f, 40.0f);
    }

    public float getSaturationLevel() {
        return this.foodSaturationLevel;
    }

    public void setFoodLevel(int n) {
        this.foodLevel = n;
    }

    public void setFoodSaturationLevel(float f) {
        this.foodSaturationLevel = f;
    }
}

