package us.dev.direkt.module.internal.core;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.event.internal.filters.WorldLoadFilter;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

@ModData(label = "Anti Crash", category = ModCategory.CORE)
public class AntiCrash extends Module {

    @Listener
    protected Link<EventPreReceivePacket> onPreReceivePacket = new Link<>(event -> {
        /*if (event.getPacket() instanceof SPacketSpawnMob) {
            SPacketSpawnMob packet = (SPacketSpawnMob) event.getPacket();
            if ((packet.getEntityType() == 55 || packet.getEntityType() == 62)
                    && packet.getDataManagerEntries().size() == 12 && packet.getDataManagerEntries().get(11).getValue() instanceof Integer) {
                if ((Integer) packet.getDataManagerEntries().get(11).getValue() > 15 || (Integer) packet.getDataManagerEntries().get(11).getValue() < 1) {
                    packet.getDataManagerEntries().set(11, new DataEntry<>((DataParameter<Integer>) packet.getDataManagerEntries().get(11).getKey(), 1));
                    packet.getDataManagerEntries().set(2, new DataEntry<>((DataParameter<String>) packet.getDataManagerEntries().get(2).getKey(), "\2478[\2479" + Direkt.getInstance().getClientName() + "\2478]\2474 Slime too large to render!"));
                    packet.getDataManagerEntries().set(3, new DataEntry<>((DataParameter<Boolean>) packet.getDataManagerEntries().get(3).getKey(), true));
                }
            }
        } else if (event.getPacket() instanceof SPacketEntityMetadata) {
            SPacketEntityMetadata packet = (SPacketEntityMetadata) event.getPacket();
            if (!(Wrapper.getWorld().getEntityByID(packet.getEntityId()) instanceof EntityDragon) && packet.getDataManagerEntries().size() == 12 && packet.getDataManagerEntries().get(11).getValue() instanceof Integer) {
                if ((Integer) packet.getDataManagerEntries().get(11).getValue() > 15 || (Integer) packet.getDataManagerEntries().get(11).getValue() < 1) {
                    packet.getDataManagerEntries().set(11, new DataEntry<>((DataParameter<Integer>) packet.getDataManagerEntries().get(11).getKey(), 1));
                    packet.getDataManagerEntries().set(2, new DataEntry<>((DataParameter<String>) packet.getDataManagerEntries().get(2).getKey(), "\2478[\2479" + Direkt.getInstance().getClientName() + "\2478]\2474 Slime too large to render!"));
                    packet.getDataManagerEntries().set(3, new DataEntry<>((DataParameter<Boolean>) packet.getDataManagerEntries().get(3).getKey(), true));
                }
            }
        }*/
    	
    	
        if (event.getPacket() instanceof SPacketEntityMetadata) {
            SPacketEntityMetadata packet = (SPacketEntityMetadata) event.getPacket();
            //packet.getDataManagerEntries().stream().forEach(dme -> System.out.println(dme.getKey().getId() + " " + dme.getValue()));
            if(packet.getDataManagerEntries().get(0).getKey().getId() == 8)
            	System.out.println(packet.getDataManagerEntries().get(0).getValue());
        }
    }, new PacketFilter<>(SPacketSpawnPosition.class, SPacketEntityMetadata.class), new WorldLoadFilter<>());
}
