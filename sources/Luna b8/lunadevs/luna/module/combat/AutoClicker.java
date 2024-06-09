package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class AutoClicker extends Module{
	int delay;
	
	public AutoClicker() {
		super("AutoClicker", Keyboard.KEY_M, Category.COMBAT, false);
	}

	@Override
	public void onUpdate() {
		 if (this.isEnabled)
		    {
				if (Minecraft.thePlayer.isBlocking()) {
					Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,new BlockPos(0, 0, 0), EnumFacing.UP));
				}
		      this.delay += 1;
		      if (((this.mc.gameSettings.keyBindAttack.isKeyDown()) && (this.delay > 4)))
		      {

		        this.delay = 0;
		        this.mc.thePlayer.swingItem();
		        if ((this.mc.objectMouseOver.entityHit instanceof Entity)){
		        this.mc.playerController.attackEntity(this.mc.thePlayer, this.mc.objectMouseOver.entityHit);
		      }
		        return;
		      }
		    }
		    }
	

	@Override
	public void onRender() {
		
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
