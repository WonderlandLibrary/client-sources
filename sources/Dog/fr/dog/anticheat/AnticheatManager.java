package fr.dog.anticheat;

import fr.dog.Dog;
import fr.dog.anticheat.check.Autoblock;
import fr.dog.anticheat.check.NoslowA;
import fr.dog.anticheat.check.NoslowB;
import fr.dog.anticheat.check.Scaffold;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.NetworkBlockPlaceEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.module.impl.player.Anticheat;

import java.util.ArrayList;

public class AnticheatManager {

    private final ArrayList<Check> checks = new ArrayList<>();

    public AnticheatManager() {
        Anticheat anticheat = Dog.getInstance().getModuleManager().getModule(Anticheat.class);
        boolean autoblock = anticheat.autoblock.getValue();
        boolean noslow = anticheat.noslow.getValue();
        boolean scaffold = anticheat.scaffold.getValue();


        checks.add(new Autoblock(anticheat));
        checks.add(new NoslowA(anticheat));
        checks.add(new NoslowB(anticheat));
        checks.add(new Scaffold(anticheat));

        Dog.getInstance().getEventBus().register(this);
    }

    @SubscribeEvent
    private void onEventPlayerTickBULLSHITUpdate(PlayerTickEvent event) {
        if (!Dog.getInstance().getModuleManager().getModule(Anticheat.class).isEnabled()) {
            return;
        }
        checks.forEach(Check::onUpdate);
    }

    @SubscribeEvent
    private void onPacketNeger(PacketReceiveEvent event) {
        if (!Dog.getInstance().getModuleManager().getModule(Anticheat.class).isEnabled()) {
            return;
        }
        checks.forEach(p -> p.onPacket(event.getPacket()));
    }

    @SubscribeEvent
    private void onBlockPlace(NetworkBlockPlaceEvent event) {
        if (!Dog.getInstance().getModuleManager().getModule(Anticheat.class).isEnabled()) {
            return;
        }
        checks.forEach(e -> e.onBlockMod(event.getPos(), event.getState()));
    }
}
