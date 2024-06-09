// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.notifications;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import xyz.niggfaclient.utils.render.RenderUtils;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.render.HUD;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import xyz.niggfaclient.font.Fonts;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.utils.other.animation.Animate;

public class Notification
{
    public NotificationType notificationType;
    public String title;
    public String body;
    public Animate translate;
    public float width;
    public float height;
    public long duration;
    public int color;
    public TimerUtil timer;
    public boolean dead;
    
    public Notification(final String title, final String body, final long duration, final NotificationType type) {
        this.title = title;
        this.body = body;
        this.notificationType = type;
        if (body != null) {
            this.width = (float)(Math.max(Fonts.sf20.getStringWidth(title), Fonts.sf18.getStringWidth(body)) + 4);
        }
        else {
            this.width = (float)(Fonts.sf18.getStringWidth(title) + 4);
        }
        this.height = 27.0f;
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        this.translate = new Animate(sr.getScaledWidth(), sr.getScaledHeight() - this.height - 2.0f);
        this.duration = duration;
        this.color = type.getColor();
        this.timer = new TimerUtil();
    }
    
    public void render(final int width, final int height, final int index) {
        if (this.timer.hasElapsed(this.duration)) {
            this.translate.animate(width, height - (this.height + 2.0f) * index - 2.0f);
        }
        else {
            this.translate.animate(width - this.width, height - (this.height + 2.0f) * index - 2.0f);
        }
        if (this.translate.getX() >= width) {
            this.dead = true;
            return;
        }
        final HUD hud = ModuleManager.getModule(HUD.class);
        if (Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
            RenderUtils.drawOutlinedRoundedRect2(this.translate.getX() - 28.0, this.translate.getY() - (hud.isEnabled() ? 23 : 12), this.translate.getX() + this.width - 3.0, this.translate.getY() + this.height - (hud.isEnabled() ? 26 : 14), 6.0, 2.0f, this.color);
            RenderUtils.drawRoundedRect2(this.translate.getX() - 28.0, this.translate.getY() - (hud.isEnabled() ? 23 : 12), this.translate.getX() + this.width - 3.0, this.translate.getY() + this.height - (hud.isEnabled() ? 26 : 14), 6.0, new Color(0, 0, 0, 100).getRGB());
            Gui.drawRect(this.translate.getX() - 2.0, this.translate.getY() + this.height - (hud.isEnabled() ? 29 : 18), this.translate.getX() + (System.currentTimeMillis() - this.timer.getCurrentMs()) / (double)this.duration * this.width - 3.0, this.translate.getY() + this.height - (hud.isEnabled() ? 28 : 17), this.color);
            final String name = this.notificationType.getName();
            switch (name) {
                case "Success": {
                    Fonts.icons40.drawStringWithShadow("o", this.translate.getX() - 24.0, this.translate.getY() - (hud.isEnabled() ? 18 : 7), this.color);
                    break;
                }
                case "Info": {
                    Fonts.icons40.drawStringWithShadow("m", this.translate.getX() - 23.0, this.translate.getY() - (hud.isEnabled() ? 18 : 7), this.color);
                    break;
                }
                case "Warning": {
                    Fonts.icons40.drawStringWithShadow("r", this.translate.getX() - 25.0, this.translate.getY() - (hud.isEnabled() ? 18 : 7), this.color);
                    break;
                }
                case "Error": {
                    Fonts.icons40.drawStringWithShadow("p", this.translate.getX() - 23.0, this.translate.getY() - (hud.isEnabled() ? 19 : 7), this.color);
                    break;
                }
            }
            Fonts.sf20.drawString(this.title, this.translate.getX() - 3.0, this.translate.getY() - (hud.isEnabled() ? 20 : 9), -1);
            Fonts.sf18.drawString(ChatFormatting.GRAY + this.body, this.translate.getX() - 3.0, this.translate.getY() - (hud.isEnabled() ? 11 : 0), -1);
        }
        else {
            RenderUtils.drawOutlinedRoundedRect2(this.translate.getX() - 28.0, this.translate.getY() - (hud.isEnabled() ? 6 : -4), this.translate.getX() + this.width - 3.0, this.translate.getY() + this.height - (hud.isEnabled() ? 8 : -2), 6.0, 2.0f, this.color);
            RenderUtils.drawRoundedRect2(this.translate.getX() - 28.0, this.translate.getY() - (hud.isEnabled() ? 6 : -4), this.translate.getX() + this.width - 3.0, this.translate.getY() + this.height - (hud.isEnabled() ? 8 : -2), 6.0, new Color(0, 0, 0, 100).getRGB());
            Gui.drawRect(this.translate.getX() - 2.0, this.translate.getY() + this.height - (hud.isEnabled() ? 12 : 2), this.translate.getX() + (System.currentTimeMillis() - this.timer.getCurrentMs()) / (double)this.duration * this.width - 3.0, this.translate.getY() + this.height - (hud.isEnabled() ? 11 : 1), this.color);
            final String name2 = this.notificationType.getName();
            switch (name2) {
                case "Success": {
                    Fonts.icons40.drawStringWithShadow("o", this.translate.getX() - 24.0, this.translate.getY() - (hud.isEnabled() ? 1.5 : -9.0), this.color);
                    break;
                }
                case "Info": {
                    Fonts.icons40.drawStringWithShadow("m", this.translate.getX() - 23.0, this.translate.getY() - (hud.isEnabled() ? 1.5 : -9.0), this.color);
                    break;
                }
                case "Warning": {
                    Fonts.icons40.drawStringWithShadow("r", this.translate.getX() - 25.0, this.translate.getY() - (hud.isEnabled() ? 0 : -10), this.color);
                    break;
                }
                case "Error": {
                    Fonts.icons40.drawStringWithShadow("p", this.translate.getX() - 23.0, this.translate.getY() - (hud.isEnabled() ? 2 : -8), this.color);
                    break;
                }
            }
            Fonts.sf20.drawString(this.title, this.translate.getX() - 3.0, this.translate.getY() - (hud.isEnabled() ? 3 : -7), -1);
            Fonts.sf18.drawString(ChatFormatting.GRAY + this.body, this.translate.getX() - 3.0, this.translate.getY() + (hud.isEnabled() ? 5.5 : 16.0), -1);
        }
    }
    
    public boolean isDead() {
        return this.dead;
    }
}
