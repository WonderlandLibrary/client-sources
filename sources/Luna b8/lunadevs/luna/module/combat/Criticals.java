package lunadevs.luna.module.combat;

import com.darkmagician6.eventapi.EventTarget;
import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPacket;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.exploits.zPackets;
import lunadevs.luna.utils.faithsminiutils.Timer;
import lunadevs.luna.utils.faithsminiutils.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumParticleTypes;

public class Criticals extends Module{

	public Criticals() {
		super("ExtremeCriticals", 0, Category.COMBAT, false);
	}

	private static Timer timer = new Timer();
	private static boolean cancelSomePackets;
	
	@Override
	public void onUpdate(){
		if(ModuleManager.findMod(NormalCriticals.class).isEnabled()){
			ModuleManager.findMod(NormalCriticals.class).setEnabled(false);
		}
		super.onUpdate();
	}
	
	public static void doCrit()
	  {
		double x = z.player().posX;
        double y = z.player().posY;
        double z = zCore.player().posZ;

        zCore.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.05, z, false));
        zCore.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
        zCore.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.012511, z, false));
        zCore.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
    }

	
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
