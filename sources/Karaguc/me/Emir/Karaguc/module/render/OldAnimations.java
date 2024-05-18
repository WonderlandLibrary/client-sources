package me.Emir.Karaguc.module.render;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;

import java.util.ArrayList;

public class OldAnimations extends Module {
    public OldAnimations() {
        super("HitAnimations", 0, Category.RENDER);
    }

    public void setup() {
        ArrayList<String> Hits = new ArrayList<String>();
        //options.add("skidma");
        Hits.add("1.7");
        Hits.add("1.8 Noswing");
        //options.add("Tempest");
        //options.add("1.8 Swing");
        Hits.add("German");
        //options.add("XIV");
        //options.add("Retard");
        Hits.add("OldAnimations");
        Hits.add("Reverse");
        Hits.add("Normal");
        //options.add("ShaderHit");

        Karaguc.instance.settingsManager.rSetting(new Setting("HitAnimations Mode", this, "Normal", Hits));

    }
   }