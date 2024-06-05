/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.handling.handlers;

import digital.rbq.module.ModuleCategory;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.handling.TabFactory;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.tabs.ModCategoryTab;

public class ModCategoryTabFactory
implements TabFactory<ModuleCategory> {
    @Override
    public Tab<ModuleCategory> parse(TabHandler handler, ModuleCategory stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        return new ModCategoryTab(handler, stateObject, parent, children, container);
    }

    @Override
    public Class<ModuleCategory> getHandledType() {
        return ModuleCategory.class;
    }
}

