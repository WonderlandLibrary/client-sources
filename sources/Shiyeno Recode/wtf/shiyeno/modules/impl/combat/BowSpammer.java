package wtf.shiyeno.modules.impl.combat;

import net.minecraft.item.BowItem;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "BowSpammer",
        type = Type.Combat
)
public class BowSpammer extends Function {
    private boolean restart = false;
    private final SliderSetting tickBow = new SliderSetting("Скорость", 2.0F, 2.0F, 3.0F, 0.05F);
    private final TimerUtil timerUtil = new TimerUtil();

    public BowSpammer() {
        this.addSettings(new Setting[]{this.tickBow});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate eventUpdate) {
            this.handleUpdateEvent(eventUpdate);
        }
    }

    private void handleUpdateEvent(EventUpdate eventUpdate) {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof BowItem && mc.player.isHandActive() && (float)mc.player.getItemInUseMaxCount() > this.tickBow.getValue().floatValue()) {
            mc.playerController.onStoppedUsingItem(mc.player);
        }

        if (mc.player.inventory.getCurrentItem().getItem() instanceof BowItem && !mc.player.isHandActive() && mc.gameSettings.keyBindUseItem.isKeyDown() && !mc.player.isHandActive() && mc.player.getHeldItemMainhand().getItem() instanceof BowItem) {
            mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
        }
    }
}