package me.finz0.osiris.module.modules.misc;

import me.finz0.osiris.module.Module;

import java.awt.*;

public class Notifications extends Module {
    public Notifications() {
        super("Notifications", Category.MISC, "Desktop notifications");
    }

    public static void sendNotification(String message, TrayIcon.MessageType messageType){
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon icon = new TrayIcon(image, "Osiris");
        icon.setImageAutoSize(true);
        icon.setToolTip("Osiris");
        try { tray.add(icon); }
        catch (AWTException e) { e.printStackTrace(); }
        icon.displayMessage("Osiris", message, messageType);
    }

}
