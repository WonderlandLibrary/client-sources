package arsenic.module;

import arsenic.main.Nexus;
import arsenic.utils.interfaces.IContainable;
import arsenic.utils.interfaces.IContainer;

import java.util.ArrayList;
import java.util.Collection;

public enum ModuleCategory implements IContainer<Module>, IContainable {
    Combat,
    Movement,
    Player,
    Other,
    Exploit,
    Visual;

    private final String name;

    private ModuleCategory() {
        name = name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<Module> getContents() {
        return new ArrayList<>(Nexus.getInstance().getModuleManager().getModulesByCategory(this));
    }
}
