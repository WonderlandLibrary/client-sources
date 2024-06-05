package Focus.Beta.IMPL.Module.impl.move;

import Focus.Beta.API.events.world.EventPacketSend;
import Focus.Beta.UTILS.world.PacketUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;

import Focus.Beta.Client;
import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventMove;
import Focus.Beta.API.events.world.EventPacketReceive;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.Module.impl.exploit.Disabler;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.UTILS.helper.Helper;
import Focus.Beta.UTILS.world.MovementUtil;

public class Fly extends Module {
    private static int SurvivalDubBow = 0;
    public static int ticks = 0;
	public Mode mode = new Mode("Mode", "Mode", FlyMode.values(), FlyMode.Vanilla);
    public final Option<Boolean> lagcheck = new Option<Boolean>("LagCheck", "LagCheck", true);
    public final Option<Boolean> dragonvalue = new Option<Boolean>("Dragon", "Dragon", false);
    public final Option<Boolean> par = new Option<Boolean>("Particle", "Particle", false);
    public final Option<Boolean> aac = new Option<Boolean>("AACV5 motion", "AACV5 motion", false);
    public final Option<Boolean> bob = new Option<Boolean>("Bobbing", "Bobbing", true);
    public final Option<Boolean> verusFast = new Option<Boolean>("Verus Fast", "Verus Fast", false);
    public final Numbers <Double> VerusSpeed = new Numbers <Double> ( "Verus Speed", "Verus Speed", 9D, 0.5D, 10D, 0.1 );
    public final Numbers <Double> VerusTick = new Numbers <Double> ( "Verus Tick", "Verus Tick", 20D, 1D, 100D, 0.1 );
    public final Numbers <Double> speed = new Numbers <Double> ( "Speed", "Speed", 1.0, 0.1, 5.0, 0.1 );

    private final Focus.Beta.UTILS.world.Timer time = new Focus.Beta.UTILS.world.Timer();
    private final Focus.Beta.UTILS.world.Timer time2 = new Focus.Beta.UTILS.world.Timer();
    private final Focus.Beta.UTILS.world.Timer aac5timer = new Focus.Beta.UTILS.world.Timer();
    private final Focus.Beta.UTILS.world.Timer kickTimer = new Focus.Beta.UTILS.world.Timer();
    private double movementSpeed;
    private EntityDragon dragon;
    int counter, level, sexVerus;
    private double flyHeight;
    boolean flyable;
    private boolean throwP, dragoncrea, countPerl, bow, hypixelDamaged, b2, done, MinemenDamaged, cantrowpearl;

    private EntityOtherPlayerMP clonedPlayer = null;
    private int packetCounter, boostTicks;
    private final Blocks blocks = new Blocks();
    private final Focus.Beta.UTILS.world.Timer deactivationDelay = new Focus.Beta.UTILS.world.Timer();
    private final Focus.Beta.UTILS.world.Timer verusTimer = new Focus.Beta.UTILS.world.Timer();
    double moveSpeed, lastDist;
    private float minemenlookPitch;
    private float minemenlookYaw;
    private int enableTick;
    boolean canFly = false;
     int damagedTicks;

    public Fly() {
        super("Fly", new String[]{"fly", "angel"}, Type.MOVE, "Allow's player to fly");
        this.setColor(new Color(158, 114, 243).getRGB());
        this.addValues(this.mode, this.lagcheck, this.dragonvalue, this.par, bob, aac, verusFast, this.speed, VerusSpeed, VerusTick);
    }

    public static float getMaxFallDist() {
    	PotionEffect potioneffect = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return (float) (Minecraft.getMinecraft().thePlayer.getMaxFallHeight() + f);
    }

    double oldX;
    double oldY;
    private boolean aac5nextFlag = false;
    double oldZ;
    private NetHandlerPlayClient netHandler;
    float oldYaw;
    float oldPitch;
    public boolean back;


