package vestige.module.impl.movement;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import vestige.event.Listener;
import vestige.event.impl.EntityActionEvent;
import vestige.event.impl.PacketSendEvent;
import vestige.event.impl.TickEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.network.PacketUtil;

public class InventoryMove extends Module {
   private final ModeSetting noSprint = new ModeSetting("No sprint", "Disabled", new String[]{"Disabled", "Enabled", "Spoof"});
   private final BooleanSetting hyixel = new BooleanSetting("Hypixel", false);
   private final BooleanSetting nobadpackets = new BooleanSetting("No Bad Packets", false);
   private boolean hadInventoryOpened;
   boolean c16 = false;
   boolean c0d = false;

   public InventoryMove() {
      super("InvMove", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.noSprint, this.hyixel, this.nobadpackets});
   }

   public boolean onDisable() {
      return false;
   }

   @Listener
   public void onSend(PacketSendEvent event) {
      if (this.nobadpackets.isEnabled()) {
         if (event.getPacket() instanceof C16PacketClientStatus) {
            if (this.c16) {
               event.setCancelled(true);
            }

            this.c16 = true;
         }

         if (event.getPacket() instanceof C0DPacketCloseWindow) {
            if (this.c0d) {
               event.setCancelled(true);
            }

            this.c0d = true;
         }
      }

   }

   @Listener(3)
   public void onTick(TickEvent event) {
      if (this.isInventoryOpened()) {
         this.allowMove();
         if (this.noSprint.is("Enabled")) {
            mc.gameSettings.keyBindSprint.pressed = false;
            mc.thePlayer.setSprinting(false);
         }
      } else if (this.hadInventoryOpened) {
         this.allowMove();
         this.hadInventoryOpened = false;
      }

   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (this.isInventoryOpened()) {
         this.allowMove();
         if (this.noSprint.is("Enabled")) {
            mc.gameSettings.keyBindSprint.pressed = false;
            mc.thePlayer.setSprinting(false);
         }
      }

      if (this.hyixel.isEnabled() && mc.currentScreen instanceof GuiInventory) {
         if (mc.thePlayer.ticksExisted % (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 3 : 4) == 0) {
            PacketUtil.sendPacket(new C0DPacketCloseWindow());
         } else if (mc.thePlayer.ticksExisted % (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 3 : 4) == 1) {
            PacketUtil.sendPacket(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
         }

         this.allowMove();
      }

   }

   @Listener
   public void onEntityAction(EntityActionEvent event) {
      if (this.isInventoryOpened()) {
         this.allowMove();
         if (this.noSprint.is("Spoof")) {
            event.setSprinting(false);
         }
      }

   }

   private boolean isInventoryOpened() {
      return mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChest;
   }

   private void allowMove() {
      GameSettings settings = mc.gameSettings;
      KeyBinding[] keys = new KeyBinding[]{settings.keyBindForward, settings.keyBindBack, settings.keyBindLeft, settings.keyBindRight, settings.keyBindJump};
      KeyBinding[] var3 = keys;
      int var4 = keys.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         KeyBinding key = var3[var5];
         key.pressed = Keyboard.isKeyDown(key.getKeyCode());
      }

      this.hadInventoryOpened = true;
   }
}
