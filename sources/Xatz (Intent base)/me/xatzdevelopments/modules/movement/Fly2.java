package me.xatzdevelopments.modules.movement;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventPacket;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.Stopwatch;
import me.xatzdevelopments.util.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Fly2 extends Module {
	public ModeSetting flymode = new ModeSetting("Fly Mode", "Vanilla", "RedeskyBounce", "HypixelWtf", "Redesky", "Redesky3");
	public ModeSetting hypixelmode = new ModeSetting("Hypixel Mode", "1", "1", "2");
	public NumberSetting Speed = new NumberSetting("Speed", 3, 1, 5, 1);
	public NumberSetting timerSpeed = new NumberSetting("Timer Speed", 1, 1, 3, 1);
	public BooleanSetting damage = new BooleanSetting("Damage", true);
	public NumberSetting redespeed = new NumberSetting("Rede Speed", 0.7, 0.2, 1.5, 0.2);
	public BooleanSetting bobbing = new BooleanSetting("Bobbing", false);
	private double OPosX;
	private double OPosY;
	private double OPosZ;
    public int ticks = 0;
    public int delay = 0;
	public static boolean overridenotification = false;
	public Timer jumptimer = new Timer();
	private boolean idk = false;
	private Stopwatch flyStopwatch = new Stopwatch();
	int state;
	public Fly2() {
		super("Fly2", Keyboard.KEY_U, Category.MOVEMENT, "Fly like a bird");
		this.addSettings(Speed, flymode, timerSpeed, damage, redespeed, bobbing);
		this.addonText = "l";
	}
	
	@Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (event.isPre()) {
				if(this.bobbing.isEnabled()) {
                    this.mc.thePlayer.cameraYaw = 0.1f;
                }
				if(this.flymode.getMode().equalsIgnoreCase("Redesky")) {
					if (!this.mc.thePlayer.onGround) {
	                    this.mc.thePlayer.posY = this.mc.thePlayer.lastTickPosY;
	                    this.mc.thePlayer.onGround = this.mc.thePlayer.onGround;
	                }
	                if (this.mc.thePlayer.onGround) {
	                    this.mc.thePlayer.posY = this.mc.thePlayer.lastTickPosY;
	                    this.mc.thePlayer.onGround = this.mc.thePlayer.onGround;
	                }
/*                    this.mc.thePlayer.capabilities.isFlying = false;
                    this.mc.thePlayer.motionY = 0.0;
                    if (this.mc.gameSettings.keyBindForward.pressed) {
                        this.mc.timer.timerSpeed = 1.0f;
                        this.mc.thePlayer.motionX = 0.0;
                        this.mc.thePlayer.motionZ = 0.0;
                        this.mc.thePlayer.motionY = 0.0;
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + this.mc.thePlayer.getLookVec().xCoord * 7.0, this.mc.thePlayer.posY + 0.7, this.mc.thePlayer.posZ + this.mc.thePlayer.getLookVec().zCoord * 7.0, false));
                    }
                    if (this.mc.gameSettings.keyBindBack.pressed) {
                        this.mc.timer.timerSpeed = 1.0f;
                        this.mc.thePlayer.motionX = 0.0;
                        this.mc.thePlayer.motionZ = 0.0;
                        this.mc.thePlayer.motionY = 0.0;
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + this.mc.thePlayer.getLookVec().xCoord * -5.0, this.mc.thePlayer.posY + 0.7, this.mc.thePlayer.posZ + this.mc.thePlayer.getLookVec().zCoord * -5.0, false));
                    }
                    if (this.mc.gameSettings.keyBindJump.pressed) {
                        this.mc.thePlayer.motionY = 0.0;
                        this.mc.timer.timerSpeed = 0.6f;
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 10.0, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 10.0, this.mc.thePlayer.posZ, false));
                    } else if (this.mc.gameSettings.keyBindSneak.pressed) {
                        this.mc.thePlayer.motionY = 0.0;
                        this.mc.timer.timerSpeed = 0.6f;
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 10.0, this.mc.thePlayer.posZ, false));
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 10.0, this.mc.thePlayer.posZ, false));
                    }
                    this.mc.thePlayer.motionX = 0.0;
                    this.mc.thePlayer.motionZ = 0.0;
                }
            if (this.flymode.getMode().equalsIgnoreCase("RedeskyBounce")) {
                int n = 0;
                this.mc.thePlayer.capabilities.isFlying = false;
                if (++n >= 20) {
                    this.mc.timer.timerSpeed = 0.6f;
                    this.mc.thePlayer.motionY = 0.0;
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + this.mc.thePlayer.motionX * 4.0, this.mc.thePlayer.posY + 2.0, this.mc.thePlayer.posZ + this.mc.thePlayer.motionZ * 4.0);
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + this.mc.thePlayer.getLookVec().xCoord * 7.0, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + this.mc.thePlayer.getLookVec().zCoord * 7.0, false));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 10.0, this.mc.thePlayer.posZ, false));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 10.0, this.mc.thePlayer.posZ, false));
                    n = 0;
                }
                if (n <= 20) {
                    this.mc.timer.timerSpeed = 1.0f;
                }
            }
            if (this.flymode.getMode().equalsIgnoreCase("Redesky3")) {
                this.mc.thePlayer.capabilities.isFlying = false;
                this.mc.thePlayer.motionY = 0.0;
                this.mc.timer.timerSpeed = 0.2f;
                if (this.mc.gameSettings.keyBindJump.pressed) {
                    this.mc.thePlayer.motionY += this.Speed.getValue();
                }
                if (this.mc.gameSettings.keyBindSneak.pressed) {
                    this.mc.thePlayer.motionY -= this.Speed.getValue();
                }
                this.mc.thePlayer.setSpeed((float) this.Speed.getValue());
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + this.mc.thePlayer.motionX, this.mc.thePlayer.posY + this.mc.thePlayer.motionY, this.mc.thePlayer.posZ + this.mc.thePlayer.motionZ);
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, true));
            }
        }
    } else if (event instanceof EventPacket && this.flymode.getMode().equalsIgnoreCase("Redesky3") && ((EventPacket)event).getPacket() instanceof C03PacketPlayer) {
        this.mc.thePlayer.posY = this.mc.thePlayer.lastTickPosY;
        this.mc.thePlayer.posX = this.mc.thePlayer.lastTickPosX;
        this.mc.thePlayer.posZ = this.mc.thePlayer.lastTickPosZ;
    } */
	}
            }
        }
	}
	
    @Override
    public void onDisable() {
        if (this.flymode.getMode().equalsIgnoreCase("Redesky3")) {
            this.mc.thePlayer.swingItem();
        }
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.timer.timerSpeed = 1.0f;
        this.mc.thePlayer.jumpMovementFactor = 0.02f;
        this.mc.thePlayer.speedInAir = 0.02f;
        this.delay = 0;
        this.ticks = 0;
//        this.boosted = false;
    }

    @Override
    public void onEnable() {
        if (this.flymode.getMode().equalsIgnoreCase("HypixelWtf") && this.mc.thePlayer.onGround) {
            this.mc.thePlayer.jump(toggled);
            double d = this.mc.thePlayer.posY + 4.0;
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, d, this.mc.thePlayer.posZ, false));
        }
        if (this.flymode.getMode().equalsIgnoreCase("Redesky3")) {
            this.mc.thePlayer.swingItem();
        }
    }
}
	
	