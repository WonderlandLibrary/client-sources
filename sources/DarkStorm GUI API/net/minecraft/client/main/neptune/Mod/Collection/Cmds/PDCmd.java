package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Events.EventPacketTake;
import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;
import net.minecraft.client.main.neptune.Utils.NetUtils;
import net.minecraft.client.main.neptune.Utils.TimeMeme;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;

@Info(name = "pd", syntax = {}, help = "Tries to find server plugins")
public class PDCmd extends Cmd {

	TimeMeme timer = new TimeMeme();

	@Memetarget
	public void onReceivePacket(EventPacketTake event) {
		if (event.packet instanceof S3APacketTabComplete) {
			S3APacketTabComplete packet = (S3APacketTabComplete) event.packet;
			String[] commands = packet.func_149630_c();
			String message = "";
			int size = 0;
			String[] array;
			for (int length = (array = commands).length, i = 0; i < length; ++i) {
				final String command = array[i];
				final String pluginName = command.split(":")[0].substring(1);
				if (!message.contains(pluginName) && command.contains(":") && !pluginName.equalsIgnoreCase("minecraft")
						&& !pluginName.equalsIgnoreCase("bukkit")) {
					++size;
					if (message.isEmpty()) {
						message += pluginName;
					} else {
						message += "§8, §7" + pluginName;
					}
				}
			}
			if (!message.isEmpty()) {
				ChatUtils.sendMessageToPlayer("Plugins (" + size + "): §7" + message + "§8.");
			} else {
				ChatUtils.sendMessageToPlayer("Plugins: None Found!");
			}
			event.setCancelled(true);
			Memeager.unregister(this);
		}
		if (this.timer.hasPassed(20000)) {
			Memeager.unregister(this);
			ChatUtils.sendMessageToPlayer("Stopped listening for an S3APacketTabComplete! Took too long (20s)!");
		}
	}

	@Override
	public void execute(String[] p0) throws Error {
		this.timer.reset();
		Memeager.register(this);
		NetUtils.sendPacket(new C14PacketTabComplete("/"));
		ChatUtils.sendMessageToPlayer("Listening for a S3APacketTabComplete for 20s!");
	}

}
