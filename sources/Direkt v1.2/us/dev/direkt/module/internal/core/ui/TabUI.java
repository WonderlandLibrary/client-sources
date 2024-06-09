package us.dev.direkt.module.internal.core.ui;


import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender2D;
import us.dev.direkt.event.internal.events.game.world.EventLoadWorld;
import us.dev.direkt.event.internal.events.system.input.EventKeyInput;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.cursor.cursors.QueuedTabCursor;
import us.dev.direkt.gui.tab.handling.Tabs;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.property.ModProperty;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * Created by Foundry on 12/28/2015.
 */
@ModData(label = "TabUI", category = ModCategory.CORE)
public  class TabUI extends Module {
    private boolean loaded;

    private final TabHandler handler = TabHandler.builder()
            .renderCoordinates(2, 12)
            .tabDimensions(92, 12)
            .tabSpacing(2, 0)
            .tabCursor(new QueuedTabCursor())
            .build();

    @Listener
    protected Link<EventRender2D> onRender2D = new Link<>(event -> {
        if (this.loaded && !Wrapper.getGameSettings().showDebugInfo)
            this.handler.doTabRendering();
    }, Link.VERY_LOW_PRIORITY + 10);

    @Listener
    protected Link<EventLoadWorld> onWorldLoad = new Link<>(event -> {
        if (!this.loaded && !Wrapper.getGameSettings().showDebugInfo) {
            this.setupTabs();
            this.loaded = true;
        }
    });

    @Listener
    protected Link<EventKeyInput> onKeyInput = new Link<>(event -> {
        if (!Wrapper.getGameSettings().showDebugInfo)
            this.handler.doKeyInput(event.getEventKey());
    });

    public TabHandler getHandler() {
        return this.handler;
    }

    private void setupTabs() {
        final Map<ModCategory, Tab<ModCategory>> categoryMappings = new LinkedHashMap<>(ModCategory.values().length);
        final TabBlock workingBlock = Tabs.newTabBlock();

        Arrays.stream(ModCategory.values())
                .map(category -> Tabs.newTab(this.handler, category, Tabs.newTabBlock(), workingBlock))
                .forEach(tab -> categoryMappings.put(tab.getStateObject(), tab));

        Direkt.getInstance().getModuleManager().getModules().stream()
                .filter(ToggleableModule.class::isInstance)
                .filter(module -> !module.getCategory().equals(ModCategory.CORE))
                .map(ToggleableModule.class::cast)
                .sorted((m1, m2) -> m1.getLabel().compareTo(m2.getLabel()))
                .map(module -> Tabs.newTab(this.handler, module, categoryMappings.get(module.getCategory()), Tabs.newTabBlock(), categoryMappings.get(module.getCategory()).findChildren().get()))
                .forEach(tab -> categoryMappings.get(tab.getStateObject().getCategory()).addChild(tab));

        categoryMappings.values().removeIf(category -> category.findChildren().get().sizeOf() == 0);

        categoryMappings.values().stream()
                .map(category -> category.findChildren().get())
                .flatMap(block -> StreamSupport.stream(block.spliterator(), false))
                .map(tab -> (Tab<ToggleableModule>) tab)
                .filter(tab -> !tab.getStateObject().getProperties().isEmpty())
                .forEach(tab -> tab.getStateObject().getProperties().stream()
                        .map(ModProperty::getProperty)
                        .forEach(property -> tab.addChild(Tabs.newTab(this.handler, property, tab, tab.findChildren().get()))));

        this.handler.setCurrentTabs(categoryMappings.values());
    }

}





