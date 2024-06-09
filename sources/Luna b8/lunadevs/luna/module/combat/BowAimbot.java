package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.EntityUtilsTwo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;

//Coded By Faith.

public class BowAimbot extends Module{

	public BowAimbot() {
		super("BowAimbot", Keyboard.KEY_NONE, Category.COMBAT, false);
	}


	  private Entity bowbowbowbowtarget;
	  private float velocity;
		
	  public void onUpdate()
	  {
	    if (!this.isEnabled) return; {
	    }
	    this.bowbowbowbowtarget = null;
	    if ((mc.thePlayer.inventory.getCurrentItem() != null) && 
	      ((mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow)) && 
	      (mc.gameSettings.keyBindUseItem.pressed))
	    {
	      this.bowbowbowbowtarget = EntityUtilsTwo.getClosestEntity(true, true);
	      aimAtbowbowbowtarget();
	    }
	  }
	  
	  private void aimAtbowbowbowtarget()
	  {
	    if (this.bowbowbowbowtarget == null) {
	      return;
	    }
	    int bowCharge = mc.thePlayer.getItemInUseDuration();
	    this.velocity = (bowCharge / 20);
	    this.velocity = ((this.velocity * this.velocity + this.velocity * 2.0F) / 3.0F);
	    
	    this.velocity = 1.0F;
	    if (this.velocity < 0.1D)
	    {
	      if ((this.bowbowbowbowtarget instanceof EntityLivingBase)) {
	        EntityUtilsTwo.faceEntityClient((EntityLivingBase)this.bowbowbowbowtarget);
	      }
	      return;
	    }
	    if (this.velocity > 1.0F) {
	      this.velocity = 1.0F;
	    }
	    double posX = 
	      this.bowbowbowbowtarget.posX + (this.bowbowbowbowtarget.posX - this.bowbowbowbowtarget.prevPosX) * 5.0D - 
	      mc.thePlayer.posX;
	    double posY = 
	      this.bowbowbowbowtarget.posY + (this.bowbowbowbowtarget.posY - this.bowbowbowbowtarget.prevPosY) * 5.0D + 
	      this.bowbowbowbowtarget.getEyeHeight() - 0.15D - mc.thePlayer.posY - 
	      mc.thePlayer.getEyeHeight();
	    double posZ = 
	      this.bowbowbowbowtarget.posZ + (this.bowbowbowbowtarget.posZ - this.bowbowbowbowtarget.prevPosZ) * 5.0D - 
	      mc.thePlayer.posZ;
	    float yaw = (float)(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
	    double y2 = Math.sqrt(posX * posX + posZ * posZ);
	    float g = 0.006F;
	    float tmp = 
	      (float)(this.velocity * this.velocity * this.velocity * this.velocity - g * (
	      g * (y2 * y2) + 2.0D * posY * (this.velocity * this.velocity)));
	    float pitch = 
	      (float)-Math.toDegrees(Math.atan((this.velocity * this.velocity - 
	      Math.sqrt(tmp)) / (g * y2)));
	    mc.thePlayer.rotationYaw = yaw;
	    mc.thePlayer.rotationPitch = pitch;
	  }


	
	@Override
	public String getValue() {
		return null;
	}
	
	
	
}
