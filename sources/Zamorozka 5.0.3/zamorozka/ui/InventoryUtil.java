package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class InventoryUtil {
	private static Minecraft mc = Minecraft.getMinecraft();
	static TimerHelper timer = new TimerHelper();

	public static int getItemCount(Container container, Item item) {
		int itemCount = 0;
		for (int i = 0; i < 45; ++i) {
			if (container.getSlot(i).getHasStack()) {
				final ItemStack is = container.getSlot(i).getStack();
				if (is.getItem() == item) {
					itemCount += is.getMaxStackSize();
				}
			}
		}
		return itemCount;
	}

	public static boolean isInventoryFull() {
		for (int index = 9; index <= 44; ++index) {
			final ItemStack stack = mc.player.inventoryContainer.getSlot(index).getStack();
			if (stack == null) {
				return false;
			}
		}
		return true;
	}

	public static void swapInventoryItems(int slot1, int slot2) {
		short short1 = Minecraft.getMinecraft().player.inventoryContainer.getNextTransactionID(Minecraft.getMinecraft().player.inventory);

		ItemStack itemstack = Minecraft.getMinecraft().player.inventoryContainer.slotClick(slot1, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
		Minecraft.getMinecraft().player.connection.sendPacket(new CPacketClickWindow(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot1, 0, ClickType.PICKUP, itemstack, short1));

		itemstack = Minecraft.getMinecraft().player.inventoryContainer.slotClick(slot2, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);

		itemstack = Minecraft.getMinecraft().player.inventoryContainer.slotClick(slot1, 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
		Minecraft.getMinecraft().player.connection.sendPacket(new CPacketClickWindow(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot1, 0, ClickType.PICKUP, itemstack, short1));

		Minecraft.getMinecraft().playerController.updateController();
	}

	public static int findHotbarPotion() {
		for (int o = 0; o < 9; o++) {
			ItemStack item = mc.player.inventory.getStackInSlot(o);
			if (item != null && InventoryUtil.isPotion(item))
				return o;
		}
		return -1;
	}

	public static int findEmptyPotion() {
		for (int o = 36; o < 45; o++) {
			ItemStack item = mc.player.inventoryContainer.getSlot(o).getStack();
			if (item == null)
				return o;
			else if (item.getItem() instanceof ItemGlassBottle)
				return o;
		}
		return -1;
	}

	/*
	 * public static void movePotions() { int emptySlot = findEmptyPotion(); if
	 * (emptySlot != -1) { int newPotion = getUseablePotion(); if (newPotion != -1)
	 * { ItemStack itemStack =
	 * mc.player.inventoryContainer.getSlot(newPotion).getStack(); if
	 * (isShiftable(itemStack)) { if (clickTask.containsClick(newPotion, 0, 1))
	 * timer.reset(); else if (timer.hasReached(50L)) { clickTask.add(newPotion, 0,
	 * 1); } } else { if (clickTask.containsClick(newPotion, 0, 0)) timer.reset();
	 * else if (timer.hasReached(50)) { add(newPotion, 0, 0); add(emptySlot, 0, 0);
	 * add(newPotion, 0, 0, newPotion); } } } } }
	 * 
	 * public void add(int windowId, int slot, int mouse, int shift) { add(new int[]
	 * { windowId, slot, mouse, shift }); }
	 */

	public static boolean isShiftable(ItemStack preferedItem) {
		if (preferedItem == null)
			return true;
		for (int o = 36; o < 45; o++) {
			if (mc.player.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = mc.player.inventoryContainer.getSlot(o).getStack();
				if (item == null)
					return true;
				else if (Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(preferedItem.getItem())) {
					// TODO: Fix this.
					// if (item.stackSize + preferedItem.stackSize <=
					// preferedItem.getMaxStackSize())
					if (item.getMaxStackSize() + preferedItem.getMaxStackSize() <= preferedItem.getMaxStackSize())
						return true;
				}
			} else
				return true;
		}
		return false;
	}

	public static int getUseablePotion() {
		for (int o = 9; o < 36; o++) {
			if (mc.player.inventoryContainer.getSlot(o).getHasStack()) {
				ItemStack item = mc.player.inventoryContainer.getSlot(o).getStack();
				if (isPotion(item))
					return o;
			}
		}
		return -1;
	}

	public static boolean isPotion(ItemStack itemStack) {
		if (itemStack.getItem() instanceof ItemSplashPotion) {
			for (PotionEffect effect : PotionUtils.getEffectsFromStack(itemStack)) {
				if (effect.getPotion() == effect.getPotion().getPotionById(5)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void usePotion() {
		EnumHand hand = EnumHand.MAIN_HAND;
		ItemStack item = mc.player.getHeldItem(EnumHand.MAIN_HAND);
		// TODO: Fix this.
		// if (item != null && mc.playerController.processRightClick(mc.player,
		// mc.world, item, hand) == EnumActionResult.SUCCESS) {
		if (item != null && mc.playerController.processRightClick(mc.player, mc.world, hand) == EnumActionResult.SUCCESS) {
		}
	}

	public static boolean isIntercepted(BlockPos pos) {
		for (Entity entity : Minecraft.getMinecraft().world.loadedEntityList) {
			if (new AxisAlignedBB(pos).intersectsWith(entity.getEntityBoundingBox())) {
				return true;
			}
		}
		return false;
	}

	public static int getBlockInHotbar(Block block) {
		for (int i = 0; i < 9; i++) {
			Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
			if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().equals(block)) {
				return i;
			}
		}
		return -1;
	}

	public static int getAnyBlockInHotbar() {
		for (int i = 0; i < 9; i++) {
			Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
			if (item instanceof ItemBlock) {
				return i;
			}
		}
		return -1;
	}

	public static int getItemInHotbar(Item designatedItem) {
		for (int i = 0; i < 9; i++) {
			Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
			if (item instanceof Item && item.equals(designatedItem)) {
				return i;
			}
		}
		return -1;
	}

	public boolean inventoryIsFull() {
		int i = 9;
		while (i < 45) {
			ItemStack stack = Minecraft.getMinecraft().player.inventoryContainer.getSlot(i).getStack();
			if (stack == null) {
				return false;
			}
			++i;
		}
		return true;
	}

	public static int getArmorItemsEquipSlot(ItemStack stack, boolean equipmentSlot) {
		if (stack.getUnlocalizedName().contains("helmet"))
			return equipmentSlot ? 4 : 5;
		if (stack.getUnlocalizedName().contains("chestplate"))
			return equipmentSlot ? 3 : 6;
		if (stack.getUnlocalizedName().contains("leggings"))
			return equipmentSlot ? 2 : 7;
		if (stack.getUnlocalizedName().contains("boots"))
			return equipmentSlot ? 1 : 8;
		return -1;
	}

	public static int getPotsInInventory(int potID) {
		int counter = 0;
		for (int i = 1; i < 45; ++i) {
			if (Minecraft.getMinecraft().player.inventoryContainer.getSlot(i).getHasStack()) {
				final ItemStack is = Minecraft.getMinecraft().player.inventoryContainer.getSlot(i).getStack();
				final Item item = is.getItem();
				if (item instanceof ItemPotion) {
					final ItemPotion potion = (ItemPotion) item;
					if (PotionUtils.getEffectsFromStack(is) != null) {
						for (final Object o : PotionUtils.getEffectsFromStack(is)) {
							final PotionEffect effect = (PotionEffect) o;
							if (effect.getPotion() == Potion.getPotionById(potID) && ItemPotion.isSplash(is.getItemDamage())) {
								++counter;
							}
						}
					}
				}
			}
		}
		return counter;
	}

	public static boolean isPotion(final ItemStack stack, final Potion potion, final boolean splash) {
		if (stack == null) {
			return false;
		}
		if (!(stack.getItem() instanceof ItemPotion)) {
			return false;
		}
		final ItemPotion potionItem = (ItemPotion) stack.getItem();
		if (splash && !ItemPotion.isSplash(stack.getItemDamage())) {
			return false;
		}
		if (PotionUtils.getEffectsFromStack(stack) == null) {
			return potion == null;
		}
		if (potion == null) {
			return false;
		}
		return false;
	}

	public static boolean hotbarHasPotion(final Potion effect, final boolean splash) {
		for (int index = 0; index <= 8; ++index) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(index);
			if (stack != null && isPotion(stack, effect, splash)) {
				return true;
			}
		}
		return false;
	}

	public static void useFirstPotionSilent(final Potion effect, final boolean splash) {
		for (int index = 0; index <= 8; ++index) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(index);
			if (stack != null && isPotion(stack, effect, splash)) {
				final int oldItem = mc.player.inventory.currentItem;
				mc.getConnection().sendPacket(new CPacketHeldItemChange(index));
				mc.getConnection().sendPacket(new CPacketCreativeInventoryAction(oldItem, mc.player.inventory.getCurrentItem()));
				mc.getConnection().sendPacket(new CPacketHeldItemChange(oldItem));
				break;
			}
		}
	}

	public static boolean doesHotbarHaveBlock() {
		for (int i = 0; i < 9; ++i) {
			if (mc.player.inventory.getStackInSlot(i) != null && mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
				return true;
			}
		}
		return false;
	}

	public static boolean inventoryHasPotion(final Potion effect, final boolean splash) {
		for (int index = 0; index <= 36; ++index) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(index);
			if (stack != null && isPotion(stack, effect, splash)) {
				return true;
			}
		}
		return false;
	}

	public static int getSwordSlot() {
		int bestSword = -1;
		float bestDamage = 1F;

		for (int i = 9; i < 45; ++i) {
			if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
				final ItemStack item = mc.player.inventoryContainer.getSlot(i).getStack();
				if (item != null) {
					if (item.getItem() instanceof ItemSword) {
						ItemSword is = (ItemSword) item.getItem();
						float damage = is.getDamageVsEntity();
						damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(16), item) * 1.26F + EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), item) * 0.01f;
						if (damage > bestDamage) {
							bestDamage = damage;
							bestSword = i;
						}
					}
				}
			}
		}
		return bestSword;
	}

	public static int getBestSword() {
		int bestSword = -1;
		float bestDamage = 1F;

		for (int k = 0; k < mc.player.inventory.mainInventory.size(); k++) {
			ItemStack is = mc.player.inventoryContainer.getSlot(k).getStack();
			if (is != null && is.getItem() instanceof ItemSword) {
				ItemSword itemSword = (ItemSword) is.getItem();
				float damage = itemSword.getMaxDamage();
				damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), is);
				if (damage > bestDamage) {
					bestDamage = damage;
					bestSword = k;
				}
			}
		}
		return bestSword;
	}

	private boolean isBadPotion(ItemStack stack) {
		if (stack != null && stack.getItem() instanceof ItemPotion) {
			for (final Object o : PotionUtils.getEffectsFromStack(stack)) {
				final PotionEffect effect = (PotionEffect) o;
				if (effect.getPotion() == Potion.getPotionById(19) || effect.getPotion() == Potion.getPotionById(7) || effect.getPotion() == Potion.getPotionById(2) || effect.getPotion() == Potion.getPotionById(18)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate(p_147046_0_, p_147046_1_, 40.0f);
		GlStateManager.scale(-p_147046_2_, p_147046_2_, p_147046_2_);
		GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
		float var6 = p_147046_5_.renderYawOffset;
		float var7 = p_147046_5_.rotationYaw;
		float var8 = p_147046_5_.rotationPitch;
		float var9 = p_147046_5_.prevRotationYawHead;
		float var10 = p_147046_5_.rotationYawHead;
		GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
		GlStateManager.rotate((-(float) Math.atan(p_147046_4_ / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
		p_147046_5_.renderYawOffset = (float) Math.atan(p_147046_3_ / 40.0f) * -14.0f;
		p_147046_5_.rotationYaw = (float) Math.atan(p_147046_3_ / 40.0f) * -14.0f;
		p_147046_5_.rotationPitch = (-(float) Math.atan(p_147046_4_ / 40.0f)) * 15.0f;
		p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
		p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
		GlStateManager.translate(0.0f, 0.0f, 0.0f);
		RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
		var11.setPlayerViewY(180.0f);
		var11.setRenderShadow(false);
		var11.doRenderEntity(p_147046_5_, 0.0, 0.0, 0.0, 0.0f, 1.0f, true);
		var11.setRenderShadow(true);
		p_147046_5_.renderYawOffset = var6;
		p_147046_5_.rotationYaw = var7;
		p_147046_5_.rotationPitch = var8;
		p_147046_5_.prevRotationYawHead = var9;
		p_147046_5_.rotationYawHead = var10;
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public static boolean doesNextSlotHavePot() {
		for (int i = 0; i < 9; ++i) {
			if (mc.player.inventory.getStackInSlot(i) != null && mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemPotion) {
				return true;
			}
		}
		return false;
	}

	public static boolean doesNextSlotHaveSoup() {
		for (int i = 0; i < 9; ++i) {
			if (mc.player.inventory.getStackInSlot(i) != null && mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSoup) {
				return true;
			}
		}
		return false;
	}

	public static int getSlotWithPot() {
		for (int i = 0; i < 9; ++i) {
			if (mc.player.inventory.getStackInSlot(i) != null && mc.player.inventory.getStackInSlot(i).getItem() == Items.SPLASH_POTION) {
				return i;
			}
		}
		return 0;
	}

	public static int getSlotWithSoup() {
		for (int i = 0; i < 9; ++i) {
			if (mc.player.inventory.getStackInSlot(i) != null && mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemSoup) {
				return i;
			}
		}
		return 0;
	}
	
	public static int getSwordAtHotbar() {
		for (int i = 0; i < 9; ++i) {
			final ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
			if (itemStack.getItem() instanceof ItemSword) {
				return i;
			}
		}
		return 1;
	}

	private int findExpInHotbar() {
		int slot = 0;
		for (int i = 0; i < 9; i++) {
			if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
				slot = i;
				break;
			}
		}
		return slot;
	}

	private int getArmorDurability() {
		int TotalDurability = 0;

		for (ItemStack itemStack : mc.player.inventory.armorInventory) {
			TotalDurability = TotalDurability + itemStack.getItemDamage();
		}
		return TotalDurability;
	}

	public static int getItemSlot(Container container, Item item) {
		int slot = 0;
		for (int i = 9; i < 45; ++i) {
			if (container.getSlot(i).getHasStack()) {
				ItemStack is = container.getSlot(i).getStack();
				if (is.getItem() == item)
					slot = i;
			}
		}
		return slot;
	}

	public static int getItemSlotInHotbar(Item item) {
		int slot = 0;
		for (int i = 0; i < 9; i++) {
			ItemStack is = mc.player.inventory.getStackInSlot(i);
			if (is.getItem() == item) {
				slot = i;
				break;
			}
		}
		return slot;
	}

	public static void swap(int slot, int hotbarNum) {
		mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
		mc.playerController.windowClick(mc.player.inventoryContainer.windowId, hotbarNum, 0, ClickType.PICKUP, mc.player);
		mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
		mc.playerController.updateController();
	}

	public static boolean isBestArmor(ItemStack stack, int type) {
		float prot = getProtection(stack);
		String strType = "";
		if (type == 1) {
			strType = "helmet";
		} else if (type == 2) {
			strType = "chestplate";
		} else if (type == 3) {
			strType = "leggings";
		} else if (type == 4) {
			strType = "boots";
		}
		if (!stack.getUnlocalizedName().contains(strType)) {
			return false;
		}
		for (int i = 5; i < 45; i++) {
			if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
				if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType))
					return false;
			}
		}
		return true;
	}

	public static float getProtection(ItemStack stack) {
		float prot = 0;
		if ((stack.getItem() instanceof ItemArmor)) {
			ItemArmor armor = (ItemArmor) stack.getItem();
			prot += armor.damageReduceAmount + (100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), stack) * 0.0075D;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(3), stack) / 100d;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(1), stack) / 100d;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(7), stack) / 100d;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(34), stack) / 50d;
			prot += EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(4), stack) / 100d;
		}
		return prot;
	}

	   public static double getProtectionValue(ItemStack stack) {
	        return !(stack.getItem() instanceof ItemArmor) ? 0.0D : (double) ((ItemArmor) stack.getItem()).damageReduceAmount + (double) ((100 - ((ItemArmor) stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), stack) * 4) * 0.0075D;
	    }
}
