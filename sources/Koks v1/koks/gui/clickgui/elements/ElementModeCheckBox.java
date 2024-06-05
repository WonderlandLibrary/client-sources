package koks.gui.clickgui.elements;

import koks.Koks;
import koks.utilities.value.Value;
import koks.utilities.value.values.ModeValue;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 08:03
 */
public class ElementModeCheckBox extends Element {

    public ModeValue modeValue;
    private int rotationAngle = 0;

    public ElementModeCheckBox(ModeValue value) {
        super(value);
        this.modeValue = value;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        float[] yHeight = {0};
        getRenderUtils().drawRect(7, getX(), getY() + 1, getX() + getWidth(), getY() + getHeight() - 1, new Color(40, 39, 42, 255));

        GL11.glPushMatrix();
        GL11.glTranslated(getX() + 9, getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 1F, 0);
        GL11.glScaled(0.8, 0.8, 0.8);
        getFontRenderer().drawStringWithShadow(this.modeValue.getName(), 0, 0, -1);
        GL11.glPopMatrix();

        double xMid2 = (this.getX() + getWidth() - getFontRenderer().getStringWidth(">"));
        double yMid2 = (this.getY() + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 1F);

        GL11.glPushMatrix();
        GL11.glTranslated(xMid2, yMid2, 0);
        GL11.glScaled(0.8, 0.8, 0.8);

        if (isExtended()) {
            if (rotationAngle < 90)
                rotationAngle++;
        } else {
            if (rotationAngle > 0)
                rotationAngle--;
        }
        GL11.glRotatef(rotationAngle, 0, 0, 1);
        getFontRenderer().drawStringWithShadow(">", 0, 0, -1);
        GL11.glPopMatrix();

        if (isExtended()) {
            Arrays.stream(this.modeValue.getObjects()).forEach(modeValue -> {
                if (modeValue.isVisible()) {
                    getRenderUtils().drawRect(7, getX(), this.getY() + this.getHeight() + yHeight[0], getX() + getWidth(), this.getY() + this.getHeight() + yHeight[0] + getHeight(), new Color(40, 39, 42, 255));

                    getRenderUtils().drawRect(7, getX() + 2, getY() + getHeight() + yHeight[0] + getHeight() / 2 - 3.5F, getX() + 9, getY() + getHeight() + yHeight[0] + getHeight() / 2 + 3.5F, modeValue.isToggled() ? Koks.getKoks().client_color : new Color(40, 39, 42, 255));
                    getRenderUtils().drawOutlineRect(getX() + 2, getY() + getHeight() + yHeight[0] + getHeight() / 2 - 3.5F, getX() + 9, getY() + getHeight() + yHeight[0] + getHeight() / 2 + 3.5F, 1, Color.black);

                    GL11.glPushMatrix();
                    GL11.glTranslated(getX() + 4, this.getY() + this.getHeight() + yHeight[0] + getHeight() / 2 - getFontRenderer().FONT_HEIGHT / 2 + 0.75F, 0);
                    GL11.glScaled(0.75, 0.75, 0.75);
                    getFontRenderer().drawStringWithShadow(modeValue.getName(), 9, 1, -1);
                    GL11.glPopMatrix();
                    yHeight[0] += getHeight();
                }
            });
        }
        getRenderUtils().drawOutlineRect(getX(), getY() + 1, getX() + getWidth(), getY() + getHeight() + yHeight[0] - (isExtended() ? 0 : 1), 2, new Color(0, 0, 0, 255));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.setExtended(!this.isExtended());
        }
        if (isExtended()) {
            float[] yHeight = {0};
            Arrays.stream(this.modeValue.getObjects()).forEach(modeValue -> {
                if (modeValue.isVisible()) {
                    if (mouseButton == 0 && mouseX > getX() + 2 && mouseY > getY() + getHeight() + yHeight[0] + getHeight() / 2 - 3.5F && mouseX < getX() + 9 && mouseY < getY() + getHeight() + yHeight[0] + getHeight() / 2 + 3.5F) {
                        modeValue.setToggled(!modeValue.isToggled());
                    }
                    yHeight[0] += getHeight();
                }
            });
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() && mouseY > getY() + 1 && mouseY < getY() + getHeight() - 1;
    }

    @Override
    public void mouseReleased() {

    }

    @Override
    public void keyTyped(int keyCode) {

    }
}
