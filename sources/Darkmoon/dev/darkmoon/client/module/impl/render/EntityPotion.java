package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

import static dev.darkmoon.client.module.impl.render.Hud.getDuration;
import static dev.darkmoon.client.module.impl.render.Hud.getPotionPower;

@ModuleAnnotation(name = "EntityPotion", category = Category.UTIL)
public class EntityPotion extends Module {
    public static BooleanSetting allInfo = new BooleanSetting("All-Potion", false);
    public TimerHelper timerHelper = new TimerHelper();
    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        List<String> potionEffects = new ArrayList<>();
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player != mc.player) { // Исключаем текущего игрока
                for (PotionEffect effect : player.getActivePotionEffects()) { // Получаем эффекты текущего игрока
                    Potion potion = effect.getPotion();
                    boolean duration = effect.getDuration() > 0;
                    int amplifier = effect.getAmplifier();
                    if (!allInfo.get()) {
                        if (potion == MobEffects.RESISTANCE && amplifier >= 1 && duration) {
                            String message = "Игрок, " + player.getDisplayName().getFormattedText() + " использует зелье " + I18n.format(effect.getEffectName(), new Object[0]) + " уровня >> " +
                                    getPotionPower(effect) + ChatFormatting.RESET + " будет длиться " + getDuration(effect);
                            potionEffects.add(message);
                        }
                        if ((potion == MobEffects.STRENGTH || potion == MobEffects.SPEED || potion == MobEffects.ABSORPTION) && amplifier >= 2 && duration) {
                            String message = "Игрок, " + player.getDisplayName().getFormattedText() + " использует зелье " + I18n.format(effect.getEffectName(), new Object[0]) + " уровня >> " +
                                    getPotionPower(effect) + ChatFormatting.RESET + " будет длиться " + getDuration(effect);
                            potionEffects.add(message);
                        } else if (allInfo.get()) {
                            if (amplifier >= 2 && duration) {
                                String message = "Игрок, " + player.getDisplayName().getFormattedText() + " использует зелье " + I18n.format(effect.getEffectName(), new Object[0]) + " уровня >> " +
                                        getPotionPower(effect) + ChatFormatting.RESET + " будет длиться " + getDuration(effect);
                                potionEffects.add(message);
                            }
                        }
                    }
                }
            }
        }
        if (!potionEffects.isEmpty() && timerHelper.hasTimeElapsed(6500, true)) {
            for (String message : potionEffects) {
                ChatUtility.addChatMessage(message);
            }
        }
    }
}
