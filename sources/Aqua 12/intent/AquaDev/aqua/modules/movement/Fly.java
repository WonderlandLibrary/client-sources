// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import net.minecraft.util.MathHelper;
import java.util.Random;
import intent.AquaDev.aqua.utils.PlayerUtil;
import events.listeners.EventUpdate;
import events.listeners.EventTick;
import events.listeners.EventPacket;
import events.listeners.EventTimerDisabler;
import events.listeners.EventPlayerMove;
import events.Event;
import intent.AquaDev.aqua.modules.misc.Disabler;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Fly extends Module
{
    public boolean verusDmg;
    
    public Fly() {
        super("Fly", Type.Movement, "Fly", 0, Category.Movement);
        Aqua.setmgr.register(new Setting("MotionReset", this, true));
        Aqua.setmgr.register(new Setting("FakeDMG", this, false));
        Aqua.setmgr.register(new Setting("Boost", this, 3.0, 0.3, 9.0, false));
        Aqua.setmgr.register(new Setting("SentinelSpeed", this, 3.0, 0.3, 9.0, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Motion", new String[] { "Motion", "Hypixel", "Minemora", "Verus", "Verus2", "Verus3", "Cubecraft", "CubecraftNew", "CubecraftSmooth", "Creative", "Watchdog", "Test" }));
    }
    
    @Override
    public void onEnable() {
        if (Fly.mc.thePlayer != null && Aqua.setmgr.getSetting("FlyFakeDMG").isState()) {
            Fly.mc.thePlayer.performHurtAnimation();
            Fly.mc.thePlayer.playSound("game.player.hurt", 1.0f, 1.0f);
        }
        if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Test")) {}
        if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Verus3")) {
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
        }
        if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Verus")) {
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
            Fly.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY + 3.001, Fly.mc.thePlayer.posZ, false));
            Fly.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY, Fly.mc.thePlayer.posZ, false));
            Fly.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY, Fly.mc.thePlayer.posZ, true));
        }
        if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Verus2")) {
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
            sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
            Fly.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY + 3.001, Fly.mc.thePlayer.posZ, false));
            Fly.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY, Fly.mc.thePlayer.posZ, false));
            Fly.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX, Fly.mc.thePlayer.posY, Fly.mc.thePlayer.posZ, true));
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Disabler.sendPacketUnlogged(new C0BPacketEntityAction(Fly.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
        this.verusDmg = false;
        Fly.mc.gameSettings.keyBindSneak.pressed = false;
        Fly.mc.thePlayer.capabilities.isFlying = false;
        Fly.mc.thePlayer.capabilities.isCreativeMode = false;
        Fly.mc.timer.timerSpeed = 1.0f;
        if (Aqua.setmgr.getSetting("FlyMotionReset").isState()) {
            Fly.mc.thePlayer.motionZ = 0.0;
            Fly.mc.thePlayer.motionX = 0.0;
        }
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPlayerMove) {
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("CubecraftSmooth")) {
                if (Fly.mc.gameSettings.keyBindJump.pressed) {
                    ((EventPlayerMove)event).setY(1.0);
                }
                if (Fly.mc.gameSettings.keyBindSneak.pressed) {
                    ((EventPlayerMove)event).setY(-1.0);
                }
                if (Fly.mc.thePlayer.ticksExisted % 1 == 0) {
                    Fly.mc.gameSettings.keyBindSprint.pressed = true;
                    final double x = Fly.mc.thePlayer.posX;
                    final double y = Fly.mc.thePlayer.posY;
                    final double z = Fly.mc.thePlayer.posZ;
                    if (Fly.mc.thePlayer.isMoving()) {
                        final double yaw1 = Math.toRadians(Fly.mc.thePlayer.rotationYaw);
                        final double speed1 = (float)Aqua.setmgr.getSetting("FlySentinelSpeed").getCurrentNumber();
                        if (Fly.mc.thePlayer.ticksExisted % 1 == 0 && !Fly.mc.gameSettings.keyBindJump.pressed) {
                            ((EventPlayerMove)event).setY(Fly.mc.thePlayer.motionY = 0.0);
                        }
                    }
                    else {
                        ((EventPlayerMove)event).setX(0.0);
                        ((EventPlayerMove)event).setZ(0.0);
                        if (!Fly.mc.gameSettings.keyBindJump.pressed) {
                            ((EventPlayerMove)event).setY(Fly.mc.thePlayer.motionY = 0.0);
                        }
                    }
                }
                else {
                    ((EventPlayerMove)event).setX(0.0);
                    ((EventPlayerMove)event).setZ(0.0);
                }
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("CubecraftNew")) {
                if (Fly.mc.thePlayer.ticksExisted % 3 == 0) {
                    Fly.mc.gameSettings.keyBindSprint.pressed = true;
                    final double x = Fly.mc.thePlayer.posX;
                    final double y = Fly.mc.thePlayer.posY;
                    final double z = Fly.mc.thePlayer.posZ;
                    if (Fly.mc.gameSettings.keyBindJump.pressed) {
                        ((EventPlayerMove)event).setY(2.0);
                    }
                    if (Fly.mc.gameSettings.keyBindSneak.pressed) {
                        ((EventPlayerMove)event).setY(-2.0);
                    }
                    if (Fly.mc.thePlayer.isMoving()) {
                        final double yaw1 = Math.toRadians(Fly.mc.thePlayer.rotationYaw);
                        final double speed1 = (float)Aqua.setmgr.getSetting("FlySentinelSpeed").getCurrentNumber();
                        if (Fly.mc.thePlayer.ticksExisted % 2 == 0) {
                            ((EventPlayerMove)event).setY(Fly.mc.thePlayer.motionY = 0.2);
                        }
                    }
                    else {
                        ((EventPlayerMove)event).setX(0.0);
                        ((EventPlayerMove)event).setZ(0.0);
                        if (!Fly.mc.gameSettings.keyBindJump.pressed) {
                            ((EventPlayerMove)event).setY(Fly.mc.thePlayer.motionY = 0.08);
                        }
                    }
                }
                else {
                    ((EventPlayerMove)event).setX(0.0);
                    ((EventPlayerMove)event).setZ(0.0);
                }
            }
        }
        if (!(event instanceof EventTimerDisabler) || !Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Hypixel") || Fly.mc.thePlayer.ticksExisted % 2 == 0) {}
        if (!(event instanceof EventPacket) || !Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Zonecraft") || Fly.mc.thePlayer.ticksExisted % 2 == 0) {}
        if (!(event instanceof EventTick) || Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Test")) {}
        if (event instanceof EventUpdate) {
            Fly.mc.thePlayer.cameraYaw = 0.035f;
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Test")) {
                Disabler.sendPacketUnlogged(new C0BPacketEntityAction(Fly.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
                Fly.mc.thePlayer.onGround = true;
                if (Fly.mc.thePlayer.isMoving()) {
                    PlayerUtil.setSpeed(0.05);
                }
                Fly.mc.thePlayer.motionY = 0.0;
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Hypixel")) {
                Fly.mc.thePlayer.motionY = 0.0;
                if (Fly.mc.thePlayer.ticksExisted % 22 == 0) {
                    Fly.mc.gameSettings.keyBindForward.pressed = true;
                    final float yawRandom = (float)MathHelper.getRandomDoubleInRange(new Random(), 1.0, 180.0);
                    final double yaw2 = Math.toRadians(Fly.mc.thePlayer.rotationYaw);
                    final double x2 = -Math.sin(yaw2) * 7.0;
                    final double z2 = Math.cos(yaw2) * 7.0;
                    final double y2 = 1.8;
                    Fly.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Fly.mc.thePlayer.posX + x2, Fly.mc.thePlayer.posY - y2, Fly.mc.thePlayer.posZ + z2, false));
                }
                else {
                    Fly.mc.timer.timerSpeed = 1.0f;
                    Fly.mc.gameSettings.keyBindForward.pressed = false;
                }
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Watchdog") && Fly.mc.thePlayer.fallDistance > 3.0f) {
                Fly.mc.thePlayer.motionY = 0.0;
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Creative")) {
                Fly.mc.thePlayer.capabilities.isFlying = true;
                Fly.mc.thePlayer.capabilities.isCreativeMode = true;
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Motion")) {
                if (Fly.mc.gameSettings.keyBindJump.pressed) {
                    Fly.mc.thePlayer.motionY = 1.0;
                }
                else if (Fly.mc.gameSettings.keyBindSneak.pressed) {
                    Fly.mc.thePlayer.motionY = -1.0;
                }
                else {
                    Fly.mc.thePlayer.motionY = 0.0;
                }
                if (Fly.mc.thePlayer.isMoving()) {
                    PlayerUtil.setSpeed(4.0);
                }
                else {
                    Fly.mc.thePlayer.motionX = 0.0;
                    Fly.mc.thePlayer.motionZ = 0.0;
                }
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Verus3")) {
                PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                Fly.mc.thePlayer.onGround = true;
                if (Fly.mc.gameSettings.keyBindJump.pressed) {
                    Fly.mc.thePlayer.motionY = 1.0;
                    Fly.mc.timer.timerSpeed = 1.0f;
                }
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                if (Fly.mc.thePlayer.ticksExisted % 5 == 0) {
                    if (Fly.mc.gameSettings.keyBindJump.pressed) {
                        Fly.mc.thePlayer.motionY = 1.0;
                    }
                    else if (Fly.mc.gameSettings.keyBindSneak.pressed) {
                        Fly.mc.thePlayer.motionY = -1.0;
                    }
                    else {
                        Fly.mc.thePlayer.motionY = 0.17000000178813934;
                    }
                }
                else {
                    Fly.mc.timer.timerSpeed = 1.0f;
                }
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("CubecraftNew")) {
                final double speed2 = (float)Aqua.setmgr.getSetting("FlySentinelSpeed").getCurrentNumber();
                if (Fly.mc.thePlayer.ticksExisted % 3 == 0) {
                    PlayerUtil.setSpeed(speed2);
                }
                else {
                    PlayerUtil.setSpeed(0.0);
                }
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("CubecraftSmooth")) {
                final double speed2 = (float)Aqua.setmgr.getSetting("FlySentinelSpeed").getCurrentNumber();
                PlayerUtil.setSpeed(speed2);
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Cubecraft")) {
                PlayerUtil.setSpeed((float)Aqua.setmgr.getSetting("FlySentinelSpeed").getCurrentNumber());
                Fly.mc.thePlayer.onGround = true;
                if (Fly.mc.gameSettings.keyBindJump.pressed) {
                    Fly.mc.thePlayer.motionY = 1.0;
                    Fly.mc.timer.timerSpeed = 1.0f;
                }
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                if (Fly.mc.thePlayer.ticksExisted % 5 == 0) {
                    if (Fly.mc.gameSettings.keyBindJump.pressed) {
                        Fly.mc.thePlayer.motionY = 1.0;
                    }
                    else if (Fly.mc.gameSettings.keyBindSneak.pressed) {
                        Fly.mc.thePlayer.motionY = -1.0;
                    }
                    else {
                        Fly.mc.thePlayer.motionY = 0.17000000178813934;
                    }
                }
                else {
                    Fly.mc.timer.timerSpeed = 1.0f;
                }
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Verus2")) {
                Fly.mc.thePlayer.onGround = true;
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                final float speed3 = (float)Aqua.setmgr.getSetting("FlyBoost").getCurrentNumber();
                if (Fly.mc.thePlayer.hurtTime != 0) {
                    PlayerUtil.setSpeed(speed3);
                    Fly.mc.timer.timerSpeed = 0.6f;
                }
                else {
                    PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                    Fly.mc.timer.timerSpeed = 1.0f;
                }
                if (!Fly.mc.gameSettings.keyBindJump.pressed && Fly.mc.gameSettings.keyBindSneak.pressed) {
                    Fly.mc.thePlayer.motionY = -1.0;
                }
                else if (Fly.mc.gameSettings.keyBindJump.pressed && !Fly.mc.gameSettings.keyBindSneak.pressed) {
                    Fly.mc.thePlayer.motionY = 1.0;
                }
                else {
                    Fly.mc.thePlayer.motionY = 0.0;
                }
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Verus")) {
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(getX(), getY() - 1.5, getZ()), 1, new ItemStack(Blocks.stone.getItem(Fly.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                Fly.mc.thePlayer.onGround = true;
                if (Fly.mc.thePlayer.hurtTime != 0) {
                    this.verusDmg = true;
                }
                if (this.verusDmg) {
                    if (!Fly.mc.gameSettings.keyBindJump.pressed && Fly.mc.gameSettings.keyBindSneak.pressed) {
                        Fly.mc.thePlayer.motionY = 0.0;
                    }
                    else if (Fly.mc.gameSettings.keyBindJump.pressed && !Fly.mc.gameSettings.keyBindSneak.pressed) {
                        Fly.mc.thePlayer.motionY = 0.0;
                    }
                    else {
                        Fly.mc.thePlayer.motionY = 0.0;
                    }
                    Fly.mc.timer.timerSpeed = 0.2f;
                    PlayerUtil.setSpeed(5.0);
                }
                else {
                    Fly.mc.timer.timerSpeed = 0.2f;
                }
            }
            if (Aqua.setmgr.getSetting("FlyMode").getCurrentMode().equalsIgnoreCase("Minemora")) {
                if (Fly.mc.thePlayer.ticksExisted % 2 == 0) {
                    Fly.mc.thePlayer.motionY = 0.03;
                    Fly.mc.timer.timerSpeed = 1.4f;
                }
                else {
                    Fly.mc.thePlayer.motionY = -0.02;
                    Fly.mc.timer.timerSpeed = 1.4f;
                }
            }
        }
    }
    
    public static void sendPacketUnlogged(final Packet<? extends INetHandler> packet) {
        Fly.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static double getX() {
        return Fly.mc.thePlayer.posX;
    }
    
    public static double getY() {
        return Fly.mc.thePlayer.posY;
    }
    
    public static double getZ() {
        return Fly.mc.thePlayer.posZ;
    }
}
