package us.dev.direkt.gui.tab.tab.tabs;

import net.minecraft.client.gui.FontRenderer;
import us.dev.api.property.Property;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.file.internal.files.ModulesFile;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.tab.AbstractTab;
import us.dev.direkt.gui.tab.tab.Tab;

/**
 * @author Foundry
 */
public class BooleanPropertyTab extends AbstractTab<Property<Boolean>> {
    public BooleanPropertyTab(TabHandler handler, Property<Boolean> stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        this.stateObject.setValue(!this.stateObject.getValue());
        Direkt.getInstance().getFileManager().getFile(ModulesFile.class).save();
    }

    @Override
    public void renderTabFront() {
        FontRenderer font = Wrapper.getFontRenderer();

        font.drawStringWithShadow(this.stateObject.getLabel(), this.getPosX() + 3, this.getPosY() + 2,
                0xCCFFFFFF);
        font.drawStringWithShadow(": ", this.getPosX() + font.getStringWidth(this.stateObject.getLabel()) + 4, this.getPosY() + 2,
                -5723992);

        String booleanValue = String.valueOf(this.stateObject.getValue());
        booleanValue = booleanValue.substring(0, 1).toUpperCase() + booleanValue.substring(1).toLowerCase();

        font.drawStringWithShadow(booleanValue, this.getPosX() + this.getWidth() - font.getStringWidth(booleanValue) - 3, this.getPosY() + 2,
                this.stateObject.getValue() ? 0xFFDDDDDD : -5723992);
    }
}
