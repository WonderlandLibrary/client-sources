package me.superskidder.lune.guis.clickguis.tomorrow;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.guis.clickguis.tomorrow.values.ModeRender;
import me.superskidder.lune.guis.clickguis.tomorrow.values.NumberRender;
import me.superskidder.lune.guis.clickguis.tomorrow.values.OptionRender;
import me.superskidder.lune.guis.clickguis.tomorrow.values.ValueRender;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.render.RenderUtils;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.util.ArrayList;

import static me.superskidder.lune.guis.clickguis.tomorrow.MainWindow.mainColor;

public class ModuleWindow {
    public float x, y, height, height2;
    public Color color;
    public Mod mod;
    public TimerUtil timerUtil = new TimerUtil();
    public ArrayList<ValueRender> vrs = new ArrayList<>();
    public float circleR = 0;
    public float circleX, circleY;

    public ModuleWindow(Mod mod, float x, float y, Color c) {
        this.x = x;
        this.y = y;
        this.color = c;
        this.mod = mod;

        for (Value v : mod.values) {
            if (v instanceof Bool) {
                vrs.add(new OptionRender(v, x, y));
            } else if (v instanceof Num) {
                vrs.add(new NumberRender(v, x, y));

            } else if (v instanceof Mode) {
                vrs.add(new ModeRender(v, x, y));
            }

            if (!(v instanceof Mode)) {
                this.height2 += 20;
            } else {
                this.height2 += ((Mode<?>) v).getModes().length * 20;
            }
        }
        circleR = 100;
    }

    public void drawModule(int mouseX, int mouseY) {
        for (ValueRender v : vrs) {
            v.x = x;
            v.y = y;
        }
        if (height != height2) {
            height += (height2 - height) / 60;
        }

        RenderUtil.drawRect(x, y + (mod.values.size() == 0 ? 25 : 20), x + 100, y + height + (mod.values.size() == 0 ? 25 : 25), new Color(249, 250, 255));

//        if (mod.isEnabled()) {
//            RenderUtil.drawRect(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), new Color(color.getRed(), color.getGreen(), color.getBlue()));
////            RenderUtil.drawCustomImage(x + 74, y + (mod.values.size() == 0 ? 6 : 5), 20, 10, new ResourceLocation("client/ui/clickgui/toggle_24x10.png"),-1);
//        } else {
//            RenderUtil.drawRect(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), new Color(255, 255, 255));
////            RenderUtil.drawCustomImage(x + 74, y + (mod.values.size() == 0 ? 6 : 5), 20, 10, new ResourceLocation("client/ui/clickgui/toggle2_24x10.png"));
//        }

        RenderUtil.drawRect(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), new Color(155, 155, 155));
        if (circleR >= 95) {
            RenderUtil.drawRect(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), mainColor);

        }

        RenderUtils.drawGradientRect(x, y + (mod.values.size() == 0 ? 25 : 20), x + 100, y + (mod.values.size() == 0 ? 27 : 22), new Color(249, 250, 255).getRGB(), new Color(200, 200, 200, 100).getRGB());


        if (circleR < 100 && mod.getState()) {
            circleR += 1 + circleR / 100;
        } else if (circleR > 0 && !mod.getState()) {
            circleR -= circleR / 20;
        }
        if(circleR <= 100) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.doGlScissor(x, y, 100, (mod.values.size() == 0 ? 25 : 20));

//            RenderUtil.circle(circleX, circleY, circleR, new Color(mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue()));
            RenderUtil.circle(circleX, circleY, circleR, mainColor);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        FontLoaders.F18.drawString(mod.getName(), x + 10, y + (mod.values.size() == 0 ? 10 : 8), new Color(255,255,255).getRGB());

        if (isHovered(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), mouseX, mouseY) && Mouse.isButtonDown(0) && timerUtil.delay(200)) {
            mod.setStage(!mod.getState());
            circleX = mouseX;
            circleY = mouseY;
            if (circleR == 0) {
                circleR = 1;
            }
            timerUtil.reset();
        }

        float valueY = y + (mod.values.size() == 0 ? 35 : 30);

        for (ValueRender v : vrs) {
            if (v.value instanceof Mode) {
                v.onRender(x,valueY);
                v.onMouseMove(mouseX, mouseY, Mouse.getButtonCount());
                for (Enum m : ((Mode<?>) v.value).getModes()) {
                    valueY += 20;
                }
            }
        }

        for (ValueRender v : vrs) {
            if (v.value instanceof Bool) {
                OptionRender render = (OptionRender) v;
                render.onRender(x, valueY);
                render.onMouseMove(mouseX, mouseY, Mouse.getButtonCount());

                valueY += 20;
            }
        }

        for (ValueRender v : vrs) {
            if (v.value instanceof Num) {

                NumberRender render = (NumberRender) v;
                render.onRender(x, valueY);
                render.onMouseMove(mouseX, mouseY, Mouse.getButtonCount());


                valueY += 20;
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
