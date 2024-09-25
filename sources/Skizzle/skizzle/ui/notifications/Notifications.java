/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package skizzle.ui.notifications;

import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.modules.ModuleManager;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.ui.notifications.Notification;
import skizzle.util.RenderUtil;

public class Notifications {
    public Minecraft mc;
    public CopyOnWriteArrayList<Notification> notifs = new CopyOnWriteArrayList();

    public static {
        throw throwable;
    }

    public void onEvent(Event Nigga) {
        Notifications Nigga2;
        for (Notification Nigga3 : Nigga2.notifs) {
            if (!Nigga3.timer3.hasTimeElapsed((long)50697569 ^ 0x3059561L, true)) continue;
            new ScaledResolution(Nigga2.mc, Nigga2.mc.displayWidth, Nigga2.mc.displayHeight);
            if (Nigga3.anim > 0 && !Nigga3.animIn) {
                Nigga3.anim = (int)((double)Nigga3.anim - ModuleManager.notifications.speed.getValue());
            }
            if (Nigga3.animTop < Float.intBitsToFloat(2.12131379E9f ^ 0x7E70AE13) && !Nigga3.animIn) {
                Nigga3.animTop = (float)((double)Nigga3.animTop + ModuleManager.notifications.speed.getValue() / 2.0);
            }
            if ((float)(System.currentTimeMillis() - Nigga3.startTime) > Nigga3.startTimeTime - Float.intBitsToFloat(1.04663334E9f ^ 0x7DF45B7F)) {
                Nigga3.anim = (int)((double)Nigga3.anim + ModuleManager.notifications.speed.getValue());
                if (Nigga3.animTop > Float.intBitsToFloat(-1.07422528E9f ^ 0x7E309F53) && Nigga3.animIn) {
                    Nigga3.animTop = (float)((double)Nigga3.animTop - ModuleManager.notifications.speed.getValue() / 2.0);
                }
            }
            if ((float)(System.currentTimeMillis() - Nigga3.startTime) > Nigga3.startTimeTime - Float.intBitsToFloat(1.03788698E9f ^ 0x7E4AE61B)) {
                Nigga3.animIn = true;
            }
            if (!((float)(System.currentTimeMillis() - Nigga3.startTime) > Nigga3.startTimeTime)) continue;
            Nigga2.notifs.remove(Nigga3);
        }
    }

