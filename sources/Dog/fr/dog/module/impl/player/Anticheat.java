package fr.dog.module.impl.player;

import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.BooleanProperty;

public class Anticheat extends Module {
    public final BooleanProperty team = BooleanProperty.newInstance("Whitelist Team", true);
    public final BooleanProperty autoblock = BooleanProperty.newInstance("Autoblock Check", true);
    public final BooleanProperty noslow = BooleanProperty.newInstance("Noslow Check", true);
    public final BooleanProperty scaffold = BooleanProperty.newInstance("Scaffold", true);

    public Anticheat() {
        super("Anticheat", ModuleCategory.PLAYER);
       this.registerProperties(team, noslow,autoblock,scaffold);
    }
}
