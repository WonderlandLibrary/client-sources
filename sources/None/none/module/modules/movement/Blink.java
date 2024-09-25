package none.module.modules.movement;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.event.events.EventPacket;
import none.module.Category;
import none.module.Module;
import none.module.modules.render.ClientColor;
import none.utils.RenderingUtil;
import none.utils.TimeHelper;
import none.valuesystem.BooleanValue;

public class Blink extends Module{

	public Blink() {
		super("Blink", "Blink", Category.MOVEMENT, Keyboard.KEY_NONE);
	}
	
	private BooleanValue ShowLine = new BooleanValue("Show-Line", true);
	
	private List<Packet> packets = new CopyOnWriteArrayList<>();
    private List<Vec3> crumbs = new CopyOnWriteArrayList<>();
    
    @Override
    protected void onEnable() {
    	super.onEnable();
    	crumbs.clear();
//    	Client.instance.moduleManager.getModulesbycls(Fly.class).setState(true);
    }
    
    @Override
    protected void onDisable() {
    	super.onDisable();
    	int count = packets.size();
    	if (mc.thePlayer == null) return;
    	for (Packet packet : packets) {
    		mc.getConnection().getNetworkManager().sendPacketNoEvent(packet);
        }
//    	Client.instance.moduleManager.getModulesbycls(Fly.class).setState(false);
    	crumbs.clear();
    	packets.clear();
    }
    
    private TimeHelper timer = new TimeHelper();
    
	@Override
	@RegisterEvent(events = {EventPacket.class, Event3D.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		setDisplayName(getName() + ChatFormatting.WHITE + ":" + ChatFormatting.GREEN + packets.size());
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (ep.isOutgoing() && ep.isPre()&& (ep.getPacket() instanceof C03PacketPlayer)) {
                ep.setCancelled(true);
                
                if(!(p instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(p instanceof C03PacketPlayer.C06PacketPlayerPosLook))
	                return;
	
	            packets.add(p);
            }else if (ep.isOutgoing() && ep.isPre()&& (ep.getPacket() instanceof C02PacketUseEntity)) {
            	ep.setCancelled(true);
            	packets.add(p);
            }else if (ep.isOutgoing() && ep.isPre()&& (ep.getPacket() instanceof C07PacketPlayerDigging)) {
            	C07PacketPlayerDigging in = (C07PacketPlayerDigging) ep.getPacket();
            	ep.setCancelled(true);
            	if (in.getStatus() == Action.ABORT_DESTROY_BLOCK) return;
            	packets.add(p);
            }else if (ep.isOutgoing() && ep.isPre()&& (ep.getPacket() instanceof C08PacketPlayerBlockPlacement)) {
            	ep.setCancelled(true);
            	packets.add(p);
            }else if (ep.isOutgoing() && ep.isPre()&& (ep.getPacket() instanceof C0APacketAnimation)) {
            	ep.setCancelled(true);
            	packets.add(p);
            }
            if(ep.isIncoming() && ep.isPre()){
            	if(ep.getPacket() instanceof S08PacketPlayerPosLook){
            		ep.setCancelled(true);
            	}
            }
		}
		
		if (event instanceof Event3D && ShowLine.getObject()) {
			if (timer.hasTimeReached(100)) {
                crumbs.add(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
                timer.setLastMS();
            }
            if (!crumbs.isEmpty() && crumbs.size() > 2) {
            	
                for (int i = 1; i < crumbs.size(); i++) {
                    Vec3 vecBegin = crumbs.get(i - 1);
                    Vec3 vecEnd = crumbs.get(i);
                    int renderColor = ClientColor.getColor();
                    if (ClientColor.rainbow.getObject()) {
                    	renderColor = ClientColor.rainbow(i * 50);
                    }
                    float beginX = (float) ((float) vecBegin.xCoord - RenderManager.renderPosX);
                    float beginY = (float) ((float) vecBegin.yCoord - RenderManager.renderPosY);
                    float beginZ = (float) ((float) vecBegin.zCoord - RenderManager.renderPosZ);
                    float endX = (float) ((float) vecEnd.xCoord - RenderManager.renderPosX);
                    float endY = (float) ((float) vecEnd.yCoord - RenderManager.renderPosY);
                    float endZ = (float) ((float) vecEnd.zCoord - RenderManager.renderPosZ);
                    final boolean bobbing = mc.gameSettings.viewBobbing;
                    mc.gameSettings.viewBobbing = false;
                    RenderingUtil.drawLine3D(beginX, beginY, beginZ, endX, endY, endZ, renderColor);
                    mc.gameSettings.viewBobbing = bobbing;
                }
            }
		}
	}
	
}
