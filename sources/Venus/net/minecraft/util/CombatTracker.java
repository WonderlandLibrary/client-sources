/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.CombatEntry;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CombatTracker {
    private final List<CombatEntry> combatEntries = Lists.newArrayList();
    private final LivingEntity fighter;
    private int lastDamageTime;
    private int combatStartTime;
    private int combatEndTime;
    private boolean inCombat;
    private boolean takingDamage;
    private String fallSuffix;

    public CombatTracker(LivingEntity livingEntity) {
        this.fighter = livingEntity;
    }

    public void calculateFallSuffix() {
        this.resetFallSuffix();
        Optional<BlockPos> optional = this.fighter.func_233644_dn_();
        if (optional.isPresent()) {
            BlockState blockState = this.fighter.world.getBlockState(optional.get());
            this.fallSuffix = !blockState.isIn(Blocks.LADDER) && !blockState.isIn(BlockTags.TRAPDOORS) ? (blockState.isIn(Blocks.VINE) ? "vines" : (!blockState.isIn(Blocks.WEEPING_VINES) && !blockState.isIn(Blocks.WEEPING_VINES_PLANT) ? (!blockState.isIn(Blocks.TWISTING_VINES) && !blockState.isIn(Blocks.TWISTING_VINES_PLANT) ? (blockState.isIn(Blocks.SCAFFOLDING) ? "scaffolding" : "other_climbable") : "twisting_vines") : "weeping_vines")) : "ladder";
        } else if (this.fighter.isInWater()) {
            this.fallSuffix = "water";
        }
    }

    public void trackDamage(DamageSource damageSource, float f, float f2) {
        this.reset();
        this.calculateFallSuffix();
        CombatEntry combatEntry = new CombatEntry(damageSource, this.fighter.ticksExisted, f, f2, this.fallSuffix, this.fighter.fallDistance);
        this.combatEntries.add(combatEntry);
        this.lastDamageTime = this.fighter.ticksExisted;
        this.takingDamage = true;
        if (combatEntry.isLivingDamageSrc() && !this.inCombat && this.fighter.isAlive()) {
            this.inCombat = true;
            this.combatEndTime = this.combatStartTime = this.fighter.ticksExisted;
            this.fighter.sendEnterCombat();
        }
    }

    public ITextComponent getDeathMessage() {
        ITextComponent iTextComponent;
        if (this.combatEntries.isEmpty()) {
            return new TranslationTextComponent("death.attack.generic", this.fighter.getDisplayName());
        }
        CombatEntry combatEntry = this.getBestCombatEntry();
        CombatEntry combatEntry2 = this.combatEntries.get(this.combatEntries.size() - 1);
        ITextComponent iTextComponent2 = combatEntry2.getDamageSrcDisplayName();
        Entity entity2 = combatEntry2.getDamageSrc().getTrueSource();
        if (combatEntry != null && combatEntry2.getDamageSrc() == DamageSource.FALL) {
            ITextComponent iTextComponent3 = combatEntry.getDamageSrcDisplayName();
            if (combatEntry.getDamageSrc() != DamageSource.FALL && combatEntry.getDamageSrc() != DamageSource.OUT_OF_WORLD) {
                if (!(iTextComponent3 == null || iTextComponent2 != null && iTextComponent3.equals(iTextComponent2))) {
                    ItemStack itemStack;
                    Entity entity3 = combatEntry.getDamageSrc().getTrueSource();
                    ItemStack itemStack2 = itemStack = entity3 instanceof LivingEntity ? ((LivingEntity)entity3).getHeldItemMainhand() : ItemStack.EMPTY;
                    iTextComponent = !itemStack.isEmpty() && itemStack.hasDisplayName() ? new TranslationTextComponent("death.fell.assist.item", this.fighter.getDisplayName(), iTextComponent3, itemStack.getTextComponent()) : new TranslationTextComponent("death.fell.assist", this.fighter.getDisplayName(), iTextComponent3);
                } else if (iTextComponent2 != null) {
                    ItemStack itemStack;
                    ItemStack itemStack3 = itemStack = entity2 instanceof LivingEntity ? ((LivingEntity)entity2).getHeldItemMainhand() : ItemStack.EMPTY;
                    iTextComponent = !itemStack.isEmpty() && itemStack.hasDisplayName() ? new TranslationTextComponent("death.fell.finish.item", this.fighter.getDisplayName(), iTextComponent2, itemStack.getTextComponent()) : new TranslationTextComponent("death.fell.finish", this.fighter.getDisplayName(), iTextComponent2);
                } else {
                    iTextComponent = new TranslationTextComponent("death.fell.killer", this.fighter.getDisplayName());
                }
            } else {
                iTextComponent = new TranslationTextComponent("death.fell.accident." + this.getFallSuffix(combatEntry), this.fighter.getDisplayName());
            }
        } else {
            iTextComponent = combatEntry2.getDamageSrc().getDeathMessage(this.fighter);
        }
        return iTextComponent;
    }

    @Nullable
    public LivingEntity getBestAttacker() {
        LivingEntity livingEntity = null;
        PlayerEntity playerEntity = null;
        float f = 0.0f;
        float f2 = 0.0f;
        for (CombatEntry combatEntry : this.combatEntries) {
            if (combatEntry.getDamageSrc().getTrueSource() instanceof PlayerEntity && (playerEntity == null || combatEntry.getDamage() > f2)) {
                f2 = combatEntry.getDamage();
                playerEntity = (PlayerEntity)combatEntry.getDamageSrc().getTrueSource();
            }
            if (!(combatEntry.getDamageSrc().getTrueSource() instanceof LivingEntity) || livingEntity != null && !(combatEntry.getDamage() > f)) continue;
            f = combatEntry.getDamage();
            livingEntity = (LivingEntity)combatEntry.getDamageSrc().getTrueSource();
        }
        return playerEntity != null && f2 >= f / 3.0f ? playerEntity : livingEntity;
    }

    @Nullable
    private CombatEntry getBestCombatEntry() {
        CombatEntry combatEntry = null;
        CombatEntry combatEntry2 = null;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < this.combatEntries.size(); ++i) {
            CombatEntry combatEntry3;
            CombatEntry combatEntry4 = this.combatEntries.get(i);
            CombatEntry combatEntry5 = combatEntry3 = i > 0 ? this.combatEntries.get(i - 1) : null;
            if ((combatEntry4.getDamageSrc() == DamageSource.FALL || combatEntry4.getDamageSrc() == DamageSource.OUT_OF_WORLD) && combatEntry4.getDamageAmount() > 0.0f && (combatEntry == null || combatEntry4.getDamageAmount() > f2)) {
                combatEntry = i > 0 ? combatEntry3 : combatEntry4;
                f2 = combatEntry4.getDamageAmount();
            }
            if (combatEntry4.getFallSuffix() == null || combatEntry2 != null && !(combatEntry4.getDamage() > f)) continue;
            combatEntry2 = combatEntry4;
            f = combatEntry4.getDamage();
        }
        if (f2 > 5.0f && combatEntry != null) {
            return combatEntry;
        }
        return f > 5.0f && combatEntry2 != null ? combatEntry2 : null;
    }

    private String getFallSuffix(CombatEntry combatEntry) {
        return combatEntry.getFallSuffix() == null ? "generic" : combatEntry.getFallSuffix();
    }

    public int getCombatDuration() {
        return this.inCombat ? this.fighter.ticksExisted - this.combatStartTime : this.combatEndTime - this.combatStartTime;
    }

    private void resetFallSuffix() {
        this.fallSuffix = null;
    }

    public void reset() {
        int n;
        int n2 = n = this.inCombat ? 300 : 100;
        if (this.takingDamage && (!this.fighter.isAlive() || this.fighter.ticksExisted - this.lastDamageTime > n)) {
            boolean bl = this.inCombat;
            this.takingDamage = false;
            this.inCombat = false;
            this.combatEndTime = this.fighter.ticksExisted;
            if (bl) {
                this.fighter.sendEndCombat();
            }
            this.combatEntries.clear();
        }
    }

    public LivingEntity getFighter() {
        return this.fighter;
    }
}

