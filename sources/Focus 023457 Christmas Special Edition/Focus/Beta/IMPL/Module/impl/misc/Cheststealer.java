
package Focus.Beta.IMPL.Module.impl.misc;


import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.UTILS.world.Timer;

public class Cheststealer extends Module {


	Timer timer = new Timer();

    private Numbers<Double> delay = new Numbers<Double>("Max Delay", "Max Delay", 4.0, 1.0, 10.0, 1.0);
    private Numbers<Double> AuraRange = new Numbers<Double>("Aura Range", "Aura Range", 1.0, 1.0, 7.0, 1.0);
    public Option<Boolean> aura = new Option("Aura", "Aura", false);
    public Option<Boolean> filter = new Option("Filter", "Filter", true);
    public Option<Boolean> titleCheck = new Option("Title Check", "Title Check", true);
    public Option<Boolean> autoclose = new Option("Auto Close", "AutoClose", true);
    public Option<Boolean> silent = new Option("Silent", "Silent", false);

    public Cheststealer() {
        super("Stealer", new String[]{"cheststeal", "chests", "stealer", "cheststealer"}, Type.MISC, "Steal items from chests");
        this.addValues(delay, AuraRange, silent, autoclose, titleCheck, aura, filter);
    }


    @EventHandler
    public void onMotionUpdate(final EventPreUpdate event){
        final int delay = (int) (this.delay.getValue() * 50);
        final EntityPlayerSP player = mc.thePlayer;
        int index;
        if(mc.currentScreen instanceof GuiChest){
            final GuiChest chest = (GuiChest) mc.currentScreen;
            boolean titleCheckk = this.titleCheck.getValue() ? (chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Contai") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Crate")) || chest.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("LOW") : true;
            if (titleCheckk) {
                if (autoclose.getValue()) {
                    if (isChestEmpty(chest) || isInventoryFull()) {
                        player.closeScreen();
                        return;
                    }
                }
                for (index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
                    final ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
                    if (stack != null && timer.reach(delay - ThreadLocalRandom.current().nextInt(0, 250))) {
                        boolean trash = filter.getValue()  ? !isTrash(stack) : true;
                        if (trash) {
                            mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, player);
                            timer.reset();
                            break;
                        }
                    }
                }
            }
        }
    }


    public final Set openedChests = new HashSet();


    public void set(Set set, TileEntity chest) {
        if (set.size() > 128) {
            set.clear();
        }
        set.add(chest);
    }

    public Vec3 getVec3(BlockPos pos) {
        return new Vec3((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
    }

    public double getDistToPos(BlockPos pos) {
        return mc.thePlayer.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
    }

    public boolean isChestEmpty(final GuiChest chest) {
        for (int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
            ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
            if (stack != null)

                if (!isTrash(stack) )
                    return false;
        }
        return true;
    }

    public boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }
        return true;
    }

    public void onEnable() {
        super.onEnable();

    }

    public void onDisable() {
        super.onDisable();
    }

    public static boolean isTrash(ItemStack item) {
        return ((item.getItem().getUnlocalizedName().contains("tnt")) || item.getDisplayName().contains("frog") ||
                (item.getItem().getUnlocalizedName().contains("stick")) ||
                (item.getItem().getUnlocalizedName().contains("string")) || (item.getItem().getUnlocalizedName().contains("flint")) ||
                (item.getItem().getUnlocalizedName().contains("feather")) || (item.getItem().getUnlocalizedName().contains("bucket")) ||
                (item.getItem().getUnlocalizedName().contains("snow")) || (item.getItem().getUnlocalizedName().contains("enchant")) ||
                (item.getItem().getUnlocalizedName().contains("exp")) || (item.getItem().getUnlocalizedName().contains("shears")) ||
                (item.getItem().getUnlocalizedName().contains("anvil")) ||
                (item.getItem().getUnlocalizedName().contains("torch")) || (item.getItem().getUnlocalizedName().contains("seeds")) ||
                (item.getItem().getUnlocalizedName().contains("leather")) || (item.getItem().getUnlocalizedName().contains("boat")) ||
                (item.getItem().getUnlocalizedName().contains("fishing")) || (item.getItem().getUnlocalizedName().contains("wheat")) ||
                (item.getItem().getUnlocalizedName().contains("flower")) || (item.getItem().getUnlocalizedName().contains("record")) ||
                (item.getItem().getUnlocalizedName().contains("note")) || (item.getItem().getUnlocalizedName().contains("sugar")) ||
                (item.getItem().getUnlocalizedName().contains("wire")) || (item.getItem().getUnlocalizedName().contains("trip")) ||
                (item.getItem().getUnlocalizedName().contains("slime")) || (item.getItem().getUnlocalizedName().contains("web")) ||
                ((item.getItem() instanceof ItemGlassBottle)) || (item.getItem().getUnlocalizedName().contains("piston")) ||
                (item.getItem().getUnlocalizedName().contains("potion") && (isBadPotion(item))) ||
                (item.getItem() instanceof ItemEgg || item.getItem() instanceof ItemSnow) ||
                (item.getItem().getUnlocalizedName().contains("Raw")));
    }

    private static boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect) o;
                    if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

