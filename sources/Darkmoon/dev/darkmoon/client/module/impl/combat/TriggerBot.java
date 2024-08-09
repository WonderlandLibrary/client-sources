package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumHand;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@ModuleAnnotation(name = "TriggerBot", category = Category.COMBAT)
public class TriggerBot extends Module {
    private final MultiBooleanSetting targets = new MultiBooleanSetting("Targets", Arrays.asList("Players", "Mobs", "Animals"));
    public BooleanSetting onlyCritical = new BooleanSetting("Only Critical", false);
    public BooleanSetting randomHits = new BooleanSetting("Random Hits", false);
    float minCPS = 0;

    private boolean canAttack() {
        boolean reason = !onlyCritical.get() || mc.player.capabilities.isFlying || mc.player.isElytraFlying() || mc.player.isPotionActive(MobEffects.LEVITATION) || mc.player.isOnLadder();
        if (mc.player.getCooledAttackStrength(1.5F) < 0.93D) {
            return false;
        } else if (mc.player.isInsideOfMaterial(Material.WATER)) {
            return true;
        } else if (!reason) {
            if (MovementUtility.isBlockAboveHead() && mc.player.onGround && mc.player.fallDistance > 0) {
                return true;
            }

            return (!mc.player.onGround && mc.player.fallDistance > 0);
        } else {
            return true;
        }
    }

    private boolean isValid(EntityLivingBase target) {
        if (target instanceof EntityPlayer) {
            if (!targets.get(0)) {
                return false;
            }

            if (((EntityPlayer) target).isBot) {
                return false;
            }

            if (DarkMoon.getInstance().getFriendManager().isFriend(target.getName())) {
                return false;
            }
        }

        if (target instanceof EntityMob && !targets.get(1)) {
            return false;
        } else if (target instanceof EntityAnimal && !targets.get(2)) {
            return false;
        } else if (!(target instanceof EntityArmorStand) && !target.getName().equals(mc.player.getName())) {
            return target.isEntityAlive() && mc.player != null && mc.world != null;
        } else {
            return false;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (minCPS > 0) {
            --minCPS;
        }
        Entity entityHit = mc.objectMouseOver.entityHit;
        if (entityHit instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entityHit;
            if (isValid(entityLivingBase) && minCPS == 0 && canAttack()) {
                mc.playerController.attackEntity(mc.player, entityLivingBase);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                if (randomHits.get()) {
                    minCPS = ThreadLocalRandom.current().nextInt(10, 14);
                } else {
                    minCPS = 10;
                }
            }
        }
    }
}
