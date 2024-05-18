package me.Emir.Karaguc.module.render;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;

import java.util.ArrayList;

public class Cape extends Module {
    public Cape() {
        super("Cape", 0, Category.RENDER);
    }

    public void setup() {
        ArrayList<String> Capes = new ArrayList<String>();
        //TODO: Add Cape
        //Capes.add("Mojang 2018");
        Capes.add("KaragucCape");
        Capes.add("DeveloppersCape");
        //Capes.add("MembersCape");

        Karaguc.instance.settingsManager.rSetting(new Setting("Cape Mode", this, "KaragucCape", Capes));

    }
}
