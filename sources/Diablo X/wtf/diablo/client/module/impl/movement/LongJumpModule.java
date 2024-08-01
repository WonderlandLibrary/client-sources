package wtf.diablo.client.module.impl.movement;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.mc.checks.CheckUtils;
import wtf.diablo.client.util.mc.collision.CollisionUtils;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

@ModuleMetaData(
        name = "Long Jump",
        description = "Allows you to jump further",
        category = ModuleCategoryEnum.MOVEMENT)
public final class LongJumpModule extends AbstractModule {

    private final ModeSetting<LongJumpMode> mode = new ModeSetting<>("Mode", LongJumpMode.WATCHDOG_BOW);

    private final NumberSetting<Integer> speed = new NumberSetting<>("Speed", 1, 1, 10, 1);

    public LongJumpModule() {
        this.registerSettings(mode, speed);
    }

    private boolean tookVelocity;
    private boolean shotBow;
    private int stage;
    private boolean jump, spoof, hasTubboBeenRapedInDaButt;
    private boolean collide;
    private final double[] spoofp = new double[3];

    @Override
    protected void onEnable() {
        super.onEnable();
        this.collide = false;
        this.stage = 0;
        jump = false;
        hasTubboBeenRapedInDaButt = false;
        spoofp[0] = mc.thePlayer.posX;
        spoofp[1] = mc.thePlayer.posY;
        spoofp[2] = mc.thePlayer.posZ;

        if (mode.getValue() == LongJumpMode.VERUS_DAMAGE) {
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ), 1, new ItemStack(Blocks.stone.getItem(mc.theWorld, new BlockPos(0, 0, 0))), 0.5f, 0.5f, 0.5f));
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, Math.round(mc.thePlayer.posY) + 4D, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer(true));
        }

        tookVelocity = false;
        shotBow = false;

        if (!mc.thePlayer.onGround)
            toggle();


        switch(mode.getValue()) {
            case VERUS:
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ, false));
                stage = 60;
                break;
            case BLOCKS_MC:
                stage = 1;
                break;
            case BUZZ:
                stage = 50;
                break;
        }

    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event ->{
        int val = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1 : 0;
        switch (mode.getValue()){
            case VERUS_DAMAGE:
                if (event.getEventType() != EventTypeEnum.PRE) {
                    return;
                }

                if (mc.thePlayer.onGround && !tookVelocity) {
                    mc.thePlayer.jump();
                    tookVelocity = true;
                } else if (mc.thePlayer.onGround) {
                    this.toggle(false);
                }

                if (tookVelocity) {
                    if (mc.thePlayer.hurtTime > 4) {
                        MovementUtil.setMotion(2);
                    }
                }
                break;
            case WATCHDOG_BOW:
                if (mc.thePlayer.onGround && !tookVelocity && !shotBow) {
                    executeBowTask(event);
                    shotBow = true;
                }
                break;
            case CLIP_NCP:
                if (event.getEventType() == EventTypeEnum.PRE) {

                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionY = 7;
                        this.jump = true;
                    } else if (this.jump) {
                        this.jump = false;
                        mc.thePlayer.motionY = CheckUtils.getGravity(MovementUtil.getJumpMotion() * 2);
                    } else {
                        if (mc.thePlayer.motionY < CollisionUtils.UNLOADED_CHUNK_MOTION) {
                            if (mc.thePlayer.ticksInAir % 4 == 0) {
                                mc.thePlayer.motionY -= CollisionUtils.ZERO_GRAVITY - 0.05;
                                mc.thePlayer.motionX *= 1.035;
                                mc.thePlayer.motionZ *= 1.035;
                            } else if (mc.thePlayer.ticksInAir % 2 == 0) {
                                mc.thePlayer.motionY = CollisionUtils.UNLOADED_CHUNK_MOTION;
                                mc.thePlayer.motionX *= 1.035;
                                mc.thePlayer.motionZ *= 1.035;
                            } else {
                                mc.thePlayer.motionY += 0.02;
                            }
                        }
                    }
                }
                break;
            case HYCRAFT:
                mc.getTimer().timerSpeed = 1.01F;
                if(stage > 10) stage--;

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.motionX *= 2.72;
                    mc.thePlayer.motionZ *= 2.72;
                    mc.thePlayer.motionY = 0.6F;
                } else {
                    if(val == 0) {
                        mc.thePlayer.motionX *= 1.0025;
                        mc.thePlayer.motionZ *= 1.0025;
                    }
                }

                if(stage > 15) jump = false;

                if(stage > 10 && mc.thePlayer.motionY < 0.11760000228881837)
                    mc.thePlayer.motionY = 0.11760000228881837;
                break;
            case TUBNET:
                if(!hasTubboBeenRapedInDaButt) {
                    mc.thePlayer.motionY = 1;
                    MovementUtil.freeze(true, false);
                }else {
                    switch(stage) {

                        case 1:

                            break;

                        default:

                            break;

                    }
                    if(mc.thePlayer.onGround) {
                        this.spoof = false;
                        this.hasTubboBeenRapedInDaButt = false;
                    }
                }
                break;
            case MINEMORA:
                if(event.getEventType() == EventTypeEnum.POST)
                    break;

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                }else {
                    mc.thePlayer.motionY+=1E-2;
                    if(mc.thePlayer.motionY < 0.16) {
                        mc.thePlayer.motionY+=2E-2;
                    }
                    MovementUtil.strafe();

                    float timer = 1.5f - (mc.thePlayer.ticksInAir / 30);
                    timer = timer < 1 ? 1 : timer;

                    mc.getTimer().timerSpeed = timer;
                }
                break;
            case BRUTE_FORCE:
                if(mc.thePlayer.fallDistance > 3 && mc.theWorld.isAirBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ))) jump = false;

                if(mc.thePlayer.onGround) mc.thePlayer.motionY = 6;

                if(mc.thePlayer.motionY < 2 && mc.thePlayer.motionY < 0) mc.thePlayer.motionY = 0;

                MovementUtil.strafe(speed.getValue());
                break;
            case VERUS:
                mc.getTimer().timerSpeed = 2.5F;
                if(stage > 10) stage--;

                if(stage > 10 && mc.thePlayer.ticksExisted % 2 == 0) mc.thePlayer.motionY = 0.42F;

                MovementUtil.strafe(0.335);

                if(stage > 15) jump = false;


                if(mc.thePlayer.moveForward > 0) MovementUtil.strafe(mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (val == 1 ? 0.43 : 0.49) : 0.375);

                if(mc.thePlayer.motionY < 0)
                    mc.thePlayer.motionY = -0.0784000015258789;
                break;
            case BUZZ:
                mc.getTimer().timerSpeed = 1.01F;
                if(stage > 10) stage--;

                if(mc.thePlayer.onGround) {
                    mc.thePlayer.motionX *= 1.72;
                    mc.thePlayer.motionZ *= 1.72;
                    mc.thePlayer.motionY = 0.6F;
                } else {
                    if(val == 0) {
                        mc.thePlayer.motionX *= 1.0025;
                        mc.thePlayer.motionZ *= 1.0025;
                    }
                }

                if(stage > 15) jump = false;

                //	if(mc.thePlayer.moveForward > 0) move.strafe(mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (val == 1 ? 0.43 : 0.49) : 0.375);

                if(stage > 10 && mc.thePlayer.motionY < 0.11760000228881837)
                    mc.thePlayer.motionY = 0.11760000228881837;
                break;
                case BLOCKS_MC:
                    if(!mc.theWorld.isAirBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 2, mc.thePlayer.posZ))) {
                        collide = true;
                        return;
                    }

                    if(!collide) return;

                    MovementUtil.strafe();
                    mc.getTimer().timerSpeed = 1F;

                    if(mc.thePlayer.onGround && !jump) {
                        mc.thePlayer.motionY=0.42f;

                        MovementUtil.strafe(0.55F);
                    }else{
                        //	move.strafe(0.5F);
                        //mc.thePlayer.motionY += 0.025;
                    }
                    break;
        }
    };

    @EventHandler
    private final Listener<RecievePacketEvent> recievePacketEventListener = e -> {
        this.setSuffix(mode.getValue().getName());

        switch (mode.getValue()){
            case VERUS_DAMAGE:
                if (e.getPacket() instanceof S12PacketEntityVelocity) {
                    tookVelocity = true;
                }
                break;
            case WATCHDOG_BOW:
                if (e.getPacket() instanceof S12PacketEntityVelocity) {
                    tookVelocity = true;
                }
                break;
        }
    };

    @EventHandler
    private final Listener<SendPacketEvent> sendPacketEventListener = event -> {
        final Packet p = event.getPacket();

        if(!mc.thePlayer.isRiding() && mc.thePlayer.ticksExisted>1 && !hasTubboBeenRapedInDaButt) {
            if (p instanceof C03PacketPlayer) {
                final C03PacketPlayer get = (C03PacketPlayer) p;


                if(!(mc.thePlayer.getDistance(spoofp[0], mc.thePlayer.posY, spoofp[2])>5.9 || Math.abs(mc.thePlayer.posY-spoofp[1])>5.9)) {
                    event.setCancelled(true);
                    spoof = true;
                    hasTubboBeenRapedInDaButt = true;
                    stage = 1;
                }else {
                    spoofp[0] = get.getPositionX();
                    spoofp[1] = get.getPositionY();
                    spoofp[2] = get.getPositionZ();
                    spoof = false;

                }
            }
        }
    };

    private void executeBowTask(final MotionEvent event){
        final int bowSlot = locateBow();

        if (bowSlot == -1) {
            return;
        }

        event.setPitch(90);

        final int oldSlot = mc.thePlayer.inventory.currentItem;

        mc.thePlayer.inventory.currentItem = bowSlot;

        final Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 20; i++) {
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            }
        });

        thread.start();


        mc.thePlayer.inventory.currentItem = oldSlot;
    }


    private int locateBow() {
        for (int i = 0; i < 9; ++i) {
            if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBow) {
                return i;
            }
        }
        return -1;
    }


    enum LongJumpMode implements IMode {
        VERUS_DAMAGE("Verus Damage"),
        BLOCKS_MC("Blocks MC"),
        NCP("NCP"),
        CLIP_NCP("Clip NCP"),
        DAMAGE("Damage"),
        VERUS("Verus"),
        VULCAN("Vulcan"),
        BUZZ("Buzz"),
        MINEMORA("Minemora"),
        BRUTE_FORCE("Brute Force"),
        TUBNET("Tubnet"),
        HYCRAFT("Hycraft"),
        WATCHDOG_BOW("Watchdog Bow");

        private final String name;

        LongJumpMode(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
