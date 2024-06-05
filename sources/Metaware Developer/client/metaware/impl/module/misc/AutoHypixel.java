package client.metaware.impl.module.misc;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.network.PacketEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.ServerUtil;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S45PacketTitle;

@ModuleInfo(name = "AutoHypixel", renderName = "Auto Hypixel", category = Category.PLAYER)
public class AutoHypixel extends Module {

    public final TimerUtil timer = new TimerUtil();
    public int done;


    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
        done = 0;
    }

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if(mc.thePlayer.ticksExisted < 5){
            timer.reset();
            done = 0;
        }
    };

    @EventHandler
    private final Listener<PacketEvent> eventListener = event -> {
      if(event.getPacket() instanceof S45PacketTitle){
          S45PacketTitle s45PacketTitle = event.getPacket();
          String string = s45PacketTitle.getMessage().getUnformattedText().toLowerCase();
          if(!ServerUtil.onServer("hypixel") || string.isEmpty()) return;
          if(string.contains("victory!") || string.contains("victory")){
              if(timer.delay(2500) && done == 0){
                  PacketUtil.packetNoEvent(new C01PacketChatMessage("/play solo_insane"));
                  done++;
                  timer.reset();
              }
          }
      }
    };

}
