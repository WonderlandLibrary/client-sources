package koks.gui.clickgui.elements;

import koks.Koks;
import koks.modules.Module;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 19:18
 */
public class ElementVisible extends Element {

    public ElementVisible(Module module) {
        super(module);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        getRenderUtils().drawRect(7, getX(), getY() + getHeight() / 2 - 3.5F, getX() + 7, getY() + getHeight() / 2 + 3.5F, new Color(40, 39, 42, 255));
        if (getModule().isVisible())
            getRenderUtils().drawRect(7, getX(), getY() + getHeight() / 2 - 3.5F, getX() + 7, getY() + getHeight() / 2 + 3.5F, Koks.getKoks().client_color);
        getRenderUtils().drawOutlineRect(getX(), getY() + getHeight() / 2 - 3.5F, getX() + 7, getY() + getHeight() / 2 + 3.5F, 1, Color.BLACK);

        GL11.glPushMatrix();
        GL11.glTranslated(getX() + 9, getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 1.5F, 0);
        GL11.glScaled(0.75, 0.75, 0.75);
        getFontRenderer().drawStringWithShadow("Visible", 0, 0, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.getModule().setVisible(!this.getModule().isVisible());
            Koks.getKoks().shutdownClient();
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + 7 && mouseY > getY() + getHeight() / 2 - 3.5F && mouseY < getY() + getHeight() / 2 + 3.5F;
    }

    @Override
    public void mouseReleased() {

    }

    @Override
    public void keyTyped(int keyCode) {

    }
}
