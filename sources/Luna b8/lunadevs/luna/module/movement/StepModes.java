package lunadevs.luna.module.movement;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class StepModes extends Module {

	public static boolean active;
	@Option.Op(name = "NCP")
	public static boolean ncp = false;
	@Option.Op(name = "AAC 3.2.1")
	public static boolean AAC = true;
	@Option.Op(name = "Lemon")
	public static boolean Lemon = false;
	@Option.Op(name = "Bunny")
	public static boolean Bunny = false;
	@Option.Op(name = "Jump")
	public static boolean jump = false;

	public StepModes() {
		super("Step", 0, Category.MOVEMENT, true);
	}

	public static String modname;

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.ncp == true) {
			if (this.AAC == true) {
				this.AAC = false;
				this.Lemon = false;
				this.Bunny = false;
				this.jump = false;

			}
			modname = "NCP";
		} else if (this.AAC == true) {
			aac();
			if (this.ncp == true) {
				this.AAC = false;
				this.Lemon = false;
				this.Bunny = false;
				this.jump = false;
			}
			modname = "AAC 3.2.1";
		} else if (this.Lemon == true) {
			lemon();
			if (this.Lemon == true) {
				this.AAC = false;
				this.ncp = false;
				this.Lemon = false;
				this.Bunny = false;
				this.jump = false;
			}
			modname = "Lemon";
		} else if (this.Bunny == true) {
			bunny();
			if (this.Bunny == true) {
				this.AAC = false;
				this.ncp = false;
				this.Lemon = false;
				this.jump = false;
			}
			modname = "Bunny";
		} else if (this.jump == true) {
			jump();
			if (this.jump == true) {
				this.AAC = false;
				this.ncp = false;
				this.Lemon = false;
				this.Bunny = false;
			}
			modname = "Bunny";
		}
		if (this.ncp == true) {
			ncp();
		}

		super.onUpdate();
	}

	private void lemon() {
		if (this.Lemon == true) {
			this.modname = "Lemon";
			
			if (((this.mc.thePlayer.motionX != 0.0D) || (this.mc.thePlayer.motionZ != 0.0D)) && (this.mc.thePlayer.isCollidedHorizontally) && (!this.mc.thePlayer.isOnLadder()))
		      {
		        if (this.mc.thePlayer.onGround)
		        {
		          this.mc.thePlayer.motionY = 0.3998D;
		          this.mc.timer.timerSpeed = 2.5F;
		        }
		        else if (this.mc.thePlayer.motionY <= -0.162D)
		        {
		          this.mc.thePlayer.motionY = 0.2369D;
		          this.mc.timer.timerSpeed = 1.337F;
		        }
		      }
		      else {
		        this.mc.timer.timerSpeed = 1.0F;
		      }
		    }
		  }
	
	
	
	private void jump() {
		if (this.jump == true) {
			this.modname = "Jump";
			
			if(zCore.gsettings().keyBindJump.pressed && zCore.p().isCollidedHorizontally){
				zCore.p().setPosition( zCore.p().lastTickPosX, zCore.p().serverPosY+zCore.p().lastTickPosY+0.09F, zCore.p().serverPosZ+zCore.p().posZ);
		      }
		    }
		  }
	
	
	private void bunny() {
		if (this.Bunny == true) {
			this.modname = "Bunny";
			
			if (((this.mc.thePlayer.motionX != 0.0D) || (this.mc.thePlayer.motionZ != 0.0D)) && (this.mc.thePlayer.isCollidedHorizontally) && (!this.mc.thePlayer.isOnLadder()))
		      {
		        if (this.mc.thePlayer.onGround)
		        {
		          this.mc.thePlayer.motionY = 0.9998D;
		          this.mc.timer.timerSpeed = 2.5F;
		        }
		        else if (this.mc.thePlayer.motionY <= -0.162D)
		        {
		          this.mc.thePlayer.motionY = 0.269D;
		          this.mc.timer.timerSpeed = 1.337F;
		        }
		      }
		      else {
		        this.mc.timer.timerSpeed = 1.0F;
		      }
		    }
		  }

	public void ncp() {
		if (this.ncp == true) {
			this.modname = "NCP";
			if ((Minecraft.thePlayer.isCollidedHorizontally) && (Minecraft.thePlayer.onGround)
					&& (Minecraft.thePlayer.isCollidedVertically) && (Minecraft.thePlayer.isCollided)) {
				Minecraft.thePlayer.setSprinting(true);
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.42D, Minecraft.thePlayer.posZ,
						Minecraft.thePlayer.onGround));
				Minecraft.thePlayer.setSprinting(true);
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.753D, Minecraft.thePlayer.posZ,
						Minecraft.thePlayer.onGround));
				Minecraft.thePlayer.setSprinting(true);
				Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0D,
						Minecraft.thePlayer.posZ);
				Minecraft.thePlayer.setSprinting(true);
			}
		}
	}

	public void aac() {
		//@author Nuddles
		if (this.AAC == true) {
			this.modname = "AAC 3.2.1";
			if ((mc.thePlayer.isCollidedHorizontally)) {
				mc.thePlayer.onGround = true;
				zCore.mc().thePlayer.stepHeight = 1;
				Minecraft.thePlayer.setSprinting(true);
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.42D, Minecraft.thePlayer.posZ,
						Minecraft.thePlayer.onGround));
				Minecraft.thePlayer.setSprinting(true);
				Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.753D, Minecraft.thePlayer.posZ,
						Minecraft.thePlayer.onGround));
				Minecraft.thePlayer.setSprinting(true);
				Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0D,
						Minecraft.thePlayer.posZ);
				Minecraft.thePlayer.setSprinting(true);
			}
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		 this.mc.timer.timerSpeed = 1.0F;
		zCore.p().noClip = false;
		mc.thePlayer.stepHeight = 0.6f;
		active = false;
	}

	@Override
	public void onEnable() {
		active = true;
		super.onEnable();
	}

	@Override
	public String getValue() {

		return modname;
	}

}
