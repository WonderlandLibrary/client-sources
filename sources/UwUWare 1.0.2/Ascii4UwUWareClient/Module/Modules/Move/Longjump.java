package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventMove;
import Ascii4UwUWareClient.API.Events.World.EventPacketSend;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.Killaura;
import Ascii4UwUWareClient.Util.EventMotion;
import Ascii4UwUWareClient.Util.MoveUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
import Ascii4UwUWareClient.Util.player.MovementUtil;
import Ascii4UwUWareClient.Util.player.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import org.apache.commons.lang3.RandomUtils;

public class Longjump extends Module {
    public int ticks = 0;
    public int stage, groundTicks, slot;
    public double moveSpeed, air, motionY;
    public boolean done, back, collided, half, damaged, shoot;

    public TimerUtil timer = new TimerUtil();

    public static Mode<Enum> mode = new Mode("Mode", "Mode", LongjumpMode.values(), LongjumpMode.Mineplex);

    private final Option<Boolean> autoDisable = new Option<Boolean>("Auto Disable", "Auto Disable", true);
    private final Option<Boolean> bobbing = new Option<Boolean>("Bobbing", "Bobbing", false);
    private final Numbers<Double> redeTicks = new Numbers<Double>("Rede Ticks", "Rede Ticks", 21D, 1D, 25D, 0.01);
    private final Numbers<Double> redeY = new Numbers<Double>("Rede High", "Rede High", 0.77, 0D, 4D, 0.01);
    private final Numbers<Double> redeXZ = new Numbers<Double>("Rede Fast", "Rede Fast", 3D, 0D, 4D, 0.01);



    public Longjump() {
        super("Longjump", new String[]{"Lj", "Longjump"}, ModuleType.Move);
        addValues(mode,redeTicks, redeY, autoDisable, bobbing);
    }

    @EventHandler
    public void onPreUpdate(EventPreUpdate event) {
        setSuffix ( mode.getModeAsString () );

        if (bobbing.getValue ()) {
            Minecraft.thePlayer.cameraYaw = 0.105F;
        }

        if (autoDisable.getValue ()) {
            if (mode.getModeAsString ().equalsIgnoreCase ( "Redesky" )) {
                if (Minecraft.thePlayer.onGround && done) {
                    setEnabled ( false );
                }
            } else if (Minecraft.thePlayer.onGround) {
                setEnabled ( false );
            }
        }
        switch (mode.getModeAsString ()) {
           /* case "HypixelPaper":
                if (Minecraft.thePlayer.hurtTime > 0){
                    damaged = true;
                }

                if (damaged){
                    switch (stage){
                        case 0:
                            if (hypixelHigh.getValue()){
                                Minecraft.thePlayer.motionY = hypixelHighValue.getValue();
                            }else {
                                Minecraft.thePlayer.jump();
                            }
                            stage++;
                            done = true;
                            break;
                        case 1:
                            mc.timer.timerSpeed = Timer.getValue().floatValue();
                            MovementUtil.setMotion(hypixelSpeed.getValue());
                            break;
                    }
                }else {
                    MoveUtils.setMotion(0);
                    Minecraft.thePlayer.motionX = 0;
                    Minecraft.thePlayer.motionY = 0;
                    Minecraft.thePlayer.motionZ = 0;
                }

                if(autoShoot.getValue()){
                    int currentSlot = mc.thePlayer.inventory.currentItem;
                    int slot = this.getSlotWithBow();
                    if (this.getSlotWithBow() == -1) {
                        return;
                    }
                    mc.thePlayer.inventory.currentItem = slot;

                    if(!shoot && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                        event.setPitch(-90);
                        mc.gameSettings.keyBindUseItem.pressed = true;
                        if(timer.hasReached(155)) {
                            shoot = true;
                            timer.reset();
                        }
                    }
                    if(shoot) {
                        mc.gameSettings.keyBindUseItem.pressed = false;
                        mc.thePlayer.stopUsingItem();
                        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        mc.thePlayer.inventory.currentItem = this.slot;
                    }
                }


                break;*/
            case "Redesky":
                if (ticks < redeTicks.getValue ()) {
                    Minecraft.thePlayer.motionY *= redeY.getValue ();
                    Minecraft.thePlayer.motionX *= 0.999;
                    Minecraft.thePlayer.motionZ *= 0.999;

                    if (redeY.getValue () > 0) {
                        Minecraft.thePlayer.jump ();
                    }
                } else {
                    done = true;
                }
                Minecraft.thePlayer.motionY += 0.03;
                ticks++;
                break;


        }
    }
    @EventHandler
    public void onPacket(EventPacketSend event) {
        switch (mode.getModeAsString ()) {
            case "Matrix":
                if (event.getPacket () instanceof S12PacketEntityVelocity) {
                    event.setCancelled ( true );
                }
                break;
        }
    }


