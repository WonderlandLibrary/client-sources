package us.dev.direkt.gui.tab.handling.handlers;


import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.handling.TabFactory;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.gui.tab.tab.tabs.ModCategoryTab;
import us.dev.direkt.module.ModCategory;

/**
 * Created by Foundry on 12/29/2015.
 */
public class ModCategoryTabFactory implements TabFactory<ModCategory> {

    @Override
    public Tab<ModCategory> parse(TabHandler handler, ModCategory stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        return new ModCategoryTab(handler, stateObject, parent, children, container);
    }

    @Override
    public Class<ModCategory> getHandledType() {
        return ModCategory.class;
    }
}
