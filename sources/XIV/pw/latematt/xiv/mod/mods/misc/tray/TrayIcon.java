package pw.latematt.xiv.mod.mods.misc.tray;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.ChatLogger;

import java.awt.*;

/**
 * @author Rederpz
 */
public class TrayIcon extends Mod {
    private XIVTray tray;

    public TrayIcon() {
        super("TrayIcon", ModType.MISCELLANEOUS, Keyboard.KEY_NONE);
    }

    @Override
    public void onEnabled() {
        if (tray == null)
            this.tray = new XIVTray();

        try {
            tray.load();
        } catch (AWTException e) {
            ChatLogger.print("Unable to load tray icon, a stack trace has been printed.");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisabled() {
        if (tray == null)
            this.tray = new XIVTray();

        tray.unload();
    }
}
