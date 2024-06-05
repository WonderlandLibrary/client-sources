package me.superskidder.lune.modules.player;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.math.MathUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

import java.awt.*;


/**
 * @description:
 * @author: Qian_Xia
 * @create: 2020-08-24 16:43
 **/
public class ChestStealer extends Mod {
    private Num<Double> delay = new Num<>("Delay", 150.0, 0.0, 1000.0);
    private Bool<Boolean> random = new Bool<>("Random", false);
    private Bool<Boolean> checkMenu = new Bool<>("AntiMenu", false);
    public static Bool<Boolean> nogui = new Bool<>("NoGui", true);

    private TimerUtil timer = new TimerUtil();

    public ChestStealer() {
        super("ChestStealer", ModCategory.Player,"Steal chest");
        this.addValues(delay, checkMenu, random, nogui);
    }

    public double getDelay() {
        return random.getValue() ? delay.getValue() + MathUtil.randomNumber(-10, 10) : delay.getValue();
    }

    @EventTarget
    public void onRender(EventRender2D event) {
        ScaledResolution sr = new ScaledResolution(mc);
        if (mc.thePlayer.openContainer instanceof ContainerChest && Lune.moduleManager.getModByClass(ChestStealer.class).getState() && ChestStealer.nogui.getValue()) {
            FontLoaders.F18.drawCenteredStringWithShadow("Stealing chest...", sr.getScaledWidth_double() / 2, sr.getScaledHeight_double() / 2, new Color(200, 200, 200).getRGB());
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;
            int i = 0;
            GuiChest guiChest = (GuiChest) mc.currentScreen;
            if (StatCollector.translateToLocal("container.chest")
                    .equalsIgnoreCase(container.getLowerChestInventory().getDisplayName().getUnformattedText())
                    || StatCollector.translateToLocal("container.chestDouble")
                    .equalsIgnoreCase(container.getLowerChestInventory().getDisplayName().getUnformattedText())
                    && this.checkMenu.getValue()) {
                while (i < container.getLowerChestInventory().getSizeInventory()) {
                    if (container.getLowerChestInventory().getStackInSlot(i) != null
                            && this.timer.hasReached(getDelay())) {
                        mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                        this.timer.reset();
                    }
                    ++i;
                }
                if (this.isEmpty()) {
                    mc.thePlayer.closeScreen();
                }
            } else {
                return;
            }
        }
    }

    private boolean isEmpty() {
        if (mc.thePlayer.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;
            int i = 0;
            while (i < container.getLowerChestInventory().getSizeInventory()) {
                ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
                if (itemStack != null && itemStack.getItem() != null) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    private boolean isBad(ItemStack item) {
        ItemStack is = null;
        float lastDamage = -1;
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is1 = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is1.getItem() instanceof ItemSword && item.getItem() instanceof ItemSword) {
                    if (lastDamage < getDamage(is1)) {
                        lastDamage = getDamage(is1);
                        is = is1;
                    }
                }
            }
        }

        if (is != null && is.getItem() instanceof ItemSword && item.getItem() instanceof ItemSword) {
            float currentDamage = getDamage(is);
            float itemDamage = getDamage(item);
            if (itemDamage > currentDamage) {
                return false;
            }
        }

        return item != null && ((item.getItem().getUnlocalizedName().contains("tnt"))
                || (item.getItem().getUnlocalizedName().contains("stick"))
                || (item.getItem().getUnlocalizedName().contains("egg")
                && !item.getItem().getUnlocalizedName().contains("leg"))
                || (item.getItem().getUnlocalizedName().contains("string"))
                || (item.getItem().getUnlocalizedName().contains("flint"))
                || (item.getItem().getUnlocalizedName().contains("compass"))
                || (item.getItem().getUnlocalizedName().contains("feather"))
                || (item.getItem().getUnlocalizedName().contains("bucket"))
                || (item.getItem().getUnlocalizedName().contains("snow"))
                || (item.getItem().getUnlocalizedName().contains("fish"))
                || (item.getItem().getUnlocalizedName().contains("enchant"))
                || (item.getItem().getUnlocalizedName().contains("exp"))
                || (item.getItem().getUnlocalizedName().contains("shears"))
                || (item.getItem().getUnlocalizedName().contains("anvil"))
                || (item.getItem().getUnlocalizedName().contains("torch"))
                || (item.getItem().getUnlocalizedName().contains("seeds"))
                || (item.getItem().getUnlocalizedName().contains("leather"))
                || ((item.getItem() instanceof ItemPickaxe)) || ((item.getItem() instanceof ItemGlassBottle))
                || ((item.getItem() instanceof ItemTool)) || (item.getItem().getUnlocalizedName().contains("piston"))
                || ((item.getItem().getUnlocalizedName().contains("potion")) && (isBadPotion(item))));
    }

    private boolean isBadPotion(ItemStack stack) {
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

    private float getDamage(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemSword)) {
            return 0;
        }
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f
                + ((ItemSword) stack.getItem()).getDamageVsEntity();
    }
}
