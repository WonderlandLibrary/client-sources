package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.PacketEvent;
import com.shroomclient.shroomclientnextgen.mixin.PlayerMoveC2SPacketAccessor;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.PacketUtil;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;

@RegisterModule(
    name = "Criticals",
    uniqueId = "criticals",
    description = "10-heart Kids",
    category = ModuleCategory.Combat
)
public class Criticals extends Module {

    @ConfigMode
    @ConfigOption(name = "Mode", description = "", order = 1)
    public static Mode mode = Mode.Off_Ground;

    PlayerInteractEntityC2SPacket attack = null;
    boolean hit = false;
    private int ticks;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send.Pre e) {
        if (e.getPacket() instanceof PlayerMoveC2SPacket p) {
            if (
                mode == Criticals.Mode.Off_Ground
            ) ((PlayerMoveC2SPacketAccessor) p).setOnGround(false);
        }
    }

    @SubscribeEvent
    public void packetEvent(PacketEvent.Send.Pre e) {
        if (
            mode == Mode.Packet &&
            e.getPacket() instanceof PlayerInteractEntityC2SPacket n &&
            !hit
        ) {
            attack = n;
            e.cancel();
            hit = true;
        }
    }

    @SubscribeEvent
    public void onUpdate(MotionEvent.Pre event) {
        if (attack != null) {
            if (mode == Mode.Packet) {
                ticks++;
                if (ticks == 1) event.setY(event.getY() + 0.0625f);
                event.setOnGround(false);
                if (ticks > 2) {
                    C.p().swingHand(Hand.MAIN_HAND);
                    PacketUtil.sendPacket(attack, true);
                    attack = null;
                    ticks = 0;
                    hit = false;
                }
            }
        }
    }

    public enum Mode {
        Off_Ground,
        Packet,
    }
}
