package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Movement.EventCollide;
import Ascii4UwUWareClient.API.Events.World.EventMove;
import Ascii4UwUWareClient.API.Events.World.EventPacketReceive;
import Ascii4UwUWareClient.API.Events.World.EventPacketSend;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.Killaura;
import Ascii4UwUWareClient.UI.Notification.Notification;
import Ascii4UwUWareClient.UI.Notification.NotificationManager;
import Ascii4UwUWareClient.UI.Notification.NotificationType;
import Ascii4UwUWareClient.Util.Helper;
import Ascii4UwUWareClient.Util.MoveUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
import Ascii4UwUWareClient.Util.player.MovementUtil;
import Ascii4UwUWareClient.Util.player.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

import java.awt.*;
import java.util.Random;

public class Fly extends Module {
    public Mode mode = new Mode("Mode", "Mode", FlyMode.values(), FlyMode.Vanilla);
    private final Option<Boolean> lagcheck = new Option<Boolean>("LagCheck", "LagCheck", true);
    private final Option<Boolean> dragonvalue = new Option<Boolean>("Dragon", "Dragon", false);
    private final Option<Boolean> par = new Option<Boolean>("Particle", "Particle", false);
    private final Option<Boolean> bob = new Option<Boolean>("Bobbing", "Bobbing", true);
    private final Option<Boolean> verusFast = new Option<Boolean>("Verus Fast", "Verus Fast", false);
    private final Numbers <Double> VerusSpeed = new Numbers <Double> ( "Verus Speed", "Verus Speed", 9D, 1D, 10D, 0.1 );
    private final Numbers <Double> VerusTick = new Numbers <Double> ( "Verus Tick", "Verus Tick", 20D, 1D, 100D, 0.1 );
    private final Numbers <Double> speed = new Numbers <Double> ( "Speed", "Speed", 1.0, 0.1, 5.0, 0.1 );

    private final TimerUtil time = new TimerUtil();
    private final TimerUtil kickTimer = new TimerUtil();
    private double movementSpeed;
    private boolean countPerl;
    private EntityDragon dragon;
    int counter, level, sexVerus;
    private double flyHeight;
    private boolean dragoncrea = false;

    private int packetCounter, boostTicks;
    private final TimerUtil deactivationDelay = new TimerUtil();
    private final TimerUtil verusTimer = new TimerUtil();
    double moveSpeed, lastDist;
    boolean b2;
    private boolean hypixelDamaged;
   public int done;
    boolean fly;

    public Fly() {
        super("Fly", new String[]{"fly", "angel"}, ModuleType.Move);
        this.setColor(new Color(158, 114, 243).getRGB());
        this.addValues(this.mode, this.lagcheck, this.dragonvalue, this.par, bob, verusFast, this.speed, VerusSpeed, VerusTick);
    }


    /**
     * Yes i know i already got a Method in the MovementUtil.
     * i dont really care about ugly code.
     * Whoever reads this i hate you anyways cuz you are trying to c&p my code!
     */
    /**
     * gets the needed falldistance for damage and fly.
     */

    public static float getMaxFallDist() {
        PotionEffect potioneffect = Minecraft.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return (float) (Minecraft.thePlayer.getMaxFallHeight() + f);
    }

    double oldX;
    double oldY;
    double oldZ;
    float oldYaw;
    float oldPitch;

