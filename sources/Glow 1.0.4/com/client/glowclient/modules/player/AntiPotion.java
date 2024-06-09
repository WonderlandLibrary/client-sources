package com.client.glowclient.modules.player;

import net.minecraftforge.event.entity.living.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class AntiPotion extends ModuleContainer
{
    public static final BooleanValue slowness;
    public static final BooleanValue fatigue;
    public static final BooleanValue weakness;
    public static final BooleanValue blindness;
    public static final BooleanValue levitation;
    public static final BooleanValue invisibility;
    public static final BooleanValue nausea;
    
    @SubscribeEvent
    public void M(final LivingEvent$LivingUpdateEvent livingEvent$LivingUpdateEvent) {
        try {
            final EntityLivingBase entityLiving;
            if ((entityLiving = livingEvent$LivingUpdateEvent.getEntityLiving()).equals((Object)Wrapper.mc.player)) {
                if (AntiPotion.invisibility.M()) {
                    final boolean invisible = false;
                    final EntityLivingBase entityLivingBase = entityLiving;
                    entityLivingBase.removePotionEffect(MobEffects.INVISIBILITY);
                    entityLivingBase.setInvisible(invisible);
                }
                if (AntiPotion.nausea.M()) {
                    entityLiving.removePotionEffect(MobEffects.NAUSEA);
                }
                if (AntiPotion.blindness.M()) {
                    entityLiving.removePotionEffect(MobEffects.BLINDNESS);
                }
                if (AntiPotion.slowness.M()) {
                    entityLiving.removePotionEffect(MobEffects.SLOWNESS);
                }
                if (AntiPotion.fatigue.M()) {
                    entityLiving.removePotionEffect(MobEffects.MINING_FATIGUE);
                }
                if (AntiPotion.levitation.M()) {
                    entityLiving.removePotionEffect(MobEffects.LEVITATION);
                }
                if (AntiPotion.weakness.M()) {
                    entityLiving.removePotionEffect(MobEffects.WEAKNESS);
                }
                entityLiving.resetPotionEffectMetadata();
                return;
            }
            if (entityLiving != null) {
                if (AntiPotion.invisibility.M()) {
                    entityLiving.setInvisible(false);
                }
                entityLiving.resetPotionEffectMetadata();
            }
        }
        catch (Exception ex) {}
    }
    
    static {
        nausea = ValueFactory.M("AntiPotion", "Nausea", "Removes Nausea", true);
        blindness = ValueFactory.M("AntiPotion", "Blindness", "Removes Blindness", true);
        invisibility = ValueFactory.M("AntiPotion", "Invisibility", "Removes Invisibility", true);
        fatigue = ValueFactory.M("AntiPotion", "Fatigue", "Removes Fatigue", true);
        levitation = ValueFactory.M("AntiPotion", "Levitation", "Removes Levitation", true);
        slowness = ValueFactory.M("AntiPotion", "Slowness", "Removes Slowness", true);
        weakness = ValueFactory.M("AntiPotion", "Weakness", "Removes Weakness", true);
    }
    
    public AntiPotion() {
        super(Category.PLAYER, "AntiPotion", false, -1, "Removes bad effects");
    }
}
