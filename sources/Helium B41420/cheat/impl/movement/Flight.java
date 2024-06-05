package rip.helium.cheat.impl.movement;

import net.minecraft.potion.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import rip.helium.ClientBase;
import rip.helium.Helium;
import rip.helium.cheat.*;
import rip.helium.event.minecraft.*;
import rip.helium.utils.*;
import rip.helium.utils.property.abs.*;
import rip.helium.utils.property.impl.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import java.util.*;
import me.hippo.systems.lwjeb.annotation.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;

public class Flight extends Cheat
{
    boolean canPacketBoost;
    //Stopwatch timer;
    private int level;
    private long lastboost;
    private boolean initialboost;
    private boolean secondaryboost;
    private boolean theogdamageboost;
    public static long disabled;
    private int delay;
    private int counter;
    private double moveSpeed, lastDist;
    public double lastreporteddistance;
    public double movementSpeed;
    public double oldX;
    public double oldY;
    public double oldZ;
    boolean allowmcc;
    private final StringsProperty prop_mode;
    private BooleanProperty antikick;
    private BooleanProperty prop_bobbing;
    private BooleanProperty prop_damage;
    boolean damaged;
    private Stopwatch timer = new Stopwatch();
    
    
    public Flight() {
        super("Flight", "Fuck that little faggot!", CheatCategory.MOVEMENT);
        this.prop_mode = new StringsProperty("Fly", "How this cheat will function.", null, false, false, new String[] {"Vanilla", "MCCentral"}, new Boolean[] { true, false, false, false, false, false, false});
        this.prop_bobbing = new BooleanProperty("View Bobbing", "bippity boppity", null, false);
        this.antikick = new BooleanProperty("AntiKick", "no vanilla kicker :DD", null, true);
        this.registerProperties(this.prop_mode, this.prop_bobbing, antikick);
    }
    
    public void onDisable() {
        this.mc.gameSettings.keyBindJump.pressed = false;
        this.mc.timer.timerSpeed = 1.0f;
        allowmcc = false;
        this.getPlayer().stepHeight = 0.6f;
        this.getPlayer().setSpeed(0.0);
        this.lastboost = System.currentTimeMillis();
        Flight.disabled = System.currentTimeMillis();
    }

    
    public void onEnable() {
        timer.reset();
    }




    @Collect
    public void updateevent(PlayerUpdateEvent event) {
        if (prop_mode.getValue().get("MCCentral")) {
            event.setOnGround(true);
        }
    }

    private boolean isColliding(final AxisAlignedBB box) {
        return this.mc.theWorld.checkBlockCollision(box);
    }

    public double getGroundLevel() {
        for (int i = (int)Math.round(this.mc.thePlayer.posY); i > 0; --i) {
            final AxisAlignedBB box = this.mc.thePlayer.boundingBox.addCoord(0.0, 0.0, 0.0);
            box.minY = i - 1;
            box.maxY = i;
            if (this.isColliding(box) && box.minY <= this.mc.thePlayer.posY) {
                return i;
            }
        }
        return 0.0;
    }

    public double fall() {
        double i;
        for (i = this.mc.thePlayer.posY; i > this.getGroundLevel(); i -= 8.0) {
            if (i < this.getGroundLevel()) {
                i = this.getGroundLevel();
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, i, this.mc.thePlayer.posZ, true));
        }
        return i;
    }

    private void ascend() {
        for (double i = this.getGroundLevel(); i < this.mc.thePlayer.posY; i += 8.0) {
            if (i > this.mc.thePlayer.posY) {
                i = this.mc.thePlayer.posY;
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, i, this.mc.thePlayer.posZ, true));
        }
    }




    @Collect
    public void disablerformeme(PlayerUpdateEvent e) {
        if (this.antikick.getValue() && this.timer.hasPassed(700.0f)) {
            this.fall();
            this.ascend();
            this.timer.reset();
        }
    }

    @Collect
    public void playermoveevent(PlayerMoveEvent event) {
        if (prop_mode.getValue().get("MCCentral")) {
            //if (Helium.instance.cheatManager.isCheatEnabled("Flight")) { //fix maybe?
                mc.thePlayer.motionY = 0.0;
                event.setY(event.getY() + 0.7);
                event.setY(event.getY() - 0.3);
                mc.thePlayer.onGround = true;
                mc.thePlayer.setMoveSpeedAris(event, 1.3);
            //}
        }
    }

    @Collect
    public void pue(PlayerUpdateEvent e) {
    	setMode(prop_mode.getSelectedStrings().get(0));
    	if (prop_mode.getValue().get("Vanilla")) {
    	if (e.isPre()) {
    		if (mc.gameSettings.keyBindJump.pressed) {
    			mc.thePlayer.motionY = 0.8;
    		}
    		else if (mc.gameSettings.keyBindJump.pressed) {
    			e.setPosY(e.getPosY() - 1);
    		}
    		else {
    			mc.thePlayer.motionY = 0.0;
    		}
    		}
        }
    }
    
    @Collect
    public void onMove(final PlayerMoveEvent event) {
    	if (prop_mode.getValue().get("Vanilla")) {
    	    if (level == 1) {
                mc.thePlayer.setMoveSpeedAris(event, 4.9);
            }

    		//mc.thePlayer.setMoveSpeedAris(event, 1.3);
    		//mc.timer.timerSpeed = 1.8f;
    	}
        //mc.thePlayer.setMoveSpeedAris(event, this.speed);
    }
    
    
    @Collect
    public void moveee(PlayerMoveEvent e) {
        if (this.mc.thePlayer.ticksExisted % 1 == 0 && this.prop_mode.getValue().get("Vanilla")) {
        	mc.thePlayer.setMoveSpeedAris(e, 3.9);
          }
    }
}
