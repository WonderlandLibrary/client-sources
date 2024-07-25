package club.bluezenith.ui.notifications;

import club.bluezenith.module.modules.render.hud.HUD;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.Minecraft.getMinecraft;

public class NotificationPublisher {
    private final List<Notification> notifications = new ArrayList<>();

    public void render() {
        if(this.notifications.isEmpty()) return;

        final ScaledResolution resolution = new ScaledResolution(getMinecraft());

        final boolean centered = HUD.module.notificationOptions.getOptionState("Centered") && getMinecraft().thePlayer != null;
        final float yMargin = 5;
        float height = centered ? resolution.getScaledHeight() / 2F + notifications.get(0).getHeight() / 2F : resolution.getScaledHeight() - 20;

        final List<Notification> copiedList = new ArrayList<>(this.notifications); //copy list to prevent concurrentmodificationexception

        for (final Notification notification : copiedList) { if(notification == null) continue;
            notification.setSpeed(centered ? 0.21F : 0.15F)
                    .doBlur(HUD.module.notificationOptions.getOptionState("Blur"))
                    .moveTo(centered ? resolution.getScaledWidth() / 2F + notification.getWidth() / 2F : resolution.getScaledWidth(), height)
                    .performAnimations();

            final boolean isInScreen = height > 0;

            if(isInScreen) {
                notification.render(resolution.getScaledWidth(), resolution.getScaledHeight());
            }

            if(notification.shouldBeRemoved())
                this.notifications.remove(notification);

            if(!notification.shouldBeRemoved())
                height -= (notification.getHeight() + yMargin) * (centered ? -1 : 1); //if "centered" make notifs stack from up to bottom

            if(centered && height > resolution.getScaledHeight())
                height = 0;
        }
    }

    public void post(Notification toPost) {
        final Notification stacked = this.notifications.stream().filter(notification -> notification.equals(toPost)).findFirst().orElse(null);
        if(stacked != null && !stacked.hasTimeExpired()) { //there is already a similar notification
            stacked.stack(); //instead of posting a new one, add a (x) suffix to the title of the original notification
            return;
        }
        this.notifications.add(toPost);
    }

    public Notification post(String title, String description, NotificationType type, long livingTime) {
        Notification notification;
        post(notification = new Notification(title, description, type, livingTime));
        return notification;
    }

    public Notification postError(String title, String description, long livingTime) {
        return post(title, description, NotificationType.ERROR, livingTime);
    }

    public Notification postInfo(String title, String description, long livingTime) {
        return post(title, description, NotificationType.INFO, livingTime);
    }

    public Notification postWarning(String title, String description, long livingTime) {
        return post(title, description, NotificationType.WARNING, livingTime);
    }

    public Notification postSuccess(String title, String description, long livingTime) {
        return post(title, description, NotificationType.SUCCESS, livingTime);
    }
}
