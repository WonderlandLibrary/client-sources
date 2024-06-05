/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventMove;
import drunkclient.beta.API.events.world.EventPacketReceive;
import drunkclient.beta.API.events.world.EventPacketSend;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.Client;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.helper.Helper;
import drunkclient.beta.UTILS.world.MovementUtil;
import drunkclient.beta.UTILS.world.PacketUtil;
import drunkclient.beta.UTILS.world.Timer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class Fly
extends Module {
    private static int SurvivalDubBow = 0;
    public static int ticks = 0;
    public Mode mode = new Mode("Mode", "Mode", (Enum[])FlyMode.values(), (Enum)FlyMode.Vanilla);
    public final Option<Boolean> lagcheck = new Option<Boolean>("LagCheck", "LagCheck", true);
    public final Option<Boolean> dragonvalue = new Option<Boolean>("Dragon", "Dragon", false);
    public final Option<Boolean> par = new Option<Boolean>("Particle", "Particle", false);
    public final Option<Boolean> aac = new Option<Boolean>("AACV5 motion", "AACV5 motion", false);
    public final Option<Boolean> bob = new Option<Boolean>("Bobbing", "Bobbing", true);
    public final Option<Boolean> verusFast = new Option<Boolean>("Verus Fast", "Verus Fast", false);
    public final Numbers<Double> VerusTick = new Numbers<Double>("Verus Tick", "Verus Tick", 20.0, 1.0, 100.0, 0.1);
    public final Numbers<Double> speed = new Numbers<Double>("Speed", "Speed", 1.0, 0.1, 5.0, 0.1);
    private final Timer time = new Timer();
    private final Timer time2 = new Timer();
    private final Timer aac5timer = new Timer();
    private final Timer kickTimer = new Timer();
    private double movementSpeed;
    private EntityDragon dragon;
    int counter;
    int level;
    int sexVerus;
    private double flyHeight;
    boolean flyable;
    private boolean throwP;
    private boolean dragoncrea;
    private boolean countPerl;
    private boolean bow;
    private boolean hypixelDamaged;
    private boolean b2;
    private boolean done;
    private boolean MinemenDamaged;
    private boolean cantrowpearl;
    private EntityOtherPlayerMP clonedPlayer = null;
    private int packetCounter;
    private int boostTicks;
    private final Blocks blocks = new Blocks();
    private final Timer deactivationDelay = new Timer();
    private final Timer verusTimer = new Timer();
    double moveSpeed;
    double lastDist;
    private float minemenlookPitch;
    private float minemenlookYaw;
    private int enableTick;
    boolean canFly = false;
    int damagedTicks;
    double oldX;
    double oldY;
    private boolean aac5nextFlag = false;
    double oldZ;
    private NetHandlerPlayClient netHandler;
    float oldYaw;
    float oldPitch;
    public boolean back;
    int stage;
    private int SurvivalDubGo;
    public double mineplexSpeed;
    public boolean reset;
    public boolean invert;
    boolean damaged;
    private final ArrayList<C03PacketPlayer> aac5C03List = new ArrayList();

    public Fly() {
        super("Fly", new String[]{"fly", "angel"}, Type.MOVE, "Allow's player to fly");
        this.setColor(new Color(158, 114, 243).getRGB());
        this.addValues(this.mode, this.lagcheck, this.dragonvalue, this.par, this.bob, this.aac, this.verusFast, this.speed, this.VerusTick);
    }

    public static float getMaxFallDist() {
        Minecraft.getMinecraft();
        PotionEffect potioneffect = Minecraft.thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        Minecraft.getMinecraft();
        return Minecraft.thePlayer.getMaxFallHeight() + f;
    }

    @EventHandler
    public void onPacketSend(EventPacketSend event) {
        switch (this.mode.getModeAsString()) {
            case "Verus": {
                if ((double)Minecraft.thePlayer.ticksExisted % 1.5 == 0.0) {
                    Fly.mc.timer.timerSpeed = 1.0f;
                }
                if (Minecraft.thePlayer.ticksExisted % 20 != 0) return;
                Minecraft.thePlayer.jump();
                return;
            }
        }
    }

    @EventHandler
    public void nig(EventPacketSend ep) {
        switch (this.mode.getModeAsString()) {
            case "Verus": {
                if (!this.damaged) return;
                if (ep.getPacket() instanceof C0FPacketConfirmTransaction) {
                    C0FPacketConfirmTransaction c0f = (C0FPacketConfirmTransaction)ep.getPacket();
                    if ((double)c0f.windowId < 5.0) {
                        ep.setCancelled(true);
                        PacketUtil.sendPacket(new C0FPacketConfirmTransaction(ThreadLocalRandom.current().nextInt(100, 400), 500, true));
                    }
                }
                if (!(ep.getPacket() instanceof C03PacketPlayer)) return;
                C03PacketPlayer packetPlayer = (C03PacketPlayer)ep.getPacket();
                packetPlayer.onGround = true;
                if (Minecraft.thePlayer.ticksExisted <= 15) return;
                ep.setCancelled(Minecraft.thePlayer.ticksExisted % 3 == 0);
                return;
            }
        }
    }

    @EventHandler
    private void onPacket(EventPacketReceive ep) {
        if (((Boolean)this.lagcheck.getValue()).booleanValue() && ep.getPacket() instanceof S08PacketPlayerPosLook) {
            ++this.packetCounter;
            S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook)ep.getPacket();
            Minecraft.getMinecraft();
            pac.yaw = Minecraft.thePlayer.rotationYaw;
            Minecraft.getMinecraft();
            pac.pitch = Minecraft.thePlayer.rotationPitch;
            this.level = -5;
            if (Client.instance.getModuleManager().getModuleByClass(Fly.class).isEnabled()) {
                Client.instance.getModuleManager().getModuleByClass(Fly.class).setEnabled(false);
            }
        }
        if (!this.mode.getModeAsString().equalsIgnoreCase("Redesky")) return;
        if (!(ep instanceof EventPacketReceive)) return;
        if (!(ep.getPacket() instanceof C03PacketPlayer)) return;
        Minecraft.thePlayer.posY = Minecraft.thePlayer.lastTickPosY;
        Minecraft.thePlayer.posX = Minecraft.thePlayer.lastTickPosX;
        Minecraft.thePlayer.posZ = Minecraft.thePlayer.lastTickPosZ;
    }

    @Override
    public void onEnable() {
        float forward = MovementInput.moveForward;
        ticks = 0;
        float strafe = MovementInput.moveStrafe;
        this.countPerl = false;
        this.hypixelDamaged = false;
        this.level = 1;
        this.dragoncrea = false;
        this.moveSpeed = 0.0;
        this.stage = 0;
        this.sexVerus = 0;
        ticks = 0;
        this.b2 = true;
        this.damagedTicks = 0;
        this.lastDist = 0.0;
        this.canFly = false;
        this.kickTimer.reset();
        this.verusTimer.reset();
        this.time2.reset();
        if (!this.mode.getModeAsString().equalsIgnoreCase("Verus")) return;
        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 3.001, Minecraft.thePlayer.posZ, false));
        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
        PacketUtil.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
    }

    @Override
    public void onDisable() {
        if (((Boolean)this.dragonvalue.getValue()).booleanValue()) {
            Minecraft.getMinecraft().theWorld.removeEntity(this.dragon);
        }
        SurvivalDubBow = 0;
        this.enableTick = 0;
        this.SurvivalDubGo = 0;
        this.flyable = true;
        Minecraft.getMinecraft();
        double posX = Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        double posY = Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        double posZ = Minecraft.thePlayer.posZ;
        Fly.mc.timer.timerSpeed = 1.0f;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.motionX = 0.0;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.motionY = 0.0;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.motionZ = 0.0;
        Minecraft.getMinecraft();
        Minecraft.thePlayer.capabilities.isFlying = false;
        this.level = 1;
        this.moveSpeed = 0.1;
        this.countPerl = false;
        this.hypixelDamaged = false;
        this.verusTimer.reset();
        this.damaged = false;
        this.b2 = false;
        this.sexVerus = 0;
        this.lastDist = 0.0;
    }

    public static boolean isOnGround(double height) {
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        if (worldClient.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty()) return false;
        return true;
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        double motionY;
        double motionZ;
        float cos;
        double motionX;
        float n2;
        float f;
        double posZ;
        double posZ2;
        double posY;
        double posX;
        double posX4;
        this.setSuffix(this.mode.getModeAsString());
        if (this.mode.getValue() == FlyMode.Verus) {
            double x = Minecraft.thePlayer.posX;
            double y = Minecraft.thePlayer.posY;
            double z = Minecraft.thePlayer.posZ;
            if (Minecraft.thePlayer.hurtTime > 0 && !this.damaged) {
                this.damagedTicks = 0;
                this.damaged = true;
                Fly.mc.timer.timerSpeed = 0.5f;
                Minecraft.thePlayer.motionY = -0.25;
            }
            if (!MovementUtil.isMoving() && !this.damaged) {
                MovementUtil.setMotion(0.0);
                Minecraft.thePlayer.ticksExisted = 0;
            }
            if (this.damaged) {
                if (this.damagedTicks == 0) {
                    Minecraft.thePlayer.jump();
                } else {
                    Minecraft.thePlayer.motionY = 0.0;
                    if (Minecraft.thePlayer.hurtTime > 0) {
                        MovementUtil.setMotion(1.8);
                    } else {
                        MovementUtil.setMotion(3.0);
                    }
                }
                e.setOnground(true);
                ++this.damagedTicks;
            }
            ++ticks;
        }
        if (this.mode.getValue() == FlyMode.WatchDuck) {
            Minecraft.thePlayer.motionY = 0.0;
            MovementUtil.setMotion(0.4f);
        }
        if (this.mode.getValue() == FlyMode.Vanilla) {
            Minecraft.thePlayer.capabilities.isFlying = false;
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionY = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
            if (Fly.mc.gameSettings.keyBindJump.isKeyDown()) {
                Minecraft.thePlayer.motionY += (Double)this.speed.getValue() * 0.5;
            }
            if (Fly.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Minecraft.thePlayer.motionY -= (Double)this.speed.getValue() * 0.5;
            }
            MovementUtil.setMotion((Double)this.speed.getValue());
        }
        if (((Boolean)this.dragonvalue.getValue()).booleanValue() && !this.dragoncrea) {
            this.dragoncrea = true;
            this.dragon = new EntityDragon(Minecraft.getMinecraft().theWorld);
            Minecraft.getMinecraft().theWorld.addEntityToWorld(666, this.dragon);
            Minecraft.getMinecraft();
            Minecraft.thePlayer.ridingEntity = this.dragon;
        } else if (((Boolean)this.dragonvalue.getValue()).booleanValue()) {
            Minecraft.getMinecraft();
            posX4 = Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            posX = posX4 - (double)(MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
            Minecraft.getMinecraft();
            posY = Minecraft.thePlayer.posY;
            Minecraft.getMinecraft();
            posZ2 = Minecraft.thePlayer.posZ;
            Minecraft.getMinecraft();
            posZ = posZ2 - (double)(MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
            f = 0.4f;
            Minecraft.getMinecraft();
            n2 = -MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI);
            Minecraft.getMinecraft();
            motionX = n2 * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0f * (float)Math.PI) * 0.4f;
            Minecraft.getMinecraft();
            cos = MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI);
            Minecraft.getMinecraft();
            motionZ = cos * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0f * (float)Math.PI) * 0.4f;
            Minecraft.getMinecraft();
            motionY = -MathHelper.sin((Minecraft.thePlayer.rotationPitch + 2.0f) / 180.0f * (float)Math.PI) * 0.4f;
            double xCoord = posX + motionX;
            double yCoord = posY + motionY;
            double zCoord = posZ + motionZ;
            Minecraft.getMinecraft();
            this.dragon.rotationPitch = Minecraft.thePlayer.rotationPitch;
            Minecraft.getMinecraft();
            this.dragon.rotationYaw = Minecraft.thePlayer.rotationYawHead - 180.0f;
            Minecraft.getMinecraft();
            this.dragon.setRotationYawHead(Minecraft.thePlayer.rotationYawHead);
            Minecraft.getMinecraft();
            this.dragon.setPosition(xCoord, Minecraft.thePlayer.posY - 2.0, zCoord);
        }
        if ((Boolean)this.par.getValue() == false) return;
        Minecraft.getMinecraft();
        posX4 = Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        posX = posX4 - (double)(MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        Minecraft.getMinecraft();
        posY = Minecraft.thePlayer.posY;
        Minecraft.getMinecraft();
        posZ2 = Minecraft.thePlayer.posZ;
        Minecraft.getMinecraft();
        posZ = posZ2 - (double)(MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        f = 0.4f;
        Minecraft.getMinecraft();
        n2 = -MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI);
        Minecraft.getMinecraft();
        motionX = n2 * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0f * (float)Math.PI) * 0.4f;
        Minecraft.getMinecraft();
        cos = MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0f * (float)Math.PI);
        Minecraft.getMinecraft();
        motionZ = cos * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0f * (float)Math.PI) * 0.4f;
        Minecraft.getMinecraft();
        motionY = -MathHelper.sin((Minecraft.thePlayer.rotationPitch + 2.0f) / 180.0f * (float)Math.PI) * 0.4f;
        int i = 0;
        while (i < 90) {
            WorldClient theWorld = Minecraft.getMinecraft().theWorld;
            EnumParticleTypes particleType = i % 4 == 0 ? EnumParticleTypes.CRIT_MAGIC : (new Random().nextBoolean() ? EnumParticleTypes.HEART : EnumParticleTypes.ENCHANTMENT_TABLE);
            double xCoord = posX + motionX;
            double yCoord = posY + motionY;
            double zCoord = posZ + motionZ;
            Minecraft.getMinecraft();
            double motionX2 = Minecraft.thePlayer.motionX;
            Minecraft.getMinecraft();
            double motionY2 = Minecraft.thePlayer.motionY;
            Minecraft.getMinecraft();
            theWorld.spawnParticle(particleType, xCoord, yCoord, zCoord, motionX2, motionY2, Minecraft.thePlayer.motionZ, new int[0]);
            ++i;
        }
    }

    @EventHandler
    public void onMove(EventMove e) {
        if (this.mode.getValue() == FlyMode.ShitCraft) {
            double baseMoveSpeed = MovementUtil.getBaseMoveSpeed();
            switch (this.stage) {
                case 0: {
                    this.moveSpeed = baseMoveSpeed;
                    if (!MovementUtil.isOnGround()) break;
                }
                Minecraft.thePlayer.motionY = MovementUtil.getJumpHeight(0.42f);
                break;
                case 1: {
                    this.moveSpeed *= ((Double)this.speed.getValue()).doubleValue();
                    double difference = 1.0 * (this.moveSpeed - baseMoveSpeed);
                    this.moveSpeed -= difference;
                    break;
                }
                case 2: {
                    double lastDif = 1.0 * (this.lastDist - baseMoveSpeed);
                    this.moveSpeed = this.lastDist - lastDif;
                    break;
                }
                default: {
                    this.moveSpeed = this.lastDist - this.lastDist / 159.99;
                }
            }
            MovementUtil.setSpeed(e, Math.max(baseMoveSpeed, this.moveSpeed));
            ++this.stage;
        }
        if (this.mode.getValue() == FlyMode.Verus && !this.damaged) {
            e.setX(0.0);
            e.setZ(0.0);
        }
        if (!this.mode.getModeAsString().equalsIgnoreCase("SurvivalDub")) return;
        Minecraft.thePlayer.motionY = Minecraft.thePlayer.movementInput.jump ? (double)0.42f : 0.0;
        EventMove.setY(Minecraft.thePlayer.motionY);
        MovementUtil.setSpeed(e, 0.36);
    }

    private int getSlotWithPearl() {
        int i = 0;
        while (i < 9) {
            Minecraft.getMinecraft();
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[i];
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemEnderPearl) return i;
            }
            ++i;
        }
        return -1;
    }

    public static void damage(double damage) {
        Minecraft mc = Minecraft.getMinecraft();
        if (damage > (double)MathHelper.floor_double(Minecraft.thePlayer.getMaxHealth())) {
            damage = MathHelper.floor_double(Minecraft.thePlayer.getMaxHealth());
        }
        double offset = 0.0625;
        if (Minecraft.thePlayer == null) return;
        if (mc.getNetHandler() == null) return;
        int i = 0;
        while ((double)i <= (3.0 + damage) / offset) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + offset / 2.0 * 1.0, Minecraft.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + offset / 2.0 * 2.0, Minecraft.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, (double)i == (3.0 + damage) / offset));
            i = (short)(i + 1);
        }
    }

    private void itsafuckingkidding() {
        float yaw = Minecraft.thePlayer.rotationYaw;
        float pitch = Minecraft.thePlayer.rotationPitch;
        Iterator<C03PacketPlayer> iterator = this.aac5C03List.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.aac5C03List.clear();
                return;
            }
            C03PacketPlayer packet = iterator.next();
            if (!packet.isMoving()) continue;
            mc.getNetHandler().addToSendQueue(packet);
            if (packet.getRotating()) {
                yaw = Minecraft.thePlayer.rotationYaw;
                pitch = Minecraft.thePlayer.rotationPitch;
            }
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(packet.getPositionX(), 1.0E159, packet.getPositionZ(), yaw, pitch, true));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(packet.getPositionX(), packet.getPositionY(), packet.getPositionZ(), yaw, pitch, true));
        }
    }

    private void moveBlocksToHotbar() {
        boolean added = false;
        if (this.isHotbarFull()) return;
        int k = 0;
        while (true) {
            Minecraft.getMinecraft();
            if (k >= Minecraft.thePlayer.inventory.mainInventory.length) return;
            if (k > 8 && !added) {
                Minecraft.getMinecraft();
                ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
                if (itemStack != null && itemStack.getItem() instanceof ItemEnderPearl) {
                    Fly.shiftClick(k);
                    added = true;
                }
            }
            ++k;
        }
    }

    public static int airSlot() {
        int j = 0;
        while (true) {
            if (j >= 8) {
                Helper.sendMessage("Clear a hotbar slot.");
                return -10;
            }
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.inventory.mainInventory[j] == null) {
                return j;
            }
            ++j;
        }
    }

    public void moveBlocksBackToInventory() {
        int i = 0;
        while (i < 9) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.inventory.getStackInSlot(i) != null) {
                Minecraft.getMinecraft();
                if (Minecraft.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemEnderPearl) {
                    Fly.shiftClick(i);
                }
            }
            ++i;
        }
    }

    public static void shiftClick(int slot) {
        PlayerControllerMP playerControllerMP = Minecraft.getMinecraft().playerController;
        Minecraft.getMinecraft();
        int n = Minecraft.thePlayer.inventoryContainer.windowId;
        Minecraft.getMinecraft();
        playerControllerMP.windowClick(n, slot, 0, 1, Minecraft.thePlayer);
    }

    public boolean isHotbarFull() {
        int count = 0;
        for (int k = 0; k < 9; ++k) {
            Minecraft.getMinecraft();
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
            if (itemStack == null) continue;
            ++count;
        }
        if (count != 8) return false;
        return true;
    }

    public static enum FlyMode {
        Vanilla,
        Verus,
        Dev,
        WatchDuck,
        ShitCraft;

    }
}

