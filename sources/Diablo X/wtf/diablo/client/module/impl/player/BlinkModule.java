package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.potion.PotionEffect;
import wtf.diablo.client.event.impl.client.TickEvent;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ModuleMetaData(name = "Blink", description = "Pauses all packets sent to the server", category = ModuleCategoryEnum.PLAYER)
public final class BlinkModule extends AbstractModule {
    private final List<Packet<?>> packetList = new ArrayList<>();

    private final BooleanSetting pulse = new BooleanSetting("Pulse", false);
    private final NumberSetting<Integer> ticks = new NumberSetting<>("Ticks", 20, 1, 250, 1);

    private int dummyPlayerID;
    private int tickCount;

    public BlinkModule() {
        this.registerSettings(pulse, ticks);
    }

    @Override
    protected void onEnable() {
        this.tickCount = 0;
        this.packetList.clear();
        this.updatePlayer();
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        this.sendPackets();
        mc.theWorld.removeEntityFromWorld(dummyPlayerID);
        super.onDisable();
    }

    @EventHandler
    private final Listener<TickEvent> tickEventListener = e -> {
        if (this.pulse.getValue()) {
            this.tickCount++;
            if (this.tickCount >= this.ticks.getValue()) {
                this.tickCount = 0;
                this.sendPackets();
                this.updatePlayer();
            }
        }
    };

    @EventHandler
    private final Listener<SendPacketEvent> packetEventListener = e -> {
        this.packetList.add(e.getPacket());
    };

    private void sendPackets() {
        this.packetList.forEach(packet -> mc.thePlayer.sendQueue.addToSendQueueNoEvent(packet));
        this.packetList.clear();
    }

    private void updatePlayer() {
        if (this.dummyPlayerID != 0) {
            mc.theWorld.removeEntityFromWorld(this.dummyPlayerID);
        }

        this.dummyPlayerID = -new Random().nextInt(1000) + 1000;

        final EntityOtherPlayerMP dummyPlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        dummyPlayer.clonePlayer(mc.thePlayer, true);
        dummyPlayer.copyLocationAndAnglesFrom(mc.thePlayer);

        for (final PotionEffect potionEffect : mc.thePlayer.getActivePotionEffects()) {
            dummyPlayer.addPotionEffect(potionEffect);
        }

        dummyPlayer.rotationYawHead = mc.thePlayer.rotationYawHead;
        mc.theWorld.addEntityToWorld(this.dummyPlayerID, dummyPlayer);
    }
}
