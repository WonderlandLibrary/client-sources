package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.LaunchPlayer;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class MatrixElytra extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Double> BoostAmount = sgGeneral.add(new DoubleSetting.Builder()
        .name("Boost")
        .description("Boost Amount")
        .defaultValue(2)
        .range(0.1, 5)
        .sliderRange(0.1, 5)
        .build()
    );
    private final Setting<Double> FallDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("Fall Distance")
        .description("Amount of fall distance needed to trigger the boost.")
        .defaultValue(0.5)
        .range(0.1, 1.5)
        .sliderRange(0.1, 1.5)
        .build()
    );
    private final Setting<MatrixElytra.Mode> mode = sgGeneral.add(new EnumSetting.Builder<MatrixElytra.Mode>()
        .name("Boost Mode")
        .description("Mode for boosting")
        .defaultValue(Mode.Client)
        .build()
    );
    private final Setting<Boolean> AirBoost = sgGeneral.add(new BoolSetting.Builder()
        .name("AirBoost")
        .description("Will boost when you press space.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Boolean> SlowDown = sgGeneral.add(new BoolSetting.Builder()
        .name("SlowDown")
        .description("Slow down when you hold shift/crouch.")
        .defaultValue(false)
        .build()
    );
    private final Setting<Double> AirBoostAmount = sgGeneral.add(new DoubleSetting.Builder()
        .name("Air Boost")
        .description("Air Boost Amount")
        .defaultValue(0.5)
        .range(0.1, 5)
        .sliderRange(0.1, 5)
        .visible(() -> AirBoost.get())
        .build()
    );
    int timer = 0;
    boolean launch = false;
    boolean Boosted = false;
    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mode.get() == Mode.Client) {
            assert mc.player != null;
            if (!mc.player.isFallFlying() && mc.player.isOnGround()) {
                mc.player.jump();
            }
            if (mc.player.fallDistance > FallDistance.get() && !mc.player.isFallFlying() && !launch) {
                mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                LaunchPlayer.MatrixElytra(BoostAmount.get());
                launch = true;
            }
            if (mc.player.isFallFlying()) {
                launch = false;
            }
            if (AirBoost.get() && mc.player.isFallFlying()) {
                if (mc.options.jumpKey.isPressed() && !Boosted) {
                    LaunchPlayer.MatrixElytra(AirBoostAmount.get());
                    Boosted = true;
                }
                else if (!mc.options.jumpKey.isPressed()) {
                    Boosted = false;
                }

            }
            if (SlowDown.get()) {
                if (mc.options.sneakKey.isPressed()) {
                    LaunchPlayer.MatrixElytra(0.1);
                }
            }
        }
    }
    public enum Mode {
        Client("Client"),

        Packet("Packet");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    public MatrixElytra() {
        super(Categories.Experimental, Items.ELYTRA, "MatrixElytra", "A bypass to allow for elytra flight on matrix.");
    }
}
