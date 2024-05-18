package us.dev.direkt.gui.tab.handling;

import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.tab.Tab;

/**
 * Created by Foundry on 12/29/2015.
 */
public interface TabFactory<T> {
    Tab<T> parse(TabHandler handler, T stateObject, Tab<?> parent, TabBlock children, TabBlock container);

    Class<? extends T> getHandledType();
}
