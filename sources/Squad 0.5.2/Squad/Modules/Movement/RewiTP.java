
package Squad.Modules.Movement;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Module;
import Squad.commands.Command;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;

public class RewiTP
extends Module
{
public RewiTP()
{
  super("Teleport", 0, 34952, Category.Player);
}

public static boolean izzda = false;
private double x;
private double y;
private double z;
static BlockPos pos4render;
private static TimeHelper delay = new TimeHelper();
public static boolean cancel = false;
private double aac2x;
private double aac2y;
private double aac2z;
private int state = 0;


public void setup(){
 	ArrayList<String> options = new ArrayList<>();
 		options.add("RewiOld");
 		options.add("RewiNew");

 		
 Squad.instance.setmgr.rSetting(new Setting("TeleportMode", this, "RewiOld", options));
}

public void onEnable()
{
  izzda = true;
  EventManager.register(this);
}

public void onDisable()
{
  izzda = false;
  mc.gameSettings.keyBindJump.pressed = false;
  EventManager.unregister(this);
}

public void onRender()
{
  renderBlockOverlay(pos4render);
}

public static void renderBlockOverlay(BlockPos pos) {}

@EventTarget
public void onUpdate(EventUpdate event)
{
	if(Squad.instance.setmgr.getSettingByName("TeleportMode").getValString().equalsIgnoreCase("RewiOld")){	
  setDisplayname("Teleport §7Rewi");
  try
  {
    BlockPos pos = mc.objectMouseOver.func_178782_a();
    this.x = (pos.getX() + 0.5D);
    this.y = (pos.getY() + 1.0D);
    this.z = (pos.getZ() + 0.5D);
    if ((mc.objectMouseOver != null) && (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) && 
      (mc.gameSettings.keyBindAttack.getIsKeyPressed()))
    {
      if (Minecraft.getMinecraft().thePlayer.getDistanceSq(this.x, this.y, this.z) < 3.0D) {
        return;
      }
      Command.msg("§3Teleporting to: §6" + this.x + ", " + this.y + ", " + this.z + " ...");
      Minecraft.getMinecraft().thePlayer.motionY = 0.01F;
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive());
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y, this.z, true));
      for (int i = 0; i < 256; i++) {
        mc.getNetHandler().addToSendQueue(new C00PacketKeepAlive());
      }
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y, this.z, true));
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
      Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, true));
      
      double yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
      yaw = Math.toRadians(yaw);
      double dX = -Math.sin(yaw) * 0.04D;
      double dZ = Math.cos(yaw) * 0.04D;
      Minecraft.getMinecraft().thePlayer.setPositionAndUpdate(Minecraft.getMinecraft().thePlayer.posX + dX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ + dZ);
    }
    pos4render = pos;
  }
  catch (Exception localException) {}
}

if(Squad.instance.setmgr.getSettingByName("TeleportMode").getValString().equalsIgnoreCase("RewiNew")){

		
		  if(mc.gameSettings.keyBindAttack.pressed == true){
              BlockPos pos = mc.objectMouseOver.func_178782_a();
                if (pos != null)
                {
                      this.aac2x = (pos.getX() + 0.5D);
                      this.aac2y = (pos.getY() + 1);
                      this.aac2z = (pos.getZ() + 0.5D);
                      Command.msg("Location: X: " + this.aac2x + " Y: " + this.aac2y + " Z: " + this.aac2z);  
                }
                
		  }
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (mc.thePlayer.isSneaking())
        {
            player.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.aac2x, this.aac2y, this.aac2z, true));
        }      
      }
		
		
	}
}



