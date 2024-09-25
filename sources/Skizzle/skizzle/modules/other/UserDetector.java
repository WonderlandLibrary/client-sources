/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;
import skizzle.users.ServerManager;
import skizzle.util.Timer;

public class UserDetector
extends Module {
    public NumberSetting delay;
    public Timer timer = new Timer();

    public UserDetector() {
        super(Qprot0.0("\ued77\u71d8\ud633\ua7f6\uccb5\u68fd\u8c3b\u810a\u5701\u67cf\ue2da\uaf1e"), 0, Module.Category.OTHER);
        UserDetector Nigga;
        Nigga.delay = new NumberSetting(Qprot0.0("\ued66\u71ce\ud63a\ua7e5\ucc88\u68b8\u8c67\u811c\u5707\u67d8\ue2da\uaf02\u6fd0\u725e\ub8e1"), 20.0, 5.0, 600.0, 1.0);
        Nigga.addSettings(Nigga.delay);
        Nigga.onDisable();
    }

    @Override
    public void onEvent(Event Nigga) {
        UserDetector Nigga2;
        if (Nigga instanceof EventUpdate && ServerManager.resetTimer.hasTimeElapsed((long)(Nigga2.delay.getValue() * 1000.0), true)) {
            try {
                new Thread(UserDetector::lambda$0).start();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static void lambda$0() {
        Client.serverUsers = null;
        Client.serverUsers = ServerManager.getUsersFromServer();
        Client.skizzleUsers.clear();
        if (Client.serverUsers != null) {
            String[] arrstring = Client.serverUsers;
            int n = Client.serverUsers.length;
            for (int i = 0; i < n; ++i) {
                String Nigga = arrstring[i];
                if (Nigga.equals(Qprot0.0("\ued77\u71f8\ud613\u2737\u4c83\u68b4"))) continue;
                Nigga = Nigga.replace(",", "");
                Client.skizzleUsers.add(Nigga);
            }
        }
    }

    public static {
        throw throwable;
    }
}

