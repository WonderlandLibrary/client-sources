package me.xatzdevelopments.xatz.client.modules;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.events.PreMotionEvent;
import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Movement.MoveUtils2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class RedeLongJump2 extends Module {

    private int ticks;
    private int afterHurtTicks;
    private boolean hurted;
    private boolean jumped;
    private double OPosX;
    private double OPosY;
    private double OPosZ;
    private int offGroundTicks;
    boolean wasonground;
    private double onGroundTicks;

	public RedeLongJump2() {
		super("RedeskyJump", Keyboard.KEY_NONE, Category.MOVEMENT, "Makes your jumps longer.");
	}
	@Override
	public void onEnable(){
        this.ticks = 0;
        this.afterHurtTicks = 0;
        this.hurted = false;
        this.jumped = false;
        this.OPosX = this.mc.thePlayer.posX;
        this.OPosY = this.mc.thePlayer.posY;
        this.OPosZ = this.mc.thePlayer.posZ;
        this.offGroundTicks = 0;
        this.onGroundTicks = 0.0;
        this.wasonground = false;
         
        super.onEnable();
		
	}
	@Override 
	public void onDisable(){
        MoveUtils2.strafe(0.1f);
        this.mc.thePlayer.speedInAir = 0.02f;
        this.mc.timer.timerSpeed = 1.0f;
		
		super.onDisable();

	}
	@Override
	public void onUpdate(UpdateEvent e){
        if (this.mc.thePlayer.ticksExisted == 1) {
            this.toggled = false;
            this.mc.timer.timerSpeed = 1.0f;
            MoveUtils2.strafe(0.1);
        }
        if (this.mc.thePlayer.onGround) {
            this.offGroundTicks = 0;
            ++this.onGroundTicks;
            this.wasonground = true;
        }
        else {
            ++this.offGroundTicks;
            this.onGroundTicks = 0.0;
        }
        this.mc.timer.timerSpeed = 0.9f;
        this.mc.thePlayer.speedInAir = 0.07f;
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        thePlayer.motionY += 0.05;
        if (this.mc.thePlayer.onGround && MoveUtils2.isMoving()) {
            this.mc.thePlayer.jump();
            this.mc.thePlayer.motionY = 0.67;
        }
	}
}
	
