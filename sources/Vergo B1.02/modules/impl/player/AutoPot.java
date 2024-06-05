package xyz.vergoclient.modules.impl.player;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.RandomUtils;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.InventoryUtils;
import xyz.vergoclient.util.main.MovementUtils;
import xyz.vergoclient.util.main.RenderUtils;
import xyz.vergoclient.util.main.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.potion.PotionEffect;

public class AutoPot extends Module implements OnEventInterface {

	public AutoPot() {
		super("Autopot", Category.PLAYER);
	}
	
	public static transient TimerUtil throwTimer = new TimerUtil();
	
	@Override
	public void onEvent(Event e) {
		
		// one instead of zero so it doesn't do stuff at the same time as the inventory manager
		if (e instanceof EventUpdate && e.isPre() && mc.thePlayer.ticksExisted % 2 == 1) {
			
			if (mc.currentScreen instanceof GuiChest || mc.currentScreen instanceof GuiCrafting || mc.currentScreen instanceof GuiContainerCreative) {
				return;
			}
			
			// Throw potions
			if (MovementUtils.isOnGround(0.75) && throwTimer.hasTimeElapsed(50 * 10, false) && !Vergo.config.modKillAura.isEnabled()) {
				for (short i = 36; i < 45; i++) {

					if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {

						ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();

						if (is.getItem() instanceof ItemPotion) {
							
							if (ItemPotion.isSplash(is.getMetadata())) {
								
								if (!ItemPotion.isPotionBad(is)) {
									
									Collection<PotionEffect> playerEffects = mc.thePlayer.getActivePotionEffects();
									ArrayList<Integer> playerEffectsIds = new ArrayList<>();
									
									playerEffects.forEach(effect -> {
										if (effect.getDuration() > 1)
											playerEffectsIds.add(effect.getPotionID());
									});
									
									for (PotionEffect effect : ItemPotion.getPotionEffects(is)) {
										
										if (!playerEffectsIds.contains(effect.getPotionID()) && (effect.getDuration() > 1 || (mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) < 0.5f)) {
											int heldItemBeforeThrow = mc.thePlayer.inventory.currentItem;
											
											((EventUpdate)e).setPitch(89.5f);
											RenderUtils.setCustomPitch(89.5f);
											RenderUtils.setCustomYaw(((EventUpdate)e).yaw + (RandomUtils.nextFloat(0, 20) - 10));
//											mc.thePlayer.rotationYaw += (RandomUtils.nextFloat(0, 20) - 10);
//											mc.thePlayer.rotationPitch += (RandomUtils.nextFloat(0, 20) - 10);
											
											final int finalI = i;
											mc.getNetHandler().getNetworkManager()
											.sendPacketNoEvent(new C09PacketHeldItemChange(finalI - 36));
										
											mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 89.5f, mc.thePlayer.onGround));
											
											
											
											mc.getNetHandler().getNetworkManager()
													.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(is));
											
											mc.thePlayer.inventory.currentItem = heldItemBeforeThrow;
											
											mc.getNetHandler().getNetworkManager().sendPacketNoEvent(
													new C09PacketHeldItemChange(heldItemBeforeThrow));
											
//											new Thread(() -> {
//												try {
//													Thread.sleep(15);
//													mc.getNetHandler().getNetworkManager()
//													.sendPacketNoEvent(new C09PacketHeldItemChange(finalI - 36));
//												
////													mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 90, mc.thePlayer.onGround));
//													
//													Thread.sleep(10);
//													mc.getNetHandler().getNetworkManager()
//															.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(is));
//													
//													mc.thePlayer.inventory.currentItem = heldItemBeforeThrow;
//													
//													Thread.sleep(10);
//													mc.getNetHandler().getNetworkManager().sendPacketNoEvent(
//															new C09PacketHeldItemChange(heldItemBeforeThrow));
//												} catch (Exception e2) {
//													// TODO: handle exception
//												}
//											}).start();
											
											throwTimer.reset();
											
											return;
										}
										
									}
									
								}
								
							}

						}

					}
					
				}
			}
			
			boolean movedItem = false;
			
			// Refill
			for (short i = 0; i < 36; i++) {
				
				if (MovementUtils.isMoving()) {
					break;
				}
				
				if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
					ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();

					if (is.getItem() instanceof ItemPotion) {
						
						if (ItemPotion.isSplash(is.getMetadata())) {
							
							if (!ItemPotion.isPotionBad(is)) {
								
								Collection<PotionEffect> playerEffects = mc.thePlayer.getActivePotionEffects();
								ArrayList<Integer> playerEffectsIds = new ArrayList<>();
								
								playerEffects.forEach(effect -> {
									if (effect.getDuration() > 1)
										playerEffectsIds.add(effect.getPotionID());
								});
								
								for (PotionEffect effect : ItemPotion.getPotionEffects(is)) {
									
									if (!playerEffectsIds.contains(effect.getPotionID()) && (effect.getDuration() > 1 || (mc.thePlayer.getHealth() / mc.thePlayer.getMaxHealth()) < 0.5f)) {
										InventoryUtils.swap(i, 7, mc.thePlayer.inventoryContainer.windowId);
										movedItem = true;
										return;
									}
									
								}
								
							}
							
						}

					}

				}

			}
			
			if (movedItem) {
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
			}
			
		}
		
	}

}
