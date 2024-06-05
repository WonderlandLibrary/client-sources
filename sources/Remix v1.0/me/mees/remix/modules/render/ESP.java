package me.mees.remix.modules.render;

import java.util.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.render.esp.*;

public class ESP extends Module
{
    public ESP() {
        super("ESP", 0, Category.RENDER);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Outline");
        options.add("Lines");
        this.addSetting(new Setting("ESPMode", this, "Outline", options));
        this.addMode(new Outline(this));
        this.addMode(new Lines(this));
    }
}
