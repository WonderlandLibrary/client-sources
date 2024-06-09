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
public class DecimalPropertyTab extends AbstractTab<Property<Double>> {
    private boolean selected;

    public DecimalPropertyTab(TabHandler handler, Property<Double> stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        super(handler, stateObject, parent, children, container);
    }

    @Override
    public void doInvocation() {
        selected = true;
        handler.disableDefaultKeyListening();

        handler.subscribeActionToKey(this, Keyboard.KEY_PRIOR, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() + 1D);
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_NEXT, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() - 1D);
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_LEFT, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() - 0.1D);
        });

        handler.subscribeActionToKey(this, Keyboard.KEY_RIGHT, (key, tab) -> {
            this.stateObject.setValue(this.stateObject.getValue() + 0.1D);
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
            float renderIncrement = (float) (this.getWidth() - 3) / (((Bounded<Double>) this.stateObject).upperBound().floatValue() - ((Bounded<Double>) this.stateObject).lowerBound().floatValue());

            OGLRender.drawRect(this.getPosX() + 3, this.getPosY() + 9, this.getPosX() + this.getWidth() - 3, this.getPosY() + 10, 0xAA606060);

            OGLRender.drawRect(this.getPosX() + 3, this.getPosY() + 9, this.getPosX() + (renderIncrement * (float) this.stateObject.getValue().doubleValue()) - (renderIncrement * ((Bounded<Double>) this.stateObject).lowerBound().floatValue()), this.getPosY() + 10, lineRenderColor);

        }
        GL11.glPushMatrix();
        GL11.glScaled(0.675, 0.675, 1.0);
        Wrapper.getFontRenderer().drawStringWithShadow(this.stateObject.getLabel(), this.getPosX() * 1.485f + 3, this.getPosY() * 1.487f + 2f,
                selected ? 0xFFFFFFFF : 0xFFDDDDDD);
        Wrapper.getFontRenderer().drawStringWithShadow(String.format("%.1f", this.stateObject.getValue()), (this.getPosX() + this.getWidth()) * 1.48f - Wrapper.getFontRenderer().getStringWidth(String.format("%.1f", this.stateObject.getValue())) - 4, this.getPosY() * 1.487f + 2f,
                selected ? 0xFFFFFFFF : 0xFFDDDDDD);
        GL11.glPopMatrix();
    }
}
