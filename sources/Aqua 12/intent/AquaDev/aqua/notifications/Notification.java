// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.notifications;

import intent.AquaDev.aqua.modules.visual.Blur;
import intent.AquaDev.aqua.modules.visual.Shadow;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.utils.TimeUtil;
import net.minecraft.client.gui.Gui;

public class Notification extends Gui
{
    private boolean showing;
    private boolean started;
    private String name;
    private String description;
    public int height;
    private long wait;
    private boolean doneWaiting;
    private NotificationType type;
    private long animationStart;
    private int notificationWidth;
    private TimeUtil timer;
    
    public Notification(final String name, final String description, final long wait, final NotificationType type) {
        this.showing = false;
        this.started = false;
        this.height = 30;
        this.wait = 2L;
        this.doneWaiting = false;
        this.notificationWidth = 150;
        this.timer = new TimeUtil();
        this.name = name;
        this.description = description;
        this.type = type;
        final int nameWidth = Aqua.INSTANCE.novoline.getStringWidth(name);
        final int descWidth = Aqua.INSTANCE.novoline.getStringWidth(description);
        if (nameWidth > 170 || descWidth > 170) {
            if (nameWidth > descWidth) {
                this.notificationWidth = nameWidth + 20;
            }
            else {
                this.notificationWidth = descWidth + 20;
            }
        }
        this.animationStart = System.currentTimeMillis();
        final boolean b = true;
        this.showing = b;
        this.started = b;
    }
    
    public void draw2(final int y) {
        if (this.isShowing()) {
            final double animationTime = 400.0;
            final double waitTime = 1000.0;
            this.doneWaiting = (System.currentTimeMillis() - this.animationStart > animationTime + waitTime);
            final double animationProgress = this.doneWaiting ? (1.0 - (System.currentTimeMillis() - (this.animationStart + animationTime + waitTime)) / animationTime) : Math.min(1.0, (System.currentTimeMillis() - this.animationStart) / animationTime);
            if (this.doneWaiting && animationProgress <= 0.1) {
                this.setShowing(false);
            }
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final float notiX = sr.getScaledWidth() / 2.0f + 75.0f - this.notificationWidth;
            int bgColor = Integer.MIN_VALUE;
            switch (this.type) {
                case INFO: {
                    bgColor = new Color(20, 20, 40, 150).getRGB();
                    break;
                }
                case WARNING: {
                    bgColor = new Color(145, 75, 20, 150).getRGB();
                    break;
                }
                case ERROR: {
                    bgColor = new Color(75, 20, 20, 150).getRGB();
                    break;
                }
                default: {
                    bgColor = new Color(20, 20, 20, 150).getRGB();
                    break;
                }
            }
            RenderUtil.drawRoundedRect2Alpha(notiX + 20.0f, y * animationProgress, this.notificationWidth - 42, this.height * animationProgress, 3.0, new Color(0, 0, 0, 70));
            GlStateManager.resetColor();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.pushMatrix();
            GL11.glEnable(3089);
            RenderUtil.scissor(notiX + 20.0f, y * animationProgress, this.notificationWidth - 42, this.height * animationProgress);
            Aqua.INSTANCE.novoline.drawString(this.name, notiX + 58.0f, (float)(y * animationProgress + 3.0), Color.white.getRGB());
            Aqua.INSTANCE.novoline.drawCenteredString(this.description, notiX + 73.0f, (float)(y * animationProgress + 16.0), Color.white.getRGB());
            GL11.glDisable(3089);
            GlStateManager.popMatrix();
            GlStateManager.resetColor();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (this.timer.hasReached(this.wait)) {
                this.doneWaiting = true;
            }
        }
    }
    
    public void draw(final int y) {
        if (this.isShowing()) {
            final double animationTime = 400.0;
            final double waitTime = 1000.0;
            this.doneWaiting = (System.currentTimeMillis() - this.animationStart > animationTime + waitTime);
            final double animationProgress = this.doneWaiting ? (1.0 - (System.currentTimeMillis() - (this.animationStart + animationTime + waitTime)) / animationTime) : Math.min(1.0, (System.currentTimeMillis() - this.animationStart) / animationTime);
            if (this.doneWaiting && animationProgress <= 0.1) {
                this.setShowing(false);
            }
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final float notiX = sr.getScaledWidth() / 2.0f + 75.0f - this.notificationWidth;
            int bgColor = Integer.MIN_VALUE;
            switch (this.type) {
                case INFO: {
                    bgColor = new Color(20, 20, 40, 150).getRGB();
                    break;
                }
                case WARNING: {
                    bgColor = new Color(145, 75, 20, 150).getRGB();
                    break;
                }
                case ERROR: {
                    bgColor = new Color(75, 20, 20, 150).getRGB();
                    break;
                }
                default: {
                    bgColor = new Color(20, 20, 20, 150).getRGB();
                    break;
                }
            }
            if (Aqua.moduleManager.getModuleByName("Shadow").isToggled()) {
                final float n;
                final double n2;
                Shadow.drawGlow(() -> RenderUtil.drawRoundedRect(n + 20.0f, y * n2, this.notificationWidth - 42, this.height * n2, 3.0, new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB()), false);
            }
            if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
                final float n3;
                final double n4;
                Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(n3 + 20.0f, y * n4, this.notificationWidth - 42, this.height * n4, 3.0, new Color(0, 0, 0, 170).getRGB()), false);
            }
            if (this.timer.hasReached(this.wait)) {
                this.doneWaiting = true;
            }
        }
    }
    
    public boolean isShowing() {
        return this.showing;
    }
    
    public void setShowing(final boolean showing) {
        this.showing = showing;
    }
    
    public boolean isStarted() {
        return this.started;
    }
    
    public void setStarted(final boolean started) {
        this.started = started;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public NotificationType getType() {
        return this.type;
    }
    
    public enum NotificationType
    {
        INFO, 
        ERROR, 
        WARNING, 
        NONE;
    }
}
