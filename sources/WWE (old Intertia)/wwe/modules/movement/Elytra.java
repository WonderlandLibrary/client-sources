package wwe.modules.movement;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import wwe.*;

public class Elytra extends qMIe
{
    public double eXQk;
    public ModeValue mode;
    public NumberValue speed;
    public BooleanValue accelerate;
    public NumberValue accelerateStart;
    public NumberValue PZrO;
    public NumberValue accelerateMax;
    
    public Elytra() {
        super("Elytra +", "Makes your fly faster/better when elytra flying", 25, Category.MOVEMENT);
        this.eXQk = 0.0;
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Packet");
        list.add("Vanilla");
        list.add("Boost");
        list.add("2h2e");
        list.add("Control");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Normal", list);
        this.speed = new NumberValue("Speed", this, "Speed when elytra flying", 1.5, 0.0, 10.0);
        this.accelerate = new BooleanValue("Accelerate", this, "Goes faster over time depending on settings(Vanilla, and Packet)", false);
        this.accelerateStart = new NumberValue("Accelerate Speed", this, "How fast you accelerate", 0.1, 0.1, 1.0);
        this.accelerateStart = new NumberValue("Accelerate Start", this, "How fast you start before you accelerate", 0.5, 0.1, 2.0);
        this.accelerateMax = new NumberValue("Accelerate Max", this, "How fast you go when you stop accelerating", 2.5, 1.0, 3.5);
    }
    
