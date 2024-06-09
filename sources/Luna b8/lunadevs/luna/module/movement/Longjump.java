package lunadevs.luna.module.movement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPlayerUpdate;
import lunadevs.luna.events.EventType;
import lunadevs.luna.events.MoveEvent;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import lunadevs.luna.utils.BlockUtils;
import lunadevs.luna.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class Longjump extends Module{
	
	
	@Option.Op(name="Old")
	public static boolean old = false;
	@Option.Op(name="New")
	public static boolean updated = true;
	
    public static boolean active;
    private int airTicks;
    private int groundTicks;
    boolean off = false;
    public double yOffset;
    private int stage;
    private double boost;
    private double moveSpeed;
    private double lastDist;
	
	public Longjump() {
		super("Longjump", Keyboard.KEY_F, Category.MOVEMENT, true);
	    this.moveSpeed = 0.2873D;
	    this.boost = 3.0D;
	}
	
	public void longjump(){
        float zDir;
        float xDir;
        if (mc.gameSettings.keyBindSneak.pressed) {
                return;
            }
            Minecraft.thePlayer.setSprinting(false);
            mc.gameSettings.keyBindLeft.pressed = false;
            mc.gameSettings.keyBindRight.pressed = false;
            mc.gameSettings.keyBindBack.pressed = false;
            if (this.isMoving()) {
                if (Keyboard.isKeyDown((int)56)) {
                    this.updatePosition(0.0, 2.147483647E9, 0.0);
                }
                if (mc.theWorld != null && Minecraft.thePlayer != null && Minecraft.thePlayer.onGround && !Minecraft.thePlayer.isDead) {
                }
                float direction3 = Minecraft.thePlayer.rotationYaw + (float)(Minecraft.thePlayer.moveForward < 0.0f ? 180 : 0) + (Minecraft.thePlayer.moveStrafing > 0.0f ? -90.0f * (Minecraft.thePlayer.moveForward < 0.0f ? -0.5f : (Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f)) : 0.0f) - (Minecraft.thePlayer.moveStrafing < 0.0f ? -90.0f * (Minecraft.thePlayer.moveForward < 0.0f ? -0.5f : (Minecraft.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f)) : 0.0f);
                xDir = (float)Math.cos((double)(direction3 + 90.0f) * 3.141592653589793 / 180.0);
                zDir = (float)Math.sin((double)(direction3 + 90.0f) * 3.141592653589793 / 180.0);
                if (!Minecraft.thePlayer.isCollidedVertically) {
                    ++this.airTicks;
                    if (mc.gameSettings.keyBindSneak.isPressed()) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(0.0, 2.147483647E9, 0.0, false));
                    }
                    this.groundTicks = 0;
                    if (!Minecraft.thePlayer.isCollidedVertically) {
                        if (Minecraft.thePlayer.motionY == -0.07190068807140403) {
                            Minecraft.thePlayer.motionY *= 0.3499999940395355;
                        }
                        if (Minecraft.thePlayer.motionY == -0.10306193759436909) {
                            Minecraft.thePlayer.motionY *= 0.550000011920929;
                        }
                        if (Minecraft.thePlayer.motionY == -0.13395038817442878) {
                            Minecraft.thePlayer.motionY *= 0.6700000166893005;
                        }
                        if (Minecraft.thePlayer.motionY == -0.16635183030382) {
                            Minecraft.thePlayer.motionY *= 0.6899999976158142;
                        }
                        if (Minecraft.thePlayer.motionY == -0.19088711097794803) {
                            Minecraft.thePlayer.motionY *= 0.7099999785423279;
                        }
                        if (Minecraft.thePlayer.motionY == -0.21121925191528862) {
                            Minecraft.thePlayer.motionY *= 0.20000000298023224;
                        }
                        if (Minecraft.thePlayer.motionY == -0.11979897632390576) {
                            Minecraft.thePlayer.motionY *= 0.9300000071525574;
                        }
                        if (Minecraft.thePlayer.motionY == -0.18758479151225355) {
                            Minecraft.thePlayer.motionY *= 0.7200000286102295;
                        }
                        if (Minecraft.thePlayer.motionY == -0.21075983825251726) {
                            Minecraft.thePlayer.motionY *= 0.7599999904632568;
                        }
                        if (this.getDistance(Minecraft.thePlayer, 69.0) < 0.5 && !BlockUtils.getBlock(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.32, Minecraft.thePlayer.posZ)).isFullCube()) {
                            if (Minecraft.thePlayer.motionY == -0.23537393014173347) {
                                Minecraft.thePlayer.motionY *= 0.029999999329447746;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08531999505205401) {
                                Minecraft.thePlayer.motionY *= -0.5;
                            }
                            if (Minecraft.thePlayer.motionY == -0.03659320313669756) {
                                Minecraft.thePlayer.motionY *= -0.10000000149011612;
                            }
                            if (Minecraft.thePlayer.motionY == -0.07481386749524899) {
                                Minecraft.thePlayer.motionY *= -0.07000000029802322;
                            }
                            if (Minecraft.thePlayer.motionY == -0.0732677700939672) {
                                Minecraft.thePlayer.motionY *= -0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.07480988066790395) {
                                Minecraft.thePlayer.motionY *= -0.03999999910593033;
                            }
                            if (Minecraft.thePlayer.motionY == -0.0784000015258789) {
                                Minecraft.thePlayer.motionY *= 0.10000000149011612;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08608320193943977) {
                                Minecraft.thePlayer.motionY *= 0.10000000149011612;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08683615560584318) {
                                Minecraft.thePlayer.motionY *= 0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08265497329678266) {
                                Minecraft.thePlayer.motionY *= 0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08245009535659828) {
                                Minecraft.thePlayer.motionY *= 0.05000000074505806;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08244005633718426) {
                                Minecraft.thePlayer.motionY = -0.08243956442521608;
                            }
                            if (Minecraft.thePlayer.motionY == -0.08243956442521608) {
                                Minecraft.thePlayer.motionY = -0.08244005590677261;
                            }
                            if (Minecraft.thePlayer.motionY > -0.1 && Minecraft.thePlayer.motionY < -0.08 && !Minecraft.thePlayer.onGround && mc.gameSettings.keyBindForward.pressed) {
                                Minecraft.thePlayer.motionY = -9.999999747378752E-5;
                            }
                        } else {
                            if (Minecraft.thePlayer.motionY < -0.2 && Minecraft.thePlayer.motionY > -0.24) {
                                Minecraft.thePlayer.motionY *= 0.7;
                            }
                            if (Minecraft.thePlayer.motionY < -0.25 && Minecraft.thePlayer.motionY > -0.32) {
                                Minecraft.thePlayer.motionY *= 0.8;
                            }
                            if (Minecraft.thePlayer.motionY < -0.35 && Minecraft.thePlayer.motionY > -0.8) {
                                Minecraft.thePlayer.motionY *= 0.98;
                            }
                            if (Minecraft.thePlayer.motionY < -0.8 && Minecraft.thePlayer.motionY > -1.6) {
                                Minecraft.thePlayer.motionY *= 0.99;
                            }
                        }
                    }
                    Timer.timerSpeed = 0.85f;
                    double[] speedVals = new double[]{0.420606, 0.417924, 0.415258, 0.412609, 0.409977, 0.407361, 0.404761, 0.402178, 0.399611, 0.39706, 0.394525, 0.392, 0.3894, 0.38644, 0.383655, 0.381105, 0.37867, 0.37625, 0.37384, 0.37145, 0.369, 0.3666, 0.3642, 0.3618, 0.35945, 0.357, 0.354, 0.351, 0.348, 0.345, 0.342, 0.339, 0.336, 0.333, 0.33, 0.327, 0.324, 0.321, 0.318, 0.315, 0.312, 0.309, 0.307, 0.305, 0.303, 0.3, 0.297, 0.295, 0.293, 0.291, 0.289, 0.287, 0.285, 0.283, 0.281, 0.279, 0.277, 0.275, 0.273, 0.271, 0.269, 0.267, 0.265, 0.263, 0.261, 0.259, 0.257, 0.255, 0.253, 0.251, 0.249, 0.247, 0.245, 0.243, 0.241, 0.239, 0.237};
                    if (mc.gameSettings.keyBindForward.pressed) {
                        try {
                            Minecraft.thePlayer.motionX = (double)xDir * speedVals[this.airTicks - 1] * boost * this.addSpeedForSpeedEffect();
                            Minecraft.thePlayer.motionZ = (double)zDir * speedVals[this.airTicks - 1] * boost * this.addSpeedForSpeedEffect();
                            this.off = true;
                        }
                        catch (ArrayIndexOutOfBoundsException var6_12) {}
                    } else {
                        Minecraft.thePlayer.motionX = 0.0;
                        Minecraft.thePlayer.motionZ = 0.0;
                    }
                } else {
                    Timer.timerSpeed = 1.0f;
                    this.airTicks = 0;
                    ++this.groundTicks;
                    Minecraft.thePlayer.motionX /= 13.0;
                    Minecraft.thePlayer.motionZ /= 13.0;
                    if (this.groundTicks == 1) {
                        this.updatePosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
                        this.updatePosition(Minecraft.thePlayer.posX + 0.0624, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
                        this.updatePosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.419, Minecraft.thePlayer.posZ);
                        this.updatePosition(Minecraft.thePlayer.posX + 0.0624, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
                        this.updatePosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.419, Minecraft.thePlayer.posZ);
                    }
                    if (this.groundTicks > 2) {
                        this.groundTicks = 0;
                        Minecraft.thePlayer.motionX = (double)xDir * 0.3;
                        Minecraft.thePlayer.motionZ = (double)zDir * 0.3;
                        Minecraft.thePlayer.motionY = 0.42399999499320984;
                    }
                }
            }
        }
	
    private double addSpeedForSpeedEffect() {
        double baseSpeed = 1.0;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed = 0.4;
        }
        return baseSpeed;
    }

    public void updatePosition(double x, double y, double z) {
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, Minecraft.thePlayer.onGround));
    }

    private double getDistance(EntityPlayer player, double distance) {
        List boundingBoxes = player.worldObj.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().addCoord(0.0, - distance, 0.0));
        if (boundingBoxes.isEmpty()) {
            return 0.0;
        }
        double y = 0.0;
        return player.posY - y;
    }

    public boolean isMoving() {
        if (Minecraft.thePlayer.moveForward == 0.0f && Minecraft.thePlayer.moveStrafing == 0.0f) {
            return false;
        }
        return true;
    }

	private static String mode;

	@Override
	public void onUpdate() {
	    if (lunadevs.luna.commands.Longjump.updated==true){
	        mode="New";
	        boost = 4.5;
        }else if (lunadevs.luna.commands.Longjump.updated==false){
	        mode="Old";
	        boost = 3.525;
        }
	    if (this.updated==true) {
	    	lunadevs.luna.commands.Longjump.updated=true;
        	mode="New";
        	boost = 4.5;
        }else if (this.old==true){
        	lunadevs.luna.commands.Longjump.updated=false;
        	mode="Old";
        	boost = 3.525;
        }
		if (!this.isEnabled){
			return;
		}
		if (lunadevs.luna.commands.Longjump.updated==false){
		    longjump();
        }
		super.onUpdate();
	}
	
	@Override
	public void onEnable() {
	    if (mc.thePlayer != null) {
	        this.moveSpeed = getBaseMoveSpeed();
	      }
	      this.lastDist = 0.0D;
	      this.stage = 1;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public String getValue() {
		return mode;
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
	  
        @EventTarget
	  public void jumpmode(MoveEvent event){
	    if (lunadevs.luna.commands.Longjump.updated==true){
	        onMove(event);
        }
      }

	  private void onMove(MoveEvent event)
	  {
		  if(!this.isEnabled) return;
	    if ((mc.thePlayer.moveStrafing == 0.0F) && (mc.thePlayer.moveForward == 0.0F)) {
	      this.stage = 1;
	    }
	    if (round(mc.thePlayer.posY - (int)mc.thePlayer.posY, 3) == round(0.93D, 3))
	    {
	    	mc.thePlayer.motionY -= 0.01D;
	      event.y -= 0.01D;
	    }
	    if ((this.stage == 1) && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)) && (mc.thePlayer.onGround) && (mc.thePlayer.isCollidedVertically) && 
	      (mc.thePlayer.motionY < 0.0D))
	    {
	      this.stage = 2;
	      this.moveSpeed = (this.boost * getBaseMoveSpeed() - 0.01D);
	    }
	    else if (this.stage == 2)
	    {
	      this.stage = 3;
	      mc.thePlayer.motionY = 0.41764345D;
	      event.y = 0.41764345D;
	      this.moveSpeed *= 2.149802D;
	    }
	    else if (this.stage == 3)
	    {
	      this.stage = 4;
	      double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
	      this.moveSpeed = (this.lastDist - difference);
	    }
	    else
	    {
	      if ((Minecraft.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0) || 
	        (mc.thePlayer.isCollidedVertically)) {
	        this.stage = 1;
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
	    event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
	    event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);
	  }
	  
	  @EventTarget
	  private void onUpdate(EventPlayerUpdate event)
	  {
		  if(!this.isEnabled) return;
	    if (event.getType() == EventType.PRE)
	    {
	      boolean speedy = mc.thePlayer.isPotionActive(Potion.moveSpeed);
	      double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
	      double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
	      this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
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
	
}
