package Focus.Beta.API.GUI.notifications;

import Focus.Beta.Client;
import Focus.Beta.IMPL.font.FontLoaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.Iterator;

public final class NotificationRenderer {
    private static final int RED = (new Color(255, 80, 80)).getRGB();
    private static final int GREEN = (new Color(135, 227, 49)).getRGB();
    private static final int ORANGE = (new Color(255, 215, 100)).getRGB();
    private static final int WHITE = (new Color(255, 255, 255)).getRGB();
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final NotificationManager notificationManager = Client.instance.getNotificationManager();
    private static int displayHeight = 0;
    private static int displayWidth = 0;

    public static void render(ScaledResolution sr){
        if(!notificationManager.getNOTIFICATIONS().isEmpty()){
            notificationManager.launch();
        }

        Iterator manager = notificationManager.getNOTIFICATIONS().iterator();
        if(manager.hasNext()){
            Notification notification = (Notification) manager.next();

            double X = notification.getX();
            double Y = sr.getScaledHeight() - notification.getY();
            String CallReason = notification.getCallReason() == null? StringUtils.capitalize(notification.getType().toString()):notification.getCallReason();
            String Message = notification.getMessage();
            String Delay = String.valueOf(((double)notification.getDelay() - notification.getCount()) / 1000.0D);
            String Delay2 = "(" + Delay.substring(0, Delay.indexOf(".") + 2) + "s) ";
            Gui.drawRect((float)(sr.getScaledWidth() - X - 2.0D), (float)(Y - 3.0D), sr.getScaledWidth(), (float)(Y + 24.0D), (new Color(0, 0, 0, 150)).getRGB());

            FontLoaders.arial22.drawString(CallReason, (double)((float)sr.getScaledWidth() - (float)X + 25.0F), (double)((float)Y), -1, true);
        }
    }
}
