package client.metaware.api.gui.notis;

import client.metaware.Metaware;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.util.StencilUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class NotificationRenderer {

    private static final int RED = new Color(255, 80, 80).getRGB();
    private static final int GREEN = new Color(135, 227, 49).getRGB();
    private static final int ORANGE = new Color(255, 215, 100).getRGB();
    private static final int WHITE = new Color(255, 255, 255).getRGB();

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final NotificationManager notificationManager = Metaware.INSTANCE.getNotificationManager();

    private static final int displayHeight = 0;
    private static final int displayWidth = 0;

    public static void draw(ScaledResolution resolution) {
        if (!notificationManager.getNotifications().isEmpty()) {
            notificationManager.update();
        }

        GL11.glPushMatrix();
        int color = 0;
        for(Notification notification1 : notificationManager.getNotifications()){
            double x = notification1.getX();
            double y = resolution.getScaledHeight() - notification1.getY();
            if(notification1.getNotificationType() != NotificationType.INFO) RenderUtil.drawRoundedRect2((float)(resolution.getScaledWidth() - x - (notification1.getNotificationType() != NotificationType.ERROR ? 2 : 0)), (float) y, resolution.getScaledWidth(), (float)y + 24, 4, new Color(getColorForType(notification1.getNotificationType())).darker().getRGB());
        }
        StencilUtil.initStencilToWrite();
        for (Notification notification : notificationManager.getNotifications()) {
            double x = notification.getX();
            double y = (resolution.getScaledHeight() - notification.getY());
            RenderUtil.drawRoundedRect2((float)(resolution.getScaledWidth() - x - (notification.getNotificationType() != NotificationType.ERROR ? 2 : 0)), (float) y, resolution.getScaledWidth(), (float)y + 24, 4, new Color(0, 0, 0, 155).getRGB());
        }
        StencilUtil.readStencilBuffer(1);
        Metaware.INSTANCE.getBlurShader(15).blur();
        StencilUtil.uninitStencilBuffer();
        GL11.glPopMatrix();

        for (Notification notification : notificationManager.getNotifications()) {

            double x = notification.getX();
            double y = (resolution.getScaledHeight() - notification.getY());
            
            String callReason = notification.getCallReason() == null ? StringUtils.capitalize(notification.getType().toString()) :
                    notification.getCallReason();
            String message = notification.getMessage();
            String seconds = String.valueOf((notification.getDelay() - notification.getCount()) / 1000.0),
                    formatted = "(" + seconds.substring(0, seconds.indexOf(".") + 2) + "s) ";

           // RenderUtil.drawRoundedRect2((float)(resolution.getScaledWidth() - x - (notification.getNotificationType() != NotificationType.ERROR ? 2 : 0)), (float) y, resolution.getScaledWidth(), (float)y + 24, 4, new Color(0, 0, 0, 155).getRGB());

//            Gui.drawRect(
//                    resolution.getScaledWidth() - x - (notification.getNotificationType() == NotificationType.WARNING || notification.getNotificationType() == NotificationType.SUCCESS || notification.getNotificationType() == NotificationType.INFO ? 2 : 0),
//                    y,
//                    resolution.getScaledWidth(),
//                    y + 24,
//                    new Color(0, 0, 0,110).getRGB());

            CustomFontRenderer bigFont = Metaware.INSTANCE.getFontManager().currentFont().size(16);

            CustomFontRenderer smallFonto = Metaware.INSTANCE.getFontManager().currentFont().size(14);

            bigFont.drawStringWithShadow(callReason, (float) (resolution.getScaledWidth() - (float)x + 25),(float)y + 2,0xffffffff);
            smallFonto.drawStringWithShadow(message + " " + formatted, (float) (resolution.getScaledWidth() - (float)x + 25),(float)y + 12.5F,new Color(200,200,200,255).getRGB());

            CustomFontRenderer fontRenderer = Metaware.INSTANCE.getFontManager().testFont().size(50);
            
            switch (notification.getType()) {
                case ERROR:
                    fontRenderer.drawString("I", (float) (resolution.getScaledWidth() - x + 1.5), (float) y, RED);
                    break;
                case WARNING:
                    fontRenderer.drawString("J",(float) (resolution.getScaledWidth() - x - 0.5f), (float) y, ORANGE);
                    break;
                case SUCCESS:
                    fontRenderer.drawString("H",(float) (resolution.getScaledWidth() - x - 1), (float) y, GREEN);
                    break;
                case INFO:
                    fontRenderer.drawString("K",(int) (resolution.getScaledWidth() - x - 0.5f), (float) y, WHITE);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + notification.getType());
            }
            
            double width = notification.getX();
            double w1 = width/100;
            double a = notification.getDelay()/100;
            double perc = (float) (notification.getCount()/a);

            //RenderUtil.drawRoundedRect2((float)(resolution.getScaledWidth() - x - (notification.getNotificationType() != NotificationType.ERROR ? 2 : 0)), (float) y + 23, resolution.getScaledWidth(), (float)y + 24, 12, getColorForType(notification.getNotificationType()));


            Gui.drawRect(
                    resolution.getScaledWidth() - x -
                            (notification.getNotificationType() != NotificationType.ERROR ? 2 : 0),
                    y + 23,
                    resolution.getScaledWidth() - x + (perc * w1),
                    y + 24,
                    new Color(getColorForType(notification.getNotificationType())).brighter().getRGB());
        }
    }


    private static int getColorForType(NotificationType type) {
        switch (type) {
            case SUCCESS:
                return GREEN;
            case ERROR:
                return RED;
            case WARNING:
                return ORANGE;
            case INFO:
                return WHITE;
        }

        return 0;
    }
}
