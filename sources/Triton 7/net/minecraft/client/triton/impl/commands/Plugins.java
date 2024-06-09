package net.minecraft.client.triton.impl.commands;

import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.event.EventManager;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.PacketReceiveEvent;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;

@Com(names = { "pl","plugin","plugins","pluginfinder" })
public class Plugins extends Command {

	Timer timer = new Timer();

	@EventTarget
	public void onReceivePacket(PacketReceiveEvent event) {
		if (event.getPacket() instanceof S3APacketTabComplete) {
			S3APacketTabComplete packet = (S3APacketTabComplete) event.getPacket();
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
						message += "ยง8, ยง7" + pluginName;
					}
				}
			}
			if (!message.isEmpty()) {
				ClientUtils.sendMessage("Plugins (" + size + "): ง7" + message + "ง8.");
			} else {
				ClientUtils.sendMessage("Plugins: None Found!");
			}
			event.setCancelled(true);
			EventManager.unregister(this);
		}
		if (this.timer.delay(20000)) {
			EventManager.unregister(this);
			ClientUtils.sendMessage("Stopped listening for an S3APacketTabComplete! Took too long (20s)!");
		}
	}

	@Override
	public void runCommand(String[] p0) throws Error {
		this.timer.reset();
		ClientUtils.packet(new C14PacketTabComplete("/"));
		ClientUtils.sendMessage("Listening for a S3APacketTabComplete for 20s!");
	}

}
