package zamorozka.modules.WORLD;

import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketUseEntity;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventSendPacket;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class XCarry extends Module {

	public XCarry() {
		super("XCarry", 0, Category.Exploits);
		// TODO Auto-generated constructor stub
	}
	
	@EventTarget
	public void sendPacket(EventSendPacket event) {
		if (event.getPacket() instanceof CPacketCloseWindow) {
            if (((CPacketCloseWindow) event.getPacket()).windowId == mc.player.inventoryContainer.windowId) {
            	event.setCancelled(true);
            }
		}
	}
	
	@Override
    public void onDisable()
    {
        super.onDisable();
        if (mc.world != null)
        {
            mc.player.connection.sendPacket(new CPacketCloseWindow(mc.player.inventoryContainer.windowId));
        }
    }
}