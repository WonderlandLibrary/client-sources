package me.protocol_client.commands.all;

import me.protocol_client.Wrapper;
import me.protocol_client.commands.Command;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Forward extends Command {

	@Override
	public String getAlias() {
		return "Forward";
	}

	@Override
	public String getDescription() {
		return "Teleport forward";
	}

	@Override
	public String getSyntax() {
		return ".forward";
	}
	private boolean speedTick;
	double forward;
	@Override
	public void onCommandSent(String command, String[] args) throws Exception {
		EntityPlayerSP entity = Wrapper.getPlayer();
		double xDir = Math.cos(Math.toRadians(entity.rotationYaw + 90.0F));
		double zDir = Math.sin(Math.toRadians(entity.rotationYaw + 90.0F));
		Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX + xDir * 0.2, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ + zDir * 0.2, Wrapper.getPlayer().onGround));
		for (int i = 0; i < 13; i++) {
			speedTick = !speedTick;
			if (this.speedTick)
				forward += 0.5;
			else forward += 1;

			if (this.speedTick)
				Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX + (xDir * forward), Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ + (zDir * forward), Wrapper.getPlayer().onGround));
			else Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX + (xDir * forward), Wrapper.getPlayer().posY + 0.41999998688698, Wrapper.getPlayer().posZ + (zDir * forward), Wrapper.getPlayer().onGround));
		}
		Wrapper.tellPlayer("§7Attempting teleport!");
	}

}
