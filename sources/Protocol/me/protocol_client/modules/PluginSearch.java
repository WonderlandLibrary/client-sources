package me.protocol_client.modules;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketRecieve;

public class PluginSearch extends Module{

	public PluginSearch() {
		super("Plugins", "plugins", 0, Category.OTHER, new String[]{"Plugin", "pl", "plugins"});
	}
	 @EventTarget
	  public void onReceive(EventPacketRecieve packet)
	  {
	    if ((packet.getPacket() instanceof S3APacketTabComplete))
	    {
	      S3APacketTabComplete packetTabComplete = (S3APacketTabComplete)packet.getPacket();
	      String[] commands = packetTabComplete.func_149630_c();
	      String message = "";
	      int size = 0;
	      String[] var7 = commands;
	      int var8 = commands.length;
	      for (int var9 = 0; var9 < var8; var9++)
	      {
	        String command = var7[var9];
	        String pluginName = command.split(":")[0].substring(1);
	        if ((!message.contains(pluginName)) && (command.contains(":")) && (!pluginName.equalsIgnoreCase("minecraft")) && (!pluginName.equalsIgnoreCase("bukkit")))
	        {
	          size++;
	          if (message.isEmpty()) {
	            message = message + pluginName;
	          } else {
	            message = message + EnumChatFormatting.GRAY + ", " + EnumChatFormatting.GREEN + pluginName + EnumChatFormatting.GRAY;
	          }
	        }
	      }
	      Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("Plugins: " + (message.isEmpty() ? "none." : new StringBuilder("(").append(size).append("): ").append(EnumChatFormatting.GREEN).append(message).append(EnumChatFormatting.GRAY).append(".").toString())));
	      packet.setCancelled(true);
	      EventManager.unregister(this);
	    }
	  }
	 public void runCmd(String s){
		 EventManager.register(this);
		 this.mc.getNetHandler().addToSendQueue(new C14PacketTabComplete("/"));
		 Wrapper.tellPlayer("Trying to find plugins... (If this is a vanilla server kys you retard)");
	 }
}
