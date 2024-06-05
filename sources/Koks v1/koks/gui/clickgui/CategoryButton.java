package koks.gui.clickgui;

import koks.Koks;
import koks.modules.Module;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 00:05
 */
public class CategoryButton {

    private final Module.Category category;
    private float x, y, width, height;
    private final RenderUtils renderUtils = new RenderUtils();

    public final List<ModuleButton> MODULE_BUTTONS = new ArrayList<>();

    private int scrolling;

    public CategoryButton(Module.Category category) {
        this.category = category;
        Koks.getKoks().moduleManager.getModules().stream().filter(module -> module.getModuleCategory().equals(category)).forEach(module -> this.MODULE_BUTTONS.add(new ModuleButton(module)));
    }

    public void drawScreen(int mouseX, int mouseY) {

        if (category == Koks.getKoks().clickGUI.category) {

            renderUtils.drawRect(7, x - 3, y, x + width + 3, y + height, new Color(22, 22, 22, 255));
            renderUtils.drawRect(7, x - 3, y, x + width + 3, y + 1, new Color(40, 39, 42, 255));
            renderUtils.drawRect(7, x - 3, y + height - 1, x + width + 3, y + height, new Color(40, 39, 42, 255));

            renderUtils.drawImage(new ResourceLocation("client/icons/" + category.name().toLowerCase() + ".png"), this.x + this.width / 2 - 32 / 2, this.y + height / 2 - 42 / 2, 32, 32, false);

            GL11.glPushMatrix();
            GL11.glTranslatef(this.x + this.width / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(category.name()) / 2 + 2, this.y + height / 2 - 42 / 2 + 34, 0);
            GL11.glScaled(0.9, 0.9, 0.9);
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(category.name(), 0, 0, -1);
            GL11.glPopMatrix();

            float[] x = {this.x + this.width + 7.5F};
            float[] y = {Koks.getKoks().clickGUI.getY() + 5};

            for (int i = 0; i < this.MODULE_BUTTONS.size(); i++) {
                ModuleButton moduleDrawer = this.MODULE_BUTTONS.get(i);
                ModuleButton moduleDrawer2;
                try {
                    moduleDrawer2 = this.MODULE_BUTTONS.get(i - 2);
                    if (moduleDrawer2 != null) {
                        if (moduleDrawer2.extended) {
                            moduleDrawer.setInformation(x[0], moduleDrawer2.getY() + moduleDrawer2.yMaxElements + 20, 142.5F, 15);
                        } else {
                            moduleDrawer.setInformation(x[0], moduleDrawer2.getY() + moduleDrawer2.getHeight() + 5, 142.5F, 15);
                        }
                    }
                } catch (Exception e2) {
                    moduleDrawer.setInformation(x[0], y[0] - scrolling, 142.5F, 15);
                }
                moduleDrawer.drawScreen(mouseX, mouseY);
                x[0] += 147.5F;
                if (x[0] > Koks.getKoks().clickGUI.getX() - 51 + Koks.getKoks().clickGUI.getWidth()) {
                    x[0] = this.x + this.width + 7.5F;
                    y[0] += 25;
                }
            }

            /*
             * Scrolling
             */

            int mouseWheel = Mouse.getDWheel();

            if (mouseWheel > 0) {
                if (scrolling > 0)
                    scrolling -= 10;
            }

            float yScrolling = 0;
            float yScrolling2 = 0;

            for (int i = 0; i < this.MODULE_BUTTONS.size(); i++) {
                ModuleButton moduleDrawer = this.MODULE_BUTTONS.get(i);
                if (i % 2 == 0) {
                    yScrolling2 += moduleDrawer.yMaxElements;
                } else {
                    yScrolling += moduleDrawer.yMaxElements;
                }
            }

            if (mouseWheel < 0) {
                if (scrolling < biggerNumber(yScrolling, yScrolling2) - Koks.getKoks().clickGUI.getHeight() + (MODULE_BUTTONS.size() * 20) / 2 + 20 && biggerNumber(yScrolling, yScrolling2) + (MODULE_BUTTONS.size() * 20) / 2 > Koks.getKoks().clickGUI.getHeight())
                    scrolling += 10;
            }

        } else {
            renderUtils.drawImage(new ResourceLocation("client/icons/" + category.name().toLowerCase() + ".png"), this.x + this.width / 2 - 32 / 2, this.y + height / 2 - 32 / 2, 32, 32, true);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            Koks.getKoks().clickGUI.category = this.category;
        }
        if (Koks.getKoks().clickGUI.category == this.category) {
            this.MODULE_BUTTONS.forEach(moduleButton -> {
                if (moduleButton.getY() + moduleButton.getHeight() + moduleButton.yMaxElements > Koks.getKoks().clickGUI.getY() && moduleButton.getY() < Koks.getKoks().clickGUI.getY() + Koks.getKoks().clickGUI.getHeight()) {
                    moduleButton.mouseClicked(mouseX, mouseY, mouseButton);
                }
            });
        }
    }

    public void mouseReleased() {
        if (Koks.getKoks().clickGUI.category == this.category) {
            this.MODULE_BUTTONS.forEach(moduleButton -> moduleButton.mouseReleased());
        }
    }

    public void keyTyped(int keyButton) {
        if (Koks.getKoks().clickGUI.category == this.category) {
            this.MODULE_BUTTONS.forEach(moduleButton -> moduleButton.keyTyped(keyButton));
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x - 3 && mouseX < x + width + 3 && mouseY > y && mouseY < y + height;
    }

    public Module.Category getCategory() {
        return category;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public float getWidth() {
        return width;
    }


    public float getHeight() {
        return height;
    }

    public float biggerNumber(float number1, float number2) {
        if (number1 > number2)
            return number1;
        else if (number2 > number1)
            return number2;
        if(number1 == number2)
            return number1;
        return 0;
    }

    public void setInformation(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
