package net.shoreline.client.impl.gui.click2.impl.module;

import net.shoreline.client.api.module.Module;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.gui.click2.component.CategoryFrame;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.string.EnumFormatter;

import java.util.List;

/**
 * @author xgraza
 * @since 03/30/24
 */
public final class ModuleCategoryFrame extends CategoryFrame {

    private final ModuleCategory category;

    public ModuleCategoryFrame(final ModuleCategory category) {
        super(EnumFormatter.formatEnum(category), 16.0f);
        this.category = category;

        // Get all modules within this Module category
        final List<Module> modules = Managers.MODULE.getModules()
                .stream()
                .filter((module) -> module.getCategory() == category)
                .toList();
        for (final Module module : modules) {
            getChildren().add(new ModuleComponent(module));
        }
    }


}
