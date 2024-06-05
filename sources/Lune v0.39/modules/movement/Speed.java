package me.superskidder.lune.modules.movement;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.modules.movement.speed.Hypixel;
import me.superskidder.lune.modules.movement.speed.Hypixel2;
import me.superskidder.lune.events.EventMove;
import me.superskidder.lune.events.EventPacketReceive;
import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.events.EventStep;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.math.MathUtil;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Speed extends Mod {
    public Mode mode = new Mode("Mode", SpeedMod.values(), SpeedMod.HypixelHop);
    public Bool<Boolean> sneak = new Bool<>("HopWhenSneak", false);
    public int stage;
    private final TimerUtil lastCheck = new TimerUtil();
    public double moveSpeed;
    private Hypixel hypixel = new Hypixel();
    private Hypixel2 hypixel2 = new Hypixel2();
    public static TargetStrafe targetstrafe = new TargetStrafe();

    enum SpeedMod {
        Hypixel,
        HypixelHop
    }

    public Speed() {
        super("Speed", ModCategory.Movement,"Bunny hop");
        this.addValues(this.mode, this.sneak);
    }

    private boolean isNotInLiquid() {
        if (mc.thePlayer == null) {
            return true;
        }
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                BlockPos pos = new BlockPos(x, (int) mc.thePlayer.getEntityBoundingBox().minY, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    return !(block instanceof BlockLiquid);
                }
            }
        }
        return true;
    }

    @EventTarget
    public void onStep(EventStep e) {
        double y = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY;
        this.moveSpeed = this.getHypixelBaseSpeed();
    }

    @EventTarget
    public void onPacket(EventPacketReceive e) {

        if (Lune.moduleManager.getModByClass(Fly.class).getState()) {
            return;
        }
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook s08PacketPlayerPosLook = (S08PacketPlayerPosLook) e.getPacket();
            if (this.lastCheck.isDelayComplete(300)) {
                s08PacketPlayerPosLook.yaw = mc.thePlayer.rotationYaw;
                s08PacketPlayerPosLook.pitch = mc.thePlayer.rotationPitch;
            }
            this.lastCheck.reset();
            this.stage = -6;
        }
    }

    @Override
    public void onDisable() {

        if (Lune.moduleManager.getModByClass(Fly.class).getState()) {
            return;
        }
    }

    @EventTarget
    private void onUpdate(EventPreUpdate e) {
        this.setDisplayName(this.mode.getModeAsString());
        if (Lune.moduleManager.getModByClass(Fly.class).getState()) {
            return;
        }
//        if(KillAura.target != null) {
//        	this.mode.setMode("Hypixel");
//        }else {
//        	this.mode.setMode("HypixelHop");
//        }
    }

    private boolean canZoom() {
        return PlayerUtil.isMoving2() && mc.thePlayer.onGround;
    }

    @EventTarget
    private void onMove(EventMove e) {


        if (Lune.moduleManager.getModByClass(Fly.class).getState() || !isNotInLiquid()) {
            return;
        }

        if (!Lune.moduleManager.getModByClass(Fly.class).getState()) {
            if(mode.getValue() == SpeedMod.HypixelHop) {
                hypixel2.onMove(e);
            }else if(mode.getValue() == SpeedMod.Hypixel){
                hypixel.onMove(e);
            }
            if (KillAura.target != null && Lune.moduleManager.getModByClass(TargetStrafe.class).getState()) {
                Speed.targetstrafe.strafe(e, moveSpeed);
            }
        }
    }

    @Override
    public void onEnabled() {
    	
    	
        if (Lune.moduleManager.getModByClass(Fly.class).getState()) {
            return;
        }
        this.stage = 0;
        mc.thePlayer.jumpMovementFactor = 0.1f;
        this.moveSpeed = MathUtil.getBaseMovementSpeed();

        hypixel2.onEnable();
    }

    private double getHypixelBaseSpeed() {
        return 0.2873D;
    }
}