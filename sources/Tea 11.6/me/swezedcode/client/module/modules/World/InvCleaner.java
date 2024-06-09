package me.swezedcode.client.module.modules.World;

import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InvCleaner extends Module {

	public InvCleaner() {
		super("InvCleaner", Keyboard.KEY_NONE, 0xFF48CF17, ModCategory.World);
		if(!MemeNames.enabled) {
			setDisplayName("InvCleaner");
		}else{
			setDisplayName("MakesYouThrowUpItems");
		}
	}

	public BooleanValue debug = new BooleanValue(this, "debug", "debug", Boolean.valueOf(true));
	
	private int slots;
	private TimerUtils timer = new TimerUtils();
	private double numberIdkWillfigureout;
	private boolean someboolean;

	public void onEnable() {
		timer.rt();
		slots = 9;
		numberIdkWillfigureout = getEnchantmentOnSword(mc.thePlayer.getHeldItem());
	}

	@EventListener
	private void onUpdate(EventPreMotionUpdates event) {
		if ((slots >= 45) && (!someboolean)) {
			setToggled(false);
			return;
		}
		if (someboolean) {
			if (timer.hD(30)) {
				mc.playerController.windowClick(0, -999, 0, 0, mc.thePlayer);
				mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
				mc.playerController.syncCurrentPlayItem();
				someboolean = false;
				if(debug.getValue()) {
					msg("Dropped " + mc.thePlayer.inventory.currentItem + ", current item in hand: " + mc.thePlayer.inventory.getCurrentItem());
				}
				timer.rt();
			}
			return;
		}
		numberIdkWillfigureout = getEnchantmentOnSword(mc.thePlayer.getHeldItem());
		ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slots).getStack();
		if ((isItemBad(stack)) && (getEnchantmentOnSword(stack) <= numberIdkWillfigureout)
				&& (stack != mc.thePlayer.getHeldItem())) {
			mc.playerController.windowClick(0, slots, 0, 0, mc.thePlayer);
			timer.rt();
			someboolean = true;
		}
		slots += 1;
	}

	public static boolean isItemBad(ItemStack item) {
		return (item != null) && ((item.getItem().getUnlocalizedName().contains("TNT"))
				|| (item.getItem().getUnlocalizedName().contains("stick"))
				|| (item.getItem().getUnlocalizedName().contains("egg"))
				|| (item.getItem().getUnlocalizedName().contains("string"))
				|| (item.getItem().getUnlocalizedName().contains("flint"))
				|| (item.getItem().getUnlocalizedName().contains("compass"))
				|| (item.getItem().getUnlocalizedName().contains("feather"))
				|| (item.getItem().getUnlocalizedName().contains("bucket"))
				|| (item.getItem().getUnlocalizedName().contains("chest"))
				|| (item.getItem().getUnlocalizedName().contains("snowball"))
				|| (item.getItem().getUnlocalizedName().contains("fish"))
				|| (item.getItem().getUnlocalizedName().contains("enchant"))
				|| (item.getItem().getUnlocalizedName().contains("exp")) || ((item.getItem() instanceof ItemPickaxe))
				|| ((item.getItem() instanceof ItemTool)) || ((item.getItem() instanceof ItemArmor))
				|| ((item.getItem() instanceof ItemSword))
				|| ((item.getItem().getUnlocalizedName().contains("potion")) && (isBadPotion(item))));
	}

	public static boolean isBadPotion(ItemStack itemStack) {
		if (itemStack == null) {
			return false;
		}
		if (!(itemStack.getItem() instanceof ItemPotion)) {
			return false;
		}
		ItemPotion itemPotion = (ItemPotion) itemStack.getItem();
		Iterator iterator = itemPotion.getEffects(itemStack).iterator();
		PotionEffect potionEffect;
		do {
			if (!iterator.hasNext()) {
				return false;
			}
			Object pObj = iterator.next();
			potionEffect = (PotionEffect) pObj;
			if (potionEffect.getPotionID() == Potion.poison.getId()) {
				return true;
			}
			if (potionEffect.getPotionID() == Potion.moveSlowdown.getId()) {
				return true;
			}
		} while (potionEffect.getPotionID() != Potion.harm.getId());
		return true;
	}

	private static double getEnchantmentOnSword(ItemStack itemStack) {
		if (itemStack == null) {
			return 0.0D;
		}
		if (!(itemStack.getItem() instanceof ItemSword)) {
			return 0.0D;
		}
		ItemSword itemSword = (ItemSword) itemStack.getItem();
		return EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180314_l.effectId, itemStack)
				+ itemSword.field_150934_a;
	}

}
