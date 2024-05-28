package arsenic.gui.click;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import arsenic.module.ModuleCategory;
import arsenic.utils.interfaces.IContainer;

public enum UICategory implements IContainer<ModuleCategory> {

    MODULES("Modules", ModuleCategory.Movement, ModuleCategory.Combat, ModuleCategory.Exploit,ModuleCategory.Visual,ModuleCategory.Player, ModuleCategory.Other);

    private final String name;
    private final List<ModuleCategory> childCategories = new ArrayList<>();

    UICategory(String name, ModuleCategory... childCategories) {
        this.name = name;
        this.childCategories.addAll(Arrays.asList(childCategories));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<ModuleCategory> getContents() {
        return childCategories;
    }
}
