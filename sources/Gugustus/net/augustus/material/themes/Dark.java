package net.augustus.material.themes;

import net.augustus.Augustus;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.material.Category;
import net.augustus.material.Main;
import net.augustus.material.Tab;
import net.augustus.material.button.CButton;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.skid.tomorrow.ColorUtils;
import net.augustus.utils.skid.tomorrow.Rect;
import net.augustus.utils.skid.tomorrow.RenderUtil;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class Dark extends Main {
    private static UnicodeFontRenderer arial18;
    private static UnicodeFontRenderer arial16;
    static {
        try {
            arial18 = new UnicodeFontRenderer(Font.createFont(0, Dark.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(18F));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            arial16 = new UnicodeFontRenderer(Font.createFont(0, Dark.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(16F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        Blist = new CButton("Modules", "client/clickgui/modules.png", 2, 4, 12, 8);
        Bconfigs = new CButton("Modules", "client/clickgui/settings.png", 3, 3, 11, 11);
        categories.clear();
        for (Categorys mt : Categorys.values()) {
            categories.add(new Category(mt, 0, false));
        }

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }



    @Override
    public void drawTasksBar() {
        super.drawTasksBar();
        RenderUtil.drawRect(windowX + animListX, windowY + 30, windowX + windowWidth, windowY + 50, ColorUtils.reAlpha(clientColor.getRGB(), 0.6f));
        float x = 4;
        for (Tab t : tabs) {
            if (currentTab == t)
                t.render(mouseX, mouseY);

            float swidth = arial16.getStringWidth(t.name) + 14;
            t.x = t.animationUtils.animate(windowX + x + animListX, t.x, drag ? 2 : 0.1F);
            new Rect(t.x, windowY + 30, swidth, 20, new Color(0, 0, 0, 0), new Runnable() {
                @Override
                public void run() {
                    if (Mouse.isButtonDown(0))
                        currentTab = t;
                }
            }).render(mouseX, mouseY);


            if (isHovered(t.x + swidth - 4, windowY + 30, t.x + swidth + 4, windowY + 50, mouseX, mouseY)) {
                arial18.drawString("-", t.x + swidth - 6, windowY + 40, new Color(255, 0, 0).getRGB());
            } else {
                arial18.drawString("-", t.x + swidth - 6, windowY + 40, new Color(255, 255, 255).getRGB());
            }

            if (t == currentTab) {
                arial16.drawString(t.name, t.x + 2, windowY + 40, -1);
                RenderUtil.drawRect(t.x, windowY + 48, t.x + swidth, windowY + 50, clientColor.getRGB());
            } else {
                arial16.drawString(t.name, t.x + 2, windowY + 40, new Color(34, 34, 34, 150).getRGB());
            }

            x += swidth;
        }
    }

    @Override
    public void drawList(float mouseX, float mouseY) {
        super.drawList(mouseX, mouseY);
        if (Blist.realized) {
            animListX = listAnim.animate(140, animListX, 0.2f);
        } else {
            animListX = listAnim.animate(0, animListX, 0.2f);
        }
//        GL11.glScissor((int)windowX, mc.displayHeight - (int)(windowY + windowHeight), (int)windowWidth, (int)windowHeight);
        if (animListX != 0) {
            RenderUtil.drawRect(windowX, windowY + 30, windowX + animListX, windowY + windowHeight, new Color(34, 34, 34));
            RenderUtil.drawGradientSideways(windowX + animListX, windowY + 30, windowX + animListX + 3, windowY + windowHeight, new Color(50, 50, 50, 100).getRGB(), new Color(255, 255, 255, 0).getRGB());
            float dWheel = Mouse.getDWheel();

//            modsY = windowY + 35;

            if (dWheel > 0 && listRoll2 < 0) {
                listRoll2 += 32;
            } else if (dWheel < 0) {
                listRoll2 -= 32;
            }

            listRoll = rollAnim.animate(listRoll2, listRoll, 0.05f);

            float modsY = windowY + 35 + listRoll;


            for (Category mt : categories) {
                if (mt.show || mt.needRemove) {
                    new Rect(windowX, modsY, animListX, 20, new Color(34, 34, 34), () -> {
                    }).render(mouseX, mouseY);
                    arial18.drawString(mt.moduleType.name(), windowX + animListX - 130, modsY + 5, new Color(255, 255, 255).getRGB());
                    modsY += 25;
                    mt.modsY2 = 0;
                    for (Module m : Augustus.getInstance().getModuleManager().getModules(mt.moduleType)) {
                        new Rect(windowX, modsY + mt.modsY2, animListX, 15, new Color(34, 34, 34), new Runnable() {
                            @Override
                            public void run() {
                            }
                        }).render(mouseX, mouseY);
                        if (modsY + 5 + mt.modsY2 < modsY + mt.modsY3 + 25)
                            arial18.drawString(m.getName(), windowX + animListX - 120, modsY + 5 + mt.modsY2, new Color(200, 200, 200).getRGB());
                        mt.modsY2 += 20;
                    }

                    if (mt.needRemove) {
                        mt.modsY3 = mt.rollAnim2.animate(-25, mt.modsY3, 0.1f);
                        if (mt.modsY3 == -25) {
                            mt.needRemove = false;
                            mt.show = false;
                        }
                    } else {
                        mt.modsY3 = mt.rollAnim2.animate(mt.modsY2, mt.modsY3, 0.1f);
                    }

                    modsY += mt.modsY3;
                } else {
                    new Rect(windowX, modsY, animListX, 20, new Color(34, 34, 34), new Runnable() {
                        @Override
                        public void run() {
                        }
                    }).render(mouseX, mouseY);
                    arial18.drawString(mt.moduleType.name(), windowX + animListX - 130, modsY + 5, new Color(255, 255, 255).getRGB());

                }

                modsY += 25;
            }
        }

    }

    @Override
    public void drawWindow(float mouseX, float mouseY) {
        RenderUtil.drawRoundedRect(windowX, windowY, windowX + windowWidth, windowY + windowHeight, 2, new Color(34, 34, 34).getRGB());

        new Rect(windowX, windowY, windowWidth, 30, clientColor, new Runnable() {
            @Override
            public void run() {
                if (!Mouse.isButtonDown(0))
                    return;
                drag = true;
                if (mouseDX == 0) {
                    mouseDX = mouseX - windowX;
                }
                if (mouseDY == 0) {
                    mouseDY = mouseY - windowY;
                }
//                mouseDX = 0;
//                mouseDY = 0;
            }
        }).render(mouseX, mouseY);
    }
}
