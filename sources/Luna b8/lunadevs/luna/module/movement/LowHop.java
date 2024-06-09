package lunadevs.luna.module.movement;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPlayerUpdate;
import lunadevs.luna.events.EventType;
import lunadevs.luna.events.MoveEvent;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.player.NoSlowdownModes;
import lunadevs.luna.utils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class LowHop extends Module{

	public LowHop() {
		super("LowHop", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}
	
	
	  private int timeState;
	  public int state;
	  public double moveSpeed;
	  private double lastDist;
	  private int cooldownHops;
	  private boolean wasOnWater = false;
	  private boolean doTime = true;
	  private TimeHelper time = new TimeHelper();

	  
		  private double getBaseMoveSpeed()
		  {
		    double baseSpeed = 0.2673D;
		    if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
		    {
		      int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
		      baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
		    }
		    return baseSpeed;
		  }
		 
			@Override
			public void onEnable() {
			    this.cooldownHops = 2;
			    this.state = 0;
			    this.moveSpeed = (getBaseMoveSpeed() * 0.800000011920929D);
			    super.onEnable();
			}

			@Override
			public void onDisable() {
				this.state = 0;
			    super.onDisable();
			}

			
			  @EventTarget
			  public void onUpdate(EventPlayerUpdate event)
			  {
			    if ((mc.thePlayer.isOnLiquid()) || (mc.thePlayer.isInLiquid())) {
			      return;
			    }
			    if (event.getType() == EventType.PRE)
			    {
			      if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F) && 
			        (mc.thePlayer.onGround))
			      {
			        this.cooldownHops = 2;
			        this.moveSpeed *= 1.0800000429153442D;
			        this.state = 2;
			      }
			      double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
			      double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
			      this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
			    }
			  }
			
			  @EventTarget
			  public void onMove(MoveEvent event)
			  {
				  Minecraft.thePlayer.motionY = -0.1f;
			    if ((mc.thePlayer.isOnLiquid()) || (mc.thePlayer.isInLiquid()))
			    {
			      this.cooldownHops = 2;
			      return;
			    }
			    if ((mc.thePlayer.isInsideBlock()) || (mc.thePlayer.isOnLadder()) || (mc.thePlayer.isEntityInsideOpaqueBlock()))
			    {
			      this.moveSpeed = 0.0D;
			      this.wasOnWater = true;
			      return;
			    }
			    if (this.wasOnWater)
			    {
			      this.moveSpeed = 0.0D;
			      this.wasOnWater = false;
			      return;
			    }
			    if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F))
			    {
			      if (!mc.thePlayer.isMoving()) {
			        this.moveSpeed = (getBaseMoveSpeed() * 0.087D);
			      } else {
			        this.moveSpeed = (getBaseMoveSpeed() * 0.05D);
			      }
			      return;
			    }
			    if (mc.thePlayer.onGround)
			    {
			    	Minecraft.thePlayer.motionY = -0.1f;
			      this.state = 2;
			      net.minecraft.util.Timer.timerSpeed = 1.0F;
			      this.timeState += 1;
			      if (this.timeState > 4) {
			        this.timeState = 0;
			      }
			      if (this.time.hasReached(3000L))
			      {
			        this.doTime = (!this.doTime);
			        this.time.reset();
			      }
			      mc.thePlayer.motionY *= 1.0499999523162842D;
			    }
			    if (round(mc.thePlayer.posY - (int)mc.thePlayer.posY, 3) == round(0.138D, 3))
			    {
			      event.y -= 0.09316090325960147D;
			      mc.thePlayer.posY -= 0.09316090325960147D;
			    }
			    if ((this.state == 1) && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)))
			    {
			      this.state = 2;
			      this.moveSpeed = (1.35D * getBaseMoveSpeed() - 0.01D);
			    }
			    else if (this.state == 2)
			    {
			      this.state = 3;
			      if ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F))
			      {
			        mc.thePlayer.motionY = 0.4D;
			        event.y = 0.4D;
			        if (this.cooldownHops > 0) {
			          this.cooldownHops -= 1;
			        }
			        this.moveSpeed *= 2.149D;
			      }
			    }
			    else if (this.state == 3)
			    {
			      this.state = 4;
			      double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
			      this.moveSpeed = (this.lastDist - difference);
			    }
			    else
			    {
			      if ((Minecraft.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0) || (mc.thePlayer.isCollidedVertically)) {
			        this.state = 1;
			      }
			      this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
			    }
			    this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
			    MovementInput movementInput = mc.thePlayer.movementInput;
			    float forward = movementInput.moveForward;
			    float strafe = movementInput.moveStrafe;
			    float yaw = mc.thePlayer.rotationYaw;
			    if ((forward == 0.0F) && (strafe == 0.0F))
			    {
			      event.x = 0.0D;
			      event.z = 0.0D;
			    }
			    else if (forward != 0.0F)
			    {
			      if (strafe >= 1.0F)
			      {
			        yaw += (forward > 0.0F ? -45 : 45);
			        strafe = 0.0F;
			      }
			      else if (strafe <= -1.0F)
			      {
			        yaw += (forward > 0.0F ? 45 : -45);
			        strafe = 0.0F;
			      }
			      if (forward > 0.0F) {
			        forward = 1.0F;
			      } else if (forward < 0.0F) {
			        forward = -1.0F;
			      }
			    }
			    double mx = Math.cos(Math.toRadians(yaw + 90.0F));
			    double mz = Math.sin(Math.toRadians(yaw + 90.0F));
			    double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
			    double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
			    if (((mc.thePlayer.isUsingItem()) || (mc.thePlayer.isBlocking())) && (!NoSlowdownModes.active==true))
			    {
			      motionX *= 0.4000000059604645D;
			      motionZ *= 0.4000000059604645D;
			    }
			    event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
			    event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);
			    
			    mc.thePlayer.stepHeight = 0.6F;
			    if ((forward == 0.0F) && (strafe == 0.0F))
			    {
			      event.x = 0.0D;
			      event.z = 0.0D;
			    }
			    else
			    {
			      boolean collideCheck = false;
			      if (Minecraft.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.expand(0.5D, 0.0D, 0.5D)).size() > 0) {
			        collideCheck = true;
			      }
			      if (forward != 0.0F)
			      {
			        if (strafe >= 1.0F)
			        {
			          yaw += (forward > 0.0F ? -45 : 45);
			          strafe = 0.0F;
			        }
			        else if (strafe <= -1.0F)
			        {
			          yaw += (forward > 0.0F ? 45 : -45);
			          strafe = 0.0F;
			        }
			        if (forward > 0.0F) {
			          forward = 1.0F;
			        } else if (forward < 0.0F) {
			          forward = -1.0F;
			        }
			      }
			    }
			  }
			  
			  public double round(double value, int places)
			  {
			    if (places < 0) {
			      throw new IllegalArgumentException();
			    }
			    BigDecimal bd = new BigDecimal(value);
			    bd = bd.setScale(places, RoundingMode.HALF_UP);
			    return bd.doubleValue();
			  }
			  
	@Override
	public String getValue() {
		return null;
	}

}
