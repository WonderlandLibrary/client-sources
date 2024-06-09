package us.dev.direkt.gui.tab.tab.tabs;

import us.dev.direkt.Wrapper;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.tab.AbstractTab;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.module.ModCategory;

/**
 * @author Foundry
 */
public class ModCategoryTab extends AbstractTab<ModCategory> {

    private float hoverPosition;

    public ModCategoryTab(TabHandler handler, ModCategory stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        if (handler.canDescendTree())
            handler.descendTabTree();
    }

    @Override
    public void renderTabFront() {
        if (this.handler.isInDirectTree(this)) {
            if (hoverPosition < 7) {
                hoverPosition = Math.min(7, hoverPosition + 0.5f);
            }
        } else {
            if (this.handler.getCurrentTab() != this && hoverPosition != 0) {
                hoverPosition = Math.max(0, hoverPosition - 0.5f);
            }
        }

        Wrapper.getFontRenderer().drawStringWithShadow(this.stateObject.toString(), this.getPosX() + 3 + hoverPosition, this.getPosY() + 2, 0xFFFFFF);
    }
}
