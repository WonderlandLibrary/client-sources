package koks.hud.tabgui;

import koks.modules.Module;
import koks.utilities.CustomFont;
import koks.utilities.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 08:35
 */
public class TabGUI {

    private final List<CategoryTab> categoryTabs = new ArrayList<>();
    private final RenderUtils renderUtils = new RenderUtils();
    public static int currentCat = 0;

    public TabGUI() {
        Arrays.stream(Module.Category.values()).forEach(category -> categoryTabs.add(new CategoryTab(category)));
    }

    public void drawScreen(int x, int y, int width, int height, boolean shadow, CustomFont tabGuiLengthFont, boolean clientColor, boolean centeredString) {
        int[] y2 = {0};
        int[] y3 = {0};

        /*
         *  SHADOWS
         */

        if (shadow) {

            this.categoryTabs.forEach(categoryTab -> {
                y3[0] += height;
            });

            GL11.glPushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
            renderUtils.drawImage(new ResourceLocation("client/shadows/top.png"), x, y - 3, width, 3, false);
            renderUtils.drawImage(new ResourceLocation("client/shadows/bottom.png"), x, y + y3[0], width, 6, false);
            renderUtils.drawImage(new ResourceLocation("client/shadows/left.png"), x - 3, y - 1, 3, y3[0] + 1, false);
            renderUtils.drawImage(new ResourceLocation("client/shadows/right.png"), x + width, y - 1, 3, y3[0] + 2, false);
            GL11.glDisable(GL11.GL_BLEND);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GL11.glPopMatrix();
        }
        /*
         * SHADOWS END
         */

        this.categoryTabs.forEach(categoryTab -> {
            categoryTab.setInformation(x, y + y2[0], width, height);
            categoryTab.drawScreen(currentCat, shadow, clientColor, centeredString, tabGuiLengthFont);
            y2[0] += height;
        });
    }

    public void drawScreen(int x, int y, int width, int height, boolean shadow, boolean clientColor, boolean centeredString) {
        int[] y2 = {0};
        int[] y3 = {0};

        /*
         *  SHADOWS
         */

        if (shadow) {

            this.categoryTabs.forEach(categoryTab -> {
                y3[0] += height;
            });

            GL11.glPushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1, 1, 1, 1);
            renderUtils.drawImage(new ResourceLocation("client/shadows/top.png"), x, y - 3, width, 3, false);
            renderUtils.drawImage(new ResourceLocation("client/shadows/bottom.png"), x, y + y3[0], width, 6, false);
            renderUtils.drawImage(new ResourceLocation("client/shadows/left.png"), x - 3, y - 1, 3, y3[0] + 1, false);
            renderUtils.drawImage(new ResourceLocation("client/shadows/right.png"), x + width, y - 1, 3, y3[0] + 2, false);
            GL11.glDisable(GL11.GL_BLEND);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GL11.glPopMatrix();
        }
        /*
         * SHADOWS END
         */

        this.categoryTabs.forEach(categoryTab -> {
            categoryTab.setInformation(x, y + y2[0], width, height);
            categoryTab.drawScreen(currentCat, shadow, clientColor, centeredString);
            y2[0] += height;
        });
    }

    public void keyPress(int key) {
        AtomicBoolean open = new AtomicBoolean(false);
        this.categoryTabs.forEach(categoryTab -> {
            categoryTab.keyPress(key);
            if (categoryTab.isExpanded())
                open.set(true);
        });
        if (open.get())
            return;
        if (key == Keyboard.KEY_DOWN) {
            currentCat++;
            if (currentCat > Module.Category.values().length - 1)
                currentCat = 0;
        }
        if (key == Keyboard.KEY_UP) {
            currentCat--;
            if (currentCat < 0)
                currentCat = Module.Category.values().length - 1;
        }
    }

}
