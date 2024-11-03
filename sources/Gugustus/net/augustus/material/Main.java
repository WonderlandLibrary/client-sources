package net.augustus.material;

import net.augustus.Augustus;
import net.augustus.clickgui.screens.ConfigGui;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.material.Tabs.ModuleTab;
import net.augustus.material.button.CButton;
import net.augustus.material.themes.Dark;
import net.augustus.modules.Module;
import net.augustus.utils.skid.tomorrow.AnimationUtils;
import net.augustus.utils.skid.tomorrow.ColorUtils;
import net.augustus.utils.skid.tomorrow.Rect;
import net.augustus.utils.skid.tomorrow.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends GuiScreen {
    private static UnicodeFontRenderer arial18;
    private static UnicodeFontRenderer arial16;
    static {
        try {
            arial18 = new UnicodeFontRenderer(Font.createFont(0, Main.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            arial16 = new UnicodeFontRenderer(Font.createFont(0, Main.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(16F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float windowX, windowY, windowWidth, windowHeight;

    public CButton Blist;
    public CButton Bconfigs;
    public float mouseX;
    public float mouseY;

    public ArrayList<Category> categories = new ArrayList<>();
    public static float animListX;
    public float listRoll2 = 0;
    public float listRoll = 0;

    public static AnimationUtils listAnim = new AnimationUtils();
    public static AnimationUtils rollAnim = new AnimationUtils();

    public float bg;
    public static AnimationUtils bgAnim = new AnimationUtils();
    public static ArrayList<Tab> tabs = new ArrayList<>();
    public static Tab currentTab;
    public static Color clientColor;
    ScaledResolution sr;
    public static Object currentObj;

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (Tab t : tabs) {
            t.mouseClicked(mouseX, mouseY);
        }
        if (!isHovered(windowX + windowWidth - 5, windowY + windowHeight - 5, windowX + windowWidth + 5, windowY + windowHeight + 5, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            drag2 = false;
            mouseDX2 = 0;
            mouseDY2 = 0;
        }
        Blist.onMouseClicked(mouseX, mouseY);
        Bconfigs.onMouseClicked(mouseX, mouseY);

        if(Bconfigs.realized) {
            mc.displayGuiScreen(new ConfigGui(new Dark()));
            Bconfigs.realized = false;
        }

        float modsY = windowY + 35 + listRoll;

        for (Category mt : categories) {
            if (mt.show) {
                new Rect(windowX, modsY, animListX, 20, new Color(255, 255, 255), () -> {
                    if (Mouse.isButtonDown(0)) {
                        if (!mt.show) {
                            mt.show = true;
                        } else {
                            mt.needRemove = !mt.needRemove;
                        }
                    }
                }).render(mouseX, mouseY);
                modsY += 25;
                for (Module m : Augustus.getInstance().getModuleManager().getModules(mt.moduleType)) {
                    if (modsY > windowY + 30 && modsY < windowY + windowHeight) {
                        if (!mt.needRemove) {
                            new Rect(windowX, modsY, animListX, 15, new Color(255, 255, 255), () -> {
                                if (Mouse.isButtonDown(0)) {
                                    m.setToggled(!m.isToggled());
                                } else if (Mouse.isButtonDown(1)) {
                                    ModuleTab modT = new ModuleTab(m);
                                    for (Tab m1 :
                                            tabs) {
                                        if(m1.name.equals(modT.name)) {
                                            currentTab = m1;
                                            return;
                                        }
                                    }
                                    tabs.add(modT);
                                    currentTab = modT;
                                }
                            }).render(mouseX, mouseY);
                        }
                    }
                    arial18.drawString(m.getName(), windowX + animListX - 120, modsY + 5, new Color(50, 50, 50).getRGB());
                    modsY += 20;
                }

            } else {
                new Rect(windowX, modsY, animListX, 20, new Color(255, 255, 255), () -> {
                    if (Mouse.isButtonDown(0)) {
                        mt.show = !mt.show;
                    }
                }).render(mouseX, mouseY);

            }

            modsY += 25;
        }


        ArrayList<Tab> tabs2 = new ArrayList<>();
        float x = 4;
        for (Tab t : tabs) {
            float swidth = arial16.getStringWidth(t.name) + 14;
            t.x = t.animationUtils.animate(windowX + x + animListX, t.x, drag ? 2 : 0.1F);
            new Rect(t.x, windowY + 30, swidth, 20, new Color(0, 0, 0, 0), () -> {
                if (Mouse.isButtonDown(0))
                    currentTab = t;
            }).render(mouseX, mouseY);


            if (isHovered(t.x + swidth - 4, windowY + 30, t.x + swidth + 4, windowY + 50, mouseX, mouseY)) {
                if (Mouse.isButtonDown(0)) {
                    tabs2.add(t);
                }
            }

            x += swidth;
        }

        for (Tab tab : tabs2) {
            tabs.remove(tab);
        }
    }

    @Override
    public void initGui() {
        sr = new ScaledResolution(mc);

        if (sr.getScaledWidth() < 550 && sr.getScaledHeight() < 300) {
            windowWidth = sr.getScaledWidth() - 10;
            windowHeight = sr.getScaledHeight() - 10;
            windowX = (sr.getScaledWidth() - windowWidth) / 2;
            windowY = (sr.getScaledHeight() - windowHeight) / 2;
        }
        if (windowWidth == 0)
            windowWidth = 550;
        if (windowHeight == 0)
            windowHeight = 300;
        if (windowX == 0)
            windowX = (sr.getScaledWidth() - windowWidth) / 2;
        if (windowY == 0)
            windowY = (sr.getScaledHeight() - windowHeight) / 2;
        if (tabs.size() == 0) {
            //Tab tab = new MainTab();
            //tabs.add(tab);
            //currentTab = tab;
        }

        super.initGui();
    }

    //Drag verb
    public float mouseDX, mouseDY;
    public boolean drag;

    public void drawWindow(float mouseX, float mouseY) {

    }

    public void drawMWindow(int mouseX, int mouseY) {
        if (currentObj == null) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            if ((mouseDX != 0 && drag) && Mouse.isButtonDown(0)) {
                windowX = this.mouseX - mouseDX;
            } else {
                drag = false;
                mouseDX = 0;
                mouseDY = 0;
            }
            if ((mouseDY != 0 && drag) && Mouse.isButtonDown(0)) {
                windowY = this.mouseY - mouseDY;
            } else {
                drag = false;
                mouseDX = 0;
                mouseDY = 0;
            }
        }
        drawWindow(this.mouseX, this.mouseY);
    }

    public void drawTasksBar() {

    }

    public void drawBar(float mouseX, float mouseY) {
        Blist.onRender(windowX + 8, windowY + 8, mouseX, mouseY);
        Bconfigs.onRender(windowX + windowWidth - 20, windowY + 8, mouseX, mouseY);
    }

    //Drag verb
    public float mouseDX2, mouseDY2;
    public boolean drag2;

    public void drawBG() {
        bg = bgAnim.animate(sr.getScaledWidth(), bg, 0.01f);
        RenderUtil.drawCircle((float) sr.getScaledWidth() / 2, (float) sr.getScaledHeight() / 2, bg, ColorUtils.reAlpha(clientColor.getRGB(), 0.1f));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        clientColor = Augustus.getInstance().getClientColor();
        drawBG();
        drawMWindow(mouseX, mouseY);
        drawBar(mouseX, mouseY);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.doGlScissor(windowX, windowY + 30, windowWidth, windowHeight - 30);
        if ((isHovered(windowX + windowWidth - 5, windowY + windowHeight - 5, windowX + windowWidth + 5, windowY + windowHeight + 5, mouseX, mouseY) && Mouse.isButtonDown(0)) || drag2) {
            if (windowWidth > 560) {
                windowWidth = mouseX - windowX - mouseDX2;
            } else if ((mouseX - windowX > windowWidth)) {
                windowWidth = mouseX - windowX - mouseDX2;
            }
            if (windowHeight > 310) {
                windowHeight = mouseY - windowY - mouseDY2;
            } else if (mouseY - windowY > windowHeight) {
                windowHeight = mouseY - windowY - mouseDY2;
            }
        }
        if (isHovered(windowX + windowWidth - 5, windowY + windowHeight - 5, windowX + windowWidth + 5, windowY + windowHeight + 5, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            drag2 = true;
            mouseDX2 = mouseX - (windowX + windowWidth);
            mouseDY2 = mouseY - (windowY + windowHeight);
        }
        if (!Mouse.isButtonDown(0)) {
            drag2 = false;
            mouseDX2 = 0;
            mouseDY2 = 0;
        }
        drawTasksBar();
        drawList(mouseX, mouseY);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public void drawList(float mouseX, float mouseY) {

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    public static boolean isHovered(float x, float y, float x2, float y2, float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
