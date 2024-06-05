package de.dietrichpaul.clientbase.feature.hack.world;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.engine.inventory.impl.ChestStealerHandler;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;

public class ChestStealerHack extends Hack {

    public ChestStealerHack() {
        super("ChestStealer", HackCategory.WORLD);
        ClientBase.INSTANCE.getInventoryEngine().add(new ChestStealerHandler(this));
    }



}
