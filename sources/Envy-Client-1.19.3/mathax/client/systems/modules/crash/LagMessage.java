package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Jebus.ChatUtils;
import mathax.client.utils.Utils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

import java.util.List;
import java.util.Random;

public class LagMessage extends Module {

    private final Setting<Integer> messageLength;

    private final Setting<Boolean> keepSending;

    private final Setting<Integer> delay;

    private final Setting<Boolean> whisper;

    private final Setting<Boolean> autoDisable;

    public LagMessage() {
        super(Categories.Crash, Items.BIRCH_SIGN, "Lag Message", "CRYSTAL || Sends a large message of complex characters that can lag other people in a server.");

        SettingGroup sgGeneral = settings.getDefaultGroup();

        messageLength = sgGeneral.add(new IntSetting.Builder()
            .name("message-length")
            .description("The length of the message.")
            .defaultValue(200)
            .min(1)
            .sliderMin(1)
            .sliderMax(1000)
            .build());

        keepSending = sgGeneral.add(new BoolSetting.Builder()
            .name("keep-sending")
            .description("Keeps sending the lag messages repeatedly.")
            .defaultValue(false)
            .build());

        delay = sgGeneral.add(new IntSetting.Builder()
            .name("delay")
            .description("The delay between lag messages in ticks.")
            .defaultValue(100)
            .min(0)
            .sliderMax(1000)
            .visible(keepSending::get)
            .build());

        whisper = sgGeneral.add(new BoolSetting.Builder()
            .name("whisper")
            .description("Whispers the lag message to a random person on the server.")
            .defaultValue(false)
            .build());

        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build());
    }

    private int timer;

    @Override
    public boolean onActivate() {
        if (Utils.canUpdate() && !keepSending.get()) {
            if (!whisper.get()) {
                sendLagMessage();
            }
            else {
                sendLagWhisper();
            }
            toggle();
        }
        if (!keepSending.get()) {
            return false;
        }
        timer = delay.get();
        return false;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (timer <= 0) {
            if (Utils.canUpdate() && keepSending.get()) {
                if (!whisper.get()) {
                    sendLagMessage();
                }
                else {
                    sendLagWhisper();
                }
            }
            timer = delay.get();
        }
        else {
            timer--;
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get() || !isActive()) {
            return;
        }
        toggle();
    }

    private void sendLagMessage() {
        String message = generateLagMessage();
        ChatUtils.sendPlayerMsg(message);
    }

    private void sendLagWhisper() {
        assert mc.world != null;
        if(mc.world.getPlayers() != null)
        {
            List<AbstractClientPlayerEntity> players = mc.world.getPlayers();
            PlayerEntity player = players.get(new Random().nextInt(players.size()));
            String message = generateLagMessage();
            ChatUtils.sendPlayerMsg("/msg " + player.getGameProfile().getName() + " " + message);
        }

    }

    private String generateLagMessage() {
        StringBuilder message = new StringBuilder();
        int i = 0;
        while (true) {
            if (i < messageLength.get()) {
                message.append((char) (Math.floor(Math.random() * 0x1D300) + 0x800));
                i++;
            } else {
                break;
            }
        }
        return message.toString();
    }

}
