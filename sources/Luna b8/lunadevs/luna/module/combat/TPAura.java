package lunadevs.luna.module.combat;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class TPAura extends Module{

	private Random random = new Random();
	int clock = 0;
	
	public TPAura() {
		super("TPAura", Keyboard.KEY_NONE, Category.COMBAT, true);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		EntityPlayer en = EntityUtils.getClosestEntity1();
	    if ((en == null) || 
	      (Minecraft.thePlayer.getDistanceToEntity(en) > 20.0 && !Minecraft.thePlayer.isInvisible()))
	    {
	      EntityUtils.lookChanged = false;
	      return;
	    }
	    EntityUtils.lookChanged = true;
	    {
	      //Minecraft.thePlayer.setPosition(en.posX + this.random.nextInt(3) * 2 - 2.0D, 
	        //en.posY, en.posZ + this.random.nextInt(3) * 2 - 2.0D);
	      zCore.p().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(en.posX + this.random.nextInt(3) * 2 - 2.0D, 
	  	        en.posY, en.posZ + this.random.nextInt(3) * 2 - 2.0D, false));
	      zCore.p().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(en.posX + this.random.nextInt(3) * 2 - 2.0D, 
		  	        en.posY, en.posZ + this.random.nextInt(3) * 2 - 2.0D, true));
	      }
	      EntityUtils.faceEntity(en);
	      this.clock += 1;
	      if (this.clock >= 2)
		    {
	      mc.thePlayer.swingItem();
	      Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(en, 
	        C02PacketUseEntity.Action.ATTACK));
	      this.clock = 0;
		super.onUpdate();
	}}

	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
	}
	@Override
	public String getValue() {
		return "Packet";
	}

}
