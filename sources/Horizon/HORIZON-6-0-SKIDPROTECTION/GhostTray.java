package HORIZON-6-0-SKIDPROTECTION;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.HeadlessException;
import javax.swing.Icon;
import java.awt.Component;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import javax.swing.plaf.metal.MetalIconFactory;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;

public class GhostTray
{
    public static TrayIcon HorizonCode_Horizon_È;
    
    static {
        GhostTray.HorizonCode_Horizon_È = new TrayIcon(Â(), "Horizon Client: GhostMode Notificator", Ý());
    }
    
    public static void HorizonCode_Horizon_È() throws Exception {
        SystemTray.getSystemTray().add(GhostTray.HorizonCode_Horizon_È);
        Thread.sleep(3000L);
    }
    
    private static Image Â() throws HeadlessException {
        final Icon defaultIcon = MetalIconFactory.getTreeHardDriveIcon();
        final Image img = new BufferedImage(defaultIcon.getIconWidth(), defaultIcon.getIconHeight(), 6);
        defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);
        return img;
    }
    
    private static PopupMenu Ý() throws HeadlessException {
        final PopupMenu menu = new PopupMenu();
        final MenuItem exit = new MenuItem("Exit Game");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(exit);
        return menu;
    }
}