    @EventHandler
    public void EventMotionUpdate(EventMotion e){

        switch (mode.getModeAsString()){
            case "Matrix":
                if (mc.thePlayer.hurtTime > 0) {
                    if (mc.thePlayer.hurtTime == 9) {
                        MoveUtils.setMotion ( 1 );
                        mc.thePlayer.jump ();
                    } else {
                        mc.thePlayer.motionX *= 1.25;
                        mc.thePlayer.motionZ *= 1.25;
                    }
                }
                break;
            case"Verus":
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }
                MoveUtils.setMotion(0.7f);

                break;
                case "Mineplex":
                if (!Minecraft.thePlayer.onGround && air > 0.0F) {
                    air++;
                    if (Minecraft.thePlayer.isCollidedVertically)
                        air = 0.0F;
                    if (Minecraft.thePlayer.isCollidedHorizontally && !collided)
                        collided = !collided;
                    double speed = half ? (0.5D - (air / 100.0F)) : (0.658D - (air / 100.0F));
                    MoveUtils.setMoveSpeed(e, 0);
                    motionY -= 0.04200000000001D;
                    if (air > 24.0F)
                        motionY -= 0.03D;
                    if (air == 12.0F)
                        motionY = -0.007D;
                    if (speed >= 1.8)
                        speed = 1;
                    if (collided)
                        speed += 0.2873D;
                    EventMove.setY(Minecraft.thePlayer.motionY = motionY);
                    MoveUtils.setMoveSpeed(e, speed *= 0.98);
                } else if (air > 0.0F) {
                    air = 0.0F;
                }
                if (MovementUtil.isOnGround() && Minecraft.thePlayer.isCollidedVertically) {
                    double groundspeed = 2.8D;
                    collided = Minecraft.thePlayer.isCollidedHorizontally;
                    groundTicks++;
                    Minecraft.thePlayer.motionX *= groundspeed;
                    Minecraft.thePlayer.motionZ *= groundspeed;
                    half = (Minecraft.thePlayer.posY != (int) Minecraft.thePlayer.posY);
                    EventMove.setY(Minecraft.thePlayer.motionY = 0.4399999D);
                    air = 1.0F;
                    motionY = Minecraft.thePlayer.motionY;
                }
                break;
                case "Redesky":

                break;
        }
    }


    @Override
    public void onEnable() {
        this.moveSpeed = MovementUtil.getBaseMoveSpeed() * (Minecraft.thePlayer.isPotionActive( Potion.moveSpeed) ? 1.0D : 1.34D);
        stage = 0;
        timer.reset();
        done = false;
        back = false;
        damaged = false;
        shoot = false;
        ticks = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        stage = 0;
        this.groundTicks = 0;
        this.stage = 0;
        this.motionY = 0.0D;
        damaged = false;
        mc.timer.timerSpeed = 1f;
        Minecraft.thePlayer.motionX = 0;
        if (!mode.getModeAsString().equalsIgnoreCase("Redesky")) {
            Minecraft.thePlayer.motionY = 0;
            Minecraft.thePlayer.motionX = 0;
            Minecraft.thePlayer.motionZ = 0;
        }
        Minecraft.thePlayer.speedInAir = 0.02f;
        super.onDisable();
    }

    public static int airSlot() {
        for (int j = 0; j < 8; ++j) {
            if (Minecraft.thePlayer.inventory.mainInventory[j] == null) {
                return j;
            }
        }
        return -10;
    }


    public void setMoveSpeed(EventMove event, double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        TargetStrafe targetStrafe = (TargetStrafe) Client.instance.getModuleManager().getModuleByClass(TargetStrafe.class);
        double moveForward = Minecraft.thePlayer.movementInput.getForward();
        double moveStrafe = TargetStrafe.canStrafe() ? targetStrafe.strafeDirection : Minecraft.thePlayer.movementInput.getStrafe();
        if (TargetStrafe.canStrafe()) {
            if (Minecraft.thePlayer.getDistanceToEntity( Killaura.target) <= 3) moveForward = 0;
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

    public enum LongjumpMode{
        HypixelPaper, Mineplex,Redesky, Matrix , Verus
    }
    public static float randomFloatValue() {
        return RandomUtils.nextFloat((float) 0.00000296219, (float) 0.00000913303);
    }

    private int getSlotWithBow() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[i];
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBow)) continue;
            return i;
        }
        return -1;
    }

}

        /*case "HypixelBow" :
        for (int i = 0; i < 9; i++) {
            if (Minecraft.thePlayer.inventory.getStackInSlot(i) == null)
                continue;
            if (Minecraft.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBow) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange ( Minecraft.thePlayer.inventory.currentItem = i));
            }
        }
        if (Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.jump();
        }

        if (MoveUtils.isMoving() && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            if (Minecraft.thePlayer.motionY < 0) {
                mc.timer.timerSpeed = 0.1f;
            } else if (mc.timer.timerSpeed == 0.1f) {
                mc.timer.timerSpeed = 1f;
            }
            event.setPitch ( -60 );
            if (Minecraft.thePlayer.hurtTime == Minecraft.thePlayer.maxHurtTime && Minecraft.thePlayer.maxHurtTime > 0) {
                if (Minecraft.thePlayer.hurtTime == 1) {
                    Minecraft.thePlayer.motionY = 0.42;
                }
            }
            ++this.ticks;
            Packet C07 = new C07PacketPlayerDigging ( C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN );
            Packet C08 = new C08PacketPlayerBlockPlacement ( Minecraft.thePlayer.inventory.getCurrentItem () );
            if (ticks >= 4) {
                Minecraft.thePlayer.sendQueue.addToSendQueue ( C07 );
                ticks = 0;
            } else if (ticks == 1) {
                Minecraft.thePlayer.sendQueue.addToSendQueue ( C08 );

            }

        }

    }*/



