/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.notification;

import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.Client;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.notification.Notification;
import me.thekirkayt.utils.ListManager;
import me.thekirkayt.utils.NahrFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;

public class NotificationManager
extends ListManager<Notification> {
    public boolean isDrawing;

    public void addNotification(String notification) {
        this.getContentList().add(new Notification(FriendManager.replaceText(notification)));
        if (this.getContentList().size() > 16) {
            this.getContentList().remove(this.getContentList().get(0));
        }
    }

    public boolean willDraw() {
        return Module.getModByName("notifications").isEnabled();
    }

    public void drawNotifications() {
        if (Module.getModByName("notifications").isEnabled() && this.getContentList().size() > 0) {
            int yPos = 1;
            ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            for (Notification not : this.getContentList()) {
                if (System.currentTimeMillis() - not.addTime >= 7500L) {
                    this.isDrawing = false;
                    this.getContentList().remove(not);
                    continue;
                }
                if (System.currentTimeMillis() - not.addTime >= 7250L) {
                    this.isDrawing = true;
                    RenderHelper.drawRect((float)ScaledResolution.getScaledWidth() + RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 1 - yPos, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 18.0f, ScaledResolution.getScaledHeight() - 11 - yPos, 1080650089);
                    RenderHelper.getNahrFont().drawString(not.message, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 15 - yPos, NahrFont.FontType.SHADOW_THICK, 1892930515, 1879048192);
                    yPos += (int)RenderHelper.getNahrFont().getStringHeight(not.message);
                    continue;
                }
                if (System.currentTimeMillis() - not.addTime >= 7000L) {
                    this.isDrawing = true;
                    RenderHelper.drawRect((float)ScaledResolution.getScaledWidth() + RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 1 - yPos, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 18.0f, ScaledResolution.getScaledHeight() - 11 - yPos, 1349085545);
                    RenderHelper.getNahrFont().drawString(not.message, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 15 - yPos, NahrFont.FontType.SHADOW_THICK, -2049715245, -2063597568);
                    yPos += (int)RenderHelper.getNahrFont().getStringHeight(not.message);
                    continue;
                }
                if (System.currentTimeMillis() - not.addTime >= 6750L) {
                    this.isDrawing = true;
                    RenderHelper.drawRect((float)ScaledResolution.getScaledWidth() + RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 1 - yPos, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 18.0f, ScaledResolution.getScaledHeight() - 11 - yPos, 1617521001);
                    RenderHelper.getNahrFont().drawString(not.message, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 15 - yPos, NahrFont.FontType.SHADOW_THICK, -1714170925, -1728053248);
                    yPos += (int)RenderHelper.getNahrFont().getStringHeight(not.message);
                    continue;
                }
                if (System.currentTimeMillis() - not.addTime >= 6500L) {
                    this.isDrawing = true;
                    RenderHelper.drawRect((float)ScaledResolution.getScaledWidth() + RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 1 - yPos, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 18.0f, ScaledResolution.getScaledHeight() - 11 - yPos, 1885956457);
                    RenderHelper.getNahrFont().drawString(not.message, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 15 - yPos, NahrFont.FontType.SHADOW_THICK, -573320237, -587202560);
                    yPos += (int)RenderHelper.getNahrFont().getStringHeight(not.message);
                    continue;
                }
                this.isDrawing = true;
                RenderHelper.drawRect((float)ScaledResolution.getScaledWidth() + RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 1 - yPos, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 18.0f, ScaledResolution.getScaledHeight() - 11 - yPos, -2140575383);
                RenderHelper.getNahrFont().drawString(not.message, (float)ScaledResolution.getScaledWidth() - RenderHelper.getNahrFont().getStringWidth(not.message) + 19.0f, ScaledResolution.getScaledHeight() - 15 - yPos, NahrFont.FontType.SHADOW_THICK, -2894893, -16777216);
                yPos += (int)RenderHelper.getNahrFont().getStringHeight(not.message);
            }
        }
    }

    public void addInfo(String msg) {
        Minecraft.getMinecraft();
        Minecraft.thePlayer.playSound("note.pling", 1.0f, 1.0f);
        msg = "\u00a7f[\u00a74R\u00a7feverie]\u00a74 " + msg;
        Client.getNotificationManager().addNotification(msg);
    }

    public void addWarn(String msg) {
        Minecraft.getMinecraft();
        Minecraft.thePlayer.playSound("note.pling", 1.0f, 0.1f);
        msg = "\u00a7f[\u00a74R\u00a7feverie]\u00a74 " + msg;
        Client.getNotificationManager().addNotification(msg);
    }

    @Override
    public void setup() {
        this.contents = new ArrayList();
        for (Notification not : Notification.notifications) {
            this.getContentList().add(not);
        }
    }
}

