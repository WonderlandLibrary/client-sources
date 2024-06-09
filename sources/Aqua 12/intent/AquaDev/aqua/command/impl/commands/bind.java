// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command.impl.commands;

import intent.AquaDev.aqua.modules.Module;
import org.lwjgl.input.Keyboard;
import intent.AquaDev.aqua.notifications.NotificationManager;
import intent.AquaDev.aqua.notifications.Notification;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.command.Command;

public class bind extends Command
{
    public bind() {
        super("bind");
    }
    
    @Override
    public void execute(final String[] args) {
        try {
            final Module mod = Aqua.moduleManager.getModuleByName(args[0]);
            if (args.length != 2) {
                return;
            }
            final String key = args[1];
            Aqua.INSTANCE.fileUtil.saveKeys();
            Aqua.INSTANCE.fileUtil.saveModules();
            NotificationManager.addNotificationToQueue(new Notification("Config", "bound " + mod.getName() + " to " + args[1], 1000L, Notification.NotificationType.INFO));
            mod.setKeyBind(Keyboard.getKeyIndex(args[1].toUpperCase()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