    @oqrr
    public void ulkK(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.owgW("Elytra +" + TextFormatting.DARK_GRAY + " Normal");
            if (Wrapper.mc.player != null) {
                if (Wrapper.mc.player.isElytraFlying()) {
                    final Vec3d lookVec = Wrapper.mc.player.getLookVec();
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionX += lookVec.x * 0.1 + (lookVec.x * this.speed.OnGi() - Wrapper.mc.player.motionX) * 0.5;
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.motionY += lookVec.y * 0.1 + (lookVec.y * this.speed.OnGi() - Wrapper.mc.player.motionY) * 0.5;
                    final EntityPlayerSP player3 = Wrapper.mc.player;
                    player3.motionZ += lookVec.z * 0.1 + (lookVec.z * this.speed.OnGi() - Wrapper.mc.player.motionZ) * 0.5;
                }
                Wrapper.mc.player.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ);
                Wrapper.mc.player.motionX = Wrapper.mc.player.motionX;
                Wrapper.mc.player.motionY = Wrapper.mc.player.motionY;
                Wrapper.mc.player.motionZ = Wrapper.mc.player.motionZ;
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Boost")) {
            this.owgW("Elytra +" + TextFormatting.DARK_GRAY + " Boost");
            if (Wrapper.mc.player != null && Wrapper.mc.player.isElytraFlying() && Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                final Vec3d lookVec2 = Wrapper.mc.player.getLookVec();
                final EntityPlayerSP player4 = Wrapper.mc.player;
                player4.motionX += lookVec2.x * 0.1 + (lookVec2.x * this.speed.OnGi() - Wrapper.mc.player.motionX) * 0.5;
                final EntityPlayerSP player5 = Wrapper.mc.player;
                player5.motionY += lookVec2.y * 0.1 + (lookVec2.y * this.speed.OnGi() - Wrapper.mc.player.motionY) * 0.5;
                final EntityPlayerSP player6 = Wrapper.mc.player;
                player6.motionZ += lookVec2.z * 0.1 + (lookVec2.z * this.speed.OnGi() - Wrapper.mc.player.motionZ) * 0.5;
                Wrapper.mc.player.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY, Wrapper.mc.player.posZ);
                Wrapper.mc.player.motionX = Wrapper.mc.player.motionX;
                Wrapper.mc.player.motionY = Wrapper.mc.player.motionY;
                Wrapper.mc.player.motionZ = Wrapper.mc.player.motionZ;
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Packet")) {
            this.owgW("Elytra +" + TextFormatting.DARK_GRAY + " Packet");
            if (Wrapper.mc.player != null) {
                Wrapper.mc.player.capabilities.isFlying = true;
                if (this.accelerate.OnGi()) {
                    if (Wrapper.mc.player.movementInput.moveForward == 0.0f && Wrapper.mc.player.movementInput.moveStrafe == 0.0f) {
                        this.eXQk = this.PZrO.OnGi();
                    }
                    if (this.eXQk + this.accelerateStart.OnGi() > this.accelerateMax.OnGi()) {
                        this.eXQk = this.accelerateMax.OnGi();
                        if (Elytra.\u2008\u200c\u2006) {
                            throw null;
                        }
                    }
                    else {
                        this.eXQk += this.accelerateStart.OnGi();
                    }
                }
                final PlayerCapabilities capabilities = Wrapper.mc.player.capabilities;
                float flySpeed;
                if (this.accelerate.OnGi()) {
                    flySpeed = (float)this.eXQk / 10.0f;
                    if (Elytra.\u2008\u200c\u2006) {
                        throw null;
                    }
                }
                else {
                    flySpeed = this.speed.OnGi().floatValue() / 10.0f;
                }
                capabilities.setFlySpeed(flySpeed);
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Vanilla")) {
            this.owgW("Elytra +" + TextFormatting.DARK_GRAY + " Vanilla");
            if (Wrapper.mc.player != null) {
                Wrapper.mc.player.capabilities.isFlying = true;
                if (this.accelerate.OnGi()) {
                    if (Wrapper.mc.player.movementInput.moveForward == 0.0f && Wrapper.mc.player.movementInput.moveStrafe == 0.0f) {
                        this.eXQk = this.PZrO.OnGi();
                    }
                    if (this.eXQk + this.accelerateStart.OnGi() > this.accelerateMax.OnGi()) {
                        this.eXQk = this.accelerateMax.OnGi();
                        if (Elytra.\u2008\u200c\u2006) {
                            throw null;
                        }
                    }
                    else {
                        this.eXQk += this.accelerateStart.OnGi();
                    }
                }
                final PlayerCapabilities capabilities2 = Wrapper.mc.player.capabilities;
                float flySpeed2;
                if (this.accelerate.OnGi()) {
                    flySpeed2 = (float)this.eXQk / 10.0f;
                    if (Elytra.\u2008\u200c\u2006) {
                        throw null;
                    }
                }
                else {
                    flySpeed2 = this.speed.OnGi().floatValue() / 10.0f;
                }
                capabilities2.setFlySpeed(flySpeed2);
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("2h2e")) {
            this.owgW("Elytra +" + TextFormatting.DARK_GRAY + " 2h2e");
            if (Wrapper.mc.player != null) {
                final float n = Wrapper.mc.player.rotationYaw * 0.017453292f;
                Wrapper.mc.player.playSound(SoundEvents.ENTITY_FIREWORK_LAUNCH, 1.0f, 1.0f);
                final float n2 = Wrapper.mc.player.rotationYaw * 0.017453292f;
                final EntityPlayerSP player7 = Wrapper.mc.player;
                player7.motionX -= MathHelper.sin(n) * 0.15f;
                final EntityPlayerSP player8 = Wrapper.mc.player;
                player8.motionZ += MathHelper.cos(n) * 0.15f;
                final float n3 = Wrapper.mc.player.rotationYaw * 0.017453292f;
                final EntityPlayerSP player9 = Wrapper.mc.player;
                player9.motionX -= MathHelper.sin(n) * 0.15f;
                final EntityPlayerSP player10 = Wrapper.mc.player;
                player10.motionZ += MathHelper.cos(n) * 0.15f;
                final float n4 = Wrapper.mc.player.rotationYaw * 0.017453292f;
                final EntityPlayerSP player11 = Wrapper.mc.player;
                player11.motionX -= MathHelper.sin(n) * 0.15f;
                final EntityPlayerSP player12 = Wrapper.mc.player;
                player12.motionZ += MathHelper.cos(n) * 0.15f;
                final float n5 = Wrapper.mc.player.rotationYaw * 0.017453292f;
                final EntityPlayerSP player13 = Wrapper.mc.player;
                player13.motionX -= MathHelper.sin(n) * 0.15f;
                final EntityPlayerSP player14 = Wrapper.mc.player;
                player14.motionZ += MathHelper.cos(n) * 0.15f;
                final float n6 = Wrapper.mc.player.rotationYaw * 0.017453292f;
                final EntityPlayerSP player15 = Wrapper.mc.player;
                player15.motionX -= MathHelper.sin(n) * 0.15f;
                final EntityPlayerSP player16 = Wrapper.mc.player;
                player16.motionZ += MathHelper.cos(n) * 0.15f;
                final float n7 = Wrapper.mc.player.rotationYaw * 0.017453292f;
                final EntityPlayerSP player17 = Wrapper.mc.player;
                player17.motionX -= MathHelper.sin(n) * 0.15f;
                final EntityPlayerSP player18 = Wrapper.mc.player;
                player18.motionZ += MathHelper.cos(n) * 0.15f;
                final float n8 = Wrapper.mc.player.rotationYaw * 0.017453292f;
                final EntityPlayerSP player19 = Wrapper.mc.player;
                player19.motionX -= MathHelper.sin(n) * 0.15f;
                final EntityPlayerSP player20 = Wrapper.mc.player;
                player20.motionZ += MathHelper.cos(n) * 0.15f;
                this.GDVp();
            }
        }
    }
    
    @oqrr
    public void aHQC(final sFUx sfUx) {
        if (this.mode.OnGi().equalsIgnoreCase("Control")) {
            this.owgW("Elytra +" + TextFormatting.DARK_GRAY + " Control");
            if (Wrapper.mc.player.isElytraFlying()) {
                if (Wrapper.mc.player.movementInput.moveForward == 0.0f && Wrapper.mc.player.movementInput.moveStrafe == 0.0f) {
                    sfUx.XNxu = -0.10000000149011612;
                    sfUx.VsNw = 0.0;
                    sfUx.lcNE = 0.0;
                    if (Elytra.\u2008\u200c\u2006) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player != null) {
                    final MovementInput movementInput = Wrapper.mc.player.movementInput;
                    float moveForward = movementInput.moveForward;
                    float moveStrafe = movementInput.moveStrafe;
                    float rotationYaw = Wrapper.mc.player.rotationYaw;
                    if (moveForward == 0.0 && moveStrafe == 0.0) {
                        sfUx.VsNw = 0.0;
                        sfUx.lcNE = 0.0;
                        if (Elytra.\u2008\u200c\u2006) {
                            throw null;
                        }
                    }
                    else {
                        if (moveForward != 0.0) {
                            if (moveStrafe > 0.0) {
                                final float n = rotationYaw;
                                int n2;
                                if (moveForward > 0.0) {
                                    n2 = -45;
                                    if (Elytra.\u2008\u200c\u2006) {
                                        throw null;
                                    }
                                }
                                else {
                                    n2 = 45;
                                }
                                rotationYaw = n + n2;
                                if (Elytra.\u2008\u200c\u2006) {
                                    throw null;
                                }
                            }
                            else if (moveStrafe < 0.0) {
                                final float n3 = rotationYaw;
                                int n4;
                                if (moveForward > 0.0) {
                                    n4 = 45;
                                    if (Elytra.\u2008\u200c\u2006) {
                                        throw null;
                                    }
                                }
                                else {
                                    n4 = -45;
                                }
                                rotationYaw = n3 + n4;
                            }
                            moveStrafe = 0.0f;
                            if (moveForward > 0.0) {
                                moveForward = 1.0f;
                                if (Elytra.\u2008\u200c\u2006) {
                                    throw null;
                                }
                            }
                            else if (moveForward < 0.0) {
                                moveForward = -1.0f;
                            }
                        }
                        if (moveStrafe > 0.0) {
                            moveStrafe = 1.0f;
                            if (Elytra.\u2008\u200c\u2006) {
                                throw null;
                            }
                        }
                        else if (moveStrafe < 0.0) {
                            moveStrafe = -1.0f;
                        }
                        sfUx.XNxu = 0.0;
                        sfUx.VsNw = moveForward * this.speed.OnGi() * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * this.speed.OnGi() * Math.sin(Math.toRadians(rotationYaw + 90.0f));
                        sfUx.lcNE = moveForward * this.speed.OnGi() * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * this.speed.OnGi() * Math.cos(Math.toRadians(rotationYaw + 90.0f));
                    }
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        Wrapper.mc.player.capabilities.isFlying = false;
        super.qBmy();
    }
}
