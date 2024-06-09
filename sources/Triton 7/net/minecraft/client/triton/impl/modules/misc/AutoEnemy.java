package net.minecraft.client.triton.impl.modules.misc;

import net.minecraft.client.triton.management.enemies.EnemyManager;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.TickEvent;
import net.minecraft.client.triton.management.friend.FriendManager;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@Mod
public class AutoEnemy extends Module {

	ItemStack[] armor;
	Timer checkTimer = new Timer();

	public AutoEnemy(){
		this.setProperties("AutoEnemy", "AutoEnemy", Module.Category.Misc, 0, "", true);
	}
	
	@EventTarget
	public void onTick(TickEvent event) {
		for (Object o : ClientUtils.mc().theWorld.loadedEntityList) {
			Entity ent = (Entity) o;
			if (ent != ClientUtils.mc().thePlayer && !EnemyManager.isEnemy(ent.getName())
					&& !FriendManager.isFriend(ent.getName()) && ent instanceof EntityPlayer) {
				int j = (armor = ((EntityPlayer) ent).inventory.armorInventory).length;
				for (int i = 0; i < j; i++) {
					ItemStack armourStack = armor[i];
					if (armourStack != null) {
						if ((armourStack.getItem() instanceof ItemArmor)) {
							int protLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId,
									armourStack);
							if (protLevel >= 4) {
								if (ent != ClientUtils.mc().thePlayer && !EnemyManager.isEnemy(ent.getName())
										&& !FriendManager.isFriend(ent.getName())) {
									ClientUtils.sendMessage(ent.getName() + " has prot 4 and has been enemied.");
									EnemyManager.addEnemy(ent.getName(), ent.getName() + " [P4]");
								}
							} else {
								if (ent != ClientUtils.mc().thePlayer && EnemyManager.isEnemy(ent.getName())
										&& !FriendManager.isFriend(ent.getName())
										&& EnemyManager.getAliasName(ent.getName()).contains("[P4]")) {
									EnemyManager.removeEnemy(ent.getName());
								}
							}
						}
					}
				}
			}
		}
	}
}
