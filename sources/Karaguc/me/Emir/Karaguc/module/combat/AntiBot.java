package me.Emir.Karaguc.module.combat;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.EventUpdate;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class AntiBot extends Module {
	
    public AntiBot() {
        super("AntiBot", Keyboard.KEY_NONE, Category.COMBAT);
    }


    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        //TODO: Add more AntiBot modes
        //options.add("Vanilla");
        options.add("Hypixel");
        //options.add("Test");
        //options.add("Rewi/Rewinside");
        //options.add("AACP");
        //options.add("GommeHD");
        //options.add("TeamKyudoo");
        options.add("HiveMC");
        options.add("Mineplex/Gwen");
        //options.add("Best");
        options.add("Guardian/VeltPvP");
        //options.add("DAC");
        //options.add("Storm")
        //options.add("Reflex");
        //options.add("HAC");
        options.add("Gcheat/BAC/Badlion");
        //options.add("NCP");
        //options.add("Minesucht");
        //options.add("Fiona");
        //options.add("Advanced");
        //options.add("Normal");
        //options.add("Auto");
        Karaguc.instance.settingsManager.rSetting(new Setting("AntiBot Mode", this, "Hypixel", options));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        for(Object entity : mc.theWorld.loadedEntityList)
            if(((Entity)entity).isInvisible() && entity != mc.thePlayer)
                mc.theWorld.removeEntity((Entity)entity);
    }
}
