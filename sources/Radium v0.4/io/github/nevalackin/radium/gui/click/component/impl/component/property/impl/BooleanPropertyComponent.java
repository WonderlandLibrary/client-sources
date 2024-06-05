package io.github.nevalackin.radium.gui.click.component.impl.component.property.impl;

import io.github.nevalackin.radium.gui.click.component.Component;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.PropertyComponent;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.OGLUtils;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import static org.lwjgl.opengl.GL11.*;

public final class BooleanPropertyComponent extends Component implements PropertyComponent {

    private final Property<Boolean> booleanProperty;
    private int buttonLeft;
    private int buttonTop;
    private int buttonRight;
    private int buttonBottom;

    public BooleanPropertyComponent(Component parent,
                                    Property<Boolean> booleanProperty,
                                    int x,
                                    int y,
                                    int width,
                                    int height) {
        super(parent, booleanProperty.getLabel(), x, y, width, height);

        this.booleanProperty = booleanProperty;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution,
                              int mouseX,
                              int mouseY) {
        int x = getX();
        int y = getY();

        int middleHeight = getHeight() / 2;
        int btnRight = x + 3 + middleHeight;
        float maxWidth = Wrapper.getFontRenderer().getWidth(getName()) + middleHeight + 6;

        boolean hovered = isHovered(mouseX, mouseY);
        boolean tooWide = maxWidth > getWidth();
        boolean needScissorBox = tooWide && !hovered;

        Gui.drawRect(x, y,
                x + (tooWide && hovered ? maxWidth : getWidth()),
                y + getHeight(),
                getSecondaryBackgroundColor(hovered));

        if (needScissorBox)
            OGLUtils.startScissorBox(RenderingUtils.getScaledResolution(), x, y, getWidth(), getHeight());
        Wrapper.getFontRenderer().drawStringWithShadow(getName(),
                btnRight + 3,
                y + middleHeight - 3,
                0xFFFFFF);
        if (needScissorBox)
            OGLUtils.endScissorBox();
        Gui.drawRect(buttonLeft = (x + 2),
                buttonTop = (y + middleHeight - (middleHeight / 2)),
                buttonRight = btnRight,
                buttonBottom = (y + middleHeight + (middleHeight / 2) + 2),
                0xFF6B6B6B);
        Gui.drawRect(buttonLeft + 0.5,
                buttonTop + 0.5,
                buttonRight - 0.5,
                buttonBottom - 0.5,
                0xFF3C3F41);
        if (booleanProperty.getValue()) {
            glPushMatrix();
            glTranslatef(buttonLeft + 1, buttonTop + 4, 1.0F);
            OGLUtils.startBlending();
            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
            glLineWidth(1.0F);
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            glDisable(GL_TEXTURE_2D);
            glBegin(GL_LINE_STRIP);
            glVertex2d(0, 0);
            glVertex2d(2, 2);
            glVertex2d(6, -2);
            glEnd();
            glDisable(GL_LINE_SMOOTH);
            glEnable(GL_TEXTURE_2D);
            OGLUtils.endBlending();
            glPopMatrix();
        }
    }

    @Override
    public void onMouseClick(int mouseX,
                             int mouseY,
                             int button) {
        if (button == 0 && mouseX > buttonLeft && mouseY > buttonTop && mouseX < buttonRight && mouseY < buttonBottom)
            booleanProperty.setValue(!booleanProperty.getValue());
    }

    @Override
    public Property<?> getProperty() {
        return booleanProperty;
    }
}
