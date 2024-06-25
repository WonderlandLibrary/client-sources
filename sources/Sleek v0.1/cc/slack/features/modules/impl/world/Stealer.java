package cc.slack.features.modules.impl.world;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.MathTimerUtil;
import cc.slack.utils.player.PlayerUtil;
import io.github.nevalackin.radbus.Listen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

@ModuleInfo(
   name = "Stealer",
   category = Category.WORLD
)
public class Stealer extends Module {
   private final NumberValue<Double> openDelaymax = new NumberValue("Open Delay Max", 150.0D, 25.0D, 1000.0D, 1.0D);
   private NumberValue<Double> openDelaymin = new NumberValue("Open Delay Min", 125.0D, 25.0D, 1000.0D, 1.0D);
   private final NumberValue<Double> stealDelaymax = new NumberValue("Steal Delay Max", 150.0D, 25.0D, 1000.0D, 1.0D);
   private NumberValue<Double> stealDelaymin = new NumberValue("Steal Delay Min", 125.0D, 25.0D, 1000.0D, 1.0D);
   private final BooleanValue autoClose = new BooleanValue("Auto Close", true);
   private final NumberValue<Double> autocloseDelaymax = new NumberValue("Auto Close Delay Max", 0.0D, 0.0D, 1000.0D, 1.0D);
   private NumberValue<Double> autocloseDelaymin = new NumberValue("Auto Close Delay Min", 0.0D, 0.0D, 1000.0D, 1.0D);
   private final AtomicReference<ArrayList<Stealer.Slot>> sortedSlots = new AtomicReference();
   private final AtomicReference<ContainerChest> chest = new AtomicReference();
   private final AtomicBoolean inChest = new AtomicBoolean(false);
   private final MathTimerUtil delayTimer = new MathTimerUtil(0L);
   private final MathTimerUtil closeTimer = new MathTimerUtil(0L);
   private final List<Item> whiteListedItems;

   public Stealer() {
      this.whiteListedItems = Arrays.asList(Items.milk_bucket, Items.golden_apple, Items.potionitem, Items.ender_pearl, Items.water_bucket, Items.arrow, Items.bow);
      this.addSettings(new Value[]{this.openDelaymax, this.openDelaymin, this.stealDelaymax, this.stealDelaymin, this.autoClose, this.autocloseDelaymax, this.autocloseDelaymin});
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if ((Double)this.openDelaymin.getValue() > (Double)this.openDelaymax.getValue()) {
         this.openDelaymin = this.openDelaymax;
      }

      if ((Double)this.stealDelaymin.getValue() > (Double)this.stealDelaymax.getValue()) {
         this.stealDelaymin = this.stealDelaymax;
      }

      if ((Double)this.autocloseDelaymin.getValue() > (Double)this.autocloseDelaymax.getValue()) {
         this.autocloseDelaymin = this.autocloseDelaymax;
      }

      if (mc.getCurrentScreen() != null && mc.getPlayer().inventoryContainer != null && mc.getPlayer().inventoryContainer instanceof ContainerPlayer && mc.getCurrentScreen() instanceof GuiChest) {
         if (!this.inChest.get()) {
            this.chest.set((ContainerChest)mc.getPlayer().openContainer);
            this.delayTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble((Double)this.openDelaymin.getValue(), (Double)this.openDelaymax.getValue() + 0.01D));
            this.delayTimer.start();
            this.generatePath((ContainerChest)this.chest.get());
            this.inChest.set(true);
         }

         if (this.inChest.get() && this.sortedSlots.get() != null && !((ArrayList)this.sortedSlots.get()).isEmpty() && this.delayTimer.hasFinished()) {
            this.clickSlot(((Stealer.Slot)((ArrayList)this.sortedSlots.get()).get(0)).s);
            this.delayTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble((Double)this.stealDelaymin.getValue(), (Double)this.stealDelaymax.getValue() + 0.01D));
            this.delayTimer.start();
            ((ArrayList)this.sortedSlots.get()).remove(0);
         }

         if (this.sortedSlots.get() != null && ((ArrayList)this.sortedSlots.get()).isEmpty() && (Boolean)this.autoClose.getValue()) {
            if (this.closeTimer.firstFinish()) {
               mc.getPlayer().closeScreen();
               this.inChest.set(false);
            } else {
               this.closeTimer.setCooldown((long)ThreadLocalRandom.current().nextDouble((Double)this.autocloseDelaymin.getValue(), (Double)this.autocloseDelaymax.getValue() + 0.01D));
               this.closeTimer.start();
            }
         }
      } else {
         this.inChest.set(false);
      }

   }

   private void generatePath(ContainerChest chest) {
      ArrayList<Stealer.Slot> slots = (ArrayList)IntStream.range(0, chest.getLowerChestInventory().getSizeInventory()).mapToObj((i) -> {
         ItemStack itemStack = (ItemStack)chest.getInventory().get(i);
         if (itemStack != null) {
            Predicate<ItemStack> stealCondition = (stack) -> {
               Item item = stack.getItem();
               return item instanceof ItemSword && (PlayerUtil.getBestSword() == null || PlayerUtil.isBetterSword(stack, PlayerUtil.getBestSword())) || item instanceof ItemAxe && (PlayerUtil.getBestAxe() == null || PlayerUtil.isBetterTool(stack, PlayerUtil.getBestAxe(), Blocks.planks)) || item instanceof ItemPickaxe && (PlayerUtil.getBestAxe() == null || PlayerUtil.isBetterTool(stack, PlayerUtil.getBestAxe(), Blocks.stone)) || item instanceof ItemBlock || item instanceof ItemArmor || this.whiteListedItems.contains(item);
            };
            if (stealCondition.test(itemStack)) {
               return new Stealer.Slot(i);
            }
         }

         return null;
      }).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
      Stealer.Slot[] sorted = this.sort((Stealer.Slot[])slots.toArray(new Stealer.Slot[0]));
      this.sortedSlots.set(new ArrayList(Arrays.asList(sorted)));
   }

   private Stealer.Slot[] sort(Stealer.Slot[] in) {
      if (in != null && in.length != 0) {
         Stealer.Slot[] out = new Stealer.Slot[in.length];
         Stealer.Slot current = in[ThreadLocalRandom.current().nextInt(0, in.length)];

         for(int i = 0; i < in.length; ++i) {
            if (i == in.length - 1) {
               out[in.length - 1] = (Stealer.Slot)Arrays.stream(in).filter((p) -> {
                  return !p.visited;
               }).findAny().orElse((Object)null);
               break;
            }

            out[i] = current;
            current.visit();
            Stealer.Slot next = (Stealer.Slot)Arrays.stream(in).filter((p) -> {
               return !p.visited;
            }).min(Comparator.comparingDouble((p) -> {
               return p.getDistance(current);
            })).orElse((Object)null);
            current = next;
         }

         return out;
      } else {
         return in;
      }
   }

   private void clickSlot(int x) {
      mc.getPlayerController().windowClick(mc.getPlayer().openContainer.windowId, x, 0, 1, mc.getPlayer());
   }

   static class Slot {
      final int x;
      final int y;
      final int s;
      boolean visited;

      Slot(int s) {
         this.x = (s + 1) % 10;
         this.y = s / 9;
         this.s = s;
      }

      public double getDistance(Stealer.Slot s) {
         return (double)(Math.abs(this.x - s.x) + Math.abs(this.y - s.y));
      }

      public void visit() {
         this.visited = true;
      }
   }
}
