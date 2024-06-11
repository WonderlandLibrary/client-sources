package Hydro.module.modules.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.Timer;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class InvManager extends Module {
	public Minecraft mc = Minecraft.getMinecraft();
	boolean invcleaner;
	boolean hasSet = false;
	private double delay;
	private Timer timer;
	private Timer updateTimer;
	boolean dropping = false;
	public int weaponSlot = 1;

	private boolean archeryItems = false;
	private int swordSlot = weaponSlot;

	public static boolean isSorting = false;

	public InvManager() {
		super("InvManager", Keyboard.KEY_F, true, Category.PLAYER, "Manages your inventory");
		this.timer = new Timer();
		updateTimer = new Timer();
		Client.instance.settingsManager.rSetting(new Setting("invmanMaxDelay", "MaxDelay", this, 15, 1, 30, true));
		Client.instance.settingsManager.rSetting(new Setting("invmanMinDelay", "MinDelay", this, 5, 1, 30, true));
		Client.instance.settingsManager.rSetting(new Setting("invmanBlocks", "BlockLimit", this, 128, 1, 640, true));
		Client.instance.settingsManager.rSetting(new Setting("invmanFood", "Food", this, true));
		Client.instance.settingsManager.rSetting(new Setting("invmanCleaner", "Cleaner", this, true));
		Client.instance.settingsManager.rSetting(new Setting("invmanOpenInv", "OpenInv", this, false));
		Client.instance.settingsManager.rSetting(new Setting("invmanKeepSword", "KeepSword", this, true));
		Client.instance.settingsManager.rSetting(new Setting("invmanKeepPickaxe", "KeepPickaxe", this, false));
		Client.instance.settingsManager.rSetting(new Setting("invmanKeepAxe", "KeepAxe", this, false));
		Client.instance.settingsManager.rSetting(new Setting("invmanKeepShovel", "KeepShovel", this, false));
	}

	@Override
	public void onEvent(Event e) {
		double min = Client.instance.settingsManager.getSettingByName("invmanMinDelay").getValDouble();
		double max = Client.instance.settingsManager.getSettingByName("invmanMaxDelay").getValDouble();
		if (updateTimer.hasReached(Math.min(min, max))) {
			if (min > max || min == max) {
				max = min * 1.1;
			}
			delay = ThreadLocalRandom.current().nextDouble(min, max);
			hasSet = true;
		}
		if (!hasSet) {
			return;
		}
		int cap = (int) Client.instance.settingsManager.getSettingByName("invmanBlocks").getValDouble();
		boolean openinv = Client.instance.settingsManager.getSettingByName("invmanOpenInv").getValBoolean();
		boolean foodOption = Client.instance.settingsManager.getSettingByName("invmanFood").getValBoolean();
		invcleaner = Client.instance.settingsManager.getSettingByName("invmanCleaner").getValBoolean();
		boolean archery = archeryItems;
		if (openinv && !(mc.currentScreen instanceof GuiInventory)) {
			return;
		}
		weaponSlot = swordSlot;
		if (weaponSlot == 0 || weaponSlot > 9) {
			weaponSlot = 69;
		}
		weaponSlot--;
		if (e.isPre()) {
			if (mc.thePlayer != null && !dropping
					&& (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory)
					&& this.timer.hasReached(delay)) {
				for (int i = 9; i < 45; ++i) {
					if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
						final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
						if (this.isBad(is, i) && !(is.getItem() instanceof ItemArmor)
								&& is != mc.thePlayer.getCurrentEquippedItem()) {
							if (invcleaner) {
								isSorting = true;
								this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 0,
										mc.thePlayer);
								this.dropping = true;
								this.timer.reset();
							}
							break;
						} else {
							if (weaponSlot < 10 && is.getItem() instanceof ItemSword && isBestWeapon(is)
									&& 45 - i - 9 != weaponSlot && !dropping) {
								isSorting = true;
								mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, weaponSlot,
										2, mc.thePlayer);
								timer.reset();
							}
						}
					}
				}
			} else if (dropping && this.timer.hasReached(delay / 2)) {
				this.mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0,
						mc.thePlayer);
				timer.reset();
				dropping = false;
				isSorting = false;
			}
		}
		if (!invcleaner) {
			dropping = false;
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
		this.dropping = false;
		isSorting = false;
	}

	private ItemStack bestSword() {
		ItemStack best = null;
		float swordDamage = 0.0f;
		for (int i = 9; i < 45; ++i) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (is.getItem() instanceof ItemSword) {
					final float swordD = this.getItemDamage(is);
					if (swordD > swordDamage) {
						swordDamage = swordD;
						best = is;
					}
				}
			}
		}
		return best;
	}

	private boolean isBad(final ItemStack stack, int slot) {
		boolean swords = Client.instance.settingsManager.getSettingByName("invmanKeepSword").getValBoolean();
		boolean axe = Client.instance.settingsManager.getSettingByName("invmanKeepAxe").getValBoolean();
		boolean pickaxe = Client.instance.settingsManager.getSettingByName("invmanKeepPickaxe").getValBoolean();
		boolean shovel = Client.instance.settingsManager.getSettingByName("invmanKeepShovel").getValBoolean();
		if (stack.getDisplayName().toLowerCase().contains("(right click)")) {
			return false;
		}
		if (stack.getDisplayName().toLowerCase().contains("\u00A7k||")) {
			return false;
		}
		if (stack.getItem() instanceof ItemBlock
				&& (getBlockCount() > Client.instance.settingsManager.getSettingByName("invmanBlocks")
						.getValDouble()/*
										 * || Scaffold.getBlacklistedBlocks().contains(((ItemBlock)stack.getItem()).
										 * getBlock())
										 */)) {
			return true;
		}
		if ((slot == weaponSlot && isBestWeapon(mc.thePlayer.inventoryContainer.getSlot(weaponSlot).getStack()))) {
			return false;
		}
		if (stack.getItem() instanceof ItemSword && !swords) {
			return true;
		} else if (swords && stack.getItem() instanceof ItemSword && !isBestWeapon(stack)) {
			return true;
		}
		if (stack.getItem() instanceof ItemPickaxe && !pickaxe) {
			return true;
		} else if (pickaxe && stack.getItem() instanceof ItemPickaxe && !isBestPickaxe(stack)) {
			return true;
		}
		if (stack.getItem() instanceof ItemAxe && !axe) {
			return true;
		} else if (axe && stack.getItem() instanceof ItemAxe && !isBestAxe(stack)) {
			return true;
		}
		if (stack.getItem().getUnlocalizedName().contains("shovel") && !shovel) {
			return true;
		} else if (shovel && stack.getItem().getUnlocalizedName().contains("shovel") && !isBestShovel(stack)) {
			return true;
		}
		if (stack.getItem() instanceof ItemSword && weaponSlot > 10) {
			return true;
		}
		if (stack.getItem() instanceof ItemPotion) {
			if (isBadPotion(stack)) {
				return true;
			}
		}
		if (stack.getItem() instanceof ItemFood
				&& Client.instance.settingsManager.getSettingByName("invmanFood").getValBoolean()
				&& !(stack.getItem() instanceof ItemAppleGold)) {
			return false;
		}
		if (stack.getItem() instanceof ItemHoe || stack.getItem() instanceof ItemArmor) {
			return true;
		}
		if ((stack.getItem() instanceof ItemBow || stack.getItem().getUnlocalizedName().contains("arrow"))
				&& archeryItems) {
			return true;
		}
		if (((stack.getItem().getUnlocalizedName().contains("tnt"))
				|| (stack.getItem().getUnlocalizedName().contains("stick"))
				|| (stack.getItem().getUnlocalizedName().contains("egg"))
				|| (stack.getItem().getUnlocalizedName().contains("string"))
				|| (stack.getItem().getUnlocalizedName().contains("cake"))
				|| (stack.getItem().getUnlocalizedName().contains("mushroom"))
				|| (stack.getItem().getUnlocalizedName().contains("flint"))
				|| (stack.getItem().getUnlocalizedName().contains("compass"))
				|| (stack.getItem().getUnlocalizedName().contains("dyePowder"))
				|| (stack.getItem().getUnlocalizedName().contains("feather"))
				|| (stack.getItem().getUnlocalizedName().contains("bucket"))
				|| (stack.getItem().getUnlocalizedName().contains("chest")
						&& !stack.getDisplayName().toLowerCase().contains("collect"))
				|| (stack.getItem().getUnlocalizedName().contains("snow"))
				|| (stack.getItem().getUnlocalizedName().contains("fish"))
				|| (stack.getItem().getUnlocalizedName().contains("enchant"))
				|| (stack.getItem().getUnlocalizedName().contains("exp"))
				|| (stack.getItem().getUnlocalizedName().contains("shears"))
				|| (stack.getItem().getUnlocalizedName().contains("anvil"))
				|| (stack.getItem().getUnlocalizedName().contains("torch"))
				|| (stack.getItem().getUnlocalizedName().contains("seeds"))
				|| (stack.getItem().getUnlocalizedName().contains("leather"))
				|| (stack.getItem().getUnlocalizedName().contains("reeds"))
				|| (stack.getItem().getUnlocalizedName().contains("skull"))
				|| (stack.getItem().getUnlocalizedName().contains("record"))
				|| (stack.getItem().getUnlocalizedName().contains("snowball"))
				|| (stack.getItem() instanceof ItemGlassBottle)
				|| (stack.getItem().getUnlocalizedName().contains("piston")))) {
			return true;
		}
		if (isDuplicate(stack, slot)) {
			return true;
		}
		return false;
	}

	private List<ItemStack> getBest() {
		final List<ItemStack> best = new ArrayList<ItemStack>();
		for (int i = 0; i < 4; ++i) {
			ItemStack armorStack = null;
			for (final ItemStack itemStack : mc.thePlayer.inventory.armorInventory) {
				if (itemStack != null) {
					if (itemStack.getItem() instanceof ItemArmor) {
						final ItemArmor stackArmor = (ItemArmor) itemStack.getItem();
						if (stackArmor.armorType == i) {
							armorStack = itemStack;
						}
					}
				}
			}
			final double reduction = (armorStack == null) ? -1.0 : this.getArmorStrength(armorStack);
			ItemStack slotStack = this.findBestArmor(i);
			if (slotStack != null && this.getArmorStrength(slotStack) <= reduction) {
				slotStack = armorStack;
			}
			if (slotStack != null) {
				best.add(slotStack);
			}
		}
		return best;
	}

	public boolean isDuplicate(ItemStack stack, int slot) {
		for (int i = 9; i < 45; ++i) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (is != stack && slot != i && is.getUnlocalizedName().equalsIgnoreCase(stack.getUnlocalizedName())
						&& !(is.getItem() instanceof ItemPotion) && !(is.getItem() instanceof ItemBlock)) {
					if (is.getItem() instanceof ItemSword) {
						if (this.getDamage(is) != this.getDamage(stack)) {
						} else {
							return true;
						}
					} else if (is.getItem() instanceof ItemTool) {
						if (this.getToolEffect(is) != this.getToolEffect(stack)) {
						} else {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private int getBlockCount() {
		int blockCount = 0;
		for (int i = 0; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				Item item = is.getItem();
				if (is.getItem() instanceof ItemBlock) {
					blockCount += is.stackSize;
				}
			}
		}
		return blockCount;
	}

	private ItemStack findBestArmor(final int itemSlot) {
		ItemStack i = null;
		double maxReduction = 0.0;
		for (int slot = 0; slot < 36; ++slot) {
			final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[slot];
			if (itemStack != null) {
				final double reduction = this.getArmorStrength(itemStack);
				if (reduction != -1.0) {
					final ItemArmor itemArmor = (ItemArmor) itemStack.getItem();
					if (itemArmor.armorType == itemSlot) {
						if (reduction >= maxReduction) {
							maxReduction = reduction;
							i = itemStack;
						}
					}
				}
			}
		}
		return i;
	}

	private double getArmorStrength(final ItemStack itemStack) {
		if (!(itemStack.getItem() instanceof ItemArmor)) {
			return -1.0;
		}
		float damageReduction = (float) ((ItemArmor) itemStack.getItem()).damageReduceAmount;
		final Map enchantments = EnchantmentHelper.getEnchantments(itemStack);
		if (enchantments.containsKey(Enchantment.protection.effectId)) {
			final int level = (int) enchantments.get(Enchantment.protection.effectId);
			damageReduction += Enchantment.protection.calcModifierDamage(level, DamageSource.generic);
		}
		return damageReduction;
	}

	private boolean isBadPotion(final ItemStack stack) {
		if (stack != null && stack.getItem() instanceof ItemPotion) {
			final ItemPotion potion = (ItemPotion) stack.getItem();
			if (ItemPotion.isSplash(stack.getItemDamage())) {
				for (final Object o : potion.getEffects(stack)) {
					final PotionEffect effect = (PotionEffect) o;
					if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId()
							|| effect.getPotionID() == Potion.moveSlowdown.getId()
							|| effect.getPotionID() == Potion.weakness.getId()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private float getItemDamage(final ItemStack itemStack) {
		float damage = ((ItemSword) itemStack.getItem()).getDamageVsEntity();
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.01f;
		return damage;
	}

	private boolean isBestPickaxe(ItemStack stack) {
		Item item = stack.getItem();
		if (!(item instanceof ItemPickaxe))
			return false;
		float value = getToolEffect(stack);
		for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (getToolEffect(is) > value && is.getItem() instanceof ItemPickaxe) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isBestShovel(ItemStack stack) {
		Item item = stack.getItem();
		if (!(item instanceof ItemSpade))
			return false;
		float value = getToolEffect(stack);
		for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (getToolEffect(is) > value && is.getItem() instanceof ItemSpade) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isBestAxe(ItemStack stack) {
		Item item = stack.getItem();
		if (!(item instanceof ItemAxe))
			return false;
		float value = getToolEffect(stack);
		for (int i = 9; i < 45; i++) {
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (getToolEffect(is) > value && is.getItem() instanceof ItemAxe && !isBestWeapon(stack)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isBestWeapon(ItemStack stack) {
		float damage = getDamage(stack);
		for (int i = 0; i < 36; i++) {
			if (mc.thePlayer.inventory.mainInventory[i] != null) {
				ItemStack is = mc.thePlayer.inventory.mainInventory[i];
				if (getDamage(is) > damage && (is.getItem() instanceof ItemSword))
					return false;
			}
		}
		return true;
	}

	private float getDamage(ItemStack stack) {
		float damage = 0;
		Item item = stack.getItem();
		if (item instanceof ItemTool) {
			ItemTool tool = (ItemTool) item;
			damage += tool.getDamage();
		}
		if (item instanceof ItemSword) {
			ItemSword sword = (ItemSword) item;
			damage += sword.getAttackDamage();
		}
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f
				+ EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
		return damage;
	}

	private float getToolEffect(ItemStack stack) {
		Item item = stack.getItem();
		if (!(item instanceof ItemTool))
			return 0;
		String name = item.getUnlocalizedName();
		ItemTool tool = (ItemTool) item;
		float value = 1;
		if (item instanceof ItemPickaxe) {
			value = tool.getStrVsBlock(stack, Blocks.stone);
			if (name.toLowerCase().contains("gold")) {
				value -= 5;
			}
		} else if (item instanceof ItemSpade) {
			value = tool.getStrVsBlock(stack, Blocks.dirt);
			if (name.toLowerCase().contains("gold")) {
				value -= 5;
			}
		} else if (item instanceof ItemAxe) {
			value = tool.getStrVsBlock(stack, Blocks.log);
			if (name.toLowerCase().contains("gold")) {
				value -= 5;
			}
		} else
			return 1f;
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) * 0.0075D;
		value += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 100d;
		return value;
	}
}
