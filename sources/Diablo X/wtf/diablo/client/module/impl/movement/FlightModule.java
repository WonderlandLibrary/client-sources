package wtf.diablo.client.module.impl.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.client.TickEvent;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.event.impl.player.EventBlockBB;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.exploit.disabler.DisablerModule;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.collision.CollisionUtils;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;
import wtf.diablo.client.util.mc.player.damage.Damage;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@ModuleMetaData(
        name = "Flight",
        description = "Enables the ability to fly",
        category = ModuleCategoryEnum.MOVEMENT,
        key = Keyboard.KEY_F
)
public final class FlightModule extends AbstractModule {
    private final ModeSetting<FlightMode> mode = new ModeSetting<>("Mode", FlightMode.CREATIVE);
    private final BooleanSetting showDistance = new BooleanSetting("Show Distance", true);
    private final NumberSetting<Float> timer = new NumberSetting<>("Timer", 1.0F, 0.1F, 10.0F, 0.05F);
    private final NumberSetting<Float> yspeed = new NumberSetting<>("YSpeed", 0.42F, 0.1F, 1.0F, 0.05F);
    private final NumberSetting<Double> xzspeed = new NumberSetting<>("XZSpeed", 0.1D, 0.1D, 1.0D, 0.05D);
    private final BooleanSetting verusfast = new BooleanSetting("Verus Fast", true);

    private int stage, phaseTicks, matrixxstart;

    private final Queue<Packet> packetQueue = new ConcurrentLinkedDeque<>();

    private double[] spoofp = new double[]{0,0,0};

    private Vec3 velocityVector;

    private double mx, my, mz, lastX, lastY, lastZ, lastTimer;

    private boolean spoof, phaseTrigger, matrixTrigger, matrixFreeze, matrixBoost, matrixxdoitagain, grounded, veloWait;

    private Vec3 lastServerPosition;

    public FlightModule() {
        this.registerSettings(this.mode, showDistance, timer);
    }

