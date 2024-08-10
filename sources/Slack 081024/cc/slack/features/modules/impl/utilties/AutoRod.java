package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.PlayerUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.Item;


@ModuleInfo(
        name = "AutoRod",
        category = Category.UTILITIES
)
public class AutoRod extends Module {


    private final NumberValue<Double> delay = new NumberValue<>("Delay", 100.0D, 50.0D, 1000.0D, 50.0D);
    private final BooleanValue autodisable = new BooleanValue("AutoDisable", false);

    private final TimeUtil time = new TimeUtil();
    private final TimeUtil time2 = new TimeUtil();
    private Boolean switchBack = false;
    private Boolean useRod = false;

    public AutoRod() {
        addSettings(delay, autodisable);
    }

  @SuppressWarnings("unused")
  @Listen
  public void onUpdate (UpdateEvent event) {
      int item = Item.getIdFromItem(mc.thePlayer.getHeldItem().getItem());
      float rodDelay = (delay.getValue()).floatValue();
      if (mc.currentScreen == null) {
          if (!(Boolean) autodisable.getValue()) {
              if (!useRod && item == 346) {
                  Rod();
                  useRod = true;
              }

              if (time.isDelayComplete(rodDelay - 50.0F) && switchBack) {
                  PlayerUtil.switchBack();
                  switchBack = false;
              }

              if (time.isDelayComplete(rodDelay) && useRod) {
                  useRod = false;
              }
          } else if (item == 346) {
              if (time2.isDelayComplete(rodDelay + 200.0F)) {
                  Rod();
                  time2.reset();
              }

              if (time.isDelayComplete(rodDelay)) {
                  mc.thePlayer.inventory.currentItem = PlayerUtil.bestWeapon(mc.thePlayer);
                  time.reset();
                  toggle();
              }
          } else if (time.isDelayComplete(100L)) {
              PlayerUtil.switchToRod();
              time.reset();
          }
      }
  }

    private void Rod() {
        int rod = PlayerUtil.findRod(36, 45);
        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventoryContainer.getSlot(rod).getStack());
        switchBack = true;
        time.reset();
    }
}
