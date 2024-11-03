package net.augustus.modules.movement;

import java.awt.Color;
import java.util.concurrent.LinkedBlockingQueue;

import org.lwjgl.input.Keyboard;

import io.netty.buffer.Unpooled;
import net.augustus.events.EventNoSlow;
import net.augustus.events.EventPostMotion;
import net.augustus.events.EventPreMotion;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventTick;
import net.augustus.events.EventUpdate;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.modules.misc.PostDisabler;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.utils.LogUtil;
import net.augustus.utils.PlayerUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow2 extends Module {

    private final TimeHelper timeHelper = new TimeHelper();
    private final TimeHelper lbug_TimeHelper = new TimeHelper();
    public BooleanValue startSlow = new BooleanValue(4573, "StartSlow", this, false);
    public BooleanValue swordSlowdown = new BooleanValue(7549, "Slowdown", this, false);
    public BooleanValue swordSprint = new BooleanValue(12423, "Sprint", this, false);
    public BooleanValue swordSwitch = new BooleanValue(92, "Switch", this, false);

    public BooleanValue swordC17Switch = new BooleanValue(6779, "C17Switch", this, true);
    public BooleanValue swordC08Pre = new BooleanValue(62,"C08Pre",this,false);
    public BooleanValue swordC0CPre = new BooleanValue(98416,"C0CPre",this,false);
    public BooleanValue swordC08Post = new BooleanValue(7825,"C08Post",this,true);
    public BooleanValue swordC08Update = new BooleanValue(9511,"C08Update",this,false);
    public BooleanValue swordC07NRPre = new BooleanValue(4001,"C07NReleasePre",this,false);
    public BooleanValue swordC07NRPost = new BooleanValue(7284,"C07NReleasePost",this,false);
    public BooleanValue swordC07NRUpdate= new BooleanValue(824,"C07NReleaseUp",this,false);
    public BooleanValue swordC07BRPre = new BooleanValue(8740,"C07BReleasePre",this,false);
    public BooleanValue swordC07BRPost = new BooleanValue(10106,"C07BReleasePost",this,false);
    public BooleanValue swordC07BRUpdate = new BooleanValue(6795,"C07BReleaseUp",this,false);
    public BooleanValue swordC07NDPre = new BooleanValue(7568,"C07NDropPre",this,false);
    public BooleanValue swordC07NDPost = new BooleanValue(14946,"C07NDropPost",this,false);
    public BooleanValue swordC07NDUpdate = new BooleanValue(5812,"C07NDropUp",this,false);
    public BooleanValue swordC07BDPre = new BooleanValue(11373,"C07BDropPre",this,false);
    public BooleanValue swordC07BDPost = new BooleanValue(605,"C07BDropPost",this,false);
    public BooleanValue swordC07BDUpdate = new BooleanValue(6368,"C07BDropUp",this,false);
    public BooleanValue swordBug = new BooleanValue(16163, "Bug", this, false);
    public BooleanValue swordTimer = new BooleanValue(1065, "Timer", this, false);
    public BooleanValue bowSlowdown = new BooleanValue(1198, "Slowdown", this, false);
    public BooleanValue bowSprint = new BooleanValue(7178, "Sprint", this, false);
    public BooleanValue bowSwitch = new BooleanValue(876, "Switch", this, false);
    public BooleanValue bowC17Switch = new BooleanValue(12536, "C17Switch", this, true);

    public BooleanValue bowC08Pre = new BooleanValue(7112,"C08Pre",this,false);
    public BooleanValue bowC0CPre = new BooleanValue(298341,"C0CPre",this,false);
    public BooleanValue bowC08Post = new BooleanValue(3156,"C08Post",this,false);

    public BooleanValue bowC08Update = new BooleanValue(7572,"C08Update",this,false);
    public BooleanValue bowC07NRPre = new BooleanValue(3885,"C07NReleasePre",this,false);
    public BooleanValue bowC07NRPost = new BooleanValue(10906,"C07NReleasePost",this,false);
    public BooleanValue bowC07NRUpdate= new BooleanValue(6718,"C07NReleaseUp",this,false);
    public BooleanValue bowC07BRPre = new BooleanValue(10053,"C07BReleasePre",this,false);
    public BooleanValue bowC07BRPost = new BooleanValue(4082,"C07BReleasePost",this,false);
    public BooleanValue bowC07BRUpdate = new BooleanValue(13276,"C07BReleaseUp",this,false);
    public BooleanValue bowC07NDPre = new BooleanValue(1318,"C07NDropPre",this,false);
    public BooleanValue bowC07NDPost = new BooleanValue(12029,"C07NDropPost",this,false);
    public BooleanValue bowC07NDUpdate = new BooleanValue(3993,"C07NDropUp",this,false);
    public BooleanValue bowC07BDPre = new BooleanValue(6380,"C07BDropPre",this,false);
    public BooleanValue bowC07BDPost = new BooleanValue(81,"C07BDropPost",this,false);
    public BooleanValue bowC07BDUpdate = new BooleanValue(11414,"C07BDropUp",this,false);
    public BooleanValue bowTimer = new BooleanValue(11517, "Timer", this, false);
    public BooleanValue restSlowdown = new BooleanValue(4293, "Slowdown", this, false);
    public BooleanValue restSprint = new BooleanValue(6750, "Sprint", this, false);
    public BooleanValue restSwitch = new BooleanValue(4973, "Switch", this, false);
    public BooleanValue restC17Switch = new BooleanValue(233, "C17Switch", this, false);


    public BooleanValue restC08Pre = new BooleanValue(9841,"C08Pre",this,false);
    public BooleanValue restC0CPre = new BooleanValue(96841,"C0CPre",this,false);
    public BooleanValue restC08Post = new BooleanValue(15159,"C08Post",this,false);
    public BooleanValue restC08Update = new BooleanValue(2151,"C08Update",this,false);
    public BooleanValue restC07NRPre = new BooleanValue(10787,"C07NReleasePre",this,false);
    public BooleanValue restC07NRPost = new BooleanValue(1707,"C07NReleasePost",this,false);
    public BooleanValue restC07NRUpdate= new BooleanValue(4528,"C07NReleaseUp",this,false);
    public BooleanValue restC07BRPre = new BooleanValue(5308,"C07BReleasePre",this,false);
    public BooleanValue restC07BRPost = new BooleanValue(1176,"C07BReleasePost",this,false);
    public BooleanValue restC07BRUpdate = new BooleanValue(15579,"C07BReleaseUp",this,false);
    public BooleanValue restC07NDPre = new BooleanValue(12508,"C07NDropPre",this,false);
    public BooleanValue restC07NDPost = new BooleanValue(7954,"C07NDropPost",this,false);
    public BooleanValue restC07NDUpdate = new BooleanValue(7461,"C07NDropUp",this,false);
    public BooleanValue restC07BDPre = new BooleanValue(15036,"C07BDropPre",this,false);
    public BooleanValue restC07BDPost = new BooleanValue(6670,"C07BDropPost",this,false);
    public BooleanValue restC07BDUpdate = new BooleanValue(11671,"C07BDropUp",this,false);

    public BooleanValue restBug = new BooleanValue(13473, "Bug", this, false);

    public BooleanValue restTimer = new BooleanValue(13926, "Timer", this, false);


    public DoubleValue swordForward = new DoubleValue(9818, "SwordForward", this, 0.2, 0.0, 1.0, 2);
    public DoubleValue swordStrafe = new DoubleValue(13222, "SwordStrafe", this, 0.2, 0.0, 1.0, 2);
    public DoubleValue bowForward = new DoubleValue(1359, "BowForward", this, 0.2, 0.0, 1.0, 2);
    public DoubleValue bowStrafe = new DoubleValue(8732, "BowStrafe", this, 0.2, 0.0, 1.0, 2);
    public DoubleValue restForward = new DoubleValue(5943, "RestForward", this, 0.2, 0.0, 1.0, 2);
    public DoubleValue restStrafe = new DoubleValue(15474, "RestStrafe", this, 0.2, 0.0, 1.0, 2);
    public DoubleValue timerSword = new DoubleValue(13771, "TimerSword", this, 0.2, 0.1, 2.0, 2);
    public DoubleValue timerBow = new DoubleValue(13722, "TimerBow", this, 0.2, 0.1, 2.0, 2);
    public DoubleValue timerRest = new DoubleValue(2207, "TimerRest", this, 0.2, 0.1, 2.0, 2);
    public final BooleanValue restlegitbug = new BooleanValue(1294, "LegitBug", this, true);
    private final BooleanValue restlegitbugpotionchk = new BooleanValue(14772, "LBugNoPotions", this, true);
    private final BooleanValue restlegitbugamountchk = new BooleanValue(1733, "LBugNoSingleItem", this, true);
    private final BooleanValue restLastUsingC07ND = new BooleanValue(12733, "LastUsingC07ND", this, true);

    private boolean restarted = false;

    public BooleansSetting sword = new BooleansSetting(
            25, "Sword", this, new Setting[]{this.swordSlowdown, this.swordSprint, this.swordSwitch, this.swordC17Switch, this.swordC08Pre, this.swordC0CPre, this.swordC08Post, this.swordC08Update, this.swordC07NRPre, this.swordC07NRPost, this.swordC07NRUpdate, this.swordC07BRPre, this.swordC07BRPost, this.swordC07BRUpdate, this.swordC07NDPre, this.swordC07NDPost, this.swordC07NDUpdate, this.swordC07BDPre, this.swordC07BDPost, this.swordC07BDUpdate, this.swordBug, this.swordTimer}
    );
    public BooleansSetting bow = new BooleansSetting(
            26, "Bow", this, new Setting[]{this.bowSlowdown, this.bowSprint, this.bowSwitch, this.bowC17Switch, this.bowC08Pre, this.bowC0CPre, this.bowC08Post, this.bowC08Update, this.bowC07NRPre, this.bowC07NRPost, this.bowC07NRUpdate, this.bowC07BRPre, this.bowC07BRPost, this.bowC07BRUpdate, this.bowC07NDPre, this.bowC07NDPost, this.bowC07NDUpdate, this.bowC07BDPre, this.bowC07BDPost, this.bowC07BDUpdate, this.bowTimer}
    );
    public BooleansSetting rest = new BooleansSetting(
            27432, "Rest", this, new Setting[]{this.restSlowdown, this.restSprint, this.restSwitch, this.restC17Switch, this.restC08Pre, this.restC0CPre, this.restC08Post, this.restC08Update, this.restC07NRPre, this.restC07NRPost, this.restC07NRUpdate, this.restC07BRPre, this.restC07BRPost, this.restC07BRUpdate, this.restC07NDPre, this.restC07NDPost, this.restC07NDUpdate, this.restC07BDPre, this.restC07BDPost, this.restC07BDUpdate, this.restBug, this.restlegitbug, this.restlegitbugpotionchk, this.restlegitbugamountchk, this.restLastUsingC07ND, this.restTimer}
    );
    public BooleanValue restLegitBug_PostDisFix = new BooleanValue(141465,"PostDisFix",this,true);
    public BooleanValue flagDetect = new BooleanValue(11890,"FlagDetect",this,true);
    public BooleanValue flagDetect_Debug = new BooleanValue(2889,"FlagDebug",this,false);
    //public BooleanValue flagDetect_autoRestart = new BooleanValue(7898,"FlagRestart",this,true);
    public BooleanValue autoRestart = new BooleanValue(8126,"AutoRestart",this,true);
    public BooleanValue autoRestart_NoNoti = new BooleanValue(6499,"NoNoti",this,true);


    private int counter = 0;
    // private GrimAutoBlock grimAutoBlock = new GrimAutoBlock();
    private ItemStack lastItemStack = null;
    private int dropcount = 0;
    private int acbw = 0;
    private boolean var0 = false;
    private final LinkedBlockingQueue<Packet<?>> var4 = new LinkedBlockingQueue<>();

    private boolean lastUsingRestItem = false;

    private boolean legitBugLast = false;


    public NoSlow2() {
        super("NoSlow2", new Color(10, 40, 53), Categorys.MOVEMENT);
    }

    

    @EventTarget
    public void onEventReadPacket(EventReadPacket eventReadPacket) {
        if (mc == null || mc.thePlayer == null || mc.theWorld == null) {
            return ;
        }
        if (eventReadPacket.getPacket() instanceof S08PacketPlayerPosLook) {
            if (flagDetect.getBoolean() && (mc.gameSettings.keyBindUseItem.pressed) && mc.currentScreen == null) {
                mc.gameSettings.keyBindUseItem.pressed = false;
                if (flagDetect_Debug.getBoolean()) LogUtil.addChatMessage("§F[§6NoSlow§F]§F[§4FlagDetect§5Debug§F] S08 Detected. Automatically set KeyBindUseItem Pressed = False.");
            }
        }


    }
    @EventTarget
    public void onEventNoSlow(EventNoSlow eventSlowDown) {
        if (mc == null || mc.thePlayer == null || mc.theWorld == null) {
            return ;
        }
        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem == null) {
            return;
        }
        if (currentItem != null && mc.gameSettings.keyBindUseItem.pressed && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
            // if (GrimAutoBlock.gabist && (mc.thePlayer.getDistanceToEntity(KillAura.target)) > grimAutoBlock.setrange.getValue()) return;
            if (this.timeHelper.reached(400L) || !this.startSlow.getBoolean()) {
                if (currentItem.getItem() instanceof ItemSword) {
                    eventSlowDown.setSprint(this.swordSprint.getBoolean());
                    if (this.swordSlowdown.getBoolean()) {
                        eventSlowDown.setMoveForward((float) this.swordForward.getValue());
                        eventSlowDown.setMoveStrafe((float) this.swordStrafe.getValue());
                    }
                } else if (currentItem.getItem() instanceof ItemBow) {
                    eventSlowDown.setSprint(this.bowSprint.getBoolean());
                    if (this.bowSlowdown.getBoolean()) {
                        eventSlowDown.setMoveForward((float) this.bowForward.getValue());
                        eventSlowDown.setMoveStrafe((float) this.bowStrafe.getValue());
                    }
                } else {
                    eventSlowDown.setSprint(this.restSprint.getBoolean());
                    if (this.restSlowdown.getBoolean()) {
                        eventSlowDown.setMoveForward((float) this.restForward.getValue());
                        eventSlowDown.setMoveStrafe((float) this.restStrafe.getValue());
                    }
                }
            }
        } else {
            this.timeHelper.reset();
        }
    }

    @EventTarget
    public void onEventPreMotion(EventPreMotion eventPreMotion) {
        if (mc == null || mc.thePlayer == null || mc.theWorld == null) {
            return ;
        }

        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem == null) {
            return;
        }
        if (mc.gameSettings.keyBindUseItem.pressed && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
            
            if (currentItem.getItem() instanceof ItemSword) {


                if (swordC08Pre.getBoolean()) {
                    sendC08();
                }
                if (swordC0CPre.getBoolean()) {
                    sendC0C();
                }
                if (swordC07NRPre.getBoolean()) {
                    sendC07NR();
                }
                if (swordC07BRPre.getBoolean()) {
                    sendC07BR();
                }
                if (swordC07NDPre.getBoolean()) {
                    sendC07ND();
                }
                if (swordC07BDPre.getBoolean()) {
                    sendC07BD();
                }

                if (this.swordSwitch.getBoolean()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                if (this.swordC17Switch.getBoolean() && mc.thePlayer.isBlocking()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("FuckHYT",new PacketBuffer(Unpooled.buffer())));
                    //mc.thePlayer.sendQueue.addToSendQueueDirect(new C0FPacketConfirmTransaction(3121,(short)3121,true));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("FuckHYT",new PacketBuffer(Unpooled.buffer())));
                    //mc.thePlayer.sendQueue.addToSendQueueDirect(new C0FPacketConfirmTransaction(3122,(short)3122,true));

                }



            } else if (currentItem.getItem() instanceof ItemBow) {
                if (bowC08Pre.getBoolean()) {
                    sendC08();
                }
                if (bowC0CPre.getBoolean()) {
                    sendC0C();
                }
                if (bowC07NRPre.getBoolean()) {
                    sendC07NR();
                }
                if (bowC07BRPre.getBoolean()) {
                    sendC07BR();
                }
                if (bowC07NDPre.getBoolean()) {
                    sendC07ND();
                }
                if (bowC07BDPre.getBoolean()) {
                    sendC07BD();
                }
                if (this.bowC17Switch.getBoolean()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("FuckHYT",new PacketBuffer(Unpooled.buffer())));
                    //mc.thePlayer.sendQueue.addToSendQueueDirect(new C0FPacketConfirmTransaction(3123,(short)3123,true));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("FuckHYT",new PacketBuffer(Unpooled.buffer())));
                    //mc.thePlayer.sendQueue.addToSendQueueDirect(new C0FPacketConfirmTransaction(3124,(short)3124,true));

                }
            } else if (currentItem.getItem() instanceof ItemFood || currentItem.getItem() instanceof ItemPotion || currentItem.getItem() instanceof ItemBucketMilk) {

                if (restC08Pre.getBoolean()) {
                    sendC08();
                }
                if (restC0CPre.getBoolean()) {
                    sendC0C();
                }
                if (restC07NRPre.getBoolean()) {
                    sendC07NR();
                }
                if (restC07BRPre.getBoolean()) {
                    sendC07BR();
                }
                if (restC07NDPre.getBoolean()) {
                    sendC07ND();
                }
                if (restC07BDPre.getBoolean()) {
                    sendC07BD();
                }
                if (this.restC17Switch.getBoolean()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("FuckHYT", new PacketBuffer(Unpooled.buffer())));
                    //mc.thePlayer.sendQueue.addToSendQueue(new C0FPacketConfirmTransaction(3125, (short) 3125, true));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                    mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("FuckHYT", new PacketBuffer(Unpooled.buffer())));
                    //mc.thePlayer.sendQueue.addToSendQueue(new C0FPacketConfirmTransaction(3125, (short) 3125, true));

                }
                if (restLastUsingC07ND.getBoolean()){
                    if (!mc.thePlayer.isUsingItem()){
                        lastUsingRestItem = false;
                        return;
                    }
                    if (!lastUsingRestItem) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
                    }
                    lastUsingRestItem = true;
                }
                if (this.restlegitbug.getBoolean()) {
                    if (restlegitbugpotionchk.getBoolean() && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemPotion) {
                        return;
                    }
                    if (restlegitbugamountchk.getBoolean() && mc.thePlayer.inventory.getCurrentItem().stackSize <= 1) {
                        return;
                    }
                    PostDisabler.postValue = false;

                    if (!legitBugLast) {
                        lbug_TimeHelper.reset();
                    }
                    legitBugLast = true;
                    if (lbug_TimeHelper.reached(40L)) {


                        mc.thePlayer.sendQueue.addToSendQueue(
                                new C07PacketPlayerDigging(
                                        C07PacketPlayerDigging.Action.DROP_ITEM,
                                        new BlockPos(0, 0, 0), EnumFacing.DOWN
                                )
                        );


                        mc.gameSettings.keyBindUseItem.pressed = false;
                        mc.thePlayer.stopUsingItem();


                        lbug_TimeHelper.reset();
                        PostDisabler.postValue = true;
                        legitBugLast = false;
                    }
                }
            }
        }
    }

    @EventTarget
    public void onEventPostMotion(EventPostMotion eventPostMotion) {
        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem != null && mc.gameSettings.keyBindUseItem.pressed && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
            
            if (currentItem.getItem() instanceof ItemSword) {

                // if (GrimAutoBlock.gabist && (mc.thePlayer.getDistanceToEntity(KillAura.target)) > grimAutoBlock.setrange.getValue()) return;
                if (swordC08Post.getBoolean()) {
                    sendC08();
                }
                if (swordC07NRPost.getBoolean()) {
                    sendC07NR();
                }
                if (swordC07BRPost.getBoolean()) {
                    sendC07BR();
                }
                if (swordC07NDPost.getBoolean()) {
                    sendC07ND();
                }
                if (swordC07BDPost.getBoolean()) {
                    sendC07BD();
                }
            } else if (currentItem.getItem() instanceof ItemBow) {
                if (bowC08Post.getBoolean()) {
                    sendC08();
                }
                if (bowC07NRPost.getBoolean()) {
                    sendC07NR();
                }
                if (bowC07BRPost.getBoolean()) {
                    sendC07BR();
                }
                if (bowC07NDPost.getBoolean()) {
                    sendC07ND();
                }
                if (bowC07BDPost.getBoolean()) {
                    sendC07BD();
                }
            } else if (currentItem.getItem() instanceof ItemFood || currentItem.getItem() instanceof ItemPotion || currentItem.getItem() instanceof ItemBucketMilk) {

                if (restC08Post.getBoolean()) {
                    sendC08();
                }

                if (restC07NRPost.getBoolean()) {
                    sendC07NR();
                }
                if (restC07BRPost.getBoolean()) {
                    sendC07BR();
                }
                if (restC07NDPost.getBoolean()) {
                    sendC07ND();
                }
                if (restC07BDPost.getBoolean()) {
                    sendC07BD();
                }
            }
        }
    }

    @EventTarget
    public void onEventUpdate(EventUpdate eventUpdate) throws InterruptedException {
        if (mc == null || mc.thePlayer == null || mc.theWorld == null) {
            return ;
        }
        if (!PlayerUtil.isHoldingFood() || !PlayerUtil.isUsingItemB()) legitBugLast = false;
        if (mc.thePlayer.ticksExisted % 30 == 0) {
            dropcount = 0;
        }
        if (mc.currentScreen == null && !mc.gameSettings.keyBindUseItem.pressed) {
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) mc.gameSettings.keyBindForward.pressed = true;
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) mc.gameSettings.keyBindBack.pressed = true;
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) mc.gameSettings.keyBindLeft.pressed = true;
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) mc.gameSettings.keyBindRight.pressed = true;
            if (Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode())) mc.gameSettings.keyBindSprint.pressed = true;
        }


        ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
        if (currentItem != null && mc.gameSettings.keyBindUseItem.pressed && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {

            if (currentItem.getItem() instanceof ItemSword) {
                // if (GrimAutoBlock.gabist && (mc.thePlayer.getDistanceToEntity(KillAura.target)) > grimAutoBlock.setrange.getValue()) return;
                if (this.swordTimer.getBoolean()) {
                    mc.getTimer().timerSpeed = (float) this.timerSword.getValue();
                } else {
                    mc.getTimer().timerSpeed = 1.0F;
                }
                if (swordC08Update.getBoolean()) {
                    sendC08();
                }
                if (swordC07NRUpdate.getBoolean()) {
                    sendC07NR();
                }
                if (swordC07BRUpdate.getBoolean()) {
                    sendC07BR();
                }
                if (swordC07NDUpdate.getBoolean()) {
                    sendC07ND();
                }
                if (swordC07BDUpdate.getBoolean()) {
                    sendC07BD();
                }

            } else if (currentItem.getItem() instanceof ItemBow) {
                if (this.bowTimer.getBoolean()) {
                    mc.getTimer().timerSpeed = (float) this.timerBow.getValue();
                } else {
                    mc.getTimer().timerSpeed = 1.0F;
                }


                if (this.bowSwitch.getBoolean()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }
                if (bowC08Update.getBoolean()) {
                    sendC08();
                }
                if (bowC07NRUpdate.getBoolean()) {
                    sendC07NR();
                }
                if (bowC07BRUpdate.getBoolean()) {
                    sendC07BR();
                }
                if (bowC07NDUpdate.getBoolean()) {
                    sendC07ND();
                }
                if (bowC07BDUpdate.getBoolean()) {
                    sendC07BD();
                }

            } else if (currentItem.getItem() instanceof ItemFood || currentItem.getItem() instanceof ItemPotion || currentItem.getItem() instanceof ItemBucketMilk) {
                if (this.restTimer.getBoolean()) {
                    mc.getTimer().timerSpeed = (float) this.timerRest.getValue();
                } else {
                    mc.getTimer().timerSpeed = 1.0F;
                }
                if (restC08Update.getBoolean()) {
                    sendC08();
                }
                if (restC07NRUpdate.getBoolean()) {
                    sendC07NR();
                }
                if (restC07BRUpdate.getBoolean()) {
                    sendC07BR();
                }
                if (restC07NDUpdate.getBoolean()) {
                    sendC07ND();
                }
                if (restC07BDUpdate.getBoolean()) {
                    sendC07BD();
                }

                if (this.restSwitch.getBoolean()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                }

            }
        }
    }
    @EventTarget
    public void onEventTick(EventTick eventTick) {
        if (mc == null || mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        if (mc.thePlayer.ticksExisted > 20 && !restarted && this.autoRestart.getBoolean()) {
//            restart(autoRestart_NoNoti.getBoolean());

        }
    }
    public void sendC08() {
        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
    }
    public void sendC07NR() {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,new BlockPos(0,0,0),EnumFacing.DOWN));
    }
    public void sendC07BR() {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,new BlockPos(-1,-1,-1),EnumFacing.UP));
    }
    public void sendC07ND() {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM,new BlockPos(0,0,0),EnumFacing.DOWN));
    }
    public void sendC07BD() {
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ITEM,new BlockPos(-1,-1,-1),EnumFacing.UP));
    }
    public void sendC0C() {
        mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput(0, 0.82f, false, false));
    }

    @Override
    public void onDisable() {
        restarted = true;
        mc.getTimer().timerSpeed = 1.0F;
        legitBugLast = false;
        dropcount = 0;
        acbw = 0;
        var0 = false;
        var4.clear();

    }

    @Override
    public void onEnable() {
        legitBugLast = false;
        mc.getTimer().timerSpeed = 1.0F;
        dropcount = 0;
        acbw = 0;
        var0 = false;
        var4.clear();
    }

    public void onWorld(EventWorld event) {
        legitBugLast = false;
        var0 = false;
        var4.clear();
        dropcount = 0;
        acbw = 0;
    }
}
