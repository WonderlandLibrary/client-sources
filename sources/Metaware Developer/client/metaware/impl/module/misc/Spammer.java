package client.metaware.impl.module.misc;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.system.TimerUtil;
import org.apache.commons.lang3.RandomStringUtils;

@ModuleInfo(name = "Spammer", renderName = "Spammer", category = Category.PLAYER)
public class Spammer extends Module {

    public DoubleProperty delay = new DoubleProperty("Seconds", 3, 1, 10, 1);
    private TimerUtil timer = new TimerUtil();

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        long delay = this.delay.getValue().longValue() * 1000L;
        if(timer.delay(delay)) {
            mc.thePlayer.sendChatMessage("Whiz Client ONTOP | discord․gg/DmTsST2K7R [" + RandomStringUtils.randomAlphanumeric(3) + "]");
            timer.reset();
        }
    };
}
