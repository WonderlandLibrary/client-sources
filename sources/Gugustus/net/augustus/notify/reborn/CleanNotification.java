package net.augustus.notify.reborn;

import net.augustus.Augustus;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.notify.NotificationType;
import net.augustus.ui.GuiIngameHook;
import net.augustus.utils.TimeHelper;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.skid.tenacity.ColorUtil;
import net.augustus.utils.skid.tenacity.RoundedUtil;
import net.augustus.utils.skid.tenacity.blur.KawaseBlur;
import net.augustus.utils.skid.tenacity.tuples.Pair;
import net.augustus.utils.skid.tomorrow.AnimationUtils;
import net.augustus.utils.skid.tomorrow.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class CleanNotification implements MM {
    public float x;
    public float width, height;
    public String name;
    public float lastTime;
    public TimeHelper timer;
    public NotificationType type;
    public boolean setBack;
    private float fy, cy = 0;
    private TimeHelper anitimer = new TimeHelper();
    private static UnicodeFontRenderer arial18;
    private AnimationUtils animationUtils = new AnimationUtils();
    private AnimationUtils animationUtils2 = new AnimationUtils();


    public CleanNotification(String name, NotificationType type) {
        this.name = name;
        this.type = type;
        this.lastTime = 1.5f;
        this.width = arial18.getStringWidth(name) + 25;
        this.height = 20;
    }

    public CleanNotification(String name, NotificationType type, float lastTime) {
        this.name = name;
        this.type = type;
        this.lastTime = lastTime;
        this.width = arial18.getStringWidth(name) + 25;
        this.height = 20;

    }

    static {
        try {
            arial18 = new UnicodeFontRenderer(Font.createFont(0, GuiIngameHook.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(16F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void render(float y) {
        fy = y;
        if (cy == 0) {
            cy = fy + 25;
        }

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        //RenderUtil.drawRect((float) (sr.getScaledWidth_double() - x), cy, (float) ((sr.getScaledWidth_double() - x) + width), cy + height, new Color(10, 10, 10, 220));
        //RenderUtil.drawRect((float) (sr.getScaledWidth_double() - x), cy + height - 1, (float) (sr.getScaledWidth_double() - x) + width, cy + height, new Color(180, 180, 180));

        if(!(mm.shaders.isToggled() && mm.shaders.blur.getBoolean())) {
            RenderUtil.drawRect((float) (sr.getScaledWidth_double() - x), cy, (float) ((sr.getScaledWidth_double() - x) + width), cy + height, new Color(10, 10, 10, 100));
        }

        arial18.drawString(name, (sr.getScaledWidth() - x) + 18, cy + 7, -1);
    }

    public void update() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (timer == null && Math.abs(x - width) < 0.1f) {
            timer = new TimeHelper();
            timer.reset();
        }
        if (anitimer.reached(10)) {
            cy = animationUtils.animate(fy, cy, 0.1f);

            if (!setBack) {
                x = animationUtils2.animate(width, x, 0.1f);
            } else {
                x = animationUtils2.animate(0, x, 0.1f);
            }
            anitimer.reset();
        }
    }

    public void shader() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        Gui.drawRect2((float) (sr.getScaledWidth_double() - x), cy, width, height, -1);
    }
}