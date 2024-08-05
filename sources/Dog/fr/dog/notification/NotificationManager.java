package fr.dog.notification;

import fr.dog.notification.impl.Notification;
import fr.dog.notification.impl.NotificationMode;
import fr.dog.notification.impl.NotificationType;
import fr.dog.util.InstanceAccess;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

public class NotificationManager implements InstanceAccess {
    private static final ArrayList<Notification> notifications = new ArrayList<>();
    private static String notifMode = "Dog";
    public static void update(String mode){
        notifMode = mode;
        draw();
    }
    private static void draw(){
        ScaledResolution sr = new ScaledResolution(mc);

        float offset = 0;
        for (int i = notifications.size() - 1; i >= 0; i--){
            Notification notification = notifications.get(i);
            notification.draw(sr.getScaledWidth(), sr.getScaledHeight() - notification.height + offset);


            offset-= (float) ((notification.height + 5)*notification.popIn.getValue());
            if (notification.shouldDelete()){
                notifications.remove(i);
            }
        }
    }

    public static void addNotification(String message, String subString, NotificationType type) {
        switch (notifMode){
            case "Flux":
                notifications.add(new Notification(message, subString, type, NotificationMode.FLUX));
                break;
            case "Dog":
                notifications.add(new Notification(message, subString, type, NotificationMode.DOG));
                break;
        }
    }
}
