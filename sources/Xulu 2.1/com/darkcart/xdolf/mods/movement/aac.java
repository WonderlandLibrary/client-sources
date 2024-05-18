package com.darkcart.xdolf.mods.movement;
 
import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.event.EventTarget;
import com.darkcart.xdolf.util.BlockHelper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.MathUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
 
public class aac extends Module {
   
    public aac() {
        super("aac", "Old", "AAC OnGround Speed [TESTING].", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.MOVEMENT);
    }
    private double speed;
    private int stage;
    private boolean disabling;
    private boolean stopMotionUntilNext;
    private double moveSpeed;
    private boolean spedUp;
    public static boolean canStep;
    private double lastDist;
    public static double yOffset;
    private boolean cancel;
    //int clock = 0;
   @Override
   public void onEnable(){
	    	super.onEnable();
	    }
    @Override
    public void onDisable(){
    	this.disabling = false;
    	Minecraft.player.speedInAir = 0.01F;
        Minecraft.player.speedOnGround = 0.01F;
        net.minecraft.util.Timer.timerSpeed = 1.0F;
    }
    Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void onUpdate(EntityPlayerSP player) {
        if(isEnabled()){
        	if (((Minecraft.player.moveForward != 0.0F) || (Minecraft.player.moveStrafing != 0.0F)) && (!Minecraft.player.isCollidedHorizontally))
            {
              if (Minecraft.player.fallDistance > 3.994D) {
                return;
              }
              if ((Minecraft.player.isInWater()) || (Minecraft.player.isOnLadder())) {
                return;
              }
              Minecraft.player.posY -= 0.3993000090122223D;
              Minecraft.player.motionY = 0.0D;
              Minecraft.player.cameraPitch = 0.3F;
              Minecraft.player.distanceWalkedModified = 44.0F;
              net.minecraft.util.Timer.timerSpeed = 1.0F;
            }
            if ((Minecraft.player.isInWater()) || (Minecraft.player.isOnLadder())) {
              return;
            }
            if ((Minecraft.player.onGround) && ((Minecraft.player.moveForward != 0.0F) || (Minecraft.player.moveStrafing != 0.0F)) && (!Minecraft.player.isCollidedHorizontally))
            {
              Minecraft.player.posY += 0.3993000090122223D;
              Minecraft.player.motionY = 0.3993000090122223D;
              Minecraft.player.distanceWalkedOnStepModified = 44.0F;
              Minecraft.player.motionX *= 1.100999D;
              Minecraft.player.motionZ *= 1.100999D;
              Minecraft.player.cameraPitch = 0.0F;
              net.minecraft.util.Timer.timerSpeed = 1.19999F;
            }
          }
        }
      }

