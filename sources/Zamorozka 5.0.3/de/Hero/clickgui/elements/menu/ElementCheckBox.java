package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import zamorozka.ui.RenderingUtils;

import java.awt.*;

/**
 * Made by HeroCode
 * it's free to use
 * but you have to credit me
 *
 * @author HeroCode
 */
public class ElementCheckBox extends Element {
    /*
     * Konstrukor
     */
    public ElementCheckBox(ModuleButton iparent, Setting iset) {
        parent = iparent;
        set = iset;
        super.setup();


    }

    public static Color setRainbow(double d, float fade) {
        float hue = (float) (System.nanoTime() * -5L + d) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade);
    }

    /*
     * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
     * sollen alle anderen Versuche der Interaktion abgebrochen werden?
     */

    /*
     * Rendern des Elements
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Color temp = ColorUtil.getClickGUIColor();
        int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200).getRGB();

        /*
         * Die Box und Umrandung rendern
         */
        RenderingUtils.drawRect(x, y, x + width, y + height, 0x77000000);

        /*
         * Titel und Checkbox rendern.
         */


        FontUtil.drawString(setstrg, x + width - FontUtil.getStringWidth(setstrg), y + FontUtil.getFontHeight() / 2 + 2, 0xffffffff);
        RenderingUtils.drawRoundedRect(x + 1, y + 5, x + 15, y + 13, set.getValBoolean() ? color : 0xff000000, set.getValBoolean() ? color : 0xff000000);
        if (set.getValBoolean()) {
        	Gui.drawRect(x + 15, y + 3, x + 12, y + 15, set.getValBoolean() ? 0xFFFFFFFF : 0xFFFFFFFF);
            //Gui.drawRect(x + 14, y + 6, x + 13, y + 12, set.getValBoolean() ? 0xFF00F1FF : 0xFFFFFFFF);
        } else {
            Gui.drawRect(x + 1, y + 3, x + 4, y + 15, set.getValBoolean() ? 0xFFFFFFFF : 0xFFFFFFFF);
        }

    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isCheckHovered(mouseX, mouseY)) {
            set.setValBoolean(!set.getValBoolean());
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /*
     * Einfacher HoverCheck, bentigt damit die Value gendert werden kann
     */
    public boolean isCheckHovered(int mouseX, int mouseY) {
        return mouseX >= x + 1 && mouseX <= x + 12 && mouseY >= y + 2 && mouseY <= y + 13;
    }
}
