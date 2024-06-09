package exhibition.management.command;

import exhibition.management.command.impl.Bind;
import exhibition.management.command.impl.Clear;
import exhibition.management.command.impl.ColorCommand;
import exhibition.management.command.impl.Config;
import exhibition.management.command.impl.Damage;
import exhibition.management.command.impl.Friend;
import exhibition.management.command.impl.Help;
import exhibition.management.command.impl.HelpIAmmUsingABlatantClientAndNeedToPanic;
import exhibition.management.command.impl.Hide;
import exhibition.management.command.impl.Insult;
import exhibition.management.command.impl.KopyKatt;
import exhibition.management.command.impl.LoadConfig;
import exhibition.management.command.impl.NotificationTest;
import exhibition.management.command.impl.PhaseMode;
import exhibition.management.command.impl.Save;
import exhibition.management.command.impl.Say;
import exhibition.management.command.impl.Settings;
import exhibition.management.command.impl.SpotifyCommand;
import exhibition.management.command.impl.Swap;
import exhibition.management.command.impl.Target;
import exhibition.management.command.impl.Toggle;
import exhibition.management.command.impl.VClip;
import exhibition.management.command.impl.Waypoint;
import java.util.Collection;
import java.util.HashMap;

public class CommandManager {
   public static final HashMap commandMap = new HashMap();

   public void addCommand(String name, Command command) {
      commandMap.put(name, command);
   }

   public Collection getCommands() {
      return commandMap.values();
   }

   public Command getCommand(String name) {
      return (Command)commandMap.get(name.toLowerCase());
   }

   public void setup() {
      (new SpotifyCommand(new String[]{"spotify", "player"}, "Controls spotify shit.")).register(this);
      (new ColorCommand(new String[]{"color", "c"}, "OkColors")).register(this);
      (new Save(new String[]{"s", "save"}, "Save config")).register(this);
      (new Insult(new String[]{"insult", "i"}, "Insult those faggot nodus users.")).register(this);
      (new Damage(new String[]{"d", "dmg", "kys", "suicide", "amandatodd"}, "Kill yourself u worthless minecraft bloachxD!")).register(this);
      (new LoadConfig(new String[]{"l", "load"}, "Loads config")).register(this);
      (new Toggle(new String[]{"toggle", "t"}, "Toggles the module.")).register(this);
      (new Settings(new String[]{"Setting", "set", "s"}, "Changing and listing settings for modules.")).register(this);
      (new Help(new String[]{"Help", "halp", "h"}, "Help for commands.")).register(this);
      (new Say(new String[]{"Say", "talk", "chat"}, "Send a message with your chat prefix.")).register(this);
      (new Bind(new String[]{"Bind", "key", "b"}, "Send a message with your chat prefix.")).register(this);
      (new Friend(new String[]{"friend", "fr", "f"}, "Add and remove friends.")).register(this);
      (new Target(new String[]{"tar", "focus", "target", "vip"}, "Set the target for the Aura and AutoIon.")).register(this);
      (new Swap(new String[]{"sw", "swap"}, "Swaps the set hotbars with the item above the selected hotbar number.")).register(this);
      (new Clear(new String[]{"clear", "cl", "clr"}, "Clears chat for you.")).register(this);
      (new KopyKatt(new String[]{"cc", "copycat", "kk"}, "Copy the players messages to insult/mock them.")).register(this);
      (new NotificationTest(new String[]{"test", "nt"}, "Notifications test.")).register(this);
      (new PhaseMode(new String[]{"phase", "phasemode", "pm"}, "Changes Phase modes.")).register(this);
      (new VClip(new String[]{"vclip", "vc", "clip"}, "Clips you vertically.")).register(this);
      (new Waypoint(new String[]{"waypoint", "wp", "marker"}, "Waypoint command.")).register(this);
      (new Config(new String[]{"config", "configs", "cfg", "cfgs"}, "Config management.")).register(this);
      (new Hide(new String[]{"hide", "hidden", "visible", "vis", "hi"}, "Hide/Show modules.")).register(this);
      (new HelpIAmmUsingABlatantClientAndNeedToPanic(new String[]{"panic", "imgettingscreenshared", "panick", "codered"}, "Does what a GHOST client does?")).register(this);
   }
}
