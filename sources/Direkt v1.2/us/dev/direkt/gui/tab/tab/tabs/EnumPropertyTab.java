package us.dev.direkt.gui.tab.tab.tabs;

import org.lwjgl.input.Keyboard;
import us.dev.api.property.Property;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.file.internal.files.ModulesFile;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.tab.AbstractTab;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.util.render.OGLRender;

/**
 * @author Foundry
 */
public class EnumPropertyTab extends AbstractTab<Property<Enum>> {

    private boolean selected;

    public EnumPropertyTab(TabHandler handler, Property<Enum> stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        final Enum[] values = this.stateObject.getValue().getClass().getEnumConstants();

        selected = true;
        handler.disableDefaultKeyListening();

        handler.subscribeActionToKey(this, Keyboard.KEY_LEFT, (key, tab) -> {
            for (int i = 0; i < values.length; i++) {
                if (values[i] == this.stateObject.getValue()) {
                    this.stateObject.setValue(i == 0 ? values[values.length - 1] : values[i - 1]);
                    break;
                }
            }
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_RIGHT, (key, tab) -> {
            for (int i = 0; i < values.length; i++)
                if (values[i] == this.stateObject.getValue()) {
                    this.stateObject.setValue(i == values.length - 1 ? values[0] : values[i + 1]);
                    break;
                }
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_RETURN, (key, tab) -> {
            selected = false;
            Direkt.getInstance().getFileManager().getFile(ModulesFile.class).save();
            handler.unsubscribeSpecificActions(this);
            handler.enableDefaultKeyListening();
        });
    }

    @Override
    public void renderTabFront() {
        int valueColor;

        if (selected) {
            OGLRender.drawRect(this.getPosX() + 1f, this.getPosY(), this.getPosX() + this.getWidth() - 1f, this.getPosY() + this.getHeight(), 0xFF050505);
            valueColor = ClientColors.FADING_GREEN.getColor();
        } else {
            valueColor = -5723992;
        }

        Wrapper.getFontRenderer().drawStringWithShadow(this.stateObject.getLabel() + ": ", this.getPosX() + 2.5f, this.getPosY() + 2, 0xFFCCCCCC);

        String enumValue = this.stateObject.getValue().toString();

        Wrapper.getFontRenderer().drawStringWithShadow(enumValue, this.getPosX() + this.getWidth() - Wrapper.getFontRenderer().getStringWidth(enumValue) - 3, this.getPosY() + 2, valueColor);
    }
}
