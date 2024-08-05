package fr.dog.command.impl;

import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.component.impl.ui.CSGOComponent;

public class CSGOCommand extends Command {
    public CSGOCommand() {
        super("csgo", "cs", "nigger", "nigga", "nigahiga", "fullpolarisabler", "ibangedfurrycane","ibangedfurrycaneparents", "ibangedfurrycanemother");
    }

    @Override
    public void execute(String[] args, String message) {
        ((CSGOComponent) Dog.getInstance().getComponentManager().getBy(obj -> obj instanceof CSGOComponent)).enabled = !((CSGOComponent) Dog.getInstance().getComponentManager().getBy(obj -> obj instanceof CSGOComponent)).enabled;
    }
}
