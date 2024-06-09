package lunadevs.luna.module.combat;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class InfiniteReach extends Module{

	private Random random = new Random();
	
	public InfiniteReach() {
		super("InfiniteReach", Keyboard.KEY_NONE, Category.COMBAT, false);
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
	      Minecraft.thePlayer.setPosition(en.posX + this.random.nextInt(3) * 2 - 2.0D, 
	        en.posY, en.posZ + this.random.nextInt(3) * 2 - 2.0D);
	      }
	    EntityUtils.faceEntity(en);
	      Minecraft.thePlayer.swingItem();
	      Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(en, 
	        C02PacketUseEntity.Action.ATTACK));
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public String getValue() {
		return null;
	}

}
