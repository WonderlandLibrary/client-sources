package net.augustus.modules.misc;

import java.awt.Color;
import java.io.IOException;
import net.augustus.events.EventClick;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventRender3D;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AutoPlay extends Module {
   public final BooleanValue cancelSound = new BooleanValue(1, "CancelSound", this, true);
   private final TimeHelper delayTimer = new TimeHelper();
   public float[] rots = new float[2];
   public float[] lastRots = new float[2];
   public StringValue mode = new StringValue(2, "Mode", this, "MinemenRanked", new String[]{"MinemenUnranked", "MinemenRanked"});
   public StringValue gameMode = new StringValue(3, "Gamemode", this, "Sumo", new String[]{"Sumo", "Boxing"});
   private float volume;

   public AutoPlay() {
      super("AutoPlay", Color.green, Categorys.MISC);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.volume = mc.gameSettings.getSoundLevel(SoundCategory.MASTER);
      if (this.cancelSound.getBoolean()) {
         mc.gameSettings.setSoundLevel(SoundCategory.MASTER, 0.0F);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      mc.gameSettings.setSoundLevel(SoundCategory.MASTER, this.volume);
   }

   @EventTarget
   public void onEventRender3D(EventRender3D eventRender3D) {
      if (mc.currentScreen instanceof GuiIngameMenu) {
         mc.currentScreen = null;
      }
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      if (mc.currentScreen == null) {
         ItemStack currStack = mc.thePlayer.inventoryContainer.getSlot(mc.thePlayer.inventory.currentItem + 36).getStack();
         if (currStack != null) {
            if (currStack.getDisplayName().contains("Unranked") && this.mode.getSelected().equals("MinemenUnranked")) {
               mc.gameSettings.keyBindUseItem.setPressTime(1);
            }

            if (!currStack.getDisplayName().contains("Unranked")
               && currStack.getDisplayName().contains("Ranked")
               && this.mode.getSelected().equals("MinemenRanked")) {
               mc.gameSettings.keyBindUseItem.setPressTime(1);
            }

            if (currStack.getDisplayName().contains("Play Again") && this.mode.getSelected().equals("MinemenUnranked")) {
               mc.gameSettings.keyBindUseItem.setPressTime(1);
            }
         }
      }
   }

   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
      if (mc.currentScreen == null) {
         this.delayTimer.reset();

         for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null) {
               Item item = itemStack.getItem();
               String var5 = this.mode.getSelected();
               switch(var5) {
                  case "MinemenUnranked":
                     if (itemStack.getDisplayName().contains("Unranked")) {
                        mc.thePlayer.inventory.currentItem = i - 36;
                     } else if (itemStack.getDisplayName().contains("Play Again")) {
                        mc.thePlayer.inventory.currentItem = i - 36;
                     }
                     break;
                  case "MinemenRanked":
                     if (!itemStack.getDisplayName().contains("Unranked") && itemStack.getDisplayName().contains("Ranked")) {
                        mc.thePlayer.inventory.currentItem = i - 36;
                     }
               }
            }
         }
      } else if (mc.currentScreen instanceof GuiChest && this.delayTimer.reached((long)(400.0 + RandomUtil.nextSecureInt(-50, 50)))) {
         GuiChest guiChest = (GuiChest)mc.currentScreen;

         for(int i = 0; i < guiChest.inventorySlots.inventorySlots.size(); ++i) {
            ItemStack itemStack = guiChest.inventorySlots.getSlot(i).getStack();
            if (itemStack != null) {
               String var10 = this.mode.getSelected();
               switch(var10) {
                  case "MinemenUnranked":
                     if (itemStack.getDisplayName().contains("Solo")) {
                        this.clickItem(i);
                        break;
                     }
                  case "MinemenRanked":
                     if (itemStack.getDisplayName().contains(this.gameMode.getSelected())) {
                        this.clickItem(i);
                     }
               }
            }
         }
      }
   }

   private void clickItem(int slot) {
      GuiChest chest = (GuiChest)mc.currentScreen;
      Slot slot1 = chest.inventorySlots.getSlot(slot);

      try {
         chest.mouseClicked(slot1.xDisplayPosition + 2 + chest.guiLeft, slot1.yDisplayPosition + 2 + chest.guiTop, 0);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.delayTimer.reset();
   }
}
