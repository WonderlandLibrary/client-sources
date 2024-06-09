package me.gishreload.yukon.commands;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.Command;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

public class damage3 extends Command{

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "damage3";
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
		for (int i = 0; i < 80.0; ++i) {
            Wrapper.getInstance().getPlayer().connection.sendPacket(new CPacketEntityAction(Wrapper.getInstance().getPlayer(), CPacketEntityAction.Action.START_FALL_FLYING));
            Minecraft.getMinecraft();
            final NetHandlerPlayClient sendQueue = Minecraft.thePlayer.connection;
            Minecraft.getMinecraft();
            final double posX = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            final double yIn = Minecraft.thePlayer.posY + 0.949;
            Minecraft.getMinecraft();
            sendQueue.sendPacket(new CPacketPlayer.Position(posX, yIn, Minecraft.thePlayer.posZ, false));
            Minecraft.getMinecraft();
            final NetHandlerPlayClient sendQueue2 = Minecraft.thePlayer.connection;
            Minecraft.getMinecraft();
            final double posX2 = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            final double posY = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            sendQueue2.sendPacket(new CPacketPlayer.Position(posX2, posY, Minecraft.thePlayer.posZ, false));
        }
        Minecraft.getMinecraft();
        final NetHandlerPlayClient sendQueue3 = Minecraft.thePlayer.connection;
        Minecraft.getMinecraft();
        final double posX3 = Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        final double posY2 = Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        sendQueue3.sendPacket(new CPacketPlayer.Position(posX3, posY2, Minecraft.thePlayer.posZ, true));
        Wrapper.getInstance().getPlayer().setPosition(Wrapper.getInstance().getPlayer().posX, Wrapper.getInstance().getPlayer().posY + 0.1, Wrapper.getInstance().getPlayer().posZ);
        Edictum.addChatMessage("\u00a74Нанесено 0.5 урона.");
		}
	}
