package vestige.module.impl.player;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;

public class AutoTool extends Module {
   private int oldSlot;
   private boolean wasDigging;
   private final BooleanSetting spoof = new BooleanSetting("Item spoof", false);

   public AutoTool() {
      super("AutoTool", Category.ULTILITY);
      this.addSettings(new AbstractSetting[]{this.spoof});
   }

   public boolean onDisable() {
      if (this.wasDigging) {
         mc.thePlayer.inventory.currentItem = this.oldSlot;
         this.wasDigging = false;
      }

      Flap.instance.getSlotSpoofHandler().stopSpoofing();
      return false;
   }

   @Listener(3)
   public void onTick(TickEvent event) {
      if ((Mouse.isButtonDown(0) || mc.gameSettings.keyBindAttack.isKeyDown()) && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         Block block = mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
         float strength = 0.0F;
         if (!this.wasDigging) {
            this.oldSlot = mc.thePlayer.inventory.currentItem;
            if (this.spoof.isEnabled()) {
               Flap.instance.getSlotSpoofHandler().startSpoofing(this.oldSlot);
            }
         }

         for(int i = 0; i <= 8; ++i) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null) {
               float slotStrength = stack.getStrVsBlock(block);
               if (slotStrength > strength) {
                  mc.thePlayer.inventory.currentItem = i;
                  strength = slotStrength;
               }
            }
         }

         this.wasDigging = true;
      } else if (this.wasDigging) {
         mc.thePlayer.inventory.currentItem = this.oldSlot;
         Flap.instance.getSlotSpoofHandler().stopSpoofing();
         this.wasDigging = false;
      } else {
         this.oldSlot = mc.thePlayer.inventory.currentItem;
      }

   }
}
