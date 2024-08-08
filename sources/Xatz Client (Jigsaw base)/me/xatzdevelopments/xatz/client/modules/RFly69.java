package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.settings.ClientSettings;
import me.xatzdevelopments.xatz.gui.custom.clickgui.CheckBtnSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ModSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.SliderSetting;
import me.xatzdevelopments.xatz.gui.custom.clickgui.ValueFormat;
import me.xatzdevelopments.xatz.module.Module;
import me.xatzdevelopments.xatz.utils.Movement.MoveUtils2;
import me.xatzdevelopments.xatz.utils.Rotation.RMoveUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import me.xatzdevelopments.xatz.client.Unused.inEvents.inEvent;
import me.xatzdevelopments.xatz.client.Unused.inEvents.Listeners.inEventMotion;
import me.xatzdevelopments.xatz.client.events.EventMotion;
import me.xatzdevelopments.xatz.client.events.LEventMotion;
import me.xatzdevelopments.xatz.client.events.UpdateEvent;

public class RFly69 extends Module {

    private float ticks;
    private int afterHurtTicks;
    private boolean hurted;
    private boolean jumped;
    private double OPosX;
    private double OPosY;
    private double OPosZ;
    private double LastOnGroundX;
    private double LastOnGroundY;
    private double LastOnGroundZ;
    private int offGroundTicks;
    private double onGroundTicks;
    private boolean spoofSlot;
    boolean wasonground;
    private int ticksExisted;
    boolean mlg;
    private BlockPos currentPos;
    float cameraPitch;
    boolean moveCancel;
    BlockPos pos;
    ItemStack itemStack;	
	
    
    @Override
	public ModSetting[] getModSettings() {
		SliderSetting<Number> RedeflySpeed = new SliderSetting<Number>("RedeflySpeed", ClientSettings.RedeflySpeed, 1.0, 6, 0.0, ValueFormat.DECIMAL);
		return new ModSetting[] { RedeflySpeed };
	}
	public RFly69() {
		super("Redesky-Fly", Keyboard.KEY_NONE, Category.HIDDEN, "Experimental fly.");
	} // Movement
    
	public void onDisable() {
		mc.timer.timerSpeed = 1;
		boolean idk = false;
		mc.thePlayer.capabilities.isFlying = false;
		mc.thePlayer.capabilities.setFlySpeed(0.05f);
	}
	
	public void onEnable() {
		this.OPosX = this.mc.thePlayer.posX;
        this.OPosY = this.mc.thePlayer.posY;
        this.OPosZ = this.mc.thePlayer.posZ;


				
	}
	public void oninEvent(inEvent e) {
		if(e instanceof inEventMotion) {
                    if (this.mc.gameSettings.keyBindJump.getIsKeyPressed() && this.mc.thePlayer.fallDistance > 0.0f) {
                        this.OPosY = this.mc.thePlayer.posY + 0.001;
                    }
                    if ((this.mc.thePlayer.posY < this.OPosY + 0.5 && this.mc.thePlayer.fallDistance > 0.0f) || this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.motionY = 0.42;
                        final double direction2 = MoveUtils2.getDirection();
                        double posX4 = -Math.sin(direction2) * 6.0;
                        double posZ4 = Math.cos(direction2) * 6.0;
                        double posX5 = -Math.sin(direction2) * 18.0;
                        double posZ5 = Math.cos(direction2) * 18.0;
                        if (!MoveUtils2.isMoving()) {
                            posZ4 = (posX4 = (posX5 = (posZ5 = 1.0)));
                        }
                        ((inEventMotion)e).yaw = this.mc.thePlayer.rotationYaw - 180.0f;
                        this.mc.timer.timerSpeed = 2.0f;
                        this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + posX4, this.mc.thePlayer.posY + (this.mc.gameSettings.keyBindJump.getIsKeyPressed() ? 5 : 1), this.mc.thePlayer.posZ + posZ4, false));
                        this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + posX5, this.mc.thePlayer.posY + (this.mc.gameSettings.keyBindJump.getIsKeyPressed() ? 5 : 1), this.mc.thePlayer.posZ + posZ5, false));
                        this.mc.thePlayer.fallDistance = 0.0f;
                    
                        
                    }
				}
	}
}


