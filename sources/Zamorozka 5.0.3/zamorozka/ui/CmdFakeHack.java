package zamorozka.ui;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.player.EntityPlayer;
import zamorozka.main.indexer;
import zamorozka.module.ModuleManager;
import zamorozka.modules.EXPLOITS.FakeHack;

public class CmdFakeHack extends Command {

	public CmdFakeHack() {
		super("fakehack");
	}

	@Override
	public void runCommand(String s, String[] commands) {
		try {
			if (commands.length == 1) {
				ChatUtils.printChatprefix("Enter a name!");
				return;
			}
			if (!ModuleManager.getModule(FakeHack.class).getState()) {
				ChatUtils.printChatprefix("Please enable FakeHack module!");
				return;
			}
			String name = commands[1];
			EntityPlayer player = mc.world.getPlayerEntityByName(name);
			if (player == null) {
				ChatUtils.printChatprefix("That player could not be found!");
				return;
			}
			if (FakeHack.isFakeHacker(player)) {
				FakeHack.removeHacker(player);
				ChatUtils.printChatprefix("Removed player " + ChatFormatting.RED + name + ChatFormatting.WHITE + " from hacker list!");
			} else {
				FakeHack.fakeHackers.add(name);
				ChatUtils.printChatprefix("Added player " + ChatFormatting.RED + name + ChatFormatting.WHITE + " as a HACKER!");
			}
		} catch (Exception e) {
			ChatUtils.printChatprefix("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription() {
		return "Adds hacker players!";
	}

	@Override
	public String getSyntax() {
		return "fakehack add <name>, fakehack del <name>";
	}

}
