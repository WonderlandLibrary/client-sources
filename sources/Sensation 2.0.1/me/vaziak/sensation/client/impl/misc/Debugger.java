package me.vaziak.sensation.client.impl.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Debugger extends Module {
	public int resources;
	public int payloads;
	public int completes;
	public int abilities;
	public int statistics;
	public int keepalives;
	private StringsProperty packets = new StringsProperty("Packets", "Which incomming packets do you want to debug?",
			true, false,
			new String[] { "Resource Pack Request/Send", "Custom Payload", "Tab Complete", "Player Ability",
					"Statistic Request",
					"Keep Alive(s)" });
	private StringsProperty client = new StringsProperty("Dev Code", "Which developer work do you want debugged - Really only for the client devs, designed for simplicity but can be usefull for error reporting etc",
			true, false,
			new String[] { "Modules", "Base", "MC code", "Renders"});

	public Debugger() {
		super("Debugger", Category.MISC);
		registerValue(packets, client);
	}
	
	@Collect
	public void onEvent(ProcessPacketEvent event) {
		 switch (packets.getSelectedStrings().get(0)) {
		 		case "Resource Pack Request/Send":
		 			if (event.getPacket() instanceof S48PacketResourcePackSend) {
		 				resources++;
		 				S48PacketResourcePackSend packet = (S48PacketResourcePackSend)event.getPacket();
		 				seperate();
		 				packet("Resource Pack Request/Send", resources);
		 				debug("URL: "+ packet.getURL(), TYPE.MC);
		 				debug("Hash: "+ packet.getHash(), TYPE.MC);
		 				debug("String: "+ packet.toString(), TYPE.MC);
		 				seperate();
		 			}
		 		break;
		 		case "Custom Payload":
		 			if (event.getPacket() instanceof S48PacketResourcePackSend) {
			 			payloads++;
		 				S3FPacketCustomPayload packet = (S3FPacketCustomPayload)event.getPacket();
		 				seperate();
		 				packet("Custom Payload", payloads);
		 				debug("Channcel Name: "+ packet.getChannelName(), TYPE.MC);
		 				debug("Buffer Data: "+ packet.getBufferData().readStringFromBuffer(255), TYPE.MC);
		 				debug("String: "+ packet.toString(), TYPE.MC);
		 				seperate();
		 			}
		 		break;
		 		case "Tab Complete":
		 			if (event.getPacket() instanceof S3APacketTabComplete) {
			 			completes++;
		 				S3APacketTabComplete packet = (S3APacketTabComplete)event.getPacket();
		 				seperate();

		 				packet("Tab Complete", completes);
		 				debug("Mathces: "+ packet.func_149630_c(), TYPE.MC);
		 				debug("String: "+ packet.toString(), TYPE.MC);
		 				seperate();
		 			}
		 		break;
		 		case "Player Ability":
		 			if (event.getPacket() instanceof S39PacketPlayerAbilities) {
			 			abilities++;
		 				S39PacketPlayerAbilities packet = (S39PacketPlayerAbilities)event.getPacket();
		 				seperate();
		 				packet("Player Ability", abilities);
		 				debug("Fly Speed: "+ packet.getFlySpeed(), TYPE.MC);
		 				debug("Walk Speed: "+ packet.getWalkSpeed(), TYPE.MC);
		 				debug("Allowed flight: "+ packet.isAllowFlying(), TYPE.MC);
		 				debug("Creative mode: "+ packet.isCreativeMode(), TYPE.MC);
		 				debug("Is flying: "+ packet.isFlying(), TYPE.MC);
		 				debug("Is vaulnerable: "+ packet.isInvulnerable(), TYPE.MC);
		 				debug("String: "+ packet.toString(), TYPE.MC);
		 				seperate();
		 			}
		 		break;
		 		case "Statistic Request":

		 			if (event.getPacket() instanceof S39PacketPlayerAbilities) {
		 				statistics++;
		 				seperate();
		 				packet("Statistic", statistics);
		 				debug("Received statistic request", TYPE.MC);
		 				seperate();
		 			}
		 		break;
		 		case "Keep Alive(s)":
		 			if (event.getPacket() instanceof S00PacketKeepAlive) {
		 				keepalives++;
		 				S00PacketKeepAlive packet = (S00PacketKeepAlive)event.getPacket();
		 				seperate();
		 				packet("Keep Alive", keepalives);
		 				debug("Key: " + packet.func_149134_c(), TYPE.MC);
		 				seperate();
		 			}
		 		break;
		 		
		 }
	}
	public void packet(String packet, int times) {
		mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + String.valueOf(times) + EnumChatFormatting.WHITE + "x " + EnumChatFormatting.GREEN + packet + EnumChatFormatting.WHITE + ": "));
	}	
	public void seperate() {
		mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "------------------------------------"));
	}
	
	public void debug(String strang, TYPE type) {

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());
        String pattern2 = "HH:mm:ss.SSSZ";
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern);

        String time = simpleDateFormat.format(new Date());
		switch (type) {
		case BASE:
			mc.thePlayer.sendChatMessage(EnumChatFormatting.WHITE + "[" + EnumChatFormatting.RED + "Sensation" + EnumChatFormatting.WHITE + "]" + EnumChatFormatting.GRAY + "{" + EnumChatFormatting.RED + "DEBUG" + EnumChatFormatting.GRAY + "}" + strang + " |" + date + " |" + time);
			break;
		case MC:
			mc.thePlayer.sendChatMessage(EnumChatFormatting.WHITE + "[" + EnumChatFormatting.DARK_RED + "Sensation" + EnumChatFormatting.WHITE + "]" + EnumChatFormatting.GRAY + "{" + EnumChatFormatting.RED + "DEBUG" + EnumChatFormatting.GRAY + "}" + strang);
			
			break;
		case MOD:
			mc.thePlayer.sendChatMessage(EnumChatFormatting.WHITE + "[" + EnumChatFormatting.GOLD + "Sensation" + EnumChatFormatting.WHITE + "]" + EnumChatFormatting.GRAY + "{" + EnumChatFormatting.RED + "DEBUG" + EnumChatFormatting.GRAY + "}" + strang);
			
			break;
		case RENDER:
			mc.thePlayer.sendChatMessage(EnumChatFormatting.WHITE + "[" + EnumChatFormatting.GREEN + "Sensation" + EnumChatFormatting.WHITE + "]" + EnumChatFormatting.GRAY + "{" + EnumChatFormatting.RED + "DEBUG" + EnumChatFormatting.GRAY + "}" + strang + " |" + date + " |" + time);
			
			break;
		default:
			break;
			
		}
	}
	
	public enum TYPE {
		
	    MOD, BASE, MC, RENDER;

	}
}
