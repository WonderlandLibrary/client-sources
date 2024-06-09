package Squad.Modules.Movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class Jesus extends Module {

	 public Jesus() {
		super("Jesus", Keyboard.KEY_J, 0xFFFFFF, Category.Movement);
	}
	 
	 TimeHelper Vaginal = new TimeHelper();
	  private int motionDelay;
	 
	 public void setup(){
		 	ArrayList<String> options = new ArrayList<>();
		 		options.add("AAC3Jesus");
		 		options.add("NewAACJesus");
		 		options.add("NCPJesus");
		 		
		 Squad.instance.setmgr.rSetting(new Setting("JesusMode", this, "AAC3Jesus", options));
		}
	 
	 
	 TimeHelper Mafucka = new TimeHelper();

	@EventTarget
	 public void onUpdate(EventUpdate event) {
		setDisplayname("Jesus");
		if(Squad.instance.setmgr.getSettingByName("JesusMode").getValString().equalsIgnoreCase("NCPJesus")){
			setDisplayname("Jesus §7NCP");		
			ncp();
		}
		
		if(Squad.instance.setmgr.getSettingByName("JesusMode").getValString().equalsIgnoreCase("AAC3Jesus")){
			setDisplayname("Jesus §7AAC3");		
			aac();
		}


		
		 }
			   public void ncp() {
				   if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY +0.0001, mc.thePlayer.posZ)).getBlock().getMaterial() == Material.water || mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY +0.0001, mc.thePlayer.posZ)).getBlock().getMaterial() == Material.lava)
	
				   {					
					   mc.thePlayer.motionY = 0;
					   mc.thePlayer.motionX *= 0.99F;
					   mc.thePlayer.motionZ *= 0.99F;
						  mc.gameSettings.keyBindJump.pressed = true;
					   if(mc.gameSettings.keyBindJump.pressed)
					   {
					    mc.thePlayer.motionY += 0.1F;
					   }
					   if(mc.gameSettings.keyBindSneak.pressed)
					   {
					    mc.thePlayer.motionY -= 0.1F;
					   }
					   if(mc.thePlayer.isCollidedHorizontally) {
					    mc.thePlayer.motionY += 0.1F;	   	  						   
  
	  } else {
		  mc.gameSettings.keyBindJump.pressed = false;
	  }
					  }
			   }
					   
					   public void aac() {
						   if(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY +0.0001, mc.thePlayer.posZ)).getBlock().getMaterial() == Material.water || mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY +0.0001, mc.thePlayer.posZ)).getBlock().getMaterial() == Material.lava)
							  {
							   mc.thePlayer.motionY = 0;
							   mc.thePlayer.motionX *= 0.99F;
							   mc.thePlayer.motionZ *= 0.99F;
							   if(mc.gameSettings.keyBindJump.pressed)
							   {
							    mc.thePlayer.motionY += 0.1F;
							   }
							   if(mc.gameSettings.keyBindSneak.pressed)
							   {
							    mc.thePlayer.motionY -= 0.1F;
							   }
							   if(mc.thePlayer.isCollidedHorizontally) {
							    mc.thePlayer.motionY += 0.1F;	   	 
							  
					   }
	}
	}
					   
					   public void newaac(){
						   if (mc.thePlayer.isInWater()) {
							      if ((mc.gameSettings.keyBindSneak.pressed) || (mc.gameSettings.keyBindJump.pressed)) {
							        return;
							      }
							      mc.thePlayer.motionX *= 1.190000057220459D;
							      mc.thePlayer.motionZ *= 1.190000057220459D;
							      if ((mc.thePlayer.isInWater()) && (Vaginal.hasReached(1000L))) {
							        motionDelay += 1;
							        motionDelay %= 2;
							        if (motionDelay == 0) {
							          mc.thePlayer.motionY = 0.012000000104308128D;
							        }
							      }
							    }
							    else
							    {
							      Vaginal.setLastMS();
							    }

					   }

}
	
  

 