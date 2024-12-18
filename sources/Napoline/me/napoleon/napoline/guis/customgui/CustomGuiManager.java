package me.napoleon.napoline.guis.customgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.manager.EventManager;
import me.napoleon.napoline.utils.render.RenderUtil;

public class CustomGuiManager extends Gui {

    public static ArrayList<GuiObject> objects = new ArrayList<>();

    public static ArrayList<GuiObject> getObjects() {
        return objects;
    }

    public static void setObjects(ArrayList<GuiObject> objects) {
        CustomGuiManager.objects = objects;
    }

    public static void addObject(GuiObject o) {
        objects.add(o);
    }

    public static void removeObject(GuiObject o) {
        objects.remove(o);
    }


    public static void drawGuiPre() {
        for (GuiObject guiObject : objects) {
            if (guiObject.pre) {
                guiObject.drawObject();
                if (Napoline.mc.currentScreen != null) {
                    guiObject.handleMouse();
                    RenderUtil.drawBordered(guiObject.x - 1, guiObject.y - 1, guiObject.width + 2, guiObject.height + 2, 1, new Color(0, 0, 0, 10).getRGB(), new Color(0, 161, 255, 100).getRGB());

                    RenderUtil.drawBordered(guiObject.x - 1, guiObject.y - guiObject.height - 3, FontLoaders.F14.getStringWidth("x:" + guiObject.x + " y:" + guiObject.y), guiObject.height, 1, new Color(0, 0, 0, 200).getRGB(), new Color(111, 111, 111, 100).getRGB());
                    FontLoaders.F14.drawStringWithShadow("x:" + guiObject.x + " y:" + guiObject.y, guiObject.x - 1, guiObject.y - guiObject.height - 2, -1);

                }
            }
        }
    }


    public static void drawGuiPost() {
        for (GuiObject guiObject : objects) {
            if (!guiObject.pre) {
                guiObject.drawObject();
                if (Napoline.mc.currentScreen != null) {
                    guiObject.handleMouse();
                    RenderUtil.drawBordered(guiObject.x - 1, guiObject.y - 1, guiObject.width + 2, guiObject.height + 2, 1, new Color(0, 0, 0, 10).getRGB(), new Color(0, 161, 255, 100).getRGB());

                }
            }
        }
    }
}
