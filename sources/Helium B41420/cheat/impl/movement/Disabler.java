package rip.helium.cheat.impl.movement;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.hippo.systems.lwjeb.annotation.Collect;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.util.Timer;
import org.apache.commons.lang3.RandomUtils;
import rip.helium.ClientBase;
import rip.helium.cheat.Cheat;
import rip.helium.cheat.CheatCategory;
import rip.helium.event.minecraft.PlayerMoveEvent;
import rip.helium.event.minecraft.PlayerUpdateEvent;
import rip.helium.event.minecraft.ProcessPacketEvent;
import rip.helium.event.minecraft.SendPacketEvent;
import rip.helium.utils.Stopwatch;
import rip.helium.utils.ctimer;
import rip.helium.utils.property.impl.StringsProperty;

public class Disabler extends Cheat {

    public static ConcurrentLinkedQueue<Packet> list;
    public ArrayList<Packet> delayedtransactions;
    private Stopwatch timer = new Stopwatch();
    public ArrayList<Packet> delayedtransactions2;
    public int confirmtransactioncounter;
    private StringsProperty mode = new StringsProperty("Mode", "How the priority target will be selected.", null, false, true, new String[]{"Viper", "Muncher", "RinaOrc", "Mineplex"}, new Boolean[]{true, false, false, false});
    
    static {
        Disabler.list = new ConcurrentLinkedQueue<Packet>();
    }

	public Disabler() {
        super("Disabler", "Fuck that little faggot!", CheatCategory.MOVEMENT);
        registerProperties(mode);
	}

	public void onEnable() {
        timer.reset();
	}


	@Collect
	public void playerUpdateEvent(PlayerUpdateEvent event) {
        if (mode.getValue().get("Viper")) {
            if (this.timer.hasPassed(3000.0f)) {
                event.setPosY(event.getPosY() + 0.42);
                return;
            }
            for (int i = 0; i < 10; ++i) {
                final boolean i2 = i > 2 && i < 8;
                final double x = i2 ? 0.2 : -226.0;
                final C03PacketPlayer.C04PacketPlayerPosition packet2 = new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + x, this.mc.thePlayer.posZ, true);
                this.mc.thePlayer.sendQueue.addToSendQueue(packet2);
            }
        } else if (mode.getValue().get("Muncher")) {
            if (this.timer.hasPassed(1500L)) {
                Disabler.mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(Disabler.mc.thePlayer.posX, Disabler.mc.thePlayer.posY + 12.0, Disabler.mc.thePlayer.posZ, true));
                this.timer.reset();
            }
        }
    }

    @Collect
    public void packetEvent(SendPacketEvent event) {
        if (mode.getValue().get("RinaOrc")) {
            if (event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C0FPacketConfirmTransaction) {
                event.setCancelled(true);
            }
        }
        if (mode.getValue().get("Mineplex")) {
            if (event.getPacket() instanceof C00PacketKeepAlive) {
                event.setCancelled(true);
            }
        }
        if (mode.getValue().get("Muncher")) {
            if (event.getPacket() instanceof S00PacketKeepAlive || event.getPacket() instanceof C0FPacketConfirmTransaction) {
                event.setCancelled(true);
            }
        }
    }


	@Collect
    public void onPacketSend(final PlayerMoveEvent event) {
		setMode(this.mode.getSelectedStrings().get(0));
        /*/if (mc.thePlayer.ticksExisted % 100 == 0 && !this.delayedtransactions2.isEmpty() && delayedtransactions2 != null) {
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(this.delayedtransactions2.get(this.confirmtransactioncounter));
            ++this.confirmtransactioncounter;
        }/*/
	}

}
