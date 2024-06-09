/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.overlays;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.module.Module;
import winter.utils.Render;
import winter.utils.render.xd.OGLRender;

public class Tab
implements Runnable {
    private static ArrayList<Module.Category> cats;
    private static ArrayList<Module> mods;
    public static boolean transQUICK;
    private static boolean pressed;
    private static boolean open;
    private static int cat;
    private static int mod;
    public static float up;
    public static float down;
    public static int transdelay;

    static {
        transQUICK = false;
        pressed = false;
        open = false;
        cat = 0;
        mod = 0;
        up = 0.0f;
        down = 0.0f;
        transdelay = 10;
    }

    public Tab() {
        cats = new ArrayList();
        Module.Category[] arrcategory = Module.Category.values();
        int n = arrcategory.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Category cat2 = arrcategory[n2];
            cats.add(cat2);
            ++n2;
        }
        mods = new ArrayList();
        new Thread(this).start();
    }

    public void render(Minecraft mc2, float y2, int color) {
        FontRenderer font = mc2.fontRendererObj;
        int mainWidth = 76;
        float multiply = 12.0f;
        int colorTop = -1157574401;
        int colorBottom = -1156876388;
        transdelay = transQUICK ? 1 : 2;
        Render.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        OGLRender.drawBorderedRectReliant(2.0f, y2 - 0.3f, mainWidth, y2 + multiply * (float)Module.Category.values().length + 1.5f - 1.0f, 1.5f, 1711276032, -2013265920);
        if (!open) {
            OGLRender.drawGradientBorderedRectReliant(2.0f, y2 + multiply * (float)cat + up - down - 0.3f, mainWidth, y2 + multiply + multiply * (float)cat + up - down + 0.3f, 1.5f, -2013265920, colorTop, colorBottom);
        } else {
            OGLRender.drawGradientBorderedRectReliant(2.0f, y2 + multiply * (float)cat - 0.3f, mainWidth, y2 + multiply + multiply * (float)cat + 0.3f, 1.5f, -2013265920, colorTop, colorBottom);
        }
        float catY = y2 + 2.0f;
        for (Module.Category cat2 : cats) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            font.drawStringShadowed(cat2.name().replaceAll("Other", "Miscellaneous"), 5.0f, catY, -788529153);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            catY += multiply;
        }
        if (open) {
            float modY = y2 + 2.0f;
            int width = 5;
            int big2 = 0;
            for (Module mod : mods) {
                if (font.getStringWidth(mod.getName()) <= big2) continue;
                big2 = font.getStringWidth(mod.getName()) + 10;
            }
            OGLRender.drawBorderedRectReliant((float)mainWidth + 2.0f, y2 - 0.3f, (float)mainWidth + 1.5f + (float)(width += big2), y2 + multiply * (float)mods.size() + 1.5f - 1.0f, 1.5f, 1711276032, -2013265920);
            OGLRender.drawGradientBorderedRectReliant((float)mainWidth + 2.0f, y2 + multiply * (float)mod + up - down - 0.3f, (float)mainWidth + 1.0f + (float)width + 0.5f, y2 + multiply + multiply * (float)mod + up - down + 0.3f, 1.5f, -2013265920, colorTop, colorBottom);
            for (Module mod : mods) {
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                font.drawStringShadowed(mod.getName(), (float)mainWidth + 5.0f, modY, mod.isEnabled() ? colorTop : -788529153);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                modY += multiply;
            }
        }
        if (!mc2.ingameGUI.getChatGUI().getChatOpen()) {
            Tab.onKey();
        }
    }

    public static void onKey() {
        int mult = 12;
        if (Keyboard.isKeyDown(200) && !pressed) {
            if (open) {
                if (mod - 1 >= 0) {
                    --mod;
                    up = mult;
                } else {
                    mod = mods.size() - 1;
                    down = mult * (mods.size() - 1);
                    transQUICK = true;
                }
            } else if (cat - 1 >= 0) {
                --cat;
                up = mult;
            } else {
                cat = cats.size() - 1;
                down = mult * (cats.size() - 1);
                transQUICK = true;
            }
            pressed = true;
        }
        if (Keyboard.isKeyDown(208) && !pressed) {
            if (open) {
                if (mod + 1 <= mods.size() - 1) {
                    ++mod;
                    down = mult;
                } else {
                    mod = 0;
                    up = mult * (mods.size() - 1);
                    transQUICK = true;
                }
            } else if (cat + 1 <= cats.size() - 1) {
                ++cat;
                down = mult;
            } else {
                cat = 0;
                up = mult * (cats.size() - 1);
                transQUICK = true;
            }
            pressed = true;
        }
        if (Keyboard.isKeyDown(203) && !pressed) {
            up = 0.0f;
            down = 0.0f;
            mod = 0;
            open = false;
            pressed = true;
        }
        if (Keyboard.isKeyDown(205) && !pressed) {
            up = 0.0f;
            down = 0.0f;
            mods.clear();
            open = true;
            for (Module mod : Client.getManager().getModulesInCategory(cats.get(cat))) {
                mods.add(mod);
            }
            pressed = true;
        }
        if (Keyboard.isKeyDown(28) && !pressed) {
            if (open) {
                mods.get(mod).toggle();
            }
            pressed = true;
        }
        if (!(Keyboard.isKeyDown(200) || Keyboard.isKeyDown(208) || Keyboard.isKeyDown(203) || Keyboard.isKeyDown(205) || Keyboard.isKeyDown(28))) {
            pressed = false;
        }
    }

    @Override
    public void run() {
        do {
            if (up > 0.0f) {
                up -= 0.5f;
            }
            if (down > 0.0f) {
                // empty if block
            }
            if ((down -= 0.5f) == 0.0f && up == 0.0f) {
                transQUICK = false;
            }
            try {
                Thread.sleep(transdelay);
            }
            catch (Exception exception) {
            }
        } while (true);
    }
}

