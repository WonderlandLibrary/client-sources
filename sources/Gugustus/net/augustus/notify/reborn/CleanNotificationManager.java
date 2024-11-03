package net.augustus.notify.reborn;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.Iterator;

public class CleanNotificationManager {
    public static ArrayList<CleanNotification> notifications = new ArrayList<CleanNotification>();

    public static ArrayList<CleanNotification> getCleanNotifications() {
        return notifications;
    }

    public static void addCleanNotification(CleanNotification n) {
        notifications.add(n);
    }

    public static void renderCleanNotifications() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float y = sr.getScaledHeight() - 30;
        for (CleanNotification notification : notifications) {
            notification.render(y);
            y -= 25;
        }

    }

    public static void update() {
        ArrayList<CleanNotification> temp = new ArrayList<>();

        for (CleanNotification no : notifications){
            temp.add(no);
            no.update();
//            System.out.println(no.timer.delay(no.lastTime * 1000) + " " );
            if(no.timer != null) {
                if (no.timer.reached((long) (no.lastTime * 1000))) {
                    no.setBack = true;
//                    no.timer.reset();
                    if(no.x <= 0.1f) {
                        temp.remove(no);
                    }
                }
            }


        }
        notifications.clear();
        for (CleanNotification no : temp) {
            notifications.add(no);
        }
    }

    public static void shader() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        for (CleanNotification notification : notifications) {
            notification.shader();
        }

    }
}