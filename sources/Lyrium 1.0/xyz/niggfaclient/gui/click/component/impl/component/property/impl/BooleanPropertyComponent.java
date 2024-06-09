// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.click.component.impl.component.property.impl;

import org.lwjgl.opengl.GL11;
import xyz.niggfaclient.module.impl.render.HUD;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.awt.Color;
import xyz.niggfaclient.font.Fonts;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.gui.click.component.impl.component.property.PropertyComponent;
import xyz.niggfaclient.gui.click.component.Component;

public final class BooleanPropertyComponent extends Component implements PropertyComponent
{
    private final Property<Boolean> booleanProperty;
    private int buttonLeft;
    private int buttonTop;
    private int buttonRight;
    private int buttonBottom;
    
    public BooleanPropertyComponent(final Component parent, final Property<Boolean> booleanProperty, final int x, final int y, final int width, final int height) {
        super(parent, booleanProperty.getName(), x, y, width, height);
        this.booleanProperty = booleanProperty;
    }
    
    @Override
    public void drawComponent(final ScaledResolution scaledResolution, final int mouseX, final int mouseY) {
        final int x = (int)(this.getX() - 0.7);
        final int y = this.getY();
        final int width = this.getWidth() + 2;
        final int height = this.getHeight() + 1;
        final int middleHeight = this.getHeight() / 2;
        final int btnRight = x + 3 + middleHeight;
        final float maxWidth = (float)(Fonts.sf19.getStringWidth(this.getName()) + middleHeight + 6);
        final boolean hovered = this.isHovered(mouseX, mouseY);
        final boolean tooWide = maxWidth > width;
        final boolean needScissorBox = tooWide && !hovered;
        RenderUtils.drawCustomRounded((float)x, (float)y, x + ((tooWide && hovered) ? maxWidth : ((float)width)), (float)(y + height + 2), 0.0f, 0.0f, 4.0f, 4.0f, this.isHovered(mouseX, mouseY) ? new Color(85, 85, 85).getRGB() : new Color(45, 45, 45).getRGB());
        if (needScissorBox) {
            RenderUtils.startScissorBox(x, y, width, height);
        }
        Fonts.sf19.drawStringWithShadow(this.getName(), (float)(btnRight + 3), (float)(y + middleHeight - 3), 16777215);
        if (needScissorBox) {
            RenderUtils.endScissorBox();
        }
        final int buttonLeft = x + 2;
        this.buttonLeft = buttonLeft;
        final double x2 = buttonLeft;
        final int buttonTop = y + middleHeight - middleHeight / 2;
        this.buttonTop = buttonTop;
        final double y2 = buttonTop;
        final int buttonRight = btnRight;
        this.buttonRight = buttonRight;
        final double width2 = buttonRight;
        final int buttonBottom = y + middleHeight + middleHeight / 2 + 2;
        this.buttonBottom = buttonBottom;
        RenderUtils.drawOutlinedRoundedRect2(x2, y2, width2, buttonBottom, 4.0, 2.0f, HUD.hudColor.getValue());
        if (this.booleanProperty.getValue()) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.buttonLeft + 1), (float)(this.buttonTop + 4), 1.0f);
            RenderUtils.startBlending();
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3553);
            GL11.glBegin(3);
            GL11.glVertex2d(0.0, 0.0);
            GL11.glVertex2d(2.0, 2.0);
            GL11.glVertex2d(6.0, -2.0);
            GL11.glEnd();
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            RenderUtils.endBlending();
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public void onMouseClick(final int mouseX, final int mouseY, final int button) {
        if (button == 0 && RenderUtils.isHovering((float)this.getX(), (float)this.getY(), (float)this.getWidth(), (float)this.getHeight(), mouseX, mouseY) && this.booleanProperty.isAvailable()) {
            this.booleanProperty.setValue(!this.booleanProperty.getValue());
        }
    }
    
    @Override
    public Property<?> getProperty() {
        return this.booleanProperty;
    }
}
