package me.superskidder.lune.guis.clickguis.tomorrow;

import me.superskidder.lune.Lune;
import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends GuiScreen {
    public static float x = 100;
    public static float y = 100;
    public static float width = 700;
    public static float height = 200;

    public static Color mainColor = new Color(55, 171, 255);

    public boolean drag;
    public float dragX = 0, dragY = 0;

    public static ModCategory currentCategory = ModCategory.Combat;

    public TimerUtil timer = new TimerUtil();

    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float outro;
    public float lastOutro;
    private boolean close = false;
    private boolean closed;

    public static ArrayList<ModuleWindow> mods = new ArrayList<ModuleWindow>();
    private float x1, y1;

    public float smoothTrans(double current, double last) {
        return (float) (current + (last - current) / (mc.debugFPS / 10));
    }

    @Override
    public void initGui() {
        super.initGui();
        x1 = x;
        y1 = y;

//        if (mods.size() == 0) {
        mods.clear();
        for (Mod module : ModuleManager.modList) {
            mods.add(new ModuleWindow(module, x, y + 50, mainColor));
        }
//        }


        percent = 1.33f;
        lastPercent = 0.98f;

        percent2 = 0.98f;
        lastPercent2 = 1f;

        outro = 1;
        lastOutro = 1;
        mainColor = new Color(106,145,235);

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if ((!closed && keyCode == Keyboard.KEY_ESCAPE)) {
            percent = percent2;
            close = true;
            return;
        }


        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution sr = new ScaledResolution(mc);
//        float outro = smoothTrans(this.outro, lastOutro);
//        if (mc.currentScreen == null) {
//            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
//            GlStateManager.scale(outro, outro, 0);
//            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
//        }


        //animation

        percent = smoothTrans(this.percent, lastPercent);

        if(!close) {
            if (percent > 0.981) {
                GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
                GlStateManager.scale(percent, percent, 0);
                GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
            } else {
                percent2 = smoothTrans(this.percent2, lastPercent2);
                GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
                GlStateManager.scale(percent2, percent2, 0);
                GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
            }
        }else {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(percent, percent, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        }


        if (percent <= 1.5 && close) {
            percent = smoothTrans(this.percent, 2);
//            percent2 = smoothTrans(this.percent2, 2);
        }

        if (percent >= 1.4 && close) {
            mc.currentScreen = null;
            mc.mouseHelper.grabMouseCursor();
            mc.inGameHasFocus = true;
        }


        //animation 2

//        if(x != x1){
//            x += (x1 - x) / 30;
//        }
//        if(y != y1){
//            y += (y1 - y) / 30;
//        }


        width = 500;
        height = 300;
        if (dragX != 0 && drag) {
            x1 = mouseX - dragX;
        }

        if (dragY != 0 && drag) {
            y1 = mouseY - dragY;
        }

        if (isHovered(x1, y1, x1 + 70, y1 + 35, mouseX, mouseY) && Mouse.isButtonDown(0)) {//拖动主窗口
            drag = true;
            if (dragX == 0) {
                dragX = mouseX - x1;
            }
            if (dragY == 0) {
                dragY = mouseY - y1;
            }

        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
            drag = false;
        }

        RenderUtil.drawRect(x, y, x + width, y + height, new Color(237, 240, 244));

        RenderUtil.drawRect(x, y, x + width, y + 35, new Color(255, 255, 255));

        FontLoaders.F24.drawString(Lune.CLIENT_NAME, x + 18, y + 15, mainColor.getRGB());

        CFontRenderer cateFont = FontLoaders.F18;

        float cateX = x + 90;
        for (ModCategory e : ModCategory.values()) {
            cateFont.drawString(e.name(), cateX, y + 15, e == currentCategory ? mainColor.getRGB() : new Color(170,170,170).getRGB());

            if (isHovered(cateX, y, cateX + cateFont.getStringWidth(e.name()), y + 35, mouseX, mouseY) && Mouse.isButtonDown(0) && timer.delay(200) && currentCategory != e) {
                mods.clear();
                for (Mod module : ModuleManager.modList) {
                    mods.add(new ModuleWindow(module, x, y + 50, mainColor));
                }
                currentCategory = e;
                timer.reset();
            }

            cateX += cateFont.getStringWidth(e.name()) + 15;
        }


        //功能列表
        float modsX = x + 10, modsY = y + 45;
        for (ModuleWindow mw : mods) {
            if (mw.mod.getType() == currentCategory) {

                if(mw.mod.getName().contains("Kill")){
                    System.out.println(mw.height2);
                    System.out.println(y + height);
                    System.out.println(modsY + mw.height2 + 25 + 10 > y + height);
                }

                x = x1;
                y = y1;
                if (!drag) {
                    if (mw.x != modsX) {
                        mw.x += (modsX - mw.x) / 30;
                    }
                    if (mw.y != modsY) {
                        mw.y += (modsY - mw.y) / 30;
                    }
//                    mw.y = modsY;

//                if (mw.y != modsY) {
//                    mw.y = modsY +  (modsY - mw.y) / 30;
//                }


//                if(x != x1){
//                    x += (x1 - x) / 30;
//                }
//                if(y != y1){
//                    y += (y1 - y) / 30;
//                }

                } else {
                    if (mw.x != modsX) {
                        mw.x += (modsX - mw.x) / 10;
                    }
                    mw.x = modsX;
                    mw.y = modsY;
                }

//                mw.x = modsX;
//                mw.y = modsY;
                mw.drawModule(mouseX, mouseY);
                if (modsY + mw.height2 <= y + height) {
                    modsY += mw.height2 + (mw.mod.values.size() == 0 ? 25 : 20) + 10;
                }
                if (modsY + mw.height2 + 25 + 10 > y + height) {
                    modsX += 110;
                    modsY = y + 45;
                }
            }
        }


    }

    public static boolean isHovered(float x, float y, float x1, float y1, float mouseX, float mouseY) {
        if (mouseX > x && mouseY > y && mouseX < x1 && mouseY < y1) {
            return true;
        }
        return false;
    }

}
