package us.dev.direkt.gui.tab.cursor;


import us.dev.direkt.gui.tab.tab.Tab;

/**
 * Created by Foundry on 1/1/2016.
 */
public interface TabCursor {
    void renderOn(Tab<?> tab);
    void renderStaticOn(Tab<?> tab);
}