    @Override
    protected void onEnable() {
        super.onEnable();

        switch (mode.getValue()) {
            case KARHU:
            case UPDATED_NCP:
                this.phaseTrigger = false;
                this.phaseTicks = 0;
                break;
            case OLD_SPARKY:
                this.lastY = mc.thePlayer.posY;
                break;
            case KARHU_JUMP:
                this.phaseTrigger = false;
                this.stage = 0;
                break;
            case VERUS_DAMAGE:
                MovementUtil.damage(Damage.VERUS);
                packetQueue.clear();
                break;
            case BUZZ_BLINK:
                my = mc.thePlayer.posY;
                if(mc.thePlayer.onGround) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ, false));
                    //Less Packets extends the fly
                    phaseTicks = Diablo.getInstance().getModuleRepository().getModuleInstance(DisablerModule.class).isEnabled() ? 200 : 10;
                }
                lastTimer = 3.5;
                break;

        }
    }


    @Override
    protected void onDisable() {
        super.onDisable();

        if (this.mode.getValue() == FlightMode.CREATIVE) {
            mc.thePlayer.capabilities.isFlying = false;
        } else if (this.mode.getValue() == FlightMode.INVADEDLANDS) {
            mc.getTimer().timerSpeed = 1f;
        }
        mc.getTimer().timerSpeed = 1; phaseTrigger = false;

        switch (mode.getValue()) {
            case OLD_VULCAN:
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                break;
        }

        packetQueue.forEach(mc.thePlayer.sendQueue::addToSendQueue);
        packetQueue.clear();

        this.distance = 0;
    }


    @EventHandler
    private final Listener<EventBlockBB> eventBlockBBListener = e -> {
        if(mc.thePlayer.movementInput == null)
            return;

        switch (mode.getValue()) {
            case KARHU:
                if(e.getBlock() instanceof BlockAir && !mc.thePlayer.movementInput.sneak)
                    if((!mc.thePlayer.onGround && mc.thePlayer.fallDistance > 0.1) || mc.thePlayer.onGround) {
                        e.setAabb(AxisAlignedBB.fromBounds(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.posX + 1.0D, mc.thePlayer.posY, mc.thePlayer.posZ + 1.0D));
                    }
                break;
            case KARHU_JUMP:
                if(!this.phaseTrigger && this.stage <= 2)
                    break;

                if(mc.thePlayer.fallDistance > 1 || this.stage > 2) {
                    e.addBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ));
                }
                break;
            case BUZZ_BLINK:
                e.addBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.lastTickPosY - 1, mc.thePlayer.posZ));
                break;
            case OLD_SPARKY:
                e.setAabb(AxisAlignedBB.fromBounds(mc.thePlayer.posX, this.lastY, mc.thePlayer.posZ, mc.thePlayer.posX + 1.0D, this.lastY, mc.thePlayer.posZ + 1.0D));
                break;
            case VERUS_GLIDE:
                if(stage <= -4) stage = 22;
                if(stage > 0) e.addBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ));
                break;
            case UPDATED_NCP:
                for(float f = 0; f < 1; f+=0.03)
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + f, mc.thePlayer.posY, mc.thePlayer.posZ, false));

                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.07, mc.thePlayer.posZ, false));

                break;
        }

    };

    @EventHandler
    private final Listener<MotionEvent> updateEventListener = e -> {
        this.setSuffix(this.mode.getValue().getName());
        final int pot = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;

        switch (this.mode.getValue()) {
            case CREATIVE:
                mc.thePlayer.capabilities.isFlying = true;
                break;
            case INVADEDLANDS:
                final DisablerModule disablerModule = Diablo.getInstance().getModuleRepository().getModuleInstance(DisablerModule.class);

                if (disablerModule.isEnabled() && disablerModule.getTicks() < 0) {
                    mc.getTimer().timerSpeed = timer.getValue();
                } else {
                    mc.getTimer().timerSpeed = 1f;
                }

                if (e.getEventType() == EventTypeEnum.PRE) {
                    mc.thePlayer.motionY = 0;
                    e.setY(Math.round(mc.thePlayer.posY));
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.prevPosX, mc.thePlayer.posY - 1, mc.thePlayer.prevPosZ), 1, new ItemStack(Blocks.stone), 1, 1, 1));
                }

                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (mc.thePlayer.ticksExisted % 2 == 0) {
                        e.setOnGround(true);
                        mc.thePlayer.motionY = 0.42F;
                        mc.thePlayer.onGround = true;
                    }
                }

                float verusSpeed;
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    verusSpeed = 0.31519f;
                } else {
                    verusSpeed = 0.24509f;
                }

                if (mc.thePlayer.hurtTime > 2 && mc.thePlayer.getHeldItem() != null && (mc.thePlayer.getHeldItem().getItem() instanceof ItemBow)) {
                    MovementUtil.setMotion(2.25 * 2);
                } else {
                    MovementUtil.setMotion(verusSpeed);
                }
                break;
            case VANILLA:
                mc.thePlayer.motionY = 0;
                break;
            case SPOOF:
                mc.thePlayer.motionY = 0;
                mc.thePlayer.onGround = true;
                break;
            case VULCAN_GLIDE:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;
                final double rando = Math.random() / 50;
                if(mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }else {
                    if(mc.thePlayer.ticksInAir > 2)
                        this.phaseTrigger = false;
                    if(mc.thePlayer.ticksInAir % 2 != 0 && mc.thePlayer.ticksInAir > 8)
                        mc.thePlayer.motionY = CollisionUtils.UNLOADED_CHUNK_MOTION;
                    else if (mc.thePlayer.ticksInAir > 8) {
                        mc.thePlayer.motionY += 0.01;
                    }

                    if (mc.thePlayer.ticksExisted % 5 == 0) {
                        //mc.thePlayer.vclip(0.25 + rando);
                        mc.thePlayer.motionY = 0.11760000228882461D;
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
                    }

                }
                ++this.phaseTicks;
                break;
            case KARHU:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;
                if(Math.random() > 0.6)
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 1, mc.thePlayer.getHeldItem(), 0, 1, 0));

                ++this.phaseTicks;
                break;
            case OLD_SPARKY:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;
                if(mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }else {
                    mc.thePlayer.motionY = CollisionUtils.UNLOADED_CHUNK_MOTION;
                }
                break;
            case VULCAN:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;

                break;
            case UPDATED_NCP:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;
                e.setY(e.getPosY() + 0.03);

                if(!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ).add(0, 2, 0)).getBlock() instanceof BlockAir) && !this.phaseTrigger) {
                    this.phaseTrigger = true;
                    this.phaseTicks++;
                    for(float f = 0; f < 0.5; f+=0.03)
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + f, mc.thePlayer.posY, mc.thePlayer.posZ, false));

                }
                if(this.phaseTrigger && this.phaseTicks > 0 && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ).add(0, 2, 0)).getBlock() instanceof BlockAir) {
                    mc.thePlayer.motionY = 0.05;
                }

                break;
            case KARHU_JUMP:
                if(e.getEventType() == EventTypeEnum.POST) break;

                if(mc.thePlayer.fallDistance > 0.1)
                    this.phaseTrigger = true;


                if(mc.thePlayer.onGround && !mc.thePlayer.movementInput.jump && this.phaseTrigger && this.stage <= 2) {
                    mc.thePlayer.jump();
                    this.phaseTrigger = false;
                    ++stage;
                }else if (this.stage > 2) {
                    this.phaseTrigger = false;
                    mc.getTimer().timerSpeed = 0.3f;
                    if(mc.thePlayer.ticksExisted % 15 == 0) {
                        mc.thePlayer.motionY = MovementUtil.getJumpMotion();
                        e.setOnGround(true);
                    }
                }
                break;
            case VULCAN_PACKET:
                if (e.getEventType() == EventTypeEnum.POST) {
                    break;
                }
                if (!this.phaseTrigger && mc.thePlayer.ticksExisted % 3 == 0 && mc.thePlayer.onGround) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, e.getPosY() - 0.42f, mc.thePlayer.posZ, false));
                    this.phaseTrigger = true;
                }

                mc.getTimer().timerSpeed = .25f;
                mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? yspeed.getValue() : mc.thePlayer.movementInput.sneak ? -yspeed.getValue() : mc.thePlayer.motionY;

                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    mc.thePlayer.motionY = CollisionUtils.UNLOADED_CHUNK_MOTION;
                    MovementUtil.strafe(this.xzspeed.getValue());
                }
            break;
            case HYCRAFT:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;

                if (mc.thePlayer.movementInput.jump) {
                    mc.thePlayer.motionY = CollisionUtils.UNLOADED_CHUNK_MOTION * -1 + Math.random() / 10;
                    e.setY(CollisionUtils.niggerizeVerticalPosition(e.getPosY()));
                    e.setOnGround(true);
                }
                if (mc.thePlayer.motionY < -0.001 && !mc.thePlayer.movementInput.sneak) {
                    mc.thePlayer.motionY -= CollisionUtils.UNLOADED_CHUNK_MOTION;
                    e.setY(CollisionUtils.niggerizeVerticalPosition(e.getPosY()));
                    e.setOnGround(true);
                }else if (!mc.thePlayer.movementInput.sneak) {
                    if(mc.thePlayer.motionY > -0.1) {
                        mc.thePlayer.motionY += CollisionUtils.UNLOADED_CHUNK_MOTION;
                        e.setY(CollisionUtils.niggerizeVerticalPosition(e.getPosY()));
                        e.setOnGround(true);
                    }
                }
                break;
            case VERUS:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;
                if(mc.thePlayer.ticksExisted%2==0) {
                    e.setOnGround(true);
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.theWorld, new BlockPos(0, 0, 0))), 0.5f, 0.5f, 0.5f));
                    e.setY(Math.floor(mc.thePlayer.posY));
                }
                final boolean fast = this.verusfast.getValue();
                final double yMotion = fast ? yspeed.getValue() : MovementUtil.getJumpMotion();

                mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? yMotion : mc.thePlayer.movementInput.sneak ? -yMotion : 0;

                if(this.verusfast.getValue()) {
                    final double multp;
                    switch(MovementUtil.potionAmplifier(Potion.moveSpeed)) {
                        default:
                            multp = MovementUtil.potionAmplifier(Potion.moveSpeed);
                            break;
                        case 2:
                            multp = 1.12D;
                            break;
                        case 3:
                            multp = 1.25D;
                            break;
                    }

                    double speedIn = MovementUtil.getBaseMoveSpeed() * 1.41D * multp;

                    if(mc.thePlayer.motionY < 0)
                        speedIn *= 0.7;

                    if(MovementUtil.getPlayerSpeed() - speedIn > 0.1)
                        speedIn *= 1 - MovementUtil.getPlayerSpeed() - speedIn;

                    if(mc.thePlayer.isUsingItem())
                        speedIn *= 0.7;

                    if(mc.thePlayer.moveStrafing > 0 || mc.thePlayer.moveForward < 0)
                        speedIn *= 0.8D;
                    MovementUtil.strafe(speedIn);
                }
                break;
            case VERUS_DAMAGE:
                if(e.getEventType() == EventTypeEnum.POST)
                    break;
                e.setY(Math.round(e.getPosY()));
                mc.getTimer().timerSpeed = (float) lastTimer;

                if(phaseTicks <= 0 && lastTimer > 1)
                    lastTimer -= 0.02F;

                if(phaseTicks > 0)
                    phaseTicks--;

                if(stage > 0)
                    stage--;

                mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? yspeed.getValue() : mc.thePlayer.movementInput.sneak ? -yspeed.getValue() : 0;

                if(mc.thePlayer.ticksExisted%6==0) {
                    e.setOnGround(true);
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.theWorld, new BlockPos(0, 0, 0))), 0.5f, 0.5f, 0.5f));
                    e.setY(Math.floor(mc.thePlayer.posY));
                }

                int val = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
                double maxSpeed = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (val == 1 ? 0.43 : 0.8) : 0.435;

                if(mc.thePlayer.moveForward > 0)
                    MovementUtil.strafe(maxSpeed);

                MovementUtil.strafe(xzspeed.getValue());
                break;
            case VERUS_GLIDE:
                if(stage > -4) stage--;

                mc.thePlayer.motionY = Math.max(-0.09800000190734864, mc.thePlayer.motionY);

                MovementUtil.strafe(pot > 0 ? 0.4 : 0.325);

                if(mc.thePlayer.moveForward > 0) MovementUtil.strafe(Math.max((pot>0 ? (pot == 1 ? 0.435 : 0.495) : 0.375), MovementUtil.getPlayerSpeed()));
                break;
            case BUZZ:
                mc.getTimer().timerSpeed = 0.4F;

                if(mc.thePlayer.hurtTime > 3) {
                    MovementUtil.strafe(9F);
                } else {
                    mc.thePlayer.setPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.posY, mc.thePlayer.lastTickPosZ);
                    mc.thePlayer.cameraYaw = 0;
                }
                break;
            case BUZZ_BLINK:
                if(mc.thePlayer.movementInput.jump) {
                    mc.thePlayer.motionX *= 0.95;
                    mc.thePlayer.motionZ *= 0.95;
                }

                if(mc.thePlayer.ticksExisted % 10 == 0) {
                    mc.thePlayer.motionX = 0;
                    mc.thePlayer.motionZ = 0;
                }
                break;
            case OLD_VULCAN:
                mc.thePlayer.motionY = mc.thePlayer.movementInput.jump ? yspeed.getValue() : mc.thePlayer.movementInput.sneak ? -yspeed.getValue() : 0;
                MovementUtil.strafe(!MovementUtil.isMoving() ? xzspeed.getValue() : 0);
                break;
        }
    };

    private double distance;

    @EventHandler
    private final Listener<TickEvent> tickEventListener = e -> {
        this.distance += mc.thePlayer.getDistanceSq(mc.thePlayer.prevPosX, mc.thePlayer.prevPosY, mc.thePlayer.prevPosZ);
    };

    @EventHandler
    private final Listener<OverlayEvent> overlayEventListener = e -> {
        if (!this.showDistance.getValue()) {
            return;
        }

        final String distance = String.format("%.2f blocks", this.distance);
        final int x = e.getScaledResolution().getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(distance) / 2;
        final int y = e.getScaledResolution().getScaledHeight() / 2 + 25;

        mc.fontRendererObj.drawStringWithShadow(distance, x, y, -1);
    };

    @EventHandler
    private final Listener<SendPacketEvent> sendPacketEventListener = event -> {
        final Packet p = event.getPacket();

        switch (mode.getValue()) {
            case VULCAN_GROUND:
                if(p instanceof C0FPacketConfirmTransaction) {
                    final C0FPacketConfirmTransaction c0f = (C0FPacketConfirmTransaction) p;

                    if(c0f.getUid() < -29000) {
                        ChatUtil.addChatMessage("Cancelled vulcan sync transaction");
                        event.setCancelled(true);
                    }
                }
                break;
            case UPDATED_NCP:
                if(!mc.thePlayer.onGround && this.phaseTrigger && this.phaseTicks > 0) {
                    event.setCancelled(true);
                    this.packetQueue.add(p);
                }else if(!this.packetQueue.isEmpty()) {
                    this.packetQueue.forEach(mc.thePlayer.sendQueue::addToSendQueue);
                    this.packetQueue.clear();
                }
                break;
            case TUBNET:
                if(event.getPacket() instanceof C03PacketPlayer)
                    event.setCancelled(true);
                break;
            case VULCAN_PACKET:
                if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
                    if(!this.phaseTrigger) {
                        this.packetQueue.add(event.getPacket());
                        event.setCancelled(true);
                    }else {
                        this.packetQueue.forEach(mc.thePlayer.sendQueue::addToSendQueue);
                        this.packetQueue.clear();
                    }
                }
                break;
        }
    };

    @EventHandler
    private final Listener<RecievePacketEvent> recievePacketEventListener = e -> {
        if(mc.isSingleplayer() || mc.thePlayer == null || mc.thePlayer.ticksExisted < 5) return;

        switch (mode.getValue()) {
            case VULCAN_PACKET:
                if(e.getPacket() instanceof S08PacketPlayerPosLook) {
                    final S08PacketPlayerPosLook flag = (S08PacketPlayerPosLook) e.getPacket();
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(flag.getX(), flag.getY(), flag.getZ(), flag.getYaw(), flag.getPitch(), false));
                    if (mc.thePlayer.getDistance(flag.getX(), flag.getY(), flag.getZ()) < 12)
                        e.setCancelled(true);
                    MovementUtil.freeze(false, false);
                    this.phaseTrigger = false;
                }
                break;
        }

    };

    enum FlightMode implements IMode {
        VANILLA("Vanilla"),
        CREATIVE("Creative"),
        SPOOF("Spoof"),
        VULCAN("Vulcan"),
        VULCAN_PACKET("Vulcan Packet"),
        VULCAN_GROUND("Vulcan Ground"),
        VULCAN_GLIDE("Vulcan Glide"),
        OLD_VULCAN("Old Vulcan"),
        SUPERCRAFT("SuperCraft"),
        HYCRAFT("HyCraft"),
        OLD_SPARKY("Old Sparky"),
        TUBNET("Tubnet"),
        VERUS("Verus"),
        VERUS_DAMAGE("Verus Damage"),
        VERUS_GLIDE("Verus Glide"),
        KARHU("Karhu"),
        KARHU_JUMP("Karhu Jump"),
        BUZZ("Buzz"),
        BUZZ_BLINK("Buzz Blink"),
        UPDATED_NCP("Updated NCP"),
        INVADEDLANDS("InvadedLands"),
        ;

        private final String name;

        FlightMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}
