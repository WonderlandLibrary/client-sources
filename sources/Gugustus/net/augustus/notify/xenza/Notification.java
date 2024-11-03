package net.augustus.notify.xenza;

import java.awt.Color;
import java.awt.Font;

import net.augustus.events.EventShaderRender;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.notify.NotificationType;
import net.augustus.notify.reborn.CleanNotificationManager;
import net.augustus.ui.GuiIngameHook;
import net.augustus.utils.ResourceUtil;
import net.augustus.utils.TimeHelper;
import net.augustus.utils.skid.lorious.BlurUtil;
import net.augustus.utils.skid.tenacity.RenderUtilTenacity;
import net.augustus.utils.skid.tenacity.blur.GaussianBlur;
import net.augustus.utils.skid.tenacity.blur.KawaseBlur;
import net.augustus.utils.skid.tomorrow.AnimationUtils;
import net.augustus.utils.skid.tomorrow.RenderUtil;
import net.lenni0451.eventapi.manager.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import tv.twitch.broadcast.FrameBuffer;

public class Notification {
    public float x;
    public float width, height;
    public String name;
    public float lastTime;
    public TimeHelper timer;
    public NotificationType type;
    public boolean setBack;
    private float fy, cy = 0;
    private TimeHelper anitimer = new TimeHelper();
    private static UnicodeFontRenderer arial16;
    private AnimationUtils animationUtils = new AnimationUtils();
    private AnimationUtils animationUtils2 = new AnimationUtils();
    private ResourceLocation check = ResourceUtil.loadResourceLocation("pictures/check.png", "check");
    private ResourceLocation cross = ResourceUtil.loadResourceLocation("pictures/cross.png", "cross");;
    static {
        try {
            arial16 = new UnicodeFontRenderer(Font.createFont(0, GuiIngameHook.class.getResourceAsStream("/ressources/arial.ttf")).deriveFont(16F));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Notification(String name, NotificationType type) {
        this.name = name;
        this.type = type;
        this.lastTime = 1.5f;
        this.width = arial16.getStringWidth(name) + 25;
        this.height = 20;
    }

    public Notification(String name, NotificationType type, float lastTime) {
        this.name = name;
        this.type = type;
        this.lastTime = lastTime;
        this.width = arial16.getStringWidth(name) + 25;
        this.height = 20;

    }

    public void render(float y) {
        fy = y;
        if (cy == 0) {
            cy = fy + 25;
        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
//        RenderUtil.drawRect((float) (sr.getScaledWidth_double() - x), cy + height - 1, (float) (sr.getScaledWidth_double() - x) + width, cy + height, new Color(180, 180, 180));

        //RenderUtil.drawCustomImageAlpha(sr.getScaledWidth() - x + 3, cy + 4, 12, 12, new ResourceLocation("client/back.png"), -1, 255);
        
        
        
        Color bg = new Color(10, 10, 10, 110);
        if(type == NotificationType.ToggleOn)
            bg = new Color(10, 255, 10, 110);
        if(type == NotificationType.ToggleOff)
            bg = new Color(255, 10, 10, 110);
        if(type == NotificationType.Error) {
            int i = Math.max(0, Math.min(255, (int) (Math.sin(System.currentTimeMillis() / 100.0) * 255.0 / 2 + 127.5)));
            bg = new Color(i, 10, 10, 110);
        }
        
//        for(int i = 0; i < 2; i++)
//        	BlurUtil.blur((float) (sr.getScaledWidth_double() - x), cy, (float) ((sr.getScaledWidth_double() - x) + width), cy + height - 0.5f);
        
        RenderUtil.drawRect((float) (sr.getScaledWidth_double() - x), cy, (float) ((sr.getScaledWidth_double() - x) + width), cy + height, bg);
        
        switch (type) {
            case ToggleOn: {
                RenderUtil.drawCustomImageAlpha(sr.getScaledWidth() - x + 3, cy + 4, 12, 12, check, -1, 255);
                arial16.drawString("Enabled:", (sr.getScaledWidth() - x) + 18, cy, -1);
                break;
            }
            case ToggleOff: {
                RenderUtil.drawCustomImageAlpha(sr.getScaledWidth() - x + 3, cy + 4, 12, 12, cross, -1, 255);
                arial16.drawString("Disabled:", (sr.getScaledWidth() - x) + 18, cy, -1);
                break;
            }
            case Error: {
            	
            	break;
            }
        }
        
        arial16.drawString(name, (sr.getScaledWidth() - x) + 18, cy + 7, -1);
        
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
}