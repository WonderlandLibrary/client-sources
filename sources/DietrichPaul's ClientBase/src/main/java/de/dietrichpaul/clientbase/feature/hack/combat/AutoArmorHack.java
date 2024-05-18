package de.dietrichpaul.clientbase.feature.hack.combat;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.engine.inventory.impl.AutoArmorHandler;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;

public class AutoArmorHack extends Hack {

    public AutoArmorHack() {
        super("AutoArmor", HackCategory.COMBAT);
        ClientBase.INSTANCE.getInventoryEngine().add(new AutoArmorHandler(this));
    }

}
