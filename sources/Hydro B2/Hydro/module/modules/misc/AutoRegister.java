package Hydro.module.modules.misc;

import Hydro.event.Event;
import Hydro.event.events.EventUpdate;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.ChatUtils;
import Hydro.util.Timer;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;

public class AutoRegister extends Module {
	
	Timer timer = new Timer();
	
	private boolean shouldSendPass = false;

	public AutoRegister() {
		super("AutoRegister", 0, true, Category.MISC, "Automatically registers a account");
	}

	@Override
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.currentScreen instanceof GuiChest) {
					GuiChest chestGui = (GuiChest) mc.currentScreen;
	                String name = chestGui.lowerChestInventory.getDisplayName().getUnformattedText();
					if(name.contains("Clique no bloco verde")) {
				        new Thread(new Runnable() {
				             @Override
				             public void run() {
				                 
				                 try {
				                     for(int i = 0; i < chestGui.inventoryRows * 9; i++) {
				                         Slot slot = (Slot) chestGui.inventorySlots.inventorySlots.get(i);
				                         // System.out.println(slot.getStack().getChatComponent().getUnformattedText());
				                         if(slot.getStack() != null && slot.getStack().getChatComponent().getUnformattedText().equals("[§aClique aqui]")) {
				                             Thread.sleep(25L);
				                             chestGui.handleMouseClick(slot, slot.slotNumber,0, 1);
				                             chestGui.handleMouseClick(slot, slot.slotNumber,0, 6);
				                             shouldSendPass = true;
				                         }
				                     }
				                     
				                 
				                 } catch(Exception e) {
				                     e.printStackTrace();
				                 }
				                 
				             }
				         }).start();
				        
				   }
					
					
				}
				
				if(shouldSendPass) {
					ChatUtils.sendMessage("/register 123456 123456");
	                ChatUtils.sendMessage("/login 123456");
	                shouldSendPass = false;
				}
				
			}
		}
	}
	
}
