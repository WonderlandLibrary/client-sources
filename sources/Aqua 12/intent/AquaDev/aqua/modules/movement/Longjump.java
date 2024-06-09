// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import events.listeners.EventClick;
import events.listeners.EventTimerDisabler;
import events.listeners.EventPreMotion;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import events.listeners.EventUpdate;
import events.listeners.EventTick;
import events.Event;
import intent.AquaDev.aqua.utils.PlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.Test;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class Longjump extends Module
{
    public static double Packets;
    public static boolean jump;
    public static boolean startUP;
    public double posY;
    public int stage;
    public int jumps;
    public boolean dmg;
    public int groundTicks;
    TimeUtil timer;
    TimeUtil timeUtil;
    public boolean hittet;
    Test test;
    
    public Longjump() {
        super("Longjump", Type.Movement, "Longjump", 0, Category.Movement);
        this.timer = new TimeUtil();
        this.timeUtil = new TimeUtil();
        this.hittet = false;
        this.test = new Test();
        Aqua.setmgr.register(new Setting("NCPBoost", this, 3.0, 0.3, 9.0, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Watchdog", new String[] { "Watchdog", "WatchdogBow", "Gamster", "OldNCP", "Hycraft", "Cubecraft" }));
    }
    
    @Override
    public void onEnable() {
        this.dmg = false;
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("OldNCP")) {
            Longjump.mc.thePlayer.jump();
        }
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Hycraft")) {
            Longjump.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Longjump.mc.thePlayer.posX, Longjump.mc.thePlayer.posY + 3.001, Longjump.mc.thePlayer.posZ, false));
            Longjump.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Longjump.mc.thePlayer.posX, Longjump.mc.thePlayer.posY, Longjump.mc.thePlayer.posZ, false));
            Longjump.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Longjump.mc.thePlayer.posX, Longjump.mc.thePlayer.posY, Longjump.mc.thePlayer.posZ, true));
            Longjump.mc.gameSettings.keyBindForward.pressed = false;
        }
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Cubecraft")) {
            Longjump.mc.thePlayer.motionY = 1.5;
        }
        this.hittet = false;
        PlayerUtil.setSpeed(0.0);
        Longjump.jump = false;
        Longjump.startUP = true;
        Longjump.mc.gameSettings.keyBindUseItem.pressed = Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("WatchdogBow");
        Aqua.moduleManager.getModuleByName("Killaura").setState(false);
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Gamster")) {
            if (Longjump.mc.thePlayer.onGround) {
                Longjump.mc.thePlayer.jump();
            }
            Longjump.mc.timer.timerSpeed = 0.09f;
        }
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
            PlayerUtil.setSpeed(0.0);
            Longjump.mc.gameSettings.keyBindForward.pressed = false;
            Longjump.mc.thePlayer.motionX = 0.0;
            Longjump.mc.thePlayer.motionZ = 0.0;
            this.dmg = false;
            this.posY = Longjump.mc.thePlayer.posY;
            Longjump.jump = false;
            this.stage = 0;
            this.jumps = 0;
            this.groundTicks = 0;
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.dmg = false;
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("OldNCP")) {
            PlayerUtil.setSpeed(0.0);
        }
        this.hittet = false;
        this.timeUtil.reset();
        Longjump.jump = false;
        Longjump.mc.thePlayer.jumpMovementFactor = 0.0f;
        Longjump.mc.thePlayer.capabilities.isFlying = false;
        Longjump.mc.timer.timerSpeed = 1.0f;
        Longjump.mc.thePlayer.capabilities.allowFlying = false;
        Longjump.startUP = false;
        Aqua.moduleManager.getModuleByName("Killaura").setState(true);
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Gamster")) {}
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
            Longjump.mc.gameSettings.keyBindJump.pressed = false;
            this.dmg = false;
            this.jumps = 0;
            this.stage = 0;
            this.groundTicks = 0;
            this.timer.reset();
            Longjump.jump = false;
        }
        Longjump.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        Longjump.mc.thePlayer.cameraYaw = 0.045f;
        if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("WatchdogBow") && Longjump.mc.thePlayer.ticksExisted % 7 == 0) {
            Longjump.mc.gameSettings.keyBindUseItem.pressed = false;
        }
        if (e instanceof EventTick && Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Gamster")) {
            if (Longjump.mc.thePlayer.ticksExisted % 5 == 0) {
                Longjump.mc.gameSettings.keyBindUseItem.pressed = false;
                Longjump.mc.timer.timerSpeed = 1.0f;
            }
            else {
                Longjump.mc.timer.timerSpeed = 0.1f;
                Longjump.mc.gameSettings.keyBindUseItem.pressed = true;
            }
        }
        if (e instanceof EventUpdate) {
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Cubecraft")) {
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Longjump.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                final double speed1 = (float)Aqua.setmgr.getSetting("LongjumpNCPBoost").getCurrentNumber();
                PlayerUtil.setSpeed(speed1);
            }
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Hycraft")) {
                if (Longjump.mc.thePlayer.hurtTime != 0) {
                    PlayerUtil.setSpeed(0.3);
                    Longjump.mc.timer.timerSpeed = 1.0f;
                    Longjump.mc.thePlayer.motionY = 0.41999998688697815;
                    this.dmg = true;
                }
                else {
                    Longjump.mc.timer.timerSpeed = 1.0f;
                }
                if (this.dmg) {
                    Longjump.mc.gameSettings.keyBindForward.pressed = true;
                    final EntityPlayerSP thePlayer = Longjump.mc.thePlayer;
                    thePlayer.motionY += 0.029999999329447746;
                }
            }
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("OldNCP")) {
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Longjump.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                Longjump.mc.timer.timerSpeed = 0.8f;
                final double speed1 = (float)Aqua.setmgr.getSetting("LongjumpNCPBoost").getCurrentNumber();
                PlayerUtil.setSpeed(speed1);
                if (Longjump.mc.thePlayer.fallDistance > 0.0f) {
                    final EntityPlayerSP thePlayer2 = Longjump.mc.thePlayer;
                    thePlayer2.motionY += 0.03999999910593033;
                }
            }
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("WatchdogBow")) {
                if (Longjump.mc.thePlayer.hurtTime != 0) {
                    Longjump.jump = true;
                }
                if (!Longjump.jump) {
                    Longjump.mc.gameSettings.keyBindForward.pressed = false;
                }
                if (Longjump.jump) {
                    if (!Longjump.mc.thePlayer.onGround) {}
                    Longjump.mc.gameSettings.keyBindForward.pressed = true;
                }
                if (Longjump.mc.thePlayer.hurtTime == 0) {
                    Longjump.mc.timer.timerSpeed = 0.1f;
                }
                if (Longjump.mc.thePlayer.hurtTime != 0) {
                    if (Longjump.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                        PlayerUtil.setSpeed(0.66);
                    }
                    else {
                        PlayerUtil.setSpeed(0.52);
                    }
                }
                Longjump.mc.thePlayer.jumpMovementFactor = 0.025f;
                if ((Longjump.mc.gameSettings.keyBindForward.pressed || Longjump.mc.gameSettings.keyBindLeft.pressed || Longjump.mc.gameSettings.keyBindRight.pressed || Longjump.mc.gameSettings.keyBindBack.pressed) && Longjump.mc.thePlayer.onGround) {
                    Longjump.mc.thePlayer.motionY = 0.41999998688697815;
                    Longjump.mc.timer.timerSpeed = 1.0f;
                    Longjump.mc.thePlayer.setSprinting(true);
                }
                else {
                    Longjump.mc.thePlayer.setSprinting(true);
                    Longjump.mc.timer.timerSpeed = 1.0f;
                    if (Longjump.mc.thePlayer.hurtTime != 0) {
                        final EntityPlayerSP thePlayer3 = Longjump.mc.thePlayer;
                        thePlayer3.motionY += 0.02800000086426735;
                        if (Longjump.mc.thePlayer.fallDistance > 0.1 && Longjump.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                            Longjump.mc.thePlayer.motionY = 0.41999998688697815;
                        }
                        if (Longjump.mc.thePlayer.fallDistance > 0.0f) {
                            final EntityPlayerSP thePlayer4 = Longjump.mc.thePlayer;
                            thePlayer4.motionY += 0.018;
                        }
                    }
                }
            }
            if (!Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Gamster") || Longjump.mc.thePlayer.hurtTime != 0) {}
        }
        if (e instanceof EventPreMotion) {
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Gamster")) {
                if (Longjump.mc.thePlayer.hurtTime != 0) {
                    this.hittet = true;
                    ((EventPreMotion)e).setPitch(-40.0f);
                }
                else {
                    if (Longjump.mc.thePlayer.onGround) {
                        Longjump.mc.thePlayer.jump();
                    }
                    if (!this.hittet) {
                        ((EventPreMotion)e).setPitch(-85.0f);
                    }
                    else {
                        ((EventPreMotion)e).setPitch(-30.0f);
                    }
                }
            }
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("WatchdogBow")) {
                ((EventPreMotion)e).setPitch(-90.0f);
            }
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
                if (this.jumps < 3) {
                    Longjump.mc.thePlayer.posY = this.posY;
                }
                else {
                    Longjump.mc.timer.timerSpeed = 1.0f;
                }
            }
        }
        if (e instanceof EventTimerDisabler && Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
            final Packet packet = EventTimerDisabler.getPacket();
            if (packet instanceof C03PacketPlayer) {
                final C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
                if (this.jumps < 3) {
                    packetPlayer.onGround = false;
                }
            }
        }
        if (e instanceof EventClick) {
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
                if (Longjump.mc.thePlayer.onGround && this.jumps < 3) {
                    Longjump.mc.thePlayer.jump();
                    ++this.jumps;
                    Longjump.mc.gameSettings.keyBindForward.pressed = false;
                }
                if (Longjump.mc.thePlayer.hurtTime != 0) {
                    Longjump.jump = true;
                    if (Longjump.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                        PlayerUtil.setSpeed(0.525);
                    }
                    else {
                        PlayerUtil.setSpeed(0.4);
                    }
                    Longjump.mc.timer.timerSpeed = 1.0f;
                }
                else {
                    Longjump.mc.gameSettings.keyBindForward.pressed = false;
                }
                if (Longjump.jump) {
                    Longjump.mc.gameSettings.keyBindForward.pressed = true;
                    Longjump.mc.gameSettings.keyBindJump.pressed = true;
                    if (!Longjump.mc.thePlayer.onGround) {
                        final EntityPlayerSP thePlayer5 = Longjump.mc.thePlayer;
                        thePlayer5.motionY += 0.025;
                    }
                }
            }
            if (Aqua.setmgr.getSetting("LongjumpMode").getCurrentMode().equalsIgnoreCase("Gamster")) {}
        }
    }
}
