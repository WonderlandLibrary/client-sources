package digital.rbq.module.implement.Command;

import digital.rbq.gui.hud.notification.Notification;
import digital.rbq.gui.hud.notification.NotificationManager;
import digital.rbq.module.Command;

/**
 * Created by John on 2017/01/14.
 */
@Command.Info(name = "fix", syntax = { "" }, help = "Fix sound system quickly.")
public class SoundFixCmd extends Command {
    @Override
    public void execute(String[] args) throws Error {
        mc.getSoundHandler().getSndManager().reloadSoundSystem();
        NotificationManager.show("Sound System", "Reloaded sound system.", Notification.Type.SUCCESS);
    }
}