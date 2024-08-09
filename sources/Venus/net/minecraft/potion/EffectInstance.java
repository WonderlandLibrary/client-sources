/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import com.google.common.collect.ComparisonChain;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EffectInstance
implements Comparable<EffectInstance> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Effect potion;
    private int duration;
    private int amplifier;
    private boolean isSplashPotion;
    private boolean ambient;
    private boolean isPotionDurationMax;
    private boolean showParticles;
    private boolean showIcon;
    @Nullable
    private EffectInstance hiddenEffects;

    public EffectInstance(Effect effect) {
        this(effect, 0, 0);
    }

    public EffectInstance(Effect effect, int n) {
        this(effect, n, 0);
    }

    public EffectInstance(Effect effect, int n, int n2) {
        this(effect, n, n2, false, true);
    }

    public EffectInstance(Effect effect, int n, int n2, boolean bl, boolean bl2) {
        this(effect, n, n2, bl, bl2, bl2);
    }

    public EffectInstance(Effect effect, int n, int n2, boolean bl, boolean bl2, boolean bl3) {
        this(effect, n, n2, bl, bl2, bl3, null);
    }

    public EffectInstance(Effect effect, int n, int n2, boolean bl, boolean bl2, boolean bl3, @Nullable EffectInstance effectInstance) {
        this.potion = effect;
        this.duration = n;
        this.amplifier = n2;
        this.ambient = bl;
        this.showParticles = bl2;
        this.showIcon = bl3;
        this.hiddenEffects = effectInstance;
    }

    public EffectInstance(EffectInstance effectInstance) {
        this.potion = effectInstance.potion;
        this.func_230117_a_(effectInstance);
    }

    void func_230117_a_(EffectInstance effectInstance) {
        this.duration = effectInstance.duration;
        this.amplifier = effectInstance.amplifier;
        this.ambient = effectInstance.ambient;
        this.showParticles = effectInstance.showParticles;
        this.showIcon = effectInstance.showIcon;
    }

    public boolean combine(EffectInstance effectInstance) {
        if (this.potion != effectInstance.potion) {
            LOGGER.warn("This method should only be called for matching effects!");
        }
        boolean bl = false;
        if (effectInstance.amplifier > this.amplifier) {
            if (effectInstance.duration < this.duration) {
                EffectInstance effectInstance2 = this.hiddenEffects;
                this.hiddenEffects = new EffectInstance(this);
                this.hiddenEffects.hiddenEffects = effectInstance2;
            }
            this.amplifier = effectInstance.amplifier;
            this.duration = effectInstance.duration;
            bl = true;
        } else if (effectInstance.duration > this.duration) {
            if (effectInstance.amplifier == this.amplifier) {
                this.duration = effectInstance.duration;
                bl = true;
            } else if (this.hiddenEffects == null) {
                this.hiddenEffects = new EffectInstance(effectInstance);
            } else {
                this.hiddenEffects.combine(effectInstance);
            }
        }
        if (!effectInstance.ambient && this.ambient || bl) {
            this.ambient = effectInstance.ambient;
            bl = true;
        }
        if (effectInstance.showParticles != this.showParticles) {
            this.showParticles = effectInstance.showParticles;
            bl = true;
        }
        if (effectInstance.showIcon != this.showIcon) {
            this.showIcon = effectInstance.showIcon;
            bl = true;
        }
        return bl;
    }

    public Effect getPotion() {
        return this.potion;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public boolean isAmbient() {
        return this.ambient;
    }

    public boolean doesShowParticles() {
        return this.showParticles;
    }

    public boolean isShowIcon() {
        return this.showIcon;
    }

    public boolean tick(LivingEntity livingEntity, Runnable runnable) {
        if (this.duration > 0) {
            if (this.potion.isReady(this.duration, this.amplifier)) {
                this.performEffect(livingEntity);
            }
            this.deincrementDuration();
            if (this.duration == 0 && this.hiddenEffects != null) {
                this.func_230117_a_(this.hiddenEffects);
                this.hiddenEffects = this.hiddenEffects.hiddenEffects;
                runnable.run();
            }
        }
        return this.duration > 0;
    }

    private int deincrementDuration() {
        if (this.hiddenEffects != null) {
            this.hiddenEffects.deincrementDuration();
        }
        return --this.duration;
    }

    public void performEffect(LivingEntity livingEntity) {
        if (this.duration > 0) {
            this.potion.performEffect(livingEntity, this.amplifier);
        }
    }

    public String getEffectName() {
        return this.potion.getName();
    }

    public String toString() {
        String string = this.amplifier > 0 ? this.getEffectName() + " x " + (this.amplifier + 1) + ", Duration: " + this.duration : this.getEffectName() + ", Duration: " + this.duration;
        if (this.isSplashPotion) {
            string = string + ", Splash: true";
        }
        if (!this.showParticles) {
            string = string + ", Particles: false";
        }
        if (!this.showIcon) {
            string = string + ", Show Icon: false";
        }
        return string;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof EffectInstance)) {
            return true;
        }
        EffectInstance effectInstance = (EffectInstance)object;
        return this.duration == effectInstance.duration && this.amplifier == effectInstance.amplifier && this.isSplashPotion == effectInstance.isSplashPotion && this.ambient == effectInstance.ambient && this.potion.equals(effectInstance.potion);
    }

    public int hashCode() {
        int n = this.potion.hashCode();
        n = 31 * n + this.duration;
        n = 31 * n + this.amplifier;
        n = 31 * n + (this.isSplashPotion ? 1 : 0);
        return 31 * n + (this.ambient ? 1 : 0);
    }

    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putByte("Id", (byte)Effect.getId(this.getPotion()));
        this.writeInternal(compoundNBT);
        return compoundNBT;
    }

    private void writeInternal(CompoundNBT compoundNBT) {
        compoundNBT.putByte("Amplifier", (byte)this.getAmplifier());
        compoundNBT.putInt("Duration", this.getDuration());
        compoundNBT.putBoolean("Ambient", this.isAmbient());
        compoundNBT.putBoolean("ShowParticles", this.doesShowParticles());
        compoundNBT.putBoolean("ShowIcon", this.isShowIcon());
        if (this.hiddenEffects != null) {
            CompoundNBT compoundNBT2 = new CompoundNBT();
            this.hiddenEffects.write(compoundNBT2);
            compoundNBT.put("HiddenEffect", compoundNBT2);
        }
    }

    public static EffectInstance read(CompoundNBT compoundNBT) {
        byte by = compoundNBT.getByte("Id");
        Effect effect = Effect.get(by);
        return effect == null ? null : EffectInstance.readInternal(effect, compoundNBT);
    }

    private static EffectInstance readInternal(Effect effect, CompoundNBT compoundNBT) {
        byte by = compoundNBT.getByte("Amplifier");
        int n = compoundNBT.getInt("Duration");
        boolean bl = compoundNBT.getBoolean("Ambient");
        boolean bl2 = true;
        if (compoundNBT.contains("ShowParticles", 0)) {
            bl2 = compoundNBT.getBoolean("ShowParticles");
        }
        boolean bl3 = bl2;
        if (compoundNBT.contains("ShowIcon", 0)) {
            bl3 = compoundNBT.getBoolean("ShowIcon");
        }
        EffectInstance effectInstance = null;
        if (compoundNBT.contains("HiddenEffect", 1)) {
            effectInstance = EffectInstance.readInternal(effect, compoundNBT.getCompound("HiddenEffect"));
        }
        return new EffectInstance(effect, n, by < 0 ? (byte)0 : by, bl, bl2, bl3, effectInstance);
    }

    public void setPotionDurationMax(boolean bl) {
        this.isPotionDurationMax = bl;
    }

    public boolean getIsPotionDurationMax() {
        return this.isPotionDurationMax;
    }

    @Override
    public int compareTo(EffectInstance effectInstance) {
        int n = 32147;
        return !(this.getDuration() > 32147 && effectInstance.getDuration() > 32147 || this.isAmbient() && effectInstance.isAmbient()) ? ComparisonChain.start().compare(this.isAmbient(), effectInstance.isAmbient()).compare(this.getDuration(), effectInstance.getDuration()).compare(this.getPotion().getLiquidColor(), effectInstance.getPotion().getLiquidColor()).result() : ComparisonChain.start().compare(this.isAmbient(), effectInstance.isAmbient()).compare(this.getPotion().getLiquidColor(), effectInstance.getPotion().getLiquidColor()).result();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((EffectInstance)object);
    }
}

