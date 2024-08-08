// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.potion.Potion;
import java.util.Iterator;
import net.minecraft.client.resources.I18n;
import java.util.ArrayList;
import net.minecraft.potion.PotionEffect;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import me.perry.mcdonalds.features.Feature;

public class PotionManager extends Feature
{
    private final Map<EntityPlayer, PotionList> potions;
    
    public PotionManager() {
        this.potions = new ConcurrentHashMap<EntityPlayer, PotionList>();
    }
    
    public List<PotionEffect> getOwnPotions() {
        return this.getPlayerPotions((EntityPlayer)PotionManager.mc.player);
    }
    
    public List<PotionEffect> getPlayerPotions(final EntityPlayer player) {
        final PotionList list = this.potions.get(player);
        List<PotionEffect> potions = new ArrayList<PotionEffect>();
        if (list != null) {
            potions = list.getEffects();
        }
        return potions;
    }
    
    public PotionEffect[] getImportantPotions(final EntityPlayer player) {
        final PotionEffect[] array = new PotionEffect[3];
        for (final PotionEffect effect : this.getPlayerPotions(player)) {
            final Potion potion = effect.getPotion();
            final String lowerCase = I18n.format(potion.getName(), new Object[0]).toLowerCase();
            switch (lowerCase) {
                case "strength": {
                    array[0] = effect;
                }
                case "weakness": {
                    array[1] = effect;
                }
                case "speed": {
                    array[2] = effect;
                    continue;
                }
            }
        }
        return array;
    }
    
    public String getPotionString(final PotionEffect effect) {
        final Potion potion = effect.getPotion();
        return I18n.format(potion.getName(), new Object[0]) + " " + (effect.getAmplifier() + 1) + " " + ChatFormatting.WHITE + Potion.getPotionDurationString(effect, 1.0f);
    }
    
    public String getColoredPotionString(final PotionEffect effect) {
        return this.getPotionString(effect);
    }
    
    public static class PotionList
    {
        private final List<PotionEffect> effects;
        
        public PotionList() {
            this.effects = new ArrayList<PotionEffect>();
        }
        
        public void addEffect(final PotionEffect effect) {
            if (effect != null) {
                this.effects.add(effect);
            }
        }
        
        public List<PotionEffect> getEffects() {
            return this.effects;
        }
    }
}
