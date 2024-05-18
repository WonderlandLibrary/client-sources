package net.SliceClient.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.SliceClient.Utils.Wrapper;
import net.SliceClient.event.EventBlockBB;
import net.SliceClient.event.EventPostMotion;
import net.SliceClient.event.EventReceivePacket;
import net.SliceClient.event.EventSendPacket;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;


public class Phase
  extends Module
{
  private int ticks;
  
  public Phase()
  {
    super("Phase", Category.EXPLOITS, 16376546);
  }
  
  public void onDisable()
  {
    EventManager.unregister(this);
  }
  
  public void onEnable()
  {
    EventManager.register(this);
    Minecraft mc = mc;
    thePlayernoClip = false;
  }
  
  @EventTarget
  public void onUpdate(EventPostMotion event) {
    double multiplier = 0.2D;
    double mx = Math.cos(Math.toRadians(thePlayerrotationYaw + 90.0F));
    double mz = Math.sin(Math.toRadians(thePlayerrotationYaw + 90.0F));
    Minecraft mc = mc;
    double n = MovementInput.moveForward * 0.2D * mx;
    Minecraft mc2 = mc;
    double x = n + MovementInput.moveStrafe * 0.2D * mz;
    Minecraft mc3 = mc;
    double n2 = MovementInput.moveForward * 0.2D * mz;
    Minecraft mc4 = mc;
    double z = n2 - MovementInput.moveStrafe * 0.2D * mx;
    double xOff = MovementInput.moveForward * 0.2D * mx + MovementInput.moveStrafe * 0.2D * mz;
    double zOff = MovementInput.moveForward * 0.2D * mz - MovementInput.moveStrafe * 0.2D * mx;
    double xOff2 = MovementInput.moveForward * 0.2D * mx + MovementInput.moveStrafe * 0.2D * mz / 4.0D;
    double zOff2 = MovementInput.moveForward * 0.2D * mz - MovementInput.moveStrafe * 0.2D * mx / 4.0D;
    AxisAlignedBB boundingBox; if ((thePlayerisCollidedHorizontally) && (!Minecraft.thePlayer.isOnLadder()) && (!isInsideBlock())) {
      boundingBox = thePlayerboundingBox;
      maxY -= 1.0D;
      ticks += 1;
      double[] startPos = { thePlayerposX, thePlayerposY, thePlayerposZ };
      BlockPos endPos = new BlockPos(thePlayerposX + xOff * 1.2D, thePlayerposY - 1.0D, thePlayerposZ + zOff * 1.2D);
      Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX + xOff, thePlayerposY, thePlayerposZ + zOff, false));
      Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY - (Wrapper.isOnLiquid() ? 9000.0D : 0.09D), thePlayerposZ, false));
      Wrapper.blinkToPos(startPos, endPos, 0.0D);
      Minecraft.thePlayer.setPosition(thePlayerposX + xOff, thePlayerposY, thePlayerposZ + zOff);
      Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY, thePlayerposZ, false));
      if ((thePlayerisCollidedHorizontally) && (!Minecraft.thePlayer.isOnLadder()) && (!isInsideBlock())) {
        Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX + x, thePlayerposY, thePlayerposZ + z, false));
        for (int i = 1; i < 10; i++) {
          Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, Double.MAX_VALUE * i, thePlayerposZ, false));
        }
        Wrapper.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY - (Wrapper.isOnLiquid() ? 9000.0D : 0.1D), thePlayerposZ, false));
        Minecraft.thePlayer.setPosition(thePlayerposX + x, thePlayerposY, thePlayerposZ + z);
      }
    }
    if ((thePlayerisCollidedHorizontally) && (!Minecraft.thePlayer.isOnLadder()) && (!isInsideBlock())) {
      boundingBox = Minecraft.thePlayer;
    }
  }
  
  private boolean isInsideBlock() {
    Minecraft mc = mc;
    int x = MathHelper.floor_double(thePlayergetEntityBoundingBoxminX);
    


    return false;
  }
  
  @EventTarget
  public void onBB(EventBlockBB event) {
    Minecraft mc = mc;
    if (Minecraft.thePlayer == null) {
      return;
    }
    double n = event.getY();
    Minecraft mc2 = mc;
    if (n > thePlayerposY + (isInsideBlock() ? 0 : 1)) {
      event.setBoundingBox(null);
    }
    Minecraft mc3 = mc;
    if (thePlayerisCollidedHorizontally) {
      double n2 = event.getY();
      Minecraft mc4 = mc;
      if (n2 > thePlayerboundingBox.minY - 0.4D) {
        event.setBoundingBox(null);
      }
    }
  }
  
  @EventTarget
  public void onReceivePacket(EventSendPacket event) {}
  
  @EventTarget
  public void onReceivePacket(EventReceivePacket event)
  {
    if ((event.getPacket() instanceof S12PacketEntityVelocity)) {
      int func_149412_c = ((S12PacketEntityVelocity)event.getPacket()).func_149412_c();
      Minecraft mc = mc;
      if ((func_149412_c == Minecraft.thePlayer.getEntityId()) && (isInsideBlock())) {
        event.setCancelled(true);
      }
    }
  }
  
  public static boolean isOnLiquid(AxisAlignedBB boundingBox) {
    boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.06D, 0.0D);
    boolean onLiquid = false;
    int y = (int)minY;
    for (int x = MathHelper.floor_double(minX); x < MathHelper.floor_double(maxX + 1.0D); x++) {
      for (int z = MathHelper.floor_double(minZ); z < MathHelper.floor_double(maxZ + 1.0D); z++) {
        Block block = Wrapper.getBlock(new BlockPos(x, y, z));
        if (block != Blocks.air) {
          if (!(block instanceof BlockLiquid)) {
            return false;
          }
          onLiquid = true;
        }
      }
    }
    return onLiquid;
  }
}
