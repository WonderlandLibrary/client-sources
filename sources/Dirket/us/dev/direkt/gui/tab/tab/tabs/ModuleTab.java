package us.dev.direkt.gui.tab.tab.tabs;

import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.file.internal.files.ModulesFile;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.tab.AbstractTab;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.util.render.OGLRender;

/**
 * @author Foundry
 */
public class ModuleTab extends AbstractTab<ToggleableModule> {

    private float hoverPosition;

    public ModuleTab(TabHandler handler, ToggleableModule stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        this.stateObject.setRunning(!this.stateObject.isRunning());
        Direkt.getInstance().getFileManager().getFile(ModulesFile.class).save();
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
        if (this.stateObject.isRunning()) {
            OGLRender.drawRect(this.getPosX() - 0.5f, this.getPosY(), this.getPosX() + 1.5f, this.getPosY() + this.getHeight(),
                    ClientColors.FADING_GREEN.getColor());
            OGLRender.drawRect(this.getPosX() + 1.5f, this.getPosY(), this.getPosX() + this.getWidth() - 1.5f, this.getPosY() + this.getHeight(),
                    0x1A050505);
        }

        Wrapper.getFontRenderer().drawStringWithShadow(this.stateObject.getLabel(), this.getPosX() + 3 + hoverPosition, this.getPosY() + 2,
                this.stateObject.isRunning() ? (this.stateObject.isLocked() ? 0x2F4F4F : 0xFFFFFFFF) : (this.stateObject.isLocked() ? 0x2F4F4F : -5723992));
        if ((this.findChildren().get()).sizeOf() > 0)
            Wrapper.getFontRenderer().drawStringWithShadow(">", this.getPosX() + this.getWidth() - Wrapper.getFontRenderer().getStringWidth(">") - 3, this.getPosY() + 2.25f,
                    this.stateObject.isRunning() ? 0xFFCCCCCC : -5723992);
    }
}
