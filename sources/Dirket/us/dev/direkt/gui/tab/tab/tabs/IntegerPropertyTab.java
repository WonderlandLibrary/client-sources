package us.dev.direkt.gui.tab.tab.tabs;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import us.dev.api.interfaces.Bounded;
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
public class IntegerPropertyTab extends AbstractTab<Property<Integer>> {
    private boolean selected;

    public IntegerPropertyTab(TabHandler handler, Property<Integer> stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        selected = true;
        handler.disableDefaultKeyListening();

        handler.subscribeActionToKey(this, Keyboard.KEY_PRIOR, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() + 10);
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_NEXT, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() - 10);
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_LEFT, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() - 1);
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_RIGHT, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() + 1);
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
        int lineRenderColor;

        if (selected) {
            OGLRender.drawRect(this.getPosX() + 1f, this.getPosY(), this.getPosX() + this.getWidth() - 1f, this.getPosY() + this.getHeight(), 0xFF050505);
            lineRenderColor = ClientColors.FADING_GREEN.getColor();
        } else {
            lineRenderColor = -5723992;
        }

        if (stateObject instanceof Bounded) {
            float renderIncrement = (float) (this.getWidth() - 3) / (((Bounded<Integer>) this.stateObject).upperBound() - ((Bounded<Integer>) this.stateObject).lowerBound().floatValue());

            OGLRender.drawRect(this.getPosX() + 3, this.getPosY() + 9, this.getPosX() + this.getWidth() - 3, this.getPosY() + 10, 0xAA606060);

            OGLRender.drawRect(this.getPosX() + 3, this.getPosY() + 9, this.getPosX() + (renderIncrement * (float) this.stateObject.getValue().doubleValue()) - (renderIncrement * ((Bounded<Integer>) this.stateObject).lowerBound().floatValue()), this.getPosY() + 10, lineRenderColor);
        }
        GL11.glPushMatrix();
        GL11.glScaled(0.7, 0.7, 1.0);
        Wrapper.getFontRenderer().drawStringWithShadow(this.stateObject.getLabel(), this.getPosX() * 1.43f + 3, this.getPosY() * 1.435f + 2f,
                selected ? 0xFFFFFFFF : 0xFFDDDDDD);
        Wrapper.getFontRenderer().drawStringWithShadow(String.valueOf(this.stateObject.getValue()), (this.getPosX() + this.getWidth()) * 1.43f - Wrapper.getFontRenderer().getStringWidth(String.valueOf(this.stateObject.getValue())) - 4, this.getPosY() * 1.435f + 2f,
                selected ? 0xFFFFFFFF : 0xFFDDDDDD);
        GL11.glPopMatrix();
    }
}
