package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;

@ModuleInfo(name = "AutoTool", renderName = "Auto Tool", category = Category.PLAYER)
public class AutoTool extends Module {
    private int oldSlot, tick;

    public Property<Boolean> switchBackToMainSlot = new Property<>("Switch Back", true);

    @EventHandler
    private final Listener<UpdatePlayerEvent> eventListener = event -> {
      if(event.isPre()){
          if(mc.playerController.isBreakingBlock()){
              tick++;

              if(tick == 1){
                  oldSlot = mc.thePlayer.inventory.currentItem;
              }

              mc.thePlayer.updateHeldTool(mc.objectMouseOver.getBlockPos());
          }else if(tick > 0){
              if(switchBackToMainSlot.getValue()){
                  mc.thePlayer.inventory.currentItem = oldSlot;
              }
              tick = 0;
          }
      }
    };


}
