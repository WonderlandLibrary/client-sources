package Squad.Modules.Movement;

import java.util.ArrayList;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.Utils.BlockUtil;
import Squad.Utils.TimeHelper;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class LongJump
extends Module
{
public static boolean active;
boolean speedTick;
static int delay;
static int delay2;
boolean hypickle2bigboostready = true;
int hypickle2delayafterbigboost = 0;
private double moveSpeed;
public static boolean isenabled;
private double lastDist;
public static int stage;
private double boost = 4.0D;
private TimeHelper time = new TimeHelper();
private boolean move = true;
private boolean canChangeMotion = false;
private static int airTicks;
private static int groundTicks;
private static float headStart;
private static double lastHDistance;
private static boolean isSpeeding;
static int timeonground;
static boolean off = false;

public LongJump()
{
  super("LongJump", 44, 2712319, Category.Movement);
  if (Minecraft.getMinecraft().theWorld != null) {
    setToggled(true);
  }
}
  public void setup() {
	 	ArrayList<String> options1 = new ArrayList<>();
	 	Squad.instance.setmgr.rSetting(new Setting("LJMods", this, "", options1));
}

@EventTarget
public void onUpdate(EventUpdate event)
{
  if (((mc.gameSettings.keyBindForward.getIsKeyPressed()) || (mc.gameSettings.keyBindLeft.getIsKeyPressed()) || (mc.gameSettings.keyBindRight.getIsKeyPressed()) || (mc.gameSettings.keyBindBack.getIsKeyPressed())) && (mc.gameSettings.keyBindJump.pressed))
  {
    setDisplayname("Longjump");
    LongJumping();
  }
}

public static void LongJumping()
{
  if ((Minecraft.getMinecraft().thePlayer == null) || (Minecraft.getMinecraft().theWorld == null)) {
    return;
  }
  if (!isenabled) {
    return;
  }
  timeonground = Minecraft.getMinecraft().thePlayer.onGround ? ++timeonground : 0;
  
  delay += 1;
  delay2 += 1;
  if (mc.gameSettings.keyBindSneak.pressed) {
    return;
  }
  Minecraft.getMinecraft().thePlayer.setSprinting(false);
  mc.gameSettings.keyBindLeft.pressed = false;
  mc.gameSettings.keyBindRight.pressed = false;
  mc.gameSettings.keyBindBack.pressed = false;
  if (isMoving())
  {
    if ((Minecraft.getMinecraft().theWorld != null) && (Minecraft.getMinecraft().thePlayer != null) && (Minecraft.getMinecraft().thePlayer.onGround) && 
      (!Minecraft.getMinecraft().thePlayer.isDead)) {
      lastHDistance = 0.0D;
    }
    float direction3 = Minecraft.getMinecraft().thePlayer.rotationYaw + (
      Minecraft.getMinecraft().thePlayer.moveForward < 0.0F ? 180 : 0) + (
      Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0F ? -90.0F * (
      Minecraft.getMinecraft().thePlayer.moveForward > 0.0F ? 0.5F : Minecraft.getMinecraft().thePlayer.moveForward < 0.0F ? -0.5F : 1.0F) : 0.0F) - (
      Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0F ? -90.0F * (
      Minecraft.getMinecraft().thePlayer.moveForward > 0.0F ? 0.5F : Minecraft.getMinecraft().thePlayer.moveForward < 0.0F ? -0.5F : 1.0F) : 0.0F);
    float xDir = (float)Math.cos((direction3 + 90.0F) * 3.141592653589793D / 180.0D);
    float zDir = (float)Math.sin((direction3 + 90.0F) * 3.141592653589793D / 180.0D);
    if (!Minecraft.getMinecraft().thePlayer.isCollidedVertically)
    {
      airTicks += 1;
      isSpeeding = true;
      if (mc.gameSettings.keyBindSneak.isPressed()) {
        Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(
          new C03PacketPlayer.C04PacketPlayerPosition(0.0D, 2.147483647E9D, 0.0D, false));
      }
      groundTicks = 0;
      if (!Minecraft.getMinecraft().thePlayer.isCollidedVertically)
      {
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.07190068807140403D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.3499999940395355D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.10306193759436909D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.550000011920929D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.13395038817442878D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.6700000166893005D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.16635183030382D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.6899999976158142D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.19088711097794803D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.7099999785423279D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.21121925191528862D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.20000000298023224D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.11979897632390576D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.9300000071525574D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.18758479151225355D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.7200000286102295D;
        }
        if (Minecraft.getMinecraft().thePlayer.motionY == -0.21075983825251726D) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.7599999904632568D;
        }
        if (getDistance(Minecraft.getMinecraft().thePlayer, 69.0D) < 0.5D) {
          if (!BlockUtil.getBlock(new BlockPos(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY - 0.32D, Minecraft.getMinecraft().thePlayer.posZ)).isFullCube())
          {
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.23537393014173347D) {
              Minecraft.getMinecraft().thePlayer.motionY *= 0.029999999329447746D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.08531999505205401D) {
              Minecraft.getMinecraft().thePlayer.motionY *= -0.5D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.03659320313669756D) {
              Minecraft.getMinecraft().thePlayer.motionY *= -0.10000000149011612D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.07481386749524899D) {
              Minecraft.getMinecraft().thePlayer.motionY *= -0.07000000029802322D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.0732677700939672D) {
              Minecraft.getMinecraft().thePlayer.motionY *= -0.05000000074505806D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.07480988066790395D) {
              Minecraft.getMinecraft().thePlayer.motionY *= -0.03999999910593033D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.0784000015258789D) {
              Minecraft.getMinecraft().thePlayer.motionY *= 0.10000000149011612D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.08608320193943977D) {
              Minecraft.getMinecraft().thePlayer.motionY *= 0.10000000149011612D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.08683615560584318D) {
              Minecraft.getMinecraft().thePlayer.motionY *= 0.05000000074505806D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.08265497329678266D) {
              Minecraft.getMinecraft().thePlayer.motionY *= 0.05000000074505806D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.08245009535659828D) {
              Minecraft.getMinecraft().thePlayer.motionY *= 0.05000000074505806D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.08244005633718426D) {
              Minecraft.getMinecraft().thePlayer.motionY = -0.08243956442521608D;
            }
            if (Minecraft.getMinecraft().thePlayer.motionY == -0.08243956442521608D) {
              Minecraft.getMinecraft().thePlayer.motionY = -0.08244005590677261D;
            }
            if ((Minecraft.getMinecraft().thePlayer.motionY <= -0.1D) || (Minecraft.getMinecraft().thePlayer.motionY >= -0.08D) || 
              (Minecraft.getMinecraft().thePlayer.onGround) || (!mc.gameSettings.keyBindForward.pressed)) {

            }
            Minecraft.getMinecraft().thePlayer.motionY = -9.999999747378752E-5D;

          }
        }
        if ((Minecraft.getMinecraft().thePlayer.motionY < -0.2D) && (Minecraft.getMinecraft().thePlayer.motionY > -0.24D)) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.7D;
        }
        if ((Minecraft.getMinecraft().thePlayer.motionY < -0.25D) && (Minecraft.getMinecraft().thePlayer.motionY > -0.32D)) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.8D;
        }
        if ((Minecraft.getMinecraft().thePlayer.motionY < -0.35D) && (Minecraft.getMinecraft().thePlayer.motionY > -0.8D)) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.98D;
        }
        if ((Minecraft.getMinecraft().thePlayer.motionY < -0.8D) && (Minecraft.getMinecraft().thePlayer.motionY > -1.6D)) {
          Minecraft.getMinecraft().thePlayer.motionY *= 0.99D;
        }
      }
      label1588:
      mc.timer.timerSpeed = 0.85F;
      double[] speedVals = { 0.420606D, 0.417924D, 0.415258D, 0.412609D, 0.409977D, 0.407361D, 
        0.404761D, 0.402178D, 0.399611D, 0.39706D, 0.394525D, 0.392D, 0.3894D, 0.38644D, 0.383655D, 0.381105D, 
        0.37867D, 0.37625D, 0.37384D, 0.37145D, 0.369D, 0.3666D, 0.3642D, 0.3618D, 0.35945D, 0.357D, 0.354D, 0.351D, 
        0.348D, 0.345D, 0.342D, 0.339D, 0.336D, 0.333D, 0.33D, 0.327D, 0.324D, 0.321D, 0.318D, 0.315D, 0.312D, 0.309D, 
        0.307D, 0.305D, 0.303D, 0.3D, 0.297D, 0.295D, 0.293D, 0.291D, 0.289D, 0.287D, 0.285D, 0.283D, 0.281D, 0.279D, 
        0.277D, 0.275D, 0.273D, 0.271D, 0.269D, 0.267D, 0.265D, 0.263D, 0.261D, 0.259D, 0.257D, 0.255D, 0.253D, 
        0.251D, 0.249D, 0.247D, 0.245D, 0.243D, 0.241D, 0.239D, 0.237D };
      if (mc.gameSettings.keyBindForward.pressed)
      {
        try
        {
          Minecraft.getMinecraft().thePlayer.motionX = 
            (xDir * speedVals[(airTicks - 1)] * 3.0D * addSpeedForSpeedEffect());
          Minecraft.getMinecraft().thePlayer.motionZ = 
            (zDir * speedVals[(airTicks - 1)] * 3.0D * addSpeedForSpeedEffect());
          off = true;
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
      }
      else
      {
        Minecraft.getMinecraft().thePlayer.motionX = 0.0D;
        Minecraft.getMinecraft().thePlayer.motionZ = 0.0D;
      }
    }
    else
    {
      mc.timer.timerSpeed = 1.0F;
      airTicks = 0;
      groundTicks += 1;
      headStart -= 1.0F;
      Minecraft.getMinecraft().thePlayer.motionX /= 13.0D;
      Minecraft.getMinecraft().thePlayer.motionZ /= 13.0D;
      if (groundTicks == 1)
      {
        updatePosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, 
          Minecraft.getMinecraft().thePlayer.posZ);
        updatePosition(Minecraft.getMinecraft().thePlayer.posX + 0.0624D, Minecraft.getMinecraft().thePlayer.posY, 
          Minecraft.getMinecraft().thePlayer.posZ);
        updatePosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.419D, 
          Minecraft.getMinecraft().thePlayer.posZ);
        updatePosition(Minecraft.getMinecraft().thePlayer.posX + 0.0624D, Minecraft.getMinecraft().thePlayer.posY, 
          Minecraft.getMinecraft().thePlayer.posZ);
        updatePosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.419D, 
          Minecraft.getMinecraft().thePlayer.posZ);
      }
      if (groundTicks > 2)
      {
        groundTicks = 0;
        Minecraft.getMinecraft().thePlayer.motionX = (xDir * 0.3D);
        Minecraft.getMinecraft().thePlayer.motionZ = (zDir * 0.3D);
        Minecraft.getMinecraft().thePlayer.motionY = 0.42399999499320984D;
      }
    }
  }
}

