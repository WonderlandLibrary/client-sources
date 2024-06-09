package lunadevs.luna.module.combat;

import com.darkmagician6.eventapi.EventTarget;
import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPacket;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.exploits.zPackets;
import lunadevs.luna.utils.faithsminiutils.Timer;
import lunadevs.luna.utils.faithsminiutils.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumParticleTypes;

public class NormalCriticals extends Module{

	public NormalCriticals() {
		super("NormalCriticals", 0, Category.COMBAT, false);
	}

	private static Timer timer = new Timer();
	private static boolean cancelSomePackets;

	public static void doCrit() {
		 if (Wrapper.getPlayer().onGround) {
	                    if (Wrapper.getPlayer().isCollidedVertically && Timer.hasReach(500)) {
	                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.0627, Wrapper.getPlayer().posZ, false));
	                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.0627, Wrapper.getPlayer().posZ, false));
	                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));
	                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));

	                        timer.reset();
	                    }}}
	@EventTarget
	  public void onEntityAttack(EventPacket event)
	  {
	    if ((event.getType() == EventPacket.EventPacketType.SEND) && ((event.getPacket() instanceof C02PacketUseEntity)) && (mc.thePlayer.onGround) && 
	      (((C02PacketUseEntity)event.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK)) {
	      doCrit();
	    }
	  }
	
	
	@Override
	public String getValue() {
		return null;
	}

}
