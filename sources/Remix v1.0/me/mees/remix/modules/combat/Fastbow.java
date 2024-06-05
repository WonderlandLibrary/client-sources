package me.mees.remix.modules.combat;

import java.util.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.fastbow.*;

public final class Fastbow extends Module
{
    public Fastbow() {
        super("Fastbow", 0, Category.COMBAT);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Guardian");
        options.add("Fast");
        this.addSetting(new Setting("FastbowMode", this, "Guardian", options));
        this.addMode(new Guardian(this));
        this.addMode(new Fast(this));
    }
}