private double getBaseMoveSpeed()
{
  double baseSpeed = 0.2873D;
  if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed))
  {
    int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
    baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
  }
  return baseSpeed;
}

private static double addSpeedForSpeedEffect()
{
  double baseSpeed = 1.0D;
  if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed))
  {
    int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
    baseSpeed = 0.4D;
  }
  return baseSpeed;
}

public static void updatePosition(double x, double y, double z)
{
  Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, Minecraft.getMinecraft().thePlayer.onGround));
}

private static double getDistance(EntityPlayer player, double distance)
{
  List boundingBoxes = player.worldObj.getCollidingBoundingBoxes(player, 
    player.getEntityBoundingBox().addCoord(0.0D, -distance, 0.0D));
  if (boundingBoxes.isEmpty()) {
    return 0.0D;
  }
  double y = 0.0D;
  
  return player.posY - y;
}

public static boolean isMoving()
{
  if ((Minecraft.getMinecraft().thePlayer.moveForward == 0.0F) && (	mc.thePlayer.moveStrafing == 0.0F)) {
    return false;
  }
  return true;
}

public void onEnable()
{
  isenabled = true;
  active = true;
  this.speedTick = false;
  this.canChangeMotion = true;
  stage = 0;
  off = false;
  mc.timer.timerSpeed = 1.0F;
}

public void onDisable()
{
  active = false;
  mc.timer.timerSpeed = 1.0F;
  isenabled = false;
}
}
