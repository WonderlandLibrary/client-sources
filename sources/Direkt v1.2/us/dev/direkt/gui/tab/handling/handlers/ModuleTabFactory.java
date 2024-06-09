package us.dev.direkt.gui.tab.handling.handlers;

import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.handling.TabFactory;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.gui.tab.tab.tabs.ModuleTab;
import us.dev.direkt.module.ToggleableModule;

/**
 * Created by Foundry on 12/29/2015.
 */
public class ModuleTabFactory implements TabFactory<ToggleableModule> {

    @Override
    public Tab<ToggleableModule> parse(TabHandler handler, ToggleableModule stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        return new ModuleTab(handler, stateObject, parent, children, container);
    }

    @Override
    public Class<ToggleableModule> getHandledType() {
        return ToggleableModule.class;
    }
}
