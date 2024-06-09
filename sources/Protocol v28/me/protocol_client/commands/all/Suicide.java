package me.protocol_client.commands.all;

import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Suicide extends Command {

	@Override
	public String getAlias() {
		return "Suicide";
	}

	@Override
	public String getDescription() {
		return "Become your hero, Amanda Todd.";
	}

	@Override
	public String getSyntax() {
		return ".suicide";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		for (int i = 0; i < 860; i++) {
			Wrapper.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.049, Wrapper.getPlayer().posZ, false));
			Wrapper.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));
		}
		Wrapper.getPlayer().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, true));
	}

}
