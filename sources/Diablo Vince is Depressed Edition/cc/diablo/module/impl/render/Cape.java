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
import cc.diablo.setting.impl.ModeSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.util.ResourceLocation;

public class Cape
extends Module {
    public ModeSetting mode = new ModeSetting("Cape", "Diablo", "Diablo", "Diablo2", "Hot");
    public ResourceLocation oldLocation;
    int countCape;

    public Cape() {
        super("Cape", "If you dont know what a cape is your fucking retarded", 0, Category.Render);
        this.addSettings(this.mode);
    }

    @Override
    public void onEnable() {
        this.countCape = 0;
        this.oldLocation = Cape.mc.thePlayer.getLocationCape();
        super.onEnable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        this.setDisplayName("Cape\u00a77 " + this.mode.getMode());
        switch (this.mode.getMode()) {
            case "Diablo": {
                Cape.mc.thePlayer.setLocationOfCape(new ResourceLocation("Client/images/DiabloCapeNew.png"));
                break;
            }
            case "Diablo2": {
                StringBuilder countStr = new StringBuilder();
                Cape.mc.thePlayer.setLocationOfCape(new ResourceLocation("Client/images/raimbow/diablo" + countStr + ".png"));
                break;
            }
            case "Hot": {
                Cape.mc.thePlayer.setLocationOfCape(new ResourceLocation("Client/images/hotcape.png"));
            }
        }
    }

    @Override
    public void onDisable() {
        Cape.mc.thePlayer.setLocationOfCape(this.oldLocation);
        super.onDisable();
    }
}

