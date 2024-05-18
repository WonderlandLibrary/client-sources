package me.protocol_client.commands.all;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.protocol_client.commands.Command;

public class Damage extends Command {

	@Override
	public String getAlias() {
		return "Damage";
	}

	@Override
	public String getDescription() {
		return "Damage yourself";
	}

	@Override
	public String getSyntax() {
		return ".damage";
	}

	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		for (int i = 0; i < 80.0 + 40.0 * (0.5 - 0.5); ++i) {
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.049, Minecraft.getMinecraft().thePlayer.posZ, false));
			Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
		}
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
	}
}
