package lunadevs.luna.module.movement;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class GlideModes extends Module {

	public static boolean active;
	@Option.Op(name = "Watchdog")
	public static boolean Watchdog = false;
	@Option.Op(name = "AAC 3.1.6")
	public static boolean AAC = false;
	@Option.Op(name = "AAC 3.2.0")
	public static boolean AACNew = true;
	@Option.Op(name = "HiveMC")
	public static boolean HiveMC = false;

	public GlideModes() {
		super("Glide", 0, Category.MOVEMENT, true);
	}

	public static String modname;

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.Watchdog == true) {
			Minecraft.thePlayer.motionY = 0.0f;
			zCore.setSpeedM((float) 0.32);
			if (this.AAC == true) {
				this.AAC = false;

			}
			modname = "Watchdog";
		} else if (this.AAC == true) {
			aac();
			Minecraft.thePlayer.motionY = -0.1f;
			if (this.Watchdog == true) {
				this.Watchdog = false;
			}
			modname = "AAC 3.1.6";
		} else if (this.AACNew == true) {
			aacnew();
			mc.thePlayer.motionY = -0.125f;
			if (this.Watchdog == true) {
				this.Watchdog = false;
				this.AAC = false;
			}
			modname = "AAC 3.2.0";
		} else if (this.HiveMC == true) {
			HiveMC();
			mc.thePlayer.motionY = -0.07f;
			if (this.Watchdog == true) {
				this.Watchdog = false;
				this.AAC = false;
				this.AACNew = false;
			}
			modname = "HiveMC";
		}
		if (this.Watchdog == true) {
			watchdog();
		}

		super.onUpdate();
	}

	private void aacnew() {
		if (this.AACNew == true) {
			this.modname = "AAC 3.2.0";
			if (mc.thePlayer.motionY < 0 && mc.thePlayer.isAirBorne || mc.thePlayer.onGround == true) {
				mc.thePlayer.motionY = -0.125f;
				mc.thePlayer.jumpMovementFactor *= 1.01227f;
				mc.thePlayer.noClip = true;
				mc.thePlayer.fallDistance = 0;
				mc.thePlayer.onGround = true;
				mc.thePlayer.moveStrafing += 0.8F;
				mc.thePlayer.jumpMovementFactor += 0.2F;
				mc.thePlayer.velocityChanged = true;

			}
		}
	}
	public void watchdog() {
		if (this.Watchdog == true) {
			this.modname = "Watchdog";
			zCore.setSpeedM((float) 0.32);
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.4f,
						Minecraft.thePlayer.posZ);

				Minecraft.thePlayer.motionY = 0.0f;
				{
					if (mc.gameSettings.keyBindSneak.isKeyDown()) {
						Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.5,
								Minecraft.thePlayer.posZ);
					}
				}
			}
		}
	}

	public void aac() {
		if (this.AAC == true) {
			this.modname = "AAC 3.1.6";
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.2f,
						Minecraft.thePlayer.posZ);
			}
			/// mc.thePlayer.motionY = -3.17f;
			{
				if (mc.gameSettings.keyBindSneak.isKeyDown()) {
					Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.0001f,
							Minecraft.thePlayer.posZ);
				}
			}
		}
	}
	
	public void HiveMC() {
		if (this.HiveMC == true) {
			this.modname = "HiveMC";
			if (mc.gameSettings.keyBindJump.isKeyDown()) {
				Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.17f,
						Minecraft.thePlayer.posZ);
			}
			
			{
				if (mc.gameSettings.keyBindSneak.isKeyDown()) {
					Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.17f,
							Minecraft.thePlayer.posZ);
				}
			}
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.stepHeight = 0.6f;
		zCore.setSpeedM((float) getBaseMoveSpeed());
		active = false;
	}

	  public double getBaseMoveSpeed()
	  {
	    double baseSpeed = 0.2873D;
	    if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
	    {
	      int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
	      baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
	    }
	    return baseSpeed;
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