    public void draw() {
        Notifications Nigga;
        if (Nigga.notifs != null) {
            int Nigga2;
            String Nigga3;
            MinecraftFontRenderer Nigga4;
            ScaledResolution Nigga5;
            int Nigga6 = 0;
            Notification Nigga7 = null;
            for (Notification Nigga8 : Nigga.notifs) {
                Nigga5 = new ScaledResolution(Nigga.mc, Nigga.mc.displayWidth, Nigga.mc.displayHeight);
                Nigga4 = Client.fontNormal;
                if (!((double)Nigga6 < ModuleManager.notifications.maxNotifs.getValue()) || Nigga7 != null && Nigga8 == Nigga7) continue;
                Nigga3 = Nigga8.name;
                Nigga2 = Nigga4.getStringWidth(Nigga3) + 30;
                if (!Nigga8.type.equals((Object)Notification.notificationType.TOGGLE) && !Nigga8.type.equals((Object)Notification.notificationType.PROFILE)) {
                    Nigga2 = Nigga4.getStringWidth(Nigga8.description) + 32;
                }
                if (Nigga2 < 45) {
                    Nigga2 = 45;
                }
                if (Nigga8.type.equals((Object)Notification.notificationType.WARNING)) {
                    Nigga2 += 20;
                }
                if (Nigga8.type.equals((Object)Notification.notificationType.INFO)) {
                    Nigga2 += 8;
                }
                Nigga8.maxLength = Nigga2;
                if (Nigga8.timer2.hasTimeElapsed((long)-1816646928 ^ 0xFFFFFFFF93B82AFAL, true)) {
                    Nigga8.time2 -= Float.intBitsToFloat(1.09048742E9f ^ 0x7F7F84AF);
                }
                Math.abs(Nigga5.getScaledWidth() - 7 - Nigga2 - (Nigga5.getScaledWidth() - 2));
                float Nigga9 = (float)(System.currentTimeMillis() - Nigga8.startTime) / Nigga8.startTimeTime;
                if (Nigga9 > Float.intBitsToFloat(1.11220838E9f ^ 0x7DCAF3DF)) {
                    Nigga9 = Float.intBitsToFloat(1.11787366E9f ^ 0x7D21662F);
                }
                float Nigga10 = (float)Nigga8.anim - Nigga9 * (float)Nigga8.maxLength;
                int Nigga11 = Client.RGBColor;
                if (Nigga8.type.equals((Object)Notification.notificationType.WARNING)) {
                    Nigga11 = -852221;
                }
                if (Nigga8.type.equals((Object)Notification.notificationType.INFO)) {
                    Nigga11 = -15998951;
                }
                if (Nigga8.type.equals((Object)Notification.notificationType.PROFILE)) {
                    Nigga11 = -16733978;
                }
                if (ModuleManager.notifications.isEnabled()) {
                    Gui.drawRect(Nigga5.getScaledWidth() - 5 - Nigga2 - 2 + Nigga8.anim, (float)(Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 32) - Nigga8.animTop, Nigga5.getScaledWidth() + Nigga8.anim, Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 8, -1879048192);
                    Gui.drawRect(Nigga5.getScaledWidth() - 5 - Nigga2 - 2 + Nigga8.anim, (float)(Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 32) - Nigga8.animTop, Nigga5.getScaledWidth() - 5 - Nigga2 + Nigga8.anim, Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 8, Nigga11);
                    Gui.drawRect(Nigga5.getScaledWidth() - 5 - Nigga2 - 2 + Nigga8.anim, Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 10, (int)((float)(Nigga5.getScaledWidth() - 5 - 2 + Nigga8.anim) + Nigga10), Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 8, Nigga11);
                    if (Nigga8.type.equals((Object)Notification.notificationType.WARNING) || Nigga8.type.equals((Object)Notification.notificationType.INFO)) {
                        int Nigga12;
                        int Nigga13;
                        GL11.glEnable((int)3042);
                        GL11.glColor4f((float)Float.intBitsToFloat(1.09902413E9f ^ 0x7E01C713), (float)Float.intBitsToFloat(1.09846016E9f ^ 0x7EF92C3D), (float)Float.intBitsToFloat(1.08316326E9f ^ 0x7F0FC29E), (float)Float.intBitsToFloat(1.1027552E9f ^ 0x7E3AB587));
                        if (Nigga8.type.equals((Object)Notification.notificationType.WARNING)) {
                            Nigga.mc.getTextureManager().bindTexture(new ResourceLocation(Qprot0.0("\u99bf\u71c0\ua2e9\u88c8\ue697\u1c3a\u8c2a\uf596\u7823\u4dc6\u9609\uaf02\u1b0b\u5d75\u92b3\u05c7\u42f9\u2ff5\u3831")));
                            Nigga13 = 20;
                            Nigga12 = 20;
                            Gui.drawModalRectWithCustomSizedTexture(Nigga5.getScaledWidth() - Nigga2 - 2 + Nigga8.anim, (int)((float)(Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 32) - Nigga8.animTop), Float.intBitsToFloat(2.13093005E9f ^ 0x7F036950), Float.intBitsToFloat(2.1272297E9f ^ 0x7ECAF32B), Nigga13, Nigga12, Nigga13, Nigga12);
                        }
                        if (Nigga8.type.equals((Object)Notification.notificationType.INFO)) {
                            Nigga.mc.getTextureManager().bindTexture(new ResourceLocation(Qprot0.0("\u99bf\u71c0\ua2e9\u88c8\ue697\u1c3a\u8c2a\uf596\u7824\u4dc8\u9612\uaf02\u1b16\u5d35\u92a4\u0587\u42ee")));
                            Nigga13 = 25;
                            Nigga12 = 25;
                            Gui.drawModalRectWithCustomSizedTexture(Nigga5.getScaledWidth() - Nigga2 - 10 + Nigga8.anim, (int)((float)(Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 34) - Nigga8.animTop), Float.intBitsToFloat(2.13164467E9f ^ 0x7F0E5117), Float.intBitsToFloat(2.11496934E9f ^ 0x7E0FDF13), Nigga13, Nigga12, Nigga13, Nigga12);
                        }
                        RenderUtil.glColor(Nigga11);
                        GL11.glDisable((int)3042);
                    }
                }
                Nigga7 = Nigga8;
                ++Nigga6;
            }
            Nigga7 = null;
            Nigga6 = 0;
            for (Notification Nigga8 : Nigga.notifs) {
                Nigga5 = new ScaledResolution(Nigga.mc, Nigga.mc.displayWidth, Nigga.mc.displayHeight);
                Nigga4 = Client.fontNormal;
                if (!((double)Nigga6 < ModuleManager.notifications.maxNotifs.getValue()) || Nigga7 != null && Nigga8 == Nigga7) continue;
                if (Nigga8.timer.hasTimeElapsed((long)-809707454 ^ 0xFFFFFFFFCFBCD848L, true) && Nigga8.time > Float.intBitsToFloat(2.08433728E9f ^ 0x7C3C767F)) {
                    Nigga8.time -= Float.intBitsToFloat(1.08570202E9f ^ 0x7F367F7D);
                }
                if ((Nigga2 = Nigga4.getStringWidth(Nigga3 = Nigga8.name)) < Nigga4.getStringWidth(Nigga8.description) && !Nigga8.type.equals((Object)Notification.notificationType.TOGGLE) && !Nigga8.type.equals((Object)Notification.notificationType.PROFILE)) {
                    Nigga2 = Nigga4.getStringWidth(Nigga8.description);
                }
                if (Nigga2 < 15) {
                    Nigga2 = 15;
                }
                if (ModuleManager.notifications.isEnabled() && Nigga8.animTop > Float.intBitsToFloat(-1.12611686E9f ^ 0x7C40D1FF)) {
                    int Nigga14 = Client.RGBColor;
                    if (Nigga8.type.equals((Object)Notification.notificationType.WARNING)) {
                        Nigga14 = -852221;
                    }
                    if (Nigga8.type.equals((Object)Notification.notificationType.INFO)) {
                        Nigga14 = -15998951;
                    }
                    if (Nigga8.type.equals((Object)Notification.notificationType.PROFILE)) {
                        Nigga14 = -16733978;
                    }
                    Nigga4.drawString(Nigga3, Nigga5.getScaledWidth() - 32 - Nigga2 + Nigga8.anim, (float)(Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 30) - Nigga8.animTop, Nigga14);
                    Nigga4.drawString(Qprot0.0("\u994b\u719c") + Nigga8.description, Nigga5.getScaledWidth() - 28 - Nigga2 + Nigga8.anim, (float)(Nigga5.getScaledHeight() - 5 * Nigga6 * 5 - 21) - Nigga8.animTop, Client.RGBColor);
                }
                Nigga7 = Nigga8;
                ++Nigga6;
            }
        }
    }

    public Notifications() {
        Notifications Nigga;
        Nigga.mc = Minecraft.getMinecraft();
    }
}

