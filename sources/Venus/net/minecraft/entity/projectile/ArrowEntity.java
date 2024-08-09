/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ArrowEntity
extends AbstractArrowEntity {
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(ArrowEntity.class, DataSerializers.VARINT);
    private Potion potion = Potions.EMPTY;
    private final Set<EffectInstance> customPotionEffects = Sets.newHashSet();
    private boolean fixedColor;

    public ArrowEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super((EntityType<? extends AbstractArrowEntity>)entityType, world);
    }

    public ArrowEntity(World world, double d, double d2, double d3) {
        super(EntityType.ARROW, d, d2, d3, world);
    }

    public ArrowEntity(World world, LivingEntity livingEntity) {
        super(EntityType.ARROW, livingEntity, world);
    }

    public void setPotionEffect(ItemStack itemStack) {
        if (itemStack.getItem() == Items.TIPPED_ARROW) {
            int n;
            this.potion = PotionUtils.getPotionFromItem(itemStack);
            List<EffectInstance> list = PotionUtils.getFullEffectsFromItem(itemStack);
            if (!list.isEmpty()) {
                for (EffectInstance effectInstance : list) {
                    this.customPotionEffects.add(new EffectInstance(effectInstance));
                }
            }
            if ((n = ArrowEntity.getCustomColor(itemStack)) == -1) {
                this.refreshColor();
            } else {
                this.setFixedColor(n);
            }
        } else if (itemStack.getItem() == Items.ARROW) {
            this.potion = Potions.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
        }
    }

    public static int getCustomColor(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        return compoundNBT != null && compoundNBT.contains("CustomPotionColor", 0) ? compoundNBT.getInt("CustomPotionColor") : -1;
    }

    private void refreshColor() {
        this.fixedColor = false;
        if (this.potion == Potions.EMPTY && this.customPotionEffects.isEmpty()) {
            this.dataManager.set(COLOR, -1);
        } else {
            this.dataManager.set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
        }
    }

    public void addEffect(EffectInstance effectInstance) {
        this.customPotionEffects.add(effectInstance);
        this.getDataManager().set(COLOR, PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.customPotionEffects)));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(COLOR, -1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isRemote) {
            if (this.inGround) {
                if (this.timeInGround % 5 == 0) {
                    this.spawnPotionParticles(1);
                }
            } else {
                this.spawnPotionParticles(2);
            }
        } else if (this.inGround && this.timeInGround != 0 && !this.customPotionEffects.isEmpty() && this.timeInGround >= 600) {
            this.world.setEntityState(this, (byte)0);
            this.potion = Potions.EMPTY;
            this.customPotionEffects.clear();
            this.dataManager.set(COLOR, -1);
        }
    }

    private void spawnPotionParticles(int n) {
        int n2 = this.getColor();
        if (n2 != -1 && n > 0) {
            double d = (double)(n2 >> 16 & 0xFF) / 255.0;
            double d2 = (double)(n2 >> 8 & 0xFF) / 255.0;
            double d3 = (double)(n2 >> 0 & 0xFF) / 255.0;
            for (int i = 0; i < n; ++i) {
                this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosXRandom(0.5), this.getPosYRandom(), this.getPosZRandom(0.5), d, d2, d3);
            }
        }
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    private void setFixedColor(int n) {
        this.fixedColor = true;
        this.dataManager.set(COLOR, n);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.potion != Potions.EMPTY && this.potion != null) {
            compoundNBT.putString("Potion", Registry.POTION.getKey(this.potion).toString());
        }
        if (this.fixedColor) {
            compoundNBT.putInt("Color", this.getColor());
        }
        if (!this.customPotionEffects.isEmpty()) {
            ListNBT listNBT = new ListNBT();
            for (EffectInstance effectInstance : this.customPotionEffects) {
                listNBT.add(effectInstance.write(new CompoundNBT()));
            }
            compoundNBT.put("CustomPotionEffects", listNBT);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("Potion", 1)) {
            this.potion = PotionUtils.getPotionTypeFromNBT(compoundNBT);
        }
        for (EffectInstance effectInstance : PotionUtils.getFullEffectsFromTag(compoundNBT)) {
            this.addEffect(effectInstance);
        }
        if (compoundNBT.contains("Color", 0)) {
            this.setFixedColor(compoundNBT.getInt("Color"));
        } else {
            this.refreshColor();
        }
    }

    @Override
    protected void arrowHit(LivingEntity livingEntity) {
        super.arrowHit(livingEntity);
        for (EffectInstance effectInstance : this.potion.getEffects()) {
            livingEntity.addPotionEffect(new EffectInstance(effectInstance.getPotion(), Math.max(effectInstance.getDuration() / 8, 1), effectInstance.getAmplifier(), effectInstance.isAmbient(), effectInstance.doesShowParticles()));
        }
        if (!this.customPotionEffects.isEmpty()) {
            for (EffectInstance effectInstance : this.customPotionEffects) {
                livingEntity.addPotionEffect(effectInstance);
            }
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        if (this.customPotionEffects.isEmpty() && this.potion == Potions.EMPTY) {
            return new ItemStack(Items.ARROW);
        }
        ItemStack itemStack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.addPotionToItemStack(itemStack, this.potion);
        PotionUtils.appendEffects(itemStack, this.customPotionEffects);
        if (this.fixedColor) {
            itemStack.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
        }
        return itemStack;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 0) {
            int n = this.getColor();
            if (n != -1) {
                double d = (double)(n >> 16 & 0xFF) / 255.0;
                double d2 = (double)(n >> 8 & 0xFF) / 255.0;
                double d3 = (double)(n >> 0 & 0xFF) / 255.0;
                for (int i = 0; i < 20; ++i) {
                    this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getPosXRandom(0.5), this.getPosYRandom(), this.getPosZRandom(0.5), d, d2, d3);
                }
            }
        } else {
            super.handleStatusUpdate(by);
        }
    }
}

