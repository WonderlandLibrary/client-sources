package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.tools.timer;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

public class ChestStealer2 extends Module {

	timer timer2 = new timer();
	

    public static double delay = 1100.0;


	

	
	

	public ChestStealer2() {
		super("ChestStealer2", Keyboard.KEY_H, Category.PLAYER,
				"An advanced cheststealer.");
	}

	@Override
	public void onDisable() {
	
		super.onDisable();
	}

	@Override
	public void onEnable() {
	
		super.onEnable();
	}

	@Override
	public void onUpdate(UpdateEvent event) {
		if (mc.thePlayer.openContainer != null) {
           
            if (mc.thePlayer.openContainer instanceof ContainerChest) {
                
                ContainerChest container = (ContainerChest)mc.thePlayer.openContainer;
                if (container.getInventory().isEmpty()) {
               
                    mc.thePlayer.openContainer = null; 
                }
                if (!container.getLowerChestInventory().getName().equals("Game Menu") && !container.getLowerChestInventory().getName().contains("Play") && !container.getLowerChestInventory().getName().contains("Clique no bloco verde") && !container.getLowerChestInventory().getName().contains("Sky Wars Solo") && !container.getLowerChestInventory().getName().contains("Sky Wars Dupla") && !container.getLowerChestInventory().getName().contains("Modos de Jogo") && !container.getLowerChestInventory().getName().contains("Bed Wars Solo") && !container.getLowerChestInventory().getName().contains("Bed Wars Dupla") && !container.getLowerChestInventory().getName().contains("Perfil") && !container.getLowerChestInventory().getName().contains("Select a Server")) { // GreatZardasht designed this at Sunday 18/10/2020 15:32 British Time
                	
                
                int i = 0;
                while (i < container.getLowerChestInventory().getSizeInventory()) {
                    if (container.getLowerChestInventory().getStackInSlot(i) != null && timer2.delay(120)) {
                       
                        mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                        timer2.reset();
                    }
                    ++i;
                }
                if (container.getInventory().isEmpty()) {
                    mc.displayGuiScreen(null);
                }
            }
            }
        }
    }
}

