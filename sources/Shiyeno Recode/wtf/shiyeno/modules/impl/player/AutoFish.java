package wtf.shiyeno.modules.impl.player;

import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.Hand;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "AutoFish",
        type = Type.Player
)
public class AutoFish extends Function {
    private final TimerUtil delay = new TimerUtil();
    private boolean isHooked = false;
    private boolean needToHook = false;

    public AutoFish() {
    }

    public void onEvent(Event event) {
        if (mc.player != null && mc.world != null) {
            if (event instanceof EventPacket) {
                EventPacket e = (EventPacket)event;
                IPacket var4 = e.getPacket();
                if (var4 instanceof SPlaySoundEffectPacket) {
                    SPlaySoundEffectPacket p = (SPlaySoundEffectPacket)var4;
                    if (p.getSound().getName().getPath().equals("entity.fishing_bobber.splash")) {
                        this.isHooked = true;
                        this.delay.reset();
                    }
                }
            }

            if (event instanceof EventUpdate) {
                EventUpdate e = (EventUpdate)event;
                if (this.isHooked) {
                    mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    this.isHooked = false;
                    this.needToHook = true;
                }

                if (this.needToHook) {
                    mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    this.needToHook = false;
                }
            }
        }
    }

    protected void onDisable() {
        super.onDisable();
        this.delay.reset();
        this.isHooked = false;
        this.needToHook = false;
    }
}