package none.module.modules.world;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.MathUtils;
import none.utils.TimeHelper;
import none.valuesystem.NumberValue;

public class PingSpoof extends Module{

	public PingSpoof() {
		super("PingSpoof", "PingSpoof", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Integer> wait = new NumberValue<Integer>("Wait", 15, 5, 100);
	
	private TimeHelper timer = new TimeHelper(); 
	
	private List<Packet> packetList = new CopyOnWriteArrayList<>();
	
	@Override
	protected void onDisable() {
		super.onDisable();
		packetList.clear();
	}
	
	@Override
	@RegisterEvent(events = {EventPacket.class, EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + wait.getInteger() + ":" + packetList.size());
		
		if (mc.thePlayer.ticksExisted <= 1) {
			packetList.clear();
		}
		
		if (event instanceof EventPacket) {
            EventPacket ep = (EventPacket) event;
            
            if (ep.getPacket() instanceof C00PacketKeepAlive) {
//            	evc("Recive");
            	packetList.add(ep.getPacket());
            	event.setCancelled(true);
            }
            
            if (timer.hasTimeReached(1000 * wait.getInteger())) {
            	int i = 0;
            	if (!packetList.isEmpty()) {
                    double totalPackets = 15;
            		for (Packet packet : packetList) {
                        if (i < totalPackets) {
                            i++;
                            mc.getConnection().getNetworkManager().sendPacketNoEvent(packet);
                            packetList.remove(packet);
                        }
            		}
            	}
//            	evc("Send");
            	mc.getConnection().getNetworkManager().sendPacketNoEvent(new C00PacketKeepAlive(10000));
            	timer.setLastMS();
            }
        }
	}

}
