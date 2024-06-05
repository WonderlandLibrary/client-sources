/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.render;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright
extends Module {
    public float prevGamma;

    public FullBright() {
        super("FullBright", "FullBright", 0, Category.Render);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        this.setDisplayName("Full Bright");
        this.prevGamma = FullBright.mc.gameSettings.gammaSetting;
        if (!FullBright.mc.thePlayer.isPotionActive(Potion.nightVision.getId())) {
            FullBright.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Integer.MAX_VALUE));
        }
        FullBright.mc.gameSettings.gammaSetting = 10000.0f;
    }

    @Override
    public void onDisable() {
        if (FullBright.mc.thePlayer.isPotionActive(Potion.nightVision.getId())) {
            FullBright.mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
        }
        FullBright.mc.gameSettings.gammaSetting = this.prevGamma;
        super.onDisable();
    }
}