    @EventHandler
    private void onPacket(EventPacketReceive ep) {

        if (mode.getValue() == FlyMode.RedeSky){
        }

        if (this.lagcheck.getValue().booleanValue()) {
            if (ep.getPacket() instanceof S08PacketPlayerPosLook && this.deactivationDelay.delay(2000F)) {
                ++this.packetCounter;
                S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook) ep.getPacket();
                pac.yaw = Minecraft.thePlayer.rotationYaw;
                pac.pitch = Minecraft.thePlayer.rotationPitch;
                this.level = -5;
                if (Client.instance.getModuleManager().getModuleByClass(Fly.class).isEnabled()) {
                    Client.instance.getModuleManager().getModuleByClass(Fly.class).setEnabled(false);
                    NotificationManager
                            .show(new Notification(NotificationType.ERROR, "LagBack check!", "Disabled Fly", 3));
                }
            }
        }
    }

    @Override
    public void onEnable() {
        float forward = MovementInput.moveForward;
        float strafe = MovementInput.moveStrafe;
        countPerl = false;
        hypixelDamaged = false;
        level = 1;
        dragoncrea = false;
        moveSpeed = 0D;
        sexVerus = 0;
        b2 = true;
        lastDist = 0.0D;
        kickTimer.reset ();
        verusTimer.reset ();
        done = 0;
        if (mode.getValue() == FlyMode.RedeSky ) {

                mc.thePlayer.jump ();
            mc.thePlayer.capabilities.isFlying = false;
        }

        if (mode.getValue() == FlyMode.OldVerus &&  verusFast.getValue()) {


                Helper.sendMessage ( "You need to wait 10 - 20 second between fly" );
            if (Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0, 3.0001, 0).expand(0, 0, 0)).isEmpty()) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 3.0001, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
            }
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.42, Minecraft.thePlayer.posZ);
        }
        }


    @Override
    public void onDisable() {
        if (this.dragonvalue.getValue().booleanValue()) {
            Minecraft.theWorld.removeEntity(dragon);
        }
        if (mode.getValue() == FlyMode.RedeSky ) {
            mc.timer.timerSpeed = 1;
            mc.thePlayer.capabilities.isFlying = false;
            mc.thePlayer.speedInAir = 0.02F;
        }
        final double posX = Minecraft.thePlayer.posX;
        final double posY = Minecraft.thePlayer.posY;
        final double posZ = Minecraft.thePlayer.posZ;
        this.mc.timer.timerSpeed = 1.0f;
        Minecraft.thePlayer.motionX = 0;
        Minecraft.thePlayer.motionY = 0;
        Minecraft.thePlayer.motionZ = 0;
        Minecraft.thePlayer.capabilities.isFlying = false;
        level = 1;
        moveSpeed = 0.1D;
        countPerl = false;
        hypixelDamaged = false;
        verusTimer.reset();
        b2 = false;
        sexVerus = 0;
        lastDist = 0.0D;
    }


    public static boolean isOnGround(double height) {
        return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer,
                Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        if (mode.getValue() == FlyMode.Verus ) {
            if (mc.thePlayer.motionY < 0) {
                mc.thePlayer.motionY = 0;
                mc.thePlayer.onGround = false;
            }
        }
        if (bob.getValue()){
            Minecraft.thePlayer.cameraYaw = 0.105F;
        }

        this.setSuffix(this.mode.getValue());
        if (this.mode.getValue() == FlyMode.Motion) {
            MovementUtil.setMotion(speed.getValue());
            Minecraft.thePlayer.onGround = false;
            e.setOnground(isOnGround(0.001));
            if (Minecraft.thePlayer.movementInput.jump) {
                Minecraft.thePlayer.motionY = speed.getValue() / 2 * 0.6;
            } else if (Minecraft.thePlayer.movementInput.sneak) {
                Minecraft.thePlayer.motionY = -speed.getValue() / 2 * 0.6;
            } else {
                Minecraft.thePlayer.motionY = 0;
            }
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
        if (this.mode.getValue() == FlyMode.Vanilla) {
            Minecraft.thePlayer.motionY = Minecraft.thePlayer.movementInput.jump ? 1.0 : (Minecraft.thePlayer.movementInput.sneak ? -1.0 : 0.0);
            if (Minecraft.thePlayer.moving()) {

                Minecraft.thePlayer.setSpeed(3.0);
            } else {
                Minecraft.thePlayer.setSpeed(0.0);
            }
            Minecraft.thePlayer.capabilities.isFlying = true;
        }
        if (mode.getValue() == FlyMode.MCCentral) {
            Minecraft.thePlayer.motionY = 0;
            MovementUtil.setMotion(speed.getValue());
            if (mc.gameSettings.keyBindJump.isPressed()) {
                Minecraft.thePlayer.motionY = 0.1;
            }
        }

        if (mode.getValue() == FlyMode.OldVerus) {

            if (Minecraft.thePlayer.hurtTime > 0 && !hypixelDamaged) {
                hypixelDamaged = true;
                boostTicks = VerusTick.getValue().intValue();
            }
            Minecraft.thePlayer.motionY = 0;
            Minecraft.thePlayer.onGround = true;
            if (mc.gameSettings.keyBindJump.isPressed() && mc.gameSettings.keyBindJump.isKeyDown()) {
                Minecraft.thePlayer.motionY = 1;
            }else if (mc.gameSettings.keyBindSneak.isPressed() && mc.gameSettings.keyBindSneak.isKeyDown()){
                Minecraft.thePlayer.motionY = -1;
            }

            if (!MovementUtil.isMoving()){
                MovementUtil.setMotion(0);
            }

        }

        if (this.mode.getValue() == FlyMode.RedeSky) {

             done++;


            mc.timer.timerSpeed = 1.0F;

        mc.thePlayer.speedInAir =  0.45F;
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.onGround = false;
        };


        if (mode.getValue() == FlyMode.H_Enderperl) {
            try {
                if (Minecraft.thePlayer.hurtTime > 0) {
                    hypixelDamaged = true;
                }

                if (!hypixelDamaged && !countPerl) {
                    moveSpeed = this.speed.getValue();
                    for (int i = 0; i < 45; i++) {
                        if (Minecraft.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl) {
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
                            countPerl = true;
                        } else {
                            Helper.sendMessage("Please move your enderperl to your hotbar");
                        }
                    }
                }
                if (hypixelDamaged && countPerl) {
                    Minecraft.thePlayer.motionY = 0;
                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                        Minecraft.thePlayer.motionY = this.speed.getValue();
                    } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                        Minecraft.thePlayer.motionY = -this.speed.getValue();
                    }
                    MoveUtils.setMotion(speed.getValue());
                } else {
                    e.setPitch(-90);
                    Minecraft.thePlayer.motionX = 0;
                    Minecraft.thePlayer.motionY = 0;
                    Minecraft.thePlayer.motionZ = 0;
                }
            } catch (Exception error) {

            }
        }

        if (this.dragonvalue.getValue() && !this.dragoncrea) {
            this.dragoncrea = true;
            dragon = new EntityDragon( Minecraft.theWorld );
            Minecraft.theWorld.addEntityToWorld(666, dragon);
            Minecraft.thePlayer.ridingEntity = dragon;
        } else if (this.dragonvalue.getValue()) {
            final double posX4 = Minecraft.thePlayer.posX;
            final double posX = posX4 - MathHelper.cos( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final double posY = Minecraft.thePlayer.posY;
            final double posZ2 = Minecraft.thePlayer.posZ;
            final double posZ = posZ2 - MathHelper.sin( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final float f = 0.4f;
            final float n2 = -MathHelper.sin( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionX = n2 * MathHelper.cos( Minecraft.thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final float cos = MathHelper.cos( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionZ = cos * MathHelper.cos( Minecraft.thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final double motionY = -MathHelper.sin((Minecraft.thePlayer.rotationPitch + 2.0f) / 180.0f * 3.1415927f) * f;
            final double xCoord = posX + motionX;
            final double yCoord = posY + motionY;
            final double zCoord = posZ + motionZ;
            dragon.rotationPitch = Minecraft.thePlayer.rotationPitch;
            dragon.rotationYaw = Minecraft.thePlayer.rotationYawHead - 180;
            dragon.setRotationYawHead( Minecraft.thePlayer.rotationYawHead);
            dragon.setPosition(xCoord, Minecraft.thePlayer.posY - 2, zCoord);
        }
        if (this.par.getValue().booleanValue()) {
            final double posX4 = Minecraft.thePlayer.posX;
            final double posX = posX4 - MathHelper.cos( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final double posY = Minecraft.thePlayer.posY;
            final double posZ2 = Minecraft.thePlayer.posZ;
            final double posZ = posZ2 - MathHelper.sin( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final float f = 0.4f;
            final float n2 = -MathHelper.sin( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionX = n2 * MathHelper.cos( Minecraft.thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final float cos = MathHelper.cos( Minecraft.thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionZ = cos * MathHelper.cos( Minecraft.thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final double motionY = -MathHelper.sin((Minecraft.thePlayer.rotationPitch + 2.0f) / 180.0f * 3.1415927f) * f;
            for (int i = 0; i < 90; ++i) {
                final WorldClient theWorld = Minecraft.theWorld;
                final EnumParticleTypes particleType = (i % 4 == 0) ? (EnumParticleTypes.CRIT_MAGIC)
                        : (new Random().nextBoolean() ? EnumParticleTypes.HEART : EnumParticleTypes.ENCHANTMENT_TABLE);
                final double xCoord = posX + motionX;
                final double yCoord = posY + motionY;
                final double zCoord = posZ + motionZ;
                final double motionX2 = Minecraft.thePlayer.motionX;
                final double motionY2 = Minecraft.thePlayer.motionY;
                theWorld.spawnParticle(particleType, xCoord, yCoord, zCoord, motionX2, motionY2, Minecraft.thePlayer.motionZ
                );
            }
        }
    }

    @EventHandler
    public void Collide(EventCollide e) {
        if (mode.getValue () == FlyMode.Collide && !mc.thePlayer.isSneaking ()) {
            e.setBoundingBox(new AxisAlignedBB(-2.0, -1.0, -2.0, 2.0, 1.0, 2.0).offset(e.getX(), e.getY(), e.getZ()));
            mc.timer.timerSpeed = 0.2f;
        }
    }
    @EventHandler
    public void onMove(EventPacketSend e) {
        if (mode.getValue () == FlyMode.Verus) {
            if (e.getPacket () instanceof C03PacketPlayer) {
                if (mc.thePlayer.motionY < 0)
                    if (e.getPacket () instanceof C03PacketPlayer) {
                        C03PacketPlayer C03PacketPlayer = (C03PacketPlayer) e.getPacket ();
                        C03PacketPlayer.onGround = false;

                    }
            }
        }
    }

    @EventHandler
    public void onMove(EventMove e) {
        if (mode.getValue() == FlyMode.OldVerus && verusFast.getValue()) {
            if (boostTicks > 0) {
                moveSpeed = boostTicks / VerusTick.getValue() * VerusSpeed.getValue();
                MoveUtils.setMotion(moveSpeed);
            }
            boostTicks--;
            if (!hypixelDamaged){
                MovementUtil.setMotion(0);
                moveSpeed = 0;
            }
        }
    }

    public void updateFlyHeight() {
        double h = 1.0D;
        AxisAlignedBB box = Minecraft.thePlayer.getEntityBoundingBox().expand(0.0625D, 0.0625D, 0.0625D);
        for (this.flyHeight = 0.0D; this.flyHeight < Minecraft.thePlayer.posY; this.flyHeight += h) {
            AxisAlignedBB nextBox = box.offset(0.0D, -this.flyHeight, 0.0D);
            if (Minecraft.theWorld.checkBlockCollision(nextBox)) {
                if (h < 0.0625D) {
                    break;
                }
                this.flyHeight -= h;
                h /= 2.0D;
            }
        }
    }

    public void goToGround() {
        if (this.flyHeight > 300.0D) {
            return;
        }
        double minY = Minecraft.thePlayer.posY - this.flyHeight;
        if (minY <= 0.0D) {
            return;
        }
        for (double y = Minecraft.thePlayer.posY; y > minY; ) {
            y -= 8.0D;
            if (y < minY) {
                y = minY;
            }
            C03PacketPlayer.C04PacketPlayerPosition packet = new C03PacketPlayer.C04PacketPlayerPosition(
                    Minecraft.thePlayer.posX, y, Minecraft.thePlayer.posZ, true);
            Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
        }
        for (double y = minY; y < Minecraft.thePlayer.posY; ) {
            y += 8.0D;
            if (y > Minecraft.thePlayer.posY) {
                y = Minecraft.thePlayer.posY;
            }
            C03PacketPlayer.C04PacketPlayerPosition packet = new C03PacketPlayer.C04PacketPlayerPosition(
                    Minecraft.thePlayer.posX, y, Minecraft.thePlayer.posZ, true);
            Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
        }
    }

    public static void damage(double damage) {
        Minecraft mc = Minecraft.getMinecraft();

        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

        double offset = 0.0625;
        //offset = 0.015625;
        if (mc.thePlayer != null && mc.getNetHandler() != null) {
            for (short i = 0; i <= ((3 + damage) / offset); i++) {
                mc.getNetHandler().addToSendQueueSilent (new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + ((offset / 2) * 1), mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + ((offset / 2) * 2), mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
                //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,
                //mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, (i == ((3 + damage) / offset))));
            }
        }

    }

    public void setMoveSpeed(EventMove event, double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        TargetStrafe targetStrafe = (TargetStrafe) Client.instance.getModuleManager().getModuleByClass(TargetStrafe.class);
        double moveForward = Minecraft.thePlayer.movementInput.getForward();
        double moveStrafe = TargetStrafe.canStrafe() ? targetStrafe.strafeDirection : Minecraft.thePlayer.movementInput.getStrafe();
        if (TargetStrafe.canStrafe()) {
            if (Minecraft.thePlayer.getDistanceToEntity(Killaura.target) <= 3) moveForward = 0;
        }
        double yaw = TargetStrafe.canStrafe() ? RotationUtil.getRotations(Killaura.target)[0] : Minecraft.thePlayer.rotationYaw;
        if (moveForward == 0.0F && moveStrafe == 0.0F) {
            event.setX(0);
            event.setZ(0);
        }
        if (moveForward != 0 && moveStrafe != 0) {
            moveForward = moveForward * Math.sin(Math.PI / 4);
            moveStrafe = moveStrafe * Math.cos(Math.PI / 4);
        }
        event.setX(moveForward * moveSpeed * -Math.sin(Math.toRadians(yaw)) + (moveStrafe) * moveSpeed * Math.cos(Math.toRadians(yaw)));
        event.setZ(moveForward * moveSpeed * Math.cos(Math.toRadians(yaw)) - (moveStrafe) * moveSpeed * -Math.sin(Math.toRadians(yaw)));

    }

    private void moveBlocksToHotbar() {
        boolean added = false;
        if (!isHotbarFull()) {
            for (int k = 0; k < Minecraft.thePlayer.inventory.mainInventory.length; ++k) {
                if (k > 8 && !added) {
                    final ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
                    if (itemStack != null && itemStack.getItem() instanceof ItemEnderPearl) {
                        shiftClick(k);
                        added = true;
                    }
                }
            }
        }
    }

    public void moveBlocksBackToInventory() {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.thePlayer.inventory.getStackInSlot(i) == null)
                continue;
            if (Minecraft.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl) {
                shiftClick(i);
            }
        }
    }
    public static void shiftClick(int slot) {
        Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.thePlayer);
    }

    public boolean isHotbarFull() {
        int count = 0;
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
            if (itemStack != null) {
                count++;
            }
        }
        return count == 8;
    }



    public enum FlyMode {
        Vanilla, Motion, RedeSky, MCCentral, H_Enderperl, OldVerus ,Collide, Verus
    }

}