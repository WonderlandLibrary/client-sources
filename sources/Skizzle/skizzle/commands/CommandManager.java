/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import skizzle.commands.Command;
import skizzle.commands.impl.Bind;
import skizzle.commands.impl.FriendCommand;
import skizzle.commands.impl.Help;
import skizzle.commands.impl.Ign;
import skizzle.commands.impl.Info;
import skizzle.commands.impl.Profile;
import skizzle.commands.impl.Say;
import skizzle.commands.impl.Script;
import skizzle.commands.impl.Settings;
import skizzle.commands.impl.Spammer;
import skizzle.commands.impl.Toggle;
import skizzle.commands.impl.test;
import skizzle.events.listeners.EventChat;

public class CommandManager {
    public List<Command> commands = new ArrayList<Command>();
    public String prefix = ".";

    public static {
        throw throwable;
    }

    public void setup() {
        CommandManager Nigga;
        Nigga.commands.add(new Script());
        Nigga.commands.add(new Toggle());
        Nigga.commands.add(new Settings());
        Nigga.commands.add(new Help());
        Nigga.commands.add(new Bind());
        Nigga.commands.add(new Say());
        Nigga.commands.add(new Info());
        Nigga.commands.add(new FriendCommand());
        Nigga.commands.add(new Profile());
        Nigga.commands.add(new Ign());
        Nigga.commands.add(new Spammer());
        Nigga.commands.add(new test());
    }

    public void handleChat(EventChat Nigga) {
        CommandManager Nigga2;
        String Nigga3 = Nigga.getMessage();
        if (!Nigga3.startsWith(Nigga2.prefix)) {
            return;
        }
        if ((Nigga3 = Nigga3.substring(Nigga2.prefix.length())).split(" ").length > 0) {
            String Nigga4 = Nigga3.split(" ")[0];
            boolean Nigga5 = false;
            for (Command Nigga6 : Nigga2.commands) {
                if (!Nigga6.aliases.contains(Nigga4) && !Nigga6.name.equalsIgnoreCase(Nigga4)) continue;
                Nigga6.onCommand(Arrays.copyOfRange(Nigga3.split(" "), 1, Nigga3.split(" ").length), Nigga3);
                Nigga5 = true;
                break;
            }
            if (!Nigga5) {
                Nigga2.showHelpMenu();
            }
        }
    }

    public void showHelpMenu() {
        CommandManager Nigga;
        Minecraft Nigga2 = Minecraft.getMinecraft();
        Nigga2.thePlayer.messagePlayer(Qprot0.0("\u87d3\u71c9\ubcaf\u67c7\u690f\u02a5\u8c26\ueb4b\u9737\uc27a\u8886"));
        Nigga2.thePlayer.messagePlayer(Qprot0.0("\u8754\u718b\ubc28\u678b\u69fb\u02f9\u8c73\ueb0f\u976d\uc23b\u88c3\uaf1e\u058f\ub273\u1d10\u1b18\u42fb\u3176\ud72b\u7631\u7e92\u0182\u9708\u2803\uaed6\u3a53\u2f11\u522a\u826a\ue3ab\u66fa\u881b\u0ae2\u6d09"));
        for (Command Nigga3 : Nigga.commands) {
            Nigga2.thePlayer.messagePlayer(Qprot0.0("\u8754\u718b\ubcaf\u6792") + Nigga3.name + Qprot0.0("\u87d3\u719c\ubc32"));
            Nigga2.thePlayer.messagePlayer(Qprot0.0("\u8754\u718b\ubc28\u678b\u69fb\u02f9\u8c1a\ueb42\u972c\uc271\u8886\uaf56\u05ca\ub2a5\u1d07\u1b5f") + Nigga3.syntax);
            Nigga2.thePlayer.messagePlayer(Qprot0.0("\u8754\u718b\ubc28\u678b\u69fb\u02f9\u8c0b\ueb54\u973e\uc275\u8891\uaf05\u059a\ub276\u1d0c\u1b1e\u42e7\u3129\ud76f\u76ba\u7ed0") + Nigga3.description);
            String Nigga4 = "";
            for (String Nigga5 : Nigga3.aliases) {
                Nigga4 = String.valueOf(Nigga4) + (Nigga4 == "" ? "" : Qprot0.0("\u8758\u718b")) + Nigga5;
            }
            Nigga2.thePlayer.messagePlayer(Qprot0.0("\u8754\u718b\ubc28\u678b\u69fb\u02f9\u8c0e\ueb5d\u9724\uc277\u8890\uaf09\u0599\ub238\u1d45\u1bd6\u42eb") + Nigga4);
            Nigga2.thePlayer.messagePlayer(Qprot0.0("\u8754\u718b\ubcaf\u67c9\u69fb\u02a3\u8c6f\ueb11\u976d\uc236\u88c3\uaf4c\u05ca\ub222\u1d45\u1b51\u42a9\u3133\ud76f\u763d\u7e92\u018a\u9701\u2803\uaedb\u3a53\u2f5e\u527a\u823e\ue3e2"));
        }
    }

    public CommandManager() {
        CommandManager Nigga;
    }
}

