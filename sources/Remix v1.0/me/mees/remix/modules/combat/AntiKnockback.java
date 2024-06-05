package me.mees.remix.modules.combat;

import java.util.*;
import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.antiknockback.*;
import me.satisfactory.base.setting.*;

public final class AntiKnockback extends Module
{
    public AntiKnockback() {
        super("AntiKnockback", 0, Category.COMBAT);
        final ArrayList<String> options = new ArrayList<String>();
        options.add("Normal");
        options.add("AAC");
        this.addMode(new Normal(this));
        this.addMode(new AAC(this));
        this.addSetting(new Setting("AntiKnockback Mode", this, "Normal", options));
    }
}
