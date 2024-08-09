package mathax.client.systems.modules.misc;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.EnumSetting;
import mathax.client.settings.IntSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class PacketHeadYaw extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final Setting<Integer> yaw = sgGeneral.add(new IntSetting.Builder()
        .name("Yaw")
        .description("Yaw of your head.")
        .defaultValue(1)
        .range(1, 360)
        .sliderRange(1, 360)
        .build()
    );
    private final Setting<PacketHeadYaw.Mode> mode = sgGeneral.add(new EnumSetting.Builder<PacketHeadYaw.Mode>()
        .name("mode")
        .description("Decide from packet or client sided rotation.")
        .defaultValue(PacketHeadYaw.Mode.Packet)
        .build()
    );
    @EventHandler
    public void onTick(TickEvent.Post event) {
       if (mode.get() == Mode.Packet) {
           float yawFloatPacket = yaw.get().floatValue();
           assert mc.player != null;
           mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.headYaw = yawFloatPacket,mc.player.getPitch(), mc.player.isOnGround()));
        }
       else if (mode.get() == Mode.Client) {
           float yawFloatClient = yaw.get().floatValue();
           assert mc.player != null;
           mc.player.setHeadYaw(yawFloatClient);
       }
    }
    public enum Mode {
        Packet("Packet"),

        Client("Client");
        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
    public PacketHeadYaw() {
        super(Categories.Fun, Items.PAPER, "PacketHeadYaw", "Uses a packet or client rotations");
    }
}
