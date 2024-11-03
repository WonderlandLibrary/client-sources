package dev.star.module.impl.movement;

import dev.star.event.impl.network.PacketReceiveEvent;
import dev.star.event.impl.network.PacketSendEvent;
import dev.star.event.impl.player.MotionEvent;
import dev.star.event.impl.player.MoveEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.misc.MathUtils;
import dev.star.utils.player.InventoryUtils;
import dev.star.utils.player.MovementUtils;
import dev.star.utils.server.PacketUtils;
import dev.star.utils.time.TimerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

public final class LongJump extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla", "Vanilla", "NCP", "Verus", "Fireball");
    private final NumberSetting damageSpeed = new NumberSetting("Damage Speed", 1, 20, 1, 0.5);
    private final BooleanSetting spoofY = new BooleanSetting("Spoof Y", false);
    private int movementTicks = 0;
    private double speed;
    private float pitch;
    private int prevSlot, ticks = 0;
    private boolean damagedBow;
    private final TimerUtil jumpTimer = new TimerUtil();
    private boolean damaged;
    private double x;
    private double y;
    private double z;
    private final List<Packet> packets = new ArrayList<>();
    private int stage;
    private boolean start =  false;
    private double oldY;
    private boolean thrown, shouldDo ;

    private int lastSlot = -1;
    private int tickss = -1;
    private boolean setSpeed;
    public static boolean stopModules;
    private boolean sentPlace;
    private int initTicks, offGroundTicks;

    @Override
    public void onMotionEvent(MotionEvent event) {
        setSuffix(mode.getMode());
        if (spoofY.isEnabled()) mc.thePlayer.posY = y;
        switch (mode.getMode()) {
            case "Vanilla":
                if (MovementUtils.isMoving() && mc.thePlayer.onGround) {
                    MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 2);
                    mc.thePlayer.jump();
                }
                break;
            case "NCP":
                if (MovementUtils.isMoving()) {
                    if (MovementUtils.isOnGround(0.00023)) {
                        mc.thePlayer.motionY = 0.41;
                    }

                    switch (movementTicks) {
                        case 1:
                            speed = MovementUtils.getBaseMoveSpeed();
                            break;
                        case 2:
                            speed = MovementUtils.getBaseMoveSpeed() + (0.132535 * Math.random());
                            break;
                        case 3:
                            speed = MovementUtils.getBaseMoveSpeed() / 2;
                            break;
                    }
                    MovementUtils.setSpeed(Math.max(speed, MovementUtils.getBaseMoveSpeed()));
                    movementTicks++;
                }
                break;
            case "Fireball":
                {
                    if (mc.thePlayer == null || mc.theWorld == null) {
                        return;
                    }
                    if (initTicks == 0) {
                        event.setYaw(mc.thePlayer.rotationYaw - 180);
                        event.setPitch(51); //ugh FUnny number
                        int fireballSlot = InventoryUtils.findItem(Items.fire_charge);
                        if (fireballSlot != -1 && fireballSlot != mc.thePlayer.inventory.currentItem) {
                            lastSlot = mc.thePlayer.inventory.currentItem;
                            mc.thePlayer.inventory.currentItem = fireballSlot;
                        }
                    }
                    if (mc.thePlayer.onGround && jumpTimer.hasTimeElapsed(1000)) {
                        toggle();
                    }
                    if (initTicks == 1) {
                        if (!sentPlace) {
                            PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
                            sentPlace = true;
                        }
                    } else if (initTicks == 2) {
                        if (lastSlot != -1) {
                            mc.thePlayer.inventory.currentItem = lastSlot;
                            lastSlot = -1;
                        }
                    }
                    if (tickss > 1) {
                        toggle();
                    }
                    if (setSpeed) {
                        stopModules = true;
                   //     MovementUtils.setSpeed1(1.43f);
                        MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 6.8);

                        tickss++;
                    }
                    if(mc.thePlayer.ticksSinceExplosionVelo >= 70 && mc.thePlayer.ticksSincePlayerVelocity > 1) {
                        mc.thePlayer.motionY += 0.028;
                    }
                    if (initTicks < 3) {
                        initTicks++;
                    }


                    if (setSpeed) {
                        if (tickss > 1) {
                            stopModules = setSpeed = false;
                            tickss = 0;
                            return;
                        }
                        stopModules = true;
                        tickss++;
                        //MovementUtils.setSpeed1(1.423f);
                        MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 6.8);

                        //     mc.thePlayer.motionY += 0.05f;
                    }

                }
                break;
            case "Verus":
                int bow = getBowSlot();

                if (damagedBow) {
                    if (mc.thePlayer.onGround && jumpTimer.hasTimeElapsed(1000)) {
                        toggle();
                    }
                    if (mc.thePlayer.onGround && mc.thePlayer.motionY > 0.003) {
                        mc.thePlayer.motionY = 10;
                    } else {
                        MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 7.2);
                    }
                }

                if (!damagedBow) {
                    switch (ticks) {
                        case 0:
                            if (prevSlot != bow) {
                                PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(bow));
                            }
                            PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bow)));
                            break;
                        case 3:
                            event.setPitch(-89.93F);
                            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), event.getYaw(), pitch, event.isOnGround()));
                            PacketUtils.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
                            if (prevSlot != bow) {
                                PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(prevSlot));
                            }
                            break;
                    }
                    if (mc.thePlayer.hurtTime != 0) {
                        damagedBow = true;
                    }
                }
        }
        if (!mode.is("Watchdog"))
            ticks++;
    }

    @Override
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (event.isCancelled()) return;

       if (mode.is("Fireball"))
       {
           if (mc.thePlayer == null || mc.theWorld == null) {
               return;
           }

           Packet<?> packet = event.getPacket();
           if (packet instanceof S12PacketEntityVelocity) {
               if (((S12PacketEntityVelocity) event.getPacket()).getEntityID() != mc.thePlayer.getEntityId()) {
                   return;
               }
               if (thrown) {
                   tickss = 0;
                   setSpeed = true;
                   thrown = false;
                   stopModules = true;
               }
           }
       }
    }

    @Override
    public void onPacketSendEvent(PacketSendEvent event) {
        if (event.isCancelled()) return;

        if (mode.is("Fireball")) {
            Packet<?> packet = event.getPacket();
            if (packet instanceof C08PacketPlayerBlockPlacement
                    && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack() != null
                    && ((C08PacketPlayerBlockPlacement) event.getPacket()).getStack().getItem() instanceof ItemFireball) {
                thrown = true;
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }
            }
        }
    }

    @Override
    public void onMoveEvent(MoveEvent event) {
        if (!damagedBow && mode.is("Verus")) {
            event.setX(0);
            event.setZ(0);
        }

        if (mode.is("Fireball"))
        {
            if(mc.thePlayer.offGroundTicks == 10) {
                oldY = mc.thePlayer.motionY;
            }

            if(mc.thePlayer.offGroundTicks > 10 && mc.thePlayer.offGroundTicks <= 10) {
//                    if(offGroundTicks % 5 == 0) {
//                        mc.thePlayer.motionY += 0.1;
//                    }
//                    mc.timer.timerSpeed = 0.5f;
            }
        }
    }

    public int getBowSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && is.getItem() == Items.bow) {
                return i;
            }
        }
        return -1;
    }

    public int getFireBallSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
            if (is != null && is.getItem() == Items.fire_charge) {
                return i;
            }
        }
        return -1;
    }


    public int getItemCount(Item item) {
        int count = 0;
        for (int i = 9; i < 45; i++) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() == item) {
                count += stack.stackSize;
            }
        }
        return count;
    }

    @Override
    public void onEnable() {
        if ((mode.is("Verus"))) {
            prevSlot = mc.thePlayer.inventory.currentItem;
            pitch = MathUtils.getRandomFloat(-89.2F, -89.99F);
            if (getBowSlot() == -1) {
                this.toggleSilent();
                return;
            } else if (getItemCount(Items.arrow) == 0) {
                this.toggleSilent();
                return;
            }
        }
        if (mode.is("Fireball"))
        {
            if (getFireBallSlot() == -1) {
                this.toggleSilent();
                return;
            } else if (getItemCount(Items.fire_charge) == 0) {
                this.toggleSilent();
                return;
            }
        }
        ticks = 0;
        damagedBow = false;
        damaged = false;
        jumpTimer.reset();
        x = mc.thePlayer.posX;
        y = mc.thePlayer.posY;
        z = mc.thePlayer.posZ;
        packets.clear();
        stage = 0;
        start = false;
        speed = 1.4f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        start = false;
        mc.timer.timerSpeed = 1;
        packets.forEach(PacketUtils::sendPacketNoEvent);
        packets.clear();
        if (mode.is("Fireball"))
        {
            if (lastSlot != -1) {
                mc.thePlayer.inventory.currentItem = lastSlot;
            }
            tickss = lastSlot = -1;
            setSpeed = stopModules = sentPlace = false;
            initTicks = 0;
        }
        super.onDisable();


    }

    public LongJump() {
        super("LongJump", Category.MOVEMENT, "jump further");
        this.addSettings(mode,  damageSpeed, spoofY);
    }

}
