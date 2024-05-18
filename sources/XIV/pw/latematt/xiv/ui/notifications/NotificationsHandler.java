package pw.latematt.xiv.ui.notifications;

import net.minecraft.client.Minecraft;
import pw.latematt.xiv.management.ListManager;
import pw.latematt.xiv.utils.RenderUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Matthew
 */
public class NotificationsHandler extends ListManager<Notification> {
    private final Minecraft mc = Minecraft.getMinecraft();

    public NotificationsHandler() {
        super(new CopyOnWriteArrayList<>());
    }

    public void draw(int x, int y) {
        for (Notification notification : contents) {
            int length = mc.fontRendererObj.getStringWidth(notification.getMessage());
            if (notification.isImportant())
                RenderUtils.drawBorderedRect(x, y, x + 2 + length + 2, y + 10, 0xFF9A2A27, 0xAA000000);
            else
                RenderUtils.drawBorderedRect(x, y, x + 2 + length + 2, y + 10, 0x80000000, 0x60000000);
            mc.fontRendererObj.drawStringWithShadow(notification.getMessage(), x + 2, y + 1, 0xFFFFFFFF);
            y += 12;
        }
    }

    public void tick() {
        contents.stream().filter(notification -> notification.getTimer().hasReached(5000)).forEach(notification -> contents.remove(notification));
    }
}
