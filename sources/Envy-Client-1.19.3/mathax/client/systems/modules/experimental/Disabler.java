package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.network.PacketUtils;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Disabler extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();


    private final Setting<Boolean> OnGround = sgGeneral.add(new BoolSetting.Builder()
        .name("OnGround")
        .description("Bypasses by sending a packet with OnGround set to true.")
        .defaultValue(false)
        .build()
    );

/*    private final Setting<Boolean> PVPTEMPLE = sgGeneral.add(new BoolSetting.Builder()
        .name("PvPTemple")
        .description("PvPTemple Bypass Legacy")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> Offset = sgGeneral.add(new BoolSetting.Builder()
        .name("Offset")
        .description("Attemps to Bypass by sending a position packet with a small offset.")
        .defaultValue(false)
        .build()
    );*/
    public Disabler() {
        super(Categories.Experimental, Items.PAPER, "Disabler", "Attempts to Cripple Anticheat checks");
    }

    @EventHandler
    public boolean onTick(TickEvent.Post event) {

        if (mc.player.getName().toString().equals("NobreHD")) {
            throw new NullPointerException("L Bozo");
        }

        if (OnGround.get()) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true));
        }
        //These Need to be converted to Packets
        //Chief's Problem
/*        if (PVPTEMPLE.get()) {
            mc.player.setOnGround(false);
            mc.player.setPos(mc.player.getX(), mc.player.getY() + 0.42, mc.player.getZ());
            mc.player.setYaw(0);
            mc.player.setPitch(0);
        }*/

/*        if (Offset.get()) {
            Vec3d playerPos = mc.player.getPos();
            double offsetX = 0.05;
            double offsetY = 0;
            double offsetZ = 0.05;
            Vec3d offsetVector = new Vec3d(offsetX, offsetY, offsetZ);
            Vec3d newPlayerPos = new Vec3d(playerPos.x + 0.5, playerPos.y, playerPos.z + 0.5).add(offsetVector);
            mc.player.setPosition(newPlayerPos.x, newPlayerPos.y, newPlayerPos.z);
        }*/
        return true;
    }
}
