package club.bluezenith.ui.button;

import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.function.Consumer;

import static club.bluezenith.util.font.FontUtil.rubikR30;
import static club.bluezenith.util.render.RenderUtil.*;
import static net.minecraft.client.renderer.GlStateManager.*;
import static org.lwjgl.opengl.GL11.*;

public class BorderlessCheckbox extends GuiButton {
    private boolean toggled;
    private float alpha;
    FontRenderer fontRenderer = rubikR30;
    private Consumer<Boolean> stateChangeListener;

    public BorderlessCheckbox(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public BorderlessCheckbox setFont(FontRenderer fontRenderer) {
        this.fontRenderer = fontRenderer;
        return this;
    }

    public BorderlessCheckbox onClick(Consumer<Boolean> stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
        return this;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.drawModifiedButton(mc, mouseX, mouseY);
    }

    @Override
    protected void drawModifiedButton(Minecraft mc, int mouseX, int mouseY) {
        final float textWidth = fontRenderer.getStringWidthF(this.displayString);

        this.hovered = mouseX >= this.xPosition - 1 && mouseY >= this.yPosition && mouseX < this.xPosition + textWidth + 10 && mouseY < this.yPosition + 9;

        RenderUtil.hollowRect(this.xPosition, this.yPosition, this.xPosition + 8, this.yPosition + 8, 1, -1);

        animateAlpha();

        rect(this.xPosition, this.yPosition, this.xPosition + 8, this.yPosition + 8, new Color(0, 0, 0, 150));
        fontRenderer.drawString(this.displayString, this.xPosition + 10, this.yPosition + 1, -1);
        drawCheckmark();
        this.mouseDragged(mc, mouseX, mouseY);
    }

    private void animateAlpha() {
        if (this.toggled) {
            alpha = animate(1, alpha, 0.1F);
            if(1 - alpha < 0.03) alpha = 1; //normalizing values a bit (when the anim is close to target it'll get slower which looks bad)
        } else {
            if(alpha < 0.03) alpha = 0;
            alpha = animate(0, alpha, 0.12F);
        }
    }

    private void drawCheckmark() {
        //drawing the checkmark
        pushMatrix();
        translate(xPosition, yPosition, 0); // translate to the checkbox
        translate(8/2D, 8/2D, 0); //translate to middle
        scale(alpha, alpha, 1); //scale anim
        translate(-8/2D, -8/2D, 0); //translate to middle again (so that it's scaled from the center)
        glLineWidth(2);
        glColor4d(1, 1, 1, alpha);
        start2D(GL11.GL_LINE_STRIP);
        glVertex2d(1.5, 4); //draw le checkmark
        glVertex2d(3.5, 6);
        glVertex2d(6.5, 2);
        end2D();
        popMatrix();
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        final boolean wasPressedOn = this.visible && this.enabled && this.hovered;
        if(wasPressedOn) {
            this.toggled = !this.toggled;
            if(this.stateChangeListener != null) this.stateChangeListener.accept(this.toggled);
        }
        return wasPressedOn;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public BorderlessCheckbox setToggled(boolean toggled) {
        this.toggled = toggled;
        if(toggled) alpha = 1; else alpha = 0;
        return this;
    }
}
