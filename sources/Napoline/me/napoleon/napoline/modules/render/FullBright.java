package me.napoleon.napoline.modules.render;

import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
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