    @EventHandler
    public void nig(EventPacketSend ep){
        switch (mode.getModeAsString()){
            case "Verus":
                if(this.damaged) {
                    if (ep.getPacket() instanceof C0FPacketConfirmTransaction) {
                        final C0FPacketConfirmTransaction c0f = (C0FPacketConfirmTransaction) ep.getPacket();
                        if (c0f.windowId < 5.0) {
                            ep.setCancelled(true);
                            PacketUtil.sendPacket(new C0FPacketConfirmTransaction(ThreadLocalRandom.current().nextInt(100, 500), (short) 500, true));
                        }
                    }
                    if (ep.getPacket() instanceof C03PacketPlayer) {
                        final C03PacketPlayer packetPlayer = (C03PacketPlayer) ep.getPacket();
                        packetPlayer.onGround = true;
                        if (mc.thePlayer.ticksExisted > 15) {
                            ep.setCancelled(this.mc.thePlayer.ticksExisted % 3 == 0);
                        }
                    }
                }
                break;
        }
    }

    @EventHandler
    private void onPacket(EventPacketReceive ep) {




        if (this.lagcheck.getValue().booleanValue()) {
            if (ep.getPacket() instanceof S08PacketPlayerPosLook) {
                ++this.packetCounter;
                S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook) ep.getPacket();
                pac.yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
                pac.pitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
                this.level = -5;

                if (Client.instance.getModuleManager().getModuleByClass(Fly.class).isEnabled()) {
                    Client.instance.getModuleManager().getModuleByClass(Fly.class).setEnabled(false);
                }
            }
        }
        if(mode.getModeAsString().equalsIgnoreCase("Redesky")) {
            if (ep instanceof EventPacketReceive && ep.getPacket() instanceof C03PacketPlayer){
                this.mc.thePlayer.posY = this.mc.thePlayer.lastTickPosY;
                this.mc.thePlayer.posX = this.mc.thePlayer.lastTickPosX;
                this.mc.thePlayer.posZ = this.mc.thePlayer.lastTickPosZ;
            }
        }
    }
    @Override
    public void onEnable() {
        float forward = MovementInput.moveForward;
        ticks = 0;
        float strafe = MovementInput.moveStrafe;
        countPerl = false;
        hypixelDamaged = false;
        level = 1;
        dragoncrea = false;
        moveSpeed = 0D;
        stage = 0;
        sexVerus = 0;
        ticks = 0;
        b2 = true;
        this.damagedTicks = 0;
        lastDist = 0.0D;
        canFly = false;
        kickTimer.reset();
        verusTimer.reset();
        time2.reset();

        if(mode
        .getModeAsString().equalsIgnoreCase("Verus")){

            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.001, mc.thePlayer.posZ, false));
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        }
    }


    @Override
    public void onDisable() {

        if (this.dragonvalue.getValue().booleanValue()) {
        	Minecraft.getMinecraft().theWorld.removeEntity(dragon);
        }
        SurvivalDubBow = 0;
        enableTick = 0;
        SurvivalDubGo = 0;
        flyable = true;
        final double posX = Minecraft.getMinecraft().thePlayer.posX;
        final double posY = Minecraft.getMinecraft().thePlayer.posY;
        final double posZ = Minecraft.getMinecraft().thePlayer.posZ;
        this.mc.timer.timerSpeed = 1.0f;
        Minecraft.getMinecraft().thePlayer.motionX = 0;
        Minecraft.getMinecraft().thePlayer.motionY = 0;
        Minecraft.getMinecraft().thePlayer.motionZ = 0;
        Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
        level = 1;
        moveSpeed = 0.1D;
        countPerl = false;
        hypixelDamaged = false;
        verusTimer.reset();
        damaged = false;
        b2 = false;
        sexVerus = 0;
        lastDist = 0.0D;
    }


    public static boolean isOnGround(double height) {
        return !Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer,
        		Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }    int stage;
	private int SurvivalDubGo;

    @EventHandler
    private void onUpdate(EventPreUpdate e) {


setSuffix(mode
        .getModeAsString());
        


        if(mode.getValue() == FlyMode.Verus){
            final double x = this.mc.thePlayer.posX;
            final double y = this.mc.thePlayer.posY;
            final double z = this.mc.thePlayer.posZ;
            if(mc.thePlayer.hurtTime > 0  && !this.damaged){
                this.damagedTicks = 0;
                this.damaged = true;
            }
            if(!MovementUtil.isMoving() && !this.damaged){
                MovementUtil.setMotion(0.0);
            }
            if(!this.damaged){
            }else{
                if(this.damagedTicks == 0){
                    this.mc.thePlayer.jump();
                }else{
                    this.mc.thePlayer.motionY = 0.0;
                    if(mc.thePlayer.hurtTime > 0){
                        MovementUtil.setMotion(1.8);
                    }else{
                        MovementUtil.setMotion(VerusSpeed.getValue());
                    }
                }
                e.setOnground(true);
                ++this.damagedTicks;
            }
            ++ticks;
        }

        if(mode.getValue() ==  FlyMode.WatchDuck){
            mc.thePlayer.motionY = 0;
            MovementUtil.setMotion(0.4f);
        }


        if (this.mode.getValue() == FlyMode.Vanilla) {

            mc.thePlayer.capabilities.isFlying = false;

            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionY = 0.0;
            mc.thePlayer.motionZ = 0.0;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionY += speed.getValue() * 0.5;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.thePlayer.motionY -= speed.getValue() * 0.5;
            }

            MovementUtil.setMotion(speed.getValue());
        }




        if (this.dragonvalue.getValue() && !this.dragoncrea) {
            this.dragoncrea = true;
            dragon = new EntityDragon( Minecraft.getMinecraft().theWorld );
            Minecraft.getMinecraft().theWorld.addEntityToWorld(666, dragon);
            Minecraft.getMinecraft().thePlayer.ridingEntity = dragon;
        } else if (this.dragonvalue.getValue()) {
            final double posX4 = Minecraft.getMinecraft().thePlayer.posX;
            final double posX = posX4 - MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final double posY = Minecraft.getMinecraft().thePlayer.posY;
            final double posZ2 = Minecraft.getMinecraft().thePlayer.posZ;
            final double posZ = posZ2 - MathHelper.sin( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final float f = 0.4f;
            final float n2 = -MathHelper.sin( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionX = n2 * MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final float cos = MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionZ = cos * MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final double motionY = -MathHelper.sin((Minecraft.getMinecraft().thePlayer.rotationPitch + 2.0f) / 180.0f * 3.1415927f) * f;
            final double xCoord = posX + motionX;
            final double yCoord = posY + motionY;
            final double zCoord = posZ + motionZ;
            dragon.rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
            dragon.rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYawHead - 180;
            dragon.setRotationYawHead( Minecraft.getMinecraft().thePlayer.rotationYawHead);
            dragon.setPosition(xCoord, Minecraft.getMinecraft().thePlayer.posY - 2, zCoord);
        }
        if (this.par.getValue().booleanValue()) {
            final double posX4 = Minecraft.getMinecraft().thePlayer.posX;
            final double posX = posX4 - MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final double posY = Minecraft.getMinecraft().thePlayer.posY;
            final double posZ2 = Minecraft.getMinecraft().thePlayer.posZ;
            final double posZ = posZ2 - MathHelper.sin( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
            final float f = 0.4f;
            final float n2 = -MathHelper.sin( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionX = n2 * MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final float cos = MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationYaw / 180.0f * 3.1415927f);
            final double motionZ = cos * MathHelper.cos( Minecraft.getMinecraft().thePlayer.rotationPitch / 180.0f * 3.1415927f) * f;
            final double motionY = -MathHelper.sin((Minecraft.getMinecraft().thePlayer.rotationPitch + 2.0f) / 180.0f * 3.1415927f) * f;
            for (int i = 0; i < 90; ++i) {
                final WorldClient theWorld = Minecraft.getMinecraft().theWorld;
                final EnumParticleTypes particleType = (i % 4 == 0) ? (EnumParticleTypes.CRIT_MAGIC)
                        : (new Random().nextBoolean() ? EnumParticleTypes.HEART : EnumParticleTypes.ENCHANTMENT_TABLE);
                final double xCoord = posX + motionX;
                final double yCoord = posY + motionY;
                final double zCoord = posZ + motionZ;
                final double motionX2 = Minecraft.getMinecraft().thePlayer.motionX;
                final double motionY2 = Minecraft.getMinecraft().thePlayer.motionY;
                theWorld.spawnParticle(particleType, xCoord, yCoord, zCoord, motionX2, motionY2, Minecraft.getMinecraft().thePlayer.motionZ
                );
            }
        }
    }


    
    public double mineplexSpeed;

	public boolean  reset, invert;
    @EventHandler
    public void onMove(EventMove e) {


        if(mode.getValue() == FlyMode.ShitCraft){
            final double baseMoveSpeed = MovementUtil.getBaseMoveSpeed();
            switch (this.stage) {
                case 0: {
                    this.moveSpeed = baseMoveSpeed;
                    if (!MovementUtil.isOnGround()) {
                        break;
                    }
                    mc.thePlayer.motionY = MovementUtil.getJumpHeight(0.41999998688697815);
                    break;
                }
                case 1: {
                    this.moveSpeed *= speed.getValue();
                    final double difference = 1 * (this.moveSpeed - baseMoveSpeed);
                    this.moveSpeed -= difference;
                    break;
                }
                case 2: {
                    final double lastDif = 1 * (this.lastDist - baseMoveSpeed);
                    this.moveSpeed = this.lastDist - lastDif;
                    break;
                }
                default: {
                    this.moveSpeed = this.lastDist - this.lastDist / 159.99;
                    break;
                }
            }

            MovementUtil.setSpeed(e, Math.max(baseMoveSpeed, this.moveSpeed));
            ++this.stage;
        }
        

        if(mode.getValue() == FlyMode.Verus){
            if(!this.damaged){
                e.setX(0.0);
                e.setZ(0.0);
            }
        }
        if(mode.getModeAsString().equalsIgnoreCase("SurvivalDub")) {
        	e.setY(mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? 0.42F : 0);
			MovementUtil.setSpeed(e, .36);
        }


    }

    private int getSlotWithPearl(){
        for(int i = 0; i < 9; ++i){
            ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof ItemEnderPearl)) continue;
            return i;
        }
        return -1;
    }


    boolean damaged;

    public static void damage(double damage) {
        Minecraft mc = Minecraft.getMinecraft();

        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

        double offset = 0.0625;
        //offset = 0.015625;
        if (mc.thePlayer != null && mc.getNetHandler() != null) {
            for (short i = 0; i <= ((3 + damage) / offset); i++) {
                mc.getNetHandler().addToSendQueue (new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + ((offset / 2) * 1), mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + ((offset / 2) * 2), mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
                //mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,
                //mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, (i == ((3 + damage) / offset))));
            }
        }

    }


    private final ArrayList<C03PacketPlayer> aac5C03List = new ArrayList<>();
    private void itsafuckingkidding(){
        float yaw = mc.thePlayer.rotationYaw;
        float pitch = mc.thePlayer.rotationPitch;
        for(C03PacketPlayer packet : this.aac5C03List){
            if(packet.isMoving()){
                mc.getNetHandler().addToSendQueue(packet);
                if(packet.getRotating()){
                    yaw = mc.thePlayer.rotationYaw;
                    pitch = mc.thePlayer.rotationPitch;
                }
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(packet.getPositionX(), 1.0E159D, packet.getPositionZ(), yaw, pitch, true));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(packet.getPositionX(), packet.getPositionY(), packet.getPositionZ(), yaw, pitch, true));
            }
        }
        this.aac5C03List.clear();
    }

    private void moveBlocksToHotbar() {
        boolean added = false;
        if (!isHotbarFull()) {
            for (int k = 0; k < Minecraft.getMinecraft().thePlayer.inventory.mainInventory.length; ++k) {
                if (k > 8 && !added) {
                    final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k];
                    if (itemStack != null && itemStack.getItem() instanceof ItemEnderPearl) {
                        shiftClick(k);
                        added = true;
                    }
                }
            }
        }
    }

    public static int airSlot() {
        for (int j = 0; j < 8; ++j) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[j] == null) {
                return j;
            }
        }
        Helper.sendMessage("Clear a hotbar slot.");
        return -10;
    }
    public void moveBlocksBackToInventory() {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) == null)
                continue;
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl) {
                shiftClick(i);
            }
        }
    }
    public static void shiftClick(int slot) {
    	Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
    }

    public boolean isHotbarFull() {
        int count = 0;
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[k];
            if (itemStack != null) {
                count++;
            }
        }
        return count == 8;
    }



    public enum FlyMode {
        Vanilla,   Verus,   WatchDuck        ,ShitCraft
    }

}