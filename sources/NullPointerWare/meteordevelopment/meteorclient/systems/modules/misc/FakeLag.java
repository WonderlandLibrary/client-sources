/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.PacketListSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.network.LongPacketClass;
import meteordevelopment.meteorclient.utils.network.PacketUtils;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.network.packet.Packet;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public final class FakeLag extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> delay = sgGeneral.add(new IntSetting.Builder()
        .name("delay")
        .description("How many milliseconds to delay")
        .defaultValue(500)
        .sliderRange(1, 10000)
        .build()
    );

    private final Setting<Set<Class<? extends Packet<?>>>> packets = sgGeneral.add(new PacketListSetting.Builder()
        .name("packets")
        .description("What packets to delay.")
        .filter(aClass -> PacketUtils.getS2CPackets().contains(aClass))
        .build()
    );

    //long names funi
    CopyOnWriteArrayList<LongPacketClass> longPacketClassCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public FakeLag() {
        super(Categories.Misc, "fakelag", "Fakes lag.");
    }

    @Override
    public void onActivate() {
        longPacketClassCopyOnWriteArrayList.clear();
    }



    @EventHandler
    public void onTick(final TickEvent.Pre e) {
        if (longPacketClassCopyOnWriteArrayList.isEmpty()) return;
        for (LongPacketClass longPacketClass : longPacketClassCopyOnWriteArrayList) {
            if (longPacketClass.getId() + delay.get() < System.currentTimeMillis() && longPacketClass.getPacket() != null) {
                longPacketClassCopyOnWriteArrayList.remove(longPacketClass);
                longPacketClass.getPacket().apply((mc.getNetworkHandler()));
            }
        }
    }


    @Override
    public void onDeactivate() {
        for (LongPacketClass longPacketClass : longPacketClassCopyOnWriteArrayList) {
            longPacketClass.getPacket().apply((mc.getNetworkHandler()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onReceivePacket(final PacketEvent.Receive e) {
        if (!packets.get().contains(e.packet.getClass())) {
            return;
        }
        e.setCancelled(true);
        //index 0 cuz otherwise post entityaction and scaffold stuff (without index 0, it reverses the list)
        longPacketClassCopyOnWriteArrayList.add(new LongPacketClass(e.packet, System.currentTimeMillis()));
    }

/*    @EventHandler(priority = EventPriority.HIGHEST)
    public void onReceivePacket(final PacketEvent.Send e) {
        if (!packets.get().contains(e.packet.getClass())) {
            return;
        }
        e.setCancelled(true);
        //index 0 cuz otherwise post entityaction and scaffold stuff (without index 0, it reverses the list)
        longPacketClassCopyOnWriteArrayList.add(new LongPacketClass(e.packet, System.currentTimeMillis()));
    }*/

}
