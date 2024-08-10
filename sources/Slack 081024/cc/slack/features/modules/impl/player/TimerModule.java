// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.PrintUtil;
import cc.slack.utils.other.TimeUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(
        name = "Timer",
        category = Category.PLAYER
)
public class TimerModule extends Module {

    private final ModeValue<String> mode = new ModeValue<>(new String[]{"Normal", "Balance"});
    private final NumberValue<Float> speed = new NumberValue<>("Speed", 1.5f, 0.1f, 10.0f, 0.25f);

    private int balance;
    public boolean balancing;
    private final TimeUtil timer = new TimeUtil();

    public TimerModule() {
        super();
        addSettings(mode, speed);
    }

    @Override
    public void onEnable() {
        balance = 0;
        balancing = false;
        timer.reset();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate(UpdateEvent event) {
        switch (mode.getValue().toLowerCase()) {
            case "balance":
                if (mc.thePlayer != null && mc.thePlayer.ticksExisted % 20 == 0) {
                    PrintUtil.debugMessage("Balance: " + balance);
                }
                break;
            case "normal":
                mc.timer.timerSpeed = speed.getValue();
                break;
        }
    }

    @Listen
    public void onPacket(PacketEvent event) {
        if (event.getDirection() != PacketDirection.INCOMING) return;

        if (mode.getValue().equalsIgnoreCase("balance")) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                if (!(event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition ||
                        event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook ||
                        event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook)) {
                    balancing = true;
                    balance--;
                    event.cancel();
                } else {
                    balancing = false;
                    balance++;
                }
            }
        }
    }

    @Listen
    public void onRender(RenderEvent event) {
        if (event.state != RenderEvent.State.RENDER_2D) return;
        if (mode.getValue().equalsIgnoreCase("balance")) {
            if (timer.hasReached(50)) {
                balance--;
                timer.reset();
            }
        }
    }


}