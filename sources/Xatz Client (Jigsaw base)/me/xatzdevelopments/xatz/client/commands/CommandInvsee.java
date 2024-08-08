package me.xatzdevelopments.xatz.client.commands;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.IChatComponent;

public class CommandInvsee extends Command {
	
	
	 EntityPlayer player;
	 boolean show;
	  
	@Override
	public void run(String[] args) {
		   if (args == null || args.length < 1) {
	            //missign args ffs
	        }
	        if (mc.thePlayer.capabilities.isCreativeMode) {
	            
	                Xatz.chatMessage("Survival mode only");
	            
	           
	        }
	        String action = args[0].toLowerCase();
	        EntityPlayer player = getPlayerByName(action);
	        if (player != null) {
	            this.player = player;
	            this.show = true;
	            mc.displayGuiScreen(new GuiInventory(player));
	            
	        Xatz.chatMessage(String.valueOf("Player not found"));
	        }
	       //successful
	        if (Keyboard.getEventKey() == 1 && this.show) {
	            this.show = false;
	            this.player = null;
	            return;
	        }
	        if (this.player != null && this.show) {
	            mc.displayGuiScreen(new GuiInventory(this.player));
	        }
	    

	    }

	    
	    public static EntityPlayer getPlayerByName(String name)
	    {
	      for (Object o : Minecraft.theWorld.loadedEntityList) {
	        if (((o instanceof EntityPlayer)) && (((EntityPlayer)o).getName().equalsIgnoreCase(name))) {
	          return (EntityPlayer)o;
	        }
	      }
	      return null;
	    }

	@Override
	public String getActivator() {
		return ".invsee";
	}

	@Override
	public String getSyntax() {
		return ".invsee";
	}

	@Override
	public String getDesc() {
		return "Attempts to see other players inventory";
	}
}
