package zamorozka.modules.WORLD;

import org.lwjgl.input.Keyboard;

import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.TRAFFIC.LongJump;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;

public class Terrain extends Module {

	public Terrain() {
		super("TerrainSpeed", Keyboard.KEY_NONE, Category.WORLD);
	}
	
	
	@Override
	public void onEnable() {
		Blocks.ICE.slipperiness = 0.4F;
		Blocks.PACKED_ICE.slipperiness = 0.4F;
		Blocks.SLIME_BLOCK.slipperiness = 0.5F;
	}
	
	@Override
	public void onDisable() {
		Blocks.ICE.slipperiness = 0.89F;
		Blocks.PACKED_ICE.slipperiness = 0.89F;
		Blocks.SLIME_BLOCK.slipperiness = 0.89F;
	}
	
	@EventTarget
    public void onPacket(EventPacket event) {
        Packet<?> p = event.getPacket();
        if (event.isIncoming()) {
            if (p instanceof SPacketPlayerPosLook && mc.player != null) {
                mc.player.onGround = false;
                mc.player.motionX *= 0;
                mc.player.motionZ *= 0;
                mc.player.jumpMovementFactor = 0;
                if (Zamorozka.moduleManager.getModule(Terrain.class).getState() && Zamorozka.moduleManager.getModule(LagCheck.class).getState()) {
                    Zamorozka.moduleManager.getModule(Terrain.class).toggle();
                    NotificationPublisher.queue("LagBack", "TerrainSpeed was lagback!", NotificationType.WARNING);
                    isEnabled = false;
                    ModuleManager.getModule(Terrain.class).setState(false);
                }
            }
        }
	}
	
}
