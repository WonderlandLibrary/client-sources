package win.sightclient.cmd;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import win.sightclient.cmd.commands.BindCommand;
import win.sightclient.cmd.commands.CapeCommand;
import win.sightclient.cmd.commands.FinalMomCommand;
import win.sightclient.cmd.commands.NameCommand;
import win.sightclient.cmd.commands.PlayTimeCommand;
import win.sightclient.cmd.commands.ReloadCommand;
import win.sightclient.cmd.commands.ToggleCommand;
import win.sightclient.cmd.commands.configs.ConfigCommand;
import win.sightclient.cmd.commands.configs.FreeCommand;
import win.sightclient.cmd.commands.configs.PremiumCommand;
import win.sightclient.cmd.commands.exploit.PluginsCommand;
import win.sightclient.event.events.chat.EventChatSend;
import win.sightclient.utils.minecraft.ChatUtils;

public class CommandManager {

	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public CommandManager() {
		this.commands.add(new NameCommand());
		this.commands.add(new ToggleCommand());
		this.commands.add(new BindCommand());
		this.commands.add(new PlayTimeCommand());
		this.commands.add(new ReloadCommand());
		this.commands.add(new ConfigCommand());
		this.commands.add(new FreeCommand());
		this.commands.add(new PremiumCommand());
		this.commands.add(new PluginsCommand());
		this.commands.add(new FinalMomCommand());
		this.commands.add(new CapeCommand());
	}
	
	public void onChat(EventChatSend ec) {
		if (ec.getMessage().startsWith(".")) {
			ec.setCancelled();
			String message = ec.getMessage().substring(1);
			boolean sent = false;
			for (Command c : this.commands) {
				for (String alias : c.alias) {
					if (message.split(" ")[0].equalsIgnoreCase(alias) && !sent) {
						c.onCommand(message);
						sent = true;
					}
				}
			}
			if (!sent) {
				ChatUtils.sendMessage("Sorry that is not a command.");
			}
		}
	}
	
    public static String get() {
        try {
            String s = "";
            final String main = String.valueOf(System.getenv("COMPUTERNAME")) + System.getenv("PROCESSOR_IDENTIFIER") + System.getProperty("user.name").trim();
            final byte[] bytes = main.getBytes("UTF-8");
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            final byte[] md5 = messageDigest.digest(bytes);
            int i = 0;
            byte[] array;
            for (int length = (array = md5).length, j = 0; j < length; ++j) {
                final byte b = array[j];
                s = String.valueOf(s) + Integer.toHexString((b & 0xFF) | 0x300).substring(0, 3);
                if (i != md5.length - 1) {
                    s = String.valueOf(s) + "-";
                }
                ++i;
            }
            return s;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
        return "";
    }
}
