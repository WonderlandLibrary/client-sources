package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;

public class AutoSoup extends Module {
   private Timer timer = new Timer();
   private String DROP = "HEADS";
   private String DELAY = "DELAY";
   private String HEALTH = "HEALTH";

   public AutoSoup(ModuleData data) {
      super(data);
      this.settings.put(this.HEALTH, new Setting(this.HEALTH, Integer.valueOf(3), "Maximum health before healing."));
      this.settings.put(this.DELAY, new Setting(this.DELAY, Integer.valueOf(350), "Delay before healing again.", 50.0D, 100.0D, 1000.0D));
      this.settings.put(this.DROP, new Setting(this.DROP, false, "Use player heads."));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.MEDIUM;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         if (em.isPost()) {
            int soupSlot = this.getSoupFromInventory();
            if (soupSlot != -1 && mc.thePlayer.getHealth() < ((Number)((Setting)this.settings.get(this.HEALTH)).getValue()).floatValue() && this.timer.delay(((Number)((Setting)this.settings.get(this.DELAY)).getValue()).floatValue())) {
               int swapTo = 6;
               if (soupSlot >= 9) {
                  this.swap(this.getSoupFromInventory(), 6);
               }

               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(6));
               mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
               this.timer.reset();
            }
         }
      }

   }

   protected void swap(int slot, int hotbarNum) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
   }

   private int getSoupFromInventory() {
      Minecraft mc = Minecraft.getMinecraft();
      int soup = -1;
      int counter = 0;

      for(int i = 1; i < 45; ++i) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            boolean shouldApple = ((Boolean)((Setting)this.settings.get(this.DROP)).getValue()).booleanValue() && (Item.getIdFromItem(item) == Item.getIdFromItem(Items.skull) || Item.getIdFromItem(item) == Item.getIdFromItem(Items.baked_potato)) && (!mc.thePlayer.isPotionActive(Potion.regeneration) || mc.thePlayer.isPotionActive(Potion.regeneration) && mc.thePlayer.getActivePotionEffect(Potion.regeneration).getDuration() <= 1 || !mc.thePlayer.isPotionActive(Potion.absorption) && is.stackSize > 1);
            if (Item.getIdFromItem(item) == 282 || shouldApple) {
               ++counter;
               soup = i;
            }
         }
      }

      return soup;
   }
}
