package io.github.liticane.clients.feature.module.impl.player;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreUpdateEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.util.misc.TimerUtil;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

@Module.Info(name = "ChestStealer",category = Module.Category.PLAYER)
public class ChestStealer extends Module {
    public NumberProperty delay = new NumberProperty("Delay", this, 100, 0, 1000, 50);
    private TimerUtil timer = new TimerUtil();

  @SubscribeEvent
  private final EventListener<PreUpdateEvent> onMotion = event -> {
      if (this.mc.player.openContainer != null && this.mc.player.openContainer instanceof ContainerChest) {
          ContainerChest container = (ContainerChest)this.mc.player.openContainer;
          int i = 0;
          while (i < container.getLowerChestInventory().getSizeInventory()) {
              if (container.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasTimeElapsed(this.delay.getValue())) {
                  this.mc.controller.windowClick(container.windowId, i, 0, 1, this.mc.player);
                  this.timer.reset();
              }
              ++i;
          }
          if (this.isEmpty()) {
              this.mc.player.closeScreen();
          }
      }
  };

    private boolean isEmpty() {
        if (this.mc.player.openContainer != null && this.mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest)this.mc.player.openContainer;
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
}
