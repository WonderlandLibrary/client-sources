package me.superskidder.lune.modules.render;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * @description: 夜视
 * @author: QianXia
 * @create: 2020/10/3 20:25
 **/
public class FullBright extends Mod {
    public FullBright() {
        super("FullBright", ModCategory.Render, "Who opens lights");
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 233, 1));
    }

    @Override
    public void onDisable() {
        mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
    }
}
