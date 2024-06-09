// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import events.listeners.EventPlayerMove;
import net.minecraft.client.entity.EntityPlayerSP;
import intent.AquaDev.aqua.modules.combat.Killaura;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.potion.Potion;
import net.minecraft.block.BlockDoubleStoneSlabNew;
import net.minecraft.block.BlockDoubleStoneSlab;
import net.minecraft.block.BlockDoubleWoodSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import events.listeners.EventUpdate;
import intent.AquaDev.aqua.notifications.NotificationManager;
import intent.AquaDev.aqua.notifications.Notification;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import events.listeners.EventPacket;
import events.Event;
import intent.AquaDev.aqua.utils.PlayerUtil;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Speed extends Module
{
    public Speed() {
        super("Speed", Type.Movement, "Speed", 0, Category.Movement);
        Aqua.setmgr.register(new Setting("MotionReset", this, true));
        Aqua.setmgr.register(new Setting("AutoDisable", this, true));
        Aqua.setmgr.register(new Setting("WatchdogBoost", this, false));
        Aqua.setmgr.register(new Setting("Speed", this, 1.0, 0.3, 9.0, false));
        Aqua.setmgr.register(new Setting("TimerBoost", this, 1.3, 1.0, 3.0, false));
        Aqua.setmgr.register(new Setting("YMotion", this, 0.42, 0.0, 0.9, false));
        Aqua.setmgr.register(new Setting("SentinelSpeed", this, 3.0, 0.3, 9.0, false));
        Aqua.setmgr.register(new Setting("Mode", this, "Watchdog", new String[] { "Watchdog", "Watchdog2", "WatchdogNew", "WatchdogSave", "Vanilla", "Strafe", "AAC3", "Intave14", "Cubecraft", "CubecraftBhob" }));
    }
    
    @Override
    public void onEnable() {
        if (Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState()) {}
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Speed.mc.timer.timerSpeed = 1.0f;
        Speed.mc.gameSettings.keyBindJump.pressed = false;
        PlayerUtil.setSpeed(0.0);
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPacket) {
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof S08PacketPlayerPosLook && Aqua.setmgr.getSetting("SpeedAutoDisable").isState()) {
                NotificationManager.addNotificationToQueue(new Notification("Speed", "§cDisabled to prevent flags", 1000L, Notification.NotificationType.INFO));
                Aqua.moduleManager.getModuleByName("Speed").setState(false);
            }
        }
        if (event instanceof EventUpdate) {
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("Cubecraft")) {
                if (Speed.mc.thePlayer.isMoving() && Speed.mc.thePlayer.onGround) {
                    final float blockStep = 1.5f;
                    final double[] dir = this.getDirection1((float)Aqua.setmgr.getSetting("SpeedSentinelSpeed").getCurrentNumber());
                    if (Speed.mc.thePlayer.ticksExisted % 3 == 0) {
                        Speed.mc.thePlayer.setPosition(Speed.mc.thePlayer.posX + dir[0], Speed.mc.thePlayer.posY, Speed.mc.thePlayer.posZ + dir[1]);
                    }
                }
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Speed.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("CubecraftBhob")) {
                PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                if (Speed.mc.thePlayer.onGround) {
                    Speed.mc.thePlayer.jump();
                    PlayerUtil.setSpeed(0.0);
                    Speed.mc.timer.timerSpeed = 1.5f;
                    final double[] dir2 = this.getDirection1((float)Aqua.setmgr.getSetting("SpeedSentinelSpeed").getCurrentNumber());
                    Speed.mc.thePlayer.setPosition(Speed.mc.thePlayer.posX + dir2[0], Speed.mc.thePlayer.posY, Speed.mc.thePlayer.posZ + dir2[1]);
                }
                if (Speed.mc.thePlayer.isMoving()) {
                    Speed.mc.timer.timerSpeed = 2.0f;
                    if (Speed.mc.thePlayer.fallDistance > 0.0f) {
                        final float blockStep = 1.5f;
                        this.getDirection1((float)Aqua.setmgr.getSetting("SpeedSentinelSpeed").getCurrentNumber());
                    }
                }
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Speed.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("Vanilla")) {
                if (Speed.mc.thePlayer.onGround) {
                    Speed.mc.thePlayer.motionY = (float)Aqua.setmgr.getSetting("SpeedYMotion").getCurrentNumber();
                }
                PlayerUtil.setSpeed((float)Aqua.setmgr.getSetting("SpeedSpeed").getCurrentNumber());
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Speed.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("Intave14")) {
                final boolean flag = this.getBlockUnderPlayer(0.1f) instanceof BlockStairs || (this.getBlockUnderPlayer(0.1f) instanceof BlockSlab && !(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleWoodSlab) && (!(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlab) || this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlabNew));
                if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null && Aqua.moduleManager.getModuleByName("Speed").isToggled() && Speed.mc.thePlayer.onGround) {
                    final float t = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.186, 0.1861);
                    if (!Speed.mc.gameSettings.keyBindJump.pressed) {
                        Speed.mc.thePlayer.jump();
                    }
                    if (!flag) {}
                }
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) == null && !Speed.mc.gameSettings.keyBindJump.pressed && Speed.mc.thePlayer.isMoving()) {
                    Speed.mc.thePlayer.jump();
                }
                if (!Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) == null) {
                    final float strafe1 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.0028299998957663774, 0.002831999910995364);
                    if (Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState() && !flag) {
                        final EntityPlayerSP thePlayer = Speed.mc.thePlayer;
                        thePlayer.motionY -= strafe1;
                    }
                }
                if (!Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null && Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState()) {
                    final float strafe1 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.0019000000320374966, 0.0020000000949949026);
                    if (!flag) {
                        final EntityPlayerSP thePlayer2 = Speed.mc.thePlayer;
                        thePlayer2.motionY -= strafe1;
                    }
                }
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("AAC3")) {
                final boolean boost = Math.abs(Speed.mc.thePlayer.rotationYawHead - Speed.mc.thePlayer.rotationYaw) < 90.0f;
                if (Speed.mc.thePlayer.isMoving() && Speed.mc.thePlayer.hurtTime < 5) {
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.timer.timerSpeed = 1.0f;
                        if (!Aqua.moduleManager.getModuleByName("Scaffold").isToggled()) {
                            Speed.mc.thePlayer.jump();
                        }
                        else {
                            Speed.mc.thePlayer.motionY = 0.4;
                        }
                        final float f = getDirection();
                        if (!Aqua.moduleManager.getModuleByName("Scaffold").isToggled()) {}
                    }
                    else {
                        Speed.mc.timer.timerSpeed = 1.0f;
                        Speed.mc.thePlayer.speedInAir = 0.021f;
                        final double currentSpeed = Math.sqrt(Speed.mc.thePlayer.motionX * Speed.mc.thePlayer.motionX + Speed.mc.thePlayer.motionZ * Speed.mc.thePlayer.motionZ);
                        final double speed1 = 1.0074;
                        final double direction = getDirection();
                        Speed.mc.thePlayer.motionX = -Math.sin(direction) * speed1 * currentSpeed;
                        Speed.mc.thePlayer.motionZ = Math.cos(direction) * speed1 * currentSpeed;
                    }
                }
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("Strafe")) {
                PlayerUtil.setSpeed((float)Aqua.setmgr.getSetting("SpeedSentinelSpeed").getCurrentNumber());
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                Fly.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(Fly.getX(), Fly.getY() - 1.5, Fly.getZ()), 1, new ItemStack(Blocks.stone.getItem(Speed.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                if (Speed.mc.thePlayer.onGround) {
                    final EntityPlayerSP thePlayer3 = Speed.mc.thePlayer;
                    thePlayer3.motionY += 0.41999998688697815;
                }
                else {
                    final EntityPlayerSP thePlayer4 = Speed.mc.thePlayer;
                    thePlayer4.motionY -= 0.029999999329447746;
                }
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("WatchdogNew")) {
                if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                    final float boost2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.186, 0.18601);
                    if (Speed.mc.thePlayer.onGround) {
                        if (!Speed.mc.gameSettings.keyBindJump.pressed) {
                            Speed.mc.thePlayer.jump();
                        }
                        final boolean flag2 = this.getBlockUnderPlayer(0.1f) instanceof BlockStairs || (this.getBlockUnderPlayer(0.1f) instanceof BlockSlab && !(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleWoodSlab) && (!(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlab) || this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlabNew));
                        if (!flag2) {
                            PlayerUtil.setSpeed(PlayerUtil.getSpeed() + boost2);
                        }
                    }
                    else {
                        PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                    }
                }
                else if (Speed.mc.thePlayer.onGround && !Speed.mc.gameSettings.keyBindJump.pressed) {
                    Speed.mc.thePlayer.jump();
                }
                else {
                    PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                }
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("WatchdogSave") && Speed.mc.thePlayer.isMoving()) {
                if (Speed.mc.thePlayer.hurtTime != 0) {
                    final float speed2 = (float)Aqua.setmgr.getSetting("SpeedTimerBoost").getCurrentNumber();
                    Speed.mc.timer.timerSpeed = speed2;
                    if ((!Speed.mc.thePlayer.isBurning() && Killaura.target != null && Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState()) || (!Speed.mc.thePlayer.isInLava() && Killaura.target != null && Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState())) {
                        PlayerUtil.setSpeed(PlayerUtil.getSpeed() + 0.007);
                    }
                    else {
                        PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                    }
                }
                else {
                    Speed.mc.timer.timerSpeed = 1.0f;
                }
                if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                    final float boost2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.186, 0.18601);
                    if (Speed.mc.thePlayer.onGround) {
                        if (!Speed.mc.gameSettings.keyBindJump.pressed) {
                            Speed.mc.thePlayer.jump();
                        }
                        final boolean flag2 = this.getBlockUnderPlayer(0.1f) instanceof BlockStairs || (this.getBlockUnderPlayer(0.1f) instanceof BlockSlab && !(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleWoodSlab) && (!(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlab) || this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlabNew));
                        if (!flag2) {
                            PlayerUtil.setSpeed(PlayerUtil.getSpeed() + boost2);
                        }
                    }
                }
                else if (Speed.mc.thePlayer.onGround && !Speed.mc.gameSettings.keyBindJump.pressed) {
                    Speed.mc.thePlayer.jump();
                    PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                }
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("Watchdog2")) {
                if (Speed.mc.thePlayer.onGround && !Speed.mc.gameSettings.keyBindJump.pressed && Speed.mc.thePlayer.isMoving()) {
                    Speed.mc.thePlayer.jump();
                    if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null) {
                        PlayerUtil.setSpeed(PlayerUtil.getSpeed() + 0.105);
                    }
                    else {
                        PlayerUtil.setSpeed(PlayerUtil.getSpeed() + 0.06);
                    }
                    Speed.mc.timer.timerSpeed = 1.0f;
                    Speed.mc.thePlayer.jumpMovementFactor = 0.04f;
                }
                final float strafe2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.002300000051036477, 0.0023399998899549246);
                if (Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState() && Speed.mc.thePlayer.fallDistance > 0.0f) {
                    final EntityPlayerSP thePlayer5 = Speed.mc.thePlayer;
                    thePlayer5.motionY -= strafe2;
                }
            }
            if (Aqua.setmgr.getSetting("SpeedMode").getCurrentMode().equalsIgnoreCase("Watchdog")) {
                if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null && Aqua.moduleManager.getModuleByName("Speed").isToggled()) {
                    if (!Speed.mc.thePlayer.onGround) {
                        if (Speed.mc.thePlayer.ticksExisted % 2 == 0) {
                            Speed.mc.timer.timerSpeed = 0.95f;
                        }
                        else {
                            Speed.mc.timer.timerSpeed = 1.23f;
                        }
                    }
                    else {
                        Speed.mc.timer.timerSpeed = 1.0f;
                    }
                }
                final boolean flag = this.getBlockUnderPlayer(0.1f) instanceof BlockStairs || (this.getBlockUnderPlayer(0.1f) instanceof BlockSlab && !(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleWoodSlab) && (!(this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlab) || this.getBlockUnderPlayer(0.1f) instanceof BlockDoubleStoneSlabNew));
                if (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null && Aqua.moduleManager.getModuleByName("Speed").isToggled()) {
                    if (Speed.mc.thePlayer.onGround) {
                        final float t = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.185, 0.185);
                        if (!Speed.mc.gameSettings.keyBindJump.pressed) {
                            Speed.mc.thePlayer.jump();
                        }
                        if (!flag) {
                            PlayerUtil.setSpeed(PlayerUtil.getSpeed() + t);
                        }
                    }
                    else {
                        PlayerUtil.setSpeed(PlayerUtil.getSpeed());
                    }
                }
                if (Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) == null && !Speed.mc.gameSettings.keyBindJump.pressed && Speed.mc.thePlayer.isMoving()) {
                    Speed.mc.thePlayer.jump();
                }
                if (!Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) == null) {
                    final float strafe1 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.0028299998957663774, 0.002839999971911311);
                    if (Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState() && !flag) {
                        final EntityPlayerSP thePlayer6 = Speed.mc.thePlayer;
                        thePlayer6.motionY -= strafe1;
                    }
                }
                if (!Speed.mc.thePlayer.onGround && Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed) != null && Aqua.setmgr.getSetting("SpeedWatchdogBoost").isState()) {
                    final float strafe1 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.0019000000320374966, 0.0020000000949949026);
                    if (!flag) {
                        final EntityPlayerSP thePlayer7 = Speed.mc.thePlayer;
                        thePlayer7.motionY -= strafe1;
                    }
                }
                PlayerUtil.setSpeed(PlayerUtil.getSpeed());
            }
        }
    }
    
    public static void setSpeed1(final EventPlayerMove moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? -45 : 45);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            }
            else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }
    
    Block getBlockUnderPlayer(final float offsetY) {
        return this.getBlockUnderPlayer(Speed.mc.thePlayer, offsetY);
    }
    
    Block getBlockUnderPlayer(final EntityPlayer player, final float offsetY) {
        return this.getWorld().getBlockState(new BlockPos(player.posX, player.posY - offsetY, player.posZ)).getBlock();
    }
    
    WorldClient getWorld() {
        return Speed.mc.theWorld;
    }
    
    public static float getDirection() {
        float var1 = Speed.mc.thePlayer.rotationYaw;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (Speed.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Speed.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Speed.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }
    
    public double[] getDirection1(final double speed) {
        final float dir = this.getDir();
        return new double[] { -(Math.sin(dir) * speed), Math.cos(dir) * speed };
    }
    
    public float getDir() {
        float yaw = Speed.mc.thePlayer.rotationYaw;
        float f = 1.0f;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
            f = -0.5f;
        }
        else if (Speed.mc.thePlayer.moveForward > 0.0f) {
            f = 0.5f;
        }
        if (Speed.mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * f;
        }
        else if (Speed.mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * f;
        }
        return (float)Math.toRadians(yaw);
    }
}
