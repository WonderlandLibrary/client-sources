package none.module.modules.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.input.Keyboard;

import joptsimple.internal.Strings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.server.S3APacketTabComplete;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.utils.TimeHelper;
import none.valuesystem.NumberValue;

public class Plugins extends Module{

	public Plugins() {
		super("Plugins", "Plugins", Category.WORLD, Keyboard.KEY_NONE);
	}
	
	private NumberValue<Integer> timeout = new NumberValue<Integer>("Timeout", 5, 0, 10);
	
	private List<String> plugins = new ArrayList();
	private boolean scan;
    private TimeHelper timer = new TimeHelper();
	
    @Override
    protected void onEnable() {
    	super.onEnable();
    	if (mc.thePlayer == null)
            return;
    	mc.thePlayer.connection.sendPacket(new C14PacketTabComplete("/"));
    	scan = true;
        plugins.clear();
    	timer.setLastMS();
    }
    
    @Override
    protected void onDisable() {
    	super.onDisable();
    	scan = false;
    	timer.setLastMS();
    }
    
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, EventPacket.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre()) {
				if (timer.hasTimeReached(timeout.getInteger() * 300)) {
					timer.setLastMS();
		            toggle();
		        }
			}
		}
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet packet = ep.getPacket();
			if (ep.isIncoming() && packet instanceof S3APacketTabComplete && scan) {
				S3APacketTabComplete packet2 = (S3APacketTabComplete) packet;
                String[] commands = packet2.func_149630_c();
                for(String s : commands) {
                    String[] split = s.split(":");
                    if (split.length > 1) {
                        String in = split[0].replaceAll("/", "");
                        if (!plugins.contains(in)) {
                            plugins.add(in);
                        }
                    }
                }
                Collections.sort(plugins);
                if (plugins.isEmpty()) {
                    evc("No plugins found");
                } else {
                    evc("[n]Plugins [t]([v]"+plugins.size()+"[t]): [v]" + Strings.join(plugins.toArray(new String[0]), "[t], [v]"));
                }
			}
		}
	}
}
