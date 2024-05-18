package me.gishreload.yukon.commands;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.Command;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class damage2 extends Command{
	double offset2 = 0.0625D;
	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "damage2";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (!mc.thePlayer.onGround) {
            for (int i3 = 0; (double) i3 <= (3.0D + 0.5) / offset2; ++i3) {
                mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX,
                        mc.thePlayer.posY + offset2, mc.thePlayer.posZ, false));
                mc.thePlayer.connection.sendPacket(new CPacketPlayer.Position(mc.thePlayer.posX, mc.thePlayer.posY,
                        mc.thePlayer.posZ, (double) i3 == (3.0D + 0.5D) / offset2));
            }
        }
}
}