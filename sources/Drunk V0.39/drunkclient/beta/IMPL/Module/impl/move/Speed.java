/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventMove;
import drunkclient.beta.API.events.world.EventPacketReceive;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.combat.Killaura;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.world.MovementUtil;
import drunkclient.beta.UTILS.world.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class Speed
extends Module {
    double motion = 0.0;
    private double stage3;
    private double moveSpeed;
    private double lastDist;
    public boolean reset;
    public boolean doSlow;
    public boolean spoofGround;
    public int stage;
    public int ticks;
    double mineplex;
    public double movementSpeed;
    private int counter;
    public double motionXZ;
    private EntityLivingBase target;
    Timer timer = new Timer();
    double dist;
    boolean prevOnGround = false;
    private int verusStage;
    private double ViperMC = 0.0;
    public drunkclient.beta.IMPL.set.Mode<Enum> modes = new drunkclient.beta.IMPL.set.Mode("Mode", "Mode", (Enum[])Mode.values(), (Enum)Mode.KokscraftRage);
    public drunkclient.beta.IMPL.set.Mode<Enum> verusModes = new drunkclient.beta.IMPL.set.Mode("Verus Mode", "Verus Mode", (Enum[])VerusMode.values(), (Enum)VerusMode.Hop);
    public Numbers<Double> speed = new Numbers<Double>("Speed", "Speed", 0.3, 0.1, 3.0, 0.1);
    public final Option<Boolean> ignorewater = new Option<Boolean>("KoksIgnoreWater", "KoksIgnoreWater", false);
    private float rotationYaw;
    private int spoofSlot;

    public Speed() {
        super("Speed", new String[0], Type.MOVE, "Allows to move faster");
        this.addValues(this.modes, this.ignorewater, this.verusModes, this.speed);
    }

    @Override
    public void onEnable() {
        this.lastDist = 0.0;
        this.spoofSlot = Minecraft.thePlayer.inventory.currentItem;
        this.verusStage = 0;
        this.mineplex = 0.0;
        this.dist = 0.0;
    }

    @EventHandler
    public void packetReceive(EventPacketReceive e) {
    }

    @EventHandler
    public void onEventPre(EventPreUpdate e) {
        this.setSuffix(this.modes.getModeAsString());
        if (this.modes.getModeAsString().equalsIgnoreCase("NCPlw")) {
            Minecraft.thePlayer.setSprinting(false);
            if (MovementUtil.isMoving()) {
                Speed.mc.timer.timerSpeed = 1.05f;
                if (Minecraft.thePlayer.onGround) {
                    Minecraft.thePlayer.jump();
                    Minecraft.thePlayer.motionY = 0.4f;
                    if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        MovementUtil.setMotion(0.39);
                    } else {
                        switch (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()) {
                            case 0: {
                                MovementUtil.setMotion(0.49);
                                break;
                            }
                            case 1: {
                                MovementUtil.setMotion(0.575);
                                break;
                            }
                            case 2: {
                                MovementUtil.setMotion(0.73);
                                break;
                            }
                        }
                    }
                } else {
                    Speed.mc.timer.timerSpeed = 1.05f;
                    MovementUtil.setMotion(MovementUtil.getSpeed() * 1.0);
                    Minecraft.thePlayer.motionY = -0.4f;
                }
            } else {
                MovementUtil.setMotion(0.0);
            }
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("Hypixel") && MovementUtil.isMoving()) {
            if (MovementUtil.isOnGround()) {
                this.stage = 0;
            }
            switch (this.stage) {
                case 0: {
                    Minecraft.thePlayer.jump();
                    Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.3992E-200 * Math.random(), Minecraft.thePlayer.posZ);
                    this.moveSpeed = MovementUtil.getBaseMoveSpeed() * 2.139992;
                    break;
                }
                case 1: {
                    this.moveSpeed *= 0.57;
                    break;
                }
                case 4: {
                    Minecraft.thePlayer.motionY -= 0.0789992;
                    break;
                }
                default: {
                    this.moveSpeed *= 0.96572;
                }
            }
            ++this.stage;
            MovementUtil.setMotion(this.moveSpeed);
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("Vanilla")) {
            MovementUtil.setMotion((Double)this.speed.getValue());
            if (Minecraft.thePlayer.onGround && MovementUtil.isMoving()) {
                Minecraft.thePlayer.jump();
            }
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("Verus") && this.verusModes.getModeAsString().equalsIgnoreCase("Hop")) {
            if (Minecraft.thePlayer.onGround && MovementUtil.isMoving()) {
                Minecraft.thePlayer.jump();
            } else if (!MovementUtil.isMoving()) {
                MovementUtil.setMotion(0.0);
            }
            MovementUtil.setMotion(0.32);
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("Matrix")) {
            if (Minecraft.thePlayer.onGround) {
                Minecraft.thePlayer.motionX *= (double)1.215f;
                Minecraft.thePlayer.motionZ *= (double)1.215f;
            }
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("KokscraftRage")) {
            this.target = Killaura.target;
            if (!Minecraft.thePlayer.isOnLadder()) {
                Minecraft mc2 = mc;
                if (!Minecraft.thePlayer.isEating()) {
                    Minecraft mc3 = mc;
                    if (Minecraft.thePlayer.isInWater() && !((Boolean)this.ignorewater.getValue()).booleanValue()) {
                        return;
                    }
                    Minecraft mc4 = mc;
                    if (!Minecraft.thePlayer.isInLava()) {
                        if (Minecraft.thePlayer.onGround) {
                            Minecraft mc6 = mc;
                            ++this.counter;
                            Minecraft.thePlayer.jump();
                            System.out.println("add");
                        } else if (this.counter < 7) {
                            if (this.counter == 4) {
                                if (this.target != null) {
                                    MovementUtil.setSpeed(e, 0.3101);
                                }
                                MovementUtil.setSpeed(e, 0.3301);
                                System.out.println("BOOOM");
                            } else {
                                Speed.mc.timer.timerSpeed = 1.0f;
                            }
                            ++this.counter;
                        } else {
                            this.counter = 0;
                        }
                    }
                }
            }
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("KokscraftSkyWars")) {
            if (Minecraft.thePlayer.isInWater() && !((Boolean)this.ignorewater.getValue()).booleanValue()) {
                return;
            }
            if (MovementUtil.isOnGround(1.0E-8) && MovementUtil.isMoving()) {
                Minecraft.thePlayer.jump();
            }
            if (MovementUtil.isOnGround(1.0E-8)) {
                Speed.mc.timer.timerSpeed = 0.16f;
                Minecraft.thePlayer.setSprinting(false);
            }
            if (!MovementUtil.isOnGround(1.0E-8)) {
                MovementUtil.setMotion(0.35);
                Speed.mc.timer.timerSpeed = 1.0f;
            }
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("KokscraftSemi")) {
            if (Minecraft.thePlayer.isInWater() && !((Boolean)this.ignorewater.getValue()).booleanValue()) {
                return;
            }
            if (MovementUtil.isOnGround(1.0E-8) && MovementUtil.isMoving()) {
                Minecraft.thePlayer.jump();
                Speed.mc.timer.timerSpeed = 1.0f;
            }
            if (!MovementUtil.isOnGround(1.0E-8) && MovementUtil.isMoving()) {
                Speed.mc.timer.timerSpeed = Killaura.target != null ? 1.0f : 1.065f;
            }
            if (MovementUtil.isMoving()) {
                if (Minecraft.thePlayer.onGround) {
                    // empty if block
                }
            }
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("KokscraftLegit")) {
            if (Minecraft.thePlayer.isInWater() && !((Boolean)this.ignorewater.getValue()).booleanValue()) {
                return;
            }
            if (MovementUtil.isOnGround(1.0E-8) && MovementUtil.isMoving()) {
                Minecraft.thePlayer.jump();
                Speed.mc.timer.timerSpeed = 1.0f;
            }
            if (!MovementUtil.isOnGround(1.0E-8) && MovementUtil.isMoving()) {
                Speed.mc.timer.timerSpeed = Killaura.target != null ? 1.0f : 1.035f;
            }
            if (MovementUtil.isMoving()) {
                if (Minecraft.thePlayer.onGround) {
                    // empty if block
                }
            }
        }
        if (this.modes.getModeAsString().equalsIgnoreCase("DEV")) {
            Minecraft.thePlayer.motionY = -0.23f;
            Speed.mc.timer.timerSpeed = 0.85f;
            if (Minecraft.thePlayer.isInWater() && !((Boolean)this.ignorewater.getValue()).booleanValue()) {
                return;
            }
            if (MovementUtil.isOnGround(1.0E-8) && MovementUtil.isMoving()) {
                Minecraft.thePlayer.jump();
                Minecraft.thePlayer.motionY = 0.1f;
            }
            if (MovementUtil.isOnGround(1.0E-8) || !MovementUtil.isMoving() || Killaura.target != null) {
                // empty if block
            }
            if (MovementUtil.isMoving()) {
                if (Minecraft.thePlayer.onGround) {
                    // empty if block
                }
            }
        }
        if (!this.modes.getModeAsString().equalsIgnoreCase("NCP")) return;
        if (MovementUtil.isMoving()) {
            if (!Minecraft.thePlayer.isCollidedHorizontally) {
                if (Minecraft.thePlayer.onGround) {
                    Minecraft.thePlayer.jump();
                    Minecraft.thePlayer.motionY = 0.42f;
                    Speed.mc.timer.timerSpeed = 1.05f;
                    if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        MovementUtil.setMotion(0.48 + (double)this.ticks);
                    } else {
                        if (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0) {
                            MovementUtil.setMotion(0.525 + (double)this.ticks);
                        }
                        if (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1) {
                            MovementUtil.setMotion(0.68 + (double)this.ticks);
                        }
                        if (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 2) {
                            MovementUtil.setMotion(0.73 + (double)this.ticks);
                        }
                    }
                    this.prevOnGround = true;
                    return;
                }
                if (this.prevOnGround) {
                    this.prevOnGround = false;
                    if ((double)this.ticks < 0.035) {
                        this.ticks = (int)((float)this.ticks + 0.04f);
                    }
                }
                if ((double)this.ticks > 0.035) {
                    this.ticks = (int)((float)this.ticks - 0.001f);
                }
                Minecraft.thePlayer.jumpMovementFactor = 0.0265f;
                Speed.mc.timer.timerSpeed = 1.05f;
                double direction = MovementUtil.getDirection();
                double speed = 1.0;
                if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    if (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0) {
                        speed = 1.0 + (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.015 : 0.0);
                    }
                    if (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1) {
                        speed = 1.0 + (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.025 : 0.0);
                    }
                    if (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 2) {
                        speed = 1.0 + (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.03 : 0.0);
                    }
                }
                double currentMotion = Math.sqrt(Minecraft.thePlayer.motionX * Minecraft.thePlayer.motionX + Minecraft.thePlayer.motionZ * Minecraft.thePlayer.motionZ);
                MovementUtil.setMotion(currentMotion * speed);
                return;
            }
        }
        this.ticks = 0;
        MovementUtil.setMotion(0.0);
    }

    /*
     * Unable to fully structure code
     */
    @EventHandler
    public void movementUpdate(EventMove e) {
        var2_2 = this.modes.getModeAsString();
        var3_3 = -1;
        switch (var2_2.hashCode()) {
            case 74110982: {
                if (!var2_2.equals("NCPlw")) break;
                var3_3 = 0;
                break;
            }
            case 82544993: {
                if (!var2_2.equals("Verus")) break;
                var3_3 = 1;
                break;
            }
            case -1298025822: {
                if (!var2_2.equals("Mineplex")) break;
                var3_3 = 2;
                break;
            }
        }
        switch (var3_3) {
            case 0: {
                Minecraft.thePlayer.setSprinting(false);
                if (MovementUtil.isMoving() == false) return;
                Speed.mc.timer.timerSpeed = 1.05f;
                if (!Minecraft.thePlayer.onGround) {
                    Speed.mc.timer.timerSpeed = 1.05f;
                    MovementUtil.setMotion(MovementUtil.getSpeed() * 1.0);
                    Minecraft.thePlayer.motionY = -0.4000000059604645;
                    return;
                }
            }
            Minecraft.thePlayer.jump();
            Minecraft.thePlayer.motionY = 0.4000000059604645;
            if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
                MovementUtil.setMotion(0.39);
                return;
            }
            switch (Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier()) {
                case 0: {
                    MovementUtil.setMotion(0.49);
                    return;
                }
                case 1: {
                    MovementUtil.setMotion(0.575);
                    return;
                }
                case 2: {
                    MovementUtil.setMotion(0.73);
                    return;
                }
            }
            return;
            case 1: {
                if (this.verusModes.getModeAsString().equalsIgnoreCase("Port") == false) return;
                if (Minecraft.thePlayer.isInLava() != false) return;
                if (Minecraft.thePlayer.isInWater() != false) return;
                if (Minecraft.thePlayer.isOnLadder() != false) return;
                if (Minecraft.thePlayer.ridingEntity != null) return;
                if (MovementUtil.isMoving() == false) return;
                if (!Minecraft.thePlayer.onGround) ** GOTO lbl71
                if (Minecraft.thePlayer.isCollidedHorizontally) ** GOTO lbl71
                Minecraft.thePlayer.jump();
                Minecraft.thePlayer.motionY = 0.0;
                MovementUtil.doStrafe(0.6499999761581421);
                EventMove.setY(0.41999998688698);
                ** GOTO lbl77
            }
lbl71:
            // 2 sources

            if (!Minecraft.thePlayer.isCollidedHorizontally) ** GOTO lbl-1000
            if (Minecraft.thePlayer.onGround) {
                Speed.mc.gameSettings.keyBindJump.pressed = true;
            } else lbl-1000:
            // 2 sources

            {
                Speed.mc.gameSettings.keyBindJump.pressed = false;
            }
lbl77:
            // 3 sources

            MovementUtil.doStrafe();
            return;
            case 2: {
                player = Minecraft.thePlayer;
                pos = new BlockPos(player.posX, player.posY - 1.0, player.posZ);
                block = Speed.mc.theWorld.getBlockState(pos).getBlock();
                Speed.mc.timer.timerSpeed = 1.0f;
                if (MovementUtil.isMoving() && MovementUtil.isOnGround()) {
                    Minecraft.thePlayer.motionY = 0.359;
                    EventMove.setY(0.359);
                    this.doSlow = true;
                    this.dist = this.mineplex;
                    this.mineplex = 0.0;
                } else {
                    Speed.mc.timer.timerSpeed = 1.0f;
                    if (this.doSlow) {
                        this.mineplex = this.dist + 0.5600000023841858;
                        this.doSlow = false;
                    } else {
                        this.mineplex = this.lastDist * (this.mineplex > 2.2 ? 0.975 : (this.moveSpeed >= 1.5 ? 0.98 : 0.985));
                    }
                    EventMove.setY(e.getY() - 1.0E-4);
                }
                max = 5.0;
                MovementUtil.doStrafe(Math.max(Math.min(this.mineplex, 5.0), this.doSlow != false ? 0.0 : 0.455));
                return;
            }
        }
    }

    private boolean invCheck() {
        int i = 36;
        while (i < 45) {
            if (!Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                return false;
            }
            ++i;
        }
        return true;
    }

    @Override
    public void onDisable() {
        Speed.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    public static enum VerusMode {
        Port,
        Hop;

    }

    public static enum Mode {
        KokscraftRage,
        KokscraftSemi,
        KokscraftLegit,
        NCP,
        Verus,
        Dev;

    }
}

