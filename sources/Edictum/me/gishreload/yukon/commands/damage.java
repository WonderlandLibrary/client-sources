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

public class damage extends Command{

	
	public double damage = 0.5;
	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "damage";
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
		if (Minecraft.thePlayer != null) {
            int i = 0;
            while ((double)i < 80.0 + 40.0 * (this.damage - 0.5)) {
            	Minecraft.thePlayer.connection.sendPacket((Packet)new CPacketPlayer.Position(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.1D, Minecraft.thePlayer.posZ, false));
            	Minecraft.thePlayer.connection.sendPacket((Packet)new CPacketPlayer.Position(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                ++i;
            }
            Minecraft.thePlayer.connection.sendPacket((Packet)new CPacketPlayer.Position(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
        }
        Edictum.addChatMessage("\u00a74Нанесено 0.5 урона.");
		}
	}
