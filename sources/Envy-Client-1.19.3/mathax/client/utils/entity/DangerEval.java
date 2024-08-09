package mathax.client.utils.entity;

import com.google.common.collect.Multimap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.explosion.Explosion;

import java.util.Collection;
import java.util.logging.Logger;

public class DangerEval {
    public static final Logger LOGGER = Logger.getLogger(DangerEval.class.getName());

    public static double eval(PlayerEntity enemy) {
        PlayerEntity me = MinecraftClient.getInstance().player;
        if (me == null || enemy == null || me.isDead()) return 0;
        LOGGER.info("Evaluating danger of " + enemy.getName().getString());
        double damage = 0;

        Item enemy_item = enemy.getMainHandStack().getItem();
        LOGGER.info("enemy_item: " + enemy_item.toString());
        if (enemy_item instanceof SwordItem || enemy_item instanceof AxeItem){
            float final_health_dif = meleeAttackCalc(me, enemy);
            LOGGER.info("Final Health Dif: " + final_health_dif);
            if (final_health_dif < 0) {
                return MathHelper.clamp(0.5-(final_health_dif/enemy.getHealth()/2), 0, 1);
            } else if (final_health_dif > 0) {
                return MathHelper.clamp(0.5+(final_health_dif/me.getHealth()/2), 0, 1);
            } else {
                return 0.5;
            }
        } else if (enemy_item instanceof BedItem){
            if (!me.world.getDimension().bedWorks()){
                damage = explosionDamageCalc(5, me);
            }
        } else if (enemy_item == Items.END_CRYSTAL) {
            damage = explosionDamageCalc(6, me);
        } else if (enemy_item == Items.RESPAWN_ANCHOR ) {
            if (!me.world.getDimension().respawnAnchorWorks()){
                damage = explosionDamageCalc(5, me);
            }
        }
        return MathHelper.clamp(damage/me.getHealth(), 0, 1);
    }

    private static float meleeAttackCalc(PlayerEntity enemy, PlayerEntity me) {
        float health_me = me.getHealth() - modifyAppliedDamage(enemy, DamageSource.player(me), attack(enemy, me));
        float health_enemy = enemy.getHealth() - modifyAppliedDamage(me, DamageSource.player(enemy), attack(me, enemy));
        Logger.getLogger(DangerEval.class.getName()).info("Health Me: " + health_me);
        Logger.getLogger(DangerEval.class.getName()).info("Health Enemy: " + health_enemy);
        return health_enemy - health_me;
    }

    public static float explosionDamageCalc(int explosionPower, PlayerEntity me) {
        DamageSource dummy = DamageSource.explosion((Explosion) null);
        float damage = 14 * explosionPower + 1;
        switch (me.world.getDifficulty()) {
            case PEACEFUL -> damage = 1;
            case EASY -> damage = (float) (damage * 0.5);
            case HARD -> damage = (float) (damage * 1.5);
        }
        damage = modifyAppliedDamage(me, dummy, damage);
        return damage;
    }


    private static float attack(PlayerEntity target, PlayerEntity attacker) {
        if (target.isAttackable()) {
            if (!target.handleAttack(attacker)) {
                Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers = attacker.getMainHandStack().getAttributeModifiers(EquipmentSlot.MAINHAND);
                float base_item_attack_damage = attributeModifiers.containsKey(EntityAttributes.GENERIC_ATTACK_DAMAGE) ? (float) attributeModifiers.get(EntityAttributes.GENERIC_ATTACK_DAMAGE).stream().mapToDouble(EntityAttributeModifier::getValue).sum() : (float) attacker.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                float enchantment_attack_damage = EnchantmentHelper.getAttackDamage(attacker.getMainHandStack(), target.getGroup());
                float effect_attack_damage = 0;
                LOGGER.info("Base Item Attack Damage: " + base_item_attack_damage);
                LOGGER.info("Enchantment Attack Damage: " + enchantment_attack_damage);
                Collection<StatusEffectInstance> effects = attacker.getStatusEffects();
                if (effects != null) {
                    for (StatusEffectInstance effect : effects) {
                        if (effect.getEffectType() == StatusEffects.STRENGTH) {
                            effect_attack_damage += 3 * (effect.getAmplifier() + 1);
                        } else if (effect.getEffectType() == StatusEffects.WEAKNESS) {
                            effect_attack_damage -= 4 * (effect.getAmplifier() + 1);
                        }
                    }
                }
                LOGGER.info("Effects Attack Damage: " + effect_attack_damage);
                float attack_damage = base_item_attack_damage + enchantment_attack_damage + effect_attack_damage;
                LOGGER.info("attack_damage: " + attack_damage);
                return attack_damage;
            }
        }
        LOGGER.info("Attack: " + 0);
        return 0;
    }

    private static float modifyAppliedDamage(LivingEntity me, DamageSource source, float amount) {
        if (source.isUnblockable()) {
            return amount;
        } else {
            int i;
            if (me.hasStatusEffect(StatusEffects.RESISTANCE) && source != DamageSource.OUT_OF_WORLD) {
                i = (me.getStatusEffect(StatusEffects.RESISTANCE).getAmplifier() + 1) * 5;
                int j = 25 - i;
                float f = amount * (float)j;
                amount = Math.max(f / 25.0F, 0.0F);
            }

            if (amount <= 0.0F) {
                return 0.0F;
            } else if (source.bypassesProtection()) {
                return amount;
            } else {
                amount = DamageUtil.getDamageLeft(amount, (float)me.getArmor(), (float)me.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS));
                i = EnchantmentHelper.getProtectionAmount(me.getArmorItems(), source);
                if (i > 0) {
                    amount = DamageUtil.getInflictedDamage(amount, (float)i);
                }

                return amount;
            }
        }
    }
}
