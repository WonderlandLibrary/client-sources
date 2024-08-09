package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.PlayerMoveC2SPacketAccessor;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class AntiHunger extends Module {
    private boolean lastOnGround, sendOnGroundTruePacket, ignorePacket;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> sprintSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Sprint")
            .description("Spoof sprinting packets.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> onGroundSetting = generalSettings.add(new BoolSetting.Builder()
            .name("On ground")
            .description("Spoof the onGround flag.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> waterCheckSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Water check")
            .description("Pause the module if you are in water")
            .defaultValue(true)
            .build()
    );

    public AntiHunger(Category category) {
        super(category, "Anti Hunger", "Reduces (does NOT remove) hunger consumption.");
    }

    @Override
    public void onEnable() {
        lastOnGround = mc.player.isOnGround();
        sendOnGroundTruePacket = true;
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (ignorePacket) {
            return;
        }

        if (event.packet instanceof ClientCommandC2SPacket && sprintSetting.get()) {
            ClientCommandC2SPacket.Mode mode = ((ClientCommandC2SPacket) event.packet).getMode();
            if (mode == ClientCommandC2SPacket.Mode.START_SPRINTING || mode == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                event.cancel();
            }
        }

        if (event.packet instanceof PlayerMoveC2SPacket && onGroundSetting.get() && mc.player.isOnGround() && mc.player.fallDistance <= 0.0 && !mc.interactionManager.isBreakingBlock()) {
            ((PlayerMoveC2SPacketAccessor) event.packet).setOnGround(false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (waterCheckSetting.get() && mc.player.isTouchingWater()) {
            ignorePacket = true;
            return;
        }

        if (mc.player.isOnGround() && !lastOnGround && !sendOnGroundTruePacket) {
            sendOnGroundTruePacket = true;
        }

        if (mc.player.isOnGround() && sendOnGroundTruePacket && onGroundSetting.get()) {
            ignorePacket = true;
            mc.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            ignorePacket = false;

            sendOnGroundTruePacket = false;
        }

        lastOnGround = mc.player.isOnGround();
    }
}