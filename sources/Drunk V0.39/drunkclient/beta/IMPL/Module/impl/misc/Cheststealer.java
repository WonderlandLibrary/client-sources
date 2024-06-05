/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.world.Timer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class Cheststealer
extends Module {
    Timer timer = new Timer();
    private Numbers<Double> delay = new Numbers<Double>("Max Delay", "Max Delay", 4.0, 1.0, 10.0, 1.0);
    private Numbers<Double> AuraRange = new Numbers<Double>("Aura Range", "Aura Range", 1.0, 1.0, 7.0, 1.0);
    public Option<Boolean> aura = new Option<Boolean>("Aura", "Aura", false);
    public Option<Boolean> filter = new Option<Boolean>("Filter", "Filter", true);
    public Option<Boolean> titleCheck = new Option<Boolean>("Title Check", "Title Check", true);
    public Option<Boolean> autoclose = new Option<Boolean>("Auto Close", "AutoClose", true);
    public Option<Boolean> silent = new Option<Boolean>("Silent", "Silent", false);
    public final Set openedChests = new HashSet();

    public Cheststealer() {
        super("Stealer", new String[]{"cheststeal", "chests", "stealer", "cheststealer"}, Type.MISC, "Steal items from chests");
        this.addValues(this.delay, this.AuraRange, this.silent, this.autoclose, this.titleCheck, this.aura, this.filter);
    }

    @EventHandler
    public void onMotionUpdate(EventPreUpdate event) {
        int delay = (int)((Double)this.delay.getValue() * 50.0);
        EntityPlayerSP player = Minecraft.thePlayer;
        if (!(Cheststealer.mc.currentScreen instanceof GuiChest)) return;
        GuiChest chest = (GuiChest)Cheststealer.mc.currentScreen;
        boolean titleCheckk = ((Boolean)this.titleCheck.getValue()).booleanValue() ? chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Contai") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Crate") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("LOW") : true;
        if (!titleCheckk) return;
        if (((Boolean)this.autoclose.getValue()).booleanValue() && (this.isChestEmpty(chest) || this.isInventoryFull())) {
            player.closeScreen();
            return;
        }
        int index = 0;
        while (index < chest.getLowerChestInventory().getSizeInventory()) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null && this.timer.reach(delay - ThreadLocalRandom.current().nextInt(0, 250))) {
                boolean trash;
                boolean bl = ((Boolean)this.filter.getValue()).booleanValue() ? !Cheststealer.isTrash(stack) : (trash = true);
                if (trash) {
                    Cheststealer.mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, player);
                    this.timer.reset();
                    return;
                }
            }
            ++index;
        }
    }

    public void set(Set set, TileEntity chest) {
        if (set.size() > 128) {
            set.clear();
        }
        set.add(chest);
    }

    public Vec3 getVec3(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public double getDistToPos(BlockPos pos) {
        return Minecraft.thePlayer.getDistance(pos.getX(), pos.getY(), pos.getZ());
    }

    public boolean isChestEmpty(GuiChest chest) {
        int index = 0;
        while (index < chest.getLowerChestInventory().getSizeInventory()) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null && !Cheststealer.isTrash(stack)) {
                return false;
            }
            ++index;
        }
        return true;
    }

    public boolean isInventoryFull() {
        int index = 9;
        while (index <= 44) {
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
            ++index;
        }
        return true;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static boolean isTrash(ItemStack item) {
        if (item.getItem().getUnlocalizedName().contains("tnt")) return true;
        if (item.getDisplayName().contains("frog")) return true;
        if (item.getItem().getUnlocalizedName().contains("stick")) return true;
        if (item.getItem().getUnlocalizedName().contains("string")) return true;
        if (item.getItem().getUnlocalizedName().contains("flint")) return true;
        if (item.getItem().getUnlocalizedName().contains("feather")) return true;
        if (item.getItem().getUnlocalizedName().contains("bucket")) return true;
        if (item.getItem().getUnlocalizedName().contains("snow")) return true;
        if (item.getItem().getUnlocalizedName().contains("enchant")) return true;
        if (item.getItem().getUnlocalizedName().contains("exp")) return true;
        if (item.getItem().getUnlocalizedName().contains("shears")) return true;
        if (item.getItem().getUnlocalizedName().contains("anvil")) return true;
        if (item.getItem().getUnlocalizedName().contains("torch")) return true;
        if (item.getItem().getUnlocalizedName().contains("seeds")) return true;
        if (item.getItem().getUnlocalizedName().contains("leather")) return true;
        if (item.getItem().getUnlocalizedName().contains("boat")) return true;
        if (item.getItem().getUnlocalizedName().contains("fishing")) return true;
        if (item.getItem().getUnlocalizedName().contains("wheat")) return true;
        if (item.getItem().getUnlocalizedName().contains("flower")) return true;
        if (item.getItem().getUnlocalizedName().contains("record")) return true;
        if (item.getItem().getUnlocalizedName().contains("note")) return true;
        if (item.getItem().getUnlocalizedName().contains("sugar")) return true;
        if (item.getItem().getUnlocalizedName().contains("wire")) return true;
        if (item.getItem().getUnlocalizedName().contains("trip")) return true;
        if (item.getItem().getUnlocalizedName().contains("slime")) return true;
        if (item.getItem().getUnlocalizedName().contains("web")) return true;
        if (item.getItem() instanceof ItemGlassBottle) return true;
        if (item.getItem().getUnlocalizedName().contains("piston")) return true;
        if (item.getItem().getUnlocalizedName().contains("potion")) {
            if (Cheststealer.isBadPotion(item)) return true;
        }
        if (item.getItem() instanceof ItemEgg) return true;
        if (item.getItem() instanceof ItemSnow) return true;
        if (item.getItem().getUnlocalizedName().contains("Raw")) return true;
        return false;
    }

    private static boolean isBadPotion(ItemStack stack) {
        PotionEffect effect;
        if (stack == null) return false;
        if (!(stack.getItem() instanceof ItemPotion)) return false;
        ItemPotion potion = (ItemPotion)stack.getItem();
        if (!ItemPotion.isSplash(stack.getItemDamage())) return false;
        Iterator<PotionEffect> iterator = potion.getEffects(stack).iterator();
        do {
            if (!iterator.hasNext()) return false;
            PotionEffect o = iterator.next();
            effect = o;
            if (effect.getPotionID() == Potion.poison.getId()) return true;
            if (effect.getPotionID() == Potion.harm.getId()) return true;
            if (effect.getPotionID() == Potion.moveSlowdown.getId()) return true;
        } while (effect.getPotionID() != Potion.weakness.getId());
        return true;
    }
}

